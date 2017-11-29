package com.ecommapp;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demoecommereceapp.R;
import com.util.AppFlags;
import com.util.CustomProgressDialog;
import com.util.MaterialProgressBar;

/**
 * Created by dhaval.mehta on 03-Nov-2017.
 */

public class ActMyOrderDetail extends BaseActivity {

    String TAG = "==ActMyOrderDetail==";
    TextView tvOrderNo;
    //
    NestedScrollView nsView;
    RelativeLayout rlMain;
    MaterialProgressBar mterialProgress;
    // Cart List
    TextView tvTagCartItems, tvCartItems;
    RecyclerView recyclerView;
    // Pricing and Amount
    TextView tvTagPricingAmount;
    TextView tvTagTotal, tvTagGST, tvTagShippingCharge, tvTagDiscount,
            tvTagTotalPayableAmount;
    public static TextView tvTotal, tvGST, tvShippingCharge, tvDiscount, tvTotalPayableAmount;
    // Billing Address
    TextView tvTagBillingAddress, tvBAUserName, tvBAAddressOne, tvBAAddressTwo,
            tvBAPincodeCity, tvBAStateCountry;
    // Delivery Address
    TextView tvTagDeleveryAddress, tvDAUserName, tvDAAddressOne, tvDAAddressTwo,
            tvDAPincodeCity, tvDAStateCountry;
    // Payment Option
    TextView tvTagPaymentOption, tvTagPaymentType, tvPaymentType, tvTagPaymentStatus, tvPaymentStatus,
            tvTagShippedTo, tvShippedTo, tvTagOrderStats, tvOrderStats;
    ImageView ivOrderStats;

