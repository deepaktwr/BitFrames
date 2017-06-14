package proj.me.bitframe;

import android.graphics.Color;
import android.support.v7.graphics.Palette;

import java.lang.ref.SoftReference;

import proj.me.bitframe.helper.Utils;

final class PaletteListener implements Palette.PaletteAsyncListener {
    SoftReference<ImageResult> imageResultSoftReference;
    BeanBitFrame beanBitFrame;
    BeanImage beanImage;
    boolean shouldCallNextCycle;

    PaletteListener(ImageResult imageResult, BeanBitFrame beanBitFrame, BeanImage beanImage, boolean shouldCallNextCycle) {
        imageResultSoftReference = new SoftReference<>(imageResult);
        this.beanBitFrame = beanBitFrame;
        this.beanImage = beanImage;
        this.shouldCallNextCycle = shouldCallNextCycle;
    }

    @Override
    public void onGenerated(Palette palette) {
        ImageResult imageResult = imageResultSoftReference.get();
        if (imageResult == null){
            Utils.logVerbose("PaletteListener, ImageResult : collected ");
            return;
        }

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

        //without this memory fluctuates more but cpu usage are less
        /*Utils.logVerbose("exppp pallet width " + bitmap.getWidth() + " height " + bitmap.getHeight());
        if (!bitmap.isRecycled()) bitmap.recycle();*/
        //also because if view frame fails in hash code then also we are not recycling bitmaps

        if(shouldCallNextCycle) imageResult.callNextCycle(null);
    }
}