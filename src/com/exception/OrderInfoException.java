package com.exception;

public class OrderInfoException extends Exception {

	public OrderInfoException() {
		super("查無符合此條件訂單!請確認欲查詢之資訊是否正確");
	}

	public OrderInfoException(String message) {
		super(message);
	}

	public OrderInfoException(Throwable cause) {
		super(cause);
	}

	public OrderInfoException(String message, Throwable cause) {
		super(message, cause);
	}

	public OrderInfoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
