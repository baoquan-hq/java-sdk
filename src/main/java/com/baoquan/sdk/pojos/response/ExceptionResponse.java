package com.baoquan.sdk.pojos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by sbwdlihao on 6/18/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExceptionResponse extends BaseResponse {

  /**
   * error message
   */
  private String message;

  private Long timestamp;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }
}
