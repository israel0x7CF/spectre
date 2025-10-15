package com.spectrun.spectrum.utils.exceptions;

public class CallbackAuthException extends RuntimeException {
  private String exceptionStatus;
  private String shortCode;
    public CallbackAuthException(String exceptionStatus,String shortCode,String message) {
        super(message);
        this.exceptionStatus = exceptionStatus;
        this.shortCode = shortCode;
    }

  public String getExceptionStatus() {
    return exceptionStatus;
  }



  public String getShortCode() {
    return shortCode;
  }


}
