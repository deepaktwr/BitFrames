package proj.me.bitframe.helper;


import proj.me.bitframe.BeanImage;

/**
 * Created by root on 13/9/16.
 */

public class BeanResult extends BeanBitmapResult {
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
