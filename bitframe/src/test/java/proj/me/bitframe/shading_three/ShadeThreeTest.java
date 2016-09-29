package proj.me.bitframe.shading_three;

import static java.lang.Math.round;
import static junit.framework.Assert.*;
import static org.mockito.Mockito.when;

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
import proj.me.bitframe.MultiDimensionModel;
import proj.me.bitframe.dimentions.BeanShade3;
import proj.me.bitframe.dimentions.ImageOrder;
import proj.me.bitframe.dimentions.LayoutType;

/**
 * Created by root on 3/4/16.
 */
@RunWith(Parameterized.class)
public class ShadeThreeTest {
    private MultiDimensionModel dimentionsModel;
    public ShadeThreeTest(MultiDimensionModel dimentionsModel){
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
    public static Collection<MultiDimensionModel> fakeData(){
        List<MultiDimensionModel> multiDimensionModels = new ArrayList<>();
        for(Dimension dimension : DimensionsModel.DIMENSIONS) {
            //8 cases
            MultiDimensionModel[] data = new MultiDimensionModel[]{
                    //parallel horz
                    new MultiDimensionModel(new int[]{
                            //835>= >=670, 630
                            225, 653,
                            225, 653,
                            225, 653
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.PARALLEL_HORZ,
                            dimension.widthPixels, dimension.heightPixels),
                    new MultiDimensionModel(new int[]{
                            //835>= >=670, 630
                            400, 653,
                            100, 953,
                            200, 753
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.PARALLEL_HORZ,
                            dimension.widthPixels, dimension.heightPixels),
                    new MultiDimensionModel(new int[]{
                            //835>= >=670, 630
                            50, 1053,
                            550, 640,
                            200, 800
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.PARALLEL_HORZ,
                            dimension.widthPixels, dimension.heightPixels),
                    new MultiDimensionModel(new int[]{
                            //835>= >=670, 630
                            400, 700,
                            30, 900,
                            400, 850
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.PARALLEL_HORZ,
                            dimension.widthPixels, dimension.heightPixels),
                    new MultiDimensionModel(new int[]{
                            //835>= >=670, 630
                            350, 700,
                            100, 900,
                            250, 850
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.PARALLEL_HORZ,
                            dimension.widthPixels, dimension.heightPixels),
                    new MultiDimensionModel(new int[]{
                            //835>= >=670, 630
                            450, 700,
                            100, 900,
                            150, 850
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.PARALLEL_HORZ,
                            dimension.widthPixels, dimension.heightPixels),
                    new MultiDimensionModel(new int[]{
                            //835>= >=670, 630
                            600, 700,
                            100, 900,
                            100, 850
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.PARALLEL_HORZ,
                            dimension.widthPixels, dimension.heightPixels),
                    new MultiDimensionModel(new int[]{
                            //835>= >=670, 630
                            600, 700,
                            50, 900,
                            100, 850
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.PARALLEL_HORZ,
                            dimension.widthPixels, dimension.heightPixels),

                    //parallel vert
                    new MultiDimensionModel(new int[]{
                            650, 300,
                            650, 300,
                            650, 300
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.PARALLEL_VERT,
                            dimension.widthPixels, dimension.heightPixels),
                    new MultiDimensionModel(new int[]{
                            //>650, 934>= >=780
                            650, 350,
                            900, 350,
                            700, 200
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.PARALLEL_VERT,
                            dimension.widthPixels, dimension.heightPixels),
                    new MultiDimensionModel(new int[]{
                            //>650, 934>= >=780
                            1000, 420,
                            900, 400,
                            700, 100
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.PARALLEL_VERT,
                            dimension.widthPixels, dimension.heightPixels),
                    new MultiDimensionModel(new int[]{
                            //>650, 934>= >=780
                            1000, 100,
                            900, 420,
                            700, 400
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.PARALLEL_VERT,
                            dimension.widthPixels, dimension.heightPixels),
                    new MultiDimensionModel(new int[]{
                            //>650, 934>= >=780
                            1000, 400,
                            900, 100,
                            700, 420
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.PARALLEL_VERT,
                            dimension.widthPixels, dimension.heightPixels),

                    //vert
                    new MultiDimensionModel(new int[]{
                            1000, 500, 800, 400, 800, 400
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.VERT,
                            dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            100, 500, 1200, 400, 800, 400
                    }, new ImageOrder[]{ImageOrder.SECOND, ImageOrder.FIRST, ImageOrder.THIRD}, LayoutType.VERT,
                            dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            100, 500, 200, 400, 800, 400
                    }, new ImageOrder[]{ImageOrder.THIRD, ImageOrder.FIRST, ImageOrder.SECOND}, LayoutType.VERT,
                            dimension.widthPixels, dimension.heightPixels),

                    //horz
                    new MultiDimensionModel(new int[]{
                            100, 500, 100, 400, 300, 400
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.HORZ,
                            dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            200, 500, 500, 800, 600, 400
                    }, new ImageOrder[]{ImageOrder.SECOND, ImageOrder.FIRST, ImageOrder.THIRD}, LayoutType.HORZ,
                            dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            600, 600, 500, 700, 600, 1000
                    }, new ImageOrder[]{ImageOrder.THIRD, ImageOrder.FIRST, ImageOrder.SECOND}, LayoutType.HORZ,
                            dimension.widthPixels, dimension.heightPixels)
            };
            multiDimensionModels.addAll(Arrays.asList(data));
        }
        return multiDimensionModels;
    }
    //before or before class annotations are not calling before the parameterized colletion method

    @Test
    public void calculateDimentions_ShouldReturnBean(){

        BeanShade3 beanShade3Result = ShadeThree.calculateDimentions(frameModel,
                dimentionsModel.params[0], dimentionsModel.params[1],
                dimentionsModel.params[2], dimentionsModel.params[3],
                dimentionsModel.params[4], dimentionsModel.params[5]
        );

        System.out.println("Start++++++++++++++++++++++++++++++++++++Start");
        System.out.println("running for : "+dimentionsModel.params[0]+" "+dimentionsModel.params[1] + " "+dimentionsModel.params[2] + " " + dimentionsModel.params[3]);
        System.out.println("getWidth1 : "+beanShade3Result.getWidth1());
        System.out.println("getHeight1 : "+beanShade3Result.getHeight1());
        System.out.println("getWidth2 : "+beanShade3Result.getWidth2());
        System.out.println("getHeight2 : "+beanShade3Result.getHeight2());
        System.out.println("getWidth3 : "+beanShade3Result.getWidth3());
        System.out.println("getHeight3 : "+beanShade3Result.getHeight3());
        for(int i=0;i<3;i++){
            System.out.println("image order : "+ beanShade3Result.getImageOrderList().get(i));
        }
        System.out.println("layoutType : "+beanShade3Result.getLayoutType());
        System.out.println("End++++++++++++++++++++++++++++++++++++End");

        int MAX_WIDTH1 = round(frameModel.getMaxContainerWidth()) + 1;
        int MAX_HEIGHT1 = round(frameModel.getMaxContainerHeight()) + 1;
        int MIN_WIDTH1 = round(frameModel.getMinFrameWidth());
        int MIN_HEIGHT1 = round(frameModel.getMinFrameHeight());


        //not for now-- because ratio of width and height are varying based on dynamic dimensions
        /*assertTrue(beanShade3Result.getLayoutType() == dimentionsModel.layoutType);
        assertTrue(beanShade3Result.getImageOrderList().get(0) == dimentionsModel.imageOrders[0]);
        assertTrue(beanShade3Result.getImageOrderList().get(1) == dimentionsModel.imageOrders[1]);
        assertTrue(beanShade3Result.getImageOrderList().get(2) == dimentionsModel.imageOrders[2]);*/

        switch(beanShade3Result.getLayoutType()){
            case PARALLEL_VERT:
                float parallelVertRequiredWidth = MAX_WIDTH1 - MAX_WIDTH1 / 10f;
                float minParallelHeight = MAX_HEIGHT1 - MAX_HEIGHT1 / 5f;

                assertTrue(beanShade3Result.getWidth1() >= parallelVertRequiredWidth);
                assertTrue(beanShade3Result.getWidth2() >= parallelVertRequiredWidth);
                assertTrue(beanShade3Result.getWidth3() >= parallelVertRequiredWidth);

                float MIN_HIGHT = MIN_HEIGHT1 / 1.5f;
                assertTrue(beanShade3Result.getHeight1() >= MIN_HIGHT - 1);
                assertTrue(beanShade3Result.getHeight2() >= MIN_HIGHT - 1);
                assertTrue(beanShade3Result.getHeight3() >= MIN_HIGHT - 1);

                assertTrue(beanShade3Result.getHeight1() + beanShade3Result.getHeight2() + beanShade3Result.getHeight3() <= MAX_HEIGHT1);
                assertTrue(beanShade3Result.getHeight1() + beanShade3Result.getHeight2() + beanShade3Result.getHeight3() >= minParallelHeight);

                assertTrue(beanShade3Result.getImageOrderList().get(0) == ImageOrder.FIRST);
                assertTrue(beanShade3Result.getImageOrderList().get(1) == ImageOrder.SECOND);
                assertTrue(beanShade3Result.getImageOrderList().get(2) == ImageOrder.THIRD);
                break;
            case PARALLEL_HORZ:
                float parallelHorzRequiredHeight = MAX_HEIGHT1 - MAX_HEIGHT1 / 5f;
                float minParallelWidth = MAX_WIDTH1 - MAX_WIDTH1 / 5f;

                assertTrue(beanShade3Result.getHeight1() >= parallelHorzRequiredHeight);
                assertTrue(beanShade3Result.getHeight2() >= parallelHorzRequiredHeight);
                assertTrue(beanShade3Result.getHeight3() >= parallelHorzRequiredHeight);

                float MIN_WIDTH = MIN_WIDTH1 / 1.5f;
                assertTrue(beanShade3Result.getWidth1() >= MIN_WIDTH - 1);
                assertTrue(beanShade3Result.getWidth2() >= MIN_WIDTH - 1);
                assertTrue(beanShade3Result.getWidth3() >= MIN_WIDTH - 1);

                assertTrue(beanShade3Result.getWidth1() + beanShade3Result.getWidth2() + beanShade3Result.getWidth3() <= MAX_WIDTH1);
                assertTrue(beanShade3Result.getWidth1() + beanShade3Result.getWidth2() + beanShade3Result.getWidth3() >= minParallelWidth);

                assertTrue(beanShade3Result.getImageOrderList().get(0) == ImageOrder.FIRST);
                assertTrue(beanShade3Result.getImageOrderList().get(1) == ImageOrder.SECOND);
                assertTrue(beanShade3Result.getImageOrderList().get(2) == ImageOrder.THIRD);
                break;
            case VERT:
                assertTrue(beanShade3Result.getHeight1() >= MIN_HEIGHT1);
                assertTrue(beanShade3Result.getHeight2() >= MIN_HEIGHT1);
                assertTrue(beanShade3Result.getHeight3() >= MIN_HEIGHT1);
                assertTrue(beanShade3Result.getHeight1() + beanShade3Result.getHeight2() <= MAX_HEIGHT1);
                assertTrue(beanShade3Result.getHeight2() == beanShade3Result.getHeight3());

                assertTrue(beanShade3Result.getWidth2() >= MIN_WIDTH1);
                assertTrue(beanShade3Result.getWidth3() >= MIN_WIDTH1);
                assertTrue(beanShade3Result.getWidth1() == beanShade3Result.getWidth2() + beanShade3Result.getWidth3());
                assertTrue(beanShade3Result.getWidth1() <= MAX_WIDTH1);
                break;
            case HORZ:
                assertTrue(beanShade3Result.getWidth1() >= MIN_WIDTH1);
                assertTrue(beanShade3Result.getWidth2() >= MIN_WIDTH1);
                assertTrue(beanShade3Result.getWidth3() >= MIN_WIDTH1);
                assertTrue(beanShade3Result.getWidth1() + beanShade3Result.getWidth2() <= MAX_WIDTH1);
                assertTrue(beanShade3Result.getWidth2() == beanShade3Result.getWidth3());

                assertTrue(beanShade3Result.getHeight2() >= MIN_HEIGHT1);
                assertTrue(beanShade3Result.getHeight3() >= MIN_HEIGHT1);
                assertTrue(beanShade3Result.getHeight1() == beanShade3Result.getHeight2() + beanShade3Result.getHeight3());
                assertTrue(beanShade3Result.getHeight1() <= MAX_HEIGHT1);
                break;
        }
    }
}
