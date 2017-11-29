package com.api.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by dhaval.mehta on 06-Oct-2017.
 */

public class ProductDetailMainModel {

    public String pr_item_name = "";
    public String pr_item_old_price = "";
    public String pr_item_new_price = "";
    public String pr_item_discount = "";

    // public ArrayList<PrDetailImageSlider> arrPrImageSlider;
    public static class PrDetailImageSlider {

        public String pr_slider_image = "";

        public PrDetailImageSlider(String image){
            pr_slider_image = image;
        }

    }


    public static class PrDetailProductSize {

        public String pr_product_size_id = "";
        public String pr_product_size = "";

        public PrDetailProductSize(String size){
            pr_product_size = size;
        }

    }


    public static class PrDetailProductColor {

        public String pr_product_color_id = "";
        public String pr_product_color = "";

        public PrDetailProductColor(String color){
            pr_product_color = color;
        }

    }


    public static class PrDetailProductExtraInfo {

        public String pr_product_extra_info_id = "";
        public String pr_product_extra_info_name = "";
        public String pr_product_extra_info_desc = "";

        public PrDetailProductExtraInfo(String title, String desc){
            pr_product_extra_info_name = title;
            pr_product_extra_info_desc = desc;
        }

    }


    public static class PrDetailProductSimmilarItems {

        public String pr_product_extra_sim_pr_id = "";
        public String pr_product_extra_sim_pr_name = "";
        public String pr_product_extra_sim_pr_old_price = "";
        public String pr_product_extra_sim_pr_new_price = "";
        public String pr_product_extra_sim_pr_discount = "";
        public String pr_product_extra_sim_pr_image = "";

        public PrDetailProductSimmilarItems(String name, String old_price, String new_price, String discount, String image){
            pr_product_extra_sim_pr_name = name;
            pr_product_extra_sim_pr_old_price = old_price;
            pr_product_extra_sim_pr_new_price = new_price;
            pr_product_extra_sim_pr_discount = discount;
            pr_product_extra_sim_pr_image = image;
        }

    }


    public static class PrDetailProductRecentlyViewed {

        public String pr_product_extra_rec_view_pr_id = "";
        public String pr_product_extra_rec_view_pr_name = "";
        public String pr_product_extra_rec_view_pr_old_price = "";
        public String pr_product_extra_rec_view_pr_new_price = "";
        public String pr_product_extra_rec_view_pr_discount = "";
        public String pr_product_extra_rec_view_pr_image = "";

        public PrDetailProductRecentlyViewed(String name, String old_price, String new_price, String discount, String image){
            pr_product_extra_rec_view_pr_name = name;
            pr_product_extra_rec_view_pr_old_price = old_price;
            pr_product_extra_rec_view_pr_new_price = new_price;
            pr_product_extra_rec_view_pr_discount = discount;
            pr_product_extra_rec_view_pr_image = image;
        }
    }
}
