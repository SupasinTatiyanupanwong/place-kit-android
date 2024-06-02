# Place Kit

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/dev.supasintatiyanupanwong.libraries.android.kits.places/places-core/badge.svg)](https://search.maven.org/search?q=g:dev.supasintatiyanupanwong.libraries.android.kits.places)
[![javadoc](https://javadoc.io/badge2/dev.supasintatiyanupanwong.libraries.android.kits.places/places-core/javadoc.svg)](https://javadoc.io/doc/dev.supasintatiyanupanwong.libraries.android.kits.places/places-core)
[![license](https://img.shields.io/github/license/SupasinTatiyanupanwong/place-kit-android.svg)](https://www.apache.org/licenses/LICENSE-2.0)

Place Kit is an abstraction wrapper that encapsulates Google Places SDK for Android and HUAWEI Site Kit.

## Architecture

The library consists of 3 artifacts; `places-core`, `places-google`, and `places-huawei`.

`places-core` artifact provides an abstraction interface to interact with Places APIs.

`places-google` artifact provides the Google Places SDK for Android integration to Place Kit.

`places-huawei` artifact provides the HUAWEI Site Kit integration to Place Kit.

Each artifact transitively depended on its corresponding [base](https://github.com/SupasinTatiyanupanwong/android-kits-base) artifacts.

## Usage

### Migrating from the existing APIs

| Google Places SDK for Android             | HUAWEI Site Kit                         | Place Kit                                                      |
|:----------------------------------------- |:--------------------------------------- |:-------------------------------------------------------------- |
| ``com.google.android.libraries.places.*`` | ``com.huawei.hms.site.*``               | ``dev.supasintatiyanupanwong.libraries.android.kits.places.*`` |
| ``PlacesClient``                          | ``SearchService``                       | ``PlacesClient``                                               |
| ``Places.createClient(context)``          | ``SearchServiceFactory.create(context)``| ``PlaceKit.createClient(context)``                             |
| ``Place``                                 | ``Site``                                | ``Place``                                                      |
| ``FetchPlaceRequest``                     | ``DetailSearchRequest``                 | ``FetchPlaceRequest``                                          |
| ``FetchPlaceResponse``                    | ``DetailSearchResponse``                | ``FetchPlaceResponse``                                         |
| ``FindAutocompletePredictionsRequest``    | ``QuerySuggestionRequest``              | ``FindAutocompletePredictionsRequest``                         |
| ``FindAutocompletePredictionsResponse``   | ``QuerySuggestionResponse``             | ``FindAutocompletePredictionsResponse``                        |

Notice that, the goal of this library is to provides 1:1 behaviors and interfaces to the Google Places SDK for Android as much as possible. Some functionality may not be available.

### Disclaimer

In no event shall we responsible or liable in any way for any claims, damages, losses, expenses, costs, or liabilities for any reason, howsoever arising, out of or in connection with your use of the service you may incur through this library. You are responsible for the payment of any fees or any charges associated with it that may be charged by your bank or credit card provider.

## Declaring dependencies

To add a dependency on Place Kit, you must add the Maven Central repository together with the Google Maven repository and/or the Huawei Maven repository to your project.

Add the dependencies for the artifacts you need in the `build.gradle` file for your app or module:

```groovy
dependencies {
    // To use the Google Places SDK for Android via Place Kit
    implementation 'dev.supasintatiyanupanwong.libraries.android.kits.places:places-google:1.0.0'

    // To use the HUAWEI Site Kit via Place Kit
    implementation 'dev.supasintatiyanupanwong.libraries.android.kits.places:places-huawei:1.0.0'
}
```

If both of integration artifacts are included in your final build, the implementation will be selected based on API availability upon application startup.

However, it is recommended to separate builds between them as next:

```groovy
android {
    // ...
    flavorDimensions += 'vendor'
    productFlavors {
        google
        huawei { applicationIdSuffix '.huawei' }
    }
}

dependencies {
    googleImplementation 'dev.supasintatiyanupanwong.libraries.android.kits.places:places-google:1.0.0'
    huaweiImplementation 'dev.supasintatiyanupanwong.libraries.android.kits.places:places-huawei:1.0.0'
}
```

But, make sure to have one of integration artifacts included in your final build, otherwise an exception will be thrown at runtime.

For more information about dependencies, see [Add build dependencies](https://developer.android.com/studio/build/dependencies).

## Additional documentation

* [Places SDK for Android - Google Developer](https://developers.google.com/places/android-sdk/overview)
* [Site Kit - HMS Core - HUAWEI Developer](https://developer.huawei.com/consumer/en/hms/huawei-sitekit/)

## Feedback

Your feedback helps make Place Kit better. Let us know if you discover new issues or have ideas for improving this library.
Please take a look at the [existing issues](https://github.com/SupasinTatiyanupanwong/place-kit-android/issues) or the [existing discussions](https://github.com/SupasinTatiyanupanwong/place-kit-android/discussions) in this library before you create a new one.

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
