package proj.me.bitframe;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.List;

import proj.me.bitframe.exceptions.FrameException;

/**
 * Created by root on 13/9/16.
 */

interface ImageResult {
    void onImageLoaded(boolean result, Bitmap bitmap, BeanImage beanImage) throws FrameException;

    void setDoneLoading(boolean doneLoading);

    FrameModel getFrameModel();

    ImageCallback getImageCallback();

    int getTotalImages();

    Context getContext();

    void updateCounter();

    int getCounter();

    void callNextCycle(String lastImagePath);

    void handleTransformedResult(Bitmap bitmap, BeanImage beanImage);

    List<Bitmap> getImages();
}
