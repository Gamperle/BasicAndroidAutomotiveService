package com.example.mathservice.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.mathservice.IMathService;

/**
 * Example client for MathService
 * Demonstrates how to bind to and use the MathService
 */
public class MathServiceClient {
    private static final String TAG = "MathServiceClient";
    private static final String SERVICE_ACTION = "com.example.mathservice.IMathService";
    private static final String SERVICE_PACKAGE = "com.example.mathservice";

    private Context mContext;
    private IMathService mMathService;
    private boolean mBound = false;

    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "Service connected");
            mMathService = IMathService.Stub.asInterface(service);
            mBound = true;
            
            // Example usage
            try {
                int result1 = mMathService.add(10, 5);
                Log.d(TAG, "10 + 5 = " + result1);
                
                int result2 = mMathService.subtract(10, 5);
                Log.d(TAG, "10 - 5 = " + result2);
            } catch (RemoteException e) {
                Log.e(TAG, "Error calling service", e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "Service disconnected");
            mMathService = null;
            mBound = false;
        }
    };

    public MathServiceClient(Context context) {
        mContext = context;
    }

    /**
     * Bind to the MathService
     */
    public void bindService() {
        Intent intent = new Intent(SERVICE_ACTION);
        intent.setPackage(SERVICE_PACKAGE);
        boolean result = mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "Bind service result: " + result);
    }

    /**
     * Unbind from the MathService
     */
    public void unbindService() {
        if (mBound) {
            mContext.unbindService(mConnection);
            mBound = false;
        }
    }

    /**
     * Perform addition
     */
    public int add(int a, int b) throws RemoteException {
        if (!mBound || mMathService == null) {
            throw new IllegalStateException("Service not bound");
        }
        return mMathService.add(a, b);
    }

    /**
     * Perform subtraction
     */
    public int subtract(int a, int b) throws RemoteException {
        if (!mBound || mMathService == null) {
            throw new IllegalStateException("Service not bound");
        }
        return mMathService.subtract(a, b);
    }

    /**
     * Check if service is bound
     */
    public boolean isBound() {
        return mBound;
    }
}
