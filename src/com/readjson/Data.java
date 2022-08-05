package com.readjson;

import org.json.*;

import com.tcg.json.JSONUtils;

public class Data {
	public TimeTable[] timeTables = new TimeTable[182];
	public UniversityDiscount[] unidiscount;
	public EarlyDiscount[] earlydiscount;
	public Data() {
		
		JSONObject obj = JSONUtils.getJSONObjectFromFile("/timeTable.json");
		
		JSONArray jsonArray = obj.getJSONArray("timeTable");
		for(int i = 0; i < jsonArray.length(); i++) {	
			timeTables[i] = new TimeTable();
			
			JSONObject obj1 = jsonArray.getJSONObject(i);
			
			timeTables[i].setTrainNo(obj1.getJSONObject("GeneralTimetable").getJSONObject("GeneralTrainInfo").getString("TrainNo"));
			timeTables[i].setDirection(obj1.getJSONObject("GeneralTimetable").getJSONObject("GeneralTrainInfo").getInt("Direction"));
			timeTables[i].setStartingStationID(obj1.getJSONObject("GeneralTimetable").getJSONObject("GeneralTrainInfo").getString("StartingStationID"));
			timeTables[i].setEndingStationNameEn(obj1.getJSONObject("GeneralTimetable").getJSONObject("GeneralTrainInfo").getJSONObject("EndingStationName").getString("En"));
			timeTables[i].setEndingStationID(obj1.getJSONObject("GeneralTimetable").getJSONObject("GeneralTrainInfo").getString("EndingStationID"));
			timeTables[i].setStartingStationNameEn(obj1.getJSONObject("GeneralTimetable").getJSONObject("GeneralTrainInfo").getJSONObject("StartingStationName").getString("En"));
			
			JSONArray stoparray = obj1.getJSONObject("GeneralTimetable").getJSONArray("StopTimes");

			timeTables[i].stops = new TimeTable.Stop[stoparray.length()];
			
			for(int j = 0; j < stoparray.length(); j++) {
				JSONObject obj2 = stoparray.getJSONObject(j);
				timeTables[i].stops[j]= timeTables[i].new Stop();
				timeTables[i].stops[j].setStopSequence(obj2.getInt("StopSequence"));
				timeTables[i].stops[j].setStationID(obj2.getString("StationID"));
				timeTables[i].stops[j].setEn(obj2.getJSONObject("StationName").getString("En"));
				timeTables[i].stops[j].setDepartureTime(obj2.getString("DepartureTime"));
//				System.out.println(stops[j].getEn());
//				System.out.println(stops[j].getDepartureTime());
			}
			
			timeTables[i].setWorkday(1,obj1.getJSONObject("GeneralTimetable").getJSONObject("ServiceDay").getInt("Monday"));
			timeTables[i].setWorkday(2,obj1.getJSONObject("GeneralTimetable").getJSONObject("ServiceDay").getInt("Tuesday"));
			timeTables[i].setWorkday(3,obj1.getJSONObject("GeneralTimetable").getJSONObject("ServiceDay").getInt("Wednesday"));
			timeTables[i].setWorkday(4,obj1.getJSONObject("GeneralTimetable").getJSONObject("ServiceDay").getInt("Thursday"));
			timeTables[i].setWorkday(5,obj1.getJSONObject("GeneralTimetable").getJSONObject("ServiceDay").getInt("Friday"));
			timeTables[i].setWorkday(6,obj1.getJSONObject("GeneralTimetable").getJSONObject("ServiceDay").getInt("Saturday"));
			timeTables[i].setWorkday(7,obj1.getJSONObject("GeneralTimetable").getJSONObject("ServiceDay").getInt("Sunday"));	
		}
	}

