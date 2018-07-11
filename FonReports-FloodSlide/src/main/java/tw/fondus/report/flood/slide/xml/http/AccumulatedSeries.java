package tw.fondus.report.flood.slide.xml.http;

import java.util.List;

import org.simpleframework.xml.ElementList;

/**
 * The POJO of accumulated series XML elements.
 * 
 * @author Chao
 *
 */
public class AccumulatedSeries {
	@ElementList( inline = true, entry = "accumulated" )
	private List<Accumulated> list;

	public List<Accumulated> getList() {
		return list;
	}

	public void setList( List<Accumulated> list ) {
		this.list = list;
	}

}
