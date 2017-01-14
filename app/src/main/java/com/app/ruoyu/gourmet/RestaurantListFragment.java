package com.app.ruoyu.gourmet;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantListFragment extends Fragment {


    OnItemSelectListener mCallback;


    public RestaurantListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.restaurant_list);
        listView.setAdapter(new RestaurantAdapter(getActivity()));

        // Set a listener to ListView.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant r = (Restaurant) listView.getItemAtPosition(position);
                //Create explicit intent to start map activity class
                Intent intent = new Intent(view.getContext(), RestaurantMapActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private String[] getRestaurantNames() {
        return new String[]{
                "Restaurant1", "Restaurant2", "Restaurant3",
                "Restaurant4", "Restaurant5", "Restaurant6",
                "Restaurant7", "Restaurant8", "Restaurant9",
                "Restaurant10"};
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnItemSelectListener) context;
        } catch (ClassCastException e) {
        }
    }

    // Container Activity must implement this interface
    public interface OnItemSelectListener {
        void onItemSelected(int position);
    }

}
