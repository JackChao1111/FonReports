package tw.fondus.report.flood.slide.util.data;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import tw.fondus.commons.fews.pi.json.accumulate.PiAccumulatedSeries;
import tw.fondus.commons.fews.pi.json.accumulate.PiAccumulatedSeriesCollection;
import tw.fondus.commons.fews.pi.json.timeseries.PiTimeSeriesArray;
import tw.fondus.commons.fews.pi.json.timeseries.PiTimeSeriesCollection;
import tw.fondus.report.commons.xml.pptx.Image;
import tw.fondus.report.commons.xml.pptx.SlideMapping;
import tw.fondus.report.flood.json.hotspot.HotSpot;
import tw.fondus.report.flood.json.hotspot.Properties;

/**
 * The util of data transform.
 * 
 * @author Chao
 *
 */
public class DataTransformUtils {
	
	public static Map<String, PiTimeSeriesArray> piTimeSeriesCollectionToMap(
			PiTimeSeriesCollection piTimeSeriesCollection ) {
		Map<String, PiTimeSeriesArray> piTimeSeriesMap = new HashMap<>();
		Stream.of( piTimeSeriesCollection.getTimeSeries() ).forEach( timeSeries -> {
			piTimeSeriesMap.put( timeSeries.getHeader().getLocationId(), timeSeries );
		} );

		return piTimeSeriesMap;
	}

	public static Map<String, PiAccumulatedSeries> piAccumulatedSeriesCollectionToMap(
			PiAccumulatedSeriesCollection piAccumulatedSeriesCollection ) {
		Map<String, PiAccumulatedSeries> piAccumulateSeriesMap = new HashMap<>();
		Stream.of( piAccumulatedSeriesCollection.getAccumulatedSeries() ).forEach( accumulatedSeries -> {
			piAccumulateSeriesMap.put( accumulatedSeries.getHeader().getLocationId(), accumulatedSeries );
		} );

		return piAccumulateSeriesMap;
	}

	public static Map<String, Properties> hotSpotJSONToPropertiesMap( HotSpot hotSpot ) {
		Map<String, Properties> propertiesMap = new HashMap<>();
		hotSpot.getFeatures().forEach( feature -> {
			propertiesMap.put( feature.getProperties().getId(), feature.getProperties() );
		} );

		return propertiesMap;
	}

	public static Map<String, Image> slideMappingImageToMap( SlideMapping slideMapping ) {
		Map<String, Image> slideMappingImageMap = new HashMap<>();
		slideMapping.getSlideImages().getList().forEach( slideImage -> {
			slideMappingImageMap.put( slideImage.getId(), slideImage );
		} );

		return slideMappingImageMap;
	}
}
