package com.baoquan.jsdk.comm;

import java.io.Serializable;

public class ImportArticleAddParam implements Serializable {

    private String title;

    private String body;

    private Long postTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getPostTime() {
        return postTime;
    }

    public void setPostTime(Long postTime) {
        this.postTime = postTime;
    }
}
