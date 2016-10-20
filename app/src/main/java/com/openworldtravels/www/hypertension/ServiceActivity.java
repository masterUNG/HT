package com.openworldtravels.www.hypertension;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
        final String[] nameStrings = getIntent().getStringArrayExtra("Name");
        String[] iconStrings = getIntent().getStringArrayExtra("Image");

        //Create ListView
        UserAdapter userAdapter = new UserAdapter(ServiceActivity.this, nameStrings,
                iconStrings);
        listView.setAdapter(userAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                MyAlert myAlert = new MyAlert(ServiceActivity.this,
                        nameStrings[i], "รายละเอียด");
                myAlert.myDialog();

            }   // onItemClick
        });

    }   // Main Method

}   // Main Class
