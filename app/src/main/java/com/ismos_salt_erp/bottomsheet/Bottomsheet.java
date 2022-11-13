package com.ismos_salt_erp.bottomsheet;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.ItemListAdapter;
import com.ismos_salt_erp.serverResponseModel.AddNewItemBrand;
import com.ismos_salt_erp.serverResponseModel.AddNewItemCategory;
import com.ismos_salt_erp.serverResponseModel.AddNewItemUnit;
import com.ismos_salt_erp.view.fragment.items.addNew.AddNewItem;

import java.util.List;

public class Bottomsheet extends BottomSheetDialogFragment {
    List<AddNewItemUnit> unitList;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetBehavior<View> bottomSheetBehavior;
    View rootView;
    String whoseFor;
    AddNewItem clickHandle;
    private List<AddNewItemBrand> brandResponseList;
    private List<AddNewItemCategory> categoryResponseList;

    public Bottomsheet(AddNewItem clickHandle, String whoseFor, List<AddNewItemUnit> unitList,   List<AddNewItemBrand> brandResponseList,     List<AddNewItemCategory> categoryResponseList) {
        this.unitList = unitList;
        this.whoseFor = whoseFor;
        this.clickHandle = clickHandle;
        this.brandResponseList = brandResponseList;
        this.categoryResponseList = categoryResponseList;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        return bottomSheetDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.bottom_model, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        LinearLayout layout = bottomSheetDialog.findViewById(R.id.layout);
        assert layout != null;
        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);

        RecyclerView rv = bottomSheetDialog.findViewById(R.id.recyclerView);
        BottomAdapter adapter = new BottomAdapter(clickHandle,whoseFor,bottomSheetBehavior,unitList,brandResponseList,categoryResponseList);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);


        ImageView backButton = bottomSheetDialog.findViewById(R.id.back);
        TextView  headerTv = bottomSheetDialog.findViewById(R.id.headerText);
        headerTv.setText(""+whoseFor);
        backButton.setOnClickListener(v -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN));

    }
}
