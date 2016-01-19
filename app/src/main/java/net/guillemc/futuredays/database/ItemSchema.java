package net.guillemc.futuredays.database;

import android.content.ContentValues;

import net.guillemc.futuredays.Item;

public class ItemSchema {
    public static String TBL = "items";
    public static String DATE = "date";
    public static String TITLE = "title";
    public static String DETAILS = "details";
    public static String AUTODEL = "autodelete";
    public static String LEVEL = "level";

    public static ContentValues getContentValues(Item item) {
        ContentValues cv = new ContentValues();
        cv.put("_id", item.getId());
        cv.put(DATE, item.getDateString());
        cv.put(TITLE, item.getTitle());
        cv.put(DETAILS, item.getDetails());
        cv.put(AUTODEL, item.isAutodelete());
        cv.put(LEVEL, item.getLevel());
        return cv;
    }

}
