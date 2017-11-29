package com.ecommapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.api.model.SideMenuChildModel;
import com.api.model.SideMenuParentModel;
import com.demoecommereceapp.R;
import com.util.AppFlags;
import com.util.CircularImageView;
import com.util.PreferencesKeys;
import com.util.floating_expandable_group_listview.FloatingGroupExpandableListView;
import com.util.floating_expandable_group_listview.WrapperExpandableListAdapter;

import java.util.ArrayList;

public class BaseActivity extends FragmentActivity {

    String TAG = "==BaseActivity==";

    protected View headerview;
    View baseViewHeaderShadow;
    // protected ListView lvMenuList;
    FloatingGroupExpandableListView floatingExpandableGroupDrawerList;
    protected BaseMenuAdapter baseMenuAdapter;
    protected TextView tvUsernameBase, tvUseremailBase, tvReqCounter;
    protected CircularImageView ivUserPhotoBase;

    protected LinearLayout llContainerMain, llContainerSub;

    protected RelativeLayout rlBaseMainHeader, rlMenu, rlBack;

    protected TextView tvTitle;
    protected TextView tvSubTitle;

    RelativeLayout left_drawer;
    protected DrawerLayout drawer;

    protected RelativeLayout rlMenu4, rlMenu3, rlMenu2, rlMenu1;
    protected ImageView ivMenu4, ivMenu3, ivMenu2, ivMenu1;

    App app;

