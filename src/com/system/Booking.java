package com.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.exception.BookingException;
import com.exception.LeftSeatException;
import com.exception.PriceException;
import com.readjson.Price;
import com.readjson.Search;
import com.readjson.Seat;

public class Booking {

	private String start;// 起站站名
	private String end;// 終站站名
	private String departure;// 出發時間
	private String arrival;// 抵達時間
	private String departure1;
	private String arrival1;
	private String ticketType;// 分為大學生或者全票
	private String name;
	int totalticket;
	int adult = 0; // 成人票張數
	int college = 0; // 大學生票張數
	int price;
	private boolean hasadult = false;
	private boolean hascollege = false;
	Seat.Class seatClass = Seat.Class.Standard; // 是否為商務座
	Seat.SeatType type = Seat.SeatType.NoPreference;
	String trackingNO;
	Seat seat;
	Price p;
	private ArrayList<Integer> trackingNoList = new ArrayList<Integer>();
	public static HashMap<String, Seat> train = new HashMap<String, Seat>();// 存每班車的座位狀況
	public static HashMap<String, BookingInfo> bookingInfo = new HashMap<String, BookingInfo>();

	public Booking() {
		p = new Price();
	}

	public static int SoldNumber(String trainname) {
		if (train.get(trainname) == null) {
			return 0;
		} else {
			return train.get(trainname).soldNumOfStandardSeats();
		}
	}

	public String Bookticket(String trainNo1, String date1, String start, String end, Seat.Class car,
			Seat.SeatType type, int adult, int college) throws BookingException, PriceException, LeftSeatException {
		this.start = start;
		this.end = end;
		try {
			if (car == Seat.Class.Business && college != 0) {
				String errormsg = "商務車次不適用大學生優惠";
				throw new BookingException(errormsg);
			}

			Search sr = new Search();
			HashMap<String, String> dtime = new HashMap<String, String>();
			dtime = sr.DtimeSearch(trainNo1);
			departure = dtime.get(start);
			arrival = dtime.get(end);
			name = "No" + trainNo1 + "_" + date1;
			totalticket = adult + college;
//		ReadPrice rp = new ReadPrice();
//		price = rp.getprice(start, end, seatType);
			if (totalticket == 1) {
				price = priceOfoneTicket(trainNo1, date1, start, end, car, type, adult, college);
				String seat = 
						Bookoneticket(trainNo1, date1, car, type, adult, college);
				System.out.println("票價: " + price);
				trackingNO = generatetrackingNo().toString();
				BookingInfo bkif = new BookingInfo(date1, trainNo1, college, adult, start, end, departure, arrival,
						seat, price);
				bookingInfo.put(trackingNO, bkif);
				return trackingNO;
			} else {
				price = priceOfmulipleticket(trainNo1, date1, start, end, car, type, adult, college);
				String[] seats = BookMulipleTicket(trainNo1, date1, car, type, adult, college);
				System.out.println("票價: " + price);
				trackingNO = generatetrackingNo().toString();
				BookingInfo bkif = new BookingInfo(date1, trainNo1, college, adult, start, end, departure, arrival,
						seats, price);
				bookingInfo.put(trackingNO, bkif);
				return trackingNO;
			}
		} catch (BookingException e) {
			System.out.println(e.getMessage());
		}
		return trackingNO;
	}

