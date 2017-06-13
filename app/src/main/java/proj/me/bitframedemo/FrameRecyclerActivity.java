package proj.me.bitframedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.List;

import proj.me.bitframe.BeanBitFrame;
import proj.me.bitframe.BeanImage;
import proj.me.bitframe.helper.Utils;
import proj.me.bitframedemo.adapter.FrameAdapter;
import proj.me.bitframedemo.beans.BaseResponse;
import proj.me.bitframedemo.beans.BundleDetail;
import proj.me.bitframedemo.beans.FrameBean;
import proj.me.bitframedemo.beans.FrameResponse;
import proj.me.bitframedemo.binders.FrameBinder;
import proj.me.bitframedemo.broadcasts.UploadReceiver;
import proj.me.bitframedemo.databinding.FrameRecyclerBinding;
import proj.me.bitframedemo.helper.Constants;
import proj.me.bitframedemo.network.DownloadImpl;
import proj.me.bitframedemo.network.RetrofitClient;

/**
 * Created by root on 20/9/16.
 */

public class FrameRecyclerActivity extends BaseActivity implements View.OnClickListener {

    private static final int CLASS_ID = 3;
    FrameAdapter frameAdapter;
    FrameBinder frameBinder;
    ProgressDialog progressDialog;
    DownloadImpl downloadImpl;
    UploadReceiver uploadReceiver;
    List<FrameBean> frameBeanList;
    //save instance
    FrameResponse frameResponse;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frameBinder = new FrameBinder();
        final FrameRecyclerBinding frameRecyclerBinding = DataBindingUtil.setContentView(this, R.layout.frame_recycler);
        frameRecyclerBinding.setOnClickHandler(this);
        frameRecyclerBinding.setFrameBinder(frameBinder);
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int heightPixels = getResources().getDisplayMetrics().heightPixels;


        int maxW = widthPixels - Utils.dpToPx(24, getResources());
        //int maxH = (int)(heightPixels - heightPixels*0.4f) < widthPixels ? widthPixels : (int)(heightPixels - heightPixels*0.4f);
        int maxH = ((int)(maxW + maxW * 0.2f) > heightPixels ? maxW : (int)(maxW + maxW * 0.2f)) - 600;

        Utils.logMessage("DISPLAY : "+widthPixels+" "+heightPixels);
        Utils.logMessage("DISPLAY : "+maxW+" "+maxH);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        frameRecyclerBinding.recyclerFrame.setLayoutManager(linearLayoutManager);
        frameBeanList = new ArrayList<>();
        frameAdapter = new FrameAdapter(maxW, maxH, frameBeanList);
        frameRecyclerBinding.recyclerFrame.setAdapter(frameAdapter);


