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

package me.tatiyanupanwong.supasin.android.libraries.kits.internal.huawei.tasks;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.Task;
import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.internal.AndroidMainThreadExecutor;
import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.internal.ResultInterceptor;
import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.listeners.OnCanceledListener;
import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.listeners.OnCompleteListener;
import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.listeners.OnFailureListener;
import me.tatiyanupanwong.supasin.android.libraries.kits.tasks.listeners.OnSuccessListener;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP_PREFIX;

@RestrictTo(LIBRARY_GROUP_PREFIX)
public final class HuaweiTask<RawResult, Result> implements Task<Result> {

    private static final Executor EXECUTORS = Executors.newCachedThreadPool();

    private final Task<Result> mImpl;

    public HuaweiTask(@NonNull Future<Result> future) {
        mImpl = new HuaweiFutureTask<>(this, future);
    }

    public HuaweiTask(
            @NonNull com.huawei.hmf.tasks.Task<RawResult> delegate,
            @NonNull ResultInterceptor<RawResult, Result> resultInterceptor) {
        mImpl = new HuaweiDelegateTask<>(this, delegate, resultInterceptor);
    }

    @Override
    public boolean isComplete() {
        return mImpl.isComplete();
    }

    @Override
    public boolean isSuccessful() {
        return mImpl.isSuccessful();
    }

    @Override
    public boolean isCanceled() {
        return mImpl.isCanceled();
    }

    @Override
    public @Nullable Result getResult() {
        return mImpl.getResult();
    }

    @Override
    public @Nullable Exception getException() {
        return mImpl.getException();
    }

    @Override
    public @NonNull Task<Result> addOnSuccessListener(
            @NonNull final OnSuccessListener<Result> listener) {
        return mImpl.addOnSuccessListener(listener);
    }

    @Override
    public @NonNull Task<Result> addOnSuccessListener(
            @NonNull Executor executor,
            @NonNull final OnSuccessListener<Result> listener) {
        return mImpl.addOnSuccessListener(executor, listener);
    }

    @Override
    public @NonNull Task<Result> addOnSuccessListener(
            @NonNull Activity activity,
            @NonNull final OnSuccessListener<Result> listener) {
        return mImpl.addOnSuccessListener(activity, listener);
    }

    @Override
    public @NonNull Task<Result> addOnFailureListener(
            @NonNull final OnFailureListener listener) {
        return mImpl.addOnFailureListener(listener);
    }

    @Override
    public @NonNull Task<Result> addOnFailureListener(
            @NonNull Executor executor,
            @NonNull final OnFailureListener listener) {
        return mImpl.addOnFailureListener(executor, listener);
    }

    @Override
    public @NonNull Task<Result> addOnFailureListener(
            @NonNull Activity activity,
            @NonNull final OnFailureListener listener) {
        return mImpl.addOnFailureListener(activity, listener);
    }

    @Override
    public @NonNull Task<Result> addOnCompleteListener(
            @NonNull final OnCompleteListener<Result> listener) {
        return mImpl.addOnCompleteListener(listener);
    }

    @Override
    public @NonNull Task<Result> addOnCompleteListener(
            @NonNull Executor executor,
            @NonNull final OnCompleteListener<Result> listener) {
        return mImpl.addOnCompleteListener(executor, listener);
    }

    @Override
    public @NonNull Task<Result> addOnCompleteListener(
            @NonNull Activity activity,
            @NonNull final OnCompleteListener<Result> listener) {
        return mImpl.addOnCompleteListener(activity, listener);
    }

    @Override
    public @NonNull Task<Result> addOnCanceledListener(
            @NonNull final OnCanceledListener listener) {
        return mImpl.addOnCanceledListener(listener);
    }

    @Override
    public @NonNull Task<Result> addOnCanceledListener(
            @NonNull Executor executor,
            @NonNull final OnCanceledListener listener) {
        return mImpl.addOnCanceledListener(executor, listener);
    }

    @Override
    public @NonNull Task<Result> addOnCanceledListener(
            @NonNull Activity activity,
            @NonNull final OnCanceledListener listener) {
        return mImpl.addOnCanceledListener(activity, listener);
    }


    private static final class HuaweiFutureTask<RawResult, Result> implements Task<Result> {
        private final @NonNull Object mLock = new Object();

        private final @NonNull List<OnSuccessListener<Result>>
                mOnSuccessListeners = new ArrayList<>();
        private final @NonNull List<OnFailureListener>
                mOnFailureListeners = new ArrayList<>();
        private final @NonNull List<OnCompleteListener<Result>>
                mOnCompleteListeners = new ArrayList<>();
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection") // Not supported yet
        private final @NonNull List<OnCanceledListener>
                mOnCanceledListeners = new ArrayList<>();

