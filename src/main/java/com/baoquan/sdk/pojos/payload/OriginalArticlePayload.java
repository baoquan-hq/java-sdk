package com.baoquan.sdk.pojos.payload;

import java.util.Map;


public class OriginalArticlePayload{

  /**
   * unique id is used to avoid create same original article
   */
  private String uniqueId;

  private String platformCode;

  private String nickName;

  private String title;

  private String linkUrl;

  private String subDate;

  private String originalType;


  public String getUniqueId(){
    return uniqueId;
  }

  public void setUniqueId(String uniqueId){
    this.uniqueId = uniqueId;
  }

  public String getPlatformCode(){
    return platformCode;
  }

  public void setPlatformCode(String platformCode){
    this.platformCode = platformCode;
  }

  public String getTitle(){
    return title;
  }

  public void setTitle(String title){
    this.title = title;
  }

  public String getLinkUrl(){
    return linkUrl;
  }

  public void setLinkUrl(String linkUrl){
    this.linkUrl = linkUrl;
  }

  public String getSubDate(){
    return subDate;
  }

  public void setSubDate(String subDate){
    this.subDate = subDate;
  }

  public String getOriginalType(){
    return originalType;
  }

  public void setOriginalType(String originalType){
    this.originalType = originalType;
  }

  public String getNickName(){
    return nickName;
  }

  public void setNickName(String nickName){
    this.nickName = nickName;
  }
}
