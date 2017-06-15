package proj.me.bitframe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import proj.me.bitframe.exceptions.FrameException;
import proj.me.bitframe.helper.Utils;
import proj.me.bitframe.shading_four.ImageShadingFour;
import proj.me.bitframe.shading_one.ImageShadingOne;
import proj.me.bitframe.shading_three.ImageShadingThree;
import proj.me.bitframe.shading_two.ImageShadingTwo;


/**
 * Created by Deepak.Tiwari on 28-09-2015.
 */
final class ImageShading implements ImageResult{

    Context context;
    List<Bitmap> images;
    int totalImages;
    ImageCallback layoutCallback;

    boolean result;
    boolean doneLoading;

    List<BeanImage> loadedBeanImages;
    FrameModel frameModel;
    int unframedImageCounter;

    FrameHandler frameHandler;
    RecycleHandler recycleHandler;

    List<BeanImage> beanImages = new ArrayList<>();
    List<UnframedPicassoTargetNew> targets;
    Picasso currentFramePicasso;


    ImageShading(Context context, ImageCallback layoutCallback, FrameModel frameModel, Picasso currentFramePicasso){
        this.context =context;
        images = new ArrayList<>();
        loadedBeanImages = new ArrayList<>();
        this.layoutCallback = layoutCallback;
        this.frameModel = frameModel;
        frameHandler = new FrameHandler(this);
        recycleHandler = new RecycleHandler(this);
        this.currentFramePicasso = currentFramePicasso;
    }

    void mapUnframedImages(List<BeanImage> beanImages, List<UnframedPicassoTargetNew> targets){
        totalImages = beanImages.size();
        this.targets = targets;
        if(totalImages > frameModel.getMaxFrameCount() && frameModel.isShouldSortImages()){
            //sort image a/c to primary and secondary value
            Collections.sort(beanImages);
        }
        this.beanImages.addAll(beanImages);
        unframedImageCounter = 0;
        BeanImage beanImage = beanImages.get(0);
        if(Utils.isLocalPath(beanImage.getImageLink())){
            Utils.logVerbose("LADING AS : "+"local image " + beanImage.getImageLink());
            new UnframedLocalTask(this).execute(beanImage);
        } else {
            Utils.logVerbose("LADING AS : " + "server image " + beanImage.getImageLink());
            UnframedPicassoTargetNew target = new UnframedPicassoTargetNew(this, beanImage);
            targets.add(target);
            currentFramePicasso.load(beanImage.getImageLink()).memoryPolicy(MemoryPolicy.NO_STORE)
                    .networkPolicy(NetworkPolicy.NO_STORE)
                    .noPlaceholder()
                    .transform(new ScaleTransformation(frameModel.getMaxContainerWidth(),
                            frameModel.getMaxContainerHeight(), totalImages, beanImage.getImageLink(),
                            beanImage, this))
                    .into(target);

        }
    }

    @Override
    public void callNextCycle(String lastImagePath) {
        if(!TextUtils.isEmpty(lastImagePath)) currentFramePicasso.invalidate(lastImagePath);
        if(beanImages != null && beanImages.size() > 0) beanImages.remove(0);
        //because targets are also running sequential, in case of parallel need to shift it to respective class
        if(targets != null && targets.size() > 0) targets.remove(0);
        if(beanImages.size() == 0) return;
        BeanImage beanImage = beanImages.get(0);
        if(Utils.isLocalPath(beanImage.getImageLink())){
            Utils.logVerbose("LADING AS : "+"local image " + beanImage.getImageLink());
            new UnframedLocalTask(this).execute(beanImage);
        } else {
            Utils.logVerbose("LADING AS : "+"server image " + beanImage.getImageLink());
            UnframedPicassoTargetNew target = new UnframedPicassoTargetNew(this, beanImage);
            targets.add(target);

            currentFramePicasso.load(beanImage.getImageLink()).memoryPolicy(MemoryPolicy.NO_STORE)
                    .networkPolicy(NetworkPolicy.NO_STORE)
                    .noPlaceholder()
                    .transform(new ScaleTransformation(frameModel.getMaxContainerWidth(),
                            frameModel.getMaxContainerHeight(), totalImages, beanImage.getImageLink(),
                            beanImage, this))
                    .into(target);
        }
    }

    @Override
    public void handleTransformedResult(Bitmap bitmap, BeanImage beanImage) {
        //caller responsibility
        if(beanImage == null){
            Message message = frameHandler.obtainMessage(3, bitmap);
            message.sendToTarget();
            return;
        }
        BeanHandler beanHandler = new BeanHandler();
        beanHandler.setBitmap(bitmap);
        beanHandler.setBeanImage(beanImage);

        Message message = frameHandler.obtainMessage(1, beanHandler);
        message.sendToTarget();
    }

    @Override
    public List<Bitmap> getImages() {
        return images;
    }



