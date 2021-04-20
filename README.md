# Place Kit

[![Download](https://api.bintray.com/packages/supasintatiyanupanwong/android.libraries.kits.places/places-core/images/download.svg)](https://bintray.com/supasintatiyanupanwong/android.libraries.kits.places/places-core/_latestVersion)
[![javadoc](https://javadoc.io/badge2/me.tatiyanupanwong.supasin.android.libraries.kits.places/places-core/javadoc.svg)](https://javadoc.io/doc/me.tatiyanupanwong.supasin.android.libraries.kits.places/places-core)
[![license](https://img.shields.io/github/license/SupasinTatiyanupanwong/place-kit-android.svg)](https://www.apache.org/licenses/LICENSE-2.0)

Place Kit is an abstraction wrapper that encapsulates Google Places SDK for Android and HUAWEI Site Kit.

## Architecture

The library consists of 3 artifacts; `places-core`, `places-google`, and `places-huawei`.

* `places-core` artifact provides an abstraction interface to interact with Places APIs.
* `places-google` artifact provides the Google Places SDK for Android integration to Place Kit.
* `places-huawei` artifact provides the HUAWEI Site Kit integration to Place Kit.

Each artifact transitively depended on its corresponding [base](https://github.com/SupasinTatiyanupanwong/android-kits-base) artifacts.

## Declaring dependencies

Add the dependencies for the artifacts you need in the `build.gradle` file for your app or module:

```groovy
dependencies {
    // To use the Google Places SDK for Android via Place Kit
    implementation 'me.tatiyanupanwong.supasin.android.libraries.kits.places:places-google:1.0.0'

    // To use the HUAWEI Site Kit via Place Kit
    implementation 'me.tatiyanupanwong.supasin.android.libraries.kits.places:places-huawei:1.0.0'
}
```

If both of integration artifacts are included in your final build, the implementation will be selected based on API availability upon application startup.

However, it is recommended to separate builds between them as next:

```groovy
android {
    ...
    flavorDimensions 'vendor'
    productFlavors {
        google
        huawei { applicationIdSuffix '.huawei' }
    }
}

configurations {
    google
    huawei

    googleImplementation.extendsFrom(google)
    googleCompileOnly.extendsFrom(huawei)

    huaweiImplementation.extendsFrom(huawei)
    huaweiCompileOnly.extendsFrom(google)
}

dependencies {
    google 'me.tatiyanupanwong.supasin.android.libraries.kits.places:places-google:1.0.0'
    huawei 'me.tatiyanupanwong.supasin.android.libraries.kits.places:places-huawei:1.0.0'
}
```

But, make sure to have one of integration artifacts included in your final build, otherwise an exception will be thrown at runtime.

For more information about dependencies, see [Add build dependencies](https://developer.android.com/studio/build/dependencies).

## Additional documentation

* [Places SDK for Android - Google Developer](https://developers.google.com/places/android-sdk/overview)
* [Site Kit - HMS Core - HUAWEI Developer](https://developer.huawei.com/consumer/en/hms/huawei-sitekit/)

## License

```
Copyright 2020 Supasin Tatiyanupanwong

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
