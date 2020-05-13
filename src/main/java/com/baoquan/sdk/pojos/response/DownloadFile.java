package com.baoquan.sdk.pojos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.InputStream;

/**
 * Created by sbwdlihao on 7/1/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DownloadFile {
  private String fileName;
  private InputStream file;

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public InputStream getFile() {
    return file;
  }

  public void setFile(InputStream file) {
    this.file = file;
  }
}