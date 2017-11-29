package com.ecommapp;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.api.model.CartListModel;
import com.demoecommereceapp.R;
import com.ecommapp.fragment.AddressFragment;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.util.AppFlags;
import com.util.MaterialProgressBar;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhaval.mehta on 27-Oct-2017.
 */

public class ActCheckout extends BaseActivity {

    String TAG = "==ActCheckout==";

    //
    RelativeLayout rlNoData;
    TextView tvNoData;
    //
    RelativeLayout llMain;
    TextView tvTagAddress, tvTagPaymentShipping;
    View viewAddressBottom, viewPaymentShippingBottom;
    View viewCardAddress, viewPaymentShipping;
    TextView tvTagBillingAddress, tvBATagChooesAddress, tvBAUserName, tvBAAddressOne, tvBAAddressTwo,
            tvBAPincodeCity, tvBAStateCountry, tvBATagChange;
    TextView tvTagDeleveryAddress, tvDATagChooesAddress, tvDAUserName, tvDAAddressOne, tvDAAddressTwo,
            tvDAPincodeCity, tvDAStateCountry, tvDATagChange;
    TextView tvNext, tvPrevious, tvProceed;
    LinearLayout llBAAddressContent, llDAAddressContent;
    FrameLayout card_back_payment_shipping, card_front_address;
    TextView tvTagPaymentOption;
    RadioGroup rgGroup;
    RadioButton rbCOD, rbPayuMoney, rbPayPal, rbStripe;

    NestedScrollView nsView;
    RecyclerView recyclerView;
    MaterialProgressBar mterialProgress;
    TextView tvCartTotalItems;

    ArrayList arryCartCheckoutList = new ArrayList();
    CartCheckoutAdapter adapter;

    //
    String strFrom = "", strCartCheckoutList = "";
    String tagAdrUserName = "", tagAdrAddressOne = "", tagAdrAddressTwo = "",
            tagAdrPincode = "", tagAdrCity = "", tagAdrState = "", tagAdrCountry = "";
    String tagDAAdrUserName = "", tagDAAdrAddressOne = "", tagDAAdrAddressTwo = "",
            tagDAAdrPincode = "", tagDAAdrCity = "", tagDAAdrState = "", tagDAAdrCountry = "";

