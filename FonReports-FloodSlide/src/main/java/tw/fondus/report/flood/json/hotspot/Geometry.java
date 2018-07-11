package tw.fondus.report.flood.json.hotspot;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * The POJO of geometry JSON object.
 * 
 * @author Chao
 *
 */
public class Geometry {
	@SerializedName( "type" )
	protected String type;
	
	@SerializedName( "coordinates" )
	protected List<String> coordinates;

	public String getType() {
		return type;
	}

	public void setType( String type ) {
		this.type = type;
	}

	public List<String> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates( List<String> coordinates ) {
		this.coordinates = coordinates;
	}

}
