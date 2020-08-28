package com.honorsmobileapps.noahshields.smashutilities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Layout of the entry screen
        RelativeLayout startLayout = (RelativeLayout) findViewById(R.id.RelativeLayout_StartLayout);
        startLayout.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Intent startNext = new Intent(this, MainStartActivity.class);
        startActivity(startNext);
    }
}
