package tw.fondus.report.commons.xml.docx;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * POJO Report Mapping of XML configuration.
 * 
 * @author Brad Chen
 *
 */
@Root( name = "ReportMapping" )
public class ReportMapping {
	@Element
	private Images images;

	@Element
	private Texts texts;

	public Images getImages() {
		return images;
	}

	public void setImages( Images images ) {
		this.images = images;
	}

	public Texts getTexts() {
		return texts;
	}

	public void setTexts( Texts texts ) {
		this.texts = texts;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this ).add( "images", images )
				.add( "texts", texts ).toString();
	}

	@Override
	public boolean equals( Object that ) {
		if ( that instanceof ReportMapping ) {
			ReportMapping o = (ReportMapping) that;
			return Objects.equal( images, o.images ) && Objects.equal( texts, o.texts );
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode( images, texts );
	}
}
