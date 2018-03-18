package com.siimk.garden;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class GlobalFunctions {


    public static boolean isAABB(double x1, double X1, double y1, double Y1,
                                 double x2, double X2, double y2, double Y2){

        if(X1 < x2) return false;
        if(x1 > X2) return false;
        if(Y1 < y2) return false;
        if(y1 > Y2) return false;

        return true;
    }

    public static double[] AABBcoordinates(double x1, double X1, double y1, double Y1,
                                           double x2, double X2, double y2, double Y2){
        double[] coordinates = {x1, X1, y1, Y1};
        if(X1 >= x2) X1 = x2;
        if(x1 <= X2) x1 = X2;
        if(Y1 >= y2) Y1 = y2;
        if(y1 <= Y2) y1 = Y2;

        return coordinates;
    }

    public static void alertbox(Context context, String title, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    public static boolean isNumeric(String s){
        try{
            int a = Integer.parseInt(s);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
