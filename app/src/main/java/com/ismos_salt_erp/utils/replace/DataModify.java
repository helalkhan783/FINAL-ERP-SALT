package com.ismos_salt_erp.utils.replace;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataModify {
    private DataModify() {}

    public static String kgToTon(String mainValue) {
        if (mainValue == null || mainValue.isEmpty() || mainValue.equals("null")) {
            mainValue = "0";
        }
        double value = Double.parseDouble(mainValue);
        double mt = value / 1000;// kg to metric ton
        return String.format("%.3f", mt);
    }

    public static String addFourDigit(Object valu) {
        String mainValue = String.valueOf(valu);
        if (mainValue == null || mainValue.isEmpty() || mainValue.equals("null")) {
            mainValue = "0";
        }

        double value = Double.parseDouble(ReplaceCommaFromString.replaceComma(mainValue));//here replace comma if be


        return String.format("%.4f", value);
    }


    public static String addThreeDigit(String mainValue) {
        if (mainValue == null || mainValue.isEmpty() || mainValue.equals("null")) {
            mainValue = "0";
        }
        double value = Double.parseDouble(mainValue);
        return String.format("%.3f", value);
    }

    public static String currentDate() {
        DateTimeFormatter dtf = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }
        LocalDateTime now = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
        }
        String currentDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDate = dtf.format(now);
        }
        return currentDate;
    }


}
