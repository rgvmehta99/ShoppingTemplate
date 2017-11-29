package com.ecommapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.api.model.AddressModel;
import com.demoecommereceapp.R;
import com.util.AppFlags;
import com.util.MaterialProgressBar;
import com.util.expandable_layout.ExpandableLayout;

import java.util.ArrayList;

/**
 * Created by dhaval.mehta on 27-Oct-2017.
 */

public class ActChooesAddress extends BaseActivity {

    String TAG = "==ActChooesAddress==";
    //
    RelativeLayout rlNoData;
    TextView tvNoData;
    //
    RelativeLayout llMain;
    RecyclerView recyclerView;
    MaterialProgressBar mterialProgress;
    FloatingActionButton fabAdd;

    String strFrom = "", iResCode = "";
    //
    ArrayList<AddressModel> arryAddressList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            //
            ViewGroup.inflate(this, R.layout.act_chooes_address, llContainerSub);
            App.showLogTAG(TAG);

            getIntentData();
            initialisation();
            // setFonts();
            setClickEvents();
            setStaticAddressList();


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
        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                //
                if (bundle.getString(App.ITAG_FROM) != null &&
                        bundle.getString(App.ITAG_FROM).length() > 0) {
                    strFrom = bundle.getString(App.ITAG_FROM);
                    App.showLog(TAG + "==strFrom==" + strFrom);
                }
                if (bundle.getString(AppFlags.iResCode) != null &&
                        bundle.getString(AppFlags.iResCode).length() > 0) {
                    iResCode = bundle.getString(AppFlags.iResCode);
                    App.showLog(TAG + "==iResCode==" + iResCode);
                }
            }
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
            tvTitle.setText("Choose Address");

            //
            rlNoData = (RelativeLayout) findViewById(R.id.rlNoData);
            rlNoData.setVisibility(View.GONE);
            tvNoData = (TextView) findViewById(R.id.tvNoData);

            //
            mterialProgress = (MaterialProgressBar) findViewById(R.id.mterialProgress);
            mterialProgress.setVisibility(View.VISIBLE);

            llMain = (RelativeLayout) findViewById(R.id.llMain);
            llMain.setVisibility(View.GONE);

            fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setAdapter() {
        try {
            if (arryAddressList != null && arryAddressList.size() > 0)
            {
                mterialProgress.setVisibility(View.GONE);
                rlNoData.setVisibility(View.GONE);
                llMain.setVisibility(View.VISIBLE);
                //
                ChooesAddressAdapter adapter = new ChooesAddressAdapter(this, arryAddressList);
                recyclerView.setAdapter(adapter);

            }
            else
            {
                mterialProgress.setVisibility(View.GONE);
                llMain.setVisibility(View.GONE);
                rlNoData.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setClickEvents() {
        try
        {
            //
            fabAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iv = new Intent(ActChooesAddress.this, ActAddAddress.class);
                    iv.putExtra(App.ITAG_FROM, "ActChooesAddress");
                    App.myStartActivity(ActChooesAddress.this, iv);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setStaticAddressList() {
        try {

            arryAddressList = new ArrayList<>();

            arryAddressList.add(new AddressModel(
                    "Jenny Methew",
                    "Lake Merced Golf Club",
                    "2300 Junipero Serra Blvd, Daly City",
                    "CA 94015",
                    "Los Angeles",
                    "California",
                    "United States"
            ));

            arryAddressList.add(new AddressModel(
                    "Jason Nick Stallon",
                    "5075 Gosford Rd",
                    "Bakersfield",
                    "CA 93313",
                    "California",
                    "California",
                    "USA"
            ));

            arryAddressList.add(new AddressModel(
                    "Roxy Susane",
                    "200 Eastern Pkwy",
                    "Brooklyn",
                    "NY 11238",
                    "New York",
                    "NY",
                    "USA"
            ));

            arryAddressList.add(new AddressModel(
                    "Morphie Demello",
                    "Curti Rd, Durgabhat",
                    "Betora",
                    "403401",
                    "Ponda - Goa",
                    "GOA",
                    "India"
            ));

            arryAddressList.add(new AddressModel(
                    "Laxmi Kalmakar",
                    "New SKF Colony, 3rd Floor, Office No. - 218,219 & 222",
                    "Gokhale Plaza Condominium, Survey No. - 186/2/1A",
                    "411033",
                    "Chinchwad - Pune",
                    "Maharashtra",
                    "India"
            ));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class ChooesAddressAdapter extends RecyclerView.Adapter<ChooesAddressAdapter.VersionViewHolder> {

        ArrayList<AddressModel> mArrayList;
        Context mContext;
        private static final int UNSELECTED = -1;


        public ChooesAddressAdapter(Context context, ArrayList<AddressModel> arrayList) {
            mArrayList = arrayList;
            mContext = context;
        }

        @Override
        public ChooesAddressAdapter.VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_chooes_address_item,
                    viewGroup, false);
            ChooesAddressAdapter.VersionViewHolder viewHolder = new ChooesAddressAdapter.VersionViewHolder(view);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final ChooesAddressAdapter.VersionViewHolder versionViewHolder, final int position) {
            try
            {
                if (mArrayList != null && mArrayList.size() > 0)
                {
                    final AddressModel model = mArrayList.get(position);
                    //
                    if (model.adr_user_name != null && model.adr_user_name.length() > 0)
                    {
                        versionViewHolder.tvUserName.setText(model.adr_user_name);
                    }
                    //
                    if (model.adr_address_one != null && model.adr_address_one.length() > 0)
                    {
                        versionViewHolder.tvAddressOne.setText(model.adr_address_one);
                    }
                    //
                    if (model.adr_address_two != null && model.adr_address_two.length() > 0)
                    {
                        versionViewHolder.tvAddressTwo.setText(model.adr_address_two);
                    }
                    //
                    if (model.adr_pincode != null && model.adr_pincode.length() > 0 &&
                            model.adr_city != null && model.adr_city.length() > 0)
                    {
                        versionViewHolder.tvPincodeCity.setText(model.adr_pincode + " - " + model.adr_city);
                    }
                    //
                    if (model.adr_state != null && model.adr_state.length() > 0 &&
                            model.adr_country != null && model.adr_country.length() > 0)
                    {
                        versionViewHolder.tvStateCountry.setText(model.adr_state + ", " + model.adr_country);
                    }


                    //
                    versionViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Bundle conData = new Bundle();
                            conData.putString(App.ITAG_FROM, "ActChooesAddress");
                            conData.putString(AppFlags.tagAdrUserName, model.adr_user_name);
                            conData.putString(AppFlags.tagAdrAddressOne, model.adr_address_one);
                            conData.putString(AppFlags.tagAdrAddressTwo, model.adr_address_two);
                            conData.putString(AppFlags.tagAdrPincode, model.adr_pincode);
                            conData.putString(AppFlags.tagAdrCity, model.adr_city);
                            conData.putString(AppFlags.tagAdrState, model.adr_state);
                            conData.putString(AppFlags.tagAdrCountry, model.adr_country);

                            Intent intent = new Intent();
                            intent.putExtras(conData);
                            // setResult(App.BILLING_ADDRESS_I_RES_CODE, intent);
                            setResult(Integer.parseInt(iResCode), intent);
                            finish();
                            animFinishActivity();
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
            CardView cardView;
            TextView tvUserName, tvAddressOne, tvAddressTwo, tvPincodeCity, tvStateCountry;

            public VersionViewHolder(View itemView) {
                super(itemView);

                // //
                cardView = (CardView) itemView.findViewById(R.id.cardView);
                tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
                tvAddressOne = (TextView) itemView.findViewById(R.id.tvAddressOne);
                tvAddressTwo = (TextView) itemView.findViewById(R.id.tvAddressTwo);
                tvPincodeCity = (TextView) itemView.findViewById(R.id.tvPincodeCity);
                tvStateCountry = (TextView) itemView.findViewById(R.id.tvStateCountry);

                /*
                * Set Fonts
                * */
                tvUserName.setTypeface(App.getTribuchet_MS());
                tvAddressOne.setTypeface(App.getTribuchet_MS());
                tvAddressTwo.setTypeface(App.getTribuchet_MS());
                tvPincodeCity.setTypeface(App.getTribuchet_MS());
                tvStateCountry.setTypeface(App.getTribuchet_MS());
            }

            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                recyclerView.smoothScrollToPosition(getAdapterPosition());
            }
        }
    }

}
