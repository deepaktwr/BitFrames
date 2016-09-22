package proj.me.bitframe.dimentions;

import android.graphics.Bitmap;

import java.lang.ref.WeakReference;
import java.util.List;

import proj.me.bitframe.BeanImage;
import proj.me.bitframe.FrameModel;
import proj.me.bitframe.ImageCallback;
import proj.me.bitframe.ImageShading;

/**
 * Created by root on 13/9/16.
 */

public class BeanResult extends BeanBitmapResult{
    private BeanImage beanImage;
    private int inSampleSize;

    public BeanImage getBeanImage() {
        return beanImage;
    }

    public void setBeanImage(BeanImage beanImage) {
        this.beanImage = beanImage;
    }
    public int getInSampleSize() {
        return inSampleSize;
    }

    public void setInSampleSize(int inSampleSize) {
        this.inSampleSize = inSampleSize;
    }
}
