package com.sifast.exception;

import com.sifast.common.ApiMessage;

public class CustomException extends Exception {

	private static final long serialVersionUID = 1L;

	public CustomException(String message) {
        super(message);
    }

    public CustomException(ApiMessage message) {
        super(message.toString());
    }

    public CustomException() {
    }
}
