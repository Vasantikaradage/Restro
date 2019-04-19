package com.restrosmart.restrohotel.Interfaces;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Model.EmployeeForm;
import com.restrosmart.restrohotel.Model.RoleForm;


import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by SHREE on 10/6/2018.
 */

public interface ApiService {


    String BASE_URL = "http://192.168.0.10/Restro_Smart/";


    /*parent category display*/
    @POST("Category.php?category=cate_disp")
    @FormUrlEncoded
    Call<JsonObject> getParentCategory(@Field("Hotel_Id") String Hotel_Id,
                                       @Field("Branch_Id") String Branch_Id);


    /*Fetching all employee Details.*/
    @FormUrlEncoded
    @POST("Employee_Signup.php?Empcall=Get_Employee")
    Call<List<EmployeeForm>> getallEmployees(@Field("Hotel_Id") String Hotel_Id,
                                             @Field("Branch_Id") String Branch_Id);

    /*employee delete*/
    @POST("Employee_Signup.php?Empcall=emp_delete")
    @FormUrlEncoded
    Call<JsonObject> employeeDelete(@Field("Emp_Id") int empId,
                                    @Field("Branch_Id") int branchId,
                                    @Field("Hotel_Id") int hotelId);

    @FormUrlEncoded
    @POST("Employee_Signup.php?Empcall=Get_Employee")
    Call<JsonObject> getallEmployeesDetail(@Field("Hotel_Id") String Hotel_Id,
                                           @Field("Branch_Id") String Branch_Id);


    /*Edit employee*/
    @POST("Employee_Signup.php?Empcall=Emp_Edit")
    @FormUrlEncoded
    Call<JsonObject> editEmployeeDetail(@Field("Emp_Id") int empId,
                                        @Field("Emp_Name") String empName,
                                        @Field("Emp_Mob") String mobile,
                                        @Field("Emp_Email") String empMobile,
                                        @Field("Emp_Address") String empAddress,
                                        @Field("User_Name") String userName,
                                        @Field("Hotel_Id") int hotelId,
                                        @Field("Branch_Id") int branchId,
                                        @Field("Role_Id") int role_id,
                                        @Field("Password") String password);


    /*Update employee status */
    @POST("Employee_Signup.php?Empcall=status")
    @FormUrlEncoded
    Call<JsonObject> updateStatus(@Field("Emp_Id") int Emp_Id,
                                  @Field("Active_Status") String status,
                                  @Field("Branch_Id") int branchId,
                                  @Field("Hotel_Id") int hotelId);


    /*Admin Login.*/

    //@POST("Employee_Signup.php?Empcall=login")
    @POST("Main_page.php?main=main_login")
    @FormUrlEncoded
    Call<JsonObject> getLogin(@Field("User_Name") String Hotel_Mob,
                              @Field("Password") String Password);

    //forget password
    @POST("Employee_Signup.php?Empcall=forgetpass")
    @FormUrlEncoded
    Call<JsonObject> forgetPassword(@Field("Emp_Email") String email,
                                    @Field("Emp_Mob") String emp_mob,
                                    @Field("Hotel_id") int hotel_id,
                                    @Field("Branch_Id") int branch_id);


    /*Admin Register.*/
    @FormUrlEncoded
    @POST("Main_page.php?main=main_register")
    Call<JsonObject> hotelRegister(@Field("Hotel_Name") String hotelName,
                                   @Field("Hotel_Address") String hotelAddress,
                                   @Field("Hotel_Mob") String hotelMob,
                                   @Field("Hotel_Phone") String hotelPhone,
                                   @Field("Hotel_Email") String hotelEmail,
                                   @Field("Hotel_Gstinno") String hotelGstin,
                                   @Field("Password") String hotelPassword,
                                   @Field("Con_Pass") String hotelConPass);

    /*branch detail*/
    @POST("Branch.php?branch=branch_disp")
    @FormUrlEncoded
    Call<JsonObject> getBranchDetail(@Field("Hotel_Id") int hotelId,
                                     @Field("Branch_Id") int branchid);

    /*branch edit detail*/
    @POST("Branch.php?branch=branch_edit")
    @FormUrlEncoded
    Call<JsonObject> editHotelDetail(
            @Field("Branch_Name") String branchName,
            @Field("Branch_Address") String hotelAddress,
            @Field("Branch_Email") String hotelEmail,
            @Field("Branch_Mob") int hotelMob,
            @Field("Branch_Gstnno") int hotelGstnNo,
            @Field("Branch_Table_Count") int noOfTable,
            @Field("Branch_Timing") String hotelTiming,
            @Field("Hotel_Id") int hotelId,
            @Field("Branch_Id") int branchid);

