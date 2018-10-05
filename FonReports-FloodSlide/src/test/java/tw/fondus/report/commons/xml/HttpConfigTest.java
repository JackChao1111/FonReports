package tw.fondus.report.commons.xml;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Preconditions;

import tw.fondus.commons.fews.pi.config.xml.util.XMLUtils;
import tw.fondus.report.flood.slide.xml.http.HttpConfig;

/**
 * The unit test of serialization/deserialization with HttpConfig.
 * 
 * @author Chao
 *
 */
public class HttpConfigTest {
	private Path path;

	@Before
	public void setUp() {
		this.path = Paths.get( "src/test/resources/HttpConfig.xml" );

		Preconditions.checkState( Files.exists( this.path ), "File doesn't exist!" );
	}

	@Test
	public void run() {
		try {
			HttpConfig httpConfig = XMLUtils.fromXML( this.path.toFile(), HttpConfig.class );
			
			Assert.assertNotNull( httpConfig );
			System.out.println( httpConfig.getLogin().getUrl() );
			System.out.println( httpConfig.getAccumulatedSeries().getList().get( 0 ).getUrl() );
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
