package proj.me.bitframedemo;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import proj.me.bitframe.BeanBitFrame;
import proj.me.bitframe.BeanImage;
import proj.me.bitframe.FrameCallback;
import proj.me.bitframe.ImageType;
import proj.me.bitframe.ViewFrame;
import proj.me.bitframe.helper.FrameType;
import proj.me.bitframe.helper.Utils;

import proj.me.bitframedemo.beans.BaseResponse;
import proj.me.bitframedemo.beans.NextBundle;
import proj.me.bitframedemo.databinding.ActivityFrameBinding;
import proj.me.bitframedemo.helper.Constants;
import proj.me.bitframedemo.network.RetrofitClient;
import proj.me.bitframedemo.network.RetrofitImpl;
import proj.me.bitframedemo.services.UploadService;

public class FrameActivity extends BaseActivity implements FrameCallback, IntentAction {

    private static final int CLASS_ID = 1;
    private static final int REQUEST_PERMISSION = 10;

    ViewFrame viewFrame;
    BindingAddText bindingAddText;

    ImageIntent imageIntent;

    RetrofitImpl retrofitImpl;
    boolean isFromInstance;

    ProgressDialog progressDialog;
    NotificationManager notificationManager;
    boolean isFrameLoaded;
    int screenWidth;
    FloatingActionButton floatingActionButton;

    //saved instance
    ArrayList<BeanImage> beanImageList = new ArrayList<>();
    String filePath;
    boolean isPicIntentInitiated;
    NextBundle nextBundle;
    boolean isServiceStarted;
    boolean hasFiredPermission;

    ArrayList<BeanBitFrame> beanBitFrameList = new ArrayList<>();
    int containerWidth;
    int containerHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFrameBinding activityFrameBinding = DataBindingUtil.setContentView(this, R.layout.activity_frame);
        activityFrameBinding.setClickHandler(this);
        bindingAddText = new BindingAddText();
        activityFrameBinding.setBindingAddText(bindingAddText);

