import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StatusDataExtractorForIncomingBikes {

	public static void main(String[] args) {
		String csvFile = "E://MS SE//Fall 2015//CMPE 239//Project//babs_open_data_year_2//201508_status_data.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();
			String station_id = "";
			int hourSaleCount = 0;
			int previousRowBikeCount = 0;
			int previousRowStationId = 0;

			PrintWriter writer = new PrintWriter("E://MS SE//Fall 2015//CMPE 239//Project//incominghourly.csv");
			writer.append("station_id,year,month,day,from,to,day_of_week,weekday,weekend,incoming_bikes");
			writer.append("\n");
			String fromTime = "";
			Calendar c = Calendar.getInstance();
			while ((line = br.readLine()) != null) {
				String[] minuteRecord = line.split(cvsSplitBy);
				int temp_station_id = Integer.parseInt(minuteRecord[0]);

				if (minuteRecord[3].contains(":00")) {
					hourSaleCount = 0;
					previousRowBikeCount = Integer.parseInt(minuteRecord[1]);
					fromTime = minuteRecord[3];
				}
				int currentRowBikeCount = Integer.parseInt(minuteRecord[1]);
				if (currentRowBikeCount > previousRowBikeCount) {
					hourSaleCount = hourSaleCount + (currentRowBikeCount - previousRowBikeCount);
				}
				if (minuteRecord[3].contains(":59")) {
					String tempMinute = minuteRecord[3];
					c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tempMinute.replace("\"", "")));
					// System.out.println(c.toString());
					int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
					int year = c.get(Calendar.YEAR);
					int month = c.get(Calendar.MONTH) + 1;
					int day = c.get(Calendar.DAY_OF_MONTH);
					String tempFromTime[] = fromTime.replaceAll("\"", "").split(" ");
					String tempToTime[] = tempMinute.replaceAll("\"", "").split(" ");
					int weekday = 0, weekend = 0;
					if (dayOfWeek >= 2 && dayOfWeek <= 6) {
						weekday = 1;
					} else {
						weekend = 1;
					}
					writer.append(temp_station_id + "," + year + "," + month + "," + day + "," + tempFromTime[1] + ","
							+ tempToTime[1] + "," + dayOfWeek + "," + weekday + "," + weekend + "," + hourSaleCount);
					writer.append("\n");
				}
				previousRowBikeCount = Integer.parseInt(minuteRecord[1]);
				previousRowStationId = temp_station_id;

			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
