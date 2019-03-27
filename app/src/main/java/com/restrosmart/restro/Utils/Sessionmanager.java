package com.restrosmart.restro.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.restrosmart.restro.Admin.ActivityAdminDrawer;
import com.restrosmart.restro.MainActivity;
import com.restrosmart.restro.Model.FoodCartModel;
import com.restrosmart.restro.Model.UserForm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

    public static final String USER = "user";
    public static final String EMP_NAME = "emp_name";
    public static final String EMP_EMAIL = "emp_email";
    public static final String EMP_MOB = "emp_mob";
    public static final String EMP_PASS = "emp_pass";
    public static final String EMP_UNAME = "emp_uname";
    public static final String EMP_ACT_STATUS = "emp_status";
    public static final String EMP_ADD = "emp_add";
    public static final String EMP_ID = "emp_id";

    //public static final String HOTEL_ID = "hotel_id";
    public static final String EMP_ADHAR = "emp_adhar";
    public static final String EMP_IMG = "emp_img";

    //Hotel Details
    public static final String HOTEL_ID = "hotelId";
    public static final String HOTEL_NAME = "hotelName";
    public static final String BRANCH_ID = "branchId";
    public static final String ROLE_ID = "roleId";

    public static final String WATER_BOTTLE_ID = "waterId";
    public static final String WATER_BOTTLE_NAME = "waterName";
    public static final String WATER_BOTTLE_IMAGE = "waterImage";
    public static final String WATER_BOTTLE_PRICE = "waterPrice";

    public static final String ISLOGIN = "login";

    //Add to food cart
    private static final String ADD_TO_FOOD_CART = "AddToFoodCart";

    //Add to liqour cart
    private static final String ADD_TO_LIQOUR_CART = "AddToLiqourCart";

    public Sessionmanager(Context mcontext) {

        this.mContext = mcontext;
        pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    //save Login info
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
    }


    //  return  user;


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
            Intent i = new Intent(mContext, MainActivity.class);

            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            mContext.startActivity(i);
        }
    }

    public void CheckLogout() {
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
        Intent i = new Intent(mContext, MainActivity.class);

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

    public HashMap getLoginInfo() {
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


    //save position info
    public int setTabposition(int position) {
        editor.putInt(TAB_POSITION, position);
        editor.commit();
        return position;

    }

    public int getTabposition() {
        return pref.getInt(TAB_POSITION, 0);

    }

    public void setWaterBottleDetail(int waterBottleId, String waterBottleName, String waterBottleImage, float waterBottlePrice) {
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

    public void saveHotelDetails(int hotelId, String hotelName, int role, int brancId, int empId) {
        editor.putString(HOTEL_ID, String.valueOf(hotelId));
        editor.putString(HOTEL_NAME, hotelName);
        editor.putString(ROLE_ID, String.valueOf(role));
        editor.putString(BRANCH_ID, String.valueOf(brancId));
        editor.putString(EMP_ID, String.valueOf(empId));
        editor.commit();
    }

    public HashMap getHotelDetails() {
        HashMap<String, String> stringMap = new HashMap<String, String>();

        stringMap.put("hotelId", pref.getString(HOTEL_ID, null));
        stringMap.put("hotelName", pref.getString(HOTEL_NAME, null));
        stringMap.put("roleId", pref.getString(ROLE_ID, null));
        stringMap.put("branchId", pref.getString(BRANCH_ID, null));
        stringMap.put("empId", pref.getString(EMP_ID, null));
        return stringMap;
    }

    /**
     * Store Food Cart products
     */
    private void saveFoodCart(Context context, List<FoodCartModel> foodCartModels) {

        Gson gson = new Gson();
        String jsonCart = gson.toJson(foodCartModels);

        editor.putString(ADD_TO_FOOD_CART, jsonCart);
        editor.commit();
    }

    //add to food cart
    public void addToFoodCart(Context context, FoodCartModel foodRVCartModel) {

        boolean isValid = false;

        ArrayList<FoodCartModel> cartRVModelArrayList = getAddToFoodCartList(context);
        if (cartRVModelArrayList == null)
            cartRVModelArrayList = new ArrayList<FoodCartModel>();

        for (int i = 0; i < cartRVModelArrayList.size(); i++) {

            FoodCartModel foodCartModel = cartRVModelArrayList.get(i);

            if (foodCartModel.getMenuId() != 0 && foodCartModel.getMenuId() == foodRVCartModel.getMenuId()) {
                Toast.makeText(context, "Already added in cart", Toast.LENGTH_SHORT).show();
                isValid = true;
            }
        }

        if (!isValid) {
            cartRVModelArrayList.add(foodRVCartModel);
            saveFoodCart(context, cartRVModelArrayList);
        } else {
            for (int i = 0; i < cartRVModelArrayList.size(); i++) {
                FoodCartModel rvModel = cartRVModelArrayList.get(i);
                if (rvModel.getMenuId() != 0 && rvModel.getMenuId() == foodRVCartModel.getMenuId()) {
                    int val = rvModel.getMenuQty();
                    rvModel.setMenuQty(val + 1);
                    rvModel.setMenuQtyPrice((val + 1) * foodRVCartModel.getMenuPrice());
                }
            }
            saveFoodCart(context, cartRVModelArrayList);
        }

        /*for(CartModel rvModel : cartRVModelArrayList){
            if(rvModel.getProductID() != null && rvModel.getProductID().equals(cartRVModel.getProductID())){

                String produtQty = Integer.parseInt(cartRVModel.getProductQty()) + "1";

                Log.v("productid", cartRVModel.getProductID());
                Log.v("rvproductid", cartRVModel.getProductID());
                Log.v("rvproductqty", cartRVModel.getProductQty());
                Log.v("cartproductid", cartRVModel.getProductID());
                Log.v("cartproductqty", cartRVModel.getProductQty());

                cartRVModel.setProductQty(produtQty);
            }else {
                cartRVModelArrayList.add(cartRVModel);
            }
        }*/
    }

    //remove food item from cart
    public void removeMenuFoodCart(Context context, int menuId) {
        ArrayList<FoodCartModel> foodCartModelArrayList = getAddToFoodCartList(context);

        if (foodCartModelArrayList != null) {

//            if (foodCartModelArrayList.contains(position)) {
//                foodCartModelArrayList.remove(position);
//            }

            for (int i = 0; i < foodCartModelArrayList.size(); i++) {
                FoodCartModel rvModel = foodCartModelArrayList.get(i);
                if (rvModel.getMenuId() != 0 && rvModel.getMenuId() == menuId) {
                    if (rvModel.getMenuQty() == 1) {
                        foodCartModelArrayList.remove(i);
                    } else {
                        int val = rvModel.getMenuQty();
                        rvModel.setMenuQty(val - 1);
                        rvModel.setMenuQtyPrice((val - 1) * rvModel.getMenuPrice());
                    }
                }
            }

            saveFoodCart(context, foodCartModelArrayList);

            /*Iterator<FoodCartModel> modelIterator = foodCartModelArrayList.iterator();

            while (modelIterator.hasNext()) {
                int id = modelIterator.next().getMenuId();
                if (id == menuId) {
                    int qty = modelIterator.next().getMenuQty();

                    if (qty == 1) {
                        modelIterator.remove();
                        saveFoodCart(context, foodCartModelArrayList);
                    } else {
                        for (int i = 0; i < foodCartModelArrayList.size(); i++) {
                            FoodCartModel rvModel = foodCartModelArrayList.get(i);
                            if (rvModel.getMenuId() != 0 && rvModel.getMenuId() == menuId) {
                                int val = rvModel.getMenuQty();
                                rvModel.setMenuQty(val - 1);
                            }
                        }
                        saveFoodCart(context, foodCartModelArrayList);
                    }
                }
            }*/
        }
    }

    //update food qty in cart
    public void updateQtyFoodCart(Context context, int value, int menuId) {
        ArrayList<FoodCartModel> cartRVModelArrayList = getAddToFoodCartList(context);

        for (int i = 0; i < cartRVModelArrayList.size(); i++) {

            FoodCartModel rvModel = cartRVModelArrayList.get(i);
            if (value == 1) {
                if (rvModel.getMenuId() != 0 && rvModel.getMenuId() == menuId) {
                    int val = rvModel.getMenuQty();
                    /*Log.v("qty", val);
                    Log.v("qtynew", String.valueOf(Integer.parseInt(val) + 1));*/
                    rvModel.setMenuQty(val + 1);
                    rvModel.setMenuQtyPrice((val + 1) * rvModel.getMenuPrice());
                }
            } else {
                if (rvModel.getMenuId() != 0 && rvModel.getMenuId() == menuId) {
                    int val = rvModel.getMenuQty();
                    /*Log.v("qty", val);
                    Log.v("qtynew", String.valueOf(Integer.parseInt(val) - 1));*/
                    rvModel.setMenuQty(val - 1);
                    rvModel.setMenuQtyPrice((val - 1) * rvModel.getMenuPrice());
                }
            }
        }

        saveFoodCart(context, cartRVModelArrayList);

    }

    //get FoodSubtotal amount
    public float getFoodSubTotal(Context context) {
        float subtotal = 0f;
        ArrayList<FoodCartModel> foodCartModelArrayList = getAddToFoodCartList(context);

        if (foodCartModelArrayList != null) {
            for (int i = 0; i < foodCartModelArrayList.size(); i++) {
                FoodCartModel foodCartModel = foodCartModelArrayList.get(i);
                subtotal += foodCartModel.getMenuQtyPrice();
            }
        }
        return subtotal;
    }

    //delete cart list after giving order
    public void deleteFoodCartList() {
        pref.edit().remove(ADD_TO_FOOD_CART).commit();
    }

    //get cart food list
    public ArrayList<FoodCartModel> getAddToFoodCartList(Context context) {
        List<FoodCartModel> foodCartModelList;

        if (pref.contains(ADD_TO_FOOD_CART)) {
            String jsonAddToCart = pref.getString(ADD_TO_FOOD_CART, null);
            Gson gson = new Gson();
            FoodCartModel[] cartRVModels = gson.fromJson(jsonAddToCart, FoodCartModel[].class);

            foodCartModelList = Arrays.asList(cartRVModels);
            foodCartModelList = new ArrayList<FoodCartModel>(foodCartModelList);
        } else
            return null;

        return (ArrayList<FoodCartModel>) foodCartModelList;
    }
}