    /*Employee Signup*/
    @POST("Employee_Signup.php?Empcall=signup")
    @FormUrlEncoded
    Call<JsonObject> AddEmployee(@FieldMap Map<String, String> signup);


    /*change Password*/
    @POST("Employee_Signup.php?Empcall=changepass")
    @FormUrlEncoded
    Call<JsonObject> ChangePassword(@Field("Emp_Id") int emp_id,
                                    @Field("Password") String password,
                                    @Field("Con_Pass") String conPass,
                                    @Field("Hotel_Id") int hotelId,
                                    @Field("Branch_Id") int branchId);

    /*Employee Role*/
    @POST("Employee_Signup.php?Empcall=role")
    @FormUrlEncoded
    Call<List<RoleForm>> getEmpRole(@Field("Hotel_Id") int hotelId);


    /*Select branch*/
    @POST("Employee_Signup.php?Empcall=branch")
    @FormUrlEncoded
    Call<JsonObject> getBranch(@Field("Hotel_Id") int hotelId);


    /*Get Category Image*/
    @POST("Category.php?category=cate_icon")
    @FormUrlEncoded
    Call<JsonObject> getCategoryImage(@Field("Hotel_Id") int Hotel_Id,
                                      @Field("Branch_Id") int branch_id,
                                      @Field("Pc_Id") int pc_id);

    /*get flavour image*/
    @POST("Flavour.php?flavour=flavour_img")
    @FormUrlEncoded
    Call<JsonObject> getFlavourImage(@Field("Menu_Id") int menuId,
                                     @Field("Category_Id") int categoryId,
                                     @Field("Branch_Id") int branchId,
                                     @Field("Hotel_Id") int hotelId,
                                     @Field("Pc_Id") int pcId);




    /*Add Category*/

    @POST("Category.php?category=subcate_add")
    @FormUrlEncoded
    Call<JsonObject> addCategory(

            @Field("Category_Name") String Category_Name,
            @Field("C_Image_Name") String C_Image_Name,
            @Field("Hotel_Id") int Hotel_Id,
            @Field("Branch_Id") int Branch_Id,
            @Field("Pc_Id") int Pc_Id);


    /*category display*/
    @POST("Category.php?category=allmenu")
    @FormUrlEncoded
    Call<JsonObject> GetAllCategory(@Field("Hotel_Id") int Hotel_Id,
                                    @Field("Branch_Id") int Branch_Id);


    //Toppings
    // Toppings display
    @POST("topping.php?topping=topping_disp")
    @FormUrlEncoded
    Call<JsonObject> toppingDisplay(@Field("Hotel_Id") int Hotel_Id,
                                    @Field("Branch_Id") int Branch_Id
            /*@Field("Pc_Id") int pc_id*/);


    //toppings add
    @POST("topping.php?topping=topping_add")
    @FormUrlEncoded
    Call<JsonObject> addToppings(@Field("Topping_Name") String toppingName,
                                 @Field("Topping_Price") int toppingPrice,
                                 @Field("Hotel_Id") int Hotel_Id,
                                 @Field("Branch_Id") int Branch_Id,
                                 @Field("Pc_Id") int pc_id);


    //topping delete
    @POST("topping.php?topping=topping_del")
    @FormUrlEncoded
    Call<JsonObject> toppingDelete(@Field("Topping_Id") int toppingId,
                                   @Field("Hotel_Id") int Hotel_Id,
                                   @Field("Branch_Id") int Branch_Id,
                                   @Field("Pc_Id") int pc_id);

    //topping edit
    @POST("topping.php?topping=topping_edit")
    @FormUrlEncoded
    Call<JsonObject> toppingEdit(@Field("Topping_Name") String toppingName,
                                 @Field("Topping_Price") int toppingPrice,
                                 @Field("Hotel_Id") int hotelId,
                                 @Field("Branch_Id") int branchId,
                                 @Field("Pc_Id") int pcId,
                                 @Field("Topping_Id") int toppingId);


    /*sub category display*/
    @POST("Offer.php?offer=subcate_select")
    @FormUrlEncoded
    Call<JsonObject> GetSubCategory(@Field("Hotel_Id") int Hotel_Id,
                                    @Field("Branch_Id") int Branch_Id,
                                    @Field("Pc_Id") int Pc_Id);


    /*menu display*/
    @POST("Offer.php?offer=menu_select")
    @FormUrlEncoded
    Call<JsonObject> GetMenuDisplay(@Field("Hotel_Id") int Hotel_Id,
                                    @Field("Branch_Id") int Branch_Id,
                                    @Field("Category_Id") int Category_Id);


