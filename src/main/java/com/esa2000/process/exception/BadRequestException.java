package com.esa2000.process.exception;

public class BadRequestException extends BQException  {

    public BadRequestException(String message, Throwable cause) {
        super(message,cause);
    }

    public BadRequestException(int errorCode) {
        super(errorCode);
    }

    public BadRequestException(int errorCode, Throwable cause) {
        super(errorCode,cause);
    }

    public BadRequestException(int errorCode, Object[] msgArgs) {
       super(errorCode,msgArgs);
    }

    public BadRequestException(int errorCode, Object[] msgArgs, Throwable cause) {
        super(errorCode,msgArgs,cause);
    }

}
