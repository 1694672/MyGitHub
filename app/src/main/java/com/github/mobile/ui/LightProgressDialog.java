package com.github.mobile.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.github.mobile.R;


/**
 * Progress dialog in Holo Light theme
 */
public class LightProgressDialog extends ProgressDialog {

    /**
     * Create progress dialog
     *
     * @param context
     * @param resId
     * @return dialog
     */
    public static AlertDialog create(Context context, int resId) {
        return create(context, context.getResources().getString(resId));
    }

    /**
     * Create progress dialog
     *
     * @param context
     * @param message
     * @return dialog
     */
    public static AlertDialog create(Context context, CharSequence message) {
        ProgressDialog dialog = new LightProgressDialog(context, message);
        dialog.setMessage(message);
        dialog.setIndeterminate(true);
        dialog.setProgressStyle(STYLE_SPINNER);
        dialog.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.spinner));
        return dialog;
    }

    private LightProgressDialog(Context context, CharSequence message) {
        super(context, THEME_HOLO_LIGHT);
    }
}
