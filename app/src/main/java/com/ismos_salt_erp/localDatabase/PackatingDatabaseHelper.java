package com.ismos_salt_erp.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PackatingDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Packating.db";
    public static final String TABLE_NAME = "Packating_Table";
    private static final int VERSION_NUMBER = 5;

    private static final String ID = "_id";
    private static final String ITEM_ID = "ItemId";
    private static final String WEIGHT = "Weight";
    private static final String QUANTITY = "Quantity";
    private static final String AVAILABLE = "Available";
    private static final String TOTAL = "Total";

    private static final String select_all = "SELECT * FROM " + TABLE_NAME;

    private Context context;

    public PackatingDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ITEM_ID + " VARCHAR(255)," + WEIGHT + " VARCHAR(255)," + QUANTITY + " VARCHAR(255)," + AVAILABLE + " VARCHAR(255)," + TOTAL + " VARCHAR(255));");
        } catch (Exception e) {

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


    public long insertData(String itemId, String weight, String quantity, String available, String total) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_ID, itemId);
        contentValues.put(WEIGHT, weight);
        contentValues.put(QUANTITY, quantity);
        contentValues.put(AVAILABLE, available);
        contentValues.put(TOTAL, total);
        long rowId = -1;
        try {
            rowId = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        } catch (Exception e) {

        }
        return rowId;
    }

    public int updateData(String updateId, String itemId, String weight, String quantity, String available, String total) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_ID, itemId);
        contentValues.put(WEIGHT, weight);
        contentValues.put(QUANTITY, quantity);
        contentValues.put(AVAILABLE, available);
        contentValues.put(TOTAL, total);
        int rowId = sqLiteDatabase.update(TABLE_NAME, contentValues, ID + " = ?", new String[]{updateId});
        return rowId;
    }

    public int deleteData(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, ID + " = ?", new String[]{id});
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