	public String BookticketRoundtrip(String trainNo1, String trainNo2, String date1, String date2, String start,
			String end, Seat.Class car, Seat.SeatType type, int adult, int college)
			throws BookingException, PriceException, LeftSeatException {
		this.start = start;
		this.end = end;
		totalticket = adult + college;
		if (totalticket == 1) {
			Search sr = new Search();
			HashMap<String, String> dtime = new HashMap<>();
			dtime = sr.DtimeSearch(trainNo1);
			departure = dtime.get(start);
			arrival = dtime.get(end);
			departure1 = departure;
			arrival1 = arrival;
			// name = "No" + trainNo1 + "_" + date1;
			price = priceOfoneTicket(trainNo1, date1, start, end, car, type, adult, college)
					+ priceOfoneTicket(trainNo2, date2, end, start, car, type, adult, college);
			String seat1 = Bookoneticket(trainNo1, date1, car, type, adult, college);
			System.out.println("-----------------------------");
			this.start = end;
			this.end = start;
			dtime = sr.DtimeSearch(trainNo2);
			departure = dtime.get(end);
			arrival = dtime.get(start);
			// name = "No" + trainNo2 + "_" + date2;
			String seat2 = Bookoneticket(trainNo2, date2, car, type, adult, college);
			System.out.println("票價: " + price);
			trackingNO = generatetrackingNo().toString();
			BookingInfo bkif = new BookingInfo(date1, trainNo1, college, adult, start, end, departure1, arrival1, seat1,
					date2, trainNo2, end, start, departure, arrival, seat2, price);
			bookingInfo.put(trackingNO, bkif);
			return trackingNO;
		} else {
			Search sr = new Search();
			HashMap<String, String> dtime = new HashMap<>();
			dtime = sr.DtimeSearch(trainNo1);
			departure = dtime.get(start);
			arrival = dtime.get(end);
			departure1 = departure;
			price = priceOfmulipleticket(trainNo1, date1, start, end, car, type, adult, college)
					+ priceOfmulipleticket(trainNo2, date2, end, start, car, type, adult, college);
			String[] seats1 = BookMulipleTicket(trainNo1, date1, car, type, adult, college);
			System.out.println("-----------------------------");
			this.start = end;
			this.end = start;
			dtime = sr.DtimeSearch(trainNo2);
			departure = dtime.get(end);
			arrival = dtime.get(start);
			String[] seats2 = BookMulipleTicket(trainNo2, date2, car, type, adult, college);
			System.out.println("票價: " + price);
			trackingNO = generatetrackingNo().toString();
			BookingInfo bkif = new BookingInfo(date1, trainNo1, college, adult, start, end, departure1, arrival1,
					seats1, date2, trainNo2, end, start, departure, arrival, seats2, price);
			bookingInfo.put(trackingNO, bkif);
			return trackingNO;

		}

	}

	/**
	 * booking just one ticket.
	 * input(trainNo,date,start,end,seatclass,seatpreference,adultnum,collegenum)
	 * 
	 * @param trainNo the train that the user want to take
	 * @param date    the date the train depart
	 * @param car     the class of the car
	 * @param type    Window, Aisle or NoPreference
	 * @throws LeftSeatException 
	 */
	public String Bookoneticket(String trainNo, String date, Seat.Class seatClass, Seat.SeatType type, int adult,
			int college) throws LeftSeatException {
		String name = "No" + trainNo + "_" + date;
		if (train.containsKey(name) == false) {
			Seat seat = new Seat();
			train.put(name, seat);
		}
		String seatNo = train.get(name).getOneSeat(seatClass, type);
		if (adult == 1 & college == 0) {
			ticketType = "Adult";
		}
		if (college == 1 & adult == 0) {
			ticketType = "College";
		}
		if (seatNo != null) {
			System.out.println("日期: " + date);
			if(ticketType.equals("Adult")) {
				System.out.println("成人票: 1");
			}else {
				System.out.println("大學生優惠票: 1");
			}
			System.out.println("車次: " + trainNo);
			System.out.println(changeToChinese(start) + " " + departure + " -> " + changeToChinese(end) + " " + arrival);
			System.out.println("座位編號: " + seatNo);
			return seatNo;
		}
		return null;
	}

