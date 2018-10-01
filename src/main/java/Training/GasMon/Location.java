package Training.GasMon;

public class Location {
	
	String x_coord;
	String y_coord;
	String uuid;
	
	public Location(String x_coord, String y_coord, String uuid) {
		
		this.x_coord = x_coord;
		this.y_coord = x_coord;
		this.uuid = uuid;
	}
	
	public String getXCoord() {
		return x_coord;
	}
	
	public String getYCoord() {
		return y_coord;
	}
	
	public String getUUID() {
		return uuid;
	}

}
