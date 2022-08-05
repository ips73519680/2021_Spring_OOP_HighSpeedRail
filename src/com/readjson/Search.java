package com.readjson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.exception.TimeException;

/**
 * dateToWeek 的例外處理從try-catch改成throws
 * @author ChienJu
 *
 */
public class Search {
	private String start;
	private String end;
	private Data data = new Data();
	private List<train> temp = new ArrayList<>();
	private List<train> early = new ArrayList<>();
	private List<train> uni = new ArrayList<>();
	private HashMap<String, Integer> stopRank = new HashMap<>();
	private static Station station = new Station();

	public Search() {
		stopRank.put("Nangang", 0);
		stopRank.put("Taipei", 1);
		stopRank.put("Banciao", 2);
		stopRank.put("Taoyuan", 3);
		stopRank.put("Hsinchu", 4);
		stopRank.put("Miaoli", 5);
		stopRank.put("Taichung", 6);
		stopRank.put("Changhua", 7);
		stopRank.put("Yunlin", 8);
		stopRank.put("Chiayi", 9);
		stopRank.put("Tainan", 10);
		stopRank.put("Zuoying", 11);
	}

	// print the whole day timeTables
	public Search(String date) throws ParseException {
		int day = dateToWeek(date);
		for (int i = 0; i < data.timeTables.length; ++i) {
			if (data.timeTables[i].getWorkday(day) == 1) {
				System.out.println("車次: " + data.timeTables[i].getTrainNo());
				for (int j = 0; j < data.timeTables[i].stops.length; ++j) {
					System.out.println(station.getChStationName(data.timeTables[i].stops[j].getEn()) + ": " + data.timeTables[i].stops[j].getDepartureTime());
				}
				System.out.println("----------");
			}
		}
	}

	public class train {
		private String TrainNo;
		private double earlyDiscount;
		private double uniDiscount;
		private String DepTime;
		private String ArrTime;
		private int departureTime;
	}

	// Depart Time Search
	public HashMap<String, String> DtimeSearch(String TrainNO) {
		HashMap<String, String> stop_dtime = new HashMap<>();
		for (int i = 0; i < data.timeTables.length; ++i) {
			if (!data.timeTables[i].getTrainNo().equals(TrainNO))
				continue;
			for (TimeTable.Stop st : data.timeTables[i].stops) {
				String stop = st.getEn();
				String dtime = st.getDepartureTime();
				stop_dtime.put(stop, dtime);
			}
		}
		return stop_dtime;
	}

	private int StringtoInt(String time) {
		int res = 0;
		for (int i = 0; i < time.length(); ++i) {
			if (time.charAt(i) == ':')
				continue;
			res = res * 10 + time.charAt(i) - '0';
		}
		return res;
	}

	// generalSearch
	public void generalSearch(String date, String start, String end, String time) throws ParseException, TimeException {
		int day = dateToWeek(date);
		this.start = start;
		this.end = end;
		if(!isTimeValid(StringtoInt(time))) {
			throw new TimeException();
		}
		int dir = stopRank.get(start) < stopRank.get(end) ? 0 : 1;
		// find all candidates
		for (int i = 0; i < data.timeTables.length; ++i) {
			if (data.timeTables[i].getWorkday(day) == 1 && dir == data.timeTables[i].getDirection()) {
				int idxA = -1;
				int idxB = -1;
				for (int j = 0; j < data.timeTables[i].stops.length; ++j) {
					if (data.timeTables[i].stops[j].getEn().equals(start)) {
						idxA = j;
					}
					if (data.timeTables[i].stops[j].getEn().equals(end)) {
						idxB = j;
					}
					if (idxB != -1 && idxA != -1) {
						train t = new train();
						t.TrainNo = data.timeTables[i].getTrainNo();
						t.DepTime = data.timeTables[i].stops[idxA].getDepartureTime();
						t.ArrTime = data.timeTables[i].stops[idxB].getDepartureTime();

//						int dtime = 0;
//						for(int k = 0; k < t.DepTime.length(); ++k) {
//							if(t.DepTime.charAt(k) == ':') continue;
//							dtime = dtime * 10 + t.DepTime.charAt(k) - '0';
//						}
						t.departureTime = StringtoInt(t.DepTime);
						if (StringtoInt(t.DepTime) >= StringtoInt(time))
							temp.add(t);
						break;
					}
				}
			}
		}
		// sorting
		Comparator<train> Comp = new Comparator<train>() {
			@Override
			public int compare(train t1, train t2) {
				return t1.departureTime - t2.departureTime;
				// return t1.DepTime.compareTo(t2.DepTime);
			}
		};
		Collections.sort(temp, Comp);
	}

