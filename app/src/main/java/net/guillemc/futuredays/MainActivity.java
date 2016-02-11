package net.guillemc.futuredays;

import android.content.Intent;
import android.support.v4.app.Fragment;

import net.guillemc.futuredays.helpers.SingleFragmentActivity;

public class MainActivity extends SingleFragmentActivity implements ListFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return new PagerFragment();
    }

    @Override
    public void onListItemSelect(Item item) {
        Intent intent = ItemActivity.newIntent(this, item.getId());
        startActivity(intent);
    }

    @Override
    public void onAddNewPressed() {
        Intent intent = ItemActivity.newIntent(this, null);
        startActivity(intent);
    }


}
