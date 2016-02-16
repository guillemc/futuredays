package net.guillemc.futuredays;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PagerFragment extends Fragment {

    private ViewPager mPager;

    public ViewPager getViewPager() {
        return mPager;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabs_and_pager, container, false);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mPager = (ViewPager) view.findViewById(R.id.viewpager);
        mPager.setAdapter(new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager()));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mPager);

        return view;
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            return ListFragment.newInstance(position == 0 ? true : false);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getString(position == 0 ? R.string.future_items : R.string.past_items);
        }
    }

    public void getCurrentItem() {
        mPager.getCurrentItem();
    }

}
