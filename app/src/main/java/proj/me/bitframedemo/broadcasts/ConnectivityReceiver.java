package proj.me.bitframedemo.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import proj.me.bitframe.helper.Utils;
import proj.me.bitframedemo.helper.Constants;

/**
 * Created by root on 17/9/16.
 */

public class ConnectivityReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = Utils.hasNetwork(context.getApplicationContext());

        if(isConnected){
            Utils.logError("connected");
            Intent notifyActivity = new Intent(Constants.NETWORK_FILTER);
            notifyActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            notifyActivity.putExtra("cause", Constants.NETWORK_ENABLED);
            context.startActivity(notifyActivity);
        }
    }
}
