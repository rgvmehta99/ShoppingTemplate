package com.ecommapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.annotation.ColorRes;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demoecommereceapp.R;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.reveal_layout_package.RevealLayout;
import com.squareup.picasso.Picasso;
import com.util.BlurTransformation;

import java.util.Random;

/**
 * Created by dhaval.mehta on 31-Aug-2017.
 */

public class ActLogin extends BaseActivity {

    String TAG = "==ActLogin==";

    //
    TextView tvLogin, tvTagRegisterNow, tvForgotPasswordTag, tvTagOR;
    MaterialEditText metEmail, metPassword;
    ImageView ivBanner;
    View viewMainBg;
    LinearLayout llEmail, llPassword, llRegisterNow, llForgotPassword, llLogin;
    RelativeLayout rlOrTag, rlSocialImage;
    RevealLayout revealLayout;
    RelativeLayout rlMain;
    int animationDuration = 800;

    BlurTransformation blurTransformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            //
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            super.onCreate(savedInstanceState);
            //
            ViewGroup.inflate(this, R.layout.act_login, llContainerSub);
            App.showLogTAG(TAG);

            initialisation();
            setFonts();
            setClickEvents();

//            Picasso.with(this)
//                    .load(R.drawable.one)
//                    //.transform(blurTransformation)
//                    .into(ivBanner);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initialisation() {
        try
        {
            //
            rlBaseMainHeader.setVisibility(View.GONE);
            setEnableDrawer(false);
//            int randomAndroidColor = App.arrStatusBarColor[new Random().nextInt(App.arrStatusBarColor.length)];
//            App.setTaskBarColored(ActLogin.this, randomAndroidColor);

            //
            revealLayout = (RevealLayout) findViewById(R.id.revealLayout);
            rlMain = (RelativeLayout) findViewById(R.id.rlMain);
            ivBanner = (ImageView) findViewById(R.id.ivBanner);

            tvLogin = (TextView) findViewById(R.id.tvLogin);
            tvTagRegisterNow = (TextView) findViewById(R.id.tvTagRegisterNow);
            tvForgotPasswordTag = (TextView) findViewById(R.id.tvForgotPasswordTag);
            tvTagOR = (TextView) findViewById(R.id.tvTagOR);
            metEmail = (MaterialEditText) findViewById(R.id.metEmail);
            metPassword = (MaterialEditText) findViewById(R.id.metPassword);
            viewMainBg = findViewById(R.id.viewMainBg);
            // blurTransformation = new BlurTransformation(ActLogin.this, 15);

            llEmail = (LinearLayout) findViewById(R.id.llEmail);
            llPassword = (LinearLayout) findViewById(R.id.llPassword);
            llRegisterNow = (LinearLayout) findViewById(R.id.llRegisterNow);
            llForgotPassword = (LinearLayout) findViewById(R.id.llForgotPassword);
            llLogin = (LinearLayout) findViewById(R.id.llLogin);
            rlOrTag = (RelativeLayout) findViewById(R.id.rlOrTag);
            rlSocialImage = (RelativeLayout) findViewById(R.id.rlSocialImage);
            //
            screenControlsAnimaiton();

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setFonts() {
        try
        {
            tvLogin.setTypeface(App.getTribuchet_MS());
            tvTagRegisterNow.setTypeface(App.getTribuchet_MS());
            tvForgotPasswordTag.setTypeface(App.getTribuchet_MS());
            tvTagOR.setTypeface(App.getTribuchet_MS());
            metEmail.setTypeface(App.getTribuchet_MS());
            metPassword.setTypeface(App.getTribuchet_MS());
        } catch (Exception e) {e.printStackTrace();}
    }


    private void screenControlsAnimaiton() {
        try
        {
            llEmail.setVisibility(View.GONE);
            llPassword.setVisibility(View.GONE);
            llRegisterNow.setVisibility(View.GONE);
            llForgotPassword.setVisibility(View.GONE);
            llLogin.setVisibility(View.GONE);
            rlOrTag.setVisibility(View.GONE);
            rlSocialImage.setVisibility(View.GONE);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // springAnimEmail.start();
                    llEmail.setVisibility(View.VISIBLE);
                    Animation RightSwipe = AnimationUtils.loadAnimation(ActLogin.this, R.anim.slide_in_left);
                    RightSwipe.setDuration(animationDuration);
                    llEmail.startAnimation(RightSwipe);
                }
            }, 100);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // springAnimPassword.start();
                    llPassword.setVisibility(View.VISIBLE);
                    Animation RightSwipe = AnimationUtils.loadAnimation(ActLogin.this, R.anim.slide_in_left);
                    RightSwipe.setDuration(animationDuration);
                    llPassword.startAnimation(RightSwipe);
                }
            }, 300);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // springAnimPassword.start();
                    llRegisterNow.setVisibility(View.VISIBLE);
                    Animation RightSwipe = AnimationUtils.loadAnimation(ActLogin.this, R.anim.slide_in_left);
                    RightSwipe.setDuration(animationDuration);
                    llRegisterNow.startAnimation(RightSwipe);
                }
            }, 500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // springAnimPassword.start();
                    llForgotPassword.setVisibility(View.VISIBLE);
                    Animation RightSwipe = AnimationUtils.loadAnimation(ActLogin.this, R.anim.slide_in_left);
                    RightSwipe.setDuration(animationDuration);
                    llForgotPassword.startAnimation(RightSwipe);
                }
            }, 800);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // springAnimPassword.start();
                    llLogin.setVisibility(View.VISIBLE);
                    Animation RightSwipe = AnimationUtils.loadAnimation(ActLogin.this, R.anim.slide_in_left);
                    RightSwipe.setDuration(animationDuration);
                    llLogin.startAnimation(RightSwipe);
                }
            }, 900);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // springAnimPassword.start();
                    rlOrTag.setVisibility(View.VISIBLE);
                    Animation RightSwipe = AnimationUtils.loadAnimation(ActLogin.this, R.anim.slide_in_left);
                    RightSwipe.setDuration(animationDuration);
                    rlOrTag.startAnimation(RightSwipe);
                }
            }, 1100);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // springAnimPassword.start();
                    rlSocialImage.setVisibility(View.VISIBLE);
                    Animation RightSwipe = AnimationUtils.loadAnimation(ActLogin.this, R.anim.slide_in_left);
                    RightSwipe.setDuration(animationDuration);
                    rlSocialImage.startAnimation(RightSwipe);
                }
            }, 1300);
        } catch (Exception e) {e.printStackTrace();}
    }


    private void setClickEvents() {
        try
        {
            //
            tvLogin.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {

//                    int cx = rlMain.getWidth() / 2;
//                    int cy = rlMain.getHeight();
//                    float finalRadius = Math.max(rlMain.getWidth(), rlMain.getHeight());
//
//                    Animator circularReveal = ViewAnimationUtils.createCircularReveal(rlMain, cx, cy, 100,
//                            finalRadius);
//                    circularReveal.setDuration(1000);
//
//                    final Animation animation1 = AnimationUtils.loadAnimation(ActLogin.this, R.anim.alpha_anim);
//                    animation1.setFillAfter(true);
//                    rlMain.startAnimation(animation1);
//                    viewMainBg.startAnimation(animation1);
//                    viewMainBg.setVisibility(View.VISIBLE);
//                    circularReveal.start();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent iv = new Intent(ActLogin.this, ActSplashScreen.class);
                            startActivity(iv);
                            overridePendingTransition(0,0);
                        }
                    }, 1000);
                }
            });

        } catch (Exception e) {e.printStackTrace();}
    }
}
