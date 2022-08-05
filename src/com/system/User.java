package com.system;

import java.util.ArrayList;

/**
 * New an arrayList of User at the beginning of main Add a user to the arrayList
 * of User when an order is valid.
 * 
 * @author ChienJu
 *
 */
public class User {
	private String userID;
	private ArrayList<String> orderNo = new ArrayList<String>();

	public User(String id, String order) {
		setUserID(id);
		addOrderNo(order);
	}

	/**
	 * @return the userID
	 */
	public String getUserID() {
		return new String(userID);
	}

	/**
	 * @param userID the userID to set
	 */
	private void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * @return the orderNo of the given index
	 */
	public String getOrderNo(int i) {
		return new String(orderNo.get(i));
	}

	/**
	 * Add the valid order to the arrayList under user
	 * 
	 * @param orderNo the orderNo to add
	 */
	public void addOrderNo(String order) {
		orderNo.add(order);
	}
	
	/**
	 * Remove the order in the ArrayList
	 * @param order the order to be removed
	 */
	public void removeOrderNo(String order) {
		orderNo.remove(order);
	}

	/**
	 * Get the number of order the user had placed.
	 * 
	 * @return the number of orders
	 */
	public int getLengthOfOrder() {
		return orderNo.size();
	}

	public boolean equals(String u) {
		if (u == this.userID)
			return true;
		else
			return false;
	}

}
