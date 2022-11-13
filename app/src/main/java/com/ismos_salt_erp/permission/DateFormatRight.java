package com.ismos_salt_erp.permission;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatRight {
    Context context;
    String date;

    public DateFormatRight(FragmentActivity context, String date) {
        this.context = context;
        this.date = date;
    }

    public String yearMothDayToDayYearMonth(){// for this format (yyy-MM-dd hh:mm a)
        String datetime = null;
        java.text.DateFormat inputFormat = new SimpleDateFormat("yyy-MM-dd hh:mm a");
        SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy & hh:mm a");
        try {
            Date convertedDate = inputFormat.parse(date);
               datetime = d.format(convertedDate);

        }catch (Exception e){

        }
        return datetime;
    }

    public String dateFormatForPm(){ //for this format (yyy-MM-dd hh:mma)
       String datetime = null;
       java.text.DateFormat inputFormat = new SimpleDateFormat("yyy-MM-dd hh:mma");
       SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy & hh:mm a");
       try {
           Date convertedDate = inputFormat.parse(date);
           datetime = d.format(convertedDate);

       }catch (Exception e){

       }
        return datetime;
    }

    public String dateFormatForWashing(){ //for this format (yyy-MM-dd HH:mm:ss)
       String datetime = null;
       java.text.DateFormat inputFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
       SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy & hh:mm:ss aa");
       try {
           Date convertedDate = inputFormat.parse(date);
           datetime = d.format(convertedDate);

       }catch (Exception e){

       }
        return datetime;
    }

    public String onlyDayMonthYear(){ //for this format (yyy-MM-dd hh:mma)
        String datetime = null;
        java.text.DateFormat inputFormat = new SimpleDateFormat("yyy-MM-dd");
        SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date convertedDate = inputFormat.parse(date);
            datetime = d.format(convertedDate);

        }catch (Exception e){

        }
        return datetime;
    }

    public String forReconciliationDayMonthYear(){ //for this format (yyy-MM-dd hh:mma)
        String datetime = null;
        java.text.DateFormat inputFormat = new SimpleDateFormat("yyyy-MMMM-dd");
        SimpleDateFormat d = new SimpleDateFormat("dd-MMMM-yyyy");

        try {
            Date convertedDate = inputFormat.parse(date);
            datetime = d.format(convertedDate);

        }catch (Exception e){

        }
        return datetime;
    }

    public String onlyTime(String time){ //for this format (yyy-MM-dd hh:mma)
        String time1 = null;
        java.text.DateFormat inputFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat d = new SimpleDateFormat("hh:mm a");
        try {
            Date convertedDate = inputFormat.parse(time);
            time1 = d.format(convertedDate);

        }catch (Exception e){

        }
        return time1;
    }


}
