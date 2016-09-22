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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import proj.me.bitframe.dimentions.BeanHandler;
import proj.me.bitframe.helper.Utils;
import proj.me.bitframe.shading_four.ImageShadingFour;
import proj.me.bitframe.shading_one.ImageShadingOne;
import proj.me.bitframe.shading_three.ImageShadingThree;
import proj.me.bitframe.shading_two.ImageShadingTwo;


/**
 * Created by Deepak.Tiwari on 28-09-2015.
 */
public final class ImageShading implements ImageResult{

    Context context;
    List<Bitmap> images;
    int totalImages;
    ImageCallback layoutCallback;

    boolean result;
    boolean doneLoading;


    List<BeanImage> loadedBeanImages;
    FrameModel frameModel;
    int unframedImageCounter;

    MyHandler myHandler;

    List<BeanImage> beanImages = new ArrayList<>();
    List<UnframedPicassoTargetNew> targets;


    ImageShading(Context context, ImageCallback layoutCallback, FrameModel frameModel){
        this.context =context;
        images = new ArrayList<>();
        loadedBeanImages = new ArrayList<>();
        this.layoutCallback = layoutCallback;
        this.frameModel = frameModel;
        myHandler = new MyHandler(this);
    }

    void mapUnframedImages(List<BeanImage> beanImages, List<UnframedPicassoTargetNew> targets){
        totalImages = beanImages.size();
        this.beanImages.addAll(beanImages);
        this.targets = targets;
        if(totalImages > frameModel.getMaxFrameCount() && frameModel.isShouldSortImages()){
            //sort image a/c to primary and secondary value
            Collections.sort(beanImages);
        }
        unframedImageCounter = 0;
        BeanImage beanImage = beanImages.get(0);
        if(beanImage.isLocalImage) new UnframedLocalTask(this).execute(beanImage);
        else {
            /*final UnframedPicassoTarget target = new UnframedPicassoTarget(beanImage, targets, this);
            targets.add(target);

            Picasso.with(context.getApplicationContext())
                    .load(beanImage.getImageLink()).memoryPolicy(MemoryPolicy.NO_CACHE)
            .transform(new ScaleTransformation(frameModel.getMaxContainerWidth(),
                    frameModel.getMaxContainerHeight(), totalImages, beanImage.getImageLink()))
                    .noPlaceholder()
                    .into(target);*/

            /*UnframedPicassoTarget target = new UnframedPicassoTarget(beanImage, targets, this);
            targets.add(target);*/
            UnframedPicassoTargetNew target = new UnframedPicassoTargetNew(beanImage, targets, this);
            targets.add(target);
            Picasso.with(context.getApplicationContext())
                    .load(beanImage.getImageLink()).memoryPolicy(MemoryPolicy.NO_STORE)
                    .networkPolicy(NetworkPolicy.NO_STORE)
                    .noPlaceholder()
                    .transform(new ScaleTransformation(frameModel.getMaxContainerWidth(),
                            frameModel.getMaxContainerHeight(), totalImages, beanImage.getImageLink(),
                            beanImage, this))
                    .into(target);


        }

        /*for(BeanImage beanImage : beanImages){
            if(beanImage.isLocalImage()) new UnframedLocalTask(this).execute(beanImage);
            else {
                final UnframedPicassoTarget target = new UnframedPicassoTarget(beanImage, targets, this);
                targets.add(target);

                Picasso.with(context.getApplicationContext())
                        .load(beanImage.getImageLink())*//*.memoryPolicy(MemoryPolicy.NO_CACHE)*//*
                .transform(new ScaleTransformation(frameModel.getMaxContainerWidth(),
                        frameModel.getMaxContainerHeight(), totalImages, beanImage.getImageLink())).into(target);
            }
        }*/
    }

