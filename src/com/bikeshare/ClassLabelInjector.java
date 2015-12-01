package station_hubs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class ClassLabelInjector {

	static HashMap<Integer, Integer> averageCount = new HashMap<Integer, Integer>();

	public static void main(String[] args) {

		fillAverageCount();
		normalizeValues();
	}

	private static void normalizeValues() {
		// TODO Auto-generated method stub
		File folder = new File("E://MS SE//Fall 2015//CMPE 239//Project//Problem2//");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				// System.out.println(listOfFiles[i].getName());
				nowNormalize(listOfFiles[i]);
			}
		}
	}

	private static void nowNormalize(File tempFile) {
		// TODO Auto-generated method stub
		String tempFilePath = tempFile.getAbsolutePath();
		String csvFile = tempFilePath.replaceAll("\\\\", "//");
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String stationSplitBy = "\\.";

		try {
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();

			String tempStationName = tempFile.getName();
			String[] stationArray = tempStationName.split(stationSplitBy);
			int station = Integer.parseInt(stationArray[0]);
			int stationAverage = averageCount.get(station);

			String newPath = "E://MS SE//Fall 2015//CMPE 239//Project//Problem2//LabeledData//" + tempStationName;
			PrintWriter writer = new PrintWriter(newPath);
			writer.append(
					"station_id,year,month,day,day_of_week,weekday,feelsLike,humid,visibility,wind,cloudcover,is_game,outgoing_bikes,demand");
			writer.append("\n");

			while ((line = br.readLine()) != null) {
				String[] dailyRecord = line.split(cvsSplitBy);
				int currentRowSale = Integer.parseInt(dailyRecord[13]);
				String classLabel = "";
				int normalizedValue = (100*currentRowSale)/(stationAverage);
				if (normalizedValue > 150) {
					classLabel = "High";
					// System.out.println("High Demand");
				} else {
					if (normalizedValue < 50) {
						classLabel = "Low";
						// System.out.println("Normal Demand");
					} else {
						classLabel = "Normal";
						// System.out.println("Low Demand");
					}
				}
				writer.append(dailyRecord[0] + "," + dailyRecord[1] + "," + dailyRecord[2] + "," + dailyRecord[3] + ","
						+ dailyRecord[4] + "," + dailyRecord[5] + "," + dailyRecord[7] + "," + dailyRecord[8] + ","
						+ dailyRecord[9] + "," + dailyRecord[10] + "," + dailyRecord[11] + "," + dailyRecord[12] + ","
						+ dailyRecord[13] + "," + classLabel);
				writer.append("\n");
				writer.flush();
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void fillAverageCount() {

		File folder = new File("E://MS SE//Fall 2015//CMPE 239//Project//Problem2//");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				// System.out.println(listOfFiles[i].getName());
				calculateAverage(listOfFiles[i]);
			}
		}
	}

	private static void calculateAverage(File tempFile) {
		// TODO Auto-generated method stub
		String tempFilePath = tempFile.getAbsolutePath();
		String csvFile = tempFilePath.replaceAll("\\\\", "//");
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String stationSplitBy = "\\.";

		try {
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();

			String tempStationName = tempFile.getName();
			String[] stationArray = tempStationName.split(stationSplitBy);
			int station = Integer.parseInt(stationArray[0]);

			int total_sales = 0;
			int rowcount = 0;
			while ((line = br.readLine()) != null) {
				String[] dailyRecord = line.split(cvsSplitBy);
				int currentDaySale = Integer.parseInt(dailyRecord[13]);
				total_sales = total_sales + currentDaySale;
				rowcount = rowcount + 1;
			}
			int average = total_sales / rowcount;
			averageCount.put(station, average);
			// System.out.println("Station: " + station + " Average Daily
			// Outgoing Bikes: " + average);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
