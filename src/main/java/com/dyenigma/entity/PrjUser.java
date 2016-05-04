package com.dyenigma.entity;

public class PrjUser extends BaseDomain {
    private String puId;

    private String prjId;

    private String userId;

    private String status;

    public String getPuId() {
        return puId;
    }

    public void setPuId(String puId) {
        this.puId = puId == null ? null : puId.trim();
    }

    public String getPrjId() {
        return prjId;
    }

    public void setPrjId(String prjId) {
        this.prjId = prjId == null ? null : prjId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

}