package com.dyenigma.entity;

public class QALog extends BaseDomain {
    private String logId;

    private String qaType;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId == null ? null : logId.trim();
    }

    public String getQaType() {
        return qaType;
    }

    public void setQaType(String qaType) {
        this.qaType = qaType == null ? null : qaType.trim();
    }

}