	/**
	 * @param trainNo
	 * @param date
	 * @param start
	 * @param end
	 * @param car
	 * @param adult
	 * @param college
	 * @throws BookingException
	 * @throws LeftSeatException 
	 */
	public String[] BookMulipleTicket(String trainNo, String date, Seat.Class seatClass, Seat.SeatType type, int adult,
			int college) throws BookingException, LeftSeatException {
		totalticket = adult + college;
		String name = "No" + trainNo + "_" + date;
		if (train.containsKey(name) == false) {
			Seat seat = new Seat();
			train.put(name, seat);
		}
		String[] seatNos = train.get(name).getMultipleSeat(seatClass, totalticket);
		for (int i = 0; i < seatNos.length; i++) {
			if (seatNos[i] == null) {
				String errormsg = "請重新選擇車次";
				throw new BookingException(errormsg);
			}
		}
		System.out.println("日期: " + date);
		System.out.println("成人票: " + String.valueOf(adult));
		System.out.println("大學生優惠票: " + String.valueOf(college));
		System.out.println("車次: " + trainNo);
		System.out.println(changeToChinese(start) + " " + departure + " -> " + changeToChinese(end) + " " + arrival);
		System.out.print("座位編號: ");
		for (int i = 0; i < seatNos.length; i++) {
			System.out.print(seatNos[i] + " ");
		}
		System.out.println("");
		return seatNos;

	}

	public Integer generatetrackingNo() {
		int min = 100000;
		int max = 999999;
		int trackingNOint = new Random().nextInt(max - min + 1) + min;
		Integer trackingNO = Integer.valueOf(trackingNOint);
		while (trackingNoList.contains(trackingNO)) {
			trackingNOint = new Random().nextInt(max - min + 1) + min;
			trackingNO = Integer.valueOf(trackingNOint);
		}
		trackingNoList.add(trackingNO);
		System.out.println("訂位代號: " + trackingNO.toString());
		return trackingNO;

	}

	public int priceOfoneTicket(String trainNo, String date, String start, String end, Seat.Class car,
			Seat.SeatType type, int adult, int college) throws PriceException {
		String nametocheck = "No" + trainNo + "_" + date;
		if (college > 0) {
			hascollege = true;
		}
		if (adult > 0) {
			hasadult = true;
		}
		int priceadult = 0;
		int pricecollege = 0;

		int soldTicket = SoldNumber(nametocheck) + 1;
		if (hasadult == true) {
			priceadult = p.price(trainNo, start, end, date, car, soldTicket, true, false);
		}
		if (hascollege == true) {
			priceadult = p.price(trainNo, start, end, date, car, soldTicket, false, true);
		}
		price = priceadult + pricecollege;
		return price;
	}

	public int priceOfmulipleticket(String trainNo, String date, String start, String end, Seat.Class car,
			Seat.SeatType type, int adult, int college) throws PriceException {
		String nametocheck = "No" + trainNo + "_" + date;
		int totaladultprice = 0;
		int totalcollegeprice = 0;
		int soldTicket = SoldNumber(nametocheck) + 1;
		if (college > 0) {
			hascollege = true;
		}
		if (adult > 0) {
			hasadult = true;
		}
		if (hasadult == true) {
			int[] priceadult = new int[adult];
			for (int i = 0; i < adult; i++) {
				priceadult[i] = p.price(trainNo, start, end, date, car, soldTicket + i, true, false);
			}
			for (int j = 0; j < priceadult.length; j++) {
				totaladultprice += priceadult[j];
			}
		}
		if (hascollege == true) {
			int pricecollege = p.price(trainNo, start, end, date, car, soldTicket, false, true);
			totalcollegeprice = pricecollege * college;
		}
		price = totaladultprice + totalcollegeprice;
		return price;
	}

	public String changeToChinese(String stationname) {
		switch (stationname) {
		case "Nangang":
			return "南港";
		case "Taipei":
			return "台北";
		case "Banciao":
			return "板橋";
		case "Taoyuan":
			return "桃園";
		case "Hsinchu":
			return "新竹";
		case "Taichung":
			return "台中";
		case "Chiayi":
			return "嘉義";
		case "Tainan":
			return "台南";
		case "Zuoying":
			return "左營";
		case "Miaoli":
			return "苗栗";
		case "Yunlin":
			return "雲林";
		case "Changhua":
			return "彰化";
		default:
			return "英文站名可能打錯了哦";
		}
	}
}