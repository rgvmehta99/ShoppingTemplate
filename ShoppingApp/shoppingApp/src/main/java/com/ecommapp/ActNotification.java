package com.ecommapp;

import android.content.Context;
import android.graphics.Bitmap;
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
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.api.model.NotificationListModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.demoecommereceapp.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.util.CircularImageView;
import com.util.MaterialProgressBar;

import java.util.ArrayList;

/**
 * Created by dhaval.mehta on 05-Oct-2017.
 */

public class ActNotification extends BaseActivity {

    String TAG = "==ActNotification==";

    //
    RelativeLayout rlNoData;
    TextView tvNoData;
    //
    RecyclerView recyclerView;
    MaterialProgressBar mterialProgress;

    ArrayList<NotificationListModel> arryNotiList;
    NotificationAdapter adapter;

    int SCREEN_HEIGHT = 0, SCREEN_WIDTH = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            //
            ViewGroup.inflate(this, R.layout.act_notification, llContainerSub);
            App.showLogTAG(TAG);

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            SCREEN_HEIGHT = size.y;
            SCREEN_WIDTH = size.x;

            initialisation();
            setFonts();
            setClickEvents();
            setNotificationListData();

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
            tvTitle.setText(App.PROFILE_MAIN_NOTIFICATIONS);

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


