package com.sinodom.elevator.util.retrofit2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GUO on 2017/12/22.
 * Retrofit生命周期管理
 */

public class RetrofitManager {
    private final List<Call> mCallList;

    public RetrofitManager() {
        mCallList = new ArrayList<>();
    }

    public <T> void call(Call<T> call, Callback<T> callback) {
        add(call);
        call.enqueue(callback);
    }

    public <T> Response<T> call(Call<T> call) throws IOException {
        add(call);
        return call.execute();
    }

    public void cancelAll() {
        synchronized (mCallList) {
            Iterator<Call> iterator = mCallList.iterator();
            while (iterator.hasNext()) {
                Call call = iterator.next();
                if (call == null || call.isCanceled()) {
                    continue;
                }
                call.cancel();
                iterator.remove();
            }
        }
    }

    private void add(Call call) {
        synchronized (mCallList) {
            mCallList.add(call);
        }
    }
}
