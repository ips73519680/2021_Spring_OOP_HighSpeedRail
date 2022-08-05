package com.system;

public class BookingInfo {

	private int college1;
	private int price;//
	private int adult1;
	private String start1;
	private String end1;
	private String departureTime1;
	private String arrivalTime1;
	private String[] seats1;
	private String seat1;
	private String trainNo2 = null;
	private String date2 = null;
	// private int college2 = -1;
	// private int adult2= -1;
	private String start2 = null;
	private String end2 = null;
	private String departureTime2 = null;
	private String arrivalTime2 = null;
	private String[] seats2;
	private String seat2;

	/**
	 * Blank constructor
	 */
	public BookingInfo() {
	}
	
	/**
	 * constructor when single one way ticket
	 * 
	 * @param date1
	 * @param college1
	 * @param adult1
	 * @param start1
	 * @param end1
	 * @param departureTime1
	 * @param arrivalTime1
	 * @param seat1
	 */
	public BookingInfo(String date1, String trainNo1, int college1, int adult1, String start1, String end1,
			String departureTime1, String arrivalTime1, String seat1,int price) {
		super();
		this.date1 = date1;
		this.trainNo1 = trainNo1;
		this.college1 = college1;
		this.adult1 = adult1;
		this.start1 = start1;
		this.end1 = end1;
		this.departureTime1 = departureTime1;
		this.arrivalTime1 = arrivalTime1;
		this.seat1 = seat1;
		this.price = price;
	}

	/**
	 * constructor when single round trip ticket
	 * 
	 * @param date1
	 * @param college1
	 * @param adult1
	 * @param start1
	 * @param end1
	 * @param departureTime1
	 * @param arrivalTime1
	 * @param seat1
	 * @param date2
	 * @param college2
	 * @param adult2
	 * @param start2
	 * @param end2
	 * @param departureTime2
	 * @param arrivalTime2
	 * @param seat2
	 */
	public BookingInfo(String date1, String trainNo1, int college1, int adult1, String start1, String end1,
			String departureTime1, String arrivalTime1, String seat1, String date2, String trainNo2, String start2,
			String end2, String departureTime2, String arrivalTime2, String seat2,int price) {
		super();
		this.date1 = date1;
		this.trainNo1 = trainNo1;
		this.college1 = college1;
		this.adult1 = adult1;
		this.start1 = start1;
		this.end1 = end1;
		this.departureTime1 = departureTime1;
		this.arrivalTime1 = arrivalTime1;
		this.seat1 = seat1;
		this.date2 = date2;
		this.trainNo2 = trainNo2;
		// this.college2 = college2;
		// this.adult2 = adult2;
		this.start2 = start2;
		this.end2 = end2;
		this.departureTime2 = departureTime2;
		this.arrivalTime2 = arrivalTime2;
		this.seat2 = seat2;
		this.price = price;
	}

	/**
	 * constructor when multiple one way ticket
	 * 
	 * @param date1
	 * @param college1
	 * @param adult1
	 * @param start1
	 * @param end1
	 * @param departureTime1
	 * @param arrivalTime1
	 * @param seat1
	 */
	public BookingInfo(String date1, String trainNo1, int college1, int adult1, String start1, String end1,
			String departureTime1, String arrivalTime1, String[] seat1,int price) {
		super();
		this.date1 = date1;
		this.trainNo1 = trainNo1;
		this.trainNo1 = trainNo1;
		this.college1 = college1;
		this.adult1 = adult1;
		this.start1 = start1;
		this.end1 = end1;
		this.departureTime1 = departureTime1;
		this.arrivalTime1 = arrivalTime1;
		this.seats1 = seat1;
		this.price = price;
	}

	/**
	 * constructor when multiple round trip ticket
	 * 
	 * @param date1
	 * @param college1
	 * @param adult1
	 * @param start1
	 * @param end1
	 * @param departureTime1
	 * @param arrivalTime1
	 * @param seat1
	 * @param date2
	 * @param college2
	 * @param adult2
	 * @param start2
	 * @param end2
	 * @param departureTime2
	 * @param arrivalTime2
	 * @param seat2
	 */
	public BookingInfo(String date1, String TrianNo1, int college1, int adult1, String start1, String end1,
			String departureTime1, String arrivalTime1, String[] seat1, String date2, String trianNo2, String start2,
			String end2, String departureTime2, String arrivalTime2, String[] seats2,int price) {
		this.date1 = date1;
		this.trainNo1 = TrianNo1;
		this.college1 = college1;
		this.adult1 = adult1;
		this.start1 = start1;
		this.end1 = end1;
		this.departureTime1 = departureTime1;
		this.arrivalTime1 = arrivalTime1;
		this.seats1 = seat1;
		this.date2 = date2;
		this.trainNo2 = trianNo2;
		this.start2 = start2;
		this.end2 = end2;
		this.departureTime2 = departureTime2;
		this.arrivalTime2 = arrivalTime2;
		this.seats2 = seats2;
		this.price = price;
	}

	private String date1;

	public String getDate1() {
		return date1;
	}

	public int getCollege1() {
		return college1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public void setTrainNo1(String trainNo1) {
		this.trainNo1 = trainNo1;
	}

	public void setCollege1(int college1) {
		this.college1 = college1;
	}

	public void setAdult1(int adult1) {
		this.adult1 = adult1;
	}

	public void setStart1(String start1) {
		this.start1 = start1;
	}

	public void setEnd1(String end1) {
		this.end1 = end1;
	}

	public void setDepartureTime1(String departureTime1) {
		this.departureTime1 = departureTime1;
	}

	public void setArrivalTime1(String arrivalTime1) {
		this.arrivalTime1 = arrivalTime1;
	}

	public void setSeats1(String[] seats1) {
		this.seats1 = seats1;
	}

	public void setSeat1(String seat1) {
		this.seat1 = seat1;
	}

	public void setTrainNo2(String trainNo2) {
		this.trainNo2 = trainNo2;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
	}

	public void setStart2(String start2) {
		this.start2 = start2;
	}

	public void setEnd2(String end2) {
		this.end2 = end2;
	}

	public void setDepartureTime2(String departureTime2) {
		this.departureTime2 = departureTime2;
	}

	public void setArrivalTime2(String arrivalTime2) {
		this.arrivalTime2 = arrivalTime2;
	}

	public void setSeats2(String[] seats2) {
		this.seats2 = seats2;
	}

	public void setSeat2(String seat2) {
		this.seat2 = seat2;
	}

	public int getAdult1() {
		return adult1;
	}

	public String getStart1() {
		return start1;
	}

	public String getEnd1() {
		return end1;
	}

	public String getDepartureTime1() {
		return departureTime1;
	}

	public String getArrivalTime1() {
		return arrivalTime1;
	}

	public String[] getSeats1() {
		return seats1;
	}

	public String getSeat1() {
		return seat1;
	}

	public String getDate2() {
		return date2;
	}

	public String getStart2() {
		return start2;
	}

	public String getEnd2() {
		return end2;
	}

	public String getDepartureTime2() {
		return departureTime2;
	}

	public String getArrivalTime2() {
		return arrivalTime2;
	}

	public String[] getSeats2() {
		return seats2;
	}

	public String getSeat2() {
		return seat2;
	}

	public String trainNo1;

	public String getTrainNo1() {
		return trainNo1;
	}

	public String getTrainNo2() {
		return trainNo2;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	

}