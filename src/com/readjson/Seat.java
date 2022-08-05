package com.readjson;

import org.json.*;

import com.exception.LeftSeatException;

public class Seat {

	public enum SeatType {
		Window, Aisle, NoPreference
	};

	public enum Class {
		Standard, Business
	};

	// load each car's information
	ReadSeat readSeat = new ReadSeat();
	JSONObject[] seats = ReadSeat.getSeats();

	private boolean[][][] leftSeats = new boolean[12][][];
	private String[][][] noOfSeats = new String[12][][];
	private static int totalNumberOfStandardSeat;

	/**
	 * Constructor for Seat.
	 */
	public Seat() {
		readSeat();
		totalNumberOfStandardSeat = 0;
		for (int i = 0; i < noOfSeats.length; i++) {
			if (i + 1 == 6)
				continue;
			for (int j = 0; j < noOfSeats[i].length; j++) {
				for (int k = 0; k < noOfSeats[i][j].length; k++) {
					totalNumberOfStandardSeat++;
				}
			}
		}
	}

	/**
	 * Read information from Seat.json
	 */
	private void readSeat() {

		// set every seat as unsold
		for (int i = 0; i < seats.length; i++) { // seat.size() == the number of cars

			if (i + 1 == 6) { // business seats
				leftSeats[i] = new boolean[17][];

				for (int j = 0; j < 17; j++) {
					Integer index = j + 1;
					int num = seats[i].getJSONArray(index.toString()).length();
					leftSeats[i][j] = new boolean[num];
				}

				for (int j = 0; j < 17; j++) {
					for (int k = 0; k < leftSeats[i][j].length; k++) {
						leftSeats[i][j][k] = true;
					}
				}
			} else { // standard seats
				int count = 1;

				while (true) {
					try {
						seats[i].getJSONArray(String.valueOf(count));
						count++;
					} catch (Exception e) {
						break;
					}
				}

				count--;
				leftSeats[i] = new boolean[count][];

				for (int j = 0; j < count; j++) {
					Integer index = j + 1;
					int num = seats[i].getJSONArray(index.toString()).length();
					leftSeats[i][j] = new boolean[num];
				}
				for (int j = 0; j < count; j++) {
					for (int k = 0; k < leftSeats[i][j].length; k++) {
						leftSeats[i][j][k] = true;
					}
				}
			}
		}

		// 設定每個座位的編號
		for (int i = 0; i < seats.length; i++) {

			if (i + 1 == 6) { // 商務座
				noOfSeats[i] = new String[17][];

				for (int j = 0; j < 17; j++) {
					Integer index = j + 1;
					int num = seats[i].getJSONArray(index.toString()).length();
					noOfSeats[i][j] = new String[num];
				}

				for (int j = 0; j < 17; j++) {
					Integer index = j + 1;
					for (int k = 0; k < leftSeats[i][j].length; k++) {
						if (j + 1 < 10) {
							noOfSeats[i][j][k] = "0" + String.valueOf(i + 1) + "0" + String.valueOf(j + 1)
									+ (String) seats[i].getJSONArray(index.toString()).get(k);

						} else {
							noOfSeats[i][j][k] = "0" + String.valueOf(i + 1) + String.valueOf(j + 1)
									+ (String) seats[i].getJSONArray(index.toString()).get(k);
						}
					}
				}
			} else { // 一般座
				Integer count = 1;

				while (true) {
					try {
						seats[i].getJSONArray(count.toString());
						count++;
					} catch (Exception e) {
						break;
					}
				}

				count--;
				noOfSeats[i] = new String[count][];

				for (int j = 0; j < count; j++) {
					Integer index = j + 1;
					int num = seats[i].getJSONArray(index.toString()).length();
					noOfSeats[i][j] = new String[num];
				}
				for (int j = 0; j < count; j++) {
					Integer index = j + 1;
					for (int k = 0; k < noOfSeats[i][j].length; k++) {
						if (i + 1 < 10) {
							if (j + 1 < 10) {
								noOfSeats[i][j][k] = "0" + String.valueOf(i + 1) + "0" + String.valueOf(j + 1)
										+ (String) seats[i].getJSONArray(index.toString()).get(k);

							} else {
								noOfSeats[i][j][k] = "0" + String.valueOf(i + 1) + String.valueOf(j + 1)
										+ (String) seats[i].getJSONArray(index.toString()).get(k);
							}
						} else {
							if (j + 1 < 10) {
								noOfSeats[i][j][k] = String.valueOf(i + 1) + "0" + String.valueOf(j + 1)
										+ (String) seats[i].getJSONArray(index.toString()).get(k);

							} else {
								noOfSeats[i][j][k] = String.valueOf(i + 1) + String.valueOf(j + 1)
										+ (String) seats[i].getJSONArray(index.toString()).get(k);

							}
						}
					}
				}
			}
		}

	}

