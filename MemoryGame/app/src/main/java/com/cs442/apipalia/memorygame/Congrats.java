package com.cs442.apipalia.memorygame;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Congrats extends Activity {

    String difficulty;
    String category;
    Integer startLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulation);

        Intent intent = getIntent();
        String score = intent.getExtras().get("score").toString();

        category = intent.getExtras().get("category").toString().toLowerCase();
        startLevel = (Integer) intent.getExtras().get("startLevel");
        difficulty = intent.getExtras().get("difficulty").toString();

        TextView tv_score = (TextView) findViewById(R.id.txt_scoreValue);
        tv_score.setText(score);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void goHome(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void goNext(View view) {
        Intent game = new Intent(this, Game.class);
        game.putExtra("category", category);
        game.putExtra("difficulty", difficulty);
        game.putExtra("startLevel", startLevel);
        this.startActivity(game);
    }
}
