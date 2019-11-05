package com.restrosmart.restrohotel.Interfaces;

import com.google.gson.JsonObject;
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
    String BASE_URL = "http://192.168.1.218/NewRestroSmart/";

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
                                        @Field("Role_Id")int roleId,
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
    @POST("Admin_Menu.php?menu=menu_disp")
    @FormUrlEncoded
    Call<JsonObject> getMenus(@Field("Category_Id") int Category_Id,
                              @Field("Pc_Id") int pcId,
                              @Field("Hotel_Id") int hotelId);

    /*delete menu*/
    @POST("Admin_Menu.php?menu=menu_delete")
    @FormUrlEncoded
    Call<JsonObject> getMenuDelete(@Field("Menu_Id") int Menu_Id,
                                   @Field("Hotel_Id") int Hotel_Id,
                                   @Field("Category_Id") int categoryId,
                                   @Field("Pc_Id") int pcId);

    /*menu status*/
    @POST("Admin_Menu.php?menu=Menu_Status")
    @FormUrlEncoded
    Call<JsonObject> getMenuStatus(@Field("Menu_Id") int Menu_Id,
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
                                @Field("topparraylist") String toppingList);

    /*edit menu*/
    @POST("Admin_Menu.php?menu=menu_edit")
    @FormUrlEncoded
    Call<JsonObject> editMenu(@Field("New_Mname") String menuName,
                              @Field("Menu_Descrip") String menuDiscription,
                              @Field("Menu_Image_Name") String menuImageName,
                              @Field("Menu_Test") int menu_test,
                              @Field("Non_Ac_Rate") int nonAcRate,
                              @Field("Menu_Id") int menuId,
                              @Field("Hotel_Id") int Hotel_Id,
                              @Field("Category_Id") int categoryId,
                              @Field("Pc_Id") int pcId,
                              @Field("topparray") String toppingList);

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
                               @Field("Cust_Id") int cust_id);

    /*Get Captain Profile*/
    @POST("Employee_Signup.php?Empcall=Emp_Profile")
    @FormUrlEncoded
    Call<JsonObject> getCaptainProfile(@Field("Hotel_Id") int hotel_id,
                                       @Field("Emp_Id") int emp_id);

    /*Get table orders*/
    @POST("Captain_Table.php?captables=table_order")
    @FormUrlEncoded
    Call<JsonObject> getTableOrders(@Field("Hotel_Id") int hotel_id,
                                    @Field("Table_Id") int table_id,
                                    @Field("Cust_Id") int cust_id);

    @POST("User_Category.php?user_cate=liquor_list")
    @FormUrlEncoded
    Call<JsonObject> getLiqourCategory(@Field("Hotel_Id") int Hotel_Id,
                                       @Field("Branch_Id") int Branch_Id,
                                       @Field("Pc_Id") int Pc_Id);

    /*Add to cart*/
    @POST("User_Cart.php?user_cart=Cart")
    @FormUrlEncoded
    Call<JsonObject> addToCart(@Field("Order_Id") int Order_Id,
                               @Field("Table_Id") int Table_Id,
                               @Field("Cust_Id") int Cust_Id,
                               @Field("Hotel_Id") int Hotel_Id,
                               @Field("Branch_Id") int Branch_Id,
                               @Field("Menu_Id") int Menu_Id,
                               @Field("Qty") int Qty,
                               @Field("toppingarray") String Topping_list,
                               @Field("Menu_Status") int Menu_Status,
                               @Field("Conf_Status") int Conf_Status,
                               @Field("FL_Status") int FL_Status,
                               @Field("Order_Status") int Order_Status);

    /*display cart menu*/
    @POST("User_Cart.php?user_cart=Cart_Disp")
    @FormUrlEncoded
    Call<JsonObject> getCartMenu(@Field("Hotel_Id") int Hotel_Id,
                                 @Field("Branch_Id") int Branch_Id,
                                 @Field("Order_Id") int Order_Id);

    /*place order*/
    @POST("User_Cart.php?user_cart=Place_Order")
    @FormUrlEncoded
    Call<JsonObject> placeOrder(@Field("Hotel_Id") int Hotel_Id,
                                @Field("Branch_Id") int Branch_Id,
                                @Field("Table_Id") int Table_Id,
                                @Field("Cust_Id") int Cust_Id,
                                @Field("Order_Id") int Order_Id,
                                @Field("Order_Msg") String Order_Msg);

    /*remove cart menu*/
    @POST("User_Cart.php?user_cart=CartMenu_delete")
    @FormUrlEncoded
    Call<JsonObject> removeCartMenu(@Field("Hotel_Id") int Hotel_Id,
                                    @Field("Branch_Id") int Branch_Id,
                                    @Field("Order_Detail_Id") int Order_Detail_Id);

    /*save cart menu*/
    @POST("User_Cart.php?user_cart=CartMenu_Edit")
    @FormUrlEncoded
    Call<JsonObject> saveCartMenu(@Field("Hotel_Id") int Hotel_Id,
                                  @Field("Branch_Id") int Branch_Id,
                                  @Field("Order_Detail_Id") int Order_Detail_Id,
                                  @Field("Qty") String Qty,
                                  @Field("toppingarraylist") String toppingarraylist);
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

    /*Fetching all employee Details.*/
    @POST("Employee_Signup.php?Empcall=Get_Employee")
    Call<JsonObject> getSAEmployees();

    /*Fetching all hotel Details.*/
    @POST("Employee_Signup.php?Empcall=Get_Employee")
    Call<JsonObject> getSAHotelDetails();

    /*Fetching all hotel Type.*/
    @POST("SA_Hotel_Reg.php?sahotel=hotel_type")
    Call<JsonObject> getSAHotelType();

    /*Fetching Country dfetails*/
    @POST("SA_Hotel_Reg.php?sahotel=country")
    Call<JsonObject> getCountry();

    /*Fetching state dfetails*/
    @POST("SA_Hotel_Reg.php?sahotel=state")
    @FormUrlEncoded
    Call<JsonObject> getState(@Field("Country_Id")int countryId);

    /*Fetching city dfetails*/
    @POST("SA_Hotel_Reg.php?sahotel=city")
    @FormUrlEncoded
    Call<JsonObject> getCity(@Field("State_Id")int stateId);

    /*Fetching city dfetails*/
    @POST("SA_Hotel_Reg.php?sahotel=cuisine")
    Call<JsonObject> getCuisine();

    /*Fetching city dfetails*/
    @POST("SA_Hotel_Reg.php?sahotel=hotel_reg")
    @FormUrlEncoded
    Call<JsonObject> hotelRegistration(@Field("Hotel_Name")String hotelName,
                                       @Field("Hotel_Mob") String mob,
                                       @Field("Hotel_Phone") String phone,
                                       @Field("Hotel_Email") String email,
                                       @Field("Hotel_Country") int countryId,
                                       @Field("Hotel_State") int stateId,
                                       @Field("Hotel_City") int cityId,
                                       @Field("Hotel_Area") String hotelArea,
                                       @Field("Hotel_Address") String hotelAddress,
                                       @Field("SA_Id") int sa_id
                                       /*@Field("action") String action*/);



}

