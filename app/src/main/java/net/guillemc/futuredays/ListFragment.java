package net.guillemc.futuredays;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private View mEmptyView;
    private ItemAdapter mAdapter;

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
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        ItemManager mgr = ItemManager.get(getActivity());
        List<Item> items = mgr.getItems();

        boolean isEmpty = items.size() <= 0;
        mRecyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        mEmptyView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);

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
            mDateView.setText(mItem.getDate().toString());
            mRelDateView.setText(mItem.getDate().toString());
        }

        public void onClick(View v) {

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
