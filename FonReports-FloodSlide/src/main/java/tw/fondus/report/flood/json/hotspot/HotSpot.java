package tw.fondus.report.flood.json.hotspot;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class HotSpot {
	@SerializedName( "type" )
	protected String type;
	
	@SerializedName( "name" )
	protected String name;
	
	@SerializedName( "features" )
	protected List<Features> features;

	public String getType() {
		return type;
	}

	public void setType( String type ) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public List<Features> getFeatures() {
		return features;
	}

	public void setFeatures( List<Features> features ) {
		this.features = features;
	}

}
