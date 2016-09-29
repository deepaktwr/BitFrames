package proj.me.bitframe.shading_two;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import proj.me.bitframe.Dimension;
import proj.me.bitframe.DimensionsModel;
import proj.me.bitframe.FrameModel;
import proj.me.bitframe.dimentions.BeanShade2;
import proj.me.bitframe.dimentions.ImageOrder;
import proj.me.bitframe.dimentions.LayoutType;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static java.lang.Math.round;

/**
 * Created by root on 23/3/16.
 */
@RunWith(Parameterized.class)
public class ShadeTwoTest {
    private DimensionsModel dimentionsModel;
    public ShadeTwoTest(DimensionsModel dimentionsModel){
        this.dimentionsModel = dimentionsModel;
    }

    @Mock
    FrameModel frameModel;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        float maxW = round(dimentionsModel.widthPixels - dimentionsModel.widthPixels * 0.04f);
        float maxH = round(maxW + maxW * 0.15f > dimentionsModel.heightPixels ? maxW : maxW + maxW * 0.15f);

        maxW = maxW % 2 == 0 ? maxW : maxW + 1;
        maxH = maxH % 2 == 0 ? maxH : maxH + 1;

        float minW = round(maxW * 0.30f);
        float minH = round(maxH * 0.30f);

        minW = minW % 2 == 0 ? minW : minW + 1;
        minH = minH % 2 == 0 ? minH : minH + 1;

        System.out.println("Start++++++++++++++++++++++++++++++++++++Start");
        System.out.println("width pixels : "+dimentionsModel.widthPixels);
        System.out.println("height pixels : "+dimentionsModel.heightPixels);
        System.out.println("maxW : "+maxW);
        System.out.println("maxH : "+maxH);
        System.out.println("minW : "+minW);
        System.out.println("minH : "+minH);
        System.out.println("End++++++++++++++++++++++++++++++++++++End");