	// conditional search for uni
	public void uniSearch(String date, String start, String end, String time) throws ParseException, TimeException {
		int day = dateToWeek(date);
		generalSearch(date, start, end, time);
		data.uni();
		for (int i = 0; i < temp.size(); ++i) {
			for (int j = 0; j < data.unidiscount.length; ++j) {
				if (!temp.get(i).TrainNo.equals(data.unidiscount[j].getTrainNo()))
					continue;
				switch (day) {
				case 1:
					if (data.unidiscount[j].getMonday() < 1) {
						temp.get(i).uniDiscount = data.unidiscount[j].getMonday();
						uni.add(temp.get(i));
					}
					break;
				case 2:
					if (data.unidiscount[j].getTuesday() < 1) {
						temp.get(i).uniDiscount = data.unidiscount[j].getTuesday();
						uni.add(temp.get(i));
					}
					break;
				case 3:
					if (data.unidiscount[j].getWednesday() < 1) {
						temp.get(i).uniDiscount = data.unidiscount[j].getWednesday();
						uni.add(temp.get(i));
					}
					break;

				case 4:
					if (data.unidiscount[j].getThursday() < 1) {
						temp.get(i).uniDiscount = data.unidiscount[j].getThursday();
						uni.add(temp.get(i));
					}
					break;
				case 5:
					if (data.unidiscount[j].getFriday() < 1) {
						temp.get(i).uniDiscount = data.unidiscount[j].getFriday();
						uni.add(temp.get(i));
					}
					break;
				case 6:
					if (data.unidiscount[j].getSaturday() < 1) {
						temp.get(i).uniDiscount = data.unidiscount[j].getSaturday();
						uni.add(temp.get(i));
					}
					break;
				case 7:
					if (data.unidiscount[j].getSunday() < 1) {
						temp.get(i).uniDiscount = data.unidiscount[j].getSunday();
						uni.add(temp.get(i));
					}
					break;
				}
			}
		}
	}

	// conditional search for early
	public void earlySearch(String date, String start, String end, String time) throws ParseException, TimeException {
		generalSearch(date, start, end, time);
		int day = dateToWeek(date);
		data.early();
		for (int i = 0; i < temp.size(); ++i) {
			for (int j = 0; j < data.earlydiscount.length; ++j) {
				if (!temp.get(i).TrainNo.equals(data.earlydiscount[j].getTrainNo()))
					continue;

				switch (day) {
				case 1:
					for (int k = 0; k < data.earlydiscount[j].mondays.length; ++k) {
						if (data.earlydiscount[j].mondays[k].isHavediscount() == true) {
							temp.get(i).earlyDiscount = data.earlydiscount[j].mondays[k].getDiscount();
							early.add(temp.get(i));
							break;
						}
					}
					break;
				case 2:
					for (int k = 0; k < data.earlydiscount[j].tuesdays.length; ++k) {
						if (data.earlydiscount[j].tuesdays[k].isHavediscount() == true) {
							temp.get(i).earlyDiscount = data.earlydiscount[j].tuesdays[k].getDiscount();
							early.add(temp.get(i));
							break;
						}
					}
					break;
				case 3:
					for (int k = 0; k < data.earlydiscount[j].wednesdays.length; ++k) {
						if (data.earlydiscount[j].wednesdays[k].isHavediscount() == true) {
							temp.get(i).earlyDiscount = data.earlydiscount[j].wednesdays[k].getDiscount();
							early.add(temp.get(i));
							break;
						}
					}
					break;

				case 4:
					for (int k = 0; k < data.earlydiscount[j].thursdays.length; ++k) {
						if (data.earlydiscount[j].thursdays[k].isHavediscount() == true) {
							temp.get(i).earlyDiscount = data.earlydiscount[j].thursdays[k].getDiscount();
							early.add(temp.get(i));
							break;
						}
					}
					break;
				case 5:
					for (int k = 0; k < data.earlydiscount[j].fridays.length; ++k) {
						if (data.earlydiscount[j].fridays[k].isHavediscount() == true) {
							temp.get(i).earlyDiscount = data.earlydiscount[j].fridays[k].getDiscount();
							early.add(temp.get(i));
							break;
						}
					}
					break;
				case 6:
					for (int k = 0; k < data.earlydiscount[j].saturdays.length; ++k) {
						if (data.earlydiscount[j].saturdays[k].isHavediscount() == true) {
							temp.get(i).earlyDiscount = data.earlydiscount[j].saturdays[k].getDiscount();
							early.add(temp.get(i));
							break;
						}
					}
					break;
				case 7:
					for (int k = 0; k < data.earlydiscount[j].sundays.length; ++k) {
						if (data.earlydiscount[j].sundays[k].isHavediscount() == true) {
							temp.get(i).earlyDiscount = data.earlydiscount[j].sundays[k].getDiscount();
							early.add(temp.get(i));
							break;
						}
					}
					break;
				}
			}
		}
	}

