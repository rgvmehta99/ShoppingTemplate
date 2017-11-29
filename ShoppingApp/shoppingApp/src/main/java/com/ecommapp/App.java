package com.ecommapp;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.demoecommereceapp.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.util.SharePrefrences;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.fabric.sdk.android.Fabric;


public class App extends Application {

    private static final String TAG = "==App==";
    public static boolean blnFullscreenActvitity = false;
    public static String DB_NAME = "ecommapp.db";
    public static String ITAG_FROM = "from";
    static Context mContext;
    public static String DB_PATH = Environment.getExternalStorageDirectory().toString() + "/" + App.APP_FOLDERNAME ;
    public static String APP_FOLDERNAME = ".E Comm App";
    public static String PREF_NAME = "e_comm_app";
    public static SharePrefrences sharePrefrences;
    public static String _RS = "₹";
    public static String PLACE_ORDER_ADDRESS_FRAGMENT = "Address";
    public static String PLACE_ORDER_PAYMENT_SHIPPING_FRAGMENT = "Payment & Shipping";
    public static int BILLING_ADDRESS_I_RES_CODE = 301;
    public static int DELIVERY_ADDRESS_I_RES_CODE = 401;

    //
    static Typeface tfMontserrat_alternates_regular, tfOpen_sans_bold, tfTribhuchet_ms;

    //
    public static int[] arrStatusBarColor = {
            R.color.status_bar_random_1,
            R.color.status_bar_random_2,
            R.color.status_bar_random_3,
            R.color.status_bar_random_4,
            R.color.status_bar_random_5,
            R.color.status_bar_random_6,
            R.color.status_bar_random_7,
            R.color.status_bar_random_8,
            R.color.status_bar_random_9,
            //R.color.status_bar_random_10,
            //R.color.status_bar_random_11,
            //R.color.status_bar_random_12,
            R.color.status_bar_random_13,
            R.color.status_bar_random_14,
            R.color.status_bar_random_15,
            R.color.status_bar_random_16,
            R.color.status_bar_random_17
    };

    //
    public static String SIDE_MENU_HOME = "Home";
    public static String SIDE_MENU_MEN = "Men";
    public static String SIDE_MENU_WOMEN = "Women";
    public static String SIDE_MENU_KIDS = "Kids";
    public static String SIDE_MENU_LATEST_PRODUCTS = "Latest Product";
    public static String SIDE_MENU_SPECIAL_OFFER = "Special Offer";
    public static String SIDE_MENU_PROFILE = "Profile";
    public static String SIDE_MENU_LOGOUT = "Logout";
    //
    public static int[] SIDE_MENU_ICONS = {
                R.drawable.ic_home,
                R.drawable.ic_men,
                R.drawable.ic_women,
                R.drawable.ic_kids,
                R.drawable.ic_latest_collection,
                R.drawable.ic_special_offers,
                R.drawable.ic_profile,
                R.drawable.ic_logout
        };


    //

    public static String PROFILE_MAIN_MY_ACCOUNTS = "My Account";
    public static String PROFILE_MAIN_NOTIFICATIONS = "Notifications";
    public static String PROFILE_MAIN_CHANGE_PASSWORD = "Change Password";
    public static String PROFILE_MAIN_SETTINGS = "Settings";
    public static String PROFILE_MAIN_MY_ORDERS = "My Orders";
    public static String PROFILE_MAIN_MY_ADDRESS = "My Address";
    public static String PROFILE_MAIN_MY_WISH_LIST = "My Wish List";
    public static String PROFILE_MAIN_MY_FAVOURITES = "My Favourites";
    public static String PROFILE_MAIN_LOGOUT = "Logout";

    //
    public static int[] PROFILE_MAIN_ICONS = {
            R.drawable.ic_my_account,
            R.drawable.ic_notifications_second,
            R.drawable.ic_change_password,
            R.drawable.ic_settings,
            R.drawable.ic_my_order,
            R.drawable.ic_my_address,
            R.drawable.ic_my_wish_list,
            R.drawable.ic_my_favorite,
            R.drawable.ic_logout_new
    };

    //
    public static String DASHBOARD_TYPE_NEW_ARRIVALS = "newArrivals";
    public static String DASHBOARD_TYPE_ADD_BANNER = "addBanner";
    public static String DASHBOARD_TYPE_DISCOUNT_ITEMS = "discountItems";
    public static String DASHBOARD_TYPE_LAST_RELATED_SEARCH = "lastRelatedSearch";

