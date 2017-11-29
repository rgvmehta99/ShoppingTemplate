package com.api.model;

import java.util.ArrayList;

/**
 * Created by dhaval.mehta on 11-Oct-2017.
 */

public class CartListModel {

    public String cart_item_id = "";
    public String cart_item_name = "";
    public String cart_item_price = "";
    public String cart_item_image = "";
    public String cart_item_qty = "";
    public String cart_item_size = "";

    public ArrayList<CartSizeModel> sizeList;
    public ArrayList<CartQtyModel> qtyList;

    public CartListModel(String item_id, String item_name, String item_price,
                         String item_img, String item_qty, String item_size,
                         ArrayList<CartSizeModel> arraySList, ArrayList<CartQtyModel> arrayQList)
    {
        cart_item_id = item_id;
        cart_item_name = item_name;
        cart_item_price = item_price;
        cart_item_image = item_img;
        cart_item_qty = item_qty;
        cart_item_size = item_size;
        sizeList = arraySList;
        qtyList = arrayQList;
    }


    public static class CartSizeModel{
        public String size_nm = "";

        public CartSizeModel(String size)
        {
            size_nm = size;
        }

        @Override
        public String toString() {
            return size_nm;
        }
    }


    public static class CartQtyModel{
        public String qty_nm = "";

        public CartQtyModel(String qty)
        {
            qty_nm = qty;
        }

        @Override
        public String toString() {
            return qty_nm;
        }
    }
}
