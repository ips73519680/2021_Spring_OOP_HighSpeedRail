package com.exception;

public class TimeException extends Exception {

	public TimeException() {
		super("輸入時間錯誤，請重新輸入或輸入R返回");
	}

	public TimeException(String message) {
		super(message);
	}

	public TimeException(Throwable cause) {
		super(cause);
	}

	public TimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public TimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
