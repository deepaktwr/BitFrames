package proj.me.bitframe.dimentions;

import android.graphics.Bitmap;

import proj.me.bitframe.BeanImage;

/**
 * Created by root on 16/9/16.
 */

public class BeanHandler {
    private Bitmap bitmap;
    private BeanImage beanImage;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public BeanImage getBeanImage() {
        return beanImage;
    }

    public void setBeanImage(BeanImage beanImage) {
        this.beanImage = beanImage;
    }
}
