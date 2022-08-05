package com.exception;

public class LeftSeatException extends Exception {

	public LeftSeatException() {
	}

	public LeftSeatException(String message) {
		super(message);
	}

	public LeftSeatException(Throwable cause) {
		super(cause);
	}

	public LeftSeatException(String message, Throwable cause) {
		super(message, cause);
	}

	public LeftSeatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
