package net.guillemc.futuredays.helpers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ConfirmFragment extends DialogFragment {

    private int mTitleId; // id of the string resource to be used as the dialog title
    private int mOk;
    private int mCancel;

    private DialogInterface.OnClickListener mListener;

    public static ConfirmFragment newInstance(int titleId, DialogInterface.OnClickListener listener) {
        ConfirmFragment fragment = new ConfirmFragment();
        fragment.mTitleId = titleId;
        fragment.mListener = listener;
        fragment.mOk = android.R.string.ok;
        fragment.mCancel = android.R.string.cancel;
        return fragment;
    }

    public void setOkTextId(int id) {
        mOk = id;
    }

    public void setCancelTextId(int id) {
        mCancel = id;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(mTitleId)
                .setPositiveButton(mOk, mListener)
                .setNegativeButton(mCancel, null)
                .create();
    }
}
