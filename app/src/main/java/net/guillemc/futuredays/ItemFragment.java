package net.guillemc.futuredays;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class ItemFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private Item mItem;

    public static ItemFragment newInstance(Long itemId) {
        ItemFragment fragment = new ItemFragment();
        if (itemId != null) {
            Bundle args = new Bundle();
            args.putSerializable(ARG_ITEM_ID, itemId);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Long itemId = (Long) getArguments().getSerializable(ARG_ITEM_ID);

        if (itemId != null) {
            ItemManager mgr = ItemManager.get(getActivity());
            mItem = mgr.getItem(itemId);
        }

        if (mItem == null) {
            mItem = new Item();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item, container, false);
        EditText title = (EditText) v.findViewById(R.id.item_title);
        title.setText(mItem.getTitle());
        return v;
    }
}
