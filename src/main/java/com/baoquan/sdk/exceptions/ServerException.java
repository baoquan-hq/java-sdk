package com.baoquan.sdk.exceptions;

/**
 * Created by sbwdlihao on 6/17/16.
 */
public class ServerException extends Exception {

  private String requestId;

  private Long timestamp;

  public String getRequestId() {
    return requestId;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public ServerException(String requestId, String message, Long timestamp) {
    super(message);
    this.requestId = requestId;
    this.timestamp = timestamp;
  }
}