    @POST("User_Category.php?user_cate=menu_display")
    @FormUrlEncoded
    Call<JsonObject> getCategory(@Field("Hotel_Id") int Hotel_Id,
                                 @Field("Branch_Id") int Branch_Id);

    @POST("Payment.php?payment=Pay_disp")
    @FormUrlEncoded
    Call<JsonObject> getPaymentMethods(@Field("Hotel_Id") int Hotel_Id,
                                       @Field("Branch_Id") int Branch_Id);

    @POST("User_Category.php?user_cate=user_menulist")
    @FormUrlEncoded
    Call<JsonObject> getSubCategoryMenu(@Field("Hotel_Id") int Hotel_Id,
                                        @Field("Branch_Id") int Branch_Id,
                                        @Field("Pc_Id") int Pc_Id);

    @POST("User_Category.php?user_cate=water_bottle")
    @FormUrlEncoded
    Call<JsonObject> getWaterBottle(@Field("Hotel_Id") int Hotel_Id,
                                    @Field("Branch_Id") int Branch_Id);


    /*category display*/
    @POST("Menu.php?menu=menu_disp")
    @FormUrlEncoded
    Call<JsonObject> getMenus(@Field("Category_Id") int Category_Id,
                              @Field("Hotel_Id") int hotelId,
                              @Field("Branch_Id") int branchId);

    /*delete menu*/
    @POST("Menu.php?menu=menu_delete")
    @FormUrlEncoded
    Call<JsonObject> getMenuDelete(@Field("Menu_Id") int Menu_Id,
                                   @Field("Hotel_Id") int Hotel_Id,
                                   @Field("Branch_Id") int Branch_Id);

    /*menu images*/
    @FormUrlEncoded
    @POST("Menu.php?menu=Menu_images")
    Call<JsonObject> getMenuImage(@Field("Category_Id") int Category_Id,
                                  @Field("Branch_Id") int Branch_Id,
                                  @Field("Hotel_Id") int Hotel_Id,
                                  @Field("Pc_Id") int Pc_Id);


    /*category Delete*/
    @FormUrlEncoded
    @POST("Category.php?category=subcate_delete")
    Call<JsonObject> deleteCategory(@Field("Category_Id") int Category_Id,
                                    @Field("Branch_Id") int Branch_Id,
                                    @Field("Hotel_Id") int Hotel_Id,
                                    @Field("Pc_Id") int pcId);


    /*Flavour display*/
    @POST("Flavour.php?flavour=flavour_disp")
    @FormUrlEncoded
    Call<JsonObject> flavourDisplay(@Field("Menu_Id") int menuId,
                                    @Field("Hotel_Id") int hotelId,
                                    @Field("Branch_Id") int branchId);


    /*Flavour add*/
    @POST("Flavour.php?flavour=flavour_add")
    @FormUrlEncoded
    Call<JsonObject> flavourAdd(@Field("Flavour_Name") String flavourName,
                                @Field("F_Image_Name") String fImageName,
                                @Field("Menu_Id") int menuId,
                                @Field("Hotel_Id") int hotelId,
                                @Field("Branch_Id") int branchId,
                                @Field("Flavarray") String flavour);




    /*Category Edit*/

    @FormUrlEncoded
    @POST("Category.php?category=subcate_edit")
    Call<JsonObject> editCategory(
            @Field("Category_Name") String Category_Name,
            @Field("New_CateName") String Category_Name_New,
            @Field("C_Image_Name") String C_Image_Name,
            @Field("Category_Id") int Category_Id,
            @Field("Hotel_Id") int Hotel_Id,
            @Field("Branch_Id") int Branch_Id,
            @Field("Pc_Id") int pcId);


    /*add menu*/
    @POST("Menu.php?menu=menu_add")
    @FormUrlEncoded
    Call<JsonObject> getMenuAdd(@Field("Menu_Name") String Menu_Name,
                                @Field("Menu_Descrip") String Menu_Descrip,
                                @Field("Menu_Image_Name") String Menu_Image_Name,
                                @Field("Category_Id") int Category_Id,
                                @Field("Menu_Test") int Menu_Test,

                                @Field("Non_Ac_Rate") String Non_Ac_Rate,
                                @Field("Hotel_Id") int Hotel_Id,
                                @Field("Branch_Id") int Branch_Id,
                                @Field("Pc_Id") int pcId,
                                @Field("topparraylist") String toppingList
    );


