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

package dev.supasintatiyanupanwong.libraries.android.kits.places.internal.huawei.net;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import com.huawei.hms.site.api.model.QuerySuggestionRequest;

import dev.supasintatiyanupanwong.libraries.android.kits.places.internal.huawei.model.HuaweiTypeFilter;
import dev.supasintatiyanupanwong.libraries.android.kits.places.net.FindAutocompletePredictionsRequest;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
public final class HuaweiFindAutocompletePredictionsRequest {

    private HuaweiFindAutocompletePredictionsRequest() {}


    public static @NonNull QuerySuggestionRequest unwrap(
            @NonNull FindAutocompletePredictionsRequest wrapped) {
        final QuerySuggestionRequest request = new QuerySuggestionRequest();
        request.setQuery(wrapped.getQuery());
        request.setCountryCode(wrapped.getCountry());
        request.setPoiTypes(HuaweiTypeFilter.unwrap(wrapped.getTypeFilter()));
        return request;
    }

}
