package proj.me.bitframe;

import java.util.List;

/**
 * Created by root on 18/4/16.
 */
public interface FrameCallback {
    //1 2
    //3 4
    /**
     * @param imageType single to multiplevalue will be null if image has not been loaded.
     * @param imagePosition defined in horizontal way.
     * @param imageLink the link of the clicked image
     * */
    void imageClick(ImageType imageType, int imagePosition, String imageLink);
    /**
     * the result after all image links loaded
     * */
    void frameResult(List<BeanBitFrame> beanBitFrameList);
    /**
     * when add overflow clicked, if it's been added the the container
     * */
    void addMoreClick();

    void containerAdded(int containerWidth, int containerHeight, boolean isAddInLayout);

    void loadedFrameColors(int lastLoadedFrameColor, int mixedLoadedColor, int inverseMixedLoadedColor);
}
