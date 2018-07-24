package com.jayzonsolutions.LunchBox.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Foodmaker {
    @SerializedName("foodmakerId")
    @Expose
    private Integer foodmakerId;
    @SerializedName("foodmakerName")
    @Expose
    private String foodmakerName;
    @SerializedName("foodmakerEmail")
    @Expose
    private String foodmakerEmail;
    @SerializedName("foodmakerNic")
    @Expose
    private String foodmakerNic;
    @SerializedName("foodmakerpassword")
    @Expose
    private String foodmakerpassword;
    @SerializedName("foodmakerAddresId")
    @Expose
    private Address foodmakerAddresId;
    @SerializedName("foodmakerAccessType")
    @Expose
    private Integer foodmakerAccessType;
    @SerializedName("foodmakerImagePath")
    @Expose
    private String foodmakerImagePath;
    @SerializedName("foodmakerActive")
    @Expose
    private Integer foodmakerActive;
    @SerializedName("foodmakerPhoneNumber")
    @Expose
    private String foodmakerPhoneNumber;
    @SerializedName("foodmakerAdminId")
    @Expose
    private Integer foodmakerAdminId;
    @SerializedName("foodmakerCreatedAt")
    @Expose
    private String foodmakerCreatedAt;
    @SerializedName("foodmakerLastUpdated")
    @Expose
    private String foodmakerLastUpdated;
    @SerializedName("averageRatings")
    @Expose
    private Double averageRatings;
    @SerializedName("foodaker_RegToken")
    @Expose
    private String foodmakerRegToken;

    public Integer getFoodmakerId() {
        return foodmakerId;
    }

    public void setFoodmakerId(Integer foodmakerId) {
        this.foodmakerId = foodmakerId;
    }

    public String getFoodmakerName() {
        return foodmakerName;
    }

    public void setFoodmakerName(String foodmakerName) {
        this.foodmakerName = foodmakerName;
    }

    public String getFoodmakerEmail() {
        return foodmakerEmail;
    }

    public void setFoodmakerEmail(String foodmakerEmail) {
        this.foodmakerEmail = foodmakerEmail;
    }

    public String getFoodmakerNic() {
        return foodmakerNic;
    }

    public void setFoodmakerNic(String foodmakerNic) {
        this.foodmakerNic = foodmakerNic;
    }

    public String getFoodmakerpassword() {
        return foodmakerpassword;
    }

    public void setFoodmakerpassword(String foodmakerpassword) {
        this.foodmakerpassword = foodmakerpassword;
    }

    public Address getFoodmakerAddresId() {
        return foodmakerAddresId;
    }

    public void setFoodmakerAddresId(Address foodmakerAddresId) {
        this.foodmakerAddresId = foodmakerAddresId;
    }

    public Object getFoodmakerAccessType() {
        return foodmakerAccessType;
    }

    public void setFoodmakerAccessType(Integer foodmakerAccessType) {
        this.foodmakerAccessType = foodmakerAccessType;
    }

    public String getFoodmakerImagePath() {
        return foodmakerImagePath;
    }

    public void setFoodmakerImagePath(String foodmakerImagePath) {
        this.foodmakerImagePath = foodmakerImagePath;
    }

    public Integer getFoodmakerActive() {
        return foodmakerActive;
    }

    public void setFoodmakerActive(Integer foodmakerActive) {
        this.foodmakerActive = foodmakerActive;
    }

    public String getFoodmakerPhoneNumber() {
        return foodmakerPhoneNumber;
    }

    public void setFoodmakerPhoneNumber(String foodmakerPhoneNumber) {
        this.foodmakerPhoneNumber = foodmakerPhoneNumber;
    }

    public Object getFoodmakerAdminId() {
        return foodmakerAdminId;
    }

    public void setFoodmakerAdminId(Integer foodmakerAdminId) {
        this.foodmakerAdminId = foodmakerAdminId;
    }

    public Object getFoodmakerCreatedAt() {
        return foodmakerCreatedAt;
    }

    public void setFoodmakerCreatedAt(String foodmakerCreatedAt) {
        this.foodmakerCreatedAt = foodmakerCreatedAt;
    }

    public Object getFoodmakerLastUpdated() {
        return foodmakerLastUpdated;
    }

    public void setFoodmakerLastUpdated(String foodmakerLastUpdated) {
        this.foodmakerLastUpdated = foodmakerLastUpdated;
    }

    public Double getAverageRatings() {
        return averageRatings;
    }

    public void setAverageRatings(Double averageRatings) {
        this.averageRatings = averageRatings;
    }

    public String getFoodmakerRegToken() {
        return foodmakerRegToken;
    }

    public void setFoodmakerRegToken(String foodmakerRegToken) {
        this.foodmakerRegToken = foodmakerRegToken;
    }

}
