package com.api.model;

/**
 * Created by dhaval.mehta on 25-Sep-2017.
 */

public class DashboardCategoriesModel {

    public String cate_id = "";
    public String cate_name = "";
    public String cate_price = "";
    public String cate_image = "";
    public String cate_desc = "";

    public DashboardCategoriesModel(String ct_id, String ct_name, String ct_price, String ct_img, String ct_desc)
    {
        cate_id = ct_id;
        cate_name = ct_name;
        cate_price = ct_price;
        cate_image = ct_img;
        cate_desc = ct_desc;
    }
}
