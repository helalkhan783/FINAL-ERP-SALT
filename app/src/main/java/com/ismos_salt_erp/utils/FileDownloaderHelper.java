package com.ismos_salt_erp.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import androidx.fragment.app.FragmentActivity;


public class FileDownloaderHelper {
    private FileDownloaderHelper() {
    }

    public static void downloadFile(FragmentActivity activity,String fileUri){
        DownloadManager downloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse("https://cmis.usi.net.bd" + fileUri);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setVisibleInDownloadsUi(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uri.getLastPathSegment());
        downloadManager.enqueue(request);
    }
}