        screenWidth = getResources().getDisplayMetrics().widthPixels;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Frame");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");

        RetrofitClient<RetrofitImpl> retrofitRetrofitClient = RetrofitClient.createClient();
        retrofitImpl = retrofitRetrofitClient.getRetrofitImpl(RetrofitImpl.class, CLASS_ID);

        imageIntent = new ImageIntent(this);

        viewFrame = (ViewFrame) findViewById(R.id.view_frame);
        viewFrame.setFrameDimensions(0, 0, 0, 0);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.extra_text);
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Utils.getVersionColor(this, R.color.colorPrimary)));

        bindingAddText.setAddText("+");
        bindingAddText.setTextColor(Utils.getVersionColor(this, R.color.white));
        bindingAddText.setTextVisibility(true);

        if (isFromInstance = savedInstanceState != null) {
            filePath = savedInstanceState.getString("file_path");
            beanImageList = savedInstanceState.getParcelableArrayList("image_list");
            isPicIntentInitiated = savedInstanceState.getBoolean("has_pic_intent");
            nextBundle = savedInstanceState.getParcelable("next_bundle");
            isServiceStarted = savedInstanceState.getBoolean("is_service_started");
            hasFiredPermission = savedInstanceState.getBoolean("has_permission_fired");

            if (!isPicIntentInitiated && beanImageList.size() > 0) {
                Utils.logMessage("pic intent not iitiated" + beanImageList.size());
                List<BeanImage> beanImages = new ArrayList<>();
                beanImages.addAll(beanImageList);
                viewFrame.showBitFrame(beanImages, this, FrameType.UNFRAMED);
            }

            //it could also have been done in onStart by only setting lastRunningService to this value
            //but we also need to do things which has been done in a sequence of service call
            //i.e ui changes according to previous service call success changes

            //we need to maintain the last ui according to previous service calls which made any change
            //accordingly we also need to reset values by resetting all next service call result values
            switch (serviceCallId) {
                case Constants.SERVICE_CALL_1:
                    if (nextBundle != null) {
                        serviceCall = retrofitImpl.getNextBundle(helperPref.getString("device_id", "abcd"), helperPref.getInt("request_counter", 1));
                        enqueueService(Constants.SERVICE_CALL_1);
                    } else {
                        lastRunningService = serviceCallId;
                        serverResponse(nextBundle);
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

        }

        bindingAddText.setErrorVisibility(beanImageList.size() == 0);

        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) return;

        //adding storage read/write permission
        int storageGroupermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(storageGroupermission != PackageManager.PERMISSION_GRANTED && !hasFiredPermission){
            hasFiredPermission = true;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case REQUEST_PERMISSION:
                hasFiredPermission = false;
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //do nothing
                }else finish();
                break;
        }
    }

    @Override
    public void imageClick(ImageType imageType, int imagePosition, String imageLink) {
        showMessage("Clickable");
    }

    @Override
    public void frameResult(List<BeanBitFrame> beanBitFrameList) {
        this.beanBitFrameList.clear();
        this.beanBitFrameList.addAll(beanBitFrameList);
    }

    @Override
    public void addMoreClick() {

    }

    @Override
    public void containerAdded(int containerWidth, int containerHeight, boolean isAddInLayout) {
        this.containerWidth = containerWidth;
        this.containerHeight = containerHeight;
    }

    @Override
    public void loadedFrameColors(int lastLoadedFrameColor, int mixedLoadedColor, int inverseMixedLoadedColor) {
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(mixedLoadedColor));
        bindingAddText.setTextColor(inverseMixedLoadedColor);
    }

    @Override
    public void picIntentInitiated(String filePath) {
        this.filePath = filePath;
        isPicIntentInitiated = true;
        isFrameLoaded = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Utils.logMessage("on act result" + beanImageList.size());
        isFrameLoaded = true;
        if (resultCode == Activity.RESULT_OK) {
            bindingAddText.setErrorVisibility(false);
            String imagePath = imageIntent.getImageResultPath(data, filePath);
            // show a view to handle the camera or gallery data with cropping and commenting, and save it to the sd card with a path
            processImage(imagePath, "my commented image", requestCode == 1);
        } else {
            showMessage("could not process image");
            Utils.logMessage("image currupted");
            if (beanImageList.size() > 0) {
                bindingAddText.setErrorVisibility(false);
                List<BeanImage> beanImages = new ArrayList<>();
                beanImages.addAll(beanImageList);
                viewFrame.showBitFrame(beanImages, this, FrameType.UNFRAMED);
            }
        }
    }

    private void processImage(String imagePath, String commentText, boolean hasExtention) {
        if (TextUtils.isEmpty(imagePath)) {
            showMessage("could not process image" + beanImageList.size());
            Utils.logMessage("image currupted");
            if (beanImageList.size() > 0) {
                List<BeanImage> beanImages = new ArrayList<>();
                beanImages.addAll(beanImageList);
                viewFrame.showBitFrame(beanImages, this, FrameType.UNFRAMED);
            }
            return;
        }
        Utils.logMessage("loading image" + beanImageList.size());

        for (int i = -1; ++i < beanImageList.size(); ) {
            BeanImage beanImage = beanImageList.get(i);
            if (beanImage.getImageLink().equals(imagePath)) {
                if (i > 3) {
                    BeanImage beanImageAt0 = beanImageList.get(0);
                    beanImageList.set(0, beanImage);
                    beanImageList.set(i, beanImageAt0);
                }
                showMessage("Duplicate Image");
                List<BeanImage> beanImages = new ArrayList<>();
                beanImages.addAll(beanImageList);
                viewFrame.showBitFrame(beanImages, this, FrameType.UNFRAMED);
                return;
            }
        }

        BeanImage beanImage1 = new BeanImage();
        beanImage1.setImageComment("my comm");
        beanImage1.setImageLink(imagePath);
        beanImageList.add(beanImage1);


        List<BeanImage> beanImages = new ArrayList<>();
        beanImages.addAll(beanImageList);

        Collections.reverse(beanImages);
        viewFrame.showBitFrame(beanImages, this, FrameType.UNFRAMED);
    }

    @Override
    public void addMoreImages(View view) {
        imageIntent.callIntentForImage();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.upload:
                if (beanImageList.size() == 0) {
                    showMessage("No Frames found!");
                    return true;
                }
                isServiceStarted = false;
                serviceCall = retrofitImpl.getNextBundle(helperPref.getString("device_id", "abcd"),
                        helperPref.getInt("request_counter", 1));
                enqueueService(Constants.SERVICE_CALL_1);
                return true;
            case R.id.download:
                if(isFrameLoaded && beanImageList.size() > 0){
                    try {
                        saveFrame();
                        showMessage("Frame is saved in external file storage");
                    } catch (IOException e) {
                        e.printStackTrace();
                        showMessage("Could not save frame!");
                    }
                }else showMessage("Please wait...");

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.upload_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", filePath);
        outState.putParcelableArrayList("image_list", beanImageList);
        outState.putBoolean("has_pic_intent", isPicIntentInitiated);
        outState.putParcelable("next_bundle", nextBundle);
        outState.putBoolean("is_service_started", isServiceStarted);
        outState.putBoolean("has_permission_fired", hasFiredPermission);
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*if(isPicIntentInitiated) */
        viewFrame.clearContainerChilds();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPicIntentInitiated = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isFromInstance && !isPicIntentInitiated && beanImageList.size() > 0) {
            List<BeanImage> beanImages = new ArrayList<>();
            beanImages.addAll(beanImageList);
            viewFrame.showBitFrame(beanImages, this, FrameType.UNFRAMED);
        }
        isFromInstance = false;
        if (isServiceRunning || hasFailed) return;
        switch (lastRunningService) {
            case Constants.SERVICE_CALL_1:
                if (nextBundle == null) {
                    serviceCall = retrofitImpl.getNextBundle(helperPref.getString("device_id", "abcd"), helperPref.getInt("request_counter", 1));
                    enqueueService(Constants.SERVICE_CALL_1);
                } else if (!isLayoutChanged) serverResponse(nextBundle);
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
    protected void serverResponse(Object responseObject) {
        switch (lastRunningService) {
            case Constants.SERVICE_CALL_1:
                nextBundle = (NextBundle) responseObject;
                break;
            case Constants.SERVICE_CALL_2:
                break;
            case Constants.SERVICE_CALL_3:
                break;
            case Constants.SERVICE_CALL_4:
                break;
        }

        if (!isAcivityVisible) return;
        isLayoutChanged = true;
        //do the things
        switch (lastRunningService) {
            case Constants.SERVICE_CALL_1:
                //showMessage("next bundle : " + nextBundle.getBundleId());
                showMessage("Uploading in progress\nCheck Notifications");
                if (isServiceStarted) break;
                //show notification
                notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                builder.setContentTitle("Uploading Images");
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setContentText("Queued");

                builder.setProgress(beanBitFrameList.size(), 0, false);
                notificationManager.notify(nextBundle.getBundleId(), builder.build());


                Intent intent = new Intent(this, UploadService.class);
                intent.putParcelableArrayListExtra("image_bits", beanBitFrameList);
                intent.putExtra("max_container_width", containerWidth);
                intent.putExtra("max_container_height", containerHeight);
                intent.putExtra("bundle_id", nextBundle.getBundleId());
                startService(intent);
                isServiceStarted = true;
                resetViewFields();

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
    protected void changeLoaderStatus(boolean isLoading) {
        if (isLoading && !progressDialog.isShowing()) progressDialog.show();
        else if (progressDialog.isShowing()) progressDialog.dismiss();
    }

    @Override
    protected void networkFailure() {
        //do nothing
    }

    @Override
    protected void reloadRequest() {
        if (isServiceRunning || !isAcivityVisible || hasFailed) return;
        switch (lastRunningService) {
            case Constants.SERVICE_CALL_1:
                if (nextBundle == null) {
                    serviceCall = retrofitImpl.getNextBundle(helperPref.getString("device_id", "abcd"), helperPref.getInt("request_counter", 1));
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
        switch (callValue) {
            case Constants.RESET_CALL_1:
                nextBundle = null;
            case Constants.RESET_CALL_2:
            case Constants.RESET_CALL_3:
            case Constants.RESET_CALL_4:
                break;
        }
    }

    @Override
    protected void serverError(Object responseObject) {
        if (!isAcivityVisible) return;
        switch (lastRunningService) {
            case Constants.SERVICE_CALL_1:
                BaseResponse baseResponse = (BaseResponse) responseObject;
                showMessage(baseResponse.getMessage());
                break;
            case Constants.SERVICE_CALL_2:
                break;
            case Constants.SERVICE_CALL_3:
                break;
            case Constants.SERVICE_CALL_4:
                break;
        }
    }

    void saveFrame() throws IOException {
        File parent = new File(Environment.getExternalStorageDirectory().getPath().toString(), "frames/");
        if(!parent.exists()) parent.mkdir();
        File imgFile = new File(Environment.getExternalStorageDirectory().getPath().toString()+"/frames","Frame_"+System.currentTimeMillis()+"_m.png");
        viewFrame.setDrawingCacheEnabled(true);
        viewFrame.buildDrawingCache();
        Bitmap bitmap1 = viewFrame.getDrawingCache();
        if(bitmap1 == null){
            imgFile.delete();
            return;
        }
        Bitmap bitmap = Bitmap.createBitmap(bitmap1, (int)Math.ceil((screenWidth - containerWidth) / 2.0), 0,
                bitmap1.getWidth() - (screenWidth - containerWidth), bitmap1.getHeight());
        if(bitmap1 != bitmap) bitmap1.recycle();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bitmapData = byteArrayOutputStream.toByteArray();


        FileOutputStream fileOutputStream = new FileOutputStream(imgFile);
        fileOutputStream.write(bitmapData);
        fileOutputStream.flush();
        fileOutputStream.close();


        byteArrayOutputStream.close();
        bitmap.recycle();
        viewFrame.setDrawingCacheEnabled(false);
    }

    @Override
    protected void resetViewFields() {
        super.resetViewFields();
        viewFrame.clearContainerChilds();
        viewFrame.invalidate();
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Utils.getVersionColor(this, R.color.colorPrimary)));
        bindingAddText.setAddText("+");
        bindingAddText.setTextColor(Utils.getVersionColor(this, R.color.white));
        bindingAddText.setTextVisibility(true);
        bindingAddText.setErrorVisibility(true);
        beanImageList.clear();
        filePath = null;
        isPicIntentInitiated = false;
        nextBundle = null;
        isServiceStarted = false;
        beanBitFrameList.clear();
        containerWidth = 0;
        containerHeight = 0;
    }

    @Override
    protected void onDestroy() {
        //clear everything
        super.onDestroy();
        Utils.logVerbose("destroyed");
        viewFrame.destroyFrame();

        viewFrame = null;
        bindingAddText = null;
        imageIntent = null;
        retrofitImpl = null;
        if (progressDialog.isShowing()) progressDialog.dismiss();
        progressDialog = null;
        floatingActionButton = null;

        //because we are saving them in instance state, so if the object is altered here then it'll effect the save instance
        //it's only for the case when activity saved it's state and calls onDestroy which is not guaranteed
        /*if(beanImageList != null) {
            for (BeanImage beanImage : beanImageList) beanImage = null;
            beanImageList.clear();
        }
        beanImageList = null;
        filePath = null;*/
    }
}
