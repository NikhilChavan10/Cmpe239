package com;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.ws.rs.Path;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;

public class REvaluator {

	String STATION_NAME;
	Boolean IS_WEEKDAY;
	Boolean IS_GAME;
	String FEELS_LIKE;
	String HUMID;
	String VISIBILITY;
	String WIND;
	String CLOUDCOVER;

	public REvaluator(String stationName, Boolean isWeekday, Boolean isGame, String feelsLike, String humid,
			String visibility, String wind, String cloudcover) {
		this.CLOUDCOVER = cloudcover;
		this.FEELS_LIKE = feelsLike;
		this.HUMID = humid;
		this.IS_GAME = isGame;
		this.IS_WEEKDAY = isWeekday;
		this.STATION_NAME = stationName;
		this.VISIBILITY = visibility;
		this.WIND = wind;
		// TODO Auto-generated constructor stub
	}

	public String evaluator() {
		Rengine engine = new Rengine(new String[] { "--no-save" }, false, null);

		Integer stationID = getStationID(STATION_NAME);
		String fileName = stationID.toString() + ".csv";
		String realDataFileName = stationID.toString() + "realData.csv";
		String realData = createTestData(CLOUDCOVER, FEELS_LIKE, HUMID, IS_GAME, IS_WEEKDAY, VISIBILITY, WIND);

		String newPath = "E://MS SE//Fall 2015//CMPE 239//Project//Problem2//LabeledData//" + fileName;
		PrintWriter writer;
		try {
			writer = new PrintWriter(newPath);
			writer.append(
					"station_id,year,month,day,day_of_week,weekday,feelsLike,humid,visibility,wind,cloudcover,is_game,outgoing_bikes,demand");
			writer.append("\n");
			writer.append(stationID + ",0000,00,00,0,0" + realData);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;

		String readCSVFile = "salesData <- read.csv(\"" + fileName + "\", header = TRUE)";
		String readRealDataFile = "realData <- read.csv(\"" + newPath + "\", header = TRUE)";

		engine.eval("setwd(\"E://MS SE//Fall 2015//CMPE 239//Project//Problem2//LabeledData\")");
		engine.eval(readCSVFile);
		engine.eval("demandDataFactor <- demandData");
		engine.eval("demandDataFactor$weekday <- factor(demandData$weekday)");
		engine.eval("demandDataFactor$is_game <- factor(demandData$is_game)");
		engine.eval("library(randomForest)");
		engine.eval("set.seed(200)");
		engine.eval("ind <- sample(2, nrow(demandDataFactor), replace = TRUE, prob = c(0.99, 0.01))");
		engine.eval("trainData <- demandDataFactor[ind==1, ]");
		engine.eval("testData <- demandDataFactor[ind==2, ]");
		engine.eval(
				"fit.rf <- randomForest(demand ~ weekday + feelsLike + humid + visibility + wind + cloudcover + is_game, data = trainData, proximity = TRUE, ntree = 4000)");
		engine.eval("trainPred <- predict(fit.rf, newdata = trainData)");
		engine.eval("testPred <- predict(fit.rf, newdata = testData)");
		engine.eval("realPred <- predict(fit.rf, newdata = realData)");
		REXP result = engine.eval("realPred");

		String demand = getTheResult(result);

		engine.end();
		return demand;
	}

	private String getTheResult(REXP result) {
		// TODO Auto-generated method stub
		String resultString = result.toString();
		String splitBy = ",";
		String[] tempString = resultString.split(splitBy);
		String demand = "";
		Character id = tempString[1].charAt(5);
		if (id.equals('0')) {
			demand = "High";
		} else {
			if (id.equals('1')) {
				demand = "Normal";
			} else {
				demand = "Low";
			}
		}
		return demand;
	}

	private String createTestData(String cLOUDCOVER2, String fEELS_LIKE2, String hUMID2, Boolean iS_GAME2,
			Boolean iS_WEEKDAY2, String vISIBILITY2, String wIND2) {
		// TODO Auto-generated method stub
		Integer weekday = 0;
		Integer isGame = 0;
		if (iS_WEEKDAY2) {
			weekday = 1;
		}
		if (iS_GAME2) {
			isGame = 1;
		}
		String testData = weekday.toString() + "," + fEELS_LIKE2 + "," + hUMID2 + "," + vISIBILITY2 + "," + wIND2 + ","
				+ cLOUDCOVER2 + "," + isGame.toString();
		return testData;
	}

	private int getStationID(String sTATION_NAME2) {
		// TODO Auto-generated method stub
		String csvFile = "E://MS SE//Fall 2015//CMPE 239//Project//babs_open_data_year_2//201508_processed_station_data.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		return 0;
	}

}
