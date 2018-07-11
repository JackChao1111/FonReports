package tw.fondus.report.commons.xml.pptx;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * POJO Slide Mapping of XML configuration.
 * 
 * @author Chao
 *
 */
@Root( name = "SlideMapping" )
public class SlideMapping {
	@Element
	private Counties counties;
	
	@Element
	private SlideImages slideImages;

	public Counties getCounties() {
		return counties;
	}

	public void setCounties( Counties counties ) {
		this.counties = counties;
	}

	public SlideImages getSlideImages() {
		return slideImages;
	}

	public void setSlideImages( SlideImages slideImages ) {
		this.slideImages = slideImages;
	}
	
}
