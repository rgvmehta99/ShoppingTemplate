package com.ecommapp.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demoecommereceapp.R;
import com.ecommapp.ActChooesAddress;
import com.ecommapp.App;
import com.util.AppFlags;

/**
 * Created by dhaval.mehta on 27-Oct-2017.
 */

public class AddressFragment extends Fragment {

    String TAG = "==AddressFragment==";

    int type;
    String fragId = "";
    View viewFragment;
    //
    TextView tvTagBillingAddress, tvBATagChooesAddress, tvBAUserName, tvBAAddressOne, tvBAAddressTwo,
            tvBAPincodeCity, tvBAStateCountry, tvBATagChange;
    TextView tvTagDeleveryAddress, tvDATagChooesAddress, tvDAUserName, tvDAAddressOne, tvDAAddressTwo,
            tvDAPincodeCity, tvDAStateCountry, tvDATagChange;
    TextView tvNext;
    LinearLayout llBAAddressContent, llDAAddressContent;
    //
    String strFrom = "", tagAdrUserName = "", tagAdrAddressOne = "", tagAdrAddressTwo = "",
            tagAdrPincode = "", tagAdrCity = "", tagAdrState = "", tagAdrCountry = "";


    public AddressFragment() {
    }


    @SuppressLint("ValidFragment")
    public AddressFragment(int type, String strFid) {
        this.type = type;
        this.fragId = strFid;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try
        {
            App.showLogTAG(TAG);
            viewFragment = inflater.inflate(R.layout.frag_address, container, false);

            // getIntenetData();
            initialization();
            setFonts();
            setClickEvents();
            setStaticArrayList();

        } catch (Exception e) {e.printStackTrace();}

        return viewFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        getIntenetData();
    }

    private void getIntenetData() {
        try
        {
            App.showLog("==getIntenetData==");
            Bundle bundle = this.getArguments();
            if (getArguments() != null)
            {
                App.showLog("==getIntenetData==bundle!=null");
                //
                if (bundle.getString(App.ITAG_FROM) != null &&
                        bundle.getString(App.ITAG_FROM).length() > 0) {
                    strFrom = bundle.getString(App.ITAG_FROM);
                    App.showLog(TAG + "==strFrom==" + strFrom);
                }

                //
                if (bundle.getString(AppFlags.tagAdrUserName) != null &&
                        bundle.getString(AppFlags.tagAdrUserName).length() > 0) {
                    tagAdrUserName = bundle.getString(AppFlags.tagAdrUserName);
                    App.showLog(TAG + "==tagAdrUserName==" + tagAdrUserName);
                }
                //
                if (bundle.getString(AppFlags.tagAdrAddressOne) != null &&
                        bundle.getString(AppFlags.tagAdrAddressOne).length() > 0) {
                    tagAdrAddressOne = bundle.getString(AppFlags.tagAdrAddressOne);
                    App.showLog(TAG + "==tagAdrAddressOne==" + tagAdrAddressOne);
                }
                //
                if (bundle.getString(AppFlags.tagAdrAddressTwo) != null &&
                        bundle.getString(AppFlags.tagAdrAddressTwo).length() > 0) {
                    tagAdrAddressTwo = bundle.getString(AppFlags.tagAdrAddressTwo);
                    App.showLog(TAG + "==tagAdrAddressTwo==" + tagAdrAddressTwo);
                }
                //
                if (bundle.getString(AppFlags.tagAdrPincode) != null &&
                        bundle.getString(AppFlags.tagAdrPincode).length() > 0) {
                    tagAdrPincode = bundle.getString(AppFlags.tagAdrPincode);
                    App.showLog(TAG + "==tagAdrPincode==" + tagAdrPincode);
                }
                //
                if (bundle.getString(AppFlags.tagAdrCity) != null &&
                        bundle.getString(AppFlags.tagAdrCity).length() > 0) {
                    tagAdrCity = bundle.getString(AppFlags.tagAdrCity);
                    App.showLog(TAG + "==tagAdrCity==" + tagAdrCity);
                }
                //
                if (bundle.getString(AppFlags.tagAdrState) != null &&
                        bundle.getString(AppFlags.tagAdrState).length() > 0) {
                    tagAdrState = bundle.getString(AppFlags.tagAdrState);
                    App.showLog(TAG + "==tagAdrState==" + tagAdrState);
                }
                //
                if (bundle.getString(AppFlags.tagAdrCountry) != null &&
                        bundle.getString(AppFlags.tagAdrCountry).length() > 0) {
                    tagAdrCountry = bundle.getString(AppFlags.tagAdrCountry);
                    App.showLog(TAG + "==tagAdrCountry==" + tagAdrCountry);
                }
            }

        } catch (Exception e) {e.printStackTrace();}
    }

