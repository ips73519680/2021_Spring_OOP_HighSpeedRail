package com.exception;

public class UserException extends Exception {

	public UserException() {
		super("查無此身分證字號/護照號碼!請確認輸入是否正確，或者輸入R返回");
	}

	public UserException(String message) {
		super(message);
	}

	public UserException(Throwable cause) {
		super(cause);
	}

	public UserException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
