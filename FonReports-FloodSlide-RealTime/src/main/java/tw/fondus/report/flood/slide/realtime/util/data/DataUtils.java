package tw.fondus.report.flood.slide.realtime.util.data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import tw.fondus.commons.fews.pi.json.warning.PiWarning;
import tw.fondus.commons.fews.pi.json.warning.PiWarningSets;
import tw.fondus.commons.fews.pi.util.risk.RiskUtils;

/**
 * Some data process for project of FloodSlide-RealTime.
 * 
 * @author Chao
 *
 */
public class DataUtils {
	public static Map<String, PiWarning> piWarningSetsToMap( PiWarningSets piWarningSets ) {
		Map<String, PiWarning> piWarningMap = new HashMap<>();
		Stream.of( piWarningSets.getCollection() ).forEach( piWarning -> {
			piWarningMap.put( piWarning.getId(), piWarning );
		} );

		return piWarningMap;
	}

	public static BigDecimal calculateCityFloodArea( PiWarningSets piWarningSets ) {
		BigDecimal sum = IntStream.range( 0, piWarningSets.getCollection().length )
				.mapToObj( i -> piWarningSets.getCollection()[i].getCount() )
				.reduce( BigDecimal.ZERO, BigDecimal::add );

		return sum.multiply( RiskUtils.FACTOR_AREA );
	}
}
