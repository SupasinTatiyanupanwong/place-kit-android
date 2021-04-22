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

/**
 * Filter to restrict the result set of autocomplete predictions to certain types.
 *
 * @since 1.0.0
 */
public enum TypeFilter {

    /**
     * Only return geocoding results with a precise address.
     */
    ADDRESS,

    CITIES,

    /**
     * Only return results that are classified as businesses.
     */
    ESTABLISHMENT,

    /**
     * Only return geocoding results, rather than business results. For example, parks, cities and
     * street addresses.
     */
    GEOCODE,

    REGIONS

}
