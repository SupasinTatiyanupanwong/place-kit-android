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

import com.huawei.hms.site.api.model.Site;

import java.util.ArrayList;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.places.model.AutocompletePrediction;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
public class HuaweiAutocompletePrediction implements AutocompletePrediction {

    private final @NonNull Site mDelegate;

    private HuaweiAutocompletePrediction(@NonNull Site delegate) {
        mDelegate = delegate;
    }

    @Override
    public @NonNull String getPlaceId() {
        return mDelegate.getSiteId();
    }

    @Override
    public @NonNull String getPrimaryText() {
        return mDelegate.getName();
    }

    @Override
    public @NonNull String getSecondaryText() {
        return mDelegate.getFormatAddress();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        HuaweiAutocompletePrediction that = (HuaweiAutocompletePrediction) obj;

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


    public static @NonNull AutocompletePrediction wrap(@NonNull Site delegate) {
        return new HuaweiAutocompletePrediction(delegate);
    }

    public static @NonNull List<AutocompletePrediction> wrap(@NonNull List<Site> delegates) {
        List<AutocompletePrediction> list = new ArrayList<>();
        for (int iter = 0, size = delegates.size(); iter < size; iter++) {
            list.add(HuaweiAutocompletePrediction.wrap(delegates.get(iter)));
        }
        return list;
    }

}
