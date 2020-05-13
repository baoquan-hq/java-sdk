package com.baoquan.sdk.pojos.response.data;

import com.baoquan.sdk.pojos.payload.Factoid;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

/**
 * Created by sbwdlihao on 6/30/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAttestationData {

  private String no;
  private String template_id;
  private Map<String,String> identities;
  private List<Factoid> factoids;
  private boolean completed;
  private Map<String, List<String>> attachments;
  private String blockchain_hash;
  private String unique_id;
  private String sha256_hash;
  private String sm3_hash;
  private String number;
  private String hhf_number;
  private String ghf_number;
  private String html;

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

  public String getUnique_id() {
    return unique_id;
  }

  public void setUnique_id(String unique_id) {
    this.unique_id = unique_id;
  }

  public String getSha256_hash() {
    return sha256_hash;
  }

  public void setSha256_hash(String sha256_hash) {
    this.sha256_hash = sha256_hash;
  }

  public String getSm3_hash() {
    return sm3_hash;
  }

  public void setSm3_hash(String sm3_hash) {
    this.sm3_hash = sm3_hash;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getHhf_number() {
    return hhf_number;
  }

  public void setHhf_number(String hhf_number) {
    this.hhf_number = hhf_number;
  }

  public String getGhf_number() {
    return ghf_number;
  }

  public void setGhf_number(String ghf_number) {
    this.ghf_number = ghf_number;
  }

  public String getHtml() {
    return html;
  }

  public void setHtml(String html) {
    this.html = html;
  }
}
