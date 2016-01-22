package net.guillemc.futuredays;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.ISODateTimeFormat;

public class Item {
    public static String TAG = "Item";

    private Long mId;
    private String mTitle;
    private String mDetails;
    private LocalDate mDate;
    private boolean mAutodelete;
    private int mLevel;

    public Item(long id) {
        mId = id;
        mDate = new LocalDate();
    }

    public Item() {
        mId = null;
        mDate = new LocalDate();
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

    public LocalDate getDate() {
        return mDate;
    }

    public void setDate(LocalDate date) {
        mDate = date;
    }

    public void setDate(String d) {
        mDate = ISODateTimeFormat.date().parseLocalDate(d);
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
        return Days.daysBetween(new LocalDate(), mDate).getDays();
    }
}
