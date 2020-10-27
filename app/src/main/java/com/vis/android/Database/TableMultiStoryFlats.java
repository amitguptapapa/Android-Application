package com.vis.android.Database;

/**
 * Created by ankita-pc on 14/3/18.
 */

public class TableMultiStoryFlats {

    static final String SQL_CREATE_Stories = ("CREATE TABLE multistory (" +
            cityCatColumn.data + " VARCHAR" + ")");

    public static final String  MultiStory = "multistory";

    public enum cityCatColumn {
        data
    }
}
