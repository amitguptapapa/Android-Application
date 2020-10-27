package com.vis.android.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravi on 15/03/18.
 */

public class DatabaseHelperIndDynamic extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "industrial_db";


    public DatabaseHelperIndDynamic(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create industrial dynamic view table
        db.execSQL(TableIndustrial.SQL_CREATE_TABLE_IND_DYNAMIC);



    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TableIndustrial.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertIndustrialData(String sno, String blockname, String totalslab, String height, String yrcons, String typecons, String structure, String area) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        //values.put(TableIndustrial.COLUMN_NOTE, note);
        values.put(TableIndustrial.columnsno,sno);
        values.put(TableIndustrial.columnblockname,blockname);
        values.put(TableIndustrial.columntotalslab,totalslab);
        values.put(TableIndustrial.columnheight,height);
        values.put(TableIndustrial.columnyrcons,yrcons);
        values.put(TableIndustrial.columntypecons,typecons);
        values.put(TableIndustrial.columnstructure,structure);
        values.put(TableIndustrial.columnarea,area);

        // insert row
        long id = db.insert(TableIndustrial.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public TableIndustrial getIndustrialData(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        /*Cursor cursor = db.query(TableIndustrial.TABLE_NAME,
                new String[]{TableIndustrial.COLUMN_ID, TableIndustrial.COLUMN_NOTE, TableIndustrial.COLUMN_TIMESTAMP},
                TableIndustrial.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);*/
        Cursor cursor = db.query(TableIndustrial.TABLE_NAME,
                new String[]{TableIndustrial.COLUMN_ID, TableIndustrial.columnsno, TableIndustrial.columnblockname, TableIndustrial.columntotalslab, TableIndustrial.columnheight, TableIndustrial.columnyrcons, TableIndustrial.columntypecons, TableIndustrial.columnstructure, TableIndustrial.columnarea, TableIndustrial.COLUMN_TIMESTAMP},
                TableIndustrial.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        TableIndustrial note = new TableIndustrial(
                cursor.getInt(cursor.getColumnIndex(TableIndustrial.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(TableIndustrial.columnsno)),
                cursor.getString(cursor.getColumnIndex(TableIndustrial.columnblockname)),
                cursor.getString(cursor.getColumnIndex(TableIndustrial.columntotalslab)),
                cursor.getString(cursor.getColumnIndex(TableIndustrial.columnheight)),
                cursor.getString(cursor.getColumnIndex(TableIndustrial.columnyrcons)),
                cursor.getString(cursor.getColumnIndex(TableIndustrial.columntypecons)),
                cursor.getString(cursor.getColumnIndex(TableIndustrial.columnstructure)),
                cursor.getString(cursor.getColumnIndex(TableIndustrial.columnarea)),
                cursor.getString(cursor.getColumnIndex(TableIndustrial.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return note;
    }

    public List<TableIndustrial> getAllIndustrialData() {
        List<TableIndustrial> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TableIndustrial.TABLE_NAME + " ORDER BY " +
                TableIndustrial.COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
      /*  if (cursor.moveToFirst()) {
            do {
                TableIndustrial note = new TableIndustrial();
                note.setId(cursor.getInt(cursor.getColumnIndex(TableIndustrial.COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(TableIndustrial.COLUMN_NOTE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(TableIndustrial.COLUMN_TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }*/
        if (cursor.moveToFirst()) {
            do {
                TableIndustrial note = new TableIndustrial();
                note.setId(cursor.getInt(cursor.getColumnIndex(TableIndustrial.COLUMN_ID)));
                note.setSNo(cursor.getString(cursor.getColumnIndex(TableIndustrial.columnsno)));
                note.setBlockName(cursor.getString(cursor.getColumnIndex(TableIndustrial.columnblockname)));
                note.setTotalSlab(cursor.getString(cursor.getColumnIndex(TableIndustrial.columntotalslab)));
                note.setHeight(cursor.getString(cursor.getColumnIndex(TableIndustrial.columnheight)));
                note.setYrCons(cursor.getString(cursor.getColumnIndex(TableIndustrial.columnyrcons)));
                note.setTypeCons(cursor.getString(cursor.getColumnIndex(TableIndustrial.columntypecons)));
                note.setStructure(cursor.getString(cursor.getColumnIndex(TableIndustrial.columnstructure)));
                note.setArea(cursor.getString(cursor.getColumnIndex(TableIndustrial.columnarea)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(TableIndustrial.COLUMN_TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }


        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public long getIndustrialCount() {
        //String countQuery = "SELECT  * FROM " + TableIndustrial.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
       // Cursor cursor = db.rawQuery(countQuery, null);

        long cnt = DatabaseUtils.queryNumEntries(db, TableIndustrial.TABLE_NAME);

        Log.v("counttt", String.valueOf(cnt));


        //int count = cursor.getCount();

        //cursor.close();

        // return count
        return cnt;
    }

    public int updateIndustrialData(TableIndustrial note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TableIndustrial.columnsno, note.getSNo());
        values.put(TableIndustrial.columnblockname, note.getBlockName());
        values.put(TableIndustrial.columntotalslab, note.getTotalSlab());
        values.put(TableIndustrial.columnheight, note.getHeight());
        values.put(TableIndustrial.columnyrcons, note.getYrCons());
        values.put(TableIndustrial.columntypecons, note.getTypeCons());
        values.put(TableIndustrial.columnstructure, note.getStructure());
        values.put(TableIndustrial.columnarea, note.getArea());

        // updating row
        return db.update(TableIndustrial.TABLE_NAME, values, TableIndustrial.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteIndustrialData(TableIndustrial note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableIndustrial.TABLE_NAME, TableIndustrial.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }

}
