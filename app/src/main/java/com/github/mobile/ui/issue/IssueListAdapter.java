package com.github.mobile.ui.issue;

import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.github.kevinsawicki.wishlist.ViewUtils;
import com.github.mobile.R;
import com.github.mobile.ui.StyledText;
import com.github.mobile.util.AvatarLoader;
import com.github.mobile.util.TypefaceUtils;

import org.eclipse.egit.github.core.Label;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.graphics.Paint.STRIKE_THRU_TEXT_FLAG;
import static org.eclipse.egit.github.core.service.IssueService.STATE_CLOSED;

/**
 * Base list adapter to display issues
 *
 * @param <V>
 */
public abstract class IssueListAdapter<V> extends SingleTypeAdapter<V> {

    /**
     * Maximum number of label bands to display
     */
    protected static final int MAX_LABELS = 8;

    /**
     * Context resources
     */
    protected final Resources resources;

    /**
     * Avatar loader
     */
    protected final AvatarLoader avatars;

    /**
     * View containing the issue number
     */
    private final TextView numberView;

    /**
     * Width of widest issue number
     */
    private int numberWidth;

    /**
     * @param viewId
     * @param inflater
     * @param elements
     * @param avatars
     */
    public IssueListAdapter(int viewId, LayoutInflater inflater,
            Resources resources, Object[] elements, AvatarLoader avatars) {
        super(inflater, viewId);

        this.resources = resources;
        this.avatars = avatars;
        numberView = (TextView) inflater.inflate(viewId, null).findViewById(
                R.id.tv_issue_number);
        setItems(elements);
    }

    /**
     * Get number of issue
     *
     * @param issue
     * @return issue number
     */
    protected abstract int getNumber(V issue);

    @SuppressWarnings("unchecked")
    private void computeNumberWidth(final Object[] items) {
        int[] numbers = new int[items.length];
        for (int i = 0; i < numbers.length; i++)
            numbers[i] = getNumber((V) items[i]);
        int digits = Math.max(TypefaceUtils.getMaxDigits(numbers), 4);
        numberWidth = TypefaceUtils.getWidth(numberView, digits)
                + numberView.getPaddingLeft() + numberView.getPaddingRight();
    }

    @Override
    public void setItems(final Object[] items) {
        super.setItems(items);

        computeNumberWidth(items);
    }

    /**
     * Update issue number displayed in given text view
     *
     *
     * @param number
     * @param state
     * @param flags
     * @param viewIndex
     */
    protected void updateNumber(int number, String state, int flags,
            int viewIndex) {
        TextView view = textView(viewIndex);
        view.setText(Integer.toString(number));
        if (STATE_CLOSED.equals(state))
            view.setPaintFlags(flags | STRIKE_THRU_TEXT_FLAG);
        else
            view.setPaintFlags(flags);
        view.getLayoutParams().width = numberWidth;
    }

    /**
     * Update reporter details in given text view
     *
     *
     * @param reporter
     * @param date
     * @param viewIndex
     */
    protected void updateReporter(String reporter, Date date, int viewIndex) {
        StyledText reporterText = new StyledText();
        reporterText.bold(reporter);
        reporterText.append(' ');
        reporterText.append(date);
        setText(viewIndex, reporterText);
    }

    /**
     * Update label views with values from given label models
     *
     * @param labels
     * @param viewIndex
     */
    protected void updateLabels(final List<Label> labels, final int viewIndex) {
        if (labels != null && !labels.isEmpty()) {
            int size = Math.min(labels.size(), MAX_LABELS);
            for (int i = 0; i < size; i++) {
                String color = labels.get(i).getColor();
                if (!TextUtils.isEmpty(color)) {
                    View view = view(viewIndex + i);
                    view.setBackgroundColor(Color.parseColor('#' + color));
                    ViewUtils.setGone(view, false);
                } else
                    setGone(viewIndex + i, true);
            }
            for (int i = size; i < MAX_LABELS; i++)
                setGone(viewIndex + i, true);
        } else
            for (int i = 0; i < MAX_LABELS; i++)
                setGone(viewIndex + i, true);
    }

    /**
     * Update label views with values from given label models
     *
     * @param labels
     * @param viewIndex
     */
    protected void updateLabelsWithNewModel(final List<com.github.mobile.api.model.Label> labels, final int viewIndex) {
        if (labels == null || labels.isEmpty()) {
            updateLabels(null, viewIndex);
            return;
        }

        List<Label> oldModelLabels = new ArrayList<>(labels.size());
        Label newLabel;
        for (com.github.mobile.api.model.Label l : labels) {
            newLabel = new Label();
            newLabel.setColor(l.color);
            oldModelLabels.add(newLabel);
        }
        updateLabels(oldModelLabels, viewIndex);
    }
}
