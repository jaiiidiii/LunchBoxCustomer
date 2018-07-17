package com.jayzonsolutions.LunchBox.model;

public class CartItem {
    private int foodmakerId;
    private FoodmakerDishes foodmakerDishes;

    private double quantity;

    public CartItem(){}
    public CartItem(int foodmakerId, FoodmakerDishes foodmakerDishes, double quantity) {
        this.foodmakerId = foodmakerId;
        this.foodmakerDishes = foodmakerDishes;

        this.quantity = quantity;
    }

    public int getFoodmakerId() {
        return foodmakerId;
    }

    public void setFoodmakerId(int foodmakerId) {
        this.foodmakerId = foodmakerId;
    }

    public FoodmakerDishes getFoodmakerDishes() {
        return foodmakerDishes;
    }

    public void setFoodmakerDishes(FoodmakerDishes foodmakerDishes) {
        this.foodmakerDishes = foodmakerDishes;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }


}
