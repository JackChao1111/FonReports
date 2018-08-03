package tw.fondus.report.flood.slide.property;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Preconditions;

import tw.fondus.report.flood.property.ConfigProperties;

/**
 * The unit test of serialization/deserialization with properties config.
 * 
 * @author Chao
 *
 */
public class PropertiesTest {
	private Path path;

	@Before
	public void setUp() {
		this.path = Paths.get( "src/test/resources/config.properties" );

		Preconditions.checkState( Files.exists( this.path ), "File doesn't exist!" );
	}

	@Test
	public void run() {
		try {
			ConfigProperties configProperties = new ConfigProperties( this.path );
			
			Assert.assertNotNull( configProperties );
			System.out.println( configProperties.getExportFolder() );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
