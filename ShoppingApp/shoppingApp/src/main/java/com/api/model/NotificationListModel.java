package com.api.model;

/**
 * Created by dhaval.mehta on 05-Oct-2017.
 */

public class NotificationListModel {

    public String noti_id = "";
    public String noti_type = "";
    public String noti_time = "";
    public String noti_title = "";
    public String noti_image = "";
    public String noti_desc = "";
    public String noti_product_name = "";
    public String noti_product_price = "";
    public String noti_product_image = "";
    public String noti_product_estimate_dlvr_dt = "";

    public NotificationListModel(String n_id, String n_type, String n_time, String n_title, String n_image, String n_desc) {
        noti_id = n_id;
        noti_type = n_type;
        noti_time = n_time;
        noti_title = n_title;
        noti_image = n_image;
        noti_desc = n_desc;
    }

    public NotificationListModel(String n_id, String n_type, String n_time, String n_title, String n_desc,
                                 String n_prdt_name, String n_prdt_price, String n_prdt_image, String n_prdt_esmt_dt) {
        noti_id = n_id;
        noti_type = n_type;
        noti_time = n_time;
        noti_title = n_title;
        noti_desc = n_desc;

        noti_product_name = n_prdt_name;
        noti_product_price = n_prdt_price;
        noti_product_image = n_prdt_image;
        noti_product_estimate_dlvr_dt = n_prdt_esmt_dt;
    }
}
