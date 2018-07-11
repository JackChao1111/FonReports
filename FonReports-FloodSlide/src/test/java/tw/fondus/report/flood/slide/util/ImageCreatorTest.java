package tw.fondus.report.flood.slide.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Preconditions;

import tw.fondus.commons.fews.pi.config.xml.util.XMLUtils;
import tw.fondus.commons.fews.pi.json.accumulate.PiAccumulatedSeries;
import tw.fondus.commons.fews.pi.json.accumulate.PiAccumulatedSeriesCollection;
import tw.fondus.commons.fews.pi.json.user.UserResponse;
import tw.fondus.report.flood.slide.util.data.DataTransformUtils;
import tw.fondus.report.flood.slide.util.svg.SVGRainfallImageCreator;
import tw.fondus.report.flood.slide.xml.http.HttpConfig;

/**
 * The unit test of create image from SVG file.
 * 
 * @author Chao
 *
 */
public class ImageCreatorTest {
	private Optional<PiAccumulatedSeriesCollection> optPiAccumulatedSeriesCollection;
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
						this.optPiAccumulatedSeriesCollection = HttpUtils.getAccumulated( series.getUrl(),
								series.getStart(), series.getEnd(), series.getBackward(), response );
					}
				} );

			} );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void run() {
		this.optPiAccumulatedSeriesCollection.ifPresent( piAccumulatedSeriesCollection -> {
			Map<String, PiAccumulatedSeries> piAccumulateSeriesMap = DataTransformUtils
					.piAccumulatedSeriesCollectionToMap( piAccumulatedSeriesCollection );

			SVGRainfallImageCreator rainfallImage = new SVGRainfallImageCreator();
			rainfallImage.createImageBySvg( piAccumulateSeriesMap, "src/test/resources/template/", "Taiwan",
					"src/test/resources/export/" );
		} );
	}
}
