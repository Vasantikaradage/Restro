package com.restrosmart.restro.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.restrosmart.restro.R;
import com.restrosmart.restro.Utils.Sessionmanager;

public class ActivityBillPayment extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView rvBillMenu;
    private TextView tvSubtotal, tvServiceTax, tvSGST, tvCGST, tvTotalBillAmount, tvPayBill;

    private Sessionmanager sessionmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_payment);

        init();
        setupToolbar();

        tvPayBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ActivityBillPayment.this, ActivityPaymentMethod.class);
                startActivity(intent);
                //showPaymentModeDialog();
            }
        });
    }

    /*private void showPaymentModeDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = null;
        if (layoutInflater != null) {
            dialogView = layoutInflater.inflate(R.layout.payment_mode_dialog, null);
        }
        dialogBuilder.setView(dialogView);

        paymentDialog = dialogBuilder.create();
        paymentDialog.setCanceledOnTouchOutside(false);
        paymentDialog.setCancelable(false);
        paymentDialog.show();

        Button btnConfirmMode = dialogView.findViewById(R.id.btnConfirmMode);
        Button btnCancelMode = dialogView.findViewById(R.id.btnCancelMode);

        btnConfirmMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentDialog.dismiss();
                sessionmanager.deleteFoodCartList();
                openScratchCardDialog();
            }
        });

        btnCancelMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentDialog.dismiss();
            }
        });
    }*/



    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setTitle("Bill");
    }

    private void init() {

        sessionmanager = new Sessionmanager(this);

        mToolbar = findViewById(R.id.toolbar1);
        rvBillMenu = findViewById(R.id.rvBillMenu);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvServiceTax = findViewById(R.id.tvServiceTax);
        tvSGST = findViewById(R.id.tvSGST);
        tvCGST = findViewById(R.id.tvCGST);
        tvTotalBillAmount = findViewById(R.id.tvTotalBillAmount);
        tvPayBill = findViewById(R.id.tvPayBill);
    }
}
