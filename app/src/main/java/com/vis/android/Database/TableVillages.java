package com.vis.android.Database;

/**
 * Created by mohammadshiblee on 03/04/18.
 */

public class TableVillages {

    static final String SQL_CREATE_DATA = ("CREATE TABLE villages ("
            + villagesColumn.id + " INTEGER PRIMARY KEY,"
            + villagesColumn.state_id + " VARCHAR,"
            + villagesColumn.district_id + " VARCHAR,"
            + villagesColumn.other + " VARCHAR,"
            + villagesColumn.name + " VARCHAR" + ")");

    public static final String attachment = "villages";

    public enum villagesColumn {
        id,
        state_id,
        district_id,
        other,
        name
    }
}