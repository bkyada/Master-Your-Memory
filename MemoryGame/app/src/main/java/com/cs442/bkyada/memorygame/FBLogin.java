package com.cs442.bkyada.memorygame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;


public class FBLogin extends Activity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        loginButton = (LoginButton) findViewById(R.id.btn_FBlogin);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));

        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                try {
                                    String name = object.getString("name");
                                    String email = object.getString("email"); // 01/31/1980 format
                                    SecureRandom random = new SecureRandom();

                                    Context applicationContext = getApplicationContext();

                                    DBHelper myDB = new DBHelper(applicationContext);
                                    CommonUtils commonUtils = new CommonUtils(applicationContext);

                                    User userFromDb = myDB.getUserByEmail(email);
                                    if(userFromDb != null) {
                                        commonUtils.saveSessionDetails(userFromDb.getId(), userFromDb.getName(),
                                                userFromDb.getEmail(), userFromDb.getEasyUnlockLevel(),
                                                userFromDb.getMediumUnlockLevel(), userFromDb.getHardUnlockLevel());
                                        Toast.makeText(getApplication(), "Welcome "+userFromDb.getName(), Toast.LENGTH_LONG ).show();
                                        goHome();
                                    } else {

                                        boolean inserted = myDB.insertUser(name, email, new BigInteger(130, random).toString(32));
                                        if(inserted) {
                                            User userFromDb1 = myDB.getUserByEmail(email);

                                            commonUtils.saveSessionDetails(userFromDb1.getId(), userFromDb1.getName(),
                                                    userFromDb1.getEmail(), userFromDb1.getEasyUnlockLevel(),
                                                    userFromDb1.getMediumUnlockLevel(), userFromDb1.getHardUnlockLevel());

                                            //Toast.makeText(applicationContext, "User Logged in successfully!", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(getApplication(), "Welcome "+name, Toast.LENGTH_LONG ).show();
                                            goHome();
                                        }
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();
                //Toast.makeText(getApplicationContext(),"User ID:" + loginResult.getAccessToken().getUserId()+ "\n" +
                //        "Auth Token: " + loginResult.getAccessToken().getToken(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login Cancelled", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Login Error", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void goHome() {
        Intent i = new Intent(this, Home.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
