package tw.fondus.report.commons.xml.pptx;

import java.math.BigDecimal;

import org.simpleframework.xml.Element;

/**
 * 
 * @author Chao
 *
 */
public class Location {
	@Element
	private BigDecimal offX;
	
	@Element
	private BigDecimal offY;
	
	@Element
	private BigDecimal extcX;
	
	@Element
	private BigDecimal extcY;
	
	public BigDecimal getOffX() {
		return offX;
	}

	public void setOffX( BigDecimal offX ) {
		this.offX = offX;
	}

	public BigDecimal getOffY() {
		return offY;
	}

	public void setOffY( BigDecimal offY ) {
		this.offY = offY;
	}

	public BigDecimal getExtcX() {
		return extcX;
	}

	public void setExtcX( BigDecimal extcX ) {
		this.extcX = extcX;
	}

	public BigDecimal getExtcY() {
		return extcY;
	}

	public void setExtcY( BigDecimal extcY ) {
		this.extcY = extcY;
	}
}
