package net.guillemc.futuredays.database;

import android.content.ContentValues;

import net.guillemc.futuredays.Item;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

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
        cv.put(DATE, item.getDate().toString());
        cv.put(TITLE, item.getTitle());
        cv.put(DETAILS, item.getDetails());
        cv.put(AUTODEL, item.isAutodelete());
        cv.put(LEVEL, item.getLevel());
        return cv;
    }

    public static LocalDate strToDate(String str) {
        DateTimeFormatter fmt = ISODateTimeFormat.date();
        return fmt.parseLocalDate(str);
    }

}
