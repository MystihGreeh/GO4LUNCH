package com.mystihgreeh.go4lunch.UtilsTest;

import static org.junit.Assert.fail;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LiveDataTestUtils {

    public static <T> void observeForTesting(LiveData<T> liveData, OnObservedListener<T> onObservedListener) {
        boolean[] called = {false};

        liveData.observeForever(value -> called[0] = true);

        onObservedListener.onObserved(liveData);

        if (!called[0]) {
            fail("LiveData didn't emit any value");
        }
    }

    public interface OnObservedListener<T> {
        void onObserved(LiveData<T> liveData);
    }
}
