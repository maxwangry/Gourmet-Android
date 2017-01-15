package com.app.ruoyu.gourmet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class RestaurantListActivity extends AppCompatActivity implements RestaurantListFragment.OnItemSelectListener {

    RestaurantListFragment listFragment;
    RestaurantGridFragment gridFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
        if (findViewById(R.id.fragment_container) != null) {
            listFragment = new RestaurantListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_list_container, listFragment).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Life cycle test", "We are at onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Life cycle test", "We are at onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Life cycle test", "We are at onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Life cycle test", "We are at onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Life cycle test", "We are at onDestroy()");
    }

    @Override
    public void onItemSelected(int position) {
        gridFragment.onItemSelected(position);
    }
}
