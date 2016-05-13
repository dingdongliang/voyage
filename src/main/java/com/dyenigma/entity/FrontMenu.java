package com.dyenigma.entity;

public class FrontMenu extends BaseDomain {
    private String fmId;

    private String fmUrl;

    private String fmDesc;

    private Integer sort;

    private String status;

    public String getFmId() {
        return fmId;
    }

    public void setFmId(String fmId) {
        this.fmId = fmId == null ? null : fmId.trim();
    }

    public String getFmUrl() {
        return fmUrl;
    }

    public void setFmUrl(String fmUrl) {
        this.fmUrl = fmUrl == null ? null : fmUrl.trim();
    }

    public String getFmDesc() {
        return fmDesc;
    }

    public void setFmDesc(String fmDesc) {
        this.fmDesc = fmDesc == null ? null : fmDesc.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

}