    //
    public static String NOTIFICAION_TYPE_INFO = "info";
    public static String NOTIFICAION_TYPE_PERSONAL = "personal";


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    // application on create methode for the create and int base values
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Fresco.initialize(this);
            MultiDex.install(this);
            Fabric.with(this, new Crashlytics());
            mContext = getApplicationContext();
            sharePrefrences = new SharePrefrences(App.this);
            getFontMontserrat_Alternates_Regular();
            getFontOpen_Sans_Bold();
            createAppFolder();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createAppFolder() {
        try {
            String sdCardPath = Environment.getExternalStorageDirectory().toString();
            File file2 = new File(sdCardPath + "/" + App.APP_FOLDERNAME + "");
            if (!file2.exists()) {
                if (!file2.mkdirs()) {
                    System.out.println("==Create Directory " + App.APP_FOLDERNAME + "====");
                } else {
                    System.out.println("==No--1Create Directory " + App.APP_FOLDERNAME + "====");
                }
            } else {
                System.out.println("== already created---No--2Create Directory " + App.APP_FOLDERNAME + "====");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String setLabelText(String newString , String defaultString) {
        if(newString !=null) {
            return newString;
        } else {
            showLog("==setLabelText====LABEL===null===newString====set default text==");
            return defaultString;
        }
    }


    public static String setAlertText(String newString , String defaultString) {
        if(newString !=null) {
            return newString;
        } else {
            showLog("==setAlertText===null===newString====set default text==");
            return defaultString;
        }
    }


    public static String getddMMMyy(String convert_date_string){
        String final_date="";
        if (convert_date_string !=null) {

            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat  outputFormat = new SimpleDateFormat("dd MMM ''yy h:mm a");
                String inputDateStr = convert_date_string;
                Date date = null;
                date = inputFormat.parse(inputDateStr);
                //String outputDateStr = outputFormat.format(date);
                final_date = outputFormat.format(date);
                final_date.toLowerCase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return final_date;
    }


    public static String getTimeAgo(String convert_date_string) {
        String finalDate = "";
        try
        {
            // Convert receive date string to UTC time zone
            SimpleDateFormat utc_time_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            utc_time_format.setTimeZone(TimeZone.getTimeZone("UTC"));
            String utc_time_date = convert_date_string;
            Date dt_utc_format = null;
            dt_utc_format = utc_time_format.parse(utc_time_date);

            SimpleDateFormat local_formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date_utc = local_formate.format(dt_utc_format);
            App.showLog("==1111==UTC time zone date==" + date_utc + "//**//");

            // Convert UTC timezone date to device local time zone
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();
            inputFormat.setTimeZone(tz);
            String inputDateStr = date_utc;
            App.showLog("==2222==Local time zone date==" + inputDateStr + "//**//");
            Date dt = null;
            dt = inputFormat.parse(inputDateStr);

            // Get timestamp from deive's local time zone date and time
            long timeStamp = 0l;
            if (inputDateStr != null && inputDateStr.length() > 0) {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = (Date)formatter.parse(inputDateStr);
                timeStamp = date.getTime();
            }

            if (timeStamp < 1000000000000L) {
                // if timestamp given in seconds, convert to millis
                timeStamp *= 1000;
            }

            // Diff current timestamp  and Receive string timestamp
            long diff = 0;
            diff = new Date().getTime() - timeStamp;
//            App.showLog("==diff==" + diff);
//            App.showLog("==timeStamp==" + timeStamp);
            double seconds = Math.abs(diff) / 1000;
            double minutes = seconds / 60;
            double hours = minutes / 60;
            double days = hours / 24;
            double years = days / 365;

//            App.showLog("==seconds==" + seconds);
//            App.showLog("==minutes==" + minutes);
//            App.showLog("==hours==" + hours);


            long now = System.currentTimeMillis();
            if (timeStamp > now || timeStamp <= 0)
            {
                finalDate =  "In the future";
            }
            else if (seconds > 2 && seconds < 60)
            {
                finalDate =  "Just now";
            }
            else if (seconds > 60 && minutes < 2)
            {
                finalDate = "1 minute ago";
            }
            else if (minutes > 2 && minutes < 60)
            {
                finalDate =  ((int)minutes) + " minutes ago";
            }
            else if (minutes == 60)
            {
                finalDate =  ((int)minutes) + " hour ago";
            }
            else if (minutes > 60 && minutes < 61)
            {
                finalDate =  ((int)minutes) + " hour ago";
            }
            else if (minutes > 61 && hours < 24)
            {
                if (((int)hours) == 1) {
                    finalDate =  ((int)hours) + " hour ago";
                } else {
                    finalDate =  ((int)hours) + " hours ago";
                }
            }
            else if (hours > 24 && hours < 36)
            {
                SimpleDateFormat format1 = new SimpleDateFormat("hh:mm a");
                format1.setTimeZone(tz);
                finalDate = format1.format(dt);
                finalDate =  "Yesterday at " + finalDate;
            }
            else
            {
                SimpleDateFormat format1 = new SimpleDateFormat("d");
                String date = format1.format(dt);
                App.showLogTAG("==getTimeAgo==date==" + date);

                if(date.endsWith("1") && !date.endsWith("11")) {
                    format1 = new SimpleDateFormat("d'st' MMMM yyyy, hh:mm a");
                    format1.setTimeZone(tz);
                }
                else if(date.endsWith("2") && !date.endsWith("12")) {
                    format1 = new SimpleDateFormat("d'nd' MMMM yyyy, hh:mm a");
                    format1.setTimeZone(tz);
                }
                else if(date.endsWith("3") && !date.endsWith("13")) {
                    format1 = new SimpleDateFormat("d'rd' MMMM yyyy, hh:mm a");
                    format1.setTimeZone(tz);
                }
                else {
                    format1 = new SimpleDateFormat("d'th' MMMM yyyy, hh:mm a");
                    format1.setTimeZone(tz);
                }
                finalDate = format1.format(dt);
                finalDate =  "" + finalDate;
            }
        } catch (Exception e) {e.printStackTrace();}

        return finalDate;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getCurrentDateTime() {
        String current_date = "";
        Calendar c = Calendar.getInstance();
        //System.out.println("Current time => " + c.getTime());

        SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        current_date = postFormater.format(c.getTime());

        return  current_date;
    }


    public static Typeface getFontMontserrat_Alternates_Regular() {
        tfMontserrat_alternates_regular = Typeface.createFromAsset(mContext.getAssets(), "fonts/montserrat_alternates_regular.ttf");
        return tfMontserrat_alternates_regular;
    }

    public static Typeface getTribuchet_MS() {
        tfTribhuchet_ms = Typeface.createFromAsset(mContext.getAssets(), "fonts/trebuchet_ms.ttf");
        return tfTribhuchet_ms;
    }


    public static Typeface getFontOpen_Sans_Bold() {
        tfOpen_sans_bold = Typeface.createFromAsset(mContext.getAssets(), "fonts/open_sans_bold.ttf");
        return tfOpen_sans_bold;
    }


    public static  void GenerateKeyHash() {
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getApplicationContext().getPackageName(),
                    PackageManager.GET_SIGNATURES); //GypUQe9I2FJr2sVzdm1ExpuWc4U= android pc -2 key
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                App.showLog("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //
    public static void showToastLong(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showToastShort(Context context, String strMessage) {
        Toast.makeText(context, strMessage, Toast.LENGTH_SHORT).show();
    }

    //
    public static void showSnackBar(View view, String strMessage) {
        Snackbar snackbar = Snackbar.make(view, strMessage, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.BLACK);
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public static void showSnackBarLong(View view, String strMessage) {
        Snackbar.make(view, strMessage, Snackbar.LENGTH_LONG).show();
    }

    //
    public static void showLog(String strMessage) {
        Log.v("==App==", "--strMessage--" + strMessage);
    }

    public static void showLogTAG(String strMessage) {
        Log.e("==TAG==", "--screen--" + strMessage);
    }

    public static void showLogResponce(String strTag, String strMessage) {
        Log.w("==RESPONSE==" + strTag, "--strResponse--" + strMessage);
    }

    public static void showLogApi(String strMessage) {
        Log.v("==App==", "--strMessage--" + strMessage);
    }

    public static void showLog(String strTag, String strMessage) {
        Log.v("==App==strTag==" + strTag, "--strMessage--" + strMessage);
    }


    public static void setTaskBarColored(Activity context, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window =  context.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // window.setStatusBarColor(Color.BLUE);
            window.setStatusBarColor(ContextCompat.getColor(context, color));
        }
    }

    public static boolean isInternetAvail(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }


    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    public static String getOnlyDigits(String s) {
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(s);
        String number = matcher.replaceAll("");
        return number;
    }

    public static String getOnlyStrings(String s) {
        Pattern pattern = Pattern.compile("[^a-z A-Z]");
        Matcher matcher = pattern.matcher(s);
        String number = matcher.replaceAll("");
        return number;
    }

    public static String getOnlyAlfaNumeric(String s) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(s);
        String number = matcher.replaceAll(" ");
        return number;
    }


    public void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideSoftKeyboardMy(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


    public static void myStartActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void myFinishActivity(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    public static void myFinishStartActivity(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public static void myProductDetailActivity(Activity activity) {
        Intent iv = new Intent(activity, ActProductDetail.class);
        activity.startActivity(iv);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public static String getMonthNameTime(String convert_date_string){
        String final_date="";
        if (convert_date_string !=null) {

            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat  outputFormat = new SimpleDateFormat("dd MMM @ hh:mm a");
                String inputDateStr = convert_date_string;
                Date date = null;
                date = inputFormat.parse(inputDateStr);
                //String outputDateStr = outputFormat.format(date);
                final_date = outputFormat.format(date);
                final_date.toLowerCase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return final_date;
    }


    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }


    public static Boolean isAppIsRunningTop(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        Log.d("current task :", "CURRENT Activity ::" + taskInfo.get(0).topActivity.getClass().getSimpleName());
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        if(componentInfo.getPackageName().equalsIgnoreCase(context.getPackageName())){
            //Activity in foreground, broadcast intent

            App.showLog("====isAppIsRunningTop===###===App is running===###=====");
            return true;
        }
        else{
            //Activity Not Running
            //Generate Notification
            App.showLog("===isAppIsRunningTop====###===App is not running===###=======");
            return false;
        }
    }


    public static boolean isAppKill(Context context) {

        ActivityManager aManager = (ActivityManager) context.getSystemService( ACTIVITY_SERVICE );
        List<ActivityManager.RunningAppProcessInfo> processInfo = aManager.getRunningAppProcesses();
        for(int i = 0; i < processInfo.size(); i++){
            if(processInfo.get(i).processName.equals(context.getPackageName())) {
                //Kill app

                App.showLog("===isAppKill==###===App is not running===###=======");
                return false;
            }
        }
        //Start app
        App.showLog("===isAppKill==###===App is running===###=====");
        return true;
    }



    public static  boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        App.showLog("=====####=====isAppIsInBackground====####===="+isInBackground);
        return isInBackground;
    }


    public static boolean isCheckReachLocation(int rangeMeter,double sDLat, double sDLon, double eDLat, double eDLon) {
        float distanceInMeters = 0;

        Location startLocation = new Location("Start");
        startLocation.setLatitude(sDLat);
        startLocation.setLongitude(sDLon);

        Location targetLocation = new Location("Ending");
        targetLocation.setLatitude(eDLat);
        targetLocation.setLongitude(eDLon);

        distanceInMeters =  (targetLocation.distanceTo(startLocation));


        // distance = locationA.distanceTo(locationB);   // in meters
        //  distance = locationA.distanceTo(locationB)/1000;   // in km

        String strCalMeters = String.format("%.02f", distanceInMeters);

        App.showLog("===checkReachLocation====strCalMeters====meters===="+strCalMeters);

        if(distanceInMeters > rangeMeter )
        {
            return false;
        }
        else
        {
            App.showLog("====-----REACHED----=====checkReachLocation====strCalMeters====meters===="+strCalMeters);
            return true;
        }
    }


    public static String getDistanceInKM(double sDLat, double sDLon, double eDLat, double eDLon) {
        float distanceInMeters = 0;

        Location startLocation = new Location("Start");
        startLocation.setLatitude(sDLat);
        startLocation.setLongitude(sDLon);

        Location targetLocation = new Location("Ending");
        targetLocation.setLatitude(eDLat);
        targetLocation.setLongitude(eDLon);

        distanceInMeters =  (targetLocation.distanceTo(startLocation) / 1000);


        // distance = locationA.distanceTo(locationB);   // in meters
      //  distance = locationA.distanceTo(locationB)/1000;   // in km

        String strCalKM = String.format("%.02f", distanceInMeters);

        App.showLog("======KM===="+strCalKM);

        return strCalKM;
    }


    public static String getDistanceInMeter(double sDLat, double sDLon, double eDLat, double eDLon) {

        float distanceInMeters = 0;

        Location startLocation = new Location("Start");
        startLocation.setLatitude(sDLat);
        startLocation.setLongitude(sDLon);

        Location targetLocation = new Location("Ending");
        targetLocation.setLatitude(eDLat);
        targetLocation.setLongitude(eDLon);

        distanceInMeters =  (targetLocation.distanceTo(startLocation));


        // distance = locationA.distanceTo(locationB);   // in meters
        //  distance = locationA.distanceTo(locationB)/1000;   // in km

        String strCalMeter = String.format("%.02f", distanceInMeters);

        App.showLog("======METER===="+strCalMeter);

        return strCalMeter;
    }
}
