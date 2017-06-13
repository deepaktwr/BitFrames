package proj.me.bitframe;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;
import java.util.List;

import proj.me.bitframe.exceptions.FrameException;
import proj.me.bitframe.helper.Utils;

/**
 * Created by root on 13/9/16.
 */

class UnframedPicassoTargetNew implements Target {
    WeakReference<ImageResult> imageResultWeakReference;
    int position;

    UnframedPicassoTargetNew(ImageResult imageResult, int position) {
        imageResultWeakReference = new WeakReference<>(imageResult);
        this.position = position;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        ImageResult imageResult = imageResultWeakReference.get();
        if(imageResult == null){
            Utils.logError("target image result got collected, onBitmapLoaded "+position);
            return;
        }
        Utils.logMessage("Came image loaded -> "+imageResult.getCounter() + " bit width = "+bitmap.getWidth()+" height "+bitmap.getHeight()+" "+position);
        imageResult.callNextCycle(imageResult.getNextUnframedBean().getImageLink());
        imageResult.handleTransformedResult(bitmap, bitmap.getWidth() > 1 ? imageResult.getNextUnframedBean() : null);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        ImageResult imageResult = imageResultWeakReference.get();
        if(imageResult == null){
            Utils.logError("target image result got collected, onBitmapFailed "+position);
            return;
        }
        boolean doneLoading = imageResult.getCounter() == (imageResult.getFrameModel().getMaxFrameCount()
                >= imageResult.getTotalImages() ? imageResult.getTotalImages()
                : imageResult.getFrameModel().getMaxFrameCount()) - 1;
        if(doneLoading) imageResult.updateCounter();
        imageResult.setDoneLoading(doneLoading);
        try {
            imageResult.onImageLoaded(false, null, imageResult.getNextUnframedBean());
        } catch (FrameException e) {
            e.printStackTrace();
        }

        //pass this directly to container as it has no result
        BeanBitFrame beanBitFrame = new BeanBitFrame();
        imageResult.getImageCallback().frameResult(beanBitFrame);

        Utils.logVerbose("Came image failed -> "+imageResult.getCounter()+" "+position);
        imageResult.callNextCycle(null);
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        //for placeholder
    }

    //should not be used for similar images, need to handle the case of similar images at the time of frame creation
    /*@Override
    public boolean equals(Object o) {
        return this.beanImage.getImageLink().equals(((UnframedPicassoTargetNew)o).beanImage.getImageLink());
    }

    @Override
    public int hashCode() {
        return targets.size() + 1;
    }*/
}
