package com.app.ruoyu.gourmet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.LruCache;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for retrieving data from backend.
 */
public class DataService {
    private LruCache<String, Bitmap> bitmapCache;
    private Context mContext;

    public DataService(Context context) {
        mContext = context;
    }

    public DataService() {
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;
        Log.e("Cache size", Integer.toString(cacheSize));

        bitmapCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes.
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    /**
     * Get nearby restaurants through Yelp API.
     */
    public List<Restaurant> getNearbyRestaurants() {
        YelpApi yelp = new YelpApi();
        String jsonResponse = yelp.searchForBusinessesByLocation("dinner", "San Francisco, CA", 20);
        return parseResponse(jsonResponse);
    }

    /**
     * Parse the JSON response returned by Yelp API.
     */
    private List<Restaurant> parseResponse(String jsonResponse) {
        try {
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray businesses = json.getJSONArray("businesses");
            ArrayList<Restaurant> restaurants = new ArrayList<>();
            for (int i = 0; i < businesses.length(); i++) {
                JSONObject business = businesses.getJSONObject(i);
                //Parse restaurant information
                if (business != null) {
                    String name = business.getString("name");
                    String type = ((JSONArray) business.get("categories")).
                            getJSONArray(0).get(0).toString();
                    JSONObject location = (JSONObject) business.get("location");
                    JSONObject coordinate = (JSONObject) location.get("coordinate");
                    double lat = coordinate.getDouble("latitude");
                    double lng = coordinate.getDouble("longitude");
                    String address =
                            ((JSONArray) location.get("display_address")).get(0).toString();
                    // Download the image.
                    Bitmap thumbnail = getBitmapFromURL(business.getString("image_url"));
                    Bitmap rating = getBitmapFromURL(business.getString("rating_img_url"));
                    restaurants.add(
                            new Restaurant(name, address, type, lat, lng, thumbnail, rating));
                }
            }
            return restaurants;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Download an Image from the given URL, then decodes and returns a Bitmap object.
     */
    public Bitmap getBitmapFromURL(String imageUrl) {
        Bitmap bitmap = null;
        if (bitmapCache != null) {
            bitmap = bitmapCache.get(imageUrl);
        }
        if (bitmap == null) {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
                if (bitmapCache != null) {
                    bitmapCache.put(imageUrl, bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Error: ", e.getMessage());
            }
        }
        return bitmap;
    }
}