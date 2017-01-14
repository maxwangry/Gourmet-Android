package com.app.ruoyu.gourmet;

/**
 * A class for restaurant, which contains all information of a restaurant.
 */
public class Restaurant {
    /**
     * All data for a restaurant.
     */
    private String name;
    private String address;
    private String type;
    private double lat;
    private double lng;

    /**
     * Constructor
     *
     * @param name name of the restaurant
     */
    public Restaurant(String name, String address, String type, double lat, double lng) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.lat = lat;
        this.lng = lng;
    }

    /**
     * Getters for private attributes of Restaurant class.
     */
    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getType() {
        return this.type;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

}