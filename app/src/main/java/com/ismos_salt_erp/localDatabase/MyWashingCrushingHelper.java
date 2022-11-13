package com.ismos_salt_erp.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyWashingCrushingHelper  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "New_Washing.db";
    public static final String TABLE_NAME = "New_Washing_Crushing";
    private static final int VERSION_NUMBER = 28;


    private static final String ID = "_id";
    private static final String PRODUCT_ID = "Product_Id";
    private static final String NAME = "Name";
    private static final String QUANTITY = "Quantity";
    private static final String UNIT = "Unit";
    private static final String SOLD_FROM = "Sold_From";
    private static final String UNIT_NAME = "Unit_Name";


    private static final String select_all = "SELECT * FROM " + TABLE_NAME;

    private Context context;

    public MyWashingCrushingHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //Toast.makeText(context, "On Create is Called", Toast.LENGTH_SHORT).show();
            db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + PRODUCT_ID + " VARCHAR(255) unique," + NAME + " VARCHAR(255)," + QUANTITY + " VARCHAR(255)," + UNIT + " VARCHAR(255),"+ SOLD_FROM + " VARCHAR(255)," + UNIT_NAME + " VARCHAR(255));");
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



    public long insertData(String productIdVal, String name, String quantity, String unit,String soldFrom, String unitName) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_ID, productIdVal);
        contentValues.put(NAME, name);
        contentValues.put(QUANTITY, quantity);
        contentValues.put(UNIT, unit);
        contentValues.put(SOLD_FROM, soldFrom);
        contentValues.put(UNIT_NAME, unitName);
        long rowId = -1;
        try {
            rowId = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        } catch (Exception e) {

        }
        return rowId;
    }



    public boolean updateData(String id, String name, String quantity, String unit,String soldFrom, String unitName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_ID, id);
        contentValues.put(NAME, name);
        contentValues.put(QUANTITY, quantity);
        contentValues.put(UNIT, unit);
        contentValues.put(SOLD_FROM, soldFrom);
        contentValues.put(UNIT_NAME, unitName);

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
}
