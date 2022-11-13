package com.ismos_salt_erp.view.fragment.items;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.utils.PathUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Dialog {
    private static final int PICK_IMAGE = 200;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 300;
    public  static Uri imageUri;
    FragmentActivity context;
    Bitmap bitmap;
    Dialog(FragmentActivity context){
        this.context = context;
    }


    public ImageView brandImage;
     public void brandDialog() {
         int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        @SuppressLint("InflateParams")
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.band_dialog_layout, null);
        //Set the view
        builder.setView(view);
        EditText brandName;
        brandName = view.findViewById(R.id.branNameEt1);
        brandImage = view.findViewById(R.id.imageView);
        Button bOk = view.findViewById(R.id.saveBtn1);
        Button cancel = view.findViewById(R.id.cancelBtn1);
        AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

          brandImage.setOnClickListener(new View.OnClickListener() {
           @Override
             public void onClick(View v) {


               Intent intent = new Intent();
               intent.setType("image/*");
               intent.setAction(Intent.ACTION_GET_CONTENT);
               context.startActivityForResult(intent, 200);


             /*  Intent intent = new Intent(Intent.ACTION_VIEW);
               intent.setType("image/*");
               context.startActivityForResult(intent,200);

                if (result == PackageManager.PERMISSION_GRANTED){

               }else {

               }*/

           /* if (!(checkStoragePermission())) {
                   requestStoragePermission(STORAGE_PERMISSION_REQUEST_CODE);
                 } else {
                     getLogoImageFromFile(getActivity().getApplication(), PICK_IMAGE);
                 } */


            }
        });


        cancel.setOnClickListener(v -> alertDialog.dismiss());//for cancel
        bOk.setOnClickListener(v -> {

            if (brandName.getText().toString().isEmpty()){
                brandName.setError("Error");
                brandName.requestFocus();
                return;
            }
            /** for image*/

            MultipartBody.Part logoBody;
            if (imageUri != null) {//logo image not mandatory here so if user not select any logo image by default it send null
                File file = null;
                try {
                    file = new File(PathUtil.getPath(context, imageUri));
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


            alertDialog.dismiss();
        });

        /* if (alertDialog.getWindow() != null) {
         *//**
         * for show sliding animation in alert dialog
         *//*
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
        }*/

        alertDialog.show();

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode ==  context.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }

            InputStream inputStream = null;
            try {
                inputStream = context.getContentResolver().openInputStream(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageUri = data.getData();

           // convertUriToBitmapImageAndSetInImageView(getPath(data.getData()), data.getData());
            /**
             * for set selected image in image view
             */
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //  binding.image.setImageDrawable(null);
           // binding.image.setImageBitmap(bitmap);

            brandImage.setImageBitmap(bitmap);


            /**
             * now set profile Image
             * */
          //  binding.imageName.setText(String.valueOf(new File("" + data.getData()).getName()));

            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
            Log.d("LOGO_IMAGE", String.valueOf(inputStream));


        }
    }

/**
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
*/

}
