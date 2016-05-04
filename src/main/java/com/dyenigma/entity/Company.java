package com.dyenigma.entity;

import com.dyenigma.utils.ExcelVOAttribute;

public class Company extends BaseDomain {
    private String coId;

    private String prntId;

    private String state;

    private String iconCls;

    private Integer sort;

    private String status;

    private String manager;

    private String bank;

    private String bankAcct;

    @ExcelVOAttribute(name = "公司名称", column = "A", isExport = true, prompt = "这是公司名称!")
    private String coName;
    @ExcelVOAttribute(name = "父公司名称", column = "I")
    private String prntName;
    @ExcelVOAttribute(name = "公司电话", column = "B")
    private String coPhone;
    @ExcelVOAttribute(name = "公司传真", column = "C")
    private String coFax;
    @ExcelVOAttribute(name = "公司地址", column = "D")
    private String coAdr;
    @ExcelVOAttribute(name = "邮政编码", column = "E")
    private String coZip;
    @ExcelVOAttribute(name = "Email", column = "F")
    private String coEmail;
    @ExcelVOAttribute(name = "联系人", column = "G")
    private String contact;
    @ExcelVOAttribute(name = "描述", column = "H")
    private String coDesc;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls == null ? null : iconCls.trim();
    }

    public String getCoPhone() {
        return coPhone;
    }

    public void setCoPhone(String coPhone) {
        this.coPhone = coPhone == null ? null : coPhone.trim();
    }

    public String getCoFax() {
        return coFax;
    }

    public void setCoFax(String coFax) {
        this.coFax = coFax == null ? null : coFax.trim();
    }

    public String getCoAdr() {
        return coAdr;
    }

    public void setCoAdr(String coAdr) {
        this.coAdr = coAdr == null ? null : coAdr.trim();
    }

    public String getCoZip() {
        return coZip;
    }

    public void setCoZip(String coZip) {
        this.coZip = coZip == null ? null : coZip.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCoEmail() {
        return coEmail;
    }

    public void setCoEmail(String coEmail) {
        this.coEmail = coEmail == null ? null : coEmail.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager == null ? null : manager.trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public String getBankAcct() {
        return bankAcct;
    }

    public void setBankAcct(String bankAcct) {
        this.bankAcct = bankAcct == null ? null : bankAcct.trim();
    }

    public String getCoDesc() {
        return coDesc;
    }

    public void setCoDesc(String coDesc) {
        this.coDesc = coDesc == null ? null : coDesc.trim();
    }
}