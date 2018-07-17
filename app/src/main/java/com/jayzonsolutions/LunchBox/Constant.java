package com.jayzonsolutions.LunchBox;

import java.util.HashMap;
import java.util.Map;

public class Constant {

    public static String userId;
    public static Map<Integer,Double> orderdishes = new HashMap<>(); // foodmakerDishId , quantity
    public static Map<Integer,Map<Integer,Double>> foodmakerdishes = new HashMap<>(); // foodmakerId , orderdisheshashmap
}
