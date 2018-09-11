package tw.fondus.report.flood.json.hotspot;

import com.google.gson.annotations.SerializedName;

/**
 * The POJO of properties JSON object.
 * 
 * @author Chao
 *
 */
public class Properties {
	@SerializedName( "ID" )
	protected String id;
	
	@SerializedName( "RMO" )
	protected String rmo;
	
	@SerializedName( "River" )
	protected String river;
	
	@SerializedName( "Basin" )
	protected String basin;
	
	@SerializedName( "County" )
	protected String county;
	
	@SerializedName( "Town" )
	protected String town;
	
	@SerializedName( "Address" )
	protected String address;
	
	@SerializedName( "Threshold1" )
	protected String threshold1;
	
	@SerializedName( "Threshold2" )
	protected String threshold2;
	
	@SerializedName( "Threshold3" )
	protected String threshold3;
	
	@SerializedName( "Flood" )
	protected String flood;
	
	@SerializedName( "Grading" )
	protected String grading;
	
	@SerializedName( "Vulnerabil" )
	protected String vulnerabil;
	
	@SerializedName( "DisasterPr" )
	protected String disasterPr;
	
	@SerializedName( "Remark" )
	protected String remark;
	
	@SerializedName( "Protection" )
	protected String protection;
	
	@SerializedName( "FloodProte" )
	protected String floodProte;
	
	@SerializedName( "PotentialC" )
	protected String potentialC;

	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

	public String getRmo() {
		return rmo;
	}

	public void setRmo( String rmo ) {
		this.rmo = rmo;
	}

	public String getRiver() {
		return river;
	}

	public void setRiver( String river ) {
		this.river = river;
	}

	public String getBasin() {
		return basin;
	}

	public void setBasin( String basin ) {
		this.basin = basin;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty( String county ) {
		this.county = county;
	}

	public String getTown() {
		return town;
	}

	public void setTown( String town ) {
		this.town = town;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress( String address ) {
		this.address = address;
	}

	public String getThreshold1() {
		return threshold1;
	}

	public void setThreshold1( String threshold1 ) {
		this.threshold1 = threshold1;
	}

	public String getThreshold2() {
		return threshold2;
	}

	public void setThreshold2( String threshold2 ) {
		this.threshold2 = threshold2;
	}

	public String getThreshold3() {
		return threshold3;
	}

	public void setThreshold3( String threshold3 ) {
		this.threshold3 = threshold3;
	}

	public String getFlood() {
		return flood;
	}

	public void setFlood( String flood ) {
		this.flood = flood;
	}

	public String getGrading() {
		return grading;
	}

	public void setGrading( String grading ) {
		this.grading = grading;
	}

	public String getVulnerabil() {
		return vulnerabil;
	}

	public void setVulnerabil( String vulnerabil ) {
		this.vulnerabil = vulnerabil;
	}

	public String getDisasterPr() {
		return disasterPr;
	}

	public void setDisasterPr( String disasterPr ) {
		this.disasterPr = disasterPr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark( String remark ) {
		this.remark = remark;
	}

	public String getProtection() {
		return protection;
	}

	public void setProtection( String protection ) {
		this.protection = protection;
	}

	public String getFloodProte() {
		return floodProte;
	}

	public void setFloodProte( String floodProte ) {
		this.floodProte = floodProte;
	}

	public String getPotentialC() {
		return potentialC;
	}

	public void setPotentialC( String potentialC ) {
		this.potentialC = potentialC;
	}

}
