package com.backup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.api.model.CartListModel;
import com.demoecommereceapp.R;
import com.ecommapp.App;
import com.ecommapp.BaseActivity;
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
    // RelativeLayout rlBottom;
    TextView tvTagAddress, tvTagPaymentShipping;
    View viewAddressBottom, viewPaymentShippingBottom;
    TextView tvTagBillingAddress, tvBATagChooesAddress, tvBAUserName, tvBAAddressOne, tvBAAddressTwo,
            tvBAPincodeCity, tvBAStateCountry, tvBATagChange;
    TextView tvTagDeleveryAddress, tvDATagChooesAddress, tvDAUserName, tvDAAddressOne, tvDAAddressTwo,
            tvDAPincodeCity, tvDAStateCountry, tvDATagChange;
    TextView tvNext, tvProceed;
    LinearLayout llBAAddressContent, llDAAddressContent;
    // LinearLayout llAddressLayout, llPaymentShippingLayout;
    FrameLayout card_back_payment_shipping, card_front_address;

    NestedScrollView nsView;
    RecyclerView recyclerView;
    MaterialProgressBar mterialProgress;
//    ViewPager viewPager;
//    ViewPagerAdapter viewPagerMainAdapter;
//    TabLayout tabLayout;
    TextView tvCartTotalItems/*, tvPlaceOrder*/;

    ArrayList arryCartCheckoutList = new ArrayList();
    CartCheckoutAdapter adapter;

    //
    String strFrom = "", strCartCheckoutList = "";
    String tagAdrUserName = "", tagAdrAddressOne = "", tagAdrAddressTwo = "",
            tagAdrPincode = "", tagAdrCity = "", tagAdrState = "", tagAdrCountry = "";

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
//            setCartListData();
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
            //baseViewHeaderShadow.setVisibility(View.GONE);
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
            // rlBottom = (RelativeLayout) findViewById(R.id.rlBottom);
            llMain.setVisibility(View.GONE);
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,  LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
//            viewPager = (ViewPager) findViewById(R.id.viewPager);
//            tabLayout = (TabLayout) findViewById(R.id.tabLayout);
            tvCartTotalItems = (TextView) findViewById(R.id.tvCartTotalItems);
            // tvPlaceOrder = (TextView) findViewById(R.id.tvPlaceOrder);

            tvTagAddress = (TextView) findViewById(R.id.tvTagAddress);
            viewAddressBottom = (View) findViewById(R.id.viewAddressBottom);
            viewAddressBottom.setVisibility(View.VISIBLE);
            tvTagAddress.setTextColor(ContextCompat.getColor(ActCheckout.this, R.color.clrBtnPink));


            tvTagPaymentShipping = (TextView) findViewById(R.id.tvTagPaymentShipping);
            viewPaymentShippingBottom = (View) findViewById(R.id.viewPaymentShippingBottom);
            viewPaymentShippingBottom.setVisibility(View.GONE);
            tvTagPaymentShipping.setTextColor(ContextCompat.getColor(ActCheckout.this, R.color.clrDarkGray));

            //
            tvTagBillingAddress = (TextView) findViewById(R.id.tvTagBillingAddress);
            tvBATagChooesAddress = (TextView) findViewById(R.id.tvBATagChooesAddress);
            tvBAUserName = (TextView) findViewById(R.id.tvBAUserName);
            tvBAAddressOne = (TextView) findViewById(R.id.tvBAAddressOne);
            tvBAAddressTwo = (TextView) findViewById(R.id.tvBAAddressTwo);
            tvBAPincodeCity = (TextView) findViewById(R.id.tvBAPincodeCity);
            tvBAStateCountry = (TextView) findViewById(R.id.tvBAStateCountry);
            tvBATagChange = (TextView) findViewById(R.id.tvBATagChange);
            //
            tvTagDeleveryAddress = (TextView) findViewById(R.id.tvTagDeleveryAddress);
            tvDATagChooesAddress = (TextView) findViewById(R.id.tvDATagChooesAddress);
            tvDAUserName = (TextView) findViewById(R.id.tvDAUserName);
            tvDAAddressOne = (TextView) findViewById(R.id.tvDAAddressOne);
            tvDAAddressTwo = (TextView) findViewById(R.id.tvDAAddressTwo);
            tvDAPincodeCity = (TextView) findViewById(R.id.tvDAPincodeCity);
            tvDAStateCountry = (TextView) findViewById(R.id.tvDAStateCountry);
            tvDATagChange = (TextView) findViewById(R.id.tvDATagChange);
            //
            tvNext = (TextView) findViewById(R.id.tvNext);
            tvProceed = (TextView) findViewById(R.id.tvProceed);
            //
            llBAAddressContent = (LinearLayout) findViewById(R.id.llBAAddressContent);
            llDAAddressContent = (LinearLayout) findViewById(R.id.llDAAddressContent);

            card_front_address = (FrameLayout) findViewById(R.id.card_front_address);
            card_back_payment_shipping = (FrameLayout) findViewById(R.id.card_back_payment_shipping);


//            llAddressLayout = (LinearLayout) findViewById(R.id.llAddressLayout);
//            llPaymentShippingLayout = (LinearLayout) findViewById(R.id.llPaymentShippingLayout);
//            llPaymentShippingLayout.setVisibility(View.GONE);


