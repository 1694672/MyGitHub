package com.github.mobile.ui.commit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mobile.Intents.Builder;
import com.github.mobile.R;
import com.github.mobile.ui.DialogFragmentActivity;
import com.github.mobile.ui.repo.RepositoryViewActivity;
import com.github.mobile.util.AvatarLoader;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.Repository;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static com.github.mobile.Intents.EXTRA_BASE;
import static com.github.mobile.Intents.EXTRA_HEAD;
import static com.github.mobile.Intents.EXTRA_REPOSITORY;

/**
 * Activity to display a comparison between two commits
 */
public class CommitCompareViewActivity extends DialogFragmentActivity {

    /**
     * Create intent for this activity
     *
     * @param repository
     * @param base
     * @param head
     * @return intent
     */
    public static Intent createIntent(final Repository repository,
            final String base, final String head) {
        Builder builder = new Builder("commits.compare.VIEW");
        builder.add(EXTRA_BASE, base);
        builder.add(EXTRA_HEAD, head);
        builder.repo(repository);
        return builder.toIntent();
    }

    /**
     * Create intent for this activity
     *
     * @param repository
     * @param head
     * @return intent
     */
    public static Intent createIntent(final Repository repository, final String head) {
        return createIntent(repository, null, head);
    }

    private Repository repository;

    @Inject
    private AvatarLoader avatars;

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository = getSerializableExtra(EXTRA_REPOSITORY);

        setContentView(R.layout.commit_compare);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle(repository.generateId());
        avatars.bind(actionBar, repository.getOwner());

        fragment = getSupportFragmentManager()
                .findFragmentById(R.id.commit_compare_list_fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu optionsMenu) {
        if (fragment != null)
            fragment.onCreateOptionsMenu(optionsMenu, getMenuInflater());

        return super.onCreateOptionsMenu(optionsMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            Intent intent = RepositoryViewActivity.createIntent(repository);
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            return true;
        default:
            if (fragment != null)
                return fragment.onOptionsItemSelected(item);
            else
                return super.onOptionsItemSelected(item);
        }
    }
}
