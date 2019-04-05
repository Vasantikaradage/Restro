package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.restrosmart.restrohotel.Admin.ActivityFlavour;
import com.restrosmart.restrohotel.Interfaces.Category;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Model.MenuDisplayForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
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
    private Category resultPosition;
    private DeleteListener deleteResult;
    private String mHotelId, mBranchId;
    private int pcId;
    private EditListener editListener;
    private  View dialoglayout;
    private  AlertDialog dialog;



    public AdapterDisplayAllMenus(Context context, int pcId, DeleteListener deleteResult, ArrayList<MenuDisplayForm> arrayListMenu, Category category, EditListener editListener) {
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

        Picasso.with(context).load(arrayListMenu.get(position).getMenu_Image_Name())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_STORE)
                .into(holder.image);

        holder.mMenuName.setText(arrayListMenu.get(position).getMenu_Name());
        holder.mMenuDisp.setText(arrayListMenu.get(position).getMenu_Descrip());

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

                    String menuname = arrayListMenu.get(position).getMenu_Name();
                    int menuId = arrayListMenu.get(position).getMenu_Id();
                    int PcId = pcId;
                    int cateId = arrayListMenu.get(position).getCategory_Id();


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
                    LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    dialoglayout = li.inflate(R.layout.dialog_menu_display, null);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setView(dialoglayout);
                    dialog = builder.create();
                    dialog.show();

                }
            });
        }

        holder.mMenuOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.mMenuOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_edit:
                                //handle menu1 click
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


                                builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();

                                return true;
                            default:
                                return false;

                        }

                    }
                });
                //displaying the popup
                popup.show();

            }


        });


        /*holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    // final int pos = position;
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                                    builder
                                                            .setTitle("Delete Menu")
                                                            .setMessage("Are you sure you want to delete this Menu ?")
                                                            .setIcon(R.drawable.ic_action_btn_delete)
                                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    removeMenu(arrayListMenu.get(position).getMenu_Id());

                                                                }

                                                                private void removeMenu(int menu_id) {

                                                                    initRetrofitCallback();
                                                                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                                                    mRetrofitService = new RetrofitService(mResultCallBack,context);
                                                                    mRetrofitService.retrofitData(MENU_DELETE,(service.getMenuDelete(menu_id,
                                                                           Integer.parseInt ( mHotelId),
                                                                            Integer.parseInt(mBranchId))
                                                                           ));

                                                                }

                                                                private void initRetrofitCallback() {
                                                                    mResultCallBack = new IResult() {
                                                                        @Override
                                                                        public void notifySuccess(int requestId, Response<JsonObject> response) {
                                                                            JsonObject jsonObject=response.body();

                                                                            String value=jsonObject.toString();

                                                                            try {
                                                                                JSONObject object=new JSONObject(value);
                                                                                int status=object.getInt("status");
                                                                                if(status==1)
                                                                                {
                                                                                    Toast.makeText(context,"Menu Deleted Successfully",Toast.LENGTH_LONG).show();
                                                                                    notifyItemRemoved(position);
                                                                                    notifyItemRangeChanged(position, arrayListMenu.size());
                                                                                    notifyDataSetChanged();
                                                                                    deleteResult.getDeleteInfoCallBack();


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

                                                            });
                                                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    });
                                                    AlertDialog alert = builder.create();
                                                    alert.show();


                                                }

                                            }
        );*/



      /*  holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultPosition.categoryListern(position);

               *//*  Intent intent=new Intent(context,AddMenu.class);
                 intent.putExtra("menuId",arrayListMenu.get(position).getMenu_Id());
                intent.putExtra("menuName",arrayListMenu.get(position).getMenu_Name());
                intent.putExtra("menuAcRate",arrayListMenu.get(position).getAc_Rate());
                intent.putExtra("menuNonAcRate",arrayListMenu.get(position).getNon_Ac_Rate());
                intent.putExtra("menuDiscription",arrayListMenu.get(position).getMenu_Descrip());
                intent.putExtra("menuImage",arrayListMenu.get(position).getMenu_Image_Name());
                context.startActivity(intent);*//*
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return arrayListMenu.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        TextView mMenuName, mMenuDisp, mMenuTeste, mMenuOption;
        // ImageButton btnEdit,btnDelete;
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
            // btnEdit=(ImageButton)itemView.findViewById(R.id.edit_button);
            //  btnDelete=(ImageButton)itemView.findViewById(R.id.delete_button);
            mMenuTeste = itemView.findViewById(R.id.menu_test);
            img_spicy = itemView.findViewById(R.id.img_spicy);
            // frameLayout=itemView.findViewById(R.id.frame_layout);


        }
    }
}