	public void uni() {
		JSONObject objuni = JSONUtils.getJSONObjectFromFile("/universityDiscount.json");
		JSONArray jsonArrayuni = objuni.getJSONArray("DiscountTrains");
		unidiscount = new UniversityDiscount[jsonArrayuni.length()];
		for(int i = 0; i < jsonArrayuni.length(); i++) {
			unidiscount[i] = new UniversityDiscount();
			//unidiscount[i].setTrainNo(jsonArrayuni.getString("TrainNo"));
			JSONObject obj2 = jsonArrayuni.getJSONObject(i);
			unidiscount[i].setTrainNo(obj2.getString("TrainNo"));
			unidiscount[i].setMonday(obj2.getJSONObject("ServiceDayDiscount").getDouble("Monday"));
			unidiscount[i].setTuesday(obj2.getJSONObject("ServiceDayDiscount").getDouble("Tuesday"));
			unidiscount[i].setWednesday(obj2.getJSONObject("ServiceDayDiscount").getDouble("Wednesday"));
			unidiscount[i].setThursday(obj2.getJSONObject("ServiceDayDiscount").getDouble("Thursday"));
			unidiscount[i].setFriday(obj2.getJSONObject("ServiceDayDiscount").getDouble("Friday"));
			unidiscount[i].setSaturday(obj2.getJSONObject("ServiceDayDiscount").getDouble("Saturday"));
			unidiscount[i].setSunday(obj2.getJSONObject("ServiceDayDiscount").getDouble("Sunday"));
			
//			System.out.println(unidiscount[i].getTrainNo());
//			System.out.println("Monday ="+unidiscount[i].getMonday());
//			System.out.println("Tuesday ="+unidiscount[i].getTuesday());
//			System.out.println("Wednesday ="+unidiscount[i].getWednesday());
//			System.out.println("Thursday ="+unidiscount[i].getThursday());
//			System.out.println("Friday ="+unidiscount[i].getFriday());
//			System.out.println("Saturday ="+unidiscount[i].getSaturday());
//			System.out.println("Sunday ="+unidiscount[i].getSunday());
//			System.out.println("");
		}
	}
	public void early() {
		JSONObject objearly = JSONUtils.getJSONObjectFromFile("/earlyDiscount.json");
		JSONArray jsonArrayearly = objearly.getJSONArray("DiscountTrains");
		earlydiscount = new EarlyDiscount[jsonArrayearly.length()];
		for(int i = 0; i < jsonArrayearly.length(); i++) { 
			earlydiscount[i] = new EarlyDiscount();
			JSONObject obj3 = jsonArrayearly.getJSONObject(i);
			earlydiscount[i].setTrainNo(obj3.getString("TrainNo"));
			//System.out.println("TrainNO = "+earlydiscount[i].getTrainNo());
			
			try {
			JSONArray jsonarraymonday = obj3.getJSONObject("ServiceDayDiscount").getJSONArray("Monday");	
				earlydiscount[i].mondays = new EarlyDiscount.Monday[jsonarraymonday.length()];
				for(int j=0; j< jsonarraymonday.length();j++) {
					JSONObject obj = jsonarraymonday.getJSONObject(j);
					earlydiscount[i].mondays[j] = earlydiscount[i].new Monday();
					earlydiscount[i].mondays[j].setDiscount(obj.getDouble("discount"));
					earlydiscount[i].mondays[j].setTickets(obj.getInt("tickets"));
					//earlydiscount[i].mondays[j].setHavediscount(true);
//					System.out.println("Monday:");
//					System.out.println("discount ="+earlydiscount[i].mondays[j].getDiscount());
//					System.out.println("tickets ="+earlydiscount[i].mondays[j].getTickets());
				}
			}catch(JSONException e){
				earlydiscount[i].mondays= new EarlyDiscount.Monday[1];
				earlydiscount[i].mondays[0] = earlydiscount[i].new Monday();
				int check = obj3.getJSONObject("ServiceDayDiscount").getInt("Monday");
				if(check ==1) {
					earlydiscount[i].mondays[0].setDiscount(0);
					earlydiscount[i].mondays[0].setTickets(0);
					earlydiscount[i].mondays[0].setHavediscount(false);
					earlydiscount[i].mondays[0].setHaveservice(true);
					//System.out.println("monday no discount");
				}else {
					earlydiscount[i].mondays[0].setDiscount(0);
					earlydiscount[i].mondays[0].setTickets(0);
					earlydiscount[i].mondays[0].setHavediscount(false);
					earlydiscount[i].mondays[0].setHaveservice(false);
					//System.out.println("monday no service");
				}			
			}
//			
//			//tuesday
			try {
			JSONArray jsonarraymonday = obj3.getJSONObject("ServiceDayDiscount").getJSONArray("Tuesday");
			if(jsonarraymonday.length()!=0) {
				earlydiscount[i].tuesdays = new EarlyDiscount.Tuesday[jsonarraymonday.length()];
				for(int j=0; j< jsonarraymonday.length();j++) {
					JSONObject obj = jsonarraymonday.getJSONObject(j);
					//stop1[j]= Timetables[i].new Stop();
					earlydiscount[i].tuesdays[j] = earlydiscount[i].new Tuesday();
					earlydiscount[i].tuesdays[j].setDiscount(obj.getDouble("discount"));
					earlydiscount[i].tuesdays[j].setTickets(obj.getInt("tickets"));
					//ckets ="+earlydiscount[i].tuesdays[j].getTickets());
				}
			}
			}catch(JSONException e){
				earlydiscount[i].tuesdays= new EarlyDiscount.Tuesday[1];
				earlydiscount[i].tuesdays[0] = earlydiscount[i].new Tuesday();
				int check = obj3.getJSONObject("ServiceDayDiscount").getInt("Tuesday");
				if(check ==1) {
					earlydiscount[i].tuesdays[0].setDiscount(0);
					earlydiscount[i].tuesdays[0].setTickets(0);
					earlydiscount[i].tuesdays[0].setHavediscount(false);
					earlydiscount[i].tuesdays[0].setHaveservice(true);
					//System.out.println("tuesday no discount");
				}else {
					earlydiscount[i].tuesdays[0].setDiscount(0);
					earlydiscount[i].tuesdays[0].setTickets(0);
					earlydiscount[i].tuesdays[0].setHavediscount(false);
					earlydiscount[i].tuesdays[0].setHaveservice(false);
					//System.out.println("tuesday no service");
				}			
			}
//			//wednesday
			try {
			JSONArray jsonarraymonday = obj3.getJSONObject("ServiceDayDiscount").getJSONArray("Wednesday");
			if(jsonarraymonday.length()!=0) {
				earlydiscount[i].wednesdays = new EarlyDiscount.Wednesday[jsonarraymonday.length()];
				for(int j=0; j< jsonarraymonday.length();j++) {
					JSONObject obj = jsonarraymonday.getJSONObject(j);
					//stop1[j]= Timetables[i].new Stop();
					earlydiscount[i].wednesdays[j] = earlydiscount[i].new Wednesday();
					earlydiscount[i].wednesdays[j].setDiscount(obj.getDouble("discount"));
					earlydiscount[i].wednesdays[j].setTickets(obj.getInt("tickets"));
//					System.out.println("Wednesday:");
//					System.out.println("discount ="+earlydiscount[i].wednesdays[j].getDiscount());
//					System.out.println("tickets ="+earlydiscount[i].wednesdays[j].getTickets());
				}
			}
			}catch(JSONException e){
				earlydiscount[i].wednesdays= new EarlyDiscount.Wednesday[1];
				earlydiscount[i].wednesdays[0] = earlydiscount[i].new Wednesday();
				int check = obj3.getJSONObject("ServiceDayDiscount").getInt("Wednesday");
				if(check ==1) {
					earlydiscount[i].wednesdays[0].setDiscount(0);
					earlydiscount[i].wednesdays[0].setTickets(0);
					earlydiscount[i].wednesdays[0].setHavediscount(false);
					earlydiscount[i].wednesdays[0].setHaveservice(true);
//					System.out.println("wednesday no discount");
				}else {
					earlydiscount[i].wednesdays[0].setDiscount(0);
					earlydiscount[i].wednesdays[0].setTickets(0);
					earlydiscount[i].wednesdays[0].setHavediscount(false);
					earlydiscount[i].wednesdays[0].setHaveservice(false);
//					System.out.println("wednesday no service");
				}			
			}
			//Thursday
			try {
			JSONArray jsonarraymonday = obj3.getJSONObject("ServiceDayDiscount").getJSONArray("Thursday");
			if(jsonarraymonday.length()!=0) {
				earlydiscount[i].thursdays = new EarlyDiscount.Thursday[jsonarraymonday.length()];
				for(int j=0; j< jsonarraymonday.length();j++) {
					JSONObject obj = jsonarraymonday.getJSONObject(j);
					//stop1[j]= Timetables[i].new Stop();
					earlydiscount[i].thursdays[j] = earlydiscount[i].new Thursday();
					earlydiscount[i].thursdays[j].setDiscount(obj.getDouble("discount"));
					earlydiscount[i].thursdays[j].setTickets(obj.getInt("tickets"));
//					System.out.println("Thursday:");
//					System.out.println("discount ="+earlydiscount[i].thursdays[j].getDiscount());
//					System.out.println("tickets ="+earlydiscount[i].thursdays[j].getTickets());
				}
			}
			}catch(JSONException e){
				earlydiscount[i].thursdays= new EarlyDiscount.Thursday[1];
				earlydiscount[i].thursdays[0] = earlydiscount[i].new Thursday();
				int check = obj3.getJSONObject("ServiceDayDiscount").getInt("Thursday");
				if(check ==1) {
					earlydiscount[i].thursdays[0].setDiscount(0);
					earlydiscount[i].thursdays[0].setTickets(0);
					earlydiscount[i].thursdays[0].setHavediscount(false);
					earlydiscount[i].thursdays[0].setHaveservice(true);
//					System.out.println("thursday no discount");
				}else {
					earlydiscount[i].thursdays[0].setDiscount(0);
					earlydiscount[i].thursdays[0].setTickets(0);
					earlydiscount[i].thursdays[0].setHavediscount(false);
					earlydiscount[i].thursdays[0].setHaveservice(false);
//					System.out.println("thursday no service");
				}			
			}
//			//Friday
			try {
			JSONArray jsonarraymonday = obj3.getJSONObject("ServiceDayDiscount").getJSONArray("Friday");
			if(jsonarraymonday.length()!=0) {
				earlydiscount[i].fridays = new EarlyDiscount.Friday[jsonarraymonday.length()];
				for(int j=0; j< jsonarraymonday.length();j++) {
					JSONObject obj = jsonarraymonday.getJSONObject(j);
					//stop1[j]= Timetables[i].new Stop();
					earlydiscount[i].fridays[j] = earlydiscount[i].new Friday();
					earlydiscount[i].fridays[j].setDiscount(obj.getDouble("discount"));
					earlydiscount[i].fridays[j].setTickets(obj.getInt("tickets"));
//					System.out.println("Friday:");
//					System.out.println("discount ="+earlydiscount[i].fridays[j].getDiscount());
//					System.out.println("tickets ="+earlydiscount[i].fridays[j].getTickets());
				}
			}
			}catch(JSONException e){
				earlydiscount[i].fridays= new EarlyDiscount.Friday[1];
				earlydiscount[i].fridays[0] = earlydiscount[i].new Friday();
				int check = obj3.getJSONObject("ServiceDayDiscount").getInt("Friday");
				if(check ==1) {
					earlydiscount[i].fridays[0].setDiscount(0);
					earlydiscount[i].fridays[0].setTickets(0);
					earlydiscount[i].fridays[0].setHavediscount(false);
					earlydiscount[i].fridays[0].setHaveservice(true);
//					System.out.println("Friday no discount");
				}else {
					earlydiscount[i].fridays[0].setDiscount(0);
					earlydiscount[i].fridays[0].setTickets(0);
					earlydiscount[i].fridays[0].setHavediscount(false);
					earlydiscount[i].fridays[0].setHaveservice(false);
//					System.out.println("Friday no service");
				}			
			}
//			//Saturday
			try {
			JSONArray jsonarraymonday = obj3.getJSONObject("ServiceDayDiscount").getJSONArray("Saturday");
			if(jsonarraymonday.length()!=0) {
				earlydiscount[i].saturdays = new EarlyDiscount.Saturday[jsonarraymonday.length()];
				for(int j=0; j< jsonarraymonday.length();j++) {
					JSONObject obj = jsonarraymonday.getJSONObject(j);
					//stop1[j]= Timetables[i].new Stop();
					earlydiscount[i].saturdays[j] = earlydiscount[i].new Saturday();
					earlydiscount[i].saturdays[j].setDiscount(obj.getDouble("discount"));
					earlydiscount[i].saturdays[j].setTickets(obj.getInt("tickets"));
//					System.out.println("Saturday:");
//					System.out.println("discount ="+earlydiscount[i].saturdays[j].getDiscount());
//					System.out.println("tickets ="+earlydiscount[i].saturdays[j].getTickets());
				}
			}
			}catch(JSONException e){
				earlydiscount[i].saturdays= new EarlyDiscount.Saturday[1];
				earlydiscount[i].saturdays[0] = earlydiscount[i].new Saturday();
				int check = obj3.getJSONObject("ServiceDayDiscount").getInt("Saturday");
				if(check ==1) {
					earlydiscount[i].saturdays[0].setDiscount(0);
					earlydiscount[i].saturdays[0].setTickets(0);
					earlydiscount[i].saturdays[0].setHavediscount(false);
					earlydiscount[i].saturdays[0].setHaveservice(true);
//					System.out.println("Saturday no discount");
				}else {
					earlydiscount[i].saturdays[0].setDiscount(0);
					earlydiscount[i].saturdays[0].setTickets(0);
					earlydiscount[i].saturdays[0].setHavediscount(false);
					earlydiscount[i].saturdays[0].setHaveservice(false);
//					System.out.println("Saturday no service");
				}			
			}
//			//Sunday
			try {
				JSONArray jsonarraymonday = obj3.getJSONObject("ServiceDayDiscount").getJSONArray("Sunday");
				if(jsonarraymonday.length()!=0) {
					earlydiscount[i].sundays = new EarlyDiscount.Sunday[jsonarraymonday.length()];
					for(int j=0; j< jsonarraymonday.length();j++) {
						JSONObject obj = jsonarraymonday.getJSONObject(j);
						//stop1[j]= Timetables[i].new Stop();
						earlydiscount[i].sundays[j] = earlydiscount[i].new Sunday();
						earlydiscount[i].sundays[j].setDiscount(obj.getDouble("discount"));
						earlydiscount[i].sundays[j].setTickets(obj.getInt("tickets"));
//						System.out.println("Sunday:");
//						System.out.println("discount ="+earlydiscount[i].sundays[j].getDiscount());
//						System.out.println("tickets ="+earlydiscount[i].sundays[j].getTickets());
					}
				}
				}catch(JSONException e){
					earlydiscount[i].sundays= new EarlyDiscount.Sunday[1];
					earlydiscount[i].sundays[0] = earlydiscount[i].new Sunday();
					int check = obj3.getJSONObject("ServiceDayDiscount").getInt("Sunday");
					if(check ==1) {
						earlydiscount[i].sundays[0].setDiscount(0);
						earlydiscount[i].sundays[0].setTickets(0);
						earlydiscount[i].sundays[0].setHavediscount(false);
						earlydiscount[i].sundays[0].setHaveservice(true);
//						System.out.println("Sunday no discount");
					}else {
						earlydiscount[i].sundays[0].setDiscount(0);
						earlydiscount[i].sundays[0].setTickets(0);
						earlydiscount[i].sundays[0].setHavediscount(false);
						earlydiscount[i].sundays[0].setHaveservice(false);
//						System.out.println("Sunday no service");
					}			
				}
		
	}
}
}