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

package me.tatiyanupanwong.supasin.android.libraries.kits.places.internal.google.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import java.util.ArrayList;
import java.util.List;

import me.tatiyanupanwong.supasin.android.libraries.kits.places.model.AutocompletePrediction;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
public class GoogleAutocompletePrediction implements AutocompletePrediction {

    private final @NonNull com.google.android.libraries.places.api.model.AutocompletePrediction
            mDelegate;

    private GoogleAutocompletePrediction(
            @NonNull com.google.android.libraries.places.api.model.AutocompletePrediction
                    delegate) {
        mDelegate = delegate;
    }

    @Override
    public String getPlaceId() {
        return mDelegate.getPlaceId();
    }

    @Override
    public String getPrimaryText() {
        return mDelegate.getPrimaryText(null).toString();
    }

    @Override
    public String getSecondaryText() {
        return mDelegate.getSecondaryText(null).toString();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        GoogleAutocompletePrediction that = (GoogleAutocompletePrediction) obj;

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


    public static @NonNull AutocompletePrediction wrap(
            @NonNull com.google.android.libraries.places.api.model.AutocompletePrediction
                    delegate) {
        return new GoogleAutocompletePrediction(delegate);
    }

    public static @Nullable List<AutocompletePrediction> wrap(
            @Nullable List<com.google.android.libraries.places.api.model.AutocompletePrediction>
                    delegates) {
        if (delegates == null) {
            return null;
        }

        List<AutocompletePrediction> list = new ArrayList<>();
        for (int iter = 0, size = delegates.size(); iter < size; iter++) {
            list.add(GoogleAutocompletePrediction.wrap(delegates.get(iter)));
        }
        return list;
    }

}
