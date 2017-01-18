package com.app.ruoyu.gourmet;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackendListFragment extends Fragment {

    private Fragment mfragment;
    private DataService dataService;
    private ListView mListView;

    public BackendListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_backend_list, container, false);
        mfragment = this;
        dataService = new DataService(getActivity());
        mListView = (ListView) view.findViewById(R.id.backend_list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant r = (Restaurant) mListView.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable(RestaurantMapActivity.EXTRA_LATLNG,
                        new LatLng(r.getLat(), r.getLng()));
                Intent intent = new Intent(view.getContext(), RestaurantMapActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        updateRestaurant(Config.user_name);
        return view;
    }

    public void updateRestaurant(String user_id) {
        String url2 = "http://laiproject.mooo.com/chihuo/recommendation?user_id=" + user_id;
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new GetRestaurantsFromBackendAsyncTask(mfragment, response, dataService).execute();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Cookie", Config.cookies);
                return headers;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest2);
    }

    private class GetRestaurantsFromBackendAsyncTask extends AsyncTask<Void, Void, List<Restaurant>> {
        private DataService dataService;
        private String response;
        private List<Restaurant> restaurantList;

        public GetRestaurantsFromBackendAsyncTask(Fragment fragment, String response, DataService dataService) {
            this.response = response;
            restaurantList = new ArrayList<>();
            this.dataService = dataService;
        }

        @Override
        protected List<Restaurant> doInBackground(Void... params) {
            try {
                JSONArray reader = new JSONArray(response);
                for (int i = 0; i < reader.length(); i++) {
                    JSONObject item = reader.getJSONObject(i);
                    Restaurant restaurant = new Restaurant();
                    restaurant.setName(item.getString("name"));
                    restaurant.setAddress(item.getString("full_address"));
                    restaurant.setLat(item.getDouble("latitude"));
                    restaurant.setLng(item.getDouble("longitude"));
                    restaurant.setStars(item.getDouble("stars"));
                    restaurant.setThumbnail(dataService.getBitmapFromURL(item.getString("image_url")));

                    JSONArray category = item.getJSONArray("categories");
                    List<String> cat = new ArrayList<>();
                    for (int j = 0; j < category.length(); j++) {
                        cat.add(category.get(j).toString());
                    }
                    restaurant.setCategories(cat);
                    restaurantList.add(restaurant);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            return restaurantList;
        }

        @Override
        protected void onPostExecute(List<Restaurant> restaurants) {
            if (restaurants != null) {
                super.onPostExecute(restaurants);
                RestaurantAdapter adapter = new RestaurantAdapter(mfragment.getActivity(), restaurants);
                mListView.setAdapter(adapter);
            } else {
                Toast.makeText(mfragment.getActivity(), "Data service error.", Toast.LENGTH_LONG);
            }
        }
    }
}

