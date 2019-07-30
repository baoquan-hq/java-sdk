package com.baoquan.sdk.pojos.response.data;


import com.baoquan.sdk.pojos.response.CreateAttestationResponse;

public class GetAttestationUrlData {
  private String status;
  private String message;
  private String blockchain_hash;
  private String hhf_hash;
  private String start_time;
  private String end_time;
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getBlockchain_hash() {
    return blockchain_hash;
  }

  public void setBlockchain_hash(String blockchain_hash) {
    this.blockchain_hash = blockchain_hash;
  }


  public String getHhf_hash() {
    return hhf_hash;
  }

  public void setHhf_hash(String hhf_hash) {
    this.hhf_hash = hhf_hash;
  }

  public String getStart_time() {
    return start_time;
  }

  public void setStart_time(String start_time) {
    this.start_time = start_time;
  }

  public String getEnd_time() {
    return end_time;
  }

  public void setEnd_time(String end_time) {
    this.end_time = end_time;
  }
}
