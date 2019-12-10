package com.restrosmart.restrohotel.Adapter;



import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.restrosmart.restrohotel.Admin.ActivityAddNewMenu;
import com.restrosmart.restrohotel.Admin.ActivityFlavour;

import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.StatusListener;
import com.restrosmart.restrohotel.Model.MenuDisplayForm;
import com.restrosmart.restrohotel.Model.ToppingsForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.restrosmart.restrohotel.Utils.flowtextview.FlowTextView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;

import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class AdapterDisplayAllMenus extends RecyclerView.Adapter<AdapterDisplayAllMenus.MyHolder> {

    private  Context context;
    private ArrayList<MenuDisplayForm> arrayListMenu;

    private DeleteListener deleteResult;
    private String mHotelId, mBranchId;
    private int pcId;
    private EditListener editListener;
    private  View dialoglayout;
    private  AlertDialog alertDialog;
    private Dialog dialog;
    private  ImageView mImageView;
    private  TextView menuName,menuPrice,topping_title;

    private  RecyclerView rvTopping;
    private ImageButton imageBtnCancel;

    private  ArrayList<ToppingsForm> arrayListToppings;
    private StatusListener statusListener;


    public AdapterDisplayAllMenus(Context context, int pcId, StatusListener statusListener, DeleteListener deleteResult, ArrayList<MenuDisplayForm> arrayListMenu, EditListener editListener) {
        this.context = context;
        this.pcId = pcId;
        this.deleteResult = deleteResult;
        this.arrayListMenu = arrayListMenu;
        this.editListener = editListener;
        this.statusListener=statusListener;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_menu_list, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        Picasso.with(context).load(arrayListMenu.get(position).getMenu_Image_Name())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_STORE)
                .into(holder.image);


        holder.mMenuDisp.setText(arrayListMenu.get(position).getMenu_Descrip());
        holder.mMenuPrice.setText("\u20B9 " + arrayListMenu.get(position).getNon_Ac_Rate());

        if (arrayListMenu.get(position).getStatus() == 0){
            holder.mMenustatus.setText("Active");
            holder.mMenustatus.setTextColor(context.getResources().getColor(R.color.colorGreen));
       }else {
            holder.mMenustatus.setText("InActive");
            holder.mMenustatus.setTextColor(context.getResources().getColor(R.color.colorRed));
        }

        arrayListToppings=arrayListMenu.get(position).getArrayListtoppings();
        int mTeste = arrayListMenu.get(position).getMenu_Test();
        if (mTeste == 1) {
           // holder.mMenuTeste.setVisibility(View.VISIBLE);
            holder.mMenuName.setText(arrayListMenu.get(position).getMenu_Name()+" (Sweet)");
            holder.mMenuTeste.setText("(Sweet)");
        } else if (mTeste == 2) {
            holder.mMenuName.setText(arrayListMenu.get(position).getMenu_Name());

//            SpannableStringBuilder ssb = new SpannableStringBuilder(arrayListMenu.get(position).getMenu_Name());
//            ssb.setSpan(new ImageSpan(context, R.drawable.ic_spicy2), 1, 0, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//            holder.mMenuName.setText(ssb, TextView.BufferType.SPANNABLE);
           // holder.mMenuName.setText(arrayListMenu.get(position).getMenu_Name()+" "+R.drawable.ic_spicy2);
           // holder.img_spicy.setVisibility(View.VISIBLE);
        } else  {
            holder.mMenuName.setText(arrayListMenu.get(position).getMenu_Name());
            holder.mMenuTeste.setVisibility(View.GONE);
            holder.img_spicy.setVisibility(View.GONE);

        }

        Sessionmanager sharedPreferanceManage = new Sessionmanager(context);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        mHotelId = name_info.get(HOTEL_ID);
        mBranchId = name_info.get(BRANCH_ID);

        if (pcId == 3) {

                holder.mMenuDisp.setVisibility(View.GONE);
                holder.mMenuPrice.setVisibility(View.GONE);
                holder.llMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (arrayListMenu.get(position).getStatus() == 0) {
                        Intent intent = new Intent(context, ActivityFlavour.class);
                        intent.putExtra("menuName", arrayListMenu.get(position).getMenu_Name());
                        intent.putExtra("menuId", arrayListMenu.get(position).getMenu_Id());
                        intent.putExtra("pcId", pcId);
                        intent.putExtra("categoryId", arrayListMenu.get(position).getCategory_Id());
                        context.startActivity(intent);
                        }else
                        {
                            Toast.makeText(context, "First you want change the menu status... then get the Flavour information", Toast.LENGTH_SHORT).show();
                        }
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

                                case R.id.menu_active:

                                    dialog = new Dialog(context);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setCancelable(false);
                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.setContentView(R.layout.dialog_active_table);

                                    // set the custom alertDialog components - text, image and button
                                    ImageView ivCloseDialog = dialog.findViewById(R.id.ivCloseDialog);
                                    ImageView ivActive= dialog.findViewById(R.id.ivActiveIcon);
                                    ImageView ivInActive= dialog.findViewById(R.id.ivInActvieIcon);
                                    RelativeLayout tvActive = dialog.findViewById(R.id.tvActive);
                                    RelativeLayout tvInActive = dialog.findViewById(R.id.tvInActive);

                                    tvActive.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            statusListener.statusListern(position,0);
                                            dialog.dismiss();
                                        }
                                    });

                                    tvInActive.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            statusListener.statusListern(position,1);
                                            dialog.dismiss();

                                        }
                                    });



                                    ivCloseDialog.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog.dismiss();
                                        }
                                    });

                                    if(arrayListMenu.get(position).getStatus()==0)
                                    {
                                        ivActive.setVisibility(View.VISIBLE);
                                        ivInActive.setVisibility(View.GONE);
                                    }
                                    else
                                    {
                                        ivActive.setVisibility(View.GONE);
                                        ivInActive.setVisibility(View.VISIBLE);
                                    }
                                    dialog.show();

                                    return true;


                                case R.id.menu_edit:
                                    //handle menu1 click

                                    if(pcId==3){
                                        editListener.getEditListenerPosition(position);

                                    }else {
                                        Intent intent = new Intent(context, ActivityAddNewMenu.class);
                                        intent.putExtra("MenuId", arrayListMenu.get(position).getMenu_Id());
                                        intent.putExtra("ImageName", arrayListMenu.get(position).getMenu_Image_Name());
                                        intent.putExtra("MenuName", arrayListMenu.get(position).getMenu_Name());
                                        intent.putExtra("Price", arrayListMenu.get(position).getNon_Ac_Rate());
                                        intent.putExtra("MenuDiscription", arrayListMenu.get(position).getMenu_Descrip());
                                        intent.putExtra("MenuTaste", arrayListMenu.get(position).getMenu_Test());
                                        intent.putExtra("categoryId", arrayListMenu.get(position).getCategory_Id());
                                        intent.putExtra("pc_Id", pcId);
                                        intent.putParcelableArrayListExtra("ArrayListToppings", arrayListMenu.get(position).getArrayListtoppings());
                                        context.startActivity(intent);
                                    }
                                    return true;

                                case R.id.menu_delete:
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder
                                            .setTitle("Delete Menu")
                                            .setMessage("Are you sure you want to delete this Menu ?")
                                            .setIcon(R.drawable.ic_action_btn_delete)
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    deleteResult.getDeleteListenerPosition(position);
                                                }
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
        alertDialog = builder.create();
        mImageView =dialoglayout.findViewById(R.id.img_menu);
        menuName=dialoglayout.findViewById(R.id.tv_menu_name);
        topping_title=dialoglayout.findViewById(R.id.topping_title);
        menuPrice=dialoglayout.findViewById(R.id.tv_menu_price);
        FlowTextView menuDiscription=dialoglayout.findViewById(R.id.tv_menu_discription);
        TextView tvDescription=dialoglayout.findViewById(R.id.tv_description);
        rvTopping=dialoglayout.findViewById(R.id.rv_menu_toppings);
        imageBtnCancel=dialoglayout.findViewById(R.id.btn_cancel);
        int menuTaste=arrayListMenu.get(position).getMenu_Test();


        if(menuTaste==1) {
            menuName.setText(arrayListMenu.get(position).getMenu_Name() +"(Sweet)");
        }
        else if(menuTaste==2){
            menuName.setText(arrayListMenu.get(position).getMenu_Name() +"(Spicy)");
//            SpannableStringBuilder ssb = new SpannableStringBuilder(arrayListMenu.get(position).getMenu_Name());
//            ssb.setSpan(new ImageSpan(context, R.drawable.ic_spicy2), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//            menuName.setText(ssb, TextView.BufferType.SPANNABLE);
        }
        else {
            menuName.setText(arrayListMenu.get(position).getMenu_Name());
        }
        String price = String.valueOf(arrayListMenu.get(position).getNon_Ac_Rate());
        menuPrice.setText("\u20B9 "+price);

        menuDiscription.setText(arrayListMenu.get(position).getMenu_Descrip());
        Picasso.with(context).load(arrayListMenu.get(position).getMenu_Image_Name())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_STORE)
                .into(mImageView);

        if (pcId == 3) {
            menuPrice.setVisibility(View.GONE);
            tvDescription.setVisibility(View.GONE);
        }
        else
        {
            menuPrice.setVisibility(View.VISIBLE);
            tvDescription.setVisibility(View.VISIBLE);
        }

        if(arrayListMenu.get(position).getArrayListtoppings().size()==0)
        {
            topping_title.setVisibility(View.GONE);
        }
        else {
            topping_title.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            rvTopping.setHasFixedSize(true);
            rvTopping.setLayoutManager(linearLayoutManager);

            AdapterDisplayAllMenusView adapterDisplayAllMenusView = new AdapterDisplayAllMenusView(context, arrayListMenu.get(position).getArrayListtoppings());
            rvTopping.setAdapter(adapterDisplayAllMenusView);
        }
            alertDialog.show();
            notifyDataSetChanged();
            imageBtnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
    }

    @Override
    public int getItemCount() {
        return arrayListMenu.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView mMenuName, mMenuDisp, mMenuTeste, mMenuOption,mMenuPrice,mMenustatus;
        private ImageView image;
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
            mMenuPrice=itemView.findViewById(R.id.tx_menu_price);
            img_spicy = itemView.findViewById(R.id.img_spicy);
            mMenustatus=itemView.findViewById(R.id.tv_menu_status);
        }
    }
}
