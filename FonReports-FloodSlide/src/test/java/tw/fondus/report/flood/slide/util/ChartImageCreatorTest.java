package tw.fondus.report.flood.slide.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Preconditions;

import tw.fondus.commons.fews.pi.config.xml.util.XMLUtils;
import tw.fondus.commons.fews.pi.json.timeseries.PiTimeSeriesArray;
import tw.fondus.commons.fews.pi.json.timeseries.PiTimeSeriesCollection;
import tw.fondus.commons.fews.pi.json.user.UserResponse;
import tw.fondus.report.flood.slide.util.chart.ChartImageCreator;
import tw.fondus.report.flood.slide.util.data.DataTransformUtils;
import tw.fondus.report.flood.slide.xml.http.HttpConfig;

public class ChartImageCreatorTest {
	private Optional<PiTimeSeriesCollection> optCountyTimeSeriesCollection;
	private Path path;
	
	@Before
	public void setUp() {
		try {
			this.path = Paths.get( "src/test/resources/HttpConfig.xml" );

			Preconditions.checkState( Files.exists( this.path ), "File doesn't exist!" );

			HttpConfig httpConfig = XMLUtils.fromXML( this.path.toFile(), HttpConfig.class );
			Optional<UserResponse> optResponse = HttpUtils.login( httpConfig.getLogin().getUrl(),
					httpConfig.getLogin().getAccount(), httpConfig.getLogin().getPassword() );
			optResponse.ifPresent( response -> {
				httpConfig.getAccumulatedSeries().getList().forEach( series -> {
					if ( series.getId().equals( "County" ) ) {
						this.optCountyTimeSeriesCollection = HttpUtils.getTimeSeriesArray( series.getUrl(), response );
					}
				} );

			} );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void run(){
		this.optCountyTimeSeriesCollection.ifPresent( piTimeSeriesCollection -> {
			Map<String, PiTimeSeriesArray> piTimeSeriesArrayMap = DataTransformUtils
					.piTimeSeriesCollectionToMap( piTimeSeriesCollection );
			ChartImageCreator chartImage = new ChartImageCreator();
			chartImage.createBarChartImage( piTimeSeriesArrayMap.get( "CT063" ), "Taipei", "src/test/resources/export" );
			
			Assert.assertTrue( Files.exists( Paths.get( "src/test/resources/export/chart/Taipei.png" ) ) );
		} );
	}
}
