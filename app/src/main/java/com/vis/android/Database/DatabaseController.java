package com.vis.android.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class DatabaseController {
    public static SQLiteDatabase myDataBase;
    private static DataBaseHelper myDataBaseHelper;

    public static void openDataBase(Context mContext) {
        if (myDataBaseHelper == null) {
            myDataBaseHelper = new DataBaseHelper(mContext);
        }
        if (myDataBase == null) {
            myDataBase = myDataBaseHelper.getWritableDatabase();
        }
    }

    public static void removeTable(String tableName) {
        myDataBase.delete(tableName, null, null);
    }

    public static long insertData(ContentValues values, String tableName) {
        long id = -1;
        try {
            id = myDataBase.insert(tableName, null, values);
           // Log.v("database----", String.valueOf(values));


        } catch (Exception e) {

            e.printStackTrace();
        }
        //Log.v("====insertData ", String.valueOf(id));
        return id;
    }

    public static long insertUpdateData(ContentValues values, String tableName, String columnName, String uniqueId) {
        try {
            if (checkRecordExist(tableName, columnName, uniqueId)) {
                return (long) myDataBase.update(tableName, values, columnName + "='" + uniqueId + "'", null);
            }
            Log.v("databaseup----", String.valueOf(values));
            return myDataBase.insert(tableName, null, values);

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    public long insertRecordsInDB(String tableName, String nullColumnHack, ContentValues initialValues) {
        long n = -1;
        try {
            myDataBase.beginTransaction();
            n = myDataBase.insert(tableName, nullColumnHack, initialValues);

            myDataBase.endTransaction();
            myDataBase.setTransactionSuccessful();
        } catch (Exception e) {
            // how to do the rollback
            e.printStackTrace();
        }

        return n;
    }

    public static boolean checkRecordExist(String tableName, String columnName, String uniqueId) {
        boolean status = false;
        Cursor cursor = myDataBase.query(tableName, null, columnName + "='" + uniqueId + "'", null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                status = true;
            }
            cursor.close();
        }
        return status;
    }

    public static boolean isDataExit(String tableName) {
        long cnt = DatabaseUtils.queryNumEntries(myDataBase, tableName);
        // AppUtils.print("isDataExit " + cnt);
        Log.v("====isDataExit ", String.valueOf(cnt));

        return cnt > 0;
    }

    public static boolean deleteRow(String tableName, String keyName, String keyValue) {
        try {
            return myDataBase.delete(tableName, new StringBuilder().append(keyName).append("=").append(keyValue).toString(), null) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<JSONObject> fetchAllDataFromValues(String tableName) {
        String query = "SELECT * FROM " + tableName;
        //  AppUtils.print("===fetchAllDataFromValues" + query);
        Log.v("fetchAllDataFromValues", query);

        Cursor cursor = myDataBase.rawQuery(query, null);

        ArrayList<JSONObject> arrayList = new ArrayList();
        if (cursor != null && cursor.moveToNext()) {
            JSONObject jsonObject = new JSONObject();
            for (int i = 0; i < cursor.getCount(); i++) {
                try {
                    jsonObject.put(cursor.getColumnName(i), cursor.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrayList.add(jsonObject);
                cursor.moveToNext();
            }
        }
        cursor.close();


        /*ArrayList<JSONObject> arrayList = new ArrayList();
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                JSONObject jsonObject = new JSONObject();
                for (int j = 0; j < cursor.getColumnCount(); j++) {
                    try {
                        jsonObject.put(cursor.getColumnName(j), cursor.getString(j));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                arrayList.add(jsonObject);
                cursor.moveToNext();
            }
        }*/


        //cursor.close();
        return arrayList;
    }

    public static ArrayList<JSONObject> fetchDataByGroup(String tableName, String groupBy) {
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM " + tableName + " ORDER  BY " + groupBy + " DESC ", null);
        ArrayList<JSONObject> arrayList = new ArrayList();
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                JSONObject mJsonObject = new JSONObject();
                for (int j = 0; j < cursor.getColumnCount(); j++) {
                    try {
                        mJsonObject.put(cursor.getColumnName(j), cursor.getString(j));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                arrayList.add(mJsonObject);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }

    public static ArrayList<JSONObject> fetchDataFromId(String tableName, String key, String byValue) {
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM " + tableName + " Where " + key + " = " + byValue, null);
        ArrayList<JSONObject> arrayList = new ArrayList();
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                JSONObject mJsonObject = new JSONObject();
                for (int j = 0; j < cursor.getColumnCount(); j++) {
                    try {
                        mJsonObject.put(cursor.getColumnName(j), cursor.getString(j));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                arrayList.add(mJsonObject);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }

    public static String getDataUsingMultipleIds(String columnName, String columnName1, String fieldId, String tableName) {
        Cursor cursor = myDataBase.rawQuery("SELECT " + columnName + " FROM " + tableName + " Where " + columnName1 + " IN (" + fieldId + ")", null);
        ArrayList<String> mStrings = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                mStrings.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return TextUtils.join(", ", mStrings);
    }

    public static ArrayList<String> fetchAllDataSpecificColumn(String columnName, String tableName) {
        Cursor cursor = myDataBase.rawQuery("SELECT " + columnName + " FROM " + tableName, null);
        ArrayList<String> arrayList = new ArrayList();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    arrayList.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return arrayList;
    }

    public static void updateData(String tableName, ContentValues mContentValues, String columnName, String columnValue) {
        myDataBase.update(tableName, mContentValues, columnName + " = '" + columnValue + "'", null);
    }


/*    public static ArrayList<HashMap<String, String>> getCityData() {
        ArrayList<HashMap<String, String>> cityList = new ArrayList<HashMap<String, String>>();
        cityList.clear();
        Cursor cur = myDataBase.rawQuery("Select * from generalform ", null);
        Log.d("cur.getCount()", String.valueOf(cur.getCount()));
        if (cur != null && cur.moveToNext()) {
            for (int i = 0; i < cur.getCount(); i++) {
                HashMap<String, String> CatList = new HashMap<>();
                CatList.put("data", cur.getString(cur.getColumnIndex(TableGroupHousing.groupHousingColumn.data.toString())));

                cur.moveToNext();
                cityList.add(CatList);
                Log.v("catlist===", String.valueOf(CatList));
            }
        }
        cur.close();


        return cityList;
    }*/

    public static ArrayList<HashMap<String, String>> getGroupHousing() {
        ArrayList<HashMap<String, String>> cityList = new ArrayList<HashMap<String, String>>();
        cityList.clear();
        Cursor cur = myDataBase.rawQuery("Select * from attachment", null);
        Log.d("cur.getCount()", String.valueOf(cur.getCount()));
        if (cur != null && cur.moveToNext()) {
            for (int i = 0; i < cur.getCount(); i++) {
                HashMap<String, String> CatList = new HashMap<>();

                Log.v("databaseFetch===", cur.getString(cur.getColumnIndex(TableGroupHousing.groupHousingColumn.data.toString())));

                CatList.put("data", cur.getString(cur.getColumnIndex(TableGroupHousing.groupHousingColumn.data.toString())));

                cur.moveToNext();
                cityList.add(CatList);
                Log.v("catlist===", String.valueOf(CatList));
            }
        }
        cur.close();


        return cityList;
    }

    public static ArrayList<HashMap<String, String>> getSubCat() {
        ArrayList<HashMap<String, String>> cityList = new ArrayList<HashMap<String, String>>();
        cityList.clear();
        Cursor cur = myDataBase.rawQuery("Select * from multistory", null);
        Log.d("cur.getCount()", String.valueOf(cur.getCount()));
        if (cur != null && cur.moveToNext()) {
            for (int i = 0; i < cur.getCount(); i++) {
                HashMap<String, String> CatList = new HashMap<>();
                Log.v("databaseFetch===", cur.getString(cur.getColumnIndex(TableMultiStoryFlats.cityCatColumn.data.toString())));

                CatList.put("data", cur.getString(cur.getColumnIndex(TableMultiStoryFlats.cityCatColumn.data.toString())));

                cur.moveToNext();
                cityList.add(CatList);
                Log.v("catlist===", String.valueOf(CatList));
            }
        }
        cur.close();


        return cityList;
    }


    public static ArrayList<HashMap<String, String>> getOtherFlats() {
        ArrayList<HashMap<String, String>> cityList = new ArrayList<HashMap<String, String>>();
        cityList.clear();
        Cursor cur = myDataBase.rawQuery("Select * from flatattachment", null);
        Log.d("cur.getCount()", String.valueOf(cur.getCount()));
        if (cur != null && cur.moveToNext()) {
            for (int i = 0; i < cur.getCount(); i++) {
                HashMap<String, String> CatList = new HashMap<>();
                Log.v("databaseFetch===", cur.getString(cur.getColumnIndex(TableOtherThanFlats.OtherThanFlatsColumn.data.toString())));

                CatList.put("data", cur.getString(cur.getColumnIndex(TableOtherThanFlats.OtherThanFlatsColumn.data.toString())));

                cur.moveToNext();
                cityList.add(CatList);
                Log.v("catlist===", String.valueOf(CatList));
            }
        }
        cur.close();


        return cityList;
    }

    public static ArrayList<HashMap<String, String>> getIndustrial() {
        ArrayList<HashMap<String, String>> industrialList = new ArrayList<HashMap<String, String>>();
        industrialList.clear();
        Cursor cur = myDataBase.rawQuery("Select * from industrial", null);
        Log.d("cur.getCount()", String.valueOf(cur.getCount()));
        if (cur != null && cur.moveToNext()) {
            for (int i = 0; i < cur.getCount(); i++) {
                HashMap<String, String> CatList = new HashMap<>();

                Log.v("databaseFetch===", cur.getString(cur.getColumnIndex(TableIndustrial.industrialColumn.data.toString())));

                CatList.put("data", cur.getString(cur.getColumnIndex(TableIndustrial.industrialColumn.data.toString())));
                CatList.put("dynamic_data", cur.getString(cur.getColumnIndex(TableIndustrial.industrialColumn.dynamic_data.toString())));

                ///
                //CatList.put("newdata", cur.getString(cur.getColumnIndex(TableIndustrial.industrialColumn.data.toString())));
                ///


                cur.moveToNext();
                industrialList.add(CatList);
                Log.v("catlist===", String.valueOf(CatList));
            }
        }
        cur.close();


        return industrialList;
    }

    public static ArrayList<HashMap<String, String>> getSpecialAssets() {
        ArrayList<HashMap<String, String>> specialAssetList = new ArrayList<HashMap<String, String>>();
        specialAssetList.clear();
        Cursor cur = myDataBase.rawQuery("Select * from specialasset", null);
        Log.d("cur.getCount()", String.valueOf(cur.getCount()));
        if (cur != null && cur.moveToNext()) {
            for (int i = 0; i < cur.getCount(); i++) {
                HashMap<String, String> CatList = new HashMap<>();

                CatList.put("data", cur.getString(cur.getColumnIndex(TableSpecialAssets.specialAssetColumn.data.toString())));
                //CatList.put("dynamic_data", cur.getString(cur.getColumnIndex(TableSpecialAssets.specialAssetColumn.dynamic_data.toString())));

                cur.moveToNext();
                specialAssetList.add(CatList);
                Log.v("catlist===", String.valueOf(CatList));
            }
        }
        cur.close();


        return specialAssetList;
    }

    public static void searchfromdb() {
        String where = "Select * from Listing where ";
        where = where + " title LIKE  '%" + "ayur" + "%' ";
        where = where + " or details like %" + "doc" + "%";
        Log.v("selectfromdb", where);

    }

    public static ArrayList<HashMap<String, String>> getTableOne(String col, String id) {
        ArrayList<HashMap<String, String>> cityList = new ArrayList<HashMap<String, String>>();
        cityList.clear();
        Cursor cur = myDataBase.rawQuery("Select " + col + " from generalform " + " WHERE id " + "= '" + id + "'", null);

        Log.d("cur.getCount()", String.valueOf(cur.getCount()));
        if (cur != null && cur.moveToNext()) {
            for (int i = 0; i < cur.getCount(); i++) {
                HashMap CatList = new HashMap<>();
//                Log.v("databaseFetch===", cur.getString(cur.getColumnIndex(col)));
                CatList.put(col, cur.getString(cur.getColumnIndex(col)));
                cur.moveToNext();
                cityList.add(CatList);
                Log.v("catlist===", String.valueOf(CatList));
            }
        }
        cur.close();
        return cityList;
    }


    public static ArrayList<HashMap<String, String>> getGeneralForm(String columnName, String tableName) {
        ArrayList<HashMap<String, String>> generalFormList = new ArrayList<HashMap<String, String>>();
        generalFormList.clear();
        Cursor cur = myDataBase.rawQuery("SELECT " + columnName + " FROM " + tableName, null);

        Log.v("###cur", String.valueOf(cur));

        if (cur != null && cur.moveToNext()) {
            for (int i = 0; i < cur.getCount(); i++) {
                HashMap<String, String> CatList = new HashMap<>();
                CatList.put(columnName, cur.getString(cur.getColumnIndex(columnName)));
                cur.moveToNext();
                generalFormList.add(CatList);
                Log.v("catlist===", String.valueOf(CatList));
            }
        }
        cur.close();

        return generalFormList;
    }

    public static ArrayList<HashMap<String, String>> getStates() {
        ArrayList<HashMap<String, String>> cityList = new ArrayList<HashMap<String, String>>();
        cityList.clear();
        Cursor cur = myDataBase.rawQuery("Select * from states", null);

        Log.d("cur.getCount()", String.valueOf(cur.getCount()));
        if (cur != null && cur.moveToNext()) {
            for (int i = 0; i < cur.getCount(); i++) {
                HashMap CatList = new HashMap<>();
                Log.v("databaseFetch===", cur.getString(cur.getColumnIndex("id")));
                CatList.put("id", cur.getString(cur.getColumnIndex("id")));
                CatList.put("name", cur.getString(cur.getColumnIndex("name")));
                cur.moveToNext();
                cityList.add(CatList);
                Log.v("catlist===", String.valueOf(CatList));
            }
        }
        cur.close();
        return cityList;
    }

    public static ArrayList<HashMap<String, String>> getDistrict(String id) {
        ArrayList<HashMap<String, String>> cityList = new ArrayList<HashMap<String, String>>();
        cityList.clear();
        Cursor cur = myDataBase.rawQuery("Select * from districts" + " WHERE state_id" + "='" + id + "'", null);

        Log.d("cur.getCount()", String.valueOf(cur.getCount()));
        if (cur != null && cur.moveToNext()) {
            for (int i = 0; i < cur.getCount(); i++) {
                HashMap CatList = new HashMap<>();
                Log.v("databaseFetch===", cur.getString(cur.getColumnIndex("id")));
                CatList.put("id", cur.getString(cur.getColumnIndex("id")));
                CatList.put("name", cur.getString(cur.getColumnIndex("name")));
                CatList.put("state_id", cur.getString(cur.getColumnIndex("state_id")));
                cur.moveToNext();
                cityList.add(CatList);
                Log.v("catlist===", String.valueOf(CatList));
            }
        }
        cur.close();
        return cityList;
    }

    public static ArrayList<HashMap<String, String>> getCities(String id) {
        ArrayList<HashMap<String, String>> cityList = new ArrayList<HashMap<String, String>>();
        cityList.clear();
        Cursor cur = myDataBase.rawQuery("Select * from cities" + " WHERE district_id" + "='" + id + "'", null);

        Log.d("cur.getCount()", String.valueOf(cur.getCount()));
        if (cur != null && cur.moveToNext()) {
            for (int i = 0; i < cur.getCount(); i++) {
                HashMap CatList = new HashMap<>();
                Log.v("databaseFetch===", cur.getString(cur.getColumnIndex("id")));
                CatList.put("id", cur.getString(cur.getColumnIndex("id")));
                CatList.put("name", cur.getString(cur.getColumnIndex("name")));
                CatList.put("state_id", cur.getString(cur.getColumnIndex("state_id")));
                CatList.put("district_id", cur.getString(cur.getColumnIndex("district_id")));
                CatList.put("other", cur.getString(cur.getColumnIndex("other")));
                cur.moveToNext();
                cityList.add(CatList);
                Log.v("catlist===", String.valueOf(CatList));
            }
        }
        cur.close();
        return cityList;
    }

    public static ArrayList<HashMap<String, String>> getVillages(String id) {
        ArrayList<HashMap<String, String>> cityList = new ArrayList<HashMap<String, String>>();
        cityList.clear();
        Cursor cur = myDataBase.rawQuery("Select * from villages" + " WHERE district_id" + "='" + id + "'", null);

        Log.d("cur.getCount()", String.valueOf(cur.getCount()));
        if (cur != null && cur.moveToNext()) {
            for (int i = 0; i < cur.getCount(); i++) {
                HashMap CatList = new HashMap<>();
                Log.v("databaseFetch===", cur.getString(cur.getColumnIndex("id")));
                CatList.put("id", cur.getString(cur.getColumnIndex("id")));
                CatList.put("name", cur.getString(cur.getColumnIndex("name")));
                CatList.put("state_id", cur.getString(cur.getColumnIndex("state_id")));
                CatList.put("district_id", cur.getString(cur.getColumnIndex("district_id")));
                CatList.put("other", cur.getString(cur.getColumnIndex("other")));
                cur.moveToNext();
                cityList.add(CatList);
                Log.v("catlist===", String.valueOf(CatList));
            }
        }
        cur.close();
        return cityList;
    }

    public static ArrayList<HashMap<String, String>> getTehsils(String id) {
        ArrayList<HashMap<String, String>> cityList = new ArrayList<HashMap<String, String>>();
        cityList.clear();
        Cursor cur = myDataBase.rawQuery("Select * from tehsils" + " WHERE district_id" + "='" + id + "'", null);

        Log.d("cur.getCount()", String.valueOf(cur.getCount()));
        if (cur != null && cur.moveToNext()) {
            for (int i = 0; i < cur.getCount(); i++) {
                HashMap CatList = new HashMap<>();
                Log.v("databaseFetch===", cur.getString(cur.getColumnIndex("id")));
                CatList.put("id", cur.getString(cur.getColumnIndex("id")));
                CatList.put("name", cur.getString(cur.getColumnIndex("name")));
                CatList.put("state_id", cur.getString(cur.getColumnIndex("state_id")));
                CatList.put("district_id", cur.getString(cur.getColumnIndex("district_id")));
                CatList.put("other", cur.getString(cur.getColumnIndex("other")));
                cur.moveToNext();
                cityList.add(CatList);
                Log.v("catlist===", String.valueOf(CatList));
            }
        }
        cur.close();
        return cityList;
    }

}

