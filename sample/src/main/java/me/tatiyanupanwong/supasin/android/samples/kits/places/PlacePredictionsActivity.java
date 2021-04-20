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

package me.tatiyanupanwong.supasin.android.samples.kits.places;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

import me.tatiyanupanwong.supasin.android.libraries.kits.places.PlaceKit;
import me.tatiyanupanwong.supasin.android.libraries.kits.places.model.AutocompletePrediction;
import me.tatiyanupanwong.supasin.android.libraries.kits.places.model.TypeFilter;
import me.tatiyanupanwong.supasin.android.libraries.kits.places.net.FindAutocompletePredictionsRequest;
import me.tatiyanupanwong.supasin.android.libraries.kits.places.net.FindAutocompletePredictionsResponse;
import me.tatiyanupanwong.supasin.android.libraries.kits.places.net.PlacesClient;
import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.Task;
import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.listeners.OnCompleteListener;
import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.listeners.OnFailureListener;
import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.listeners.OnSuccessListener;

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

        mViews.toolbar.setTitle("Place Kit Sample");
        mViews.toolbar.inflateMenu(R.menu.place_predictions_menu);

        SearchView searchView = (SearchView) mViews.toolbar.getMenu()
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
                mViews.progress.setIndeterminate(true);

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
        mViews.list.setLayoutManager(layoutManager);
        mViews.list.setAdapter(mAdapter);
        mViews.list.addItemDecoration(
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
                                mAdapter.setPredictions(predictions);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Log.e(TAG, Log.getStackTraceString(exception));
                            }
                        })
                .addOnCompleteListener(
                        new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<FindAutocompletePredictionsResponse> task) {
                                mViews.progress.setIndeterminate(false);
                            }
                        });
    }


    private static final class ViewHolder {
        final Toolbar toolbar;
        final ProgressBar progress;
        final RecyclerView list;

        ViewHolder(@NonNull Activity activity) {
            toolbar = activity.findViewById(R.id.toolbar);
            progress = activity.findViewById(R.id.progress);
            list = activity.findViewById(R.id.list);
        }
    }

}
