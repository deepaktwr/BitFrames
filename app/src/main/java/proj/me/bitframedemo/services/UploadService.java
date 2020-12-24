package proj.me.bitframedemo.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import proj.me.bitframe.BeanBitFrame;
import proj.me.bitframe.exceptions.FrameException;
import proj.me.bitframe.helper.BeanResult;
import proj.me.bitframe.helper.Utils;
import proj.me.bitframedemo.R;
import proj.me.bitframedemo.beans.BaseResponse;
import proj.me.bitframedemo.beans.Bundle;
import proj.me.bitframedemo.beans.UploadRequest;
import proj.me.bitframedemo.helper.Constants;
import proj.me.bitframedemo.network.RetrofitClient;
import proj.me.bitframedemo.network.UploadImpl;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by root on 18/9/16.
 */

public class UploadService extends IntentService{
    private static final int CLASS_ID = 2;
    public UploadService() {
        super("Image Upload Worker");
    }
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UploadService(String name) {
        super(name);
    }

    int bundleId;
    int reqWidth;
    int reqHeight;

    NotificationManager notificationManager;

    RetrofitClient<UploadImpl> retrofitClient;

    @Override
    protected void onHandleIntent(Intent intent) {
        ArrayList<BeanBitFrame> beanBitList = intent.getParcelableArrayListExtra("image_bits");
        bundleId = intent.getIntExtra("bundle_id", 0);
        int maxContainerWidth = intent.getIntExtra("max_container_width", 0);
        int maxContainerHeight = intent.getIntExtra("max_container_height", 0);

        float totalImage = beanBitList.size() > 4 ? 4 : beanBitList.size();
        reqWidth = (int)(maxContainerWidth/(totalImage > 1 ? totalImage - 0.4f : 1));
        reqHeight = (int)(maxContainerHeight/(totalImage > 1 ? totalImage - 0.4f : 1));

        retrofitClient = RetrofitClient.createClient();

        Utils.logVerbose("starting intent service");

        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Uploading Images");
        builder.setContentText("uploading : 0 / "+beanBitList.size());

        builder.setProgress(beanBitList.size(), 0, false);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        notificationManager.notify(bundleId, builder.build());

        SharedPreferences sharedPreferences = getSharedPreferences("helper", MODE_PRIVATE);
        boolean isAnyFrameUploaded = false;
        for(int i=-1;++i<beanBitList.size();){
            boolean isUploadSuccess = false;
            try {
                BeanBitFrame beanBitFrame = beanBitList.get(i);
                if(/*!beanBitFrame.isLoaded() || */TextUtils.isEmpty(beanBitFrame.getImageLink())){
                    Utils.logVerbose("skipping bean at :"+i);
                    continue;
                }
                isUploadSuccess = uploadImage(beanBitFrame, sharedPreferences.getInt("request_counter", 1),
                        sharedPreferences.getString("device_id", "abcd"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FrameException e) {
                e.printStackTrace();
            }
            isAnyFrameUploaded = true;
            String message = null;
            boolean hasSucess = false;
            if(i < beanBitList.size() - 1) message = "uploading frames: " + (i + 1) + " / " + beanBitList.size();
            else{
                hasSucess = true;
                message = beanBitList.size() + " Frames Uploaded!";
                builder.setContentTitle("Uploading Done");
            }
            builder.setContentText(message);
            builder.setProgress(beanBitList.size(), i + 1, false);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            notificationManager.notify(bundleId, builder.build());


            Utils.logVerbose("upload progress " + i + " of "+ beanBitList.size() +" result : "+isUploadSuccess);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("request_counter", sharedPreferences.getInt("request_counter", 1) + 1);
            if(hasSucess) editor.putBoolean("has_new_frames", true);
            editor.apply();

            if(hasSucess){
                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
                Intent intent1 = new Intent(Constants.UPLOAD_DONE);
                localBroadcastManager.sendBroadcast(intent1);
            }
        }

        if(!isAnyFrameUploaded){
            builder.setContentText("Uploading Failed!");
            builder.setProgress(beanBitList.size(), beanBitList.size(), false);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            notificationManager.notify(bundleId, builder.build());


            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("request_counter", sharedPreferences.getInt("request_counter", 1) + 1);
            editor.apply();
        }

        beanBitList.clear();
    }


    boolean uploadImage(BeanBitFrame beanBitFrame, int requestCounter, String deviceId) throws IOException, FrameException {
        Utils.logVerbose("bundleId : "+bundleId+" deviceId :" + deviceId+" requestId : "+requestCounter);
        Bundle bundle = new Bundle();
        bundle.setComment(beanBitFrame.getImageComment());
        bundle.setDescription(beanBitFrame.getImageComment()+" description");
        bundle.setHasGreaterVibrant(beanBitFrame.isHasGreaterVibrantPopulation());
        bundle.setHeight((int)beanBitFrame.getHeight());
        bundle.setWidth((int)beanBitFrame.getWidth());
        bundle.setImgName(Utils.isLocalPath(beanBitFrame.getImageLink()) ? "img"+System.currentTimeMillis()+"bundle"+requestCounter+"m.png" :
                beanBitFrame.getImageLink());
        bundle.setMutedColor(beanBitFrame.getMutedColor());
        bundle.setVibrantColor(beanBitFrame.getVibrantColor());
        bundle.setTitle(beanBitFrame.getImageComment()+" title");
        bundle.setPrimaryCount(beanBitFrame.getPrimaryCount());
        bundle.setSecondaryCount(beanBitFrame.getSecondaryCount());

        UploadRequest uploadRequest = new UploadRequest();
        uploadRequest.setBundleId(bundleId);
        uploadRequest.setDeviceId(deviceId);
        uploadRequest.setRequestId(requestCounter);
        uploadRequest.setBundle(bundle);

        if(Utils.isLocalPath(beanBitFrame.getImageLink())) return generateUploadRequest(beanBitFrame.getImageLink(), uploadRequest);
        else return generatePostRequest(uploadRequest);
    }

    boolean generateUploadRequest(String imagePath, UploadRequest uploadRequest) throws IOException, FrameException {
        Utils.logVerbose("uploading");
        File imgFile = new File(Environment.getExternalStorageDirectory().getPath().toString(), uploadRequest.getBundle().getImgName());
        BeanResult beanResult = Utils.decodeBitmapFromPath(reqWidth, reqHeight, imagePath, this.getApplicationContext());
        if(beanResult == null){
            imgFile.delete();
            return false;
        }
        Bitmap bitmap = beanResult.getBitmap();
        if(bitmap == null){
            imgFile.delete();
            return false;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byte[] bitmapData = byteArrayOutputStream.toByteArray();


        FileOutputStream fileOutputStream = new FileOutputStream(imgFile);
        fileOutputStream.write(bitmapData);
        fileOutputStream.flush();
        fileOutputStream.close();


        byteArrayOutputStream.close();


        RequestBody requestBodyFile = RequestBody.create(MediaType.parse("image/*"), imgFile);
        RequestBody requestBodyJson = RequestBody.create(MediaType.parse("text/plain"),
                retrofitClient.getJsonObject(uploadRequest));

        UploadImpl uploadImpl = retrofitClient.getRetrofitImpl(UploadImpl.class, CLASS_ID);

        //make sync call
        Call<BaseResponse> uploadBundle = uploadImpl.uploadImage(requestBodyFile, requestBodyJson);
        Response<BaseResponse> response = uploadBundle.execute();
        BaseResponse baseResponse = response.body();
        if(baseResponse != null) Utils.logVerbose(" status : "+baseResponse.getStatus()+" message : "+baseResponse.getMessage());

        imgFile.delete();
        bitmap.recycle();

        return response.isSuccessful();

    }
    boolean generatePostRequest(UploadRequest uploadRequest) throws IOException {
        Utils.logVerbose("posting");
        UploadImpl uploadImpl = retrofitClient.getRetrofitImpl(UploadImpl.class, CLASS_ID);

        //make sync call
        Call<BaseResponse> postBundle = uploadImpl.postBundle(uploadRequest);
        Response<BaseResponse> response = postBundle.execute();
        BaseResponse baseResponse = response.body();
        if(baseResponse != null) Utils.logVerbose(" status : "+baseResponse.getStatus()+" message : "+baseResponse.getMessage());

        return response.isSuccessful();
    }
}
