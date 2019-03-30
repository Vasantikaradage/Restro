package com.restrosmart.restro.Admin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public  class MyReceiver extends BroadcastReceiver {
    private String Refresh_CategoryList;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("", "Refresh_CategoryList" + "Refresh_CategoryList");
        Refresh_CategoryList = intent.getAction();
        Intent intent1=new Intent(context,ActivityAdminDrawer.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("Refresh_CategoryList", Refresh_CategoryList);
        context.startActivity(intent1);
    }}
