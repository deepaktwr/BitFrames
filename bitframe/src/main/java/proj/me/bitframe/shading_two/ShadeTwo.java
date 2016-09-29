package proj.me.bitframe.shading_two;

import java.util.ArrayList;
import java.util.List;

import proj.me.bitframe.FrameModel;
import proj.me.bitframe.dimentions.BeanShade2;
import proj.me.bitframe.dimentions.ImageOrder;
import proj.me.bitframe.dimentions.LayoutType;
import static java.lang.Math.round;


/**
 * Created by root on 21/3/16.
 * counting 1 to last in horizontal manner
 */
 class ShadeTwo {

    private static float WIDTH_1 = 0, WIDTH_2 = 0, HEIGHT_1 = 0, HEIGHT_2 = 0;
     static BeanShade2 calculateDimentions(FrameModel frameModel, float width1, float height1, float width2, float height2){
        WIDTH_1 = 0; WIDTH_2 = 0; HEIGHT_1 = 0; HEIGHT_2 = 0;
        BeanShade2 beanShade2 = new BeanShade2();
        //width1 amount to required width
        float amountW1 = width1 / frameModel.getMaxContainerWidth();
        //height1 amount to required height
        float amountH1 = height1 / frameModel.getMaxContainerHeight();

        //width2 amount to required width
        float amountW2 = width2 / frameModel.getMaxContainerWidth();
        //height2 amount to required height
        float amountH2 = height2 / frameModel.getMaxContainerHeight();

        beanShade2.setLayoutType(LayoutType.HORZ);

        List<ImageOrder> imageOrderList = new ArrayList<>();

        float requiredMinWidth = frameModel.getMinFrameWidth() + frameModel.getMinFrameWidth() / 1.5f;

        if(amountW1 >= amountH1 && amountW1 >= amountH2 && amountW1 >= amountW2 && (width1 >= requiredMinWidth || width2 >= requiredMinWidth)){
            imageOrderList.add(ImageOrder.FIRST);
            imageOrderList.add(ImageOrder.SECOND);
            //will go vert with first image
            beanShade2.setLayoutType(LayoutType.VERT);

            //pre width calculation

            WIDTH_1 = width1 > frameModel.getMaxContainerWidth() ? frameModel.getMaxContainerWidth() : width1 < frameModel.getMinFrameWidth() ? frameModel.getMinFrameWidth() : width1;
            WIDTH_2 = width2 > frameModel.getMaxContainerWidth() ? frameModel.getMaxContainerWidth() : width2 < frameModel.getMinFrameWidth() ? frameModel.getMinFrameWidth() : width2;

            HEIGHT_1 = height1 < frameModel.getMinFrameHeight() ? frameModel.getMinFrameHeight() : height1;
            HEIGHT_2 = height2 < frameModel.getMinFrameHeight() ? frameModel.getMinFrameHeight() : height2;

            calculateVert(frameModel, beanShade2, height1, height2);

        }else if(amountW2 >= amountH1 && amountW2 >= amountH2 && amountW2 >= amountW1 && (width1 >= requiredMinWidth || width2 >= requiredMinWidth)){
            imageOrderList.add(ImageOrder.SECOND);
            imageOrderList.add(ImageOrder.FIRST);


            //will go vert with second image
            beanShade2.setLayoutType(LayoutType.VERT);

            //pre width calculation

            WIDTH_1 = width2 > frameModel.getMaxContainerWidth() ? frameModel.getMaxContainerWidth() : width2 < frameModel.getMinFrameWidth() ? frameModel.getMinFrameWidth() : width2;
            WIDTH_2 = width1 > frameModel.getMaxContainerWidth() ? frameModel.getMaxContainerWidth() : width1 < frameModel.getMinFrameWidth() ? frameModel.getMinFrameWidth() : width1;
            HEIGHT_1 = height2 < frameModel.getMinFrameHeight() ? frameModel.getMinFrameHeight() : height2;
            HEIGHT_2 = height1 < frameModel.getMinFrameHeight() ? frameModel.getMinFrameHeight() : height1;

            calculateVert(frameModel, beanShade2, height2, height1);

        }else if((amountH1 >= amountW1 && amountH1 >= amountW2 && amountH1 >= amountH2) || (amountH1 >= amountH2 && (width1 < requiredMinWidth && width2 < requiredMinWidth))){
            imageOrderList.add(ImageOrder.FIRST);
            imageOrderList.add(ImageOrder.SECOND);

            //will go horz with first image

            //pre height calculation
            HEIGHT_1 = height1 > frameModel.getMaxContainerHeight() ? frameModel.getMaxContainerHeight() : height1 < frameModel.getMinFrameHeight() ? frameModel.getMinFrameHeight() : height1;
            HEIGHT_2 = height2 > frameModel.getMaxContainerHeight() ? frameModel.getMaxContainerHeight() : height2 < frameModel.getMinFrameHeight() ? frameModel.getMinFrameHeight() : height2;

            WIDTH_1 = width1 < frameModel.getMinFrameWidth() ? frameModel.getMinFrameWidth() : width1;
            WIDTH_2 = width2 < frameModel.getMinFrameWidth() ? frameModel.getMinFrameWidth() : width2;

            calculateHorz(frameModel, beanShade2, width1, width2);

        }else if((amountH2 >= amountW1 && amountH2 >= amountW2 && amountH2 >= amountH1) || (amountH2 >= amountH1 && (width1 < requiredMinWidth && width2 < requiredMinWidth))){
            imageOrderList.add(ImageOrder.SECOND);
            imageOrderList.add(ImageOrder.FIRST);

            //will go horz with second image

            //pre height calculation
            HEIGHT_1 = height2 > frameModel.getMaxContainerHeight() ? frameModel.getMaxContainerHeight() : height2 < frameModel.getMinFrameHeight() ? frameModel.getMinFrameHeight() : height2;
            HEIGHT_2 = height1 > frameModel.getMaxContainerHeight() ? frameModel.getMaxContainerHeight() : height1 < frameModel.getMinFrameHeight() ? frameModel.getMinFrameHeight() : height1;

            WIDTH_1 = width2 < frameModel.getMinFrameWidth() ? frameModel.getMinFrameWidth() : width2;
            WIDTH_2 = width1 < frameModel.getMinFrameWidth() ? frameModel.getMinFrameWidth() : width1;

            calculateHorz(frameModel, beanShade2, width2, width1);
        }

        beanShade2.setImageOrderList(imageOrderList);

        beanShade2.setWidth1(round(WIDTH_1));
        beanShade2.setWidth2(round(WIDTH_2));
        beanShade2.setHeight1(round(HEIGHT_1));
        beanShade2.setHeight2(round(HEIGHT_2));

        return beanShade2;
    }

    private static void calculateVert(FrameModel frameModel, BeanShade2 beanShade2, float height1, float height2){
        //adjust add button in layout through width
        float maxImageWidthForAdd = WIDTH_1 - WIDTH_1 * frameModel.getMinAddRatio();
        if(!frameModel.isShouldShowComment() || WIDTH_2 >= WIDTH_1 * 0.9f){
            if(frameModel.isHasFixedDimensions()) WIDTH_1 = frameModel.getMaxContainerWidth();
            WIDTH_2 = WIDTH_1;
            beanShade2.setAddInLayout(false);
        }else if(WIDTH_2 > maxImageWidthForAdd && maxImageWidthForAdd >= frameModel.getMinFrameWidth()){
            if(frameModel.isHasFixedDimensions()) WIDTH_1 = frameModel.getMaxContainerWidth();
            WIDTH_2 = maxImageWidthForAdd;
            beanShade2.setAddInLayout(true);
        }else if(WIDTH_2 > maxImageWidthForAdd){
            float width1Increase = frameModel.getMinFrameWidth() - maxImageWidthForAdd;
            WIDTH_1 = frameModel.isHasFixedDimensions() || WIDTH_1 + width1Increase > frameModel.getMaxContainerWidth() ? frameModel.getMaxContainerWidth() : WIDTH_1 + width1Increase;
            WIDTH_2 = frameModel.getMinFrameWidth();
            beanShade2.setAddInLayout(true);
        }else beanShade2.setAddInLayout(true);


        //post height calculation
        float sumHeight = HEIGHT_1 + HEIGHT_2;
        if(sumHeight > frameModel.getMaxContainerHeight() || frameModel.isHasFixedDimensions()){
            float ratio = (float)height1 / (height1 + height2);
            HEIGHT_1 = frameModel.getMaxContainerHeight() * ratio;
            HEIGHT_2 = frameModel.getMaxContainerHeight() * (1f - ratio);

            if(HEIGHT_1 < frameModel.getMinFrameHeight()){
                HEIGHT_1 = frameModel.getMinFrameHeight();
                if(frameModel.getMinFrameHeight() + HEIGHT_2 > frameModel.getMaxContainerHeight()) HEIGHT_2 = frameModel.getMaxContainerHeight() - frameModel.getMinFrameHeight();
            } else if(HEIGHT_2 < frameModel.getMinFrameHeight()){
                HEIGHT_2  = frameModel.getMinFrameHeight();
                if(frameModel.getMinFrameHeight() + HEIGHT_1 > frameModel.getMaxContainerHeight()) HEIGHT_1 = frameModel.getMaxContainerHeight() - frameModel.getMinFrameHeight();
            }
        }
    }
    private static void calculateHorz(FrameModel frameModel, BeanShade2 beanShade2, float width1, float width2){
        //adjust add button in layout through height
        float maxImageHeightForAdd = HEIGHT_1 - HEIGHT_1 * frameModel.getMinAddRatio();
        if(!frameModel.isShouldShowComment() || HEIGHT_2 >= HEIGHT_1 * 0.9f){
            if(frameModel.isHasFixedDimensions()) HEIGHT_1 = frameModel.getMaxContainerHeight();
            HEIGHT_2 = HEIGHT_1;
            beanShade2.setAddInLayout(false);
        }else if(HEIGHT_2 > maxImageHeightForAdd && maxImageHeightForAdd >= frameModel.getMinFrameHeight()){
            if(frameModel.isHasFixedDimensions()) HEIGHT_1 = frameModel.getMaxContainerHeight();
            HEIGHT_2 = maxImageHeightForAdd;
            beanShade2.setAddInLayout(true);
        }else if(HEIGHT_2 > maxImageHeightForAdd){
            float height1Increase = frameModel.getMinFrameHeight() - maxImageHeightForAdd;
            HEIGHT_1 = frameModel.isHasFixedDimensions() || HEIGHT_1 + height1Increase > frameModel.getMaxContainerHeight() ? frameModel.getMaxContainerHeight() : HEIGHT_1 + height1Increase;
            HEIGHT_2 = frameModel.getMinFrameHeight();
            beanShade2.setAddInLayout(true);
        }else beanShade2.setAddInLayout(true);

        //post width calculation
        float sumWidth = WIDTH_1 + WIDTH_2;
        if(sumWidth > frameModel.getMaxContainerWidth() || frameModel.isHasFixedDimensions()){
            float ratio =  (float)width1/ (width1 + width2);
            WIDTH_1 = frameModel.getMaxContainerWidth() * ratio;
            WIDTH_2 = frameModel.getMaxContainerWidth() * (1f - ratio);

            if(WIDTH_1 < frameModel.getMinFrameWidth()){
                WIDTH_1 = frameModel.getMinFrameWidth();
                if(frameModel.getMinFrameWidth() + WIDTH_2 > frameModel.getMaxContainerWidth()) WIDTH_2 = frameModel.getMaxContainerWidth() - frameModel.getMinFrameWidth();
            } else if(WIDTH_2 < frameModel.getMinFrameWidth()){
                WIDTH_2  = frameModel.getMinFrameWidth();
                if(frameModel.getMinFrameWidth() + WIDTH_1 > frameModel.getMaxContainerWidth()) WIDTH_1 = frameModel.getMaxContainerWidth() - frameModel.getMinFrameWidth();
            }
        }
    }
}
