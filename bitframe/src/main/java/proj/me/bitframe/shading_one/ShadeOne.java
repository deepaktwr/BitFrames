package proj.me.bitframe.shading_one;

import proj.me.bitframe.FrameModel;
import proj.me.bitframe.dimentions.BeanShade1;

/**
 * Created by root on 21/3/16.
 */
 class ShadeOne {
     static BeanShade1 calculateDimentions(FrameModel frameModel, int width, int height){
        BeanShade1 beanShade1 = new BeanShade1();
        float WIDTH_1 = frameModel.isHasFixedDimensions() || width > frameModel.getMaxContainerWidth() ? frameModel.getMaxContainerWidth() : width < frameModel.getMinFrameWidth() ? frameModel.getMinFrameWidth() : width;
        float HEIGHT_1 = frameModel.isHasFixedDimensions() || height > frameModel.getMaxContainerHeight() ? frameModel.getMaxContainerHeight() : height < frameModel.getMinFrameHeight() ? frameModel.getMinFrameHeight() : height;
        beanShade1.setWidth1((int)WIDTH_1);
        beanShade1.setHeight1((int)HEIGHT_1);
        return beanShade1;
    }
}
