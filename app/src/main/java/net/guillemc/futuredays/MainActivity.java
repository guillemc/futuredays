package net.guillemc.futuredays;

import android.support.v4.app.Fragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

}
