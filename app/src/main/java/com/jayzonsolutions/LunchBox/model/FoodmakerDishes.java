package com.jayzonsolutions.LunchBox.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodmakerDishes {

    @SerializedName("foodmakerDishesId")
    @Expose
    private Integer foodmakerDishesId;
    @SerializedName("dishId")
    @Expose
    private Integer dishId;
    @SerializedName("foodmakerid")
    @Expose
    private Integer foodmakerid;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("imagepath")
    @Expose
    private String imagepath;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("foodmaker")
    @Expose
    private Foodmaker foodmaker;
    @SerializedName("dishes")
    @Expose
    private Dish dish;

    public Integer getFoodmakerDishesId() {
        return foodmakerDishesId;
    }

    public void setFoodmakerDishesId(Integer foodmakerDishesId) {
        this.foodmakerDishesId = foodmakerDishesId;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public Integer getFoodmakerid() {
        return foodmakerid;
    }

    public void setFoodmakerid(Integer foodmakerid) {
        this.foodmakerid = foodmakerid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Foodmaker getFoodmaker() {
        return foodmaker;
    }

    public void setFoodmaker(Foodmaker foodmaker) {
        this.foodmaker = foodmaker;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

}

