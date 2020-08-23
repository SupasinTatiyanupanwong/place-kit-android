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

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.google.android.libraries.places.api.Places;

import me.tatiyanupanwong.supasin.android.libraries.kits.internal.google.tasks.GoogleTask;
import me.tatiyanupanwong.supasin.android.libraries.kits.places.net.FetchPlaceRequest;
import me.tatiyanupanwong.supasin.android.libraries.kits.places.net.FetchPlaceResponse;
import me.tatiyanupanwong.supasin.android.libraries.kits.places.net.FindAutocompletePredictionsRequest;
import me.tatiyanupanwong.supasin.android.libraries.kits.places.net.FindAutocompletePredictionsResponse;
import me.tatiyanupanwong.supasin.android.libraries.kits.places.net.PlacesClient;
import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.Task;
import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.internal.ResultInterceptor;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
public final class GooglePlacesClient implements PlacesClient {

    private final com.google.android.libraries.places.api.net.PlacesClient mDelegate;

    public GooglePlacesClient(Context context) {
        mDelegate = Places.createClient(context);
    }

    @Override
    public @NonNull Task<FindAutocompletePredictionsResponse> findAutocompletePredictions(
            @NonNull FindAutocompletePredictionsRequest request) {
        return new GoogleTask<>(
                mDelegate.findAutocompletePredictions(
                        GoogleFindAutocompletePredictionsRequest.unwrap(request)),
                new ResultInterceptor<
                        com.google.android.libraries.places.api.net
                                .FindAutocompletePredictionsResponse,
                        FindAutocompletePredictionsResponse>() {
                    @Override
                    public @Nullable FindAutocompletePredictionsResponse intercept(
                            @Nullable com.google.android.libraries.places.api.net
                                    .FindAutocompletePredictionsResponse response) {
                        return GoogleFindAutocompletePredictionsResponse.wrap(response);
                    }
                }
        );
    }

    @Override
    public @NonNull Task<FetchPlaceResponse> fetchPlace(@NonNull FetchPlaceRequest request) {
        return new GoogleTask<>(
                mDelegate.fetchPlace(GoogleFetchPlaceRequest.unwrap(request)),
                new ResultInterceptor<
                        com.google.android.libraries.places.api.net.FetchPlaceResponse,
                        FetchPlaceResponse>() {
                    @Override
                    public @Nullable FetchPlaceResponse intercept(
                            @Nullable com.google.android.libraries.places.api.net
                                    .FetchPlaceResponse response) {
                        return GoogleFetchPlaceResponse.wrap(response);
                    }
                }
        );
    }

}
