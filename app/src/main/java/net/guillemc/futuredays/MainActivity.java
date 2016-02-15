package net.guillemc.futuredays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements ListFragment.Callbacks, ItemFragment.Callbacks {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_master_detail);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new PagerFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    // ListFragment Callbacks

    @Override
    public void onListItemSelect(Item item) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = ItemActivity.newIntent(this, item.getId());
            startActivity(intent);
        } else {
            Fragment detail = ItemFragment.newInstance(item.getId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, detail)
                    .commit();
        }
    }

    @Override
    public void onAddNewPressed() {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = ItemActivity.newIntent(this, null);
            startActivity(intent);
        } else {
            Fragment detail = ItemFragment.newInstance(null);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, detail)
                    .commit();
        }
    }


    // ItemFragment Callbacks

    @Override
    public void onSaveItem(Item item) {
        updateListFragment();
        Toast.makeText(this, R.string.item_saved, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteItem(Item item) {
        removeDetailFragment();
        updateListFragment();
    }

    @Override
    public void onCancel(Item item) {
        removeDetailFragment();
    }

    protected void updateListFragment() {
        PagerFragment pager = (PagerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (pager != null) {
            // get access to the current ListFragment being displayed, and update it
            //list.updateUI();
        }
    }

    protected void removeDetailFragment() {
        ItemFragment detail = (ItemFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_fragment_container);
        if (detail != null) {
            getSupportFragmentManager().beginTransaction()
                    .remove(detail)
                    .commit();
        }
    }
}
