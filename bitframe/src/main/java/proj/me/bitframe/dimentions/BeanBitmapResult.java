package proj.me.bitframe.dimentions;

import android.graphics.Bitmap;

import proj.me.bitframe.BeanImage;

/**
 * Created by root on 14/9/16.
 */

public class BeanBitmapResult {
    private Bitmap bitmap;
    private int width;
    private int height;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
