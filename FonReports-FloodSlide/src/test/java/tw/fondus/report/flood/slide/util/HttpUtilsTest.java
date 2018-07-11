package tw.fondus.report.flood.slide.util;

import java.io.File;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import tw.fondus.commons.fews.pi.config.xml.util.XMLUtils;
import tw.fondus.commons.fews.pi.json.user.UserResponse;
import tw.fondus.report.flood.slide.xml.http.HttpConfig;

public class HttpUtilsTest {
	private HttpConfig httpConfig;
	
	@Before
	public void readConfig(){
		try {
			httpConfig = XMLUtils.fromXML( new File( "src/test/resources/HttpConfig.xml" ), HttpConfig.class );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void run(){
		Optional<UserResponse> optResponse = HttpUtils.login( httpConfig.getLogin().getUrl(),
				httpConfig.getLogin().getAccount(), httpConfig.getLogin().getPassword() );
		optResponse.ifPresent( response -> {
			System.out.println( response );
		} );
	}
}
