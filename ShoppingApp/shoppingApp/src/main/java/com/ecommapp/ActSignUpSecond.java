package com.ecommapp;

import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demoecommereceapp.R;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.reveal_layout_package.RevealLayout;
import com.util.BlurTransformation;

import java.util.Random;

/**
 * Created by dhaval.mehta on 11-Sep-2017.
 */

public class ActSignUpSecond extends BaseActivity {


    String TAG = "==ActSignUpSecond==";

    //
    TextView tvSignup, tvTagLoginNow, tvTagOR;
    MaterialEditText metFullName, metContactNo, metEmail, metPassword, metConfirmPassword;
    ImageView ivBanner, ivBack;
    View viewMainBg;
    LinearLayout llFullName, llContactNo, llEmail, llPassword, llConfirmPassword, llLoginNow, llSignup;
    RelativeLayout rlOrTag, rlSocialImage;
    RevealLayout revealLayout;
    RelativeLayout rlBanner, rlMain;
    NestedScrollView nsView;
    //
    BlurTransformation blurTransformation;

    //
    int animationDuration = 800;
    int SCREEN_HEIGHT = 0, SCREEN_WIDTH = 0;
    float BANNER_SHOW_PERCENTAGE = 0.40f; // 40%


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            //
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            super.onCreate(savedInstanceState);
            //
            ViewGroup.inflate(this, R.layout.act_signup_second, llContainerSub);
            App.showLogTAG(TAG);

            initialisation();
            dynamicSetSize();
            setFonts();
            setClickEvents();

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
            int randomAndroidColor = App.arrStatusBarColor[new Random().nextInt(App.arrStatusBarColor.length)];
            App.setTaskBarColored(ActSignUpSecond.this, randomAndroidColor);

            //
            revealLayout = (RevealLayout) findViewById(R.id.revealLayout);
            rlMain = (RelativeLayout) findViewById(R.id.rlMain);
            rlBanner = (RelativeLayout) findViewById(R.id.rlBanner);
            ivBanner = (ImageView) findViewById(R.id.ivBanner);
            ivBack = (ImageView) findViewById(R.id.ivBack);

            tvSignup = (TextView) findViewById(R.id.tvSignup);
            tvTagLoginNow = (TextView) findViewById(R.id.tvTagLoginNow);
            tvTagOR = (TextView) findViewById(R.id.tvTagOR);

            metFullName = (MaterialEditText) findViewById(R.id.metFullName);
            metContactNo = (MaterialEditText) findViewById(R.id.metContactNo);
            metEmail = (MaterialEditText) findViewById(R.id.metEmail);
            metPassword = (MaterialEditText) findViewById(R.id.metPassword);
            metConfirmPassword = (MaterialEditText) findViewById(R.id.metConfirmPassword);

            viewMainBg = findViewById(R.id.viewMainBg);
            // blurTransformation = new BlurTransformation(ActLoginSecond.this, 15);

