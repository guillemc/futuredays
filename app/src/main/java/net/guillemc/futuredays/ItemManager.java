package net.guillemc.futuredays;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import net.guillemc.futuredays.database.ItemBaseHelper;
import net.guillemc.futuredays.database.ItemCursorWrapper;
import net.guillemc.futuredays.database.ItemSchema;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemManager {
    public static String TAG = "ItemManager";

    private static ItemManager sItemManager;

    private Context mContext;
    private SQLiteDatabase mDatabase;


    public static ItemManager get(Context ctx) {
        if (sItemManager == null) {
            sItemManager = new ItemManager(ctx);
        }
        return sItemManager;
    }

    private ItemManager(Context ctx) {
        mContext = ctx.getApplicationContext();
        mDatabase = new ItemBaseHelper(mContext).getWritableDatabase();
    }

    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        String where = ItemSchema.DATE + " >= ?";
        String[] params = { Item.dateToString(new Date()) };
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

    @Nullable
    public Item getItem(long id) {
        String where = "_id = ?";
        String[] params = { new Long(id).toString() };
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
        String[] params = { new Long(id).toString() };
        mDatabase.delete(ItemSchema.TBL, "_id = ?", params);
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

    public void deleteOldItems() {
        String[] params = { Item.dateToString(new Date()) };
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
