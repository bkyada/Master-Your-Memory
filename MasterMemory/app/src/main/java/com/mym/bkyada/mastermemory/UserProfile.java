package com.mym.bkyada.mastermemory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfile extends Activity {
    private CommonUtils commonUtils ;
    private DBHelper myDB;
    TextView txtName;
    TextView txtEmail;
    User user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        commonUtils = new CommonUtils(this);
        myDB = new DBHelper(this);
        user = commonUtils.getUserSessionDetails();
        txtName = (TextView) findViewById(R.id.txt_prof_name);
        txtName.setText(user.getName());

        txtEmail = (TextView) findViewById(R.id.txt_prof_email);
        txtEmail.setText(user.getEmail());

        ImageView changeName = (ImageView) findViewById(R.id.img_right_edit_name);
        changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtNewName = (EditText) findViewById(R.id.edittxt_name);
                String name = (String) txtNewName.getText().toString();
                if(name.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Please enter username.", Toast.LENGTH_SHORT).show();
                } else {
                    boolean success = myDB.updateName(user.getEmail(), name);
                    if (success) {
                        Toast.makeText(getApplicationContext(), "Name has been updated successfully", Toast.LENGTH_SHORT).show();
                        commonUtils.saveSessionDetails(user.getId(), name, user.getEmail(), user.getEasyUnlockLevel(), user.getMediumUnlockLevel(), user.getHardUnlockLevel());
                        txtName.setText(name);
                    }
                }
            }
        });

        ImageView changeEmail = (ImageView) findViewById(R.id.img_right_edit_email);
        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtNewEmail = (EditText) findViewById(R.id.edittxt_email);
                String email = (String) txtNewEmail.getText().toString();

                if (email.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Please enter email address.", Toast.LENGTH_SHORT).show();
                } else if (!myDB.validateEmail(email)) {
                    Toast.makeText(getApplicationContext(),"Invalid email address.\nPlease enter proper email address.", Toast.LENGTH_SHORT).show();
                } else {
                    boolean success =myDB.updateEmail(user.getEmail(),email);
                    if (success) {
                        Toast.makeText(getApplicationContext(), "Email has been updated successfully", Toast.LENGTH_SHORT).show();
                        commonUtils.saveSessionDetails(user.getId(), user.getName(), email, user.getEasyUnlockLevel(), user.getMediumUnlockLevel(), user.getHardUnlockLevel());
                    }
                }
            }
        });

        ImageView changePass = (ImageView) findViewById(R.id.img_right_pass);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtNewPass = (EditText) findViewById(R.id.edittxt_pass);
                String pass = (String) txtNewPass.getText().toString();

                if (pass.trim().length() < 4) {
                    Toast.makeText(getApplicationContext(),"Password should be more than 4 characters.", Toast.LENGTH_SHORT).show();
                } else {
                    boolean success =myDB.updateName(user.getEmail(),pass);
                    if(success) {
                        Toast.makeText(getApplicationContext(), "Password has been updated successfully",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void back(View view) {
        finish();
    }

}
