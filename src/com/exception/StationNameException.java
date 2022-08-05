package com.exception;

public class StationNameException extends Exception {

	public StationNameException() {
	}

	public StationNameException(String message) {
		super(message);
	}

	public StationNameException(Throwable cause) {
		super(cause);
	}

	public StationNameException(String message, Throwable cause) {
		super(message, cause);
	}

	public StationNameException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
