package com.api.model;

/**
 * Created by dhaval.mehta on 10-Oct-2017.
 */

public class MyOrderListModel {

    public String order_id = "";
    public String order_ship_to = "";
    public String order_number = "";
    public String order_status = "";
    public String order_date = "";
    public String order_item_name = "";
    public String order_item_price = "";
    public String order_item_image = "";

    public MyOrderListModel(String ship_to, String ord_no, String ord_status, String ord_dt,
                            String ord_item_nm, String ord_item_prc, String ord_item_img)
    {
        order_ship_to = ship_to;
        order_number = ord_no;
        order_status = ord_status;
        order_date = ord_dt;
        order_item_name = ord_item_nm;
        order_item_price = ord_item_prc;
        order_item_image = ord_item_img;
    }
}
