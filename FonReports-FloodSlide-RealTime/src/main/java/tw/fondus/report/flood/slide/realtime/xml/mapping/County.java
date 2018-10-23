package tw.fondus.report.flood.slide.realtime.xml.mapping;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * The POJO of county XML elements.
 * 
 * @author Chao
 *
 */
public class County {
	@Attribute
	private String region;
	
	@Attribute
	private String city;
	
	@Attribute
	private String name;
	
	@Element
	private ImageLocation imageLocation;

	public String getRegion() {
		return region;
	}

	public void setRegion( String region ) {
		this.region = region;
	}

	public String getCity() {
		return city;
	}

	public void setCity( String city ) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public ImageLocation getImageLocation() {
		return imageLocation;
	}

	public void setImageLocation( ImageLocation imageLocation ) {
		this.imageLocation = imageLocation;
	}
	
}
