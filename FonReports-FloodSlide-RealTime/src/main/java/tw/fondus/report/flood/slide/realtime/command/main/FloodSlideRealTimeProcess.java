package tw.fondus.report.flood.slide.realtime.command.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.PresentationMLPackage;
import org.docx4j.openpackaging.parts.PresentationML.SlidePart;
import org.pptx4j.Pptx4jException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.JCommander;
import com.google.common.base.Preconditions;

import strman.Strman;
import tw.fondus.commons.fews.pi.config.xml.util.XMLUtils;
import tw.fondus.commons.fews.pi.json.user.UserResponse;
import tw.fondus.commons.fews.pi.json.warning.PiWarning;
import tw.fondus.commons.fews.pi.json.warning.PiWarningSets;
import tw.fondus.commons.fews.pi.util.risk.RiskUtils;
import tw.fondus.commons.util.string.StringUtils;
import tw.fondus.commons.util.time.TimeUtils;
import tw.fondus.report.commons.slide.ITable;
import tw.fondus.report.commons.util.HttpUtils;
import tw.fondus.report.commons.util.ReportsPptxUtils;
import tw.fondus.report.flood.slide.realtime.command.process.ProgramArguments;
import tw.fondus.report.flood.slide.realtime.property.RESTProperties;
import tw.fondus.report.flood.slide.realtime.table.FloodTable;
import tw.fondus.report.flood.slide.realtime.util.data.DataUtils;
import tw.fondus.report.flood.slide.realtime.util.svg.SVGVillagesFloodImageCreator;
import tw.fondus.report.flood.slide.realtime.xml.mapping.County;
import tw.fondus.report.flood.slide.realtime.xml.mapping.ImageLocation;
import tw.fondus.report.flood.slide.realtime.xml.mapping.SlideMapping;

/**
 * Flood slide main process for real time.
 * 
 * @author Chao
 *
 */
public class FloodSlideRealTimeProcess {
	private Logger log = LoggerFactory.getLogger( this.getClass() );

	public static void main( String[] args ) {
		ProgramArguments arguments = new ProgramArguments();
		new FloodSlideRealTimeProcess().run( args, arguments );
	}

	/**
	 * Use arguments to execute program with standard Command-Line Interface.
	 * 
	 * @param args
	 * @param arguments
	 */
	public void run( String[] args, ProgramArguments arguments ) {
		log.info( "Realtime flood slide process start." );
		JCommander command = new JCommander( arguments );

		command.parse( args );

		if ( arguments.isHelp() ) {
			command.usage();
		} else {
			String basePath = arguments.getBasePath();
			Preconditions.checkState( Files.exists( Paths.get( basePath ) ), "Can not find work directory exist." );

			String templatePath = Strman.append( basePath, StringUtils.PATH, arguments.getTemplateDir() );
			Preconditions.checkState( Files.exists( Paths.get( templatePath ) ),
					"Can not find template directory exist." );

			String outputPath = Strman.append( basePath, StringUtils.PATH, arguments.getOutputDir() );
			Preconditions.checkState( Files.exists( Paths.get( outputPath ) ), "Can not find output directory exist." );

			Path restPropertiesPath = Paths
					.get( Strman.append( basePath, StringUtils.PATH, arguments.getInputs().get( 0 ) ) );
			Preconditions.checkState( Files.exists( restPropertiesPath ), "Can not find REST-API properties exist." );

			Path slideMappingPath = Paths
					.get( Strman.append( basePath, StringUtils.PATH, arguments.getInputs().get( 1 ) ) );
			Preconditions.checkState( Files.exists( slideMappingPath ),
					"Can not find XML configuration of slide mapping exist." );

			this.runProcess( templatePath, outputPath, arguments.getInputs().get( 2 ), restPropertiesPath,
					slideMappingPath );
		}
	}

