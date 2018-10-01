package Training.GasMon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;


public class ExportCSV {

	public static void exportFile()
			throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File("data.csv"));
		StringBuilder sb = new StringBuilder();

			sb.append("Location ID" + "," + "Value","");
			sb.append("Date,From,To,Amount,Narrative\n");

			for (Transaction t : t_hmap.get(key)) {
				sb.append(t.getDate() + "," + t.getFromAccount() + "," + t.getToAccount() + "," + t.getAmount() + ","
						+ t.getNarrative() + "\n");

			}
			sb.append("\n");

		}

		pw.write(sb.toString());
		pw.close();
		System.out.println("done!");
	}

}