package proj.me.bitframe;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.graphics.Palette;

import proj.me.bitframe.dimentions.BeanResult;
import proj.me.bitframe.helper.Utils;

/**
 * Created by root on 13/9/16.
 */

public class UnframedLocalTask extends AsyncTask<BeanImage, Integer, BeanResult> {
    ImageResult imageResult;

    UnframedLocalTask(ImageResult imageResult){
        this.imageResult = imageResult;
    }
    @Override
    protected BeanResult doInBackground(BeanImage... params) {
        Utils.logError("came ");
        BeanImage beanImage = params[0];
        float totalImage = imageResult.getTotalImages() > 4 ? 4 : imageResult.getTotalImages();
        BeanResult beanResult = Utils.decodeBitmapFromPath(
                (int)(imageResult.getFrameModel().getMaxContainerWidth()/(totalImage > 1 ? totalImage - 0.4f : 1)),
                (int)(imageResult.getFrameModel().getMaxContainerHeight()/(totalImage > 1 ? totalImage - 0.4f : 1)),
                beanImage.getImageLink(),
                beanImage.isHasExtention(), imageResult.getContext());
        if(beanResult == null) beanResult = new BeanResult();
        beanResult.setBeanImage(beanImage);
        return beanResult;
    }

    @Override
    protected void onPostExecute(final BeanResult beanResult) {
        super.onPostExecute(beanResult);
        if(beanResult.getBitmap() == null){
            boolean doneLoading = imageResult.getCounter() == (imageResult.getFrameModel().getMaxFrameCount()
                    >= imageResult.getTotalImages() ? imageResult.getTotalImages()
                    : imageResult.getFrameModel().getMaxFrameCount()) - 1;
            if(doneLoading) imageResult.updateCounter();
            imageResult.setDoneLoading(doneLoading);
            imageResult.onImageLoaded(false, null, beanResult.getBeanImage());

            //pass this directly to container as it has no result
            BeanBitFrame beanBitFrame = new BeanBitFrame();
            imageResult.getImageCallback().frameResult(beanBitFrame);

            Utils.logError("Came image failed -> "+imageResult.getCounter());
            imageResult.callNextCycle(null);
            return;
        }
        if(imageResult.getCounter() >= imageResult.getFrameModel().getMaxFrameCount()){
            imageResult.updateCounter();
            final BeanBitFrame beanBitFrame = new BeanBitFrame();
            beanBitFrame.setWidth(beanResult.getWidth());
            beanBitFrame.setHeight(beanResult.getHeight());
            beanBitFrame.setLoaded(true);
            Palette.from(beanResult.getBitmap()).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
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

                    BeanImage beanImage = beanResult.getBeanImage();

                    beanBitFrame.setHasExtention(beanImage.isHasExtention());
                    beanBitFrame.setLocalImage(beanImage.isLocalImage());
                    beanBitFrame.setImageComment(beanImage.getImageComment());
                    beanBitFrame.setImageLink(beanImage.getImageLink());
                    beanBitFrame.setPrimaryCount(beanImage.getPrimaryCount());
                    beanBitFrame.setSecondaryCount(beanImage.getSecondaryCount());

                    imageResult.getImageCallback().frameResult(beanBitFrame);
                    if(!beanResult.getBitmap().isRecycled()) beanResult.getBitmap().recycle();
                    Utils.logError("loading new image after recycle");
                    imageResult.callNextCycle(null);
                }
            });
        }else {
            boolean doneLoading = imageResult.getCounter() == (imageResult.getFrameModel().getMaxFrameCount()
                    >= imageResult.getTotalImages() ? imageResult.getTotalImages()
                    : imageResult.getFrameModel().getMaxFrameCount()) - 1;
            imageResult.setDoneLoading(doneLoading);
            imageResult.onImageLoaded(true, beanResult.getBitmap(), beanResult.getBeanImage());
            imageResult.updateCounter();
            Utils.logError("loading new image");
            imageResult.callNextCycle(null);
        }
    }
}
