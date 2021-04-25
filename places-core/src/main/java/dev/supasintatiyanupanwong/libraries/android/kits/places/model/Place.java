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

import androidx.annotation.Nullable;

import dev.supasintatiyanupanwong.libraries.android.kits.places.net.FetchPlaceRequest;
import dev.supasintatiyanupanwong.libraries.android.kits.places.net.PlacesClient;

/**
 * Represents a particular physical place.
 * <p>
 * A Place encapsulates information about a physical location, including its name, address, and
 * any other information we might have about it.
 * <p>
 * <strong>Note:</strong> In general, some fields will be inapplicable to certain places, or the
 * information may not exist.
 *
 * @since 1.0.0
 */
public interface Place {

    /**
     * Returns the unique ID of this Place.
     * <p>
     * This ID can be defined in a FetchPlaceRequest, which may be used in {@link
     * PlacesClient#fetchPlace(FetchPlaceRequest) fetchPlace(FetchPlaceRequest)} to look up the
     * same place at a later time. Place ID data is constantly changing, so it is possible for
     * subsequent requests using the same ID to fail (for example, if the place no longer exists
     * in the database). A returned Place may also have a different ID from the ID specified in
     * the request, as there may be multiple IDs for a given place.
     *
     * @return the unique ID of this Place.
     */
    @Nullable String getId();

    /**
     * Returns the name of this Place.
     * <p>
     * The address is localized according to the device's locale.
     *
     * @return the name of this Place.
     */
    @Nullable String getName();

    /**
     * Returns a human-readable address for this Place. May return null if the address is unknown.
     * <p>
     * The address is localized according to the device's locale.
     *
     * @return a human-readable address for this Place. May return null if the address is unknown.
     */
    @Nullable String getAddress();

    /**
     * Returns the latitude location of this Place.
     * <p>
     * The location is not necessarily the center of the Place, or any particular entry or exit
     * point, but some arbitrarily chosen point within the geographic extent of the Place.
     *
     * @return the latitude location of this Place.
     */
    double getLatitude();

    /**
     * Returns the longitude location of this Place.
     * <p>
     * The location is not necessarily the center of the Place, or any particular entry or exit
     * point, but some arbitrarily chosen point within the geographic extent of the Place.
     *
     * @return the longitude location of this Place.
     */
    double getLongitude();


    // Not supported in Huawei mode, but required in Google mode.
    enum Field {
        ADDRESS,
        ADDRESS_COMPONENTS,
        ID,
        LAT_LNG,
        NAME,
        OPENING_HOURS,
        PHONE_NUMBER,
        PHOTO_METADATAS,
        PLUS_CODE,
        PRICE_LEVEL,
        RATING,
        TYPES,
        USER_RATINGS_TOTAL,
        UTC_OFFSET,
        VIEWPORT,
        WEBSITE_URI
    }

}
