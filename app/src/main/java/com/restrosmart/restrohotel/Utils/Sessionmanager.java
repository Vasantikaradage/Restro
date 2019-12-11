package com.restrosmart.restrohotel.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.restrosmart.restrohotel.Admin.ActivityAdminDrawer;
import com.restrosmart.restrohotel.Admin.ActivityLogin;
import com.restrosmart.restrohotel.Model.UserForm;

import java.util.HashMap;

/**
 * Created by SHREE on 24/10/2018.
 */

public class Sessionmanager {

    // Shared Preferences
    private SharedPreferences pref;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Context
    private Context mContext;
    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Pref";
    public static final String TAB_POSITION = "position";

    public static final String PC_ID = "pc_id";

    //Employee Details
    public static final String EMP_NAME = "emp_name";
    public static final String EMP_EMAIL = "emp_email";
    public static final String EMP_MOB = "emp_mob";
    public static final String EMP_PASS = "emp_pass";
    public static final String EMP_UNAME = "emp_uname";
    public static final String EMP_ACT_STATUS = "emp_status";
    public static final String EMP_ADD = "emp_add";
    public static final String EMP_ID = "empId";
    public static final String EMP_ADHAR = "emp_adhar";
    public static final String EMP_IMG = "emp_img";

    //Hotel Details
    public static final String HOTEL_ID = "hotelId";
    public static final String HOTEL_NAME = "hotelName";
    public static final String BRANCH_ID = "branchId";
    public static final String ROLE_ID = "roleId";

    //Water bottle details
    public static final String WATER_BOTTLE_ID = "waterId";
    public static final String WATER_BOTTLE_NAME = "waterName";
    public static final String WATER_BOTTLE_IMAGE = "waterImage";
    public static final String WATER_BOTTLE_PRICE = "waterPrice";

    public static final String ISLOGIN = "login";

    public static final String REMEMBER_USER_NAME = "remember_user_name";
    public static final String REMEMBER_PASSWORD = "remember_password";
    public static final String REMEMBER_ME = "remember_me";

    public static final String TABLE_ID = "TableId";
    public static final String TABLE_NO = "TableNo";
    public static final String USER_ID = "UserId";
    private static final String CART_COUNT = "CartCount";
    private static final String ORDER_ID = "OrderId";
    public static final String CUST_ID = "CustID";
    public static final String CUST_NAME = "CustName";
    public static final String CUST_MOB = "CustMob";


