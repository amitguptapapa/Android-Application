package com.vis.android.Database;

/**
 * Created by mohammadshiblee on 03/04/18.
 */

public class TableTehsils {

    static final String SQL_CREATE_DATA = ("CREATE TABLE tehsils ("
            + tehsilsColumn.id + " INTEGER PRIMARY KEY,"
            + tehsilsColumn.state_id + " VARCHAR,"
            + tehsilsColumn.district_id + " VARCHAR,"
            + tehsilsColumn.other + " VARCHAR,"
            + tehsilsColumn.name + " VARCHAR" + ")");

    public static final String attachment = "tehsils";

    public enum tehsilsColumn {
        id,
        state_id,
        district_id,
        other,
        name
    }
}