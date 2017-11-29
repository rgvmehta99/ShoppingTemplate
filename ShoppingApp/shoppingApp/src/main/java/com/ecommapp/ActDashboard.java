package com.ecommapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.api.model.DashboardCategoriesModel;
import com.api.model.DashboardHeaderViewpagerModel;
import com.api.model.DashboardListModel;
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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.util.CarouselLinearLayout;
import com.util.CircularImageView;
import com.util.MaterialProgressBar;
import com.util.PreferencesKeys;
import com.util.shimmer_recycler_view.ShimmerRecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by dhaval.mehta on 11-Sep-2017.
 */

public class ActDashboard extends BaseActivity {

    String TAG = "==ActDashboard==";

    //
    RelativeLayout rlNoData;
    TextView tvNoData;
    //
    LinearLayout llMain;
    ViewPager viewPager;
    CircleIndicator indicator;
    NestedScrollView nsView;
    // ShimmerRecyclerView recyclerView;
    RecyclerView recyclerView;
    MaterialProgressBar mterialProgress;

    ArrayList<DashboardListModel> arrayListDashboard;
    DashboardAdapter adapter;
    ArrayList<DashboardHeaderViewpagerModel> arrayListHeaderViewPager;
    TutorialAdapter tutorialAdapter;

    int SCREEN_HEIGHT = 0, SCREEN_WIDTH = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            //
            ViewGroup.inflate(this, R.layout.act_dashboard_new, llContainerSub);
            App.showLogTAG(TAG);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            SCREEN_HEIGHT = size.y;
            SCREEN_WIDTH = size.x;

