package com.ecommapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.api.model.ProfileMainModel;
import com.demoecommereceapp.R;
import com.util.CircularImageView;
import com.util.PreferencesKeys;
import com.util.custom_gridview.GridRecyclerView;
import com.util.custom_gridview.ItemOffsetDecoration;

import java.util.ArrayList;

/**
 * Created by dhaval.mehta on 05-Oct-2017.
 */

public class ActProfileMainSecond extends BaseActivity {

    String TAG = "==ActProfileMainSecondSecond==";

    //
    CircularImageView ivProfileImage;
    ImageView ivBackSub;
    TextView tvUserName;
    GridRecyclerView gridRecyclerView;
    RelativeLayout rlBanner;

    ArrayList<ProfileMainModel> arryProfileList;
    ProfileMainListAdapter adapter;

    int SCREEN_HEIGHT, SCREEN_WIDTH;
    float BANNER_SHOW_PERCENTAGE = 0.40f; // 40%

    String strFrom = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            //
            ViewGroup.inflate(this, R.layout.act_profile_main_second, llContainerSub);
            App.showLogTAG(TAG);

            getIntenetData();
            initialisation();
            dynamicSetSize();
            setFonts();
            setClickEvents();
            setProfileData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getIntenetData() {
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
            }

        } catch (Exception e) {e.printStackTrace();}
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.sharePrefrences.setPref(PreferencesKeys.strMenuSelectedId, "6");
    }



    private void initialisation() {
        try
        {
            App.sharePrefrences.setPref(PreferencesKeys.strMenuSelectedId, "6");

            //
            rlBaseMainHeader.setVisibility(View.GONE);

            rlBanner = (RelativeLayout) findViewById(R.id.rlBanner);
            ivProfileImage = (CircularImageView) findViewById(R.id.ivProfileImage);
            ivBackSub = (ImageView) findViewById(R.id.ivBackSub);
            tvUserName = (TextView) findViewById(R.id.tvUserName);
            gridRecyclerView = (GridRecyclerView) findViewById(R.id.gridRecyclerView);
            GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 3);
            gridRecyclerView.setLayoutManager(linearLayoutManager);
            gridRecyclerView.addItemDecoration(new ItemOffsetDecoration(10));

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

            RelativeLayout.LayoutParams paramsRLBanner = new RelativeLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            paramsRLBanner.height = (int) (SCREEN_HEIGHT * BANNER_SHOW_PERCENTAGE);
            rlBanner.setLayoutParams(paramsRLBanner);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setFonts() {
        try
        {
            tvUserName.setTypeface(App.getTribuchet_MS());
        } catch (Exception e) {e.printStackTrace();}
    }


    private void setClickEvents() {
        try
        {
            //
            ivBackSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    App.showLog("==onBackPressed==");
                    onBackPressed();
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

            adapter = new ProfileMainListAdapter(ActProfileMainSecond.this, arryProfileList);
            gridRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            if (strFrom != null && strFrom.length() > 0 &&
                    strFrom.equalsIgnoreCase("BaseActivity"))
            {
                GridLayoutAnimationController controller = (GridLayoutAnimationController)
                        AnimationUtils.loadLayoutAnimation(ActProfileMainSecond.this,
                                R.anim.grid_layout_animation_from_bottom);
                gridRecyclerView.setLayoutAnimation(controller);
                gridRecyclerView.scheduleLayoutAnimation();
            }

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
                final ProfileMainModel model = mArrayList.get(position);
                versionViewHolder.tvProfileTitle.setTypeface(App.getTribuchet_MS());


                //
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) versionViewHolder.llMain.getLayoutParams();
                int width = (SCREEN_WIDTH/3);
                params.height = width;
                params.width = width;
                versionViewHolder.llMain.setLayoutParams(params);

                String strImage = "";
                if (model.profile_title != null && model.profile_title.length() > 0) {
                    versionViewHolder.tvProfileTitle.setText(model.profile_title);
                }

                versionViewHolder.ivProflileIcon.setImageResource(App.PROFILE_MAIN_ICONS[position]);


                versionViewHolder.llMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (model.profile_title.equalsIgnoreCase(App.PROFILE_MAIN_CHANGE_PASSWORD))
                        {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(ActProfileMainSecond.this, ActChangePassword.class);
                                    intent.putExtra(App.ITAG_FROM, "ActProfileMainSecond");
                                    startActivity(intent);
                                    animStartActivity();
                                }
                            }, 180);
                        }
                        else if (model.profile_title.equalsIgnoreCase(App.PROFILE_MAIN_LOGOUT))
                        {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    showLogoutDialog();
                                }
                            }, 180);
                        }
                        else if (model.profile_title.equalsIgnoreCase(App.PROFILE_MAIN_NOTIFICATIONS))
                        {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(ActProfileMainSecond.this, ActNotification.class);
                                    intent.putExtra(App.ITAG_FROM, "ActProfileMainSecond");
                                    startActivity(intent);
                                    animStartActivity();
                                }
                            }, 180);
                        }
                        else if (model.profile_title.equalsIgnoreCase(App.PROFILE_MAIN_MY_ORDERS))
                        {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(ActProfileMainSecond.this, ActMyOrderList.class);
                                    intent.putExtra(App.ITAG_FROM, "ActProfileMainSecond");
                                    startActivity(intent);
                                    animStartActivity();
                                }
                            }, 180);
                        }
                        else if (model.profile_title.equalsIgnoreCase(App.PROFILE_MAIN_MY_ADDRESS))
                        {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(ActProfileMainSecond.this, ActMyAddress.class);
                                    intent.putExtra(App.ITAG_FROM, "ActProfileMainSecond");
                                    startActivity(intent);
                                    animStartActivity();
                                }
                            }, 180);
                        }
                    }
                });

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
        App.myFinishActivity(ActProfileMainSecond.this);
    }
}
