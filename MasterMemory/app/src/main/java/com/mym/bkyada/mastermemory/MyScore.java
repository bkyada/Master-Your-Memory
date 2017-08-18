package com.mym.bkyada.mastermemory;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyScore extends Activity {

    private DBHelper myDB;
    private CommonUtils commonUtils;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_score);
        commonUtils = new CommonUtils(this);
        user = commonUtils.getUserSessionDetails();
        myDB = new DBHelper(this);

        TextView easy = (TextView) findViewById(R.id.easyScore);
        easy.setText(myDB.getMyScore(user.getName(), "easy"));
        TextView medium = (TextView) findViewById(R.id.mediumScore);
        medium.setText(myDB.getMyScore(user.getName(), "medium"));
        TextView hard = (TextView) findViewById(R.id.hardScore);
        hard.setText(myDB.getMyScore(user.getName(), "hard"));
    }

    public void back(View view) {
        finish();
    }

}
