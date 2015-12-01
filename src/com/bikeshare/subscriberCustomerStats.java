import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

public class subscriberCustomerStats {

	public static void main(String[] args) {

		String tripCSVFile = "E://MS SE//Fall 2015//CMPE 239//Project//babs_open_data_year_2//201508_trip_data.csv";
		String stationCSVFile = "E://MS SE//Fall 2015//CMPE 239//Project//babs_open_data_year_2//201508_station_data.csv";

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		HashMap<Integer, HashMap<Integer, Integer>> stationSubscriberData = new HashMap<Integer, HashMap<Integer, Integer>>();
		HashMap<Integer, HashMap<Integer, Integer>> stationCustomerData = new HashMap<Integer, HashMap<Integer, Integer>>();

		try {
			br = new BufferedReader(new FileReader(tripCSVFile));
			line = br.readLine();
			int startTerminal = 0;
			int endTerminal = 0;
			String customerType = "";
			while ((line = br.readLine()) != null) {
				String[] tripRecord = line.split(cvsSplitBy);
				startTerminal = Integer.parseInt(tripRecord[4]);
				endTerminal = Integer.parseInt(tripRecord[7]);
				customerType = tripRecord[9];
				// System.out.println(customerType);
				if (customerType.equalsIgnoreCase("subscriber")) {
					if (stationSubscriberData.containsKey(startTerminal)) {
						HashMap<Integer, Integer> tempEndTerminalMap = stationSubscriberData.get(startTerminal);
						if (tempEndTerminalMap.containsKey(endTerminal)) {
							int tripCount = tempEndTerminalMap.get(endTerminal);
							tripCount++;
							tempEndTerminalMap.put(endTerminal, tripCount);
							stationSubscriberData.put(startTerminal, tempEndTerminalMap);
						} else {
							tempEndTerminalMap.put(endTerminal, 1);
							stationSubscriberData.put(startTerminal, tempEndTerminalMap);
						}
					} else {
						HashMap<Integer, Integer> endTerminalMap = new HashMap<Integer, Integer>();
						endTerminalMap.put(endTerminal, 1);
						stationSubscriberData.put(startTerminal, endTerminalMap);
					}
				} else {
					if (stationCustomerData.containsKey(startTerminal)) {
						HashMap<Integer, Integer> tempEndTerminalMap = stationCustomerData.get(startTerminal);
						if (tempEndTerminalMap.containsKey(endTerminal)) {
							int tripCount = tempEndTerminalMap.get(endTerminal);
							tripCount++;
							tempEndTerminalMap.put(endTerminal, tripCount);
							stationCustomerData.put(startTerminal, tempEndTerminalMap);
						} else {
							tempEndTerminalMap.put(endTerminal, 1);
							stationCustomerData.put(startTerminal, tempEndTerminalMap);
						}
					} else {
						HashMap<Integer, Integer> endTerminalMap = new HashMap<Integer, Integer>();
						endTerminalMap.put(endTerminal, 1);
						stationCustomerData.put(startTerminal, endTerminalMap);
					}
				}
			}

			System.out.println(stationSubscriberData);
			System.out.println(stationCustomerData);

			br = new BufferedReader(new FileReader(stationCSVFile));
			line = br.readLine();
			HashMap<Integer, String> stationData = new HashMap<Integer, String>();

			while ((line = br.readLine()) != null) {
				String[] stationRecord = line.split(cvsSplitBy);
				int stationID = Integer.parseInt(stationRecord[0]);
				String stationName = stationRecord[1];
				stationData.put(stationID, stationName);
			}

			writeToSubscriberFile(stationData, stationSubscriberData);
			writeToCustomerFile(stationData, stationCustomerData);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void writeToCustomerFile(HashMap<Integer, String> stationData,
			HashMap<Integer, HashMap<Integer, Integer>> stationCustomerData) {
		Iterator iterator = stationCustomerData.keySet().iterator();

		PrintWriter writer;
		try {
			writer = new PrintWriter(
					"E://MS SE//Fall 2015//CMPE 239//Project//projectFindings//station_customer_stats.csv");
			writer.append(
					"start_station_id,start_station_name,end_station_id,end_station_name,total_trips_customer");
			writer.append("\n");
			while (iterator.hasNext()) {

				int tempStartTerminal = (int) iterator.next();
				String tempStationName = stationData.get(tempStartTerminal);

				HashMap<Integer, Integer> temptripData = stationCustomerData.get(tempStartTerminal);
				Iterator tripIterator = temptripData.keySet().iterator();

				while (tripIterator.hasNext()) {
					int tempEndTerminal = (int) tripIterator.next();
					String tempEndStationName = stationData.get(tempEndTerminal);
					int trip_count = temptripData.get(tempEndTerminal);
					writer.append(tempStartTerminal + "," + tempStationName + "," + tempEndTerminal + ","
							+ tempEndStationName + "," + trip_count);
					writer.append("\n");
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void writeToSubscriberFile(HashMap<Integer, String> stationData,
			HashMap<Integer, HashMap<Integer, Integer>> stationSubscriberData) {
		Iterator iterator = stationSubscriberData.keySet().iterator();

		PrintWriter writer;
		try {
			writer = new PrintWriter(
					"E://MS SE//Fall 2015//CMPE 239//Project//projectFindings//station_subscriber_stats.csv");
			writer.append(
					"start_station_id,start_station_name,end_station_id,end_station_name,total_trips_subscriber");
			writer.append("\n");
			while (iterator.hasNext()) {

				int tempStartTerminal = (int) iterator.next();
				String tempStationName = stationData.get(tempStartTerminal);

				HashMap<Integer, Integer> temptripData = stationSubscriberData.get(tempStartTerminal);
				Iterator tripIterator = temptripData.keySet().iterator();

				while (tripIterator.hasNext()) {
					int tempEndTerminal = (int) tripIterator.next();
					String tempEndStationName = stationData.get(tempEndTerminal);
					int trip_count = temptripData.get(tempEndTerminal);
					writer.append(tempStartTerminal + "," + tempStationName + "," + tempEndTerminal + ","
							+ tempEndStationName + "," + trip_count);
					writer.append("\n");
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}