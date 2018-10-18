package tw.fondus.report.flood.slide.util.svg;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import nl.wldelft.util.FileUtils;
import strman.Strman;
import tw.fondus.commons.fews.pi.json.timeseries.PiTimeSeriesArray;
import tw.fondus.commons.fews.pi.util.timeseries.PiSeriesUtils;
import tw.fondus.commons.util.string.StringUtils;
import tw.fondus.commons.util.svg.SVGUtils;
import tw.fondus.commons.util.xml.Dom4JUtils;

/**
 * Create rainfall image from SVG file.
 * 
 * @author Chao
 *
 */
public class SVGRainfallImageCreator {
	public static final BigDecimal DEFAULT_LEVEL = new BigDecimal( 5 );
	public static final BigDecimal LEVEL_1 = new BigDecimal( 30 );
	public static final BigDecimal LEVEL_2 = new BigDecimal( 60 );
	public static final BigDecimal LEVEL_3 = new BigDecimal( 90 );
	public static final BigDecimal LEVEL_4 = new BigDecimal( 120 );
	public static final BigDecimal LEVEL_5 = new BigDecimal( 150 );

	/**
	 * Create image by SVG file and pi accumulate series data of county rainfall.
	 * 
	 * @param propertiesMap
	 * @param piTimeSeriesArrayMap
	 * @param svgPath
	 * @param svgFileNameWithOutExt
	 * @param exportPtah
	 */
	public void createImageBySvg( Map<String, PiTimeSeriesArray> piTimeSeriesArrayMap, String svgPath,
			String svgFileNameWithOutExt, String exportPath ) {
		try {
			File svgFile = new File( Strman.append( svgPath, StringUtils.PATH, svgFileNameWithOutExt, ".svg" ) );

			if ( svgFile.exists() ) {
				Document svgDocument = Dom4JUtils.parseXML( svgFile );
				Element rootElement = svgDocument.getRootElement();	
				List<Element> paths = rootElement.elements( "path" );

				paths.forEach( path -> {
					String pathId = path.attributeValue( "id" );
					Attribute classAttribute = path.attribute( "class" );
					PiTimeSeriesArray timedSeriesArray = piTimeSeriesArrayMap.get( pathId );
					BigDecimal hour6 = PiSeriesUtils.calculateAccumulate( timedSeriesArray, 74, 79 );
					path.remove( classAttribute );
					if ( hour6.compareTo( DEFAULT_LEVEL ) == -1 ) {
						path.addAttribute( "class", "defaultCounty" );
					} else if ( hour6.compareTo( LEVEL_1 ) == -1 ) {
						path.addAttribute( "class", "levelOneCounty" );
					} else if ( hour6.compareTo( LEVEL_2 ) == -1 ) {
						path.addAttribute( "class", "levelTwoCounty" );
					} else if ( hour6.compareTo( LEVEL_3 ) == -1 ) {
						path.addAttribute( "class", "levelThreeCounty" );
					} else if ( hour6.compareTo( LEVEL_4 ) == -1 ) {
						path.addAttribute( "class", "levelFourCounty" );
					} else if ( hour6.compareTo( LEVEL_5 ) == -1 ) {
						path.addAttribute( "class", "levelFiveCounty" );
					} else {
						path.addAttribute( "class", "levelSixCounty" );
					}
				} );

				String tempSvgPath = Strman.append( exportPath, StringUtils.PATH, svgFileNameWithOutExt, ".svg" );
				String exportImagePath = Strman.append( exportPath, StringUtils.PATH, svgFileNameWithOutExt,
						"_Rainfall.png" );

				Dom4JUtils.write( svgDocument, tempSvgPath );
				SVGUtils.setWidth( 4000 );
				SVGUtils.export( tempSvgPath, exportImagePath );
				FileUtils.delete( tempSvgPath );
			} else {
				throw new Exception( "SVG file is not exit." );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
