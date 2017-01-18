package com.app.ruoyu.gourmet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button buttonYelp;
    Button buttonBackend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonYelp = (Button) findViewById(R.id.button_yelp);
        buttonBackend = (Button) findViewById(R.id.button_backend);

        buttonYelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RestaurantListActivity.class);
                startActivity(intent);
            }
        });

        buttonBackend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonBackend.setText("New text");
            }
        });
    }
}
