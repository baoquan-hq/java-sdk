package com.baoquan.sdk.exceptions;

/**
 * Created by sbwdlihao on 6/17/16.
 */
public class ServerException extends Exception {

  private String requestId;

  public String getRequestId() {
    return requestId;
  }

  public ServerException(String requestId, String message) {
    super(message);
    this.requestId = requestId;
  }
}
