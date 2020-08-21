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

package me.tatiyanupanwong.supasin.android.libraries.kits.tasks;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.Executor;

import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.listeners.OnCanceledListener;
import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.listeners.OnCompleteListener;
import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.listeners.OnFailureListener;
import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.listeners.OnSuccessListener;

/**
 * Represents an asynchronous operation.
 *
 * <p/><h2>Handling task results</h2>
 *
 * <p>To be notified when the task succeeds, attach an {@code OnSuccessListener}:
 *
 * <pre><code>
 * task.addOnSuccessListener(new OnSuccessListener<Result>() {
 *    {@literal @}Override
 *     public void onSuccess(Result result) {
 *         // Task completed successfully
 *         // ...
 *     }
 * });
 * </code></pre>
 *
 * <p>To be notified when the task fails, attach an {@code OnFailureListener}:
 *
 * <pre><code>
 * task.addOnFailureListener(new OnFailureListener() {
 *    {@literal @}Override
 *     public void onFailure(@NonNull Exception exception) {
 *         // Task failed with an exception
 *         // ...
 *     }
 * });
 * </code></pre>
 *
 * <p>To handle success and failure in the same listener, attach an {@code OnCompleteListener}:
 *
 * <pre><code>
 * task.addOnCompleteListener(new OnCompleteListener<Result>() {
 *    {@literal @}Override
 *     public void onSuccess(Result result) {
 *         if (task.isSuccessful()) {
 *             // Task completed successfully
 *             Result result = task.getResult();
 *         } else {
 *             // Task failed with an exception
 *             Exception exception = task.getException();
 *         }
 *     }
 * });
 * </code></pre>
 *
 * <p/><h2>Threading</h2>
 *
 * <p>Listeners attached to a thread are run on the application main (UI) thread by default. When
 * attaching a listener, you can also specify an {@code Executor} that is used to schedule
 * listeners.
 *
 * <pre><code>
 * // Create a new ThreadPoolExecutor with 2 threads for each processor on the
 * // device and a 60 second keep-alive time.
 * int numCores = Runtime.getRuntime().availableProcessors();
 * ThreadPoolExecutor executor = new ThreadPoolExecutor(numCores * 2, numCores *2,
 *         60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
 *
 * task.addOnCompleteListener(executor, new OnCompleteListener<Result>() {
 *    {@literal @}Override
 *     public void onComplete(@NonNull Task<Result> task) {
 *         // ...
 *     }
 * });
 * </code></pre>
 *
 * <p/><h2>Activity-scoped listeners</h2>
 *
 * <p>If you are listening for task results in an {@code Activity}, you may want to add
 * activity-scoped listeners to the task. These listeners are removed during the onStop method of
 * your Activity so that your listeners are not called when the Activity is no longer visible.
 *
 * <pre><code>
 * Activity activity = MainActivity.this;
 * task.addOnCompleteListener(activity, new OnCompleteListener<Result>() {
 *    {@literal @}Override
 *     public void onComplete(@NonNull Task<Result> task) {
 *         // ...
 *     }
 * });
 * </code></pre>
 */
public interface Task<R> {

    /**
     * Returns {@code true} if the Task is complete; {@code false} otherwise.
     *
     * @return {@code true} if the Task is complete; {@code false} otherwise.
     */
    boolean isComplete();

    /**
     * Returns {@code true} if the Task has completed successfully; {@code false} otherwise.
     *
     * @return {@code true} if the Task has completed successfully; {@code false} otherwise.
     */
    boolean isSuccessful();

    /**
     * Returns {@code true} if the Task is canceled; {@code false} otherwise.
     *
     * @return {@code true} if the Task is canceled; {@code false} otherwise.
     */
    boolean isCanceled();

    /**
     * Gets the result of the Task, if it has already completed successfully.
     *
     * @return the result of the Task, if it has already completed successfully.
     * @throws IllegalStateException if the Task is not yet complete or failed with an exception
     */
    @Nullable R getResult();

    /**
     * Returns the exception that caused the Task to fail. Returns {@code null} if the Task is
     * not yet complete, or completed successfully.
     */
    @Nullable Exception getException();

    /**
     * Adds a listener that is called if the Task completes successfully.
     * <p>
     * The listener will be called on the main application thread. If the Task has already
     * completed successfully, a call to the listener will be immediately scheduled. If multiple
     * listeners are added, they will be called in the order in which they were added.
     *
     * @param listener the listener to be called.
     * @return this Task instance for method chaining.
     */
    @NonNull Task<R> addOnSuccessListener(@NonNull OnSuccessListener<R> listener);

    /**
     * Adds a listener that is called on the specified executor if the Task completes successfully.
     * <p>
     * If the Task has already completed successfully, a call to the listener will be immediately
     * scheduled. If multiple listeners are added, they will be called in the order in which they
     * were added.
     *
     * @param executor the executor to use to call the listener.
     * @param listener the listener to be called.
     * @return this Task instance for method chaining.
     */
    @NonNull Task<R> addOnSuccessListener(
            @NonNull Executor executor,
            @NonNull OnSuccessListener<R> listener);

