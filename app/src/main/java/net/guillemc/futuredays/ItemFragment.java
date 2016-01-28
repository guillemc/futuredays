package net.guillemc.futuredays;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class ItemFragment extends Fragment {

    public static final String TAG = "item_fragment";
    public static final String ARG_ITEM_ID = "item_id";

    private Item mItem;

    private EditText mTitle;
    private EditText mDetails;
    private Button mDate;
    private CheckBox mAutoDelete;


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
        mTitle = (EditText) v.findViewById(R.id.item_title);
        mTitle.setText(mItem.getTitle());

        mDetails = (EditText) v.findViewById(R.id.item_details);
        mDetails.setText(mItem.getDetails());

        mDate = (Button) v.findViewById(R.id.item_date);
        mDate.setText(mItem.getLocalizedDate());

        mAutoDelete = (CheckBox) v.findViewById(R.id.item_autodelete);
        mAutoDelete.setChecked(mItem.isAutodelete());

        return v;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        Log.d(TAG, "onViewStateRestored : " + mTitle.getText());
    }
}
