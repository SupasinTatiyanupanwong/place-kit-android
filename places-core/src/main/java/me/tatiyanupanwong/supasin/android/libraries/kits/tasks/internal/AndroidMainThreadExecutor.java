/*
 * Copyright 2020 Supasin Tatiyanupanwong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.tatiyanupanwong.supasin.android.libraries.kits.tasks.internal;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import java.util.concurrent.Executor;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP_PREFIX;

@RestrictTo(LIBRARY_GROUP_PREFIX)
public final class AndroidMainThreadExecutor implements Executor {

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private AndroidMainThreadExecutor() {}

    public static @NonNull Executor getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        mHandler.post(runnable);
    }


    private static final class LazyHolder {
        private static final @NonNull Executor INSTANCE = new AndroidMainThreadExecutor();

        private LazyHolder() {}
    }

}