    //
    String strFrom = "", strOrderNo = "", strOrderStatus = "", strOrderShippedTo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            //
            ViewGroup.inflate(this, R.layout.act_my_order_detail, llContainerSub);
            App.showLogTAG(TAG);

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            getIntentData();
            initialisation();
            setFonts();
            setClickEvents();
            //
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setAdapter();
                }
            }, 3000);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getIntentData() {
        try
        {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                //
                if (bundle.getString(App.ITAG_FROM) != null &&
                        bundle.getString(App.ITAG_FROM).length() > 0) {
                    strFrom = bundle.getString(App.ITAG_FROM);
                    App.showLog(TAG + "==strFrom==" + strFrom);
                }
                //
                if (bundle.getString(AppFlags.tagOrderNo) != null &&
                        bundle.getString(AppFlags.tagOrderNo).length() > 0) {
                    strOrderNo = bundle.getString(AppFlags.tagOrderNo);
                    App.showLog(TAG + "==strOrderNo==" + strOrderNo);
                }
                //
                if (bundle.getString(AppFlags.tagOrderShippedTo) != null &&
                        bundle.getString(AppFlags.tagOrderShippedTo).length() > 0) {
                    strOrderShippedTo = bundle.getString(AppFlags.tagOrderShippedTo);
                    App.showLog(TAG + "==strOrderShippedTo==" + strOrderShippedTo);
                }
                //
                if (bundle.getString(AppFlags.tagOrderStatus) != null &&
                        bundle.getString(AppFlags.tagOrderStatus).length() > 0) {
                    strOrderStatus = bundle.getString(AppFlags.tagOrderStatus);
                    App.showLog(TAG + "==strOrderStatus==" + strOrderStatus);
                }

            }

        } catch (Exception e) {e.printStackTrace();}
    }


    private void initialisation() {
        try
        {
            //
            baseViewHeaderShadow.setVisibility(View.GONE);
            rlBaseMainHeader.setVisibility(View.VISIBLE);
            setEnableDrawer(true);
            rlBack.setVisibility(View.VISIBLE);
            tvTitle.setText("Order Detail");

            //
            mterialProgress = (MaterialProgressBar) findViewById(R.id.mterialProgress);
            mterialProgress.setVisibility(View.VISIBLE);

            rlMain = (RelativeLayout) findViewById(R.id.rlMain);
            rlMain.setVisibility(View.GONE);
            nsView = (NestedScrollView) findViewById(R.id.nsView);
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,  LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            tvTagCartItems = (TextView) findViewById(R.id.tvTagCartItems);
            tvCartItems = (TextView) findViewById(R.id.tvCartItems);

            //
            tvOrderNo = (TextView) findViewById(R.id.tvOrderNo);
            //
            tvTagPricingAmount = (TextView) findViewById(R.id.tvTagPricingAmount);
            tvTagTotal = (TextView) findViewById(R.id.tvTagTotal);
            tvTotal = (TextView) findViewById(R.id.tvTotal);
            tvTagGST = (TextView) findViewById(R.id.tvTagGST);
            tvGST = (TextView) findViewById(R.id.tvGST);
            tvTagShippingCharge = (TextView) findViewById(R.id.tvTagShippingCharge);
            tvShippingCharge = (TextView) findViewById(R.id.tvShippingCharge);
            tvTagDiscount = (TextView) findViewById(R.id.tvTagDiscount);
            tvDiscount = (TextView) findViewById(R.id.tvDiscount);
            tvTagTotalPayableAmount = (TextView) findViewById(R.id.tvTagTotalPayableAmount);
            tvTotalPayableAmount = (TextView) findViewById(R.id.tvTotalPayableAmount);

            //
            tvTagBillingAddress = (TextView) findViewById(R.id.tvTagBillingAddress);
            tvBAUserName = (TextView) findViewById(R.id.tvBAUserName);
            tvBAAddressOne = (TextView) findViewById(R.id.tvBAAddressOne);
            tvBAAddressTwo = (TextView) findViewById(R.id.tvBAAddressTwo);
            tvBAPincodeCity = (TextView) findViewById(R.id.tvBAPincodeCity);
            tvBAStateCountry = (TextView) findViewById(R.id.tvBAStateCountry);

            tvTagDeleveryAddress = (TextView) findViewById(R.id.tvTagDeleveryAddress);
            tvDAUserName = (TextView) findViewById(R.id.tvDAUserName);
            tvDAAddressOne = (TextView) findViewById(R.id.tvDAAddressOne);
            tvDAAddressTwo = (TextView) findViewById(R.id.tvDAAddressTwo);
            tvDAPincodeCity = (TextView) findViewById(R.id.tvDAPincodeCity);
            tvDAStateCountry = (TextView) findViewById(R.id.tvDAStateCountry);

            //
            tvTagPaymentOption = (TextView) findViewById(R.id.tvTagPaymentOption);
            tvTagPaymentType = (TextView) findViewById(R.id.tvTagPaymentType);
            tvPaymentType = (TextView) findViewById(R.id.tvPaymentType);
            tvTagPaymentStatus = (TextView) findViewById(R.id.tvTagPaymentStatus);
            tvPaymentStatus = (TextView) findViewById(R.id.tvPaymentStatus);
            tvTagShippedTo = (TextView) findViewById(R.id.tvTagShippedTo);
            tvShippedTo = (TextView) findViewById(R.id.tvShippedTo);
            tvTagOrderStats = (TextView) findViewById(R.id.tvTagOrderStats);
            tvOrderStats = (TextView) findViewById(R.id.tvOrderStats);
            ivOrderStats = (ImageView) findViewById(R.id.ivOrderStats);


        } catch (Exception e) {e.printStackTrace();}
    }


    private void setFonts() {
        try
        {
            tvOrderNo.setTypeface(App.getTribuchet_MS());
            //
            tvTagCartItems.setTypeface(App.getTribuchet_MS());
            tvCartItems.setTypeface(App.getTribuchet_MS());
            //
            tvTagPricingAmount.setTypeface(App.getTribuchet_MS());
            tvTagTotal.setTypeface(App.getTribuchet_MS());
            tvTotal.setTypeface(App.getTribuchet_MS());
            tvTagGST.setTypeface(App.getTribuchet_MS());
            tvGST.setTypeface(App.getTribuchet_MS());
            tvTagShippingCharge.setTypeface(App.getTribuchet_MS());
            tvShippingCharge.setTypeface(App.getTribuchet_MS());
            tvTagDiscount.setTypeface(App.getTribuchet_MS());
            tvDiscount.setTypeface(App.getTribuchet_MS());
            tvTagTotalPayableAmount.setTypeface(App.getTribuchet_MS());
            tvTotalPayableAmount.setTypeface(App.getTribuchet_MS());
            //
            tvTagBillingAddress.setTypeface(App.getTribuchet_MS());
            tvBAUserName.setTypeface(App.getTribuchet_MS());
            tvBAAddressOne.setTypeface(App.getTribuchet_MS());
            tvBAAddressTwo.setTypeface(App.getTribuchet_MS());
            tvBAPincodeCity.setTypeface(App.getTribuchet_MS());
            tvBAStateCountry.setTypeface(App.getTribuchet_MS());
            //
            tvTagDeleveryAddress.setTypeface(App.getTribuchet_MS());
            tvDAUserName.setTypeface(App.getTribuchet_MS());
            tvDAAddressOne.setTypeface(App.getTribuchet_MS());
            tvDAAddressTwo.setTypeface(App.getTribuchet_MS());
            tvDAPincodeCity.setTypeface(App.getTribuchet_MS());
            tvDAStateCountry.setTypeface(App.getTribuchet_MS());

            //
            tvTagPaymentOption.setTypeface(App.getTribuchet_MS());
            tvTagPaymentType.setTypeface(App.getTribuchet_MS());
            tvPaymentType.setTypeface(App.getTribuchet_MS());
            tvTagPaymentStatus.setTypeface(App.getTribuchet_MS());
            tvPaymentStatus.setTypeface(App.getTribuchet_MS());
            tvTagShippedTo.setTypeface(App.getTribuchet_MS());
            tvShippedTo.setTypeface(App.getTribuchet_MS());
            tvTagOrderStats.setTypeface(App.getTribuchet_MS());
            tvOrderStats.setTypeface(App.getTribuchet_MS());

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

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setAdapter() {
        try
        {
            //
            mterialProgress.setVisibility(View.GONE);
            rlMain.setVisibility(View.VISIBLE);

            String orderNo = "Order No. <b>" + strOrderNo + "</b>";
            tvOrderNo.setText(Html.fromHtml(orderNo));

            //
            String paymentType = "<b>Pending</b>";
            tvPaymentStatus.setTextColor(ContextCompat.getColor(ActMyOrderDetail.this, R.color.clrRed));
            tvPaymentStatus.setText(Html.fromHtml(paymentType));
            //
            String paymentStatus = "<b>COD</b>";
            tvPaymentType.setText(Html.fromHtml(paymentStatus));
            //
            tvOrderStats.setText(strOrderStatus);

            if (strOrderStatus.contains("Del")){
                ivOrderStats.setImageResource(R.drawable.green_round);
            }
            else if (strOrderStatus.contains("Pen")){
                ivOrderStats.setImageResource(R.drawable.red_round);
            }
            else if (strOrderStatus.contains("Shi")){
                ivOrderStats.setImageResource(R.drawable.orange_round);
            }

            //
            String orderShippedTo = "<b>"+strOrderShippedTo+"</b>";
            tvShippedTo.setText(Html.fromHtml(orderShippedTo));


        } catch (Exception e) {e.printStackTrace();}
    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        App.myFinishActivity(ActMyOrderDetail.this);
    }
}
