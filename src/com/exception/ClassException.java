package com.exception;

public class ClassException extends Exception {

	public ClassException() {
	}

	public ClassException(String message) {
		super(message);
	}

	public ClassException(Throwable cause) {
		super(cause);
	}

	public ClassException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClassException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
