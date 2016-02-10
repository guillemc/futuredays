package net.guillemc.futuredays;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private View mEmptyView;
    private ItemAdapter mAdapter;
    private boolean mFuture = true;

    public static final String ARG_FUTURE = "future";


    private Callbacks mCallbacks; // this corresponds to the hosting activity

    /*
     * The hosting activity needs to implement this interface
     */
    public interface Callbacks {
        void onListItemSelect(Item item);
    }

    public static ListFragment newInstance(boolean future) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_FUTURE, future);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_FUTURE)) {
            mFuture = args.getBoolean(ARG_FUTURE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        mEmptyView = view.findViewById(R.id.empty_list_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final Button addItem = (Button) view.findViewById(R.id.add_item);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_item:
                Intent intent = ItemActivity.newIntent(getActivity(), null);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        ItemManager mgr = ItemManager.get(getActivity());
        List<Item> items = mgr.getItems(mFuture);

        boolean isEmpty = items.size() <= 0;

        if (isEmpty) {
            mEmptyView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            TextView tv = (TextView) mEmptyView.findViewById(R.id.empty_text);
            Button b = (Button) mEmptyView.findViewById(R.id.add_item);
            if (mFuture) {
                tv.setText(getResources().getText(R.string.empty_list));
                b.setVisibility(View.VISIBLE);
            } else {
                tv.setText(getResources().getText(R.string.empty_list_past));
                b.setVisibility(View.GONE);
            }
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        }

        if (mAdapter == null) {
            mAdapter = new ItemAdapter(items);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(items);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void addItem() {

    }



    private class ItemHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Item mItem;
        private TextView mTitleView;
        private TextView mDateView;
        private TextView mRelDateView;

        public ItemHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mTitleView = (TextView) itemView.findViewById(R.id.list_item_title);
            mDateView = (TextView) itemView.findViewById(R.id.list_item_date);
            mRelDateView = (TextView) itemView.findViewById(R.id.list_item_reldate);
        }

        public void bindItem(Item item) {
            mItem = item;
            mTitleView.setText(mItem.getTitle());
            mDateView.setText(mItem.getLocalizedDate());
            String relDate;
            int days = mItem.getDayDiff();
            if (days == 0) {
                relDate = getResources().getString(R.string.today);
            } else if (days == 1) {
                relDate = getResources().getString(R.string.tomorrow);
            } else {
                relDate = String.format(getResources().getString(R.string.num_days), days > 0 ? "+" + days : days);
            }
            mRelDateView.setText(relDate);
        }

        public void onClick(View v) {
            mCallbacks.onListItemSelect(mItem);
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

        private List<Item> mItems;

        public ItemAdapter(List<Item> items) {
            mItems = items;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item, parent, false);
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            Item item = mItems.get(position);
            holder.bindItem(item);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public void setItems(List<Item> items) {
            mItems = items;
        }
    }
}
