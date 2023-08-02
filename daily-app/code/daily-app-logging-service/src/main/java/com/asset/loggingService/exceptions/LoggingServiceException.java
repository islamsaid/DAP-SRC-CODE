package com.asset.loggingService.exceptions;

public class LoggingServiceException   extends RuntimeException {

    protected int errorCode;
    protected int errorSeverity;
    protected String args;

    public LoggingServiceException(int errorCode) {
        this.errorCode = errorCode;
    }

    public LoggingServiceException(int errorCode, int errorSeverity) {
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
    }

    public LoggingServiceException(int errorCode, int errorSeverity, String args) {
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
        this.args = args;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorSeverity() {
        return errorSeverity;
    }

    public void setErrorSeverity(int errorSeverity) {
        this.errorSeverity = errorSeverity;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

}
