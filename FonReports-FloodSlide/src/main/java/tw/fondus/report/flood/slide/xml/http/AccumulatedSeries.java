package tw.fondus.report.flood.slide.xml.http;

import java.util.List;

import org.simpleframework.xml.ElementList;

public class AccumulatedSeries {
	@ElementList( inline = true, entry = "accumulatedSerie" )
	private List<AccumulatedSerie> list;

	public List<AccumulatedSerie> getList() {
		return list;
	}

	public void setList( List<AccumulatedSerie> list ) {
		this.list = list;
	}

}
