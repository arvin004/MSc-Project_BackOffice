package com.backoffice.exception;

public class BackOfficeException extends Exception {

	private static final long serialVersionUID = -7937694006977727343L;

	public BackOfficeException() {
		super();
	}

	public BackOfficeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BackOfficeException(String message, Throwable cause) {
		super(message, cause);
	}

	public BackOfficeException(String message) {
		super(message);
	}

	public BackOfficeException(Throwable cause) {
		super(cause);
	}
}
