package com.ismos_salt_erp.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class EditSaleDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Edit_Sale.db";
    public static final String TABLE_NAME = "Edit_Product_Sale";
    private static final int VERSION_NUMBER = 1;


    private static final String ID = "_id";
    private static final String PRODUCT_ID = "Product_Id";
    private static final String ITEM = "Item";
    private static final String SELLING_PRICE = "Selling_Price";
    private static final String BUYING_PRICE = "Buying_Price";
    private static final String QUANTITY = "Quantity";
    private static final String UNIT = "Unit";
    private static final String UNIT_ID = "Unit_Id";
    private static final String TOTAL_AMOUNT = "Total_Amount";
    private static final String SOLD_FROM = "Sold_From";

    private static final String select_all = "SELECT * FROM " + TABLE_NAME;


    private Context context;

    public EditSaleDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + PRODUCT_ID + " VARCHAR(255) unique," + ITEM + " VARCHAR(255)," + SELLING_PRICE + " VARCHAR(255)," + BUYING_PRICE + " VARCHAR(255)," + QUANTITY + " VARCHAR(255)," + UNIT + " VARCHAR(255)," + UNIT_ID + " VARCHAR(255)," + TOTAL_AMOUNT + " VARCHAR(255)," + SOLD_FROM + " VARCHAR(255));");
            Toast.makeText(context, "On Create is Called", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "On Create" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Toast.makeText(context, "On Upgrade is Called", Toast.LENGTH_SHORT).show();
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        } catch (Exception e) {
            Toast.makeText(context, "On Upgrade " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public long insertData(String productId, String item, String sellingPrice, String buyingPrice, String quantity, String unit,
                           String unitId, String totalAmount, String soldFrom) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_ID, productId);
        contentValues.put(ITEM, item);
        contentValues.put(SELLING_PRICE, sellingPrice);
        contentValues.put(BUYING_PRICE, buyingPrice);
        contentValues.put(QUANTITY, quantity);
        contentValues.put(UNIT, unit);
        contentValues.put(UNIT_ID, unitId);
        contentValues.put(TOTAL_AMOUNT, totalAmount);
        contentValues.put(SOLD_FROM, soldFrom);

        long rowId = -1;
        try {
            rowId = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        } catch (Exception e) {

        }
        return rowId;
    }


    public boolean updateData(String updateId,
                              String productId, String item, String sellingPrice, String buyingPrice, String quantity, String unit,
                              String unitId, String totalAmount, String soldFrom
    ) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_ID, productId);
        contentValues.put(ITEM, item);
        contentValues.put(SELLING_PRICE, sellingPrice);
        contentValues.put(BUYING_PRICE, buyingPrice);
        contentValues.put(QUANTITY, quantity);
        contentValues.put(UNIT, unit);
        contentValues.put(UNIT_ID, unitId);
        contentValues.put(TOTAL_AMOUNT, totalAmount);
        contentValues.put(SOLD_FROM, soldFrom);
        sqLiteDatabase.update(TABLE_NAME, contentValues, PRODUCT_ID + " = ?", new String[]{updateId});
        return true;
    }



    public int deleteData(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, PRODUCT_ID + " = ?", new String[]{id});
    }

    public Cursor displayAllData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(select_all, null);
        return cursor;
    }


}
