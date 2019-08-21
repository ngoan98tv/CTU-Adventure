package com.ctu.ctu_explorer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class BuildingsActivity extends AppCompatActivity {
    TextView tv, tv2;
    ScrollView sv;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_buildings);

        tv = (TextView)findViewById(R.id.tv);
        tv2 = (TextView) findViewById(R.id.tv2);
        sv = (ScrollView)findViewById(R.id.sv);
        iv = (ImageView)findViewById(R.id.iv);
    }
}