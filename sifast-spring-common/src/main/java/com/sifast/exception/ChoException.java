package com.sifast.exception;

import com.sifast.common.ApiMessage;

public class ChoException extends Exception {

    public ChoException(String message) {
        super(message);
    }

    public ChoException(ApiMessage message) {
        super(message.toString());
    }

    public ChoException() {
    }
}
