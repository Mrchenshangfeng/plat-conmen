/**
 * 
 */
package com.hywisdom.platform.common.utils;

@SuppressWarnings("serial")
public abstract class BaseException extends RuntimeException {
	public BaseException() {
	}

	public BaseException(Throwable ex) {
		super(ex);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, Throwable ex) {
		super(message, ex);
	}
}
