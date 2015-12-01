import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class StationDataExtractorForIncomingBikes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String csvFile = "E://MS SE//Fall 2015//CMPE 239//Project//incominghourly.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();
			
			int previous_station_id = 0;
			PrintWriter writer = null;
		
			while ((line = br.readLine()) != null) {
				String[] hourRecord = line.split(cvsSplitBy);
				int current_station_id = Integer.parseInt(hourRecord[0]);
				
				if (current_station_id != previous_station_id) {
					String path = "E://MS SE//Fall 2015//CMPE 239//Project//StationDataWithIncomingBikes//" + current_station_id + ".csv";
					writer = new PrintWriter(path);
					writer.append("station_id,year,month,day,from,to,day_of_week,weekday,weekend,incoming_bikes");
					writer.append("\n");
				}

				writer.append(hourRecord[0] + "," + hourRecord[1] + "," + hourRecord[2] + "," + hourRecord[3] + ","
						+ hourRecord[4] + "," + hourRecord[5] + "," + hourRecord[6] + "," + hourRecord[7] + ","
						+ hourRecord[8] + "," + hourRecord[9]);
				writer.append("\n");

				previous_station_id = current_station_id;
				writer.flush();
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
