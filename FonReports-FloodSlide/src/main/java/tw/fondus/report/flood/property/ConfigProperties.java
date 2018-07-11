package tw.fondus.report.flood.property;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Properties;

/**
 * The POJO of properties configuration.
 * 
 * @author Chao
 *
 */
public class ConfigProperties {
	private static Properties properties;
	private static String templateFolder;
	private static String exportFolder;
	private static String httpConfig;
	private static String slideConfig;
	private static String hotSpotJSON;
	private static String pptxTemplate;
	
	public ConfigProperties(Path configPath) throws IOException{
		properties = new Properties();
		properties.load( new InputStreamReader( new FileInputStream( configPath.toFile() ), "UTF-8" ) );
		templateFolder = properties.getProperty( "TEMPLATE_FOLDER" );
		exportFolder = properties.getProperty( "EXPORT_FOLDER" );
		httpConfig = properties.getProperty( "HTTP_CONFIG" );
		slideConfig = properties.getProperty( "SLIDE_CONFIG" );
		hotSpotJSON = properties.getProperty( "HOT_SPOT_JSON" );
		pptxTemplate = properties.getProperty( "PPTX_TEMPLATE" );
	}
	
	public Properties getProperties(){
		return properties;
	}
	
	public String getTemplateFolder(){
		return templateFolder;
	}
	
	public String getExportFolder(){
		return exportFolder;
	}
	
	public String getHttpConfig(){
		return httpConfig;
	}
	
	public String getSlideConfig(){
		return slideConfig;
	}
	
	public String getHotSpotJSON(){
		return hotSpotJSON;
	}
	
	public String getPptxTemplate(){
		return pptxTemplate;
	}
}
