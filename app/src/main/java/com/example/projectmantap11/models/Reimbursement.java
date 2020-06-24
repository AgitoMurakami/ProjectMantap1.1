package com.example.projectmantap11.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Reimbursement {

    @SerializedName("uuid")
    @Expose
    private String id;

    @SerializedName("company_id")
    @Expose
    private String companyID;

    @SerializedName("user_uuid")
    @Expose
    private String user;

    @SerializedName("categories")
    @Expose
    private String categories;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("invoice_id")
    @Expose
    private String invoiceID;

    @SerializedName("amount")
    @Expose
    private String uang;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("file_path")
    @Expose
    private String imagePath;

    @SerializedName("note")
    @Expose
    private String note;

    @SerializedName("created_date")
    @Expose
    private String createdDate;

    @SerializedName("modify_date")
    @Expose
    private String modifyDate;

    ///////////////
    //CONSTRUCTOR

    public Reimbursement(String user, String categories, String status, String uang, String description, String note, String createdDate, String modifyDate) {
        this.user = user;
        this.categories = categories;
        this.status = status;
        this.uang = uang;
        this.description = description;
        this.note = note;
        this.createdDate = createdDate;
        this.modifyDate = modifyDate;
    }


    ///////////////////////
    //GETTER AND SETTER


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getUang() {
        return uang;
    }

    public void setUang(String uang) {
        this.uang = uang;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }
}
