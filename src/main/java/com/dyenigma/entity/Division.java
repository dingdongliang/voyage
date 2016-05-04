package com.dyenigma.entity;

public class Division extends BaseDomain {
    private String divId;

    private String divName;

    private String manager;

    private String divPhone;

    private String divAdr;

    private String coId;

    private String coName;

    private String divDesc;

    private String status;

    private String iconCls;

    private String state;

    public String getDivId() {
        return divId;
    }

    public void setDivId(String divId) {
        this.divId = divId == null ? null : divId.trim();
    }

    public String getDivName() {
        return divName;
    }

    public void setDivName(String divName) {
        this.divName = divName == null ? null : divName.trim();
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager == null ? null : manager.trim();
    }

    public String getDivPhone() {
        return divPhone;
    }

    public void setDivPhone(String divPhone) {
        this.divPhone = divPhone == null ? null : divPhone.trim();
    }

    public String getDivAdr() {
        return divAdr;
    }

    public void setDivAdr(String divAdr) {
        this.divAdr = divAdr == null ? null : divAdr.trim();
    }

    public String getCoId() {
        return coId;
    }

    public void setCoId(String coId) {
        this.coId = coId == null ? null : coId.trim();
    }

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName == null ? null : coName.trim();
    }

    public String getDivDesc() {
        return divDesc;
    }

    public void setDivDesc(String divDesc) {
        this.divDesc = divDesc == null ? null : divDesc.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls == null ? null : iconCls.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

}