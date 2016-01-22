package net.guillemc.futuredays;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class MainActivity extends SingleFragmentActivity implements ListFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return new ListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    public void onListItemSelect(Item item) {
        Intent intent = ItemActivity.newIntent(this, item.getId());
        startActivity(intent);
    }
}
