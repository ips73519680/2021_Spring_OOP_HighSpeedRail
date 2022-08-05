package com.readjson;

import java.util.HashMap;
import java.util.Map;

import com.exception.PriceException;
import com.system.Booking;

import java.util.Calendar;

public class Price {

	public Price() {

	}

	// date to day of the week
	public String date2Day(String date) {

		String[] DateArray = date.split("-");
		int day = Integer.parseInt(DateArray[2]);
		int month = Integer.parseInt(DateArray[1]);
		int year = Integer.parseInt(DateArray[0]);
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, day);
		String[] days = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
		int n;
		n = c.get(Calendar.DAY_OF_WEEK);
		return days[n - 1];
	}

	// 布基琩高╰参⊿Τ耞布琌(瞶)讽ぃ瞶璹布(讽ぱ礚厩ネ纔磃)肚穦=0
	// return a ticket price
	public int price(String trainNo, String start, String end, String date, Seat.Class car, int bookingnumber,
			boolean adult, boolean college) throws PriceException {
		int ticketprice = 0;
		String type = null;
		ReadPrice price = new ReadPrice();
		Map<String, Double> WeekMap = new HashMap<>();
		Data data = new Data();
		String week = date2Day(date);

		// type : Business
		if (car.equals(Seat.Class.Business)) {
			ticketprice = price.getprice(start, end, "business");
			return ticketprice;
		}

		// type : college (0.5, 0.75, 0.88)
		if (college == true) {
			
			boolean collegeensure = false;
			data.uni();

			for (int i = 0; i < data.unidiscount.length; i++) {
				if (trainNo.equals(data.unidiscount[i].getTrainNo())) {
					
					collegeensure = true;
					WeekMap.put("Monday", data.unidiscount[i].getMonday());
					WeekMap.put("Tuesday", data.unidiscount[i].getTuesday());
					WeekMap.put("Wednesday", data.unidiscount[i].getWednesday());
					WeekMap.put("Thursday", data.unidiscount[i].getThursday());
					WeekMap.put("Friday", data.unidiscount[i].getFriday());
					WeekMap.put("Saturday", data.unidiscount[i].getSaturday());
					WeekMap.put("Sunday", data.unidiscount[i].getSunday());
					break;

				}
			}
			if(collegeensure == false) {
				ticketprice = price.getprice(start, end, "standard");
				System.out.println("No university discount.");
				return ticketprice;
			}
		

			type = Double.toString(WeekMap.get(week));

			// if no university discount, type = "standard" (no discount)
			try {
				ticketprice = price.getprice(start, end, type);

				if (ticketprice == 0) {
					type = "standard";
					throw new PriceException("No train.");
				}
				if (type == "1") {
					throw new PriceException("No university discount.");
				}

			} catch (PriceException e) {
				System.out.println(e.getMessage());
			}

		}

		// type : adult(0.65, 0.8, 0.9)
		else if (adult == true) {
			data.early();
			int i;
			boolean earlyensure = false;

			for (i = 0; i < data.earlydiscount.length; i++) {
				if (trainNo.equals(data.earlydiscount[i].getTrainNo())) {
					earlyensure = true;
					break;
				}
				if (i == (data.earlydiscount.length - 1) && earlyensure == false) {
					ticketprice = price.getprice(start, end, "standard");
					return ticketprice;
				}
			}

			if (week.equals("Monday")) {
				for (int j = 0; j < data.earlydiscount[i].mondays.length; j++) {

					if (data.earlydiscount[i].mondays.length == 1) {
						type = "standard";
					} else {
						if (bookingnumber <= data.earlydiscount[i].mondays[j].getTickets()) {
							type = Double.toString(data.earlydiscount[i].mondays[j].getDiscount());
							break;
						} else if ((j + 1) == data.earlydiscount[i].mondays.length) {
							type = "standard";
							break;
						} else {
							bookingnumber = bookingnumber - data.earlydiscount[i].mondays[j].getTickets();
						}
					}
				}
			}

			if (week.equals("Tuesday")) {
				for (int j = 0; j < data.earlydiscount[i].tuesdays.length; j++) {

					if (data.earlydiscount[i].tuesdays.length == 1) {
						type = "standard";
					} else {
						if (bookingnumber <= data.earlydiscount[i].tuesdays[j].getTickets()) {
							type = Double.toString(data.earlydiscount[i].tuesdays[j].getDiscount());
							break;
						} else if ((j + 1) == data.earlydiscount[i].tuesdays.length) {
							type = "standard";
							break;
						} else {
							bookingnumber = bookingnumber - data.earlydiscount[i].tuesdays[j].getTickets();
						}
					}
				}
			}

			if (week.equals("Wednesday")) {
				for (int j = 0; j < data.earlydiscount[i].wednesdays.length; j++) {

					if (data.earlydiscount[i].wednesdays.length == 1) {
						type = "standard";
					} else {
						if (bookingnumber <= data.earlydiscount[i].wednesdays[j].getTickets()) {
							type = Double.toString(data.earlydiscount[i].wednesdays[j].getDiscount());
							break;
						} else if ((j + 1) == data.earlydiscount[i].wednesdays.length) {
							type = "standard";
							break;
						} else {
							bookingnumber = bookingnumber - data.earlydiscount[i].wednesdays[j].getTickets();
						}
					}
				}
			}

			if (week.equals("Thursday")) {
				for (int j = 0; j < data.earlydiscount[i].thursdays.length; j++) {

					if (data.earlydiscount[i].thursdays.length == 1) {
						type = "standard";
					} else {
						if (bookingnumber <= data.earlydiscount[i].thursdays[j].getTickets()) {
							type = Double.toString(data.earlydiscount[i].thursdays[j].getDiscount());
							break;
						} else if ((j + 1) == data.earlydiscount[i].thursdays.length) {
							type = "standard";
							break;
						} else {
							bookingnumber = bookingnumber - data.earlydiscount[i].thursdays[j].getTickets();
						}
					}
				}
			}

			if (week.equals("Friday")) {
				for (int j = 0; j < data.earlydiscount[i].fridays.length; j++) {

					if (data.earlydiscount[i].fridays.length == 1) {
						type = "standard";
					} else {
						if (bookingnumber <= data.earlydiscount[i].fridays[j].getTickets()) {
							type = Double.toString(data.earlydiscount[i].fridays[j].getDiscount());
							break;
						} else if ((j + 1) == data.earlydiscount[i].fridays.length) {
							type = "standard";
							break;
						} else {
							bookingnumber = bookingnumber - data.earlydiscount[i].fridays[j].getTickets();
						}
					}
				}
			}

			if (week.equals("Saturday")) {
				for (int j = 0; j < data.earlydiscount[i].saturdays.length; j++) {

					if (data.earlydiscount[i].saturdays.length == 1) {
						type = "standard";
					} else {
						if (bookingnumber <= data.earlydiscount[i].saturdays[j].getTickets()) {
							type = Double.toString(data.earlydiscount[i].saturdays[j].getDiscount());
							break;
						} else if ((j + 1) == data.earlydiscount[i].saturdays.length) {
							type = "standard";
							break;
						} else {
							bookingnumber = bookingnumber - data.earlydiscount[i].saturdays[j].getTickets();
						}
					}
				}
			}

			if (week.equals("Sunday")) {
				for (int j = 0; j < data.earlydiscount[i].sundays.length; j++) {

					if (data.earlydiscount[i].sundays.length == 1) {
						type = "standard";

					} else {
						if (bookingnumber <= data.earlydiscount[i].sundays[j].getTickets()) {
							type = Double.toString(data.earlydiscount[i].sundays[j].getDiscount());
							break;
						} else if ((j + 1) == data.earlydiscount[i].sundays.length) {
							type = "standard";
							break;
						} else {
							bookingnumber = bookingnumber - data.earlydiscount[i].sundays[j].getTickets();
						}
					}
				}
			}

		}

		ticketprice = price.getprice(start, end, type);
		return ticketprice;

	}

	// 琩高琘óΩ琘ぱ厩ネ纔磃
	public String getCollegeType(String trainNo, String date) {

		String type = null;
		ReadPrice price = new ReadPrice();
		Map<String, Double> WeekMap = new HashMap<>();
		Data data = new Data();
		String week = date2Day(date);

		data.uni();

		for (int i = 0; i < data.unidiscount.length; i++)
			if (trainNo.equals(data.unidiscount[i].getTrainNo())) {

				WeekMap.put("Monday", data.unidiscount[i].getMonday());
				WeekMap.put("Tuesday", data.unidiscount[i].getTuesday());
				WeekMap.put("Wednesday", data.unidiscount[i].getWednesday());
				WeekMap.put("Thursday", data.unidiscount[i].getThursday());
				WeekMap.put("Friday", data.unidiscount[i].getFriday());
				WeekMap.put("Saturday", data.unidiscount[i].getSaturday());
				WeekMap.put("Sunday", data.unidiscount[i].getSunday());
				break;

			}

		type = Double.toString(WeekMap.get(week));

		return type;
	}

	// only矗ㄑ倒getEarlyType(String trainNo, String date)ㄏノ
	// 猔種程沧埃ず甧!!
	public int getBookingNumber(String trainNo, String date) {
		Seat train;
		String dateString = "No" + trainNo + "_" + date;
//		// 程沧璶簿埃 new, ぃ礛璹计盢ッ环琌箂
//		Booking booking = new Booking();
		return Booking.SoldNumber(dateString);
	}

	// 琩高琘óΩ琘ぱヘ玡Ν尘纔磃
	public String getEarlyType(String trainNo, String date) {

		String type = null;
		Data data = new Data();
		String week = date2Day(date);
		int bookingnumber = getBookingNumber(trainNo, date);

		data.early();
		int i;
		for (i = 0; i < data.earlydiscount.length; i++) {
			if (trainNo.equals(data.earlydiscount[i].getTrainNo())) {

				break;
			}
		}

		if (week.equals("Monday")) {
			for (int j = 0; j < data.earlydiscount[i].mondays.length; j++) {

				if (data.earlydiscount[i].mondays.length == 1) {
					type = "standard";
				} else {
					if (bookingnumber <= data.earlydiscount[i].mondays[j].getTickets()) {
						type = Double.toString(data.earlydiscount[i].mondays[j].getDiscount());
						break;
					} else if ((j + 1) == data.earlydiscount[i].mondays.length) {
						type = "standard";
						break;
					} else {
						bookingnumber = bookingnumber - data.earlydiscount[i].mondays[j].getTickets();
					}
				}
			}
		}

		if (week.equals("Tuesday")) {
			for (int j = 0; j < data.earlydiscount[i].tuesdays.length; j++) {

				if (data.earlydiscount[i].tuesdays.length == 1) {
					type = "standard";
				} else {
					if (bookingnumber <= data.earlydiscount[i].tuesdays[j].getTickets()) {
						type = Double.toString(data.earlydiscount[i].tuesdays[j].getDiscount());
						break;
					} else if ((j + 1) == data.earlydiscount[i].tuesdays.length) {
						type = "standard";
						break;
					} else {
						bookingnumber = bookingnumber - data.earlydiscount[i].tuesdays[j].getTickets();
					}
				}
			}
		}

		if (week.equals("Wednesday")) {
			for (int j = 0; j < data.earlydiscount[i].wednesdays.length; j++) {

				if (data.earlydiscount[i].wednesdays.length == 1) {
					type = "standard";
				} else {
					if (bookingnumber <= data.earlydiscount[i].wednesdays[j].getTickets()) {
						type = Double.toString(data.earlydiscount[i].wednesdays[j].getDiscount());
						break;
					} else if ((j + 1) == data.earlydiscount[i].wednesdays.length) {
						type = "standard";
						break;
					} else {
						bookingnumber = bookingnumber - data.earlydiscount[i].wednesdays[j].getTickets();
					}
				}
			}
		}

		if (week.equals("Thursday")) {
			for (int j = 0; j < data.earlydiscount[i].thursdays.length; j++) {

				if (data.earlydiscount[i].thursdays.length == 1) {
					type = "standard";
				} else {
					if (bookingnumber <= data.earlydiscount[i].thursdays[j].getTickets()) {
						type = Double.toString(data.earlydiscount[i].thursdays[j].getDiscount());
						break;
					} else if ((j + 1) == data.earlydiscount[i].thursdays.length) {
						type = "standard";
						break;
					} else {
						bookingnumber = bookingnumber - data.earlydiscount[i].thursdays[j].getTickets();
					}
				}
			}
		}

		if (week.equals("Friday")) {
			for (int j = 0; j < data.earlydiscount[i].fridays.length; j++) {

				if (data.earlydiscount[i].fridays.length == 1) {
					type = "standard";
				} else {
					if (bookingnumber <= data.earlydiscount[i].fridays[j].getTickets()) {
						type = Double.toString(data.earlydiscount[i].fridays[j].getDiscount());
						break;
					} else if ((j + 1) == data.earlydiscount[i].fridays.length) {
						type = "standard";
						break;
					} else {
						bookingnumber = bookingnumber - data.earlydiscount[i].fridays[j].getTickets();
					}
				}
			}
		}

		if (week.equals("Saturday")) {
			for (int j = 0; j < data.earlydiscount[i].saturdays.length; j++) {

				if (data.earlydiscount[i].saturdays.length == 1) {
					type = "standard";
				} else {
					if (bookingnumber <= data.earlydiscount[i].saturdays[j].getTickets()) {
						type = Double.toString(data.earlydiscount[i].saturdays[j].getDiscount());
						break;
					} else if ((j + 1) == data.earlydiscount[i].saturdays.length) {
						type = "standard";
						break;
					} else {
						bookingnumber = bookingnumber - data.earlydiscount[i].saturdays[j].getTickets();
					}
				}
			}
		}

		if (week.equals("Sunday")) {
			for (int j = 0; j < data.earlydiscount[i].sundays.length; j++) {

				if (data.earlydiscount[i].sundays.length == 1) {
					type = "standard";

				} else {
					if (bookingnumber <= data.earlydiscount[i].sundays[j].getTickets()) {
						type = Double.toString(data.earlydiscount[i].sundays[j].getDiscount());
						break;
					} else if ((j + 1) == data.earlydiscount[i].sundays.length) {
						type = "standard";
						break;
					} else {
						bookingnumber = bookingnumber - data.earlydiscount[i].sundays[j].getTickets();
					}
				}
			}
		}

		return type;
	}
}
