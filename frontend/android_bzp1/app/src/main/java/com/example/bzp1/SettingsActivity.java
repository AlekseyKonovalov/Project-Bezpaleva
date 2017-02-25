package com.example.bzp1;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.radlist, android.R.layout.simple_spinner_item);

        //Настраиваем внешний вид выпадающего списка, используя готовый системный шаблон:
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int position = adapter.getPosition(Integer.toString(radius));
        spinner.setAdapter(adapter);
        spinner.setSelection(position);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
        ((TextView) parent.getChildAt(0)).setTextSize(17);

        radius=Integer.parseInt(item);
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.backmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_revert:{
                Intent intent=new Intent(this, LoginActivity.class);
                intent.putExtra("radius", radius);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent intent=new Intent(this, LoginActivity.class);
        intent.putExtra("radius", radius);
        startActivity(intent);
    }


}

