import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class ZipcodeInjector {

	public static void main(String[] args) {

		String csvFile = "E://MS SE//Fall 2015//CMPE 239//Project//babs_open_data_year_2//201508_station_data.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String landmark = "";
		String zipCode = "";

		HashMap<String, String> zip_codes = new HashMap<String, String>();
		zip_codes.put("San Francisco", "94107");
		zip_codes.put("Redwood City", "94063");
		zip_codes.put("Palo Alto", "94301");
		zip_codes.put("Mountain View", "94041");
		zip_codes.put("San Jose", "95113");

		try {
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();

			PrintWriter writer = new PrintWriter(
					"E://MS SE//Fall 2015//CMPE 239//Project//201508_processed_station_data.csv");
			writer.append("station_id,name,lat,long,dockcount,landmark,installation,zipcode");
			writer.append("\n");

			while ((line = br.readLine()) != null) {
				String[] stationRecord = line.split(cvsSplitBy);
				landmark = stationRecord[5];
				if (zip_codes.containsKey(landmark)) {
					zipCode = zip_codes.get(landmark);
					writer.append(stationRecord[0] + "," + stationRecord[1] + "," + stationRecord[2] + ","
							+ stationRecord[3] + "," + stationRecord[4] + "," + stationRecord[5] + ","
							+ stationRecord[6] + "," + zipCode);
					writer.append("\n");
				}
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
