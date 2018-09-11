package tw.fondus.report.commons.xml;

import java.util.List;

import org.simpleframework.xml.ElementList;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * The POJO of texts mapping XML elements.
 * 
 * @author Brad Chen
 *
 */
public class Texts {
	@ElementList(inline = true, entry = "mapping")
	private List<Mapping> mappings;
	
	public List<Mapping> getMappings() {
		return mappings;
	}

	public void setMappings( List<Mapping> mappings ) {
		this.mappings = mappings;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this ).add( "mappings", mappings ).toString();
	}

	@Override
	public boolean equals( Object that ) {
		if ( that instanceof Texts ) {
			Texts o = (Texts) that;
			return Objects.equal( mappings, o.mappings );
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode( mappings );
	}
}
