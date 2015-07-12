package jp.citrous.sortedlist_sample;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;

/**
 * Created by citrous on 2015/07/12.
 */
public class SelectDataDialogFragment extends DialogFragment {

    public static final String TAG = SelectDataDialogFragment.class.getName();

    public static final int OPERATION_ADD = 1;

    public static final int OPERATION_REMOVE = 2;

    private static final String OPERATION = "operation";

    private OnDataSelectListener listener;

    public static SelectDataDialogFragment newInstance(int operation, Fragment callbackFragment) {
        SelectDataDialogFragment dialog = new SelectDataDialogFragment();
        Bundle args = new Bundle();
        args.putInt(OPERATION, operation);
        dialog.setTargetFragment(callbackFragment, 0);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnDataSelectListener) getTargetFragment();
        } catch (ClassCastException e) {
            listener = null;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final int operation = getArguments().getInt(OPERATION);
        final String[] selectables = getResources().getStringArray(R.array.data_array);
        final ArrayList<SampleData> selectedDataList = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setTitle(R.string.select_add)
                .setMultiChoiceItems(R.array.data_array, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                String text = selectables[which];
                                SampleData data = new SampleData(Integer.valueOf(text), text);
                                if (isChecked) {
                                    selectedDataList.add(data);
                                } else if (selectedDataList.contains(data)) {
                                    selectedDataList.remove(data);
                                }
                            }
                        })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onDataSelected(operation, selectedDataList);
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    public interface OnDataSelectListener {
        void onDataSelected(int operation, ArrayList<SampleData> selectedDataList);
    }
}
