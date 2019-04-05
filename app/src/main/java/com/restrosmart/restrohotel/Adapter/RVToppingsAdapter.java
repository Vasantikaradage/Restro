package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Model.ToppingsForm;
import com.restrosmart.restrohotel.R;
import java.util.ArrayList;


public class RVToppingsAdapter extends RecyclerView.Adapter<RVToppingsAdapter.MyHolder> {
    private Context mContext;
    private ArrayList<ToppingsForm> arrayListToppings;
    private DeleteListener deleteListener;
    private EditListener editListener;

    public RVToppingsAdapter(Context context, ArrayList<ToppingsForm> rvVegToppings, DeleteListener deleteListener, EditListener editListener) {
        this.mContext = context;
        this.arrayListToppings = rvVegToppings;
        this.deleteListener = deleteListener;
        this.editListener = editListener;
    }


    @NonNull
    @Override
    public RVToppingsAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_toppings_item, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RVToppingsAdapter.MyHolder myHolder, final int i) {
        // myHolder.tvToppingsName.setText(arrayListToppings.get(i).getToppingsName());
        myHolder.tvToppingsName.setText(arrayListToppings.get(i).getToppingsName());
        String price = String.valueOf(arrayListToppings.get(i).getToppingsPrice());
        myHolder.tvToppingsPrice.setText(price);
        myHolder.tvToppingsOptionMenu.setOnClickListener(new View.OnClickListener() {
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
                                //handle menu1 click
                                return true;

                            case R.id.menu_delete:
                                //handle menu2 click
                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder
                                        .setTitle("Delete Toppings")
                                        .setMessage("Are you sure you want to delete this Toppins ?")
                                        .setIcon(R.drawable.ic_action_btn_delete)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                deleteListener.getDeleteListenerPosition(i);

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

    @Override
    public int getItemCount() {
        return arrayListToppings.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvToppingsName;
        private TextView tvToppingsPrice, tvToppingsOptionMenu;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvToppingsName = itemView.findViewById(R.id.tv_toppings_name);
            tvToppingsPrice = itemView.findViewById(R.id.tv_toppings_price);
            tvToppingsOptionMenu = itemView.findViewById(R.id.tv_toppings_option_menu);

        }
    }
}
