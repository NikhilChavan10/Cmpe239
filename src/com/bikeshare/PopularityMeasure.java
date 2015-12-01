package station_hubs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PopularityMeasure {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String tripCSVFile = "E://MS SE//Fall 2015//CMPE 239//Project//Problem3//paloAlto.csv";
		String stationCSVFile = "E://MS SE//Fall 2015//CMPE 239//Project//babs_open_data_year_2//201508_station_data.csv";

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		HashMap<Integer, HashMap<Integer, Integer>> stationTripData = new HashMap<Integer, HashMap<Integer, Integer>>();
		try {
			br = new BufferedReader(new FileReader(tripCSVFile));
			line = br.readLine();
			int startTerminal = 0;
			int endTerminal = 0;

			Calendar c = Calendar.getInstance();

			while ((line = br.readLine()) != null) {
				String[] tripRecord = line.split(cvsSplitBy);

				String dateTime = tripRecord[2];
				c.setTime(new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(dateTime));
				int hour = c.get(Calendar.HOUR_OF_DAY);
				int minute = c.get(Calendar.MINUTE);

				if (hour == 8) {
					startTerminal = Integer.parseInt(tripRecord[4]);
					endTerminal = Integer.parseInt(tripRecord[7]);
					if (stationTripData.containsKey(startTerminal)) {
						HashMap<Integer, Integer> tempEndTerminalMap = stationTripData.get(startTerminal);
						if (tempEndTerminalMap.containsKey(endTerminal)) {
							int tripCount = tempEndTerminalMap.get(endTerminal);
							tripCount++;
							tempEndTerminalMap.put(endTerminal, tripCount);
							stationTripData.put(startTerminal, tempEndTerminalMap);
						} else {
							tempEndTerminalMap.put(endTerminal, 1);
							stationTripData.put(startTerminal, tempEndTerminalMap);
						}
					} else {
						HashMap<Integer, Integer> endTerminalMap = new HashMap<Integer, Integer>();
						endTerminalMap.put(endTerminal, 1);
						stationTripData.put(startTerminal, endTerminalMap);
					}
				}
			}

			br = new BufferedReader(new FileReader(stationCSVFile));
			line = br.readLine();
			HashMap<Integer, String> stationData = new HashMap<Integer, String>();
			while ((line = br.readLine()) != null) {
				String[] stationRecord = line.split(cvsSplitBy);
				int stationID = Integer.parseInt(stationRecord[0]);
				String stationName = stationRecord[1];
				stationData.put(stationID, stationName);
			}

			Iterator iterator = stationTripData.keySet().iterator();
			int max_trip = 0;
			String finalStationName = "";
			String finalEndStationName = "";
			int finalTripCount = 0;
			while (iterator.hasNext()) {

				int tempStartTerminal = (int) iterator.next();
				String tempStationName = stationData.get(tempStartTerminal);

				HashMap<Integer, Integer> temptripData = stationTripData.get(tempStartTerminal);
				Iterator tripIterator = temptripData.keySet().iterator();

				while (tripIterator.hasNext()) {
					int tempEndTerminal = (int) tripIterator.next();
					String tempEndStationName = stationData.get(tempEndTerminal);
					int trip_count = temptripData.get(tempEndTerminal);
					if (trip_count > max_trip) {
						finalStationName = tempStationName;
						finalEndStationName = tempEndStationName;
						finalTripCount = trip_count;
						max_trip = trip_count;
						// System.out.println(tempStationName + " To " +
						// tempEndStationName + " = " + trip_count);
					}
					// writer.append(tempStartTerminal + "," + tempStationName +
					// "," + tempEndTerminal + ","
					// + tempEndStationName + "," + trip_count);
					// writer.append("\n");
				}

			}
			System.out.println(finalStationName + " To " + finalEndStationName + " = " + finalTripCount);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
