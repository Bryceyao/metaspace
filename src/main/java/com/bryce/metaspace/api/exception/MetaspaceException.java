package com.bryce.metaspace.api.exception;

public class MetaspaceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MetaspaceException() {
		super();
	}

	public MetaspaceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MetaspaceException(String message, Throwable cause) {
		super(message, cause);
	}

	public MetaspaceException(String message) {
		super(message);
	}

	public MetaspaceException(Throwable cause) {
		super(cause);
	}
}