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

import android.content.Context;

import androidx.annotation.NonNull;

import dev.supasintatiyanupanwong.libraries.android.kits.places.PlaceKit;
import dev.supasintatiyanupanwong.libraries.android.kits.tasks.Task;

/**
 * Client that exposes the Places API methods. To get a {@link PlacesClient}, use {@link
 * PlaceKit#createClient(Context)}.
 *
 * @since 1.0.0
 */
public interface PlacesClient {

    /**
     * Fetches autocomplete predictions.
     *
     * @param request The request specifying details of the autocomplete query.
     * @return A task used for observing the request. Response containing the list of prediction
     * objects.
     */
    @NonNull Task<FindAutocompletePredictionsResponse> findAutocompletePredictions(
            @NonNull FindAutocompletePredictionsRequest request);

    /**
     * Fetches the details of a place.
     *
     * @param request The request specifying the place of interest.
     * @return A task used for observing the request. Response containing the place of interest.
     */
    @NonNull Task<FetchPlaceResponse> fetchPlace(
            @NonNull FetchPlaceRequest request);

}
