package Training.GasMon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;

public class ExportCSV extends TimerTask {

	HashMap<String, Sensor> hmap;

	public ExportCSV(HashMap<String, Sensor> hmap) {
		this.hmap = hmap;
	}

	@Override
	public void run() {

		exportFile();

	}

	private void exportFile() {
		StringBuilder sb = new StringBuilder();
		if (new File("data.csv").isFile()) {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader("data.csv"));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String line;
			try {
				while ((line = br.readLine()) != null) {
					sb.append(line + ",\n");
				}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			sb.append("Location ID, Value, Time,\n");
		}

		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File("data.csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String s : hmap.keySet()) {

			String location = hmap.get(s).getLocation();
			Double value = hmap.get(s).getValue();
			String time = hmap.get(s).getTime();

			sb.append(location + "," + value + "," + time +","+ "\n");

		}

		pw.write(sb.toString());
		pw.close();

	}
}
