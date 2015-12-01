import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class WeatherClassifier {

	public static void main(String[] args) {
		String csvFile = "E://MS SE//Fall 2015//CMPE 239//Project//babs_open_data_year_2//201508_weather_data.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();

			PrintWriter writer = new PrintWriter(
					"E://MS SE//Fall 2015//CMPE 239//Project//201508_processed_weather_data.csv");
			writer.append("zipcode,year,month,day,feelsLike,humid,visibility,wind,cloudcover");
			writer.append("\n");
			
			Calendar c = Calendar.getInstance();
			
			while ((line = br.readLine()) != null) {
				String[] weatherRecord = line.split(cvsSplitBy);

				String date = weatherRecord[0];
				c.setTime(new SimpleDateFormat("MM/dd/yyyy").parse(date));
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH) + 1;
				int day = c.get(Calendar.DAY_OF_MONTH);

				String temperature = feelsLikeDecoder(weatherRecord[2]);
				String dewPoint = humidityDecoder(weatherRecord[5]);
				String visibility = visibilityDecoder(weatherRecord[14]);
				String windSpeed = windDecoder(weatherRecord[17]);
				String cloudCover = cloudCoverDecoder(weatherRecord[20]);

				writer.append(weatherRecord[23] + "," + year + "," + month + "," + day + "," + temperature + ","
						+ dewPoint + "," + visibility + "," + windSpeed + "," + cloudCover);
				writer.append("\n");
				writer.flush();
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String feelsLikeDecoder(String temperature1) {
		/*
		 * 101.3 > Extremely Hot 89.6 > Very Hot 81.5 > Hot 74.3 > Very Warm
		 * 69.8 > Warm 61.7 > Pleasant 50 > Mild 39.2 > Cool 26.6 > Cold 21.2 >
		 * Very Cold 10.4 > Freezingly Cold
		 */
		if (temperature1.equals("")) {
			return "N/A";
		} else {
			int temperature = Integer.parseInt(temperature1);
			if (temperature > 101.3) {
				return "Extremely Hot";
			} else {
				if (temperature > 89.6) {
					return "Very Hot";
				} else {
					if (temperature > 81.5) {
						return "Hot";
					} else {
						if (temperature > 74.3) {
							return "Very Warm";
						} else {
							if (temperature > 69.8) {
								return "Warm";
							} else {
								if (temperature > 61.7) {
									return "Pleasant";
								} else {
									if (temperature > 50) {
										return "Mild";
									} else {
										if (temperature > 39.2) {
											return "Cool";
										} else {
											if (temperature > 26.6) {
												return "Cold";
											} else {
												if (temperature > 21.2) {
													return "Very Cold";
												} else {
													if (temperature > 10.4) {
														return "Freezingly Cold";
													} else {
														return "Bitter Cold";
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public static String humidityDecoder(String dewPoint1) {
		if (dewPoint1.equals("")) {
			return "N/A";
		} else {
			int dewPoint = Integer.parseInt(dewPoint1);
			if (dewPoint > 75) {
				return "Oppressively Humid";
			} else {
				if (dewPoint > 70) {
					return "Miserably Humid";
				} else {
					if (dewPoint > 65) {
						return "Uncomfortably Humid";
					} else {
						if (dewPoint > 60) {
							return "Fairly Humid";
						} else {
							if (dewPoint > 50) {
								return "Comfortable";
							} else {
								return "Dry";
							}
						}
					}
				}
			}
		}
	}

	public static String visibilityDecoder(String visibility1) {
		/*
		 * <25m Dense Fog <125m Thick Fog <350m Moderate Fog <885m Light Fog
		 * <1950m Thin Fog <3400m Haze <7950m Light Haze <19050m Clear Very
		 * Clear
		 */
		if (visibility1.equals("")) {
			return "N/A";
		} else {
			int visibility = Integer.parseInt(visibility1);
			if (visibility > 11.83) {
				return "Very Clear";
			} else {
				if (visibility > 4.93) {
					return "Clear";
				} else {
					if (visibility > 2.11) {
						return "Light Haze";
					} else {
						if (visibility > 1.21) {
							return "Haze";
						} else {
							if (visibility > 0.54) {
								return "Thin Fog";
							} else {
								if (visibility > 0.21) {
									return "Light Fog";
								} else {
									if (visibility > 0.07) {
										return "Moderate Fog";
									} else {
										if (visibility > 0.01) {
											return "Thick Fog";
										} else {
											return "Dense Fog";
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public static String windDecoder(String windSpeed1) {
		if (windSpeed1.equals("")) {
			return "N/A";
		} else {
			int windSpeed = Integer.parseInt(windSpeed1);
			if (windSpeed > 72.9) {
				return "Hurricane";
			} else {
				if (windSpeed > 63.6) {
					return "Violent Storm";
				} else {
					if (windSpeed > 54.8) {
						return "Storm";
					} else {
						if (windSpeed > 46.3) {
							return "Strong Gale";
						} else {
							if (windSpeed > 38.4) {
								return "Gale";
							} else {
								if (windSpeed > 31) {
									return "High Wind";
								} else {
									if (windSpeed > 24.1) {
										return "Strong Breeze";
									} else {
										if (windSpeed > 17.9) {
											return "Fresh Breeze";
										} else {
											if (windSpeed > 12.2) {
												return "Moderate Breeze";
											} else {
												if (windSpeed > 7.4) {
													return "Gentle Breeze";
												} else {
													if (windSpeed > 3.4) {
														return "Light Breeze";
													} else {
														if (windSpeed > 0.7) {
															return "Light Air";
														} else {
															return "Calm";
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public static String cloudCoverDecoder(String cloudLevel1) {
		if (cloudLevel1.equals("")) {
			return "N/A";
		} else {
			int cloudLevel = Integer.parseInt(cloudLevel1);
			if (cloudLevel == 8) {
				return "Overcast";
			} else {
				if (cloudLevel == 5 || cloudLevel == 6 || cloudLevel == 7) {
					return "Broken";
				} else {
					if (cloudLevel == 3 || cloudLevel == 4) {
						return "Scattered";
					} else {
						if (cloudLevel == 1 || cloudLevel == 2) {
							return "Few";
						} else {
							return "Clear";
						}
					}
				}
			}
		}
	}
}
