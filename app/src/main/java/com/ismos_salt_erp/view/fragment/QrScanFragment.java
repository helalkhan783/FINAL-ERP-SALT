package com.ismos_salt_erp.view.fragment;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.view.activity.LoadUrlActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class QrScanFragment extends BaseFragment implements QRCodeReaderView.OnQRCodeReadListener {
    private View view;
    @BindView(R.id.toolbarTitle)
    TextView toolbar;
    @BindView(R.id.qrdecoderview)
    QRCodeReaderView qrCodeReaderView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_qr_scan, container, false);
        ButterKnife.bind(this, view);
        toolbar.setText("Scan Qr Code");

        /**
         * for set qr code config
         */
        qrCodeReaderView.setOnQRCodeReadListener(this);

        // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView.setAutofocusInterval(2000L);

        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(true);

        // Use this function to set front camera preview
        qrCodeReaderView.setFrontCamera();

        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();

        return view;
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {

        String currentLink = text;
        if (currentLink.isEmpty()) {
            return;
        } else {
            qrCodeReaderView.stopCamera();
            if (!(isInternetOn(getActivity()))) {
                //. Navigation.findNavController(getView()).navigate(R.id.action_qrScanFragment_to_internetConnectionFaildFragment);
                Toasty.info(getContext(), "Please Check your Internet Connection", Toasty.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(getActivity(), LoadUrlActivity.class);
                intent.putExtra("QR_URL", currentLink);
                startActivity(intent);
            }

        }

    }

    @Override
    public void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        qrCodeReaderView.startCamera();
    }


    @OnClick(R.id.backbtn)
    public void backBtnClick() {
        qrCodeReaderView.stopCamera();
        getActivity().onBackPressed();
    }
}