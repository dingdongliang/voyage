package com.dyenigma.entity;

public class UserPmsn extends BaseDomain {
    private String upmId;

    private String userId;

    private String pmsnId;

    private String status;

    public String getUpmId() {
        return upmId;
    }

    public void setUpmId(String upmId) {
        this.upmId = upmId == null ? null : upmId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getPmsnId() {
        return pmsnId;
    }

    public void setPmsnId(String pmsnId) {
        this.pmsnId = pmsnId == null ? null : pmsnId.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

}