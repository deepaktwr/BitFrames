package proj.me.bitframe;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import proj.me.bitframe.exceptions.FrameException;
import proj.me.bitframe.helper.Utils;

/**
 * Created by root on 15/9/16.
 */

class ScaleTransformation implements Transformation {
    float containerWidth;
    float containerHeight;
    int totalImage;
    String key;
    BeanImage beanImage;
    WeakReference<ImageResult> imageResultSoftReference;

    ScaleTransformation(float containerWidth, float containerHeight, int totalImage, String key, BeanImage beanImage, ImageResult imageResult){
        this.containerWidth = containerWidth;
        this.containerHeight = containerHeight;
        this.totalImage = totalImage;
        this.key = key;
        this.beanImage = beanImage;
        imageResultSoftReference = new WeakReference<>(imageResult);
    }
    @Override
    public Bitmap transform(Bitmap source) {
        totalImage = totalImage > 4 ? 4 : totalImage;
        Utils.logVerbose("transforming w : "+source.getWidth()+" h : "+source.getHeight());
        Bitmap target = null;
        try {
            target = Utils.getScaledBitmap(source,
                    (int)(containerWidth/(totalImage > 1 ? totalImage - 0.4f : 1)),
                    (int)(containerHeight/(totalImage > 1 ? totalImage - 0.4f : 1)));
        } catch (FrameException e) {
            e.printStackTrace();
        }
        boolean isNotEqual = false;
        if(isNotEqual = target != source) source.recycle();

        ImageResult imageResult = imageResultSoftReference.get();

        if(imageResult == null) return target;

        if(isNotEqual) imageResult.handleTransformedResult(target, beanImage);

        return isNotEqual ? Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565) : target;
    }



    @Override
    public String key() {
        return "square";
    }
}
