package com.restrosmart.restrohotel.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
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

import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.StatusListener;
import com.restrosmart.restrohotel.Model.FlavourForm;
import com.restrosmart.restrohotel.Model.FlavourUnitForm;
import com.restrosmart.restrohotel.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SHREE on 28/12/2018.
 */

public class AdapterDisplayFlavour  extends RecyclerView.Adapter<AdapterDisplayFlavour.MyHolder> {
private Context context;
private ArrayList<FlavourForm>  flavourFormArrayList;
private ArrayList<FlavourUnitForm> flavourUnitFormArrayList;
private  EditListener editListener;
private  DeleteListener deleteListener;
private ImageView mImageView;
private  RecyclerView rvFlavourDisplay;
private  TextView flavourName;
private ImageButton imageBtnCancel;
private StatusListener statusListener;


    private  View dialoglayout;
    private  AlertDialog alertDialog;
    private  Dialog dialog;


    public AdapterDisplayFlavour(Context activityFlavour, ArrayList<FlavourForm> arrayListFlavour, StatusListener statusListener, ArrayList<FlavourUnitForm> flavourUnitFormArrayList, EditListener editListener, DeleteListener deleteListener) {
    this.context=activityFlavour;
    this.flavourFormArrayList=arrayListFlavour;
    this.flavourUnitFormArrayList=flavourUnitFormArrayList;
    this.deleteListener=deleteListener;
    this.editListener=editListener;
    this.statusListener=statusListener;

    }

    @NonNull
    @Override
    public AdapterDisplayFlavour.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_flavour_list, parent, false);

        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterDisplayFlavour.MyHolder holder, final int position) {

        holder.flavourName.setText(flavourFormArrayList.get(position).getFlavourName());

       Picasso.with(context).load(flavourFormArrayList.get(position).getFlavourImage())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_STORE)
                .into(holder.imageFlavour);

        if (flavourFormArrayList.get(position).getFlavourStatus() == 0){
            holder.mFlavourStatus.setText("Active");
            holder.mFlavourStatus.setTextColor(context.getResources().getColor(R.color.colorGreen));
        }else {
            holder.mFlavourStatus.setText("InActive");
            holder.mFlavourStatus.setTextColor(context.getResources().getColor(R.color.colorRed));
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.scratch_start_gradient));
        }

       holder.llflavour.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
               dialoglayout = li.inflate(R.layout.dialog_flavour_display, null);
               final AlertDialog.Builder builder = new AlertDialog.Builder(context);
               builder.setView(dialoglayout);
               alertDialog = builder.create();
               mImageView =dialoglayout.findViewById(R.id.img_menu);
               flavourName=dialoglayout.findViewById(R.id.flavour_name);
               TextView unit=dialoglayout.findViewById(R.id.unit);

               rvFlavourDisplay=dialoglayout.findViewById(R.id.rv_flavour);
               imageBtnCancel=dialoglayout.findViewById(R.id.btn_cancel);

              flavourName.setText(flavourFormArrayList.get(position).getFlavourName());

               Picasso.with(context).load(flavourFormArrayList.get(position).getFlavourImage())
                       .memoryPolicy(MemoryPolicy.NO_CACHE)
                       .memoryPolicy(MemoryPolicy.NO_STORE)
                       .into(mImageView);

               if( flavourFormArrayList.get(position).getArrayListUnits().size()==0)
               {
                   unit.setVisibility(View.GONE);
               }
               else {
                   unit.setVisibility(View.VISIBLE);

                   LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                   rvFlavourDisplay.setHasFixedSize(true);
                   rvFlavourDisplay.setLayoutManager(linearLayoutManager);

                   AdapterDisplayAllFlavourView adapterDisplayAllFlavourView = new AdapterDisplayAllFlavourView(context, flavourFormArrayList.get(position).getArrayListUnits());
                   rvFlavourDisplay.setAdapter(adapterDisplayAllFlavourView);
                   notifyDataSetChanged();
               }
               alertDialog.show();
               imageBtnCancel.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       alertDialog.dismiss();
                   }
               });


           }
       });

       holder.menuOption.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //creating a popup menu
               PopupMenu popup = new PopupMenu(context, holder.menuOption);
               //inflating menu from xml resource

                   popup.inflate(R.menu.options_menu);

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

                               if(flavourFormArrayList.get(position).getFlavourStatus()==0)
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
                               editListener.getEditListenerPosition(position);
                               return true;

                           case R.id.menu_delete:
                               //handle menu2 click
                               AlertDialog.Builder builder = new AlertDialog.Builder(context);
                               builder
                                       .setTitle("Delete Flavour")
                                       .setMessage("Are you sure you want to delete this flavour ?")
                                       .setIcon(R.drawable.ic_action_btn_delete)
                                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                           public void onClick(DialogInterface dialog, int which) {
                                               deleteListener.getDeleteListenerPosition(position);

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
                             //  viewMenuInfo(position);
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
       /* holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                dialoglayout = li.inflate(R.layout.dialog_unit_show, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(dialoglayout);
                alertDialog = builder.create();

                RecyclerView recyclerView=(RecyclerView)dialoglayout.findViewById(R.id.recycler_flavour_unit);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                AdapterDisplayFlavourUnit adapterDisplayFlavourUnit = new AdapterDisplayFlavourUnit(context,flavourUnitFormArrayList );
                recyclerView.setAdapter(adapterDisplayFlavourUnit);

                alertDialog.show();



            }
        });
*/

    }

    @Override
    public int getItemCount() {
        return flavourFormArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView flavourName,flavourDiscription,menuOption,mFlavourStatus;
        private ImageView imageFlavour;
        private LinearLayout llflavour;
        private CardView cardView;
      //  private FrameLayout frameLayout;

        public MyHolder(View itemView) {
            super(itemView);
            flavourName=itemView.findViewById(R.id.tx_flavour_name);
            imageFlavour=itemView.findViewById(R.id.circle_image);
            //flavourDiscription=itemView.findViewById(R.id.tx_flavour_disp);
            menuOption=itemView.findViewById(R.id.textViewOptions);
            llflavour=itemView.findViewById(R.id.llflavour);
            mFlavourStatus=itemView.findViewById(R.id.tv_flavour_status);
            cardView=itemView.findViewById(R.id.cardview);


        }
    }
}
