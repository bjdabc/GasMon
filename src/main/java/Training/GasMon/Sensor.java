package Training.GasMon;

public class Sensor {

	String location;
	Double value;
	String time;

	public Sensor(String location, String value, String time) {
		this.location = location;
		this.value = Double.valueOf(value);
		this.time = time;
	}

	public String getLocation() {
		return location;
	}

	public Double getValue() {
		return value;
	}

	public Double setValue(Double addition) {

		value = (value + addition) / 2;
		return value;
	}

	public String getTime() {
		return time;
	}
}
