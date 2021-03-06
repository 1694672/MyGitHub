package com.github.mobile.core.user;

import android.content.Intent;

import com.github.mobile.core.repo.RepositoryUtils;
import com.github.mobile.ui.user.UserViewActivity;

import org.eclipse.egit.github.core.User;

import java.util.List;

import static com.github.mobile.ui.user.UserViewActivity.TAB_FOLLOWEES;
import static com.github.mobile.ui.user.UserViewActivity.TAB_FOLLOWERS;
import static com.github.mobile.ui.user.UserViewActivity.TAB_MEMBERS;
import static com.github.mobile.ui.user.UserViewActivity.TAB_STARS;
import static com.github.mobile.ui.user.UserViewActivity.TAB_TEAMS;

/**
 * Parses a {@link User} from a path
 */
public class UserUriMatcher {

    /**
     * Get the user for the given path
     *
     * @param pathSegments
     * @return {@link User} or null if path is not valid
     */
    public static User getUser(List<String> pathSegments) {
        return getUser(pathSegments, 0);
    }

    /**
     * Get an intent for an exact {@link User} match
     *
     * @param pathSegments
     * @return {@link Intent} or null if path is not valid
     */
    public static Intent getUserIntent(List<String> pathSegments) {
        if (pathSegments.size() < 1 || pathSegments.size() > 3)
            return null;

        // Organization
        if (pathSegments.size() == 3) {
            if (!"orgs".equals(pathSegments.get(0))) {
                return null;
            }

            User user = getUser(pathSegments, 1);
            if (user == null) {
                return null;
            }

            switch (pathSegments.get(2)) {
            case "people":
                return UserViewActivity.createIntent(user, TAB_MEMBERS);
            case "teams":
                return UserViewActivity.createIntent(user, TAB_TEAMS);
            default:
                return null;
            }
        }

        // User
        User user = getUser(pathSegments);

        if (pathSegments.size() == 1) {
            if (user == null)
                return null;

            return UserViewActivity.createIntent(user);
        }

        if (user != null) {
            switch (pathSegments.get(1)) {
            case "followers":
                return UserViewActivity.createIntent(user, TAB_FOLLOWERS);
            case "following":
                return UserViewActivity.createIntent(user, TAB_FOLLOWEES);
            default:
                return null;
            }
        }

        user = getUser(pathSegments, 1);
        if (user == null) {
            return null;
        }

        switch (pathSegments.get(0)) {
        case "orgs":
            return UserViewActivity.createIntent(user);
        case "stars":
            return UserViewActivity.createIntent(user, TAB_STARS);
        default:
            return null;
        }
    }

    public static User getUser(List<String> pathSegments, int position) {
        if (pathSegments.size() < position + 1)
            return null;

        String login = pathSegments.get(position);
        if (!RepositoryUtils.isValidOwner(login))
            return null;

        return new User().setLogin(login);
    }
}
