package tw.fondus.report.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Preconditions;

import tw.fondus.commons.json.util.JSONUtils;
import tw.fondus.report.flood.json.hotspot.HotSpot;

/**
 * The unit test of serialization/deserialization with JSON configuration.
 * 
 * @author Chao
 *
 */
public class JSONTest {
	private Path path;
	@Before
	public void setUp() {
		this.path = Paths.get( "src/test/resources/Taiwan_Hot_Spot.geojson" );

		Preconditions.checkState( Files.exists( this.path ), "File doesn't exist!" );
	}

	@Test
	public void run() throws IOException {
		String json = new String( Files.readAllBytes( this.path ) );

		HotSpot hotSpot = JSONUtils.fromJSON( json, HotSpot.class );
		System.out.println( hotSpot.getFeatures().get( 0 ).getProperties().getId() );
	}
}
