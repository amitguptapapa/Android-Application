package com.vis.android.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohammadshiblee on 27/03/18.
 */

public class DatabaseHelperSA_Dynamic extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "specialasset_db";


    public DatabaseHelperSA_Dynamic(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create industrial dynamic view table

        db.execSQL(TableSpecialAssets.SQL_CREATE_TABLE_SA_DYNAMIC);


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed

        db.execSQL("DROP TABLE IF EXISTS " + TableSpecialAssets.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    ////Special Assest

    public long insertSpecialAssetsData(String sno, String blockname, String totalslab, String height, String yrcons, String typecons, String structure, String area) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        //values.put(TableSpecialAssets.COLUMN_NOTE, note);
        values.put(TableSpecialAssets.columnsno,sno);
        values.put(TableSpecialAssets.columnblockname,blockname);
        values.put(TableSpecialAssets.columntotalslab,totalslab);
        values.put(TableSpecialAssets.columnheight,height);
        values.put(TableSpecialAssets.columnyrcons,yrcons);
        values.put(TableSpecialAssets.columntypecons,typecons);
        values.put(TableSpecialAssets.columnstructure,structure);
        values.put(TableSpecialAssets.columnarea,area);

        // insert row
        long id = db.insert(TableSpecialAssets.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public TableSpecialAssets getSpecialAssetsData(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        /*Cursor cursor = db.query(TableSpecialAssets.TABLE_NAME,
                new String[]{TableSpecialAssets.COLUMN_ID, TableSpecialAssets.COLUMN_NOTE, TableSpecialAssets.COLUMN_TIMESTAMP},
                TableSpecialAssets.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);*/
        Cursor cursor = db.query(TableSpecialAssets.TABLE_NAME,
                new String[]{TableSpecialAssets.COLUMN_ID, TableSpecialAssets.columnsno, TableSpecialAssets.columnblockname, TableSpecialAssets.columntotalslab, TableSpecialAssets.columnheight, TableSpecialAssets.columnyrcons, TableSpecialAssets.columntypecons, TableSpecialAssets.columnstructure, TableSpecialAssets.columnarea, TableSpecialAssets.COLUMN_TIMESTAMP},
                TableSpecialAssets.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        TableSpecialAssets note = new TableSpecialAssets(
                cursor.getInt(cursor.getColumnIndex(TableSpecialAssets.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(TableSpecialAssets.columnsno)),
                cursor.getString(cursor.getColumnIndex(TableSpecialAssets.columnblockname)),
                cursor.getString(cursor.getColumnIndex(TableSpecialAssets.columntotalslab)),
                cursor.getString(cursor.getColumnIndex(TableSpecialAssets.columnheight)),
                cursor.getString(cursor.getColumnIndex(TableSpecialAssets.columnyrcons)),
                cursor.getString(cursor.getColumnIndex(TableSpecialAssets.columntypecons)),
                cursor.getString(cursor.getColumnIndex(TableSpecialAssets.columnstructure)),
                cursor.getString(cursor.getColumnIndex(TableSpecialAssets.columnarea)),
                cursor.getString(cursor.getColumnIndex(TableSpecialAssets.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return note;
    }

    public List<TableSpecialAssets> getAllSpecialAssetsData() {
        List<TableSpecialAssets> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TableSpecialAssets.TABLE_NAME + " ORDER BY " +
                TableSpecialAssets.COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
      /*  if (cursor.moveToFirst()) {
            do {
                TableSpecialAssets note = new TableSpecialAssets();
                note.setId(cursor.getInt(cursor.getColumnIndex(TableSpecialAssets.COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(TableSpecialAssets.COLUMN_NOTE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(TableSpecialAssets.COLUMN_TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }*/
        if (cursor!=null && cursor.moveToFirst()) {
            do {
                TableSpecialAssets note = new TableSpecialAssets();
                note.setId(cursor.getInt(cursor.getColumnIndex(TableSpecialAssets.COLUMN_ID)));
                note.setSNo(cursor.getString(cursor.getColumnIndex(TableSpecialAssets.columnsno)));
                note.setBlockName(cursor.getString(cursor.getColumnIndex(TableSpecialAssets.columnblockname)));
                note.setTotalSlab(cursor.getString(cursor.getColumnIndex(TableSpecialAssets.columntotalslab)));
                note.setHeight(cursor.getString(cursor.getColumnIndex(TableSpecialAssets.columnheight)));
                note.setYrCons(cursor.getString(cursor.getColumnIndex(TableSpecialAssets.columnyrcons)));
                note.setTypeCons(cursor.getString(cursor.getColumnIndex(TableSpecialAssets.columntypecons)));
                note.setStructure(cursor.getString(cursor.getColumnIndex(TableSpecialAssets.columnstructure)));
                note.setArea(cursor.getString(cursor.getColumnIndex(TableSpecialAssets.columnarea)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(TableSpecialAssets.COLUMN_TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }


        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public long getSpecialAssetsCount() {
       // String countQuery = "SELECT  * FROM " + TableSpecialAssets.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = db.rawQuery(countQuery, null);

       // int count = cursor.getCount();
       // cursor.close();

        long cnt = DatabaseUtils.queryNumEntries(db, TableSpecialAssets.TABLE_NAME);


        // return count
        return cnt;
    }

    public int updateSpecialAssetsData(TableSpecialAssets note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TableSpecialAssets.columnsno, note.getSNo());
        values.put(TableSpecialAssets.columnblockname, note.getBlockName());
        values.put(TableSpecialAssets.columntotalslab, note.getTotalSlab());
        values.put(TableSpecialAssets.columnheight, note.getHeight());
        values.put(TableSpecialAssets.columnyrcons, note.getYrCons());
        values.put(TableSpecialAssets.columntypecons, note.getTypeCons());
        values.put(TableSpecialAssets.columnstructure, note.getStructure());
        values.put(TableSpecialAssets.columnarea, note.getArea());

        // updating row
        return db.update(TableSpecialAssets.TABLE_NAME, values, TableSpecialAssets.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteSpecialAssetsData(TableSpecialAssets note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableSpecialAssets.TABLE_NAME, TableSpecialAssets.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }



}