            initialisation();
            setFonts();
            setClickEvents();
            setDashboardListData();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setDataToViewPager();
                    setDashboardAdapter();
                }
            }, 3000);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        App.sharePrefrences.setPref(PreferencesKeys.strMenuSelectedId, "0");
    }


    private void initialisation() {
        try
        {
            App.sharePrefrences.setPref(PreferencesKeys.strMenuSelectedId, "0");

            //
            baseViewHeaderShadow.setVisibility(View.GONE);
            rlBaseMainHeader.setVisibility(View.VISIBLE);
            setEnableDrawer(true);
            rlMenu.setVisibility(View.VISIBLE);

            rlMenu4.setVisibility(View.VISIBLE);
            ivMenu4.setImageResource(R.drawable.ic_cart);

            rlMenu3.setVisibility(View.VISIBLE);
            ivMenu3.setImageResource(R.drawable.ic_notification);

            //
            rlNoData = (RelativeLayout) findViewById(R.id.rlNoData);
            rlNoData.setVisibility(View.GONE);
            tvNoData = (TextView) findViewById(R.id.tvNoData);

            //
            llMain = (LinearLayout) findViewById(R.id.llMain);
            llMain.setVisibility(View.GONE);
            nsView = (NestedScrollView) findViewById(R.id.nsView);
            viewPager = (ViewPager) findViewById(R.id.viewPager);
            indicator = (CircleIndicator) findViewById(R.id.indicator);
            mterialProgress = (MaterialProgressBar) findViewById(R.id.mterialProgress);
            mterialProgress.setVisibility(View.VISIBLE);
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            // recyclerView.showShimmerAdapter();

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
//            viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
//                @Override public void transformPage(View page, float position) {
//                    if (viewPager.getCurrentItem() == 0) {
//                        // page.setTranslationX(220);
//                        page.setPadding(350, 0,0 ,0);
//                    } else if (viewPager.getCurrentItem() == tutorialAdapter.getCount() - 1) {
//                        // page.setTranslationX(-220);
//                    } else {
//                        // page.setTranslationX(0);
//                        page.setPadding(0, 0,0 ,0);
//                    }
//                }
//            });

            //
            rlMenu3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iv = new Intent(ActDashboard.this, ActNotification.class);
                    App.myStartActivity(ActDashboard.this, iv);
                }
            });

            //
            rlMenu4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iv = new Intent(ActDashboard.this, ActCartList.class);
                    App.myStartActivity(ActDashboard.this, iv);
                }
            });

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setDataToViewPager() {
        try
        {
            viewPager.setClipToPadding(false);
            viewPager.setClipChildren(false);
            // viewPager.setPadding(-20, -10, 90, -10); // left - top - right - bottom
            // viewPager.setPageMargin(50);
            // viewPager.setOffscreenPageLimit(3);
            // viewPager.setPageTransformer(true, new DashboardPagerTransformer(ActDashboard.this));


            arrayListHeaderViewPager = new ArrayList<DashboardHeaderViewpagerModel>();

            //
            arrayListHeaderViewPager.add(new DashboardHeaderViewpagerModel(
                    "",
                    "http://mytechquest.com/blog/wp-content/uploads/2013/07/Zalora-Android-Shopping-App-Enjoy-25-Percent-Discount.jpg"
            ));

            arrayListHeaderViewPager.add(new DashboardHeaderViewpagerModel(
                    "",
                    "https://assets.nerdwallet.com/blog/wp-content/uploads/2015/10/shutterstock_208789324.jpg"
            ));

            arrayListHeaderViewPager.add(new DashboardHeaderViewpagerModel(
                    "",
                    "http://srv-live-02.lazada.com.ph/cms/fashion-landing/men/Lazada-fashion-100914-LP-men-PH_01_B.jpg"
            ));

            arrayListHeaderViewPager.add(new DashboardHeaderViewpagerModel(
                    "",
                    "http://images3.communitycollegereview.com/article/462x462/0/343/Special-Challenges-and-Support-for-First-Generation-Community-College-Students-hO8C7o.jpg"
            ));

            arrayListHeaderViewPager.add(new DashboardHeaderViewpagerModel(
                    "",
                    "http://www.mercury-cm.co.uk/files/image/banner-our-stories.jpg"
            ));

            arrayListHeaderViewPager.add(new DashboardHeaderViewpagerModel(
                    "",
                    "http://sites.austincc.edu/intlbus/wp-content/uploads/sites/24/2014/08/new-student.jpg"
            ));

//            arrayListHeaderViewPager.add(new DashboardHeaderViewpagerModel(
//                    "",
//                    "https://hdwallpapersbuzz.com/wp-content/uploads/2017/02/hd-wallpapers-for-mobile-480x800-images-20.jpg"
//            ));

            arrayListHeaderViewPager.add(new DashboardHeaderViewpagerModel(
                    "",
                    "https://cdns.klimg.com/girl.fimela.com/resources/news/2014/07/22/2167/425x425-variasi-tas-untuk-kamu-yang-aktif-di-kampus-140722z_thumbnail.jpg"
            ));

//            arrayListHeaderViewPager.add(new DashboardHeaderViewpagerModel(
//                    "",
//                    "https://static.getjar.com/ss/e4/817499_4.jpg"
//            ));

            //
            tutorialAdapter = new TutorialAdapter(this, arrayListHeaderViewPager);
            viewPager.setAdapter(tutorialAdapter);
            indicator.setViewPager(viewPager);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class DashboardPagerTransformer implements ViewPager.PageTransformer {

        private int maxTranslateOffsetX;
        private ViewPager viewPager;

        public DashboardPagerTransformer(Context context) {
            this.maxTranslateOffsetX = dp2px(context, 180);
        }

        public void transformPage(View view, float position) {
            if (viewPager == null) {
                viewPager = (ViewPager) view.getParent();
            }

            int leftInScreen = view.getLeft() - viewPager.getScrollX();
            int centerXInViewPager = leftInScreen + view.getMeasuredWidth() / 2;
            int offsetX = centerXInViewPager - viewPager.getMeasuredWidth() / 2;
            float offsetRate = (float) offsetX * 0.38f / viewPager.getMeasuredWidth();
            float scaleFactor = 1 - Math.abs(offsetRate);

            if (scaleFactor > 0) {
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
                view.setTranslationX(-maxTranslateOffsetX * offsetRate);
                //ViewCompat.setElevation(view, 0.0f);
            }
            ViewCompat.setElevation(view, scaleFactor);

        }

        /**
         * Dp to pixel conversion
         */
        private int dp2px(Context context, float dipValue) {
            float m = context.getResources().getDisplayMetrics().density;
            return (int) (dipValue * m + 0.5f);
        }

    }


    private void setDashboardListData() {
        try
        {
            arrayListDashboard = new ArrayList<>();

            //------------------------------------------------------
            /*
            * TYPE 1 - New Arrivals - Add
            * */
            //------------------------------------------------------
            ArrayList<DashboardCategoriesModel> arrCateNewArrival = new ArrayList<>();
            arrCateNewArrival.add(new DashboardCategoriesModel(
                    "1",
                    "Armani Suit",
                    "15,000/-",
                    "https://ae01.alicdn.com/kf/HTB1HEN0KFXXXXcuXFXXq6xXFXXX0/Commercial-fashion-plaid-men-suits-wedding-male-piece-set-suit-light-gray-Men-large-plaid-suit.jpg",
                    ""
            ));
            arrCateNewArrival.add(new DashboardCategoriesModel(
                    "2",
                    "Gucchi Blazer",
                    "23,000/-",
                    "https://ae01.alicdn.com/kf/HTB1kO.NKpXXXXc8XXXXq6xXFXXXm/2015-Wool-arrival-Mens-Suit-suit-male-dresses-wedding-suits-for-men-Blazer-designs-gentleman-style.jpg",
                    ""
            ));
            arrCateNewArrival.add(new DashboardCategoriesModel(
                    "3",
                    "Raymond Coat",
                    "41,000/-",
                    "https://ae01.alicdn.com/kf/HTB1ANcqKVXXXXaoXpXXq6xXFXXXE/2017-Vintage-Grey-Mens-Suits-Peaked-Lapel-Wedding-Suits-For-Men-Groom-Tuxedos-for-men-One.jpg_640x640.jpg",
                    ""
            ));
            arrCateNewArrival.add(new DashboardCategoriesModel(
                    "4",
                    "Groom Special",
                    "55,000/-",
                    "http://s3.weddbook.com/t4/2/0/5/2053533/groom-groomsmen-and-ring-bearers.jpg",
                    ""
            ));
            arrayListDashboard.add(new DashboardListModel(
                    "1",
                    App.DASHBOARD_TYPE_NEW_ARRIVALS,
                    "New Arrivals for MEN",
                    "https://ae01.alicdn.com/kf/HTB1KFZVKXXXXXXhXFXXq6xXFXXXs/2015-New-Arrival-Mens-Suits-High-Quality-Wool-Suit-Men-Fashion-Business-Suits-Costume-Homme-Men.jpg",
                    arrCateNewArrival
            ));


            //------------------------------------------------------
            /*
            * TYPE - 2 - Adverticement Banner - Add
            * */
            //------------------------------------------------------
            ArrayList<DashboardCategoriesModel> arrCateAddBanner = new ArrayList<>();
            arrCateAddBanner.add(new DashboardCategoriesModel(
                    "5",
                    "New Addverticement with special offer",
                    "3,000/-",
                    "http://keenzie.com.au/media/catalog/category/landing-men-new.jpg",
                    ""
            ));
            arrayListDashboard.add(new DashboardListModel(
                    "2",
                    App.DASHBOARD_TYPE_ADD_BANNER,
                    "New Addverticement with special offer",
                    "http://keenzie.com.au/media/catalog/category/landing-men-new.jpg",
                    arrCateAddBanner
            ));



            //------------------------------------------------------
            /*
            * TYPE - 3 - Discount Items - Add
            * */
            //------------------------------------------------------
            ArrayList<DashboardCategoriesModel> arrCateDiscountItems = new ArrayList<>();
            arrCateDiscountItems.add(new DashboardCategoriesModel(
                    "6",
                    "New Discount available",
                    "1,500/- off",
                    "http://www.joanart.co.nz/includes/templates/joanart//images/Women%20clothes.jpg",
                    ""
            ));
            arrayListDashboard.add(new DashboardListModel(
                    "3",
                    App.DASHBOARD_TYPE_DISCOUNT_ITEMS,
                    "New Discount available",
                    "http://www.joanart.co.nz/includes/templates/joanart//images/Women%20clothes.jpg",
                    arrCateDiscountItems
            ));



            //------------------------------------------------------
            /*
            * TYPE - 4 - Last Related Search - Add
            * */
            //------------------------------------------------------
            ArrayList<DashboardCategoriesModel> arrCateLastRelatedSearch = new ArrayList<>();
            arrCateLastRelatedSearch.add(new DashboardCategoriesModel(
                    "7",
                    "Addidas Canvas",
                    "4,300/-",
                    "http://www.jjessamine.co.uk/images/large/JJESS_Mall/adidas%20NMD_R1%20Shoes%20Black%20White%20Men%20-%20Adidas%20Shoes%20New%20Arrivals%20-%20S11t2839%20661_4_LRG.jpg",
                    ""
            ));
            arrCateLastRelatedSearch.add(new DashboardCategoriesModel(
                    "8",
                    "Skybag eg1300T",
                    "1,700/-",
                    "https://m.media-amazon.com/images/S/aplus-media/sota/944459f3-fb69-4fc5-a40c-74dd0915f92c._SR285,285_.jpg",
                    ""
            ));
            arrCateLastRelatedSearch.add(new DashboardCategoriesModel(
                    "9",
                    "SONY ca2100-SA",
                    "5,700/-",
                    "https://n1.sdlcdn.com/imgs/b/g/p/Sony-DAV-TZ145-5-1-1546583-11-cb109.jpg",
                    ""
            ));
            arrayListDashboard.add(new DashboardListModel(
                    "4",
                    App.DASHBOARD_TYPE_LAST_RELATED_SEARCH,
                    "Last Related Search Items",
                    "http://www.charlestoncvb.com/assets/images/plan-your-trip/205.jpg",
                    arrCateLastRelatedSearch
            ));



            //------------------------------------------------------
            /*
            * TYPE - 2 - Addverticement Banner - Add
            * */
            //------------------------------------------------------
            ArrayList<DashboardCategoriesModel> arrCateAddBannerTwo = new ArrayList<>();
            arrCateAddBannerTwo.add(new DashboardCategoriesModel(
                    "10",
                    "New Arrival for WOMEN",
                    "",
                    "https://i.pinimg.com/736x/66/dc/04/66dc04223e205fb9f55ada9656885426--banner-design-banners.jpg",
                    ""
            ));
            arrayListDashboard.add(new DashboardListModel(
                    "5",
                    App.DASHBOARD_TYPE_ADD_BANNER,
                    "New Addverticement with special offer",
                    "https://i.pinimg.com/736x/66/dc/04/66dc04223e205fb9f55ada9656885426--banner-design-banners.jpg",
                    arrCateAddBannerTwo
            ));





            //------------------------------------------------------
            /*
            * TYPE 1 - New Arrivals - Add
            * */
            //------------------------------------------------------
            ArrayList<DashboardCategoriesModel> arrCateNewArrivalTwo = new ArrayList<>();
            arrCateNewArrivalTwo.add(new DashboardCategoriesModel(
                    "11",
                    "SK096 Skullcandy",
                    "2,700/-",
                    "https://cdn.trendhunterstatic.com/thumbs/kidrobot-x-skullcandy-headphones.jpeg",
                    ""
            ));
            arrCateNewArrivalTwo.add(new DashboardCategoriesModel(
                    "12",
                    "JBL JT100",
                    "5,200/-",
                    "https://images-na.ssl-images-amazon.com/images/I/71H8RU9sprL._SY355_.jpg",
                    ""
            ));
            arrCateNewArrivalTwo.add(new DashboardCategoriesModel(
                    "13",
                    "JBL HomeH101",
                    "41,500/-",
                    "http://d3d71ba2asa5oz.cloudfront.net/12021657/images/166955__wws1.jpg",
                    ""
            ));
            arrCateNewArrivalTwo.add(new DashboardCategoriesModel(
                    "14",
                    "LG Slim SD8",
                    "58,000/-",
                    "http://www.gadgethelpline.com/wp-content/uploads/2011/07/LG-OLED-TV.jpg",
                    ""
            ));
            arrayListDashboard.add(new DashboardListModel(
                    "6",
                    App.DASHBOARD_TYPE_NEW_ARRIVALS,
                    "Electronics Weekend",
                    "",
                    arrCateNewArrivalTwo
            ));




            //------------------------------------------------------
            /*
            * TYPE - 2 - Addverticement Banner - Add
            * */
            //------------------------------------------------------
            ArrayList<DashboardCategoriesModel> arrCateAddBannerThree = new ArrayList<>();
            arrCateAddBannerThree.add(new DashboardCategoriesModel(
                    "15",
                    "New Arrival for MEN spring",
                    "",
                    "http://www.sydneybanners.com/images/swappable-poster-systems.jpg",
                    ""
            ));
            arrayListDashboard.add(new DashboardListModel(
                    "7",
                    App.DASHBOARD_TYPE_ADD_BANNER,
                    "New Addverticement with special offer",
                    "http://www.sydneybanners.com/images/swappable-poster-systems.jpg",
                    arrCateAddBannerThree
            ));




            //------------------------------------------------------
            /*
            * TYPE - 4 - Last Related Search - Add
            * */
            //------------------------------------------------------
            ArrayList<DashboardCategoriesModel> arrCateLastRelatedSearchTwo = new ArrayList<>();
            arrCateLastRelatedSearchTwo.add(new DashboardCategoriesModel(
                    "16",
                    "USHA Juicer",
                    "2,300/-",
                    "https://images-eu.ssl-images-amazon.com/images/G/31//img16/Home-SA/Revamp/670x381_juicers._V278098910_.jpg",
                    ""
            ));
            arrCateLastRelatedSearchTwo.add(new DashboardCategoriesModel(
                    "7",
                    "Heier Water Purifier",
                    "21,700/-",
                    "http://p3.fide.pl/800/46008_morphy_richards_redefine_dyspenser_goracej_wody.jpg",
                    ""
            ));
            arrCateLastRelatedSearchTwo.add(new DashboardCategoriesModel(
                    "18",
                    "LG WM-21-8778-99",
                    "35,350/-",
                    "http://www.lgnewsroom.com/wp-content/uploads/2011/01/LG-UNVEILS-TOTAL-HOME-APPLIANCE-SOLUTION-EMPOWERING-CONSUMERS-TO-SMARTLY-MANAGE-THEIR-HOMES21.jpg",
                    ""
            ));
            arrayListDashboard.add(new DashboardListModel(
                    "8",
                    App.DASHBOARD_TYPE_LAST_RELATED_SEARCH,
                    "Home Appliance",
                    "http://www.homeappliancesworld.com/files/2015/09/home-appliance1.jpg",
                    arrCateLastRelatedSearchTwo
            ));



            //------------------------------------------------------
            /*
            * TYPE - 3 - Discount Items - Add
            * */
            //------------------------------------------------------
            ArrayList<DashboardCategoriesModel> arrCateDiscountItemsTwo = new ArrayList<>();
            arrCateDiscountItemsTwo.add(new DashboardCategoriesModel(
                    "19",
                    "Discount bronanza here",
                    "14% flat",
                    "http://dininginthedarkkl.com/wp-content/uploads/2014/12/DID-14-percent-discount.jpg",
                    ""
            ));
            arrayListDashboard.add(new DashboardListModel(
                    "9",
                    App.DASHBOARD_TYPE_DISCOUNT_ITEMS,
                    "Discount bronanza here",
                    "http://dininginthedarkkl.com/wp-content/uploads/2014/12/DID-14-percent-discount.jpg",
                    arrCateDiscountItemsTwo
            ));


        } catch (Exception e) {e.printStackTrace();}
    }


    private void setDashboardAdapter() {
        try
        {
            mterialProgress.setVisibility(View.GONE);

            if (arrayListDashboard != null && arrayListDashboard.size() > 0)
            {
                llMain.setVisibility(View.VISIBLE);
                rlNoData.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                adapter = new DashboardAdapter(ActDashboard.this, arrayListDashboard);

                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            else
            {
                llMain.setVisibility(View.GONE);
                rlNoData.setVisibility(View.VISIBLE);
                tvNoData.setText("No items found.");
            }
        } catch (Exception e) {e.printStackTrace();}
    }


    public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.VersionViewHolder> {
        ArrayList<DashboardListModel> mArrayList;
        Context mContext;


        public DashboardAdapter(Context context, ArrayList<DashboardListModel> arrayList) {
            mArrayList = arrayList;
            mContext = context;
        }

        @Override
        public DashboardAdapter.VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_dashboard_item,
                    viewGroup, false);
            DashboardAdapter.VersionViewHolder viewHolder = new DashboardAdapter.VersionViewHolder(view);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final DashboardAdapter.VersionViewHolder versionViewHolder, final int position) {
            try
            {
                final DashboardListModel modelList = mArrayList.get(position);
                String cate_type = modelList.dash_main_cate_type;

                //
                if (cate_type != null && cate_type.length() > 0)
                {
                    // TYPE - 1
                    if (cate_type.equalsIgnoreCase(App.DASHBOARD_TYPE_NEW_ARRIVALS))
                    {
//                        versionViewHolder.llHeaderViewPager.setVisibility(View.GONE);
                        versionViewHolder.llMainTypeOne.setVisibility(View.VISIBLE);
                        versionViewHolder.llMainTypeTwo.setVisibility(View.GONE);
                        versionViewHolder.llMainTypeThree.setVisibility(View.GONE);
                        versionViewHolder.llMainTypeFour.setVisibility(View.GONE);
                        //
                        if (modelList.dash_main_cate_name != null && modelList.dash_main_cate_name.length() > 0)
                        {
                            versionViewHolder.tvNewArrivalsTag.setText(modelList.dash_main_cate_name);
                        }
                        //
                        if (modelList.arryCategories != null && modelList.arryCategories.size() > 0)
                        {
                            //
                            // *********** 1
                            //
                            if (modelList.arryCategories.get(0).cate_name != null &&
                                    modelList.arryCategories.get(0).cate_name.length() > 0)
                            {
                                versionViewHolder.tvNewArrivalsProductNameOne.setText(modelList.arryCategories.get(0).cate_name);
                            }
                            //
                            if (modelList.arryCategories.get(0).cate_price != null &&
                                    modelList.arryCategories.get(0).cate_price.length() > 0)
                            {
                                versionViewHolder.tvNewArrivalsProductPriceOne.setText(modelList.arryCategories.get(0).cate_price);
                            }
                            //
                            if (modelList.arryCategories.get(0).cate_image != null &&
                                    modelList.arryCategories.get(0).cate_image.length() > 0)
                            {
                                ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(modelList.arryCategories.get(0).cate_image))
                                        .setResizeOptions(new ResizeOptions(SCREEN_WIDTH, 150))
                                        .build();
                                DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                                        .setImageRequest(imageRequest)
                                        .setOldController(versionViewHolder.ivNewArrivalsProductImgOne.getController())
                                        .setAutoPlayAnimations(true)
                                        .build();
                                versionViewHolder.ivNewArrivalsProductImgOne.setController(draweeController);
//                                Glide.with(ActDashboard.this)
//                                        .load(modelList.arryCategories.get(0).cate_image)
//                                        .placeholder(R.drawable.placeholder)
//                                        .into(versionViewHolder.ivNewArrivalsProductImgOne);
                            }
                            //
                            // *********** 2
                            //
                            if (modelList.arryCategories.get(1).cate_name != null &&
                                    modelList.arryCategories.get(1).cate_name.length() > 0)
                            {
                                versionViewHolder.tvNewArrivalsProductNameTwo.setText(modelList.arryCategories.get(1).cate_name);
                            }
                            //
                            if (modelList.arryCategories.get(1).cate_price != null &&
                                    modelList.arryCategories.get(1).cate_price.length() > 0)
                            {
                                versionViewHolder.tvNewArrivalsProductPriceTwo.setText(modelList.arryCategories.get(1).cate_price);
                            }
                            //
                            if (modelList.arryCategories.get(1).cate_image != null &&
                                    modelList.arryCategories.get(1).cate_image.length() > 0)
                            {
                                ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(modelList.arryCategories.get(1).cate_image))
                                        .setResizeOptions(new ResizeOptions(SCREEN_WIDTH, 150))
                                        .build();
                                DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                                        .setImageRequest(imageRequest)
                                        .setOldController(versionViewHolder.ivNewArrivalsProductImgTwo.getController())
                                        .setAutoPlayAnimations(true)
                                        .build();
                                versionViewHolder.ivNewArrivalsProductImgTwo.setController(draweeController);
//                                Glide.with(ActDashboard.this)
//                                        .load(modelList.arryCategories.get(1).cate_image)
//                                        .placeholder(R.drawable.placeholder)
//                                        .into(versionViewHolder.ivNewArrivalsProductImgTwo);
                            }
                            //
                            // *********** 3
                            //
                            if (modelList.arryCategories.get(2).cate_name != null &&
                                    modelList.arryCategories.get(2).cate_name.length() > 0)
                            {
                                versionViewHolder.tvNewArrivalsProductNameThree.setText(modelList.arryCategories.get(2).cate_name);
                            }
                            //
                            if (modelList.arryCategories.get(2).cate_price != null &&
                                    modelList.arryCategories.get(2).cate_price.length() > 0)
                            {
                                versionViewHolder.tvNewArrivalsProductPriceThree.setText(modelList.arryCategories.get(2).cate_price);
                            }
                            //
                            if (modelList.arryCategories.get(2).cate_image != null &&
                                    modelList.arryCategories.get(2).cate_image.length() > 0)
                            {
                                ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(modelList.arryCategories.get(2).cate_image))
                                        .setResizeOptions(new ResizeOptions(SCREEN_WIDTH, 150))
                                        .build();
                                DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                                        .setImageRequest(imageRequest)
                                        .setOldController(versionViewHolder.ivNewArrivalsProductImgThree.getController())
                                        .setAutoPlayAnimations(true)
                                        .build();
                                versionViewHolder.ivNewArrivalsProductImgThree.setController(draweeController);
//                                Glide.with(ActDashboard.this)
//                                        .load(modelList.arryCategories.get(2).cate_image)
//                                        .placeholder(R.drawable.placeholder)
//                                        .into(versionViewHolder.ivNewArrivalsProductImgThree);
                            }
                            //
                            // *********** 4
                            //
                            if (modelList.arryCategories.get(3).cate_name != null &&
                                    modelList.arryCategories.get(3).cate_name.length() > 0)
                            {
                                versionViewHolder.tvNewArrivalsProductNameFour.setText(modelList.arryCategories.get(3).cate_name);
                            }
                            //
                            if (modelList.arryCategories.get(3).cate_price != null &&
                                    modelList.arryCategories.get(3).cate_price.length() > 0)
                            {
                                versionViewHolder.tvNewArrivalsProductPriceFour.setText(modelList.arryCategories.get(3).cate_price);
                            }
                            //
                            if (modelList.arryCategories.get(3).cate_image != null &&
                                    modelList.arryCategories.get(3).cate_image.length() > 0)
                            {
                                ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(modelList.arryCategories.get(3).cate_image))
                                        .setResizeOptions(new ResizeOptions(SCREEN_WIDTH, 150))
                                        .build();
                                DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                                        .setImageRequest(imageRequest)
                                        .setOldController(versionViewHolder.ivNewArrivalsProductImgFour.getController())
                                        .setAutoPlayAnimations(true)
                                        .build();
                                versionViewHolder.ivNewArrivalsProductImgFour.setController(draweeController);
//                                Glide.with(ActDashboard.this)
//                                        .load(modelList.arryCategories.get(3).cate_image)
//                                        .placeholder(R.drawable.placeholder)
//                                        .into(versionViewHolder.ivNewArrivalsProductImgFour);
                            }
                        }

                    }
                    // TYPE - 2
                    else if (cate_type.equalsIgnoreCase(App.DASHBOARD_TYPE_ADD_BANNER))
                    {
//                        versionViewHolder.llHeaderViewPager.setVisibility(View.GONE);
                        versionViewHolder.llMainTypeOne.setVisibility(View.GONE);
                        versionViewHolder.llMainTypeTwo.setVisibility(View.VISIBLE);
                        versionViewHolder.llMainTypeThree.setVisibility(View.GONE);
                        versionViewHolder.llMainTypeFour.setVisibility(View.GONE);
                        //
                        if (modelList.dash_main_cate_image != null && modelList.dash_main_cate_image.length() > 0)
                        {
                            ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(modelList.dash_main_cate_image))
                                    .setResizeOptions(new ResizeOptions(SCREEN_WIDTH, 210))
                                    .build();
                            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                                    .setImageRequest(imageRequest)
                                    .setOldController(versionViewHolder.ivNewArrivalsProductImgFour.getController())
                                    .setAutoPlayAnimations(true)
                                    .build();
                            versionViewHolder.ivAddBannerImage.setController(draweeController);
//                            Glide.with(ActDashboard.this)
//                                .load(modelList.dash_main_cate_image)
//                                .placeholder(R.drawable.placeholder)
//                                .fitCenter()
//                                .into(versionViewHolder.ivAddBannerImage);
                        }

                    }
                    // TYPE - 3
                    else if (cate_type.equalsIgnoreCase(App.DASHBOARD_TYPE_DISCOUNT_ITEMS))
                    {
//                        versionViewHolder.llHeaderViewPager.setVisibility(View.GONE);
                        versionViewHolder.llMainTypeOne.setVisibility(View.GONE);
                        versionViewHolder.llMainTypeTwo.setVisibility(View.GONE);
                        versionViewHolder.llMainTypeThree.setVisibility(View.VISIBLE);
                        versionViewHolder.llMainTypeFour.setVisibility(View.GONE);
                        //
                        if (modelList.dash_main_cate_name != null && modelList.dash_main_cate_name.length() > 0)
                        {
                            versionViewHolder.tvDiscountTag.setText(modelList.dash_main_cate_name);
                        }
                        //
                        if (modelList.dash_main_cate_image != null && modelList.dash_main_cate_image.length() > 0)
                        {
                            ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(modelList.dash_main_cate_image))
                                    .setResizeOptions(new ResizeOptions(120, 120))
                                    .build();
                            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                                    .setImageRequest(imageRequest)
                                    .setOldController(versionViewHolder.ivDiscountTagCircularImage.getController())
                                    .setAutoPlayAnimations(true)
                                    .build();
                            versionViewHolder.ivDiscountTagCircularImage.setController(draweeController);

//                            Glide.with(ActDashboard.this)
//                                    .load(modelList.dash_main_cate_image)
//                                    .asBitmap()
//                                    .placeholder(R.drawable.placeholder)
//                                    .into(new SimpleTarget<Bitmap>(100,100) {
//                                        @Override
//                                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
//                                            versionViewHolder.ivDiscountTagCircularImage.setImageBitmap(resource);
//
//                                        }
//                                    });
                        }
                    }
                    // TYPE - 4
                    else if (cate_type.equalsIgnoreCase(App.DASHBOARD_TYPE_LAST_RELATED_SEARCH))
                    {
//                        versionViewHolder.llHeaderViewPager.setVisibility(View.GONE);
                        versionViewHolder.llMainTypeOne.setVisibility(View.GONE);
                        versionViewHolder.llMainTypeTwo.setVisibility(View.GONE);
                        versionViewHolder.llMainTypeThree.setVisibility(View.GONE);
                        versionViewHolder.llMainTypeFour.setVisibility(View.VISIBLE);
                        //
                        if (modelList.dash_main_cate_name != null && modelList.dash_main_cate_name.length() > 0)
                        {
                            versionViewHolder.tvLastRelatedSearchTag.setText(modelList.dash_main_cate_name);
                        }
                        //
                        if (modelList.dash_main_cate_image != null && modelList.dash_main_cate_image.length() > 0)
                        {
                            Glide.with(ActDashboard.this)
                                    .load(modelList.dash_main_cate_image)
                                    .asBitmap()
                                    .placeholder(R.drawable.placeholder)
                                    .into(new SimpleTarget<Bitmap>(100,100) {
                                        @Override
                                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                            versionViewHolder.ivLastRelatedSearchTagCircularImage.setImageBitmap(resource);

                                        }
                                    });
                        }
                        //
                        if (modelList.arryCategories != null && modelList.arryCategories.size() > 0)
                        {
                            //
                            // *********** 1
                            //
                            if (modelList.arryCategories.get(0).cate_name != null &&
                                    modelList.arryCategories.get(0).cate_name.length() > 0)
                            {
                                versionViewHolder.tvLastRelatedSearchProductNameOne.setText(modelList.arryCategories.get(0).cate_name);
                            }
                            //
                            if (modelList.arryCategories.get(0).cate_price != null &&
                                    modelList.arryCategories.get(0).cate_price.length() > 0)
                            {
                                versionViewHolder.tvLastRelatedSearchProductPriceOne.setText(modelList.arryCategories.get(0).cate_price);
                            }
                            //
                            if (modelList.arryCategories.get(0).cate_image != null &&
                                    modelList.arryCategories.get(0).cate_image.length() > 0)
                            {

                                ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(modelList.arryCategories.get(0).cate_image))
                                        .setResizeOptions(new ResizeOptions(SCREEN_WIDTH, 245))
                                        .build();
                                DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                                        .setImageRequest(imageRequest)
                                        .setOldController(versionViewHolder.ivLastRelatedSearchProductImgOne.getController())
                                        .setAutoPlayAnimations(true)
                                        .build();
                                versionViewHolder.ivLastRelatedSearchProductImgOne.setController(draweeController);

//                                Glide.with(ActDashboard.this)
//                                        .load(modelList.arryCategories.get(0).cate_image)
//                                        .placeholder(R.drawable.placeholder)
//                                        .into(versionViewHolder.ivLastRelatedSearchProductImgOne);
                            }
                            //
                            // *********** 2
                            //
                            if (modelList.arryCategories.get(1).cate_name != null &&
                                    modelList.arryCategories.get(1).cate_name.length() > 0)
                            {
                                versionViewHolder.tvLastRelatedSearchProductNameTwo.setText(modelList.arryCategories.get(1).cate_name);
                            }
                            //
                            if (modelList.arryCategories.get(1).cate_price != null &&
                                    modelList.arryCategories.get(1).cate_price.length() > 0)
                            {
                                versionViewHolder.tvLastRelatedSearchProductPriceTwo.setText(modelList.arryCategories.get(1).cate_price);
                            }
                            //
                            if (modelList.arryCategories.get(1).cate_image != null &&
                                    modelList.arryCategories.get(1).cate_image.length() > 0)
                            {
                                ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(modelList.arryCategories.get(1).cate_image))
                                        .setResizeOptions(new ResizeOptions(SCREEN_WIDTH, 90))
                                        .build();
                                DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                                        .setImageRequest(imageRequest)
                                        .setOldController(versionViewHolder.ivLastRelatedSearchProductImgTwo.getController())
                                        .setAutoPlayAnimations(true)
                                        .build();
                                versionViewHolder.ivLastRelatedSearchProductImgTwo.setController(draweeController);

