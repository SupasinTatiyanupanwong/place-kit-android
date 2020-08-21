/*
 * Copyright 2020 Supasin Tatiyanupanwong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.tatiyanupanwong.supasin.android.libraries.kits.internal.google.tasks;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import java.util.concurrent.Executor;

import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.Task;
import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.internal.ResultInterceptor;
import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.listeners.OnCanceledListener;
import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.listeners.OnCompleteListener;
import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.listeners.OnFailureListener;
import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.listeners.OnSuccessListener;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP_PREFIX;

@RestrictTo(LIBRARY_GROUP_PREFIX)
public final class GoogleTask<RawResult, Result> implements Task<Result> {

    private final com.google.android.gms.tasks.Task<RawResult> mDelegate;
    private final ResultInterceptor<RawResult, Result> mResultInterceptor;

    private Result mResult;

    public GoogleTask(
            @NonNull com.google.android.gms.tasks.Task<RawResult> delegate,
            @NonNull ResultInterceptor<RawResult, Result> resultInterceptor) {
        mDelegate = delegate;
        mResultInterceptor = resultInterceptor;
    }

    @Override
    public boolean isComplete() {
        return mDelegate.isComplete();
    }

    @Override
    public boolean isSuccessful() {
        return mDelegate.isSuccessful();
    }

    @Override
    public boolean isCanceled() {
        return mDelegate.isCanceled();
    }

    @Override
    public @Nullable Result getResult() {
        return mResult;
    }

    @Override
    public @Nullable Exception getException() {
        return mDelegate.getException();
    }

    @Override
    public @NonNull Task<Result> addOnSuccessListener(
            @NonNull final OnSuccessListener<Result> listener) {
        mDelegate.addOnSuccessListener(
                new com.google.android.gms.tasks.OnSuccessListener<RawResult>() {
                    @Override
                    public void onSuccess(RawResult result) {
                        mResult = mResultInterceptor.intercept(result);
                        listener.onSuccess(mResult);
                    }
                });
        return this;
    }

    @Override
    public @NonNull Task<Result> addOnSuccessListener(
            @NonNull Executor executor,
            @NonNull final OnSuccessListener<Result> listener) {
        mDelegate.addOnSuccessListener(
                executor,
                new com.google.android.gms.tasks.OnSuccessListener<RawResult>() {
                    @Override
                    public void onSuccess(RawResult result) {
                        mResult = mResultInterceptor.intercept(result);
                        listener.onSuccess(mResult);
                    }
                });
        return this;
    }

    @Override
    public @NonNull Task<Result> addOnSuccessListener(
            @NonNull Activity activity,
            @NonNull final OnSuccessListener<Result> listener) {
        mDelegate.addOnSuccessListener(
                activity,
                new com.google.android.gms.tasks.OnSuccessListener<RawResult>() {
                    @Override
                    public void onSuccess(RawResult result) {
                        mResult = mResultInterceptor.intercept(result);
                        listener.onSuccess(mResult);
                    }
                });
        return this;
    }

    @Override
    public @NonNull Task<Result> addOnFailureListener(
            @NonNull final OnFailureListener listener) {
        mDelegate.addOnFailureListener(
                new com.google.android.gms.tasks.OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        listener.onFailure(exception);
                    }
                });
        return this;
    }

    @Override
    public @NonNull Task<Result> addOnFailureListener(
            @NonNull Executor executor,
            @NonNull final OnFailureListener listener) {
        mDelegate.addOnFailureListener(
                executor,
                new com.google.android.gms.tasks.OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        listener.onFailure(exception);
                    }
                });
        return this;
    }

    @Override
    public @NonNull Task<Result> addOnFailureListener(
            @NonNull Activity activity,
            @NonNull final OnFailureListener listener) {
        mDelegate.addOnFailureListener(
                activity,
                new com.google.android.gms.tasks.OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        listener.onFailure(exception);
                    }
                });
        return this;
    }

    @Override
    public @NonNull Task<Result> addOnCompleteListener(
            @NonNull final OnCompleteListener<Result> listener) {
        mDelegate.addOnCompleteListener(
                new com.google.android.gms.tasks.OnCompleteListener<RawResult>() {
                    @Override
                    public void onComplete(
                            @NonNull com.google.android.gms.tasks.Task<RawResult> task) {
                        mResult = mResultInterceptor.intercept(task.getResult());
                        listener.onComplete(GoogleTask.this);
                    }
                });
        return this;
    }

    @Override
    public @NonNull Task<Result> addOnCompleteListener(
            @NonNull Executor executor,
            @NonNull final OnCompleteListener<Result> listener) {
        mDelegate.addOnCompleteListener(
                executor,
                new com.google.android.gms.tasks.OnCompleteListener<RawResult>() {
                    @Override
                    public void onComplete(
                            @NonNull com.google.android.gms.tasks.Task<RawResult> task) {
                        mResult = mResultInterceptor.intercept(task.getResult());
                        listener.onComplete(GoogleTask.this);
                    }
                });
        return this;
    }

    @Override
    public @NonNull Task<Result> addOnCompleteListener(
            @NonNull Activity activity,
            @NonNull final OnCompleteListener<Result> listener) {
        mDelegate.addOnCompleteListener(
                activity,
                new com.google.android.gms.tasks.OnCompleteListener<RawResult>() {
                    @Override
                    public void onComplete(
                            @NonNull com.google.android.gms.tasks.Task<RawResult> task) {
                        mResult = mResultInterceptor.intercept(task.getResult());
                        listener.onComplete(GoogleTask.this);
                    }
                });
        return this;
    }

    @Override
    public @NonNull Task<Result> addOnCanceledListener(
            @NonNull final OnCanceledListener listener) {
        mDelegate.addOnCanceledListener(
                new com.google.android.gms.tasks.OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        listener.onCanceled();
                    }
                });
        return this;
    }

    @Override
    public @NonNull Task<Result> addOnCanceledListener(
            @NonNull Executor executor,
            @NonNull final OnCanceledListener listener) {
        mDelegate.addOnCanceledListener(
                executor,
                new com.google.android.gms.tasks.OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        listener.onCanceled();
                    }
                });
        return this;
    }

    @Override
    public @NonNull Task<Result> addOnCanceledListener(
            @NonNull Activity activity,
            @NonNull final OnCanceledListener listener) {
        mDelegate.addOnCanceledListener(
                activity,
                new com.google.android.gms.tasks.OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        listener.onCanceled();
                    }
                });
        return this;
    }

}
