package tw.fondus.report.flood.slide.realtime.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import tw.fondus.report.commons.util.HttpUtils;
import tw.fondus.report.flood.slide.realtime.util.svg.SVGVillagesFloodImageCreator;
import tw.fondus.commons.fews.pi.json.user.UserResponse;
import tw.fondus.commons.fews.pi.json.warning.PiWarning;
import tw.fondus.commons.fews.pi.json.warning.PiWarningSets;

import org.junit.Before;
import org.junit.Test;

/**
 * The unit test of creating image by SVG.
 * 
 * @author Chao
 *
 */
public class SVGVillagesFloodImageCreatorTest {
	Map<String, PiWarning> piWarningMap;
	@Before
	public void setUp() {
		piWarningMap = new HashMap<>();
		Optional<UserResponse> optResponse = HttpUtils.login( "Account/Login", "account", "password" );
		optResponse.ifPresent( response -> {
			Optional<PiWarningSets> optPiWarningSets = HttpUtils
					.getWarnings( "Floods/Real-Time/Southern/Kaohsiung/Villages/Warnings", response );
			optPiWarningSets.ifPresent( piWarningSets -> {
				Stream.of( piWarningSets.getCollection() ).forEach( piWarning ->{
					piWarningMap.put( piWarning.getId(), piWarning );
				} );
			} );
		} );
	}
	
	@Test
	public void run() throws Exception{
		SVGVillagesFloodImageCreator.createImageBySVG( piWarningMap, "src/test/resources/Template/Southern/Kaohsiung" );
	}
}
