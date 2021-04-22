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

package dev.supasintatiyanupanwong.libraries.android.kits.places.internal.google.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import java.util.ArrayList;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.places.model.Place;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
public class GooglePlace implements Place {

    private final @NonNull com.google.android.libraries.places.api.model.Place mDelegate;

    private GooglePlace(@NonNull com.google.android.libraries.places.api.model.Place delegate) {
        mDelegate = delegate;
    }

    @Override
    public @Nullable String getId() {
        return mDelegate.getId();
    }

    @Override
    public @Nullable String getName() {
        return mDelegate.getName();
    }

    @Override
    public @Nullable String getAddress() {
        return mDelegate.getAddress();
    }

    @Override
    public double getLatitude() {
        return mDelegate.getLatLng() != null ? mDelegate.getLatLng().latitude : 0;
    }

    @Override
    public double getLongitude() {
        return mDelegate.getLatLng() != null ? mDelegate.getLatLng().longitude : 0;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        GooglePlace that = (GooglePlace) obj;

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


    public static @NonNull Place wrap(
            @NonNull com.google.android.libraries.places.api.model.Place delegate) {
        return new GooglePlace(delegate);
    }


    public static final class Field {
        public static @NonNull com.google.android.libraries.places.api.model.Place.Field unwrap(
                @NonNull Place.Field wrapped) {
            switch (wrapped) {
                case ADDRESS:
                    return com.google.android.libraries.places.api.model
                            .Place.Field.ADDRESS;
                case ADDRESS_COMPONENTS:
                    return com.google.android.libraries.places.api.model
                            .Place.Field.ADDRESS_COMPONENTS;
                case ID:
                    return com.google.android.libraries.places.api.model
                            .Place.Field.ID;
                case LAT_LNG:
                    return com.google.android.libraries.places.api.model
                            .Place.Field.LAT_LNG;
                case NAME:
                    return com.google.android.libraries.places.api.model
                            .Place.Field.NAME;
                case OPENING_HOURS:
                    return com.google.android.libraries.places.api.model
                            .Place.Field.OPENING_HOURS;
                case PHONE_NUMBER:
                    return com.google.android.libraries.places.api.model
                            .Place.Field.PHONE_NUMBER;
                case PHOTO_METADATAS:
                    return com.google.android.libraries.places.api.model
                            .Place.Field.PHOTO_METADATAS;
                case PLUS_CODE:
                    return com.google.android.libraries.places.api.model
                            .Place.Field.PLUS_CODE;
                case PRICE_LEVEL:
                    return com.google.android.libraries.places.api.model
                            .Place.Field.PRICE_LEVEL;
                case RATING:
                    return com.google.android.libraries.places.api.model
                            .Place.Field.RATING;
                case TYPES:
                    return com.google.android.libraries.places.api.model
                            .Place.Field.TYPES;
                case USER_RATINGS_TOTAL:
                    return com.google.android.libraries.places.api.model
                            .Place.Field.USER_RATINGS_TOTAL;
                case UTC_OFFSET:
                    return com.google.android.libraries.places.api.model
                            .Place.Field.UTC_OFFSET;
                case VIEWPORT:
                    return com.google.android.libraries.places.api.model
                            .Place.Field.VIEWPORT;
                case WEBSITE_URI:
                    return com.google.android.libraries.places.api.model
                            .Place.Field.WEBSITE_URI;
            }
            throw new IllegalArgumentException("Unsupported field: " + wrapped);
        }

        public static @NonNull List<
                com.google.android.libraries.places.api.model.Place.Field> unwrap(
                        @NonNull List<Place.Field> wrappeds) {
            List<com.google.android.libraries.places.api.model.Place.Field> list =
                    new ArrayList<>();
            for (int iter = 0, size = wrappeds.size(); iter < size; iter++) {
                list.add(Field.unwrap(wrappeds.get(iter)));
            }
            return list;
        }
    }

}
