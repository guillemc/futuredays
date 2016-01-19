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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
