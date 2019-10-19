package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.CategoryForm;
import com.restrosmart.restrohotel.Model.MenuDisplayForm;
import com.restrosmart.restrohotel.Model.ToppingsForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.PARENT_CATEGORY_WITH_SUB;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class AdapterDisplayAllCategoryOffer extends RecyclerView.Adapter<AdapterDisplayAllCategoryOffer.MyHolder> {
    private Context context;
    private List<CategoryForm> arrayList;
    private  RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private ArrayList<MenuDisplayForm> arrayListMenu=new ArrayList<>();
    private RecyclerView rvMenu;
    private  Sessionmanager sessionmanager;
    private  int hotelId,branchId;

    private int mExpandedPosition = -1, previousExpandedPosition;

    public AdapterDisplayAllCategoryOffer(Context activity, ArrayList<CategoryForm> arrayList) {
        this.context = activity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_category_itemlist_offer, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        holder.tx_name.setText(arrayList.get(position).getCategory_Name());

        final boolean isExpanded = position == mExpandedPosition;
        rvMenu.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);

        sessionmanager = new Sessionmanager(context);
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(name_info.get(HOTEL_ID));

        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack,context);
        mRetrofitService.retrofitData(PARENT_CATEGORY_WITH_SUB, (service.getMenus(arrayList.get(position).getCategory_id(),arrayList.get(position).getPc_Id(),hotelId )));
      /*  holder.tvReschedule.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.viewLine.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.viewLine1.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);

        holder.tvReschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                positionListerner.positionListern(i);

            }
        });
*/



        if (isExpanded)
            previousExpandedPosition = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1 : position;
                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(position);

                // holder.btnAdd.setAnimation(rightToLeft);
                //  holder.cvQty.setAnimation(leftToRight);

                /*selectedPos = position;
                notifyDataSetChanged();*/
            }
        });

    }

    private void initRetrofitCallBack() {
        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String mRespnse = jsonObject.toString();
                try {
                    JSONObject jsonObject1 = new JSONObject(mRespnse);

                    int status = jsonObject1.getInt("status");
                    if (status == 1) {
                       // llNoMenuData.setVisibility(View.GONE);

                        JSONArray jsonArray = jsonObject1.getJSONArray("submenu");

                       arrayListMenu.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                            MenuDisplayForm menuForm = new MenuDisplayForm();
                            menuForm.setMenu_Id(jsonObject2.getInt("Menu_Id"));
                            menuForm.setCategory_Id(jsonObject2.getInt("Category_Id"));
                            menuForm.setMenu_Name(jsonObject2.getString("Menu_Name"));
                            menuForm.setMenu_Image_Name(jsonObject2.getString("Menu_Image_Name"));
                            menuForm.setNon_Ac_Rate(jsonObject2.getInt("Non_Ac_Rate"));
                            menuForm.setMenu_Test(jsonObject2.getInt("Menu_Test"));
                            menuForm.setMenu_Descrip(jsonObject2.getString("Menu_Descrip"));
                            menuForm.setHotel_Id(jsonObject2.getInt("Hotel_Id"));
                            //menuForm.setArrayListtoppings(jsonObject2.getJSONArray("Topping"));


                            JSONArray toppingArray=jsonObject2.getJSONArray("Topping");

                           /* arrayListToppings=new ArrayList<>();
                            for(int in=0; in<toppingArray.length(); in++)
                            {

                                JSONObject toppingsObject=toppingArray.getJSONObject(in);
                                ToppingsForm toppingsForm=new ToppingsForm();
                                toppingsForm.setToppingId(toppingsObject.getInt("Topping_Id"));
                                toppingsForm.setToppingsName(toppingsObject.getString("Topping_Name"));
                                toppingsForm.setToppingsPrice(toppingsObject.getInt("Topping_Price"));
                                arrayListToppings.add(toppingsForm);
                            }

                            menuForm.setArrayListtoppings(arrayListToppings);*/
                            arrayListMenu.add(menuForm);
                        }
                        callAdapter();

                    } else {
                       // llNoMenuData.setVisibility(View.VISIBLE);
                       // progressBar.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyError(int requestId, Throwable error) {

            }
        };
    }

    private void callAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvMenu.setHasFixedSize(true);
        rvMenu.setLayoutManager(linearLayoutManager);

        AdapterDisplayAllMenusOffer displayAllMenus = new AdapterDisplayAllMenusOffer(context, arrayListMenu);
        rvMenu.setAdapter(displayAllMenus);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private  TextView tx_name;
        private  CircleImageView circleImageView;
        private ImageButton imgBtnEdit, imagBtnDelete;




        public MyHolder(final View itemView) {
            super(itemView);

            circleImageView = (CircleImageView) itemView.findViewById(R.id.circle_image);
            tx_name = (TextView) itemView.findViewById(R.id.tv_category_name);
            imgBtnEdit = itemView.findViewById(R.id.btn_edit_button);
            imagBtnDelete = itemView.findViewById(R.id.btn_delete_button);
            rvMenu=itemView.findViewById(R.id.rv_menu);


        }
    }
}
