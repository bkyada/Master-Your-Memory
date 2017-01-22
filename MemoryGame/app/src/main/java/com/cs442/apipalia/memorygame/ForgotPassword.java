package com.cs442.apipalia.memorygame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForgotPassword extends Activity {
    private DBHelper mydb ;
   // String password="1234";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Button button = (Button) findViewById(R.id.btn_SendPassword);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPassword();
            }
        });

    }


    public void sendPassword() {
        EditText editText=(EditText)findViewById(R.id.txt_email);
        String email = editText.getText().toString().trim();
        mydb = new DBHelper(this);
        User u = mydb.getUserByEmail(email);
        SendMail sm = new SendMail(this,email,u.getPassword());
        sm.execute();
    }

    public void back(View view) {
        finish();
    }
}
