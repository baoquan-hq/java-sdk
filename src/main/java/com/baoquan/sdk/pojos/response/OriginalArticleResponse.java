package com.baoquan.sdk.pojos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OriginalArticleResponse {

  private String originalId;

  public String getOriginalId(){
    return originalId;
  }

  public void setOriginalId(String originalId){
    this.originalId = originalId;
  }
}
