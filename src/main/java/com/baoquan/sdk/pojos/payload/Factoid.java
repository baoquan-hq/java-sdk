package com.baoquan.sdk.pojos.payload;

import java.util.Map;

/**
 * Created by sbwdlihao on 6/17/16.
 *
 * factoid is a data that fill in the template
 * when you edit template in baoquan.com you can define factoids with it
 */
public class Factoid {

  /**
   * unique id is used to avoid create same attestation when net exception
   */
  private String unique_id;

  /**
   * the type of the factoid
   */
  private String type;

  /**
   * the data of the factoid
   */
  private Object data;

  private Map<IdentityType, String> identities;

  public String getUnique_id() {
    return unique_id;
  }

  public void setUnique_id(String unique_id) {
    this.unique_id = unique_id;
  }

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

  public Map<IdentityType, String> getIdentities() {
    return identities;
  }

  public void setIdentities(Map<IdentityType, String> identities) {
    this.identities = identities;
  }
}
