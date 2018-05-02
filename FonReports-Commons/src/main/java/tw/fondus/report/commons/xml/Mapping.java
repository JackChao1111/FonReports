package tw.fondus.report.commons.xml;

import org.simpleframework.xml.Attribute;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * The POJO of mapping XML elements.
 * 
 * @author Brad Chen
 *
 */
public class Mapping {
	@Attribute
	private String id;
	
	@Attribute
	private String value;

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
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this ).add( "id", id )
				.add( "value", value ).toString();
	}

	@Override
	public boolean equals( Object that ) {
		if ( that instanceof Mapping ) {
			Mapping o = (Mapping) that;
			return Objects.equal( id, o.id ) && Objects.equal( value, o.value );
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode( id, value );
	}
}
