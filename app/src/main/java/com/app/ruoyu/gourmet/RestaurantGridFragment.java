package com.app.ruoyu.gourmet;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantGridFragment extends Fragment {

    GridView gridView;

    public RestaurantGridFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_grid, container, false);
        gridView = (GridView) view.findViewById(R.id.restaurant_grid);
        gridView.setAdapter(new RestaurantAdapter(getActivity()));
        return view;
    }

    public void onItemSelected(int position) {
        for (int i = 0; i < gridView.getChildCount(); i++) {
            if (position == i) {
                gridView.getChildAt(i).setBackgroundColor(Color.BLUE);
            } else {
                gridView.getChildAt(i).setBackgroundColor(Color.parseColor("#EEEEEE"));
            }
        }

    }
}
