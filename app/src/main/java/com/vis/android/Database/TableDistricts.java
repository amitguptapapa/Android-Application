package com.vis.android.Database;

/**
 * Created by mohammadshiblee on 03/04/18.
 */

public class TableDistricts {

    static final String SQL_CREATE_DATA = ("CREATE TABLE districts ("
            + districtsColumn.id + " INTEGER PRIMARY KEY,"
            + districtsColumn.state_id + " VARCHAR,"
            + districtsColumn.name + " VARCHAR" + ")");

    public static final String attachment = "districts";

    public enum districtsColumn {
        //data,
        id,
        state_id,
        name
    }
}