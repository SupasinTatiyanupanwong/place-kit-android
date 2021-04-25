/*
 * Copyright 2020 Supasin Tatiyanupanwong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.supasintatiyanupanwong.libraries.android.kits.places.internal.huawei.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.huawei.hms.site.api.model.Coordinate;
import com.huawei.hms.site.api.model.Site;

import dev.supasintatiyanupanwong.libraries.android.kits.places.model.Place;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
public class HuaweiPlace implements Place {

    private final @NonNull Site mDelegate;

    private HuaweiPlace(@NonNull Site delegate) {
        mDelegate = delegate;
    }

    @Override
    public @Nullable String getId() {
        return mDelegate.getSiteId();
    }

    @Override
    public @Nullable String getName() {
        return mDelegate.getName();
    }

    @Override
    public @Nullable String getAddress() {
        return mDelegate.getFormatAddress();
    }

    @Override
    public double getLatitude() {
        Coordinate coord = mDelegate.getLocation();
        return coord != null ? coord.getLat() : 0;
    }

    @Override
    public double getLongitude() {
        Coordinate coord = mDelegate.getLocation();
        return coord != null ? coord.getLng() : 0;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        HuaweiPlace that = (HuaweiPlace) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override
    public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override
    public @NonNull String toString() {
        return mDelegate.toString();
    }


    public static @NonNull Place wrap(@NonNull Site delegate) {
        return new HuaweiPlace(delegate);
    }

}
