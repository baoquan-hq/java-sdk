package com.baoquan.sdk.pojos.response.data;

import com.baoquan.sdk.pojos.payload.Factoid;

import java.util.List;
import java.util.Map;

/**
 * Created by sbwdlihao on 6/30/16.
 */
public class GetAttestationData {

  private String no;
  private String template_id;
  private Map<String,String> identities;
  private List<Factoid> factoids;
  private boolean completed;
  private Map<String, List<String>> attachments;
  private String blockchain_hash;

  public String getNo() {
    return no;
  }

  public void setNo(String no) {
    this.no = no;
  }

  public String getTemplate_id() {
    return template_id;
  }

  public void setTemplate_id(String template_id) {
    this.template_id = template_id;
  }

  public Map<String, String> getIdentities() {
    return identities;
  }

  public void setIdentities(Map<String, String> identities) {
    this.identities = identities;
  }

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

  public Map<String, List<String>> getAttachments() {
    return attachments;
  }

  public void setAttachments(Map<String, List<String>> attachments) {
    this.attachments = attachments;
  }

  public String getBlockchain_hash() {
    return blockchain_hash;
  }

  public void setBlockchain_hash(String blockchain_hash) {
    this.blockchain_hash = blockchain_hash;
  }
}
