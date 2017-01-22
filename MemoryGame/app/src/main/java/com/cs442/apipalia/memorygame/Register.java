package com.cs442.apipalia.memorygame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {
    private DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register();
    }

    public void register() {
        final EditText txt_rUsername = (EditText) findViewById(R.id.txt_rUsername);
        final EditText txt_rEmail = (EditText) findViewById(R.id.txt_rEmail);
        final EditText txt_rPassword = (EditText) findViewById(R.id.txt_rPassword);
        final EditText txt_rConfirmPassword = (EditText) findViewById(R.id.txt_rConfirmPassword);
        Button btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = txt_rUsername.getText().toString().trim();
                String email = txt_rEmail.getText().toString().trim();
                String password = txt_rPassword.getText().toString().trim();
                String confirmPassword = txt_rConfirmPassword.getText().toString().trim();
                Context applicationContext = getApplicationContext();
                myDB = new DBHelper(applicationContext);
                Result result = myDB.validateUserDetails(userName, email, password, confirmPassword);
                if(result.isSuccess()) {
                    boolean inserted = myDB.insertUser(userName, email, password);
                    if(inserted) {
                        Toast.makeText(applicationContext, "User registered successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(applicationContext, Login.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(applicationContext, result.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void back(View view) {
        finish();
    }
}
