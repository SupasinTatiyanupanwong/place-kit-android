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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.tatiyanupanwong.supasin.android.libraries.kits.places.model.AutocompletePrediction;

public class PlacePredictionsAdapter extends
        RecyclerView.Adapter<PlacePredictionsAdapter.ViewHolder> {

    private final @NonNull List<AutocompletePrediction> mPredictions = new ArrayList<>();


    @Override
    public @NonNull PlacePredictionsAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new PlacePredictionsAdapter.ViewHolder(
                inflater.inflate(R.layout.place_predictions_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlacePredictionsAdapter.ViewHolder holder, int position) {
        holder.setPrediction(mPredictions.get(position));
    }

    @Override
    public int getItemCount() {
        return mPredictions.size();
    }

    public void setPredictions(@Nullable List<AutocompletePrediction> predictions) {
        mPredictions.clear();
        if (predictions != null) {
            mPredictions.addAll(predictions);
        }
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTitle;
        private final TextView mAddress;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mAddress = itemView.findViewById(R.id.address);
        }

        void setPrediction(AutocompletePrediction prediction) {
            mTitle.setText(prediction.getPrimaryText());
            mAddress.setText(prediction.getSecondaryText());
        }
    }

}
