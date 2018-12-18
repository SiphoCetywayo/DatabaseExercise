package com.example.sipho.databaseexercise;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "Storage.db";
    public static final String TABLE_NAME = "racecars";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "Speed";
    public static final String COL_4 = "Tank";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE  " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, Speed INTEGER, Tank INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String name, String speed, String tank) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, speed);
        contentValues.put(COL_4, tank);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;

        else
            return true;

    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor results = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return results;

    }

    public Cursor getCarFuelSizes(String smallestTank, SQLiteDatabase sqLiteDatabase) {

        String projection[] = {
                DatabaseHandler.COL_1,
                DatabaseHandler.COL_2,
                DatabaseHandler.COL_3,
                DatabaseHandler.COL_4};

        String selection = DatabaseHandler.COL_4 + " > ?";
        String[] selectionArgs = new String[]{smallestTank};
        Cursor cursor = sqLiteDatabase.query(DatabaseHandler.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        return cursor;
    }

    public boolean updateData(String id, String name, String speed, String tank) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, speed);
        contentValues.put(COL_4, tank);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}
