package net.guillemc.futuredays;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import net.guillemc.futuredays.database.DatabaseHelper;
import net.guillemc.futuredays.database.ItemCursorWrapper;
import net.guillemc.futuredays.database.ItemSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import hirondelle.date4j.DateTime;

public class ItemManager {
    public static String TAG = "ItemManager";

    private static ItemManager sItemManager;

    private SQLiteDatabase mDatabase;


    public static ItemManager get(Context ctx) {
        if (sItemManager == null) {
            sItemManager = new ItemManager(ctx);
        }
        return sItemManager;
    }

    private ItemManager(Context ctx) {
        mDatabase = new DatabaseHelper(ctx.getApplicationContext()).getWritableDatabase();
    }

    public List<Item> getItems(boolean future) {
        String where = ItemSchema.DATE + (future ? " >= ?" : " < ?");
        String[] params = { DateTime.today(TimeZone.getDefault()).format("YYYY-MM-DD") };
        ItemCursorWrapper cursor = new ItemCursorWrapper(mDatabase.query(ItemSchema.TBL,
                null,   // all columns
                where,  // where
                params, // args
                null,   // groupBy
                null,   // having
                ItemSchema.DATE + ", " + ItemSchema.LEVEL + " DESC" // orderBy
        ));
        return getList(cursor);
    }

    public List<Item> getItems() {
        return getItems(true);
    }

    @Nullable
    public Item getItem(long id) {
        String where = "_id = ?";
        String[] params = {Long.toString(id)};
        ItemCursorWrapper cursor = new ItemCursorWrapper(mDatabase.query(ItemSchema.TBL,
                null,
                where,
                params,
                null,
                null,
                null
        ));
        return getItem(cursor);
    }

    public void deleteItem(long id) {
        String[] params = {Long.toString(id)};
        mDatabase.delete(ItemSchema.TBL, "_id = ?", params);
    }

    public void deleteItem(Item i) {
        if (!i.isNew()) {
            deleteItem(i.getId());
        }
    }

    public void updateItem(Item i) {
        ContentValues cv = ItemSchema.getContentValues(i);
        String[] params = { i.getId().toString() };
        mDatabase.update(ItemSchema.TBL, cv, "_id = ?", params);
    }

    public void createItem(Item i) {
        ContentValues cv = ItemSchema.getContentValues(i);
        long id = mDatabase.insert(ItemSchema.TBL, null, cv);
        if (id > 0) {
            i.setId(id);
        } else {
            Log.e(TAG, "Insert item failed");
        }
    }

    public void saveItem(Item i) {
        if (i.getId() == null) {
            createItem(i);
        } else {
            updateItem(i);
        }
    }

    public void deleteOldItems() {
        String[] params = { DateTime.today(TimeZone.getDefault()).format("YYYY-MM-DD") };
        mDatabase.delete(ItemSchema.TBL, String.format("%s = 1 AND %s < ?", ItemSchema.AUTODEL, ItemSchema.DATE), params);
    }

    private List<Item> getList(ItemCursorWrapper cursor) {
        List<Item> l = new ArrayList<>();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                l.add(cursor.getItem());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return l;
    }

    @Nullable
    private Item getItem(ItemCursorWrapper cursor) {
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getItem();
        } finally {
            cursor.close();
        }
    }





}
