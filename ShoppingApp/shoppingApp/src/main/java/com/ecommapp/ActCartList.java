package com.ecommapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.util.AppFlags;
import com.util.MaterialProgressBar;
import com.util.expandable_layout.ExpandableLayout;

import java.util.ArrayList;

/**
 * Created by dhaval.mehta on 10-Oct-2017.
 */

public class ActCartList extends BaseActivity {

    String TAG = "==ActCartList==";
    //
    RelativeLayout rlNoData;
    TextView tvNoData;
    //
    RelativeLayout llMain;
    RelativeLayout rlBottom;
    NestedScrollView nsView;
    RecyclerView recyclerView;
    MaterialProgressBar mterialProgress;
    TextView tvCartTotalItems, tvTagTotal, tvTagGST, tvTagShippingCharge, tvTagDiscount,
            tvTagTotalPayableAmount, tvCheckout;
    public static TextView tvTotal, tvGST, tvShippingCharge, tvDiscount, tvTotalPayableAmount;
    //
    ArrayList<CartListModel> arryCartList;
    CartListAdapter adapter;
    ArrayList<CartListModel> arryCheckoutList = new ArrayList<>();

    int SCREEN_HEIGHT = 0, SCREEN_WIDTH = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            //
            ViewGroup.inflate(this, R.layout.act_cart_list, llContainerSub);
            App.showLogTAG(TAG);

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            SCREEN_HEIGHT = size.y; // 720 * 0.30
            SCREEN_WIDTH = size.x;

            initialisation();
            setFonts();
            setClickEvents();
            setCartListData();

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


    private void initialisation() {
        try
        {
            //
            baseViewHeaderShadow.setVisibility(View.GONE);
            rlBaseMainHeader.setVisibility(View.VISIBLE);
            setEnableDrawer(true);
            rlBack.setVisibility(View.VISIBLE);
            tvTitle.setText("My Cart");

            //
            rlNoData = (RelativeLayout) findViewById(R.id.rlNoData);
            rlNoData.setVisibility(View.GONE);
            tvNoData = (TextView) findViewById(R.id.tvNoData);

            //
            mterialProgress = (MaterialProgressBar) findViewById(R.id.mterialProgress);
            mterialProgress.setVisibility(View.VISIBLE);

            llMain = (RelativeLayout) findViewById(R.id.llMain);
            nsView = (NestedScrollView) findViewById(R.id.nsView);
            rlBottom = (RelativeLayout) findViewById(R.id.rlBottom);
            llMain.setVisibility(View.GONE);
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);


            tvCartTotalItems = (TextView) findViewById(R.id.tvCartTotalItems);
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
            tvCheckout = (TextView) findViewById(R.id.tvCheckout);

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setFonts() {
        try
        {
            tvNoData.setTypeface(App.getTribuchet_MS());
            tvCartTotalItems.setTypeface(App.getTribuchet_MS());
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
            tvCheckout.setTypeface(App.getTribuchet_MS());

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
            nsView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    int diff = scrollY - oldScrollY;
                    if (diff >= 0)
                    {
                        //App.showLog("Scroll Up");
                        Animation slideDown = AnimationUtils.loadAnimation(ActCartList.this, R.anim.slide_down);
                        slideDown.setDuration(100);
                        rlBottom.startAnimation(slideDown);
                        rlBottom.setVisibility(View.GONE);
                    }
                    else
                    {
                        //App.showLog("Scroll Down");
                        Animation slideUp = AnimationUtils.loadAnimation(ActCartList.this, R.anim.slide_up);
                        slideUp.setDuration(100);
                        rlBottom.startAnimation(slideUp);
                        rlBottom.setVisibility(View.VISIBLE);
                    }
                }
            });

            //
            tvCheckout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Gson gson = new Gson();
                    String jsonCheckout = gson.toJson(arryCheckoutList);

