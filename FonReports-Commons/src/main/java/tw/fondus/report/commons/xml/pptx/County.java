package tw.fondus.report.commons.xml.pptx;

import org.simpleframework.xml.Attribute;

/**
 * The POJO of county XML elements.
 * 
 * @author Chao
 *
 */
public class County {
	@Attribute
	private String id;
	
	@Attribute
	private String eName;
	
	@Attribute
	private String cName;

	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

	public String geteName() {
		return eName;
	}

	public void seteName( String eName ) {
		this.eName = eName;
	}

	public String getcName() {
		return cName;
	}

	public void setcName( String cName ) {
		this.cName = cName;
	}

}