    private void setClickEvents() {
        try
        {
            //
            tvBATagChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setClassName("com.demoecommereceapp", "com.ecommapp.ActChooesAddress");
                    intent.putExtra(AppFlags.iResCode, "" + App.BILLING_ADDRESS_I_RES_CODE);
                    startActivityForResult(intent, App.BILLING_ADDRESS_I_RES_CODE);
                    getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                }
            });

        } catch (Exception e) {e.printStackTrace();}
    }


    private void initialization() {
        try
        {
            tvTagBillingAddress = (TextView) viewFragment.findViewById(R.id.tvTagBillingAddress);
            tvBATagChooesAddress = (TextView) viewFragment.findViewById(R.id.tvBATagChooesAddress);
            tvBAUserName = (TextView) viewFragment.findViewById(R.id.tvBAUserName);
            tvBAAddressOne = (TextView) viewFragment.findViewById(R.id.tvBAAddressOne);
            tvBAAddressTwo = (TextView) viewFragment.findViewById(R.id.tvBAAddressTwo);
            tvBAPincodeCity = (TextView) viewFragment.findViewById(R.id.tvBAPincodeCity);
            tvBAStateCountry = (TextView) viewFragment.findViewById(R.id.tvBAStateCountry);
            tvBATagChange = (TextView) viewFragment.findViewById(R.id.tvBATagChange);
            //
            tvTagDeleveryAddress = (TextView) viewFragment.findViewById(R.id.tvTagDeleveryAddress);
            tvDATagChooesAddress = (TextView) viewFragment.findViewById(R.id.tvDATagChooesAddress);
            tvDAUserName = (TextView) viewFragment.findViewById(R.id.tvDAUserName);
            tvDAAddressOne = (TextView) viewFragment.findViewById(R.id.tvDAAddressOne);
            tvDAAddressTwo = (TextView) viewFragment.findViewById(R.id.tvDAAddressTwo);
            tvDAPincodeCity = (TextView) viewFragment.findViewById(R.id.tvDAPincodeCity);
            tvDAStateCountry = (TextView) viewFragment.findViewById(R.id.tvDAStateCountry);
            tvDATagChange = (TextView) viewFragment.findViewById(R.id.tvDATagChange);
            //
            tvNext = (TextView) viewFragment.findViewById(R.id.tvNext);
            //
            llBAAddressContent = (LinearLayout) viewFragment.findViewById(R.id.llBAAddressContent);
            llDAAddressContent = (LinearLayout) viewFragment.findViewById(R.id.llDAAddressContent);

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setFonts() {
        try
        {
            tvTagBillingAddress.setTypeface(App.getTribuchet_MS());
            tvBATagChooesAddress.setTypeface(App.getTribuchet_MS());
            tvBAUserName.setTypeface(App.getTribuchet_MS());
            tvBAAddressOne.setTypeface(App.getTribuchet_MS());
            tvBAAddressTwo.setTypeface(App.getTribuchet_MS());
            tvBAPincodeCity.setTypeface(App.getTribuchet_MS());
            tvBAStateCountry.setTypeface(App.getTribuchet_MS());
            tvBATagChange.setTypeface(App.getTribuchet_MS());
            //
            tvTagDeleveryAddress.setTypeface(App.getTribuchet_MS());
            tvDATagChooesAddress.setTypeface(App.getTribuchet_MS());
            tvDAUserName.setTypeface(App.getTribuchet_MS());
            tvDAAddressOne.setTypeface(App.getTribuchet_MS());
            tvDAAddressTwo.setTypeface(App.getTribuchet_MS());
            tvDAPincodeCity.setTypeface(App.getTribuchet_MS());
            tvDAStateCountry.setTypeface(App.getTribuchet_MS());
            tvDATagChange.setTypeface(App.getTribuchet_MS());
            //
            tvNext.setTypeface(App.getTribuchet_MS());

        } catch (Exception e) {e.printStackTrace();}
    }


    private void setStaticArrayList() {
        try
        {

        } catch (Exception e) {e.printStackTrace();}
    }
}
