package proj.me.bitframe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import java.util.List;

/**
 * Created by Deepak.Tiwari on 30-10-2015.
 */
public interface ImageCallback{
    void addImageView(View view, int viewWidth, int viewHeight, boolean hasAddInLayout);
    //1 2
    //3 4
    /**
     * @param imageType single to multiplevalue will be null if image has not been loaded.
     * @param imagePosition defined in horizontal way.
     * @param imageLink the link of the clicked image
     * */
    void imageClicked(ImageType imageType, int imagePosition, String imageLink);

    void setColorsToAddMoreView(int resultColor, int mixedColor, int invertedColor);

    void frameResult(BeanBitFrame ... beanBitFrames);

    void addMore();
}
