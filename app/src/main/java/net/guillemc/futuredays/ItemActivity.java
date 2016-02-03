package net.guillemc.futuredays;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import net.guillemc.futuredays.helpers.SingleFragmentActivity;

public class ItemActivity extends SingleFragmentActivity implements ItemFragment.Callbacks {

    public static final String EXTRA_ITEM_ID = "item_id";

    public static Intent newIntent(Context packageContext, Long itemId) {
        Intent intent = new Intent(packageContext, ItemActivity.class);
        if (itemId != null) {
            intent.putExtra(EXTRA_ITEM_ID, itemId);
        }
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ITEM_ID)) {
            Long itemId = (Long) getIntent().getSerializableExtra(EXTRA_ITEM_ID);
            return ItemFragment.newInstance(itemId);
        }
        return new ItemFragment();
    }

    @Override
    public void onSaveItem(Item item) {
        finish();
    }

    @Override
    public void onDeleteItem(Item item) {
        finish();
    }

    @Override
    public void onCancel(Item item) {
        finish();
    }
}
