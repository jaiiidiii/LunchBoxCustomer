package com.jayzonsolutions.LunchBox;

public class GlobalVariables {
    private static GlobalVariables instance;

    private String CustomerId;
    private double Latitude;
    private double Longitude;
    private String UserName;
    private String RegistrationId;

    public static GlobalVariables getInstance() {
        return instance;
    }

    public static void setInstance(GlobalVariables instance) {
        GlobalVariables.instance = instance;
    }

    public static synchronized GlobalVariables GetInstance() {
        if (instance == null) {
            instance = new GlobalVariables();
        }
        return instance;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getRegistrationId() {
        return RegistrationId;
    }

    public void setRegistrationId(String registrationId) {
        RegistrationId = registrationId;
    }

    public void ResetVariables() {

        CustomerId = null;
        Latitude = 0;
        Longitude = 0;
        UserName = null;
        RegistrationId = null;
    }

}
