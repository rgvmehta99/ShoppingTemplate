package com.ecommapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.demoecommereceapp.R;

public class ActSplashScreen extends BaseActivity {

    String TAG = "==ActSplashScreen==";

    ImageView ivLogo;
    TextView tvCompTitle;
    View dummyView;

    //
    private SpringAnimation springAnim, springAnimText;
    private static final int FINAL_Y_POSITION = 550;
    private static final int FINAL_Y_POSITION_TEXT = 450;
    private float originalButtonTranslation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            super.onCreate(savedInstanceState);

            //
            ViewGroup.inflate(this, R.layout.act_splash_screen, llContainerSub);
            rlBaseMainHeader.setVisibility(View.GONE);
            setEnableDrawer(false);

            App.showLogTAG(TAG);

            initialisation();
            loadAnimation();
        } catch (Exception e) {e.printStackTrace();}
    }


    private void initialisation() {
        try
        {
            ivLogo = (ImageView) findViewById(R.id.ivLogo);

            tvCompTitle = (TextView) findViewById(R.id.tvCompTitle);
            tvCompTitle.setTypeface(App.getTribuchet_MS());

            dummyView = (View) findViewById(R.id.dummyView);

            originalButtonTranslation = dummyView.getTranslationY();
            springAnim = new SpringAnimation(ivLogo, DynamicAnimation.TRANSLATION_Y, FINAL_Y_POSITION);
            springAnim.getSpring().setDampingRatio(0.5f);
            springAnim.getSpring().setStiffness(5);

            springAnimText = new SpringAnimation(tvCompTitle, DynamicAnimation.TRANSLATION_Y, FINAL_Y_POSITION_TEXT);
            springAnimText.getSpring().setDampingRatio(0.5f);
            springAnimText.getSpring().setStiffness(5);

        } catch (Exception e) {e.printStackTrace();}
    }


    private void loadAnimation() {
        try
        {
            //
            springAnim.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                @Override
                public void onAnimationEnd(DynamicAnimation animation, boolean canceled,
                                           float value, float velocity) {
                    ivLogo.setTranslationX(originalButtonTranslation);
                }
            });

            //
            springAnimText.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                @Override
                public void onAnimationEnd(DynamicAnimation animation, boolean canceled,
                                           float value, float velocity) {
                    tvCompTitle.setTranslationX(originalButtonTranslation);
                }
            });


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    springAnim.start();
                }
            }, 800);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvCompTitle.setVisibility(View.VISIBLE);
                    springAnimText.start();
                }
            }, 1000);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent iv = new Intent(ActSplashScreen.this, ActLoginSecond.class);
                    App.myStartActivity(ActSplashScreen.this, iv);
                }
            }, 4000);
        } catch (Exception e) {e.printStackTrace();}
    }
}
