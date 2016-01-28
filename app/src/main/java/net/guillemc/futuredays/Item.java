package net.guillemc.futuredays;

import java.util.Locale;
import java.util.TimeZone;

import hirondelle.date4j.DateTime;

public class Item {
    public static String TAG = "Item";

    private Long mId;
    private String mTitle;
    private String mDetails;
    private DateTime mDate;
    private boolean mAutodelete;
    private int mLevel;
    private static DateTime today;

    public Item(long id) {
        this();
        mId = id;
    }

    public Item() {
        today = DateTime.today(TimeZone.getDefault());
        mDate = today;
    }

    public boolean isAutodelete() {
        return mAutodelete;
    }

    public void setAutodelete(boolean autodelete) {
        mAutodelete = autodelete;
    }

    public void setAutodelete(int autodelete) {
        mAutodelete = autodelete != 0;
    }

    public DateTime getDate() {
        return mDate;
    }

    public void setDate(DateTime date) {
        mDate = date;
    }

    public void setDate(String d) {
        mDate = new DateTime(d);
    }

    public String getDate(String fmt) {
        return mDate.format(fmt);
    }

    public String getLocalizedDate() {
        String fmt = "WWW, DD MMM YYYY";
        return mDate.format(fmt, Locale.getDefault());
    }

    public String getDetails() {
        return mDetails;
    }

    public void setDetails(String details) {
        mDetails = details;
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = new Long(id);
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int level) {
        mLevel = level;
    }

    public int getDayDiff() {
        return today.numDaysFrom(mDate);
    }
}
