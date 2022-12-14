package com.system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.exception.BookingException;
import com.exception.ClassException;
import com.exception.DateException;
import com.exception.LeftSeatException;
import com.exception.NoOfTicketException;
import com.exception.OrderInfoException;
import com.exception.OrderNoException;
import com.exception.PrefException;
import com.exception.PriceException;
import com.exception.ReviseException;
import com.exception.StationNameException;
import com.exception.TimeException;
import com.exception.UserException;
import com.readjson.Search;
import com.readjson.Seat;
import com.readjson.Station;

public class Main {
	private static Station station = new Station();
	private static Search search = new Search();
	private Booking booking = new Booking();
	protected ArrayList<User> users = new ArrayList<User>();

	/**
	 * Constructor of Main
	 */
	public Main() {

	}

	/**
	 * Find & output the booking info if the input order. If the order No. is not
	 * exist, throw an OrderNoException.
	 * 
	 * @param user    the userID
	 * @param orderNo the order No. that be found
	 * @throws OrderNoException
	 * @throws UserException
	 */
	public void findBookingInfo(String user, String orderNo) throws OrderNoException, UserException {

		BookingInfo info;
		boolean userFlag = false;
		int index = 0; // make compiler happy
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserID().equals(user)) {
				userFlag = true;
				index = i;
				break;
			}
		}
		if (userFlag) {
			boolean orderFlag = false;

			for (int i = 0; i < users.get(index).getLengthOfOrder(); i++) {
				if (users.get(index).getOrderNo(i).equals(orderNo)) {
					orderFlag = true;
					break;
				}
			}

			if (orderFlag) {
				info = Booking.bookingInfo.get(orderNo);

				if (info.getDate2() == null) { // one way trip
					String departureTime = info.getDepartureTime1();
					String arrivalTime = info.getArrivalTime1();

					if (info.getSeats1() == null) { // single seat one way trip
						// print train No. & date & departure & arrival time
						System.out.println("????: " + info.getDate1() + "\n" + "???H??: " + info.getAdult1() + "\n"
								+ "?j??????: " + info.getCollege1() + "\n" + "????: " + info.getTrainNo1() + "\n"
								+ station.getChStationName(info.getStart1()) + " " + departureTime + " -> "
								+ station.getChStationName(info.getEnd1()) + " " + arrivalTime);
						// print trip duration
						if (Integer.valueOf(arrivalTime.substring(3, 5)) >= Integer
								.valueOf(departureTime.substring(3, 5))) {
							System.out.printf("????????: %d?p?? %d????\n",
									Integer.valueOf(arrivalTime.substring(0, 2))
											- Integer.valueOf(departureTime.substring(0, 2)),
									Integer.valueOf(arrivalTime.substring(3, 5))
											- Integer.valueOf(departureTime.substring(3, 5)));
						} else {
							System.out.printf("????????: %d?p?? %d????\n",
									Integer.valueOf(arrivalTime.substring(0, 2))
											- Integer.valueOf(departureTime.substring(0, 2)) - 1,
									Integer.valueOf(arrivalTime.substring(3, 5)) + 60
											- Integer.valueOf(departureTime.substring(3, 5)));
						}
						// print seat No.
						System.out.println("?y???s??: " + info.getSeat1());
						// print price
						System.out.println("????: " + info.getPrice());
					} else { // multiple seats one way trip
						// print train No. & date & departure & arrival time
						System.out.println("????: " + info.getDate1() + "\n" + "???H??: " + info.getAdult1() + "\n"
								+ "?j??????: " + info.getCollege1() + "\n" + "????: " + info.getTrainNo1() + "\n"
								+ station.getChStationName(info.getStart1()) + " " + departureTime + " -> "
								+ station.getChStationName(info.getEnd1()) + " " + arrivalTime);
						// print trip duration
						if (Integer.valueOf(arrivalTime.substring(3, 5)) >= Integer
								.valueOf(departureTime.substring(3, 5))) {
							System.out.printf("????????: %d?p?? %d????\n",
									Integer.valueOf(arrivalTime.substring(0, 2))
											- Integer.valueOf(departureTime.substring(0, 2)),
									Integer.valueOf(arrivalTime.substring(3, 5))
											- Integer.valueOf(departureTime.substring(3, 5)));
						} else {
							System.out.printf("????????: %d?p?? %d????\n",
									Integer.valueOf(arrivalTime.substring(0, 2))
											- Integer.valueOf(departureTime.substring(0, 2)) - 1,
									Integer.valueOf(arrivalTime.substring(3, 5)) + 60
											- Integer.valueOf(departureTime.substring(3, 5)));
						}

						// print seat No.
						System.out.println("?y???s??: ");
						for (int i = 0; i < info.getSeats1().length; i++) {
							if (info.getSeats1().length > 4) { // print on a new line if number of seats is more than 4
								if (i > info.getSeats1().length / 2) {
									System.out.println("");
								}
							}
							System.out.print(info.getSeats1()[i] + " ");
						}
						// print price
						System.out.println("");
						System.out.println("????: " + info.getPrice());
					}
				} else { // round trip
					String departureTime1 = info.getDepartureTime1();
					String arrivalTime1 = info.getArrivalTime1();
					String departureTime2 = info.getDepartureTime2();
					String arrivalTime2 = info.getArrivalTime2();

					if (info.getSeats1() == null) { // single seat round trip
						// print train No. & date & departure & arrival time (outbound)
						System.out.println("?h?{ : " + "\n" + "????: " + info.getDate1() + "\n" + "???H??: " + info.getAdult1()
								+ "\n" + "?j??????: " + info.getCollege1() + "\n" + "????: " + info.getTrainNo1() + "\n"
								+ station.getChStationName(info.getStart1()) + " " + departureTime1 + " -> "
								+ station.getChStationName(info.getEnd1()) + " " + arrivalTime1);
						// print trip duration
						if (Integer.valueOf(arrivalTime1.substring(3, 5)) >= Integer
								.valueOf(departureTime1.substring(3, 5))) {
							System.out.printf("????????: %d?p?? %d????\n",
									Integer.valueOf(arrivalTime1.substring(0, 2))
											- Integer.valueOf(departureTime1.substring(0, 2)),
									Integer.valueOf(arrivalTime1.substring(3, 5))
											- Integer.valueOf(departureTime1.substring(3, 5)));
						} else {
							System.out.printf("????????: %d?p?? %d????\n",
									Integer.valueOf(arrivalTime1.substring(0, 2))
											- Integer.valueOf(departureTime1.substring(0, 2)) - 1,
									Integer.valueOf(arrivalTime1.substring(3, 5)) + 60
											- Integer.valueOf(departureTime1.substring(3, 5)));
						}

						// print seat No.
						System.out.println("?y???s??: " + info.getSeat1());

						// print train No. & date & departure & arrival time (return)
						System.out.println("---------------------");
						System.out.println("?^?{ : " + "\n" + "????: " + info.getDate2() + "\n" + "???H??: " + info.getAdult1()
								+ "\n" + "?j??????: " + info.getCollege1() + "\n" + "????: " + info.getTrainNo2() + "\n"
								+ station.getChStationName(info.getStart2()) + " " + departureTime2 + " -> "
								+ station.getChStationName(info.getEnd2()) + " " + arrivalTime2);
						// print trip duration
						if (Integer.valueOf(arrivalTime2.substring(3, 5)) >= Integer
								.valueOf(departureTime2.substring(3, 5))) {
							System.out.printf("????????: %d?p?? %d????\n",
									Integer.valueOf(arrivalTime2.substring(0, 2))
											- Integer.valueOf(departureTime2.substring(0, 2)),
									Integer.valueOf(arrivalTime2.substring(3, 5))
											- Integer.valueOf(departureTime2.substring(3, 5)));
						} else {
							System.out.printf("????????: %d?p?? %d????\n",
									Integer.valueOf(arrivalTime2.substring(0, 2))
											- Integer.valueOf(departureTime2.substring(0, 2)) - 1,
									Integer.valueOf(arrivalTime2.substring(3, 5)) + 60
											- Integer.valueOf(departureTime2.substring(3, 5)));
						}
						// print seat No.
						System.out.println("?y???s??: " + info.getSeat2());
						// print price
						System.out.println("????: " + info.getPrice());
					} else { // multiple seats round trip
						// print train No. & date & departure & arrival time (outbound)
						System.out.println("?h?{ : " + "\n" + "????: " + info.getDate1() + "\n" + "???H??: " + info.getAdult1()
								+ "\n" + "?j??????: " + info.getCollege1() + "\n" + "????: " + info.getTrainNo1() + "\n"
								+ station.getChStationName(info.getStart1()) + " " + departureTime1 + " -> "
								+ station.getChStationName(info.getEnd1()) + " " + arrivalTime1);
						// print trip duration
						if (Integer.valueOf(arrivalTime1.substring(3, 5)) >= Integer
								.valueOf(departureTime1.substring(3, 5))) {
							System.out.printf("????????: %d?p?? %d????\n",
									Integer.valueOf(arrivalTime1.substring(0, 2))
											- Integer.valueOf(departureTime1.substring(0, 2)),
									Integer.valueOf(arrivalTime1.substring(3, 5))
											- Integer.valueOf(departureTime1.substring(3, 5)));
						} else {
							System.out.printf("????????: %d?p?? %d????\n",
									Integer.valueOf(arrivalTime1.substring(0, 2))
											- Integer.valueOf(departureTime1.substring(0, 2)) - 1,
									Integer.valueOf(arrivalTime1.substring(3, 5)) + 60
											- Integer.valueOf(departureTime1.substring(3, 5)));
						}
						// print seat No.
						System.out.println("?y???s??: ");
						for (int i = 0; i < info.getSeats1().length; i++) {
							if (info.getSeats1().length > 4) { // print on a new line if number of seats is more than 4
								if (i > info.getSeats1().length / 2) {
									System.out.println("");
								}
							}
							System.out.print(info.getSeats1()[i] + " ");
						}

						// print train No. & date & departure & arrival time (return)
						System.out.println("---------------------");
						System.out.println("?^?{ : " + "\n" + "????: " + info.getDate2() + "\n" + "???H??: " + info.getAdult1()
								+ "\n" + "?j??????: " + info.getCollege1() + "\n" + "????: " + info.getTrainNo2() + "\n"
								+ station.getChStationName(info.getStart2()) + " " + departureTime2 + " -> "
								+ station.getChStationName(info.getEnd2()) + " " + arrivalTime2);
						// print trip duration
						if (Integer.valueOf(arrivalTime2.substring(3, 5)) >= Integer
								.valueOf(departureTime2.substring(3, 5))) {
							System.out.printf("????????: %d?p?? %d????\n",
									Integer.valueOf(arrivalTime2.substring(0, 2))
											- Integer.valueOf(departureTime2.substring(0, 2)),
									Integer.valueOf(arrivalTime2.substring(3, 5))
											- Integer.valueOf(departureTime2.substring(3, 5)));
						} else {
							System.out.printf("????????: %d?p?? %d????\n",
									Integer.valueOf(arrivalTime2.substring(0, 2))
											- Integer.valueOf(departureTime2.substring(0, 2)) - 1,
									Integer.valueOf(arrivalTime2.substring(3, 5)) + 60
											- Integer.valueOf(departureTime2.substring(3, 5)));
						}
						// print seat No.
						System.out.println("?y???s??: ");
						for (int i = 0; i < info.getSeats2().length; i++) {
							if (info.getSeats2().length > 4) { // print on a new line if number of seats is more than 4
								if (i > info.getSeats2().length / 2) {
									System.out.println("");
								}
							}
							System.out.print(info.getSeats2()[i] + " ");
						}
						// print price
						System.out.println("");
						System.out.println("????: " + info.getPrice());
					}
				}
			} else {
				throw new OrderNoException();
			}

		} else {
			throw new UserException();
		}
	}

	/**
	 * Find the tracking number if users forgot it but still remember other
	 * information.
	 * 
	 * @param user      the user ID the user input when they book the order
	 * @param departure the departure station
	 * @param arrival   the arrival station
	 * @param date      the first(outbound) trip date in the form of yyyy-mm-dd
	 * @param trainNo   the train number of the booked tickets(s)
	 * @throws UserException
	 * @throws OrderInfoException
	 */
	public void findOrderNo(String user, String departure, String arrival, String date, String trainNo)
			throws UserException, OrderInfoException {

		boolean userFlag = false;
		int index = 0; // make compiler happy
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserID().equals(user)) {
				userFlag = true;
				index = i;
				break;
			}
		}
		if (userFlag) {
			ArrayList<BookingInfo> infos = new ArrayList<BookingInfo>();
			ArrayList<String> trackingNo = new ArrayList<String>();
			boolean orderFlag = false;
			for (int i = 0; i < users.get(index).getLengthOfOrder(); i++) {
				BookingInfo info = Booking.bookingInfo.get(users.get(index).getOrderNo(i));
				if (info.getStart1().equals(departure) && info.getEnd1().equals(arrival) && info.getDate1().equals(date)
						&& info.getTrainNo1().equals(trainNo)) {
					orderFlag = true;
					infos.add(info);
					trackingNo.add(users.get(index).getOrderNo(i));
				}
			}

			if (orderFlag) {
				for (int i = 0; i < infos.size(); i++) {
					if (infos.size() == 1) { // print one order
						System.out.println("?q???N??: " + trackingNo.get(i));
						System.out.println("????????: ");
						if (infos.get(i).getDate2() == null) { // print one way info
							BookingInfo info = infos.get(i);
							String departureTime1 = info.getDepartureTime1();
							String arrivalTime1 = info.getArrivalTime1();
							System.out.println("????: " + info.getDate1() + "\n" + "???H??: " + info.getAdult1() + "\n"
									+ "?j??????: " + info.getCollege1() + "\n" + "????: " + info.getTrainNo1() + "\n"
									+ station.getChStationName(info.getStart1()) + " " + departureTime1 + " -> "
									+ station.getChStationName(info.getEnd1()) + " " + arrivalTime1);
							System.out.print("?y???s??: ");
							if (infos.get(i).getSeats1() == null) { // print single seat
								System.out.println(infos.get(i).getSeat1());
							} else { // print multiple seats
								System.out.println("");
								for (int j = 0; j < infos.get(i).getSeats1().length; j++) {
									if (infos.get(i).getSeats1().length > 4) { // print on a new line if number of seats
																				// is more than 4
										if (j > infos.get(i).getSeats1().length / 2) {
											System.out.println("");
										}
									}
									System.out.print(infos.get(i).getSeats1()[i]);
								}
								System.out.println("");
							}
							// print price
							System.out.println("????: " + info.getPrice());
						} else { // print round trip info
							// print outbound trip
							BookingInfo info = infos.get(i);
							String departureTime1 = info.getDepartureTime1();
							String arrivalTime1 = info.getArrivalTime1();
							String departureTime2 = info.getDepartureTime2();
							String arrivalTime2 = info.getArrivalTime2();
							System.out.println(
									"?h?{ : " + "\n" + "????: " + info.getDate1() + "\n" + "???H??: " + info.getAdult1() + "\n"
											+ "?j??????: " + info.getCollege1() + "\n" + "????: " + info.getTrainNo1() + "\n"
											+ station.getChStationName(info.getStart1()) + " " + departureTime1 + " -> "
											+ station.getChStationName(info.getEnd1()) + " " + arrivalTime1);
							System.out.print("?y???s??: ");
							if (infos.get(i).getSeats1() == null) { // print single seat
								System.out.println(infos.get(i).getSeat1());
							} else { // print multiple seats
								System.out.println("");
								for (int j = 0; j < infos.get(i).getSeats1().length; j++) {
									if (infos.get(i).getSeats1().length > 4) { // print on a new line if number of seats
																				// is more than 4
										if (j > infos.get(i).getSeats1().length / 2) {
											System.out.println("");
										}
									}
									System.out.print(infos.get(i).getSeats1()[i]);
								}
								System.out.println("");
							}

							// print return trip
							System.out.println("---------------------");
							System.out.println(
									"?^?{ : " + "\n" + "????: " + info.getDate2() + "\n" + "???H??: " + info.getAdult1() + "\n"
											+ "?j??????: " + info.getCollege1() + "\n" + "????: " + info.getTrainNo2() + "\n"
											+ station.getChStationName(info.getStart2()) + " " + departureTime2 + " -> "
											+ station.getChStationName(info.getEnd2()) + " " + arrivalTime2);
							System.out.print("?y???s??: ");
							if (infos.get(i).getSeats2() == null) { // print single seat
								System.out.println(infos.get(i).getSeat2());
							} else { // print multiple seats
								System.out.println("");
								for (int j = 0; j < infos.get(i).getSeats2().length; j++) {
									if (infos.get(i).getSeats2().length > 4) { // print on a new line if number of seats
																				// is more than 4
										if (j > infos.get(i).getSeats2().length / 2) {
											System.out.println("");
										}
									}
									System.out.print(infos.get(i).getSeats2()[i]);
								}
								System.out.println("");
							}
							// print price
							System.out.println("????: " + info.getPrice());
						}

					} else { // print multiple orders
						System.out.printf("??%d???q??????\n", i + 1);
						System.out.println("?q???N??: " + trackingNo.get(i));
						System.out.println("????????: ");
						if (infos.get(i).getDate2() == null) { // print one way info
							BookingInfo info = infos.get(i);
							String departureTime1 = info.getStart1();
							String arrivalTime1 = info.getEnd1();
							System.out.println("????: " + info.getDate1() + "\n" + "???H??: " + info.getAdult1() + "\n"
									+ "?j??????: " + info.getCollege1() + "\n" + "????: " + info.getTrainNo1() + "\n"
									+ station.getChStationName(info.getStart1()) + " " + departureTime1 + " -> "
									+ station.getChStationName(info.getEnd1()) + " " + arrivalTime1);
							System.out.print("?y???s??: ");
							if (infos.get(i).getSeats1() == null) { // print single seat
								System.out.println(infos.get(i).getSeat1());
							} else { // print multiple seats
								System.out.println("");
								for (int j = 0; j < infos.get(i).getSeats1().length; j++) {
									if (infos.get(i).getSeats1().length > 4) { // print on a new line if number of seats
																				// is more than 4
										if (j > infos.get(i).getSeats1().length / 2) {
											System.out.println("");
										}
									}
									System.out.print(infos.get(i).getSeats1()[i] + " ");
								}
								System.out.println("");
							}
							// print price
							System.out.println("????: " + info.getPrice());
						} else { // print round trip info
							// print outbound trip
							BookingInfo info = infos.get(i);
							String departureTime1 = info.getStart1();
							String arrivalTime1 = info.getEnd1();
							String departureTime2 = info.getStart2();
							String arrivalTime2 = info.getEnd2();
							System.out.println(
									"?h?{ : " + "\n" + "????: " + info.getDate1() + "\n" + "???H??: " + info.getAdult1() + "\n"
											+ "?j??????: " + info.getCollege1() + "\n" + "????: " + info.getTrainNo1() + "\n"
											+ station.getChStationName(info.getStart1()) + " " + departureTime1 + " -> "
											+ station.getChStationName(info.getEnd1()) + " " + arrivalTime1);
							System.out.print("?y???s??: ");
							if (infos.get(i).getSeats1() == null) { // print single seat
								System.out.println(infos.get(i).getSeat1());
							} else { // print multiple seats
								System.out.println("");
								for (int j = 0; j < infos.get(i).getSeats1().length; j++) {
									if (infos.get(i).getSeats1().length > 4) { // print on a new line if number of seats
																				// is more than 4
										if (j > infos.get(i).getSeats1().length / 2) {
											System.out.println("");
										}
									}
									System.out.print(infos.get(i).getSeats1()[i] + " ");
								}
								System.out.println("");
							}

							// print return trip
							System.out.println("---------------------");
							System.out.println(
									"?^?{ : " + "\n" + "????: " + info.getDate2() + "\n" + "???H??: " + info.getAdult1() + "\n"
											+ "?j??????: " + info.getCollege1() + "\n" + "????: " + info.getTrainNo2() + "\n"
											+ station.getChStationName(info.getStart2()) + " " + departureTime2 + " -> "
											+ station.getChStationName(info.getEnd2()) + " " + arrivalTime2);
							System.out.print("?y???s??: ");
							if (infos.get(i).getSeats2() == null) { // print single seat
								System.out.println(infos.get(i).getSeat2());
							} else { // print multiple seats
								System.out.println("");
								for (int j = 0; j < infos.get(i).getSeats2().length; j++) {
									if (infos.get(i).getSeats2().length > 4) { // print on a new line if number of seats
																				// is more than 4
										if (j > infos.get(i).getSeats2().length / 2) {
											System.out.println("");
										}
									}
									System.out.print(infos.get(i).getSeats2()[i] + " ");
								}
								System.out.println("");
							}
							// print price
							System.out.println("????: " + info.getPrice());
						}
					}
				}
			} else {
				throw new OrderInfoException();
			}

		} else {
			throw new UserException();
		}
	}

	/**
	 * Calculate the date difference of the two input date
	 * 
	 * @param fDate the subtrahend
	 * @param oDate the minuend
	 * @return the date difference(oDate - fDate)
	 */
	public static int differentDays(Date fDate, Date oDate) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(fDate);
		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(oDate);
		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
		return day2 - day1;
	}

	public static boolean diffReviseTimes(String order) throws ParseException {
		Date now = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String sNow = ft.format(now);
		Date time1 = ft.parse(sNow);
		Date time2 = ft.parse(order);
		int diff = (int)(time2.getTime() - time1.getTime()) / (1000 * 60 * 60);
		if(diff < 3) {
			return false;
		}else {
			return true;
		}
	}
	
	public static boolean diffBookingTimes(String order) throws ParseException {
		Date now = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String sNow = ft.format(now);
		Date time1 = ft.parse(sNow);
		Date time2 = ft.parse(order);
		int diff = (int)(time2.getTime() - time1.getTime()) / (1000 * 60);
		if(diff < 30) {
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * Check if the input date is within the following 28 days
	 * 
	 * @param date the date be checked
	 * @return true if it's within the following 28 days
	 * @throws ParseException
	 */
	public static boolean checkDate(String date) throws ParseException {
		Date now = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		String sNow = ft.format(now);
		int diff = differentDays(ft.parse(sNow), ft.parse(date));
		if (diff > 28) {
			return false;
		} else if (diff < 0) {
			return false;
		} else {
			return true;
		}
	}

	public static void main(String[] args) {
		Main m = new Main();
		Scanner s = new Scanner(System.in);
		String input;

		System.out.println("---------------------");
		System.out.println("???????z???n???A??: A.?q?? B.?h??/???? C.?d??");
		while (true) { // make the program running until demo is over
			input = s.nextLine().toUpperCase();
			while (true) {
				if (input.equals("A")) { // booking ticket
					String trainNo1 = null;
					String trainNo2 = null;
					Seat.Class carClass = Seat.Class.Standard;
					Seat.SeatType seatType = Seat.SeatType.NoPreference;
					int adult = 0;
					int college = 0;
					String date1 = null;
					String date2 = null;
					String time1 = null;
					String time2 = null;
					String start = null;// ?_?????W
					String end = null;// ???????W
					System.out.println("---------------------");
					System.out.println("???????z?n?q?????????O: A.???{?? B.???^?? R.???^");
					Booking booking = new Booking();
					String c;
					while (true) {
						c = s.nextLine().toUpperCase();
						if (c.equals("A")) { // one way booking
							System.out.println("---------------------");
							System.out.println("???????????J?z???q?????????H???????j?A???????JR???^: ???? ?_?? ???? ???? ????(?@??or????) ???H???i?? ?j?????u?f???i??\n"
									+ "Eg. 2021-07-02 ?x?_ ?x?? 14:20 ???? 2 1");
							while (true) {
								try {
									String[] info = s.nextLine().split("\\s+");
									if (info[0].equals("R")) {
										adult = college = 0;
										break;
									} else {
										if (info.length != 7) {
											throw new java.util.InputMismatchException();
										}
										
										date1 = info[0];
										if (!checkDate(date1)) {
											throw new DateException("???J???????????????Z????28??(?t)?H?????????A?????s???J?A???????JR???^");
										}
										
										time1 = info[3];
										
										String time = date1 + " " + time1;
										if(!diffBookingTimes(time)) {
											throw new TimeException("?L?k?q???A?Z???o???????w????30?????A?????s???J?????JR???^");
										}
										
										
										adult = Integer.valueOf(info[5]);
										college = Integer.valueOf(info[6]);
										if (adult + college > 8 || adult + college <= 0 || adult < 0 || college < 0) {
											throw new NoOfTicketException("?q???`?i????????1?i?????h8?i?A?????s???J?A???????JR???^");
										}
										
										start = station.getEnStationName(info[1]);
										end = station.getEnStationName(info[2]);
										if (info[4].equals("?@??")) {
											carClass = Seat.Class.Standard;
											if (college == 0) {
												System.out.println("---------------------");
												search.generalSearchAndPrint(date1, start, end, time1);
												break;
											} else {
												System.out.println("---------------------");
												search.uniSearchAndPrint(date1, start, end, time1);
												break;
											}
										} else if (info[4].equals("????")) {
											carClass = Seat.Class.Business;
											if (college != 0) {
												throw new ClassException("???J???T???~?A???????L?c???j?????u?f???A?????s???J?A???????JR???^");
											} else {
												System.out.println("---------------------");
												search.generalSearchAndPrint(date1, start, end, time1);
												break;
											}
										} else {
											throw new ClassException("???J???????~?A?????s???J?A???????JR???^");
										}
									}
								} catch (ParseException e) {
									System.out.println("???J?????????????????~?A?????s???J?A???????JR???^");
								} catch (DateException e) {
									System.out.println(e);
								} catch (StationNameException e) {
									System.out.println("???J???W???~?A?????s???J?A???????JR???^");
								} catch (java.util.InputMismatchException e) {
									System.out.println("???J???????~?A?????s???J?A???????JR???^");
								} catch (ClassException e) {
									System.out.println(e);
								} catch (NoOfTicketException e) {
									System.out.println(e);
								} catch (TimeException e) {
									System.out.println(e);
								}
							}

							String tracking = null; // initialized to make compiler happy
							int total = adult + college;
							if (total == 1) { // book single seat one way ticket
								System.out.println("---------------------");
								System.out.println(
										"???????z???w?w???????????J?y?????n(?a??/?a???D/?????w)???H???????j?A???????JR???^: ???? ?y?????n\n" + "Eg. 0611 ?a??");
								while (true) {
									String pref = null;
									String[] info = s.nextLine().split("\\s+");
									try {
										if (info[0].equals("R")) {
											total = 0;
											break;
										} else {
											if (info.length != 2) {
												throw new java.util.InputMismatchException();
											} else {
												pref = info[1];
												trainNo1 = info[0];
												
												if (pref.equals("?a??")) {
													seatType = Seat.SeatType.Window;
													try {
														System.out.println("---------------------");
														tracking = booking.Bookticket(trainNo1, date1, start, end, carClass,
																seatType, adult, college);
														break;
													} catch (BookingException e) {
														System.out.println(e);
													} catch (PriceException e) {
														System.out.println(e);
													} catch (LeftSeatException e) {
														System.out.println(e);
													}
												} else if (pref.equals("?a???D")) {
													seatType = Seat.SeatType.Aisle;
													try {
														System.out.println("---------------------");
														tracking = booking.Bookticket(trainNo1, date1, start, end, carClass,
																seatType, adult, college);
														break;
													} catch (BookingException e) {
														System.out.println(e);
													} catch (PriceException e) {
														System.out.println(e);
													} catch (LeftSeatException e) {
														System.out.println(e);
													}
												} else if (pref.equals("?????w")) {
													try {
														System.out.println("---------------------");
														tracking = booking.Bookticket(trainNo1, date1, start, end, carClass,
																seatType, adult, college);
														break;
													} catch (BookingException e) {
														System.out.println(e);
													} catch (PriceException e) {
														System.out.println(e);
													} catch (LeftSeatException e) {
														System.out.println(e);
													}
												} else {
													System.out.println("???J???~?A?????s???J?A???????JR???^");
												}
											}
										}
									} catch (java.util.InputMismatchException e) {
										System.out.println("???J???????~?A?????s???J?A???????JR???^");
									}
								}
							} else if (total > 1) { // book multiple seat one way ticket
								System.out.println("---------------------");
								System.out.println("?????J?z???w?w???????A???????JR???^: \n" + "Eg. 0661");
								while (true) {
									String[] info = s.nextLine().split("\\s+");
									try {
										if (info[0].equals("R")) {
											total = 0;
											break;
										} else {
											if (info.length != 1) {
												throw new java.util.InputMismatchException();
											} else {
												trainNo1 = info[0];
												System.out.println("---------------------");
												tracking = booking.Bookticket(trainNo1, date1, start, end, carClass,
														seatType, adult, college);
												break;
											}
										}
									} catch (java.util.InputMismatchException e) {
										System.out.println("???J???????~?A?????s???J?A???????JR???^");
									} catch (BookingException e) {
										System.out.println(e); // this is supposed no way to happen
									} catch (PriceException e) {
										System.out.println(e);
									} catch (LeftSeatException e) {
										System.out.println(e);
									}
								}
							} else {
								break;
							}

							if (total == 0) {
								break;
							} else {
								boolean r = false;
								while (true) {
									System.out.println("---------------------");
									System.out.println("?????J?z?????????r?????@?????X?A???????JR???^: ");
									try {
										String userID = s.nextLine();
										if (userID.equals("R")) {
											r = true;
											break;
										} else {
											boolean userExist = false;
											User user = null;
											for (int i = 0; i < m.users.size(); i++) {
												if (m.users.get(i).getUserID().equals(userID)) {
													userExist = true;
													user = m.users.get(i);
													break;
												}
											}
											if (userExist) {
												user.addOrderNo(tracking);
											} else {
												User newUser = new User(userID, tracking);
												m.users.add(newUser);
											}
											break;
										}
									} catch (NoSuchElementException e) {
										System.out.println("?????J?z?????????r?????@?????X?A???????JR???^: ");
									}
								}
								if (r) {
									break;
								} else {
									System.out.println("?q??????!");
									break;
								}
							}
						} else if (c.equals("B")) { // round trip booking
							System.out.println("---------------------");
							System.out.println("???????????J?z???q?????????H???????j?A???????JR???^: \n"
									+ "?h?{???? ?h?{???? ?^?{???? ?^?{???? ?h?{?_?? ?h?{???? ????(?@??or????) ???H???i?? ?j?????u?f???i??\n"
									+ "Eg. 2021-07-02 14:20 2021-07-03 14:20 ?x?_ ?x?? ???? 1 0");
							while (true) {
								try {
									String[] info = s.nextLine().split("\\s+");
									if (info[0].equals("R")) {
										adult = college = 0;
										break;
									} else {
										if (info.length != 9) {
											throw new java.util.InputMismatchException();
										}
										date1 = info[0];
										time1 = info[1];
										date2 = info[2];
										time2 = info[3];
										
										String time = date1 + " " + time1;
										if(!diffBookingTimes(time)) {
											throw new TimeException("?L?k?h?q?A?Z???o???????w????30?????A?????s???J?????JR???^");
										}
										
										if (!checkDate(date1) || !checkDate(date2)) {
											throw new DateException("???J???????????????Z????28??(?t)?H?????????A?????s???J?A???????JR???^");
										} else {
											SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
											if (differentDays(ft.parse(date1), ft.parse(date2)) < 0) {
												throw new DateException("?^?{???????b?h?{?????????A?????s???J?A???????JR???^");
											} else if (differentDays(ft.parse(date1), ft.parse(date2)) == 0) {
												SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
												if (sdf.parse(date2).getTime() <= sdf.parse(time1).getTime()) {
													throw new DateException("?^?{???????b?h?{?????????A?????s???J?A???????JR???^");
												}
											}
										}
										
										start = station.getEnStationName(info[4]);
										end = station.getEnStationName(info[5]);
										adult = Integer.valueOf(info[7]);
										college = Integer.valueOf(info[8]);
										if (adult + college > 4 || adult + college <= 0 || adult < 0 || college < 0) {
											throw new NoOfTicketException("?q???`?i????????1?i?????h4?i?A?????s???J?A???????JR???^");
										}
										if (info[6].equals("?@??")) {
											carClass = Seat.Class.Standard;
											if (college == 0) {
												System.out.println("---------------------");
												search.generalSearchAndPrint(date1, start, end, time1);
												search.generalSearch(date2, end, start, time2);
												break;
											} else {
												System.out.println("---------------------");
												search.uniSearchAndPrint(date1, start, end, time1);
												search.uniSearch(date2, end, start, time2);
												break;
											}
										} else if (info[6].equals("????")) {
											carClass = Seat.Class.Business;
											if (college != 0) {
												throw new ClassException("???J???T???~?A???????L?c???j?????u?f???A?????s???J?A???????JR???^");
											} else {
												System.out.println("---------------------");
												search.generalSearchAndPrint(date1, start, end, time1);
												search.generalSearch(date2, end, start, time2);
												break;
											}
										} else {
											throw new ClassException("???J???????~?A?????s???J?A???????JR???^");
										}
									}
								} catch (ParseException e) {
									System.out.println("???J?????????????????~?A?????s???J?A???????JR???^");
								} catch (DateException e) {
									System.out.println(e);
								} catch (StationNameException e) {
									System.out.println("???J???W???~?A?????s???J?A???????JR???^");
								} catch (java.util.InputMismatchException e) {
									System.out.println("???J???????~?A?????s???J?A???????JR???^");
								} catch (ClassException e) {
									System.out.println(e);
								} catch (NoOfTicketException e) {
									System.out.println(e);
								} catch (TimeException e) {
									System.out.println("???J???????~?A?????s???J?????JR???^");
								}
							}

							String tracking = null; // initialized to make compiler happy
							String pref = null;
							int total = adult + college;
							if (total == 1) { // book single seat round trip ticket
								System.out.println("---------------------");
								System.out.println("???????z???w?w???h?{?????????J?y?????n(?a??/?a???D/?????w)???H???????j?A" + "???????JR???^: ???? ?y?????n\n"
										+ "Eg. 0611 ?a??");
								while (true) {
									String[] info = s.nextLine().split("\\s+");
									try {
										if (info[0].equals("R")) {
											total = 0;
											break;
										} else {
											if (info.length != 2) {
												throw new java.util.InputMismatchException();
											} else {
												pref = info[1];
												trainNo1 = info[0];
												if (pref.equals("?a??")) {
													seatType = Seat.SeatType.Window;
													break;
												} else if (pref.equals("?a???D")) {
													seatType = Seat.SeatType.Aisle;
													break;
												} else if (pref.equals("?????w")) {
													break;
												} else {
													throw new PrefException("???J???~?A?????s???J?A???????JR???^");
												}
											}
										}
									} catch (java.util.InputMismatchException e) {
										System.out.println("???J???????~?A?????s???J?A???????JR???^");
									} catch (PrefException e) {
										System.out.println(e);
									}
								}

								try {
									if (college == 0) {
										System.out.println("---------------------");
										search.generalSearchAndPrint(date2, end, start, time2);
									} else {
										System.out.println("---------------------");
										search.uniSearchAndPrint(date2, end, start, time2);
									}
								} catch (ParseException e) {
									System.out.println(e); // this should not happen
								} catch (TimeException e) {
									System.out.println("???J???????~?A?????s???J?????JR???^");
								}
								System.out.println("---------------------");
								System.out.println("???????z???w?w???^?{?????A???????JR???^: \n" + "Eg. 0611");
								while (true) {
									String[] info = s.nextLine().split("\\s+");
									try {
										if (info[0].equals("R")) {
											total = 0;
											break;
										} else {
											if (info.length != 1) {
												throw new java.util.InputMismatchException();
											} else {
												trainNo2 = info[0];
												System.out.println("---------------------");
												tracking = booking.BookticketRoundtrip(trainNo1, trainNo2, date1, date2,
														start, end, carClass, seatType, adult, college);
												break;
											}
										}
									} catch (java.util.InputMismatchException e) {
										System.out.println("???J???????~?A?????s???J?A???????JR???^");
									} catch (BookingException e) {
										System.out.println(e + "?A?????s???J?A???????JR???^");
									} catch (PriceException e) {
										System.out.println(e + "?A?????s???J?A???????JR???^");
									} catch (LeftSeatException e) {
										System.out.println(e);
									}
								}
							} else if (total > 1) { // book multiple seat round trip ticket
								System.out.println("---------------------");
								System.out.println("???????z???w?w???h?{?????A???????JR???^: \n" + "Eg. 0611");
								while (true) {
									String[] info = s.nextLine().split("\\s+");
									try {
										if (info[0].equals("R")) {
											total = 0;
											break;
										} else {
											if (info.length != 1) {
												throw new java.util.InputMismatchException();
											} else {
												trainNo1 = info[0];
												break;
											}
										}
									} catch (java.util.InputMismatchException e) {
										System.out.println("???J???????~?A?????s???J?A???????JR???^");
									}
								}

								try {
									if (college == 0) {
										System.out.println("---------------------");
										search.generalSearchAndPrint(date2, end, start, time2);
									} else {
										System.out.println("---------------------");
										search.uniSearchAndPrint(date2, end, start, time2);
									}
								} catch (ParseException e) {
									System.out.println(e);
								} catch (TimeException e) {
									System.out.println("???J???????~?A?????s???J?????JR???^");
								}

								System.out.println("???????z???w?w???^?{?????A???????JR???^: \n" + "Eg. 0611");
								while (true) {
									String[] info = s.nextLine().split("\\s+");
									try {
										if (info[0].equals("R")) {
											total = 0;
											break;
										} else {
											if (info.length != 1) {
												throw new java.util.InputMismatchException();
											} else {
												trainNo2 = info[0];
												System.out.println("---------------------");
												tracking = booking.BookticketRoundtrip(trainNo1, trainNo2, date1, date2,
														start, end, carClass, seatType, adult, college);
												break;
											}
										}
									} catch (java.util.InputMismatchException e) {
										System.out.println("???J???????~?A?????s???J?A???????JR???^");
									} catch (BookingException e) {
										System.out.println(e); // this is supposed no way to happen
									} catch (PriceException e) {
										System.out.println(e);
									} catch (LeftSeatException e) {
										System.out.println(e);
									}
								}
							} else {
								break;
							}

							if (total == 0) {
								break;
							} else {
								boolean r = false;
								while (true) {
									System.out.println("---------------------");
									System.out.println("?????J?z?????????r?????@?????X?A???????JR???^: ");
									try {
										String userID = s.nextLine();
										if (userID.equals("R")) {
											r = true;
											break;
										} else {
											boolean userExist = false;
											User user = null;
											for (int i = 0; i < m.users.size(); i++) {
												if (m.users.get(i).getUserID().equals(userID)) {
													userExist = true;
													user = m.users.get(i);
													break;
												}
											}
											if (userExist) {
												user.addOrderNo(tracking);
											} else {
												User newUser = new User(userID, tracking);
												m.users.add(newUser);
											}
											break;
										}
									} catch (NoSuchElementException e) {
										System.out.println("?????J?z?????????r?????@?????X?A???????JR???^: ");
									}
								}
								if (r) {
									break;
								} else {
									System.out.println("?q??????!");
									break;
								}
							}
						} else if (c.equals("R")) {
							break;
						}
					}
					System.out.println("---------------------");
					System.out.println("???????z???n???A??: A.?q?? B.?h??/???? C.?d??");
					break;
				} else if (input.equals("B")) { // refund or revise
					System.out.println("---------------------");
					System.out.println("?????J?z???n???A??: A.?????q?? B.?????q?? R.???^");
					String c = s.nextLine().toUpperCase();
					while (true) {
						if (c.equals("A")) { // ?????q??
							System.out.println("---------------------");
							System.out.println(
									"???????????J?z?????????r??(???@?????X)?M?z?n???????q???N?????H???????j?A???????JR???^: \n" + "Eg. A12341234 999999(?@6?????r)");
							while (true) {
								try {
									String[] info = s.nextLine().split("\\s+");
									if (info[0].equals("R")) {
										break;
									}
									if (info.length != 2) {
										throw new java.util.InputMismatchException("???J???????~?A?????s???J?A???????JR???^");
									}
									boolean userFlag = false;
									User user = null; // initialized to make compiler happy
									for (int i = 0; i < m.users.size(); i++) {
										if (m.users.get(i).getUserID().equals(info[0])) {
											user = m.users.get(i);
											userFlag = true;
											break;
										}
									}
									if (!userFlag) {
										throw new UserException();
									}

									boolean orderFlag = false;
									String orderNo = null; // initialized to make compiler happy
									for (int i = 0; i < user.getLengthOfOrder(); i++) {
										if (user.getOrderNo(i).equals(info[1])) {
											orderFlag = true;
											orderNo = info[1];
										}
									}
									if (!orderFlag) {
										throw new OrderNoException("?d?L???q???s??!???T?{???J???q???s???O?_???T?????s???J?A???????JR???^");
									}
									
									BookingInfo bk = Booking.bookingInfo.get(orderNo);
									String time = bk.getDate1() + " " + bk.getDepartureTime1();
									if(!diffReviseTimes(time)) {
										throw new TimeException("?L?k?h?q?A?Z???o???????w?????T?p???A?????s???J?????JR???^");
									}
									
									RefundOrRevise cancel = new RefundOrRevise(m.booking);
									cancel.cancelTicket(orderNo);
									user.removeOrderNo(orderNo);
									break;
								} catch (java.util.InputMismatchException e) {
									System.out.println(e);
								} catch (UserException e) {
									System.out.println("?d?L???????????A?????s???J?????JR???^");
								} catch (OrderNoException e) {
									System.out.println(e);
								} catch (ParseException e) {
									System.out.println("???J?????????????????~?A?????s???J?????JR???^");
								} catch (TimeException e) {
									System.out.println(e);
								}
							}
							break;
						} else if (c.equals("B")) { // ?????q??
							System.out.println("---------------------");
							System.out.println("???????????J?z?????????r??(???@?????X)?B?z?????????q???N???M???h?????????i?????H???????j?A???????JR???^: \n"
									+ "???????r?? ?q???N?? ???h?????H???i?? ???h???j?????u?f???i??\n" + "Eg. A12341234 999999(?@6?????r) 1 0");
							while (true) {
								try {
									String[] info = s.nextLine().split("\\s+");
									if (info[0].equals("R")) {
										break;
									}
									if (info.length != 4) {
										throw new java.util.InputMismatchException("???J???????~?A?????s???J?A???????JR???^");
									}

									User user = null; // initialized to make compiler happy
									boolean userFlag = false;
									for (int i = 0; i < m.users.size(); i++) {
										if (m.users.get(i).getUserID().equals(info[0])) {
											user = m.users.get(i);
											userFlag = true;
											break;
										}
									}
									if (!userFlag) {
										throw new UserException();
									}

									boolean orderFlag = false;
									String orderNo = null; // initialized to make compiler happy
									for (int i = 0; i < user.getLengthOfOrder(); i++) {
										if (user.getOrderNo(i).equals(info[1])) {
											orderFlag = true;
											orderNo = info[1];
										}
									}
									if (!orderFlag) {
										throw new OrderNoException("?d?L???q???s??!???T?{???J???q???s???O?_???T?????s???J?A???????JR???^");
									}
									int adult = Integer.valueOf(info[2]);
									int college = Integer.valueOf(info[3]);
									if(adult + college == 0) {
										throw new ReviseException("???J?h???i?????~?A?h???`?i????????1?i?A?????s???J???????JR???^");
									}
									
									BookingInfo bk = Booking.bookingInfo.get(orderNo);
									String time = bk.getDate1() + " " + bk.getDepartureTime1();
									if(!diffReviseTimes(time)) {
										throw new TimeException("?L?k?h?q?A?Z???o???????w?????T?p???A?????s???J?????JR???^");
									}
									
									RefundOrRevise revise = new RefundOrRevise(m.booking);
									System.out.println("---------------------");
									revise.reviseTicket(orderNo, adult, college);
									break;
								} catch (java.util.InputMismatchException e) {
									System.out.println(e);
								} catch (UserException e) {
									System.out.println("?d?L???????????A?????s???J???????JR???^");
								} catch (OrderNoException e) {
									System.out.println(e);
								} catch (ReviseException e) {
									System.out.println(e);
								}catch (ParseException e) {
									System.out.println("???J?????????????????~?A?????s???J?????JR???^");
								} catch (TimeException e) {
									System.out.println(e);
								}
							}
							break;
						} else if (c.equals("R")) { // ???^
							break;
						} else {
							System.out.println("???J???~?A ?????s???J?A???????JR???^");
						}
					}
					System.out.println("---------------------");
					System.out.println("???????z???n???A??: A.?q?? B.?h??/???? C.?d??");
					break;
				} else if (input.equals("C")) { // search
					System.out.println("---------------------");
					System.out.println("???????z?n?d????????: A.?d???q?????T(???q???N??) B.?d???q?????T(?L?q???N??) C.?????????? D.?u?f???? R.???^");
					String c = s.nextLine().toUpperCase();
					while (true) {
						if (c.equals("A")) { // A.?d???q?????T(???q???N??)
							System.out.println("---------------------");
							System.out.println("?????J?z???????q?????T???H???????j?A???????JR???^ ??????: ???????r??(???@?????X) ?q???N??(?@6????)\n"
									+ "Eg. A12341234 999999");
							while (true) {
								try {
									String[] info = s.nextLine().split("\\s+");
									if (info[0].equals("R")) {
										break;
									}
									if (info.length != 2) {
										throw new java.util.InputMismatchException("???J???????~?A?????s???J?A???????JR???^");
									}
									System.out.println("---------------------");
									m.findBookingInfo(info[0], info[1]);
									break;
								} catch (OrderNoException | UserException e) {
									System.out.println(e);
								} catch (java.util.InputMismatchException e) {
									System.out.println(e);
								}
							}
							break;
						} else if (c.equals("B")) { // ?d???q?????T(?L?q???N??)
							System.out.println("---------------------");
							System.out.println("?????J?z???????q?????T???H???????j?A???????JR???^ ??????: ???????r??(???@?????X) ?_?? ???? ???? ????\n"
									+ "Eg. A12341234 ?x?_ ?x?? 2021-07-05 0639");
							while (true) {
								try {
									String[] info = s.nextLine().split("\\s+");
									if (info[0].equals("R")) {
										break;
									}
									if (info.length != 5) {
										throw new java.util.InputMismatchException("???J???????~?A?????s???J?A???????JR???^");
									}
									String start = station.getEnStationName(info[1]);
									String end = station.getEnStationName(info[2]);
									System.out.println("---------------------");
									m.findOrderNo(info[0], start, end, info[3], info[4]);
									break;
								} catch (UserException | OrderInfoException e) {
									System.out.println(e);
								} catch (java.util.InputMismatchException e) {
									System.out.println(e);
								} catch (StationNameException e) {
									System.out.println("???J???W???~?A?????s???J?A???????JR???^");
								}
							}
							break;
						} else if (c.equals("C")) { // ?d???u?f????
							System.out.println("---------------------");
							System.out.println("???????????J???d?????????A???????JR???^: \n" + "Eg.2021-06-23");
							while (true) {
								try {
									String[] info = s.nextLine().split("\\s+");
									if (info[0].equals("R")) {
										break;
									}else {
										if (info.length != 1) {
											throw new java.util.InputMismatchException("???J???????~?A?????s???J?A???????JR???^");
										}else {
											System.out.println("---------------------");
											new Search(info[0]);
											break;
										}
									}
								} catch (java.util.InputMismatchException e) {
									System.out.println(e);
								} catch (NoSuchElementException | ParseException e) {
									System.out.println("???J???????~?A?????s???J?A???????JR???^");
								}
							}
							break;
						} else if (c.equals("D")) { // ?d???u?f????
							System.out.println(
									"?????????????J???d?????????B?_?W???M???????H???????j?A???????JR???^: ???? ?_?? ???? ????\n" + "Eg.2021-06-23 ?x?_ ?x?? 19:20");
							while (true) {
								try {
									String[] info = s.nextLine().split("\\s+");
									if (info[0].equals("R")) {
										break;
									}else {
										if (info.length != 4) {
											throw new java.util.InputMismatchException("???J???????~?A?????s???J?A???????JR???^");
										}else {
											String start = info[1];
											String end = info[2];
											search.earlySearch(info[0], station.getEnStationName(start),
													station.getEnStationName(end), info[3]);
											search.uniSearch(info[0], station.getEnStationName(start),
													station.getEnStationName(end), info[3]);
											System.out.println("------??????????-------");
											search.earlySearchAndPrint(info[0], station.getEnStationName(start),
													station.getEnStationName(end), info[3]);
											System.out.println("----?j?????u?f??????-----");
											search.uniSearchAndPrint(info[0], station.getEnStationName(start),
													station.getEnStationName(end), info[3]);
											break;
										}
									}
								} catch (java.util.InputMismatchException e) {
									System.out.println(e);
								} catch (ParseException e) {
									System.out.println("???J???????~?A?????s???J?A???????JR???^");
								} catch (StationNameException e) {
									System.out.println("???J???W???~?A?????s???J?A???????JR???^");
								} catch (TimeException e) {
									System.out.println("???J???????~?A?????s???J?????JR???^");
								}
							}
							break;
						} else if (c.equals("R")) {
							break;
						} else {
							System.out.println("???J???~?A ?????s???J?A???????JR???^");
						}
					}
					System.out.println("---------------------");
					System.out.println("???????z???n???A??: A.?q?? B.?h??/???? C.?d??");
					break;
				} else if (input.equals("L")) { // end the whole program
					System.exit(0);
				} else {
					System.out.println("???J???~?A ?????s???J");
					break;
				}
			}

		}

	}

}
