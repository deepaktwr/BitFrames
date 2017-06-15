package proj.me.bitframe;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.List;

import proj.me.bitframe.exceptions.FrameException;
import proj.me.bitframe.helper.Utils;

/**
 * Created by root on 13/9/16.
 */

class UnframedPicassoTargetNew implements Target {
    WeakReference<ImageResult> imageResultSoftReference;
    BeanImage beanImage;

    UnframedPicassoTargetNew(ImageResult imageResult, BeanImage beanImage) {
        imageResultSoftReference = new WeakReference<>(imageResult);
        this.beanImage = beanImage;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        ImageResult imageResult = imageResultSoftReference.get();
        if(imageResult == null) return;
        Utils.logMessage("Came image loaded -> "+imageResult.getCounter() + " bit width = "+bitmap.getWidth()+" height "+bitmap.getHeight());
        imageResult.callNextCycle(beanImage.getImageLink());
        imageResult.handleTransformedResult(bitmap, bitmap.getWidth() > 1 ? beanImage : null);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        ImageResult imageResult = imageResultSoftReference.get();
        if(imageResult == null) return;
        boolean doneLoading = imageResult.getCounter() == (imageResult.getFrameModel().getMaxFrameCount()
                >= imageResult.getTotalImages() ? imageResult.getTotalImages()
                : imageResult.getFrameModel().getMaxFrameCount()) - 1;
        if(doneLoading) imageResult.updateCounter();
        imageResult.setDoneLoading(doneLoading);
        try {
            imageResult.onImageLoaded(false, null, beanImage);
        } catch (FrameException e) {
            e.printStackTrace();
        }

        //pass this directly to container as it has no result
        BeanBitFrame beanBitFrame = new BeanBitFrame();
        imageResult.getImageCallback().frameResult(beanBitFrame);

        Utils.logVerbose("Came image failed -> "+imageResult.getCounter());
        imageResult.callNextCycle(null);
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        //for placeholder
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof UnframedPicassoTargetNew)) return false;
        BeanImage beanImageTarget = ((UnframedPicassoTargetNew)o).beanImage;
        return beanImage.getImageLink().equals(beanImageTarget.getImageLink())
                && (beanImage.getImageComment() == null || beanImage.getImageComment().equals(beanImageTarget.getImageComment()))
                && beanImage.getPrimaryCount() == beanImageTarget.getPrimaryCount()
                && beanImage.getSecondaryCount() == beanImageTarget.getSecondaryCount();
    }

    @Override
    public int hashCode() {
        int result = beanImage.getPrimaryCount() + beanImage.getSecondaryCount();
        result = result <= 0 ? 17 : result;

        //result = 31 * result + beanImage.hashCode(); -- because bean image properties needs to to checked not bean image itself
        result = 31 * result + beanImage.getImageLink().hashCode();
        if(beanImage.getImageComment() != null) result = 31 * result + beanImage.getImageComment().hashCode();
        result = 31 * result + beanImage.getPrimaryCount();
        result = 31 * result + beanImage.getSecondaryCount();

        return result;
    }
}
