package com.ismos_salt_erp.date_time_picker;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CurrentDatePick {
    public  static String getCurrentDate(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateOnly = new SimpleDateFormat("yyyy-MM-dd");
        return dateOnly.format(cal.getTime());

    }


    public  static String yesterdayDate(String date){
        if (date == null){
            date =getCurrentDate();
        }

        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }

        //convert String to LocalDate
        LocalDate today = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            today = LocalDate.parse(date, formatter);
        }
        String finalYesterDay = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            finalYesterDay = (today.minusDays(1)).format(DateTimeFormatter.ISO_DATE);
        }
        return finalYesterDay;
    }
}
