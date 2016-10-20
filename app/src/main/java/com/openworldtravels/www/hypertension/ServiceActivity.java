package com.openworldtravels.www.hypertension;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class ServiceActivity extends AppCompatActivity {

    //Explicit
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        //Bind Widget
        listView = (ListView) findViewById(R.id.livUser);

        //Receive Value From Intent
        String[] nameStrings = getIntent().getStringArrayExtra("Name");
        String[] iconStrings = getIntent().getStringArrayExtra("Image");

        //Create ListView
        UserAdapter userAdapter = new UserAdapter(ServiceActivity.this, nameStrings,
                iconStrings);
        listView.setAdapter(userAdapter);

    }   // Main Method

}   // Main Class