        private final @NonNull HuaweiTask<RawResult, Result> mDelegate;

        private boolean mIsComplete;
        private boolean mIsSuccessful;
        @SuppressWarnings("unused") // Not supported yet
        private boolean mIsCanceled;

        private @Nullable Result mResult;
        private @Nullable Exception mException;

        HuaweiFutureTask(
                @NonNull HuaweiTask<RawResult, Result> delegate,
                final @NonNull Future<Result> future) {
            mDelegate = delegate;

            EXECUTORS.execute(new Runnable() {
                @Override
                public void run() {
                    synchronized (mLock) {
                        try {
                            mResult = future.get();
                            mIsSuccessful = true;
                            mIsComplete = true;
                            for (OnSuccessListener<Result> listener : mOnSuccessListeners) {
                                listener.onSuccess(mResult);
                            }
                            for (OnCompleteListener<Result> listener : mOnCompleteListeners) {
                                listener.onComplete(mDelegate);
                            }
                        } catch (Exception exception) {
                            mException = exception;
                            mIsComplete = true;
                            for (OnFailureListener listener : mOnFailureListeners) {
                                listener.onFailure(mException);
                            }
                            for (OnCompleteListener<Result> listener : mOnCompleteListeners) {
                                listener.onComplete(mDelegate);
                            }
                        }
                    }
                }
            });
        }

        @Override
        public boolean isComplete() {
            synchronized (mLock) {
                return mIsComplete;
            }
        }

        @Override
        public boolean isSuccessful() {
            synchronized (mLock) {
                return mIsSuccessful;
            }
        }

        @Override
        public boolean isCanceled() {
            synchronized (mLock) {
                return mIsCanceled;
            }
        }

        @Override
        public @Nullable Result getResult() {
            synchronized (mLock) {
                return mResult;
            }
        }

        @Override
        public @Nullable Exception getException() {
            synchronized (mLock) {
                return mException;
            }
        }