    /*edit menu*/
    @POST("Menu.php?menu=menu_edit")
    @FormUrlEncoded
    Call<JsonObject> editMenu(@Field("Menu_Name") String menuName,
                              @Field("Menu_Descrip") String menuDiscription,
                              @Field("Menu_Image_Name") String menuImageName,
                              @Field("Menu_Test") int menu_test,
                              @Field("Non_Ac_Rate") String nonAcRate,
                              @Field("Menu_Id") int menuId,
                              @Field("Hotel_Id") int Hotel_Id,
                              @Field("Branch_Id") int Branch_Id,
                              @Field("Category_Id") int categoryId,
                              @Field("Pc_Id") int pcId,
                              @Field("topparraylist") String toppingList);


    /*offer display*/
    @POST("Offer.php?offer=offer_disp")
    @FormUrlEncoded
    Call<JsonObject> GetOffer(@Field("Hotel_Id") int Hotel_Id,
                              @Field("Branch_Id") int branchId);

    /*offer delete*/
    @POST("Offer.php?offer=offer_delete")
    @FormUrlEncoded
    Call<JsonObject> deleteOffer(@Field("Offer_Id") int offerId,
                                 @Field("Hotel_Id") int hotelId,
                                 @Field("Branch_Id") int branchId);

    /*offer delete*/
    @POST("Offer.php?offer=offer_status")
    @FormUrlEncoded
    Call<JsonObject> offOffer(@Field("Offer_Id") int offerId,
                              @Field("Hotel_Id") int hotelId,
                              @Field("Branch_Id") int branchId,
                              @Field("Status") int status);


    /*Add new Offer*/
    @POST("Offer.php?offer=offer_add")
    @FormUrlEncoded
    Call<JsonObject> AddNewOffer(@Field("Offer_Name") String offer_name,
                                 @Field("Offer_From") String offer_form,
                                 @Field("Offer_To") String offer_to,
                                 @Field("Offer_Value") String offer_value,
                                 @Field("Menu_Id") int menu_id,
                                 @Field("Coupon_Code") String coupon_code,
                                 @Field("Hotel_Id") int hotel_id,
                                 @Field("Branch_Id") int branch_id,
                                 @Field("Status") int status);


    /*offer edit*/

    @POST("/Offer.php?offer=offer_edit")
    @FormUrlEncoded
    Call<JsonObject> editOffer(@Field("Offer_Id") int offerId,
                               @Field("Offer_Name") String offer_name,
                               @Field("Offer_From") String offer_form,
                               @Field("Offer_To") String offer_to,
                               @Field("Offer_Value") String offer_value,
                               @Field("Hotel_Id") int hotel_id,
                               @Field("Branch_Id") int branch_id);


    /*Add payment methods*/
    @POST("Payment.php?payment=Pay.add")
    Call<JsonObject> AddPaymentMode(@Field("PaymentName") String payment_name,
                                    @Field("AutorizedPerson") String person_name,
                                    @Field("Bank_Name") String bank_name,
                                    @Field("IFSC_CODE") String ifsc_code,
                                    @Field("BranchName") String branch_name,
                                    @Field("UPICode") String upiCode,
                                    @Field("Mobile") int mob_no,
                                    @Field("Icon") String icon,
                                    @Field("QR_Img") String qr_img,
                                    @Field("Branch_Id") int branch_id,
                                    @Field("Hotel_Id") int hotel_id);


    /*Display Water Bottle*/
    @POST("User_Category.php?user_cate=water_bottle")
    @FormUrlEncoded
    Call<JsonObject> DisplayWaterBottle(@Field("Branch_Id") int branch_id,
                                        @Field("Hotel_Id") int hotel_id);


    /*Display Water Bottle*/
    @POST("Menu.php?menu=Waterbottle_add")
    @FormUrlEncoded
    Call<JsonObject> AddWaterBottle(@Field("Menu_Id") int id,
                                    @Field("Menu_Name") String menuName,
                                    @Field("Menu_Image_Name") String imageName,
                                    @Field("Category_Id") int category_id,
                                    @Field("Non_Ac_Rate") int price,
                                    @Field("Branch_Id") int branch_id,
                                    @Field("Hotel_Id") int hotel_id);

    /*edit Water Bottle*/
    @POST("Menu.php?menu=Waterbottle_edit")
    @FormUrlEncoded
    Call<JsonObject> editWaterBottle(@Field("Menu_Name") String menuName,
                                     @Field("Non_Ac_Rate") int price,
                                     @Field("Branch_Id") int branch_id,
                                     @Field("Hotel_Id") int hotel_id);



    /*offer delete*/
/*
@POST("")
@FormUrlEncoded
Call<JsonObject> getOfferDelete(@Field(""),);
*/


}
