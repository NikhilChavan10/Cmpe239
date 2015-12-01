import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class DailySalesCalculator {

	public static void main(String[] args) {

		File folder = new File("E://MS SE//Fall 2015//CMPE 239//Project//Problem1");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println(listOfFiles[i].getName());
				dailySalesCalculator(listOfFiles[i]);
			}
		}

	}

	public static void dailySalesCalculator(File tempFile) {
		String tempFilePath = tempFile.getAbsolutePath();
		String csvFile = tempFilePath.replaceAll("\\\\", "//");
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();

			int previous_day = 0;
			int dailySalesCount = 0;
			String[] previousRow = line.split(cvsSplitBy);

			String stationName = tempFile.getName();
			String newPath = "E://MS SE//Fall 2015//CMPE 239//Project//Problem1//DailyData//" + stationName;
			PrintWriter writer = new PrintWriter(newPath);
			writer.append("station_id,year,month,day,day_of_week,weekday,outgoing_bikes");
			writer.append("\n");

			while ((line = br.readLine()) != null) {
				String[] hourlyRecord = line.split(cvsSplitBy);
				int current_day = Integer.parseInt(hourlyRecord[3]);
				if (current_day != previous_day && previous_day != 0) {
					writer.append(previousRow[0] + "," + previousRow[1] + "," + previousRow[2] + "," + previousRow[3]
							+ "," + previousRow[6] + "," + previousRow[7] + "," + dailySalesCount);
					writer.append("\n");
					dailySalesCount = 0;
				}

				dailySalesCount = dailySalesCount + Integer.parseInt(hourlyRecord[8]);
				previous_day = current_day;
				previousRow = hourlyRecord;
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
