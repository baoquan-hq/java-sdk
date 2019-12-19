package com.baoquan.jsdk.exceptions;

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
