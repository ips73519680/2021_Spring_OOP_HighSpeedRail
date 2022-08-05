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
						System.out.println("日期: " + info.getDate1() + "\n" + "成人票: " + info.getAdult1() + "\n"
								+ "大學生票: " + info.getCollege1() + "\n" + "車次: " + info.getTrainNo1() + "\n"
								+ station.getChStationName(info.getStart1()) + " " + departureTime + " -> "
								+ station.getChStationName(info.getEnd1()) + " " + arrivalTime);
						// print trip duration
						if (Integer.valueOf(arrivalTime.substring(3, 5)) >= Integer
								.valueOf(departureTime.substring(3, 5))) {
							System.out.printf("行車時間: %d小時 %d分鐘\n",
									Integer.valueOf(arrivalTime.substring(0, 2))
											- Integer.valueOf(departureTime.substring(0, 2)),
									Integer.valueOf(arrivalTime.substring(3, 5))
											- Integer.valueOf(departureTime.substring(3, 5)));
						} else {
							System.out.printf("行車時間: %d小時 %d分鐘\n",
									Integer.valueOf(arrivalTime.substring(0, 2))
											- Integer.valueOf(departureTime.substring(0, 2)) - 1,
									Integer.valueOf(arrivalTime.substring(3, 5)) + 60
											- Integer.valueOf(departureTime.substring(3, 5)));
						}
						// print seat No.
						System.out.println("座位編號: " + info.getSeat1());
						// print price
						System.out.println("票價: " + info.getPrice());
					} else { // multiple seats one way trip
						// print train No. & date & departure & arrival time
						System.out.println("日期: " + info.getDate1() + "\n" + "成人票: " + info.getAdult1() + "\n"
								+ "大學生票: " + info.getCollege1() + "\n" + "車次: " + info.getTrainNo1() + "\n"
								+ station.getChStationName(info.getStart1()) + " " + departureTime + " -> "
								+ station.getChStationName(info.getEnd1()) + " " + arrivalTime);
						// print trip duration
						if (Integer.valueOf(arrivalTime.substring(3, 5)) >= Integer
								.valueOf(departureTime.substring(3, 5))) {
							System.out.printf("行車時間: %d小時 %d分鐘\n",
									Integer.valueOf(arrivalTime.substring(0, 2))
											- Integer.valueOf(departureTime.substring(0, 2)),
									Integer.valueOf(arrivalTime.substring(3, 5))
											- Integer.valueOf(departureTime.substring(3, 5)));
						} else {
							System.out.printf("行車時間: %d小時 %d分鐘\n",
									Integer.valueOf(arrivalTime.substring(0, 2))
											- Integer.valueOf(departureTime.substring(0, 2)) - 1,
									Integer.valueOf(arrivalTime.substring(3, 5)) + 60
											- Integer.valueOf(departureTime.substring(3, 5)));
						}

						// print seat No.
						System.out.println("座位編號: ");
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
						System.out.println("票價: " + info.getPrice());
					}
				} else { // round trip
					String departureTime1 = info.getDepartureTime1();
					String arrivalTime1 = info.getArrivalTime1();
					String departureTime2 = info.getDepartureTime2();
					String arrivalTime2 = info.getArrivalTime2();

					if (info.getSeats1() == null) { // single seat round trip
						// print train No. & date & departure & arrival time (outbound)
						System.out.println("去程 : " + "\n" + "日期: " + info.getDate1() + "\n" + "成人票: " + info.getAdult1()
								+ "\n" + "大學生票: " + info.getCollege1() + "\n" + "車次: " + info.getTrainNo1() + "\n"
								+ station.getChStationName(info.getStart1()) + " " + departureTime1 + " -> "
								+ station.getChStationName(info.getEnd1()) + " " + arrivalTime1);
						// print trip duration
						if (Integer.valueOf(arrivalTime1.substring(3, 5)) >= Integer
								.valueOf(departureTime1.substring(3, 5))) {
							System.out.printf("行車時間: %d小時 %d分鐘\n",
									Integer.valueOf(arrivalTime1.substring(0, 2))
											- Integer.valueOf(departureTime1.substring(0, 2)),
									Integer.valueOf(arrivalTime1.substring(3, 5))
											- Integer.valueOf(departureTime1.substring(3, 5)));
						} else {
							System.out.printf("行車時間: %d小時 %d分鐘\n",
									Integer.valueOf(arrivalTime1.substring(0, 2))
											- Integer.valueOf(departureTime1.substring(0, 2)) - 1,
									Integer.valueOf(arrivalTime1.substring(3, 5)) + 60
											- Integer.valueOf(departureTime1.substring(3, 5)));
						}

						// print seat No.
						System.out.println("座位編號: " + info.getSeat1());

						// print train No. & date & departure & arrival time (return)
						System.out.println("---------------------");
						System.out.println("回程 : " + "\n" + "日期: " + info.getDate2() + "\n" + "成人票: " + info.getAdult1()
								+ "\n" + "大學生票: " + info.getCollege1() + "\n" + "車次: " + info.getTrainNo2() + "\n"
								+ station.getChStationName(info.getStart2()) + " " + departureTime2 + " -> "
								+ station.getChStationName(info.getEnd2()) + " " + arrivalTime2);
						// print trip duration
						if (Integer.valueOf(arrivalTime2.substring(3, 5)) >= Integer
								.valueOf(departureTime2.substring(3, 5))) {
							System.out.printf("行車時間: %d小時 %d分鐘\n",
									Integer.valueOf(arrivalTime2.substring(0, 2))
											- Integer.valueOf(departureTime2.substring(0, 2)),
									Integer.valueOf(arrivalTime2.substring(3, 5))
											- Integer.valueOf(departureTime2.substring(3, 5)));
						} else {
							System.out.printf("行車時間: %d小時 %d分鐘\n",
									Integer.valueOf(arrivalTime2.substring(0, 2))
											- Integer.valueOf(departureTime2.substring(0, 2)) - 1,
									Integer.valueOf(arrivalTime2.substring(3, 5)) + 60
											- Integer.valueOf(departureTime2.substring(3, 5)));
						}
						// print seat No.
						System.out.println("座位編號: " + info.getSeat2());
						// print price
						System.out.println("票價: " + info.getPrice());
					} else { // multiple seats round trip
						// print train No. & date & departure & arrival time (outbound)
						System.out.println("去程 : " + "\n" + "日期: " + info.getDate1() + "\n" + "成人票: " + info.getAdult1()
								+ "\n" + "大學生票: " + info.getCollege1() + "\n" + "車次: " + info.getTrainNo1() + "\n"
								+ station.getChStationName(info.getStart1()) + " " + departureTime1 + " -> "
								+ station.getChStationName(info.getEnd1()) + " " + arrivalTime1);
						// print trip duration
						if (Integer.valueOf(arrivalTime1.substring(3, 5)) >= Integer
								.valueOf(departureTime1.substring(3, 5))) {
							System.out.printf("行車時間: %d小時 %d分鐘\n",
									Integer.valueOf(arrivalTime1.substring(0, 2))
											- Integer.valueOf(departureTime1.substring(0, 2)),
									Integer.valueOf(arrivalTime1.substring(3, 5))
											- Integer.valueOf(departureTime1.substring(3, 5)));
						} else {
							System.out.printf("行車時間: %d小時 %d分鐘\n",
									Integer.valueOf(arrivalTime1.substring(0, 2))
											- Integer.valueOf(departureTime1.substring(0, 2)) - 1,
									Integer.valueOf(arrivalTime1.substring(3, 5)) + 60
											- Integer.valueOf(departureTime1.substring(3, 5)));
						}
						// print seat No.
						System.out.println("座位編號: ");
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
						System.out.println("回程 : " + "\n" + "日期: " + info.getDate2() + "\n" + "成人票: " + info.getAdult1()
								+ "\n" + "大學生票: " + info.getCollege1() + "\n" + "車次: " + info.getTrainNo2() + "\n"
								+ station.getChStationName(info.getStart2()) + " " + departureTime2 + " -> "
								+ station.getChStationName(info.getEnd2()) + " " + arrivalTime2);
						// print trip duration
						if (Integer.valueOf(arrivalTime2.substring(3, 5)) >= Integer
								.valueOf(departureTime2.substring(3, 5))) {
							System.out.printf("行車時間: %d小時 %d分鐘\n",
									Integer.valueOf(arrivalTime2.substring(0, 2))
											- Integer.valueOf(departureTime2.substring(0, 2)),
									Integer.valueOf(arrivalTime2.substring(3, 5))
											- Integer.valueOf(departureTime2.substring(3, 5)));
						} else {
							System.out.printf("行車時間: %d小時 %d分鐘\n",
									Integer.valueOf(arrivalTime2.substring(0, 2))
											- Integer.valueOf(departureTime2.substring(0, 2)) - 1,
									Integer.valueOf(arrivalTime2.substring(3, 5)) + 60
											- Integer.valueOf(departureTime2.substring(3, 5)));
						}
						// print seat No.
						System.out.println("座位編號: ");
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
						System.out.println("票價: " + info.getPrice());
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
						System.out.println("訂位代號: " + trackingNo.get(i));
						System.out.println("詳細資料: ");
						if (infos.get(i).getDate2() == null) { // print one way info
							BookingInfo info = infos.get(i);
							String departureTime1 = info.getDepartureTime1();
							String arrivalTime1 = info.getArrivalTime1();
							System.out.println("日期: " + info.getDate1() + "\n" + "成人票: " + info.getAdult1() + "\n"
									+ "大學生票: " + info.getCollege1() + "\n" + "車次: " + info.getTrainNo1() + "\n"
									+ station.getChStationName(info.getStart1()) + " " + departureTime1 + " -> "
									+ station.getChStationName(info.getEnd1()) + " " + arrivalTime1);
							System.out.print("座位編號: ");
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
							System.out.println("票價: " + info.getPrice());
						} else { // print round trip info
							// print outbound trip
							BookingInfo info = infos.get(i);
							String departureTime1 = info.getDepartureTime1();
							String arrivalTime1 = info.getArrivalTime1();
							String departureTime2 = info.getDepartureTime2();
							String arrivalTime2 = info.getArrivalTime2();
							System.out.println(
									"去程 : " + "\n" + "日期: " + info.getDate1() + "\n" + "成人票: " + info.getAdult1() + "\n"
											+ "大學生票: " + info.getCollege1() + "\n" + "車次: " + info.getTrainNo1() + "\n"
											+ station.getChStationName(info.getStart1()) + " " + departureTime1 + " -> "
											+ station.getChStationName(info.getEnd1()) + " " + arrivalTime1);
							System.out.print("座位編號: ");
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
									"回程 : " + "\n" + "日期: " + info.getDate2() + "\n" + "成人票: " + info.getAdult1() + "\n"
											+ "大學生票: " + info.getCollege1() + "\n" + "車次: " + info.getTrainNo2() + "\n"
											+ station.getChStationName(info.getStart2()) + " " + departureTime2 + " -> "
											+ station.getChStationName(info.getEnd2()) + " " + arrivalTime2);
							System.out.print("座位編號: ");
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
							System.out.println("票價: " + info.getPrice());
						}

					} else { // print multiple orders
						System.out.printf("第%d筆訂單資料\n", i + 1);
						System.out.println("訂位代號: " + trackingNo.get(i));
						System.out.println("詳細資料: ");
						if (infos.get(i).getDate2() == null) { // print one way info
							BookingInfo info = infos.get(i);
							String departureTime1 = info.getStart1();
							String arrivalTime1 = info.getEnd1();
							System.out.println("日期: " + info.getDate1() + "\n" + "成人票: " + info.getAdult1() + "\n"
									+ "大學生票: " + info.getCollege1() + "\n" + "車次: " + info.getTrainNo1() + "\n"
									+ station.getChStationName(info.getStart1()) + " " + departureTime1 + " -> "
									+ station.getChStationName(info.getEnd1()) + " " + arrivalTime1);
							System.out.print("座位編號: ");
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
							System.out.println("票價: " + info.getPrice());
						} else { // print round trip info
							// print outbound trip
							BookingInfo info = infos.get(i);
							String departureTime1 = info.getStart1();
							String arrivalTime1 = info.getEnd1();
							String departureTime2 = info.getStart2();
							String arrivalTime2 = info.getEnd2();
							System.out.println(
									"去程 : " + "\n" + "日期: " + info.getDate1() + "\n" + "成人票: " + info.getAdult1() + "\n"
											+ "大學生票: " + info.getCollege1() + "\n" + "車次: " + info.getTrainNo1() + "\n"
											+ station.getChStationName(info.getStart1()) + " " + departureTime1 + " -> "
											+ station.getChStationName(info.getEnd1()) + " " + arrivalTime1);
							System.out.print("座位編號: ");
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
									"回程 : " + "\n" + "日期: " + info.getDate2() + "\n" + "成人票: " + info.getAdult1() + "\n"
											+ "大學生票: " + info.getCollege1() + "\n" + "車次: " + info.getTrainNo2() + "\n"
											+ station.getChStationName(info.getStart2()) + " " + departureTime2 + " -> "
											+ station.getChStationName(info.getEnd2()) + " " + arrivalTime2);
							System.out.print("座位編號: ");
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
							System.out.println("票價: " + info.getPrice());
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
		System.out.println("請選擇您需要的服務: A.訂票 B.退票/修改 C.查詢");
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
					String start = null;// 起站站名
					String end = null;// 終站站名
					System.out.println("---------------------");
					System.out.println("請選擇您要訂的車票類別: A.單程票 B.來回票 R.返回");
					Booking booking = new Booking();
					String c;
					while (true) {
						c = s.nextLine().toUpperCase();
						if (c.equals("A")) { // one way booking
							System.out.println("---------------------");
							System.out.println("請照順序輸入您的訂票資料並以空格間隔，或者輸入R返回: 日期 起站 迄站 時間 艙等(一般or商務) 成人票張數 大學生優惠票張數\n"
									+ "Eg. 2021-07-02 台北 台中 14:20 商務 2 1");
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
											throw new DateException("輸入日期應為今日及距今日28日(含)以內之日期，請重新輸入，或者輸入R返回");
										}
										
										time1 = info[3];
										
										String time = date1 + " " + time1;
										if(!diffBookingTimes(time)) {
											throw new TimeException("無法訂票，距離發車時間已不到30分鐘，請重新輸入或輸入R返回");
										}
										
										
										adult = Integer.valueOf(info[5]);
										college = Integer.valueOf(info[6]);
										if (adult + college > 8 || adult + college <= 0 || adult < 0 || college < 0) {
											throw new NoOfTicketException("訂票總張數應至少1張並至多8張，請重新輸入，或者輸入R返回");
										}
										
										start = station.getEnStationName(info[1]);
										end = station.getEnStationName(info[2]);
										if (info[4].equals("一般")) {
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
										} else if (info[4].equals("商務")) {
											carClass = Seat.Class.Business;
											if (college != 0) {
												throw new ClassException("輸入資訊錯誤，商務艙無販售大學生優惠票，請重新輸入，或者輸入R返回");
											} else {
												System.out.println("---------------------");
												search.generalSearchAndPrint(date1, start, end, time1);
												break;
											}
										} else {
											throw new ClassException("輸入艙等錯誤，請重新輸入，或者輸入R返回");
										}
									}
								} catch (ParseException e) {
									System.out.println("輸入日期或時間格式錯誤，請重新輸入，或者輸入R返回");
								} catch (DateException e) {
									System.out.println(e);
								} catch (StationNameException e) {
									System.out.println("輸入站名錯誤，請重新輸入，或者輸入R返回");
								} catch (java.util.InputMismatchException e) {
									System.out.println("輸入格式錯誤，請重新輸入，或者輸入R返回");
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
										"請選擇您欲預定的車次並輸入座位偏好(靠窗/靠走道/不指定)並以空格間隔，或者輸入R返回: 車次 座位偏好\n" + "Eg. 0611 靠窗");
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
												
												if (pref.equals("靠窗")) {
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
												} else if (pref.equals("靠走道")) {
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
												} else if (pref.equals("不指定")) {
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
													System.out.println("輸入錯誤，請重新輸入，或者輸入R返回");
												}
											}
										}
									} catch (java.util.InputMismatchException e) {
										System.out.println("輸入格式錯誤，請重新輸入，或者輸入R返回");
									}
								}
							} else if (total > 1) { // book multiple seat one way ticket
								System.out.println("---------------------");
								System.out.println("請輸入您欲預定的車次，或者輸入R返回: \n" + "Eg. 0661");
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
										System.out.println("輸入格式錯誤，請重新輸入，或者輸入R返回");
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
									System.out.println("請輸入您的身分證字號或護照號碼，或者輸入R返回: ");
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
										System.out.println("請輸入您的身分證字號或護照號碼，或者輸入R返回: ");
									}
								}
								if (r) {
									break;
								} else {
									System.out.println("訂位完成!");
									break;
								}
							}
						} else if (c.equals("B")) { // round trip booking
							System.out.println("---------------------");
							System.out.println("請照順序輸入您的訂票資料並以空格間隔，或者輸入R返回: \n"
									+ "去程日期 去程時間 回程日期 回程時間 去程起站 去程迄站 艙等(一般or商務) 成人票張數 大學生優惠票張數\n"
									+ "Eg. 2021-07-02 14:20 2021-07-03 14:20 台北 台中 商務 1 0");
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
											throw new TimeException("無法退訂，距離發車時間已不到30分鐘，請重新輸入或輸入R返回");
										}
										
										if (!checkDate(date1) || !checkDate(date2)) {
											throw new DateException("輸入日期應為今日及距今日28日(含)以內之日期，請重新輸入，或者輸入R返回");
										} else {
											SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
											if (differentDays(ft.parse(date1), ft.parse(date2)) < 0) {
												throw new DateException("回程日期應在去程日期之後，請重新輸入，或者輸入R返回");
											} else if (differentDays(ft.parse(date1), ft.parse(date2)) == 0) {
												SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
												if (sdf.parse(date2).getTime() <= sdf.parse(time1).getTime()) {
													throw new DateException("回程時間應在去程時間之後，請重新輸入，或者輸入R返回");
												}
											}
										}
										
										start = station.getEnStationName(info[4]);
										end = station.getEnStationName(info[5]);
										adult = Integer.valueOf(info[7]);
										college = Integer.valueOf(info[8]);
										if (adult + college > 4 || adult + college <= 0 || adult < 0 || college < 0) {
											throw new NoOfTicketException("訂票總張數應至少1張並至多4張，請重新輸入，或者輸入R返回");
										}
										if (info[6].equals("一般")) {
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
										} else if (info[6].equals("商務")) {
											carClass = Seat.Class.Business;
											if (college != 0) {
												throw new ClassException("輸入資訊錯誤，商務艙無販售大學生優惠票，請重新輸入，或者輸入R返回");
											} else {
												System.out.println("---------------------");
												search.generalSearchAndPrint(date1, start, end, time1);
												search.generalSearch(date2, end, start, time2);
												break;
											}
										} else {
											throw new ClassException("輸入艙等錯誤，請重新輸入，或者輸入R返回");
										}
									}
								} catch (ParseException e) {
									System.out.println("輸入日期或時間格式錯誤，請重新輸入，或者輸入R返回");
								} catch (DateException e) {
									System.out.println(e);
								} catch (StationNameException e) {
									System.out.println("輸入站名錯誤，請重新輸入，或者輸入R返回");
								} catch (java.util.InputMismatchException e) {
									System.out.println("輸入格式錯誤，請重新輸入，或者輸入R返回");
								} catch (ClassException e) {
									System.out.println(e);
								} catch (NoOfTicketException e) {
									System.out.println(e);
								} catch (TimeException e) {
									System.out.println("輸入時間錯誤，請重新輸入或輸入R返回");
								}
							}

							String tracking = null; // initialized to make compiler happy
							String pref = null;
							int total = adult + college;
							if (total == 1) { // book single seat round trip ticket
								System.out.println("---------------------");
								System.out.println("請選擇您欲預定的去程車次並輸入座位偏好(靠窗/靠走道/不指定)並以空格間隔，" + "或者輸入R返回: 車次 座位偏好\n"
										+ "Eg. 0611 靠窗");
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
												if (pref.equals("靠窗")) {
													seatType = Seat.SeatType.Window;
													break;
												} else if (pref.equals("靠走道")) {
													seatType = Seat.SeatType.Aisle;
													break;
												} else if (pref.equals("不指定")) {
													break;
												} else {
													throw new PrefException("輸入錯誤，請重新輸入，或者輸入R返回");
												}
											}
										}
									} catch (java.util.InputMismatchException e) {
										System.out.println("輸入格式錯誤，請重新輸入，或者輸入R返回");
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
									System.out.println("輸入時間錯誤，請重新輸入或輸入R返回");
								}
								System.out.println("---------------------");
								System.out.println("請選擇您欲預定的回程車次，或者輸入R返回: \n" + "Eg. 0611");
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
										System.out.println("輸入格式錯誤，請重新輸入，或者輸入R返回");
									} catch (BookingException e) {
										System.out.println(e + "，請重新輸入，或者輸入R返回");
									} catch (PriceException e) {
										System.out.println(e + "，請重新輸入，或者輸入R返回");
									} catch (LeftSeatException e) {
										System.out.println(e);
									}
								}
							} else if (total > 1) { // book multiple seat round trip ticket
								System.out.println("---------------------");
								System.out.println("請選擇您欲預定的去程車次，或者輸入R返回: \n" + "Eg. 0611");
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
										System.out.println("輸入格式錯誤，請重新輸入，或者輸入R返回");
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
									System.out.println("輸入時間錯誤，請重新輸入或輸入R返回");
								}

								System.out.println("請選擇您欲預定的回程車次，或者輸入R返回: \n" + "Eg. 0611");
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
										System.out.println("輸入格式錯誤，請重新輸入，或者輸入R返回");
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
									System.out.println("請輸入您的身分證字號或護照號碼，或者輸入R返回: ");
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
										System.out.println("請輸入您的身分證字號或護照號碼，或者輸入R返回: ");
									}
								}
								if (r) {
									break;
								} else {
									System.out.println("訂位完成!");
									break;
								}
							}
						} else if (c.equals("R")) {
							break;
						}
					}
					System.out.println("---------------------");
					System.out.println("請選擇您需要的服務: A.訂票 B.退票/修改 C.查詢");
					break;
				} else if (input.equals("B")) { // refund or revise
					System.out.println("---------------------");
					System.out.println("請輸入您需要的服務: A.取消訂位 B.修改訂位 R.返回");
					String c = s.nextLine().toUpperCase();
					while (true) {
						if (c.equals("A")) { // 取消訂位
							System.out.println("---------------------");
							System.out.println(
									"請照順序輸入您的身分證字號(或護照號碼)和您要取消的訂位代號並以空格間隔，或者輸入R返回: \n" + "Eg. A12341234 999999(共6位數字)");
							while (true) {
								try {
									String[] info = s.nextLine().split("\\s+");
									if (info[0].equals("R")) {
										break;
									}
									if (info.length != 2) {
										throw new java.util.InputMismatchException("輸入格式錯誤，請重新輸入，或者輸入R返回");
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
										throw new OrderNoException("查無此訂單編號!請確認輸入之訂單編號是否正確並重新輸入，或者輸入R返回");
									}
									
									BookingInfo bk = Booking.bookingInfo.get(orderNo);
									String time = bk.getDate1() + " " + bk.getDepartureTime1();
									if(!diffReviseTimes(time)) {
										throw new TimeException("無法退訂，距離發車時間已不到三小時，請重新輸入或輸入R返回");
									}
									
									RefundOrRevise cancel = new RefundOrRevise(m.booking);
									cancel.cancelTicket(orderNo);
									user.removeOrderNo(orderNo);
									break;
								} catch (java.util.InputMismatchException e) {
									System.out.println(e);
								} catch (UserException e) {
									System.out.println("查無該用戶資料，請重新輸入或輸入R返回");
								} catch (OrderNoException e) {
									System.out.println(e);
								} catch (ParseException e) {
									System.out.println("輸入日期或時間格式錯誤，請重新輸入或輸入R返回");
								} catch (TimeException e) {
									System.out.println(e);
								}
							}
							break;
						} else if (c.equals("B")) { // 修改訂位
							System.out.println("---------------------");
							System.out.println("請照順序輸入您的身分證字號(或護照號碼)、您欲修改的訂位代號和欲退票的票種張數並以空格間隔，或者輸入R返回: \n"
									+ "身分證字號 訂位代號 欲退票成人票張數 欲退票大學生優惠票張數\n" + "Eg. A12341234 999999(共6位數字) 1 0");
							while (true) {
								try {
									String[] info = s.nextLine().split("\\s+");
									if (info[0].equals("R")) {
										break;
									}
									if (info.length != 4) {
										throw new java.util.InputMismatchException("輸入格式錯誤，請重新輸入，或者輸入R返回");
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
										throw new OrderNoException("查無此訂單編號!請確認輸入之訂單編號是否正確並重新輸入，或者輸入R返回");
									}
									int adult = Integer.valueOf(info[2]);
									int college = Integer.valueOf(info[3]);
									if(adult + college == 0) {
										throw new ReviseException("輸入退票張數錯誤，退票總張數應至少1張，請重新輸入或者輸入R返回");
									}
									
									BookingInfo bk = Booking.bookingInfo.get(orderNo);
									String time = bk.getDate1() + " " + bk.getDepartureTime1();
									if(!diffReviseTimes(time)) {
										throw new TimeException("無法退訂，距離發車時間已不到三小時，請重新輸入或輸入R返回");
									}
									
									RefundOrRevise revise = new RefundOrRevise(m.booking);
									System.out.println("---------------------");
									revise.reviseTicket(orderNo, adult, college);
									break;
								} catch (java.util.InputMismatchException e) {
									System.out.println(e);
								} catch (UserException e) {
									System.out.println("查無該用戶資料，請重新輸入或者輸入R返回");
								} catch (OrderNoException e) {
									System.out.println(e);
								} catch (ReviseException e) {
									System.out.println(e);
								}catch (ParseException e) {
									System.out.println("輸入日期或時間格式錯誤，請重新輸入或輸入R返回");
								} catch (TimeException e) {
									System.out.println(e);
								}
							}
							break;
						} else if (c.equals("R")) { // 返回
							break;
						} else {
							System.out.println("輸入錯誤， 請重新輸入，或者輸入R返回");
						}
					}
					System.out.println("---------------------");
					System.out.println("請選擇您需要的服務: A.訂票 B.退票/修改 C.查詢");
					break;
				} else if (input.equals("C")) { // search
					System.out.println("---------------------");
					System.out.println("請選擇您要查詢的項目: A.查詢訂票資訊(有訂位代號) B.查詢訂票資訊(無訂位代號) C.當日時刻表 D.優惠車次 R.返回");
					String c = s.nextLine().toUpperCase();
					while (true) {
						if (c.equals("A")) { // A.查詢訂票資訊(有訂位代號)
							System.out.println("---------------------");
							System.out.println("請輸入您當時的訂票資訊並以空格間隔，或者輸入R返回 格式為: 身分證字號(或護照號碼) 訂位代號(共6位數)\n"
									+ "Eg. A12341234 999999");
							while (true) {
								try {
									String[] info = s.nextLine().split("\\s+");
									if (info[0].equals("R")) {
										break;
									}
									if (info.length != 2) {
										throw new java.util.InputMismatchException("輸入格式錯誤，請重新輸入，或者輸入R返回");
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
						} else if (c.equals("B")) { // 查詢訂票資訊(無訂位代號)
							System.out.println("---------------------");
							System.out.println("請輸入您當時的訂票資訊並以空格間隔，或者輸入R返回 格式為: 身分證字號(或護照號碼) 起站 迄站 日期 車次\n"
									+ "Eg. A12341234 台北 台中 2021-07-05 0639");
							while (true) {
								try {
									String[] info = s.nextLine().split("\\s+");
									if (info[0].equals("R")) {
										break;
									}
									if (info.length != 5) {
										throw new java.util.InputMismatchException("輸入格式錯誤，請重新輸入，或者輸入R返回");
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
									System.out.println("輸入站名錯誤，請重新輸入，或者輸入R返回");
								}
							}
							break;
						} else if (c.equals("C")) { // 查詢優惠車次
							System.out.println("---------------------");
							System.out.println("請依格式輸入欲查詢的日期，或者輸入R返回: \n" + "Eg.2021-06-23");
							while (true) {
								try {
									String[] info = s.nextLine().split("\\s+");
									if (info[0].equals("R")) {
										break;
									}else {
										if (info.length != 1) {
											throw new java.util.InputMismatchException("輸入格式錯誤，請重新輸入，或者輸入R返回");
										}else {
											System.out.println("---------------------");
											new Search(info[0]);
											break;
										}
									}
								} catch (java.util.InputMismatchException e) {
									System.out.println(e);
								} catch (NoSuchElementException | ParseException e) {
									System.out.println("輸入格式錯誤，請重新輸入，或者輸入R返回");
								}
							}
							break;
						} else if (c.equals("D")) { // 查詢優惠車次
							System.out.println(
									"請依照順序輸入欲查詢的日期、起訖站和時間並以空格間隔，或者輸入R返回: 日期 起站 迄站 時間\n" + "Eg.2021-06-23 台北 台中 19:20");
							while (true) {
								try {
									String[] info = s.nextLine().split("\\s+");
									if (info[0].equals("R")) {
										break;
									}else {
										if (info.length != 4) {
											throw new java.util.InputMismatchException("輸入格式錯誤，請重新輸入，或者輸入R返回");
										}else {
											String start = info[1];
											String end = info[2];
											search.earlySearch(info[0], station.getEnStationName(start),
													station.getEnStationName(end), info[3]);
											search.uniSearch(info[0], station.getEnStationName(start),
													station.getEnStationName(end), info[3]);
											System.out.println("------早鳥票車次-------");
											search.earlySearchAndPrint(info[0], station.getEnStationName(start),
													station.getEnStationName(end), info[3]);
											System.out.println("----大學生優惠票車次-----");
											search.uniSearchAndPrint(info[0], station.getEnStationName(start),
													station.getEnStationName(end), info[3]);
											break;
										}
									}
								} catch (java.util.InputMismatchException e) {
									System.out.println(e);
								} catch (ParseException e) {
									System.out.println("輸入格式錯誤，請重新輸入，或者輸入R返回");
								} catch (StationNameException e) {
									System.out.println("輸入站名錯誤，請重新輸入，或者輸入R返回");
								} catch (TimeException e) {
									System.out.println("輸入時間錯誤，請重新輸入或輸入R返回");
								}
							}
							break;
						} else if (c.equals("R")) {
							break;
						} else {
							System.out.println("輸入錯誤， 請重新輸入，或者輸入R返回");
						}
					}
					System.out.println("---------------------");
					System.out.println("請選擇您需要的服務: A.訂票 B.退票/修改 C.查詢");
					break;
				} else if (input.equals("L")) { // end the whole program
					System.exit(0);
				} else {
					System.out.println("輸入錯誤， 請重新輸入");
					break;
				}
			}

		}

	}

}