    int SCREEN_HEIGHT = 0, SCREEN_WIDTH = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            //
            ViewGroup.inflate(this, R.layout.act_checkout, llContainerSub);
            App.showLogTAG(TAG);

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            SCREEN_HEIGHT = size.y;
            SCREEN_WIDTH = size.x;

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
                if (bundle.getString(AppFlags.tagCartCheckoutList) != null &&
                        bundle.getString(AppFlags.tagCartCheckoutList).length() > 0) {

                    strCartCheckoutList = bundle.getString(AppFlags.tagCartCheckoutList);
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<CartListModel>>(){}.getType();
                    ArrayList<CartListModel> carsList = gson.fromJson(strCartCheckoutList, type);
                    for (CartListModel model : carsList)
                    {
                        App.showLogTAG("-----------**************--------");
                        App.showLog(TAG + "==cartCheckout==name==" + model.cart_item_name);
                        App.showLog(TAG + "==cartCheckout==image==" + model.cart_item_image);
                        App.showLog(TAG + "==cartCheckout==price==" + App._RS + " " + model.cart_item_price);
                        App.showLog(TAG + "==cartCheckout==Qty==" + model.cart_item_qty);
                        App.showLog(TAG + "==cartCheckout==Size==" + model.cart_item_size);

                        arryCartCheckoutList.add(model);
                    }
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
            tvTitle.setText("Checkout");

            //
            rlNoData = (RelativeLayout) findViewById(R.id.rlNoData);
            rlNoData.setVisibility(View.GONE);
            tvNoData = (TextView) findViewById(R.id.tvNoData);

            //
            mterialProgress = (MaterialProgressBar) findViewById(R.id.mterialProgress);
            mterialProgress.setVisibility(View.VISIBLE);

            llMain = (RelativeLayout) findViewById(R.id.llMain);
            nsView = (NestedScrollView) findViewById(R.id.nsView);
            llMain.setVisibility(View.GONE);
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,  LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            tvCartTotalItems = (TextView) findViewById(R.id.tvCartTotalItems);

            tvTagAddress = (TextView) findViewById(R.id.tvTagAddress);
            viewAddressBottom = (View) findViewById(R.id.viewAddressBottom);
            viewAddressBottom.setVisibility(View.VISIBLE);
            tvTagAddress.setTextColor(ContextCompat.getColor(ActCheckout.this, R.color.clrBtnPink));

            tvTagPaymentShipping = (TextView) findViewById(R.id.tvTagPaymentShipping);
            viewPaymentShippingBottom = (View) findViewById(R.id.viewPaymentShippingBottom);
            viewPaymentShippingBottom.setVisibility(View.GONE);
            tvTagPaymentShipping.setTextColor(ContextCompat.getColor(ActCheckout.this, R.color.clrDarkGray));

            card_front_address = (FrameLayout) findViewById(R.id.card_front_address);
            card_back_payment_shipping = (FrameLayout) findViewById(R.id.card_back_payment_shipping);

            /*
            * Address View controls
            * */
            viewCardAddress = findViewById(R.id.card_front_address);
            tvTagBillingAddress = (TextView) viewCardAddress.findViewById(R.id.tvTagBillingAddress);
            tvBATagChooesAddress = (TextView) viewCardAddress.findViewById(R.id.tvBATagChooesAddress);
            tvBAUserName = (TextView) viewCardAddress.findViewById(R.id.tvBAUserName);
            tvBAAddressOne = (TextView) viewCardAddress.findViewById(R.id.tvBAAddressOne);
            tvBAAddressTwo = (TextView) viewCardAddress.findViewById(R.id.tvBAAddressTwo);
            tvBAPincodeCity = (TextView) viewCardAddress.findViewById(R.id.tvBAPincodeCity);
            tvBAStateCountry = (TextView) viewCardAddress.findViewById(R.id.tvBAStateCountry);
            tvBATagChange = (TextView) viewCardAddress.findViewById(R.id.tvBATagChange);
            //
            tvTagDeleveryAddress = (TextView) viewCardAddress.findViewById(R.id.tvTagDeleveryAddress);
            tvDATagChooesAddress = (TextView) viewCardAddress.findViewById(R.id.tvDATagChooesAddress);
            tvDAUserName = (TextView) viewCardAddress.findViewById(R.id.tvDAUserName);
            tvDAAddressOne = (TextView) viewCardAddress.findViewById(R.id.tvDAAddressOne);
            tvDAAddressTwo = (TextView) viewCardAddress.findViewById(R.id.tvDAAddressTwo);
            tvDAPincodeCity = (TextView) viewCardAddress.findViewById(R.id.tvDAPincodeCity);
            tvDAStateCountry = (TextView) viewCardAddress.findViewById(R.id.tvDAStateCountry);
            tvDATagChange = (TextView) viewCardAddress.findViewById(R.id.tvDATagChange);
            //
            tvNext = (TextView) viewCardAddress.findViewById(R.id.tvNext);
            //
            llBAAddressContent = (LinearLayout) viewCardAddress.findViewById(R.id.llBAAddressContent);
            llBAAddressContent.setVisibility(View.GONE);
            llDAAddressContent = (LinearLayout) viewCardAddress.findViewById(R.id.llDAAddressContent);
            llDAAddressContent.setVisibility(View.GONE);


            viewPaymentShipping = findViewById(R.id.card_back_payment_shipping);
            tvTagPaymentOption = (TextView) viewPaymentShipping.findViewById(R.id.tvTagPaymentOption);
            rgGroup = (RadioGroup) viewPaymentShipping.findViewById(R.id.rgGroup);
            rbCOD = (RadioButton) viewPaymentShipping.findViewById(R.id.rbCOD);
            rbPayuMoney = (RadioButton) viewPaymentShipping.findViewById(R.id.rbPayuMoney);
            rbPayPal = (RadioButton) viewPaymentShipping.findViewById(R.id.rbPayPal);
            rbStripe = (RadioButton) viewPaymentShipping.findViewById(R.id.rbStripe);
            //
            tvPrevious = (TextView) viewPaymentShipping.findViewById(R.id.tvPrevious);
            tvProceed = (TextView) viewPaymentShipping.findViewById(R.id.tvProceed);

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setFonts() {
        try
        {
            tvNoData.setTypeface(App.getTribuchet_MS());
            tvCartTotalItems.setTypeface(App.getTribuchet_MS());
            tvTagAddress.setTypeface(App.getTribuchet_MS());
            tvTagPaymentShipping.setTypeface(App.getTribuchet_MS());
            // tvPlaceOrder.setTypeface(App.getTribuchet_MS());
            tvTagBillingAddress.setTypeface(App.getTribuchet_MS());
            tvBATagChooesAddress.setTypeface(App.getTribuchet_MS());
            tvBAUserName.setTypeface(App.getTribuchet_MS());
            tvBAAddressOne.setTypeface(App.getTribuchet_MS());
            tvBAAddressTwo.setTypeface(App.getTribuchet_MS());
            tvBAPincodeCity.setTypeface(App.getTribuchet_MS());
            tvBAStateCountry.setTypeface(App.getTribuchet_MS());
            tvBATagChange.setTypeface(App.getTribuchet_MS());
            //
            tvTagDeleveryAddress.setTypeface(App.getTribuchet_MS());
            tvDATagChooesAddress.setTypeface(App.getTribuchet_MS());
            tvDAUserName.setTypeface(App.getTribuchet_MS());
            tvDAAddressOne.setTypeface(App.getTribuchet_MS());
            tvDAAddressTwo.setTypeface(App.getTribuchet_MS());
            tvDAPincodeCity.setTypeface(App.getTribuchet_MS());
            tvDAStateCountry.setTypeface(App.getTribuchet_MS());
            tvDATagChange.setTypeface(App.getTribuchet_MS());
            //
            tvNext.setTypeface(App.getTribuchet_MS());

            //
            //
            tvTagPaymentOption.setTypeface(App.getTribuchet_MS());
            rbCOD.setTypeface(App.getTribuchet_MS());
            rbPayuMoney.setTypeface(App.getTribuchet_MS());
            rbPayPal.setTypeface(App.getTribuchet_MS());
            rbStripe.setTypeface(App.getTribuchet_MS());
            //
            tvPrevious.setTypeface(App.getTribuchet_MS());
            tvProceed.setTypeface(App.getTribuchet_MS());

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
            tvTagAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    animationPaymentOUT_AddressIn();
                }
            });

