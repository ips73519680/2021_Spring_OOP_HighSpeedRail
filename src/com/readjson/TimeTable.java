package com.readjson;

import java.util.*;

public class TimeTable {
	
	public TimeTable() { 
	
	}
		private String  TrainNo;
		private int  Direction;
		private String  StartingStationID;		
		private String EndingStationNameEn ;			
		private String  EndingStationID;
		private String StartingStationNameEn;
		public Stop[] stops; 	
	
		public String getEndingStationNameEn() {
			return EndingStationNameEn;
		}
		public void setEndingStationNameEn(String endingStationNameEn) {
			EndingStationNameEn = endingStationNameEn;
		}
		public String getTrainNo() {
			return TrainNo;
		}
		public void setTrainNo(String trainNo) {			
			TrainNo = trainNo;
		
		}
		public int getDirection() {
			return Direction;
		}
		public void setDirection(int direction) {
			Direction = direction;
		}
		public String getStartingStationID() {
			return StartingStationID;
		}
		public void setStartingStationID(String startingStationID) {
			StartingStationID = startingStationID;
		}
		public String getEndingStationID() {
			return EndingStationID;
		}
		public void setEndingStationID(String endingStationID) {
			EndingStationID = endingStationID;
		}

		public String getStartingStationNameEn() {
			return StartingStationNameEn;
		}
		public void setStartingStationNameEn(String startingStationNameEn) {
			StartingStationNameEn = startingStationNameEn;
		}
			
		public class Stop{
			private String En ; // name of stop
			private String DepartureTime;
			private int StopSequence;  //
			private String StationID;  //number of stop
			public int getStopSequence() {
				return StopSequence;
			}
			public void setStopSequence(int stopSequence) {
				StopSequence = stopSequence;
			}
			public String getStationID() {
				return StationID;
			}
			public void setStationID(String stationID) {
				StationID = stationID;
			}
			public String getDepartureTime() {
				return DepartureTime;
			}
			public void setDepartureTime(String departureTime) {
				DepartureTime = departureTime;
			}
			
			
			
			public String getEn() {
				return En;
			}
			public void setEn(String en) {
				En = en;
			}

		}
		
	
		private int[] Workday = new int[8];
		public int getWorkday(int day) {
			return Workday[day];
		}
		public void setWorkday(int day,int vaild) {
			Workday[day]=vaild;
	}

}