package com.ecommapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.api.model.MyOrderListModel;
import com.demoecommereceapp.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.util.AppFlags;
import com.util.MaterialProgressBar;
import com.util.expandable_layout.ExpandableLayout;

import java.util.ArrayList;

/**
 * Created by dhaval.mehta on 10-Oct-2017.
 */

public class ActMyOrderList extends BaseActivity {

    String TAG = "==ActMyOrderList==";

    //
    RelativeLayout rlNoData;
    TextView tvNoData;
    //
    RecyclerView recyclerView;
    MaterialProgressBar mterialProgress;

    ArrayList<MyOrderListModel> arryMyOrderList;
    MyOrderListAdapter adapter;

    int SCREEN_HEIGHT = 0, SCREEN_WIDTH = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            //
            ViewGroup.inflate(this, R.layout.act_my_order_list, llContainerSub);
            App.showLogTAG(TAG);

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            SCREEN_HEIGHT = size.y;
            SCREEN_WIDTH = size.x;

            initialisation();
            setFonts();
            setClickEvents();
            setMyOrderListData();

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
            tvTitle.setText(App.PROFILE_MAIN_MY_ORDERS);

            //
            rlNoData = (RelativeLayout) findViewById(R.id.rlNoData);
            rlNoData.setVisibility(View.GONE);
            tvNoData = (TextView) findViewById(R.id.tvNoData);

