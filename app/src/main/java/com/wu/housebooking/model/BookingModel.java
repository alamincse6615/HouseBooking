package com.wu.housebooking.model;

public class BookingModel {

    String PropertyId;
    String adminCommissionAmount;
    String adminCommissionPercent;
    String id;
    boolean isAcceptByAdmin;
    boolean isAcceptByPropertyOwner;
    boolean isDeleteByPropertyOwner;
    boolean isPaidAdminCommissionAmount;
    boolean isPaidProOwnerCommissionAmount;
    boolean isPayment;
    String paymentAmountByUser;
    String paymentMethod;
    String paymentTransactionId;
    String proOwnerCommissionAmount;
    String proOwnerCommissionPercent;
    String uid;
    String userAddress;
    String userEmail;
    String userName;
    String userPhone;
    String booking_date;
    String return_date;
    String PropertyOwnerId;





    public BookingModel() {
    }

    public BookingModel(String propertyId, String adminCommissionAmount, String adminCommissionPercent, String id, boolean isAcceptByAdmin, boolean isAcceptByPropertyOwner, boolean isDeleteByPropertyOwner, boolean isPaidAdminCommissionAmount, boolean isPaidProOwnerCommissionAmount, boolean isPayment, String paymentAmountByUser, String paymentMethod, String paymentTransactionId, String proOwnerCommissionAmount, String proOwnerCommissionPercent, String uid, String userAddress, String userEmail, String userName, String userPhone, String booking_date, String return_date, String propertyOwnerId) {
        PropertyId = propertyId;
        this.adminCommissionAmount = adminCommissionAmount;
        this.adminCommissionPercent = adminCommissionPercent;
        this.id = id;
        this.isAcceptByAdmin = isAcceptByAdmin;
        this.isAcceptByPropertyOwner = isAcceptByPropertyOwner;
        this.isDeleteByPropertyOwner = isDeleteByPropertyOwner;
        this.isPaidAdminCommissionAmount = isPaidAdminCommissionAmount;
        this.isPaidProOwnerCommissionAmount = isPaidProOwnerCommissionAmount;
        this.isPayment = isPayment;
        this.paymentAmountByUser = paymentAmountByUser;
        this.paymentMethod = paymentMethod;
        this.paymentTransactionId = paymentTransactionId;
        this.proOwnerCommissionAmount = proOwnerCommissionAmount;
        this.proOwnerCommissionPercent = proOwnerCommissionPercent;
        this.uid = uid;
        this.userAddress = userAddress;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPhone = userPhone;
        this.booking_date = booking_date;
        this.return_date = return_date;
        PropertyOwnerId = propertyOwnerId;
    }

    public String getPropertyId() {
        return PropertyId;
    }

    public void setPropertyId(String propertyId) {
        PropertyId = propertyId;
    }

    public String getAdminCommissionAmount() {
        return adminCommissionAmount;
    }

    public void setAdminCommissionAmount(String adminCommissionAmount) {
        this.adminCommissionAmount = adminCommissionAmount;
    }

    public String getAdminCommissionPercent() {
        return adminCommissionPercent;
    }

    public void setAdminCommissionPercent(String adminCommissionPercent) {
        this.adminCommissionPercent = adminCommissionPercent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAcceptByAdmin() {
        return isAcceptByAdmin;
    }

    public void setAcceptByAdmin(boolean acceptByAdmin) {
        isAcceptByAdmin = acceptByAdmin;
    }

    public boolean isAcceptByPropertyOwner() {
        return isAcceptByPropertyOwner;
    }

    public void setAcceptByPropertyOwner(boolean acceptByPropertyOwner) {
        isAcceptByPropertyOwner = acceptByPropertyOwner;
    }

    public boolean isDeleteByPropertyOwner() {
        return isDeleteByPropertyOwner;
    }

    public void setDeleteByPropertyOwner(boolean deleteByPropertyOwner) {
        isDeleteByPropertyOwner = deleteByPropertyOwner;
    }

    public boolean isPaidAdminCommissionAmount() {
        return isPaidAdminCommissionAmount;
    }

    public void setPaidAdminCommissionAmount(boolean paidAdminCommissionAmount) {
        isPaidAdminCommissionAmount = paidAdminCommissionAmount;
    }

    public boolean isPaidProOwnerCommissionAmount() {
        return isPaidProOwnerCommissionAmount;
    }

    public void setPaidProOwnerCommissionAmount(boolean paidProOwnerCommissionAmount) {
        isPaidProOwnerCommissionAmount = paidProOwnerCommissionAmount;
    }

    public boolean isPayment() {
        return isPayment;
    }

    public void setPayment(boolean payment) {
        isPayment = payment;
    }

    public String getPaymentAmountByUser() {
        return paymentAmountByUser;
    }

    public void setPaymentAmountByUser(String paymentAmountByUser) {
        this.paymentAmountByUser = paymentAmountByUser;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentTransactionId() {
        return paymentTransactionId;
    }

    public void setPaymentTransactionId(String paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }

    public String getProOwnerCommissionAmount() {
        return proOwnerCommissionAmount;
    }

    public void setProOwnerCommissionAmount(String proOwnerCommissionAmount) {
        this.proOwnerCommissionAmount = proOwnerCommissionAmount;
    }

    public String getProOwnerCommissionPercent() {
        return proOwnerCommissionPercent;
    }

    public void setProOwnerCommissionPercent(String proOwnerCommissionPercent) {
        this.proOwnerCommissionPercent = proOwnerCommissionPercent;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getPropertyOwnerId() {
        return PropertyOwnerId;
    }

    public void setPropertyOwnerId(String propertyOwnerId) {
        PropertyOwnerId = propertyOwnerId;
    }
}
