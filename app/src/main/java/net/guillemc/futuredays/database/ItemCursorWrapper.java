package net.guillemc.futuredays.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import net.guillemc.futuredays.Item;

public class ItemCursorWrapper extends CursorWrapper {



    public ItemCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Item getItem() {
        long id = getLong(getColumnIndex("_id"));
        String date = getString(getColumnIndex(ItemSchema.DATE));
        String title = getString(getColumnIndex(ItemSchema.TITLE));
        String details = getString(getColumnIndex(ItemSchema.DETAILS));
        int autoDel = getInt(getColumnIndex(ItemSchema.AUTODEL));
        int level = getInt(getColumnIndex(ItemSchema.LEVEL));

        Item item = new Item(id);
        item.setDate(date);
        item.setTitle(title);
        item.setDetails(details);
        item.setAutodelete(autoDel);
        item.setLevel(level);
        return item;
    }


}
