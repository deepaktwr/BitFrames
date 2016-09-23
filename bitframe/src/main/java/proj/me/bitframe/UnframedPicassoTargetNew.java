package proj.me.bitframe;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import proj.me.bitframe.helper.Utils;

/**
 * Created by root on 13/9/16.
 */

class UnframedPicassoTargetNew implements Target {
    BeanImage beanImage;
    ImageResult imageResult;
    List<UnframedPicassoTargetNew> targets;

    UnframedPicassoTargetNew(BeanImage beanImage, List<UnframedPicassoTargetNew> targets, ImageResult imageResult) {
        this.beanImage = beanImage;
        this.imageResult = imageResult;
        this.targets = targets;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        targets.remove(this);
        Utils.logMessage("Came image loaded -> "+imageResult.getCounter() + "bit width = "+bitmap.getWidth()+" height "+bitmap.getHeight());
        imageResult.callNextCycle(beanImage.getImageLink());
        imageResult.handleTransformedResult(bitmap, bitmap.getWidth() > 1 ? beanImage : null);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        targets.remove(this);
        boolean doneLoading = imageResult.getCounter() == (imageResult.getFrameModel().getMaxFrameCount()
                >= imageResult.getTotalImages() ? imageResult.getTotalImages()
                : imageResult.getFrameModel().getMaxFrameCount()) - 1;
        if(doneLoading) imageResult.updateCounter();
        imageResult.setDoneLoading(doneLoading);
        imageResult.onImageLoaded(false, null, beanImage);

        //pass this directly to container as it has no result
        BeanBitFrame beanBitFrame = new BeanBitFrame();
        imageResult.getImageCallback().frameResult(beanBitFrame);

        Utils.logError("Came image failed -> "+imageResult.getCounter());
        imageResult.callNextCycle(null);
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
    }

    @Override
    public boolean equals(Object o) {
        return this.beanImage.getImageLink().equals(((UnframedPicassoTargetNew)o).beanImage.getImageLink());
    }

    @Override
    public int hashCode() {
        return targets.size() + 1;
    }
}
