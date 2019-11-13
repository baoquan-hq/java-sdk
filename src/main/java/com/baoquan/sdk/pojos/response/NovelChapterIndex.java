package com.baoquan.sdk.pojos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedList;
@JsonIgnoreProperties(ignoreUnknown = true)
public class NovelChapterIndex {

    private Integer totalcount;
    private LinkedList<ALable> aLables = new LinkedList<ALable>();
    private Integer attestationCount;
    private Integer money;

    public Integer getAttestationCount() {
        return attestationCount;
    }

    public void setAttestationCount(Integer attestationCount) {
        this.attestationCount = attestationCount;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(Integer totalcount) {
        this.totalcount = totalcount;
    }

    public LinkedList<ALable> getaLables() {
        return aLables;
    }

    public void setaLables(LinkedList<ALable> aLables) {
        this.aLables = aLables;
    }
}