            llFullName = (LinearLayout) findViewById(R.id.llFullName);
            llContactNo = (LinearLayout) findViewById(R.id.llContactNo);
            llEmail = (LinearLayout) findViewById(R.id.llEmail);
            llPassword = (LinearLayout) findViewById(R.id.llPassword);
            llConfirmPassword = (LinearLayout) findViewById(R.id.llConfirmPassword);
            llLoginNow = (LinearLayout) findViewById(R.id.llLoginNow);
            llSignup = (LinearLayout) findViewById(R.id.llSignup);
            rlOrTag = (RelativeLayout) findViewById(R.id.rlOrTag);
            rlSocialImage = (RelativeLayout) findViewById(R.id.rlSocialImage);
            //
            // screenControlsAnimaiton();


        } catch (Exception e) {e.printStackTrace();}
    }


    private void setFonts() {
        try
        {
            tvSignup.setTypeface(App.getTribuchet_MS());
            tvTagLoginNow.setTypeface(App.getTribuchet_MS());
            tvTagOR.setTypeface(App.getTribuchet_MS());

            metFullName.setTypeface(App.getTribuchet_MS());
            metContactNo.setTypeface(App.getTribuchet_MS());
            metEmail.setTypeface(App.getTribuchet_MS());
            metPassword.setTypeface(App.getTribuchet_MS());
            metConfirmPassword.setTypeface(App.getTribuchet_MS());
        } catch (Exception e) {e.printStackTrace();}
    }


    private void dynamicSetSize() {
        try
        {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            SCREEN_HEIGHT = size.y;
            SCREEN_WIDTH = size.x;

            Log.e("*********************", "************************");
            Log.e("==SCR Height==", SCREEN_HEIGHT + " ****");
            Log.e("==SCR Width==", SCREEN_WIDTH + " ****");
            Log.e("*********************", "************************");

            LinearLayout.LayoutParams paramsRLBanner = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            // paramsRLBanner.height = (SCREEN_HEIGHT/2) - 100;
            paramsRLBanner.height = (int) (SCREEN_HEIGHT * BANNER_SHOW_PERCENTAGE);
            rlBanner.setLayoutParams(paramsRLBanner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void screenControlsAnimaiton() {
        try
        {
            llFullName.setVisibility(View.GONE);
            llContactNo.setVisibility(View.GONE);
            llEmail.setVisibility(View.GONE);
            llPassword.setVisibility(View.GONE);
            llConfirmPassword.setVisibility(View.GONE);
            llLoginNow.setVisibility(View.GONE);
            llSignup.setVisibility(View.GONE);
            rlOrTag.setVisibility(View.GONE);
            rlSocialImage.setVisibility(View.GONE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // springAnimEmail.start();
                    llFullName.setVisibility(View.VISIBLE);
                    Animation RightSwipe = AnimationUtils.loadAnimation(ActSignUpSecond.this, R.anim.slide_in_left);
                    RightSwipe.setDuration(animationDuration);
                    llFullName.startAnimation(RightSwipe);
                }
            }, 100);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // springAnimEmail.start();
                    llContactNo.setVisibility(View.VISIBLE);
                    Animation RightSwipe = AnimationUtils.loadAnimation(ActSignUpSecond.this, R.anim.slide_in_left);
                    RightSwipe.setDuration(animationDuration);
                    llContactNo.startAnimation(RightSwipe);
                }
            }, 300);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // springAnimEmail.start();
                    llEmail.setVisibility(View.VISIBLE);
                    Animation RightSwipe = AnimationUtils.loadAnimation(ActSignUpSecond.this, R.anim.slide_in_left);
                    RightSwipe.setDuration(animationDuration);
                    llEmail.startAnimation(RightSwipe);
                }
            }, 500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // springAnimPassword.start();
                    llPassword.setVisibility(View.VISIBLE);
                    Animation RightSwipe = AnimationUtils.loadAnimation(ActSignUpSecond.this, R.anim.slide_in_left);
                    RightSwipe.setDuration(animationDuration);
                    llPassword.startAnimation(RightSwipe);
                }
            }, 700);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // springAnimPassword.start();
                    llConfirmPassword.setVisibility(View.VISIBLE);
                    Animation RightSwipe = AnimationUtils.loadAnimation(ActSignUpSecond.this, R.anim.slide_in_left);
                    RightSwipe.setDuration(animationDuration);
                    llConfirmPassword.startAnimation(RightSwipe);
                }
            }, 900);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // springAnimPassword.start();
                    llLoginNow.setVisibility(View.VISIBLE);
                    Animation RightSwipe = AnimationUtils.loadAnimation(ActSignUpSecond.this, R.anim.slide_in_left);
                    RightSwipe.setDuration(animationDuration);
                    llLoginNow.startAnimation(RightSwipe);
                }
            }, 1000);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // springAnimPassword.start();
                    llSignup.setVisibility(View.VISIBLE);
                    Animation RightSwipe = AnimationUtils.loadAnimation(ActSignUpSecond.this, R.anim.slide_in_left);
                    RightSwipe.setDuration(animationDuration);
                    llSignup.startAnimation(RightSwipe);
                }
            }, 1100);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // springAnimPassword.start();
                    rlOrTag.setVisibility(View.VISIBLE);
                    Animation RightSwipe = AnimationUtils.loadAnimation(ActSignUpSecond.this, R.anim.slide_in_left);
                    RightSwipe.setDuration(animationDuration);
                    rlOrTag.startAnimation(RightSwipe);
                }
            }, 1250);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // springAnimPassword.start();
                    rlSocialImage.setVisibility(View.VISIBLE);
                    Animation RightSwipe = AnimationUtils.loadAnimation(ActSignUpSecond.this, R.anim.slide_in_left);
                    RightSwipe.setDuration(animationDuration);
                    rlSocialImage.startAnimation(RightSwipe);
                }
            }, 1400);

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setClickEvents() {
        try
        {
            //
            tvSignup.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {

                    Intent iv = new Intent(ActSignUpSecond.this, ActSignUpSecond.class);
                    startActivity(iv);
                    overridePendingTransition(0,0);
                }
            });

            //
            tvTagLoginNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iv = new Intent(ActSignUpSecond.this, ActLoginSecond.class);
                    App.myStartActivity(ActSignUpSecond.this, iv);
                }
            });

        } catch (Exception e) {e.printStackTrace();}
    }
}
