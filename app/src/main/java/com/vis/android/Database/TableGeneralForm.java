package com.vis.android.Database;

/**
 * Created by mohammadshiblee on 03/04/18.
 */

public class TableGeneralForm {

    static final String SQL_CREATE_DATA = ("CREATE TABLE generalform ("
           // + generalFormColumn.data + " VARCHAR,"
            + generalFormColumn.id + " INTEGER PRIMARY KEY,"
            + generalFormColumn.column1 + " VARCHAR,"
            + generalFormColumn.column2 + " VARCHAR,"
            + generalFormColumn.column3 + " VARCHAR,"
            + generalFormColumn.column4 + " VARCHAR,"
            + generalFormColumn.column5 + " VARCHAR,"
            + generalFormColumn.column6 + " VARCHAR,"
            + generalFormColumn.column7 + " VARCHAR,"
            + generalFormColumn.column8 + " VARCHAR,"
            + generalFormColumn.column9 + " VARCHAR,"
            + generalFormColumn.column10 + " VARCHAR,"
            + generalFormColumn.column11 + " VARCHAR,"
            + generalFormColumn.column12 + " VARCHAR,"
            + generalFormColumn.column13 + " VARCHAR,"
            + generalFormColumn.id_new + " VARCHAR,"
            + generalFormColumn.column_new + " VARCHAR"
            + ")");

    public static final String attachment = "generalform";

    public enum generalFormColumn {
        //data,
        id,
        column1,
        column2,
        column3,
        column4,
        column5,
        column6,
        column7,
        column8,
        column9,
        column10,
        column11,
        column12,
        column13,
        id_new,
        column_new
    }
}