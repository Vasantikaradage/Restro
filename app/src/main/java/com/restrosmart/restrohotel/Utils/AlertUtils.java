package com.restrosmart.restrohotel.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class AlertUtils {

 //  private Sessionmanager sessionmanager;

/*Logout Alert*/
    public static void AlertDialoge(final Context context, int icon, String title, String message) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setIcon(icon);
        alertDialog.setMessage(message);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

             Sessionmanager   sessionmanager = new Sessionmanager(context);
                sessionmanager.CheckLogout();

            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
