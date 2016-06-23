package com.baoquan.sdk.pojos.payload;

import com.baoquan.sdk.BaoquanClient;

import java.util.List;
import java.util.Map;

/**
 * Created by sbwdlihao on 6/17/16.
 */
public class AttestationPayload {

  /**
   * factoid set
   */
  private List<Factoid> factoids;

  /**
   * whether all the factoid set upload to baoquan.com, if not you can call AddFactoids of {@link BaoquanClient}
   */
  private boolean completed = true;

  /**
   * sign info of attachment
   */
  private Map<String, Map<String, Map<String, List<String>>>> signs;

  public List<Factoid> getFactoids() {
    return factoids;
  }

  public void setFactoids(List<Factoid> factoids) {
    this.factoids = factoids;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  public Map<String, Map<String, Map<String, List<String>>>> getSigns() {
    return signs;
  }

  public void setSigns(Map<String, Map<String, Map<String, List<String>>>> signs) {
    this.signs = signs;
  }
}