    ArrayList<SideMenuParentModel> arrySideMenuParentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (App.blnFullscreenActvitity == true)
        {
            App.blnFullscreenActvitity = false;
        }

        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.act_baseactivity);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            app = (App) getApplicationContext();

            setSideMenuList();
            initialization();
            setBaseClickEvents();
            setFonts();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSideMenuList() {
        try
        {
            arrySideMenuParentList = new ArrayList<>();
            //
            arrySideMenuParentList.add(new SideMenuParentModel(
                    "0",
                    App.SIDE_MENU_HOME,
                    false
            ));


            //
            ArrayList<SideMenuChildModel> arrChildListMen = new ArrayList<>();
            arrChildListMen.add(new SideMenuChildModel("1", App.SIDE_MENU_MEN + " Clothes"));
            arrChildListMen.add(new SideMenuChildModel("2", App.SIDE_MENU_MEN + " Watchs"));
            arrChildListMen.add(new SideMenuChildModel("3", App.SIDE_MENU_MEN + " Shoes"));
            arrChildListMen.add(new SideMenuChildModel("4", App.SIDE_MENU_MEN + " Nightwear"));
            arrChildListMen.add(new SideMenuChildModel("5", App.SIDE_MENU_MEN + " Latest Branded Items"));
            arrChildListMen.add(new SideMenuChildModel("6", App.SIDE_MENU_MEN + " New Collection"));
            arrChildListMen.add(new SideMenuChildModel("7", App.SIDE_MENU_MEN + " Beach Moves Outfits"));
            arrySideMenuParentList.add(new SideMenuParentModel(
                    "1",
                    App.SIDE_MENU_MEN,
                    arrChildListMen,
                    true
            ));


            //
            ArrayList<SideMenuChildModel> arrChildListWomen = new ArrayList<>();
            arrChildListWomen.add(new SideMenuChildModel("8", App.SIDE_MENU_WOMEN + " Western Outfits"));
            arrChildListWomen.add(new SideMenuChildModel("9", App.SIDE_MENU_WOMEN + " Diamond Watchs"));
            arrChildListWomen.add(new SideMenuChildModel("10", App.SIDE_MENU_WOMEN + " Shoes"));
            arrChildListWomen.add(new SideMenuChildModel("11", App.SIDE_MENU_WOMEN + " Nightwear"));
            arrChildListWomen.add(new SideMenuChildModel("12", App.SIDE_MENU_WOMEN + " Jewellery"));
            arrChildListWomen.add(new SideMenuChildModel("13", App.SIDE_MENU_WOMEN + " Cosmetics"));
            arrChildListWomen.add(new SideMenuChildModel("14", App.SIDE_MENU_WOMEN + " Beach Moves Clothes"));
            arrySideMenuParentList.add(new SideMenuParentModel(
                    "2",
                    App.SIDE_MENU_WOMEN,
                    arrChildListWomen,
                    true
            ));


            //
            ArrayList<SideMenuChildModel> arrChildListKids = new ArrayList<>();
            arrChildListKids.add(new SideMenuChildModel("15", App.SIDE_MENU_KIDS + " Uniforms"));
            arrChildListKids.add(new SideMenuChildModel("16", App.SIDE_MENU_KIDS + " Shorts"));
            arrChildListKids.add(new SideMenuChildModel("17", App.SIDE_MENU_KIDS + " Trendy"));
            arrChildListKids.add(new SideMenuChildModel("18", App.SIDE_MENU_KIDS + " Funcky Charms"));
            arrChildListKids.add(new SideMenuChildModel("19", App.SIDE_MENU_KIDS + " New Magic"));
            arrySideMenuParentList.add(new SideMenuParentModel(
                    "3",
                    App.SIDE_MENU_KIDS,
                    arrChildListKids,
                    true
            ));


            //
            ArrayList<SideMenuChildModel> arrChildListLatest = new ArrayList<>();
            arrChildListLatest.add(new SideMenuChildModel("20", "Latest Trends"));
            arrChildListLatest.add(new SideMenuChildModel("21", "Latest Outfits"));
            arrChildListLatest.add(new SideMenuChildModel("22", "Latest Offers"));
            arrySideMenuParentList.add(new SideMenuParentModel(
                    "4",
                    App.SIDE_MENU_LATEST_PRODUCTS,
                    arrChildListLatest,
                    true
            ));


            //
            ArrayList<SideMenuChildModel> arrChildListSpecial = new ArrayList<>();
            arrChildListSpecial.add(new SideMenuChildModel("23", "Special Demands"));
            arrChildListSpecial.add(new SideMenuChildModel("24", "Special Market Trend"));
            arrChildListSpecial.add(new SideMenuChildModel("25", "Special Offers"));
            arrySideMenuParentList.add(new SideMenuParentModel(
                    "5",
                    App.SIDE_MENU_SPECIAL_OFFER,
                    arrChildListSpecial,
                    true
            ));

            arrySideMenuParentList.add(new SideMenuParentModel(
                    "6",
                    App.SIDE_MENU_PROFILE,
                    false
            ));

            arrySideMenuParentList.add(new SideMenuParentModel(
                    "7",
                    App.SIDE_MENU_LOGOUT,
                    false
            ));

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setFonts() {
        tvTitle.setTypeface(App.getTribuchet_MS());
        tvUsernameBase.setTypeface(App.getTribuchet_MS());
        tvUseremailBase.setTypeface(App.getTribuchet_MS());
        tvReqCounter.setTypeface(App.getTribuchet_MS());
    }


    private void setBaseClickEvents() {
        try {
            rlMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {

                    if (drawer.isDrawerOpen(left_drawer)) {
                        drawer.closeDrawers();
                    } else {
                        drawer.openDrawer(left_drawer);
                    }
                }
            });

            rlBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    onBackPressed();
                }
            });

            //
            llContainerSub.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // TODO Auto-generated method stub
                    System.out.println("===on touch=2=");
                    if (v instanceof EditText) {
                        System.out.println("=touch no hide edittext==2=");
                    } else {
                        System.out.println("===on touch hide=2=");
                        app.hideKeyBoard(v);
                    }
                    return true;
                }
            });

            //
            ivUserPhotoBase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if (drawer.isDrawerOpen(left_drawer)) {
                        drawer.closeDrawers();
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(BaseActivity.this, ActProfileMainSecond.class);
                            intent.putExtra(App.ITAG_FROM, "BaseActivity");
                            startActivity(intent);
                            animStartActivity();
                        }
                    }, 350);
                }
            });

            //
            tvUsernameBase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (drawer.isDrawerOpen(left_drawer)) {
                        drawer.closeDrawers();
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(BaseActivity.this, ActProfileMainSecond.class);
                            intent.putExtra(App.ITAG_FROM, "BaseActivity");
                            startActivity(intent);
                            animStartActivity();
                        }
                    }, 350);
                }
            });

            //
            tvUseremailBase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (drawer.isDrawerOpen(left_drawer)) {
                        drawer.closeDrawers();
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(BaseActivity.this, ActProfileMainSecond.class);
                            intent.putExtra(App.ITAG_FROM, "BaseActivity");
                            startActivity(intent);
                            animStartActivity();
                        }
                    }, 350);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initialization() {
        try {
            // lvMenuList = (ListView) findViewById(R.id.lvMenuList);
            floatingExpandableGroupDrawerList = (FloatingGroupExpandableListView) findViewById(R.id.floatingExpandableGroupDrawerList);
            // LayoutInflater inflater = getLayoutInflater();
            // headerview = (ViewGroup) inflater.inflate(R.layout.nav_header_act_home, null, false);
            // lvMenuList.addHeaderView(headerview, null, false);

            baseViewHeaderShadow = (View) findViewById(R.id.baseViewHeaderShadow);
            rlBaseMainHeader = (RelativeLayout) findViewById(R.id.rlBaseMainHeader);
            llContainerMain = (LinearLayout) findViewById(R.id.llContainerMain);
            llContainerSub = (LinearLayout) findViewById(R.id.llContainerSub);
            rlMenu = (RelativeLayout) findViewById(R.id.rlMenu);

            rlBack = (RelativeLayout) findViewById(R.id.rlBack);
            tvTitle = (TextView) findViewById(R.id.tvTitle);
            tvSubTitle = (TextView) findViewById(R.id.tvSubTitle);

            // for the handel right side menu click
            rlMenu4 = (RelativeLayout) findViewById(R.id.rlMenu4);
            rlMenu3 = (RelativeLayout) findViewById(R.id.rlMenu3);
            rlMenu2 = (RelativeLayout) findViewById(R.id.rlMenu2);
            rlMenu1 = (RelativeLayout) findViewById(R.id.rlMenu1);

            // for the right side menu images
            ivMenu4 = (ImageView) findViewById(R.id.ivMenu4);
            ivMenu3 = (ImageView) findViewById(R.id.ivMenu3);
            ivMenu2 = (ImageView) findViewById(R.id.ivMenu2);
            ivMenu1 = (ImageView) findViewById(R.id.ivMenu1);

            tvReqCounter = (TextView) findViewById(R.id.tvReqCounter);

            left_drawer = (RelativeLayout) findViewById(R.id.left_drawer);
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.setDrawerShadow(R.drawable.elevation_drawer_bg, Gravity.LEFT);
            // drawer.setScrimColor(getResources().getColor(android.R.color.transparent));

            ivUserPhotoBase = (CircularImageView) findViewById(R.id.ivUserPhotoBase);
            tvUsernameBase = (TextView) findViewById(R.id.tvUsernameBase);
            tvUseremailBase = (TextView) findViewById(R.id.tvUseremailBase);

            initDrawer();
            setDrawerListAdapter();

            tvTitle.setText(R.string.app_name);
            setBaseMenuDrawerDataAnyActivity();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    protected void setDrawerListAdapter() {
        try{
            if (floatingExpandableGroupDrawerList != null)
            {
                BaseSideMenuAdapter adapter = new BaseSideMenuAdapter(this, arrySideMenuParentList);
                final WrapperExpandableListAdapter wrapperAdapter = new WrapperExpandableListAdapter(adapter, this);
                floatingExpandableGroupDrawerList.setAdapter(wrapperAdapter);


                floatingExpandableGroupDrawerList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView expandableListView, View view, int position, long l) {

                        if (expandableListView.isGroupExpanded(position))
                        {
                            floatingExpandableGroupDrawerList.collapseGroup(position);
                        }
                        else
                        {
                            // PARENT DETECTED
                            if (arrySideMenuParentList.get(position).isChild)
                            {
                                App.showToastShort(BaseActivity.this, "Parent detected. - " + arrySideMenuParentList.get(position).parent_name);
                                floatingExpandableGroupDrawerList.expandGroup(position);
                            }
                            // NO PARENRT DETECTED
                            else
                            {

                                if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
                                    drawer.closeDrawers();
                                }

                                // SIDE_MENU_HOME
                                if (arrySideMenuParentList.get(position).parent_id
                                        .equalsIgnoreCase("0"))
                                {
                                    if (!App.sharePrefrences.getStringPref(PreferencesKeys.strMenuSelectedId).equalsIgnoreCase("0")) {
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent(BaseActivity.this, ActDashboard.class);
                                                intent.putExtra(App.ITAG_FROM, "BaseActivity");
                                                startActivity(intent);
                                                animStartActivity();
                                            }
                                        }, 350);
                                    } else {
                                        drawer.closeDrawers();
                                    }
                                }
                                // SIDE_MENU_PROFILE
                                else if (arrySideMenuParentList.get(position).parent_id
                                        .equalsIgnoreCase("6"))
                                {
                                    if (!App.sharePrefrences.getStringPref(PreferencesKeys.strMenuSelectedId).equalsIgnoreCase("6")) {
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent(BaseActivity.this, ActProfileMainSecond.class);
                                                intent.putExtra(App.ITAG_FROM, "BaseActivity");
                                                startActivity(intent);
                                                animStartActivity();
                                            }
                                        }, 350);
                                    } else {
                                        drawer.closeDrawers();
                                    }
                                }
                                // SIDE_MENU_LOGOUT
                                else if (arrySideMenuParentList.get(position).parent_id
                                        .equalsIgnoreCase("7"))
                                {
                                    showLogoutDialog();
                                }


                                App.showToastShort(BaseActivity.this, "No Parent. - " + arrySideMenuParentList.get(position).parent_name + "--"
                                        + arrySideMenuParentList.get(position).parent_id);
                            }
                        }

                        return true;
                    }
                });
            }
        }
        catch (Exception e) {e.printStackTrace();}
    }


    public class BaseSideMenuAdapter extends BaseExpandableListAdapter {

        private final Context mContext;
        private final LayoutInflater mLayoutInflater;
        ArrayList<SideMenuParentModel> arrParent;

//        private int[] left_menu = {
//                R.drawable.home,
//                R.drawable.leftmenu_restaurants,
//                R.drawable.leftmenu_reservation,
//                R.drawable.leftmenu_promotions,
//                R.drawable.leftmenu_discount,
//                R.drawable.leftmenu_spa,
//                R.drawable.events,
//                R.drawable.location,
//                R.drawable.feedback,
//                R.drawable.chat_side,
//                R.drawable.settings,
//                R.drawable.leftmenu_contact
//        };

        public BaseSideMenuAdapter(Context context, ArrayList<SideMenuParentModel> arrParentList) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arrParent = arrParentList;
        }

        @Override
        public int getGroupCount() {
            return arrParent.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return arrParent.get(groupPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }


        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.sidemenu_parent_item, parent, false);
            }

            SideMenuParentModel parentModel = arrParent.get(groupPosition);
            // App.showLog("==BaseActivity==getGroupView=="+ parentModel.parent_name);

            final TextView tvParentCateName = (TextView) convertView.findViewById(R.id.tvParentCateName);
            tvParentCateName.setText(parentModel.parent_name);
            tvParentCateName.setTypeface(App.getTribuchet_MS());


            final ImageView ivParentSideMenuIcon = (ImageView) convertView.findViewById(R.id.ivParentSideMenuIcon);
            ivParentSideMenuIcon.setImageResource(App.SIDE_MENU_ICONS[groupPosition]);

            final ImageView ivSideMenuPlusIcon = (ImageView) convertView.findViewById(R.id.ivSideMenuPlusIcon);
            if (isExpanded) {
                ivSideMenuPlusIcon.setImageResource(R.drawable.ic_remove);
            } else {
                ivSideMenuPlusIcon.setImageResource(R.drawable.ic_add);
            }

            //
            if (parentModel.isChild) {
                ivSideMenuPlusIcon.setVisibility(View.VISIBLE);
            } else {
                ivSideMenuPlusIcon.setVisibility(View.GONE);
            }

            return convertView;
        }


        public void expand(final View v) {
            v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final int targetHeight = v.getMeasuredHeight();

            // Older versions of android (pre API 21) cancel animations for views with a height of 0.
            v.getLayoutParams().height = 1;
            v.setVisibility(View.VISIBLE);
            Animation a = new Animation()
            {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    v.getLayoutParams().height = interpolatedTime == 1
                            ? ViewGroup.LayoutParams.WRAP_CONTENT
                            : (int)(targetHeight * interpolatedTime);
                    v.requestLayout();
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            // 1dp/ms
            a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
            v.startAnimation(a);
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            if (arrParent.get(groupPosition).arryChildList != null)
                return arrParent.get(groupPosition).arryChildList.size();
            else
                return 0;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }


        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.sidemenu_child_item, parent, false);
            }

            SideMenuChildModel childModel = arrParent.get(groupPosition).arryChildList.get(childPosition);
            // App.showLog("==BaseActivity==getChildView==" + childModel.child_name);

            final RelativeLayout rlChild = (RelativeLayout) convertView.findViewById(R.id.rlChild);
            final View viewFooter = convertView.findViewById(R.id.viewFooter);

            final TextView tvChildCateName = (TextView) convertView.findViewById(R.id.tvChildCateName);
            tvChildCateName.setText(childModel.child_name);
            tvChildCateName.setTypeface(App.getTribuchet_MS());

            //
            if (isLastChild)
                viewFooter.setVisibility(View.VISIBLE);
            else
                viewFooter.setVisibility(View.GONE);

            rlChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

    }


    public void setBaseMenuDrawerDataAnyActivity() {
        try {
            if (tvUsernameBase != null && ivUserPhotoBase != null && tvUseremailBase != null) {
                if (App.sharePrefrences.getStringPref(PreferencesKeys.strUserFullName) != null &&
                        App.sharePrefrences.getStringPref(PreferencesKeys.strUserFullName).length() > 1) {
                    String strFullName = App.sharePrefrences.getStringPref(PreferencesKeys.strUserFullName);
                    tvUsernameBase.setText(strFullName);
                }

                if (App.sharePrefrences.getStringPref(PreferencesKeys.strUserEmail) != null &&
                        App.sharePrefrences.getStringPref(PreferencesKeys.strUserEmail).length() > 1) {
                    String strEmail = App.sharePrefrences.getStringPref(PreferencesKeys.strUserEmail);
                    tvUseremailBase.setText(strEmail);
                }

                if (App.sharePrefrences.getStringPref(PreferencesKeys.strUserImageurl) != null && App.sharePrefrences.getStringPref(PreferencesKeys.strUserImageurl).length() > 1) {
                    String strUserImageurl = App.sharePrefrences.getStringPref(PreferencesKeys.strUserImageurl);
//                    Picasso.with(getApplicationContext())
//                            .load(App.strBaseUploadedPicUrl + strUserImageurl)
//                            .placeholder(R.mipmap.ic_launcher)
//                            .fit().centerCrop()
//                            .into(ivUserPhotoBase);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    private void initDrawer() {
        try {
            left_drawer.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {// TODO Auto-generated method stub
                    // TODO Auto-generated method stub
                    App.showLog("==base menu act==", "===on touch==");

                    if (v instanceof EditText) {
                        App.showLog("==base menu act==", "=touch no hide edittext==2=");
                    } else {
                        App.showLog("==base menu act==", "===on touch hide=2=");
                        //   v.clearFocus();
                        app.hideSoftKeyboardMy(BaseActivity.this);
                    }
                    return true;
                }
            });


            drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    try
                    {
                        App.showLog("==onDrawerOpened===");
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }

                @Override
                public void onDrawerClosed(View drawerView) {

                    App.showLog("==onDrawerClosed===");

                    if (drawerView instanceof EditText) {
                        App.showLog("==base menu act==", "=touch no hide edittext==2=");
                    } else {
                        App.showLog("==base menu act==", "===on touch hide=2=");
                        //   v.clearFocus();
                        app.hideSoftKeyboardMy(BaseActivity.this);
                    }
                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setEnableDrawer(boolean blnEnable) {
        if (blnEnable == true) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }


    public static void overrideFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView) {
                //((TextView) v).setTypeface(typeface);
            }
        } catch (Exception e) {
        }
    }


    public ArrayList<MenuItems> getBaseMenuListData() {
        ArrayList<MenuItems> arrayListMenuItems = new ArrayList<>();

        arrayListMenuItems.add(new MenuItems(App.SIDE_MENU_HOME, R.drawable.ic_back, 0));
        arrayListMenuItems.add(new MenuItems(App.SIDE_MENU_MEN, R.drawable.ic_back, 1));
        arrayListMenuItems.add(new MenuItems(App.SIDE_MENU_WOMEN, R.drawable.ic_back, 2));
        arrayListMenuItems.add(new MenuItems(App.SIDE_MENU_KIDS, R.drawable.ic_back, 3));
        arrayListMenuItems.add(new MenuItems(App.SIDE_MENU_LATEST_PRODUCTS, R.drawable.ic_back, 4));
        arrayListMenuItems.add(new MenuItems(App.SIDE_MENU_SPECIAL_OFFER, R.drawable.ic_back, 5));
        arrayListMenuItems.add(new MenuItems(App.SIDE_MENU_PROFILE, R.drawable.ic_back, 6));
        arrayListMenuItems.add(new MenuItems(App.SIDE_MENU_LOGOUT, R.drawable.ic_back, 7));

        return arrayListMenuItems;
    }


    public class MenuItems {
        String strMenuName;
        int intImageDrawable, intMenuId;

        public MenuItems(String name, int id) {
            strMenuName = name;
            intMenuId = id;
        }

        public MenuItems(String name, int image, int id) {
            strMenuName = name;
            intImageDrawable = image;
            intMenuId = id;
        }
    }


    public class BaseMenuAdapter extends BaseAdapter {
        Context mContext;
        ArrayList<MenuItems> mArrayListMenuItems;
        LayoutInflater inflater;

        public BaseMenuAdapter(Context context, ArrayList<MenuItems> arrayListMenuItems) {
            inflater = LayoutInflater.from(context);
            mArrayListMenuItems = arrayListMenuItems;
            mContext = context;
        }

        @Override
        public int getCount() {
            return mArrayListMenuItems.size();
        }

        @Override
        public Object getItem(int i) {
            return mArrayListMenuItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return mArrayListMenuItems.get(i).intMenuId;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            View rowView = convertView;
            ViewHolder viewHolder;
            // reuse views
            if (rowView == null) {
                viewHolder = new ViewHolder();
                rowView = inflater.inflate(R.layout.raw_menulist, null);
                // configure view holder

                viewHolder.ivMenuIcon = (ImageView) rowView.findViewById(R.id.ivMenuIcon);
                viewHolder.tvMenuItem = (TextView) rowView.findViewById(R.id.tvMenuItem);
                viewHolder.rlMenuItem = (RelativeLayout) rowView.findViewById(R.id.rlMenuItem);

                viewHolder.tvMenuItem.setTypeface(App.getFontMontserrat_Alternates_Regular());

                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) rowView.getTag();
            }

            final String menu_selected_id = App.sharePrefrences.getStringPref(PreferencesKeys.strMenuSelectedId);

            if (menu_selected_id != null && menu_selected_id.length() > 0) {
                try {

                    int intMenu = 0;
                    intMenu = Integer.parseInt(menu_selected_id);

                    if (mArrayListMenuItems.get(i).intMenuId == intMenu) {
                        viewHolder.ivMenuIcon.setColorFilter(Color.argb(255, 255, 255, 255)); // White Tint
                        viewHolder.tvMenuItem.setTextColor(0xffffffff);
                    } else {
                        viewHolder.ivMenuIcon.setColorFilter(Color.argb(255, 255, 219, 000)); // Yellow Tint
                        viewHolder.tvMenuItem.setTextColor(0x40ffffff);

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                App.sharePrefrences.setPref(PreferencesKeys.strMenuSelectedId, "0");
            }

            viewHolder.ivMenuIcon.setImageResource(mArrayListMenuItems.get(i).intImageDrawable);
            viewHolder.tvMenuItem.setText(mArrayListMenuItems.get(i).strMenuName);
            viewHolder.rlMenuItem.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NewApi")
                @Override
                public void onClick(View view) {

                    drawer.closeDrawer(GravityCompat.START);

                    App.sharePrefrences.setPref(PreferencesKeys.strMenuSelectedId, "" + mArrayListMenuItems.get(i).intMenuId);
                    App.showLog("===selected menu id===pref==" + menu_selected_id);

                     if (mArrayListMenuItems.get(i).intMenuId == 0) { // Dashboard
                         if (!menu_selected_id.equalsIgnoreCase("0")) {
                             new Handler().postDelayed(new Runnable() {
                                 @Override
                                 public void run() {
//                                     Intent intent = new Intent(BaseActivity.this, ActDashboard.class);
//                                     intent.putExtra(App.ITAG_FROM, "BaseActivity");
//                                     intent.putExtra(App.ITAG_DATA, "0");
//                                     startActivity(intent);
//                                     animStartActivity();
                                 }
                             }, 280);
                         }
                     }
                        else if (mArrayListMenuItems.get(i).intMenuId == 1) {
                        if (!menu_selected_id.equalsIgnoreCase("1")) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                                    Intent intent = new Intent(BaseActivity.this, ActNotification.class);
//                                    intent.putExtra(App.ITAG_FROM, "BaseActivity");
//                                    startActivity(intent);
//                                    animStartActivity();
                                }
                            }, 280);
                        } else {
                            drawer.closeDrawers();
                        }
                    } else if (mArrayListMenuItems.get(i).intMenuId == 2) {
                        if (!menu_selected_id.equalsIgnoreCase("2")) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                                    Intent intent = new Intent(BaseActivity.this, ActMyTrip.class);
//                                    intent.putExtra(App.ITAG_FROM, "BaseActivity");
//                                    startActivity(intent);
//                                    animStartActivity();
                                }
                            }, 280);
                        } else {
                            drawer.closeDrawers();
                        }
                    } else if (mArrayListMenuItems.get(i).intMenuId == 3) {
                        if (!menu_selected_id.equalsIgnoreCase("3")) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                                    Intent intent = new Intent(BaseActivity.this, ActMyEarnings.class);
//                                    intent.putExtra(App.ITAG_FROM, "BaseActivity");
//                                    startActivity(intent);
//                                    animStartActivity();
                                }
                            }, 280);
                        } else {
                            drawer.closeDrawers();
                        }
                    }else if (mArrayListMenuItems.get(i).intMenuId == 4) {
                        if (!menu_selected_id.equalsIgnoreCase("4")) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                                    Intent intent = new Intent(BaseActivity.this, ActMyLoyaltyPoints.class);
//                                    intent.putExtra(App.ITAG_FROM, "BaseActivity");
//                                    startActivity(intent);
//                                    animStartActivity();
                                }
                            }, 280);
                        } else {
                            drawer.closeDrawers();
                        }
                    }  else if (mArrayListMenuItems.get(i).intMenuId == 5) {
                        if (!menu_selected_id.equalsIgnoreCase("5")) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                                    Intent intent = new Intent(BaseActivity.this, ActSettings.class);
//                                    intent.putExtra(App.ITAG_FROM, "BaseActivity");
//                                    startActivity(intent);
//                                    animStartActivity();
                                }
                            }, 280);
                        } else {
                            drawer.closeDrawers();
                        }
                    } else if (mArrayListMenuItems.get(i).intMenuId == 6) {
                        if (App.isInternetAvail(BaseActivity.this)) {
                            {
                                final Dialog dialog = new Dialog(BaseActivity.this);
                                dialog.getWindow().setBackgroundDrawableResource(R.drawable.prograss_bg); //temp removed
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                                // Include dialog.xml file
                                dialog.setContentView(R.layout.popup_exit);

                                // set values for custom dialog components - text, image and button
                                TextView    tvExitMessage = (TextView) dialog.findViewById(R.id.tvMessage);
                                TextView   tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
                                TextView   tvOK = (TextView) dialog.findViewById(R.id.tvOk);

                                String strAlertMessage = "Are you sure you want to logout ?";
                                String strYes = "yes";
                                String strNo = "no";

                                tvExitMessage.setText(strAlertMessage);
                                tvCancel.setText(strNo);
                                tvOK.setText(strYes);


                                tvExitMessage.setTypeface(App.getFontMontserrat_Alternates_Regular());
                                tvCancel.setTypeface(App.getFontMontserrat_Alternates_Regular());
                                tvOK.setTypeface(App.getFontMontserrat_Alternates_Regular());

                                dialog.show();

                                tvCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        new Handler().postDelayed(new Runnable() {

                                            @Override
                                            public void run() {
                                                dialog.dismiss();
                                            }
                                        }, 800);
                                    }
                                });

                                tvOK.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        new Handler().postDelayed(new Runnable() {

                                            @Override
                                            public void run() {
                                                // asyncLogout();
                                            }
                                        }, 800);
                                    }
                                });
                            }
                        } else {
                            App.showSnackBar(tvTitle, getString(R.string.strNetError));
                        }


                    }  else {
                         App.showLog("=======Menu item not found====");
                            drawer.closeDrawers();
                        }
                    /* else {
                        App.showLog("=======Menu item not found====");
                    }*/
                    baseMenuAdapter.notifyDataSetChanged();
                }
            });
            return rowView;
        }

        public class ViewHolder {
            ImageView ivMenuIcon;
            TextView tvMenuItem;
            RelativeLayout rlMenuItem;
        }
    }


    public void animStartActivity() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public void animFinishActivity() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onResume() {
        try {
            if (AppFlags.isEditProfileBase == true)
            {
                App.showLog("==base onResume===");
                AppFlags.isEditProfileBase = false;
                setDrawerListAdapter();
                setBaseMenuDrawerDataAnyActivity();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onResume();

    }


    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        //	setCloseDrawerMenu(true);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(llContainerMain.getWindowToken(), 0);
        super.onPause();
    }


    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        System.out.println("=====Base onStop====");
        //	setCloseDrawerMenu(true);
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub

        System.out.println("=====Base onDestroy====");
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
        } else {
            try {
                super.onBackPressed();
                animFinishActivity();
            } catch (Exception e) {
                e.printStackTrace();
                App.showLog("==Exception on base back click====");
            }
        }
    }


    protected void logoutFinishAllActivity() {
        try {
            App.showLog("=====Logout err===");
            String strDeviceId = App.sharePrefrences.getStringPref(PreferencesKeys.strDeviceId);
            // String lid = App.sharePrefrences.getStringPref(PreferencesKeys.strLid);

            App.sharePrefrences.clearPrefValues();

            App.sharePrefrences.setPref(PreferencesKeys.strDeviceId, strDeviceId);

            NotificationManager notifManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notifManager.cancelAll();

            Intent iv = new Intent(BaseActivity.this, ActLoginSecond.class);
            iv.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            iv.putExtra(App.ITAG_FROM, "BaseActivity");
            startActivity(iv);
            animStartActivity();
            finishAffinity();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showExitDialog() {
        try
        {
            if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawers();
            }

            final Dialog dialog = new Dialog(BaseActivity.this);
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND); // for dialog shadow
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
            dialog.setContentView(R.layout.popup_exit);

            // set values for custom dialog components - text, image and button
            TextView   tvExitMessage = (TextView) dialog.findViewById(R.id.tvMessage);
            TextView    tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
            TextView    tvOK = (TextView) dialog.findViewById(R.id.tvOk);


            String strAlertMessageExit = "Are you sure you want to exit ?";
            String strYes = "Exit";
            String strNo = "Cancel";

            tvExitMessage.setText(strAlertMessageExit);
            tvCancel.setText(strNo);
            tvOK.setText(strYes);

            tvExitMessage.setTypeface(App.getTribuchet_MS());
            tvCancel.setTypeface(App.getTribuchet_MS());
            tvOK.setTypeface(App.getTribuchet_MS());

            dialog.show();

            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    }, 280);
                }
            });

            tvOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dialog.dismiss();

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            App.myFinishActivity(BaseActivity.this);
                            finishAffinity();
                            onBackPressed();
                            return;
                        }
                    }, 280);

                }
            });
        } catch (Exception e) {e.printStackTrace();}
    }


    public void showLogoutDialog() {
        try
        {
            if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawers();
            }

            final Dialog dialog = new Dialog(BaseActivity.this);
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND); // for dialog shadow
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
            dialog.setContentView(R.layout.popup_exit);

            // set values for custom dialog components - text, image and button
            TextView   tvExitMessage = (TextView) dialog.findViewById(R.id.tvMessage);
            TextView    tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
            TextView    tvOK = (TextView) dialog.findViewById(R.id.tvOk);


            String strAlertMessageExit = "Are you sure you want to logout app ?";
            String strYes = "Logout";
            String strNo = "Cancel";

            tvExitMessage.setText(strAlertMessageExit);
            tvCancel.setText(strNo);
            tvOK.setText(strYes);

            tvExitMessage.setTypeface(App.getTribuchet_MS());
            tvCancel.setTypeface(App.getTribuchet_MS());
            tvOK.setTypeface(App.getTribuchet_MS());

            dialog.show();

            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    }, 280);
                }
            });

            tvOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dialog.dismiss();

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            logoutFinishAllActivity();
                            return;
                        }
                    }, 280);

                }
            });
        } catch (Exception e) {e.printStackTrace();}
    }
}
