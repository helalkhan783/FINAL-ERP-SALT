package com.ismos_salt_erp.date_time_picker;

import androidx.fragment.app.FragmentActivity;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
public class DateTimePicker {
    public DateTimePicker() {
    }
// This is generic method .It can handle any type class or object .And this method open date picker
    public static <T> void openDatePicker(T typee, FragmentActivity activity) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                (DatePickerDialog.OnDateSetListener) typee,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        assert activity.getFragmentManager() != null;
        dpd.show(activity.getSupportFragmentManager(), "Datepickerdialog");
    }
    public static String dateSelect(int year, int monthOfYear, int dayOfMonth ) {
        int month = monthOfYear;
        if (month == 12) {
            month = 1;
        } else {
            month = monthOfYear + 1;
        }

        String mainMonth, mainDay;


        if (month <= 9) {
            mainMonth = "0" + month;
        } else {
            mainMonth = String.valueOf(month);
        }
        if (dayOfMonth <= 9) {
            mainDay = "0" + dayOfMonth;
        } else {
            mainDay = String.valueOf(dayOfMonth);
        }
        String selectedDate = year + "-" + mainMonth + "-" + mainDay;//set the selected date

        return selectedDate;
    }

}
