package com.devina.weatherapplication.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CommonMethods {

    public static String getIconFileName(String icon)
    {
        String imageName=icon.substring(icon.lastIndexOf('/')+1);

        icon=icon.substring(0, icon.lastIndexOf('/'));

        String folderName=icon.substring(icon.lastIndexOf('/')+1);

        return folderName+"/"+imageName;

    }

    public static Bitmap getIconDrawable(String filename, Context context)
    {
        try {

            InputStream is=context.getAssets().open(filename);

            Bitmap imgBitmap= BitmapFactory.decodeStream(is);

            imgBitmap=Bitmap.createScaledBitmap(imgBitmap, 250, 250, false);

            is.close();

            return imgBitmap;

        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

        return null;
    }

    public static String formatDateString(String date)
    {
        String year=date.substring(0, date.indexOf('-'));
        date=date.substring(date.indexOf('-')+1);

        String month=date.substring(0, date.indexOf('-'));
        String day=date.substring(date.indexOf('-')+1);

        try {
            int m=Integer.parseInt(month.trim());

            month=getMonth(m);
        }
        catch (NumberFormatException nfe)
        {
            nfe.printStackTrace();
        }

        return day+" "+month+" "+year;
    }

    private static String getMonth(int mm)
    {
        switch (mm)
        {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
        }

        return "Jan";
    }

    public static String formatTimeString(String time)
    {
        String hour=time.substring(0, time.indexOf(':'));
        time=time.substring(time.indexOf(':'));

        try {
            int hr=Integer.parseInt(hour.trim());

            if(hr>=0 && hr<12)
            {
                if(hr == 0)
                    hr=12;

                time=hr+time+" AM";
            }
            else
            {
                if(hr != 12)
                {
                    hr=hr-12;
                }

                time=hr+time+" PM";
            }
        }
        catch (NumberFormatException nfe)
        {
            nfe.printStackTrace();
        }

        return time;
    }

    public static List<String> checkPermissions(Context context)
    {
        List<String> permissionsToReq=new ArrayList<>();

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            permissionsToReq.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            permissionsToReq.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        return permissionsToReq;
    }
}
