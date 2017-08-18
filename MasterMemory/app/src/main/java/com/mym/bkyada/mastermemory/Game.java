package com.mym.bkyada.mastermemory;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends AppCompatActivity {
    private static int ROW_COUNT = 0;
    private static int COL_COUNT = 0;
    private Context context;
    private Drawable backImage;
    private int[][] tiles;
    private List<Drawable> images;
    private Tile firstTile, secondTile;
    private static final Object lock = new Object();
    private int tries = 0;
    private int hits = 0;
    private int score = 0;
    private TableLayout mainTable;
    private UpdateTilesHandler tilesHandler;
    private RelativeLayout game_layout;
    private boolean startProgress = true;
    private ProgressBar progressBar;
    private Handler progressBarHandler;
    private int progressStatus;
    private TextView tv_time;
    private TextView tv_score;
    private TextView tv_tries;
    private DBHelper myDB;
    private CommonUtils commonUtils;
    String difficulty;
    String category;
    Integer startLevel, maxUnlockLevel;
    User user;
    private SoundPool soundp1, soundp2, sound, soundgameover;
    private  int soundSuccess, soundFail, soundCongo, soundOver;
    private boolean isGameComplete = false;
    Thread progressThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isGameComplete = false;
        setContentView(R.layout.activity_game);
        myDB = new DBHelper(this);
        commonUtils = new CommonUtils(this);
        user = commonUtils.getUserSessionDetails();
        tilesHandler = new UpdateTilesHandler();
        progressBarHandler = new Handler();
        mainTable = (TableLayout) findViewById(R.id.layout_game);
        context  = mainTable.getContext();
        game_layout = (RelativeLayout) findViewById(R.id.activity_game);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        tv_time = (TextView) findViewById(R.id.time);
        TextView tv_level = (TextView) findViewById(R.id.level);
        TextView tv_levelNo = (TextView) findViewById(R.id.level_by_number);
        tv_score = (TextView) findViewById(R.id.score);
        tv_tries = (TextView) findViewById(R.id.tries);
        Intent i = this.getIntent();
        category = i.getExtras().get("category").toString().toLowerCase();

        startLevel = (Integer) i.getExtras().get("startLevel");
        // Log.d("DDDDD"," FFFF  "+startLevel);
        tv_levelNo.setText(startLevel.toString());
        difficulty = i.getExtras().get("difficulty").toString();
        loadImages(category);
        backImage =  getResources().getDrawable(R.drawable.back_image);
        tv_level.setText(difficulty);
        tv_time.setText(getTime(progressStatus));


        if ("Easy".equals(difficulty) || "Fácil".equals(difficulty) || "簡單".equals(difficulty)) {
            difficulty = "Easy";
            COL_COUNT = 2;
            ROW_COUNT = 4;
            hits = 4;
            maxUnlockLevel = user.getEasyUnlockLevel();
            if(startLevel > 7) {
                startLevel = 7;
            }
            progressBar.setMax(progressStatus = 65 - (startLevel-1)*10);
        } else if ("Medium".equals(difficulty) || "Medio".equals(difficulty) || "中".equals(difficulty)) {
            difficulty = "Medium";
            COL_COUNT = 3;
            ROW_COUNT = 4;
            hits = 6;
            maxUnlockLevel = user.getMediumUnlockLevel();
            if(startLevel > 7) {
                startLevel = 7;
            }
            progressBar.setMax(progressStatus = 65 - (startLevel-1)*10);
        } else if ("Hard".equals(difficulty) || "Difícil".equals(difficulty) || "硬".equals(difficulty)) {
            difficulty = "Hard";
            COL_COUNT = 4;
            ROW_COUNT = 5;
            hits = 10;
            maxUnlockLevel = user.getHardUnlockLevel();
            if(startLevel > 10) {
                startLevel = 10;
            }
            progressBar.setMax(progressStatus = 95 - (startLevel-1)*10);
        }
        newGame();
        soundp1= new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundSuccess = soundp1.load(context, R.raw.success, 1);
        soundp2= new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundFail = soundp2.load(context, R.raw.fail, 1);
        sound = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundCongo = sound.load(context, R.raw.congrats, 1);

        soundgameover = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundOver = soundgameover.load(context, R.raw.gameover, 1);
    }

    private void loadImages(String category) {
        images = new ArrayList<Drawable>();
        if ("animals".equals(category)) {
            game_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_animals));
            images.add(getResources().getDrawable(R.drawable.animal_01));
            images.add(getResources().getDrawable(R.drawable.animal_02));
            images.add(getResources().getDrawable(R.drawable.animal_03));
            images.add(getResources().getDrawable(R.drawable.animal_04));
            images.add(getResources().getDrawable(R.drawable.animal_05));
            images.add(getResources().getDrawable(R.drawable.animal_06));
            images.add(getResources().getDrawable(R.drawable.animal_07));
            images.add(getResources().getDrawable(R.drawable.animal_08));
            images.add(getResources().getDrawable(R.drawable.animal_09));
            images.add(getResources().getDrawable(R.drawable.animal_10));
        } else if ("birds".equals(category)) {
            game_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_birds));
            images.add(getResources().getDrawable(R.drawable.bird_01));
            images.add(getResources().getDrawable(R.drawable.bird_02));
            images.add(getResources().getDrawable(R.drawable.bird_03));
            images.add(getResources().getDrawable(R.drawable.bird_04));
            images.add(getResources().getDrawable(R.drawable.bird_05));
            images.add(getResources().getDrawable(R.drawable.bird_06));
            images.add(getResources().getDrawable(R.drawable.bird_07));
            images.add(getResources().getDrawable(R.drawable.bird_08));
            images.add(getResources().getDrawable(R.drawable.bird_09));
            images.add(getResources().getDrawable(R.drawable.bird_10));
        } else if ("food".equals(category)) {
            game_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_food));
            images.add(getResources().getDrawable(R.drawable.food_01));
            images.add(getResources().getDrawable(R.drawable.food_02));
            images.add(getResources().getDrawable(R.drawable.food_03));
            images.add(getResources().getDrawable(R.drawable.food_04));
            images.add(getResources().getDrawable(R.drawable.food_05));
            images.add(getResources().getDrawable(R.drawable.food_06));
            images.add(getResources().getDrawable(R.drawable.food_07));
            images.add(getResources().getDrawable(R.drawable.food_08));
            images.add(getResources().getDrawable(R.drawable.food_09));
            images.add(getResources().getDrawable(R.drawable.food_10));
        } else if ("cartoon".equals(category)) {
            game_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_cartoon));
            images.add(getResources().getDrawable(R.drawable.cartoon_01));
            images.add(getResources().getDrawable(R.drawable.cartoon_02));
            images.add(getResources().getDrawable(R.drawable.cartoon_03));
            images.add(getResources().getDrawable(R.drawable.cartoon_04));
            images.add(getResources().getDrawable(R.drawable.cartoon_05));
            images.add(getResources().getDrawable(R.drawable.cartoon_06));
            images.add(getResources().getDrawable(R.drawable.cartoon_07));
            images.add(getResources().getDrawable(R.drawable.cartoon_08));
            images.add(getResources().getDrawable(R.drawable.cartoon_09));
            images.add(getResources().getDrawable(R.drawable.cartoon_10));
        } else if ("logos".equals(category)) {
            game_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_logos));
            images.add(getResources().getDrawable(R.drawable.logo_01));
            images.add(getResources().getDrawable(R.drawable.logo_02));
            images.add(getResources().getDrawable(R.drawable.logo_03));
            images.add(getResources().getDrawable(R.drawable.logo_04));
            images.add(getResources().getDrawable(R.drawable.logo_05));
            images.add(getResources().getDrawable(R.drawable.logo_06));
            images.add(getResources().getDrawable(R.drawable.logo_07));
            images.add(getResources().getDrawable(R.drawable.logo_08));
            images.add(getResources().getDrawable(R.drawable.logo_09));
            images.add(getResources().getDrawable(R.drawable.logo_10));
        } else if ("christmas".equals(category)) {
            game_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_christmas));
            images.add(getResources().getDrawable(R.drawable.christmas_01));
            images.add(getResources().getDrawable(R.drawable.christmas_02));
            images.add(getResources().getDrawable(R.drawable.christmas_03));
            images.add(getResources().getDrawable(R.drawable.christmas_04));
            images.add(getResources().getDrawable(R.drawable.christmas_05));
            images.add(getResources().getDrawable(R.drawable.christmas_06));
            images.add(getResources().getDrawable(R.drawable.christmas_07));
            images.add(getResources().getDrawable(R.drawable.christmas_08));
            images.add(getResources().getDrawable(R.drawable.christmas_09));
            images.add(getResources().getDrawable(R.drawable.christmas_10));
        }
    }

    private void newGame() {
        tiles = new int[COL_COUNT][ROW_COUNT];
        TableLayout tr = (TableLayout) findViewById(R.id.layout_game);
        for (int r = 0; r < ROW_COUNT; r++) {
            tr.addView(createTableRow(r));
        }
        firstTile = null;
        loadTiles();
    }



    private void loadTiles(){
        // To calculate value behind images
        try {
            int size = ROW_COUNT * COL_COUNT;
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int i = 0; i < size; i++) {
                list.add(new Integer(i));
            }
            Random r = new Random();
            for (int i = size-1; i >= 0; i--) {
                int t = (i > 0) ? r.nextInt(i) : 0;
                t = list.remove(t).intValue();
                tiles[i % COL_COUNT][i / COL_COUNT] = t % (size/2);
            }
        } catch (Exception e) {
//            Log.e("loadTiles()", e.getMessage());
        }
    }

    private TableRow createTableRow(int r){
        TableRow row = new TableRow(context);
        row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
        row.setHorizontalGravity(Gravity.CENTER);
        for (int c = 0; c < COL_COUNT; c++) {
            row.addView(createImageButtonTile(c, r));
        }
        return row;
    }

    private View createImageButtonTile(int x, int y){
        Button button = new Button(context);
        button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
        button.setBackgroundDrawable(backImage);
        button.setId(100*x+y);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                synchronized (lock) {
                    if (startProgress) {
                        startTimer();
                        startProgress = false;
                    }
                    if (firstTile != null && secondTile != null) return;
                    int id = v.getId();
                    int x = id/100;
                    int y = id%100;
                    flipTile((Button) v, x, y);
                }
            }
        });
        return button;
    }

    private String getTime(int seconds) {
        int min = seconds / 60;
        int sec = seconds - (min * 60);
        return String.format("%02d:%02d", min, sec);
    }

    private void startTimer() {
         progressThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus > 0 && hits != 0) {
                    progressStatus--;
                    progressBarHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            tv_time.setText(getTime(progressStatus));
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
//                            Log.e("startTimer()", e.getMessage());
                    }
                }
                if(progressStatus < 1 && !isGameComplete) {
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        //                            Log.e("startTimer()", e.getMessage());
                    }
                    onGameOver();
                }
            }
        });
        progressThread.start();
    }

    private void flipTile(Button button, int x, int y) {
        button.setBackgroundDrawable(images.get(tiles[x][y]));
        if (firstTile == null) {
            firstTile = new Tile(x, y, button);
        } else {
            if (firstTile.x == x && firstTile.y == y) return;
            secondTile = new Tile(x, y, button);

            tv_tries.setText(""+(++tries));

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    try {
                        synchronized (lock) {
                            tilesHandler.sendEmptyMessage(0);
                        }
                    } catch (Exception e) {
//                            Log.e("flipTile()", e.getMessage());
                    }
                }
            };
            Timer timer = new Timer(false);
            timer.schedule(task, 1000);
        }
    }

    class UpdateTilesHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            synchronized (lock) {
                checkTiles();
            }
        }
    }

    public void checkTiles() {
        if (tiles[secondTile.x][secondTile.y] == tiles[firstTile.x][firstTile.y]) {
            soundp1.play(soundSuccess, 1, 1, 1, 0, 1);
            firstTile.button.setVisibility(View.INVISIBLE);
            secondTile.button.setVisibility(View.INVISIBLE);
            hits--;
            score += 10;
            tv_score.setText(""+score);
            if (hits == 0) {
                onGameComplete();
            }
        } else {
            soundp2.play(soundFail, 1, 1, 1, 0, 1);
            secondTile.button.setBackgroundDrawable(backImage);
            firstTile.button.setBackgroundDrawable(backImage);
        }
        firstTile = null;
        secondTile = null;
    }

    public void startNewGame(View view) {
        Intent intent = new Intent(this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        isGameComplete = true;
        finish();
        startActivity(intent);
    }

    public void tryAgain(View view) {
        isGameComplete = true;

        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void onGameComplete() {
        score += progressStatus;
        score = score + (startLevel * 20);
        score = score - tries;
        isGameComplete = true;

        if (score < 0) score = 0;

        tv_score.setText(""+score);

        myDB.insertUserScore(user.getId(), startLevel, user.getName(), score, difficulty.toLowerCase());
        if(maxUnlockLevel == startLevel) {
            myDB.updateUserUnlockLevel(user.getId(), difficulty, startLevel + 1);
            commonUtils.updateSessionDetails(difficulty, startLevel + 1);
        }
        Intent intent = new Intent(this, Congrats.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("score", ""+score);
        intent.putExtra("category", category);
        intent.putExtra("difficulty", difficulty);
        intent.putExtra("startLevel", startLevel + 1);
        startActivity(intent);
        sound.play(soundCongo, 1, 1, 1, 0, 1);
    }

    private void onGameOver() {

        Intent intent = new Intent(this, GameOver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        intent.putExtra("category", category);
        intent.putExtra("difficulty", difficulty);
        intent.putExtra("startLevel", startLevel);
        startActivity(intent);
        soundgameover.play(soundOver, 1, 1, 1, 0, 1);
    }




    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}