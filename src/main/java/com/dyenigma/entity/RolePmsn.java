package com.dyenigma.entity;

public class RolePmsn extends BaseDomain {
    private String rpId;

    private String roleId;

    private String pmsnId;

    private String status;

    public String getRpId() {
        return rpId;
    }

    public void setRpId(String rpId) {
        this.rpId = rpId == null ? null : rpId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
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