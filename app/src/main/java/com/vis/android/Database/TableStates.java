package com.vis.android.Database;

/**
 * Created by mohammadshiblee on 03/04/18.
 */

public class TableStates {

    static final String SQL_CREATE_DATA = ("CREATE TABLE states ("
            + statesColumn.id + " INTEGER PRIMARY KEY,"
            + statesColumn.name + " VARCHAR" + ")");

    public static final String attachment = "states";

    public enum statesColumn {
        //data,
        id,
        name
    }
}