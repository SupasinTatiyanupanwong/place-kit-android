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

package me.tatiyanupanwong.supasin.android.libraries.kits.places.net;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.tatiyanupanwong.supasin.android.libraries.kits.places.model.TypeFilter;

public class FindAutocompletePredictionsRequest {

    public final String mQuery;
    public final String mCountry;
    public final TypeFilter mTypeFilter;

    private FindAutocompletePredictionsRequest(@NonNull Builder builder) {
        mQuery = builder.mQuery;
        mCountry = builder.mCountry;
        mTypeFilter = builder.mTypeFilter;
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
