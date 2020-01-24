package com.restrosmart.restrohotel.Admin;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.restrosmart.restrohotel.R;

public class ActivityInvoice  extends AppCompatActivity {
    private TextView btnPayBill;
    private Dialog dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        init();
        btnPayBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(ActivityInvoice.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.dialog_payment_mode);

                // set the custom alertDialog components - text, image and button
                ImageView ivCloseDialog = dialog.findViewById(R.id.ivCloseDialog);
                final ImageView ivCash= dialog.findViewById(R.id.iv_cash_icon);
                final ImageView ivCard= dialog.findViewById(R.id.iv_card_icon);
                RelativeLayout tvActive = dialog.findViewById(R.id.tvActive);
                RelativeLayout tvInActive = dialog.findViewById(R.id.tvInActive);
                Button btnOk=dialog.findViewById(R.id.btn_ok);

                tvActive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //statusListener.statusListern(position,0);
                        ivCash.setVisibility(View.VISIBLE);
                        ivCard.setVisibility(View.GONE);

                    }
                });

                tvInActive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // statusListener.statusListern(position,1);
                        ivCash.setVisibility(View.GONE);
                        ivCard.setVisibility(View.VISIBLE);


                    }
                });

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });



                ivCloseDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

              /*  if(arrayListMenu.get(position).getStatus()==0)
                {
                    ivActive.setVisibility(View.VISIBLE);
                    ivInActive.setVisibility(View.GONE);
                }
                else
                {
                    ivActive.setVisibility(View.GONE);
                    ivInActive.setVisibility(View.VISIBLE);
                }*/
                dialog.show();

            }
        });
    }

    private void init() {
        btnPayBill=findViewById(R.id.tvPayBill);
    }
}
