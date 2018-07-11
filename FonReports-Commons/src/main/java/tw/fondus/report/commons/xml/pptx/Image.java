package tw.fondus.report.commons.xml.pptx;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * 
 * @author Chao
 *
 */
public class Image {
	@Attribute
	private String id;
	
	@Attribute
	private String value;
	
	@Element
	private Location location;

	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue( String value ) {
		this.value = value;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation( Location location ) {
		this.location = location;
	}
	
}
