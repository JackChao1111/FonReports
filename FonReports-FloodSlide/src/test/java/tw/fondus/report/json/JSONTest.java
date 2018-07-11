package tw.fondus.report.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import tw.fondus.commons.json.util.JSONUtils;
import tw.fondus.report.flood.json.hotspot.HotSpot;


public class JSONTest {
	@Test
	public void run() throws IOException{
		String taiwanHotSpotJSONPath = "src/test/resources/Taiwan_Hot_Spot.geojson";
		String json = new String( Files.readAllBytes( Paths.get( taiwanHotSpotJSONPath ) ) );
		
		HotSpot hotSpot = JSONUtils.fromJSON( json, HotSpot.class );
		System.out.println( hotSpot.getFeatures().get( 0 ).getProperties().getId());		
		
	}
}
