package com.sifast.common.enums;

public enum HttpCostumMessage {
    CHECK_ENTRY_MSG("Error occured, Check your entry please."), NO_RESPONSE_MSG("No response data for this request."), EMPTY_RESPONSE_MSG(
            "Empty response for this request."), BAD_ARGUMENTS("Arguments not supported");

    private String message;

    HttpCostumMessage(String message) {
        this.message = message;
    }

    public String getValue() {
        return message;
    }
}