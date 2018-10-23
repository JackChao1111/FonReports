package tw.fondus.report.flood.slide.realtime.property;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Preconditions;

/**
 * The unit test of rest properties.
 * 
 * @author Chao
 *
 */
public class RESTPropertiesTest {
	Path path;
	
	@Before
	public void setUp(){
		this.path = Paths.get( "src/test/resources/rest.properties" );
		Preconditions.checkState( Files.exists( this.path ), "File doesn't exist!" );
	}
	
	@Test
	public void run() throws IOException{
		RESTProperties restProperties = new RESTProperties( this.path );
		System.out.println( restProperties.getVillagesFloodUrl() );
		Assert.assertNotNull( restProperties );
	}
}
