package com.ecommapp;

import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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

public class ActLoginSecond extends BaseActivity {

    String TAG = "==ActLoginSecondSecond==";

    //
    TextView tvLogin, tvTagRegisterNow, tvForgotPasswordTag, tvTagOR;
    MaterialEditText metEmail, metPassword;
    ImageView ivBanner, ivBackSub;
    View viewMainBg;
    LinearLayout llEmail, llPassword, llRegisterNow, llForgotPassword, llLogin;
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
        try {
            //
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            super.onCreate(savedInstanceState);
            //
            ViewGroup.inflate(this, R.layout.act_login_second, llContainerSub);
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
            App.setTaskBarColored(ActLoginSecond.this, randomAndroidColor);

            //
            revealLayout = (RevealLayout) findViewById(R.id.revealLayout);
            rlMain = (RelativeLayout) findViewById(R.id.rlMain);
            rlBanner = (RelativeLayout) findViewById(R.id.rlBanner);
            ivBanner = (ImageView) findViewById(R.id.ivBanner);
            ivBackSub = (ImageView) findViewById(R.id.ivBackSub);

            tvLogin = (TextView) findViewById(R.id.tvLogin);
            tvTagRegisterNow = (TextView) findViewById(R.id.tvTagRegisterNow);
            tvForgotPasswordTag = (TextView) findViewById(R.id.tvForgotPasswordTag);
            tvTagOR = (TextView) findViewById(R.id.tvTagOR);
            metEmail = (MaterialEditText) findViewById(R.id.metEmail);
            metPassword = (MaterialEditText) findViewById(R.id.metPassword);
            viewMainBg = findViewById(R.id.viewMainBg);
            // blurTransformation = new BlurTransformation(ActLoginSecond.this, 15);

            llEmail = (LinearLayout) findViewById(R.id.llEmail);
            llPassword = (LinearLayout) findViewById(R.id.llPassword);
            llRegisterNow = (LinearLayout) findViewById(R.id.llRegisterNow);
            llForgotPassword = (LinearLayout) findViewById(R.id.llForgotPassword);
            llLogin = (LinearLayout) findViewById(R.id.llLogin);
            rlOrTag = (RelativeLayout) findViewById(R.id.rlOrTag);
            rlSocialImage = (RelativeLayout) findViewById(R.id.rlSocialImage);
            //
            // screenControlsAnimaiton();


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


    private void setClickEvents() {
        try
        {
            //
            tvLogin.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {

                    Intent iv = new Intent(ActLoginSecond.this, ActDashboard.class);
                    startActivity(iv);
                    App.myStartActivity(ActLoginSecond.this, iv);
                    // overridePendingTransition(0,0);
                }
            });

            //
            tvTagRegisterNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iv = new Intent(ActLoginSecond.this, ActSignUpSecond.class);
                    App.myStartActivity(ActLoginSecond.this, iv);
                }
            });

            //
            tvForgotPasswordTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iv = new Intent(ActLoginSecond.this, ActForgotPassword.class);
                    App.myStartActivity(ActLoginSecond.this, iv);
                }
            });

            //
            ivBackSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

        } catch (Exception e) {e.printStackTrace();}
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        showExitDialog();
    }

}
