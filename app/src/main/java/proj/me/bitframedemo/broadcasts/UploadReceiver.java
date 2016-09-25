package proj.me.bitframedemo.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import proj.me.bitframe.helper.Utils;
import proj.me.bitframedemo.helper.Constants;

/**
 * Created by root on 21/9/16.
 */

public class UploadReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Utils.logError("came in broadcast");
        switch(action){
            case Constants.UPLOAD_DONE:
                Utils.logError("came in broadcast upload done");
                Intent notifyActivity = new Intent(Constants.UPLOAD_FILTER);
                notifyActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                notifyActivity.putExtra("cause", Constants.UPLOAD_DONE);
                context.startActivity(notifyActivity);
                break;
        }
    }
}
