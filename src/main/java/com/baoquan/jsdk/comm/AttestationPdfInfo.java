package com.baoquan.jsdk.comm;

import java.io.Serializable;


public class AttestationPdfInfo implements Serializable {
    private String no;

    private String imgBase;


    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getImgBase() {
        return imgBase;
    }

    public void setImgBase(String imgBase) {
        this.imgBase = imgBase;
    }
}
