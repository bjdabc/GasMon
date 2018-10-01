package Training.GasMon;

public class Sensor {
	
	String location;
	Double value;
	String time;
	
	public Sensor(String location, Double value, String time) {
		this.location = location;
		this.value = value;
		this.time = time;
	}
	
	public String getLocation() {
		return location;
	}
	
	public Double getValue() {
		return value;
	}
	
	public String getTime() {
		return time;
	}
}
