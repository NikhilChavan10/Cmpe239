import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RegionalSalesCalculator {

	static HashMap<String, Integer> regionalSales = new HashMap<String, Integer>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File folder = new File(
				"E://MS SE//Fall 2015//CMPE 239//Project//Problem1//DailyData//ProcessedWithWeather//San Jose");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println(listOfFiles[i].getName());
				regionalSalesCalculator(listOfFiles[i]);
			}
		}

		printDailySale();

	}

	private static void printDailySale() {
		// TODO Auto-generated method stub
		/*
		 * Iterator salesIterator = regionalSales.entrySet().iterator(); while
		 * (salesIterator.hasNext()) { Map.Entry<String, Integer> pair =
		 * (Map.Entry<String, Integer>) salesIterator.next();
		 * System.out.println(pair.getKey() + "=" + pair.getValue()); }
		 */

		String fileName = "E://MS SE//Fall 2015//CMPE 239//Project//Problem1//DailyData//ProcessedWithWeather//San Jose//SanJose.csv";
		PrintWriter writer;
		try {
			writer = new PrintWriter(fileName);
			writer.append(
					"year,month,day,day_of_week,weekday,temperature,dew_point,humidity,sea_pressure,visibility,wind_speed,cloudcover,outgoing_bikes");
			writer.append("\n");

			Iterator salesIterator = regionalSales.entrySet().iterator();
			while (salesIterator.hasNext()) {
				Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>) salesIterator.next();
				// System.out.println(pair.getKey() + "=" + pair.getValue());
				String[] weatherRecord = getThatDaysWeatherRecord(pair.getKey());

				if (weatherRecord != null) {
					writer.append(Integer.parseInt(weatherRecord[1]) + "," + Integer.parseInt(weatherRecord[2]) + ","
							+ Integer.parseInt(weatherRecord[3]) + "," + Integer.parseInt(weatherRecord[4]) + ","
							+ Integer.parseInt(weatherRecord[5]) + "," + Double.parseDouble(weatherRecord[6]) + ","
							+ Double.parseDouble(weatherRecord[7]) + "," + Double.parseDouble(weatherRecord[8]) + ","
							+ Double.parseDouble(weatherRecord[9]) + "," + Double.parseDouble(weatherRecord[10]) + ","
							+ Double.parseDouble(weatherRecord[11]) + "," + Double.parseDouble(weatherRecord[12]) + ","
							+ pair.getValue());
					writer.append("\n");
					writer.flush();
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String[] getThatDaysWeatherRecord(String key) {
		// TODO Auto-generated method stub
		String csvFile = "E://MS SE//Fall 2015//CMPE 239//Project//Problem1//DailyData//ProcessedWithWeather//San Jose//2.csv";
		BufferedReader br1 = null;
		String secondline = "";
		String cvsSplitBy = ",";

		String argumentCSVSplitBy = "+";
		String[] yearMonthDay = key.split(cvsSplitBy);

		/*
		 * int year = Integer.parseInt(yearMonthDay[0]); int month =
		 * Integer.parseInt(yearMonthDay[1]); int day =
		 * Integer.parseInt(yearMonthDay[2]);
		 */
		System.out.println(key);
		System.out.println(yearMonthDay[0] + "," + yearMonthDay[1] + "," + yearMonthDay[2]);

		try {
			br1 = new BufferedReader(new FileReader(csvFile));
			secondline = br1.readLine();

			while ((secondline = br1.readLine()) != null) {
				String[] tempWeather = secondline.split(cvsSplitBy);
				if (yearMonthDay[0].equals(tempWeather[1]) && yearMonthDay[1].equals(tempWeather[2])
						&& yearMonthDay[2].equals(tempWeather[3])) {
					return tempWeather;
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static void regionalSalesCalculator(File tempFile) {
		// TODO Auto-generated method stub
		String tempFilePath = tempFile.getAbsolutePath();
		String csvFile = tempFilePath.replaceAll("\\\\", "//");
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();

			while ((line = br.readLine()) != null) {
				String[] dailyRecord = line.split(cvsSplitBy);
				String year = dailyRecord[1] + "," + dailyRecord[2] + "," + dailyRecord[3];
				if (regionalSales.containsKey(year)) {
					int totalSale = regionalSales.get(year);
					totalSale = totalSale + Integer.parseInt(dailyRecord[13]);
					regionalSales.put(year, totalSale);
				} else {
					regionalSales.put(year, Integer.parseInt(dailyRecord[13]));
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
