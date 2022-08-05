package com.readjson;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tcg.json.JSONUtils;
import java.util.*;

public class ReadPrice {

	protected static Map<String, JSONObject> DestinationStationsMap = new HashMap<String, JSONObject>();
	protected static Map<String, JSONObject> FaresMap = new HashMap<String, JSONObject>();

	protected String originStation;
	protected String destinationStations;
	protected String ticketType;
	protected String[] originStationNumber = { "0990", "1000", "1010", "1020", "1030", "1035", "1040",
			"1043", "1047", "1050", "1060", "1070" };
	protected ArrayList<JSONObject> priceArray = new ArrayList<JSONObject>();
	protected ArrayList<JSONObject> destinationStation = new ArrayList<JSONObject>();
	protected ArrayList<JSONObject> fares = new ArrayList<JSONObject>();
	protected JSONArray jsonArray, jsonArrayFares;
	protected JSONObject obj;

	// 啟動ReadPrice
	public ReadPrice() {

		obj = JSONUtils.getJSONObjectFromFile("/price.json");
		JSONArray jsonArray = obj.getJSONArray("AllPrice");

		// 以"OriginStation所有資訊"為單位的arraylist
		for (int i = 0; i < jsonArray.length(); i++) {
			priceArray.add((JSONObject) jsonArray.get(i));
		}

		// input element:<OriginStation name (ID), Price Information>
		for (int i = 0; i < jsonArray.length(); i++) {
			DestinationStationsMap.put((String) originStationNumber[i], (JSONObject) priceArray.get(i));
		}
	}

	// return price
	/**
	 * Return the corresponding price
	 * @param originStation the departure station
	 * @param desrinationStations the arrival station
	 * @param ticketType the seat preference, Window, Aisle or NoPreference
	 * @return
	 */
	public int getprice(String originStation, String desrinationStations, String ticketType) {

		Station station = new Station();
		int ticketprice = 0;
		this.originStation = (String) station.getStationID(originStation);
		this.destinationStations = (String) station.getStationID(desrinationStations);
		this.ticketType = ticketType;

		JSONArray jsonArrayDesrination = DestinationStationsMap.get(this.originStation)
				.getJSONArray("DesrinationStations");

		for (int i = 0; i < jsonArrayDesrination.length(); i++) {
			destinationStation.add((JSONObject) jsonArrayDesrination.get(i));
		}

		for (int i = 0; i < jsonArrayDesrination.length(); i++) {
			FaresMap.put((String) destinationStation.get(i).get("ID"), (JSONObject) destinationStation.get(i));
		}

		for (int i = 0; i < jsonArrayDesrination.length(); i++) {
			if (destinationStation.get(i).get("ID").equals(this.destinationStations)) {
				jsonArrayFares = FaresMap.get(this.destinationStations).getJSONArray("Fares");
			}
		}

		for (int i = 0; i < jsonArrayFares.length(); i++) {
			fares.add((JSONObject) jsonArrayFares.get(i));
		}

		for (int i = 0; i < jsonArrayFares.length(); i++) {
			if (fares.get(i).get("TicketType").equals(this.ticketType)) {
				ticketprice = (int) fares.get(i).get("Price");
			}
		}

		return ticketprice;
	}

}
