package com.example.bzp1;
import android.content.Intent;
import android.view.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void butInputSystem_Click(View v) {
        Intent intent = new Intent(MainActivity.this, InputSystemActivity.class);
        startActivity(intent);
    }

    public void butInfo_Click(View v) {
        Intent intent = new Intent(MainActivity.this, InformationActivityy.class);
        startActivity(intent);
    }

    public void butMap_Click(View v) {
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        startActivity(intent);
    }

    public void  butSettings_Click(View v) {
        //
    }

    public void  butContacts_Click(View v) {
        //
    }


}
