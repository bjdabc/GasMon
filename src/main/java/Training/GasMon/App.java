package Training.GasMon;

import java.util.HashMap;

public class App {
	
	String arn = "arn:aws:sns:eu-west-1:552908040772:EventProcessing-RFM-Sept-2018-snsTopicSensorDataPart1-PUR0KBORONQF";

	public static void main(String[] args) {
		HashMap<String, Location> hmap = new HashMap<>();

		String filename = "locations.json";

		ReadJson.readFile(hmap, filename);
		
		SQSInfo.create();

	}
	

}
