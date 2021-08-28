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

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.supasintatiyanupanwong.libraries.android.kits.places.PlaceKit;
import dev.supasintatiyanupanwong.libraries.android.kits.places.model.AutocompletePrediction;
import dev.supasintatiyanupanwong.libraries.android.kits.places.model.TypeFilter;
import dev.supasintatiyanupanwong.libraries.android.kits.places.net.FindAutocompletePredictionsRequest;
import dev.supasintatiyanupanwong.libraries.android.kits.places.net.FindAutocompletePredictionsResponse;
import dev.supasintatiyanupanwong.libraries.android.kits.places.net.PlacesClient;
import dev.supasintatiyanupanwong.libraries.android.kits.tasks.Task;
import dev.supasintatiyanupanwong.libraries.android.kits.tasks.listeners.OnCompleteListener;
import dev.supasintatiyanupanwong.libraries.android.kits.tasks.listeners.OnFailureListener;
import dev.supasintatiyanupanwong.libraries.android.kits.tasks.listeners.OnSuccessListener;

public class PlacePredictionsActivity extends AppCompatActivity {

    private static final String TAG = PlacePredictionsActivity.class.getSimpleName();

    private final @NonNull Handler mHandler = new Handler();
    private final @NonNull PlacePredictionsAdapter mAdapter = new PlacePredictionsAdapter();

    private ViewHolder mViews;
    private PlacesClient mPlacesClient;

    public PlacePredictionsActivity() {
        super(R.layout.place_predictions_activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViews = new ViewHolder(this);

        mPlacesClient = PlaceKit.createClient(this);

        mViews.mToolbar.setTitle("Place Kit Sample");
        mViews.mToolbar.inflateMenu(R.menu.place_predictions_menu);

        SearchView searchView = (SearchView) mViews.mToolbar.getMenu()
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
                mViews.mProgress.setIndeterminate(true);

                // Cancel any previous place prediction requests
                mHandler.removeCallbacksAndMessages(null);

                // Start a new place prediction request in 300 ms
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getPlacePredictions(newText);
                    }
                }, 300);
                return true;
            }
        });


        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mViews.mList.setLayoutManager(layoutManager);
        mViews.mList.setAdapter(mAdapter);
        mViews.mList.addItemDecoration(
                new DividerItemDecoration(this, layoutManager.getOrientation()));
    }

    private void getPlacePredictions(String query) {
        final FindAutocompletePredictionsRequest newRequest =
                new FindAutocompletePredictionsRequest.Builder()
                        .setTypeFilter(TypeFilter.ESTABLISHMENT)
                        .setQuery(query)
                        .setCountry("US")
                        .build();

        mPlacesClient.findAutocompletePredictions(newRequest)
                .addOnSuccessListener(
                        new OnSuccessListener<FindAutocompletePredictionsResponse>() {
                            @Override
                            public void onSuccess(
                                    @Nullable FindAutocompletePredictionsResponse result) {
                                List<AutocompletePrediction> predictions =
                                        result == null ? null : result.getAutocompletePredictions();
                                mViews.mError.setVisibility(View.INVISIBLE);
                                mViews.mEmpty.setVisibility(
                                        (predictions == null || predictions.isEmpty())
                                                ? View.VISIBLE
                                                : View.INVISIBLE
                                );
                                mAdapter.setPredictions(predictions);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                mViews.mError.setVisibility(View.VISIBLE);
                                mAdapter.setPredictions(null);
                                Log.e(TAG, Log.getStackTraceString(exception));
                            }
                        })
                .addOnCompleteListener(
                        new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<FindAutocompletePredictionsResponse> task) {
                                mViews.mProgress.setIndeterminate(false);
                            }
                        });
    }


    private static final class ViewHolder {
        final Toolbar mToolbar;
        final ProgressBar mProgress;
        final RecyclerView mList;
        final View mEmpty;
        final View mError;

        ViewHolder(@NonNull Activity activity) {
            mToolbar = activity.findViewById(R.id.toolbar);
            mProgress = activity.findViewById(R.id.progress);
            mList = activity.findViewById(R.id.list);
            mEmpty = activity.findViewById(R.id.empty);
            mError = activity.findViewById(R.id.error);
        }
    }

}
