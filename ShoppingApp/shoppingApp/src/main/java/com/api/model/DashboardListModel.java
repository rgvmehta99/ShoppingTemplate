package com.api.model;

import java.util.ArrayList;

/**
 * Created by dhaval.mehta on 25-Sep-2017.
 */

public class DashboardListModel {

    public String dash_main_cate_id = "";
    public String dash_main_cate_type = "";
    public String dash_main_cate_name = "";
    public String dash_main_cate_image = "";

    public ArrayList<DashboardCategoriesModel> arryCategories;

    public DashboardListModel(String id, String type, String name,
                              String image, ArrayList<DashboardCategoriesModel> arrCategories)
    {
        dash_main_cate_id = id;
        dash_main_cate_type = type;
        dash_main_cate_name = name;
        dash_main_cate_image = image;
        arryCategories = arrCategories;
    }
}
