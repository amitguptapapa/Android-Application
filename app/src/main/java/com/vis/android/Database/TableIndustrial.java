package com.vis.android.Database;

public class TableIndustrial {

    public static final String TABLE_NAME = "industrialdynamic";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public static final String columnsno = "sno";
    public static final String columnblockname = "blockname";
    public static final String columntotalslab = "totalslab";
    public static final String columnheight = "height";
    public static final String columnyrcons = "yrcons";
    public static final String columntypecons = "typecons";
    public static final String columnstructure = "structure";
    public static final String columnarea = "area";


    private int id;
    private String timestamp;

    private String sno;
    private String blockname;
    private String totalslab;
    private String height;
    private String yrcons;
    private String typecons;
    private String structure;
    private String area;




    static final String SQL_CREATE_DATA = ("CREATE TABLE industrial (" +
            industrialColumn.data + " VARCHAR,"
            + industrialColumn.dynamic_data + " VARCHAR" + ")");

    public static final String attachment = "industrial";


    public enum industrialColumn {
        data,
        dynamic_data
    }

    ///

    public static final String SQL_CREATE_TABLE_IND_DYNAMIC =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + columnsno + " VARCHAR,"
                    + columnblockname + " VARCHAR,"
                    + columntotalslab + " VARCHAR,"
                    + columnheight + " VARCHAR,"
                    + columnyrcons + " VARCHAR,"
                    + columntypecons + " VARCHAR,"
                    + columnstructure + " VARCHAR,"
                    + columnarea + " VARCHAR,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";


    public TableIndustrial() {
    }


    public TableIndustrial(int id,String sno, String blockname, String totalslab, String height,String yrcons,String typecons,String structure,String area,String timestamp) {

        this.id = id;
        this.sno = sno;
        this.blockname = blockname;
        this.totalslab = totalslab;
        this.height = height;
        this.yrcons = yrcons;
        this.typecons = typecons;
        this.structure = structure;
        this.area = area;
        this.timestamp = timestamp;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getSNo() {
        return sno;
    }

    public void setSNo(String sno) {
        this.sno = sno;
    }
    public String getBlockName() {
        return blockname;
    }

    public void setBlockName(String blockname) {
        this.blockname = blockname;
    }
    public String getTotalSlab() {
        return totalslab;
    }

    public void setTotalSlab(String totalSlab) {
        this.totalslab = totalSlab;
    }
    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
    public String getYrCons() {
        return yrcons;
    }

    public void setYrCons(String yrCons) {
        this.yrcons = yrCons;
    }
    public String getTypeCons() {
        return typecons;
    }

    public void setTypeCons(String typecons) {
        this.typecons = typecons;
    }
    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}