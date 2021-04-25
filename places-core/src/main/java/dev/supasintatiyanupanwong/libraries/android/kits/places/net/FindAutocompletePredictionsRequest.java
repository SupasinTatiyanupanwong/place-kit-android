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

package dev.supasintatiyanupanwong.libraries.android.kits.places.net;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import dev.supasintatiyanupanwong.libraries.android.kits.places.model.TypeFilter;

/**
 * Request used by {@link
 * PlacesClient#findAutocompletePredictions(FindAutocompletePredictionsRequest)
 * findAutocompletePredictions(FindAutocompletePredictionsRequest)}.
 *
 * @since 1.0.0
 */
public class FindAutocompletePredictionsRequest {

    private final @Nullable String mQuery;
    private final @Nullable String mCountry;
    private final @Nullable TypeFilter mTypeFilter;

    private FindAutocompletePredictionsRequest(@NonNull Builder builder) {
        mQuery = builder.mQuery;
        mCountry = builder.mCountry;
        mTypeFilter = builder.mTypeFilter;
    }

    public @Nullable String getQuery() {
        return mQuery;
    }

    public @Nullable String getCountry() {
        return mCountry;
    }

    public @Nullable TypeFilter getTypeFilter() {
        return mTypeFilter;
    }


    public static class Builder {
        String mQuery;
        String mCountry;
        TypeFilter mTypeFilter;

        public @NonNull Builder setQuery(@Nullable String query) {
            mQuery = query;
            return this;
        }

        public @NonNull Builder setCountry(@Nullable String country) {
            mCountry = country;
            return this;
        }

        public @NonNull Builder setTypeFilter(@Nullable TypeFilter typeFilter) {
            mTypeFilter = typeFilter;
            return this;
        }

        public @NonNull FindAutocompletePredictionsRequest build() {
            return new FindAutocompletePredictionsRequest(this);
        }
    }

}
