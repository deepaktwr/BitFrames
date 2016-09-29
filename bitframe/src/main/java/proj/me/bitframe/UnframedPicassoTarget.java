package proj.me.bitframe;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import proj.me.bitframe.exceptions.FrameException;
import proj.me.bitframe.helper.Utils;

/**
 * Created by root on 13/9/16.
 */

class UnframedPicassoTarget implements Target {
    BeanImage beanImage;
    boolean doneLoading;
    ImageResult imageResult;
    List<UnframedPicassoTarget> targets;

    UnframedPicassoTarget(BeanImage beanImage, List<UnframedPicassoTarget> targets, ImageResult imageResult) {
        this.beanImage = beanImage;
        this.imageResult = imageResult;
        this.targets = targets;
    }

    @Override
    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
        targets.remove(this);

        /*float totalImage = imageResult.getTotalImages() > 4 ? 4 : imageResult.getTotalImages();

        BeanBitmapResult beanBitmapResult = Utils.getScaledResult(bitmap,
                (int)(imageResult.getFrameModel().getMaxContainerWidth()/(totalImage > 1 ? totalImage - 0.4f : 1)),
                (int)(imageResult.getFrameModel().getMaxContainerHeight()/(totalImage > 1 ? totalImage - 0.4f : 1)));*/

        /*BeanBitmapResult beanBitmapResult = new BeanBitmapResult();
        beanBitmapResult.setHeight(bitmap.getHeight());
        beanBitmapResult.setWidth(bitmap.getWidth());
        beanBitmapResult.setBitmap(bitmap);*/

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
                    bitmap.recycle();
                    Utils.logVerbose("loading new image after recycle");
                    imageResult.callNextCycle(beanImage.getImageLink());*/

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

                    beanBitFrame.setImageComment(beanImage.getImageComment());
                    beanBitFrame.setImageLink(beanImage.getImageLink());
                    beanBitFrame.setPrimaryCount(beanImage.getPrimaryCount());
                    beanBitFrame.setSecondaryCount(beanImage.getSecondaryCount());

                    imageResult.getImageCallback().frameResult(beanBitFrame);
                    if(!bitmap.isRecycled()) bitmap.recycle();
                    Utils.logVerbose("loading new image after recycle");
                    imageResult.callNextCycle(beanImage.getImageLink());
                }
            });
        }else {
            doneLoading = imageResult.getCounter() == (imageResult.getFrameModel().getMaxFrameCount()
                    >= imageResult.getTotalImages() ? imageResult.getTotalImages() : imageResult.getFrameModel().getMaxFrameCount()) - 1;
            imageResult.setDoneLoading(doneLoading);
            try {
                imageResult.onImageLoaded(true, /*beanBitmapResult.getBitmap()*/bitmap, beanImage);
            } catch (FrameException e) {
                e.printStackTrace();
            }
            imageResult.updateCounter();
            Utils.logVerbose("loading new image");
            imageResult.callNextCycle(beanImage.getImageLink());
        }
        Utils.logMessage("Came image loaded -> "+imageResult.getCounter());
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        targets.remove(this);
        /*if(doneLoading)*/ imageResult.updateCounter();
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
    }

    @Override
    public boolean equals(Object o) {
        return this.beanImage.getImageLink().equals(((UnframedPicassoTarget)o).beanImage.getImageLink());
    }

    @Override
    public int hashCode() {
        return targets.size() + 1;
    }
}
