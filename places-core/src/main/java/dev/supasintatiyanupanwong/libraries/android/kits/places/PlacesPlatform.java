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

package dev.supasintatiyanupanwong.libraries.android.kits.places;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import dev.supasintatiyanupanwong.libraries.android.kits.internal.Preconditions;

/**
 * @since 1.0.0
 */
abstract class PlacesPlatform {

    private static PlacesPlatform sPlatform;

    static synchronized void init(@NonNull Context context) {
        sPlatform = findPlatform(context);
    }

    static synchronized @NonNull PlacesPlatform get() {
        return sPlatform;
    }


    abstract @NonNull PlacesFactory getFactory();


    private static @NonNull PlacesPlatform findPlatform(Context context) {
        PlacesPlatform google = GooglePlacesPlatform.buildIfSupported(context);
        if (google != null) {
            return google;
        }

        PlacesPlatform huawei = HuaweiPlacesPlatform.buildIfSupported(context);
        if (huawei != null) {
            return huawei;
        }

        throw new IllegalStateException(
                "Can't find supported platform, make sure to include one of the next artifacts:"
                        + " ':places-google', or ':places-huawei'");
    }


    private static final class GooglePlacesPlatform extends PlacesPlatform {
        private static final String LIBRARY_PACKAGE_NAME =
                "dev.supasintatiyanupanwong.libraries.android.kits.places.internal.google";

        private static PlacesFactory sFactory;

        private GooglePlacesPlatform() {}

        @Override
        @NonNull PlacesFactory getFactory() {
            return sFactory;
        }

        static @Nullable PlacesPlatform buildIfSupported(@NonNull Context context) {
            try {
                sFactory = Preconditions.checkNotNull(
                        (PlacesFactory) Class.forName(LIBRARY_PACKAGE_NAME + ".GooglePlacesFactory")
                                .getMethod("buildIfSupported", Context.class)
                                .invoke(null, context)
                );

                return new GooglePlacesPlatform();
            } catch (Exception ex) {
                return null;
            }
        }
    }

    private static final class HuaweiPlacesPlatform extends PlacesPlatform {
        private static final String LIBRARY_PACKAGE_NAME =
                "dev.supasintatiyanupanwong.libraries.android.kits.places.internal.huawei";

        private static PlacesFactory sFactory;

        private HuaweiPlacesPlatform() {}

        @Override
        @NonNull PlacesFactory getFactory() {
            return sFactory;
        }

        static @Nullable PlacesPlatform buildIfSupported(@NonNull Context context) {
            try {
                sFactory = Preconditions.checkNotNull(
                        (PlacesFactory) Class.forName(LIBRARY_PACKAGE_NAME + ".HuaweiPlacesFactory")
                                .getMethod("buildIfSupported", Context.class)
                                .invoke(null, context)
                );

                return new HuaweiPlacesPlatform();
            } catch (Exception ex) {
                return null;
            }
        }
    }

}
