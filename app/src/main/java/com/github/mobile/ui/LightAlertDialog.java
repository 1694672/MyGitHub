package com.github.mobile.ui;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Alert dialog using the Holo Light theme
 */
public class LightAlertDialog extends AlertDialog {

    /**
     * Create alert dialog
     *
     * @param context
     * @return dialog
     */
    public static AlertDialog create(final Context context) {
        return new LightAlertDialog(context, THEME_HOLO_LIGHT);
    }

    private LightAlertDialog(final Context context, final int theme) {
        super(context, theme);
    }

    private LightAlertDialog(final Context context) {
        super(context);
    }

    /**
     * Alert dialog builder using the Holo Light theme
     */
    public static class Builder extends AlertDialog.Builder {

        /**
         * Create alert dialog builder
         *
         * @param context
         * @return dialog builder
         */
        public static LightAlertDialog.Builder create(final Context context) {
            return new LightAlertDialog.Builder(context, THEME_HOLO_LIGHT);
        }

        private Builder(Context context) {
            super(context);
        }

        private Builder(Context context, int theme) {
           super(context, theme);
        }
    }
}
