import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class WeatherInjectorForIncomingBikes {
	public static void main(String[] args) {

		File folder = new File("E://MS SE//Fall 2015//CMPE 239//Project//StationDataWithIncomingBikes//DailyData");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String stationID = getStationID(listOfFiles[i].getName());
				String zipCode = getZipCode(stationID);
				injectWeatherData(stationID, zipCode);
			}
		}

	}

	private static void injectWeatherData(String stationID, String zipCode) {
		// TODO Auto-generated method stub
		String csvFile = "E://MS SE//Fall 2015//CMPE 239//Project//babs_open_data_year_2//201508_processed_weather_data.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		List<String[]> processedWeatherRecord = new ArrayList<String[]>();

		try {
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();

			while ((line = br.readLine()) != null) {
				String[] processedWeatherData = line.split(cvsSplitBy);
				if (zipCode.equals(processedWeatherData[0])) {
					processedWeatherRecord.add(processedWeatherData);
				}
			}
			appendWeatherRecord(stationID, processedWeatherRecord);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void appendWeatherRecord(String stationID, List processedWeatherData) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String csvFile = "E://MS SE//Fall 2015//CMPE 239//Project//StationDataWithIncomingBikes//DailyData//" + stationID + ".csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		PrintWriter writer = new PrintWriter(
				"E://MS SE//Fall 2015//CMPE 239//Project//StationDataWithIncomingBikes//DailyData//ProcessedWithWeather//" + stationID + ".csv");
		writer.append(
				"station_id,year,month,day,day_of_week,weekday,weekend,feelsLike,humid,visibility,wind,cloudcover,incoming_bikes");
		writer.append("\n");

		try {
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();

			while ((line = br.readLine()) != null) {
				String[] dailyRecord = line.split(cvsSplitBy);
				for (int i = 0; i < processedWeatherData.size(); i++) {
					String[] processed = (String[]) processedWeatherData.get(i);
					if (dailyRecord[1].equals(processed[1]) && dailyRecord[2].equals(processed[2])
							&& dailyRecord[3].equals(processed[3])) {
						writer.append(dailyRecord[0] + "," + dailyRecord[1] + "," + dailyRecord[2] + ","
								+ dailyRecord[3] + "," + dailyRecord[4] + "," + dailyRecord[5] + "," + dailyRecord[6]
								+ "," + processed[4] + "," + processed[5] + "," + processed[6] + "," + processed[7]
								+ "," + processed[8] + "," + dailyRecord[7]);
						writer.append("\n");
						
					}
					writer.flush();
				}
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getZipCode(String stationID) {
		// TODO Auto-generated method stub
		String csvFile = "E://MS SE//Fall 2015//CMPE 239//Project//babs_open_data_year_2//201508_processed_station_data.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();

			while ((line = br.readLine()) != null) {
				String[] processedStationData = line.split(cvsSplitBy);
				if (stationID.equals(processedStationData[0])) {
					String zipCode = processedStationData[7];
					return zipCode;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getStationID(String name) {
		// TODO Auto-generated method stub
		String[] stationFileName = name.split("\\.");
		return stationFileName[0];
	}

}
