package com.jayzonsolutions.LunchBox.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class OrderDish {

    @SerializedName("orderDishesId")
    @Expose
    private Integer orderDishesId;
    @SerializedName("dishId")
    @Expose
    private Integer dishId;
    @SerializedName("quantity")
    @Expose
    private Double quantity;
    @SerializedName("dishes")
    @Expose
    private FoodmakerDishes dishes;

    public Integer getOrderDishesId() {
        return orderDishesId;
    }

    public void setOrderDishesId(Integer orderDishesId) {
        this.orderDishesId = orderDishesId;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public FoodmakerDishes getDishes() {
        return dishes;
    }

    public void setDishes(FoodmakerDishes dishes) {
        this.dishes = dishes;
    }

}