        when(frameModel.getMaxContainerWidth()).thenReturn(maxW);
        when(frameModel.getMaxContainerHeight()).thenReturn(maxH);
        when(frameModel.getMinFrameWidth()).thenReturn(minW);
        when(frameModel.getMinFrameHeight()).thenReturn(minH);
        when(frameModel.isAddInLayout()).thenReturn(false);
        when(frameModel.getMinAddRatio()).thenReturn(0.25f);
        when(frameModel.isHasFixedDimensions()).thenReturn(true);
        when(frameModel.isShouldShowComment()).thenReturn(false);
    }

    @Parameterized.Parameters
    public static Collection<DimensionsModel> fakeData(){
        boolean vertLayout = true;
        boolean firstImageLeftOrTop = true;
        List<DimensionsModel> dimensionsModelList = new ArrayList();
        for(Dimension dimension : DimensionsModel.DIMENSIONS){
            DimensionsModel[] data = new DimensionsModel[]{
                    new DimensionsModel(new int[]{500, 400, 300, 100}, firstImageLeftOrTop, vertLayout, dimension.widthPixels, dimension.heightPixels),
                    new DimensionsModel(new int[]{5000, 4000, 8000, 10000}, !firstImageLeftOrTop, !vertLayout, dimension.widthPixels, dimension.heightPixels),
                    new DimensionsModel(new int[]{200, 500, 300, 400}, firstImageLeftOrTop, !vertLayout, dimension.widthPixels, dimension.heightPixels),
                    new DimensionsModel(new  int[]{10, 20, 30, 40}, !firstImageLeftOrTop, !vertLayout, dimension.widthPixels, dimension.heightPixels),
                    new DimensionsModel(new  int[]{500, 20, 500, 40}, firstImageLeftOrTop, vertLayout, dimension.widthPixels, dimension.heightPixels),
                    new DimensionsModel(new  int[]{500, 600, 5000, 400}, !firstImageLeftOrTop, vertLayout, dimension.widthPixels, dimension.heightPixels),
                    new DimensionsModel(new  int[]{105, 186, 300, 168}, firstImageLeftOrTop, !vertLayout, dimension.widthPixels, dimension.heightPixels),
                    new DimensionsModel(new  int[]{300, 168, 105, 186}, !firstImageLeftOrTop, !vertLayout, dimension.widthPixels, dimension.heightPixels),
                    new DimensionsModel(new  int[]{500, 1860, 1000, 1680}, firstImageLeftOrTop, !vertLayout, dimension.widthPixels, dimension.heightPixels),
            };
            dimensionsModelList.addAll(Arrays.asList(data));
        }
        return dimensionsModelList;
    }

    @Test
    public void calculateDimentions_ShouldReturnBean(){
        BeanShade2 beanShade2Result = ShadeTwo.calculateDimentions
                (frameModel, dimentionsModel.params[0],
                dimentionsModel.params[1],
                dimentionsModel.params[2],
                dimentionsModel.params[3]);

        boolean isFirstImageLeftOrTop = beanShade2Result.getImageOrderList().get(0) == ImageOrder.FIRST;
        boolean isVertLayout = beanShade2Result.getLayoutType() == LayoutType.VERT;

        System.out.println("Start++++++++++++++++++++++++++++++++++++Start");
        System.out.println("running for : "+dimentionsModel.params[0]+" "+dimentionsModel.params[1] + " "+dimentionsModel.params[2] + " " + dimentionsModel.params[3]);
        System.out.println("getWidth1 : "+beanShade2Result.getWidth1());
        System.out.println("getHeight1 : "+beanShade2Result.getHeight1());
        System.out.println("getWidth2 : "+beanShade2Result.getWidth2());
        System.out.println("getHeight2 : "+beanShade2Result.getHeight2());
        System.out.println("isFirstImageLeftOrTop = expected: "+dimentionsModel.firstImageLeftOrTop+" actual: "+isFirstImageLeftOrTop);
        System.out.println("isVertLayout = expected: "+dimentionsModel.vertLayout+" actual: "+isVertLayout);
        System.out.println("End++++++++++++++++++++++++++++++++++++End");

        /*assertEquals(dimentionsModel.firstImageLeftOrTop, isFirstImageLeftOrTop);
        assertEquals(dimentionsModel.vertLayout, isVertLayout);*/

        int MAX_WIDTH = round(frameModel.getMaxContainerWidth()) + 1;
        int MAX_HEIGHT = round(frameModel.getMaxContainerHeight()) + 1;
        int MIN_WIDTH = round(frameModel.getMinFrameWidth());
        int MIN_HEIGHT = round(frameModel.getMinFrameHeight());


        if(/*dimentionsModel.vertLayout*/isVertLayout){
            assertTrue(beanShade2Result.getWidth1() >= MIN_WIDTH);
            assertTrue(beanShade2Result.getWidth1() <= MAX_WIDTH);

            assertTrue(beanShade2Result.getWidth2() >= MIN_WIDTH);
            assertTrue(beanShade2Result.getWidth2() <= MAX_WIDTH);

            assertTrue(beanShade2Result.getHeight1() >= MIN_HEIGHT);
            assertTrue(beanShade2Result.getHeight2() >= MIN_HEIGHT);

            float sumHeight = beanShade2Result.getHeight1() + beanShade2Result.getHeight2();

            assertTrue(sumHeight <= MAX_HEIGHT);

            /*float height1Ratio = beanShade2Result.getHeight1() / sumHeight;
            float height2Ratio = beanShade2Result.getHeight2() / sumHeight;

            assertTrue(height1Ratio >= Utils.MIN_RATIO || height1Ratio <= Utils.MAX_RATIO);
            assertTrue(height2Ratio >= Utils.MIN_RATIO || height2Ratio <= Utils.MAX_RATIO);*/

            if(beanShade2Result.isAddInLayout())
                assertTrue(beanShade2Result.getWidth1() > beanShade2Result.getWidth2());
            else assertTrue(beanShade2Result.getWidth1() == beanShade2Result.getWidth2());
        }else{
            assertTrue(beanShade2Result.getHeight1() >= MIN_HEIGHT);
            assertTrue(beanShade2Result.getHeight1() <= MAX_HEIGHT);

            assertTrue(beanShade2Result.getHeight2() >= MIN_HEIGHT);
            assertTrue(beanShade2Result.getHeight2() <= MAX_HEIGHT);

            assertTrue(beanShade2Result.getWidth1() >= MIN_WIDTH);
            assertTrue(beanShade2Result.getWidth2() >= MIN_WIDTH);

            float sumWidth = beanShade2Result.getWidth1() + beanShade2Result.getWidth2();

            assertTrue(sumWidth <= MAX_WIDTH);

            /*float width1Ratio = beanShade2Result.getWidth1() / sumWidth;
            float width2Ratio = beanShade2Result.getWidth2() / sumWidth;

            assertTrue(width1Ratio >= Utils.MIN_RATIO || width1Ratio <= Utils.MAX_RATIO);
            assertTrue(width2Ratio >= Utils.MIN_RATIO || width2Ratio <= Utils.MAX_RATIO);*/

            if(beanShade2Result.isAddInLayout())
                assertTrue(beanShade2Result.getHeight1() > beanShade2Result.getHeight2());
            else assertTrue(beanShade2Result.getHeight1() == beanShade2Result.getHeight2());
        }

    }
}
