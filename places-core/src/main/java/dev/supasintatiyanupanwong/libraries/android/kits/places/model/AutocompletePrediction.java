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

package dev.supasintatiyanupanwong.libraries.android.kits.places.model;

import androidx.annotation.NonNull;

/**
 * Represents an autocomplete suggestion of a place, based on a particular text query.
 * <p>
 * An AutocompletePrediction includes the description of the suggested place as well as basic
 * details including place ID and types.
 *
 * @since 1.0.0
 */
public interface AutocompletePrediction {

    /**
     * Returns the place ID of the place being referred to by this prediction.
     *
     * @return the place ID of the place being referred to by this prediction.
     */
    @NonNull String getPlaceId();

    /**
     * Returns the primary text of a place. This will usually be the name of the place.
     * <p>
     * Example: "Eiffel Tower", "123 Pitt Street"
     *
     * @return the primary text of a place. This will usually be the name of the place.
     */
    @NonNull String getPrimaryText();

    /**
     * Returns the secondary text of a place. This provides extra context on the place, and can
     * be used as a second line when showing autocomplete predictions.
     * <p>
     * Example: "Avenue Anatole France, Paris, France", "Sydney, New South Wales"
     *
     * @return the secondary text of a place. This provides extra context on the place, and can
     * be used as a second line when showing autocomplete predictions.
     */
    @NonNull String getSecondaryText();

}