                    Intent iv = new Intent(ActCartList.this, ActCheckout.class);
                    iv.putExtra(App.ITAG_FROM, "ActCartList");
                    iv.putExtra(AppFlags.tagCartCheckoutList, jsonCheckout);
                    App.myStartActivity(ActCartList.this, iv);
                }
            });

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setCartListData() {
        try
        {
            arryCartList = new ArrayList<>();

            ArrayList<CartListModel.CartQtyModel> arrayQtyList = new ArrayList<>();
            arrayQtyList.add(new CartListModel.CartQtyModel("1"));
            arrayQtyList.add(new CartListModel.CartQtyModel("2"));
            arrayQtyList.add(new CartListModel.CartQtyModel("3"));
            arrayQtyList.add(new CartListModel.CartQtyModel("4"));
            arrayQtyList.add(new CartListModel.CartQtyModel("5"));
            arrayQtyList.add(new CartListModel.CartQtyModel("6"));


            ArrayList<CartListModel.CartSizeModel> arrayList1 = new ArrayList<>();
            arrayList1.add(new CartListModel.CartSizeModel("Ex S"));
            arrayList1.add(new CartListModel.CartSizeModel("M"));
            arrayList1.add(new CartListModel.CartSizeModel("L"));
            arrayList1.add(new CartListModel.CartSizeModel("XL"));
            arryCartList.add(new CartListModel(
                    "1",
                    "Denim-Shirt-Men-Mens-Casual-Shirts",
                    "750.00",
                    "https://i.pinimg.com/736x/fd/7d/69/fd7d69c38c079674c2a1a84845df7237--denim-shirt-men-mens-casual-shirts.jpg",
                    "1",
                    "L",
                    arrayList1,
                    arrayQtyList
            ));

            ArrayList<CartListModel.CartSizeModel> arrayList2 = new ArrayList<>();
            arrayList2.add(new CartListModel.CartSizeModel("M"));
            arrayList2.add(new CartListModel.CartSizeModel("L"));
            arrayList2.add(new CartListModel.CartSizeModel("XL"));
            arryCartList.add(new CartListModel(
                    "2",
                    "Adidas-Shorts-NOVA-14-Black-White",
                    "1125.00",
                    "https://www.pslteamsports.com/assets/images/adidas/shorts/adi_nova14_blackwhite.jpg",
                    "1",
                    "M",
                    arrayList2,
                    arrayQtyList
                    ));

            ArrayList<CartListModel.CartSizeModel> arrayList3 = new ArrayList<>();
            arrayList3.add(new CartListModel.CartSizeModel("M"));
            arrayList3.add(new CartListModel.CartSizeModel("L"));
            arrayList3.add(new CartListModel.CartSizeModel("XL"));
            arrayList3.add(new CartListModel.CartSizeModel("XXL"));
            arrayList3.add(new CartListModel.CartSizeModel("3XL"));
            arryCartList.add(new CartListModel(
                    "3",
                    "Man-Jacket-Field-Lather-Brown-Jacket",
                    "3350.00",
                    "https://i.pinimg.com/736x/b9/68/76/b9687630983cd5e3615d179f79e2ab59--man-jacket-field-jacket-men.jpg",
                    "1",
                    "XL",
                    arrayList3,
                    arrayQtyList
                    ));

            ArrayList<CartListModel.CartSizeModel> arrayList4 = new ArrayList<>();
            arrayList4.add(new CartListModel.CartSizeModel("38"));
            arrayList4.add(new CartListModel.CartSizeModel("40"));
            arrayList4.add(new CartListModel.CartSizeModel("42"));
            arrayList4.add(new CartListModel.CartSizeModel("46"));
            arryCartList.add(new CartListModel(
                    "4",
                    "Jordan rBVaHFXyVSeAULcjAAJc4kkFPXQ874 for Man",
                    "11580.00",
                    "http://image.dhgate.com/0x0/f2/albu/g3/M00/C8/23/rBVaHFXyVSeAULcjAAJc4kkFPXQ874.jpg",
                    "1",
                    "42",
                    arrayList4,
                    arrayQtyList
                    ));

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setAdapter() {
        try
        {
            if (arryCartList != null && arryCartList.size() > 0)
            {
                mterialProgress.setVisibility(View.GONE);
                llMain.setVisibility(View.VISIBLE);
                rlNoData.setVisibility(View.GONE);
                //
                String size = "Total cart items <b><font color='#EB3573'>" + arryCartList.size() + "</font></b>";
                tvCartTotalItems.setText(Html.fromHtml(size));
                // set adapter
                adapter = new CartListAdapter(this, arryCartList);
                recyclerView.setAdapter(adapter);

                //
                float fltTotal = 0;
                float fltGST = 568.0f;
                float fltShipping = 150.0f;
                float fltDiscount = 665.0f;
                for (int i = 0 ; i< arryCartList.size() ; i++)
                {
                    float temp_price = Float.parseFloat(arryCartList.get(i).cart_item_price);
                    fltTotal += temp_price;
                    App.showLog(TAG + "==total==" + fltTotal);
                }
                //
                float fltPayableAmount = ((fltTotal + fltGST + fltShipping) - fltDiscount);
                tvTotal.setText(App._RS + " " + fltTotal);
                tvGST.setText(App._RS + " " + fltGST);
                tvShippingCharge.setText(App._RS + " " + fltShipping);
                tvDiscount.setText(App._RS + " " + fltDiscount);
                tvTotalPayableAmount.setText(App._RS + " " + fltPayableAmount);
            }
            else
            {
                mterialProgress.setVisibility(View.GONE);
                llMain.setVisibility(View.GONE);
                rlNoData.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {e.printStackTrace();}

    }
    

    public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.VersionViewHolder> {
        ArrayList<CartListModel> mArrayList;
        Context mContext;
        private static final int UNSELECTED = -1;
        private int selectedItem = UNSELECTED;


        public CartListAdapter(Context context, ArrayList<CartListModel> arrayList) {
            mArrayList = arrayList;
            mContext = context;
        }

        @Override
        public CartListAdapter.VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_cart_item,
                    viewGroup, false);
            CartListAdapter.VersionViewHolder viewHolder = new CartListAdapter.VersionViewHolder(view);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final CartListAdapter.VersionViewHolder versionViewHolder, final int position) {
            try
            {
                if (mArrayList != null && mArrayList.size() > 0)
                {
                    final CartListModel model = mArrayList.get(position);

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
                        String size = "Size <b><font color='#EB3573'>" + model.cart_item_size + "</font></b>";
                        versionViewHolder.tvItemSize.setText(Html.fromHtml(size));
                    }
                    //
                    if (model.cart_item_qty != null && model.cart_item_qty.length() > 0)
                    {
                        String qty = "Qty <b><font color='#EB3573'>" + model.cart_item_qty + "</font></b>";
                        versionViewHolder.tvItemQty.setText(Html.fromHtml(qty));
                    }
                    //
                    if (model.cart_item_image != null && model.cart_item_image.length() > 0)
                    {
                        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(model.cart_item_image))
                                .setResizeOptions(new ResizeOptions(SCREEN_WIDTH, 130))
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


                    //
                    if (model.sizeList != null && model.sizeList.size() > 0)
                    {

                        String[] mSizeStrArry = new String[model.sizeList.size()];
                        for (int i = 0; i < model.sizeList.size(); i++) {
                            mSizeStrArry[i] = model.sizeList.get(i).toString();
                        }
                        versionViewHolder.spinnerSize.setItems(mSizeStrArry);


                        for (int k = 0 ; k < model.sizeList.size() ; k++)
                        {
                            if (model.cart_item_size.equalsIgnoreCase(model.sizeList.get(k).size_nm))
                            {
                                versionViewHolder.spinnerSize.setSelectedIndex(k);
                            }
                        }

                        versionViewHolder.spinnerSize.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>()
                        {
                            @Override
                            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                                App.showLog("==spinnerSize==Clicked==" + item);

                                String size = "Size <b><font color='#EB3573'>" + item + "</font></b>";
                                versionViewHolder.tvItemSize.setText(Html.fromHtml(size));
                            }
                        });

                        versionViewHolder.spinnerSize.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener()
                        {
                            @Override
                            public void onNothingSelected(MaterialSpinner spinner) {
                                App.showLog("==spinnerSize==Nothing selected==");
                            }
                        });
                    }

                    //
                    if (model.qtyList != null && model.qtyList.size() > 0)
                    {
                        String[] mQtyStrArry = new String[model.qtyList.size()];
                        for (int i = 0; i < model.qtyList.size(); i++) {
                            mQtyStrArry[i] = model.qtyList.get(i).toString();
                        }
                        versionViewHolder.spinnerQty.setItems(mQtyStrArry);

                        for (int k = 0 ; k < model.qtyList.size() ; k++)
                        {
                            if (model.cart_item_qty.equalsIgnoreCase(model.qtyList.get(k).qty_nm))
                            {
                                versionViewHolder.spinnerQty.setSelectedIndex(k);
                            }
                        }

                        versionViewHolder.spinnerQty.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>()
                        {
                            @Override
                            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                                App.showLog("==spinnerQty==Clicked==" + item);

                                String qty = "Qty <b><font color='#EB3573'>" + model.qtyList.get(position).qty_nm + "</font></b>";
                                versionViewHolder.tvItemQty.setText(Html.fromHtml(qty));
                            }
                        });

                        versionViewHolder.spinnerQty.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener()
                        {
                            @Override
                            public void onNothingSelected(MaterialSpinner spinner) {
                                App.showLog("==spinnerQty==Nothing selected==");
                            }
                        });
                    }

                    String qty = versionViewHolder.tvItemQty.getText().toString();
                    qty = qty.replace("Qty ", "");
                    App.showLog("===sendQTY=" + qty);

                    String size = versionViewHolder.tvItemSize.getText().toString();
                    size = size.replace("Size ", "");
                    App.showLog("===sendSize=" + size);

                    //
                    arryCheckoutList.add(new CartListModel(
                            model.cart_item_id,
                            model.cart_item_name,
                            model.cart_item_price,
                            model.cart_item_image,
                            qty,
                            size,
                            null,
                            null
                    ));

                    //
                    versionViewHolder.tvTagEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (versionViewHolder != null) {
                                versionViewHolder.tvTagEdit.setSelected(false);
                                versionViewHolder.tvTagEdit.setText("Edit");
                                versionViewHolder.expandableLayout.collapse();

                                String qty = versionViewHolder.tvItemQty.getText().toString();
                                qty = qty.replace("Qty ", "");
                                App.showLog("===sendQTY=" + qty);

                                String size = versionViewHolder.tvItemSize.getText().toString();
                                size = size.replace("Size ", "");
                                App.showLog("===sendSize=" + size);

                                arryCheckoutList.get(position).cart_item_qty = qty;
                                arryCheckoutList.get(position).cart_item_size = size;

                                //
//                                arryCheckoutList.get(position).cart_item_qty = versionViewHolder.tvItemQty.getText().toString();
//                                arryCheckoutList.get(position).cart_item_size = versionViewHolder.tvItemSize.getText().toString();

                            }

                            if (position == selectedItem) {
                                selectedItem = UNSELECTED;
                            } else {
                                versionViewHolder.tvTagEdit.setSelected(true);
                                versionViewHolder.tvTagEdit.setText("Done");
                                versionViewHolder.expandableLayout.expand();
                                selectedItem = position;
                            }
                        }
                    });

                }
            }
            catch (Exception e) {e.printStackTrace();}
        }

        @Override
        public int getItemCount() {
            return mArrayList.size();
        }


        public  class VersionViewHolder extends RecyclerView.ViewHolder implements ExpandableLayout.OnExpansionUpdateListener
        {
            TextView tvItemName, tvItemPrice, tvItemSize, tvItemQty, tvTagEdit, tvTagMoveToFavourites, tvTagRemove;
            SimpleDraweeView ivProductImg;
            ExpandableLayout expandableLayout;
            MaterialSpinner spinnerSize, spinnerQty;

            public VersionViewHolder(View itemView) {
                super(itemView);
                // //
                tvItemName = (TextView) itemView.findViewById(R.id.tvItemName);
                tvItemPrice = (TextView) itemView.findViewById(R.id.tvItemPrice);
                tvItemSize = (TextView) itemView.findViewById(R.id.tvItemSize);
                tvItemQty = (TextView) itemView.findViewById(R.id.tvItemQty);
                tvTagEdit = (TextView) itemView.findViewById(R.id.tvTagEdit);
                tvTagMoveToFavourites = (TextView) itemView.findViewById(R.id.tvTagMoveToFavourites);
                tvTagRemove = (TextView) itemView.findViewById(R.id.tvTagRemove);

                spinnerSize = (MaterialSpinner) itemView.findViewById(R.id.spinnerSize);
                spinnerQty = (MaterialSpinner) itemView.findViewById(R.id.spinnerQty);

                expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandableLayout);
                expandableLayout.setOnExpansionUpdateListener(this);

                ivProductImg = (SimpleDraweeView) itemView.findViewById(R.id.ivProductImg);

                /*
                * Set Fonts
                * */
                tvItemName.setTypeface(App.getTribuchet_MS());
                tvItemPrice.setTypeface(App.getTribuchet_MS());
                tvItemSize.setTypeface(App.getTribuchet_MS());
                tvItemQty.setTypeface(App.getTribuchet_MS());
                tvTagEdit.setTypeface(App.getTribuchet_MS());
                tvTagMoveToFavourites.setTypeface(App.getTribuchet_MS());
                tvTagRemove.setTypeface(App.getTribuchet_MS());
            }

            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                recyclerView.smoothScrollToPosition(getAdapterPosition());
            }
        }
    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        App.myFinishActivity(ActCartList.this);
    }
}