	/**
	 * Calculate the number of unsold standard seats.
	 * 
	 * @return the number of unsold standard seats
	 */
	public int leftNoOfStandardSeats() {
		int count = 0;
		for (int i = 0; i < leftSeats.length; i++) {
			if (i + 1 == 6) // skip business seat
				continue;
			for (int j = 0; j < leftSeats[i].length; j++) {
				for (int k = 0; k < leftSeats[i][j].length; k++) {
					if (leftSeats[i][j][k] == true) {
						count++;
					}
				}
			}
		}
		return count;
	}

	/**
	 * Calculate the number of unsold business seats.
	 * 
	 * @return the number of unsold business seats
	 */
	public int leftNoOfBusinessSeats() {
		int count = 0;
		for (int i = 0; i < leftSeats[5].length; i++) {
			for (int j = 0; j < leftSeats[5][i].length; j++) {
				if (leftSeats[5][i][j] == true) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * 
	 * @return
	 */
	public int soldNumOfStandardSeats() {
		return totalNumberOfStandardSeat - leftNoOfStandardSeats();
	}

	/**
	 * Check if there's standard window seat
	 * 
	 * @return true if there's still standard window seat
	 */
	public boolean isStandardWindowSeatLeft() {
		for (int i = 0; i < noOfSeats.length; i++) {
			if (i + 1 == 6) // skip business seat
				continue;
			for (int j = 0; j < noOfSeats[i].length; j++) {
				for (int k = 0; k < noOfSeats[i][j].length; k++) {
					if (noOfSeats[i][j][k].charAt(4) == 'A' || noOfSeats[i][j][k].charAt(4) == 'E') {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Check if there's standard aisle seat
	 * 
	 * @return true if there's still standard aisle seat
	 */
	public boolean isStandardAisleSeatLeft() {
		for (int i = 0; i < noOfSeats.length; i++) {
			if (i + 1 == 6) // skip business seat
				continue;
			for (int j = 0; j < noOfSeats[i].length; j++) {
				for (int k = 0; k < noOfSeats[i][j].length; k++) {
					if (noOfSeats[i][j][k].charAt(4) == 'C' || noOfSeats[i][j][k].charAt(4) == 'D') {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Check if there's business window seat
	 * 
	 * @return true if there's still business window seat
	 */
	public boolean isBusinessWindowSeatLeft() {
		for (int i = 0; i < noOfSeats[5].length; i++) {
			for (int k = 0; k < noOfSeats[5][i].length; k++) {
				if (noOfSeats[5][i][k].charAt(4) == 'A' || noOfSeats[5][i][k].charAt(4) == 'E') {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check if there's business aisle seat
	 * 
	 * @return true if there's still business aisle seat
	 */
	public boolean isBusinessAisleSeatLeft() {
		for (int i = 0; i < noOfSeats[5].length; i++) {
			for (int k = 0; k < noOfSeats[5][i].length; k++) {
				if (noOfSeats[5][i][k].charAt(4) == 'C' || noOfSeats[5][i][k].charAt(4) == 'D') {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Get one unsold seat. Please check if there's enough seat by yourself.
	 * 
	 * @param car Standard seat or Business seat
	 * @return the seat No. of the booked ticket
	 */
	public String getSeat(Class car) {
		switch (car) {
		case Standard:
			for (int i = 0; i < noOfSeats.length; i++) {
				if (i + 1 == 6)
					continue; // skip business seat
				for (int j = 0; j < noOfSeats[i].length; j++) {
					for (int k = 0; k < noOfSeats[i][j].length; k++) {
						if (leftSeats[i][j][k]) {
							setSeat(i, j, k);
							return noOfSeats[i][j][k];
						}
					}
				}
			}
			break;
		case Business:
			for (int i = 0; i < noOfSeats[5].length; i++) {
				for (int k = 0; k < noOfSeats[5][i].length; k++) { // check business seat
					if (leftSeats[5][i][k]) {
						setSeat(5, i, k);
						return noOfSeats[5][i][k];
					}
				}
			}
			break;
		}
		return null;
	}

	/**
	 * Get one unsold window seat. Please check if there's enough window seat by
	 * your own first.
	 * 
	 * @param car Standard seat or Business seat
	 * @return the seat No. of the booked ticket
	 */
	public String getWindowSeat(Class car) {
		switch (car) {
		case Standard:
			for (int i = 0; i < noOfSeats.length; i++) {
				if (i + 1 == 6)
					continue; // skip business seat
				for (int j = 0; j < noOfSeats[i].length; j++) {
					for (int k = 0; k < noOfSeats[i][j].length; k++) {
						if (noOfSeats[i][j][k].charAt(4) == 'A' || noOfSeats[i][j][k].charAt(4) == 'E') {
							if (leftSeats[i][j][k]) {
								setSeat(i, j, k);
								return noOfSeats[i][j][k];
							}
						}
					}
				}
			}
			break;
		case Business:
			for (int i = 0; i < noOfSeats[5].length; i++) { // check business seat
				for (int k = 0; k < noOfSeats[5][i].length; k++) {
					if (noOfSeats[5][i][k].charAt(4) == 'A' || noOfSeats[5][i][k].charAt(4) == 'E') {
						if (leftSeats[5][i][k]) {
							setSeat(5, i, k);
							return noOfSeats[5][i][k];
						}
					}
				}
			}
			break;
		}
		return null;
	}

	/**
	 * Get one unsold aisle seat. Please check if there's enough aisle seat by your
	 * own first.
	 * 
	 * @param car Standard seat or Business seat
	 * @return the seat No. of the booked ticket
	 */
	public String getAisleSeat(Class car) {
		switch (car) {
		case Standard:
			for (int i = 0; i < noOfSeats.length; i++) {
				if (i + 1 == 6)
					continue; // skip business seat
				for (int j = 0; j < noOfSeats[i].length; j++) {
					for (int k = 0; k < noOfSeats[i][j].length; k++) {
						if (noOfSeats[i][j][k].charAt(4) == 'C' || noOfSeats[i][j][k].charAt(4) == 'D') {
							if (leftSeats[i][j][k]) {
								setSeat(i, j, k);
								return noOfSeats[i][j][k];
							}
						}
					}
				}
			}
			break;
		case Business:
			for (int i = 0; i < noOfSeats[5].length; i++) { // check business seat
				for (int k = 0; k < noOfSeats[5][i].length; k++) {
					if (noOfSeats[5][i][k].charAt(4) == 'C' || noOfSeats[5][i][k].charAt(4) == 'D') {
						if (leftSeats[5][i][k]) {
							setSeat(5, i, k);
							return noOfSeats[5][i][k];
						}
					}
				}
			}
			break;
		}
		return null;
	}

	/**
	 * Get one seat.
	 * 
	 * @param car  the class of the seat
	 * @param type the seat preference, eg.Window, Aisle
	 * @return the seat NO. have booked. If there's no enough seat in this train,
	 *         return null.
	 * @throws LeftSeatException 
	 */
	public String getOneSeat(Class car, SeatType type) throws LeftSeatException {
		switch (car) {
		case Standard:
			switch (type) {
			case Window:
				if (isStandardWindowSeatLeft()) {
					return getWindowSeat(car);
				} else if (leftNoOfStandardSeats() <= 0) {
					throw new LeftSeatException("您所選車次已無剩餘座位，請選擇其他車次");
//					System.out.println("您所選車次已無剩餘座位，請選擇其他車次");
//					break;
				} else {
					System.out.println("沒有足夠靠窗座位，將為您分配其他條件座位");
					return getSeat(car);
				}
			case Aisle:
				if (isStandardAisleSeatLeft()) {
					return getAisleSeat(car);
				} else if (leftNoOfStandardSeats() <= 0) {
					throw new LeftSeatException("您所選車次已無剩餘座位，請選擇其他車次");
//					System.out.println("您所選車次已無剩餘座位，請選擇其他車次");
//					break;
				} else {
					System.out.println("沒有足夠靠走道座位，將為您分配其他條件座位");
					return getSeat(car);
				}
			case NoPreference:
				if (leftNoOfStandardSeats() > 0) {
					return getSeat(car);
				} else {
					throw new LeftSeatException("您所選車次已無剩餘座位，請選擇其他車次");
//					System.out.println("您所選車次已無剩餘座位，請選擇其他車次");
//					break;
				}
			}
		case Business:
			switch (type) {
			case Window:
				if (isBusinessWindowSeatLeft()) {
					return getWindowSeat(car);
				} else if (leftNoOfBusinessSeats() <= 0) {
					throw new LeftSeatException("您所選車次已無剩餘座位，請選擇其他車次");
//					System.out.println("您所選車次已無剩餘座位，請選擇其他車次");
//					break;
				} else {
					System.out.println("沒有足夠靠窗座位，將為您分配其他條件座位");
					return getSeat(car);
				}
			case Aisle:
				if (isBusinessAisleSeatLeft()) {
					return getAisleSeat(car);
				} else if (leftNoOfBusinessSeats() <= 0) {
					throw new LeftSeatException("您所選車次已無剩餘座位，請選擇其他車次");
//					System.out.println("您所選車次已無剩餘座位，請選擇其他車次");
//					break;
				} else {
					System.out.println("沒有足夠靠走道座位，將為您分配其他條件座位");
					return getSeat(car);
				}
			case NoPreference:
				if (leftNoOfBusinessSeats() > 0) {
					return getSeat(car);
				} else {
					throw new LeftSeatException("您所選車次已無剩餘座位，請選擇其他車次");
//					System.out.println("您所選車次已無剩餘座位，請選擇其他車次");
//					break;
				}
			}
		}
		return null;
	}

	/**
	 * Get seats according to the input seat number.
	 * 
	 * @param car    the class of the seats
	 * @param number the number of seats customer wants to book
	 * @return the seat NO. have booked. If there's no enough seat in this train,
	 *         return null.
	 * @throws LeftSeatException 
	 */
	public String[] getMultipleSeat(Class car, int number) throws LeftSeatException {
		String[] seats = new String[number];
		switch (car) {
		case Standard:
			if (leftNoOfStandardSeats() >= number) {
				for (int i = 0; i < number; i++) {
					seats[i] = getOneSeat(car, SeatType.NoPreference);
				}
				return seats;
			} else {
				throw new LeftSeatException("您所選車次已無剩餘座位，請選擇其他車次");
//				System.out.println("您所選車次已無足夠座位，請選擇其他車次");
//				break;
			}
		case Business:
			if (leftNoOfBusinessSeats() >= number) {
				for (int i = 0; i < number; i++) {
					seats[i] = getOneSeat(car, SeatType.NoPreference);
				}
				return seats;
			} else {
				throw new LeftSeatException("您所選車次已無剩餘座位，請選擇其他車次");
//				System.out.println("您所選車次已無足夠座位，請選擇其他車次");
//				break;
			}
		}
		return null;
	}

	/**
	 * Set the sold seat to false
	 * 
	 * @param i the car of the seat
	 * @param j the row of the seat
	 * @param k the column of the seat
	 */
	public void setSeat(int i, int j, int k) {
		leftSeats[i][j][k] = false;
	}

	/**
	 * Free the given seat.
	 * 
	 * @param seat the seat that you want to free
	 */
	public void freeOneSeat(String seat) {
		int i = Integer.valueOf(seat.substring(0, 2)) - 1;
		int j = Integer.valueOf(seat.substring(2, 4)) - 1;
		for (int k = 0; k < noOfSeats[i][j].length; k++) {
			if (noOfSeats[i][j][k] == seat) {
				leftSeats[i][j][k] = true;
			}
		}
	}

	/**
	 * Free the given seats.
	 * 
	 * @param seat the array of seats you want to free
	 */
	public void freeMultipleSeats(String[] seat) {
		int length = seat.length;
		for (int i = 0; i < length; i++) {
			freeOneSeat(seat[i]);
		}
	}
}
