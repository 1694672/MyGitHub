package com.github.mobile.sync;

import android.content.Intent;
import android.os.IBinder;

import com.google.inject.Inject;

import roboguice.inject.ContextScopedProvider;
import roboguice.service.RoboService;

/**
 * Sync adapter service
 */
public class SyncAdapterService extends RoboService {

    @Inject
    private ContextScopedProvider<SyncAdapter> syncAdapterProvider;

    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapterProvider.get(this).getSyncAdapterBinder();
    }
}