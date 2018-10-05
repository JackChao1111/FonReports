package tw.fondus.report.flood.slide.util.svg;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import nl.wldelft.util.FileUtils;
import strman.Strman;
import tw.fondus.commons.fews.pi.json.accumulate.PiAccumulatedSeries;
import tw.fondus.commons.util.string.StringUtils;
import tw.fondus.commons.util.svg.SVGUtils;
import tw.fondus.commons.util.xml.Dom4JUtils;
import tw.fondus.report.flood.json.hotspot.Properties;

/**
 * Create hot spot image from SVG file.
 * 
 * @author Chao
 *
 */
public class SVGHotSpotImageCreator {
	private static final int MISSING_VALUE = -999;
	private static final String INNER_WATER_CLASS="hotSpotInnerWater";
	private static final String EXTERNAL_WATER_CLASS="hotSpotExternalWater";
	private static final String DEFAULT_CLASS="defaultHotSpot";
	private static final String ATTRIBUTE_CLASS_NAME="class";
	private List<Properties> warningPropertiesIW;
	private List<Properties> warningPropertiesEW;

	/**
	 * Create image by SVG file and pi accumulate series data of hot spot.
	 * 
	 * @param propertiesMap
	 * @param piAccumulateSeriesMap
	 * @param svgPath
	 * @param svgFileNameWithOutExt
	 * @param exportPtah
	 */
	public void createImageBySvg( Map<String, Properties> propertiesMap,
			Map<String, PiAccumulatedSeries> piAccumulateSeriesMap, String svgPath, String svgFileNameWithOutExt,
			String exportPtah ) {
		try {
			File svgFile = new File( Strman.append( svgPath, StringUtils.PATH, svgFileNameWithOutExt, ".svg" ) );

			if ( svgFile.exists() ) {
				Document svgDocument = Dom4JUtils.parseXML( svgFile );
				Element rootElement = svgDocument.getRootElement();
				List<Element> circles = rootElement.elements( "circle" );

				warningPropertiesIW = new ArrayList<>();
				warningPropertiesEW = new ArrayList<>();		
				circles.forEach( circle -> {
					String circleId = circle.attributeValue( "id" );
					Properties properties = propertiesMap.get( circleId );
					PiAccumulatedSeries accumulatedSeries = piAccumulateSeriesMap.get( circleId );

					int hour24 = accumulatedSeries.getAccumulated().getHour24().intValue();
					int thresHold1 = Integer.valueOf( properties.getThreshold1() );
					int thresHold2 = Integer.valueOf( properties.getThreshold2() );
					int thresHold3 = Integer.valueOf( properties.getThreshold3() );

					Attribute classAttribute = circle.attribute( ATTRIBUTE_CLASS_NAME );
					circle.remove( classAttribute );

					if ( circleId.endsWith( "I" ) && properties.getGrading().equals( "1" ) ) {
						if ( (!(thresHold1 == MISSING_VALUE)) && hour24 > thresHold1 ) {
							circle.addAttribute( ATTRIBUTE_CLASS_NAME, INNER_WATER_CLASS );
							warningPropertiesIW.add( properties );
						} else if ( (!(thresHold2 == MISSING_VALUE)) && hour24 > thresHold2 ) {
							circle.addAttribute( ATTRIBUTE_CLASS_NAME, INNER_WATER_CLASS );
							warningPropertiesIW.add( properties );
						} else if ( (!(thresHold3 == MISSING_VALUE)) && hour24 > thresHold3 ) {
							circle.addAttribute( ATTRIBUTE_CLASS_NAME, INNER_WATER_CLASS );
							warningPropertiesIW.add( properties );
						} else {
							circle.addAttribute( ATTRIBUTE_CLASS_NAME, DEFAULT_CLASS );
						}
					} else if ( circleId.endsWith( "E" ) ) {
						if ( (!(thresHold1 == MISSING_VALUE)) && hour24 > thresHold1 ) {
							circle.addAttribute( ATTRIBUTE_CLASS_NAME, EXTERNAL_WATER_CLASS );
							warningPropertiesEW.add( properties );
						} else if ( (!(thresHold2 == MISSING_VALUE)) && hour24 > thresHold2 ) {
							circle.addAttribute( ATTRIBUTE_CLASS_NAME, EXTERNAL_WATER_CLASS );
							warningPropertiesEW.add( properties );
						} else if ( (!(thresHold3 == MISSING_VALUE)) && hour24 > thresHold3 ) {
							circle.addAttribute( ATTRIBUTE_CLASS_NAME, EXTERNAL_WATER_CLASS );
							warningPropertiesEW.add( properties );
						} else {
							circle.addAttribute( ATTRIBUTE_CLASS_NAME, DEFAULT_CLASS );
						}
					} else {
						circle.addAttribute( ATTRIBUTE_CLASS_NAME, DEFAULT_CLASS );
					}
				} );

				String tempSvgPath = Strman.append( exportPtah, StringUtils.PATH, svgFileNameWithOutExt, ".svg" );
				String exportImagePath = Strman.append( exportPtah, StringUtils.PATH, svgFileNameWithOutExt, ".png" );

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

	public List<Properties> getWarningPropertiesEW() {
		return warningPropertiesEW;
	}
	
	public List<Properties> getWarningPropertiesIW() {
		return warningPropertiesIW;
	}
}
