package com.ismos_salt_erp.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ismos_salt_erp.R;

import java.util.List;

public class LicenseInfoDialog extends DialogFragment {
    private TextView et;
    private SmartMaterialSpinner spinner1;
    private List<String> provinceList;
    String value; // I try to get the value of EditText like this, but it doesn't work
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setCancelable(false);

        builder.setView(inflater.inflate(R.layout.lisence_info_layout, null))
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        et = (TextView) LicenseInfoDialog.this.getDialog().findViewById(R.id.reference);
                        value = et.getText().toString();
                        Toast.makeText(getContext(),  value, Toast.LENGTH_SHORT).show();// I try to get the value of EditText like this, but it doesn't work
                    }})
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LicenseInfoDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
