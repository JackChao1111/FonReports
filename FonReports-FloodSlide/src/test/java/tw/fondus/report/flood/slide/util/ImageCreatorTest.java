package tw.fondus.report.flood.slide.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import tw.fondus.commons.fews.pi.config.xml.util.XMLUtils;
import tw.fondus.commons.fews.pi.json.accumulate.PiAccumulatedSeries;
import tw.fondus.commons.fews.pi.json.accumulate.PiAccumulatedSeriesCollection;
import tw.fondus.commons.fews.pi.json.user.UserResponse;
import tw.fondus.report.flood.slide.util.svg.SVGRainfallImageCreator;
import tw.fondus.report.flood.slide.xml.http.HttpConfig;

public class ImageCreatorTest {
	private Optional<PiAccumulatedSeriesCollection> optPiAccumulatedSeriesCollection;

	@Before
	public void getData() {
		try {
			HttpConfig httpConfig = XMLUtils.fromXML( new File( "src/test/resources/HttpConfig.xml" ),
					HttpConfig.class );
			Optional<UserResponse> optResponse = HttpUtils.login( httpConfig.getLogin().getUrl(),
					httpConfig.getLogin().getAccount(), httpConfig.getLogin().getPassword() );
			optResponse.ifPresent( response -> {
				httpConfig.getAccumulatedSeries().getList().forEach( series -> {
					if ( series.getId().equals( "Countys" ) ) {
						optPiAccumulatedSeriesCollection = HttpUtils.getAccumulated( series.getUrl(), series.getStart(),
								series.getEnd(), series.getBackward(), response );
					}
				} );

			} );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void run(){
		optPiAccumulatedSeriesCollection.ifPresent( piAccumulatedSeriesCollection -> {
			Map<String, PiAccumulatedSeries> piAccumulateSeriesMap = new HashMap<String, PiAccumulatedSeries>();
			Stream.of( piAccumulatedSeriesCollection.getAccumulatedSeries() ).forEach( accumulatedSeries -> {
				piAccumulateSeriesMap.put( accumulatedSeries.getHeader().getLocationId(), accumulatedSeries );
			} );
			
			SVGRainfallImageCreator rainfallImage = new SVGRainfallImageCreator();
			rainfallImage.createImageBySvg( piAccumulateSeriesMap, "src/test/resources/template/", "Taiwan", "src/test/resources/export/" );
		} );
	}
}
