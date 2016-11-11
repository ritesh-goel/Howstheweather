package com.rg.howstheweather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Ritesh Goel on 06-11-2016.
 */

public class AlertDialogFragment extends DialogFragment {

    Context context;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Oops,Sorry!")
                .setMessage("There was an error. Please try again.")
                .setPositiveButton("OK",null);
        AlertDialog dialog = builder.create();
        return dialog;
    }
}
