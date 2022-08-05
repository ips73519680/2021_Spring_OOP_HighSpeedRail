package com.readjson;

import org.json.*;

import com.tcg.json.JSONUtils;

public class ReadSeat {
	private static JSONObject obj = JSONUtils.getJSONObjectFromFile("/seat.json");
	private static JSONArray jsonArray = obj.getJSONArray("cars");
	private static JSONObject[] seats = new JSONObject[12];
	
	public ReadSeat() {
		read();
	}
	
	private void read() {
		// read seats from different cars
		for (int i = 0; i < 12; i++) {
			seats[i] = (JSONObject)((JSONObject) jsonArray.get(i)).get("seats");
		}
	}
	
	public static JSONObject[] getSeats() {
		JSONObject[] seat = new JSONObject[12];
		for(int i = 0; i < 12; i++) {
			seat[i] = seats[i];
		}
		return seat;
	}
	
}