	/**
	 * Run flood slide process for real time.
	 * 
	 * @param templatePath
	 * @param outputPath
	 * @param tempPPTXFileName
	 * @param restPropertiesPath
	 * @param slideMappingPath
	 */
	private void runProcess( String templatePath, String outputPath, String tempPPTXFileName, Path restPropertiesPath,
			Path slideMappingPath ) {
		try {
			RESTProperties restProperties = new RESTProperties( restPropertiesPath );
			SlideMapping slideMapping = XMLUtils.fromXML( slideMappingPath.toFile(), SlideMapping.class );

			Optional<UserResponse> optResponse = HttpUtils.login( restProperties.getLoginUrl(),
					restProperties.getAccount(), restProperties.getPassword() );

			optResponse.ifPresent( response -> {
				slideMapping.getCounties().forEach( county -> {
					String villagesFloodUrl = restProperties.getVillagesFloodUrl()
							.replace( "{Region}", county.getRegion() )
							.replace( "{City}", county.getCity() );
					Optional<PiWarningSets> optPiWarningSets = HttpUtils.getWarnings( villagesFloodUrl, response );
					optPiWarningSets.ifPresent( piWarningSets -> {
						this.createSlide( templatePath, outputPath, tempPPTXFileName, county, piWarningSets );
					} );
				} );
			} );
		} catch (IOException e) {
			log.error( "Flood slide realtime process: Parsing properties has something wrong." );
		} catch (Exception e) {
			log.error(
					"Flood slide realtime process: Parsing XML configuration of slide mapping has something wrong." );
		}
	}

	/**
	 * Create flood slide by county.
	 * 
	 * @param templatePath
	 * @param outputPath
	 * @param tempPPTXFileName
	 * @param county
	 * @param piWarningSets
	 */
	private void createSlide( String templatePath, String outputPath, String tempPPTXFileName, County county,
			PiWarningSets piWarningSets ) {
		try {
			PresentationMLPackage presentationMLPackage = ReportsPptxUtils
					.open( Paths.get( Strman.append( templatePath, StringUtils.PATH, tempPPTXFileName ) ) );
			SlidePart slidePart = presentationMLPackage.getMainPresentationPart().getSlide( 0 );

			List<PiWarning> sortedList = Stream.of( piWarningSets.getCollection() )
					.sorted( Comparator.comparing( PiWarning::getWarning )
							.thenComparing( PiWarning::getRatio )
							.thenComparing( PiWarning::getValue )
							.thenComparing( PiWarning::getName )
							.reversed() )
					.collect( Collectors.toList() );
			List<String[]> contents = new ArrayList<>();
			for ( int i = 0; i < sortedList.size(); i++ ) {
				if ( i == 10 ) {
					break;
				} else {
					PiWarning piWarning = sortedList.get( i );
					contents.add( new String[] { piWarning.getName(),
							piWarning.getCount().multiply( RiskUtils.FACTOR_AREA ).toString(),
							piWarning.getValue().toString(), piWarning.getRatio().toString(),
							piWarning.getWarning() } );
				}
			}

			ITable floodTable = new FloodTable( 6096000, 6096000, 4064000, 3175000 );
			floodTable.createGrid( contents );
			ReportsPptxUtils.addTable( slidePart, floodTable.getTable() );

			Map<String, PiWarning> piWarningMap = DataUtils.piWarningSetsToMap( piWarningSets );
			String tempSVGPath = Strman.append( templatePath, StringUtils.PATH, county.getRegion(), StringUtils.PATH,
					county.getCity() );
			SVGVillagesFloodImageCreator.createImageBySVG( piWarningMap, tempSVGPath );
			ImageLocation imageLocation = county.getImageLocation();
			ReportsPptxUtils.addImage( presentationMLPackage, slidePart,
					Strman.append( tempSVGPath, StringUtils.PATH, "Villages.png" ), imageLocation.getOffX(),
					imageLocation.getOffY(), imageLocation.getExtcX(), imageLocation.getExtcY() );

			Map<String, String> mappings = new HashMap<String, String>();
			mappings.put( "city", county.getName() );
			mappings.put( "area", DataUtils.calculateCityFloodArea( piWarningSets ).toString() );
			mappings.put( "time", TimeUtils.toString( piWarningSets.getTimeZero(), TimeUtils.YMDHMS_SLASH, TimeUtils.UTC8 ) );
			slidePart.variableReplace( mappings );

			presentationMLPackage.save( new File( Strman.append( outputPath, StringUtils.PATH, county.getRegion(), StringUtils.PATH,
					county.getCity(), StringUtils.PATH, "Tows.pptx" ) ) );
		} catch (Docx4JException e) {
			log.error( "Flood slide realtime process: Open PPTX file has something wrong." );
		} catch (Pptx4jException e) {
			log.error( "Flood slide realtime process: Get slide has something wrong." );
		} catch (Exception e) {
			log.error( "Flood slide realtime process: Create image by SVG has something wrong." );
		}
	}
}
