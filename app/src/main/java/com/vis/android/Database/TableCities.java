package com.vis.android.Database;

/**
 * Created by mohammadshiblee on 03/04/18.
 */

public class TableCities {

    static final String SQL_CREATE_DATA = ("CREATE TABLE cities ("
            + citiesColumn.id + " INTEGER PRIMARY KEY,"
            + citiesColumn.state_id + " VARCHAR,"
            + citiesColumn.district_id + " VARCHAR,"
            + citiesColumn.other + " VARCHAR,"
            + citiesColumn.name + " VARCHAR" + ")");

    public static final String attachment = "cities";

    public enum citiesColumn {
        //data,
        id,
        state_id,
        district_id,
        other,
        name
    }
}