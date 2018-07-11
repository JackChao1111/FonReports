package tw.fondus.report.flood.slide.property;

import java.nio.file.Paths;

import org.junit.Test;

import tw.fondus.report.flood.property.ConfigProperties;

public class PropertiesTest {
	@Test
	public void run(){
		try {
			ConfigProperties configProperties = new ConfigProperties( Paths.get( "src/test/resources/config.properties" ) );
			System.out.println( configProperties.getExportFolder() );
		} catch (Exception e) {
		}
	}
	
}
