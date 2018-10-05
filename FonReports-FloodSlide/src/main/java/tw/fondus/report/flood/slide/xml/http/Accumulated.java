package tw.fondus.report.flood.slide.xml.http;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * The POJO of accumulated XML elements.
 * 
 * @author Chao
 *
 */
public class Accumulated {
	@Attribute
	private String id;
	
	@Element
	private String url;
	
	@Element
	private int start;
	
	@Element
	private int end;
	
	@Element
	private String backward;

	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl( String url ) {
		this.url = url;
	}

	public int getStart() {
		return start;
	}

	public void setStart( int start ) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd( int end ) {
		this.end = end;
	}

	public String getBackward() {
		return backward;
	}

	public void setBackward( String backward ) {
		this.backward = backward;
	}
	
}
