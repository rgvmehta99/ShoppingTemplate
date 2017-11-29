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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demoecommereceapp.R;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.reveal_layout_package.RevealLayout;

import java.util.Random;

/**
 * Created by dhaval.mehta on 05-Oct-2017.
 */

public class ActForgotPassword extends BaseActivity {

    String TAG = "==ActForgotPassword==";

    //
    TextView tvForgotPassword;
    MaterialEditText metEmail;
    ImageView ivBanner, ivBackSub;
    RelativeLayout rlBanner;
    NestedScrollView nsView;

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
            ViewGroup.inflate(this, R.layout.act_forgot_password, llContainerSub);
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

            //
            rlBanner = (RelativeLayout) findViewById(R.id.rlBanner);
            ivBanner = (ImageView) findViewById(R.id.ivBanner);
            ivBackSub = (ImageView) findViewById(R.id.ivBackSub);

            tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
            metEmail = (MaterialEditText) findViewById(R.id.metEmail);

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


    private void setFonts() {
        try
        {
            tvForgotPassword.setTypeface(App.getTribuchet_MS());
            metEmail.setTypeface(App.getTribuchet_MS());

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setClickEvents() {
        try
        {
            //
            tvForgotPassword.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {

                    App.showToastShort(ActForgotPassword.this, "Your Password sent to your email id.");

                    Intent iv = new Intent(ActForgotPassword.this, ActLogin.class);
                    startActivity(iv);
                    App.myStartActivity(ActForgotPassword.this, iv);
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
        App.myFinishActivity(ActForgotPassword.this);
    }

}
