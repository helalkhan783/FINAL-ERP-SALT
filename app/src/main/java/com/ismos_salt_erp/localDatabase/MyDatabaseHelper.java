package com.ismos_salt_erp.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "New_Sale.db";
    public static final String TABLE_NAME = "New_Product_Sale";
    private static final int VERSION_NUMBER = 31;


    private static final String ID = "_id";
    private static final String PRODUCT_ID = "Product_Id";
    private static final String NAME = "Name";
    private static final String QUANTITY = "Quantity";
    private static final String UNIT = "Unit";
    private static final String UNIT_NAME = "Unit_Name";
    private static final String PRICE = "Price";
    private static final String DISCOUNT = "Discount";
    private static final String TOTAL_PRICE = "Total_Price";


    private static final String select_all = "SELECT * FROM " + TABLE_NAME;

    private Context context;

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //Toast.makeText(context, "On Create is Called", Toast.LENGTH_SHORT).show();
            db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + PRODUCT_ID + " VARCHAR(255) unique," + NAME + " VARCHAR(255),"
                    + QUANTITY + " VARCHAR(255)," + UNIT + " VARCHAR(255)," + UNIT_NAME + " VARCHAR(255)," + PRICE + " VARCHAR(255)," + DISCOUNT + " VARCHAR(255)," + TOTAL_PRICE + " VARCHAR(255));");
        } catch (Exception e) {
            //  Toast.makeText(context, "On Create" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            // Toast.makeText(context, "On Upgrade is Called", Toast.LENGTH_SHORT).show();
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        } catch (Exception e) {
            // Toast.makeText(context, "On Upgrade " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public long insertData(String productIdVal, String name, String quantity, String unit, String unitName,String price,String discount,String totalPrice) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_ID, productIdVal);
        contentValues.put(NAME, name);
        contentValues.put(QUANTITY, quantity);
        contentValues.put(UNIT, unit);
        contentValues.put(UNIT_NAME, unitName);
        contentValues.put(PRICE, price);
        contentValues.put(DISCOUNT, discount);
        contentValues.put(TOTAL_PRICE, totalPrice);
        long rowId = -1;
        try {
            rowId = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            Log.d("ERROR",""+e.getMessage());
        }
        return rowId;
    }

    public boolean updateData(String id, String name, String quantity, String unit, String unitName,String price,String discount,String totalPrice) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_ID, id);
        contentValues.put(NAME, name);
        contentValues.put(QUANTITY, quantity);
        contentValues.put(UNIT, unit);
        contentValues.put(UNIT_NAME, unitName);
        contentValues.put(PRICE, price);
        contentValues.put(DISCOUNT, discount);
        contentValues.put(TOTAL_PRICE, totalPrice);
        sqLiteDatabase.update(TABLE_NAME, contentValues, PRODUCT_ID + " = ?", new String[]{id});

        return true;
    }

    public int deleteData(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, PRODUCT_ID + " = ?", new String[]{id});
    }


    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME); //delete all rows in a table
        db.close();
    }

    public Cursor displayAllData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(select_all, null);
        return cursor;
    }
    public List<SalesRequisitionItems>   getDataFromLoca(){
        List<SalesRequisitionItems> itemsList = new ArrayList<>();
        Cursor cursor = this.displayAllData();
        if (cursor.getCount() > 0) {//means didn't have any data
            while (cursor.moveToNext()) {
                SalesRequisitionItems item = new SalesRequisitionItems();
                item.setProductID(cursor.getString(1));
                item.setProductTitle(cursor.getString(2));
                item.setQuantity(cursor.getString(3));
                item.setUnit(cursor.getString(4));
                item.setUnit_name(cursor.getString(5));
                item.setPrice(cursor.getString(6));
                item.setDiscount(cursor.getString(7));
                item.setTotalPrice(cursor.getString(8));
                itemsList.add(item);
            }
        }

        return itemsList;
    }


}
