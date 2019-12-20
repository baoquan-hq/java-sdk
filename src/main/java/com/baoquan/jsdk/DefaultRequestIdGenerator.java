package com.baoquan.jsdk;

import java.util.UUID;


public class DefaultRequestIdGenerator implements RequestIdGenerator {

  @Override
  public String createRequestId() {
    return UUID.randomUUID().toString().replace("-","");
  }
}
