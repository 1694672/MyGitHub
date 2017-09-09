package com.github.mobile.ui.notification;

import android.accounts.Account;
import android.net.Uri;
import android.view.View;
import android.widget.ListView;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.github.mobile.R;
import com.github.mobile.accounts.AuthenticatedUserTask;
import com.github.mobile.api.model.Notification;
import com.github.mobile.api.model.Subject;
import com.github.mobile.api.service.NotificationService;
import com.github.mobile.ui.NewPagedItemFragment;
import com.github.mobile.ui.UriLauncherActivity;
import com.google.inject.Inject;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import okhttp3.ResponseBody;

/**
 * Fragment to display a list of {@link Notification} objects
 */
public class NotificationsListFragment extends NewPagedItemFragment<Notification> {

    @Inject
    private NotificationService service;

    public NotificationsListFragment() {
        super(R.string.no_notifications, R.string.loading_notifications, R.string.error_notifications_load);
    }

    @Override
    protected Object getResourceId(Notification resource) {
        return resource.id;
    }

    @Override
    protected Collection<Notification> getPage(int page, int itemsPerPage) throws IOException {
        return service.listNotifications(true, page).execute().body();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        final Notification notification = (Notification) l.getItemAtPosition(position);

        // Create an URL we can open
        String url = notification.subject.url;
        if (url == null) {
            return;
        }

        url = url.replace("://api.github.com/repos/", "://github.com/");
        switch (notification.subject.type) {
        case Subject.TYPE_ISSUE:
            UriLauncherActivity.launchUri(getContext(), Uri.parse(url));
            break;
        case Subject.TYPE_PULL_REQUEST:
            url = url.replace("/pulls/", "/pull/");
            UriLauncherActivity.launchUri(getContext(), Uri.parse(url));
            break;
        case Subject.TYPE_COMMIT:
            url = url.replace("/commits/", "/commit/");
            UriLauncherActivity.launchUri(getContext(), Uri.parse(url));
            break;
        default:
            break;
        }

        // Mark notification as read
        if (notification.is_unread) {
            new AuthenticatedUserTask<ResponseBody>(getActivity()) {

                @Override
                protected ResponseBody run(Account account) throws Exception {
                    return service.markAsRead(notification.id).execute().body();
                }

                @Override
                protected void onSuccess(ResponseBody responseBody) throws Exception {
                    notification.is_unread = false;
                    notifyDataSetChanged();
                }
            }.execute();
        }
    }

    @Override
    protected SingleTypeAdapter<Notification> createAdapter(List<Notification> items) {
        return new NotificationsListAdapter(getActivity(),
                items.toArray(new Notification[items.size()]));
    }
}
