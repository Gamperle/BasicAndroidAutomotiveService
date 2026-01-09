package com.example.mathservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Math Service implementation
 * Provides basic arithmetic operations through AIDL interface
 */
public class MathService extends Service {
    private static final String TAG = "MathService";

    private final IMathService.Stub mBinder = new IMathService.Stub() {
        @Override
        public int add(int a, int b) throws RemoteException {
            Log.d(TAG, "add() called: " + a + " + " + b);
            return a + b;
        }

        @Override
        public int subtract(int a, int b) throws RemoteException {
            Log.d(TAG, "subtract() called: " + a + " - " + b);
            return a - b;
        }

        @Override
        public String getInterfaceHash() {
            return IMathService.HASH;
        }

        @Override
        public int getInterfaceVersion() {
            return IMathService.VERSION;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "MathService created");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "MathService bound");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "MathService unbound");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "MathService destroyed");
    }
}
