package com.app.ruoyu.gourmet;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for retrieving data from backend.
 */
public class DataService {
    /**
     * Fake all the restaurant data for now. We will refine this and connect
     * to our backend later.
     */
    public static List<Restaurant> getRestaurantData() {
        List<Restaurant> restaurantData = new ArrayList<Restaurant>();
        for (int i = 0; i < 10; ++i) {
            restaurantData.add(
                    new Restaurant("Restaurant", "1184 W valley Blvd, CA 90101",
                            "New American", i * 7 + 7, i * 5 - 5));
        }
        return restaurantData;
    }
}