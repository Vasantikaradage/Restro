package com.restrosmart.restrohotel.Interfaces;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Model.RoleForm;


import org.json.JSONObject;

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
    String BASE_URL = "http://192.168.2.220:8080/NewRestroSmart/";

    /*parent category display*/
    @POST("Category.php?category=cate_disp")
    @FormUrlEncoded
    Call<JsonObject> getParentCategory(@Field("Hotel_Id") int Hotel_Id,
                                       @Field("Branch_Id") int Branch_Id);


    /*Fetching all employee Details.*/
    @POST("Employee_Signup.php?Empcall=Get_Employee")
    @FormUrlEncoded
    Call<JsonObject> getallEmployees(@Field("Hotel_Id") int Hotel_Id);

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
                                        @Field("Emp_Img") String image,
                                        @Field("Img_Old_Name") String oldImageName,
                                        @Field("Img_Type") String imageType,

                                        @Field("Emp_Mob") String mobile,
                                        @Field("Emp_Email") String empMobile,
                                        @Field("Emp_Address") String empAddress,
                                        @Field("User_Name") String userName,
                                        @Field("Emp_Adhar_Id") String adhar,
                                        @Field("Hotel_Id") int hotelId);

    /*Admin Edit employee*/
    @POST("Employee_Signup.php?Empcall=Admin_Emp_Edit")
    @FormUrlEncoded
    Call<JsonObject> adminEditEmployeeDetail(@Field("Emp_Id") int empId,
                                             @Field("Emp_Name") String empName,
                                             @Field("Emp_Img") String image,
                                             @Field("Img_Old_Name") String oldImageName,
                                             @Field("Img_Type") String imageType,
                                             @Field("Emp_Mob") String mobile,
                                             @Field("Emp_Email") String empMobile,
                                             @Field("Emp_Address") String empAddress,
                                             @Field("User_Name") String userName,
                                             @Field("Emp_Adhar_Id") String adhar,
                                             @Field("Role_Id") int roleId,
                                             @Field("Hotel_Id") int hotelId);

    /*Update Employee photo*/
    @POST("Branch.php?branch=branch_disp")
    @FormUrlEncoded
    Call<JsonObject> UpdateEmployeeImage(@Field("Hotel_Id") int hotelId,
                                         @Field("Branch_Id") int branchid,
                                         @Field("Emp_Id") int empId);


    /*Update employee status */
    @POST("Employee_Signup.php?Empcall=status")
    @FormUrlEncoded
    Call<JsonObject> updateStatus(@Field("Emp_Id") int Emp_Id,
                                  @Field("Active_Status") String status,
                                  @Field("Hotel_Id") int hotelId);


    /*Admin Login.*/

    //@POST("Employee_Signup.php?Empcall=login")
    @POST("Employee_Signup.php?Empcall=login")
    @FormUrlEncoded
    Call<JsonObject> getLogin(@Field("User_Name") String Username,
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

    /*hotel detail*/
    @POST("Admin_Hotel.php?hotel=hotel_disp")
    @FormUrlEncoded
    Call<JsonObject> getHotelDetail(@Field("Hotel_Id") int hotelId);

    /*branch edit detail*/
    @POST("Branch.php?branch=branch_edit")
    @FormUrlEncoded
    Call<JsonObject> editBranchDetails(
            @Field("Branch_Name") String branchName,
            @Field("Branch_Address") String hotelAddress,
            @Field("Branch_Email") String hotelEmail,
            @Field("Branch_Mob") String hotelMob,
            @Field("Branch_Phone") String branchPhone,
            @Field("Branch_Gstnno") String hotelGstnNo,
            @Field("Branch_Table_Count") int noOfTable,
            @Field("Start_Time") String hotelstartTime,
            @Field("End_Time") String hotelendTime,
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
                                    @Field("Hotel_Id") int hotelId);

    /*Employee Role*/
    @POST("Employee_Signup.php?Empcall=admin_role")
    Call<List<RoleForm>> getEmpRole();


    /*Select branch*/
    @POST("Employee_Signup.php?Empcall=branch")
    @FormUrlEncoded
    Call<JsonObject> getBranch(@Field("Hotel_Id") int hotelId);


    /*Get Category Image*/
    @POST("Admin_Category.php?category=cate_icon")
    @FormUrlEncoded
    Call<JsonObject> getCategoryImage(@Field("Pc_Id") int pc_id);

    /*get flavour image*/
    @POST("Admin_Flavour.php?flavour=flavour_img")
    @FormUrlEncoded
    Call<JsonObject> getFlavourImage(@Field("Menu_Id") int menuId,
                                     @Field("Category_Id") int categoryId,
                                     @Field("Hotel_Id") int hotelId,
                                     @Field("Pc_Id") int pcId);

    /*Add Category*/
    @POST("Admin_Category.php?category=subcate_add")
    @FormUrlEncoded
    Call<JsonObject> addCategory(@Field("Category_Name") String Category_Name,
                                 @Field("C_Image_Name") String C_Image_Name,
                                 @Field("Hotel_Id") int Hotel_Id,
                                 @Field("Pc_Id") int Pc_Id);

    /*category display*/
    // @POST("Category.php?category=allmenu")
    @POST("Admin_Category.php?category=allmenu")
    @FormUrlEncoded
    Call<JsonObject> GetAllCategory(@Field("Hotel_Id") int Hotel_Id);

    /*category display*/

    @POST("Offer_Promo.php?offerpromo=offer_menu")
    @FormUrlEncoded
    Call<JsonObject> GetAllCategorywithMenu(@Field("Hotel_Id") int Hotel_Id);


    //Toppings
    // Toppings display
    @POST("Admin_Topping.php?topping=topping_disp")
    @FormUrlEncoded
    Call<JsonObject> toppingDisplay(@Field("Hotel_Id") int Hotel_Id);

    /*display topping image*/
    @POST("Admin_Topping.php?topping=topping_img")
    @FormUrlEncoded
    Call<JsonObject> toppingImage(@Field("Hotel_Id") int Hotel_Id,
                                  @Field("Pc_Id") int pc_id);

    //toppings add
    @POST("Admin_Topping.php?topping=topping_add")
    @FormUrlEncoded
    Call<JsonObject> addToppings(@Field("Topping_Name") String toppingName,
                                 @Field("Topping_Img") String image,
                                 @Field("Topping_Price") int toppingPrice,
                                 @Field("Hotel_Id") int Hotel_Id,
                                 @Field("Pc_Id") int pc_id);

    //topping delete
    @POST("Admin_Topping.php?topping=topping_del")
    @FormUrlEncoded
    Call<JsonObject> toppingDelete(@Field("Topping_Id") int toppingId,
                                   @Field("Hotel_Id") int Hotel_Id,
                                   @Field("Pc_Id") int pc_id);

    //topping edit
    @POST("Admin_Topping.php?topping=topping_edit")
    @FormUrlEncoded
    Call<JsonObject> toppingEdit(@Field("Topping_Name") String toppingName,
                                 @Field("Topping_Img") String image,
                                 @Field("Topping_Price") int toppingPrice,
                                 @Field("Hotel_Id") int hotelId,
                                 @Field("Pc_Id") int pcId,
                                 @Field("Topping_Id") int toppingId);


    //table add
    @POST("Admin_Table.php?table=area_table_add")
    @FormUrlEncoded
    Call<JsonObject> addtables(@Field("Area_Name") String areaName,
                               @Field("Table_Count") int tableCount,
                               @Field("Hotel_Id") int Hotel_Id);

    //table display
    @POST("Admin_Table.php?table=table_disp")
    @FormUrlEncoded
    Call<JsonObject> tableDisplay(@Field("Hotel_Id") int Hotel_Id);

    //update Area
    @POST("Admin_Table.php?table=Area_edit")
    @FormUrlEncoded
    Call<JsonObject> UpdateArea(@Field("Area_Name") String areaName,
                                @Field("Area_Id") int areaId,
                                @Field("Hotel_Id") int Hotel_I);

    //Area status
    @POST("Admin_Table.php?table=area_status")
    @FormUrlEncoded
    Call<JsonObject> AreaStatus(@Field("Area_Status") int areaStatus,
                                @Field("Area_Id") int areaId,
                                @Field("Hotel_Id") int Hotel_Id);


    //Table status
    @POST("Admin_Table.php?table=table_status")
    @FormUrlEncoded
    Call<JsonObject> TableStatus(@Field("Table_Status") int areaStatus,
                                 @Field("Table_Id") int tableId,
                                 @Field("Hotel_Id") int Hotel_Id);


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
    Call<JsonObject> getCategory(@Field("Hotel_Id") int Hotel_Id);


    /*category display*/
    @POST("Admin_Menu.php?menu=menu_disp")
    @FormUrlEncoded
    Call<JsonObject> getMenus(@Field("Category_Id") int Category_Id,
                              @Field("Pc_Id") int pcId,
                              @Field("Hotel_Id") int hotelId);

    /*delete menu*/
    @POST("Admin_Menu.php?menu=menu_delete")
    @FormUrlEncoded
    Call<JsonObject> getMenuDelete(@Field("Menu_Id") String Menu_Id,
                                   @Field("Hotel_Id") int Hotel_Id,
                                   @Field("Category_Id") int categoryId,
                                   @Field("Pc_Id") int pcId);

    /*menu status*/
    @POST("Admin_Menu.php?menu=Menu_Status")
    @FormUrlEncoded
    Call<JsonObject> getMenuStatus(@Field("Menu_Id") String Menu_Id,
                                   @Field("Hotel_Id") int Hotel_Id,
                                   @Field("Menu_Status") int status);

    /*menu images*/
    @FormUrlEncoded
    @POST("Admin_Menu.php?menu=Menu_images")
    Call<JsonObject> getMenuImage(@Field("Category_Id") int Category_Id,
                                  @Field("Hotel_Id") int Hotel_Id,
                                  @Field("Pc_Id") int Pc_Id);

    /*category Delete*/
    @POST("Admin_Category.php?category=subcate_delete")
    @FormUrlEncoded
    Call<JsonObject> deleteCategory(@Field("Category_Id") int Category_Id,
                                    @Field("Hotel_Id") int Hotel_Id,
                                    @Field("Pc_Id") int pcId);

    /*Flavour display*/
    @POST("Admin_Flavour.php?flavour=flavour_disp")
    @FormUrlEncoded
    Call<JsonObject> flavourDisplay(@Field("Menu_Id") int menuId,
                                    @Field("Hotel_Id") int hotelId,
                                    @Field("Pc_Id") int pcId,
                                    @Field("Category_Id") int categoryId);

    /*Flavour add*/
    @POST("Admin_Flavour.php?flavour=flavour_add")
    @FormUrlEncoded
    Call<JsonObject> flavourAdd(@Field("Flavour_Name") String flavourName,
                                @Field("F_Image_Name") String fImageName,
                                @Field("Menu_Id") int menuId,
                                @Field("Hotel_Id") int hotelId,
                                @Field("Flavour_Status") int flavourStatus,
                                @Field("Flavarray") String flavour);

    /*Flavour edit*/
    @POST("Admin_Flavour.php?flavour=flavour_edit")
    @FormUrlEncoded
    Call<JsonObject> flavourEdit(@Field("Flavour_Name") String flavourName,
                                 @Field("F_Image_Name") String fImageName,
                                 @Field("Flavour_Id") int flavourId,
                                 @Field("Hotel_Id") int hotelId,
                                 @Field("flavunit") String flavour);

    /*Flavour Delete*/
    @POST("Admin_Flavour.php?flavour=flavour_delete")
    @FormUrlEncoded
    Call<JsonObject> flavourDelete(@Field("Hotel_Id") int hotelId,
                                   @Field("Flavour_Id") int flavourId);

    /*Flavour Delete*/
    @POST("Admin_Flavour.php?flavour=flavour_status")
    @FormUrlEncoded
    Call<JsonObject> getFlavourStatus(@Field("Flavour_Id") int flavourId,
                                      @Field("Hotel_Id") int hotelId,
                                      @Field("Flavour_Status") int status);

    /*Category Edit*/
    @FormUrlEncoded
    @POST("Admin_Category.php?category=subcate_edit")
    Call<JsonObject> editCategory(
            @Field("Category_Name") String Category_Name,
            @Field("New_CateName") String Category_Name_New,
            @Field("C_Image_Name") String C_Image_Name,
            @Field("Category_Id") int Category_Id,
            @Field("Hotel_Id") int Hotel_Id,
            @Field("Pc_Id") int pcId);

    /*add menu*/
    @POST("Admin_Menu.php?menu=menu_add")
    @FormUrlEncoded
    Call<JsonObject> getMenuAdd(@Field("Menu_Name") String Menu_Name,
                                @Field("Menu_Descrip") String Menu_Descrip,
                                @Field("Menu_Image_Name") String Menu_Image_Name,
                                @Field("Category_Id") int Category_Id,
                                @Field("Menu_Test") int Menu_Test,
                                @Field("Non_Ac_Rate") String Non_Ac_Rate,
                                @Field("Hotel_Id") int Hotel_Id,
                                @Field("Pc_Id") int pcId,
                                @Field("topparray") String toppingList);

    /*edit menu*/
    @POST("Admin_Menu.php?menu=menu_edit")
    @FormUrlEncoded
    Call<JsonObject> editMenu(@Field("New_Mname") String menuName,
                              @Field("Menu_Descrip") String menuDiscription,
                              @Field("Menu_Image_Name") String menuImageName,
                              @Field("Menu_Test") int menu_test,
                              @Field("Non_Ac_Rate") int nonAcRate,
                              @Field("Menu_Id") String menuId,
                              @Field("Hotel_Id") int Hotel_Id,
                              @Field("Category_Id") int categoryId,
                              @Field("Pc_Id") int pcId,
                              @Field("topparray") String toppingList);

    /*offer display*/
    @POST("Offer.php?offdetail=Offerdetail_disp")
    Call<JsonObject> GetOffer();

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
                                 @Field("Menu_Id") String menu_id,
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
    @POST("Admin_Menu.php?menu=Waterbottle_disp")
    @FormUrlEncoded
    Call<JsonObject> DisplayWaterBottle(@Field("Hotel_Id") int hotel_id);


    /*Add  Water Bottle*/
    @POST("Admin_Menu.php?menu=Waterbottle_add")
    @FormUrlEncoded
    Call<JsonObject> AddWaterBottle(@Field("Water_Name") String menuName,
                                    @Field("Water_Price") int price,
                                    @Field("Hotel_Id") int hotel_id);

    /*edit Water Bottle*/
    @POST("Admin_Menu.php?menu=Waterbottle_edit")
    @FormUrlEncoded
    Call<JsonObject> editWaterBottle(@Field("Water_Id") String waterId,
                                     @Field("Water_Name") String waterName,
                                     @Field("Water_Price") int price,
                                     @Field("Hotel_Id") int hotel_id);

    /*new Order*/
    @POST("Admin_Order.php?order=order_disp")
    @FormUrlEncoded
    Call<JsonObject> Order(@Field("Hotel_Id") int hotel_id);

    /*active table*/
    @POST("Admin_Dashboard.php?admin_dash=Active_Table")
    @FormUrlEncoded
    Call<JsonObject> activeTable(@Field("Hotel_Id") int hotel_id);

    /*disaplay promoCode*/
    @POST("Offer_Promo.php?offerpromo=offerpromo_disp")
    @FormUrlEncoded
    Call<JsonObject> displayPromoCode(@Field("Offer_Type_Id") int offerId,
                                      @Field("Hotel_Id") int hotelId);


    /*Add promoCode*/
    @POST("Offer_Promo.php?offerpromo=offerpromo_add")
    @FormUrlEncoded
    Call<JsonObject> addPromoCode(@Field("Offer_Type_Id") int offerId,
                                  @Field("Offer_Price") String offerPrice,
                                  @Field("Coupon_Code") String promoCode,
                                  @Field("Offer_Desp") String offerDesp,
                                  @Field("Offer_From") String offerFrom,
                                  @Field("Offer_To") String offerTo,
                                  @Field("Hotel_Id") int hotelId,
                                  @Field("Status") int status);

    /*edit promoCode*/
    @POST("Offer_Promo.php?offerpromo=offerpromo_edit")
    @FormUrlEncoded
    Call<JsonObject> editPromoCode(@Field("Offer_Id") int offerId,
                                   @Field("Offer_From") String offerFrom,
                                   @Field("Offer_To") String offerTo,
                                   @Field("Offer_Price") String offerPrice,
                                   @Field("Hotel_Id") int hotelId);

    /*delete promoCode*/
    @POST("Offer_Promo.php?offerpromo=offer_delete")
    @FormUrlEncoded
    Call<JsonObject> deleteOffer(@Field("Hotel_Id") int hotel_id,
                                 @Field("Offer_Id") int offerId);


    /*Add rushHours*/
    @POST("Offer_Rush.php?offerrush=offerrush_add")
    @FormUrlEncoded
    Call<JsonObject> addRushHours(@Field("Offer_From") String offerFrom,
                                  @Field("Offer_To") String offerTo,
                                  @Field("Hotel_Id") int hotel_id,
                                  @Field("Offer_Desp") String offer_disp,
                                  @Field("Status") int status,
                                  @Field("Offer_Name") String offer_name,
                                  @Field("Offer_Type_Id") int offerTypeId);

    /*display rushHours*/
    @POST("Offer_Rush.php?offerrush=offerrush_disp")
    @FormUrlEncoded
    Call<JsonObject> displayRushHours(@Field("Hotel_Id") int hotel_id,
                                      @Field("Offer_Type_Id") int offerTypeId);


    /*edit rush hours*/
    @POST("Offer_Rush.php?offerrush=offerrush_edit")
    @FormUrlEncoded
    Call<JsonObject> editRushHours(@Field("Offer_Id") int offerId,
                                   @Field("Hotel_Id") String hotel_id,
                                   @Field("Offer_From") String offerFrom,
                                   @Field("Offer_To") String offerTo,
                                   @Field("Offer_Desp") String offerDescription,
                                   @Field("Offer_Name") String offerName);

    /*delete rush hours*/
    @POST("Offer_Promo.php?offerpromo=offerpromo_delete")
    @FormUrlEncoded
    Call<JsonObject> deleteRushHours(@Field("Hotel_Id") int hotel_id,
                                     @Field("Offer_Id") int offerId);

    /*apply offer rush hours*/
    @POST("Offer_Rush.php?offerrush=offerrush_apply")
    @FormUrlEncoded
    Call<JsonObject> applyRushHours(@Field("Hotel_Id") int hotel_id,
                                    @Field("Offer_Id") int offerTypeId,
                                    @Field("MenuOfferArray") String menuOffer);


    /*
/*display Scratchcard*/
    @POST("Offer_Scratch.php?offerscratch=offerscratch_disp")
    @FormUrlEncoded
    Call<JsonObject> displayScratchcard(@Field("Offer_Type_Id") int offerTypeId,
                                        @Field("Hotel_Id") int hotelId);


    /*Add Scratchcard*/
    @POST("Offer_Scratch.php?offerscratch=offerscratch_add")
    @FormUrlEncoded
    Call<JsonObject> addScratchcard(@Field("Offer_Type_Id") int offerTypeId,
                                    @Field("Offer_Name") String offerName,
                                    @Field("Offer_Desp") String offerDiscription,
                                    @Field("Offer_From") String offerFrom,
                                    @Field("Offer_To") String offerTo,
                                    @Field("Ppl_Total_Count") String pplTotalCount,
                                    @Field("Ppl_Winner_Count") String pplWinnerCount,
                                    @Field("Hotel_Id") int hotelId);

    /*apply offer rush hours*/
    @POST("Offer_Scratch.php?offerscratch=offerscratch_apply")
    @FormUrlEncoded
    Call<JsonObject> applyScratchcard(@Field("Hotel_Id") int hotel_id,
                                      @Field("Offer_Id") int offerId,
                                      @Field("MenuOfferArray") String menuOffer);


    /*edit Scratchcard*/
    @POST("Offer_Scratch.php?offerscratch=offerscratch_edit")
    @FormUrlEncoded
    Call<JsonObject> editScratchcard(@Field("Offer_Id") int offerId,
                                     @Field("Offer_Desp") String offerDisc,
                                     @Field("Offer_Name") String offerName,
                                     @Field("Offer_From") String offerFrom,
                                     @Field("Offer_To") String offerTo,
                                     @Field("Ppl_Total_Count") String pplTotalCount,
                                     @Field("Ppl_Winner_Count") String pplWinnerCount,
                                     @Field("Hotel_Id") int hotelId);

    /*Add promoCode*/
    @POST("Admin_Dashboard.php?admin_dash=Active_Table")
    @FormUrlEncoded
    Call<JsonObject> deleteScratchcard(@Field("Hotel_Id") String hotel_id,
                                       @Field("Hotel_Id") String hotel_id1);


    /*Add DailyOffer*/
    @POST("Offer_Daily.php?daily=offerdaily_add")
    @FormUrlEncoded
    Call<JsonObject> addDailyOffer(@Field("Offer_Type_Id") int offerId,
                                   @Field("Offer_Price") String offerPrice,
                                   @Field("Offer_Name") String offerName,
                                   @Field("Offer_Img") String offerImage,
                                   @Field("Offer_Desp") String offerDesp,
                                   @Field("Offer_From") String offerFrom,
                                   @Field("Offer_To") String offerTo,
                                   @Field("Hotel_Id") int hotelId,
                                   @Field("Offer_Price_Status") int status,
                                   @Field("Buy_Count") String buyCnt,
                                   @Field("Get_Count") String getCnt);


    /*apply offer Daily offer*/
    @POST("Offer_Daily.php?daily=offerdaily_apply")
    @FormUrlEncoded
    Call<JsonObject> applyDailyOffer(@Field("Hotel_Id") int hotel_id,
                                     @Field("Offer_Id") int offerTypeId,
                                     @Field("MenuOfferArray") String menuOffer);

    /*edit DailyOffer*/
    @POST("Offer_Daily.php?daily=offerdaily_edit")
    @FormUrlEncoded
    Call<JsonObject> editDailyOffer(@Field("Offer_Id") int offerId,
                                    @Field("Offer_Price") String offerPrice,
                                    @Field("Offer_Name") String offerName,
                                    @Field("Offer_Img") String offerImage,
                                    @Field("Offer_Desp") String offerDesp,
                                    @Field("Offer_From") String offerFrom,
                                    @Field("Offer_To") String offerTo,
                                    @Field("Hotel_Id") int hotelId,
                                    @Field("Offer_Price_Status") int status,
                                    @Field("Buy_Count") String buyCnt,
                                    @Field("Get_Count") String getCnt);


    /*get parent Category*/
    @POST("user/get_menu.php")
    @FormUrlEncoded
    Call<JsonObject> getCategory(@Field("Hotel_Id") int Hotel_Id,
                                 @Field("u_key") String u_key);

    /*Get Banner Image*/
    @POST("Offer_Daily.php?daily=dailyimg_disp")
    @FormUrlEncoded
    Call<JsonObject> getBannerImage(@Field("Hotel_Id") int Hotel_Id);

    /*display dailyoffer*/
    @POST("Offer_Daily.php?daily=offerdaily_disp")
    @FormUrlEncoded
    Call<JsonObject> displayDailyOffer(@Field("Offer_Type_Id") int offerTypeId,
                                       @Field("Hotel_Id") int Hotel_Id);
    /*

     */
    /*get sub menu amd category *//*

    @POST("user/get_menulist.php")
    @FormUrlEncoded
    Call<JsonObject> getSubCategoryMenu(@Field("Hotel_Id") int Hotel_Id,
                                        @Field("Pc_Id") int Pc_Id,
                                        @Field("u_key") String u_key);
*/


    /*CAPTAIN START*/
    /*Scan table list*/
    @POST("Captain_Table.php?captables=scan_tablelist")
    @FormUrlEncoded
    Call<JsonObject> getScanTable(@Field("Hotel_Id") int hotel_id,
                                  @Field("Emp_Id") int emp_id);

    /*Scan table confirm*/
    @POST("Captain_Table.php?captables=conf_tablelist")
    @FormUrlEncoded
    Call<JsonObject> scanConfirmTable(@Field("Hotel_Id") int hotel_id,
                                      @Field("Table_Id") int Table_Id,
                                      @Field("Table_No") int Table_No,
                                      @Field("Area_Id") int Area_Id,
                                      @Field("Emp_Id") int Emp_Id,
                                      @Field("Table_conf_Status") int Table_conf_Status);

    /*Get booked table */
    @POST("Captain_Table.php?captables=book_free_table")
    @FormUrlEncoded
    Call<JsonObject> getBookedTable(@Field("Hotel_Id") int hotel_id,
                                    @Field("Emp_Id") int Emp_Id);

    /*Swap tables*/
    @POST("Captain_Table.php?captables=swap_table")
    @FormUrlEncoded
    Call<JsonObject> swapTable(@Field("Hotel_Id") int hotel_id,
                               @Field("Old_TableNo") int oldtable_id,
                               @Field("New_TableNo") int newtable_id,
                               @Field("Cust_Id") String cust_id);

    /*Get Captain Profile*/
    @POST("Employee_Signup.php?Empcall=Emp_Profile")
    @FormUrlEncoded
    Call<JsonObject> getCaptainProfile(@Field("Hotel_Id") int hotel_id,
                                       @Field("Emp_Id") int emp_id);

    /*Get table orders*/
    @POST("user/order_list.php")
    @FormUrlEncoded
    Call<JsonObject> getTableOrders(@Field("Hotel_Id") int hotel_id,
                                    @Field("Cust_Id") String cust_id,
                                    @Field("u_key") String u_key);

    /*get parcel/take away Orders*/
    @POST("Captain_Order.php?caporders=table_orders")
    @FormUrlEncoded
    Call<JsonObject> getHaveParcelOrders(@Field("Hotel_Id") int hotel_id);

    /*delete Order*/
    @POST("Captain_Order.php?caporders=cap_order_delete")
    @FormUrlEncoded
    Call<JsonObject> capDeleteOrder(@Field("Hotel_Id") int hotel_id,
                                    @Field("Table_Id") int table_id,
                                    @Field(" Order_Id") int order_id,
                                    @Field("Cust_Id") String cust_id);

    /*ready Order*/
    @POST("Captain_Order.php?caporders=order_ready")
    @FormUrlEncoded
    Call<JsonObject> capReadyOrder(@Field("Hotel_Id") int hotel_id,
                                   @Field(" Order_Id") int order_id);

    /*complete Order*/
    @POST("user/user_order_complete.php")
    @FormUrlEncoded
    Call<JsonObject> capCompleteOrder(@Field("Hotel_Id") int hotel_id,
                                      @Field("Table_Id") int table_id,
                                      @Field("Cust_Id") String cust_id,
                                      @Field("oredridlist") String oredridlist,
                                      @Field("u_key") String u_key);

    /*get free tables*/
    @POST("Captain.php?captain=Cap_Table")
    @FormUrlEncoded
    Call<JsonObject> getFreeTables(@Field("Hotel_Id") int hotel_id,
                                   @Field("Emp_Id") int emp_id);

    /*register customer from captain for having here orders*/
    @POST("Captain.php?captain=Cap_Customer")
    @FormUrlEncoded
    Call<JsonObject> registerCustomer(@Field("Hotel_Id") int hotel_id,
                                      @Field("Table_Id") int table_id,
                                      @Field("Table_No") int table_no,
                                      @Field("Ccust_Name") String cust_name,
                                      @Field("Ccust_Mob") String cust_mob);

    @POST("user/get_menulist.php")
    @FormUrlEncoded
    Call<JsonObject> getSubCategoryMenu(@Field("Hotel_Id") int Hotel_Id,
                                        @Field("Pc_Id") int Pc_Id,
                                        @Field("u_key") String u_key);

    @POST("user/get_liquorlist.php")
    @FormUrlEncoded
    Call<JsonObject> getLiqourCategory(@Field("Hotel_Id") int Hotel_Id,
                                       @Field("Pc_Id") int Pc_Id,
                                       @Field("u_key") String u_key);

    @POST("user/water_bottle.php")
    @FormUrlEncoded
    Call<JsonObject> getWaterBottle(@Field("Hotel_Id") int Hotel_Id,
                                    @Field("u_key") String u_key);

    /*@POST("User_Category.php?user_cate=liquor_list")
    @FormUrlEncoded
    Call<JsonObject> getLiqourCategory(@Field("Hotel_Id") int Hotel_Id,
                                       @Field("Branch_Id") int Branch_Id,
                                       @Field("Pc_Id") int Pc_Id);*/

    /*Add to cart*/
    @POST("user/cart.php")
    @FormUrlEncoded
    Call<JsonObject> addToCart(@Field("Order_Id") int Order_Id,
                               @Field("Table_Id") int Table_Id,
                               @Field("Table_No") int Table_No,
                               @Field("Cust_Id") String Cust_Id,
                               @Field("Hotel_Id") int Hotel_Id,
                               @Field("Menu_Id") String Menu_Id,
                               @Field("Menu_Name") String Menu_Name,
                               @Field("Menu_Price") String Menu_Price,
                               @Field("Qty") int Qty,
                               @Field("Flavour_Name") String Flavour_Name,
                               @Field("Flav_Qty") int Flav_Qty,
                               @Field("Unit_Name") String Unit_Name,
                               @Field("Unit_Cost") String Unit_Cost,
                               @Field("toppingarray") String Topping_list,
                               @Field("Menu_Status") int Menu_Status,
                               @Field("Conf_Status") int Conf_Status,
                               @Field("FL_Status") int FL_Status,
                               @Field("Order_Status") int Order_Status,
                               @Field("Offer_Type_Id") int Offer_Type_Id,
                               @Field("Pc_Id") int Pc_Id,
                               @Field("Menu_List") String Menu_List,
                               @Field("u_key") String u_key);


    /*display cart menu*/
    @POST("user/cart_display.php")
    @FormUrlEncoded
    Call<JsonObject> getCartDisplay(@Field("Hotel_Id") int Hotel_Id,
                                    @Field("Order_Id") int Order_Id,
                                    @Field("Cust_Id") String Cust_Id,
                                    @Field("Table_Id") int Table_Id,
                                    @Field("u_key") String u_key);

    /*remove cart menu*/
    @POST("user/cart_menu_delete.php")
    @FormUrlEncoded
    Call<JsonObject> removeCartMenu(@Field("Hotel_Id") int Hotel_Id,
                                    @Field("Order_Detail_Id") int Order_Detail_Id,
                                    @Field("u_key") String u_key);

    /*save cart menu*/
    @POST("user/cart_menu_edit.php")
    @FormUrlEncoded
    Call<JsonObject> saveCartMenu(@Field("Hotel_Id") int Hotel_Id,
                                  @Field("Order_Detail_Id") int Order_Detail_Id,
                                  @Field("Qty") String Qty,
                                  @Field("FL_Status") String FL_Status,
                                  @Field("toppingarraylist") String toppingarraylist,
                                  @Field("u_key") String u_key);

    /*place order*/
    @POST("user/place_order.php")
    @FormUrlEncoded
    Call<JsonObject> placeOrder(@Field("Hotel_Id") int Hotel_Id,
                                @Field("Table_Id") int Table_Id,
                                @Field("Table_No") int mTableNo,
                                @Field("Cust_Id") String Cust_Id,
                                @Field("Order_Id") int Order_Id,
                                @Field("Order_Msg") String Order_Msg,
                                @Field("Sub_Total") float Sub_Total,
                                @Field("u_key") String u_key);
    /*CAPTAIN END*/


    /*Employee Image Update*/
    @POST("Employee_Signup.php?Empcall=EmpImg_Update")
    @FormUrlEncoded
    Call<JsonObject> employeeImageUpdate(@Field("Emp_Img") String empImage,
                                         @Field("Img_Type") String empImageType,
                                         @Field("Img_Old_Name") String oldImage,
                                         @Field("Emp_Id") int empId,
                                         @Field("Hotel_Id") int hotel_id);

    /*Swap
    tables for admin side*/
    @POST("Admin_Table.php?table=admin_move_table")
    @FormUrlEncoded
    Call<JsonObject> AdminSwapTable(@Field("Hotel_Id") int hotel_id,
                                    @Field("Old_AreaId") int old_area_id,
                                    @Field("New_AreaId") int new_area_id,
                                    @Field("Table_Id") int tableId);

    /*Allocate table to captain*/
    @POST("Admin_Table.php?table=allocate_table")
    @FormUrlEncoded
    Call<JsonObject> allocateTable(@Field("Tableidarray") String areaIdList,
                                   @Field("Emp_Id") int empId,
                                   @Field("Area_Id") int areaId,
                                   @Field("Hotel_Id") int hotel_id);


    //table display for Captain
    @POST("Admin_Table.php?table=assigned_table_list")
    @FormUrlEncoded
    Call<JsonObject> captainTableDisplay(@Field("Hotel_Id") int Hotel_Id);



    /*Super Admin*/

    /*Fetching all admin employee Details.*/
    @POST("SA_Emp.php?saemp=SA_Employee")
    @FormUrlEncoded
    Call<JsonObject> getSAEmployees(@Field("SA_Id") int saId);

    /*Fetching all hotel Details.*/
    @POST("SA_Hotel_Reg.php?sahotel=hotel_disp")
    @FormUrlEncoded
    Call<JsonObject> getSAHotelDetails(@Field("SA_Id") int saId);

    /*Fetching all hotel Type.*/
    @POST("SA_Hotel_Reg.php?sahotel=hotel_type")
    Call<JsonObject> getSAHotelType();

    /*Fetching Country dfetails*/
    @POST("SA_Hotel_Reg.php?sahotel=country")
    Call<JsonObject> getCountry();

    /*Fetching state dfetails*/
    @POST("SA_Hotel_Reg.php?sahotel=state")
    @FormUrlEncoded
    Call<JsonObject> getState(@Field("Country_Id") int countryId);

    /*Fetching city dfetails*/
    @POST("SA_Hotel_Reg.php?sahotel=city")
    @FormUrlEncoded
    Call<JsonObject> getCity(@Field("State_Id") int stateId);

    /*Fetching cuisine dfetails*/
    @POST("SA_Hotel_Reg.php?sahotel=cuisine")
    Call<JsonObject> getCuisine();

    /*Fetching cuisine dfetails*/
    @POST("SA_Hotel_Reg.php?sahotel=tags")
    Call<JsonObject> getTags();

    /*Fetching hotel basic registration dfetails*/
    @POST("SA_Hotel_Reg.php?sahotel=hotel_reg")
    @FormUrlEncoded
    Call<JsonObject> hotelRegistration(@Field("Hotel_Name") String hotelName,
                                       @Field("Hotel_Mob") String mob,
                                       @Field("Hotel_Phone") String phone,
                                       @Field("Hotel_Email") String email,
                                       @Field("Hotel_Country") int countryId,
                                       @Field("Hotel_State") int stateId,
                                       @Field("Hotel_City") int cityId,
                                       @Field("Hotel_Area") String hotelArea,
                                       @Field("Hotel_Address") String hotelAddress,
                                       @Field("SA_Id") int sa_id,

                                       @Field("Hotel_Type_Id") int hotel_type_id,
                                       @Field("Hotel_Table_Count") int hotel_table_count,
                                       @Field("cusisinearray") String cuisineArray,
                                       @Field("tagsarray") String tagsArray,

                                       @Field("Hotel_Gstinno") String gstn,
                                       @Field("Hotel_CGST") String cgst,
                                       @Field("Hotel_SGST") String sgst);

    /*Fetching image details*/
    @POST("SA_Hotel_Reg.php?sahotel=hotel_img")
    @FormUrlEncoded
    Call<JsonObject> saveHotelImage(@Field("Hotel_Id") int hotelId,
                                    @Field("Hotel_Img") String imageBitmap,
                                    @Field("Img_Ext") String extension);


    //   /*feathing hotel Other details */
    @POST("SA_Hotel_Reg.php?sahotel=hotel_Other_detail")
    @FormUrlEncoded
    Call<JsonObject> otherHotelDetails(@Field("Hotel_Type_Id") int hotel_type_id,
                                       @Field("Hotel_Table_Count") String hotel_table_count,
                                       @Field("Hotel_Id") int hotelId,
                                       @Field("cusisinearray") String cuisineArray,
                                       @Field("tagsarray") String tagsArray);

    ///*feathching sa profile*/
    @POST("SA_Emp.php?saemp=SA_Disp")
    @FormUrlEncoded
    Call<JsonObject> getSuperAdminProfile(@Field("SA_Id") int saId);
}

