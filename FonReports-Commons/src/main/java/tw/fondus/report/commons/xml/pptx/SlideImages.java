package tw.fondus.report.commons.xml.pptx;

import java.util.List;

import org.simpleframework.xml.ElementList;

/**
 * 
 * @author Chao
 *
 */
public class SlideImages {
	@ElementList( inline = true, entry = "image" )
	private List<Image> list;

	public List<Image> getList() {
		return list;
	}

	public void setList( List<Image> slideImages ) {
		this.list = slideImages;
	}
	
}