        @Override
        public @NonNull Task<Result> addOnSuccessListener(
                final @NonNull OnSuccessListener<Result> listener) {
            mOnSuccessListeners.add(new OnSuccessListener<Result>() {
                @Override
                public void onSuccess(final @Nullable Result result) {
                    AndroidMainThreadExecutor.getInstance().execute(new Runnable() {
                        @Override
                        public void run() {
                            listener.onSuccess(result);
                        }
                    });
                }
            });
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnSuccessListener(
                final @NonNull Executor executor,
                final @NonNull OnSuccessListener<Result> listener) {
            mOnSuccessListeners.add(new OnSuccessListener<Result>() {
                @Override
                public void onSuccess(final @Nullable Result result) {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            listener.onSuccess(result);
                        }
                    });
                }
            });
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnSuccessListener(
                @NonNull Activity activity,
                final @NonNull OnSuccessListener<Result> listener) {
            final @NonNull OnSuccessListener<Result> delegate = new OnSuccessListener<Result>() {
                @Override
                public void onSuccess(final @Nullable Result result) {
                    AndroidMainThreadExecutor.getInstance().execute(new Runnable() {
                        @Override
                        public void run() {
                            listener.onSuccess(result);
                        }
                    });
                }
            };
            mOnSuccessListeners.add(delegate);
            if (activity instanceof LifecycleOwner) {
                LifecycleOwner lifecycleOwner = (LifecycleOwner) activity;
                lifecycleOwner.getLifecycle().addObserver(new LifecycleObserver() {
                    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
                    void onStop() {
                        mOnSuccessListeners.remove(delegate);
                    }
                });
            }
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnFailureListener(
                final @NonNull OnFailureListener listener) {
            mOnFailureListeners.add(new OnFailureListener() {
                @Override
                public void onFailure(final @NonNull Exception exception) {
                    AndroidMainThreadExecutor.getInstance().execute(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFailure(exception);
                        }
                    });
                }
            });
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnFailureListener(
                final @NonNull Executor executor,
                final @NonNull OnFailureListener listener) {
            mOnFailureListeners.add(new OnFailureListener() {
                @Override
                public void onFailure(final @NonNull Exception exception) {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFailure(exception);
                        }
                    });
                }
            });
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnFailureListener(
                @NonNull Activity activity,
                final @NonNull OnFailureListener listener) {
            final @NonNull OnFailureListener delegate = new OnFailureListener() {
                @Override
                public void onFailure(final @NonNull Exception exception) {
                    AndroidMainThreadExecutor.getInstance().execute(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFailure(exception);
                        }
                    });
                }
            };
            mOnFailureListeners.add(delegate);
            if (activity instanceof LifecycleOwner) {
                LifecycleOwner lifecycleOwner = (LifecycleOwner) activity;
                lifecycleOwner.getLifecycle().addObserver(new LifecycleObserver() {
                    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
                    void onStop() {
                        mOnFailureListeners.remove(delegate);
                    }
                });
            }
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnCompleteListener(
                final @NonNull OnCompleteListener<Result> listener) {
            mOnCompleteListeners.add(new OnCompleteListener<Result>() {
                @Override
                public void onComplete(final @NonNull Task<Result> task) {
                    AndroidMainThreadExecutor.getInstance().execute(new Runnable() {
                        @Override
                        public void run() {
                            listener.onComplete(mDelegate);
                        }
                    });
                }
            });
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnCompleteListener(
                final @NonNull Executor executor,
                final @NonNull OnCompleteListener<Result> listener) {
            mOnCompleteListeners.add(new OnCompleteListener<Result>() {
                @Override
                public void onComplete(final @NonNull Task<Result> task) {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            listener.onComplete(mDelegate);
                        }
                    });
                }
            });
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnCompleteListener(
                @NonNull Activity activity,
                final @NonNull OnCompleteListener<Result> listener) {
            final @NonNull OnCompleteListener<Result> delegate = new OnCompleteListener<Result>() {
                @Override
                public void onComplete(final @NonNull Task<Result> task) {
                    AndroidMainThreadExecutor.getInstance().execute(new Runnable() {
                        @Override
                        public void run() {
                            listener.onComplete(mDelegate);
                        }
                    });
                }
            };
            mOnCompleteListeners.add(delegate);
            if (activity instanceof LifecycleOwner) {
                LifecycleOwner lifecycleOwner = (LifecycleOwner) activity;
                lifecycleOwner.getLifecycle().addObserver(new LifecycleObserver() {
                    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
                    void onStop() {
                        mOnCompleteListeners.remove(delegate);
                    }
                });
            }
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnCanceledListener(
                final @NonNull OnCanceledListener listener) {
            mOnCanceledListeners.add(new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    AndroidMainThreadExecutor.getInstance().execute(new Runnable() {
                        @Override
                        public void run() {
                            listener.onCanceled();
                        }
                    });
                }
            });
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnCanceledListener(
                final @NonNull Executor executor,
                final @NonNull OnCanceledListener listener) {
            mOnCanceledListeners.add(new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            listener.onCanceled();
                        }
                    });
                }
            });
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnCanceledListener(
                @NonNull Activity activity,
                final @NonNull OnCanceledListener listener) {
            final @NonNull OnCanceledListener delegate = new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    AndroidMainThreadExecutor.getInstance().execute(new Runnable() {
                        @Override
                        public void run() {
                            listener.onCanceled();
                        }
                    });
                }
            };
            mOnCanceledListeners.add(delegate);
            if (activity instanceof LifecycleOwner) {
                LifecycleOwner lifecycleOwner = (LifecycleOwner) activity;
                lifecycleOwner.getLifecycle().addObserver(new LifecycleObserver() {
                    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
                    void onStop() {
                        mOnCanceledListeners.remove(delegate);
                    }
                });
            }
            return mDelegate;
        }
    }

    private static final class HuaweiDelegateTask<RawResult, Result> implements Task<Result> {
        private final @NonNull HuaweiTask<RawResult, Result> mDelegate;
        private final @NonNull com.huawei.hmf.tasks.Task<RawResult> mResultDelegate;
        private final @NonNull ResultInterceptor<RawResult, Result> mResultInterceptor;

        private boolean mIsComplete;
        private boolean mIsSuccessful;
        private boolean mIsCanceled;

        private @Nullable Result mResult;
        private @Nullable Exception mException;

        HuaweiDelegateTask(
                @NonNull HuaweiTask<RawResult, Result> delegate,
                @NonNull com.huawei.hmf.tasks.Task<RawResult> resultDelegate,
                @NonNull ResultInterceptor<RawResult, Result> resultInterceptor) {
            mDelegate = delegate;
            mResultDelegate = resultDelegate;
            mResultInterceptor = resultInterceptor;

            addOnCompleteListener(new OnCompleteListener<Result>() {
                @Override
                public void onComplete(@NonNull Task<Result> task) {
                    if (task.isSuccessful()) {
                        mIsSuccessful = true;
                        mResult = task.getResult();
                    } else {
                        mException = task.getException();
                    }
                    mIsComplete = true;
                }
            });
            addOnCanceledListener(new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    mIsCanceled = true;
                }
            });
        }

        @Override
        public boolean isComplete() {
            return mIsComplete;
        }

        @Override
        public boolean isSuccessful() {
            return mIsSuccessful;
        }

        @Override
        public boolean isCanceled() {
            return mIsCanceled;
        }

        @Override
        public @Nullable Result getResult() {
            return mResult;
        }

        @Override
        public @Nullable Exception getException() {
            return mException;
        }

        @Override
        public @NonNull Task<Result> addOnSuccessListener(
                @NonNull final OnSuccessListener<Result> listener) {
            mResultDelegate.addOnSuccessListener(
                    new com.huawei.hmf.tasks.OnSuccessListener<RawResult>() {
                        @Override
                        public void onSuccess(RawResult result) {
                            mResult = mResultInterceptor.intercept(result);
                            listener.onSuccess(mResult);
                        }
                    });
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnSuccessListener(
                @NonNull Executor executor,
                @NonNull final OnSuccessListener<Result> listener) {
            mResultDelegate.addOnSuccessListener(
                    executor,
                    new com.huawei.hmf.tasks.OnSuccessListener<RawResult>() {
                        @Override
                        public void onSuccess(RawResult result) {
                            mResult = mResultInterceptor.intercept(result);
                            listener.onSuccess(mResult);
                        }
                    });
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnSuccessListener(
                @NonNull Activity activity,
                @NonNull final OnSuccessListener<Result> listener) {
            mResultDelegate.addOnSuccessListener(
                    activity,
                    new com.huawei.hmf.tasks.OnSuccessListener<RawResult>() {
                        @Override
                        public void onSuccess(RawResult result) {
                            mResult = mResultInterceptor.intercept(result);
                            listener.onSuccess(mResult);
                        }
                    });
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnFailureListener(
                @NonNull final OnFailureListener listener) {
            mResultDelegate.addOnFailureListener(
                    new com.huawei.hmf.tasks.OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            listener.onFailure(exception);
                        }
                    });
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnFailureListener(
                @NonNull Executor executor,
                @NonNull final OnFailureListener listener) {
            mResultDelegate.addOnFailureListener(
                    executor,
                    new com.huawei.hmf.tasks.OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            listener.onFailure(exception);
                        }
                    });
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnFailureListener(
                @NonNull Activity activity,
                @NonNull final OnFailureListener listener) {
            mResultDelegate.addOnFailureListener(
                    activity,
                    new com.huawei.hmf.tasks.OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            listener.onFailure(exception);
                        }
                    });
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnCompleteListener(
                @NonNull final OnCompleteListener<Result> listener) {
            mResultDelegate.addOnCompleteListener(
                    new com.huawei.hmf.tasks.OnCompleteListener<RawResult>() {
                        @Override
                        public void onComplete(
                                @NonNull com.huawei.hmf.tasks.Task<RawResult> task) {
                            mResult = mResultInterceptor.intercept(task.getResult());
                            listener.onComplete(mDelegate);
                        }
                    });
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnCompleteListener(
                @NonNull Executor executor,
                @NonNull final OnCompleteListener<Result> listener) {
            mResultDelegate.addOnCompleteListener(
                    executor,
                    new com.huawei.hmf.tasks.OnCompleteListener<RawResult>() {
                        @Override
                        public void onComplete(
                                @NonNull com.huawei.hmf.tasks.Task<RawResult> task) {
                            mResult = mResultInterceptor.intercept(task.getResult());
                            listener.onComplete(mDelegate);
                        }
                    });
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnCompleteListener(
                @NonNull Activity activity,
                @NonNull final OnCompleteListener<Result> listener) {
            mResultDelegate.addOnCompleteListener(
                    activity,
                    new com.huawei.hmf.tasks.OnCompleteListener<RawResult>() {
                        @Override
                        public void onComplete(
                                @NonNull com.huawei.hmf.tasks.Task<RawResult> task) {
                            mResult = mResultInterceptor.intercept(task.getResult());
                            listener.onComplete(mDelegate);
                        }
                    });
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnCanceledListener(
                @NonNull final OnCanceledListener listener) {
            mResultDelegate.addOnCanceledListener(
                    new com.huawei.hmf.tasks.OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                            listener.onCanceled();
                        }
                    });
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnCanceledListener(
                @NonNull Executor executor,
                @NonNull final OnCanceledListener listener) {
            mResultDelegate.addOnCanceledListener(
                    executor,
                    new com.huawei.hmf.tasks.OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                            listener.onCanceled();
                        }
                    });
            return mDelegate;
        }

        @Override
        public @NonNull Task<Result> addOnCanceledListener(
                @NonNull Activity activity,
                @NonNull final OnCanceledListener listener) {
            mResultDelegate.addOnCanceledListener(
                    activity,
                    new com.huawei.hmf.tasks.OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                            listener.onCanceled();
                        }
                    });
            return mDelegate;
        }
    }

}
