package tw.fondus.report.flood.json.hotspot;

import com.google.gson.annotations.SerializedName;

/**
 * The POJO of features JSON object.
 * 
 * @author Chao
 *
 */
public class Features {
	@SerializedName( "type" )
	protected String type;
	
	@SerializedName( "properties" )
	protected Properties properties;
	
	@SerializedName( "geometry" )
	protected Geometry geometry;

	public String getType() {
		return type;
	}

	public void setType( String type ) {
		this.type = type;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties( Properties properties ) {
		this.properties = properties;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry( Geometry geometry ) {
		this.geometry = geometry;
	}

}
