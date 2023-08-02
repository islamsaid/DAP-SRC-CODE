package com.asset.dailapp.springcloudconfigserver.exception;

public class ConfigServerException extends RuntimeException {

    protected int errorCode;
    protected int errorSeverity;
    protected String args;

    public ConfigServerException(int errorCode) {
        this.errorCode = errorCode;
    }

    public ConfigServerException(int errorCode, int errorSeverity) {
        this.errorCode = errorCode;
        this.errorSeverity = errorSeverity;
    }

    public ConfigServerException(int errorCode, int errorSeverity, String args) {
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
