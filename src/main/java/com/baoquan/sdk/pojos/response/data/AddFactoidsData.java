package com.baoquan.sdk.pojos.response.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by sbwdlihao on 6/17/16.
 *
 * data of add factoid response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddFactoidsData {

  /**
   * true if success
   */
  private boolean success;

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }
}
