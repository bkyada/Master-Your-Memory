package com.cs442.bkyada.memorygame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SplashScreen extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        loadMainScreen();
    }

    private void loadMainScreen() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        animation.reset();
        LinearLayout splashLayout = (LinearLayout) findViewById(R.id.layout_splash);
        splashLayout.clearAnimation();
        splashLayout.startAnimation(animation);
        animation = AnimationUtils.loadAnimation(this, R.anim.translate);
        animation.reset();
        ImageView iv = (ImageView) findViewById(R.id.img_splash);
        iv.clearAnimation();
        iv.startAnimation(animation);
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent intent = new Intent(SplashScreen.this, Login.class);
                    startActivity(intent);
                    SplashScreen.this.finish();
                } catch (Exception e) {}
            }
        }.start();
    }
}
