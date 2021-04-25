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

package dev.supasintatiyanupanwong.libraries.android.kits.places.internal.google.net;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import dev.supasintatiyanupanwong.libraries.android.kits.places.internal.google.model.GooglePlace;
import dev.supasintatiyanupanwong.libraries.android.kits.places.model.Place;
import dev.supasintatiyanupanwong.libraries.android.kits.places.net.FetchPlaceResponse;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
public class GoogleFetchPlaceResponse implements FetchPlaceResponse {

    private final @NonNull com.google.android.libraries.places.api.net.FetchPlaceResponse mDelegate;

    private @Nullable Place mPlace;

    private GoogleFetchPlaceResponse(
            @NonNull com.google.android.libraries.places.api.net.FetchPlaceResponse delegate) {
        this.mDelegate = delegate;
    }

    @Override
    public @NonNull Place getPlace() {
        if (mPlace == null) {
            mPlace = GooglePlace.wrap(mDelegate.getPlace());
        }
        return mPlace;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        GoogleFetchPlaceResponse that = (GoogleFetchPlaceResponse) obj;

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


    public static @Nullable FetchPlaceResponse wrap(
            @Nullable com.google.android.libraries.places.api.net.FetchPlaceResponse delegate) {
        return delegate == null ? null : new GoogleFetchPlaceResponse(delegate);
    }

}
