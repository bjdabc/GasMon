package Training.GasMon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJson {

	public static void readFile(HashMap<String, Location> hmap, String filename) {

		FileReader file = null;

		try {
			file = new FileReader(filename);
		} catch (FileNotFoundException e1) {

		}

		JSONParser parser = new JSONParser();
		JSONArray a = null;
		try {
			try {
				a = (JSONArray) parser.parse(file);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {

		}

		for (Object o : a) {

			JSONObject locations = (JSONObject) o;

			String x_coord = (String) locations.get("x").toString();
			String y_coord = (String) locations.get("y").toString();
			String uuid = (String) locations.get("id").toString();

			hmap.put(uuid, new Location(x_coord, y_coord, uuid));

		}
	}
}