        frameRecyclerBinding.recyclerFrame.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE) frameRecyclerBinding.fab.show();
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0 || dy<0 && frameRecyclerBinding.fab.isShown()) frameRecyclerBinding.fab.hide();
            }
        });

        uploadReceiver = new UploadReceiver();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Frames...");

        RetrofitClient<DownloadImpl> retrofitRetrofitClient = RetrofitClient.createClient();
        downloadImpl = retrofitRetrofitClient.getRetrofitImpl(DownloadImpl.class, CLASS_ID);


        if(savedInstanceState != null){
            frameResponse = savedInstanceState.getParcelable("frame_response");
            switch(serviceCallId){
                case Constants.SERVICE_CALL_1:
                    if(frameResponse == null || helperPref.getBoolean("has_new_frames", false)){
                        serviceCall = downloadImpl.getDeviceBundle(helperPref.getString("device_id", "abcd"));
                        enqueueService(Constants.SERVICE_CALL_1);
                    } else{
                        lastRunningService = serviceCallId;
                        serverResponse(frameResponse);
                    }
                    break;
                case Constants.SERVICE_CALL_2:
                    //so that if service call 2 has been called after service call 1 then we first need to make ui changes
                    //based on service call 1 result then we will call service call 2
                    break;
                case Constants.SERVICE_CALL_3:
                    break;
                case Constants.SERVICE_CALL_4:
                    break;
            }
        }else {
            serviceCall = downloadImpl.getDeviceBundle(helperPref.getString("device_id", "abcd"));
            enqueueService(Constants.SERVICE_CALL_1);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String cause = intent.getStringExtra("cause");
        if(TextUtils.isEmpty(cause)) return;
        switch(cause){
            case Constants.UPLOAD_DONE:
                SharedPreferences.Editor editor = helperPref.edit();
                editor.putBoolean("has_new_frames", false);
                editor.apply();

                frameBinder.setErrorText("");
                serviceCall = downloadImpl.getDeviceBundle(helperPref.getString("device_id", "abcd"));
                enqueueService(Constants.SERVICE_CALL_1);
                break;
        }
    }

    @Override
    protected void serverResponse(Object responseObject) {

        switch(lastRunningService){
            case Constants.SERVICE_CALL_1:
                frameResponse = (FrameResponse) responseObject;
                break;
            case Constants.SERVICE_CALL_2:
                break;
            case Constants.SERVICE_CALL_3:
                break;
            case Constants.SERVICE_CALL_4:
                break;
        }

        if(!isAcivityVisible) return;
        isLayoutChanged = true;

        switch(lastRunningService) {
            case Constants.SERVICE_CALL_1:
                frameBinder.setErrorText("");
                frameBinder.setHasData(true);
                for(int i=-1;++i<frameResponse.getBundle().size();){
                    BundleDetail bundleDetail = frameResponse.getBundle().get(i);
                    FrameBean frameBean = new FrameBean();
                    frameBean.setTitle("Title "+i);
                    frameBean.setDescription("Description "+i+" for Title "+i+" shows the content of images which has "+
                            bundleDetail.getImages().size() + " images, max count for showing frames is 4, you may decrease it as" +
                            " per your needs..");
                    List<BeanImage> beanImages = new ArrayList<>();
                    frameBean.setBeanBitFrameList(beanImages);
                    for(proj.me.bitframedemo.beans.Bundle bundle : bundleDetail.getImages()){
                        //change
                        //because picasso is not loading IMG_currentTimeInMillis_1
                        //while it's loading imgcurrentTimeInMillis1m.png
                        //if(!bundle.getImgName().contains(".png")) continue;
                        //so for picasso i thing the extension is required
                        BeanBitFrame beanBitFrame = new BeanBitFrame();
                        beanBitFrame.setHasGreaterVibrantPopulation(bundle.isHasGreaterVibrant());
                        beanBitFrame.setPrimaryCount(bundle.getPrimaryCount());
                        beanBitFrame.setSecondaryCount(bundle.getSecondaryCount());
                        beanBitFrame.setImageLink(/*bundle.getImgName()*/"https://s3-us-west-1.amazonaws.com/pro-manager/images/20170606174814752942003.jpg");
                        beanBitFrame.setImageComment(bundle.getComment());
                        beanBitFrame.setWidth(bundle.getWidth());
                        beanBitFrame.setHeight(bundle.getHeight());
                        beanBitFrame.setMutedColor(bundle.getMutedColor());
                        beanBitFrame.setVibrantColor(bundle.getVibrantColor());
                        beanBitFrame.setLoaded(true);
                        beanImages.add(beanBitFrame);
                    }
                    if(beanImages.size() > 0) frameBeanList.add(frameBean);
                }

                frameAdapter.notifyDataSetChanged();
                SharedPreferences.Editor editor = helperPref.edit();
                editor.putBoolean("has_new_frames", false);
                editor.apply();

                break;
        }
    }

    @Override
    protected void changeLoaderStatus(boolean isLoading) {
        if(isLoading && !progressDialog.isShowing()) progressDialog.show();
        else if(progressDialog.isShowing()) progressDialog.dismiss();
    }

    @Override
    protected void networkFailure() {
        frameBinder.setErrorText("No Frames found\n\nReload?");
        frameBinder.setHasData(false);
    }

    @Override
    protected void reloadRequest() {
        if(isServiceRunning || !isAcivityVisible || hasFailed) return;
        switch(lastRunningService){
            case Constants.SERVICE_CALL_1:
                if(frameResponse == null || helperPref.getBoolean("has_new_frames", false)) {
                    frameBinder.setErrorText("");
                    serviceCall = downloadImpl.getDeviceBundle(helperPref.getString("device_id", "abcd"));
                    enqueueService(Constants.SERVICE_CALL_1);
                }
                break;
            case Constants.SERVICE_CALL_2:
                break;
            case Constants.SERVICE_CALL_3:
                break;
            case Constants.SERVICE_CALL_4:
                break;
        }
    }

    @Override
    protected void resetCallValue(int callValue) {
        hasFailed = false;
        switch(callValue){
            case Constants.RESET_CALL_1:
                frameBeanList.clear();
                frameResponse= null;
            case Constants.RESET_CALL_2:
            case Constants.RESET_CALL_3:
            case Constants.RESET_CALL_4:
                break;
        }
    }

    @Override
    protected void serverError(Object responseObject) {
        if(!isAcivityVisible) return;
        switch(lastRunningService){
            case Constants.SERVICE_CALL_1:
                BaseResponse baseResponse = (BaseResponse) responseObject;
                frameBinder.setErrorText("No Frames found\n\nReload?");
                frameBinder.setHasData(false);
                //showMessage(baseResponse.getMessage());
                break;
            case Constants.SERVICE_CALL_2:
                break;
            case Constants.SERVICE_CALL_3:
                break;
            case Constants.SERVICE_CALL_4:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch((String)v.getTag()){
            case "reload":
                frameBinder.setErrorText("");
                serviceCall = downloadImpl.getDeviceBundle(helperPref.getString("device_id", "abcd"));
                enqueueService(Constants.SERVICE_CALL_1);
                break;
            case "add":
                Intent intent = new Intent(this, FrameActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.UPLOAD_DONE);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(uploadReceiver, intentFilter);

        if(isServiceRunning || hasFailed) return;
        switch(lastRunningService){
            case Constants.SERVICE_CALL_1:
                if(frameResponse == null || helperPref.getBoolean("has_new_frames", false)) {
                    frameBinder.setErrorText("");
                    serviceCall = downloadImpl.getDeviceBundle(helperPref.getString("device_id", "abcd"));
                    enqueueService(Constants.SERVICE_CALL_1);
                }else if(!isLayoutChanged) serverResponse(frameResponse);
                break;
            case Constants.SERVICE_CALL_2:
                break;
            case Constants.SERVICE_CALL_3:
                break;
            case Constants.SERVICE_CALL_4:
                break;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.unregisterReceiver(uploadReceiver);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("frame_response", frameResponse);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.unbindDrawables(findViewById(R.id.root_layout), true, true);
        frameAdapter = null;
        frameBinder = null;
        progressDialog = null;
        downloadImpl = null;
        uploadReceiver = null;
        frameBeanList.clear();
        frameBeanList = null;
    }
}
