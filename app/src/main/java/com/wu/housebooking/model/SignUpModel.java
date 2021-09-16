package com.wu.housebooking.model;

public class SignUpModel {
    String strFullname;
    String strEmail;
    String strPassword;
    String strMobi;
    String userType;
    String Uid;
    String address;

    public SignUpModel() {
    }

    public SignUpModel(String strFullname, String strEmail, String strPassword, String strMobi, String userType, String uid) {
        this.strFullname = strFullname;
        this.strEmail = strEmail;
        this.strPassword = strPassword;
        this.strMobi = strMobi;
        this.userType = userType;
        Uid = uid;
    }

    public String getStrFullname() {
        return strFullname;
    }

    public void setStrFullname(String strFullname) {
        this.strFullname = strFullname;
    }

    public String getStrEmail() {
        return strEmail;
    }

    public void setStrEmail(String strEmail) {
        this.strEmail = strEmail;
    }

    public String getStrPassword() {
        return strPassword;
    }

    public void setStrPassword(String strPassword) {
        this.strPassword = strPassword;
    }

    public String getStrMobi() {
        return strMobi;
    }

    public void setStrMobi(String strMobi) {
        this.strMobi = strMobi;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
