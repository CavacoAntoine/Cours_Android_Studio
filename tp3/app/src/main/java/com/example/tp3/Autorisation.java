package com.example.tp3;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Autorisation {

    private Autorisation() {
    }

    public static boolean callPermission(AppCompatActivity activity) {
        if (android.os.Build.VERSION.SDK_INT >=  android.os.Build.VERSION_CODES.M) {
            // Check if we have send SMS permission
            int callPermission = ActivityCompat.checkSelfPermission(activity,
                    Manifest.permission.CALL_PHONE);

            if (callPermission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                activity.requestPermissions(
                        new String[]{Manifest.permission.CALL_PHONE}, 1);
                return  false;
            }else {
                return true;
            }
        }else {
            return true;
        }
    }

    public static boolean smsPermission(AppCompatActivity activity) {
        if (android.os.Build.VERSION.SDK_INT >=  android.os.Build.VERSION_CODES.M) { // 23

            // Check if we have send SMS permission
            int sendSmsPermisson = ActivityCompat.checkSelfPermission(activity,
                    Manifest.permission.SEND_SMS);

            if (sendSmsPermisson != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                activity.requestPermissions(
                        new String[]{Manifest.permission.SEND_SMS}, 1);
                return  false;
            }else {
                return true;
            }
        }else {
            return true;
        }
    }


    public static boolean loginPermission(AppCompatActivity activity) {
        if (android.os.Build.VERSION.SDK_INT >=  android.os.Build.VERSION_CODES.M) { // 23

            // Check if we have send SMS permission
            int loginPermisson = ActivityCompat.checkSelfPermission(activity,
                    "login.permission.CALL_LOGIN");

            if (loginPermisson != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                activity.requestPermissions(
                        new String[]{"login.permission.CALL_LOGIN"}, 1);
                return  false;
            }else {
                return true;
            }
        }else {
            return true;
        }

    }

}
