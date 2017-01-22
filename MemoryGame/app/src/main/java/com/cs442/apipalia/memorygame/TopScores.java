package com.cs442.apipalia.memorygame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class TopScores extends Activity {

    ListView list_score;
    private DBHelper myDB;
    Button easy, normal, hard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_scores);
        list_score = (ListView) findViewById(R.id.list_score);
        myDB = new DBHelper(this);
        easy = (Button) findViewById(R.id.btn_easy);
        normal = (Button) findViewById(R.id.btn_normal);
        hard = (Button) findViewById(R.id.btn_hard);
        showEasy(null);
    }

    public void back(View view) {
        finish();
    }

    public void showEasy(View view) {
        easy.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_scores_buttons_selected));
        normal.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_scores_buttons));
        hard.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_scores_buttons));
        ScoreAdapter scoreAdapter = new ScoreAdapter(this, R.layout.list_top_scores, myDB.getTopScores("easy"));
        list_score.setAdapter(scoreAdapter);
    }

    public void showMedium(View view) {
        easy.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_scores_buttons));
        normal.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_scores_buttons_selected));
        hard.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_scores_buttons));
        ScoreAdapter scoreAdapter = new ScoreAdapter(this, R.layout.list_top_scores, myDB.getTopScores("medium"));
        list_score.setAdapter(scoreAdapter);
    }

    public void showHard(View view) {
        easy.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_scores_buttons));
        normal.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_scores_buttons));
        hard.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_scores_buttons_selected));
        ScoreAdapter scoreAdapter = new ScoreAdapter(this, R.layout.list_top_scores, myDB.getTopScores("hard"));
        list_score.setAdapter(scoreAdapter);
    }
}
