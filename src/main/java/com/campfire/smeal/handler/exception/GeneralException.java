package com.campfire.smeal.handler.exception;

import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {
    private SmErrorCode smErrorCode;
    private String detailMessage;

    public GeneralException(SmErrorCode errorCode) {
        super(errorCode.getMessage());
        this.smErrorCode = errorCode;
        this.detailMessage= errorCode.getMessage();
    }

    public GeneralException(SmErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.smErrorCode = errorCode;
        this.detailMessage= detailMessage;
    }
}
