package tw.fondus.report.flood.slide.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Preconditions;

import tw.fondus.commons.fews.pi.config.xml.util.XMLUtils;
import tw.fondus.commons.fews.pi.json.user.UserResponse;
import tw.fondus.report.flood.slide.xml.http.HttpConfig;

/**
 * The unit test of serialization/deserialization with HttpConfig.
 * 
 * @author Chao
 *
 */
public class HttpUtilsTest {
	private Path path;
	@Before
	public void setUp(){
		this.path = Paths.get( "src/test/resources/HttpConfig.xml" );

		Preconditions.checkState( Files.exists( this.path ), "File doesn't exist!" );
	}
	
	@Test
	public void run(){
		try {
			HttpConfig httpConfig = XMLUtils.fromXML( this.path.toFile(), HttpConfig.class );
			Optional<UserResponse> optResponse = HttpUtils.login( httpConfig.getLogin().getUrl(),
					httpConfig.getLogin().getAccount(), httpConfig.getLogin().getPassword() );
			optResponse.ifPresent( response -> {
				System.out.println( response );
			} );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
