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

package dev.supasintatiyanupanwong.libraries.android.kits.places.internal.huawei.model;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.huawei.hms.site.api.model.LocationType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.places.model.TypeFilter;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
public final class HuaweiTypeFilter {

    // https://developers.google.com/places/web-service/supported_types#table3
    private static final EnumMap<TypeFilter, List<LocationType>> TYPE_FILTERS =
            new EnumMap<TypeFilter, List<LocationType>>(TypeFilter.class) {{
                put(TypeFilter.ADDRESS, new ArrayList<LocationType>() {{
                    add(LocationType.STREET_ADDRESS);
                }});
                put(TypeFilter.CITIES, new ArrayList<LocationType>() {{
                    add(LocationType.LOCALITY);
                    add(LocationType.ADMINISTRATIVE_AREA_LEVEL_3);
                }});
                put(TypeFilter.ESTABLISHMENT, new ArrayList<LocationType>() {{
                    add(LocationType.ESTABLISHMENT);
                }});
                put(TypeFilter.GEOCODE, new ArrayList<LocationType>() {{
                    add(LocationType.GEOCODE);
                }});
                put(TypeFilter.REGIONS, new ArrayList<LocationType>() {{
                    add(LocationType.LOCALITY);
                    add(LocationType.SUBLOCALITY);
                    add(LocationType.POSTAL_CODE);
                    add(LocationType.COUNTRY);
                    add(LocationType.ADMINISTRATIVE_AREA_LEVEL_1);
                    add(LocationType.ADMINISTRATIVE_AREA_LEVEL_2);
                }});
            }};

    private HuaweiTypeFilter() {}


    public static @Nullable List<LocationType> unwrap(@Nullable TypeFilter wrapped) {
        return wrapped == null ? null : TYPE_FILTERS.get(wrapped);
    }

}
