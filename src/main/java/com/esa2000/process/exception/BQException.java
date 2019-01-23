package com.esa2000.process.exception;

public class BQException extends RuntimeException{

    private int errorCode = -1;
    private String errorMsg;
    private String message ;
    private Object[] msgArgs;
    private String type;
    /**
     * 用于存放后端返回的数据
     **/
    private Object data;

    //当前系统的异常
    public BQException(int errorCode) {
        this.errorCode = errorCode;
        this.message = this.getMessage();
    }

    //当前系统的异常
    public BQException(int errorCode, Throwable cause) {
        this.errorCode = errorCode;
        this.message = cause.getMessage();
    }

    //接收第三方异常消息
    public BQException(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;

    }

    //当前系统的异常，没有定义code时使用
    public BQException(String errorMsg, Throwable cause) {
        super(errorMsg);
        this.message = cause.getMessage();
    }


    public BQException(int errorCode, Object[] msgArgs) {
        this.errorCode = errorCode;
        this.msgArgs = msgArgs;
        this.message = this.getMessage();
    }


    public BQException(int errorCode, Object[] msgArgs, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.msgArgs = msgArgs;
        this.message = cause.getMessage();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object[] getMsgArgs() {
        return msgArgs;
    }

    public void setMsgArgs(Object[] msgArgs) {
        this.msgArgs = msgArgs;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
