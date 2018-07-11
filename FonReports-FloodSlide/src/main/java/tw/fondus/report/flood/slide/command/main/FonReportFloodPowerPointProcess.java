package tw.fondus.report.flood.slide.command.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.xml.bind.JAXBException;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.PresentationMLPackage;
import org.docx4j.openpackaging.parts.PresentationML.SlidePart;
import org.joda.time.DateTime;
import org.pptx4j.Pptx4jException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.JCommander;
import com.google.common.base.Preconditions;

import strman.Strman;
import tw.fondus.commons.fews.pi.config.xml.util.XMLUtils;
import tw.fondus.commons.fews.pi.json.accumulate.PiAccumulatedSeries;
import tw.fondus.commons.fews.pi.json.accumulate.PiAccumulatedSeriesCollection;
import tw.fondus.commons.fews.pi.json.user.UserResponse;
import tw.fondus.commons.json.util.JSONUtils;
import tw.fondus.commons.util.string.StringUtils;
import tw.fondus.commons.util.time.TimeUtils;
import tw.fondus.report.commons.util.ReportsPptxUtils;
import tw.fondus.report.commons.xml.pptx.Image;
import tw.fondus.report.commons.xml.pptx.SlideMapping;
import tw.fondus.report.flood.json.hotspot.HotSpot;
import tw.fondus.report.flood.json.hotspot.Properties;
import tw.fondus.report.flood.property.ConfigProperties;
import tw.fondus.report.flood.slide.command.process.ProgramArguments;
import tw.fondus.report.flood.slide.table.CountyEWTable;
import tw.fondus.report.flood.slide.table.CountyIWTable;
import tw.fondus.report.flood.slide.table.Forecasting6hrRainfallTable;
import tw.fondus.report.flood.slide.table.HotSpotCountTable;
import tw.fondus.report.flood.slide.table.Table;
import tw.fondus.report.flood.slide.util.HttpUtils;
import tw.fondus.report.flood.slide.util.data.DataTransformUtils;
import tw.fondus.report.flood.slide.util.svg.SVGHotSpotImageCreator;
import tw.fondus.report.flood.slide.util.svg.SVGRainfallImageCreator;
import tw.fondus.report.flood.slide.xml.http.HttpConfig;

public class FonReportFloodPowerPointProcess {
	private Logger log = LoggerFactory.getLogger( this.getClass() );
	private PresentationMLPackage presentationMLPackage;
	DateTime reportT0;

