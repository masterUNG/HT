package com.openworldtravels.www.hypertension;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class FirstActivity extends AppCompatActivity {

    //Explicit
    private Button signInButton, signUpButton;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //Bind Widget
        signInButton = (Button) findViewById(R.id.button);
        signUpButton = (Button) findViewById(R.id.button2);
        userEditText = (EditText) findViewById(R.id.editText4);
        passwordEditText = (EditText) findViewById(R.id.editText5);

        //signIn Controller
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get Value
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                //Check Space
                if (userString.equals("") || passwordString.equals("")) {
                    //Have Space
                    MyAlert myAlert = new MyAlert(FirstActivity.this, "มีช่องว่าง",
                            "กรุณากรอกทุกช่อง คะ");
                    myAlert.myDialog();

                } else {
                    //No Space
                    MyConstant myConstant = new MyConstant();
                    SynUser synUser = new SynUser(FirstActivity.this);
                    synUser.execute(myConstant.getUrlAllJSONString());

                }

            }   // onClick
        });



        //signUp Controller
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FirstActivity.this, SignUpActivity.class);
                startActivity(intent);

            }   // onClick
        });

    }   // Main Method

    private class SynUser extends AsyncTask<String, Void, String> {

        //Explicit
        private Context context;
        private String[] imageStrings, nameStrings;
        private boolean aBoolean = true;
        private String truePasswordString;

        public SynUser(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strings[0]).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }   // doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("20octV2", "JSON ==> " + s);

            try {

                JSONArray jsonArray = new JSONArray(s);

                imageStrings = new String[jsonArray.length()];
                nameStrings = new String[jsonArray.length()];

                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    //setup for Array
                    imageStrings[i] = jsonObject.getString("Image");
                    nameStrings[i] = jsonObject.getString("Name");

                    //Check User
                    if (userString.equals(jsonObject.getString("User"))) {

                        aBoolean = false;
                        truePasswordString = jsonObject.getString("Password");

                    }   // if

                }   // for

                if (aBoolean) {
                    //User False
                    MyAlert myAlert = new MyAlert(context, "User False",
                            "No " + userString + " in my Database");
                    myAlert.myDialog();

                } else if (!passwordString.equals(truePasswordString)) {
                    //Password False
                    MyAlert myAlert = new MyAlert(context, "Password False",
                            "Please Try Again Password False");
                    myAlert.myDialog();

                } else {
                    //Password True
                    Toast.makeText(context, "Welcome to my App", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(FirstActivity.this, ServiceActivity.class);
                    intent.putExtra("Image", imageStrings);
                    intent.putExtra("Name", nameStrings);
                    startActivity(intent);
                    finish();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }   // try

        }   // onPost

    }   // SynUser Class

}   // Main Class
