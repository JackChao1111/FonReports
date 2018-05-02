package tw.fondus.report.command.main;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.JCommander;
import com.google.common.base.Preconditions;

import strman.Strman;
import tw.fondus.commons.fews.pi.config.xml.util.XMLUtils;
import tw.fondus.commons.util.string.StringUtils;
import tw.fondus.report.command.process.ProgramArguments;
import tw.fondus.report.commons.util.ReportsDocxUtils;
import tw.fondus.report.commons.xml.ReportMapping;

public class FonReportsWordProcess {
	private Logger log = LoggerFactory.getLogger( this.getClass() );

	public static void main( String[] args ) {
		ProgramArguments arguments = new ProgramArguments();
		new FonReportsWordProcess().run( args, arguments );
	}

	/**
	 * Use arguments to execute program with standard Command-Line Interface.
	 * 
	 * @param args
	 * @param arguments
	 */
	public void run( String[] args, ProgramArguments arguments ) {
		JCommander command = new JCommander( arguments );

		/** Run the program **/
		command.parse( args );

		if ( arguments.isHelp() ) {
			command.usage();
		} else {
			Path basePath = Paths.get( arguments.getBasePath() );
			Preconditions.checkState( Files.exists( basePath ), "Can not find work directory exist." );

			Path templatePath = Paths.get( Strman.append( arguments.getBasePath(), arguments.getTemplate() ) );
			Preconditions.checkState( Files.exists( templatePath ), "Can not find template file exist." );

			Path configPath = Paths.get( Strman.append( arguments.getBasePath(), arguments.getConfig() ) );
			Preconditions.checkState( Files.exists( configPath ),
					"Can not find report mapping of XML configuration exist." );

			this.replaceProcess( arguments, basePath, templatePath, configPath );
		}
	}

	/**
	 * The main process logic.
	 * 
	 * @param arguments
	 * @param basePath
	 * @param templatePath
	 * @param configPath
	 */
	public void replaceProcess( ProgramArguments arguments, Path basePath, Path templatePath, Path configPath ) {
		try {
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load( templatePath.toFile() );

			ReportMapping reportMapping = XMLUtils.fromXML( configPath.toFile(), ReportMapping.class );
			reportMapping.getImages().getMappings().forEach( mapping -> {
				ReportsDocxUtils.replaceToImage( wordMLPackage, mapping.getId(),
						Strman.append( basePath.toString(), StringUtils.PATH, mapping.getValue() ) );
			} );

			Map<String, String> mappings = reportMapping.getTexts().getMappings().stream().collect(
					Collectors.toMap( mapping -> mapping.getId(), mapping -> mapping.getValue() ) );
			ReportsDocxUtils.replaceVariable( wordMLPackage, mappings );

			ReportsDocxUtils.save( wordMLPackage,
					Strman.append( basePath.toString(), StringUtils.PATH, arguments.getOutput() ) );

		} catch (Docx4JException e) {
			log.error( "FonReportsWordProcess: load word template has something wrong!", e );
		} catch (Exception e) {
			log.error( "FonReportsWordProcess: load mapping xml configuration has something wrong!", e );
		}
	}
}
