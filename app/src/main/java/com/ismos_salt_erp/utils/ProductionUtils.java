package com.ismos_salt_erp.utils;

import com.ismos_salt_erp.R;

import java.util.ArrayList;
import java.util.List;

public class ProductionUtils {
    public static final String washingCrushing = "Washing Crushing";
    public static final String washingCrushingList = "Washing Crushing List";
    public static final String pendingWashingCrushing = "Washing Crushing Pending";
    public static final String iodization = "Iodization";
    public static final String iodizationList = "Iodization History";
    public static final String pendingIodization = "Iodization Pending";
    public static final String packaging = "Packaging";
    public static final String packating = "Cartoning";
    public static final String packagingList = "Packaging List";
    public static final String packegingPending = "Packaging Pending";
    public static final String packatingList = "Cartoning List";
    public static final String cartooningPending = "Cartoning Pending";
    public static final String unpackCartoning = "Unpack Cartoning";
    public static final String stockInfo = MtUtils.currentStockInfo;
    public static List<String> productionNameList() {
        List<String> productionNameList = new ArrayList<>();
        productionNameList.add(washingCrushing);
        productionNameList.add(washingCrushingList);
        productionNameList.add(pendingWashingCrushing);
        productionNameList.add(iodization);
        productionNameList.add(iodizationList);
        productionNameList.add(pendingIodization);
        productionNameList.add(packaging);
        productionNameList.add(packating);
        productionNameList.add(packagingList);
        //   productionNameList.add(packegingPending);
        productionNameList.add(packatingList);
        // productionNameList.add(cartooningPending);
        productionNameList.add(unpackCartoning);
        productionNameList.add(stockInfo);
        return productionNameList;
    }

    public static List<Integer> productionImageList() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.washing_crushing);
        imageList.add(R.drawable.washing_crushing_list);
        imageList.add(R.drawable.pending_washing);
        imageList.add(R.drawable.iodazetion_icon);
        imageList.add(R.drawable.iodazetion_history);
        imageList.add(R.drawable.washing_crushing);
        imageList.add(R.drawable.packaging);
        imageList.add(R.drawable.packating);
        imageList.add(R.drawable.packating);
        imageList.add(R.drawable.packaeting_list);
        imageList.add(R.drawable.packaging);
        imageList.add(R.drawable.packaging);
        imageList.add(R.drawable.unpacketing_list);
        imageList.add((R.drawable.stock_input_output_repurt));
        return imageList;
    }
}
