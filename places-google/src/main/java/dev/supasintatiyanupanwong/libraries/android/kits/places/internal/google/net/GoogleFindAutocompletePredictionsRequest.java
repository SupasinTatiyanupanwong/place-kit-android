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
import androidx.annotation.RestrictTo;

import dev.supasintatiyanupanwong.libraries.android.kits.places.internal.google.model.GoogleTypeFilter;
import dev.supasintatiyanupanwong.libraries.android.kits.places.net.FindAutocompletePredictionsRequest;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
public final class GoogleFindAutocompletePredictionsRequest {

    private GoogleFindAutocompletePredictionsRequest() {}


    public static @NonNull com.google.android.libraries.places.api.net
            .FindAutocompletePredictionsRequest unwrap(
                    @NonNull FindAutocompletePredictionsRequest wrapped) {
        return com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
                .builder()
                .setQuery(wrapped.getQuery())
                .setCountry(wrapped.getCountry())
                .setTypeFilter(GoogleTypeFilter.wrap(wrapped.getTypeFilter()))
                .build();
    }

}
