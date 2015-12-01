package station_hubs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TripDataSeparator {

	public static void main(String[] args) {

		String tripCSVFile = "E://MS SE//Fall 2015//CMPE 239//Project//babs_open_data_year_2//201508_trip_data.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
			br = new BufferedReader(new FileReader(tripCSVFile));
			line = br.readLine();

			PrintWriter sanJosewriter = new PrintWriter(
					"E://MS SE//Fall 2015//CMPE 239//Project//Problem3//sanJose.csv");
			sanJosewriter.append(
					"Trip ID,Duration,Start Date,Start Station,Start Terminal,End Date,End Station,End Terminal,Bike #,Subscriber Type,Zip Code");
			sanJosewriter.append("\n");

			PrintWriter redwoodCityWriter = new PrintWriter(
					"E://MS SE//Fall 2015//CMPE 239//Project//Problem3//redwoodCity.csv");
			redwoodCityWriter.append(
					"Trip ID,Duration,Start Date,Start Station,Start Terminal,End Date,End Station,End Terminal,Bike #,Subscriber Type,Zip Code");
			redwoodCityWriter.append("\n");

			PrintWriter mountainViewWriter = new PrintWriter(
					"E://MS SE//Fall 2015//CMPE 239//Project//Problem3//mountainView.csv");
			mountainViewWriter.append(
					"Trip ID,Duration,Start Date,Start Station,Start Terminal,End Date,End Station,End Terminal,Bike #,Subscriber Type,Zip Code");
			mountainViewWriter.append("\n");

			PrintWriter paloAltoWriter = new PrintWriter(
					"E://MS SE//Fall 2015//CMPE 239//Project//Problem3//paloAlto.csv");
			paloAltoWriter.append(
					"Trip ID,Duration,Start Date,Start Station,Start Terminal,End Date,End Station,End Terminal,Bike #,Subscriber Type,Zip Code");
			paloAltoWriter.append("\n");

			PrintWriter sanFranciscoWriter = new PrintWriter(
					"E://MS SE//Fall 2015//CMPE 239//Project//Problem3//sanFrancisco.csv");
			sanFranciscoWriter.append(
					"Trip ID,Duration,Start Date,Start Station,Start Terminal,End Date,End Station,End Terminal,Bike #,Subscriber Type,Zip Code");
			sanFranciscoWriter.append("\n");

			//Calendar c = Calendar.getInstance();

			while ((line = br.readLine()) != null) {
				String[] tripRecord = line.split(cvsSplitBy);
				
				String stationID = tripRecord[4];

				String region = findTheRegion(stationID);

				if (region.equalsIgnoreCase("San Jose")) {
					sanJosewriter.append(line);
					sanJosewriter.append("\n");
				} else {
					if (region.equalsIgnoreCase("Redwood City")) {
						redwoodCityWriter.append(line);
						redwoodCityWriter.append("\n");
					} else {
						if (region.equalsIgnoreCase("Mountain View")) {
							mountainViewWriter.append(line);
							mountainViewWriter.append("\n");
						} else {
							if (region.equalsIgnoreCase("Palo Alto")) {
								paloAltoWriter.append(line);
								paloAltoWriter.append("\n");
							} else {
								sanFranciscoWriter.append(line);
								sanFranciscoWriter.append("\n");
							}
						}
					}
				}

				sanJosewriter.flush();
				redwoodCityWriter.flush();
				mountainViewWriter.flush();
				paloAltoWriter.flush();
				sanFranciscoWriter.flush();

				/*c.setTime(new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(dateTime));
				int hour = c.get(Calendar.HOUR_OF_DAY);
				int minute = c.get(Calendar.MINUTE);*/

			}
			
			sanJosewriter.close();
			redwoodCityWriter.close();
			mountainViewWriter.close();
			paloAltoWriter.close();
			sanFranciscoWriter.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String findTheRegion(String stationID) {
		// TODO Auto-generated method stub
		String stationCSVFile = "E://MS SE//Fall 2015//CMPE 239//Project//babs_open_data_year_2//201508_processed_station_data.csv";
		BufferedReader stationBr = null;
		String line = "";
		String cvsSplitBy = ",";
		String region = "";

		try {
			stationBr = new BufferedReader(new FileReader(stationCSVFile));
			line = stationBr.readLine();

			while ((line = stationBr.readLine()) != null) {
				String[] stationRecord = line.split(cvsSplitBy);
				String startStation = stationRecord[0];

				if (stationID.equals(startStation)) {
					region = stationRecord[5];
					return region;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return region;
	}

}
