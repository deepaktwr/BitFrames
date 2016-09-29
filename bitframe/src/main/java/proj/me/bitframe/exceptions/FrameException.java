package proj.me.bitframe.exceptions;

import android.text.TextUtils;

import proj.me.bitframe.helper.Utils;

/**
 * Created by root on 29/9/16.
 */

public class FrameException extends Exception{
    public FrameException(String message){
        super(message);
        Utils.logVerbose(message);
    }

    public FrameException(String message, Throwable throwable){
        super(message, throwable);
        Utils.logVerbose(message);
    }

    public FrameException(Throwable throwable){
        super(throwable);
        String message = throwable.getMessage();
        if(!TextUtils.isEmpty(message)) Utils.logVerbose(message);
    }
}
