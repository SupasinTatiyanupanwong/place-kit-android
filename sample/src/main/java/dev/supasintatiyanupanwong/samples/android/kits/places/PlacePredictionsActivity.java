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

package dev.supasintatiyanupanwong.samples.android.kits.places;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.places.PlaceKit;
import dev.supasintatiyanupanwong.libraries.android.kits.places.model.AutocompletePrediction;
import dev.supasintatiyanupanwong.libraries.android.kits.places.model.TypeFilter;
import dev.supasintatiyanupanwong.libraries.android.kits.places.net.FindAutocompletePredictionsRequest;
import dev.supasintatiyanupanwong.libraries.android.kits.places.net.PlacesClient;
import dev.supasintatiyanupanwong.samples.android.kits.places.databinding.PlacePredictionsActivityBinding;

public class PlacePredictionsActivity extends AppCompatActivity {

    private static final String TAG = PlacePredictionsActivity.class.getSimpleName();

    private final @NonNull Handler mHandler = new Handler(Looper.getMainLooper());
    private final @NonNull PlacePredictionsAdapter mAdapter = new PlacePredictionsAdapter();

    private PlacePredictionsActivityBinding mBinding;
    private PlacesClient mPlacesClient;

    public PlacePredictionsActivity() {
        super(R.layout.place_predictions_activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = PlacePredictionsActivityBinding.bind(findViewById(android.R.id.content));

        mPlacesClient = PlaceKit.createClient(this);

        mBinding.toolbar.setTitle("Place Kit Sample");
        mBinding.toolbar.inflateMenu(R.menu.place_predictions_menu);

        SearchView searchView = (SearchView) mBinding.toolbar.getMenu()
                .findItem(R.id.place_predictions_search)
                .getActionView();
        searchView.setQueryHint("Search a Place");
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                mBinding.progress.setIndeterminate(true);

                // Cancel any previous place prediction requests
                mHandler.removeCallbacksAndMessages(null);

                // Start a new place prediction request in 300 ms
                mHandler.postDelayed(() -> getPlacePredictions(newText), 300);
                return true;
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.list.setLayoutManager(layoutManager);
        mBinding.list.setAdapter(mAdapter);
        mBinding.list.addItemDecoration(
                new DividerItemDecoration(this, layoutManager.getOrientation()));
    }

    private void getPlacePredictions(String query) {
        if (query == null || query.isEmpty()) {
            mBinding.progress.setIndeterminate(false);
            mBinding.hint.setVisibility(View.VISIBLE);
            mBinding.empty.setVisibility(View.INVISIBLE);
            mBinding.error.setVisibility(View.INVISIBLE);

            mAdapter.setPredictions(null);

            return;
        }

        mBinding.hint.setVisibility(View.INVISIBLE);

        final FindAutocompletePredictionsRequest newRequest =
                new FindAutocompletePredictionsRequest.Builder()
                        .setTypeFilter(TypeFilter.ESTABLISHMENT)
                        .setQuery(query)
                        .setCountry("US")
                        .build();

        mPlacesClient.findAutocompletePredictions(newRequest)
                .addOnSuccessListener(result -> {
                    List<AutocompletePrediction> predictions = result == null
                            ? null
                            : result.getAutocompletePredictions();
                    mBinding.error.setVisibility(View.INVISIBLE);
                    mBinding.empty.setVisibility(
                            (predictions == null || predictions.isEmpty())
                                    ? View.VISIBLE
                                    : View.INVISIBLE
                    );
                    mAdapter.setPredictions(predictions);
                })
                .addOnFailureListener(exception -> {
                    mBinding.error.setVisibility(View.VISIBLE);
                    mAdapter.setPredictions(null);
                    Log.e(TAG, Log.getStackTraceString(exception));
                })
                .addOnCompleteListener(task -> mBinding.progress.setIndeterminate(false));
    }
}
