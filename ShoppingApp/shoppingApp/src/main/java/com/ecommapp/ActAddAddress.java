package com.ecommapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demoecommereceapp.R;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by dhaval.mehta on 04-Nov-2017.
 */

public class ActAddAddress extends BaseActivity {

    String TAG = "==ActAddAddress==";
    //
//    RelativeLayout rlNoData;
//    TextView tvNoData;
    //
    TextView tvAddAddress;
    MaterialEditText metFullName, metAddressOne, metAddressTwo, metPincode, metCity, metState, metCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            //
            ViewGroup.inflate(this, R.layout.act_add_address, llContainerSub);
            App.showLogTAG(TAG);

            // getIntentData();
            initialisation();
            setFonts();
            setClickEvents();
            // setStaticAddressList();


//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    setAdapter();
//                }
//            }, 3000);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialisation() {
        try
        {
            //
            baseViewHeaderShadow.setVisibility(View.GONE);
            rlBaseMainHeader.setVisibility(View.VISIBLE);
            setEnableDrawer(true);
            rlBack.setVisibility(View.VISIBLE);
            tvTitle.setText("Add Address");
            //
            tvAddAddress = (TextView) findViewById(R.id.tvAddAddress);
            metFullName =(MaterialEditText) findViewById(R.id.metFullName);
            metAddressOne =(MaterialEditText) findViewById(R.id.metAddressOne);
            metAddressTwo =(MaterialEditText) findViewById(R.id.metAddressTwo);
            metPincode =(MaterialEditText) findViewById(R.id.metPincode);
            metCity =(MaterialEditText) findViewById(R.id.metCity);
            metState =(MaterialEditText) findViewById(R.id.metState);
            metCountry =(MaterialEditText) findViewById(R.id.metCountry);

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setFonts() {
        try
        {
            tvAddAddress.setTypeface(App.getTribuchet_MS());
            metFullName.setTypeface(App.getTribuchet_MS());
            metAddressOne.setTypeface(App.getTribuchet_MS());
            metAddressTwo.setTypeface(App.getTribuchet_MS());
            metPincode.setTypeface(App.getTribuchet_MS());
            metCity.setTypeface(App.getTribuchet_MS());
            metState.setTypeface(App.getTribuchet_MS());
            metCountry.setTypeface(App.getTribuchet_MS());

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setClickEvents() {
        try
        {
            //
            rlBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            //
            tvAddAddress.setOnClickListener(new View.OnClickListener() {
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
        App.myFinishActivity(ActAddAddress.this);
    }
}