            //
            tvTagPaymentShipping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    animationAddressOUT_PaymentIn();
                }
            });


            //
            tvNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    animationAddressOUT_PaymentIn();
                }
            });

            //
            tvPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    animationPaymentOUT_AddressIn();
                }
            });

            //
            tvProceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rbCOD.isChecked() == true)
                    {
                        Gson gson = new Gson();
                        String jsonCheckout = gson.toJson(arryCartCheckoutList);

                        Intent iv = new Intent(ActCheckout.this, ActPlaceOrder.class);
                        iv.putExtra(AppFlags.tagCartCheckoutList, jsonCheckout);
                        App.myStartActivity(ActCheckout.this, iv);
                    }
                    else if (rbPayuMoney.isChecked() == true)
                    {

                    }
                    else if (rbPayPal.isChecked() == true)
                    {

                    }
                    else if (rbStripe.isChecked() == true)
                    {

                    }
                    else
                    {
                        App.showToastShort(ActCheckout.this, "Please Select valid payment option");
                    }
                }
            });

            //
            tvBATagChooesAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setClassName("com.demoecommereceapp", "com.ecommapp.ActChooesAddress");
                    intent.putExtra(AppFlags.iResCode, "" + App.BILLING_ADDRESS_I_RES_CODE);
                    startActivityForResult(intent, App.BILLING_ADDRESS_I_RES_CODE);
                    animStartActivity();
                    // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                }
            });

            //
            tvBATagChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setClassName("com.demoecommereceapp", "com.ecommapp.ActChooesAddress");
                    intent.putExtra(AppFlags.iResCode, "" + App.BILLING_ADDRESS_I_RES_CODE);
                    startActivityForResult(intent, App.BILLING_ADDRESS_I_RES_CODE);
                    animStartActivity();
                    // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                }
            });


            //
            tvDATagChooesAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setClassName("com.demoecommereceapp", "com.ecommapp.ActChooesAddress");
                    intent.putExtra(AppFlags.iResCode, "" + App.DELIVERY_ADDRESS_I_RES_CODE);
                    startActivityForResult(intent, App.DELIVERY_ADDRESS_I_RES_CODE);
                    animStartActivity();
                    // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                }
            });

            //
            tvDATagChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setClassName("com.demoecommereceapp", "com.ecommapp.ActChooesAddress");
                    intent.putExtra(AppFlags.iResCode, "" + App.DELIVERY_ADDRESS_I_RES_CODE);
                    startActivityForResult(intent, App.DELIVERY_ADDRESS_I_RES_CODE);
                    animStartActivity();
                    // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                }
            });


        } catch (Exception e) {e.printStackTrace();}
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try
        {
            App.showLog(TAG + "==onActivityResult==");
            App.showLog(TAG + "==onActivityResult==resultCode==" + resultCode);

            if (resultCode == App.BILLING_ADDRESS_I_RES_CODE)
            {
                Bundle res = data.getExtras();
                strFrom = res.getString(App.ITAG_FROM);
                tagAdrUserName = res.getString(AppFlags.tagAdrUserName);
                tagAdrAddressOne = res.getString(AppFlags.tagAdrAddressOne);
                tagAdrAddressTwo = res.getString(AppFlags.tagAdrAddressTwo);
                tagAdrPincode = res.getString(AppFlags.tagAdrPincode);
                tagAdrCity = res.getString(AppFlags.tagAdrCity);
                tagAdrState = res.getString(AppFlags.tagAdrState);
                tagAdrCountry = res.getString(AppFlags.tagAdrCountry);

                App.showLogTAG("------ ***** onActivityResult ***** -----");
                App.showLog("==strFrom==" + strFrom);
                App.showLog("==tagAdrUserName==" + tagAdrUserName);
                App.showLog("==tagAdrAddressOne==" + tagAdrAddressOne);
                App.showLog("==tagAdrAddressTwo==" + tagAdrAddressTwo);
                App.showLog("==tagAdrPincode==" + tagAdrPincode);
                App.showLog("==tagAdrCity==" + tagAdrCity);
                App.showLog("==tagAdrState==" + tagAdrState);
                App.showLog("==tagAdrCountry==" + tagAdrCountry);


                if (tagAdrUserName != null && tagAdrUserName.length() > 0 &&
                        tagAdrAddressOne != null && tagAdrAddressOne.length() > 0 &&
                        tagAdrPincode != null && tagAdrPincode.length() > 0 &&
                        tagAdrCity != null && tagAdrCity.length() > 0)
                {
                    tvBATagChooesAddress.setVisibility(View.GONE);
                    llBAAddressContent.setVisibility(View.VISIBLE);
                    //
                    if (tagAdrUserName != null && tagAdrUserName.length() > 0)
                    {
                        tvBAUserName.setText(tagAdrUserName);
                    }
                    //
                    if (tagAdrAddressOne != null && tagAdrAddressOne.length() > 0)
                    {
                        tvBAAddressOne.setText(tagAdrAddressOne);
                    }
                    //
                    if (tagAdrAddressTwo != null && tagAdrAddressTwo.length() > 0)
                    {
                        tvBAAddressTwo.setText(tagAdrAddressTwo);
                    }
                    //
                    if (tagAdrPincode != null && tagAdrPincode.length() > 0 &&
                            tagAdrCity != null && tagAdrCity.length() > 0)
                    {
                        tvBAPincodeCity.setText(tagAdrPincode + " - " + tagAdrCity);
                    }
                    //
                    if (tagAdrState != null && tagAdrState.length() > 0 &&
                            tagAdrCountry != null && tagAdrCountry.length() > 0)
                    {
                        tvBAStateCountry.setText(tagAdrState + ", " + tagAdrCountry);
                    }
                }
            }
            else if (resultCode == App.DELIVERY_ADDRESS_I_RES_CODE)
            {
                Bundle res = data.getExtras();
                strFrom = res.getString(App.ITAG_FROM);
                tagDAAdrUserName = res.getString(AppFlags.tagAdrUserName);
                tagDAAdrAddressOne = res.getString(AppFlags.tagAdrAddressOne);
                tagDAAdrAddressTwo = res.getString(AppFlags.tagAdrAddressTwo);
                tagDAAdrPincode = res.getString(AppFlags.tagAdrPincode);
                tagDAAdrCity = res.getString(AppFlags.tagAdrCity);
                tagDAAdrState = res.getString(AppFlags.tagAdrState);
                tagDAAdrCountry = res.getString(AppFlags.tagAdrCountry);

                App.showLogTAG("------ ***** onActivityResult ***** -----");
                App.showLog("==strFrom==" + strFrom);
                App.showLog("==tagAdrUserName==" + tagAdrUserName);
                App.showLog("==tagAdrAddressOne==" + tagAdrAddressOne);
                App.showLog("==tagAdrAddressTwo==" + tagAdrAddressTwo);
                App.showLog("==tagAdrPincode==" + tagAdrPincode);
                App.showLog("==tagAdrCity==" + tagAdrCity);
                App.showLog("==tagAdrState==" + tagAdrState);
                App.showLog("==tagAdrCountry==" + tagAdrCountry);


                if (tagDAAdrUserName != null && tagDAAdrUserName.length() > 0 &&
                        tagDAAdrAddressOne != null && tagDAAdrAddressOne.length() > 0 &&
                        tagDAAdrPincode != null && tagDAAdrPincode.length() > 0 &&
                        tagDAAdrCity != null && tagDAAdrCity.length() > 0)
                {
                    tvDATagChooesAddress.setVisibility(View.GONE);
                    llDAAddressContent.setVisibility(View.VISIBLE);
                    //
                    if (tagDAAdrUserName != null && tagDAAdrUserName.length() > 0)
                    {
                        tvDAUserName.setText(tagDAAdrUserName);
                    }
                    //
                    if (tagDAAdrAddressOne != null && tagDAAdrAddressOne.length() > 0)
                    {
                        tvDAAddressOne.setText(tagDAAdrAddressOne);
                    }
                    //
                    if (tagDAAdrAddressTwo != null && tagDAAdrAddressTwo.length() > 0)
                    {
                        tvDAAddressTwo.setText(tagDAAdrAddressTwo);
                    }
                    //
                    if (tagDAAdrPincode != null && tagDAAdrPincode.length() > 0 &&
                            tagDAAdrCity != null && tagDAAdrCity.length() > 0)
                    {
                        tvDAPincodeCity.setText(tagDAAdrPincode + " - " + tagDAAdrCity);
                    }
                    //
                    if (tagDAAdrState != null && tagDAAdrState.length() > 0 &&
                            tagDAAdrCountry != null && tagDAAdrCountry.length() > 0)
                    {
                        tvDAStateCountry.setText(tagDAAdrState + ", " + tagDAAdrCountry);
                    }
                }
            }

        } catch (Exception e) {e.printStackTrace();}

        super.onActivityResult(requestCode, resultCode, data);
    }


    private void setAdapter() {
        try
        {
            if (arryCartCheckoutList != null && arryCartCheckoutList.size() > 0)
            {
                mterialProgress.setVisibility(View.GONE);
                rlNoData.setVisibility(View.GONE);
                llMain.setVisibility(View.VISIBLE);

                //
                String size = "Total cart items <b><font color='#EB3573'>" + arryCartCheckoutList.size() + "</font></b>";
                tvCartTotalItems.setText(Html.fromHtml(size));
                //
                adapter = new CartCheckoutAdapter(this, arryCartCheckoutList);
                recyclerView.setAdapter(adapter);
            }
            else
            {
                mterialProgress.setVisibility(View.GONE);
                llMain.setVisibility(View.GONE);
                rlNoData.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {e.printStackTrace();}

    }


    public class CartCheckoutAdapter extends RecyclerView.Adapter<CartCheckoutAdapter.VersionViewHolder> {
        ArrayList<CartListModel> mArrayList;
        Context mContext;
        int width;


        public CartCheckoutAdapter(Context context, ArrayList<CartListModel> arrayList) {
            mArrayList = arrayList;
            mContext = context;
        }

        @Override
        public CartCheckoutAdapter.VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_cart_checkout_item,
                    viewGroup, false);
            CartCheckoutAdapter.VersionViewHolder viewHolder = new CartCheckoutAdapter.VersionViewHolder(view);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final CartCheckoutAdapter.VersionViewHolder versionViewHolder, final int position) {
            try
            {
                if (mArrayList != null && mArrayList.size() > 0)
                {
                    CartListModel model = mArrayList.get(position);

//
                    if (model.cart_item_name != null && model.cart_item_name.length() > 0)
                    {
                        versionViewHolder.tvItemName.setText(model.cart_item_name);
                    }
                    //
                    if (model.cart_item_price != null && model.cart_item_price.length() > 0)
                    {
                        versionViewHolder.tvItemPrice.setText(App._RS + " " + model.cart_item_price);
                    }
                    //
                    if (model.cart_item_size != null && model.cart_item_size.length() > 0)
                    {
                        // versionViewHolder.tvItemSize.setText(model.cart_item_size);
                        String size = "Size <b><font color='#EB3573'>" + model.cart_item_size + "</font></b>";
                        versionViewHolder.tvItemSize.setText(Html.fromHtml(size));
                    }
                    //
                    if (model.cart_item_qty != null && model.cart_item_qty.length() > 0)
                    {
                        // versionViewHolder.tvItemQty.setText(model.cart_item_qty);
                        String qty = "Qty <b><font color='#EB3573'>" + model.cart_item_qty+ "</font></b>";
                        versionViewHolder.tvItemQty.setText(Html.fromHtml(qty));
                    }
                    //
                    if (model.cart_item_image != null && model.cart_item_image.length() > 0)
                    {
                        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(model.cart_item_image))
                                .setResizeOptions(new ResizeOptions(90, 90))
                                .build();
                        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                                .setImageRequest(imageRequest)
                                .setOldController(versionViewHolder.ivProductImg.getController())
                                .setAutoPlayAnimations(true)
                                .build();
                        versionViewHolder.ivProductImg.setController(draweeController);
//                            Uri imageUri = Uri.parse(model.noti_image);
//                            versionViewHolder.ivInfoImage.setImageURI(imageUri);
                    }
                }
            }
            catch (Exception e) {e.printStackTrace();}
        }

        @Override
        public int getItemCount() {
            return mArrayList.size();
        }


        class VersionViewHolder extends RecyclerView.ViewHolder
        {
            LinearLayout llInfo;
            TextView tvItemName, tvItemPrice, tvItemSize, tvItemQty;
            SimpleDraweeView ivProductImg;

            public VersionViewHolder(View itemView) {
                super(itemView);

                // //
                llInfo = (LinearLayout) itemView.findViewById(R.id.llInfo);
                tvItemName = (TextView) itemView.findViewById(R.id.tvItemName);
                tvItemPrice = (TextView) itemView.findViewById(R.id.tvItemPrice);
                tvItemSize = (TextView) itemView.findViewById(R.id.tvItemSize);
                tvItemQty = (TextView) itemView.findViewById(R.id.tvItemQty);

                ivProductImg = (SimpleDraweeView) itemView.findViewById(R.id.ivProductImg);

                /*
                * Set Fonts - TYPE - INFO
                * */
                tvItemName.setTypeface(App.getTribuchet_MS());
                tvItemPrice.setTypeface(App.getTribuchet_MS());
                tvItemSize.setTypeface(App.getTribuchet_MS());
                tvItemQty.setTypeface(App.getTribuchet_MS());
            }
        }
    }


    private void animationAddressOUT_PaymentIn() {
        try
        {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
            animation.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                    // TODO Auto-generated method stub
                    card_back_payment_shipping.setVisibility(View.VISIBLE);
                    //
                    viewAddressBottom.setVisibility(View.GONE);
                    tvTagAddress.setTextColor(ContextCompat.getColor(ActCheckout.this, R.color.clrDarkGray));
                    viewPaymentShippingBottom.setVisibility(View.VISIBLE);
                    tvTagPaymentShipping.setTextColor(ContextCompat.getColor(ActCheckout.this, R.color.clrBtnPink));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    card_front_address.setVisibility(View.GONE);
                }
            });
            card_front_address.startAnimation(animation);
        } catch (Exception e) {e.printStackTrace();}
    }


    private void animationPaymentOUT_AddressIn() {
        try
        {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right);
            animation.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                    // TODO Auto-generated method stub
                    //
                    viewAddressBottom.setVisibility(View.VISIBLE);
                    tvTagAddress.setTextColor(ContextCompat.getColor(ActCheckout.this, R.color.clrBtnPink));
                    viewPaymentShippingBottom.setVisibility(View.GONE);
                    tvTagPaymentShipping.setTextColor(ContextCompat.getColor(ActCheckout.this, R.color.clrDarkGray));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    card_front_address.setVisibility(View.VISIBLE);
                    card_back_payment_shipping.setVisibility(View.GONE);
                }
            });
            card_back_payment_shipping.startAnimation(animation);
        } catch (Exception e) {e.printStackTrace();}
    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        App.myFinishActivity(ActCheckout.this);
    }
}
