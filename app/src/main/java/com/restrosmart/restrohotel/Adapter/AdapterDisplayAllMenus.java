package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.restrosmart.restrohotel.Admin.ActivityAddNewMenu;
import com.restrosmart.restrohotel.Admin.ActivityFlavour;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Model.MenuDisplayForm;
import com.restrosmart.restrohotel.Model.ToppingsForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.restrosmart.restrohotel.Utils.flowtextview.FlowTextView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class AdapterDisplayAllMenus extends RecyclerView.Adapter<AdapterDisplayAllMenus.MyHolder> {

    private  Context context;
    private ArrayList<MenuDisplayForm> arrayListMenu;
    private PositionListener resultPosition;
    private DeleteListener deleteResult;
    private String mHotelId, mBranchId;
    private int pcId;
    private EditListener editListener;
    private  View dialoglayout;
    private  AlertDialog dialog;
    private  CircleImageView mCircularImageView;
    private  TextView menuName,menuPrice;

    private  RecyclerView rvTopping;
    private ImageButton imageBtnCancel;

    private  ArrayList<ToppingsForm> arrayListToppings;
    Menu item;
    private ApiService apiService;



    public AdapterDisplayAllMenus(Context context, int pcId, DeleteListener deleteResult, ArrayList<MenuDisplayForm> arrayListMenu, PositionListener category, EditListener editListener) {
        this.context = context;
        this.pcId = pcId;
        this.deleteResult = deleteResult;
        this.arrayListMenu = arrayListMenu;
        this.resultPosition = category;
        this.editListener = editListener;

    }

    @NonNull
    @Override
    public AdapterDisplayAllMenus.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_menu_list, parent, false);

        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterDisplayAllMenus.MyHolder holder, final int position) {

        Picasso.with(context).load(apiService.BASE_URL+arrayListMenu.get(position).getMenu_Image_Name())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_STORE)
                .into(holder.image);

        holder.mMenuName.setText(arrayListMenu.get(position).getMenu_Name());
        holder.mMenuDisp.setText(arrayListMenu.get(position).getMenu_Descrip());

        arrayListToppings=arrayListMenu.get(position).getArrayListtoppings();

        int mTeste = arrayListMenu.get(position).getMenu_Test();

        if (mTeste == 1) {
            holder.mMenuTeste.setVisibility(View.VISIBLE);
            holder.mMenuTeste.setText("(Sweet)");
        } else if (mTeste == 2) {
            holder.img_spicy.setVisibility(View.VISIBLE);
        } else {

        }


        Sessionmanager sharedPreferanceManage = new Sessionmanager(context);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();

        mHotelId = name_info.get(HOTEL_ID);
        mBranchId = name_info.get(BRANCH_ID);

        if (pcId == 3) {

            holder.llMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActivityFlavour.class);
                    intent.putExtra("menuName", arrayListMenu.get(position).getMenu_Name());
                    intent.putExtra("menuId", arrayListMenu.get(position).getMenu_Id());
                    intent.putExtra("pcId", pcId);
                    intent.putExtra("categoryId", arrayListMenu.get(position).getCategory_Id());
                    context.startActivity(intent);

                }
            });
        }
        else
        {
            holder.llMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewMenuInfo(position);

                }
            });
        }

        holder.mMenuOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.mMenuOption);
                //inflating menu from xml resource
                if (pcId == 3) {
                    popup.inflate(R.menu.option_menu_liquors);

                } else {
                    popup.inflate(R.menu.options_menu);
                }
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.menu_edit:
                                    //handle menu1 click
                                    Intent intent = new Intent(context, ActivityAddNewMenu.class);
                                    intent.putExtra("MenuId", arrayListMenu.get(position).getMenu_Id());
                                    intent.putExtra("ImageName", arrayListMenu.get(position).getMenu_Image_Name());
                                    intent.putExtra("MenuName", arrayListMenu.get(position).getMenu_Name());
                                    intent.putExtra("Price", arrayListMenu.get(position).getNon_Ac_Rate());
                                    intent.putExtra("MenuDiscription", arrayListMenu.get(position).getMenu_Descrip());
                                    intent.putExtra("MenuTaste", arrayListMenu.get(position).getMenu_Test());
                                    intent.putExtra("categoryId",arrayListMenu.get(position).getCategory_Id());
                                    intent.putExtra("pc_Id",pcId);
                                    intent.putParcelableArrayListExtra("ArrayListToppings", arrayListMenu.get(position).getArrayListtoppings());


                                    context.startActivity(intent);
                                    return true;

                                case R.id.menu_delete:
                                    //handle menu2 click
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder
                                            .setTitle("Delete Menu")
                                            .setMessage("Are you sure you want to delete this Menu ?")
                                            .setIcon(R.drawable.ic_action_btn_delete)
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    deleteResult.getDeleteListenerPosition(position);

                                                }

                                           /* private void removeMenu(int menu_id) {

                                                initRetrofitCallback();
                                                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                                mRetrofitService = new RetrofitService(mResultCallBack,context);
                                                mRetrofitService.retrofitData(MENU_DELETE,(service.getMenuDelete(menu_id,
                                                        Integer.parseInt ( mHotelId),
                                                        Integer.parseInt(mBranchId))
                                                ));*/


                                            });


                                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog alert = builder.create();
                                    alert.show();

                                    return true;

                                case R.id.menu_view:
                                    viewMenuInfo(position);
                                    return  true;
                                default:
                                    return false;

                            }

                        }
                    });
                    //displaying the popup
                    popup.show();
            }

        });
    }

    private void viewMenuInfo(int position) {
        LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialoglayout = li.inflate(R.layout.dialog_menu_display, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialoglayout);
        dialog = builder.create();
        mCircularImageView=dialoglayout.findViewById(R.id.img_menu);
        menuName=dialoglayout.findViewById(R.id.tv_menu_name);
        menuPrice=dialoglayout.findViewById(R.id.tv_menu_price);
        FlowTextView menuDiscription=dialoglayout.findViewById(R.id.tv_menu_discription);
        rvTopping=dialoglayout.findViewById(R.id.rv_menu_toppings);
        imageBtnCancel=dialoglayout.findViewById(R.id.btn_cancel);

        menuName.setText(arrayListMenu.get(position).getMenu_Name());
        String price = String.valueOf(arrayListMenu.get(position).getNon_Ac_Rate());
        menuPrice.setText("\u20B9 "+price);

        menuDiscription.setText(arrayListMenu.get(position).getMenu_Descrip());
        Picasso.with(context).load(apiService.BASE_URL+arrayListMenu.get(position).getMenu_Image_Name())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_STORE)
                .into(mCircularImageView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvTopping.setHasFixedSize(true);
        rvTopping.setLayoutManager(linearLayoutManager);

        AdapterDisplayAllMenusView adapterDisplayAllMenusView = new AdapterDisplayAllMenusView(context, arrayListMenu.get(position).getArrayListtoppings());
        rvTopping.setAdapter(adapterDisplayAllMenusView);
        dialog.show();
        notifyDataSetChanged();
        imageBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayListMenu.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        TextView mMenuName, mMenuDisp, mMenuTeste, mMenuOption;
        private CircleImageView image;
        private ImageView img_spicy;
        private LinearLayout llMenu;


        public MyHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.circle_image);
            mMenuName = itemView.findViewById(R.id.tx_menu_name);
            mMenuDisp = itemView.findViewById(R.id.tx_menu_disp);
            mMenuOption = itemView.findViewById(R.id.textViewOptions);
            llMenu = itemView.findViewById(R.id.llMenu);
            mMenuTeste = itemView.findViewById(R.id.menu_test);
            img_spicy = itemView.findViewById(R.id.img_spicy);


        }
    }
}
