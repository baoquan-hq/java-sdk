package com.baoquan.jsdk.comm;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class UrlArticleAddParam implements Serializable {

    @NotBlank(message = "平台代码不能为空, (weixin,zhihu,jianshu,douban,weibo,toutiao)")
    private String platform;

    @NotBlank(message = "文章地址不能为空")
    private String url;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
