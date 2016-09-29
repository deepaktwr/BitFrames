package proj.me.bitframe.shading_four;

import java.util.ArrayList;
import java.util.List;

import proj.me.bitframe.FrameModel;
import proj.me.bitframe.dimentions.BeanShade4;
import proj.me.bitframe.dimentions.ImageOrder;
import proj.me.bitframe.dimentions.LayoutType;
import proj.me.bitframe.helper.Utils;

import static java.lang.Math.round;


/**
 * Created by root on 6/4/16.
 */
 class ShadeFour {
    private static float WIDTH_1, WIDTH_2, WIDTH_3, HEIGHT_1, HEIGHT_2, HEIGHT_3, WIDTH_4, HEIGHT_4;

    private static float MIN_WIDTH;
    private static float MIN_HIGHT;

    static BeanShade4 calculateDimentions(FrameModel frameModel, float width1, float height1, float width2, float height2, float width3, float height3,
                                                 float width4, float height4){
        WIDTH_1 = 0; WIDTH_2 = 0; WIDTH_3 = 0; HEIGHT_1 = 0; HEIGHT_2 = 0; HEIGHT_3 = 0; WIDTH_4 = 0; HEIGHT_4 = 0;
        MIN_WIDTH = frameModel.getMinFrameWidth();
        MIN_HIGHT = frameModel.getMinFrameHeight();
        BeanShade4 beanShade4 = new BeanShade4();
        List<ImageOrder> imageOrderList = new ArrayList<>();


        float min2Height = 2f * frameModel.getMinFrameHeight() / 1.5f;
        float min2Width = 2f * frameModel.getMinFrameWidth() / 1.5f;

        float horzVertMinWidth = 0.7f * frameModel.getMaxContainerWidth();//70% of width
        float vertHorzMinHeight = 0.7f * frameModel.getMaxContainerHeight();//70% of height

        horzVertMinWidth = horzVertMinWidth >= min2Width ? horzVertMinWidth : min2Width;
        vertHorzMinHeight = vertHorzMinHeight >= min2Height ? vertHorzMinHeight : min2Height;

        float min3Height = 3f * frameModel.getMinFrameHeight() / 1.5f;
        float min3Width = 3f * frameModel.getMinFrameWidth() / 1.5f;

        float parallelMinWidth = frameModel.getMaxContainerWidth() - (frameModel.getMaxContainerWidth() / 5f);//subtracting 20% of the width
        float parallelMinHeight = frameModel.getMaxContainerHeight()- (frameModel.getMaxContainerHeight() / 5f);//subtracting 20% of height

        parallelMinWidth = parallelMinWidth >= min3Width ? parallelMinWidth : min3Width;
        parallelMinHeight = parallelMinHeight >= min3Height ? parallelMinHeight : min3Height;

        float amountW1 = width1 / frameModel.getMaxContainerWidth();
        float amountW2 = width2 / frameModel.getMaxContainerWidth();
        float amountW3 = width3 / frameModel.getMaxContainerWidth();
        float amountW4 = width4 / frameModel.getMaxContainerWidth();

        float amountH1 = height1 / frameModel.getMaxContainerHeight();
        float amountH2 = height2 / frameModel.getMaxContainerHeight();
        float amountH3 = height3 / frameModel.getMaxContainerHeight();
        float amountH4 = height4 / frameModel.getMaxContainerHeight();

        if(width1 >= parallelMinWidth || width2 >= parallelMinWidth || width3 >= parallelMinWidth || width4 >= parallelMinWidth ||
                height1 >= parallelMinHeight || height2 >= parallelMinHeight || height3 >= parallelMinHeight || height4 >= parallelMinHeight){
            //because three width or height is going to be in parallel so min width and height will change accordingly

            //2//check for any 2 width or height satisfying condition for max width or max height
            //width in parallel
            if(width1 >= parallelMinWidth && amountW1 >= amountW2 && amountW1 >= amountW3 && amountW1 >= amountW4 &&
                    amountW1 >= amountH1 && amountW1 >= amountH2 && amountW1 >= amountH3 && amountW1 >= amountH4){
                MIN_HIGHT = frameModel.getMinFrameHeight() / 1.5f;
                //width1 as top in vert
                //for double vert
                if(width2 >= parallelMinWidth && amountW2 >= amountW3 && amountW2 >= amountW4 &&
                        amountW2 >= amountH2 && amountW2 >= amountH3 && amountW2 >= amountH4){
                    //width2 as below to width1 in parallel vert
                    beanShade4.setLayoutType(LayoutType.VERT_DOUBLE);

                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setDoubleWidthThenHeight(frameModel, width1, width2, width3, width4, height1, height2, height3, height4);
                }else if(width3 >= parallelMinWidth && amountW3 >= amountW2 && amountW3 >= amountW4 &&
                        amountW3 >= amountH2 && amountW3 >= amountH3 && amountW3 >= amountH4){
                    //width3 as below to width1 in parallel vert
                    beanShade4.setLayoutType(LayoutType.VERT_DOUBLE);

                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setDoubleWidthThenHeight(frameModel, width1, width3, width2, width4, height1, height3, height2, height4);
                }else if(width4 >= parallelMinWidth && amountW4 >= amountW2 && amountW4 >= amountW3 &&
                        amountW4 >= amountH2 && amountW4 >= amountH3 && amountW4 >= amountH4){
                    //width4 as below to width1 in parallel vert
                    beanShade4.setLayoutType(LayoutType.VERT_DOUBLE);

                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.THIRD);

                    setDoubleWidthThenHeight(frameModel, width1, width4, width2, width3, height1, height4, height2, height3);
                }

                //for vert horz
                else if(height2 >= vertHorzMinHeight && amountH2 >= amountH3 && amountH2 >= amountH4 &&
                        amountH2 >= amountW2 && amountH2 >= amountW3 && amountH2 >= amountW4){
                    //height2 as left vert below width1 in vert horz
                    beanShade4.setLayoutType(LayoutType.VERT_HORZ);

                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setVertHorzHightAndWidth(frameModel, width1, width2, width3, width4, height1, height2, height3, height4);
                }else if(height3 >= vertHorzMinHeight && amountH3 >= amountH2 && amountH3 >= amountH4 &&
                        amountH3 >= amountW2 && amountH3 >= amountW3 && amountH3 >= amountW4){
                    //height3 as left vert below width1 in vert horz
                    beanShade4.setLayoutType(LayoutType.VERT_HORZ);

                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setVertHorzHightAndWidth(frameModel, width1, width3, width2, width4, height1, height3, height2, height4);
                }else if(height4 >= vertHorzMinHeight && amountH4 >= amountH2 && amountH4 >= amountH3 &&
                        amountH4 >= amountW2 && amountH4 >= amountW3 && amountH4 >= amountW4){
                    //height4 as left vert below width1 in vert horz
                    beanShade4.setLayoutType(LayoutType.VERT_HORZ);

                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.THIRD);

                    setVertHorzHightAndWidth(frameModel, width1, width4, width2, width3, height1, height4, height2, height3);
                }

                //for vert
                else{
                    MIN_HIGHT = frameModel.getMinFrameHeight();
                    MIN_WIDTH = frameModel.getMinFrameWidth() / 1.5f;
                    //width2, width3 and width4 will go below the width1 in vert
                    beanShade4.setLayoutType(LayoutType.VERT);

                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setVertWidthAndHeight(frameModel, width1, width2, width3, width4, height1, height2, height3, height4);
                }
            }else if(width2 >= parallelMinWidth && amountW2 >= amountW1 && amountW2 >= amountW3 && amountW2 >= amountW4 &&
                    amountW2 >= amountH1 && amountW2 >= amountH2 && amountW2 >= amountH3 && amountW2 >= amountH4){
                MIN_HIGHT = frameModel.getMinFrameHeight() / 1.5f;
                //width2 as top in vert
                //for double vert
                if(width1 >= parallelMinWidth && amountW1 >= amountW3 && amountW1 >= amountW4 &&
                        amountW1 >= amountH1 && amountW1 >= amountH3 && amountW1 >= amountH4){
                    //width1 as below to width2 in parallel vert
                    beanShade4.setLayoutType(LayoutType.VERT_DOUBLE);

                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setDoubleWidthThenHeight(frameModel, width2, width1, width3, width4, height2, height1, height3, height4);
                }else if(width3 >= parallelMinWidth && amountW3 >= amountW1 && amountW3 >= amountW4 &&
                        amountW3 >= amountH1 && amountW3 >= amountH3 && amountW3 >= amountH4){
                    //width3 as below to width2 in parallel vert
                    beanShade4.setLayoutType(LayoutType.VERT_DOUBLE);

                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setDoubleWidthThenHeight(frameModel, width2, width3, width1, width4, height2, height3, height1, height4);
                }else if(width4 >= parallelMinWidth && amountW4 >= amountW1 && amountW4 >= amountW3 &&
                        amountW4 >= amountH1 && amountW4 >= amountH3 && amountW4 >= amountH4){
                    //width4 as below to width2 in parallel vert
                    beanShade4.setLayoutType(LayoutType.VERT_DOUBLE);

                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.THIRD);

                    setDoubleWidthThenHeight(frameModel, width2, width4, width1, width3, height2, height4, height1, height3);
                }

                //for vert horz
                else if(height1 >= vertHorzMinHeight && amountH1 >= amountH3 && amountH1 >= amountH4 &&
                        amountH1 >= amountW1 && amountH1 >= amountW3 && amountH1 >= amountW4){
                    //height1 as left vert below width2 in vert horz
                    beanShade4.setLayoutType(LayoutType.VERT_HORZ);

                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setVertHorzHightAndWidth(frameModel, width2, width1, width3, width4, height2, height1, height3, height4);
                }else if(height3 >= vertHorzMinHeight && amountH3 >= amountH1 && amountH3 >= amountH4 &&
                        amountH3 >= amountW1 && amountH3 >= amountW3 && amountH3 >= amountW4){
                    //height3 as left vert below width2 in vert horz
                    beanShade4.setLayoutType(LayoutType.VERT_HORZ);

                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setVertHorzHightAndWidth(frameModel, width2, width3, width1, width4, height2, height3, height1, height4);
                }else if(height4 >= vertHorzMinHeight && amountH4 >= amountH1 && amountH4 >= amountH3 &&
                        amountH4 >= amountW1 && amountH4 >= amountW3 && amountH4 >= amountW4){
                    //height4 as left vert below width2 in vert horz
                    beanShade4.setLayoutType(LayoutType.VERT_HORZ);

                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.THIRD);

                    setVertHorzHightAndWidth(frameModel, width2, width4, width1, width3, height2, height4, height1, height3);
                }

                //for vert
                else{
                    MIN_HIGHT = frameModel.getMinFrameHeight();
                    MIN_WIDTH = frameModel.getMinFrameWidth() / 1.5f;
                    //width1, width3 and width4 will go below the width2 in vert
                    beanShade4.setLayoutType(LayoutType.VERT);

                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setVertWidthAndHeight(frameModel, width2, width1, width3, width4, height2, height1, height3, height4);
                }
            }else if(width3 >= parallelMinWidth && amountW3 >= amountW2 && amountW3 >= amountW1 && amountW3 >= amountW4 &&
                    amountW3 >= amountH1 && amountW3 >= amountH2 && amountW3 >= amountH3 && amountW3 >= amountH4){
                MIN_HIGHT = frameModel.getMinFrameHeight() / 1.5f;
                //width3 as top in vert
                //for double vert
                if(width1 >= parallelMinWidth && amountW1 >= amountW2 && amountW1 >= amountW4 &&
                        amountW1 >= amountH1 && amountW1 >= amountH2 && amountW1 >= amountH4){
                    //width1 as below to width3 in parallel vert
                    beanShade4.setLayoutType(LayoutType.VERT_DOUBLE);

                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setDoubleWidthThenHeight(frameModel, width3, width1, width2, width4, height3, height1, height2, height4);
                }else if(width2 >= parallelMinWidth && amountW2 >= amountW1 && amountW2 >= amountW4 &&
                        amountW2 >= amountH1 && amountW2 >= amountH2 && amountW2 >= amountH4){
                    //width2 as below to width3 in parallel vert
                    beanShade4.setLayoutType(LayoutType.VERT_DOUBLE);

                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setDoubleWidthThenHeight(frameModel, width3, width2, width1, width4, height3, height2, height1, height4);
                }else if(width4 >= parallelMinWidth && amountW4 >= amountW1 && amountW4 >= amountW2 &&
                        amountW4 >= amountH1 && amountW4 >= amountH2 && amountW4 >= amountH4){
                    //width4 as below to width3 in parallel vert
                    beanShade4.setLayoutType(LayoutType.VERT_DOUBLE);

                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);

                    setDoubleWidthThenHeight(frameModel, width3, width4, width1, width2, height3, height4, height1, height2);
                }

                //for vert horz
                else if(height1 >= vertHorzMinHeight && amountH1 >= amountH2 && amountH1 >= amountH4 &&
                        amountH1 >= amountW1 && amountH1 >= amountW2 && amountH1 >= amountW4){
                    //height1 as left vert below width3 in vert horz
                    beanShade4.setLayoutType(LayoutType.VERT_HORZ);

                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setVertHorzHightAndWidth(frameModel, width3, width1, width2, width4, height3, height1, height2, height4);
                }else if(height2 >= vertHorzMinHeight && amountH2 >= amountH1 && amountH2 >= amountH4 &&
                        amountH2 >= amountW1 && amountH2 >= amountW2 && amountH2 >= amountW4){
                    //height2 as left vert below width3 in vert horz
                    beanShade4.setLayoutType(LayoutType.VERT_HORZ);

                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setVertHorzHightAndWidth(frameModel, width3, width2, width1, width4, height3, height2, height1, height4);
                }else if(height4 >= vertHorzMinHeight && amountH4 >= amountH2 && amountH4 >= amountH1 &&
                        amountH4 >= amountW1 && amountH4 >= amountW2 && amountH4 >= amountW4){
                    //height4 as left vert below width3 in vert horz
                    beanShade4.setLayoutType(LayoutType.VERT_HORZ);

                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);

                    setVertHorzHightAndWidth(frameModel, width3, width4, width1, width2, height3, height4, height1, height2);
                }

                //for vert
                else{
                    MIN_HIGHT = frameModel.getMinFrameHeight();
                    MIN_WIDTH = frameModel.getMinFrameWidth() / 1.5f;
                    //width2, width3 and width4 will go below the width3 in vert
                    beanShade4.setLayoutType(LayoutType.VERT);

                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setVertWidthAndHeight(frameModel, width3, width1, width2, width4, height3, height1, height2, height4);
                }
            }else if(width4 >= parallelMinWidth && amountW4 >= amountW2 && amountW4 >= amountW3 && amountW4 >= amountW1 &&
                    amountW4 >= amountH1 && amountW4 >= amountH2 && amountW4 >= amountH3 && amountW4 >= amountH4){
                MIN_HIGHT = frameModel.getMinFrameHeight() / 1.5f;
                //width4 as top in vert
                //for double vert
                if(width1 >= parallelMinWidth && amountW1 >= amountW2 && amountW1 >= amountW3 &&
                        amountW1 >= amountH1 && amountW1 >= amountH2 && amountW1 >= amountH3){
                    //width1 as below to width4 in parallel vert
                    beanShade4.setLayoutType(LayoutType.VERT_DOUBLE);

                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.THIRD);

                    setDoubleWidthThenHeight(frameModel, width4, width1, width2, width3, height4, height1, height2, height3);
                }else if(width2 >= parallelMinWidth && amountW2 >= amountW1 && amountW2 >= amountW3 &&
                        amountW2 >= amountH1 && amountW2 >= amountH2 && amountW2 >= amountH3){
                    //width2 as below to width4 in parallel vert
                    beanShade4.setLayoutType(LayoutType.VERT_DOUBLE);

                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.THIRD);

                    setDoubleWidthThenHeight(frameModel, width4, width2, width1, width3, height4, height2, height1, height3);
                }else if(width3 >= parallelMinWidth && amountW3 >= amountW1 && amountW3 >= amountW2 &&
                        amountW3 >= amountH1 && amountW3 >= amountH2 && amountW3 >= amountH3){
                    //width3 as below to width4 in parallel vert
                    beanShade4.setLayoutType(LayoutType.VERT_DOUBLE);

                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);

                    setDoubleWidthThenHeight(frameModel, width4, width3, width1, width2, height4, height3, height1, height2);
                }

                //for vert horz
                else if(height1 >= vertHorzMinHeight && amountH1 >= amountH2 && amountH1 >= amountH3 &&
                        amountH1 >= amountW1 && amountH1 >= amountW2 && amountH1 >= amountW3){
                    //height1 as left vert below width4 in vert horz
                    beanShade4.setLayoutType(LayoutType.VERT_HORZ);

                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.THIRD);

                    setVertHorzHightAndWidth(frameModel, width4, width1, width2, width3, height4, height1, height2, height3);
                }else if(height2 >= vertHorzMinHeight && amountH2 >= amountH1 && amountH2 >= amountH3 &&
                        amountH2 >= amountW1 && amountH2 >= amountW2 && amountH2 >= amountW3){
                    //height2 as left vert below width4 in vert horz
                    beanShade4.setLayoutType(LayoutType.VERT_HORZ);

                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.THIRD);

                    setVertHorzHightAndWidth(frameModel, width4, width2, width1, width3, height4, height2, height1, height3);
                }else if(height3 >= vertHorzMinHeight && amountH3 >= amountH2 && amountH3 >= amountH1 &&
                        amountH3 >= amountW1 && amountH3 >= amountW2 && amountH3 >= amountW3){
                    //height3 as left vert below width4 in vert horz
                    beanShade4.setLayoutType(LayoutType.VERT_HORZ);

                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);

                    setVertHorzHightAndWidth(frameModel, width4, width3, width1, width2, height4, height3, height1, height2);
                }

                //for vert
                else{
                    MIN_HIGHT = frameModel.getMinFrameHeight();
                    MIN_WIDTH = frameModel.getMinFrameWidth() / 1.5f;
                    //width2, width3 and width1 will go below the width4 in vert
                    beanShade4.setLayoutType(LayoutType.VERT);

                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.THIRD);

                    setVertWidthAndHeight(frameModel, width4, width1, width2, width3, height4, height1, height2, height3);
                }
            }

            //height in parallel
            else if(height1 >= parallelMinHeight && amountH1 >= amountH2 && amountH1 >= amountH3 && amountH1 >= amountH4 &&
                    amountH1 >= amountW1 && amountH1 >= amountW2 && amountH1 >= amountW3 && amountH1 >= amountW4){
                MIN_WIDTH = frameModel.getMinFrameWidth() / 1.5f;
                //height as left in horz
                //for double horz
                if(height2 >= parallelMinHeight && amountH2 >= amountH3 && amountH2 >= amountH4 &&
                        amountH2 >= amountW2 && amountH2 >= amountW3 && amountH2 >= amountH4){
                    //height2 to right of  height1 in parallel horz
                    beanShade4.setLayoutType(LayoutType.HORZ_DOUBLE);

                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setDoubleHeightThenWidth(frameModel, width1, width2, width3, width4, height1, height2, height3, height4);
                }else if(height3 >= parallelMinHeight && amountH3 >= amountH2 && amountH3 >= amountH4 &&
                        amountH3 >= amountW2 && amountH3 >= amountW3 && amountH3 >= amountH4){
                    //height3 to right of  height1 in parallel horz
                    beanShade4.setLayoutType(LayoutType.HORZ_DOUBLE);

                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setDoubleHeightThenWidth(frameModel, width1, width3, width2, width4, height1, height3, height2, height4);
                }else if(height4 >= parallelMinHeight && amountH4 >= amountH3 && amountH4 >= amountH2 &&
                        amountH4 >= amountW2 && amountH4 >= amountW3 && amountH4 >= amountH4){
                    //height4 to right of  height1 in parallel horz
                    beanShade4.setLayoutType(LayoutType.HORZ_DOUBLE);

                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.THIRD);

                    setDoubleHeightThenWidth(frameModel, width1, width4, width2, width3, height1, height4, height2, height3);
                }

                //for horz vert
                else if(width2 >= horzVertMinWidth && amountW2 >= amountW3 && amountW2 >= amountW4 &&
                        amountW2 >= amountH2 && amountW2 >= amountH3 && amountW2 >= amountH4){
                    //width2 as right of horz right height1 in horz vert
                    beanShade4.setLayoutType(LayoutType.HORZ_VERT);

                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setHorzVertHeightAndWidth(frameModel, width1, width2, width3, width4, height1, height2, height3, height4);
                }else if(width3 >= horzVertMinWidth && amountW3 >= amountW2 && amountW3 >= amountW4 &&
                        amountW3 >= amountH2 && amountW3 >= amountH3 && amountW3 >= amountH4){
                    //width3 as right of horz right height1 in horz vert
                    beanShade4.setLayoutType(LayoutType.HORZ_VERT);

                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setHorzVertHeightAndWidth(frameModel, width1, width3, width2, width4, height1, height3, height2, height4);
                }else if(width4 >= horzVertMinWidth && amountW4 >= amountW3 && amountW4 >= amountW2 &&
                        amountW4 >= amountH2 && amountW4 >= amountH3 && amountW4 >= amountH4){
                    //width4 as right of horz right height1 in horz vert
                    beanShade4.setLayoutType(LayoutType.HORZ_VERT);

                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.THIRD);

                    setHorzVertHeightAndWidth(frameModel, width1, width4, width2, width3, height1, height4, height2, height3);
                }

                //for horz
                else{
                    MIN_HIGHT = frameModel.getMinFrameHeight() / 1.5f;
                    MIN_WIDTH = frameModel.getMinFrameWidth();
                    //height2, height3, height4 will go right to the height1 in horz
                    beanShade4.setLayoutType(LayoutType.HORZ);

                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setHorzHeightAndWidth(frameModel, width1, width2, width3, width4, height1, height2, height3, height4);
                }
            }else if(height2 >= parallelMinHeight && amountH2 >= amountH1 && amountH2 >= amountH3 && amountH2 >= amountH4 &&
                    amountH2 >= amountW1 && amountH2 >= amountW2 && amountH2 >= amountW3 && amountH2 >= amountW4){
                MIN_WIDTH = frameModel.getMinFrameWidth() / 1.5f;
                //height as left in horz
                //for double horz
                if(height1 >= parallelMinHeight && amountH1 >= amountH3 && amountH1 >= amountH4 &&
                        amountH1 >= amountW1 && amountH1 >= amountW3 && amountH1 >= amountH4){
                    //height1 to right of  height2 in parallel horz
                    beanShade4.setLayoutType(LayoutType.HORZ_DOUBLE);

                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setDoubleHeightThenWidth(frameModel, width2, width1, width3, width4, height2, height1, height3, height4);
                }else if(height3 >= parallelMinHeight && amountH3 >= amountH1 && amountH3 >= amountH4 &&
                        amountH3 >= amountW1 && amountH3 >= amountW3 && amountH3 >= amountH4){
                    //height3 to right of  height2 in parallel horz
                    beanShade4.setLayoutType(LayoutType.HORZ_DOUBLE);

                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setDoubleHeightThenWidth(frameModel, width2, width3, width1, width4, height2, height3, height1, height4);
                }else if(height4 >= parallelMinHeight && amountH4 >= amountH3 && amountH4 >= amountH1 &&
                        amountH4 >= amountW1 && amountH4 >= amountW3 && amountH4 >= amountH4){
                    //height4 to right of  height2 in parallel horz
                    beanShade4.setLayoutType(LayoutType.HORZ_DOUBLE);

                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.THIRD);

                    setDoubleHeightThenWidth(frameModel, width2, width4, width1, width3, height2, height4, height1, height3);
                }

                //for horz vert
                else if(width1 >= horzVertMinWidth && amountW1 >= amountW3 && amountW1 >= amountW4 &&
                        amountW1 >= amountH1 && amountW1 >= amountH3 && amountW1 >= amountH4){
                    //width1 as right of horz right height2 in horz vert
                    beanShade4.setLayoutType(LayoutType.HORZ_VERT);

                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setHorzVertHeightAndWidth(frameModel, width2, width1, width3, width4, height2, height1, height3, height4);
                }else if(width3 >= horzVertMinWidth && amountW3 >= amountW1 && amountW3 >= amountW4 &&
                        amountW3 >= amountH1 && amountW3 >= amountH3 && amountW3 >= amountH4){
                    //width3 as right of horz right height2 in horz vert
                    beanShade4.setLayoutType(LayoutType.HORZ_VERT);

                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setHorzVertHeightAndWidth(frameModel, width2, width3, width1, width4, height2, height3, height1, height4);
                }else if(width4 >= horzVertMinWidth && amountW4 >= amountW3 && amountW4 >= amountW1 &&
                        amountW4 >= amountH1 && amountW4 >= amountH3 && amountW4 >= amountH4){
                    //width4 as right of horz right height2 in horz vert
                    beanShade4.setLayoutType(LayoutType.HORZ_VERT);

                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.THIRD);

                    setHorzVertHeightAndWidth(frameModel, width2, width4, width1, width3, height2, height4, height1, height3);
                }

                //for horz
                else{
                    MIN_HIGHT = frameModel.getMinFrameHeight() / 1.5f;
                    MIN_WIDTH = frameModel.getMinFrameWidth();
                    //height1, height3, height4 will go right to the height2 in horz
                    beanShade4.setLayoutType(LayoutType.HORZ);

                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setHorzHeightAndWidth(frameModel, width2, width1, width3, width4, height2, height1, height3, height4);
                }
            }else if(height3 >= parallelMinHeight && amountH3 >= amountH2 && amountH3 >= amountH1 && amountH3 >= amountH4 &&
                    amountH3 >= amountW1 && amountH3 >= amountW2 && amountH3 >= amountW3 && amountH3 >= amountW4){
                MIN_WIDTH = frameModel.getMinFrameWidth() / 1.5f;
                //height as left in horz
                //for double horz
                if(height2 >= parallelMinHeight && amountH2 >= amountH1 && amountH2 >= amountH4 &&
                        amountH2 >= amountW2 && amountH2 >= amountW1 && amountH2 >= amountH4){
                    //height2 to right of  height3 in parallel horz
                    beanShade4.setLayoutType(LayoutType.HORZ_DOUBLE);

                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setDoubleHeightThenWidth(frameModel, width3, width2, width1, width4, height3, height2, height1, height4);
                }else if(height1 >= parallelMinHeight && amountH1 >= amountH2 && amountH1 >= amountH4 &&
                        amountH1 >= amountW2 && amountH1 >= amountW1 && amountH1 >= amountH4){
                    //height1 to right of  height3 in parallel horz
                    beanShade4.setLayoutType(LayoutType.HORZ_DOUBLE);

                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setDoubleHeightThenWidth(frameModel, width3, width1, width2, width4, height3, height1, height2, height4);
                }else if(height4 >= parallelMinHeight && amountH4 >= amountH1 && amountH4 >= amountH2 &&
                        amountH4 >= amountW2 && amountH4 >= amountW1 && amountH4 >= amountH4){
                    //height4 to right of  height3 in parallel horz
                    beanShade4.setLayoutType(LayoutType.HORZ_DOUBLE);

                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);

                    setDoubleHeightThenWidth(frameModel, width3, width4, width1, width2, height3, height4, height1, height2);
                }

                //for horz vert
                else if(width2 >= horzVertMinWidth && amountW2 >= amountW1 && amountW2 >= amountW4 &&
                        amountW2 >= amountH2 && amountW2 >= amountH1 && amountW2 >= amountH4){
                    //width2 as right of horz right height3 in horz vert
                    beanShade4.setLayoutType(LayoutType.HORZ_VERT);

                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setHorzVertHeightAndWidth(frameModel, width3, width2, width1, width4, height3, height2, height1, height4);
                }else if(width1 >= horzVertMinWidth && amountW1 >= amountW2 && amountW1 >= amountW4 &&
                        amountW1 >= amountH2 && amountW1 >= amountH1 && amountW1 >= amountH4){
                    //width1 as right of horz right height3 in horz vert
                    beanShade4.setLayoutType(LayoutType.HORZ_VERT);

                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setHorzVertHeightAndWidth(frameModel, width3, width1, width2, width4, height3, height1, height2, height4);
                }else if(width4 >= horzVertMinWidth && amountW4 >= amountW1 && amountW4 >= amountW2 &&
                        amountW4 >= amountH2 && amountW4 >= amountH1 && amountW4 >= amountH4){
                    //width4 as right of horz right height3 in horz vert
                    beanShade4.setLayoutType(LayoutType.HORZ_VERT);

                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);

                    setHorzVertHeightAndWidth(frameModel, width3, width4, width1, width2, height3, height4, height1, height2);
                }

                //for horz
                else{
                    MIN_HIGHT = frameModel.getMinFrameHeight() / 1.5f;
                    MIN_WIDTH = frameModel.getMinFrameWidth();
                    //height2, height1, height4 will go right to the height3 in horz
                    beanShade4.setLayoutType(LayoutType.HORZ);

                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FOURTH);

                    setHorzHeightAndWidth(frameModel, width3, width1, width2, width4, height3, height1, height2, height4);
                }
            }else if(height4 >= parallelMinHeight && amountH4 >= amountH2 && amountH4 >= amountH3 && amountH4 >= amountH1 &&
                    amountH4 >= amountW1 && amountH4 >= amountW2 && amountH4 >= amountW3 && amountH4 >= amountW4){
                MIN_WIDTH = frameModel.getMinFrameWidth() / 1.5f;
                //height as left in horz
                //for double horz
                if(height2 >= parallelMinHeight && amountH2 >= amountH3 && amountH2 >= amountH1 &&
                        amountH2 >= amountW2 && amountH2 >= amountW3 && amountH2 >= amountH1){
                    //height2 to right of  height4 in parallel horz
                    beanShade4.setLayoutType(LayoutType.HORZ_DOUBLE);

                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.THIRD);

                    setDoubleHeightThenWidth(frameModel, width4, width2, width1, width3, height4, height2, height1, height3);
                }else if(height3 >= parallelMinHeight && amountH3 >= amountH2 && amountH3 >= amountH1 &&
                        amountH3 >= amountW2 && amountH3 >= amountW3 && amountH3 >= amountH1){
                    //height3 to right of  height4 in parallel horz
                    beanShade4.setLayoutType(LayoutType.HORZ_DOUBLE);

                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);

                    setDoubleHeightThenWidth(frameModel, width4, width3, width1, width2, height4, height3, height1, height2);
                }else if(height1 >= parallelMinHeight && amountH1 >= amountH3 && amountH1 >= amountH2 &&
                        amountH1 >= amountW2 && amountH1 >= amountW3 && amountH1 >= amountH1){
                    //height1 to right of  height4 in parallel horz
                    beanShade4.setLayoutType(LayoutType.HORZ_DOUBLE);

                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.THIRD);

                    setDoubleHeightThenWidth(frameModel, width4, width1, width2, width3, height4, height1, height2, height3);
                }

                //for horz vert
                else if(width2 >= horzVertMinWidth && amountW2 >= amountW3 && amountW2 >= amountW1 &&
                        amountW2 >= amountH2 && amountW2 >= amountH3 && amountW2 >= amountH1){
                    //width2 as right of horz right height4 in horz vert
                    beanShade4.setLayoutType(LayoutType.HORZ_VERT);

                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.THIRD);

                    setHorzVertHeightAndWidth(frameModel, width4, width2, width1, width3, height4, height2, height1, height3);
                }else if(width3 >= horzVertMinWidth && amountW3 >= amountW2 && amountW3 >= amountW1 &&
                        amountW3 >= amountH2 && amountW3 >= amountH3 && amountW3 >= amountH1){
                    //width3 as right of horz right height4 in horz vert
                    beanShade4.setLayoutType(LayoutType.HORZ_VERT);

                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.THIRD);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);

                    setHorzVertHeightAndWidth(frameModel, width4, width3, width1, width2, height4, height3, height1, height2);
                }else if(width1 >= horzVertMinWidth && amountW1 >= amountW3 && amountW1 >= amountW2 &&
                        amountW1 >= amountH2 && amountW1 >= amountH3 && amountW1 >= amountH1){
                    //width1 as right of horz right height4 in horz vert
                    beanShade4.setLayoutType(LayoutType.HORZ_VERT);

                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.THIRD);

                    setHorzVertHeightAndWidth(frameModel, width4, width1, width2, width3, height4, height1, height2, height3);
                }

                //for horz
                else{
                    MIN_HIGHT = frameModel.getMinFrameHeight() / 1.5f;
                    MIN_WIDTH = frameModel.getMinFrameWidth();
                    //height2, height3, height1 will go right to the height4 in horz
                    beanShade4.setLayoutType(LayoutType.HORZ);

                    imageOrderList.add(ImageOrder.FOURTH);
                    imageOrderList.add(ImageOrder.FIRST);
                    imageOrderList.add(ImageOrder.SECOND);
                    imageOrderList.add(ImageOrder.THIRD);

                    setHorzHeightAndWidth(frameModel, width4, width1, width2, width3, height4, height1, height2, height3);
                }
            }

            //2//if not then check for any one width or height satisfying condition for max width or height and
            //also any other one height or width satisfying less then max condition but still big respectively
            //or if we have one width max and one height max then we'll check the max between them

            //2//make it parr vert or horz as we have already checked condition for atleast one width or height being max
            beanShade4.setImageOrderList(imageOrderList);

            beanShade4.setWidth1(round(WIDTH_1));
            beanShade4.setWidth2(round(WIDTH_2));
            beanShade4.setWidth3(round(WIDTH_3));
            beanShade4.setWidth4(round(WIDTH_4));

            beanShade4.setHeight1(round(HEIGHT_1));
            beanShade4.setHeight2(round(HEIGHT_2));
            beanShade4.setHeight3(round(HEIGHT_3));
            beanShade4.setHeight4(round(HEIGHT_4));

            return beanShade4;
        }
        //case for multiple identical layout

        float MAX_HEIGHT_DIFFERENCE = MIN_HIGHT * 0.3f;//30%
        float MAX_WIDTH_DIFFERENCE = MIN_WIDTH * 0.3f;//30%
        boolean varyWidth;
        //vary width or height
        if((varyWidth = Math.abs(height1 - height2) <= MAX_HEIGHT_DIFFERENCE && Math.abs(height3 - height4) <= MAX_HEIGHT_DIFFERENCE)
                || Math.abs(width1 - width2) <= MAX_WIDTH_DIFFERENCE && Math.abs(width3 - width4) <= MAX_WIDTH_DIFFERENCE)
            setWidthAndHeight1234(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
        else if((varyWidth = Math.abs(height1 - height3) <= MAX_HEIGHT_DIFFERENCE && Math.abs(height2 - height4) <= MAX_HEIGHT_DIFFERENCE)
                || Math.abs(width1 - width3) <= MAX_WIDTH_DIFFERENCE && Math.abs(width2 - width4) <= MAX_WIDTH_DIFFERENCE)
            setWidthAndHeight1324(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
        else if((varyWidth = Math.abs(height2 - height3) <= MAX_HEIGHT_DIFFERENCE && Math.abs(height1 - height4) <= MAX_HEIGHT_DIFFERENCE)
                || Math.abs(width2 - width3) <= MAX_WIDTH_DIFFERENCE && Math.abs(width1 - width4) <= MAX_WIDTH_DIFFERENCE)
            setWidthAndHeight1423(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);

        //identical
        else{
            float maxWidth = Math.max(Math.max(Math.max(width1, width2), width3), width4);
            float maxHeight = Math.max(Math.max(Math.max(height1, height2), height3), height4);

            if(varyWidth = maxWidth >= maxHeight){
                if(maxWidth == width1){
                    float diffHeight12 = Math.abs(height1 - height2);
                    float diffHeight13 = Math.abs(height1 - height3);
                    float diffHeight14 = Math.abs(height1 - height4);
                    if(diffHeight12 <= diffHeight13 && diffHeight12 <= diffHeight14)
                        setWidthAndHeight1234(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                    else if(diffHeight13 <= diffHeight12 && diffHeight13 <= diffHeight14)
                        setWidthAndHeight1324(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                    else if(diffHeight14 <= diffHeight12 && diffHeight14 <= diffHeight13)
                        setWidthAndHeight1423(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                }else if(maxWidth == width2){
                    float diffHeight21 = Math.abs(height1 - height2);
                    float diffHeight23 = Math.abs(height2 - height3);
                    float diffHeight24 = Math.abs(height2 - height4);
                    if(diffHeight21 <= diffHeight23 && diffHeight21 <= diffHeight24)
                        setWidthAndHeight1234(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                    else if(diffHeight24 <= diffHeight21 && diffHeight24 <= diffHeight23)
                        setWidthAndHeight1324(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                    else if(diffHeight23 <= diffHeight21 && diffHeight23 <= diffHeight24)
                        setWidthAndHeight1423(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                }else if(maxWidth == width3){
                    float diffHeight31 = Math.abs(height1 - height3);
                    float diffHeight32 = Math.abs(height2 - height3);
                    float diffHeight34 = Math.abs(height3 - height4);
                    if(diffHeight34 <= diffHeight31 && diffHeight34 <= diffHeight32)
                        setWidthAndHeight1234(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                    else if(diffHeight31 <= diffHeight32 && diffHeight31 <= diffHeight34)
                        setWidthAndHeight1324(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                    else if(diffHeight32 <= diffHeight31 && diffHeight32 <= diffHeight34)
                        setWidthAndHeight1423(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                }else{
                    float diffHeight41 = Math.abs(height1 - height4);
                    float diffHeight42 = Math.abs(height2 - height4);
                    float diffHeight43 = Math.abs(height3 - height4);
                    if(diffHeight43 <= diffHeight41 && diffHeight43 <= diffHeight42)
                        setWidthAndHeight1234(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                    else if(diffHeight42 <= diffHeight41 && diffHeight42 <= diffHeight43)
                        setWidthAndHeight1324(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                    else if(diffHeight41 <= diffHeight42 && diffHeight41 <= diffHeight43)
                        setWidthAndHeight1423(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                }
            }else{
                if(maxHeight == height1){
                    float diffWidth12 = Math.abs(width1 - width2);
                    float diffWidth13 = Math.abs(width1 - width3);
                    float diffWidth14 = Math.abs(width1 - width4);
                    if(diffWidth12 <= diffWidth13 && diffWidth12 <= diffWidth14)
                        setWidthAndHeight1234(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                    else if(diffWidth13 <= diffWidth12 && diffWidth13 <= diffWidth14)
                        setWidthAndHeight1324(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                    else if(diffWidth14 <= diffWidth12 && diffWidth14 <= diffWidth13)
                        setWidthAndHeight1423(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                }else if(maxHeight == height2){
                    float diffWidth21 = Math.abs(width1 - width2);
                    float diffWidth23 = Math.abs(width2 - width3);
                    float diffWidth24 = Math.abs(width2 - width4);
                    if(diffWidth21 <= diffWidth23 && diffWidth21 <= diffWidth24)
                        setWidthAndHeight1234(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                    else if(diffWidth24 <= diffWidth21 && diffWidth24 <= diffWidth23)
                        setWidthAndHeight1324(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                    else if(diffWidth23 <= diffWidth21 && diffWidth23 <= diffWidth24)
                        setWidthAndHeight1423(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                }else if(maxHeight == height3){
                    float diffWidth31 = Math.abs(width1 - width3);
                    float diffWidth32 = Math.abs(width2 - width3);
                    float diffWidth34 = Math.abs(width3 - width4);
                    if(diffWidth34 <= diffWidth31 && diffWidth34 <= diffWidth32)
                        setWidthAndHeight1234(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                    else if(diffWidth31 <= diffWidth32 && diffWidth31 <= diffWidth34)
                        setWidthAndHeight1324(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                    else if(diffWidth32 <= diffWidth31 && diffWidth32 <= diffWidth34)
                        setWidthAndHeight1423(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                }else{
                    float diffWidth41 = Math.abs(width1 - width4);
                    float diffWidth42 = Math.abs(width2 - width4);
                    float diffWidth43 = Math.abs(width3 - width4);
                    if(diffWidth43 <= diffWidth41 && diffWidth43 <= diffWidth42)
                        setWidthAndHeight1234(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                    else if(diffWidth42 <= diffWidth41 && diffWidth42 <= diffWidth43)
                        setWidthAndHeight1324(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                    else if(diffWidth41 <= diffWidth42 && diffWidth41 <= diffWidth43)
                        setWidthAndHeight1423(imageOrderList, width1, width2, width3, width4, height1, height2, height3, height4);
                }
            }
        }

        if(varyWidth){
            calculateWidthAndHeightVaryWidth(frameModel);
            beanShade4.setLayoutType(LayoutType.IDENTICAL_VARY_WIDTH);
        }
        else{
            calculateWidthAndHeightVaryHeight(frameModel);
            beanShade4.setLayoutType(LayoutType.IDENTICAL_VARY_HEIGHT);
        }

        beanShade4.setImageOrderList(imageOrderList);

        beanShade4.setWidth1(round(WIDTH_1));
        beanShade4.setWidth2(round(WIDTH_2));
        beanShade4.setWidth3(round(WIDTH_3));
        beanShade4.setWidth4(round(WIDTH_4));

        beanShade4.setHeight1(round(HEIGHT_1));
        beanShade4.setHeight2(round(HEIGHT_2));
        beanShade4.setHeight3(round(HEIGHT_3));
        beanShade4.setHeight4(round(HEIGHT_4));

        return beanShade4;
    }

    private static void setDoubleWidthThenHeight(FrameModel frameModel, float width1, float width2, float width3, float width4,
                                                        float height1, float height2, float height3, float height4){
        WIDTH_1 = frameModel.isHasFixedDimensions() || width1 > frameModel.getMaxContainerWidth() ? frameModel.getMaxContainerWidth() : width1;
        WIDTH_2 = frameModel.isHasFixedDimensions() || width2 > frameModel.getMaxContainerWidth() ? frameModel.getMaxContainerWidth() : width2;

        WIDTH_3 = width3 < MIN_WIDTH ? MIN_WIDTH  : width3;
        WIDTH_4 = width4 < MIN_WIDTH ? MIN_WIDTH  : width4;

        float sumWidth = WIDTH_3 + WIDTH_4;
        float totalSum = frameModel.isHasFixedDimensions() || sumWidth > frameModel.getMaxContainerWidth() ? frameModel.getMaxContainerWidth() : sumWidth;


        float avgWidth = (WIDTH_1 + WIDTH_2 + totalSum) / 3f;


        /*WIDTH_3 = totalSum * WIDTH_3 / sumWidth;
        WIDTH_4 = totalSum * WIDTH_4 / sumWidth;*/

        float actualSumWidth = width3 + width4;
        WIDTH_3 = totalSum * (float)width3 / actualSumWidth;
        WIDTH_4 = totalSum * (float)width4 / actualSumWidth;

        if(WIDTH_3 < MIN_WIDTH){
            WIDTH_3 = MIN_WIDTH;
            WIDTH_4 = totalSum - MIN_WIDTH;
        }else if(WIDTH_4 < MIN_WIDTH){
            WIDTH_4 = MIN_WIDTH;
            WIDTH_3 = totalSum - MIN_WIDTH;
        }

        WIDTH_1 = WIDTH_2 = /*totalSum = avgWidth*/ round(WIDTH_3) + round(WIDTH_4);

        //changing actual dimentions
        if(frameModel.isHasFixedDimensions()){
            height1 += frameModel.getMaxContainerHeight() / 3f  + 2;
            height2 += frameModel.getMaxContainerHeight() / 3f  + 2;
            height3 += frameModel.getMaxContainerHeight() / 3f  + 2;
            height4 += frameModel.getMaxContainerHeight() / 3f  + 2;
        }

        float height3C = (height3 + height4) / 2f;

        float heightSum = height1 + height2 + height3C;



        //height calculation
        HEIGHT_1 = height1;
        HEIGHT_2 = height2;
        HEIGHT_3 = height3C;

        if(heightSum > frameModel.getMaxContainerHeight()){
            float reducePercent = ((heightSum - frameModel.getMaxContainerHeight()) / heightSum) * 100f;
            HEIGHT_1 = height1 - height1 * reducePercent / 100f;
            HEIGHT_2 = height2 - height2 * reducePercent / 100f;
            HEIGHT_3 = height3C - height3C * reducePercent / 100f;
        }

        float height1Increase = 0f, height2Increase = 0f, height3Increase = 0f;

        if(HEIGHT_1 < MIN_HIGHT) height1Increase = MIN_HIGHT - HEIGHT_1;
        if(HEIGHT_2 < MIN_HIGHT) height2Increase = MIN_HIGHT - HEIGHT_2;
        if(HEIGHT_3 < MIN_HIGHT) height3Increase = MIN_HIGHT - HEIGHT_3;

        //it'll make 6 cases
        if(height1Increase > 0 && height2Increase > 0 && height3Increase > 0) {
            HEIGHT_1 = MIN_HIGHT;
            HEIGHT_2 = MIN_HIGHT;
            HEIGHT_3 = MIN_HIGHT;
        }else if(height1Increase > 0 && height2Increase > 0){
            HEIGHT_1 = MIN_HIGHT;
            HEIGHT_2 = MIN_HIGHT;
            if(HEIGHT_1 + HEIGHT_2 + HEIGHT_3 > frameModel.getMaxContainerHeight()) HEIGHT_3 = frameModel.getMaxContainerHeight() - HEIGHT_1 - HEIGHT_2;
            //HEIGHT_3 -= height1Increase + height2Increase;
        }else if(height2Increase > 0 && height3Increase > 0){
            HEIGHT_3 = MIN_HIGHT;
            HEIGHT_2 = MIN_HIGHT;
            if(HEIGHT_1 + HEIGHT_2 + HEIGHT_3 > frameModel.getMaxContainerHeight()) HEIGHT_1 = frameModel.getMaxContainerHeight() - HEIGHT_2 - HEIGHT_3;
            //HEIGHT_1 -= height2Increase + height3Increase;
        }else if(height1Increase > 0 && height3Increase > 0){
            HEIGHT_1 = MIN_HIGHT;
            HEIGHT_3 = MIN_HIGHT;
            if(HEIGHT_1 + HEIGHT_2 + HEIGHT_3 > frameModel.getMaxContainerHeight()) HEIGHT_2 = frameModel.getMaxContainerHeight() - HEIGHT_1 - HEIGHT_3;
            //HEIGHT_2 -= height1Increase + height3Increase;
        }else {
            if (height1Increase > 0) {
                HEIGHT_1 = MIN_HIGHT;
                if(HEIGHT_1 + HEIGHT_2 + HEIGHT_3 > frameModel.getMaxContainerHeight()){
                    float totalHeight = frameModel.getMaxContainerHeight() - MIN_HIGHT;
                    float height = HEIGHT_2;
                    HEIGHT_2 = totalHeight * HEIGHT_2 / (HEIGHT_2 + HEIGHT_3);
                    HEIGHT_3 = totalHeight * HEIGHT_3 / (height + HEIGHT_3);
                    if (HEIGHT_2 < MIN_HIGHT){
                        HEIGHT_3 = totalHeight - MIN_HIGHT;
                        HEIGHT_2 = MIN_HIGHT;
                    }
                    else if (HEIGHT_3 < MIN_HIGHT){
                        HEIGHT_2 = totalHeight - MIN_HIGHT;
                        HEIGHT_3 = MIN_HIGHT;
                    }
                }
            }
            else if (height2Increase > 0) {
                HEIGHT_2 = MIN_HIGHT;
                if(HEIGHT_1 + HEIGHT_2 + HEIGHT_3 > frameModel.getMaxContainerHeight()){
                    float totalHeight = frameModel.getMaxContainerHeight() - MIN_HIGHT;
                    float height = HEIGHT_1;
                    HEIGHT_1 = totalHeight * HEIGHT_1 / (HEIGHT_1 + HEIGHT_3);
                    HEIGHT_3 = totalHeight * HEIGHT_3 / (height + HEIGHT_3);
                    if (HEIGHT_1 < MIN_HIGHT){
                        HEIGHT_3 = totalHeight - MIN_HIGHT;
                        HEIGHT_1 = MIN_HIGHT;
                    }
                    else if (HEIGHT_3 < MIN_HIGHT){
                        HEIGHT_1 = totalHeight - MIN_HIGHT;
                        HEIGHT_3 = MIN_HIGHT;
                    }
                }
            }
            else if (height3Increase > 0) {
                HEIGHT_3 = MIN_HIGHT;
                if(HEIGHT_1 + HEIGHT_2 + HEIGHT_3 > frameModel.getMaxContainerHeight()){
                    float totalHeight = frameModel.getMaxContainerHeight() - MIN_HIGHT;
                    float height = HEIGHT_2;
                    HEIGHT_2 = totalHeight * HEIGHT_2 / (HEIGHT_2 + HEIGHT_1);
                    HEIGHT_1 = totalHeight * HEIGHT_1 / (height + HEIGHT_1);
                    if (HEIGHT_2 < MIN_HIGHT){
                        HEIGHT_1 = totalHeight - MIN_HIGHT;
                        HEIGHT_2 = MIN_HIGHT;
                    }
                    else if (HEIGHT_1 < MIN_HIGHT){
                        HEIGHT_2 = totalHeight - MIN_HIGHT;
                        HEIGHT_1 = MIN_HIGHT;
                    }
                }
            }
        }

        HEIGHT_4 = HEIGHT_3;
    }

    private static void setDoubleHeightThenWidth(FrameModel frameModel, float width1, float width2, float width3, float width4,
                                                 float height1, float height2, float height3, float height4){
        HEIGHT_1 = frameModel.isHasFixedDimensions() || height1 > frameModel.getMaxContainerHeight() ? frameModel.getMaxContainerHeight() : height1;
        HEIGHT_2 = frameModel.isHasFixedDimensions() || height2 > frameModel.getMaxContainerHeight() ? frameModel.getMaxContainerHeight() : height2;

        HEIGHT_3 = height3 < MIN_HIGHT ? MIN_HIGHT : height3;
        HEIGHT_4 = height4 < MIN_HIGHT ? MIN_HIGHT : height4;

        float sumHeight = HEIGHT_3 + HEIGHT_4;
        float totalSum = frameModel.isHasFixedDimensions() || sumHeight > frameModel.getMaxContainerHeight() ? frameModel.getMaxContainerHeight() : sumHeight;


        float avgHeight = (HEIGHT_1 + HEIGHT_2 + totalSum) / 3f;


        /*HEIGHT_3 = totalSum * HEIGHT_3 / sumHeight;
        HEIGHT_4 = totalSum * HEIGHT_4 / sumHeight;*/

        float actualSumHeight = height3 + height4;
        HEIGHT_3 = totalSum * (float)height3 / actualSumHeight;
        HEIGHT_4 = totalSum * (float)height4 / actualSumHeight;

        if(HEIGHT_3 < MIN_HIGHT){
            HEIGHT_3 = MIN_HIGHT;
            HEIGHT_4 = totalSum - MIN_HIGHT;
        }else if(HEIGHT_4 < MIN_HIGHT){
            HEIGHT_4 = MIN_HIGHT;
            HEIGHT_3 = totalSum - MIN_HIGHT;
        }

        HEIGHT_1 = HEIGHT_2 = /*totalSum = avgHeight*/round(HEIGHT_3) + round(HEIGHT_4);


        //changing actual dimentions
        if(frameModel.isHasFixedDimensions()){
            width1 += frameModel.getMaxContainerWidth() / 3f  + 2;
            width2 += frameModel.getMaxContainerWidth() / 3f  + 2;
            width3 += frameModel.getMaxContainerWidth() / 3f  + 2;
            width4 += frameModel.getMaxContainerWidth() / 3f  + 2;

        }


        float width3C = (width3 + width4) / 2f;

        float widthSum = width1 + width2 + width3C;

        //width calculation
        WIDTH_1 = width1;
        WIDTH_2 = width2;
        WIDTH_3 = width3C;

        if(widthSum > frameModel.getMaxContainerWidth()){
            float reducePercent = ((widthSum - frameModel.getMaxContainerWidth()) / widthSum) * 100f;
            WIDTH_1 = width1 - width1 * reducePercent / 100f;
            WIDTH_2 = width2 - width2 * reducePercent / 100f;
            WIDTH_3 = width3C - width3C * reducePercent / 100f;
        }

        float width1Increase = 0f, width2Increase = 0f, width3Increase = 0f;

        if(WIDTH_1 < MIN_WIDTH) width1Increase = MIN_WIDTH - WIDTH_1;
        if(WIDTH_2 < MIN_WIDTH) width2Increase = MIN_WIDTH - WIDTH_2;
        if(WIDTH_3 < MIN_WIDTH) width3Increase = MIN_WIDTH - WIDTH_3;

        //it'll make 6 cases
        if(width1Increase > 0 && width2Increase > 0 && width3Increase > 0) {
            WIDTH_1 = MIN_WIDTH;
            WIDTH_2 = MIN_WIDTH;
            WIDTH_3 = MIN_WIDTH;
        }else if(width1Increase > 0 && width2Increase > 0){
            WIDTH_1 = MIN_WIDTH;
            WIDTH_2 = MIN_WIDTH;
            if(WIDTH_1 + WIDTH_2 + WIDTH_3 > frameModel.getMaxContainerWidth()) WIDTH_3 = frameModel.getMaxContainerWidth() - WIDTH_1 - WIDTH_2;
            //WIDTH_3 -= width1Increase + width2Increase;
        }else if(width2Increase > 0 && width3Increase > 0){
            WIDTH_3 = MIN_WIDTH;
            WIDTH_2 = MIN_WIDTH;
            if(WIDTH_1 + WIDTH_2 + WIDTH_3 > frameModel.getMaxContainerWidth()) WIDTH_1 = frameModel.getMaxContainerWidth() - WIDTH_2 - WIDTH_3;
            //WIDTH_1 -= width2Increase + width3Increase;
        }else if(width1Increase > 0 && width3Increase > 0){
            WIDTH_1 = MIN_WIDTH;
            WIDTH_3 = MIN_WIDTH;
            if(WIDTH_1 + WIDTH_2 + WIDTH_3 > frameModel.getMaxContainerWidth()) WIDTH_2 = frameModel.getMaxContainerWidth() - WIDTH_1 - WIDTH_3;
            //WIDTH_2 -= width1Increase + width3Increase;
        }else {
            if (width1Increase > 0) {
                WIDTH_1 = MIN_WIDTH;
                if(WIDTH_1 + WIDTH_2 + WIDTH_3 > frameModel.getMaxContainerWidth()){
                    float totalWidth = frameModel.getMaxContainerWidth() - MIN_WIDTH;
                    float width = WIDTH_2;
                    WIDTH_2 = totalWidth * WIDTH_2 / (WIDTH_2 + WIDTH_3);
                    WIDTH_3 = totalWidth * WIDTH_3 / (width + WIDTH_3);
                    if (WIDTH_2 < MIN_WIDTH){
                        WIDTH_3 = totalWidth - MIN_WIDTH;
                        WIDTH_2 = MIN_WIDTH;
                    }
                    else if (WIDTH_3 < MIN_WIDTH){
                        WIDTH_2 = totalWidth - MIN_WIDTH;
                        WIDTH_3 = MIN_WIDTH;
                    }
                }
            }
            else if (width2Increase > 0) {
                WIDTH_2 = MIN_WIDTH;
                if(WIDTH_1 + WIDTH_2 + WIDTH_3 > frameModel.getMaxContainerWidth()){
                    float totalWidth = frameModel.getMaxContainerWidth() - MIN_WIDTH;
                    //here WIDTH_1 instead of width1 because it has been reduced or not with same percentage
                    float width = WIDTH_1;
                    WIDTH_1 = totalWidth * WIDTH_1 / (WIDTH_1 + WIDTH_3);
                    WIDTH_3 = totalWidth * WIDTH_3 / (width + WIDTH_3);
                    if (WIDTH_1 < MIN_WIDTH){
                        WIDTH_3 = totalWidth - MIN_WIDTH;
                        WIDTH_1 = MIN_WIDTH;
                    }
                    else if (WIDTH_3 < MIN_WIDTH){
                        WIDTH_1 = totalWidth - MIN_WIDTH;
                        WIDTH_3 = MIN_WIDTH;
                    }
                }
            }
            else if (width3Increase > 0) {
                WIDTH_3 = MIN_WIDTH;
                if(WIDTH_1 + WIDTH_2 + WIDTH_3 > frameModel.getMaxContainerWidth()){
                    float totalWidth = frameModel.getMaxContainerWidth() - MIN_WIDTH;
                    float width = WIDTH_2;
                    WIDTH_2 = totalWidth * WIDTH_2 / (WIDTH_2 + WIDTH_1);
                    WIDTH_1 = totalWidth * WIDTH_1 / (width + WIDTH_1);
                    if (WIDTH_2 < MIN_WIDTH){
                        WIDTH_1 = totalWidth - MIN_WIDTH;
                        WIDTH_2 = MIN_WIDTH;
                    }
                    else if (WIDTH_1 < MIN_WIDTH){
                        WIDTH_2 = totalWidth - MIN_WIDTH;
                        WIDTH_1 = MIN_WIDTH;
                    }
                }
            }
        }

        WIDTH_4 = WIDTH_3;
    }

    private static void setVertHorzHightAndWidth(FrameModel frameModel, float width1, float width2, float width3, float width4,
                                                 float height1, float height2, float height3, float height4){
        WIDTH_1 = frameModel.isHasFixedDimensions() || width1 > frameModel.getMaxContainerWidth() ? frameModel.getMaxContainerWidth() : width1;

        WIDTH_2 = width2 < MIN_WIDTH ? MIN_WIDTH : width2;
        WIDTH_3 = width3 < MIN_WIDTH ? MIN_WIDTH : width3;
        WIDTH_4 = width4 < MIN_WIDTH ? MIN_WIDTH : width4;

        float avgWidth = (WIDTH_3 + WIDTH_4) / 2f;
        float widthSum = avgWidth + WIDTH_2;

        float totalWidth = frameModel.isHasFixedDimensions() || widthSum > frameModel.getMaxContainerWidth() ? frameModel.getMaxContainerWidth() : widthSum;

        float totalAvgWidth = (WIDTH_1 + totalWidth) / 2f;


        float actualAvgWidth = (width3 + width4) / 2f;
        float actualWidthSum = width2 + actualAvgWidth;

        WIDTH_2 = totalAvgWidth * width2 / actualWidthSum;
        WIDTH_3 = WIDTH_4 = totalAvgWidth * actualAvgWidth / actualWidthSum;

        if(WIDTH_2 < MIN_WIDTH){
            WIDTH_2 = MIN_WIDTH;
            WIDTH_3 = WIDTH_4 = totalAvgWidth - MIN_WIDTH;
        }else if(WIDTH_3 < MIN_WIDTH){
            WIDTH_3 = WIDTH_4 = MIN_WIDTH;
            WIDTH_2 = totalAvgWidth - MIN_WIDTH;
        }

        WIDTH_1 = /*totalAvgWidth*/ round(WIDTH_2) + round(WIDTH_3);

        HEIGHT_3 = height3 < MIN_HIGHT ? MIN_HIGHT : height3;
        HEIGHT_4 = height4 < MIN_HIGHT ? MIN_HIGHT : height4;

        float avgHeight = (height2 + (HEIGHT_3 + HEIGHT_4)) / 2f;

        float height3A = height3;

        //changing actual dimentions
        height3 = round((avgHeight * (float)height3 / (height3 + height4)));
        height4 = round((avgHeight * (float)height4 / (height3A + height4)));


        //changing actual dimentions
        if(frameModel.isHasFixedDimensions()){
            height1 += frameModel.getMaxContainerHeight() / 3f  + 2;
            height3 += frameModel.getMaxContainerHeight() / 3f  + 2;
            height4 += frameModel.getMaxContainerHeight() / 3f  + 2;
        }

        //height calculation
        HEIGHT_1 = height1;
        HEIGHT_3 = height3;
        HEIGHT_4 = height4;

        float heightSum = HEIGHT_1 + HEIGHT_3 + HEIGHT_4;

        if(heightSum > frameModel.getMaxContainerHeight()){
            float reducePercent = ((heightSum - frameModel.getMaxContainerHeight()) / heightSum) * 100f;
            HEIGHT_1 = height1 - height1 * reducePercent / 100f;
            HEIGHT_3 = height3 - height3 * reducePercent / 100f;
            HEIGHT_4 = height4 - height4 * reducePercent / 100f;
        }

        float height1Increase = 0f, height3Increase = 0f, height4Increase = 0f;

        if(HEIGHT_1 < MIN_HIGHT) height1Increase = MIN_HIGHT - HEIGHT_1;
        if(HEIGHT_3 < MIN_HIGHT) height3Increase = MIN_HIGHT - HEIGHT_3;
        if(HEIGHT_4 < MIN_HIGHT) height4Increase = MIN_HIGHT - HEIGHT_4;

        //it'll make 6 cases
        if(height1Increase > 0 && height3Increase > 0 && height4Increase > 0) {
            HEIGHT_1 = MIN_HIGHT;
            HEIGHT_3 = MIN_HIGHT;
            HEIGHT_4 = MIN_HIGHT;
        }else if(height1Increase > 0 && height3Increase > 0){
            HEIGHT_1 = MIN_HIGHT;
            HEIGHT_3 = MIN_HIGHT;
            if(HEIGHT_1 + HEIGHT_3 + HEIGHT_4 > frameModel.getMaxContainerHeight()) HEIGHT_4 = frameModel.getMaxContainerHeight() - HEIGHT_1 - HEIGHT_3;
            //HEIGHT_4 -= height1Increase + height3Increase;
        }else if(height3Increase > 0 && height4Increase > 0){
            HEIGHT_4 = MIN_HIGHT;
            HEIGHT_3 = MIN_HIGHT;
            if(HEIGHT_1 + HEIGHT_3 + HEIGHT_4 > frameModel.getMaxContainerHeight()) HEIGHT_1 = frameModel.getMaxContainerHeight() - HEIGHT_3 - HEIGHT_4;
            //HEIGHT_1 -= height3Increase + height4Increase;
        }else if(height1Increase > 0 && height4Increase > 0){
            HEIGHT_1 = MIN_HIGHT;
            HEIGHT_4 = MIN_HIGHT;
            if(HEIGHT_1 + HEIGHT_3 + HEIGHT_4 > frameModel.getMaxContainerHeight()) HEIGHT_3 = frameModel.getMaxContainerHeight() - HEIGHT_1 - HEIGHT_4;
            //HEIGHT_3 -= height1Increase + height4Increase;
        }else {
            if (height1Increase > 0) {
                HEIGHT_1 = MIN_HIGHT;
                if(HEIGHT_1 + HEIGHT_3 + HEIGHT_4 > frameModel.getMaxContainerHeight()){
                    float totalHeight = frameModel.getMaxContainerHeight() - MIN_HIGHT;
                    float height = HEIGHT_3;
                    HEIGHT_3 = totalHeight * HEIGHT_3 / (HEIGHT_3 + HEIGHT_4);
                    HEIGHT_4 = totalHeight * HEIGHT_4 / (height + HEIGHT_4);
                    if (HEIGHT_3 < MIN_HIGHT){
                        HEIGHT_4 = totalHeight - MIN_HIGHT;
                        HEIGHT_3 = MIN_HIGHT;
                    }
                    else if (HEIGHT_4 < MIN_HIGHT){
                        HEIGHT_3 = totalHeight - MIN_HIGHT;
                        HEIGHT_4 = MIN_HIGHT;
                    }
                }
            }
            else if (height3Increase > 0) {
                HEIGHT_3 = MIN_HIGHT;
                if(HEIGHT_1 + HEIGHT_3 + HEIGHT_4 > frameModel.getMaxContainerHeight()){
                    float totalHeight = frameModel.getMaxContainerHeight() - MIN_HIGHT;
                    float height = HEIGHT_1;
                    HEIGHT_1 = totalHeight * HEIGHT_1 / (HEIGHT_1 + HEIGHT_4);
                    HEIGHT_4 = totalHeight * HEIGHT_4 / (height + HEIGHT_4);
                    if (HEIGHT_1 < MIN_HIGHT){
                        HEIGHT_4 = totalHeight - MIN_HIGHT;
                        HEIGHT_1 = MIN_HIGHT;
                    }
                    else if (HEIGHT_4 < MIN_HIGHT){
                        HEIGHT_1 = totalHeight - MIN_HIGHT;
                        HEIGHT_4 = MIN_HIGHT;
                    }
                }
            }
            else if (height4Increase > 0) {
                HEIGHT_4 = MIN_HIGHT;
                if(HEIGHT_1 + HEIGHT_3 + HEIGHT_4 > frameModel.getMaxContainerHeight()){
                    float totalHeight = frameModel.getMaxContainerHeight() - MIN_HIGHT;
                    float height = HEIGHT_1;
                    HEIGHT_1 = totalHeight * HEIGHT_1 / (HEIGHT_1 + HEIGHT_3);
                    HEIGHT_3 = totalHeight * HEIGHT_3 / (height + HEIGHT_3);
                    if (HEIGHT_3 < MIN_HIGHT){
                        HEIGHT_1 = totalHeight - MIN_HIGHT;
                        HEIGHT_3 = MIN_HIGHT;
                    }
                    else if (HEIGHT_1 < MIN_HIGHT){
                        HEIGHT_3 = totalHeight - MIN_HIGHT;
                        HEIGHT_1 = MIN_HIGHT;
                    }
                }
            }
        }

        HEIGHT_2 = round(HEIGHT_3) + round(HEIGHT_4);
    }

    private static void setHorzVertHeightAndWidth(FrameModel frameModel, float width1, float width2, float width3, float width4,
                                                  float height1, float height2, float height3, float height4){
        HEIGHT_1 = frameModel.isHasFixedDimensions() || height1 > frameModel.getMaxContainerHeight() ? frameModel.getMaxContainerHeight() : height1;

        HEIGHT_2 = height2 < MIN_HIGHT ? MIN_HIGHT : height2;
        HEIGHT_3 = height3 < MIN_HIGHT ? MIN_HIGHT : height3;
        HEIGHT_4 = height4 < MIN_HIGHT ? MIN_HIGHT : height4;

        float avgHeight = (HEIGHT_3 + HEIGHT_4) / 2f;
        float heightSum = avgHeight + HEIGHT_2;

        float totalHeight = frameModel.isHasFixedDimensions() || heightSum > frameModel.getMaxContainerHeight() ? frameModel.getMaxContainerHeight() : heightSum;

        float totalAvgHeight = (HEIGHT_1 + totalHeight) / 2f;


        float actualAvgHeight = (height3 + height4) / 2f;
        float actualHeightSum = height2 + actualAvgHeight;

        HEIGHT_2 = totalAvgHeight * height2 / actualHeightSum;
        HEIGHT_3 = HEIGHT_4 = totalAvgHeight * actualAvgHeight / actualHeightSum;

        if(HEIGHT_2 < MIN_HIGHT){
            HEIGHT_2 = MIN_HIGHT;
            HEIGHT_3 = HEIGHT_4 = totalAvgHeight - MIN_HIGHT;
        }else if(HEIGHT_3 < MIN_HIGHT){
            HEIGHT_3 = HEIGHT_4 = MIN_HIGHT;
            HEIGHT_2 = totalAvgHeight - MIN_HIGHT;
        }

        HEIGHT_1 = /*totalAvgHeight*/round(HEIGHT_2) + round(HEIGHT_3);

        WIDTH_3 = width3 < MIN_WIDTH ? MIN_WIDTH : width3;
        WIDTH_4 = width4 < MIN_WIDTH ? MIN_WIDTH : width4;

        float avgWidth = (width2 + (WIDTH_3 + WIDTH_4)) / 2f;


        float width3A = width3;
        //changing actual dimentions
        width3 = round((avgWidth * (float)width3 / (width3 + width4)));
        width4 = round((avgWidth * (float)width4 / (width3A + width4)));

        //changing actual dimentions
        if(frameModel.isHasFixedDimensions()){
            width1 += frameModel.getMaxContainerWidth() / 3f  + 2;
            width3 += frameModel.getMaxContainerWidth() / 3f  + 2;
            width4 += frameModel.getMaxContainerWidth() / 3f  + 2;
        }

        //width calculation
        WIDTH_1 = width1;
        WIDTH_3 = width3;
        WIDTH_4 = width4;

        float widthSum = WIDTH_1 + WIDTH_3 + WIDTH_4;

        if(widthSum > frameModel.getMaxContainerWidth()){
            float reducePercent = ((widthSum - frameModel.getMaxContainerWidth()) / widthSum) * 100f;
            WIDTH_1 = width1 - width1 * reducePercent / 100f;
            WIDTH_3 = width3 - width3 * reducePercent / 100f;
            WIDTH_4 = width4 - width4 * reducePercent / 100f;
        }

        float width1Increase = 0f, width3Increase = 0f, width4Increase = 0f;

        if(WIDTH_1 < MIN_WIDTH) width1Increase = MIN_WIDTH - WIDTH_1;
        if(WIDTH_3 < MIN_WIDTH) width3Increase = MIN_WIDTH - WIDTH_3;
        if(WIDTH_4 < MIN_WIDTH) width4Increase = MIN_WIDTH - WIDTH_4;

        //it'll make 6 cases
        if(width1Increase > 0 && width3Increase > 0 && width4Increase > 0) {
            WIDTH_1 = MIN_WIDTH;
            WIDTH_3 = MIN_WIDTH;
            WIDTH_4 = MIN_WIDTH;
        }else if(width1Increase > 0 && width3Increase > 0){
            WIDTH_1 = MIN_WIDTH;
            WIDTH_3 = MIN_WIDTH;
            if(WIDTH_1 + WIDTH_3 + WIDTH_4 > frameModel.getMaxContainerWidth()) WIDTH_4 = frameModel.getMaxContainerWidth() - WIDTH_1 - WIDTH_3;
            //WIDTH_4 -= width1Increase + width3Increase;
        }else if(width3Increase > 0 && width4Increase > 0){
            WIDTH_4 = MIN_WIDTH;
            WIDTH_3 = MIN_WIDTH;
            if(WIDTH_1 + WIDTH_3 + WIDTH_4 > frameModel.getMaxContainerWidth()) WIDTH_1 = frameModel.getMaxContainerWidth() - WIDTH_3 - WIDTH_4;
            //WIDTH_1 -= width3Increase + width4Increase;
        }else if(width1Increase > 0 && width4Increase > 0){
            WIDTH_1 = MIN_WIDTH;
            WIDTH_4 = MIN_WIDTH;
            if(WIDTH_1 + WIDTH_3 + WIDTH_4 > frameModel.getMaxContainerWidth()) WIDTH_3 = frameModel.getMaxContainerWidth() - WIDTH_1 - WIDTH_4;
            //WIDTH_3 -= width1Increase + width4Increase;
        }else {
            if (width1Increase > 0) {
                WIDTH_1 = MIN_WIDTH;
                if(WIDTH_1 + WIDTH_3 + WIDTH_4 > frameModel.getMaxContainerWidth()){
                    float totalWidth = frameModel.getMaxContainerWidth() - MIN_WIDTH;
                    float width = WIDTH_3;
                    WIDTH_3 = totalWidth * WIDTH_3 / (WIDTH_3 + WIDTH_4);
                    WIDTH_4 = totalWidth * WIDTH_4 / (width + WIDTH_4);
                    if (WIDTH_4 < MIN_WIDTH){
                        WIDTH_3 = totalWidth - MIN_WIDTH;
                        WIDTH_4 = MIN_WIDTH;
                    }
                    else if (WIDTH_3 < MIN_WIDTH){
                        WIDTH_4 = totalWidth - MIN_WIDTH;
                        WIDTH_3 = MIN_WIDTH;
                    }
                }
            }
            else if (width3Increase > 0) {
                WIDTH_3 = MIN_WIDTH;
                if(WIDTH_1 + WIDTH_3 + WIDTH_4 > frameModel.getMaxContainerWidth()){
                    float totalWidth = frameModel.getMaxContainerWidth() - MIN_WIDTH;
                    float width = WIDTH_1;
                    WIDTH_1 = totalWidth * WIDTH_1 / (WIDTH_1 + WIDTH_4);
                    WIDTH_4 = totalWidth * WIDTH_4 / (width + WIDTH_4);
                    if (WIDTH_4 < MIN_WIDTH){
                        WIDTH_1 = totalWidth - MIN_WIDTH;
                        WIDTH_4 = MIN_WIDTH;
                    }
                    else if (WIDTH_1 < MIN_WIDTH){
                        WIDTH_4 = totalWidth - MIN_WIDTH;
                        WIDTH_1 = MIN_WIDTH;
                    }
                }
            }
            else if (width4Increase > 0) {
                WIDTH_4 = MIN_WIDTH;
                if(WIDTH_1 + WIDTH_3 + WIDTH_4 > frameModel.getMaxContainerWidth()){
                    float totalWidth = frameModel.getMaxContainerWidth() - MIN_WIDTH;
                    float width = WIDTH_1;
                    WIDTH_1 = totalWidth * WIDTH_1 / (WIDTH_1 + WIDTH_3);
                    WIDTH_3 = totalWidth * WIDTH_3 / (width + WIDTH_3);
                    if (WIDTH_1 < MIN_WIDTH){
                        WIDTH_3 = totalWidth - MIN_WIDTH;
                        WIDTH_1 = MIN_WIDTH;
                    }
                    else if (WIDTH_3 < MIN_WIDTH){
                        WIDTH_1 = totalWidth - MIN_WIDTH;
                        WIDTH_3 = MIN_WIDTH;
                    }
                }
            }
        }

        WIDTH_2 = round(WIDTH_3) + round(WIDTH_4);
    }

    private static void setVertWidthAndHeight(FrameModel frameModel, float width1, float width2, float width3, float width4,
                                              float height1, float height2, float height3, float height4){
        HEIGHT_1 = height1 < MIN_HIGHT ? MIN_HIGHT : height1;
        HEIGHT_2 = height2 < MIN_HIGHT ? MIN_HIGHT : height2;
        HEIGHT_3 = height3 < MIN_HIGHT ? MIN_HIGHT : height3;
        HEIGHT_4 = height4 < MIN_HIGHT ? MIN_HIGHT : height4;

        float actualAvgHeight = (height2 + height3 + height4) / 3f;
        float avgHeight = (HEIGHT_2 + HEIGHT_3 + HEIGHT_4) / 3f;
        avgHeight = avgHeight < MIN_HIGHT ? MIN_HIGHT : avgHeight;

        float sumHeight = HEIGHT_1 + avgHeight;
        if(sumHeight > frameModel.getMaxContainerHeight() || frameModel.isHasFixedDimensions()){
            float ratio = (float)height1 / (height1 + actualAvgHeight);
            HEIGHT_1 = frameModel.getMaxContainerHeight() * ratio;
            avgHeight = frameModel.getMaxContainerHeight() * (1f - ratio);

            if(HEIGHT_1 < MIN_HIGHT){
                HEIGHT_1 = MIN_HIGHT;
                if(MIN_HIGHT + avgHeight > frameModel.getMaxContainerHeight()) avgHeight = frameModel.getMaxContainerHeight() - MIN_HIGHT;
            } else if(avgHeight < MIN_HIGHT){
                avgHeight  = MIN_HIGHT;
                if(MIN_HIGHT + HEIGHT_1 > frameModel.getMaxContainerHeight()) HEIGHT_1 = frameModel.getMaxContainerHeight() - MIN_HIGHT;
            }
        }

        HEIGHT_2 = HEIGHT_3 = HEIGHT_4 = avgHeight;


        //width calculation
        WIDTH_1 = frameModel.isHasFixedDimensions() || width1 > frameModel.getMaxContainerWidth() ? frameModel.getMaxContainerWidth() : width1;

        float MAX_WIDTH = (width1 + (width2 < MIN_WIDTH ? MIN_WIDTH : width2)
                                +(width3 < MIN_WIDTH ? MIN_WIDTH : width3)
                                +(width4 < MIN_WIDTH ? MIN_WIDTH : width4)) / 2f;

        MAX_WIDTH = frameModel.isHasFixedDimensions() || MAX_WIDTH > frameModel.getMaxContainerWidth() ? frameModel.getMaxContainerWidth() : MAX_WIDTH;


        //changing actual dimentions
        if(frameModel.isHasFixedDimensions()){
            width2 += MAX_WIDTH / 3f  + 2;
            width3 += MAX_WIDTH / 3f  + 2;
            width4 += MAX_WIDTH / 3f  + 2;
        }


        WIDTH_2 = width2;
        WIDTH_3 = width3;
        WIDTH_4 = width4;

        float sumWidth = WIDTH_2 + WIDTH_3 + WIDTH_4;


        if(sumWidth > MAX_WIDTH){
            float reducePercent = ((sumWidth - MAX_WIDTH) / sumWidth) * 100f;
            WIDTH_2 = width2 - width2 * reducePercent / 100f;
            WIDTH_3 = width3 - width3 * reducePercent / 100f;
            WIDTH_4 = width4 - width4 * reducePercent / 100f;
        }

        float width2Increase = 0f, width3Increase = 0f, width4Increase = 0f;

        if(WIDTH_2 < MIN_WIDTH) width2Increase = MIN_WIDTH - WIDTH_2;
        if(WIDTH_3 < MIN_WIDTH) width3Increase = MIN_WIDTH - WIDTH_3;
        if(WIDTH_4 < MIN_WIDTH) width4Increase = MIN_WIDTH - WIDTH_4;

        //it'll make 6 cases
        if(width2Increase > 0 && width3Increase > 0 && width4Increase > 0) {
            WIDTH_2 = MIN_WIDTH;
            WIDTH_3 = MIN_WIDTH;
            WIDTH_4 = MIN_WIDTH;
        }else if(width2Increase > 0 && width3Increase > 0){
            WIDTH_2 = MIN_WIDTH;
            WIDTH_3 = MIN_WIDTH;
            if(WIDTH_2 + WIDTH_3 + WIDTH_4 > MAX_WIDTH) WIDTH_4 = MAX_WIDTH - WIDTH_2 - WIDTH_3;
            //WIDTH_4 -= width2Increase + width3Increase;
        }else if(width3Increase > 0 && width4Increase > 0){
            WIDTH_4 = MIN_WIDTH;
            WIDTH_3 = MIN_WIDTH;
            if(WIDTH_2 + WIDTH_3 + WIDTH_4 > MAX_WIDTH) WIDTH_2 = MAX_WIDTH - WIDTH_3 - WIDTH_4;
            //WIDTH_2 -= width3Increase + width4Increase;
        }else if(width2Increase > 0 && width4Increase > 0){
            WIDTH_2 = MIN_WIDTH;
            WIDTH_4 = MIN_WIDTH;
            if(WIDTH_2 + WIDTH_3 + WIDTH_4 > MAX_WIDTH) WIDTH_3 = MAX_WIDTH - WIDTH_2 - WIDTH_4;
            //WIDTH_3 -= width2Increase + width4Increase;
        }else {
            if (width2Increase > 0) {
                WIDTH_2 = MIN_WIDTH;
                if(WIDTH_2 + WIDTH_3 + WIDTH_4 > MAX_WIDTH){
                    float totalWidth = MAX_WIDTH - MIN_WIDTH;
                    float width = WIDTH_3;
                    WIDTH_3 = totalWidth * WIDTH_3 / (WIDTH_3 + WIDTH_4);
                    WIDTH_4 = totalWidth * WIDTH_4 / (width + WIDTH_4);
                    if (WIDTH_3 < MIN_WIDTH){
                        WIDTH_4 = totalWidth - MIN_WIDTH;
                        WIDTH_3 = MIN_WIDTH;
                    }
                    else if (WIDTH_4 < MIN_WIDTH){
                        WIDTH_3 = totalWidth - MIN_WIDTH;
                        WIDTH_4 = MIN_WIDTH;
                    }
                }
            }
            else if (width3Increase > 0) {
                WIDTH_3 = MIN_WIDTH;
                if(WIDTH_2 + WIDTH_3 + WIDTH_4 > MAX_WIDTH){
                    float totalWidth = MAX_WIDTH - MIN_WIDTH;
                    float width = WIDTH_2;
                    WIDTH_2 = totalWidth * WIDTH_2 / (WIDTH_2 + WIDTH_4);
                    WIDTH_4 = totalWidth * WIDTH_4 / (width + WIDTH_4);
                    if (WIDTH_2 < MIN_WIDTH){
                        WIDTH_4 = totalWidth - MIN_WIDTH;
                        WIDTH_2 = MIN_WIDTH;
                    }
                    else if (WIDTH_4 < MIN_WIDTH){
                        WIDTH_2 = totalWidth - MIN_WIDTH;
                        WIDTH_4 = MIN_WIDTH;
                    }
                }
            }
            else if (width4Increase > 0) {
                WIDTH_4 = MIN_WIDTH;
                if(WIDTH_2 + WIDTH_3 + WIDTH_4 > MAX_WIDTH){
                    float totalWidth = MAX_WIDTH - MIN_WIDTH;
                    float width = WIDTH_3;
                    WIDTH_3 = totalWidth * WIDTH_3 / (WIDTH_3 + WIDTH_2);
                    WIDTH_2 = totalWidth * WIDTH_2 / (width + WIDTH_2);
                    if (WIDTH_3 < MIN_WIDTH){
                        WIDTH_2 = totalWidth - MIN_WIDTH;
                        WIDTH_3 = MIN_WIDTH;
                    }
                    else if (WIDTH_2 < MIN_WIDTH){
                        WIDTH_3 = totalWidth - MIN_WIDTH;
                        WIDTH_2 = MIN_WIDTH;
                    }
                }
            }
        }

        WIDTH_1 = round(WIDTH_2) + round(WIDTH_3) + round(WIDTH_4);
    }

    private static void setHorzHeightAndWidth(FrameModel frameModel, float width1, float width2, float width3, float width4,
                                              float height1, float height2, float height3, float height4){
        WIDTH_1 = width1 < MIN_WIDTH ? MIN_WIDTH : width1;
        WIDTH_2 = width2 < MIN_WIDTH ? MIN_WIDTH : width2;
        WIDTH_3 = width3 < MIN_WIDTH ? MIN_WIDTH : width3;
        WIDTH_4 = width4 < MIN_WIDTH ? MIN_WIDTH : width4;

        float actualAvgWidth = (width2 + width3 + width4) / 3f;
        float avgWidth = (WIDTH_2 + WIDTH_3 + WIDTH_4) / 3f;
        avgWidth = avgWidth < MIN_WIDTH ? MIN_WIDTH : avgWidth;

        float sumWidth = WIDTH_1 + avgWidth;
        if(sumWidth > frameModel.getMaxContainerWidth() || frameModel.isHasFixedDimensions()){
            float ratio = (float)width1 / (width1 + actualAvgWidth);
            WIDTH_1 = frameModel.getMaxContainerWidth() * ratio;
            avgWidth = frameModel.getMaxContainerWidth() * (1f - ratio);

            if(WIDTH_1 < MIN_WIDTH){
                WIDTH_1 = MIN_WIDTH;
                if(MIN_WIDTH + avgWidth > frameModel.getMaxContainerWidth()) avgWidth = frameModel.getMaxContainerWidth() - MIN_WIDTH;
            } else if(avgWidth < MIN_WIDTH){
                avgWidth  = MIN_WIDTH;
                if(MIN_WIDTH + WIDTH_1 > frameModel.getMaxContainerWidth()) WIDTH_1 = frameModel.getMaxContainerWidth() - MIN_WIDTH;
            }
        }

        WIDTH_2 = WIDTH_3 = WIDTH_4 = avgWidth;


        //height calculation
        HEIGHT_1 = frameModel.isHasFixedDimensions() || height1 > frameModel.getMaxContainerHeight() ? frameModel.getMaxContainerHeight() : height1;

        float MAX_HEIGHT = (height1 + (height2 < MIN_HIGHT ? MIN_HIGHT : height2)
                +(height3 < MIN_HIGHT ? MIN_HIGHT : height3)
                +(height4 < MIN_HIGHT ? MIN_HIGHT : height4)) / 2f;

        MAX_HEIGHT = frameModel.isHasFixedDimensions() || MAX_HEIGHT > frameModel.getMaxContainerHeight() ? frameModel.getMaxContainerHeight() : MAX_HEIGHT;


        //changing actual dimentions
        if(frameModel.isHasFixedDimensions()){
            height2 += MAX_HEIGHT / 3f  + 2;
            height3 += MAX_HEIGHT / 3f  + 2;
            height4 += MAX_HEIGHT / 3f  + 2;
        }

        HEIGHT_2 = height2;
        HEIGHT_3 = height3;
        HEIGHT_4 = height4;

        float heightSum = HEIGHT_2 + HEIGHT_3 + HEIGHT_4;

        if(heightSum > MAX_HEIGHT){
            float reducePercent = ((heightSum - MAX_HEIGHT) / heightSum) * 100f;
            HEIGHT_2 = height2 - height2 * reducePercent / 100f;
            HEIGHT_3 = height3 - height3 * reducePercent / 100f;
            HEIGHT_4 = height4 - height4 * reducePercent / 100f;
        }

        float height2Increase = 0f, height3Increase = 0f, height4Increase = 0f;

        if(HEIGHT_2 < MIN_HIGHT) height2Increase = MIN_HIGHT - HEIGHT_2;
        if(HEIGHT_3 < MIN_HIGHT) height3Increase = MIN_HIGHT - HEIGHT_3;
        if(HEIGHT_4 < MIN_HIGHT) height4Increase = MIN_HIGHT - HEIGHT_4;

        //it'll make 6 cases
        if(height2Increase > 0 && height3Increase > 0 && height4Increase > 0) {
            HEIGHT_2 = MIN_WIDTH;
            HEIGHT_3 = MIN_WIDTH;
            HEIGHT_4 = MIN_WIDTH;
        }else if(height2Increase > 0 && height3Increase > 0){
            HEIGHT_2 = MIN_HIGHT;
            HEIGHT_3 = MIN_HIGHT;
            if(HEIGHT_2 + HEIGHT_3 + HEIGHT_4 > MAX_HEIGHT) HEIGHT_4 = frameModel.getMaxContainerHeight() - HEIGHT_2 - HEIGHT_3;
            //HEIGHT_4 -= height2Increase + height3Increase;
        }else if(height3Increase > 0 && height4Increase > 0){
            HEIGHT_4 = MIN_HIGHT;
            HEIGHT_3 = MIN_HIGHT;
            if(HEIGHT_2 + HEIGHT_3 + HEIGHT_4 > MAX_HEIGHT) HEIGHT_2 = frameModel.getMaxContainerHeight() - HEIGHT_3 - HEIGHT_4;
            //HEIGHT_2 -= height3Increase + height4Increase;
        }else if(height2Increase > 0 && height4Increase > 0){
            HEIGHT_2 = MIN_HIGHT;
            HEIGHT_4 = MIN_HIGHT;
            if(HEIGHT_2 + HEIGHT_3 + HEIGHT_4 > MAX_HEIGHT) HEIGHT_3 = frameModel.getMaxContainerHeight() - HEIGHT_2 - HEIGHT_4;
            //HEIGHT_3 -= height2Increase + height4Increase;
        }else {
            if (height2Increase > 0) {
                HEIGHT_2 = MIN_HIGHT;
                if(HEIGHT_2 + HEIGHT_3 + HEIGHT_4 > MAX_HEIGHT){
                    float totalHeight = MAX_HEIGHT - MIN_HIGHT;
                    float height = HEIGHT_3;
                    HEIGHT_3 = totalHeight * HEIGHT_3 / (HEIGHT_3 + HEIGHT_4);
                    HEIGHT_4 = totalHeight * HEIGHT_4 / (height + HEIGHT_4);
                    if (HEIGHT_3 < MIN_HIGHT){
                        HEIGHT_4 = totalHeight - MIN_HIGHT;
                        HEIGHT_3 = MIN_HIGHT;
                    }
                    else if (HEIGHT_4 < MIN_HIGHT){
                        HEIGHT_3 = totalHeight - MIN_HIGHT;
                        HEIGHT_4 = MIN_HIGHT;
                    }
                }
            }
            else if (height3Increase > 0) {
                HEIGHT_3 = MIN_HIGHT;
                if(HEIGHT_2 + HEIGHT_3 + HEIGHT_4 > MAX_HEIGHT){
                    float totalHeight = MAX_HEIGHT - MIN_HIGHT;
                    float height = HEIGHT_2;
                    HEIGHT_2 = totalHeight * HEIGHT_2 / (HEIGHT_2 + HEIGHT_4);
                    HEIGHT_4 = totalHeight * HEIGHT_4 / (height + HEIGHT_4);
                    if (HEIGHT_2 < MIN_HIGHT){
                        HEIGHT_4 = totalHeight - MIN_HIGHT;
                        HEIGHT_2 = MIN_HIGHT;
                    }
                    else if (HEIGHT_4 < MIN_HIGHT){
                        HEIGHT_2 = totalHeight - MIN_HIGHT;
                        HEIGHT_4 = MIN_HIGHT;
                    }
                }
            }
            else if (height4Increase > 0) {
                HEIGHT_4 = MIN_HIGHT;
                if(HEIGHT_2 + HEIGHT_3 + HEIGHT_4 > MAX_HEIGHT){
                    float totalHeight = MAX_HEIGHT - MIN_HIGHT;
                    float height = HEIGHT_2;
                    HEIGHT_2 = totalHeight * HEIGHT_2 / (HEIGHT_2 + HEIGHT_3);
                    HEIGHT_3 = totalHeight * HEIGHT_3 / (height + HEIGHT_3);
                    if (HEIGHT_3 < MIN_HIGHT){
                        HEIGHT_2 = totalHeight - MIN_HIGHT;
                        HEIGHT_3 = MIN_HIGHT;
                    }
                    else if (HEIGHT_2 < MIN_HIGHT){
                        HEIGHT_3 = totalHeight - MIN_HIGHT;
                        HEIGHT_2 = MIN_HIGHT;
                    }
                }
            }
        }

        HEIGHT_1 = round(HEIGHT_2) + round(HEIGHT_3) + round(HEIGHT_4);
    }

    private static void setWidthAndHeight1234(List<ImageOrder> imageOrderList, float width1, float width2, float width3, float width4,
                                              float height1, float height2, float height3, float height4){
        HEIGHT_1 = height1;
        HEIGHT_2 = height2;
        HEIGHT_3 = height3;
        HEIGHT_4 = height4;

        WIDTH_1 = width1;
        WIDTH_2 = width2;
        WIDTH_3 = width3;
        WIDTH_4 = width4;

        imageOrderList.add(ImageOrder.FIRST);
        imageOrderList.add(ImageOrder.SECOND);
        imageOrderList.add(ImageOrder.THIRD);
        imageOrderList.add(ImageOrder.FOURTH);
    }
    private static void setWidthAndHeight1324(List<ImageOrder> imageOrderList, float width1, float width2, float width3, float width4,
                                              float height1, float height2, float height3, float height4){
        HEIGHT_1 = height1;
        HEIGHT_3 = height2;
        HEIGHT_2 = height3;
        HEIGHT_4 = height4;

        WIDTH_1 = width1;
        WIDTH_3 = width2;
        WIDTH_2 = width3;
        WIDTH_4 = width4;

        imageOrderList.add(ImageOrder.FIRST);
        imageOrderList.add(ImageOrder.THIRD);
        imageOrderList.add(ImageOrder.SECOND);
        imageOrderList.add(ImageOrder.FOURTH);
    }

    private static void setWidthAndHeight1423(List<ImageOrder> imageOrderList, float width1, float width2, float width3, float width4,
                                              float height1, float height2, float height3, float height4){
        HEIGHT_1 = height1;
        HEIGHT_3 = height2;
        HEIGHT_4 = height3;
        HEIGHT_2 = height4;

        WIDTH_1 = width1;
        WIDTH_3 = width2;
        WIDTH_4 = width3;
        WIDTH_2 = width4;

        imageOrderList.add(ImageOrder.FIRST);
        imageOrderList.add(ImageOrder.FOURTH);
        imageOrderList.add(ImageOrder.SECOND);
        imageOrderList.add(ImageOrder.THIRD);
    }


    private static void calculateWidthAndHeightVaryWidth(FrameModel frameModel){
        //1-2
        //3-4

        //height calculation
        float height1 = HEIGHT_1;
        float height2 = HEIGHT_2;
        float height3 = HEIGHT_3;
        float height4 = HEIGHT_4;

        HEIGHT_1 = height1 < MIN_HIGHT ? MIN_HIGHT : height1;
        HEIGHT_2 = height2 < MIN_HIGHT ? MIN_HIGHT : height2;
        HEIGHT_3 = height3 < MIN_HIGHT ? MIN_HIGHT : height3;
        HEIGHT_4 = height4 < MIN_HIGHT ? MIN_HIGHT : height4;

        float actualAvgHeight1 = (height1 + height2) / 2f;
        float avgHeight1 = (HEIGHT_1 + HEIGHT_2) / 2f;
        avgHeight1 = avgHeight1 < MIN_HIGHT ? MIN_HIGHT : avgHeight1;

        float actualAvgHeight2 = (height3 + height4) / 2f;
        float avgHeight2 = (HEIGHT_3 + HEIGHT_4) / 2f;
        avgHeight2 = avgHeight2 < MIN_HIGHT ? MIN_HIGHT : avgHeight2;

        float sumHeight = avgHeight1 + avgHeight2;

        if(sumHeight > frameModel.getMaxContainerHeight() || frameModel.isHasFixedDimensions()){
            float ratio = actualAvgHeight1 / (actualAvgHeight1 + actualAvgHeight2);
            avgHeight1 = frameModel.getMaxContainerHeight() * ratio;
            avgHeight2 = frameModel.getMaxContainerHeight() * (1f - ratio);

            if(avgHeight1 < MIN_HIGHT){
                avgHeight1 = MIN_HIGHT;
                if(MIN_HIGHT + avgHeight2 > frameModel.getMaxContainerHeight()) avgHeight2 = frameModel.getMaxContainerHeight() - MIN_HIGHT;
            } else if(avgHeight2 < MIN_HIGHT){
                avgHeight2  = MIN_HIGHT;
                if(MIN_HIGHT + avgHeight1 > frameModel.getMaxContainerHeight()) avgHeight1 = frameModel.getMaxContainerHeight() - MIN_HIGHT;
            }
        }

        HEIGHT_1 = HEIGHT_2 = avgHeight1;
        HEIGHT_3 = HEIGHT_4 = avgHeight2;

        //width calculation
        float width1 = WIDTH_1;
        float width2 = WIDTH_2;
        float width3 = WIDTH_3;
        float width4 = WIDTH_4;

        WIDTH_1 = width1 < MIN_WIDTH ? MIN_WIDTH : width1;
        WIDTH_2 = width2 < MIN_WIDTH ? MIN_WIDTH : width2;
        WIDTH_3 = width3 < MIN_WIDTH ? MIN_WIDTH : width3;
        WIDTH_4 = width4 < MIN_WIDTH ? MIN_WIDTH : width4;

        float avgSum = (WIDTH_1 + WIDTH_2 + WIDTH_3 + WIDTH_4) / 2f;

        if(avgSum > frameModel.getMaxContainerWidth() || frameModel.isHasFixedDimensions()) avgSum = frameModel.getMaxContainerWidth();

        float ratio1 = width1 / (width1 + width2);
        WIDTH_1 = avgSum * ratio1;
        WIDTH_2 = avgSum * (1f - ratio1);

        if(WIDTH_1 < MIN_WIDTH){
            WIDTH_1 = MIN_WIDTH;
            //we can't do that because w1 + w2 = w3 + w4 and this condition will break that, hence we can do this in the height instead
            /*if(MIN_WIDTH + WIDTH_2 > frameModel.getMaxContainerWidth()) */WIDTH_2 = avgSum - MIN_WIDTH;
        } else if(WIDTH_2 < MIN_WIDTH){
            WIDTH_2  = MIN_WIDTH;
            /*if(MIN_WIDTH + WIDTH_1 > frameModel.getMaxContainerWidth()) */WIDTH_1 = avgSum - MIN_WIDTH;
        }

        float ratio2 = width3 / (width3 + width4);
        WIDTH_3 = avgSum * ratio2;
        WIDTH_4 = avgSum * (1f - ratio2);

        if(WIDTH_3 < MIN_WIDTH){
            WIDTH_3 = MIN_WIDTH;
            /*if(MIN_WIDTH + WIDTH_4 > frameModel.getMaxContainerWidth()) */WIDTH_4 = avgSum - MIN_WIDTH;
        } else if(WIDTH_4 < MIN_WIDTH){
            WIDTH_4  = MIN_WIDTH;
            /*if(MIN_WIDTH + WIDTH_3 > frameModel.getMaxContainerWidth()) */WIDTH_3 = avgSum - MIN_WIDTH;
        }
    }
    private static void calculateWidthAndHeightVaryHeight(FrameModel frameModel){
        //1-3
        //2-4

        //width calculation
        float width1 = WIDTH_1;
        float width2 = WIDTH_2;
        float width3 = WIDTH_3;
        float width4 = WIDTH_4;

        WIDTH_1 = width1 < MIN_WIDTH ? MIN_WIDTH : width1;
        WIDTH_2 = width2 < MIN_WIDTH ? MIN_WIDTH : width2;
        WIDTH_3 = width3 < MIN_WIDTH ? MIN_WIDTH : width3;
        WIDTH_4 = width4 < MIN_WIDTH ? MIN_WIDTH : width4;

        float actualAvgWidth1 = (width1 + width2) / 2f;
        float avgWidth1 = (WIDTH_1 + WIDTH_2) / 2f;
        avgWidth1 = avgWidth1 < MIN_WIDTH ? MIN_WIDTH: avgWidth1;

        float actualAvgWidth2 = (width3 + width4) / 2f;
        float avgWidth2 = (WIDTH_3 + WIDTH_4) / 2f;
        avgWidth2 = avgWidth2 < MIN_WIDTH ? MIN_WIDTH : avgWidth2;

        float sumWidth = avgWidth1 + avgWidth2;

        if(sumWidth > frameModel.getMaxContainerWidth() || frameModel.isHasFixedDimensions()){
            float ratio = actualAvgWidth1 / (actualAvgWidth1 + actualAvgWidth2);
            avgWidth1 = frameModel.getMaxContainerWidth() * ratio;
            avgWidth2 = frameModel.getMaxContainerWidth() * (1f - ratio);

            if(avgWidth1 < MIN_WIDTH){
                avgWidth1 = MIN_WIDTH;
                if(MIN_WIDTH + avgWidth2 > frameModel.getMaxContainerWidth()) avgWidth2 = frameModel.getMaxContainerWidth()- MIN_WIDTH;
            } else if(avgWidth2 < MIN_WIDTH){
                avgWidth2  = MIN_WIDTH;
                if(MIN_WIDTH + avgWidth1 > frameModel.getMaxContainerWidth()) avgWidth1 = frameModel.getMaxContainerWidth() - MIN_WIDTH;
            }
        }

        WIDTH_1 = WIDTH_2 = avgWidth1;
        WIDTH_3 = WIDTH_4 = avgWidth2;


        //height calculation
        float height1 = HEIGHT_1;
        float height2 = HEIGHT_2;
        float height3 = HEIGHT_3;
        float height4 = HEIGHT_4;

        HEIGHT_1 = height1 < MIN_HIGHT ? MIN_HIGHT : height1;
        HEIGHT_2 = height2 < MIN_HIGHT ? MIN_HIGHT : height2;
        HEIGHT_3 = height3 < MIN_HIGHT ? MIN_HIGHT : height3;
        HEIGHT_4 = height4 < MIN_HIGHT ? MIN_HIGHT : height4;

        float avgHeight = (HEIGHT_1 + HEIGHT_2 + HEIGHT_3 + HEIGHT_4) / 2f;
        if(avgHeight > frameModel.getMaxContainerHeight() || frameModel.isHasFixedDimensions()) avgHeight = frameModel.getMaxContainerHeight();

        float ratio1 = height1 / (height1 + height2);
        HEIGHT_1 = avgHeight * ratio1;
        HEIGHT_2 = avgHeight * (1f - ratio1);

        if(HEIGHT_1 < MIN_HIGHT){
            HEIGHT_1 = MIN_HIGHT;
            /*if(MIN_HIGHT + HEIGHT_2 > frameModel.getMaxContainerHeight()) */HEIGHT_2 = avgHeight - MIN_HIGHT;
        } else if(HEIGHT_2 < MIN_HIGHT){
            HEIGHT_2  = MIN_HIGHT;
            /*if(MIN_HIGHT + HEIGHT_1 > frameModel.getMaxContainerHeight()) */HEIGHT_1 = avgHeight - MIN_HIGHT;
        }

        float ratio2 = height3 / (height3 + height4);
        HEIGHT_3 = avgHeight * ratio2;
        HEIGHT_4 = avgHeight * (1f - ratio2);

        if(HEIGHT_3 < MIN_HIGHT){
            HEIGHT_3 = MIN_HIGHT;
            /*if(MIN_HIGHT + HEIGHT_4 > frameModel.getMaxContainerHeight()) */HEIGHT_4 = avgHeight - MIN_HIGHT;
        } else if(HEIGHT_4 < MIN_HIGHT){
            HEIGHT_4  = MIN_HIGHT;
            /*if(MIN_HIGHT + HEIGHT_3 > frameModel.getMaxContainerHeight()) */HEIGHT_3 = avgHeight - MIN_HIGHT;
        }
    }
}
