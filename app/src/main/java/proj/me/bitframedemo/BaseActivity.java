package proj.me.bitframedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;

import java.io.IOException;

import proj.me.bitframe.helper.Utils;
import proj.me.bitframedemo.beans.BaseResponse;
import proj.me.bitframedemo.broadcasts.ConnectivityReceiver;
import proj.me.bitframedemo.helper.Constants;
import proj.me.bitframedemo.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 17/9/16.
 */

public abstract class BaseActivity extends AppCompatActivity implements Callback {

    Call serviceCall;
    protected abstract void serverResponse(Object responseObject);
    protected abstract void changeLoaderStatus(boolean isLoading);
    protected abstract void networkFailure();
    protected abstract void reloadRequest();
    protected abstract void resetCallValue(int callValue);
    protected abstract void serverError(Object responseObject);

    SharedPreferences helperPref;

    protected boolean isServiceRunning;
    protected boolean isAcivityVisible;
    protected boolean isLayoutChanged;

    protected int lastRunningService;

    //saved instance
    private boolean isReceiverEnabled;
    protected boolean hasFailed;
    protected int serviceCallId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isAcivityVisible = true;
        if(savedInstanceState != null){
            hasFailed = savedInstanceState.getBoolean("has_failed");
            isReceiverEnabled = savedInstanceState.getBoolean("receiver_state");
            serviceCallId = savedInstanceState.getInt("service_call_id");
        }

        helperPref = getSharedPreferences("helper", MODE_PRIVATE);

        if(helperPref.getString("device_id", null) == null){
            SharedPreferences.Editor editor = helperPref.edit();
            editor.putString("device_id", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
            editor.apply();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        isAcivityVisible = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        changeLoaderStatus(false);
        if(serviceCall != null && !serviceCall.isCanceled() && !serviceCall.isExecuted()) serviceCall.cancel();
        isAcivityVisible = false;
        isServiceRunning = false;
        if(isReceiverEnabled) changeConnectivityReceiverState(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        isAcivityVisible = true;
    }

    protected void enqueueService(int runService){
        serviceCallId = runService;
        isServiceRunning = true;
        lastRunningService = runService;
        int callValue = 0;
        switch(runService){
            case Constants.SERVICE_CALL_1:
                callValue = Constants.RESET_CALL_1;
                break;
            case Constants.SERVICE_CALL_2:
                callValue = Constants.RESET_CALL_2;
                break;
            case Constants.SERVICE_CALL_3:
                callValue = Constants.RESET_CALL_3;
                break;
            case Constants.SERVICE_CALL_4:
                callValue = Constants.RESET_CALL_4;
                break;
        }
        resetCallValue(callValue);
        changeLoaderStatus(true);
        serviceCall.enqueue(this);
    }

    protected void resetViewFields(){
        if(isReceiverEnabled) changeConnectivityReceiverState(false);
        isServiceRunning = false;
        isReceiverEnabled = false;
        serviceCallId = 0;
        hasFailed = false;
        lastRunningService = 0;
    }

    @Override
    public void onResponse(Call call, Response response) {
        isServiceRunning = false;

        SharedPreferences.Editor editor = helperPref.edit();
        editor.putInt("request_counter", helperPref.getInt("request_counter", 1) + 1);
        editor.apply();

        if(isReceiverEnabled) changeConnectivityReceiverState(false);
        changeLoaderStatus(false);
        Object res =  response.body();
        Object errorBody = response.errorBody();
        if(res == null || !response.isSuccessful()){
            hasFailed = true;
            if(errorBody == null) showMessage(Constants.WRONG);
            else{
                Object object = null;
                try {
                    RetrofitClient retrofitClient = RetrofitClient.createClient();
                    object = retrofitClient.getErrorRespose(BaseResponse.class, response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(object != null) serverError(object);
                else showMessage(Constants.WRONG);
            }
        }
        else serverResponse(res);
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        isServiceRunning = false;
        changeLoaderStatus(false);
        if(t instanceof IOException){
            boolean isConnected = Utils.hasNetwork(getApplicationContext());
            if(!isConnected) {
                showMessage("No Network");
                networkFailure();
                if (!isReceiverEnabled) changeConnectivityReceiverState(true);
            }else showMessage(Constants.WRONG);
        }
        else{
            hasFailed = true;

            SharedPreferences.Editor editor = helperPref.edit();
            editor.putInt("request_counter", helperPref.getInt("request_counter", 1) + 1);
            editor.apply();

            if(isReceiverEnabled) changeConnectivityReceiverState(false);
            showMessage(t.getMessage());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String cause = intent.getStringExtra("cause");
        if(TextUtils.isEmpty(cause)) return;
        Utils.logVerbose("caused");
        switch(cause){
            case Constants.NETWORK_ENABLED:
                changeConnectivityReceiverState(false);
                reloadRequest();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serviceCall = null;
        helperPref = null;
    }

    protected void showMessage(String message){
        if(!isAcivityVisible) return;
        Utils.showToast(this.getApplicationContext(), message);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("receiver_state", isReceiverEnabled);
        outState.putBoolean("has_failed", hasFailed);
        outState.putInt("service_call_id", serviceCallId);
    }

    void changeConnectivityReceiverState(boolean isEnable){
        Utils.logVerbose(isEnable ? "turing state on" : "turing state off");
        isReceiverEnabled = isEnable;
        //PackageManager.COMPONENT_ENABLED_STATE_DISABLED not working
        //using PackageManager.COMPONENT_ENABLED_STATE_DEFAULT
        ComponentName c = new ComponentName(this, ConnectivityReceiver.class);
        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(c, isEnable ?
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
                PackageManager.DONT_KILL_APP);
    }
}
