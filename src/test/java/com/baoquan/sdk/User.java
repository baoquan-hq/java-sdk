package com.baoquan.sdk;

/**
 * Created by sbwdlihao on 6/20/16.
 */
public class User {

  private String registered_at;

  private String name;

  private String phone_number;

  private String username;

  public String getRegistered_at() {
    return registered_at;
  }

  public void setRegistered_at(String registered_at) {
    this.registered_at = registered_at;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone_number() {
    return phone_number;
  }

  public void setPhone_number(String phone_number) {
    this.phone_number = phone_number;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
