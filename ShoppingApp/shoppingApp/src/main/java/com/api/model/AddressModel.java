package com.api.model;

/**
 * Created by dhaval.mehta on 27-Oct-2017.
 */

public class AddressModel {

    public String adr_user_name = "";
    public String adr_address_one = "";
    public String adr_address_two = "";
    public String adr_pincode = "";
    public String adr_city = "";
    public String adr_state = "";
    public String adr_country = "";

    public AddressModel(String name, String address1, String address2, String pincode, String city, String state, String country) {
        adr_user_name = name;
        adr_address_one = address1;
        adr_address_two = address2;
        adr_pincode = pincode;
        adr_city = city;
        adr_state = state;
        adr_country = country;
    }
}
