package tw.fondus.report.flood.slide.xml.http;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root( name = "HttpConfig")
public class HttpConfig {
	@Element
	private Login login;
	
	@Element
	private AccumulatedSeries accumulatedSeries;

	public Login getLogin() {
		return login;
	}

	public void setLogin( Login login ) {
		this.login = login;
	}

	public AccumulatedSeries getAccumulatedSeries() {
		return accumulatedSeries;
	}

	public void setAccumulatedSeries( AccumulatedSeries accumulatedSeries ) {
		this.accumulatedSeries = accumulatedSeries;
	}
	
}
