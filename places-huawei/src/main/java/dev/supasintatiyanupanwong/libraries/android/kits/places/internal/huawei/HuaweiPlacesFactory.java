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

package dev.supasintatiyanupanwong.libraries.android.kits.places.internal.huawei;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiAvailability;

import java.util.Arrays;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.places.PlacesFactory;
import dev.supasintatiyanupanwong.libraries.android.kits.places.internal.huawei.net.HuaweiPlacesClient;
import dev.supasintatiyanupanwong.libraries.android.kits.places.net.PlacesClient;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
@SuppressWarnings("unused")
public final class HuaweiPlacesFactory implements PlacesFactory {

    private final @NonNull String mApiKey;

    private HuaweiPlacesFactory(@NonNull String apiKey) {
        mApiKey = apiKey;
    }

    @Override
    public @NonNull PlacesClient createClient(@NonNull Context context) {
        return new HuaweiPlacesClient(context, mApiKey);
    }


    public static @Nullable PlacesFactory buildIfSupported(@NonNull Context context) {
        final List<Integer> unavailableResults = Arrays.asList(
                ConnectionResult.SERVICE_DISABLED,
                ConnectionResult.SERVICE_MISSING,
                ConnectionResult.SERVICE_INVALID);
        final int result =
                HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(context);
        if (unavailableResults.contains(result)) {
            return null;
        }

        String apiKey;
        try {
            apiKey = Uri.encode(
                    AGConnectServicesConfig.fromContext(context).getString("client/api_key"));
        } catch (Exception ex) {
            apiKey = null;
        }

        if (TextUtils.isEmpty(apiKey)) {
            throw new NullPointerException("API key is not found in agconnect-services.json");
        }

        return new HuaweiPlacesFactory(apiKey);
    }

}
