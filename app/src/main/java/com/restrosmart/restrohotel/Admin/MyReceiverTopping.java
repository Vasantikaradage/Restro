package com.restrosmart.restrohotel.Admin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiverTopping  extends BroadcastReceiver {
    private String Refresh_ToppngsList;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("", "ToppingList" + "ToppingList");
        Refresh_ToppngsList = intent.getAction();
        Intent intent1=new Intent(context,ActivityAdminDrawer.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("Refresh_ToppingList", Refresh_ToppngsList);
        context.startActivity(intent1);
    }}

