package com.api.model;

import java.util.ArrayList;

/**
 * Created by dhaval.mehta on 19-Sep-2017.
 */

public class SideMenuParentModel {

    public String parent_id;
    public String parent_name;
    public ArrayList<SideMenuChildModel> arryChildList;
    public boolean isChild;

    public SideMenuParentModel(String p_id, String p_name, ArrayList<SideMenuChildModel> arr, boolean b) {
        parent_id = p_id;
        parent_name = p_name;
        arryChildList = arr;
        isChild = b;
    }


    public SideMenuParentModel(String p_id, String p_name, boolean b) {
        parent_id = p_id;
        parent_name = p_name;
        isChild = b;
    }
}
