import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WeatherProcessor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String csvFile = "E://MS SE//Fall 2015//CMPE 239//Project//babs_open_data_year_2//201508_weather_data.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();

			Calendar c = Calendar.getInstance();
			int i = 0;

			PrintWriter writer = new PrintWriter("E://MS SE//Fall 2015//CMPE 239//Project//processedForProblem1.csv");
			writer.append(
					"zipcode,year,month,day,temperature,dew_point,humidity,sea_pressure,visibility,wind_speed,cloudcover");
			writer.append("\n");

			while ((line = br.readLine()) != null) {
				String[] weatherRecord = line.split(cvsSplitBy);

				c.setTime(new SimpleDateFormat("MM/dd/yyyy").parse(weatherRecord[0]));
				int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH) + 1;
				int day = c.get(Calendar.DAY_OF_MONTH);

				double temperature = weatherParser(weatherRecord[2]);
				double dewPoint = weatherParser(weatherRecord[5]);
				double humidity = weatherParser(weatherRecord[8]);
				double seaPressure = weatherParser(weatherRecord[11]);
				double visibility = weatherParser(weatherRecord[14]);
				double windSpeed = weatherParser(weatherRecord[17]);
				double cloudCover = weatherParser(weatherRecord[20]);
				int zipCode = Integer.parseInt(weatherRecord[23]);

				writer.append(zipCode + "," + year + "," + month + "," + day + "," + temperature + "," + dewPoint + ","
						+ humidity + "," + seaPressure + "," + visibility + "," + windSpeed + "," + cloudCover);
				writer.append("\n");
				writer.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
}
