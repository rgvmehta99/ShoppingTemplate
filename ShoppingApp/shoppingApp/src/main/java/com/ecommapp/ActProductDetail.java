package com.ecommapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.api.model.NotificationListModel;
import com.api.model.ProductDetailMainModel;
import com.demoecommereceapp.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.squareup.picasso.Picasso;
import com.util.BlurTransformation;
import com.util.MaterialProgressBar;
import com.util.custom_gridview.GridRecyclerView;
import com.util.custom_gridview.ItemOffsetDecoration;
import com.util.expandable_layout.ExpandableLayout;

import java.util.ArrayList;

/**
 * Created by dhaval.mehta on 06-Oct-2017.
 */

public class ActProductDetail extends BaseActivity {

    String TAG = "==ActProductDetail==";

    //
    RelativeLayout rlNoData;
    TextView tvNoData;
    //
    MaterialProgressBar mterialProgress;
    NestedScrollView nsView;
    ImageView ivBackSub, ivBackgroundPallete;
    RecyclerView recyclerViewProductImage;
    TextView tvItemName, tvItemOldPrice, tvItemNewPrice, tvItemDiscount;
    RecyclerView recyclerViewSize; // Size - Horizontal i.e. S, M, L, Xl etc..
    GridRecyclerView gridRecyclerViewColor; // Color i.e. into Square box - color drable
    RecyclerView recyclerViewExtraInfo; // Style Info, Desc, Delivery Detail, Reviews with Expandable layout demo
    ImageView ivLike, ivShare;
    TextView tvAddToBag;
    RecyclerView recyclerViewSimilar; // Similar Products - Horizontal
    RecyclerView recyclerViewRecentlyView; // Recently View Items - Horizontal
    RelativeLayout rlBottom;
    BlurTransformation blurTransformation;


    //
    ArrayList<ProductDetailMainModel.PrDetailImageSlider> arryImageSlider;
    PrDetailImageSliderAdapter adapterPrImageSlider;

    //
    ArrayList<ProductDetailMainModel.PrDetailProductSize> arryProductSize;
    PrDetailProductSizeAdapter adapterPrProductSize;

    //
    ArrayList<ProductDetailMainModel.PrDetailProductColor> arryProductColor;
    PrDetailProductColorAdapter adapterProductColor;

    //
    ArrayList<ProductDetailMainModel.PrDetailProductExtraInfo> arryProductExtraInfo;
    PrDetailProductExtraInfoAdapter adapterProductExtraInfo;

    //
    ArrayList<ProductDetailMainModel.PrDetailProductSimmilarItems> arryProductSimilar;
    PrDetailProductSimilarItemsAdapter adapterProductSimilarItems;

    //
    ArrayList<ProductDetailMainModel.PrDetailProductRecentlyViewed> arryProductRecentlyView;
    PrDetailProductRecentlyViewAdapter adapterProductRecentlyView;

    int SCREEN_HEIGHT = 0, SCREEN_WIDTH = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            //
            ViewGroup.inflate(this, R.layout.act_product_detail, llContainerSub);
            App.showLogTAG(TAG);

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            SCREEN_HEIGHT = size.y;
            SCREEN_WIDTH = size.x;

            initialisation();
            setFonts();
            setClickEvents();
            setStaticData();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setDataAdapter();
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

            blurTransformation = new BlurTransformation(ActProductDetail.this, 15);

            //
            rlNoData = (RelativeLayout) findViewById(R.id.rlNoData);
            rlNoData.setVisibility(View.GONE);
            tvNoData = (TextView) findViewById(R.id.tvNoData);

            //
            mterialProgress = (MaterialProgressBar) findViewById(R.id.mterialProgress);
            mterialProgress.setVisibility(View.VISIBLE);

            rlBottom = (RelativeLayout) findViewById(R.id.rlBottom);
            rlBottom.setVisibility(View.GONE);
            nsView = (NestedScrollView) findViewById(R.id.nsView);
            nsView.setVisibility(View.GONE);

            ivBackSub = (ImageView) findViewById(R.id.ivBackSub);
            ivBackgroundPallete = (ImageView) findViewById(R.id.ivBackgroundPallete);

