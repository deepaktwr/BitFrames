package proj.me.bitframe;

import android.os.AsyncTask;
import androidx.palette.graphics.Palette;

import java.lang.ref.WeakReference;

import proj.me.bitframe.exceptions.FrameException;
import proj.me.bitframe.helper.BeanResult;
import proj.me.bitframe.helper.Utils;

/**
 * Created by root on 13/9/16.
 */

class UnframedLocalTask extends AsyncTask<BeanImage, Integer, BeanResult> {
    WeakReference<ImageResult> imageResultSoftReference;

    UnframedLocalTask(ImageResult imageResult){
        imageResultSoftReference = new WeakReference<>(imageResult);
    }
    @Override
    protected BeanResult doInBackground(BeanImage... params) {
        ImageResult imageResult = imageResultSoftReference.get();
        if(imageResult == null) return null;

        BeanImage beanImage = params[0];
        BeanResult beanResult = null;
        float totalImage = imageResult.getTotalImages() > 4 ? 4 : imageResult.getTotalImages();
        try {
            beanResult = Utils.decodeBitmapFromPath(
                    (int)(imageResult.getFrameModel().getMaxContainerWidth()/(totalImage > 1 ? totalImage - 0.4f : 1)),
                    (int)(imageResult.getFrameModel().getMaxContainerHeight()/(totalImage > 1 ? totalImage - 0.4f : 1)),
                    beanImage.getImageLink(), imageResult.getContext());
        } catch (FrameException e) {
            e.printStackTrace();
        }
        if(beanResult == null) beanResult = new BeanResult();
        beanResult.setBeanImage(beanImage);
        return beanResult;
    }

    @Override
    protected void onPostExecute(final BeanResult beanResult) {
        super.onPostExecute(beanResult);

        final ImageResult imageResult = imageResultSoftReference.get();
        if(imageResult == null || beanResult == null) return;

        if(beanResult.getBitmap() == null){
            boolean doneLoading = imageResult.getCounter() == (imageResult.getFrameModel().getMaxFrameCount()
                    >= imageResult.getTotalImages() ? imageResult.getTotalImages()
                    : imageResult.getFrameModel().getMaxFrameCount()) - 1;
            if(doneLoading) imageResult.updateCounter();
            imageResult.setDoneLoading(doneLoading);
            try {
                imageResult.onImageLoaded(false, null, beanResult.getBeanImage());
            } catch (FrameException e) {
                e.printStackTrace();
            }

            //pass this directly to container as it has no result
            BeanBitFrame beanBitFrame = new BeanBitFrame();
            imageResult.getImageCallback().frameResult(beanBitFrame);

            Utils.logVerbose("Came image failed -> "+imageResult.getCounter());
            imageResult.callNextCycle(null);
            return;
        }
        if(imageResult.getCounter() >= imageResult.getFrameModel().getMaxFrameCount()){
            imageResult.updateCounter();
            final BeanBitFrame beanBitFrame = new BeanBitFrame();
            beanBitFrame.setWidth(beanResult.getWidth());
            beanBitFrame.setHeight(beanResult.getHeight());
            beanBitFrame.setLoaded(true);
            Palette.from(beanResult.getBitmap()).generate(new PaletteListener(imageResult, beanBitFrame, beanResult.getBeanImage(), true));
        }else {
            boolean doneLoading = imageResult.getCounter() == (imageResult.getFrameModel().getMaxFrameCount()
                    >= imageResult.getTotalImages() ? imageResult.getTotalImages()
                    : imageResult.getFrameModel().getMaxFrameCount()) - 1;
            imageResult.setDoneLoading(doneLoading);
            try {
                imageResult.onImageLoaded(true, beanResult.getBitmap(), beanResult.getBeanImage());
            } catch (FrameException e) {
                e.printStackTrace();
            }
            imageResult.updateCounter();
            imageResult.callNextCycle(null);
        }
    }
}