//                                Glide.with(ActDashboard.this)
//                                        .load(modelList.arryCategories.get(1).cate_image)
//                                        .placeholder(R.drawable.placeholder)
//                                        .into(versionViewHolder.ivLastRelatedSearchProductImgTwo);
                            }
                            //
                            // *********** 2
                            //
                            if (modelList.arryCategories.get(2).cate_name != null &&
                                    modelList.arryCategories.get(2).cate_name.length() > 0)
                            {
                                versionViewHolder.tvLastRelatedSearchProductNameThree.setText(modelList.arryCategories.get(2).cate_name);
                            }
                            //
                            if (modelList.arryCategories.get(2).cate_price != null &&
                                    modelList.arryCategories.get(2).cate_price.length() > 0)
                            {
                                versionViewHolder.tvLastRelatedSearchProductPriceThree.setText(modelList.arryCategories.get(2).cate_price);
                            }
                            //
                            if (modelList.arryCategories.get(2).cate_image != null &&
                                    modelList.arryCategories.get(2).cate_image.length() > 0)
                            {
                                ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(modelList.arryCategories.get(2).cate_image))
                                        .setResizeOptions(new ResizeOptions(SCREEN_WIDTH, 90))
                                        .build();
                                DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                                        .setImageRequest(imageRequest)
                                        .setOldController(versionViewHolder.ivLastRelatedSearchProductImgThree.getController())
                                        .setAutoPlayAnimations(true)
                                        .build();
                                versionViewHolder.ivLastRelatedSearchProductImgThree.setController(draweeController);