    @Override
    public void onImageLoaded(boolean result, Bitmap bitmap, BeanImage beanImage) throws FrameException{
        if(result) {
            images.add(bitmap);
            loadedBeanImages.add(beanImage);
        }

        if(!this.result) this.result = result;

        if(doneLoading && !this.result){
            loadedBeanImages.add(beanImage);
            ImageShades imageShades = new ImageShadingOne(context, totalImages, frameModel);
            imageShades.setImageCallback(layoutCallback);
            imageShades.setCurrentFramePicasso(currentFramePicasso);
            imageShades.setResult(false);
            imageShades.updateFrameUi(null, loadedBeanImages, false);
            images.clear();
            loadedBeanImages.clear();
            this.result =false;
            doneLoading = false;
        }else if(doneLoading){
            ImageShades imageShades = null;
            switch(images.size()){
                case 1:
                    Utils.logMessage("going to load 1");
                    imageShades = new ImageShadingOne(context, totalImages, frameModel);
                    imageShades.setResult(true);
                    break;
                case 2:
                    Utils.logMessage("going to load 2");
                    imageShades = new ImageShadingTwo(context, totalImages, frameModel);
                    break;
                case 3:
                    Utils.logMessage("going to load 3");
                    imageShades = new ImageShadingThree(context, totalImages, frameModel);
                    break;
                case 4:
                    Utils.logMessage("going to load 4");
                    imageShades = new ImageShadingFour(context, totalImages, frameModel);
                    break;
            }
            if(imageShades != null){
                imageShades.setImageCallback(layoutCallback);
                imageShades.setCurrentFramePicasso(currentFramePicasso);
                imageShades.updateFrameUi(images, loadedBeanImages, false);
            }
            //RecycleHandler responsibility
            if(frameModel.isShouldRecycleBitmaps()) recycleHandler.sendEmptyMessageDelayed(2, 500);
            else images.clear();
            loadedBeanImages.clear();
            this.result =false;
            doneLoading = false;
        }
    }

    @Override
    public void setDoneLoading(boolean doneLoading) {
        this.doneLoading = doneLoading;
    }

    @Override
    public FrameModel getFrameModel() {
        return frameModel;
    }

    @Override
    public ImageCallback getImageCallback() {
        return layoutCallback;
    }

    @Override
    public int getTotalImages() {
        return totalImages;
    }

    @Override
    public Context getContext() {
        return context.getApplicationContext();
    }

    @Override
    public void updateCounter() {
        unframedImageCounter++;
    }

    @Override
    public int getCounter() {
        return unframedImageCounter;
    }


    /**
     * picasso will be loaded in last when the mapping
     * of the images is done and decided that which image will go in which frame
     * */
    void mapFramedImages(List<BeanImage> beanBitFrames) throws FrameException{
        totalImages = beanBitFrames.size();
        if(totalImages > frameModel.getMaxFrameCount()){
            if(frameModel.isShouldSortImages()){
                //sort image a/c to primary and secondary value
                Collections.sort(beanBitFrames);
            }
            //go to mapping with top max frame count images
            for(int i =0;i<frameModel.getMaxFrameCount();i++){
                loadedBeanImages.add(beanBitFrames.get(i));
            }
            ImageShades imageShades = new ImageShadingFour(context, totalImages, frameModel);
            imageShades.setImageCallback(layoutCallback);
            imageShades.setCurrentFramePicasso(currentFramePicasso);
            imageShades.updateFrameUi(null, loadedBeanImages, true);
        }else{
            //go to mapping directly
            for(BeanImage beanImage : beanBitFrames){
                loadedBeanImages.add(beanImage);
            }
            ImageShades imageShades = null;
            switch(totalImages){
                case 1:
                    imageShades = new ImageShadingOne(context, totalImages, frameModel);
                    imageShades.setResult(true);
                    break;
                case 2:
                    imageShades = new ImageShadingTwo(context, totalImages, frameModel);
                    break;
                case 3:
                    imageShades = new ImageShadingThree(context, totalImages, frameModel);
                    break;
                case 4:
                    imageShades = new ImageShadingFour(context, totalImages, frameModel);
                    break;
            }
            if(imageShades != null){
                imageShades.setImageCallback(layoutCallback);
                imageShades.setCurrentFramePicasso(currentFramePicasso);
                imageShades.updateFrameUi(null, loadedBeanImages, true);
            }
        }
    }

    static class RecycleHandler extends Handler{
        WeakReference<ImageResult> imageResultSoftReference;
        RecycleHandler(ImageResult imageResult){
            imageResultSoftReference = new WeakReference<>(imageResult);
        }

        @Override
        public void handleMessage(Message msg) {
            ImageResult imageResult = imageResultSoftReference.get();
            if(imageResult == null) {
                super.handleMessage(msg);
                return;
            }

            switch(msg.what){
                case 2:
                    List<Bitmap> bitmaps = imageResult.getImages();
                    for (Bitmap bitmap1 : bitmaps) {
                        Utils.logVerbose("exppp normal width " + bitmap1.getWidth() + " height " + bitmap1.getHeight());
                        if (!bitmap1.isRecycled()) bitmap1.recycle();
                        bitmap1 = null;
                    }
                    bitmaps.clear();
                    break;
            }
        }
    }
}
