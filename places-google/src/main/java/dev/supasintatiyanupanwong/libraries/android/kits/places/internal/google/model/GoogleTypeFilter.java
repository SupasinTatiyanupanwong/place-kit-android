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

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import dev.supasintatiyanupanwong.libraries.android.kits.places.model.TypeFilter;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
public final class GoogleTypeFilter {

    private GoogleTypeFilter() {}


    public static @Nullable com.google.android.libraries.places.api.model.TypeFilter wrap(
            @Nullable TypeFilter delegate) {
        if (delegate == null) {
            return null;
        }

        switch (delegate) {
            case ADDRESS:
                return com.google.android.libraries.places.api.model.TypeFilter.ADDRESS;
            case CITIES:
                return com.google.android.libraries.places.api.model.TypeFilter.CITIES;
            case ESTABLISHMENT:
                return com.google.android.libraries.places.api.model.TypeFilter.ESTABLISHMENT;
            case GEOCODE:
                return com.google.android.libraries.places.api.model.TypeFilter.GEOCODE;
            case REGIONS:
                return com.google.android.libraries.places.api.model.TypeFilter.REGIONS;
            default:
                return null;
        }
    }

}