    /**
     * Adds an Activity-scoped listener that is called if the Task completes successfully.
     * <p>
     * The listener will be called on the main application thread. If the Task has already completed
     * successfully, a call to the listener will be immediately scheduled. If multiple listeners are
     * added, they will be called in the order in which they were added.
     * <p>
     * The listener will be automatically removed during {@code Activity.onStop()}.
     *
     * @param activity the activity that the listener will be scoped to.
     * @param listener the listener to be called.
     * @return this Task instance for method chaining.
     */
    @NonNull Task<R> addOnSuccessListener(
            @NonNull Activity activity,
            @NonNull OnSuccessListener<R> listener);

    /**
     * Adds a listener that is called if the Task fails.
     * <p>
     * The listener will be called on the main application thread. If the Task has already failed,
     * a call to the listener will be immediately scheduled. If multiple listeners are added, they
     * will be called in the order in which they were added.
     *
     * @param listener the listener to be called.
     * @return this Task instance for method chaining.
     */
    @NonNull Task<R> addOnFailureListener(@NonNull OnFailureListener listener);

    /**
     * Adds a listener that is called on the specified executor if the Task fails.
     * <p>
     * If the Task has already failed, a call to the listener will be immediately scheduled.
     * If multiple listeners are added, they will be called in the order in which they were added.
     *
     * @param executor the executor to use to call the listener.
     * @param listener the listener to be called.
     * @return this Task instance for method chaining.
     */
    @NonNull Task<R> addOnFailureListener(
            @NonNull Executor executor,
            @NonNull OnFailureListener listener);

    /**
     * Adds an Activity-scoped listener that is called if the Task fails.
     * <p>
     * The listener will be called on the main application thread. If the Task has already failed,
     * a call to the listener will be immediately scheduled. If multiple listeners are added, they
     * will be called in the order in which they were added.
     * <p>
     * The listener will be automatically removed during {@code Activity.onStop()}.
     *
     * @param activity the activity that the listener will be scoped to.
     * @param listener the listener to be called.
     * @return this Task instance for method chaining.
     */
    @NonNull Task<R> addOnFailureListener(
            @NonNull Activity activity,
            @NonNull OnFailureListener listener);

    /**
     * Adds a listener that is called if the Task completes.
     * <p>
     * The listener will be called on the main application thread. If the Task is already complete,
     * a call to the listener will be immediately scheduled. If multiple listeners are added, they
     * will be called in the order in which they were added.
     *
     * @param listener the listener to be called.
     * @return this Task instance for method chaining.
     */
    @NonNull Task<R> addOnCompleteListener(@NonNull OnCompleteListener<R> listener);

    /**
     * Adds a listener that is called on the specified executor if the Task completes.
     * <p>
     * If the Task is already complete, a call to the listener will be immediately scheduled.
     * If multiple listeners are added, they will be called in the order in which they were added.
     *
     * @param executor the executor to use to call the listener.
     * @param listener the listener to be called.
     * @return this Task instance for method chaining.
     */
    @NonNull Task<R> addOnCompleteListener(
            @NonNull Executor executor,
            @NonNull OnCompleteListener<R> listener);

    /**
     * Adds an Activity-scoped listener that is called if the Task completes.
     * <p>
     * The listener will be called on the main application thread. If the Task is already complete,
     * a call to the listener will be immediately scheduled. If multiple listeners are added, they
     * will be called in the order in which they were added.
     * <p>
     * The listener will be automatically removed during {@code Activity.onStop()}.
     *
     * @param activity the activity that the listener will be scoped to.
     * @param listener the listener to be called.
     * @return this Task instance for method chaining.
     */
    @NonNull Task<R> addOnCompleteListener(
            @NonNull Activity activity,
            @NonNull OnCompleteListener<R> listener);

    /**
     * Adds a listener that is called if the Task is canceled.
     * <p>
     * The listener will be called on the main application thread. If the Task has already been
     * canceled, a call to the listener will be immediately scheduled. If multiple listeners are
     * added, they will be called in the order in which they were added.
     *
     * @param listener the listener to be called.
     * @return this Task instance for method chaining.
     */
    @NonNull Task<R> addOnCanceledListener(@NonNull OnCanceledListener listener);

    /**
     * Adds a listener that is called on the specified executor if the Task is canceled.
     * <p>
     * If the Task has already been canceled, a call to the listener will be immediately scheduled.
     * If multiple listeners are added, they will be called in the order in which they were added.
     *
     * @param executor the executor to use to call the listener.
     * @param listener the listener to be called.
     * @return this Task instance for method chaining.
     */
    @NonNull Task<R> addOnCanceledListener(
            @NonNull Executor executor,
            @NonNull OnCanceledListener listener);

    /**
     * Adds an Activity-scoped listener that is called if the Task is canceled.
     * <p>
     * The listener will be called on the main application thread. If the Task has already been
     * canceled, a call to the listener will be immediately scheduled. If multiple listeners are
     * added, they will be called in the order in which they were added.
     * <p>
     * The listener will be automatically removed during {@code Activity.onStop()}.
     *
     * @param activity the activity that the listener will be scoped to.
     * @param listener the listener to be called.
     * @return this Task instance for method chaining.
     */
    @NonNull Task<R> addOnCanceledListener(
            @NonNull Activity activity,
            @NonNull OnCanceledListener listener);

}
