package com.exception;

public class NoOfTicketException extends Exception {

	public NoOfTicketException() {
	}

	public NoOfTicketException(String message) {
		super(message);
	}

	public NoOfTicketException(Throwable cause) {
		super(cause);
	}

	public NoOfTicketException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoOfTicketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
