package com.ismos_salt_erp.view.fragment.customers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.utils.PathUtil;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public abstract class AddUpDel extends BaseFragment  {
    private  final int PICK_IMAGE = 200;
    private  final int STORAGE_PERMISSION_REQUEST_CODE = 300;

    public void showDialog(String title) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        @SuppressLint("InflateParams")
        View view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.purchase_dialog, null);
        builder.setView(view); //set layout
        TextView tvTitle, tvMessage;
        ImageView imageIcon = view.findViewById(R.id.img_icon);
        tvMessage = view.findViewById(R.id.tv_message);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText("" + title);//set warning titleCustomerList
        tvMessage.setText("SALT ERP");
        imageIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.app_sub_logo));//set warning image
        Button bOk = view.findViewById(R.id.btn_ok);
        Button cancel = view.findViewById(R.id.cancel);
        android.app.AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        cancel.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        bOk.setOnClickListener(v -> {
            alertDialog.dismiss();
            if (!isInternetOn(getActivity())) {
                infoMessage(getActivity().getApplication(), "Please check your internet connection");
                return;
            }
            save();
        });
        alertDialog.show();

    }

    public abstract void save();

    public void forImage() {
        if (!(checkStoragePermission())) {
            requestStoragePermission(STORAGE_PERMISSION_REQUEST_CODE);
        } else {
            getLogoImageFromFile(getActivity().getApplication(), PICK_IMAGE);
        }
    }

    @SneakyThrows
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {

                return;
            }
            imageUri(data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    infoMessage(requireActivity().getApplication(), "Permission Granted");
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    infoMessage(requireActivity().getApplication(), "Permission Decline");
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    public abstract void imageUri(Intent uri);

    public Bitmap getBitmapImage(Intent uri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    // image uri convert to multipart body
    public MultipartBody.Part imageLogobody(Uri imageUri, String fileName) {
        MultipartBody.Part logoBody;
        if (imageUri != null) {//logo image not mandatory here so if user not select any logo image by default it send null
            File file = null;
            try {
                file = new File(PathUtil.getPath(getActivity(), imageUri));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

            // MultipartBody.Part is used to send also the actual file name
            logoBody =
                    MultipartBody.Part.createFormData("image", file.getName(), requestFile);//here document is name of from data
        } else {
            logoBody = null;
        }
        return logoBody;
    }

    public void message(String message) {
        Toasty.info(getContext(), "" + message, Toasty.LENGTH_LONG).show();
    }

    public  void errorMes(String message){
        Toasty.error(getContext(), "Something Wrong Contact to Support \n" + message, Toasty.LENGTH_LONG).show();

    }

}
