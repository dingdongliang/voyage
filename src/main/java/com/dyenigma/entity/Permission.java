package com.dyenigma.entity;

public class Permission extends BaseDomain {
    private String pmsnId;

    private String pmsnName;

    private String prntId;

    private String prntName;

    private String pmsnCode;

    private Integer sort;

    private String pmsnType;

    private String status;

    private String pmsnUrl;

    private String iconCls;

    private String pmsnDesc;

    private String state;

    private String isDefault;

    private String isUsed;

    public String getPmsnId() {
        return pmsnId;
    }

    public void setPmsnId(String pmsnId) {
        this.pmsnId = pmsnId == null ? null : pmsnId.trim();
    }

    public String getPmsnName() {
        return pmsnName;
    }

    public void setPmsnName(String pmsnName) {
        this.pmsnName = pmsnName == null ? null : pmsnName.trim();
    }

    public String getPrntId() {
        return prntId;
    }

    public void setPrntId(String prntId) {
        this.prntId = prntId == null ? null : prntId.trim();
    }

    public String getPrntName() {
        return prntName;
    }

    public void setPrntName(String prntName) {
        this.prntName = prntName == null ? null : prntName.trim();
    }

    public String getPmsnCode() {
        return pmsnCode;
    }

    public void setPmsnCode(String pmsnCode) {
        this.pmsnCode = pmsnCode == null ? null : pmsnCode.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getPmsnType() {
        return pmsnType;
    }

    public void setPmsnType(String pmsnType) {
        this.pmsnType = pmsnType == null ? null : pmsnType.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getPmsnUrl() {
        return pmsnUrl;
    }

    public void setPmsnUrl(String pmsnUrl) {
        this.pmsnUrl = pmsnUrl == null ? null : pmsnUrl.trim();
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls == null ? null : iconCls.trim();
    }

    public String getPmsnDesc() {
        return pmsnDesc;
    }

    public void setPmsnDesc(String pmsnDesc) {
        this.pmsnDesc = pmsnDesc == null ? null : pmsnDesc.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault == null ? null : isDefault.trim();
    }

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed == null ? null : isUsed.trim();
    }

}