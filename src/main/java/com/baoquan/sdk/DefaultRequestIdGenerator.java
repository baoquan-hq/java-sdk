package com.baoquan.sdk;

import java.util.UUID;

/**
 * Created by sbwdlihao on 6/17/16.
 */
public class DefaultRequestIdGenerator implements RequestIdGenerator {

  @Override
  public String createRequestId() {
    return UUID.randomUUID().toString();
  }
}