//                                Glide.with(ActDashboard.this)
//                                        .load(modelList.arryCategories.get(2).cate_image)
//                                        .placeholder(R.drawable.placeholder)
//                                        .into(versionViewHolder.ivLastRelatedSearchProductImgThree);
                            }
                        }
                    }
                    // Other Type found
                    else
                    {
//                        versionViewHolder.llHeaderViewPager.setVisibility(View.GONE);
                        versionViewHolder.llMainTypeOne.setVisibility(View.GONE);
                        versionViewHolder.llMainTypeTwo.setVisibility(View.GONE);
                        versionViewHolder.llMainTypeThree.setVisibility(View.GONE);
                        versionViewHolder.llMainTypeFour.setVisibility(View.GONE);
                    }
                }
                else
                {
                    // cate_type is null
                    App.showToastShort(ActDashboard.this, "No category type found.");
                }


                // CLICK
                versionViewHolder.rlViewAllTagOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        App.myProductDetailActivity(ActDashboard.this);
                    }
                });

                versionViewHolder.ivNewArrivalsProductImgOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        App.myProductDetailActivity(ActDashboard.this);
                    }
                });

                versionViewHolder.ivNewArrivalsProductImgTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        App.myProductDetailActivity(ActDashboard.this);
                    }
                });

                versionViewHolder.ivNewArrivalsProductImgThree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        App.myProductDetailActivity(ActDashboard.this);
                    }
                });

                versionViewHolder.ivNewArrivalsProductImgFour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        App.myProductDetailActivity(ActDashboard.this);
                    }
                });

            } catch (Exception e) {e.printStackTrace();}
        }

        @Override
        public int getItemCount() {
            return mArrayList.size();
        }


        class VersionViewHolder extends RecyclerView.ViewHolder
        {
            //
            // TYPE - 1 Controls
            //
            LinearLayout llMainTypeOne;
            RelativeLayout rlHeaderOne;
            TextView tvNewArrivalsTag, rlViewAllTagOne;
//            ImageView ivNewArrivalsProductImgOne, ivNewArrivalsProductImgTwo,
//                    ivNewArrivalsProductImgThree, ivNewArrivalsProductImgFour;
            SimpleDraweeView ivNewArrivalsProductImgOne, ivNewArrivalsProductImgTwo,
                    ivNewArrivalsProductImgThree, ivNewArrivalsProductImgFour;
            TextView tvNewArrivalsProductNameOne, tvNewArrivalsProductPriceOne,
                    tvNewArrivalsProductNameTwo, tvNewArrivalsProductPriceTwo,
                    tvNewArrivalsProductNameThree, tvNewArrivalsProductPriceThree,
                    tvNewArrivalsProductNameFour, tvNewArrivalsProductPriceFour;

            //
            // TYPE - 2 Controls
            //
            LinearLayout llMainTypeTwo;
            SimpleDraweeView ivAddBannerImage;
            // ImageView ivAddBannerImage;

            //
            // TYPE - 3 Controls
            //
            LinearLayout llMainTypeThree;
            TextView tvDiscountTag;
            SimpleDraweeView ivDiscountTagCircularImage;
            // CircularImageView ivDiscountTagCircularImage;

            //
            // TYPE - 4 - Controls
            //
            LinearLayout llMainTypeFour;
            RelativeLayout rlHeaderFour;
            CircularImageView ivLastRelatedSearchTagCircularImage;
            TextView tvLastRelatedSearchTag, rlViewAllTagFour;
//            ImageView ivLastRelatedSearchProductImgOne, ivLastRelatedSearchProductImgTwo,
//                    ivLastRelatedSearchProductImgThree;
            SimpleDraweeView ivLastRelatedSearchProductImgOne, ivLastRelatedSearchProductImgTwo,
                    ivLastRelatedSearchProductImgThree;
            TextView tvLastRelatedSearchProductNameOne, tvLastRelatedSearchProductPriceOne,
                    tvLastRelatedSearchProductNameTwo, tvLastRelatedSearchProductPriceTwo,
                    tvLastRelatedSearchProductNameThree, tvLastRelatedSearchProductPriceThree;

            public VersionViewHolder(View itemView) {
                super(itemView);


                // // TYPE - HEADER VIEW PAGER
//                llHeaderViewPager = (LinearLayout) itemView.findViewById(R.id.llHeaderViewPager);
//                viewPager = (ViewPager) itemView.findViewById(R.id.viewPager);

                // // TYPE - 1
                llMainTypeOne = (LinearLayout) itemView.findViewById(R.id.llMainTypeOne);
                rlHeaderOne = (RelativeLayout) itemView.findViewById(R.id.rlHeaderOne);

                tvNewArrivalsTag = (TextView) itemView.findViewById(R.id.tvNewArrivalsTag);
                rlViewAllTagOne = (TextView) itemView.findViewById(R.id.rlViewAllTagOne);

                ivNewArrivalsProductImgOne = (SimpleDraweeView) itemView.findViewById(R.id.ivNewArrivalsProductImgOne);
                ivNewArrivalsProductImgTwo = (SimpleDraweeView) itemView.findViewById(R.id.ivNewArrivalsProductImgTwo);
                ivNewArrivalsProductImgThree = (SimpleDraweeView) itemView.findViewById(R.id.ivNewArrivalsProductImgThree);
                ivNewArrivalsProductImgFour = (SimpleDraweeView) itemView.findViewById(R.id.ivNewArrivalsProductImgFour);

                tvNewArrivalsProductNameOne = (TextView) itemView.findViewById(R.id.tvNewArrivalsProductNameOne);
                tvNewArrivalsProductPriceOne = (TextView) itemView.findViewById(R.id.tvNewArrivalsProductPriceOne);
                tvNewArrivalsProductNameTwo = (TextView) itemView.findViewById(R.id.tvNewArrivalsProductNameTwo);
                tvNewArrivalsProductPriceTwo = (TextView) itemView.findViewById(R.id.tvNewArrivalsProductPriceTwo);
                tvNewArrivalsProductNameThree = (TextView) itemView.findViewById(R.id.tvNewArrivalsProductNameThree);
                tvNewArrivalsProductPriceThree = (TextView) itemView.findViewById(R.id.tvNewArrivalsProductPriceThree);
                tvNewArrivalsProductNameFour = (TextView) itemView.findViewById(R.id.tvNewArrivalsProductNameFour);
                tvNewArrivalsProductPriceFour = (TextView) itemView.findViewById(R.id.tvNewArrivalsProductPriceFour);

                /* * * TYPE - 1 Fonts
                * */
                tvNewArrivalsTag.setTypeface(App.getTribuchet_MS());
                rlViewAllTagOne.setTypeface(App.getTribuchet_MS());

                tvNewArrivalsProductNameOne.setTypeface(App.getTribuchet_MS());
                tvNewArrivalsProductPriceOne.setTypeface(App.getTribuchet_MS());
                tvNewArrivalsProductNameTwo.setTypeface(App.getTribuchet_MS());
                tvNewArrivalsProductPriceTwo.setTypeface(App.getTribuchet_MS());
                tvNewArrivalsProductNameThree.setTypeface(App.getTribuchet_MS());
                tvNewArrivalsProductPriceThree.setTypeface(App.getTribuchet_MS());
                tvNewArrivalsProductNameFour.setTypeface(App.getTribuchet_MS());
                tvNewArrivalsProductPriceFour.setTypeface(App.getTribuchet_MS());



                // // TYPE - 2
                llMainTypeTwo = (LinearLayout) itemView.findViewById(R.id.llMainTypeTwo);
                ivAddBannerImage = (SimpleDraweeView) itemView.findViewById(R.id.ivAddBannerImage);




                // // TYPE - 3
                llMainTypeThree = (LinearLayout) itemView.findViewById(R.id.llMainTypeThree);
                tvDiscountTag = (TextView) itemView.findViewById(R.id.tvDiscountTag);
                ivDiscountTagCircularImage = (SimpleDraweeView) itemView.findViewById(R.id.ivDiscountTagCircularImage);
                // ivDiscountTagCircularImage = (CircularImageView) itemView.findViewById(R.id.ivDiscountTagCircularImage);

                /* * * TYPE - 3 Fonts
                * */
                tvDiscountTag.setTypeface(App.getTribuchet_MS());


                // // TYPE - 4
                llMainTypeFour = (LinearLayout) itemView.findViewById(R.id.llMainTypeFour);
                rlHeaderFour = (RelativeLayout) itemView.findViewById(R.id.rlHeaderFour);

                ivLastRelatedSearchTagCircularImage = (CircularImageView) itemView.findViewById(R.id.ivLastRelatedSearchTagCircularImage);

                tvLastRelatedSearchTag = (TextView) itemView.findViewById(R.id.tvLastRelatedSearchTag);
                rlViewAllTagFour = (TextView) itemView.findViewById(R.id.rlViewAllTagFour);

                ivLastRelatedSearchProductImgOne = (SimpleDraweeView) itemView.findViewById(R.id.ivLastRelatedSearchProductImgOne);
                ivLastRelatedSearchProductImgTwo = (SimpleDraweeView) itemView.findViewById(R.id.ivLastRelatedSearchProductImgTwo);
                ivLastRelatedSearchProductImgThree = (SimpleDraweeView) itemView.findViewById(R.id.ivLastRelatedSearchProductImgThree);

                tvLastRelatedSearchProductNameOne = (TextView) itemView.findViewById(R.id.tvLastRelatedSearchProductNameOne);
                tvLastRelatedSearchProductPriceOne = (TextView) itemView.findViewById(R.id.tvLastRelatedSearchProductPriceOne);
                tvLastRelatedSearchProductNameTwo = (TextView) itemView.findViewById(R.id.tvLastRelatedSearchProductNameTwo);
                tvLastRelatedSearchProductPriceTwo = (TextView) itemView.findViewById(R.id.tvLastRelatedSearchProductPriceTwo);
                tvLastRelatedSearchProductNameThree = (TextView) itemView.findViewById(R.id.tvLastRelatedSearchProductNameThree);
                tvLastRelatedSearchProductPriceThree = (TextView) itemView.findViewById(R.id.tvLastRelatedSearchProductPriceThree);

                /* * * TYPE - 4 Fonts
                * */
                tvLastRelatedSearchTag.setTypeface(App.getTribuchet_MS());
                rlViewAllTagFour.setTypeface(App.getTribuchet_MS());

                tvLastRelatedSearchProductNameOne.setTypeface(App.getTribuchet_MS());
                tvLastRelatedSearchProductPriceOne.setTypeface(App.getTribuchet_MS());
                tvLastRelatedSearchProductNameTwo.setTypeface(App.getTribuchet_MS());
                tvLastRelatedSearchProductPriceTwo.setTypeface(App.getTribuchet_MS());
                tvLastRelatedSearchProductNameThree.setTypeface(App.getTribuchet_MS());
                tvLastRelatedSearchProductPriceThree.setTypeface(App.getTribuchet_MS());

            }
        }
    }


    private class TutorialAdapter extends PagerAdapter {

        ArrayList<DashboardHeaderViewpagerModel> arrList;
        Context ctx;

        public TutorialAdapter(Context context, ArrayList<DashboardHeaderViewpagerModel> arrListTutorialModel) {
            // TODO Auto-generated constructor stub
            this.arrList = arrListTutorialModel;
            ctx = context;
        }

        @Override
        public int getCount() {
            return arrList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View new_view = null;

            LayoutInflater inflater = getLayoutInflater();
            new_view = inflater.inflate(R.layout.row_dashboard_header_viewpager, null);

            SimpleDraweeView ivDtaa = (SimpleDraweeView) new_view.findViewById(R.id.pagerImg);

            if (arrList.get(position).pager_img != null && arrList.get(position).pager_img.length() > 0)
            {
                ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(arrList.get(position).pager_img))
                        .setResizeOptions(new ResizeOptions(SCREEN_WIDTH, 220))
                        .build();
                DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(imageRequest)
                        .setOldController(ivDtaa.getController())
                        .setAutoPlayAnimations(true)
                        .build();
                ivDtaa.setController(draweeController);
            }

            container.addView(new_view);
            return new_view;
        }
    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        showExitDialog();
    }
}
