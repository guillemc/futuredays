package net.guillemc.futuredays;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Item {
    public static String TAG = "Item";

    private Long mId;
    private String mTitle;
    private String mDetails;
    private Date mDate;
    private boolean mAutodelete;
    private int mLevel;

    public Item(long id) {
        mId = id;
        mDate = new Date();
    }

    public Item() {
        mId = null;
        mDate = new Date();
    }

    public boolean isAutodelete() {
        return mAutodelete;
    }

    public void setAutodelete(boolean autodelete) {
        mAutodelete = autodelete;
    }

    public Date getDate() {
        return mDate;
    }

    public String getDateString() {
        return dateToString(mDate);
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public void setDate(String d) {
        mDate = stringToDate(d);
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

    public static String dateToString(Date d) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(d);
    }
    public static Date stringToDate(String s) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        try {
            d = fmt.parse(s);
        } catch (ParseException e) {
            Log.e(TAG, String.format("Can't parse '%s' into date: ", s), e);
        }
        return d;
    }
}
