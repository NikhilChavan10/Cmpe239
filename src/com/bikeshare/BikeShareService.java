package com.bikeshare;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("demand")
public class BikeShareService {
	
	@GET
	@Produces("application/json")
	public String convertCtoFfromInput() {
		return "kunal";
	}
	
	
	@Path("{c}")
	@GET
	@Produces("application/json")
	public String convertCtoFfromInput(@QueryParam("stationName") String stationName,
			@QueryParam("isWeekday") Boolean isWeekday, @QueryParam("isGame") Boolean isGame,
			@QueryParam("feelsLike") String feelsLike, @QueryParam("humid") String humid,
			@QueryParam("visibility") String visibility, @QueryParam("wind") String wind,
			@QueryParam("cloudcover") String cloudcover) {
		//REvaluator r = new REvaluator(stationName, isWeekday, isGame, feelsLike, humid, visibility, wind, cloudcover);
		String demand=null;//r.evaluator();
		return demand;
}
}
