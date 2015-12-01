import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AbsoluteWeather {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File folder = new File("E://MS SE//Fall 2015//CMPE 239//Project//Problem1//DailyData");
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
		String csvFile = "E://MS SE//Fall 2015//CMPE 239//Project//processedForProblem1.csv";
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
		String csvFile = "E://MS SE//Fall 2015//CMPE 239//Project//Problem1//DailyData//" + stationID + ".csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		PrintWriter writer = new PrintWriter(
				"E://MS SE//Fall 2015//CMPE 239//Project//Problem1//DailyData//ProcessedWithWeather//" + stationID
						+ ".csv");
		writer.append(
				"station_id,year,month,day,day_of_week,weekday,temperature,dew_point,humidity,sea_pressure,visibility,wind_speed,cloudcover,outgoing_bikes");
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

						System.out.println(processed[0] + "," + processed[1] + "," + processed[2] + "," + processed[3]);
						System.out.println(processed[4] + "," + processed[5] + "," + processed[6] + "," + processed[7]
								+ "," + processed[8] + "," + processed[9] + "," + processed[10]);

						double temperature = weatherParser(processed[4]);
						double dewPoint = weatherParser(processed[5]);
						double humidity = weatherParser(processed[6]);
						double seaPressure = weatherParser(processed[7]);
						double visibility = weatherParser(processed[8]);
						double windSpeed = weatherParser(processed[9]);
						double cloudCover = weatherParser(processed[10]);

						writer.append(dailyRecord[0] + "," + dailyRecord[1] + "," + dailyRecord[2] + ","
								+ dailyRecord[3] + "," + dailyRecord[4] + "," + dailyRecord[5] + "," + temperature + ","
								+ dewPoint + "," + humidity + "," + seaPressure + "," + visibility + "," + windSpeed
								+ "," + cloudCover + "," + dailyRecord[6]);
						writer.append("\n");

					}
				}
				writer.flush();
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static double weatherParser(String record) {
		// TODO Auto-generated method stub
		if (record.equals("")) {
			return -99;
		}
		return Double.parseDouble(record);
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
