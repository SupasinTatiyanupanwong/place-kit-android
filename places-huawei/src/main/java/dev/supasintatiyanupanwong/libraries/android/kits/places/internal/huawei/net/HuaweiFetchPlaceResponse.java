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
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.huawei.hms.site.api.model.DetailSearchResponse;

import dev.supasintatiyanupanwong.libraries.android.kits.places.internal.huawei.model.HuaweiPlace;
import dev.supasintatiyanupanwong.libraries.android.kits.places.model.Place;
import dev.supasintatiyanupanwong.libraries.android.kits.places.net.FetchPlaceResponse;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
public class HuaweiFetchPlaceResponse implements FetchPlaceResponse {

    private final @NonNull DetailSearchResponse mDelegate;

    private @Nullable Place mPlace;

    private HuaweiFetchPlaceResponse(@NonNull DetailSearchResponse delegate) {
        mDelegate = delegate;
    }

    @Override
    public @NonNull Place getPlace() {
        if (mPlace == null) {
            mPlace = HuaweiPlace.wrap(mDelegate.getSite());
        }
        return mPlace;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        HuaweiFetchPlaceResponse that = (HuaweiFetchPlaceResponse) obj;

        return mDelegate.equals(that.mDelegate);
    }

    @Override
    public int hashCode() {
        return mDelegate.hashCode();
    }

    @Override
    public @NonNull String toString() {
        return mDelegate.toString();
    }


    public static @NonNull FetchPlaceResponse wrap(@NonNull DetailSearchResponse delegate) {
        return new HuaweiFetchPlaceResponse(delegate);
    }

}
