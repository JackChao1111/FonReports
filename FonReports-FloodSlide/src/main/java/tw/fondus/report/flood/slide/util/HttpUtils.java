package tw.fondus.report.flood.slide.util;

import java.util.Optional;

import strman.Strman;
import tw.fondus.commons.fews.pi.json.accumulate.PiAccumulatedSeriesCollection;
import tw.fondus.commons.fews.pi.json.user.UserResponse;
import tw.fondus.commons.web.pi.service.IHttpPiService;
import tw.fondus.commons.web.pi.service.impl.CommonsHttpService;

/**
 * The util of HTTP connection.
 * 
 * @author Chao
 *
 */
public class HttpUtils {
	private static final String COMMON_URL = "http://localhost/";
	private static IHttpPiService httpService;

	static {
		httpService = new CommonsHttpService();
	}

	/**
	 * Get user response.
	 * 
	 * @param url
	 * @param account
	 * @param password
	 * @return
	 */
	public static Optional<UserResponse> login( String url, String account, String password ) {
		System.out.println( Strman.append( COMMON_URL, url ) + "," +  account + "," + password);
		Optional<UserResponse> optResponse = httpService.loginREST( Strman.append( COMMON_URL, url ), account,
				password );
		
		return optResponse;
	}

	/**
	 * Get pi accumulated series.
	 * 
	 * @param url
	 * @param start
	 * @param end
	 * @param backward
	 * @param response
	 * @return
	 */
	public static Optional<PiAccumulatedSeriesCollection> getAccumulated( String url, int start, int end,
			String backward, UserResponse response ) {
		Optional<PiAccumulatedSeriesCollection> optPiAccumulatedSeries = httpService.getAccumulatedSeries(
				Strman.append( COMMON_URL, url, combineAccumulatedAttribute( start, end, backward ) ), response );

		return optPiAccumulatedSeries;
	}

	/**
	 * Combine pi accumulated series attribute.
	 * 
	 * @param start
	 * @param end
	 * @param backward
	 * @return
	 */
	private static String combineAccumulatedAttribute( int start, int end, String backward ) {
		String attrubute = "";
		try {
			if ( end < start ) {
				throw new Exception();
			}
			attrubute = Strman.append( "start=", String.valueOf( start ), "&end=", String.valueOf( end ), "&backward=",
					backward );
		} catch (Exception e) {
			System.out.println( "The end time must bigger than start time." );
		}
		return attrubute;
	}
}
