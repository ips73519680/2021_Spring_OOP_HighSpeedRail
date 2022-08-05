package com.exception;

public class OrderNoException extends Exception {

	public OrderNoException() {
		super("查無此訂單編號!請確認輸入之訂單編號是否正確，或者輸入R返回");
	}

	public OrderNoException(String message) {
		super(message);
	}

	public OrderNoException(Throwable cause) {
		super(cause);
	}

	public OrderNoException(String message, Throwable cause) {
		super(message, cause);
	}

	public OrderNoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
