package com.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.ecommapp.App;

import java.io.ByteArrayOutputStream;


public class SharePrefrences {
    App appObj;

    public SharePrefrences(App obj) {
        this.appObj = obj;
    }

    public void clearPrefValues() {
        SharedPreferences.Editor pref = appObj.getSharedPreferences(
                appObj.PREF_NAME, Context.MODE_PRIVATE).edit();
        pref.clear();
        pref.commit();
    }

    public void setPref(String key, String val) {
        SharedPreferences.Editor pref = appObj.getSharedPreferences(
                appObj.PREF_NAME, Context.MODE_PRIVATE).edit();
        pref.putString(key, val);
        pref.commit();
    }

    public void setPref(String key, int val) {
        SharedPreferences.Editor pref = appObj.getSharedPreferences(
                appObj.PREF_NAME, Context.MODE_PRIVATE).edit();
        pref.putInt(key, val);
        pref.commit();
    }

    public void setPref(String key, boolean val) {
        SharedPreferences.Editor pref = appObj.getSharedPreferences(
                appObj.PREF_NAME, Context.MODE_PRIVATE).edit();
        pref.putBoolean(key, val);
        pref.commit();
    }

    public String getStringPref(String key) {
        SharedPreferences pref = appObj.getSharedPreferences(appObj.PREF_NAME,
                Context.MODE_PRIVATE);
        return pref.getString(key, "");
    }

    public int getIntPref(String key) {
        SharedPreferences pref = appObj.getSharedPreferences(appObj.PREF_NAME,
                Context.MODE_PRIVATE);
        return pref.getInt(key, 0);
    }

    public boolean getBooleanPref(String key) {
        SharedPreferences pref = appObj.getSharedPreferences(appObj.PREF_NAME,
                Context.MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }

    public String getValueString(Context context, String Key) {
        SharedPreferences settings = context.getSharedPreferences(
                appObj.PREF_NAME, 0);

        return settings.getString(Key, "");
    }

    public void setValueString(Context context, String Key, String Value) {
        SharedPreferences settings = context.getSharedPreferences(
                appObj.PREF_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Key, Value);

        editor.commit();
    }

  /*  public void saveArray(Context context, String[] array, String arrayName) {

        SharedPreferences settings = context.getSharedPreferences(
                appObj.PREF_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = settings.edit();

        editor.putInt(arrayName + "_size", array.length);

        for (int i = 0; i < array.length; i++) {

            editor.putString(arrayName + "_" + i, array[i]);

            // Log.e("arrayname==", arrayName + "_" + i + "" + array[i] + "");
        }
        // Log.e("Save Succeed?", editor.commit() + "");
    }*/

    public void saveArray(Context context, String[] array, String arrayName) {
        SharedPreferences settings = context.getSharedPreferences(
                appObj.PREF_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = settings.edit();

        editor.putInt(arrayName + "_size", array.length);

        for (int i = 0; i < array.length; i++) {

            editor.putString(arrayName + "_" + i, array[i]);

            //Log.e("arrayname==", arrayName + "_" + i + "" + array[i] + "");
        }
        editor.commit();

        //Log.e("Save Succeed?", editor.commit() + "");
    }

    public String loadArrayValue(String arrayName, int index) {
        SharedPreferences settings = appObj.getSharedPreferences(
                appObj.PREF_NAME, Context.MODE_PRIVATE);

        // int size = settings.getInt(arrayName + "_size", 0);
        /*
         * String array[] = new String[size]; for (int i = 0; i < size; i++)
		 * array[i] = settings.getString(arrayName + "_" + i, null);
		 */

        // Log.e("arrayname", arrayName);
        // Log.e("index", index + "");

        String temp = settings.getString(arrayName + "_" + index, null);

        return temp;
    }

   /* public int getArraySize(Context context, String arrayName) {
        SharedPreferences settings = context.getSharedPreferences(
                appObj.PREF_NAME, Context.MODE_PRIVATE);
        return settings.getInt(arrayName + "_size", 0);
    }*/
    public int getArraySize(Context context, String arrayName) {
        SharedPreferences settings = context.getSharedPreferences(appObj.PREF_NAME, Context.MODE_PRIVATE);
        return settings.getInt(arrayName + "_size", 0);
    }


    public void storeImage(Context context, String key, Bitmap val) {
        SharedPreferences.Editor pref = appObj.getSharedPreferences(
                appObj.PREF_NAME, Context.MODE_PRIVATE).edit();


        pref.putString(key, encodeTobase64(val));

        pref.commit();
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }




}