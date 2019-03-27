package com.restrosmart.restro.Interfaces;

import com.google.gson.JsonObject;
import com.restrosmart.restro.Model.EmployeeRole;
import com.restrosmart.restro.Model.GetEmployeeDetails;
import com.restrosmart.restro.Model.RoleForm;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

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
    Call<List<GetEmployeeDetails>> getallEmployees(@Field("Hotel_Id") String Hotel_Id,
                                                   @Field("Branch_Id") String Branch_Id);

    @FormUrlEncoded
    @POST("Employee_Signup.php?Empcall=Get_Employee")
    Call<JsonObject> getallEmployeesDetail(@Field("Hotel_Id") String Hotel_Id,
                                           @Field("Branch_Id") String Branch_Id);

    /*Update employee status */
    @Multipart
//"Employee_Signup.php?Empcall=status/{Active_Status}"
    @PUT("Employee_Signup.php?Empcall=Get_Employee")
    Call<List<GetEmployeeDetails>> updateStatus(@Part("Emp_Id") String Emp_Id, @Query("Active_Status") String status);


    /*Admin Login.*/
    @FormUrlEncoded
    //@POST("Employee_Signup.php?Empcall=login")
    @POST("Main_page.php?main=main_login")
    Call<JsonObject> getLogin(@Field("User_Name") String Hotel_Mob,
                              @Field("Password") String Password);

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


    /*Employee Signup*/
    @POST("Employee_Signup.php?Empcall=signup")
    @FormUrlEncoded
    Call<JsonObject> AddEmployee(@FieldMap Map<String, String> signup);


    /*Employee Role*/
    @POST("Employee_Signup.php?Empcall=role")
    @FormUrlEncoded
    Call<List<RoleForm>> getEmpRole(@Field("Hotel_Id") int hotelId);


    /*Select branch*/
    @POST("Employee_Signup.php?Empcall=branch")
    @FormUrlEncoded
    Call<JsonObject> getBranch(@Field("Hotel_Id") int hotelId);


    /*Get Category Image*/
    @POST("Category.php?category=cate_img")
    @FormUrlEncoded
    Call<JsonObject> getCategoryImage(@Field("Hotel_Id") int Hotel_Id);


    /*Get Employee Role */
    @GET("Employee_Signup.php?Empcall=role")
    Call<List<EmployeeRole>> employeeRole();


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

    /*category Delete*/
    @FormUrlEncoded
    @POST("Category.php?category=subcate_delete")
    Call<JsonObject> deleteCategory(@Field("Category_Id") int Category_Id,
                                    @Field("Branch_Id") int Branch_Id,
                                    @Field("Hotel_Id") int Hotel_Id);


    /*Category Edit*/

    @FormUrlEncoded
    @POST("Category.php?category=subcate_edit")
    Call<JsonObject> editCategory(
            @Field("Category_Name") String Category_Name,
            @Field("C_Image_Name") String C_Image_Name,
            @Field("Category_Id") int Category_Id,
            @Field("Hotel_Id") int Hotel_Id,
            @Field("Branch_Id") int Branch_Id);


    /*  @POST("Category.php?category=subcate_disp")
      @FormUrlEncoded
      Call<JsonObject> getMenuDelete(@Field("Menu_Id") int Menu_Id);

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
                                @Field("Branch_Id") int Branch_Id
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
                              @Field("Branch_Id") int Branch_Id);


    /*offer display*/
    @POST("Offer.php?offer=offer_name")
    @FormUrlEncoded
    Call<JsonObject> GetOfferTitle(@Field("Hotel_Id") int Hotel_Id);

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


    /*offer delete*/
/*
@POST("")
@FormUrlEncoded
Call<JsonObject> getOfferDelete(@Field(""),);
*/


}
