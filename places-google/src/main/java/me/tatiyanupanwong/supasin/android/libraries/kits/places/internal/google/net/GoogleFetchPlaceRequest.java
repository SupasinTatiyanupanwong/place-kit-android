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

package me.tatiyanupanwong.supasin.android.libraries.kits.places.internal.google.net;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import me.tatiyanupanwong.supasin.android.libraries.kits.places.internal.google.model.GooglePlace;
import me.tatiyanupanwong.supasin.android.libraries.kits.places.net.FetchPlaceRequest;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
public final class GoogleFetchPlaceRequest {

    private GoogleFetchPlaceRequest() {}


    public static @NonNull com.google.android.libraries.places.api.net.FetchPlaceRequest unwrap(
            @NonNull FetchPlaceRequest wrapped) {
        return com.google.android.libraries.places.api.net.FetchPlaceRequest
                .newInstance(wrapped.mPlaceId, GooglePlace.Field.unwrap(wrapped.mPlaceFields));
    }

}
