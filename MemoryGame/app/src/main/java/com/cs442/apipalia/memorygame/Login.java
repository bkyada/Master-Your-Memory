package com.cs442.apipalia.memorygame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;

public class Login extends Activity {
    private DBHelper myDB;
    private CommonUtils commonUtils ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonUtils = new CommonUtils(this);
        if(commonUtils.isUserLoggedIn()) {
            Intent i = new Intent(Login.this, Home.class);
            startActivity(i);
        }
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        login();

        TextView btn_forgotPassword=(TextView) findViewById(R.id.txt_forgotPassword);
        btn_forgotPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });

        TextView btn_howtoplay=(TextView) findViewById(R.id.txt_howToPlay);
        btn_howtoplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                howToPlay();
            }
        });
    }

    public void login() {

        final Context applicationContext = getApplicationContext();
        myDB = new DBHelper(applicationContext);
        commonUtils = new CommonUtils(this);

        Button loginBtn = (Button) findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                EditText txt_rUserEmail = (EditText) findViewById(R.id.txt_username);
                EditText txt_password = (EditText) findViewById(R.id.txt_password);

                String userEmail = txt_rUserEmail.getText().toString().trim();
                String password = txt_password.getText().toString().trim();

                myDB = new DBHelper(applicationContext);
                Result result = myDB.validateUserEmailPassword(userEmail, password);

                if(result.isSuccess()) {
                    // validated
                    User userFromDb = myDB.getUserByEmail(userEmail);
                    if(userFromDb != null && userFromDb.getPassword().equals(password)) {
                        commonUtils.saveSessionDetails(userFromDb.getId(), userFromDb.getName(),
                                userFromDb.getEmail(), userFromDb.getEasyUnlockLevel(),
                                userFromDb.getMediumUnlockLevel(), userFromDb.getHardUnlockLevel());
                        Toast.makeText(getApplication(), "Welcome "+userFromDb.getName(), Toast.LENGTH_LONG ).show();
                        Intent i = new Intent(applicationContext, Home.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplication(), "Invalid email or password.", Toast.LENGTH_LONG ).show();
                    }
                } else {
                    Toast.makeText(getApplication(), result.getMsg(), Toast.LENGTH_LONG ).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void createNewAccount(View view) {
        Intent register = new Intent(this, Register.class);
        startActivity(register);
    }

    public void forgotPassword() {
        Intent forgotPassword = new Intent(this, ForgotPassword.class);
        startActivity(forgotPassword);
    }

    public void topScores(View view) {
        Intent topScores = new Intent(this, TopScores.class);
        startActivity(topScores);
    }

    public void howToPlay() {
        Intent help = new Intent(this, Help.class);
        help.putExtra("parent", "login");
        startActivity(help);
    }

}