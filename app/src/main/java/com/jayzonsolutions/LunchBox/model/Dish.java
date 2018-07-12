package com.jayzonsolutions.LunchBox.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dish {
    @SerializedName("dishId")
    @Expose
    private Integer dishId;
    @SerializedName("dishName")
    @Expose
    private String dishName;
    @SerializedName("dishSellingPrice")
    @Expose
    private Integer dishSellingPrice;
    @SerializedName("dishAvailableTime")
    @Expose
    private String dishAvailableTime;
    @SerializedName("dishImagePath")
    @Expose
    private String dishImagePath;

    private Integer dishQuantity =0;

    private String dishPriceAsPerQuantity;

    public String getDishPriceAsPerQuantity() {
        return dishPriceAsPerQuantity;
    }

    public void setDishPriceAsPerQuantity(String dishPriceAsPerQuantity) {
        this.dishPriceAsPerQuantity = dishPriceAsPerQuantity;
    }

    public Integer getDishQuantity() {
        return dishQuantity;
    }

    public void setDishQuantity(Integer dishQuantity) {
        this.dishQuantity = dishQuantity;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Integer getDishSellingPrice() {
        return dishSellingPrice;
    }

    public void setDishSellingPrice(Integer dishSellingPrice) {
        this.dishSellingPrice = dishSellingPrice;
    }

    public String getDishAvailableTime() {
        return dishAvailableTime;
    }

    public void setDishAvailableTime(String dishAvailableTime) {
        this.dishAvailableTime = dishAvailableTime;
    }

    public String getDishImagePath() {
        return dishImagePath;
    }

    public void setDishImagePath(String dishImagePath) {
        this.dishImagePath = dishImagePath;
    }
}
