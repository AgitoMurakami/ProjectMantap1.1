package com.example.projectmantap11.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Karyawan {

    /*
    {
        "id": "u001",
        "companyId": "c001",
        "employeeId": "u1",
        "email": "u1@mail.com",
        "name": "Willy",
        "phone": "08000001",
        "address": "Address 1",
        "createdDate": "2020-06-04T00:00:00.000+0000",
        "lastUpdated": "2020-06-04T00:00:00.000+0000",
        "role": "ADMIN_MASTER",
        "branch": "PT. Corona Surabaya Asoy",
        "division": "Divisi Corona A",
        "rank": "Corona Boss",
        "deleted": false,
        "actived": true
    }
     */

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("companyId")
    @Expose
    private String companyID;

    @SerializedName("employeeId")
    @Expose
    private String employeeID;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("createdDate")
    @Expose
    private String createdDate;

    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;

    @SerializedName("role")
    @Expose
    private String role;

    @SerializedName("branch")
    @Expose
    private String branch;

    @SerializedName("division")
    @Expose
    private String division;

    @SerializedName("rank")
    @Expose
    private String rank;

    @SerializedName("deleted")
    @Expose
    private String deleted;

    @SerializedName("actived")
    @Expose
    private String actived;

    //EMPTY CONSTRUCTOR
    public Karyawan() {
    }

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

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getActived() {
        return actived;
    }

    public void setActived(String actived) {
        this.actived = actived;
    }
}
