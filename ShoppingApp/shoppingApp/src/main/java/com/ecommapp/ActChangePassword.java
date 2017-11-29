package com.ecommapp;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demoecommereceapp.R;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by dhaval.mehta on 09-Oct-2017.
 */

public class ActChangePassword extends BaseActivity {

    String TAG = "==ActChangePassword==";
    //
    TextView tvResetPassword;
    MaterialEditText metNewPassword, metConfirmNewPassword;
    ImageView ivBanner, ivBackSub;
    RelativeLayout rlBanner, rlMain;

    //
    int SCREEN_HEIGHT = 0, SCREEN_WIDTH = 0;
    float BANNER_SHOW_PERCENTAGE = 0.40f; // 40%

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            //
            ViewGroup.inflate(this, R.layout.act_change_password, llContainerSub);
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
            rlMain = (RelativeLayout) findViewById(R.id.rlMain);
            rlBanner = (RelativeLayout) findViewById(R.id.rlBanner);
            ivBanner = (ImageView) findViewById(R.id.ivBanner);
            ivBackSub = (ImageView) findViewById(R.id.ivBackSub);

            tvResetPassword = (TextView) findViewById(R.id.tvResetPassword);
            metNewPassword = (MaterialEditText) findViewById(R.id.metNewPassword);
            metConfirmNewPassword = (MaterialEditText) findViewById(R.id.metConfirmNewPassword);


        } catch (Exception e) {e.printStackTrace();}
    }


    private void setFonts() {
        try
        {
            tvResetPassword.setTypeface(App.getTribuchet_MS());
            metNewPassword.setTypeface(App.getTribuchet_MS());
            metConfirmNewPassword.setTypeface(App.getTribuchet_MS());
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
            tvResetPassword.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {

                    App.showToastShort(ActChangePassword.this, "Your password is reset now.");
                    App.myFinishActivity(ActChangePassword.this);
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
         App.myFinishActivity(ActChangePassword.this);
    }

}
