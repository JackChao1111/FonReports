package tw.fondus.report.commons.util;

import java.security.GeneralSecurityException;

import okhttp3.OkHttpClient;
import tw.fondus.commons.util.http.HttpUtils;

public class HttpClientUtils {
	public static OkHttpClient getSSLClient( OkHttpClient client ) throws GeneralSecurityException {
		return HttpUtils.buildSSLClient( client,
				HttpUtils.getTrustAllSSLSocket( HttpUtils.TLS_V1 ),
				HttpUtils.getTrustAllManager(),
				HttpUtils.getTrustAllHostVerifier() );
	}
}