    @Override
    public void callNextCycle(String lastImagePath) {
        beanImages.remove(0);
        if(beanImages.size() == 0) return;
        BeanImage beanImage = beanImages.get(0);
        if(beanImage.isLocalImage) new UnframedLocalTask(this).execute(beanImage);
        else {
            Picasso picasso = Picasso.with(context.getApplicationContext());
            if(!TextUtils.isEmpty(lastImagePath)) picasso.invalidate(lastImagePath);
            /*UnframedPicassoTarget target = new UnframedPicassoTarget(beanImage, targets, this);
            targets.add(target);*/
            UnframedPicassoTargetNew target = new UnframedPicassoTargetNew(beanImage, targets, this);
            targets.add(target);

            picasso.load(beanImage.getImageLink()).memoryPolicy(MemoryPolicy.NO_STORE)
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
        if(beanImage == null){
            Message message = myHandler.obtainMessage(3, bitmap);
            myHandler.sendMessageDelayed(message, 100);
            return;
        }
        BeanHandler beanHandler = new BeanHandler();
        beanHandler.setBitmap(bitmap);
        beanHandler.setBeanImage(beanImage);

        Message message = myHandler.obtainMessage(1, beanHandler);
        message.sendToTarget();
    }

    @Override
    public List<Bitmap> getImages() {
        return images;
    }

    @Override
    public void onImageLoaded(boolean result, Bitmap bitmap, BeanImage beanImage){
        if(result) {
            images.add(bitmap);
            loadedBeanImages.add(beanImage);
        }

        if(!this.result) this.result = result;

        if(doneLoading && !this.result){
            ImageShadingOne imageShadingOne = new ImageShadingOne(context, layoutCallback, totalImages, frameModel);
            loadedBeanImages.add(beanImage);
            imageShadingOne.updateFailedOrSingleUi(false, null, loadedBeanImages, false);
            images.clear();
            loadedBeanImages.clear();
            this.result =false;
            doneLoading = false;
        }else if(doneLoading){
            switch(images.size()){
                case 1:
                    Utils.logMessage("going to load 1");
                    ImageShadingOne imageShadingOne = new ImageShadingOne(context, layoutCallback, totalImages, frameModel);
                    imageShadingOne.updateFailedOrSingleUi(true, images.get(0), loadedBeanImages, false);
                    break;
                case 2:
                    Utils.logMessage("going to load 2");
                    ImageShadingTwo imageShadingTwo = new ImageShadingTwo(context, layoutCallback, totalImages, frameModel);
                    imageShadingTwo.updateDoubleUi(images, loadedBeanImages, false);
                    break;
                case 3:
                    Utils.logMessage("going to load 3");
                    ImageShadingThree imageShadingThree = new ImageShadingThree(context, layoutCallback, totalImages, frameModel);
                    imageShadingThree.updateTripleUi(images, loadedBeanImages, false);
                    break;
                case 4:
                    Utils.logMessage("going to load 4");
                    ImageShadingFour imageShadingFour = new ImageShadingFour(context, layoutCallback, totalImages, frameModel);
                    imageShadingFour.updateOctalUi(images, loadedBeanImages, false);
                    break;
            }
            //images.clear();
            /*new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Utils.logError("recycld");
                    for(Bitmap bitmap1 : images) bitmap1.recycle();
                    images.clear();
                }
            }, 500);*/
            if(frameModel.isShouldRecycleBitmaps()) myHandler.sendEmptyMessageDelayed(2, 500);
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
    void mapFramedImages(List<BeanImage> beanBitFrames){
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
            ImageShadingFour imageShadingFour = new ImageShadingFour(context, layoutCallback, totalImages, frameModel);
            imageShadingFour.updateOctalUi(null, loadedBeanImages, true);
        }else{
            //go to mapping directly
            for(BeanImage beanImage : beanBitFrames){
                loadedBeanImages.add(beanImage);
            }
            switch(totalImages){
                case 1:
                    ImageShadingOne imageShadingOne = new ImageShadingOne(context, layoutCallback, totalImages, frameModel);
                    imageShadingOne.updateFailedOrSingleUi(true, null, loadedBeanImages, true);
                    break;
                case 2:
                    ImageShadingTwo imageShadingTwo = new ImageShadingTwo(context, layoutCallback, totalImages, frameModel);
                    imageShadingTwo.updateDoubleUi(null, loadedBeanImages, true);
                    break;
                case 3:
                    ImageShadingThree imageShadingThree = new ImageShadingThree(context, layoutCallback, totalImages, frameModel);
                    imageShadingThree.updateTripleUi(null, loadedBeanImages, true);
                    break;
                case 4:
                    ImageShadingFour imageShadingFour = new ImageShadingFour(context, layoutCallback, totalImages, frameModel);
                    imageShadingFour.updateOctalUi(null, loadedBeanImages, true);
                    break;
            }
        }
    }


    static class MyHandler extends Handler{
        WeakReference<ImageResult> imageShadingWeakReference;
        MyHandler(ImageResult imageResult){
            imageShadingWeakReference = new WeakReference<>(imageResult);
        }
        @Override
        public void handleMessage(Message msg) {
            final ImageResult imageResult = imageShadingWeakReference.get();
            if(imageResult == null){
                super.handleMessage(msg);
                return;
            }

            switch(msg.what){
                case 1:
                    final BeanHandler beanHandler = (BeanHandler) msg.obj;
                    final Bitmap bitmap = beanHandler.getBitmap();
                    if(imageResult.getCounter() >= imageResult.getFrameModel().getMaxFrameCount()){
                        imageResult.updateCounter();
                        final BeanBitFrame beanBitFrame = new BeanBitFrame();
                        beanBitFrame.setWidth(/*beanBitmapResult.getWidth()*/bitmap.getWidth());
                        beanBitFrame.setHeight(/*beanBitmapResult.getHeight()*/bitmap.getHeight());
                        beanBitFrame.setLoaded(true);
                        Palette.from(/*beanBitmapResult.getBitmap()*/bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                /*int defaultColor = Color.parseColor("#ffffffff");
                                beanBitFrame.setVibrantColor(palette.getVibrantColor(defaultColor));
                                beanBitFrame.setMutedColor(palette.getMutedColor(defaultColor));
                                imageResult.getImageCallback().frameResult(beanBitFrame);
                                Utils.logError("exppp pallet width "+bitmap.getWidth()+" height "+bitmap.getHeight());
                                if(!bitmap.isRecycled()) bitmap.recycle();*/



                                int defaultColor = Color.parseColor("#ffffffff");
                                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                                Palette.Swatch mutedSwatch = palette.getMutedSwatch();
                                int vibrantPopulation = vibrantSwatch == null ? 0 : vibrantSwatch.getPopulation();
                                int mutedPopulation = mutedSwatch == null ? 0 : mutedSwatch.getPopulation();

                                int vibrantColor = palette.getVibrantColor(defaultColor);
                                int mutedColor = palette.getMutedColor(defaultColor);

                                beanBitFrame.setHasGreaterVibrantPopulation(vibrantPopulation > mutedPopulation);
                                beanBitFrame.setMutedColor(mutedColor);
                                beanBitFrame.setVibrantColor(vibrantColor);

                                BeanImage beanImage = beanHandler.getBeanImage();

                                beanBitFrame.setHasExtention(beanImage.isHasExtention());
                                beanBitFrame.setLocalImage(beanImage.isLocalImage());
                                beanBitFrame.setImageComment(beanImage.getImageComment());
                                beanBitFrame.setImageLink(beanImage.getImageLink());
                                beanBitFrame.setPrimaryCount(beanImage.getPrimaryCount());
                                beanBitFrame.setSecondaryCount(beanImage.getSecondaryCount());

                                imageResult.getImageCallback().frameResult(beanBitFrame);
                                Utils.logError("exppp pallet width "+bitmap.getWidth()+" height "+bitmap.getHeight());
                                if(!bitmap.isRecycled()) bitmap.recycle();
                            }
                        });
                    }else {
                        boolean doneLoading = imageResult.getCounter() == (imageResult.getFrameModel().getMaxFrameCount()
                                >= imageResult.getTotalImages() ? imageResult.getTotalImages()
                                : imageResult.getFrameModel().getMaxFrameCount()) - 1;
                        imageResult.setDoneLoading(doneLoading);
                        imageResult.onImageLoaded(true, bitmap, beanHandler.getBeanImage());
                        imageResult.updateCounter();
                    }
                    break;
                case 2:
                    List<Bitmap> bitmaps = imageResult.getImages();
                    for(Bitmap bitmap1 : bitmaps){
                        Utils.logError("exppp normal width "+bitmap1.getWidth()+" height "+bitmap1.getHeight());
                        if(!bitmap1.isRecycled()) bitmap1.recycle();
                        bitmap1 = null;
                    }
                    bitmaps.clear();
                    break;
                case 3:
                    Bitmap bit  = (Bitmap) msg.obj;
                    if(!bit.isRecycled()) bit.recycle();
                    bit = null;
                    break;
            }
        }
    }
}
