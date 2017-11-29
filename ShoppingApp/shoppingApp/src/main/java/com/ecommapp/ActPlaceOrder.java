package com.ecommapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.api.model.CartListModel;
import com.demoecommereceapp.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.util.AppFlags;
import com.util.CustomProgressDialog;
import com.util.MaterialProgressBar;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhaval.mehta on 02-Nov-2017.
 */

public class ActPlaceOrder extends BaseActivity {

    String TAG = "==ActPlaceOrder==";

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
    TextView tvTagPaymentOption, tvTagPaymentType, tvPaymentType, tvTagPaymentStatus, tvPaymentStatus;
    //
    TextView tvPlaceOrder;

    //
    String strFrom = "", strCartPlaceOrder = "";
    //
    ArrayList arryCartPlaceOrder = new ArrayList();
    CartCheckoutAdapter adapter;
    //
    CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            //
            ViewGroup.inflate(this, R.layout.act_place_order, llContainerSub);
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
                if (bundle.getString(AppFlags.tagCartCheckoutList) != null &&
                        bundle.getString(AppFlags.tagCartCheckoutList).length() > 0) {

                    strCartPlaceOrder = bundle.getString(AppFlags.tagCartCheckoutList);
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<CartListModel>>(){}.getType();
                    ArrayList<CartListModel> carsList = gson.fromJson(strCartPlaceOrder, type);
                    for (CartListModel model : carsList)
                    {
                        App.showLogTAG("-----------**************--------");
                        App.showLog(TAG + "==cartCheckout==name==" + model.cart_item_name);
                        App.showLog(TAG + "==cartCheckout==image==" + model.cart_item_image);
                        App.showLog(TAG + "==cartCheckout==price==" + App._RS + " " + model.cart_item_price);
                        App.showLog(TAG + "==cartCheckout==Qty==" + model.cart_item_qty);
                        App.showLog(TAG + "==cartCheckout==Size==" + model.cart_item_size);

                        arryCartPlaceOrder.add(model);
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
            tvTitle.setText("Place Order");

            //
            mterialProgress = (MaterialProgressBar) findViewById(R.id.mterialProgress);
            mterialProgress.setVisibility(View.VISIBLE);
            customProgressDialog = new CustomProgressDialog(ActPlaceOrder.this);

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
            //
            tvPlaceOrder = (TextView) findViewById(R.id.tvPlaceOrder);

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setFonts() {
        try
        {
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

            //
            tvPlaceOrder.setTypeface(App.getTribuchet_MS());

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
            tvPlaceOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customProgressDialog.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            customProgressDialog.dismiss();

                            App.showToastShort(ActPlaceOrder.this, "Your order has been placed successfuly");

                            Intent iv = new Intent(ActPlaceOrder.this, ActDashboard.class);
                            App.myStartActivity(ActPlaceOrder.this, iv);
                        }
                    }, 3000);
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
            //
            if (arryCartPlaceOrder != null && arryCartPlaceOrder.size() > 0)
            {
                String size = "(<b><font color='#EB3573'>" + arryCartPlaceOrder.size() + "</font></b> items)";
                tvCartItems.setText(Html.fromHtml(size));
                //
                adapter = new CartCheckoutAdapter(this, arryCartPlaceOrder);
                recyclerView.setAdapter(adapter);
            }

            //
            String paymentType = "<b>Pending</b>";
            tvPaymentStatus.setTextColor(ContextCompat.getColor(ActPlaceOrder.this, R.color.clrRed));
            tvPaymentStatus.setText(Html.fromHtml(paymentType));
            //
            String paymentStatus = "<b>COD</b>";
            tvPaymentType.setText(Html.fromHtml(paymentStatus));

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
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_cart_place_order_item,
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
                                .setResizeOptions(new ResizeOptions(120, 120))
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
        App.myFinishActivity(ActPlaceOrder.this);
    }
}
