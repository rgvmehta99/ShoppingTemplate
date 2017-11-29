package com.ecommapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;

import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.api.model.ProfileMainModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.demoecommereceapp.R;
import com.squareup.picasso.Picasso;
import com.util.CircularImageView;
import com.util.PreferencesKeys;
import com.util.custom_gridview.GridRecyclerView;
import com.util.custom_gridview.ItemOffsetDecoration;

import java.util.ArrayList;

/**
 * Created by dhaval.mehta on 27-Sep-2017.
 */

public class ActProfileMain extends BaseActivity {

    String TAG = "==ActProfileMain==";
    CollapsingToolbarLayout collapseLayout;
    AppBarLayout appBarLayout;
    Toolbar toolBar;

    CircularImageView ivCircilarProfileImage;
    GridRecyclerView recycler_view;

    int SCREEN_HEIGHT, SCREEN_WIDTH;

    ArrayList<ProfileMainModel> arryProfileList;
    ProfileMainListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            //
            ViewGroup.inflate(this, R.layout.act_profile_main, llContainerSub);
            App.showLogTAG(TAG);

            initialisation();
            setFonts();
            setClickEvents();
            // setProfileData();

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            SCREEN_HEIGHT = size.y;
            SCREEN_WIDTH = size.x;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initialisation() {
        try
        {
            App.sharePrefrences.setPref(PreferencesKeys.strMenuSelectedId, "6");

            //
            rlBaseMainHeader.setVisibility(View.GONE);

            collapseLayout = (CollapsingToolbarLayout) findViewById(R.id.collapseLayout);
            collapseLayout.setTitle("Roxy Morphy");
            appBarLayout = (AppBarLayout) findViewById(R.id.appbarLayout);
            toolBar = (Toolbar) findViewById(R.id.toolBar);

            recycler_view = (GridRecyclerView) findViewById(R.id.recycler_view);
            GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 3);
            recycler_view.setLayoutManager(linearLayoutManager);
            recycler_view.addItemDecoration(new ItemOffsetDecoration(10));

            ivCircilarProfileImage = (CircularImageView) findViewById(R.id.ivCircilarProfileImage);
            Glide.with(ActProfileMain.this)
                    .load(R.drawable.dummy_user_model)
                    .asBitmap()
                    .placeholder(R.drawable.placeholder)
                    .into(new SimpleTarget<Bitmap>(100,100) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            // setBackgroundImage(resource);
                            ivCircilarProfileImage.setImageBitmap(resource);

                        }
                    });
        } catch (Exception e) {e.printStackTrace();}
    }


    private void setFonts() {
        try
        {
            collapseLayout.setExpandedTitleTypeface(App.getTribuchet_MS());
            collapseLayout.setCollapsedTitleTypeface(App.getTribuchet_MS());

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setClickEvents() {
        try
        {
            //
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                boolean isShow = false;
                int scrollRange = -1;

                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout.getTotalScrollRange();
                    }
                    if (scrollRange + verticalOffset == 0)
                    {
                        // During Collapse State
                        collapseLayout.setTitle("Profile");
                        isShow = true;
                    }
                    else if(isShow)
                    {
                        // During Expand State
                        collapseLayout.setTitle("Roxy Morphy");//carefull there should a space between double quote otherwise it wont work
                        isShow = false;
                    }
                }
            });

            //
            toolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    rlMenu.performClick();
                }
            });

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setProfileData() {
        try
        {
            arryProfileList = new ArrayList<>();
            arryProfileList.add(new ProfileMainModel(App.PROFILE_MAIN_MY_ACCOUNTS));
            arryProfileList.add(new ProfileMainModel(App.PROFILE_MAIN_NOTIFICATIONS));
            arryProfileList.add(new ProfileMainModel(App.PROFILE_MAIN_CHANGE_PASSWORD));
            arryProfileList.add(new ProfileMainModel(App.PROFILE_MAIN_SETTINGS));
            arryProfileList.add(new ProfileMainModel(App.PROFILE_MAIN_MY_ORDERS));
            arryProfileList.add(new ProfileMainModel(App.PROFILE_MAIN_MY_ADDRESS));
            arryProfileList.add(new ProfileMainModel(App.PROFILE_MAIN_MY_WISH_LIST));
            arryProfileList.add(new ProfileMainModel(App.PROFILE_MAIN_MY_FAVOURITES));
            arryProfileList.add(new ProfileMainModel(App.PROFILE_MAIN_LOGOUT));

            adapter = new ProfileMainListAdapter(ActProfileMain.this, arryProfileList);
            recycler_view.setAdapter(adapter);
            GridLayoutAnimationController controller = (GridLayoutAnimationController)
                    AnimationUtils.loadLayoutAnimation(ActProfileMain.this,
                            R.anim.grid_layout_animation_from_bottom);
            recycler_view.setLayoutAnimation(controller);
            adapter.notifyDataSetChanged();
            recycler_view.scheduleLayoutAnimation();


        } catch (Exception e) {e.printStackTrace();}
    }


    public class ProfileMainListAdapter extends RecyclerView.Adapter<ProfileMainListAdapter.VersionViewHolder> {
        ArrayList<ProfileMainModel> mArrayList;
        Context mContext;


        public ProfileMainListAdapter(Context context, ArrayList<ProfileMainModel> arrayList) {
            mArrayList = arrayList;
            mContext = context;
        }

        @Override
        public ProfileMainListAdapter.VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_profile_main_items, viewGroup, false);
            ProfileMainListAdapter.VersionViewHolder viewHolder = new ProfileMainListAdapter.VersionViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ProfileMainListAdapter.VersionViewHolder versionViewHolder, final int position) {

            try
            {
                //
                final ProfileMainModel modelProfileMain = mArrayList.get(position);
                versionViewHolder.tvProfileTitle.setTypeface(App.getTribuchet_MS());


                //
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) versionViewHolder.llMain.getLayoutParams();
                int width = (SCREEN_WIDTH/3);
                params.height = width;
                params.width = width;
                versionViewHolder.llMain.setLayoutParams(params);

                String strImage = "";
                if (modelProfileMain.profile_title != null && modelProfileMain.profile_title.length() > 0) {
                    versionViewHolder.tvProfileTitle.setText(modelProfileMain.profile_title);
                }

                versionViewHolder.ivProflileIcon.setImageResource(App.PROFILE_MAIN_ICONS[position]);

            } catch (Exception e) {e.printStackTrace();}

        }

        @Override
        public int getItemCount() {
            return mArrayList.size();
        }


        class VersionViewHolder extends RecyclerView.ViewHolder {
            ImageView ivProflileIcon;
            TextView tvProfileTitle;
            LinearLayout llMain;

            public VersionViewHolder(View itemView) {
                super(itemView);

                llMain = (LinearLayout) itemView.findViewById(R.id.llMain);
                tvProfileTitle = (TextView) itemView.findViewById(R.id.tvProfileTitle);
                ivProflileIcon = (ImageView) itemView.findViewById(R.id.ivProflileIcon);
            }
        }
    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        App.myFinishActivity(ActProfileMain.this);
    }
}
