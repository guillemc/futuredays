package net.guillemc.futuredays.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ItemBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DBNAME = "items.db";

    public ItemBaseHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ItemSchema.TBL + "(_id INTEGER PRIMARY KEY" +
                        ", " + ItemSchema.DATE + " TEXT" +
                        ", " + ItemSchema.TITLE + " TEXT" +
                        ", " + ItemSchema.DETAILS + " TEXT" +
                        ", " + ItemSchema.AUTODEL + " INTEGER" +
                        ", " + ItemSchema.LEVEL + " INTEGER" +
                        ")");
        db.execSQL("INSERT INTO " + ItemSchema.TBL + "(" + ItemSchema.DATE +
                        ", " + ItemSchema.TITLE +
                        ", " + ItemSchema.DETAILS +
                        ", " + ItemSchema.AUTODEL +
                        ", " + ItemSchema.LEVEL +
                        ") VALUES ('2016-04-01', 'Dentist', '10:30 - keep calm', 1, 1), " +
                                "('2016-05-12', 'Change toothbrush', 'preferably a yellow one', 1, 1), " +
                                "('2016-06-04', 'Meeting with K', 'bring a gun', 1, 1)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
