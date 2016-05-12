package com.dyenigma.entity;

public class Answer extends BaseDomain {
    private String qstnId;

    private String ansrId;

    private String ansrDesc;

    private Integer ansrAgree;

    private Integer ansrOpos;

    public String getQstnId() {
        return qstnId;
    }

    public void setQstnId(String qstnId) {
        this.qstnId = qstnId == null ? null : qstnId.trim();
    }

    public String getAnsrId() {
        return ansrId;
    }

    public void setAnsrId(String ansrId) {
        this.ansrId = ansrId == null ? null : ansrId.trim();
    }

    public String getAnsrDesc() {
        return ansrDesc;
    }

    public void setAnsrDesc(String ansrDesc) {
        this.ansrDesc = ansrDesc == null ? null : ansrDesc.trim();
    }

    public Integer getAnsrAgree() {
        return ansrAgree;
    }

    public void setAnsrAgree(Integer ansrAgree) {
        this.ansrAgree = ansrAgree;
    }

    public Integer getAnsrOpos() {
        return ansrOpos;
    }

    public void setAnsrOpos(Integer ansrOpos) {
        this.ansrOpos = ansrOpos;
    }

}