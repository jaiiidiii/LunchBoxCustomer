package com.jayzonsolutions.LunchBox.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
   // public static HashMap<Integer,List<CartItem>> cartItems =  new HashMap<>();
    public static Map<Integer,CartItem> orderdishes = new HashMap<>(); // foodmakerDishId , CartItem
    public static Map<Integer,Map<Integer,CartItem>> foodmakerdishes = new HashMap<>(); // foodmakerId , orderdisheshashmap


}
