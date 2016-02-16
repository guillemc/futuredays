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
        FragmentManager fm = getSupportFragmentManager();
        PagerFragment pager = (PagerFragment) fm.findFragmentById(R.id.fragment_container);
        if (pager != null) {
            // Get access to the current ListFragment being displayed, and update it.
            // Hacky, as it relies on the internal naming used by ViewPager
            // http://stackoverflow.com/questions/8785221/retrieve-a-fragment-from-a-viewpager
            int current = pager.getViewPager().getCurrentItem();
            String tag = "android:switcher:" + R.id.viewpager + ":" + current;
            ListFragment list = (ListFragment) fm.findFragmentByTag(tag);
            if (list != null) {
                list.updateUI();
            }
        }
    }

    protected void removeDetailFragment() {
        FragmentManager fm = getSupportFragmentManager();
        ItemFragment detail = (ItemFragment) fm.findFragmentById(R.id.detail_fragment_container);
        if (detail != null) {
            fm.beginTransaction().remove(detail).commit();
        }
    }
}
