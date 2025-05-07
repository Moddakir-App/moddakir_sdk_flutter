package com.moddakir.call.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.moddakir.call.Connectivity;


public class ConnectionStateMonitor extends LiveData<Integer> {

    private Context mContext;
    private ConnectivityManager.NetworkCallback networkCallback = null;
 //   private NetworkReceiver networkReceiver;
    private ConnectivityManager connectivityManager;

    public ConnectionStateMonitor(Context context) {
        mContext = context;
        connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            networkCallback = new NetworkCallback(this);
        } else {
          //  networkReceiver = new NetworkReceiver();
        }
    }

    @Override
    protected void onActive() {
        super.onActive();
        updateConnection();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            NetworkRequest networkRequest = new NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .build();
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback);

        } else {
           // mContext.registerReceiver(networkReceiver, new IntentFilter("Update"));
        }
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        } else {
          //  mContext.unregisterReceiver(networkReceiver);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    class NetworkCallback extends ConnectivityManager.NetworkCallback {
        private ConnectionStateMonitor mConnectionStateMonitor;

        public NetworkCallback(ConnectionStateMonitor connectionStateMonitor) {
            mConnectionStateMonitor = connectionStateMonitor;
        }

        @Override
        public void onAvailable(Network network) {
            if (network != null) {
                mConnectionStateMonitor.postValue(2);
            }
        }

        @Override
        public void onLost(Network network) {
            mConnectionStateMonitor.postValue(0);
        }

        @Override
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            Log.e("Donw", networkCapabilities.getLinkDownstreamBandwidthKbps() + "");
            if (networkCapabilities.getLinkDownstreamBandwidthKbps() > 500) {
                postValue(2);
            } else if (networkCapabilities.getLinkDownstreamBandwidthKbps() < 20) {
                postValue(0);
            } else if (networkCapabilities.getLinkDownstreamBandwidthKbps() < 500) {
                postValue(1);
            }
        }
    }

    private void updateConnection() {
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                postValue(Connectivity.isConnectionFast(activeNetwork.getType(), activeNetwork.getSubtype()));
            } else {
                postValue(0);
            }
        }
    }

    class NetworkReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("Update")) {
                updateConnection();
            }
        }
    }
}