    private void setNotificationListData() {
        try
        {
            arryNotiList = new ArrayList<>();
            //
            arryNotiList.add(new NotificationListModel(
                    "1",
                    App.NOTIFICAION_TYPE_INFO,
                    "2017-10-04 15:36:55",
                    "New Diwali sale is here!",
                    "http://mytechquest.com/blog/wp-content/uploads/2013/07/Zalora-Android-Shopping-App-Enjoy-25-Percent-Discount.jpg",
                    "You can join event by participatig to new feature and discount activities."
            ));

            //
            arryNotiList.add(new NotificationListModel(
                    "2",
                    App.NOTIFICAION_TYPE_INFO,
                    "2017-10-03 22:11:55",
                    "New arrivals for MEN",
                    "http://srv-live-02.lazada.com.ph/cms/fashion-landing/men/Lazada-fashion-100914-LP-men-PH_01_B.jpg",
                    "Good News for MEN. Here is lates collection & new brand for MEN exclusively for you. Hurry!"
            ));

            //
            arryNotiList.add(new NotificationListModel(
                    "3",
                    App.NOTIFICAION_TYPE_PERSONAL,
                    "2017-10-05 10:12:45",
                    "Your Sony ED-568-BASS has been dispatched",
                    "",

                    "Sony ED-568-BASS",
                    "1345.00",
                    "http://www.reviewdelta.com/images/productimages//sony-xb-30-400x400-imadfj8zkcpjpzdt.jpeg",
                    "2017-10-07 10:00:00"
            ));

            //
            arryNotiList.add(new NotificationListModel(
                    "3",
                    App.NOTIFICAION_TYPE_INFO,
                    "2017-09-28 07:05:15",
                    "Did you know, We have reached 1 billion Happy Customer",
                    "http://www.mercury-cm.co.uk/files/image/banner-our-stories.jpg",
                    "Customer satisfaction shold be main goal for every owner. Here we go. We have reached accross 1 billions Happy Customers."
            ));

            //
            arryNotiList.add(new NotificationListModel(
                    "4",
                    App.NOTIFICAION_TYPE_PERSONAL,
                    "2017-10-05 17:12:45",
                    "Your order of LG-LD9874-28'LED TV has been placed",
                    "",

                    "LG-LD9874-28'LED TV",
                    "28,569.00",
                    "http://brain-images.cdn.dixons.com/4/9/10161494/l_10161494_002.jpg",
                    "2017-10-15 11:00:00"
            ));


            //
            arryNotiList.add(new NotificationListModel(
                    "5",
                    App.NOTIFICAION_TYPE_PERSONAL,
                    "2017-10-02 17:15:45",
                    "Your order of Philips-PH-230-1200wt delivered successfully",
                    "",

                    "Philips-PH-230-1200wt",
                    "1570.00",
                    "https://n3.sdlcdn.com/imgs/f/i/5/Philips-GC1905-21-Steam-Iron-1183862-1-dab5e.jpg",
                    "2017-10-02 17:15:00"
            ));

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setAdapter() {
        try
        {
            if (arryNotiList != null && arryNotiList.size() > 0)
            {
                mterialProgress.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                rlNoData.setVisibility(View.GONE);
                // set adapter
                adapter = new NotificationAdapter(this, arryNotiList);
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


    public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.VersionViewHolder> {
        ArrayList<NotificationListModel> mArrayList;
        Context mContext;
        int width;


        public NotificationAdapter(Context context, ArrayList<NotificationListModel> arrayList) {
            mArrayList = arrayList;
            mContext = context;
        }

        @Override
        public NotificationAdapter.VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_notification_item,
                    viewGroup, false);
            NotificationAdapter.VersionViewHolder viewHolder = new NotificationAdapter.VersionViewHolder(view);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final NotificationAdapter.VersionViewHolder versionViewHolder, final int position) {
            try
            {
                if (mArrayList != null && mArrayList.size() > 0)
                {
                    NotificationListModel model = mArrayList.get(position);

                    if (model.noti_type.equalsIgnoreCase(App.NOTIFICAION_TYPE_INFO))
                    {
                        versionViewHolder.llInfo.setVisibility(View.VISIBLE);
                        versionViewHolder.llPersonal.setVisibility(View.GONE);

                        //
                        if (model.noti_time != null && model.noti_time.length() > 0)
                        {
                            // versionViewHolder.tvInfoDate.setText(App.getddMMMyy(model.noti_time));
                            versionViewHolder.tvInfoDate.setText(App.getTimeAgo(model.noti_time));
                        }
                        //
                        if (model.noti_title != null && model.noti_title.length() > 0)
                        {
                            versionViewHolder.tvInfoTitle.setText(model.noti_title);
                        }
                        //
                        if (model.noti_desc != null && model.noti_desc.length() > 0)
                        {
                            versionViewHolder.tvInfoDesc.setText(model.noti_desc);
                        }

                        //
                        if (model.noti_image != null && model.noti_image.length() > 0)
                        {
                            ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(model.noti_image))
                                    .setResizeOptions(new ResizeOptions(SCREEN_WIDTH, 130))
                                    .build();
                            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                                    .setImageRequest(imageRequest)
                                    .setOldController(versionViewHolder.ivInfoImage.getController())
                                    .setAutoPlayAnimations(true)
                                    .build();
                            versionViewHolder.ivInfoImage.setController(draweeController);
//                            Uri imageUri = Uri.parse(model.noti_image);
//                            versionViewHolder.ivInfoImage.setImageURI(imageUri);
                        }
                    }
                    else if (model.noti_type.equalsIgnoreCase(App.NOTIFICAION_TYPE_PERSONAL))
                    {
                        versionViewHolder.llPersonal.setVisibility(View.VISIBLE);
                        versionViewHolder.llInfo.setVisibility(View.GONE);

                        //
                        if (model.noti_time != null && model.noti_time.length() > 0)
                        {
                            versionViewHolder.tvPrDate.setText(App.getTimeAgo(model.noti_time));
                        }
                        //
                        if (model.noti_title != null && model.noti_title.length() > 0)
                        {
                            versionViewHolder.tvPrTitle.setText(model.noti_title);
                        }
                        //
                        if (model.noti_product_name != null && model.noti_product_name.length() > 0)
                        {
                            versionViewHolder.tvPrItemName.setText(model.noti_product_name);
                        }
                        //
                        if (model.noti_product_price != null && model.noti_product_price.length() > 0)
                        {
                            versionViewHolder.tvPrPrice.setText(model.noti_product_price);
                        }
                        //
                        if (model.noti_product_estimate_dlvr_dt != null && model.noti_product_estimate_dlvr_dt.length() > 0)
                        {
                            versionViewHolder.tvPrEstimateDt.setText(App.getddMMMyy(model.noti_product_estimate_dlvr_dt));
                        }
                        //
                        if (model.noti_product_image != null && model.noti_product_image.length() > 0)
                        {
                            ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(model.noti_product_image))
                                    .setResizeOptions(new ResizeOptions(130, 130))
                                    .build();
                            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                                    .setImageRequest(imageRequest)
                                    .setOldController(versionViewHolder.ivPrProductImg.getController())
                                    .setAutoPlayAnimations(true)
                                    .build();
                            versionViewHolder.ivPrProductImg.setController(draweeController);
                        }
                    }
                    else
                    {
                        versionViewHolder.llInfo.setVisibility(View.GONE);
                        versionViewHolder.llPersonal.setVisibility(View.GONE);
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
            //
            // Type - INFO
            //
            LinearLayout llInfo;
            TextView tvInfoDate, tvInfoTitle, tvInfoDesc;
            // ImageView ivInfoImage;
            SimpleDraweeView ivInfoImage;

            //
            // Type - PERSONAL
            //
            LinearLayout llPersonal;
            TextView tvPrDate, tvPrTitle, tvPrTagItemName, tvPrItemName, tvPrTagPrice, tvPrPrice,
                    tvPrTagEstimateDt, tvPrEstimateDt;
            // CircularImageView ivPrProductImg;
            SimpleDraweeView ivPrProductImg;

            public VersionViewHolder(View itemView) {
                super(itemView);
                
                // // TYPE - INFO
                llInfo = (LinearLayout) itemView.findViewById(R.id.llInfo);
                tvInfoDate = (TextView) itemView.findViewById(R.id.tvInfoDate);
                tvInfoTitle = (TextView) itemView.findViewById(R.id.tvInfoTitle);
                tvInfoDesc = (TextView) itemView.findViewById(R.id.tvInfoDesc);
                // ivInfoImage = (ImageView) itemView.findViewById(R.id.ivInfoImage);
                ivInfoImage = (SimpleDraweeView) itemView.findViewById(R.id.ivInfoImage);

                /*
                * Set Fonts - TYPE - INFO
                * */
                tvInfoDate.setTypeface(App.getTribuchet_MS());
                tvInfoTitle.setTypeface(App.getTribuchet_MS());
                tvInfoDesc.setTypeface(App.getTribuchet_MS());



                // // TYPE - PERSONAL
                llPersonal = (LinearLayout) itemView.findViewById(R.id.llPersonal);
                tvPrDate = (TextView) itemView.findViewById(R.id.tvPrDate);
                tvPrTitle = (TextView) itemView.findViewById(R.id.tvPrTitle);
                tvPrTagItemName = (TextView) itemView.findViewById(R.id.tvPrTagItemName);
                tvPrItemName = (TextView) itemView.findViewById(R.id.tvPrItemName);
                tvPrTagPrice = (TextView) itemView.findViewById(R.id.tvPrTagPrice);
                tvPrPrice = (TextView) itemView.findViewById(R.id.tvPrPrice);
                tvPrTagEstimateDt = (TextView) itemView.findViewById(R.id.tvPrTagEstimateDt);
                tvPrEstimateDt = (TextView) itemView.findViewById(R.id.tvPrEstimateDt);
                ivPrProductImg = (SimpleDraweeView) itemView.findViewById(R.id.ivPrProductImg);

                /*
                * Set Fonts - TYPE - PERSONAL
                * */
                tvPrDate.setTypeface(App.getTribuchet_MS());
                tvPrTitle.setTypeface(App.getTribuchet_MS());
                tvPrTagItemName.setTypeface(App.getTribuchet_MS());
                tvPrItemName.setTypeface(App.getTribuchet_MS());
                tvPrTagPrice.setTypeface(App.getTribuchet_MS());
                tvPrPrice.setTypeface(App.getTribuchet_MS());
                tvPrTagEstimateDt.setTypeface(App.getTribuchet_MS());
                tvPrEstimateDt.setTypeface(App.getTribuchet_MS());
            }
        }
    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        App.myFinishActivity(ActNotification.this);
    }
}
