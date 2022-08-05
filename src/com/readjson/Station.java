package com.readjson;

import org.json.JSONArray;
import org.json.JSONObject;

import com.exception.StationNameException;
import com.tcg.json.JSONUtils;
import java.util.*;


public class Station {
	protected static HashMap<String, String> stationID = new HashMap<String, String>();
	protected static HashMap<String, String> zh_tw = new HashMap<String, String>();
	protected static HashMap<String, String> en = new HashMap<String, String>();
	protected static HashMap<String, String> stationAddress = new HashMap<String, String>();
	
	//將Station的資料存入Map
	public Station() {
		
		JSONObject obj = JSONUtils.getJSONObjectFromFile("/station.json");
		JSONArray jsonArray = obj.getJSONArray("Station");
		ArrayList<JSONObject> station = new ArrayList<JSONObject>();
		ArrayList<JSONObject> stationName = new ArrayList<JSONObject>();

		// 以"station所有資訊"為單位的arraylist
		for (int i = 0; i < jsonArray.length(); i++) {
			station.add((JSONObject) jsonArray.get(i));
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			stationName.add((JSONObject) (station.get(i).get("StationName")));
		}

		// input element:<station name (En),station id>
		for (int i = 0; i < jsonArray.length(); i++) {
			stationID.put((String) stationName.get(i).get("En"), (String) station.get(i).get("StationID"));
		}

		// input element:<station name (En),station name (Ch)>
		for (int i = 0; i < jsonArray.length(); i++) {
			zh_tw.put((String) stationName.get(i).get("En"), (String) stationName.get(i).get("Zh_tw"));
		}

		// input element:<station name (En),station name (En)>
		for (int i = 0; i < jsonArray.length(); i++) {
			en.put((String) stationName.get(i).get("Zh_tw"), (String) stationName.get(i).get("En"));
		}

		// input element:<station name (En),station address>
		for (int i = 0; i < jsonArray.length(); i++) {
			stationAddress.put((String) stationName.get(i).get("En"), (String) station.get(i).get("StationAddress"));
		}

	}
	
	//return StationID
	public String getStationID(String station_name_en) {
		return stationID.get(station_name_en);
	}
	
	//return Station Chinese Name
	public String getChStationName(String station_name_en) {
		return zh_tw.get(station_name_en);
	}
	
	//return Station English Name
	public String getEnStationName (String name) throws StationNameException{
		return en.get(name);
	}
	
	//return Station Address
	public String getstationAddress(String station_name_en) {
		return stationAddress.get(station_name_en);
	}

}
