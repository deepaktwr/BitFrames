package proj.me.bitframe.shading_four;

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
import proj.me.bitframe.dimentions.BeanShade4;
import proj.me.bitframe.dimentions.ImageOrder;
import proj.me.bitframe.dimentions.LayoutType;

import static java.lang.Math.round;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by root on 10/4/16.
 */
@RunWith(Parameterized.class)
public class ShadeFourTest {
    private MultiDimensionModel dimentionsModel;

    public ShadeFourTest(MultiDimensionModel dimentionsModel) {
        this.dimentionsModel = dimentionsModel;
    }

    @Mock
    FrameModel frameModel;

    @Before
    public void setup() {
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
        System.out.println("width pixels : " + dimentionsModel.widthPixels);
        System.out.println("height pixels : " + dimentionsModel.heightPixels);
        System.out.println("maxW : " + maxW);
        System.out.println("maxH : " + maxH);
        System.out.println("minW : " + minW);
        System.out.println("minH : " + minH);
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
    public static Collection<MultiDimensionModel> fakeData() {
        List<MultiDimensionModel> multiDimensionModelList = new ArrayList<>();
        for (Dimension dimension : DimensionsModel.DIMENSIONS) {

            float maxW = round(dimension.widthPixels - dimension.widthPixels * 0.04f);
            float maxH = round(maxW + maxW * 0.15f > dimension.heightPixels ? maxW : maxW + maxW * 0.15f);

            maxW = maxW % 2 == 0 ? maxW : maxW + 1;
            maxH = maxH % 2 == 0 ? maxH : maxH + 1;

            float minW = round(maxW * 0.30f);
            float minH = round(maxH * 0.30f);

            minW = minW % 2 == 0 ? minW : minW + 1;
            minH = minH % 2 == 0 ? minH : minH + 1;

            int MAX_WIDTH1 = round(maxW) + 1;
            int MAX_HEIGHT1 = round(maxH) + 1;
            int MIN_WIDTH1 = round(minW);
            int MIN_HEIGHT1 = round(minH);


            int min3Height = (int) (3f * MIN_HEIGHT1 / 1.5f);
            int min3Width = (int) (3f * MIN_WIDTH1 / 1.5f);

            int parallelMinWidth = (int) (MAX_WIDTH1 - (MAX_WIDTH1 / 5f));//subtracting 20% of the width
            int parallelMinHeight = (int) (MAX_HEIGHT1 - (MAX_HEIGHT1 / 5f));//subtracting 20% of height

            parallelMinWidth = parallelMinWidth >= min3Width ? parallelMinWidth : min3Width;
            parallelMinHeight = parallelMinHeight >= min3Height ? parallelMinHeight : min3Height;


            float min2Height = 2f * MIN_HEIGHT1 / 1.5f;
            float min2Width = 2f * MIN_WIDTH1 / 1.5f;

            float horzVertMinWidth = 0.7f * MAX_WIDTH1;//70% of width
            float vertHorzMinHeight = 0.7f * MAX_HEIGHT1;//70% of height

            horzVertMinWidth = horzVertMinWidth >= min2Width ? horzVertMinWidth : min2Width;
            vertHorzMinHeight = vertHorzMinHeight >= min2Height ? vertHorzMinHeight : min2Height;

            MultiDimensionModel[] dimentionsModels = new MultiDimensionModel[]{
                    //layout type = vert
                    new MultiDimensionModel(new int[]{
                            parallelMinWidth + 1, 500,
                            50, 200,
                            40, 20,
                            200, 20
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD, ImageOrder.FOURTH}, LayoutType.VERT, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            parallelMinWidth - 20, 200,
                            parallelMinWidth + 100, 500,
                            10, 200,
                            100, 400
                    }, new ImageOrder[]{ImageOrder.SECOND, ImageOrder.FIRST, ImageOrder.THIRD, ImageOrder.FOURTH}, LayoutType.VERT, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            parallelMinWidth - 1, 600,
                            100, 200,
                            (int) MAX_WIDTH1, 400,
                            400, 500
                    }, new ImageOrder[]{ImageOrder.THIRD, ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.FOURTH}, LayoutType.VERT, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            300, 700,
                            parallelMinWidth - 1, 600,
                            600, 600,
                            (int) MAX_WIDTH1 - 50, 700
                    }, new ImageOrder[]{ImageOrder.FOURTH, ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.VERT_HORZ, dimension.widthPixels, dimension.heightPixels),


                    //layout type = horz
                    new MultiDimensionModel(new int[]{
                            300, parallelMinHeight + 1,
                            20, 60,
                            60, 60,
                            400, 500
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD, ImageOrder.FOURTH}, LayoutType.HORZ, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            300, parallelMinHeight - 1,
                            200, (int) MAX_HEIGHT1,
                            200, 60,
                            500, 500
                    }, new ImageOrder[]{ImageOrder.SECOND, ImageOrder.FIRST, ImageOrder.THIRD, ImageOrder.FOURTH}, LayoutType.HORZ, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            20, 500,
                            20, 60,
                            30, parallelMinHeight + 1,
                            50, 500
                    }, new ImageOrder[]{ImageOrder.THIRD, ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.FOURTH}, LayoutType.HORZ, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            20, 500,
                            200, 60,
                            50, 500,
                            300, 700,
                    }, new ImageOrder[]{ImageOrder.FOURTH, ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.HORZ, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            200, 500,
                            200, 60,
                            300, 4000,
                            500, 500
                    }, new ImageOrder[]{ImageOrder.THIRD, ImageOrder.FOURTH, ImageOrder.FIRST, ImageOrder.SECOND}, LayoutType.HORZ_VERT, dimension.widthPixels, dimension.heightPixels),

                    //view double
                    new MultiDimensionModel(new int[]{
                            2000, 500,
                            2000, 60,
                            300, 1000,
                            500, 500
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD, ImageOrder.FOURTH}, LayoutType.VERT_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            600, 500,
                            300, 100,
                            (int) MAX_WIDTH1, 600,
                            500, 500
                    }, new ImageOrder[]{ImageOrder.THIRD, ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.FOURTH}, LayoutType.VERT_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            (int) MAX_WIDTH1, 60,
                            30, 10,
                            600, 50,
                            50, 50
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.THIRD, ImageOrder.SECOND, ImageOrder.FOURTH}, LayoutType.VERT_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            parallelMinWidth + 10, 60,
                            30, 100,
                            50, 500,
                            parallelMinWidth + 1, 50,
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.FOURTH, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.VERT_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            parallelMinWidth + 100, 50,
                            5000, 60,
                            30, 500,
                            50, 50,
                    }, new ImageOrder[]{ImageOrder.SECOND, ImageOrder.FIRST, ImageOrder.THIRD, ImageOrder.FOURTH}, LayoutType.VERT_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            5, 5,
                            700, 5,
                            parallelMinWidth + 10, 5,
                            5, 5,
                    }, new ImageOrder[]{ImageOrder.SECOND, ImageOrder.THIRD, ImageOrder.FIRST, ImageOrder.FOURTH}, LayoutType.VERT_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            600, 500,
                            parallelMinWidth + 800, 500,
                            700, 500,
                            800, 600,
                    }, new ImageOrder[]{ImageOrder.SECOND, ImageOrder.FOURTH, ImageOrder.FIRST, ImageOrder.THIRD}, LayoutType.VERT_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            parallelMinWidth + 100, 50,
                            30, 500,
                            5000, 60,
                            50, 50,
                    }, new ImageOrder[]{ImageOrder.THIRD, ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.FOURTH}, LayoutType.VERT_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            300, 500,
                            parallelMinWidth + 100, 500,
                            800, 600,
                            500, 500,
                    }, new ImageOrder[]{ImageOrder.THIRD, ImageOrder.SECOND, ImageOrder.FIRST, ImageOrder.FOURTH}, LayoutType.VERT_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            300, 50,
                            500, 50,
                            800, 600,
                            parallelMinWidth + 100, 500,
                    }, new ImageOrder[]{ImageOrder.THIRD, ImageOrder.FOURTH, ImageOrder.FIRST, ImageOrder.SECOND}, LayoutType.VERT_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            parallelMinWidth + 100, 500,
                            300, 50,
                            500, 50,
                            800, 600,
                    }, new ImageOrder[]{ImageOrder.FOURTH, ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.VERT_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            3000, 2000,
                            parallelMinWidth + 5000, 2500,
                            5000, 3500,
                            8000, 4600,
                    }, new ImageOrder[]{ImageOrder.FOURTH, ImageOrder.SECOND, ImageOrder.FIRST, ImageOrder.THIRD}, LayoutType.VERT_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            500, 350,
                            300, 200,
                            parallelMinWidth + 200, 50,
                            800, 60,
                    }, new ImageOrder[]{ImageOrder.FOURTH, ImageOrder.THIRD, ImageOrder.FIRST, ImageOrder.SECOND}, LayoutType.VERT_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    //horz double
                    new MultiDimensionModel(new int[]{
                            500, 3500,
                            300, 2000,
                            parallelMinWidth + 200, 50,
                            800, 60,
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD, ImageOrder.FOURTH}, LayoutType.HORZ_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            50, 800,
                            20, 50,
                            50, 750,
                            80, 50,
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.THIRD, ImageOrder.SECOND, ImageOrder.FOURTH}, LayoutType.HORZ_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            500, 3500,
                            200, 50,
                            500, 60,
                            300, (int) MAX_HEIGHT1,
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.FOURTH, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.HORZ_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            300, (int) MAX_HEIGHT1,
                            500, 3500,
                            200, 500,
                            500, 600,
                    }, new ImageOrder[]{ImageOrder.SECOND, ImageOrder.FIRST, ImageOrder.THIRD, ImageOrder.FOURTH}, LayoutType.HORZ_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            200, 50,
                            300, (int) MAX_HEIGHT1,
                            50, 700,
                            50, 600,
                    }, new ImageOrder[]{ImageOrder.SECOND, ImageOrder.THIRD, ImageOrder.FIRST, ImageOrder.FOURTH}, LayoutType.HORZ_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            200, 50,
                            30, 700,
                            500, 60,
                            50, 680,
                    }, new ImageOrder[]{ImageOrder.SECOND, ImageOrder.FOURTH, ImageOrder.FIRST, ImageOrder.THIRD}, LayoutType.HORZ_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            50, 680,
                            200, 50,
                            30, 700,
                            500, 60,
                    }, new ImageOrder[]{ImageOrder.THIRD, ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.FOURTH}, LayoutType.HORZ_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            200, 500,
                            500, 780,
                            300, 800,
                            500, 600,
                    }, new ImageOrder[]{ImageOrder.THIRD, ImageOrder.SECOND, ImageOrder.FIRST, ImageOrder.FOURTH}, LayoutType.HORZ_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            2000, 5000,
                            5000, 6000,
                            3000, 8000,
                            5000, 7800,
                    }, new ImageOrder[]{ImageOrder.THIRD, ImageOrder.FOURTH, ImageOrder.FIRST, ImageOrder.SECOND}, LayoutType.HORZ_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            5000, 7800,
                            2000, 5000,
                            5000, 6000,
                            3000, 8000,
                    }, new ImageOrder[]{ImageOrder.FOURTH, ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.HORZ_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            500, 780,
                            30, 5000,
                            500, 600,
                            30, 8000,
                    }, new ImageOrder[]{ImageOrder.FOURTH, ImageOrder.SECOND, ImageOrder.FIRST, ImageOrder.THIRD}, LayoutType.HORZ_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            20, 50,
                            50, 60,
                            50, 780,
                            30, 800,
                    }, new ImageOrder[]{ImageOrder.FOURTH, ImageOrder.THIRD, ImageOrder.FIRST, ImageOrder.SECOND}, LayoutType.HORZ_DOUBLE, dimension.widthPixels, dimension.heightPixels),

                    //VERT_HORZ
                    new MultiDimensionModel(new int[]{
                            5000, 800,
                            200, 2000,
                            500, 600,
                            300, 800,
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD, ImageOrder.FOURTH}, LayoutType.VERT_HORZ, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            5000, 50,
                            50, 500,
                            20, 600,
                            30, 80,
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.THIRD, ImageOrder.SECOND, ImageOrder.FOURTH}, LayoutType.VERT_HORZ, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            800, 80,
                            50, 60,
                            200, 200,
                            200, (int) vertHorzMinHeight + 20,
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.FOURTH, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.VERT_HORZ, dimension.widthPixels, dimension.heightPixels),


                    new MultiDimensionModel(new int[]{
                            100, (int) vertHorzMinHeight + 20,
                            600, 100,
                            100, 100,
                            100, 100,
                    }, new ImageOrder[]{ImageOrder.SECOND, ImageOrder.FIRST, ImageOrder.THIRD, ImageOrder.FOURTH}, LayoutType.VERT_HORZ, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            1000, 1000,
                            6000, 1000,
                            1000, 3000,
                            1000, 1000,
                    }, new ImageOrder[]{ImageOrder.SECOND, ImageOrder.THIRD, ImageOrder.FIRST, ImageOrder.FOURTH}, LayoutType.VERT_HORZ, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            300, 300,
                            (int) MAX_WIDTH1, 10,
                            10, 10,
                            10, (int) MAX_HEIGHT1,
                    }, new ImageOrder[]{ImageOrder.SECOND, ImageOrder.FOURTH, ImageOrder.FIRST, ImageOrder.THIRD}, LayoutType.VERT_HORZ, dimension.widthPixels, dimension.heightPixels),

                    //HORZ_VERT
                    new MultiDimensionModel(new int[]{
                            50, 8000,
                            2000, 2,
                            500, 600,
                            300, 800,
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD, ImageOrder.FOURTH}, LayoutType.HORZ_VERT, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            500, (int) MAX_HEIGHT1,
                            50, parallelMinHeight,
                            (int) MAX_WIDTH1 - 2, 200,
                            parallelMinWidth, 80,
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.THIRD, ImageOrder.SECOND, ImageOrder.FOURTH}, LayoutType.HORZ_VERT, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            50, 720,
                            200, 200,
                            500, 600,
                            600, 100,
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.FOURTH, ImageOrder.SECOND, ImageOrder.THIRD}, LayoutType.HORZ_VERT, dimension.widthPixels, dimension.heightPixels),

                    //VARY_WIDTH
                    new MultiDimensionModel(new int[]{
                            50, 400,
                            200, 350,
                            500, 200,
                            300, 150,
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD, ImageOrder.FOURTH}, LayoutType.IDENTICAL_VARY_WIDTH, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            50, 40,
                            50, 40,
                            50, 40,
                            50, 40,
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD, ImageOrder.FOURTH}, LayoutType.IDENTICAL_VARY_WIDTH, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            500, 400,
                            500, 400,
                            500, 400,
                            500, 400,
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD, ImageOrder.FOURTH}, LayoutType.IDENTICAL_VARY_WIDTH, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            350, 250,
                            50, 500,
                            500, 200,
                            50, 400,
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.THIRD, ImageOrder.SECOND, ImageOrder.FOURTH}, LayoutType.IDENTICAL_VARY_WIDTH, dimension.widthPixels, dimension.heightPixels),

                    new MultiDimensionModel(new int[]{
                            35, 25,
                            50, 50,
                            50, 20,
                            50, 40,
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD, ImageOrder.FOURTH}, LayoutType.IDENTICAL_VARY_WIDTH, dimension.widthPixels, dimension.heightPixels),
                    new MultiDimensionModel(new int[]{
                            35, 25,
                            50, 500,
                            50, 20,
                            50, 400,
                    }, new ImageOrder[]{ImageOrder.FIRST, ImageOrder.SECOND, ImageOrder.THIRD, ImageOrder.FOURTH}, LayoutType.IDENTICAL_VARY_HEIGHT, dimension.widthPixels, dimension.heightPixels),


            };
            multiDimensionModelList.addAll(Arrays.asList(dimentionsModels));
        }
        return multiDimensionModelList;
    }

    @Test
    public void calculateDimentions_ShouldReturnBean() {
        BeanShade4 beanShade4Result = ShadeFour.calculateDimentions(frameModel,
                dimentionsModel.params[0], dimentionsModel.params[1],
                dimentionsModel.params[2], dimentionsModel.params[3],
                dimentionsModel.params[4], dimentionsModel.params[5],
                dimentionsModel.params[6], dimentionsModel.params[7]
        );

        System.out.println("Start++++++++++++++++++++++++++++++++++++Start");
        System.out.println("running for : " + dimentionsModel.params[0] + " " + dimentionsModel.params[1] + " " + dimentionsModel.params[2] + " " + dimentionsModel.params[3]);
        System.out.println("getWidth1 : " + beanShade4Result.getWidth1());
        System.out.println("getHeight1 : " + beanShade4Result.getHeight1());
        System.out.println("getWidth2 : " + beanShade4Result.getWidth2());
        System.out.println("getHeight2 : " + beanShade4Result.getHeight2());
        System.out.println("getWidth3 : " + beanShade4Result.getWidth3());
        System.out.println("getHeight3 : " + beanShade4Result.getHeight3());
        System.out.println("getWidth4 : " + beanShade4Result.getWidth4());
        System.out.println("getHeight4 : " + beanShade4Result.getHeight4());
        for (int i = 0; i < 4; i++) {
            System.out.println("image order : " + beanShade4Result.getImageOrderList().get(i));
        }
        System.out.println("layoutType : " + beanShade4Result.getLayoutType());
        System.out.println("End++++++++++++++++++++++++++++++++++++End");

        int MAX_WIDTH1 = round(frameModel.getMaxContainerWidth()) + 1;
        int MAX_HEIGHT1 = round(frameModel.getMaxContainerHeight()) + 1;
        int MIN_WIDTH1 = round(frameModel.getMinFrameWidth());
        int MIN_HEIGHT1 = round(frameModel.getMinFrameHeight());

        /*assertTrue(beanShade4Result.getLayoutType() == dimentionsModel.layoutType);
        assertTrue(beanShade4Result.getImageOrderList().get(0) == dimentionsModel.imageOrders[0]);
        assertTrue(beanShade4Result.getImageOrderList().get(1) == dimentionsModel.imageOrders[1]);
        assertTrue(beanShade4Result.getImageOrderList().get(2) == dimentionsModel.imageOrders[2]);
        assertTrue(beanShade4Result.getImageOrderList().get(3) == dimentionsModel.imageOrders[3]);*/

        float MIN_HEIGHT = 0f;
        float MIN_WIDTH = 0f;

        switch (beanShade4Result.getLayoutType()) {
            case VERT:
                MIN_HEIGHT = MIN_HEIGHT1;
                MIN_WIDTH = MIN_WIDTH1 / 1.5f;

                assertTrue(beanShade4Result.getWidth1() >= beanShade4Result.getWidth2() +
                        beanShade4Result.getWidth3() + beanShade4Result.getWidth4() - 1);

                assertTrue(beanShade4Result.getWidth2() >= MIN_WIDTH - 1);
                assertTrue(beanShade4Result.getWidth3() >= MIN_WIDTH - 1);
                assertTrue(beanShade4Result.getWidth4() >= MIN_WIDTH - 1);

                assertTrue(beanShade4Result.getHeight1() >= MIN_HEIGHT - 1);
                assertTrue(beanShade4Result.getHeight2() >= MIN_HEIGHT - 1);
                assertTrue(beanShade4Result.getHeight3() >= MIN_HEIGHT - 1);
                assertTrue(beanShade4Result.getHeight4() >= MIN_HEIGHT - 1);

                assertTrue(beanShade4Result.getWidth1() <= MAX_WIDTH1);
                break;
            case HORZ:
                MIN_HEIGHT = MIN_HEIGHT1 / 1.5f;
                MIN_WIDTH = MIN_WIDTH1;

                assertTrue(beanShade4Result.getHeight1() >= beanShade4Result.getHeight2() +
                        beanShade4Result.getHeight3() + beanShade4Result.getHeight4() - 2);

                assertTrue(beanShade4Result.getHeight2() >= MIN_HEIGHT - 1);
                assertTrue(beanShade4Result.getHeight3() >= MIN_HEIGHT - 1);
                assertTrue(beanShade4Result.getHeight4() >= MIN_HEIGHT - 1);

                assertTrue(beanShade4Result.getWidth1() >= MIN_WIDTH - 1);
                assertTrue(beanShade4Result.getWidth2() >= MIN_WIDTH - 1);
                assertTrue(beanShade4Result.getWidth3() >= MIN_WIDTH - 1);
                assertTrue(beanShade4Result.getWidth4() >= MIN_WIDTH - 1);

                assertTrue(beanShade4Result.getHeight1() <= MAX_HEIGHT1);
                break;
            case VERT_DOUBLE:
                MIN_HEIGHT = MIN_HEIGHT1 / 1.5f;
                MIN_WIDTH = MIN_WIDTH1;

                assertTrue(beanShade4Result.getWidth1() == beanShade4Result.getWidth2());
                assertTrue(beanShade4Result.getWidth1() >= beanShade4Result.getWidth3() + beanShade4Result.getWidth4() - 2);

                assertTrue(beanShade4Result.getWidth3() >= MIN_WIDTH - 1);
                assertTrue(beanShade4Result.getWidth4() >= MIN_WIDTH - 1);

                assertTrue(beanShade4Result.getHeight3() == beanShade4Result.getHeight4());

                assertTrue(beanShade4Result.getHeight1() >= MIN_HEIGHT - 1);
                assertTrue(beanShade4Result.getHeight2() >= MIN_HEIGHT - 1);
                assertTrue(beanShade4Result.getHeight3() >= MIN_HEIGHT - 1);

                assertTrue(beanShade4Result.getWidth1() <= MAX_WIDTH1);

                assertTrue(beanShade4Result.getHeight1() + beanShade4Result.getHeight2() + beanShade4Result.getHeight3() <= MAX_HEIGHT1);
                break;
            case HORZ_DOUBLE:
                MIN_HEIGHT = MIN_HEIGHT1;
                MIN_WIDTH = MIN_WIDTH1 / 1.5f;

                assertTrue(beanShade4Result.getHeight1() == beanShade4Result.getHeight2());
                assertTrue(beanShade4Result.getHeight1() >= beanShade4Result.getHeight3() + beanShade4Result.getHeight4() - 2);

                assertTrue(beanShade4Result.getHeight3() >= MIN_HEIGHT - 1);
                assertTrue(beanShade4Result.getHeight4() >= MIN_HEIGHT - 1);

                assertTrue(beanShade4Result.getWidth3() == beanShade4Result.getWidth4());

                assertTrue(beanShade4Result.getWidth1() >= MIN_WIDTH - 1);
                assertTrue(beanShade4Result.getWidth2() >= MIN_WIDTH - 1);
                assertTrue(beanShade4Result.getWidth3() >= MIN_WIDTH - 1);

                assertTrue(beanShade4Result.getHeight1() <= MAX_HEIGHT1);

                assertTrue(beanShade4Result.getWidth1() + beanShade4Result.getWidth2() + beanShade4Result.getWidth3() <= MAX_WIDTH1);
                break;
            case VERT_HORZ:
                MIN_HEIGHT = MIN_HEIGHT1 / 1.5f;
                MIN_WIDTH = MIN_WIDTH1;

                assertTrue(beanShade4Result.getWidth1() >= beanShade4Result.getWidth2() + beanShade4Result.getWidth3() - 2);

                assertTrue(beanShade4Result.getHeight1() >= MIN_HEIGHT - 1);
                assertTrue(beanShade4Result.getHeight3() >= MIN_HEIGHT - 1);
                assertTrue(beanShade4Result.getHeight4() >= MIN_HEIGHT - 1);

                assertTrue(beanShade4Result.getHeight2() >= beanShade4Result.getHeight3() + beanShade4Result.getHeight4() - 2);

                assertTrue(beanShade4Result.getWidth3() == beanShade4Result.getWidth4());

                assertTrue(beanShade4Result.getWidth2() >= MIN_WIDTH - 1);
                assertTrue(beanShade4Result.getWidth3() >= MIN_WIDTH - 1);
                assertTrue(beanShade4Result.getWidth4() >= MIN_WIDTH - 1);

                assertTrue(beanShade4Result.getWidth1() <= MAX_WIDTH1);
                assertTrue(beanShade4Result.getHeight1() + beanShade4Result.getHeight2() <= MAX_HEIGHT1);
                break;

            case HORZ_VERT:
                MIN_HEIGHT = MIN_HEIGHT1;
                MIN_WIDTH = MIN_WIDTH1 / 1.5f;

                assertTrue(beanShade4Result.getHeight1() >= beanShade4Result.getHeight2() + beanShade4Result.getHeight3() - 2);

                assertTrue(beanShade4Result.getWidth1() >= MIN_WIDTH - 1);
                assertTrue(beanShade4Result.getWidth3() >= MIN_WIDTH - 1);
                assertTrue(beanShade4Result.getWidth4() >= MIN_WIDTH - 1);

                assertTrue(beanShade4Result.getWidth2() >= beanShade4Result.getWidth3() + beanShade4Result.getWidth4() - 2);

                assertTrue(beanShade4Result.getHeight3() == beanShade4Result.getHeight4());

                assertTrue(beanShade4Result.getHeight2() >= MIN_HEIGHT - 1);
                assertTrue(beanShade4Result.getHeight3() >= MIN_HEIGHT - 1);
                assertTrue(beanShade4Result.getHeight4() >= MIN_HEIGHT - 1);

                assertTrue(beanShade4Result.getHeight1() <= MAX_HEIGHT1);
                assertTrue(beanShade4Result.getWidth1() + beanShade4Result.getWidth2() <= MAX_WIDTH1);
                break;

            case IDENTICAL_VARY_WIDTH:
                MIN_HEIGHT = MIN_HEIGHT1;
                MIN_WIDTH = MIN_WIDTH1;

                assertTrue(beanShade4Result.getHeight1() == beanShade4Result.getHeight2());
                assertTrue(beanShade4Result.getHeight3() == beanShade4Result.getHeight4());

                assertTrue(beanShade4Result.getWidth1() >= MIN_WIDTH - 1);
                assertTrue(beanShade4Result.getWidth2() >= MIN_WIDTH - 1);
                assertTrue(beanShade4Result.getWidth3() >= MIN_WIDTH - 1);
                assertTrue(beanShade4Result.getWidth4() >= MIN_WIDTH - 1);


                assertTrue(beanShade4Result.getHeight1() >= MIN_HEIGHT - 1);
                assertTrue(beanShade4Result.getHeight2() >= MIN_HEIGHT - 1);
                assertTrue(beanShade4Result.getHeight3() >= MIN_HEIGHT - 1);
                assertTrue(beanShade4Result.getHeight4() >= MIN_HEIGHT - 1);

                assertTrue(beanShade4Result.getWidth1() + beanShade4Result.getWidth2() <= beanShade4Result.getWidth3() + beanShade4Result.getWidth4());
                assertTrue(beanShade4Result.getHeight1() + beanShade4Result.getHeight3() <= beanShade4Result.getHeight2() + beanShade4Result.getHeight4());

                assertTrue(beanShade4Result.getHeight1() + beanShade4Result.getHeight3() <= MAX_HEIGHT1);
                assertTrue(beanShade4Result.getWidth1() + beanShade4Result.getWidth2() <= MAX_WIDTH1);
                break;
            case IDENTICAL_VARY_HEIGHT:
                MIN_HEIGHT = MIN_HEIGHT1;
                MIN_WIDTH = MIN_WIDTH1;

                assertTrue(beanShade4Result.getWidth1() == beanShade4Result.getWidth2());
                assertTrue(beanShade4Result.getWidth3() == beanShade4Result.getWidth4());

                assertTrue(beanShade4Result.getWidth1() >= MIN_WIDTH - 1);
                assertTrue(beanShade4Result.getWidth2() >= MIN_WIDTH - 1);
                assertTrue(beanShade4Result.getWidth3() >= MIN_WIDTH - 1);
                assertTrue(beanShade4Result.getWidth4() >= MIN_WIDTH - 1);


                assertTrue(beanShade4Result.getHeight1() >= MIN_HEIGHT - 1);
                assertTrue(beanShade4Result.getHeight2() >= MIN_HEIGHT - 1);
                assertTrue(beanShade4Result.getHeight3() >= MIN_HEIGHT - 1);
                assertTrue(beanShade4Result.getHeight4() >= MIN_HEIGHT - 1);

                assertTrue(beanShade4Result.getWidth1() + beanShade4Result.getWidth3() <= beanShade4Result.getWidth2() + beanShade4Result.getWidth4());
                assertTrue(beanShade4Result.getHeight1() + beanShade4Result.getHeight2() <= beanShade4Result.getHeight3() + beanShade4Result.getHeight4());

                assertTrue(beanShade4Result.getHeight1() + beanShade4Result.getHeight2() <= MAX_HEIGHT1);
                assertTrue(beanShade4Result.getWidth1() + beanShade4Result.getWidth3() <= MAX_WIDTH1);
                break;
        }

    }
}