            //
            mterialProgress = (MaterialProgressBar) findViewById(R.id.mterialProgress);
            mterialProgress.setVisibility(View.VISIBLE);

            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setVisibility(View.GONE);

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setFonts() {
        try
        {
            tvNoData.setTypeface(App.getTribuchet_MS());

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


    private void setMyOrderListData() {
        try
        {
            arryMyOrderList = new ArrayList<>();

            arryMyOrderList.add(new MyOrderListModel(
                    "Jenny Methew",
                    "SHP0005893-A",
                    "Pending",
                    "2017-10-15",
                    "Sony XBA SD-l109844187 Bass",
                    "2750.00",
                    "https://cdn.overcart.com/media/catalog/product/s/o/sony-xba-a1ap-earphone-sdl109844187-1-19f46.jpg"
            ));

            arryMyOrderList.add(new MyOrderListModel(
                    "Jenny Methew",
                    "SHP0006987-CV",
                    "Delivered",
                    "2017-10-06",
                    "Phillips 41JCOaSw7b PH-045 1200wt",
                    "4500.00",
                    "https://images-na.ssl-images-amazon.com/images/I/41JCOaSw7bL._SY450_.jpg"
            ));

            arryMyOrderList.add(new MyOrderListModel(
                    "Jenny Methew",
                    "SHP0000012-L",
                    "Shipped - On way",
                    "2017-10-11",
                    "Whirlpool WA-46698-JK-556 356lt",
                    "33450.00",
                    "https://image3.pricedekho.com/p/45/1/21/537221/1039885-whirlpool-230-i-magic-5g-single-door-refrigerator-wine-orchid-picture-large.jpg"
            ));

            arryMyOrderList.add(new MyOrderListModel(
                    "Roxy Morohy",
                    "SHP000789566-LP",
                    "Delivered",
                    "2017-09-09",
                    "Orpat Iron Press Machine OP-556 1000 wt",
                    "1250.00",
                    "https://rukminim1.flixcart.com/image/704/704/iron/c/g/u/orpat-687-cl-dx-687-cl-dx-original-imad43qympz7j7sr.jpeg?q=70"
            ));

            arryMyOrderList.add(new MyOrderListModel(
                    "Samuel Addems",
                    "SHP02796566-WM",
                    "Delivered",
                    "2017-08-23",
                    "Cinthol Men Deo - Cool bast (pack of 3)",
                    "450.00",
                    "http://ecx.images-amazon.com/images/I/41EfCXwTrJL._SY355_.jpg"
            ));

            arryMyOrderList.add(new MyOrderListModel(
                    "John Rambo",
                    "SHP02796566-LA",
                    "Delivered",
                    "2017-05-14",
                    "Gatsby Hair Sprey",
                    "189.00",
                    "https://www.mens-hairstylists.com/wp-content/uploads/2016/11/how-to-use-hair-spray-for-men.jpg"
            ));

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setAdapter() {
        try
        {
            if (arryMyOrderList != null && arryMyOrderList.size() > 0)
            {
                mterialProgress.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                rlNoData.setVisibility(View.GONE);
                // set adapter
                adapter = new MyOrderListAdapter(ActMyOrderList.this, arryMyOrderList);
                recyclerView.setAdapter(adapter);
            }
            else
            {
                mterialProgress.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                rlNoData.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {e.printStackTrace();}
    }


    public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.VersionViewHolder> {
        ArrayList<MyOrderListModel> mArrayList;
        Context mContext;
        private static final int UNSELECTED = -1;
        private int selectedItem = UNSELECTED;


        public MyOrderListAdapter(Context context, ArrayList<MyOrderListModel> arrayList) {
            mArrayList = arrayList;
            mContext = context;
        }

        @Override
        public MyOrderListAdapter.VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_my_order_list_item,
                    viewGroup, false);
            MyOrderListAdapter.VersionViewHolder viewHolder = new MyOrderListAdapter.VersionViewHolder(view);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final MyOrderListAdapter.VersionViewHolder versionViewHolder, final int position) {
            try
            {
                if (mArrayList != null && mArrayList.size() > 0)
                {
                    final MyOrderListModel model = mArrayList.get(position);
                    //
                    if (model.order_item_name != null && model.order_item_name.length() > 0)
                    {
                        versionViewHolder.tvOrderItemName.setText("Order no. " + model.order_number);
                    }
                    //
                    if (model.order_item_name != null && model.order_item_name.length() > 0)
                    {
                        // versionViewHolder.tvOrderItemName.setText(model.order_item_name);
                        versionViewHolder.tvPrItemName.setText(model.order_item_name);
                    }
                    //
                    if (model.order_status != null && model.order_status.length() > 0)
                    {
                        versionViewHolder.tvOrderStatusMain.setText(model.order_status);

                        if (model.order_status.contains("Del")){
                            versionViewHolder.ivOrderStatusMain.setImageResource(R.drawable.green_round);
                        }
                        else if (model.order_status.contains("Pen")){
                            versionViewHolder.ivOrderStatusMain.setImageResource(R.drawable.red_round);
                        }
                        else if (model.order_status.contains("Shi")){
                            versionViewHolder.ivOrderStatusMain.setImageResource(R.drawable.orange_round);
                        }
                    }
                    //
                    if (model.order_number != null && model.order_number.length() > 0)
                    {
                        versionViewHolder.tvPrOrderNo.setText(model.order_number);
                    }
                    //
                    if (model.order_item_price != null && model.order_item_price.length() > 0)
                    {
                        versionViewHolder.tvPrPrice.setText(App._RS + " " + model.order_item_price);
                    }
                    //
                    if (model.order_ship_to != null && model.order_ship_to.length() > 0)
                    {
                        versionViewHolder.tvPrOrderShipTo.setText(model.order_ship_to);
                    }
                    //
                    if (model.order_item_image != null && model.order_item_image.length() > 0)
                    {
                        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(model.order_item_image))
                                .setResizeOptions(new ResizeOptions(SCREEN_WIDTH, 140))
                                .build();
                        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                                .setImageRequest(imageRequest)
                                .setOldController(versionViewHolder.ivPrProductImg.getController())
                                .setAutoPlayAnimations(true)
                                .build();
                        versionViewHolder.ivPrProductImg.setController(draweeController);
//                            Uri imageUri = Uri.parse(model.noti_image);
//                            versionViewHolder.ivInfoImage.setImageURI(imageUri);
                    }

                    //
                    versionViewHolder.tvTagOrderDetail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (versionViewHolder != null) {
                                versionViewHolder.tvTagOrderDetail.setSelected(false);
                                versionViewHolder.expandableLayout.collapse();
                            }

                            if (position == selectedItem) {
                                selectedItem = UNSELECTED;
                            } else {
                                versionViewHolder.tvTagOrderDetail.setSelected(true);
                                versionViewHolder.expandableLayout.expand();
                                selectedItem = position;
                            }
                        }
                    });


                    //
                    versionViewHolder.llRightArrow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent iv = new Intent(ActMyOrderList.this, ActMyOrderDetail.class);
                            iv.putExtra(App.ITAG_FROM, "ActMyOrderList");
                            iv.putExtra(AppFlags.tagOrderShippedTo, model.order_ship_to);
                            iv.putExtra(AppFlags.tagOrderStatus, model.order_status);
                            iv.putExtra(AppFlags.tagOrderNo, model.order_number);
                            App.myStartActivity(ActMyOrderList.this, iv);
                        }
                    });

                    //
                    versionViewHolder.llSubMain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent iv = new Intent(ActMyOrderList.this, ActMyOrderDetail.class);
                            iv.putExtra(App.ITAG_FROM, "ActMyOrderList");
                            iv.putExtra(AppFlags.tagOrderShippedTo, model.order_ship_to);
                            iv.putExtra(AppFlags.tagOrderStatus, model.order_status);
                            iv.putExtra(AppFlags.tagOrderNo, model.order_number);
                            App.myStartActivity(ActMyOrderList.this, iv);
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


        class VersionViewHolder extends RecyclerView.ViewHolder implements ExpandableLayout.OnExpansionUpdateListener
        {
            LinearLayout llRightArrow, llSubMain;
            TextView tvOrderItemName, tvTagOrderStatusMain, tvOrderStatusMain,
                    tvTagOrderDetail,
                    tvPrTagOrderNo, tvPrOrderNo, tvPrTagItemName, tvPrItemName, tvPrTagPrice, tvPrPrice,
                    tvPrTagOrderShipTo, tvPrOrderShipTo;
            ImageView ivOrderStatusMain, ivDropDownIcon/*, ivStatus*/;
            ExpandableLayout expandableLayout;
            SimpleDraweeView ivPrProductImg;



            public VersionViewHolder(View itemView) {
                super(itemView);

                llRightArrow = (LinearLayout) itemView.findViewById(R.id.llRightArrow);
                llSubMain = (LinearLayout) itemView.findViewById(R.id.llSubMain);

                tvOrderItemName = (TextView) itemView.findViewById(R.id.tvOrderItemName);
                tvTagOrderStatusMain = (TextView) itemView.findViewById(R.id.tvTagOrderStatusMain);
                tvOrderStatusMain = (TextView) itemView.findViewById(R.id.tvOrderStatusMain);
                tvTagOrderDetail = (TextView) itemView.findViewById(R.id.tvTagOrderDetail);
                tvPrTagOrderNo = (TextView) itemView.findViewById(R.id.tvPrTagOrderNo);
                tvPrOrderNo = (TextView) itemView.findViewById(R.id.tvPrOrderNo);
                tvPrTagItemName = (TextView) itemView.findViewById(R.id.tvPrTagItemName);
                tvPrItemName = (TextView) itemView.findViewById(R.id.tvPrItemName);
                tvPrTagPrice = (TextView) itemView.findViewById(R.id.tvPrTagPrice);
                tvPrPrice = (TextView) itemView.findViewById(R.id.tvPrPrice);
                tvPrTagOrderShipTo = (TextView) itemView.findViewById(R.id.tvPrTagOrderShipTo);
                tvPrOrderShipTo = (TextView) itemView.findViewById(R.id.tvPrOrderShipTo);

                ivOrderStatusMain = (ImageView) itemView.findViewById(R.id.ivOrderStatusMain);
                ivDropDownIcon = (ImageView) itemView.findViewById(R.id.ivDropDownIcon);
                expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandableLayout);
                expandableLayout.setOnExpansionUpdateListener(this);

                ivPrProductImg = (SimpleDraweeView) itemView.findViewById(R.id.ivPrProductImg);

                // //
                tvOrderItemName.setTypeface(App.getTribuchet_MS());
                tvTagOrderStatusMain.setTypeface(App.getTribuchet_MS());
                tvOrderStatusMain.setTypeface(App.getTribuchet_MS());
                tvTagOrderDetail.setTypeface(App.getTribuchet_MS());
                tvPrTagOrderNo.setTypeface(App.getTribuchet_MS());
                tvPrOrderNo.setTypeface(App.getTribuchet_MS());
                tvPrTagItemName.setTypeface(App.getTribuchet_MS());
                tvPrItemName.setTypeface(App.getTribuchet_MS());
                tvPrTagPrice.setTypeface(App.getTribuchet_MS());
                tvPrPrice.setTypeface(App.getTribuchet_MS());
                tvPrTagOrderShipTo.setTypeface(App.getTribuchet_MS());
                tvPrOrderShipTo.setTypeface(App.getTribuchet_MS());
            }

            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                recyclerView.smoothScrollToPosition(getAdapterPosition());
                ivDropDownIcon.setRotation(expansionFraction * 180);
            }
        }
    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        App.myFinishActivity(ActMyOrderList.this);
    }
}
