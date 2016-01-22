package net.guillemc.futuredays;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class ItemActivity extends SingleFragmentActivity {

    public static final String EXTRA_ITEM_ID = "item_id";

    public static Intent newIntent(Context packageContext, Long itemId) {
        Intent intent = new Intent(packageContext, ItemActivity.class);
        intent.putExtra(EXTRA_ITEM_ID, itemId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Long itemId = (Long) getIntent().getSerializableExtra(EXTRA_ITEM_ID);
        return ItemFragment.newInstance(itemId);
    }


}