	public void backAndForthSearchAndPrint(String date, String start, String end, String time) throws ParseException, TimeException {
		System.out.println("---南下---");
		generalSearch(date, start, end, time);
		print();
		System.out.println("---北上---");
		generalSearch(date, end, start, time);
		print();
	}

	public void print() {
		for (int i = 0; i < Math.min(5, temp.size()); i++) {
			System.out.println("車次: " + temp.get(i).TrainNo);
			System.out.println(station.getChStationName(start) + " " + temp.get(i).DepTime + " -> " + station.getChStationName(end) + " " + temp.get(i).ArrTime);
			System.out.println();
		}
		temp.clear();
	}

	public void printuni() {
		for (int i = 0; i < Math.min(5, uni.size()); i++) {
			System.out.println("車次: " + uni.get(i).TrainNo);
			System.out.println(station.getChStationName(start) + " " + uni.get(i).DepTime + " -> " + station.getChStationName(end) + " " + uni.get(i).ArrTime);
			System.out.println("大學生優惠 : " + (100 - (int) (100 * uni.get(i).uniDiscount)) + "% OFF");
			System.out.println();
		}
		temp.clear();
		uni.clear();
	}

	public void printearly() {
		for (int i = 0; i < Math.min(5, early.size()); i++) {
			System.out.println("車次: " + early.get(i).TrainNo);
			System.out.println(station.getChStationName(start) + " " + early.get(i).DepTime + " -> " + station.getChStationName(end) + " " + early.get(i).ArrTime);
			System.out.println("早鳥優惠 : " + (100 - (int) (100 * early.get(i).earlyDiscount)) + "% OFF");
			System.out.println();
		}
		temp.clear();
		early.clear();
	}

	private static int dateToWeek(String datetime) throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		int[] weekDays = { 7, 1, 2, 3, 4, 5, 6 };
		Calendar cal = Calendar.getInstance();
		cal.setLenient(false);
		f.setLenient(false);
		Date datet = f.parse(datetime);
		cal.setTime(datet);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
//		if (w < 0) {
//			w = 0;
//		}
		return weekDays[w];
	}
	
	private static boolean isTimeValid(int time) {
		String hour = Integer.toString(time).substring(0, 2);
		String minute = Integer.toString(time).substring(2, 4);
		if(Integer.valueOf(hour) < 24) {
			if(Integer.valueOf(minute) < 60) {
				return true;
			}else {
				return false;
			}
		}else if(Integer.valueOf(hour) == 24) {
			if(Integer.valueOf(minute) == 0) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

	public void earlySearchAndPrint(String date, String start, String end, String time) throws ParseException, TimeException {
		earlySearch(date, start, end, time);
		printearly();
	}

	public void uniSearchAndPrint(String date, String start, String end, String time) throws ParseException, TimeException {
		uniSearch(date, start, end, time);
		printuni();
	}

	public void generalSearchAndPrint(String date, String start, String end, String time) throws ParseException, TimeException {
		generalSearch(date, start, end, time);
		print();
	}
}
