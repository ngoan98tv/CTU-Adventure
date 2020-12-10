package com.ctu.ctu_explorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class BuildingsActivity extends AppCompatActivity {
    TextView tv, tv2;
    ScrollView sv;
    ImageView iv;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buildings);

        Intent intent = getIntent();
        code = intent.getStringExtra("code");
        Buildings buildings = new Buildings(BuildingsActivity.this);

        tv = (TextView)findViewById(R.id.tv);
        tv2 = (TextView) findViewById(R.id.tv2);
        sv = (ScrollView)findViewById(R.id.sv);
        iv = (ImageView)findViewById(R.id.iv);

        tv.setText(buildings.getNameByCode(code));
        tv2.setText(buildings.getDescriptionByCode(code));
    }
}