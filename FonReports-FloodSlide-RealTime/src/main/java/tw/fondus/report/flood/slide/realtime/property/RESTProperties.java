package tw.fondus.report.flood.slide.realtime.property;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Properties;

/**
 * The POJO of REST-API properties configuration.
 * 
 * @author Chao
 *
 */
public class RESTProperties {
	private static Properties properties;
	private static String account;
	private static String password;
	private static String loginUrl;
	private static String villagesFloodUrl;

	public RESTProperties( Path propertiesPath ) throws IOException{
		properties = new Properties();
		properties.load( new InputStreamReader( new FileInputStream( propertiesPath.toFile() ), "UTF-8" ) );
		account = properties.getProperty( "rest.account" );
		password = properties.getProperty( "rest.password" );
		loginUrl = properties.getProperty( "rest.url.login" );
		villagesFloodUrl = properties.getProperty( "rest.url.flood.villages" );
	}

	public Properties getProperties() {
		return properties;
	}
	
	public String getAccount() {
		return account;
	}
	public String getPassword() {
		return password;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public String getVillagesFloodUrl() {
		return villagesFloodUrl;
	}	
}
