package tw.fondus.report.commons.xml.pptx;

import java.util.List;

import org.simpleframework.xml.ElementList;

/**
 * The POJO of counties XML elements.
 * 
 * @author Chao
 *
 */
public class Counties {
	@ElementList( inline = true, entry = "county" )
	private List<County> list;

	public List<County> getList() {
		return list;
	}

	public void setList( List<County> counties ) {
		this.list = counties;
	}
	
}
