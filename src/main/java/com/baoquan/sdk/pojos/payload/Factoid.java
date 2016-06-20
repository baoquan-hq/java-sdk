package com.baoquan.sdk.pojos.payload;

/**
 * Created by sbwdlihao on 6/17/16.
 *
 * factoid is a data that fill in the template
 * when you edit template in baoquan.com you can define factoids with it
 */
public class Factoid {

  /**
   * the type of the factoid
   */
  private String type;

  /**
   * the data of the factoid
   */
  private Object data;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
