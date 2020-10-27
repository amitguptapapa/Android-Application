package com.vis.android.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    DataBaseHelper(Context context) {
        super(context, "vis", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableMultiStoryFlats.SQL_CREATE_Stories);
        db.execSQL(TableGroupHousing.SQL_CREATE_DATA);
        db.execSQL(TableOtherThanFlats.SQL_CREATE_DATA);
        db.execSQL(TableIndustrial.SQL_CREATE_DATA);
        db.execSQL(TableSpecialAssets.SQL_CREATE_DATA);
        db.execSQL(TableGeneralForm.SQL_CREATE_DATA);
        db.execSQL(TableStates.SQL_CREATE_DATA);
        db.execSQL(TableDistricts.SQL_CREATE_DATA);
        db.execSQL(TableCities.SQL_CREATE_DATA);
        db.execSQL(TableVillages.SQL_CREATE_DATA);
        db.execSQL(TableTehsils.SQL_CREATE_DATA);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TableMultiStoryFlats.MultiStory);
            db.execSQL("DROP TABLE IF EXISTS " + TableGroupHousing.attachment);
            db.execSQL("DROP TABLE IF EXISTS " + TableOtherThanFlats.flatattachment);
            db.execSQL("DROP TABLE IF EXISTS " + TableIndustrial.attachment);
            db.execSQL("DROP TABLE IF EXISTS " + TableSpecialAssets.attachment);
            db.execSQL("DROP TABLE IF EXISTS " + TableGeneralForm.attachment);
            db.execSQL("DROP TABLE IF EXISTS " + TableStates.attachment);
            db.execSQL("DROP TABLE IF EXISTS " + TableDistricts.attachment);
            db.execSQL("DROP TABLE IF EXISTS " + TableCities.attachment);
            db.execSQL("DROP TABLE IF EXISTS " + TableVillages.attachment);
            db.execSQL("DROP TABLE IF EXISTS " + TableTehsils.attachment);
            onCreate(db);
        }
    }

    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }
}
