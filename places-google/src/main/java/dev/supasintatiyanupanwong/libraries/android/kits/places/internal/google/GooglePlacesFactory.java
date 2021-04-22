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

package dev.supasintatiyanupanwong.libraries.android.kits.places.internal.google;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.libraries.places.api.Places;

import java.util.Arrays;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.places.PlacesFactory;
import dev.supasintatiyanupanwong.libraries.android.kits.places.internal.google.net.GooglePlacesClient;
import dev.supasintatiyanupanwong.libraries.android.kits.places.net.PlacesClient;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
@SuppressWarnings("unused")
public final class GooglePlacesFactory implements PlacesFactory {

    private GooglePlacesFactory() {}

    @Override
    public @NonNull PlacesClient createClient(@NonNull Context context) {
        return new GooglePlacesClient(context);
    }


    public static @Nullable PlacesFactory buildIfSupported(@NonNull Context context) {
        final List<Integer> unavailableResults = Arrays.asList(
                ConnectionResult.SERVICE_DISABLED,
                ConnectionResult.SERVICE_MISSING,
                ConnectionResult.SERVICE_INVALID);
        final int result =
                GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        if (unavailableResults.contains(result)) {
            return null;
        }

        String apiKey;
        try {
            apiKey = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)
                    .metaData
                    .getString("com.google.android.geo.API_KEY");
        } catch (PackageManager.NameNotFoundException e) {
            apiKey = null;
        }

        if (TextUtils.isEmpty(apiKey)) {
            throw new NullPointerException("API key is not declared in AndroidManifest.xml");
        }

        Places.initialize(context, apiKey);

        return new GooglePlacesFactory();
    }

}
