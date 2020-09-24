package com.baoquan.jsdk.comm;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ImportArticleAddParam {

    @NotBlank(message = "文章标题不能为空")
    private String title;

    @NotBlank(message = "文章正文不能为空")
    private String body;

    @NotNull(message = "发布时间，时间戳（s）不能为空")
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
