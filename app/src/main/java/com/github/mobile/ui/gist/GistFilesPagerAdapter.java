package com.github.mobile.ui.gist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.github.mobile.ui.FragmentPagerAdapter;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;

import java.util.Map;

import static com.github.mobile.Intents.EXTRA_GIST_FILE;

/**
 * Pager adapter for all the files in a given gist
 */
public class GistFilesPagerAdapter extends FragmentPagerAdapter {

    private final GistFile[] files;

    /**
     * @param activity
     * @param gist
     */
    public GistFilesPagerAdapter(AppCompatActivity activity, Gist gist) {
        super(activity);

        Map<String, GistFile> gistFiles = gist.getFiles();
        if (gistFiles != null && !gistFiles.isEmpty())
            files = gistFiles.values().toArray(new GistFile[gistFiles.size()]);
        else
            files = new GistFile[0];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return files[position].getFilename();
    }

    @Override
    public Fragment getItem(final int position) {
        GistFile file = files[position];
        Fragment fragment = new GistFileFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_GIST_FILE, file);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return files.length;
    }
}
