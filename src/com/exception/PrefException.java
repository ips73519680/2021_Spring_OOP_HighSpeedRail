package com.exception;

public class PrefException extends Exception {

	public PrefException() {
	}

	public PrefException(String message) {
		super(message);
	}

	public PrefException(Throwable cause) {
		super(cause);
	}

	public PrefException(String message, Throwable cause) {
		super(message, cause);
	}

	public PrefException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
