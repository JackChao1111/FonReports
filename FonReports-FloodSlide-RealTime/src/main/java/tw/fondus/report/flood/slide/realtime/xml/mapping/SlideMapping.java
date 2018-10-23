package tw.fondus.report.flood.slide.realtime.xml.mapping;

import java.util.List;

import org.simpleframework.xml.ElementList;

/**
 * The POJO of slide mapping XML elements.
 * 
 * @author Chao
 *
 */
public class SlideMapping {
	@ElementList( inline = true, entry = "county" )
	private List<County> counties;

	public List<County> getCounties() {
		return counties;
	}

	public void setCounties( List<County> counties ) {
		this.counties = counties;
	}
}
