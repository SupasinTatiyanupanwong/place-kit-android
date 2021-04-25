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

import java.util.Collections;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.places.model.Place;

/**
 * Request used by {@link PlacesClient#fetchPlace(FetchPlaceRequest) fetchPlace(FetchPlaceRequest)}.
 *
 * @since 1.0.0
 */
public class FetchPlaceRequest {

    private final @NonNull String mPlaceId;
    private final @NonNull List<Place.Field> mPlaceFields;

    private FetchPlaceRequest(@NonNull Builder builder) {
        mPlaceId = builder.mPlaceId;
        mPlaceFields = builder.mPlaceFields;
    }

    public @NonNull String getPlaceId() {
        return mPlaceId;
    }

    public @NonNull List<Place.Field> getPlaceFields() {
        return mPlaceFields;
    }


    public static class Builder {
        String mPlaceId;
        List<Place.Field> mPlaceFields;

        public @NonNull Builder setPlaceId(@NonNull String placeId) {
            mPlaceId = placeId;
            return this;
        }

        public @NonNull Builder setPlaceFields(@NonNull List<Place.Field> placeFields) {
            mPlaceFields = Collections.unmodifiableList(placeFields);
            return this;
        }

        public @NonNull FetchPlaceRequest build() {
            return new FetchPlaceRequest(this);
        }
    }

}
