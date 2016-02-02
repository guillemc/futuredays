package net.guillemc.futuredays.helpers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import hirondelle.date4j.DateTime;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private OnDatePickedListener mListener;
    private DateTime mInitialDate;

    public interface OnDatePickedListener {
        void onDateSelected(DateTime date);
    }

    public static DatePickerFragment newInstance(DateTime initialDate, OnDatePickedListener listener) {
        DatePickerFragment dp = new DatePickerFragment();
        dp.mInitialDate = initialDate;
        dp.mListener = listener;
        return dp;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year = mInitialDate.getYear();
        int month = mInitialDate.getMonth() - 1;
        int day = mInitialDate.getDay();

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        DateTime date = DateTime.forDateOnly(year, 1 + monthOfYear, dayOfMonth);
        mListener.onDateSelected(date);
    }
}