//            setupMainViewPager(viewPager);
//
//            tabLayout.setTabTextColors(getResources().getColorStateList(R.color.clrWhite));
//            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#EB3573"));
//            tabLayout.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));
//            tabLayout.setTabTextColors(Color.parseColor("#666666"), Color.parseColor("#EB3573"));
//            tabLayout.setupWithViewPager(viewPager);
//
//            ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
//            int tabsCount = vg.getChildCount();
//            for (int j = 0; j < tabsCount; j++) {
//                ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
//                int tabChildsCount = vgTab.getChildCount();
//                for (int i = 0; i < tabChildsCount; i++) {
//                    View tabViewChild = vgTab.getChildAt(i);
//                    if (tabViewChild instanceof TextView) {
//                        ((TextView) tabViewChild).setTypeface(App.getTribuchet_MS(), Typeface.NORMAL);
//                    }
//                }
//            }
//
//            viewPager.setCurrentItem(0);

        } catch (Exception e) {e.printStackTrace();}
    }


//    private void setupMainViewPager(ViewPager viewPager) {
//        try
//        {
//            App.showLog("==setupMainViewPager==");
//
//            // viewPagerMainAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//
//            if (strFrom != null && strFrom.equalsIgnoreCase("ActCartList"))
//            {
//                viewPagerMainAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//
//                viewPagerMainAdapter.addFrag(new AddressFragment(1, "1"), App.PLACE_ORDER_ADDRESS_FRAGMENT);
//                viewPagerMainAdapter.addFrag(new AddressFragment(1, "2"), App.PLACE_ORDER_PAYMENT_SHIPPING_FRAGMENT);
//            }
//            else if (strFrom != null && strFrom.equalsIgnoreCase("ActChooesAddress"))
//            {
//                viewPagerMainAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//
//                App.showLog("==setupMainViewPager==ActChooesAddress==");
//
//                AddressFragment fragment = new AddressFragment(1, "1");
//                Bundle conData = new Bundle();
//                conData.putString(App.ITAG_FROM, "ActCheckout");
//                conData.putString(AppFlags.tagAdrUserName, tagAdrUserName);
//                conData.putString(AppFlags.tagAdrAddressOne, tagAdrAddressOne);
//                conData.putString(AppFlags.tagAdrAddressTwo, tagAdrAddressTwo);
//                conData.putString(AppFlags.tagAdrPincode, tagAdrPincode);
//                conData.putString(AppFlags.tagAdrCity, tagAdrCity);
//                conData.putString(AppFlags.tagAdrState, tagAdrState);
//                conData.putString(AppFlags.tagAdrCountry, tagAdrCountry);
//
//                fragment.setArguments(conData);
//                viewPagerMainAdapter.addFrag(fragment, App.PLACE_ORDER_ADDRESS_FRAGMENT);
//
//
//                AddressFragment fragmentSecond = new AddressFragment(1, "2");
//                fragmentSecond.setArguments(conData);
//                viewPagerMainAdapter.addFrag(fragmentSecond, App.PLACE_ORDER_PAYMENT_SHIPPING_FRAGMENT);
//
//            }
//
////            viewPagerMainAdapter.addFrag(new AddressFragment(1, "1"), App.PLACE_ORDER_ADDRESS_FRAGMENT);
////            viewPagerMainAdapter.addFrag(new AddressFragment(1, "2"), App.PLACE_ORDER_PAYMENT_SHIPPING_FRAGMENT);
//
//            viewPager.setAdapter(viewPagerMainAdapter);
//
//        } catch (Exception e) {e.printStackTrace();}
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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

        }

        //
//        setupMainViewPager(viewPager);

        super.onActivityResult(requestCode, resultCode, data);
    }


    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
            tvNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
                    animation.setAnimationListener(new Animation.AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                            // TODO Auto-generated method stub
                            card_back_payment_shipping.setVisibility(View.VISIBLE);
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
                }
            });

            //
            tvProceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right);
                    animation.setAnimationListener(new Animation.AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                            // TODO Auto-generated method stub

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
                }
            });

        } catch (Exception e) {e.printStackTrace();}
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

//                //
//                String size = "Total cart items <b><font color='#EB3573'>" + arryCartList.size() + "</font></b>";
//                tvCartTotalItems.setText(Html.fromHtml(size));
//                // set adapter
//                adapter = new ActCartList.CartListAdapter(this, arryCartList);
//                recyclerView.setAdapter(adapter);
//
//                //
//                float fltTotal = 0;
//                float fltGST = 568.0f;
//                float fltShipping = 150.0f;
//                float fltDiscount = 665.0f;
//                for (int i = 0 ; i< arryCartList.size() ; i++)
//                {
//                    float temp_price = Float.parseFloat(arryCartList.get(i).cart_item_price);
//                    fltTotal += temp_price;
//                    App.showLog(TAG + "==total==" + fltTotal);
//                }
//                //
//                float fltPayableAmount = ((fltTotal + fltGST + fltShipping) - fltDiscount);
//                tvTotal.setText(App._RS + " " + fltTotal);
//                tvGST.setText(App._RS + " " + fltGST);
//                tvShippingCharge.setText(App._RS + " " + fltShipping);
//                tvDiscount.setText(App._RS + " " + fltDiscount);
//                tvTotalPayableAmount.setText(App._RS + " " + fltPayableAmount);
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


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        App.myFinishActivity(ActCheckout.this);
    }
}
