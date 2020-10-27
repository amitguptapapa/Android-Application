package com.vis.android.Database;

/**
 * Created by ankita-pc on 20/3/18.
 */

public class TableOtherThanFlats {
    static final String SQL_CREATE_DATA = ("CREATE TABLE flatattachment (" +
            OtherThanFlatsColumn.data + " VARCHAR" + ")");

    public static final String flatattachment = "flatattachment";
    public enum OtherThanFlatsColumn {
        data
    }
}
