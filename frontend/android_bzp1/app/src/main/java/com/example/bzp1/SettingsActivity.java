package com.example.bzp1;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private int radius;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        radius=100;
        try{
            radius=getIntent().getExtras().getInt("radius");
        }
        catch (Exception e){};
        Log.i("bzp1", "SetRadius  " + Integer.toString(radius));

        setContentView(R.layout.activity_settings);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        List<String> elements = new ArrayList<String>();
        elements.add("100");
        elements.add("50");
        elements.add("10");


        //Создаем для spinner адаптер:
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, elements);

        //Настраиваем внешний вид выпадающего списка, используя готовый системный шаблон:
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Присоединяем адаптер данных к spinner:
        spinner.setAdapter(dataAdapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        radius=Integer.parseInt(item);
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent intent=new Intent(this, LoginActivity.class);
        intent.putExtra("radius", radius);
        startActivity(intent);
    }
}

