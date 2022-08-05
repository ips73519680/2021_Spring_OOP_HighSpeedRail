package com.exception;

public class ReviseException extends Exception {

	public ReviseException() {
	}

	public ReviseException(String message) {
		super(message);
	}

	public ReviseException(Throwable cause) {
		super(cause);
	}

	public ReviseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReviseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