	public static void main( String[] args ) {
		ProgramArguments arguments = new ProgramArguments();
		new FonReportFloodPowerPointProcess().run( args, arguments );
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
			String basePath = arguments.getBasePath();
			Preconditions.checkState( Files.exists( Paths.get( basePath ) ), "Can not find work directory exist." );

			Path propertiesPath = Paths.get( Strman.append( basePath, StringUtils.PATH, arguments.getProperties() ) );
			Preconditions.checkState( Files.exists( propertiesPath ), "Can not find properties config exist." );

			this.createSlideProcess( basePath, propertiesPath );
		}
	}

	private void createSlideProcess( String basePath, Path propertiesPath ) {
		try {
			// Get all config file
			ConfigProperties configProperties = new ConfigProperties( propertiesPath );

			String templatePath = Strman.append( basePath, StringUtils.PATH, configProperties.getTemplateFolder() );
			String exportPath = Strman.append( basePath, StringUtils.PATH, configProperties.getExportFolder() );

			HttpConfig httpConfig = XMLUtils.fromXML(
					Paths.get( Strman.append( basePath, StringUtils.PATH, configProperties.getHttpConfig() ) ).toFile(),
					HttpConfig.class );
			SlideMapping slideMapping = XMLUtils.fromXML( Paths
					.get( Strman.append( basePath, StringUtils.PATH, configProperties.getSlideConfig() ) ).toFile(),
					SlideMapping.class );
			HotSpot hotSpot = JSONUtils.fromJSON(
					new String( Files.readAllBytes( Paths.get( Strman.append(
							Strman.append( basePath, StringUtils.PATH, configProperties.getHotSpotJSON() ) ) ) ) ),
					HotSpot.class );

			// Slide mapping image config to map.
			Map<String, Image> slideMappingImageMap = DataTransformUtils.slideMappingImageToMap( slideMapping );

			// Get template pptx file.
			presentationMLPackage = ReportsPptxUtils.open( Strman.append( basePath, StringUtils.PATH,
					configProperties.getTemplateFolder(), StringUtils.PATH, configProperties.getPptxTemplate() ) );

			// Get REST API user response.
			Optional<UserResponse> optResponse = HttpUtils.login( httpConfig.getLogin().getUrl(),
					httpConfig.getLogin().getAccount(), httpConfig.getLogin().getPassword() );

			// Create text variable mappings.
			Map<String, String> mappings = new HashMap<String, String>();

			optResponse.ifPresent( response -> {
				// Get accumulated series data by http config.
				httpConfig.getAccumulatedSeries().getList().forEach( accumulatedSeries -> {
					if ( accumulatedSeries.getId().equals( "County" ) ) {
						// Get county accumulate from REST API.
						Optional<PiAccumulatedSeriesCollection> optCountyPiAccumulatedSeriesCollection = HttpUtils
								.getAccumulated( accumulatedSeries.getUrl(), accumulatedSeries.getStart(),
										accumulatedSeries.getEnd(), accumulatedSeries.getBackward(), response );
						optCountyPiAccumulatedSeriesCollection.ifPresent( countyPiAccumulatedSeriesCollection -> {
							// Get REST API T0 time for report.
							reportT0 = countyPiAccumulatedSeriesCollection.getTimeZero();
							mappings.put( "Time", Strman.append(
									TimeUtils.toString( reportT0, TimeUtils.YMDHMS, TimeUtils.UTC8 ), "+08:00" ) );
							try {
								// Get slide of Taiwan rainfall 6hr forecasting.
								SlidePart slidePart = presentationMLPackage.getMainPresentationPart().getSlide( 1 );
								slidePart.variableReplace( mappings );

								// Transform list to map format.
								Map<String, PiAccumulatedSeries> countyPiAccumulateSeriesMap = DataTransformUtils
										.piAccumulatedSeriesCollectionToMap( countyPiAccumulatedSeriesCollection );

								// Create Taiwan rainfall image from svg and
								// accumulate data.
								SVGRainfallImageCreator rainfallImage = new SVGRainfallImageCreator();
								rainfallImage.createImageBySvg( countyPiAccumulateSeriesMap, templatePath, "Taiwan",
										exportPath );

								// Add Taiwan rainfall image into slide.
								Image taiwanRainfallImage = slideMappingImageMap.get( "Taiwan_Rainfall" );
								ReportsPptxUtils.addImage( presentationMLPackage, slidePart,
										Strman.append( exportPath, StringUtils.PATH, taiwanRainfallImage.getValue() ),
										taiwanRainfallImage );

								// Create Taiwan rainfall 6hr forecasting table
								// info.
								List<String[]> rainfall6hrForecasting = new ArrayList<String[]>();
								slideMapping.getCounties().getList().forEach( county -> {
									rainfall6hrForecasting.add( new String[] { Strman.append( county.getcName(), "地區" ),
											countyPiAccumulateSeriesMap.get( county.getId() )
													.getAccumulated()
													.getHour6()
													.toString() } );
								} );

								// Add Taiwan rainfall 6hr forecasting table
								// into slide.
								Table forecasting6hrTable = new Forecasting6hrRainfallTable( 2302791, 5100911, 4944980,
										1048887 );
								forecasting6hrTable.createGrid( rainfall6hrForecasting );
								ReportsPptxUtils.addTable( slidePart, forecasting6hrTable.getTable() );
							} catch (JAXBException e) {
								log.error( "Variable replace has something wrong." );
							} catch (Docx4JException e) {
								log.error( "Add table into slide has something wrong." );
							} catch (Pptx4jException e) {
								log.error( "Get slide from presentationMLPackage has something wrong." );
							} catch (Exception e) {
								log.error( "Add image int slide has something wrong." );
							}
						} );
					} else if ( accumulatedSeries.getId().equals( "HotSpot" ) ) {
						// Get hot spot accumulate from REST API.
						Optional<PiAccumulatedSeriesCollection> optHotSpotPiAccumulatedSeriesCollection = HttpUtils
								.getAccumulated( accumulatedSeries.getUrl(), accumulatedSeries.getStart(),
										accumulatedSeries.getEnd(), accumulatedSeries.getBackward(), response );

						optHotSpotPiAccumulatedSeriesCollection.ifPresent( hotSpotPiAccumulatedSeriesCollection -> {
							try {
								// Get REST API T0 time for report.
								reportT0 = hotSpotPiAccumulatedSeriesCollection.getTimeZero();
								mappings.put( "Time", Strman.append(
										TimeUtils.toString( reportT0, TimeUtils.YMDHMS, TimeUtils.UTC8 ), "+08:00" ) );

								// Get slide of Taiwan hot spot.
								SlidePart slidePart = presentationMLPackage.getMainPresentationPart().getSlide( 2 );
								slidePart.variableReplace( mappings );

								// Transform list to map format.
								Map<String, PiAccumulatedSeries> hotSpotPiAccumulateSeriesMap = DataTransformUtils
										.piAccumulatedSeriesCollectionToMap( hotSpotPiAccumulatedSeriesCollection );
								Map<String, Properties> propertiesMap = DataTransformUtils
										.hotSpotJSONToPropertiesMap( hotSpot );

								// Create Taiwan hot spot image from svg and
								// accumulate data.
								SVGHotSpotImageCreator hotSpotImageTaiwan = new SVGHotSpotImageCreator();
								hotSpotImageTaiwan.createImageBySvg( propertiesMap, hotSpotPiAccumulateSeriesMap,
										templatePath.toString(), "Taiwan", exportPath );

								// Add Taiwan hot spot image into slide.
								Image taiwanHotSpotImage = slideMappingImageMap.get( "Taiwan" );
								ReportsPptxUtils.addImage( presentationMLPackage, slidePart,
										Strman.append( exportPath, StringUtils.PATH, taiwanHotSpotImage.getValue() ),
										taiwanHotSpotImage );

								// Create hot spot of count and each county.
								List<String[]> hotSpotCount = new ArrayList<String[]>();
								IntStream.range( 0, slideMapping.getCounties().getList().size() ).forEach( i -> {
									// Create county hot spot image from svg and
									// accumulate data.
									SVGHotSpotImageCreator hotSpotImageCounty = new SVGHotSpotImageCreator();
									hotSpotImageCounty.createImageBySvg( propertiesMap, hotSpotPiAccumulateSeriesMap,
											templatePath.toString(),
											slideMapping.getCounties().getList().get( i ).geteName(), exportPath );

									// Add warning hot spot count by each
									// county.
									hotSpotCount.add( new String[] {
											Strman.append( slideMapping.getCounties().getList().get( i ).getcName(),
													"地區" ),
											String.valueOf( hotSpotImageCounty.getWarningPropertiesEW().size() ),
											String.valueOf( hotSpotImageCounty.getWarningPropertiesIW().size() ) } );

									// If county hot spot pass warning level
									// Add image and table into slide.
									if ( hotSpotImageCounty.getWarningPropertiesEW().size() > 0
											|| hotSpotImageCounty.getWarningPropertiesIW().size() > 0 ) {
										try {
											// Get county slide.
											SlidePart slidePartCounty = presentationMLPackage.getMainPresentationPart()
													.getSlide( i + 3 );

											// Define table info of external
											// water and inner water size.
											int contentMaxSizeEW = 5;
											int contentMaxSizeIW = 5;
											if ( hotSpotImageCounty.getWarningPropertiesEW().size() == 0 ) {
												contentMaxSizeEW = 0;
												contentMaxSizeIW = 10;
											} else if ( hotSpotImageCounty.getWarningPropertiesIW().size() == 0 ) {
												contentMaxSizeEW = 10;
												contentMaxSizeIW = 0;
											}

											// Create warning info.
											List<String[]> countyEWContents = new ArrayList<String[]>();
											List<String[]> countyIWContents = new ArrayList<String[]>();
											for ( int propertiesNum = 0; propertiesNum < contentMaxSizeEW; propertiesNum++ ) {
												if ( propertiesNum >= hotSpotImageCounty.getWarningPropertiesEW()
														.size() ) {
													break;
												} else {
													Properties properties = hotSpotImageCounty.getWarningPropertiesEW()
															.get( propertiesNum );
													countyEWContents.add( new String[] { properties.getRiver(),
															properties.getBasin(), properties.getProtection(),
															properties.getFloodProte(), properties.getPotentialC() } );
												}
											}

											for ( int propertiesNum = 0; propertiesNum < contentMaxSizeIW; propertiesNum++ ) {
												if ( propertiesNum >= hotSpotImageCounty.getWarningPropertiesIW()
														.size() ) {
													break;
												} else {
													Properties properties = hotSpotImageCounty.getWarningPropertiesIW()
															.get( propertiesNum );
													countyIWContents.add( new String[] { properties.getTown(),
															properties.getAddress(), properties.getVulnerabil(),
															properties.getDisasterPr() } );
												}
											}

											// Add county hot spot image into
											// slide.
											Image countyHotSpotImage = slideMappingImageMap
													.get( slideMapping.getCounties().getList().get( i ).geteName() );
											ReportsPptxUtils.addImage(
													presentationMLPackage, slidePartCounty, Strman.append( exportPath,
															StringUtils.PATH, countyHotSpotImage.getValue() ),
													countyHotSpotImage );

											// Add external water table into
											// slide.
											Table countyEWTable = new CountyEWTable( 5546381, 2398034, 3429001,
													985696 );
											countyEWTable.createGrid( countyEWContents );
											ReportsPptxUtils.addTable( slidePartCounty, countyEWTable.getTable() );

											// Add inner water table into slide.
											Table countyIWTable = new CountyIWTable( 5546382, 2559095, 3429000,
													3660730 );
											countyIWTable.createGrid( countyIWContents );
											ReportsPptxUtils.addTable( slidePartCounty, countyIWTable.getTable() );
										} catch (Docx4JException e) {
											log.error( "Add table into slide has something wrong." );
										} catch (Pptx4jException e) {
											log.error( "Get slide from presentationMLPackage has something wrong." );
										} catch (Exception e) {
											log.error( "Add image int slide has something wrong." );
										}
									}
								} );

								// Add Taiwan hot spot count table into slide.
								Table hotSpotCountTable = new HotSpotCountTable( 2456798, 7080656, 4971557, 885490 );
								hotSpotCountTable.createGrid( hotSpotCount );
								ReportsPptxUtils.addTable( slidePart, hotSpotCountTable.getTable() );

								// Remove slide of county of no warning hot
								// spot.
								for ( int i = hotSpotCount.size() - 1; i >= 0; i-- ) {
									if ( hotSpotCount.get( i )[1].equals( "0" )
											&& hotSpotCount.get( i )[2].equals( "0" ) ) {
										presentationMLPackage.getMainPresentationPart().removeSlide( i + 3 );
									}
								}
							} catch (Docx4JException e) {
								log.error( "Add table into slide has something wrong." );
							} catch (Pptx4jException e) {
								log.error( "Get slide from presentationMLPackage has something wrong." );
							} catch (Exception e) {
								log.error( "Add image int slide has something wrong." );
							}
						} );
					}
				} );
			} );

			// Add report t0 time into cover slide.
			SlidePart slidePart = presentationMLPackage.getMainPresentationPart().getSlide( 0 );
			mappings.put( "Time", Strman.append( TimeUtils.toString( reportT0, TimeUtils.YMDHMS, TimeUtils.UTC8 ) ) );
			slidePart.variableReplace( mappings );

			// Save presentationMLPackage by using file name with report t0
			// time.
			ReportsPptxUtils.save( presentationMLPackage,
					Strman.append( exportPath, StringUtils.PATH, "PPT", StringUtils.PATH,
							TimeUtils.toString( reportT0, TimeUtils.YMDHMS_NONSPLITE, TimeUtils.UTC8 ), ".pptx" ) );
		} catch (JAXBException e) {
			log.error( "Variable replace has something wrong." );
		} catch (Docx4JException e) {
			log.error( "Saving presentationMLPackage has something wrong." );
		} catch (Pptx4jException e) {
			log.error( "Get slide from presentationMLPackage has something wrong." );
		} catch (IOException e) {
			log.error( "Reading JSON file has something wrong." );
		} catch (Exception e) {
			log.error( "Parsing XML file has something wrong." );
		}
	}
}
