package com.app.ruoyu.gourmet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class RestaurantAdapter extends BaseAdapter {
    Context context;
    List<Restaurant> restaurantData;

    public RestaurantAdapter(Context context, List<Restaurant> restaurantData) {
        this.context = context;
        this.restaurantData = restaurantData;
    }

    @Override
    public int getCount() {
        return restaurantData.size();
    }

    @Override
    public Restaurant getItem(int position) {
        return restaurantData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_restaurant_list_item,
                    parent, false);
        }

        TextView restaurantName = (TextView) convertView.findViewById(
                R.id.restaurant_name);
        TextView restaurantAddress = (TextView) convertView.findViewById(
                R.id.restaurant_address);
        TextView restaurantType = (TextView) convertView.findViewById(
                R.id.restaurant_type);
        ImageView restaurantThumbnail = (ImageView) convertView.findViewById(
                R.id.restaurant_thumbnail);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.restaurant_rating_bar);
        ImageView restaurantRating = (ImageView) convertView.findViewById(
                R.id.restaurant_rating);

        Restaurant r = restaurantData.get(position);
        if (r.getRating() == null) {
            restaurantRating.setVisibility(View.GONE);
            ratingBar.setVisibility(View.VISIBLE);
            ratingBar.setRating((float) r.getStars());
        }
        restaurantName.setText(r.getName());
        restaurantAddress.setText(r.getAddress());
        restaurantType.setText((r.getType() == null) ? r.getCategories().get(0) : r.getType());
        restaurantType.setText(r.getType());
        restaurantThumbnail.setImageBitmap(r.getThumbnail());
        restaurantRating.setImageBitmap(r.getRating());
        return convertView;
    }
}