            recyclerViewProductImage = (RecyclerView) findViewById(R.id.recyclerViewProductImage);
            LinearLayoutManager hlManagerImgSld = new LinearLayoutManager(ActProductDetail.this, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewProductImage.setLayoutManager(hlManagerImgSld);
            recyclerViewProductImage.addItemDecoration(new PaddingItemDecoration(190)); // Padding

            tvItemName = (TextView) findViewById(R.id.tvItemName);
            tvItemOldPrice = (TextView) findViewById(R.id.tvItemOldPrice);
            tvItemNewPrice = (TextView) findViewById(R.id.tvItemNewPrice);
            tvItemDiscount = (TextView) findViewById(R.id.tvItemDiscount);

            recyclerViewSize = (RecyclerView) findViewById(R.id.recyclerViewSize);
            LinearLayoutManager hlManagerPrSize = new LinearLayoutManager(ActProductDetail.this, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewSize.setLayoutManager(hlManagerPrSize);
            recyclerViewSize.setNestedScrollingEnabled(false);

            gridRecyclerViewColor = (GridRecyclerView) findViewById(R.id.gridRecyclerViewColor);
            GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 5 );
            gridRecyclerViewColor.setLayoutManager(linearLayoutManager);
            gridRecyclerViewColor.addItemDecoration(new ItemOffsetDecoration(10));
            gridRecyclerViewColor.setNestedScrollingEnabled(false);

            recyclerViewExtraInfo = (RecyclerView) findViewById(R.id.recyclerViewExtraInfo);
            LinearLayoutManager vrManagerPrInfo = new LinearLayoutManager(ActProductDetail.this, LinearLayoutManager.VERTICAL, false);
            recyclerViewExtraInfo.setLayoutManager(vrManagerPrInfo);
            recyclerViewExtraInfo.setNestedScrollingEnabled(false);

            ivLike = (ImageView) findViewById(R.id.ivLike);
            ivShare = (ImageView) findViewById(R.id.ivShare);

            tvAddToBag = (TextView) findViewById(R.id.tvAddToBag);

            recyclerViewSimilar = (RecyclerView) findViewById(R.id.recyclerViewSimilar);
            LinearLayoutManager hlManagerPrSimilar = new LinearLayoutManager(ActProductDetail.this, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewSimilar.setLayoutManager(hlManagerPrSimilar);
            recyclerViewSimilar.setNestedScrollingEnabled(false);

            recyclerViewRecentlyView = (RecyclerView) findViewById(R.id.recyclerViewRecentlyView);
            LinearLayoutManager hlManagerPrRecently = new LinearLayoutManager(ActProductDetail.this, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewRecentlyView.setLayoutManager(hlManagerPrRecently);
            recyclerViewRecentlyView.setNestedScrollingEnabled(false);

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setFonts() {
        try
        {
            tvItemName.setTypeface(App.getTribuchet_MS());
            tvItemOldPrice.setTypeface(App.getTribuchet_MS());
            tvItemNewPrice.setTypeface(App.getTribuchet_MS());
            tvItemDiscount.setTypeface(App.getTribuchet_MS());
            tvAddToBag.setTypeface(App.getTribuchet_MS());
            tvNoData.setTypeface(App.getTribuchet_MS());

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setClickEvents() {
        try
        {
            //
            nsView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    int diff = scrollY - oldScrollY;
                    if (diff >= 0)
                    {
                        //App.showLog("Scroll Up");
                        Animation slideDown = AnimationUtils.loadAnimation(ActProductDetail.this, R.anim.slide_down);
                        slideDown.setDuration(100);
                        rlBottom.startAnimation(slideDown);
                        rlBottom.setVisibility(View.GONE);
                    }
                    else
                    {
                        //App.showLog("Scroll Down");
                        Animation slideUp = AnimationUtils.loadAnimation(ActProductDetail.this, R.anim.slide_up);
                        slideUp.setDuration(100);
                        rlBottom.startAnimation(slideUp);
                        rlBottom.setVisibility(View.VISIBLE);
                    }
                }
            });

            //
            ivBackSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            //
            ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    App.showToastShort(ActProductDetail.this, "You have added this product into Favourite list.");
                }
            });

            //
            ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    App.showToastShort(ActProductDetail.this, "Share detail with social media.");
                }
            });

            //
            tvAddToBag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    App.showToastShort(ActProductDetail.this, "Item successfully added to your cart.");
                }
            });

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setStaticData() {
        try
        {
            setPrImageSliderList();
            setPrProductSizeList();
            setPrProductColorList();
            setPrProductExtraInfo();
            setPrProductSimilarItems();
            setPrProductRecentlyView();

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setDataAdapter() {
        try
        {
            rlBaseMainHeader.setVisibility(View.GONE);
            mterialProgress.setVisibility(View.GONE);
            nsView.setVisibility(View.VISIBLE);

            //
            tvItemNewPrice.setText(App._RS + " " + "7000.00");
            tvItemOldPrice.setText(App._RS + " " + "15000.00");
            tvItemOldPrice.setPaintFlags(tvItemOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();
            tvItemOldPrice.setText(tvItemOldPrice.getText(), TextView.BufferType.SPANNABLE);
            Spannable spannable = (Spannable) tvItemOldPrice.getText();
            spannable.setSpan(STRIKE_THROUGH_SPAN, 0, tvItemOldPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            //
            if (arryImageSlider != null && arryImageSlider.size() > 0)
            {
                adapterPrImageSlider = new PrDetailImageSliderAdapter(ActProductDetail.this, arryImageSlider);
                recyclerViewProductImage.setAdapter(adapterPrImageSlider);
                adapterPrImageSlider.notifyDataSetChanged();


                final Bitmap image = App.getBitmapFromURL(arryImageSlider.get(0).pr_slider_image);
                Palette.from(image).generate(new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                        if (vibrantSwatch != null) {
                            ivBackgroundPallete.setBackgroundColor(vibrantSwatch.getRgb());
                            // (vibrantSwatch.getRgb()); // (vibrantSwatch.getTitleTextColor()); // (vibrantSwatch.getBodyTextColor());
                        }
                    }
                });

            }


            //
            if (arryProductSize != null && arryProductSize.size() > 0)
            {
                adapterPrProductSize = new PrDetailProductSizeAdapter(ActProductDetail.this, arryProductSize);
                recyclerViewSize.setAdapter(adapterPrProductSize);
                adapterPrProductSize.notifyDataSetChanged();
            }


            //
            if (arryProductColor != null && arryProductColor.size() > 0)
            {
                adapterProductColor = new PrDetailProductColorAdapter(ActProductDetail.this, arryProductColor);
                gridRecyclerViewColor.setAdapter(adapterProductColor);
                adapterProductColor.notifyDataSetChanged();
            }


            //
            if (arryProductExtraInfo != null && arryProductExtraInfo.size() > 0)
            {
                adapterProductExtraInfo = new PrDetailProductExtraInfoAdapter(ActProductDetail.this, arryProductExtraInfo);
                recyclerViewExtraInfo.setAdapter(adapterProductExtraInfo);
                adapterProductExtraInfo.notifyDataSetChanged();
            }


            //
            if (arryProductSimilar != null && arryProductSimilar.size() > 0)
            {
                adapterProductSimilarItems = new PrDetailProductSimilarItemsAdapter(ActProductDetail.this, arryProductSimilar);
                recyclerViewSimilar.setAdapter(adapterProductSimilarItems);
                adapterProductSimilarItems.notifyDataSetChanged();
            }


            //
            if (arryProductRecentlyView != null && arryProductRecentlyView.size() > 0)
            {
                adapterProductRecentlyView = new PrDetailProductRecentlyViewAdapter(ActProductDetail.this, arryProductRecentlyView);
                recyclerViewRecentlyView.setAdapter(adapterProductRecentlyView);
                adapterProductRecentlyView.notifyDataSetChanged();
            }

        } catch (Exception e) {e.printStackTrace();}
    }




    private void setPrImageSliderList() {
        try
        {
            arryImageSlider = new ArrayList<>();

            arryImageSlider.add(new ProductDetailMainModel.PrDetailImageSlider("https://ae01.alicdn.com/kf/HTB1B4ttIpXXXXa6XFXXq6xXFXXX6/Hot-Stylish-men-s-blazer-suit-2016-Men-s-Casual-Slim-fit-Blazer-jacket-Coat-Suits.jpg"));
            arryImageSlider.add(new ProductDetailMainModel.PrDetailImageSlider("https://pic.elinkmall.com/HW/HW186/HW186L.jpg"));
            arryImageSlider.add(new ProductDetailMainModel.PrDetailImageSlider("https://ae01.alicdn.com/kf/HTB1dROmKVXXXXaUaXXXq6xXFXXXC/202430732/HTB1dROmKVXXXXaUaXXXq6xXFXXXC.jpg"));
            arryImageSlider.add(new ProductDetailMainModel.PrDetailImageSlider("https://ae01.alicdn.com/kf/HTB1rM7LJVXXXXb2XVXXq6xXFXXXf/2017-New-Fashion-Good-quality-Men-s-Slim-Fit-Blazer-Jacket-Male-Business-Suit-black-navy.jpg"));
            arryImageSlider.add(new ProductDetailMainModel.PrDetailImageSlider("https://ae01.alicdn.com/kf/HTB1p6xwLpXXXXXJXpXXq6xXFXXXp/Men-Blazer-slim-fit-suit-jacket-Brand-New-Spring-autumn-outwear-coat-costume-homme-black-navy.jpg"));
            arryImageSlider.add(new ProductDetailMainModel.PrDetailImageSlider("https://ae01.alicdn.com/kf/HTB1DTG4LFXXXXb3XVXXq6xXFXXXh/2017New-homme-blazers-Men-slim-fit-suit-jacket-blue-navy-black-velvet-blazer-2017-spring-autumn.jpg"));
            arryImageSlider.add(new ProductDetailMainModel.PrDetailImageSlider("https://ae01.alicdn.com/kf/HTB1S5OzOVXXXXcXXpXXq6xXFXXXX/NEW-Mens-Fashion-Brand-Blazer-British-s-Style-casual-Slim-Fit-suit-jacket-male-Blazers-men.jpg"));
            arryImageSlider.add(new ProductDetailMainModel.PrDetailImageSlider("https://ae01.alicdn.com/kf/HTB106cpaqmWQ1JjSZPhq6xCJFXaq/Men-Casual-Blazer-2017-Spring-Mens-Suits-Blazer-Single-Button-Men-Slim-Fit-Blazer-For-Men.jpg"));
            arryImageSlider.add(new ProductDetailMainModel.PrDetailImageSlider("https://ae01.alicdn.com/kf/HTB16YGWQpXXXXaRXVXXq6xXFXXXZ/FGKKS-2017-New-Arrival-Brand-Clothing-Masculine-Blazer-Men-Fashion-Solid-Color-Male-Suits-Casual-Suit.jpg"));
            arryImageSlider.add(new ProductDetailMainModel.PrDetailImageSlider("http://blzjeans.com/29924-108086-thickbox/blazzer-sweat-homme-gris-chine-jack-and-jones.jpg"));
            arryImageSlider.add(new ProductDetailMainModel.PrDetailImageSlider("https://ae01.alicdn.com/kf/HTB1AvVYQFXXXXcAXpXXq6xXFXXXI/FGKKS-2017-New-Spring-Autumn-thin-Casual-Men-Blazer-Cotton-Slim-England-Suit-Blaser-Masculino-Male.jpg"));

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setPrProductSizeList() {
        try
        {
            arryProductSize = new ArrayList<>();

            arryProductSize.add(new ProductDetailMainModel.PrDetailProductSize("Ex.S"));
            arryProductSize.add(new ProductDetailMainModel.PrDetailProductSize("S"));
            arryProductSize.add(new ProductDetailMainModel.PrDetailProductSize("M"));
            arryProductSize.add(new ProductDetailMainModel.PrDetailProductSize("L"));
            arryProductSize.add(new ProductDetailMainModel.PrDetailProductSize("XL"));
            arryProductSize.add(new ProductDetailMainModel.PrDetailProductSize("XXL"));
            arryProductSize.add(new ProductDetailMainModel.PrDetailProductSize("XXXL"));
            arryProductSize.add(new ProductDetailMainModel.PrDetailProductSize("4XL"));
            arryProductSize.add(new ProductDetailMainModel.PrDetailProductSize("5XL"));

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setPrProductColorList() {
        try
        {
            arryProductColor = new ArrayList<>();

            arryProductColor.add(new ProductDetailMainModel.PrDetailProductColor("#F44336"));
            arryProductColor.add(new ProductDetailMainModel.PrDetailProductColor("#EB3573"));
            arryProductColor.add(new ProductDetailMainModel.PrDetailProductColor("#E91E63"));
            arryProductColor.add(new ProductDetailMainModel.PrDetailProductColor("#9C27B0"));
            arryProductColor.add(new ProductDetailMainModel.PrDetailProductColor("#673AB7"));
            arryProductColor.add(new ProductDetailMainModel.PrDetailProductColor("#2196F3"));
            arryProductColor.add(new ProductDetailMainModel.PrDetailProductColor("#009688"));
            arryProductColor.add(new ProductDetailMainModel.PrDetailProductColor("#4CAF50"));
            arryProductColor.add(new ProductDetailMainModel.PrDetailProductColor("#8BC34A"));
            arryProductColor.add(new ProductDetailMainModel.PrDetailProductColor("#CDDC39"));
            arryProductColor.add(new ProductDetailMainModel.PrDetailProductColor("#FFEB3B"));
            arryProductColor.add(new ProductDetailMainModel.PrDetailProductColor("#FFC107"));


        } catch (Exception e) {e.printStackTrace();}
    }


    private void setPrProductExtraInfo() {
        try
        {
            arryProductExtraInfo = new ArrayList<>();

            arryProductExtraInfo.add(new ProductDetailMainModel.PrDetailProductExtraInfo(
                    "Style",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."
            ));

            arryProductExtraInfo.add(new ProductDetailMainModel.PrDetailProductExtraInfo(
                    "Fabric",
                    "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages."
            ));

            arryProductExtraInfo.add(new ProductDetailMainModel.PrDetailProductExtraInfo(
                    "Size Chart",
                    "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source."
            ));

            arryProductExtraInfo.add(new ProductDetailMainModel.PrDetailProductExtraInfo(
                    "Return Policy",
                    "Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32."
            ));


        } catch (Exception e) {e.printStackTrace();}
    }


    private void setPrProductSimilarItems() {
        try
        {
            arryProductSimilar = new ArrayList<>();

            arryProductSimilar.add(new ProductDetailMainModel.PrDetailProductSimmilarItems(
                    "Plaid Blue Shirt for Man - Casual Long sleeve",
                    "1345.00",
                    "750.00",
                    "40%",
                    "https://ae01.alicdn.com/kf/HTB1.V0eIFXXXXagXFXXq6xXFXXX6/2017-Plaid-Shirts-Men-Casual-Long-Sleeve-Shirts-Mens-Dress-Fashion-Shirts-Man-2017-Plus-size.jpg"
            ));


            arryProductSimilar.add(new ProductDetailMainModel.PrDetailProductSimmilarItems(
                    "Denim-Shirts-5XL-Cotton-Slim-Fit-Pockets-Camiseta-Masculina",
                    "1400.00",
                    "850.55",
                    "32%",
                    "https://ae01.alicdn.com/kf/HTB1PJ9mNXXXXXamXXXXq6xXFXXXh/Plus-Size-Men-Denim-Shirts-5XL-Cotton-Slim-Fit-Pockets-Camiseta-Masculina-2017-Long-Sleeve-Blue.jpg"
            ));

            arryProductSimilar.add(new ProductDetailMainModel.PrDetailProductSimmilarItems(
                    "Spring-Summer-Long-Sleeve-T-Shirts",
                    "1200.00",
                    "550.00",
                    "55%",
                    "https://ae01.alicdn.com/kf/HTB1Fl88NXXXXXcoXpXXq6xXFXXXf/100-Cotton-2017-Spring-Summer-Brand-Clothing-Men-Clothes-T-Shirt-Long-Sleeve-T-Shirts-Mens.jpg_640x640.jpg"
            ));

            arryProductSimilar.add(new ProductDetailMainModel.PrDetailProductSimmilarItems(
                    "Asian-Fashion-Long-Sleeve-Mandarin-Collar-Mens-Shirts-Male-Casual-Linen-Shirt",
                    "880.00",
                    "550.00",
                    "25%",
                    "https://ae01.alicdn.com/kf/HTB1pa2wJFXXXXX2XpXXq6xXFXXX5/2017-Asian-Fashion-Long-Sleeve-Mandarin-Collar-Mens-Shirts-Male-Casual-Linen-Shirt-Men-Plus-Size.jpg_640x640.jpg"
            ));

            arryProductSimilar.add(new ProductDetailMainModel.PrDetailProductSimmilarItems(
                    "Camisas-Fashion-Slim-Fit-Men-Clothes-Men-Casual-shirt",
                    "1250.00",
                    "1000.00",
                    "20%",
                    "https://ae01.alicdn.com/kf/HTB1vVqrIFXXXXcgXVXXq6xXFXXXA/Camisas-Fashion-design-Slim-Fit-Men-Clothes-Men-Casual-shirt-Spring-Autumn-Men-s-Long-sleeve.jpg"
            ));

            arryProductSimilar.add(new ProductDetailMainModel.PrDetailProductSimmilarItems(
                    "Gilet-de-costume-man-suit-cotton-vests-wedding-gray-casual",
                    "2250.00",
                    "1350.00",
                    "65%",
                    "https://ae01.alicdn.com/kf/HTB1ZSUHHVXXXXXIXXXXq6xXFXXXz/2016-gilet-de-costume-man-suit-cotton-vests-for-men-formal-waistcoat-wedding-gray-casual-dress.jpg_640x640.jpg"
            ));


            arryProductSimilar.add(new ProductDetailMainModel.PrDetailProductSimmilarItems(
                    "Casual-Patchwork-Stripe-Denim-Shirt-Outerwear-Blue",
                    "650.00",
                    "650.00",
                    "0%",
                    "https://ae01.alicdn.com/wsphoto/v0/1336078187/2013-Spring-Autumn-Fashion-Mens-Clothes-Man-Designer-Casual-Patchwork-Stripe-Denim-Shirt-Outerwear-Blue-Jean.jpg"
            ));

            arryProductSimilar.add(new ProductDetailMainModel.PrDetailProductSimmilarItems(
                    "New-Autumn-Fashion-Brand-font-b-Men-b-font-Clothes-Slim-Fit",
                    "1150.00",
                    "750.00",
                    "37%",
                    "https://ae01.alicdn.com/kf/HTB1nNGISpXXXXc3XXXXq6xXFXXXQ/2017-New-Autumn-Fashion-Brand-font-b-Men-b-font-Clothes-Slim-Fit-font-b-Men.jpg"
            ));



        } catch (Exception e) {e.printStackTrace();}
    }


    private void setPrProductRecentlyView() {
        try
        {
            arryProductRecentlyView = new ArrayList<>();

            arryProductRecentlyView.add(new ProductDetailMainModel.PrDetailProductRecentlyViewed(
                    "New Man Blue - Fit Yumblur-Cotton-Plain-With-Shadow-Linen-Shaded",
                    "1345.00",
                    "750.00",
                    "40%",
                    "http://38.media.tumblr.com/e615b632a02b13b9b72a1fdf218841e5/tumblr_nad94m7fb01tq2dijo1_500.jpg"
            ));


            arryProductRecentlyView.add(new ProductDetailMainModel.PrDetailProductRecentlyViewed(
                    "Vintage-Henry-T-Shirts-Casual-Long-Sleeve",
                    "1400.00",
                    "850.55",
                    "32%",
                    "https://ae01.alicdn.com/kf/HTB15nDNKFXXXXaMapXXq6xXFXXXV/Brand-Designer-Men-Cotton-Vintage-Henry-T-Shirts-Casual-Long-Sleeve-High-quality-Men-old-color.jpg_640x640.jpg"
            ));

            arryProductRecentlyView.add(new ProductDetailMainModel.PrDetailProductRecentlyViewed(
                    "Gilet-de-costume-man-suit-cotton-vests-blazzer-black-creame-dark-blue",
                    "1200.00",
                    "550.00",
                    "55%",
                    "https://i.pinimg.com/736x/e9/b9/e5/e9b9e55aa9ffaf69eae1266979f2e0d2.jpg"
            ));

            arryProductRecentlyView.add(new ProductDetailMainModel.PrDetailProductRecentlyViewed(
                    "Asian-Fashion-Long-Sleeve-Mandarin-Collar-Mens-Shirts-Male-Casual-Linen-Shirt",
                    "880.00",
                    "550.00",
                    "25%",
                    "https://ae01.alicdn.com/kf/HTB1pa2wJFXXXXX2XpXXq6xXFXXX5/2017-Asian-Fashion-Long-Sleeve-Mandarin-Collar-Mens-Shirts-Male-Casual-Linen-Shirt-Men-Plus-Size.jpg_640x640.jpg"
            ));

            arryProductRecentlyView.add(new ProductDetailMainModel.PrDetailProductRecentlyViewed(
                    "Camisas-Fashion-Slim-Fit-Men-Clothes-Men-Casual-shirt",
                    "1250.00",
                    "1000.00",
                    "20%",
                    "https://ae01.alicdn.com/kf/HTB1vVqrIFXXXXcgXVXXq6xXFXXXA/Camisas-Fashion-design-Slim-Fit-Men-Clothes-Men-Casual-shirt-Spring-Autumn-Men-s-Long-sleeve.jpg"
            ));

            arryProductRecentlyView.add(new ProductDetailMainModel.PrDetailProductRecentlyViewed(
                    "Gilet-de-costume-man-suit-cotton-vests-wedding-gray-casual",
                    "2250.00",
                    "1350.00",
                    "65%",
                    "https://ae01.alicdn.com/kf/HTB1ZSUHHVXXXXXIXXXXq6xXFXXXz/2016-gilet-de-costume-man-suit-cotton-vests-for-men-formal-waistcoat-wedding-gray-casual-dress.jpg_640x640.jpg"
            ));


            arryProductRecentlyView.add(new ProductDetailMainModel.PrDetailProductRecentlyViewed(
                    "Ethnic-wear-indian-men-mens-traditional-wear-indian",
                    "950.00",
                    "650.00",
                    "10%",
                    "https://i.pinimg.com/736x/19/f8/5c/19f85cd304632ffef38b5f35f05035ed--ethnic-wear-indian-men-mens-traditional-wear-indian.jpg"
            ));

            arryProductRecentlyView.add(new ProductDetailMainModel.PrDetailProductRecentlyViewed(
                    "New_assassin_pulover",
                    "1150.00",
                    "750.00",
                    "37%",
                    "http://throwingmoneyatthescreen.com/shared_images/products/new_assassin_pulover.png"
            ));



        } catch (Exception e) {e.printStackTrace();}
    }






    public class PrDetailImageSliderAdapter extends RecyclerView.Adapter<PrDetailImageSliderAdapter.VersionViewHolder> {
        ArrayList<ProductDetailMainModel.PrDetailImageSlider> mArrayList;
        Context mContext;
        int width;


        public PrDetailImageSliderAdapter(Context context, ArrayList<ProductDetailMainModel.PrDetailImageSlider> arrayList) {
            mArrayList = arrayList;
            mContext = context;
        }

        @Override
        public PrDetailImageSliderAdapter.VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_pr_detail_image_slider,
                    viewGroup, false);
            PrDetailImageSliderAdapter.VersionViewHolder viewHolder = new PrDetailImageSliderAdapter.VersionViewHolder(view);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final PrDetailImageSliderAdapter.VersionViewHolder versionViewHolder, final int position) {
            try
            {
                if (mArrayList != null && mArrayList.size() > 0)
                {
                    ProductDetailMainModel.PrDetailImageSlider model = mArrayList.get(position);

                    if (model.pr_slider_image != null && model.pr_slider_image.length() > 0)
                    {
                        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(model.pr_slider_image))
                                .setResizeOptions(new ResizeOptions(270, 370))
                                .build();
                        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                                .setImageRequest(imageRequest)
                                .setOldController(versionViewHolder.ivPrSlideImage.getController())
                                .setAutoPlayAnimations(true)
                                .build();
                        versionViewHolder.ivPrSlideImage.setController(draweeController);
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
            SimpleDraweeView ivPrSlideImage;

            public VersionViewHolder(View itemView) {
                super(itemView);

                ivPrSlideImage = (SimpleDraweeView) itemView.findViewById(R.id.ivPrSlideImage);
            }
        }
    }


    public class PrDetailProductSizeAdapter extends RecyclerView.Adapter<PrDetailProductSizeAdapter.VersionViewHolder> {
        ArrayList<ProductDetailMainModel.PrDetailProductSize> mArrayList;
        Context mContext;
        int width;


        public PrDetailProductSizeAdapter(Context context, ArrayList<ProductDetailMainModel.PrDetailProductSize> arrayList) {
            mArrayList = arrayList;
            mContext = context;
        }

        @Override
        public PrDetailProductSizeAdapter.VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_pr_detail_product_size,
                    viewGroup, false);
            PrDetailProductSizeAdapter.VersionViewHolder viewHolder = new PrDetailProductSizeAdapter.VersionViewHolder(view);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final PrDetailProductSizeAdapter.VersionViewHolder versionViewHolder, final int position) {
            try
            {
                if (mArrayList != null && mArrayList.size() > 0)
                {
                    ProductDetailMainModel.PrDetailProductSize model = mArrayList.get(position);

                    if (model.pr_product_size != null && model.pr_product_size.length() > 0)
                    {
                        versionViewHolder.tvProductSize.setText(model.pr_product_size);
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
            TextView tvProductSize;


            public VersionViewHolder(View itemView) {
                super(itemView);

                tvProductSize = (TextView) itemView.findViewById(R.id.tvProductSize);
                tvProductSize.setTypeface(App.getTribuchet_MS());
            }
        }
    }


    public class PrDetailProductColorAdapter extends RecyclerView.Adapter<PrDetailProductColorAdapter.VersionViewHolder> {
        ArrayList<ProductDetailMainModel.PrDetailProductColor> mArrayList;
        Context mContext;


        public PrDetailProductColorAdapter(Context context, ArrayList<ProductDetailMainModel.PrDetailProductColor> arrayList) {
            mArrayList = arrayList;
            mContext = context;
        }

        @Override
        public PrDetailProductColorAdapter.VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_pr_detail_product_color, viewGroup, false);
            PrDetailProductColorAdapter.VersionViewHolder viewHolder = new PrDetailProductColorAdapter.VersionViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final PrDetailProductColorAdapter.VersionViewHolder versionViewHolder, final int position) {
            try
            {
                ProductDetailMainModel.PrDetailProductColor model = mArrayList.get(position);

                GradientDrawable shape = new GradientDrawable();
                shape.setShape(GradientDrawable.RECTANGLE);
                shape.setCornerRadii(new float[] { 8, 8, 8, 8, 0, 0, 0, 0 });
                shape.setColor(Color.parseColor(model.pr_product_color));
                shape.setStroke(0, ContextCompat.getColor(mContext, R.color.status_bar_random_1));
                versionViewHolder.ivProductColor.setBackgroundDrawable(shape);

            } catch (Exception e) {e.printStackTrace();}

        }

        @Override
        public int getItemCount() {
            return mArrayList.size();
        }


        class VersionViewHolder extends RecyclerView.ViewHolder {
            ImageView ivProductColor;

            public VersionViewHolder(View itemView) {
                super(itemView);

                ivProductColor = (ImageView) itemView.findViewById(R.id.ivProductColor);
            }
        }
    }


    public class PrDetailProductExtraInfoAdapter extends RecyclerView.Adapter<PrDetailProductExtraInfoAdapter.VersionViewHolder> {
        ArrayList<ProductDetailMainModel.PrDetailProductExtraInfo> mArrayList;
        Context mContext;
        private static final int UNSELECTED = -1;
        private int selectedItem = UNSELECTED;


        public PrDetailProductExtraInfoAdapter(Context context, ArrayList<ProductDetailMainModel.PrDetailProductExtraInfo> arrayList) {
            mArrayList = arrayList;
            mContext = context;
        }

        @Override
        public PrDetailProductExtraInfoAdapter.VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_pr_detail_product_extra_info,
                    viewGroup, false);
            PrDetailProductExtraInfoAdapter.VersionViewHolder viewHolder = new PrDetailProductExtraInfoAdapter.VersionViewHolder(view);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final PrDetailProductExtraInfoAdapter.VersionViewHolder versionViewHolder,
                                     final int position) {
            try
            {
                if (mArrayList != null && mArrayList.size() > 0)
                {
                    ProductDetailMainModel.PrDetailProductExtraInfo model = mArrayList.get(position);
                    //
                    if (model.pr_product_extra_info_name != null && model.pr_product_extra_info_name.length() > 0 )
                    {
                        versionViewHolder.tvExtraInfotitle.setText(model.pr_product_extra_info_name);
                    }
                    //
                    if (model.pr_product_extra_info_desc != null && model.pr_product_extra_info_desc.length() > 0 )
                    {
                        versionViewHolder.tvExtraInfoDesc.setText(model.pr_product_extra_info_desc);
                    }

                    //
                    versionViewHolder.tvExtraInfotitle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (versionViewHolder != null) {
                                versionViewHolder.tvExtraInfotitle.setSelected(false);
                                versionViewHolder.expandableLayout.collapse();
                            }

                            if (position == selectedItem) {
                                selectedItem = UNSELECTED;
                            } else {
                                versionViewHolder.tvExtraInfotitle.setSelected(true);
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


        class VersionViewHolder extends RecyclerView.ViewHolder implements ExpandableLayout.OnExpansionUpdateListener
        {

            TextView tvExtraInfotitle, tvExtraInfoDesc;
            ExpandableLayout expandableLayout;
            ImageView ivDropDownIcon;

            public VersionViewHolder(View itemView) {
                super(itemView);

                tvExtraInfotitle = (TextView) itemView.findViewById(R.id.tvExtraInfotitle);
                tvExtraInfoDesc = (TextView) itemView.findViewById(R.id.tvExtraInfoDesc);
                ivDropDownIcon = (ImageView) itemView.findViewById(R.id.ivDropDownIcon);
                expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandableLayout);
                expandableLayout.setOnExpansionUpdateListener(this);

                tvExtraInfotitle.setTypeface(App.getTribuchet_MS());
                tvExtraInfoDesc.setTypeface(App.getTribuchet_MS());
            }

            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                recyclerViewExtraInfo.smoothScrollToPosition(getAdapterPosition());
                ivDropDownIcon.setRotation(expansionFraction * 180);
            }
        }
    }


    public class PrDetailProductSimilarItemsAdapter extends RecyclerView.Adapter<PrDetailProductSimilarItemsAdapter.VersionViewHolder> {
        ArrayList<ProductDetailMainModel.PrDetailProductSimmilarItems> mArrayList;
        Context mContext;
        int width;


        public PrDetailProductSimilarItemsAdapter(Context context, ArrayList<ProductDetailMainModel.PrDetailProductSimmilarItems> arrayList) {
            mArrayList = arrayList;
            mContext = context;
        }

        @Override
        public PrDetailProductSimilarItemsAdapter.VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_pr_detail_product_simmilar_items,
                    viewGroup, false);
            PrDetailProductSimilarItemsAdapter.VersionViewHolder viewHolder = new PrDetailProductSimilarItemsAdapter.VersionViewHolder(view);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final PrDetailProductSimilarItemsAdapter.VersionViewHolder versionViewHolder, final int position) {
            try
            {
                if (mArrayList != null && mArrayList.size() > 0)
                {
                    ProductDetailMainModel.PrDetailProductSimmilarItems model = mArrayList.get(position);
                    //
                    if (model.pr_product_extra_sim_pr_name != null && model.pr_product_extra_sim_pr_name.length() > 0)
                    {
                        versionViewHolder.tvProductName.setText(model.pr_product_extra_sim_pr_name);
                    }
                    //
                    if (model.pr_product_extra_sim_pr_old_price != null && model.pr_product_extra_sim_pr_old_price.length() > 0)
                    {
                        versionViewHolder.tvItemOldPrice.setText(App._RS + " " + model.pr_product_extra_sim_pr_old_price);
                        versionViewHolder.tvItemOldPrice.setPaintFlags(versionViewHolder.tvItemOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    //
                    if (model.pr_product_extra_sim_pr_new_price != null && model.pr_product_extra_sim_pr_new_price.length() > 0)
                    {
                        versionViewHolder.tvItemNewPrice.setText(App._RS + " " + model.pr_product_extra_sim_pr_new_price);
                    }
                    //
                    if (model.pr_product_extra_sim_pr_discount != null && model.pr_product_extra_sim_pr_discount.length() > 0)
                    {
                        versionViewHolder.tvItemDiscount.setText(model.pr_product_extra_sim_pr_discount);
                    }
                    //
                    if (model.pr_product_extra_sim_pr_image != null && model.pr_product_extra_sim_pr_image.length() > 0)
                    {
                        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(model.pr_product_extra_sim_pr_image))
                                .setResizeOptions(new ResizeOptions(160, 180))
                                .build();
                        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                                .setImageRequest(imageRequest)
                                .setOldController(versionViewHolder.ivImage.getController())
                                .setAutoPlayAnimations(true)
                                .build();
                        versionViewHolder.ivImage.setController(draweeController);
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
            TextView tvProductName, tvItemOldPrice, tvItemNewPrice, tvItemDiscount;
            SimpleDraweeView ivImage;

            public VersionViewHolder(View itemView) {
                super(itemView);

                tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
                tvItemOldPrice = (TextView) itemView.findViewById(R.id.tvItemOldPrice);
                tvItemNewPrice = (TextView) itemView.findViewById(R.id.tvItemNewPrice);
                tvItemDiscount = (TextView) itemView.findViewById(R.id.tvItemDiscount);
                ivImage = (SimpleDraweeView) itemView.findViewById(R.id.ivImage);
                //
                tvProductName.setTypeface(App.getTribuchet_MS());
                tvItemOldPrice.setTypeface(App.getTribuchet_MS());
                tvItemNewPrice.setTypeface(App.getTribuchet_MS());
                tvItemDiscount.setTypeface(App.getTribuchet_MS());
            }
        }
    }


    public class PrDetailProductRecentlyViewAdapter extends RecyclerView.Adapter<PrDetailProductRecentlyViewAdapter.VersionViewHolder> {
        ArrayList<ProductDetailMainModel.PrDetailProductRecentlyViewed> mArrayList;
        Context mContext;
        int width;


        public PrDetailProductRecentlyViewAdapter(Context context, ArrayList<ProductDetailMainModel.PrDetailProductRecentlyViewed> arrayList) {
            mArrayList = arrayList;
            mContext = context;
        }

        @Override
        public PrDetailProductRecentlyViewAdapter.VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_pr_detail_product_simmilar_items,
                    viewGroup, false);
            PrDetailProductRecentlyViewAdapter.VersionViewHolder viewHolder = new PrDetailProductRecentlyViewAdapter.VersionViewHolder(view);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final PrDetailProductRecentlyViewAdapter.VersionViewHolder versionViewHolder, final int position) {
            try
            {
                if (mArrayList != null && mArrayList.size() > 0)
                {
                    ProductDetailMainModel.PrDetailProductRecentlyViewed model = mArrayList.get(position);
                    //
                    if (model.pr_product_extra_rec_view_pr_name != null && model.pr_product_extra_rec_view_pr_name.length() > 0)
                    {
                        versionViewHolder.tvProductName.setText(model.pr_product_extra_rec_view_pr_name);
                    }
                    //
                    if (model.pr_product_extra_rec_view_pr_old_price != null && model.pr_product_extra_rec_view_pr_old_price.length() > 0)
                    {
                        versionViewHolder.tvItemOldPrice.setText(App._RS + " " + model.pr_product_extra_rec_view_pr_old_price);
                        versionViewHolder.tvItemOldPrice.setPaintFlags(versionViewHolder.tvItemOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    //
                    if (model.pr_product_extra_rec_view_pr_new_price != null && model.pr_product_extra_rec_view_pr_new_price.length() > 0)
                    {
                        versionViewHolder.tvItemNewPrice.setText(App._RS + " " + model.pr_product_extra_rec_view_pr_new_price);
                    }
                    //
                    if (model.pr_product_extra_rec_view_pr_discount != null && model.pr_product_extra_rec_view_pr_discount.length() > 0)
                    {
                        versionViewHolder.tvItemDiscount.setText(model.pr_product_extra_rec_view_pr_discount);
                    }
                    //
                    if (model.pr_product_extra_rec_view_pr_image != null && model.pr_product_extra_rec_view_pr_image.length() > 0)
                    {
                        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(model.pr_product_extra_rec_view_pr_image))
                                .setResizeOptions(new ResizeOptions(160, 180))
                                .build();
                        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                                .setImageRequest(imageRequest)
                                .setOldController(versionViewHolder.ivImage.getController())
                                .setAutoPlayAnimations(true)
                                .build();
                        versionViewHolder.ivImage.setController(draweeController);
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
            TextView tvProductName, tvItemOldPrice, tvItemNewPrice, tvItemDiscount;
            SimpleDraweeView ivImage;

            public VersionViewHolder(View itemView) {
                super(itemView);

                tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
                tvItemOldPrice = (TextView) itemView.findViewById(R.id.tvItemOldPrice);
                tvItemNewPrice = (TextView) itemView.findViewById(R.id.tvItemNewPrice);
                tvItemDiscount = (TextView) itemView.findViewById(R.id.tvItemDiscount);
                ivImage = (SimpleDraweeView) itemView.findViewById(R.id.ivImage);
                //
                tvProductName.setTypeface(App.getTribuchet_MS());
                tvItemOldPrice.setTypeface(App.getTribuchet_MS());
                tvItemNewPrice.setTypeface(App.getTribuchet_MS());
                tvItemDiscount.setTypeface(App.getTribuchet_MS());
            }
        }
    }


    
    public class PaddingItemDecoration extends RecyclerView.ItemDecoration {
        private final int size;

        public PaddingItemDecoration(int size) {
            this.size = size;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            // Apply offset only to first item
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.left += size;
            }
        }
    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        App.myFinishActivity(ActProductDetail.this);
    }
}