package com.bugsnag.android;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.util.Comparator;
import java.util.Locale;
import java.util.UUID;

/**
 * Store and flush Sessions which couldn't be sent immediately due to
 * lack of network connectivity.
 */
class SessionStore extends FileStore<Session> {

    static final Comparator<File> SESSION_COMPARATOR = new Comparator<File>() {
        @Override
        public int compare(File lhs, File rhs) {
            if (lhs == null && rhs == null) {
                return 0;
            }
            if (lhs == null) {
                return 1;
            }
            if (rhs == null) {
                return -1;
            }
            String lhsName = lhs.getName();
            String rhsName = rhs.getName();
            return lhsName.compareTo(rhsName);
        }
    };

    SessionStore(@NonNull Configuration config, @NonNull Context appContext,
                 @Nullable Delegate delegate) {
        super(config, appContext, config.getSessionStore(),
            128, SESSION_COMPARATOR, delegate);
    }

    @NonNull
    @Override
    String getFilename(Object object) {
        return String.format(Locale.US, "%s%s%d.json",
            storeDirectory, UUID.randomUUID().toString(), System.currentTimeMillis());
    }

}
