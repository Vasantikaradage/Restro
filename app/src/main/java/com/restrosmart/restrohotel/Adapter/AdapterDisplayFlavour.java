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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.restrosmart.restrohotel.Admin.ActivityAddNewMenu;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Model.FlavourForm;
import com.restrosmart.restrohotel.Model.FlavourUnitForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.Utils.flowtextview.FlowTextView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SHREE on 28/12/2018.
 */

public class AdapterDisplayFlavour  extends RecyclerView.Adapter<AdapterDisplayFlavour.MyHolder> {
private Context context;
private ArrayList<FlavourForm>  flavourFormArrayList;
private ArrayList<FlavourUnitForm> flavourUnitFormArrayList;
private  EditListener editListener;
private  DeleteListener deleteListener;
private CircleImageView mCircularImageView;
private  RecyclerView rvFlavourDisplay;
private  TextView flavourName;
private ImageButton imageBtnCancel;
private ApiService apiService;

    private  View dialoglayout;
    private  AlertDialog dialog;


    public AdapterDisplayFlavour(Context activityFlavour, ArrayList<FlavourForm> arrayListFlavour, ArrayList<FlavourUnitForm> flavourUnitFormArrayList, EditListener editListener, DeleteListener deleteListener) {
    this.context=activityFlavour;
    this.flavourFormArrayList=arrayListFlavour;
    this.flavourUnitFormArrayList=flavourUnitFormArrayList;
    this.deleteListener=deleteListener;
    this.editListener=editListener;

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

       Picasso.with(context).load(apiService.BASE_URL+flavourFormArrayList.get(position).getFlavourImage())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_STORE)
                .into(holder.imageFlavour);

       holder.llflavour.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
               dialoglayout = li.inflate(R.layout.dialog_flavour_display, null);
               final AlertDialog.Builder builder = new AlertDialog.Builder(context);
               builder.setView(dialoglayout);
               dialog = builder.create();
               mCircularImageView=dialoglayout.findViewById(R.id.img_menu);
               flavourName=dialoglayout.findViewById(R.id.flavour_name);
               TextView unit=dialoglayout.findViewById(R.id.unit);

               rvFlavourDisplay=dialoglayout.findViewById(R.id.rv_flavour);
               imageBtnCancel=dialoglayout.findViewById(R.id.btn_cancel);

              flavourName.setText(flavourFormArrayList.get(position).getFlavourName());

               Picasso.with(context).load(apiService.BASE_URL+flavourFormArrayList.get(position).getFlavourImage())
                       .memoryPolicy(MemoryPolicy.NO_CACHE)
                       .memoryPolicy(MemoryPolicy.NO_STORE)
                       .into(mCircularImageView);

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
               dialog.show();
               imageBtnCancel.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       dialog.dismiss();
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
                           case R.id.menu_edit:
                               editListener.getEditListenerPosition(position);
                               //handle menu1 click
                               /*Intent intent = new Intent(context, ActivityAddNewMenu.class);
                               intent.putExtra("MenuId", arrayListMenu.get(position).getMenu_Id());
                               intent.putExtra("ImageName", arrayListMenu.get(position).getMenu_Image_Name());
                               intent.putExtra("MenuName", arrayListMenu.get(position).getMenu_Name());
                               intent.putExtra("Price", arrayListMenu.get(position).getNon_Ac_Rate());
                               intent.putExtra("MenuDiscription", arrayListMenu.get(position).getMenu_Descrip());
                               intent.putExtra("MenuTaste", arrayListMenu.get(position).getMenu_Test());
                               intent.putParcelableArrayListExtra("ArrayListToppings", arrayListMenu.get(position).getArrayListtoppings());


                               context.startActivity(intent);*/
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
                dialog = builder.create();

                RecyclerView recyclerView=(RecyclerView)dialoglayout.findViewById(R.id.recycler_flavour_unit);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                AdapterDisplayFlavourUnit adapterDisplayFlavourUnit = new AdapterDisplayFlavourUnit(context,flavourUnitFormArrayList );
                recyclerView.setAdapter(adapterDisplayFlavourUnit);

                dialog.show();



            }
        });
*/

    }

    @Override
    public int getItemCount() {
        return flavourFormArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView flavourName,flavourDiscription,menuOption;
        private ImageView imageFlavour;
        private LinearLayout llflavour;
      //  private FrameLayout frameLayout;

        public MyHolder(View itemView) {
            super(itemView);
            flavourName=itemView.findViewById(R.id.tx_flavour_name);
            imageFlavour=itemView.findViewById(R.id.circle_image);
            //flavourDiscription=itemView.findViewById(R.id.tx_flavour_disp);
            menuOption=itemView.findViewById(R.id.textViewOptions);
            llflavour=itemView.findViewById(R.id.llflavour);

        }
    }
}
