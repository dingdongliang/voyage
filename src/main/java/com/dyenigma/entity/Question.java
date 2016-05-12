package com.dyenigma.entity;

public class Question extends BaseDomain {
    private String qstnId;

    private String qstnKey;

    private String qstnDesc;

    private String status;

    public String getQstnId() {
        return qstnId;
    }

    public void setQstnId(String qstnId) {
        this.qstnId = qstnId == null ? null : qstnId.trim();
    }

    public String getQstnKey() {
        return qstnKey;
    }

    public void setQstnKey(String qstnKey) {
        this.qstnKey = qstnKey == null ? null : qstnKey.trim();
    }

    public String getQstnDesc() {
        return qstnDesc;
    }

    public void setQstnDesc(String qstnDesc) {
        this.qstnDesc = qstnDesc == null ? null : qstnDesc.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

}