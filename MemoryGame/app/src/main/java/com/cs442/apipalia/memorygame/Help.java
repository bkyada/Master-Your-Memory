package com.cs442.apipalia.memorygame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.FragmentManager;
import android.app.Fragment;
import android.app.FragmentTransaction;

public class Help extends Activity {

    Button rules,video;
    String parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        rules = (Button) findViewById(R.id.btn_rules);
        video = (Button) findViewById(R.id.btn_video);

        Intent i = getIntent();
        parent = i.getExtras().get("parent").toString();

    }

    //Fragment fragment;
    public void changeFragment(View view){
        Fragment fragment;
        if(view == findViewById(R.id.btn_rules)){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment = new HelpRuleFragment();
            ft.replace(R.id.fragment1,fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
        else if(view == findViewById(R.id.btn_video)){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment = new HelpFragment();
            ft.replace(R.id.fragment1,fragment);
            ft.addToBackStack(null);
            ft.commit();

        }
    }

    @Override
    public void onBackPressed() {
        if ("login".equals(parent)) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
        if ("home".equals(parent)) {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
    }
}
