package com.ismos_salt_erp.utils;

import com.ismos_salt_erp.R;

import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class MonitoringUtil {
    public static final String addMonitoring = "Add Monitoring";
    public static final String monitoringHistory = "Monitoring History";


    public static List<String> getMonitoringNameList() {
        List<String> list = new ArrayList<>();
        list.add(addMonitoring);
        list.add(monitoringHistory);
        return list;
    }


    public static List<Integer> getMonitoringImageList() {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.add_monitoring);
        list.add(R.drawable.monitoring_history);
        return list;
    }

}
