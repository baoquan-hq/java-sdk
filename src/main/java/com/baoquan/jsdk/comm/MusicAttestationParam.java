package com.baoquan.jsdk.comm;

import javax.validation.constraints.NotBlank;

public class MusicAttestationParam extends BaseAttestationPayloadParam {

    @NotBlank(message = "platform不能为空")
    private String platform;    //平台: QQ, KUGOU, KUWO, QQMV

    @NotBlank(message = "url不能为空")
    private String url;     //地址

    @NotBlank(message = "song不能为空")
    private String song;    //歌曲

    @NotBlank(message = "singer不能为空")
    private String singer;  //歌手

    private String album;   //专辑

    private String type;   //类型

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

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
