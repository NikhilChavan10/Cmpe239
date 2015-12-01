import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class EventInjector {

	static HashMap<Integer, ArrayList<Integer>> sharkCalendar = new HashMap<Integer, ArrayList<Integer>>();
	static HashMap<Integer, ArrayList<Integer>> giantsCalendar = new HashMap<Integer, ArrayList<Integer>>();
	static HashMap<Integer, ArrayList<Integer>> fourty9ersCalendar = new HashMap<Integer, ArrayList<Integer>>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		getSharkCalendar();
		getGiantsCalendar();
		get49ersCalendar();

		File folder = new File("E://MS SE//Fall 2015//CMPE 239//Project//Problem1//DailyData//ProcessedWithWeather");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				injectEventData(listOfFiles[i]);
			}
		}
	}

	private static void injectEventData(File currentFile) {
		// TODO Auto-generated method stub
		String tempFilePath = currentFile.getAbsolutePath();
		String csvFile = tempFilePath.replaceAll("\\\\", "//");

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();

			String stationName = currentFile.getName();
			String newPath = "E://MS SE//Fall 2015//CMPE 239//Project//Problem1//DailyData//ProcessedWithWeather//Problem//"
					+ stationName;
			PrintWriter writer = new PrintWriter(newPath);
			writer.append(
					"year,month,day,day_of_week,weekday,temperature,dew_point,humidity,sea_pressure,visibility,windspeed,cloudcover,is_game,outgoing_bikes");
			writer.append("\n");

			while ((line = br.readLine()) != null) {
				String[] dailyRecord = line.split(cvsSplitBy);
				int current_month = Integer.parseInt(dailyRecord[1]);
				int current_day = Integer.parseInt(dailyRecord[2]);

				int isSharkGame = getSharkGameStatus(current_month, current_day);
				int isGiantsGame = getGiatnsGameStatus(current_month, current_day);
				int is49ersGame = get49ersGameStatus(current_month, current_day);
				int isGame = 0;
				if(isSharkGame == 1 || isGiantsGame == 1 || is49ersGame ==1) {
					isGame = 1;
				}

				writer.append(dailyRecord[0] + "," + dailyRecord[1] + "," + dailyRecord[2] + "," + dailyRecord[3] + ","
						+ dailyRecord[4] + "," + dailyRecord[5] + "," + dailyRecord[6] + "," + dailyRecord[7] + ","
						+ dailyRecord[8] + "," + dailyRecord[9] + "," + dailyRecord[10] + "," + dailyRecord[11] + ","
						+ isGame + "," + dailyRecord[12]);
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

	private static int get49ersGameStatus(int current_month, int current_day) {
		// TODO Auto-generated method stub
		if (fourty9ersCalendar.containsKey(current_month)) {
			ArrayList<Integer> gameDays = fourty9ersCalendar.get(current_month);
			if (gameDays.contains(current_day)) {
				return 1;
			}
		}
		return 0;
	}

	private static int getGiatnsGameStatus(int current_month, int current_day) {
		// TODO Auto-generated method stub
		if (giantsCalendar.containsKey(current_month)) {
			ArrayList<Integer> gameDays = giantsCalendar.get(current_month);
			if (gameDays.contains(current_day)) {
				return 1;
			}
		}
		return 0;
	}

	private static int getSharkGameStatus(int current_month, int current_day) {
		// TODO Auto-generated method stub
		if (sharkCalendar.containsKey(current_month)) {
			ArrayList<Integer> gameDays = sharkCalendar.get(current_month);
			if (gameDays.contains(current_day)) {
				return 1;
			}
		}
		return 0;
	}

	private static void get49ersCalendar() {
		// TODO Auto-generated method stub

		ArrayList<Integer> a1 = new ArrayList<Integer>();
		a1 = new ArrayList<Integer>();
		a1.add(14);
		a1.add(28);
		fourty9ersCalendar.put(9, a1);

		a1 = new ArrayList<Integer>();
		a1.add(5);
		fourty9ersCalendar.put(10, a1);

		a1 = new ArrayList<Integer>();
		a1.add(2);
		a1.add(23);
		a1.add(27);
		fourty9ersCalendar.put(11, a1);

		a1 = new ArrayList<Integer>();
		a1.add(20);
		a1.add(28);
		fourty9ersCalendar.put(12, a1);

		a1 = new ArrayList<Integer>();
		a1.add(23);
		fourty9ersCalendar.put(8, a1);
	}

	private static void getGiantsCalendar() {

		ArrayList<Integer> a1 = new ArrayList<Integer>();
		// TODO Auto-generated method stub
		a1.add(9);
		a1.add(10);
		a1.add(11);
		a1.add(12);
		a1.add(13);
		a1.add(14);
		a1.add(25);
		a1.add(26);
		a1.add(27);
		a1.add(28);
		giantsCalendar.put(9, a1);

		a1 = new ArrayList<Integer>();
		a1.add(13);
		a1.add(14);
		a1.add(15);
		a1.add(16);
		a1.add(17);
		a1.add(18);
		a1.add(19);
		a1.add(21);
		a1.add(22);
		a1.add(23);
		giantsCalendar.put(4, a1);

		a1 = new ArrayList<Integer>();
		a1.add(1);
		a1.add(2);
		a1.add(3);
		a1.add(4);
		a1.add(5);
		a1.add(6);
		a1.add(7);
		a1.add(8);
		a1.add(9);
		a1.add(10);
		a1.add(19);
		a1.add(20);
		a1.add(21);
		a1.add(28);
		a1.add(29);
		a1.add(30);
		a1.add(31);
		giantsCalendar.put(5, a1);

		a1 = new ArrayList<Integer>();
		a1.add(1);
		a1.add(2);
		a1.add(3);
		a1.add(12);
		a1.add(13);
		a1.add(14);
		a1.add(15);
		a1.add(16);
		a1.add(23);
		a1.add(24);
		a1.add(25);
		a1.add(26);
		a1.add(27);
		a1.add(28);
		giantsCalendar.put(6, a1);

		a1 = new ArrayList<Integer>();
		a1.add(6);
		a1.add(7);
		a1.add(8);
		a1.add(10);
		a1.add(11);
		a1.add(12);
		a1.add(24);
		a1.add(25);
		a1.add(26);
		a1.add(27);
		a1.add(28);
		a1.add(29);
		giantsCalendar.put(7, a1);

		a1 = new ArrayList<Integer>();
		a1.add(11);
		a1.add(12);
		a1.add(13);
		a1.add(14);
		a1.add(15);
		a1.add(16);
		a1.add(25);
		a1.add(26);
		a1.add(27);
		a1.add(28);
		a1.add(29);
		a1.add(30);
		giantsCalendar.put(8, a1);

	}

	private static void getSharkCalendar() {
		// TODO Auto-generated method stub
		ArrayList<Integer> a1 = new ArrayList<Integer>();
		a1.add(23);
		a1.add(26);
		a1.add(27);
		a1.add(30);
		sharkCalendar.put(9, a1);

		a1 = new ArrayList<Integer>();
		a1.add(11);
		a1.add(23);
		a1.add(25);
		sharkCalendar.put(10, a1);

		a1 = new ArrayList<Integer>();
		a1.add(1);
		a1.add(6);
		a1.add(20);
		a1.add(22);
		a1.add(26);
		a1.add(29);
		sharkCalendar.put(11, a1);

		a1 = new ArrayList<Integer>();
		a1.add(2);
		a1.add(4);
		a1.add(9);
		a1.add(11);
		a1.add(13);
		a1.add(18);
		a1.add(20);
		a1.add(30);
		sharkCalendar.put(12, a1);

		a1 = new ArrayList<Integer>();
		a1.add(3);
		a1.add(10);
		a1.add(15);
		a1.add(17);
		a1.add(19);
		a1.add(21);
		a1.add(29);
		a1.add(31);
		sharkCalendar.put(1, a1);

		a1 = new ArrayList<Integer>();
		a1.add(2);
		a1.add(7);
		a1.add(9);
		a1.add(11);
		a1.add(15);
		a1.add(21);
		a1.add(26);
		a1.add(28);
		sharkCalendar.put(2, a1);

		a1 = new ArrayList<Integer>();
		a1.add(2);
		a1.add(7);
		a1.add(9);
		a1.add(12);
		a1.add(14);
		sharkCalendar.put(3, a1);

		a1 = new ArrayList<Integer>();
		a1.add(1);
		a1.add(3);
		a1.add(6);
		sharkCalendar.put(4, a1);

	}

}
