package com.restrosmart.restrohotel.Adapter;
import android.content.Context;

import android.content.DialogInterface;
import android.support.annotation.NonNull;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Model.ToppingsForm;
import com.restrosmart.restrohotel.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdapterDisplayAllToppings extends RecyclerView.Adapter<AdapterDisplayAllToppings.MyHolder> {

    private Context mContext;
    private List<ToppingsForm> arrayList;
    private  EditListener editListener;
    private  DeleteListener deleteListener;
    private ApiService ApiService;

    public AdapterDisplayAllToppings(Context context, ArrayList<ToppingsForm> toppingsForms, EditListener editListener, DeleteListener deleteListener) {
        this.mContext = context;
        this.arrayList = toppingsForms;
        this.editListener=editListener;
        this.deleteListener=deleteListener;

    }

    @NonNull
    @Override
    public AdapterDisplayAllToppings.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_toppings_item, viewGroup, false);
        return new AdapterDisplayAllToppings.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterDisplayAllToppings.MyHolder myHolder, final int i) {
        myHolder.tvToppingsName.setText(arrayList.get(i).getToppingsName());
        String price= String.valueOf(arrayList.get(i).getToppingsPrice());
        myHolder.tvToppingsPrice.setText( "\u20B9 "+price);

        //  Random color = new Random();
      //  myHolder.imageViewToppings.setBackgroundColor(Color.argb(255, color.nextInt(255), color.nextInt(255), color.nextInt(255)));
     ;
        /*Randomly get color from list of colors*/
        int[] androidColors = mContext.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        myHolder.imageViewToppings.setBackgroundColor(randomAndroidColor);

        Picasso.with(mContext).load(ApiService.BASE_URL+arrayList.get(i).getImage()).resize(500, 500).into(myHolder.imageView);



        myHolder.ivEdit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               editListener.getEditListenerPosition(i);
           }
       });

       myHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
               builder
                       .setTitle("Delete Topping")
                       .setMessage("Are you sure you want to delete this Topping ?")
                       .setIcon(R.drawable.ic_action_btn_delete)
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int which) {
                               deleteListener.getDeleteListenerPosition(i);

                           }
                       });


               builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                   }
               });

               AlertDialog alert = builder.create();
               alert.show();
           }
       });






       /* myHolder.tvToppingsOptionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(mContext, myHolder.tvToppingsOptionMenu);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_edit:
                                editListener.getEditListenerPosition(i);
                                //handle menu1 click
                                return true;

                            case R.id.menu_delete:
                                //handle menu2 click
                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder
                                        .setTitle("Delete Topping")
                                        .setMessage("Are you sure you want to delete this Topping ?")
                                        .setIcon(R.drawable.ic_action_btn_delete)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                deleteListener.getDeleteListenerPosition(i);

                                            }
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
        });*/

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvToppingsName;
        private ImageView imageViewToppings,ivEdit,ivDelete,imageView;
        private TextView tvToppingsPrice, tvToppingsOptionMenu;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvToppingsName = itemView.findViewById(R.id.tv_toppings_name);
            tvToppingsPrice = itemView.findViewById(R.id.tv_toppings_price);
            imageViewToppings=itemView.findViewById(R.id.img_toppings);
            imageView=itemView.findViewById(R.id.imageview);
            ivEdit=itemView.findViewById(R.id.iv_edit);
            ivDelete=itemView.findViewById(R.id.iv_delete);
           // tvToppingsOptionMenu = itemView.findViewById(R.id.tv_toppings_option_menu);
        }
    }
}