    public Sessionmanager(Context mcontext) {
        this.mContext = mcontext;
        pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean isRememberMe() {
        return pref.getBoolean(REMEMBER_ME, false);
    }

    public void setRememberMe(String userID, String password) {

        editor.putString(REMEMBER_USER_NAME, userID);
        editor.putString(REMEMBER_PASSWORD, password);
        editor.putBoolean(REMEMBER_ME, true);

        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getRememberMe() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(REMEMBER_USER_NAME, pref.getString(REMEMBER_USER_NAME, null));
        user.put(REMEMBER_PASSWORD, pref.getString(REMEMBER_PASSWORD, null));
        return user;
    }

    public void clearRememberMe() {
        editor.remove(REMEMBER_USER_NAME);
        editor.remove(REMEMBER_PASSWORD);
        editor.putBoolean(REMEMBER_ME, false);

        // commit changes
        editor.commit();
    }

   /* //save Login info
    public void createSession(UserForm user) {

        // Gson gson = new Gson();
        // String json = gson.toJson(user);
        editor.putString(EMP_EMAIL, String.valueOf(user.getEmp_Email()));
        editor.putString(EMP_ACT_STATUS, String.valueOf(user.getActive_Status()));
        editor.putString(EMP_ADD, String.valueOf(user.getEmp_Address()));
        editor.putString(EMP_ID, String.valueOf(user.getEmp_Id()));
        editor.putString(EMP_IMG, String.valueOf(user.getEmp_Img()));
        editor.putString(EMP_MOB, String.valueOf(user.getEmp_Mob()));
        editor.putString(EMP_PASS, String.valueOf(user.getPassword()));
        editor.putString(EMP_ADHAR, String.valueOf(user.getEmp_Adhar_Id()));
        editor.putString(ROLE_ID, String.valueOf(user.getRole()));
        editor.putString(EMP_UNAME, String.valueOf(user.getUser_Name()));
        editor.putString(EMP_NAME, String.valueOf(user.getEmp_Name()));
        editor.putBoolean(ISLOGIN, true);
        editor.commit();
    }*/

    public void CheckLogin() {

        if (isLogin()) {

            Intent i = new Intent(mContext, ActivityAdminDrawer.class);

            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            mContext.startActivity(i);
        } else {
            // After logout redirect user to Login Activity
            Intent i = new Intent(mContext, ActivityLogin.class);

            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            mContext.startActivity(i);
        }
    }

    void CheckLogout() {
        editor.remove(EMP_EMAIL);
        editor.remove(EMP_ID);
        editor.remove(EMP_NAME);
        editor.remove(ROLE_ID);
        editor.remove(EMP_MOB);
        editor.remove(EMP_ACT_STATUS);
        editor.remove(EMP_UNAME);
        editor.remove(EMP_ADD);
        editor.remove(EMP_IMG);
        editor.remove(EMP_PASS);
        editor.remove(EMP_ADHAR);

        editor.remove(ISLOGIN);

        editor.commit();


        // After logout redirect user to Login Activity
        Intent i = new Intent(mContext, ActivityLogin.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        mContext.startActivity(i);
    }

    //method to check direct login
    public boolean isLogin() {
        return pref.getBoolean(ISLOGIN, false);
    }

   /* public HashMap getLoginInfo() {
        // Gson gson = new Gson();
        //String user = gson.toJson(user1);
        //  pref.getString("user", String.valueOf(user1));

        HashMap<String, String> stringMap = new HashMap<String, String>();

        stringMap.put("emp_name", pref.getString(EMP_NAME, null));
        stringMap.put("emp_email", pref.getString(EMP_EMAIL, null));
        stringMap.put("emp_mob", pref.getString(EMP_MOB, null));
        stringMap.put("emp_pass", pref.getString(EMP_PASS, null));
        stringMap.put("emp_uname", pref.getString(EMP_UNAME, null));
        stringMap.put("emp_status", pref.getString(EMP_ACT_STATUS, null));
        stringMap.put("emp_add", pref.getString(EMP_ADD, null));
        stringMap.put("emp_id", pref.getString(EMP_ID, null));
        stringMap.put("emp_role", pref.getString(ROLE_ID, null));
        stringMap.put("emp_adhar", pref.getString(EMP_ADHAR, null));
        stringMap.put("emp_img", pref.getString(EMP_IMG, null));

        return stringMap;
    }
*/
    //save position info
    public void setTabposition(int position) {
        editor.putInt(TAB_POSITION, position);
        editor.commit();
    }

    public int getTabposition() {
        return pref.getInt(TAB_POSITION, 0);
    }

    public void setWaterBottleDetail(String waterBottleId, String waterBottleName, String waterBottleImage, float waterBottlePrice) {
        editor.putString(WATER_BOTTLE_ID, String.valueOf(waterBottleId));
        editor.putString(WATER_BOTTLE_NAME, waterBottleName);
        editor.putString(WATER_BOTTLE_IMAGE, waterBottleImage);
        editor.putString(WATER_BOTTLE_PRICE, String.valueOf(waterBottlePrice));
        editor.commit();
    }

    public HashMap getWaterBottleDetail() {
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("waterId", pref.getString(WATER_BOTTLE_ID, null));
        hashMap.put("waterName", pref.getString(WATER_BOTTLE_NAME, null));
        hashMap.put("waterImage", pref.getString(WATER_BOTTLE_IMAGE, null));
        hashMap.put("waterPrice", pref.getString(WATER_BOTTLE_PRICE, null));
        return hashMap;
    }

    public void saveHotelDetails(int hotelId, String hotelName, int role, int empId) {
        editor.putString(HOTEL_ID, String.valueOf(hotelId));
        editor.putString(HOTEL_NAME, hotelName);
        editor.putString(ROLE_ID, String.valueOf(role));
        editor.putString(EMP_ID, String.valueOf(empId));
        editor.commit();
    }

    public HashMap getHotelDetails() {
        HashMap<String, String> stringMap = new HashMap<String, String>();
        stringMap.put("hotelId", pref.getString(HOTEL_ID, null));
        stringMap.put("hotelName", pref.getString(HOTEL_NAME, null));
        stringMap.put("roleId", pref.getString(ROLE_ID, null));
        stringMap.put("empId", pref.getString(EMP_ID, null));
        return stringMap;
    }

    public void saveSuperAdminDetails(int empId, String empName, int Role_Id) {
        editor.putString(EMP_ID, String.valueOf(empId));
        editor.putString(EMP_NAME, empName);
        editor.putString(ROLE_ID, String.valueOf(Role_Id));
        editor.commit();
    }

    public HashMap getSuperAdminDetails() {
        HashMap<String, String> stringMap = new HashMap<String, String>();
        stringMap.put("emp_name", pref.getString(EMP_NAME, null));
        stringMap.put("roleId", pref.getString(ROLE_ID, null));
        stringMap.put("empId", pref.getString(EMP_ID, null));
        return stringMap;
    }

    public void saveCaptainDetails(int empId, String empName, int Role_Id) {
        editor.putString(EMP_ID, String.valueOf(empId));
        editor.putString(EMP_NAME, empName);
        editor.putString(ROLE_ID, String.valueOf(Role_Id));
        editor.commit();
    }

    public HashMap getCaptainDetails() {
        HashMap<String, String> stringMap = new HashMap<String, String>();
        stringMap.put("empName", pref.getString(HOTEL_NAME, null));
        stringMap.put("roleId", pref.getString(ROLE_ID, null));
        stringMap.put("empId", pref.getString(EMP_ID, null));
        return stringMap;
    }

    public void saveCustDetails(String custId, String custName, String custMob, int tableId, int tableNo) {
        editor.putString(CUST_ID, custId);
        editor.putString(CUST_NAME, custName);
        editor.putString(CUST_MOB, custMob);
        editor.putInt(TABLE_ID, tableId);
        editor.putInt(TABLE_NO, tableNo);
        editor.commit();
    }

    public HashMap<String, String> getCustDetails() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put(CUST_ID, pref.getString(CUST_ID, null));
        hashMap.put(CUST_NAME, pref.getString(CUST_NAME, null));
        hashMap.put(CUST_MOB, pref.getString(CUST_MOB, null));
        hashMap.put(TABLE_ID, String.valueOf(pref.getInt(TABLE_ID, 0)));
        hashMap.put(TABLE_NO, String.valueOf(pref.getInt(TABLE_NO, 0)));
        return hashMap;
    }

    public void deleteCustDetails() {
        editor.remove(CUST_ID);
        editor.remove(CUST_NAME);
        editor.remove(CUST_MOB);
        editor.remove(TABLE_ID);
        editor.remove(TABLE_NO);
        editor.commit();
    }

    /* *//**
     * Save Table No & User Id
     *//*
    public void saveUserDetails(String custId) {
        editor.putString(USER_ID, custId);
        editor.putString(USER_NAME, custName);
        editor.putString(USER_MOBILE, custMob);
        editor.commit();
    }

    *//**
     * Get Table No & User Id
     *//*
    public HashMap<String, String> getCustDetails() {
        HashMap<String, String> userDetails = new HashMap<String, String>();
        userDetails.put("tableNo", String.valueOf(pref.getInt(TABLE_NO, 0)));
        userDetails.put("userId", pref.getString(USER_ID, null));
        return userDetails;

    }
*/

    /**
     * Save cart count
     */
    public void saveCartCount() {
        editor.putInt(CART_COUNT, pref.getInt(CART_COUNT, 0) + 1);
        editor.commit();
    }

    /**
     * Get cart count
     */
    public int getCartCount() {
        return pref.getInt(CART_COUNT, 0);
    }

    /**
     * Delete cart count
     */
    public void deleteCartCount() {
        editor.putInt(CART_COUNT, pref.getInt(CART_COUNT, 0) - 1);
        editor.commit();
    }

    /**
     * Reset cart count
     */
    public void resetCartCount() {
        editor.remove(CART_COUNT);
        editor.commit();
    }

    /**
     * Save order id
     */
    public void saveOrderID(int order_id) {
        editor.putInt(ORDER_ID, order_id);
        editor.commit();
    }

    /**
     * Get order id
     */
    public int getOrderID() {
        return pref.getInt(ORDER_ID, 0);
    }

    /**
     * Delete order id
     */
    public void deleteOrderID() {
        editor.remove(ORDER_ID);
        editor.commit();
    }

}
