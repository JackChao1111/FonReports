package tw.fondus.report.commons.xml;

import java.io.File;

import org.junit.Test;

import tw.fondus.commons.fews.pi.config.xml.util.XMLUtils;
import tw.fondus.report.flood.slide.xml.http.HttpConfig;

public class HttpConfigTest {
	@Test
	public void run(){
		try {
			HttpConfig httpConfig = XMLUtils.fromXML( new File( "src/test/resources/HttpConfig.xml" ), HttpConfig.class );
			System.out.println( httpConfig.getLogin().getUrl() );
			System.out.println( httpConfig.getAccumulatedSeries().getList().get( 0 ).getUrl() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
