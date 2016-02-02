package net.guillemc.futuredays;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import net.guillemc.futuredays.helpers.ConfirmFragment;
import net.guillemc.futuredays.helpers.DatePickerFragment;

import hirondelle.date4j.DateTime;

public class ItemFragment extends Fragment implements DatePickerFragment.OnDatePickedListener {

    public static final String TAG = "item_fragment";
    public static final String ARG_ITEM_ID = "item_id";

    private Item mItem;

    private EditText mTitle;
    private EditText mDetails;
    private Button mDate;
    private CheckBox mAutoDelete;

    private Button mSave;
    private Button mCancel;
    private Button mDelete;

    private Callbacks mCallbacks;

    /*
     * The hosting activity needs to implement this interface
     */
    public interface Callbacks {
        void onSaveItem(Item item);
        void onDeleteItem(Item item);
        void onCancel(Item item);
    }


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
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
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

        mCancel = (Button) v.findViewById(R.id.btn_cancel);
        mSave = (Button) v.findViewById(R.id.btn_save);
        mDelete = (Button) v.findViewById(R.id.btn_delete);
        mDelete.setVisibility(mItem.isNew() ? View.GONE : View.VISIBLE);

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onCancel(mItem);
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
                mCallbacks.onSaveItem(mItem);
            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete();
            }
        });

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment f = DatePickerFragment.newInstance(mItem.getDate(), ItemFragment.this);
                f.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("itemDate", mItem.getDate());
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey("itemDate")) {
            DateTime itemDate = (DateTime) savedInstanceState.getSerializable("itemDate");
            mItem.setDate(itemDate);
            mDate.setText(mItem.getLocalizedDate());
        }
        Log.d(TAG, "onViewStateRestored : " + mItem.getDate("YYYY-MM-DD"));
    }

    @Override
    public void onDateSelected(DateTime date) {
        mItem.setDate(date);
        mDate.setText(mItem.getLocalizedDate());
    }

    private void confirmDelete() {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteItem();
                mCallbacks.onDeleteItem(mItem);
            }
        };
        ConfirmFragment dialog = ConfirmFragment.newInstance(R.string.confirm_delete, listener);
        dialog.show(getActivity().getSupportFragmentManager(), "deleteConfirmation");
    }

    private void saveItem() {
        mItem.setTitle(mTitle.getText().toString());
        mItem.setDetails(mDetails.getText().toString());
        // date is already set
        mItem.setAutodelete(mAutoDelete.isChecked());
        ItemManager.get(getActivity()).saveItem(mItem);
    }

    private void deleteItem() {
        ItemManager.get(getActivity()).deleteItem(mItem);
    }
}
