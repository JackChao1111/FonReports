package tw.fondus.report.flood.slide.xml.http;

import org.simpleframework.xml.Element;

/**
 * The POJO of login XML elements.
 * 
 * @author Chao
 *
 */
public class Login {
	@Element
	private String url;
	
	@Element
	private String account;
	
	@Element
	private String password;

	public String getUrl() {
		return url;
	}

	public void setUrl( String url ) {
		this.url = url;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount( String account ) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword( String password ) {
		this.password = password;
	}
	
}
