package proj.me.bitframe.shading_four;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import proj.me.bitframe.BeanBitFrame;
import proj.me.bitframe.BeanImage;
import proj.me.bitframe.FrameModel;
import proj.me.bitframe.ImageCallback;
import proj.me.bitframe.ImageClickHandler;
import proj.me.bitframe.ImageType;
import proj.me.bitframe.R;
import proj.me.bitframe.databinding.ViewMultipleHorzBinding;
import proj.me.bitframe.databinding.ViewMultipleHorzDoubleBinding;
import proj.me.bitframe.databinding.ViewMultipleHorzVertBinding;
import proj.me.bitframe.databinding.ViewMultipleVaryHeightBinding;
import proj.me.bitframe.databinding.ViewMultipleVaryWidthBinding;
import proj.me.bitframe.databinding.ViewMultipleVertBinding;
import proj.me.bitframe.databinding.ViewMultipleVertDoubleBinding;
import proj.me.bitframe.databinding.ViewMultipleVertHorzBinding;
import proj.me.bitframe.dimentions.BeanShade4;
import proj.me.bitframe.dimentions.ShadeFour;
import proj.me.bitframe.helper.Utils;

/**
 * Created by Deepak.Tiwari on 29-09-2015.
 */
public class ImageShadingFour implements ImageClickHandler {
    LayoutInflater inflater;
    Context context;
    ImageCallback layoutCallback;
    int totalImages;


    String imageLink1, imageLink2, imageLink3, imageLink4;
    BeanBitFrame beanBitFrame1, beanBitFrame2, beanBitFrame3, beanBitFrame4;

    BindingShadeFour bindingShadeFour;
    FrameModel frameModel;

    public ImageShadingFour(Context context, ImageCallback layoutCallback, int totalImages, FrameModel frameModel) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.layoutCallback = layoutCallback;
        this.totalImages = totalImages;

        beanBitFrame1 = new BeanBitFrame();
        beanBitFrame2 = new BeanBitFrame();
        beanBitFrame3 = new BeanBitFrame();
        beanBitFrame4 = new BeanBitFrame();
        beanBitFrame1.setLoaded(true);
        beanBitFrame2.setLoaded(true);
        beanBitFrame3.setLoaded(true);
        beanBitFrame4.setLoaded(true);

        this.frameModel = frameModel;
    }


    public void updateOctalUi(List<Bitmap> images, List<BeanImage> beanImages, boolean hasImageProperties) {
        BeanBitFrame beanBitFrameFirst = null, beanBitFrameSecond = null, beanBitFrameThird = null, beanBitFrameFourth = null;
        if(hasImageProperties){
            beanBitFrameFirst = (BeanBitFrame) beanImages.get(0);
            beanBitFrameSecond = (BeanBitFrame) beanImages.get(1);
            beanBitFrameThird = (BeanBitFrame) beanImages.get(2);
            beanBitFrameFourth = (BeanBitFrame) beanImages.get(3);
        }

        int width1 = hasImageProperties ? (int)beanBitFrameFirst.getWidth() : images.get(0).getWidth();
        int height1 = hasImageProperties ? (int)beanBitFrameFirst.getHeight() : images.get(0).getHeight();

        int width2 = hasImageProperties ? (int)beanBitFrameSecond.getWidth() : images.get(1).getWidth();
        int height2 = hasImageProperties ? (int)beanBitFrameSecond.getHeight() : images.get(1).getHeight();

        int width3 = hasImageProperties ? (int)beanBitFrameThird.getWidth() : images.get(2).getWidth();
        int height3 = hasImageProperties ? (int)beanBitFrameThird.getHeight() : images.get(2).getHeight();

        int width4 = hasImageProperties ? (int)beanBitFrameFourth.getWidth() : images.get(3).getWidth();
        int height4 = hasImageProperties ? (int)beanBitFrameFourth.getHeight() : images.get(3).getHeight();

        Log.e("MAX_WIDTH", ""+frameModel.getMaxContainerWidth());
        Log.e("MAX_HEIGHT", ""+frameModel.getMaxContainerHeight());
        Log.e("MIN_WIDTH", ""+frameModel.getMinFrameWidth());
        Log.e("MIN_HIGHT", ""+ frameModel.getMinFrameHeight());

        Log.e("getWidth1 : ", ""+width1);
        Log.e("getHeight1 : ", ""+height1);
        Log.e("getWidth2 : ", ""+width2);
        Log.e("getHeight2 : ", ""+height2);
        Log.e("getWidth3 : ", ""+width3);
        Log.e("getHeight3 : ", ""+height3);
        Log.e("getWidth4 : ", ""+width4);
        Log.e("getHeight4 : ", ""+height4+"\n\n");


        BeanShade4 beanShade4 = ShadeFour.calculateDimentions(frameModel, width1, height1, width2, height2, width3, height3, width4, height4);
        Log.e("Start", "++++++++++++++++++++++++++++++++++++Start");
        Log.e("getWidth1 : ", ""+beanShade4.getWidth1());
        Log.e("getHeight1 : ", ""+beanShade4.getHeight1());
        Log.e("getWidth2 : ", ""+beanShade4.getWidth2());
        Log.e("getHeight2 : ", ""+beanShade4.getHeight2());
        Log.e("getWidth3 : ", ""+beanShade4.getWidth3());
        Log.e("getHeight3 : ", ""+beanShade4.getHeight3());
        Log.e("getWidth4 : ", ""+beanShade4.getWidth4());
        Log.e("getHeight4 : ", ""+beanShade4.getHeight4());
        for(int i=0;i<4;i++) Log.e("image order : ", ""+beanShade4.getImageOrderList().get(i));

        Log.e("layoutType : ", ""+beanShade4.getLayoutType());
        Log.e("End", "++++++++++++++++++++++++++++++++++++End");

        imageLink1  = beanImages.get(0).getImageLink();
        imageLink2  = beanImages.get(1).getImageLink();
        imageLink3  = beanImages.get(2).getImageLink();
        imageLink4  = beanImages.get(3).getImageLink();

        boolean isFirstLocalImage = beanImages.get(0).isLocalImage();
        boolean isSecondLocalImage = beanImages.get(1).isLocalImage();
        boolean isThirdLocalImage = beanImages.get(2).isLocalImage();
        boolean isFourthLocalImage = beanImages.get(3).isLocalImage();

        boolean isFirstHasExtension = beanImages.get(0).isHasExtention();
        boolean isSecondHasExtension = beanImages.get(1).isHasExtention();
        boolean isThirdHasExtension = beanImages.get(2).isHasExtention();
        boolean isFourthHasExtension = beanImages.get(3).isHasExtention();

        int firstPrimaryCount = beanImages.get(0).getPrimaryCount();
        int firstSecondaryCount = beanImages.get(0).getSecondaryCount();

        int secondPrimaryCount = beanImages.get(1).getPrimaryCount();
        int secondSecondaryCount = beanImages.get(1).getSecondaryCount();

        int thirdPrimaryCount = beanImages.get(2).getPrimaryCount();
        int thirdSecondaryCount = beanImages.get(2).getSecondaryCount();

        int fourthPrimaryCount = beanImages.get(3).getPrimaryCount();
        int fourthSecondaryCount = beanImages.get(3).getSecondaryCount();

        bindingShadeFour = new BindingShadeFour();
        View root = null;

        switch(beanShade4.getLayoutType()){
            case VERT:
                ViewMultipleVertBinding viewMultipleVertBinding = DataBindingUtil.bind(inflater.inflate(R.layout.view_multiple_vert, null));
                viewMultipleVertBinding.setClickHandler(this);
                viewMultipleVertBinding.setShadeFour(bindingShadeFour);
                root = viewMultipleVertBinding.getRoot();
                break;
            case HORZ:
                ViewMultipleHorzBinding viewMultipleHorzBinding = DataBindingUtil.bind(inflater.inflate(R.layout.view_multiple_horz, null));
                viewMultipleHorzBinding.setClickHandler(this);
                viewMultipleHorzBinding.setShadeFour(bindingShadeFour);
                root = viewMultipleHorzBinding.getRoot();
                break;
            case VERT_DOUBLE:
                ViewMultipleVertDoubleBinding viewMultipleVertDoubleBinding = DataBindingUtil.bind(inflater.inflate(R.layout.view_multiple_vert_double, null));
                viewMultipleVertDoubleBinding.setClickHandler(this);
                viewMultipleVertDoubleBinding.setShadeFour(bindingShadeFour);
                root = viewMultipleVertDoubleBinding.getRoot();
                break;
            case HORZ_DOUBLE:
                ViewMultipleHorzDoubleBinding viewMultipleHorzDoubleBinding = DataBindingUtil.bind(inflater.inflate(R.layout.view_multiple_horz_double, null));
                viewMultipleHorzDoubleBinding.setClickHandler(this);
                viewMultipleHorzDoubleBinding.setShadeFour(bindingShadeFour);
                root = viewMultipleHorzDoubleBinding.getRoot();
                break;
            case VERT_HORZ:
                ViewMultipleVertHorzBinding viewMultipleVertHorzBinding = DataBindingUtil.bind(inflater.inflate(R.layout.view_multiple_vert_horz, null));
                viewMultipleVertHorzBinding.setClickHandler(this);
                viewMultipleVertHorzBinding.setShadeFour(bindingShadeFour);
                root = viewMultipleVertHorzBinding.getRoot();
                break;
            case HORZ_VERT:
                ViewMultipleHorzVertBinding viewMultipleHorzVertBinding = DataBindingUtil.bind(inflater.inflate(R.layout.view_multiple_horz_vert, null));
                viewMultipleHorzVertBinding.setClickHandler(this);
                viewMultipleHorzVertBinding.setShadeFour(bindingShadeFour);
                root = viewMultipleHorzVertBinding.getRoot();
                break;
            case IDENTICAL_VARY_WIDTH:
                ViewMultipleVaryWidthBinding viewMultipleVaryWidthBinding = DataBindingUtil.bind(inflater.inflate(R.layout.view_multiple_vary_width, null));
                viewMultipleVaryWidthBinding.setClickHandler(this);
                viewMultipleVaryWidthBinding.setShadeFour(bindingShadeFour);
                root = viewMultipleVaryWidthBinding.getRoot();
                break;
            case IDENTICAL_VARY_HEIGHT:
                ViewMultipleVaryHeightBinding viewMultipleVaryHeightBinding = DataBindingUtil.bind(inflater.inflate(R.layout.view_multiple_vary_height, null));
                viewMultipleVaryHeightBinding.setClickHandler(this);
                viewMultipleVaryHeightBinding.setShadeFour(bindingShadeFour);
                root = viewMultipleVaryHeightBinding.getRoot();
                break;
        }



        Bitmap bitmap1 = null, bitmap2 = null, bitmap3 = null, bitmap4 = null;

        int resultColor = 0;
        boolean hasGreaterVibrantPopulation = false;

        switch (beanShade4.getImageOrderList().get(0)){
            case FIRST:
                bitmap1 = hasImageProperties ? null : images.get(0);
                bindingShadeFour.setFirstComment(beanImages.get(0).getImageComment());
                imageLink1 = beanImages.get(0).getImageLink();
                isFirstLocalImage = beanImages.get(0).isLocalImage();
                isFirstHasExtension = beanImages.get(0).isHasExtention();
                firstPrimaryCount = beanImages.get(0).getPrimaryCount();
                firstSecondaryCount = beanImages.get(0).getSecondaryCount();

                if(hasImageProperties) {
                    hasGreaterVibrantPopulation = beanBitFrameFirst.isHasGreaterVibrantPopulation();
                    resultColor = hasGreaterVibrantPopulation ? beanBitFrameFirst.getVibrantColor() : beanBitFrameFirst.getMutedColor();
                    switch (frameModel.getColorCombination()) {
                        case VIBRANT_TO_MUTED:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameFirst.getVibrantColor();
                            else resultColor = beanBitFrameFirst.getMutedColor();
                            break;
                        case MUTED_TO_VIBRANT:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameFirst.getMutedColor();
                            else resultColor = beanBitFrameFirst.getVibrantColor();
                            break;
                    }

                    bindingShadeFour.setFirstImageBgColor(resultColor);
                    bindingShadeFour.setFirstCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));

                    beanBitFrame1.setMutedColor(beanBitFrameFirst.getMutedColor());
                    beanBitFrame1.setVibrantColor(beanBitFrameFirst.getVibrantColor());
                    beanBitFrame1.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);

                    beanBitFrame1.setPrimaryCount(beanBitFrameFirst.getPrimaryCount());
                    beanBitFrame1.setSecondaryCount(beanBitFrameFirst.getSecondaryCount());

                    beanBitFrame1.setWidth(/*beanShade4.getWidth1()*/beanBitFrameFirst.getWidth());
                    beanBitFrame1.setHeight(/*beanShade4.getHeight1()*/beanBitFrameFirst.getHeight());
                }

                break;
            case SECOND:
                bitmap1 = hasImageProperties ? null : images.get(1);
                bindingShadeFour.setFirstComment(beanImages.get(1).getImageComment());
                imageLink1 = beanImages.get(1).getImageLink();
                isFirstLocalImage = beanImages.get(1).isLocalImage();
                isFirstHasExtension = beanImages.get(1).isHasExtention();
                firstPrimaryCount = beanImages.get(1).getPrimaryCount();
                firstSecondaryCount = beanImages.get(1).getSecondaryCount();

                if(hasImageProperties) {
                    hasGreaterVibrantPopulation = beanBitFrameSecond.isHasGreaterVibrantPopulation();
                    resultColor = hasGreaterVibrantPopulation ? beanBitFrameSecond.getVibrantColor() : beanBitFrameSecond.getMutedColor();
                    switch (frameModel.getColorCombination()) {
                        case VIBRANT_TO_MUTED:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameSecond.getVibrantColor();
                            else resultColor = beanBitFrameSecond.getMutedColor();
                            break;
                        case MUTED_TO_VIBRANT:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameSecond.getMutedColor();
                            else resultColor = beanBitFrameSecond.getVibrantColor();
                            break;
                    }

                    bindingShadeFour.setFirstImageBgColor(resultColor);
                    bindingShadeFour.setFirstCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));

                    beanBitFrame1.setMutedColor(beanBitFrameSecond.getMutedColor());
                    beanBitFrame1.setVibrantColor(beanBitFrameSecond.getVibrantColor());
                    beanBitFrame1.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);

                    beanBitFrame1.setPrimaryCount(beanBitFrameSecond.getPrimaryCount());
                    beanBitFrame1.setSecondaryCount(beanBitFrameSecond.getSecondaryCount());

                    beanBitFrame1.setWidth(/*beanShade4.getWidth1()*/beanBitFrameSecond.getWidth());
                    beanBitFrame1.setHeight(/*beanShade4.getHeight1()*/beanBitFrameSecond.getHeight());
                }

                break;
            case THIRD:
                bitmap1 = hasImageProperties ? null : images.get(2);
                bindingShadeFour.setFirstComment(beanImages.get(2).getImageComment());
                imageLink1 = beanImages.get(2).getImageLink();
                isFirstLocalImage = beanImages.get(2).isLocalImage();
                isFirstHasExtension = beanImages.get(2).isHasExtention();
                firstPrimaryCount = beanImages.get(2).getPrimaryCount();
                firstSecondaryCount = beanImages.get(2).getSecondaryCount();

                if(hasImageProperties) {
                    hasGreaterVibrantPopulation = beanBitFrameThird.isHasGreaterVibrantPopulation();
                    resultColor = hasGreaterVibrantPopulation ? beanBitFrameThird.getVibrantColor() : beanBitFrameThird.getMutedColor();
                    switch (frameModel.getColorCombination()) {
                        case VIBRANT_TO_MUTED:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameThird.getVibrantColor();
                            else resultColor = beanBitFrameThird.getMutedColor();
                            break;
                        case MUTED_TO_VIBRANT:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameThird.getMutedColor();
                            else resultColor = beanBitFrameThird.getVibrantColor();
                            break;
                    }

                    bindingShadeFour.setFirstImageBgColor(resultColor);
                    bindingShadeFour.setFirstCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));

                    beanBitFrame1.setMutedColor(beanBitFrameThird.getMutedColor());
                    beanBitFrame1.setVibrantColor(beanBitFrameThird.getVibrantColor());
                    beanBitFrame1.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);

                    beanBitFrame1.setPrimaryCount(beanBitFrameThird.getPrimaryCount());
                    beanBitFrame1.setSecondaryCount(beanBitFrameThird.getSecondaryCount());

                    beanBitFrame1.setWidth(/*beanShade4.getWidth1()*/beanBitFrameThird.getWidth());
                    beanBitFrame1.setHeight(/*beanShade4.getHeight1()*/beanBitFrameThird.getHeight());
                }

                break;
            case FOURTH:
                bitmap1 = hasImageProperties ? null : images.get(3);
                bindingShadeFour.setFirstComment(beanImages.get(3).getImageComment());
                imageLink1 = beanImages.get(3).getImageLink();
                isFirstLocalImage = beanImages.get(3).isLocalImage();
                isFirstHasExtension = beanImages.get(3).isHasExtention();
                firstPrimaryCount = beanImages.get(3).getPrimaryCount();
                firstSecondaryCount = beanImages.get(3).getSecondaryCount();

                if(hasImageProperties) {
                    hasGreaterVibrantPopulation = beanBitFrameFourth.isHasGreaterVibrantPopulation();
                    resultColor = hasGreaterVibrantPopulation ? beanBitFrameFourth.getVibrantColor() : beanBitFrameFourth.getMutedColor();
                    switch (frameModel.getColorCombination()) {
                        case VIBRANT_TO_MUTED:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameFourth.getVibrantColor();
                            else resultColor = beanBitFrameFourth.getMutedColor();
                            break;
                        case MUTED_TO_VIBRANT:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameFourth.getMutedColor();
                            else resultColor = beanBitFrameFourth.getVibrantColor();
                            break;
                    }

                    bindingShadeFour.setFirstImageBgColor(resultColor);
                    bindingShadeFour.setFirstCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));

                    beanBitFrame1.setMutedColor(beanBitFrameFourth.getMutedColor());
                    beanBitFrame1.setVibrantColor(beanBitFrameFourth.getVibrantColor());
                    beanBitFrame1.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);

                    beanBitFrame1.setPrimaryCount(beanBitFrameFourth.getPrimaryCount());
                    beanBitFrame1.setSecondaryCount(beanBitFrameFourth.getSecondaryCount());

                    beanBitFrame1.setWidth(/*beanShade3.getWidth1()*/beanBitFrameFourth.getWidth());
                    beanBitFrame1.setHeight(/*beanShade3.getHeight1()*/beanBitFrameFourth.getHeight());
                }

                break;
        }switch (beanShade4.getImageOrderList().get(1)){
            case FIRST:
                bitmap2 = hasImageProperties ? null : images.get(0);
                bindingShadeFour.setSecondComment(beanImages.get(0).getImageComment());
                imageLink2 = beanImages.get(0).getImageLink();
                isSecondLocalImage = beanImages.get(0).isLocalImage();
                isSecondHasExtension = beanImages.get(0).isHasExtention();
                secondPrimaryCount = beanImages.get(0).getPrimaryCount();
                secondSecondaryCount = beanImages.get(0).getSecondaryCount();

                if(hasImageProperties) {
                    hasGreaterVibrantPopulation = beanBitFrameFirst.isHasGreaterVibrantPopulation();
                    resultColor = hasGreaterVibrantPopulation ? beanBitFrameFirst.getVibrantColor() : beanBitFrameFirst.getMutedColor();
                    switch (frameModel.getColorCombination()) {
                        case VIBRANT_TO_MUTED:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameFirst.getVibrantColor();
                            else resultColor = beanBitFrameFirst.getMutedColor();
                            break;
                        case MUTED_TO_VIBRANT:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameFirst.getMutedColor();
                            else resultColor = beanBitFrameFirst.getVibrantColor();
                            break;
                    }

                    bindingShadeFour.setSecondImageBgColor(resultColor);
                    bindingShadeFour.setSecondCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));

                    beanBitFrame2.setMutedColor(beanBitFrameFirst.getMutedColor());
                    beanBitFrame2.setVibrantColor(beanBitFrameFirst.getVibrantColor());
                    beanBitFrame2.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);

                    beanBitFrame2.setPrimaryCount(beanBitFrameFirst.getPrimaryCount());
                    beanBitFrame2.setSecondaryCount(beanBitFrameFirst.getSecondaryCount());

                    beanBitFrame2.setWidth(/*beanShade4.getWidth1()*/beanBitFrameFirst.getWidth());
                    beanBitFrame2.setHeight(/*beanShade4.getHeight1()*/beanBitFrameFirst.getHeight());
                }

                break;
            case SECOND:
                bitmap2 = hasImageProperties ? null : images.get(1);
                bindingShadeFour.setSecondComment(beanImages.get(1).getImageComment());
                imageLink2 = beanImages.get(1).getImageLink();
                isSecondLocalImage = beanImages.get(1).isLocalImage();
                isSecondHasExtension = beanImages.get(1).isHasExtention();
                secondPrimaryCount = beanImages.get(1).getPrimaryCount();
                secondSecondaryCount = beanImages.get(1).getSecondaryCount();

                if(hasImageProperties) {
                    hasGreaterVibrantPopulation = beanBitFrameSecond.isHasGreaterVibrantPopulation();
                    resultColor = hasGreaterVibrantPopulation ? beanBitFrameSecond.getVibrantColor() : beanBitFrameSecond.getMutedColor();
                    switch (frameModel.getColorCombination()) {
                        case VIBRANT_TO_MUTED:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameSecond.getVibrantColor();
                            else resultColor = beanBitFrameSecond.getMutedColor();
                            break;
                        case MUTED_TO_VIBRANT:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameSecond.getMutedColor();
                            else resultColor = beanBitFrameSecond.getVibrantColor();
                            break;
                    }

                    bindingShadeFour.setSecondImageBgColor(resultColor);
                    bindingShadeFour.setSecondCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));

                    beanBitFrame2.setMutedColor(beanBitFrameSecond.getMutedColor());
                    beanBitFrame2.setVibrantColor(beanBitFrameSecond.getVibrantColor());
                    beanBitFrame2.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);

                    beanBitFrame2.setPrimaryCount(beanBitFrameSecond.getPrimaryCount());
                    beanBitFrame2.setSecondaryCount(beanBitFrameSecond.getSecondaryCount());

                    beanBitFrame2.setWidth(/*beanShade4.getWidth1()*/beanBitFrameSecond.getWidth());
                    beanBitFrame2.setHeight(/*beanShade4.getHeight1()*/beanBitFrameSecond.getHeight());
                }

                break;
            case THIRD:
                bitmap2 = hasImageProperties ? null : images.get(2);
                bindingShadeFour.setSecondComment(beanImages.get(2).getImageComment());
                imageLink2 = beanImages.get(2).getImageLink();
                isSecondLocalImage = beanImages.get(2).isLocalImage();
                isSecondHasExtension = beanImages.get(2).isHasExtention();
                secondPrimaryCount = beanImages.get(2).getPrimaryCount();
                secondSecondaryCount = beanImages.get(2).getSecondaryCount();

                if(hasImageProperties) {
                    hasGreaterVibrantPopulation = beanBitFrameThird.isHasGreaterVibrantPopulation();
                    resultColor = hasGreaterVibrantPopulation ? beanBitFrameThird.getVibrantColor() : beanBitFrameThird.getMutedColor();
                    switch (frameModel.getColorCombination()) {
                        case VIBRANT_TO_MUTED:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameThird.getVibrantColor();
                            else resultColor = beanBitFrameThird.getMutedColor();
                            break;
                        case MUTED_TO_VIBRANT:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameThird.getMutedColor();
                            else resultColor = beanBitFrameThird.getVibrantColor();
                            break;
                    }

                    bindingShadeFour.setSecondImageBgColor(resultColor);
                    bindingShadeFour.setSecondCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));

                    beanBitFrame2.setMutedColor(beanBitFrameThird.getMutedColor());
                    beanBitFrame2.setVibrantColor(beanBitFrameThird.getVibrantColor());
                    beanBitFrame2.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);

                    beanBitFrame2.setPrimaryCount(beanBitFrameThird.getPrimaryCount());
                    beanBitFrame2.setSecondaryCount(beanBitFrameThird.getSecondaryCount());

                    beanBitFrame2.setWidth(/*beanShade4.getWidth1()*/beanBitFrameThird.getWidth());
                    beanBitFrame2.setHeight(/*beanShade4.getHeight1()*/beanBitFrameThird.getHeight());
                }

                break;
            case FOURTH:
                bitmap2 = hasImageProperties ? null : images.get(3);
                bindingShadeFour.setSecondComment(beanImages.get(3).getImageComment());
                imageLink2 = beanImages.get(3).getImageLink();
                isSecondLocalImage = beanImages.get(3).isLocalImage();
                isSecondHasExtension = beanImages.get(3).isHasExtention();
                secondPrimaryCount = beanImages.get(3).getPrimaryCount();
                secondSecondaryCount = beanImages.get(3).getSecondaryCount();

                if(hasImageProperties) {
                    hasGreaterVibrantPopulation = beanBitFrameFourth.isHasGreaterVibrantPopulation();
                    resultColor = hasGreaterVibrantPopulation ? beanBitFrameFourth.getVibrantColor() : beanBitFrameFourth.getMutedColor();
                    switch (frameModel.getColorCombination()) {
                        case VIBRANT_TO_MUTED:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameFourth.getVibrantColor();
                            else resultColor = beanBitFrameFourth.getMutedColor();
                            break;
                        case MUTED_TO_VIBRANT:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameFourth.getMutedColor();
                            else resultColor = beanBitFrameFourth.getVibrantColor();
                            break;
                    }

                    bindingShadeFour.setSecondImageBgColor(resultColor);
                    bindingShadeFour.setSecondCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));

                    beanBitFrame2.setMutedColor(beanBitFrameFourth.getMutedColor());
                    beanBitFrame2.setVibrantColor(beanBitFrameFourth.getVibrantColor());
                    beanBitFrame2.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);

                    beanBitFrame2.setPrimaryCount(beanBitFrameFourth.getPrimaryCount());
                    beanBitFrame2.setSecondaryCount(beanBitFrameFourth.getSecondaryCount());

                    beanBitFrame2.setWidth(/*beanShade4.getWidth1()*/beanBitFrameFourth.getWidth());
                    beanBitFrame2.setHeight(/*beanShade4.getHeight1()*/beanBitFrameFourth.getHeight());
                }

                break;
        }switch (beanShade4.getImageOrderList().get(2)){
            case FIRST:
                bitmap3 = hasImageProperties ? null : images.get(0);
                bindingShadeFour.setThirdComment(beanImages.get(0).getImageComment());
                imageLink3 = beanImages.get(0).getImageLink();
                isThirdLocalImage = beanImages.get(0).isLocalImage();
                isThirdHasExtension = beanImages.get(0).isHasExtention();
                thirdPrimaryCount = beanImages.get(0).getPrimaryCount();
                thirdSecondaryCount = beanImages.get(0).getSecondaryCount();

                if(hasImageProperties) {
                    hasGreaterVibrantPopulation = beanBitFrameFirst.isHasGreaterVibrantPopulation();
                    resultColor = hasGreaterVibrantPopulation ? beanBitFrameFirst.getVibrantColor() : beanBitFrameFirst.getMutedColor();
                    switch (frameModel.getColorCombination()) {
                        case VIBRANT_TO_MUTED:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameFirst.getVibrantColor();
                            else resultColor = beanBitFrameFirst.getMutedColor();
                            break;
                        case MUTED_TO_VIBRANT:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameFirst.getMutedColor();
                            else resultColor = beanBitFrameFirst.getVibrantColor();
                            break;
                    }

                    bindingShadeFour.setThirdImageBgColor(resultColor);
                    bindingShadeFour.setThirdCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));

                    beanBitFrame3.setMutedColor(beanBitFrameFirst.getMutedColor());
                    beanBitFrame3.setVibrantColor(beanBitFrameFirst.getVibrantColor());
                    beanBitFrame3.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);

                    beanBitFrame3.setPrimaryCount(beanBitFrameFirst.getPrimaryCount());
                    beanBitFrame3.setSecondaryCount(beanBitFrameFirst.getSecondaryCount());

                    beanBitFrame3.setWidth(/*beanShade4.getWidth1()*/beanBitFrameFirst.getWidth());
                    beanBitFrame3.setHeight(/*beanShade4.getHeight1()*/beanBitFrameFirst.getHeight());
                }

                break;
            case SECOND:
                bitmap3 = hasImageProperties ? null : images.get(1);
                bindingShadeFour.setThirdComment(beanImages.get(1).getImageComment());
                imageLink3 = beanImages.get(1).getImageLink();
                isThirdLocalImage = beanImages.get(1).isLocalImage();
                isThirdHasExtension = beanImages.get(1).isHasExtention();
                thirdPrimaryCount = beanImages.get(1).getPrimaryCount();
                thirdSecondaryCount = beanImages.get(1).getSecondaryCount();

                if(hasImageProperties) {
                    hasGreaterVibrantPopulation = beanBitFrameSecond.isHasGreaterVibrantPopulation();
                    resultColor = hasGreaterVibrantPopulation ? beanBitFrameSecond.getVibrantColor() : beanBitFrameSecond.getMutedColor();
                    switch (frameModel.getColorCombination()) {
                        case VIBRANT_TO_MUTED:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameSecond.getVibrantColor();
                            else resultColor = beanBitFrameSecond.getMutedColor();
                            break;
                        case MUTED_TO_VIBRANT:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameSecond.getMutedColor();
                            else resultColor = beanBitFrameSecond.getVibrantColor();
                            break;
                    }

                    bindingShadeFour.setThirdImageBgColor(resultColor);
                    bindingShadeFour.setThirdCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));

                    beanBitFrame3.setMutedColor(beanBitFrameSecond.getMutedColor());
                    beanBitFrame3.setVibrantColor(beanBitFrameSecond.getVibrantColor());
                    beanBitFrame3.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);

                    beanBitFrame3.setPrimaryCount(beanBitFrameSecond.getPrimaryCount());
                    beanBitFrame3.setSecondaryCount(beanBitFrameSecond.getSecondaryCount());

                    beanBitFrame3.setWidth(/*beanShade4.getWidth1()*/beanBitFrameSecond.getWidth());
                    beanBitFrame3.setHeight(/*beanShade4.getHeight1()*/beanBitFrameSecond.getHeight());
                }

                break;
            case THIRD:
                bitmap3 = hasImageProperties ? null : images.get(2);
                bindingShadeFour.setThirdComment(beanImages.get(2).getImageComment());
                imageLink3 = beanImages.get(2).getImageLink();
                isThirdLocalImage = beanImages.get(2).isLocalImage();
                isThirdHasExtension = beanImages.get(2).isHasExtention();
                thirdPrimaryCount = beanImages.get(2).getPrimaryCount();
                thirdSecondaryCount = beanImages.get(2).getSecondaryCount();

                if(hasImageProperties) {
                    hasGreaterVibrantPopulation = beanBitFrameThird.isHasGreaterVibrantPopulation();
                    resultColor = hasGreaterVibrantPopulation ? beanBitFrameThird.getVibrantColor() : beanBitFrameThird.getMutedColor();
                    switch (frameModel.getColorCombination()) {
                        case VIBRANT_TO_MUTED:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameThird.getVibrantColor();
                            else resultColor = beanBitFrameThird.getMutedColor();
                            break;
                        case MUTED_TO_VIBRANT:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameThird.getMutedColor();
                            else resultColor = beanBitFrameThird.getVibrantColor();
                            break;
                    }

                    bindingShadeFour.setThirdImageBgColor(resultColor);
                    bindingShadeFour.setThirdCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));

                    beanBitFrame3.setMutedColor(beanBitFrameThird.getMutedColor());
                    beanBitFrame3.setVibrantColor(beanBitFrameThird.getVibrantColor());
                    beanBitFrame3.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);

                    beanBitFrame3.setPrimaryCount(beanBitFrameThird.getPrimaryCount());
                    beanBitFrame3.setSecondaryCount(beanBitFrameThird.getSecondaryCount());

                    beanBitFrame3.setWidth(/*beanShade4.getWidth1()*/beanBitFrameThird.getWidth());
                    beanBitFrame3.setHeight(/*beanShade4.getHeight1()*/beanBitFrameThird.getHeight());
                }

                break;
            case FOURTH:
                bitmap3 = hasImageProperties ? null : images.get(3);
                bindingShadeFour.setThirdComment(beanImages.get(3).getImageComment());
                imageLink3 = beanImages.get(3).getImageLink();
                isThirdLocalImage = beanImages.get(3).isLocalImage();
                isThirdHasExtension = beanImages.get(3).isHasExtention();
                thirdPrimaryCount = beanImages.get(3).getPrimaryCount();
                thirdSecondaryCount = beanImages.get(3).getSecondaryCount();

                if(hasImageProperties) {
                    hasGreaterVibrantPopulation = beanBitFrameFourth.isHasGreaterVibrantPopulation();
                    resultColor = hasGreaterVibrantPopulation ? beanBitFrameFourth.getVibrantColor() : beanBitFrameFourth.getMutedColor();
                    switch (frameModel.getColorCombination()) {
                        case VIBRANT_TO_MUTED:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameFourth.getVibrantColor();
                            else resultColor = beanBitFrameFourth.getMutedColor();
                            break;
                        case MUTED_TO_VIBRANT:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameFourth.getMutedColor();
                            else resultColor = beanBitFrameFourth.getVibrantColor();
                            break;
                    }

                    bindingShadeFour.setThirdImageBgColor(resultColor);
                    bindingShadeFour.setThirdCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));

                    beanBitFrame3.setMutedColor(beanBitFrameFourth.getMutedColor());
                    beanBitFrame3.setVibrantColor(beanBitFrameFourth.getVibrantColor());
                    beanBitFrame3.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);

                    beanBitFrame3.setPrimaryCount(beanBitFrameFourth.getPrimaryCount());
                    beanBitFrame3.setSecondaryCount(beanBitFrameFourth.getSecondaryCount());

                    beanBitFrame3.setWidth(/*beanShade4.getWidth1()*/beanBitFrameFourth.getWidth());
                    beanBitFrame3.setHeight(/*beanShade4.getHeight1()*/beanBitFrameFourth.getHeight());
                }

                break;
        }switch (beanShade4.getImageOrderList().get(3)){
            case FIRST:
                bitmap4 = hasImageProperties ? null : images.get(0);
                bindingShadeFour.setFourthComment(beanImages.get(0).getImageComment());
                imageLink4 = beanImages.get(0).getImageLink();
                isFourthLocalImage = beanImages.get(0).isLocalImage();
                isFourthHasExtension = beanImages.get(0).isHasExtention();
                fourthPrimaryCount = beanImages.get(0).getPrimaryCount();
                fourthSecondaryCount = beanImages.get(0).getSecondaryCount();

                if(hasImageProperties) {
                    hasGreaterVibrantPopulation = beanBitFrameFirst.isHasGreaterVibrantPopulation();
                    resultColor = hasGreaterVibrantPopulation ? beanBitFrameFirst.getVibrantColor() : beanBitFrameFirst.getMutedColor();
                    switch (frameModel.getColorCombination()) {
                        case VIBRANT_TO_MUTED:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameFirst.getVibrantColor();
                            else resultColor = beanBitFrameFirst.getMutedColor();
                            break;
                        case MUTED_TO_VIBRANT:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameFirst.getMutedColor();
                            else resultColor = beanBitFrameFirst.getVibrantColor();
                            break;
                    }

                    bindingShadeFour.setFourthImageBgColor(resultColor);
                    bindingShadeFour.setFourthCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));

                    beanBitFrame4.setMutedColor(beanBitFrameFirst.getMutedColor());
                    beanBitFrame4.setVibrantColor(beanBitFrameFirst.getVibrantColor());
                    beanBitFrame4.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);

                    beanBitFrame4.setPrimaryCount(beanBitFrameFirst.getPrimaryCount());
                    beanBitFrame4.setSecondaryCount(beanBitFrameFirst.getSecondaryCount());

                    beanBitFrame4.setWidth(/*beanShade4.getWidth1()*/beanBitFrameFirst.getWidth());
                    beanBitFrame4.setHeight(/*beanShade4.getHeight1()*/beanBitFrameFirst.getHeight());
                }

                break;
            case SECOND:
                bitmap4 = hasImageProperties ? null : images.get(1);
                bindingShadeFour.setFourthComment(beanImages.get(1).getImageComment());
                imageLink4 = beanImages.get(1).getImageLink();
                isFourthLocalImage = beanImages.get(1).isLocalImage();
                isFourthHasExtension = beanImages.get(1).isHasExtention();
                fourthPrimaryCount = beanImages.get(1).getPrimaryCount();
                fourthSecondaryCount = beanImages.get(1).getSecondaryCount();

                if(hasImageProperties) {
                    hasGreaterVibrantPopulation = beanBitFrameSecond.isHasGreaterVibrantPopulation();
                    resultColor = hasGreaterVibrantPopulation ? beanBitFrameSecond.getVibrantColor() : beanBitFrameSecond.getMutedColor();
                    switch (frameModel.getColorCombination()) {
                        case VIBRANT_TO_MUTED:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameSecond.getVibrantColor();
                            else resultColor = beanBitFrameSecond.getMutedColor();
                            break;
                        case MUTED_TO_VIBRANT:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameSecond.getMutedColor();
                            else resultColor = beanBitFrameSecond.getVibrantColor();
                            break;
                    }

                    bindingShadeFour.setFourthImageBgColor(resultColor);
                    bindingShadeFour.setFourthCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));

                    beanBitFrame4.setMutedColor(beanBitFrameSecond.getMutedColor());
                    beanBitFrame4.setVibrantColor(beanBitFrameSecond.getVibrantColor());
                    beanBitFrame4.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);

                    beanBitFrame4.setPrimaryCount(beanBitFrameSecond.getPrimaryCount());
                    beanBitFrame4.setSecondaryCount(beanBitFrameSecond.getSecondaryCount());

                    beanBitFrame4.setWidth(/*beanShade4.getWidth1()*/beanBitFrameSecond.getWidth());
                    beanBitFrame4.setHeight(/*beanShade4.getHeight1()*/beanBitFrameSecond.getHeight());
                }

                break;
            case THIRD:
                bitmap4 = hasImageProperties ? null : images.get(2);
                bindingShadeFour.setFourthComment(beanImages.get(2).getImageComment());
                imageLink4 = beanImages.get(2).getImageLink();
                isFourthLocalImage = beanImages.get(2).isLocalImage();
                isFourthHasExtension = beanImages.get(2).isHasExtention();
                fourthPrimaryCount = beanImages.get(2).getPrimaryCount();
                fourthSecondaryCount = beanImages.get(2).getSecondaryCount();

                if(hasImageProperties) {
                    hasGreaterVibrantPopulation = beanBitFrameThird.isHasGreaterVibrantPopulation();
                    resultColor = hasGreaterVibrantPopulation ? beanBitFrameThird.getVibrantColor() : beanBitFrameThird.getMutedColor();
                    switch (frameModel.getColorCombination()) {
                        case VIBRANT_TO_MUTED:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameThird.getVibrantColor();
                            else resultColor = beanBitFrameThird.getMutedColor();
                            break;
                        case MUTED_TO_VIBRANT:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameThird.getMutedColor();
                            else resultColor = beanBitFrameThird.getVibrantColor();
                            break;
                    }

                    bindingShadeFour.setFourthImageBgColor(resultColor);
                    bindingShadeFour.setFourthCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));

                    beanBitFrame4.setMutedColor(beanBitFrameThird.getMutedColor());
                    beanBitFrame4.setVibrantColor(beanBitFrameThird.getVibrantColor());
                    beanBitFrame4.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);

                    beanBitFrame4.setPrimaryCount(beanBitFrameThird.getPrimaryCount());
                    beanBitFrame4.setSecondaryCount(beanBitFrameThird.getSecondaryCount());

                    beanBitFrame4.setWidth(/*beanShade4.getWidth1()*/beanBitFrameThird.getWidth());
                    beanBitFrame4.setHeight(/*beanShade4.getHeight1()*/beanBitFrameThird.getHeight());
                }

                break;
            case FOURTH:
                bitmap4 = hasImageProperties ? null : images.get(3);
                bindingShadeFour.setFourthComment(beanImages.get(3).getImageComment());
                imageLink4 = beanImages.get(3).getImageLink();
                isFourthLocalImage = beanImages.get(3).isLocalImage();
                isFourthHasExtension = beanImages.get(3).isHasExtention();
                fourthPrimaryCount = beanImages.get(3).getPrimaryCount();
                fourthSecondaryCount = beanImages.get(3).getSecondaryCount();

                if(hasImageProperties) {
                    hasGreaterVibrantPopulation = beanBitFrameFourth.isHasGreaterVibrantPopulation();
                    resultColor = hasGreaterVibrantPopulation ? beanBitFrameFourth.getVibrantColor() : beanBitFrameFourth.getMutedColor();
                    switch (frameModel.getColorCombination()) {
                        case VIBRANT_TO_MUTED:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameFourth.getVibrantColor();
                            else resultColor = beanBitFrameFourth.getMutedColor();
                            break;
                        case MUTED_TO_VIBRANT:
                            if (hasGreaterVibrantPopulation)
                                resultColor = beanBitFrameFourth.getMutedColor();
                            else resultColor = beanBitFrameFourth.getVibrantColor();
                            break;
                    }

                    bindingShadeFour.setFourthImageBgColor(resultColor);
                    bindingShadeFour.setFourthCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));

                    beanBitFrame4.setMutedColor(beanBitFrameFourth.getMutedColor());
                    beanBitFrame4.setVibrantColor(beanBitFrameFourth.getVibrantColor());
                    beanBitFrame4.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);

                    beanBitFrame4.setPrimaryCount(beanBitFrameFourth.getPrimaryCount());
                    beanBitFrame4.setSecondaryCount(beanBitFrameFourth.getSecondaryCount());

                    beanBitFrame4.setWidth(/*beanShade4.getWidth1()*/beanBitFrameFourth.getWidth());
                    beanBitFrame4.setHeight(/*beanShade4.getHeight1()*/beanBitFrameFourth.getHeight());
                }

                break;
        }


        final ImageView imageView1 = (ImageView)root.findViewById(R.id.view_multiple_image1);
        final ImageView imageView2 = (ImageView)root.findViewById(R.id.view_multiple_image2);
        final ImageView imageView3 = (ImageView)root.findViewById(R.id.view_multiple_image3);
        final ImageView imageView4 = (ImageView)root.findViewById(R.id.view_multiple_image4);

        if(totalImages > 4) {
            bindingShadeFour.setCounterVisibility(true);
            bindingShadeFour.setCounterText("+" + (totalImages - 4));
        }


        BindingShadeFour.setLayoutWidth(imageView1, beanShade4.getWidth1());
        BindingShadeFour.setLayoutWidth(imageView2, beanShade4.getWidth2());
        BindingShadeFour.setLayoutWidth(imageView3, beanShade4.getWidth3());
        BindingShadeFour.setLayoutWidth(imageView4, beanShade4.getWidth4());

        BindingShadeFour.setLayoutHeight(imageView1, beanShade4.getHeight1());
        BindingShadeFour.setLayoutHeight(imageView2, beanShade4.getHeight2());
        BindingShadeFour.setLayoutHeight(imageView3, beanShade4.getHeight3());
        BindingShadeFour.setLayoutHeight(imageView4, beanShade4.getHeight4());

        bindingShadeFour.setFirstImageScaleType(frameModel.getScaleType());
        bindingShadeFour.setSecondImageScaleType(frameModel.getScaleType());
        bindingShadeFour.setThirdImageScaleType(frameModel.getScaleType());
        bindingShadeFour.setFourthImageScaleType(frameModel.getScaleType());

        if(!hasImageProperties) {
            BindingShadeFour.setBitmap(imageView1, bitmap1);
            generatePalette(bitmap1, 0);
            BindingShadeFour.setBitmap(imageView2, bitmap2);
            generatePalette(bitmap2, 1);
            BindingShadeFour.setBitmap(imageView3, bitmap3);
            generatePalette(bitmap3, 2);
            BindingShadeFour.setBitmap(imageView4, bitmap4);
            generatePalette(bitmap4, 3);
        }


        bindingShadeFour.setFirstCommentVisibility(frameModel.isShouldShowComment());
        bindingShadeFour.setSecondCommentVisibility(frameModel.isShouldShowComment());
        bindingShadeFour.setThirdCommentVisibility(frameModel.isShouldShowComment());
        bindingShadeFour.setFourthCommentVisibility(frameModel.isShouldShowComment());


        //a/c to layout type
        switch (beanShade4.getLayoutType()){
            case VERT:
                layoutCallback.addImageView(root, beanShade4.getWidth1(), beanShade4.getHeight1() + beanShade4.getHeight2(), false);
                break;
            case HORZ:
                layoutCallback.addImageView(root, beanShade4.getWidth1() + beanShade4.getWidth2(), beanShade4.getHeight1(), false);
                break;
            case VERT_DOUBLE:
                layoutCallback.addImageView(root, beanShade4.getWidth1(), beanShade4.getHeight1() + beanShade4.getHeight2() + beanShade4.getHeight3(), false);
                break;
            case HORZ_DOUBLE:
                layoutCallback.addImageView(root, beanShade4.getWidth1() + beanShade4.getWidth2() + beanShade4.getWidth3(), beanShade4.getHeight1(), false);
                break;
            case VERT_HORZ:
                layoutCallback.addImageView(root, beanShade4.getWidth1(), beanShade4.getHeight1() + beanShade4.getHeight2(), false);
                break;
            case HORZ_VERT:
                layoutCallback.addImageView(root, beanShade4.getWidth1() + beanShade4.getWidth2(), beanShade4.getHeight1(), false);
                break;
            case IDENTICAL_VARY_WIDTH:
                layoutCallback.addImageView(root, beanShade4.getWidth1() + beanShade4.getWidth2(), beanShade4.getHeight1() + beanShade4.getHeight3(), false);
                break;
            case IDENTICAL_VARY_HEIGHT:
                layoutCallback.addImageView(root, beanShade4.getWidth1() + beanShade4.getWidth3(), beanShade4.getHeight1() + beanShade4.getHeight2(), false);
                break;
        }

        if(!hasImageProperties) {
            //need to give actual dimensions for future calculations in different devices
            beanBitFrame1.setWidth(/*beanShade4.getWidth1()*/bitmap1.getWidth());
            beanBitFrame1.setHeight(/*beanShade4.getHeight1()*/bitmap1.getHeight());

            beanBitFrame2.setWidth(/*beanShade4.getWidth2()*/bitmap2.getWidth());
            beanBitFrame2.setHeight(/*beanShade4.getHeight2()*/bitmap3.getHeight());

            beanBitFrame3.setWidth(/*beanShade4.getWidth3()*/bitmap3.getWidth());
            beanBitFrame3.setHeight(/*beanShade4.getHeight3()*/bitmap3.getHeight());

            beanBitFrame4.setWidth(/*beanShade4.getWidth4()*/bitmap4.getWidth());
            beanBitFrame4.setHeight(/*beanShade4.getHeight4()*/bitmap4.getHeight());
        }


        beanBitFrame1.setImageLink(imageLink1);
        beanBitFrame1.setImageComment(bindingShadeFour.getFirstComment());
        beanBitFrame1.setLocalImage(isFirstLocalImage);
        beanBitFrame1.setHasExtention(isFirstHasExtension);
        beanBitFrame1.setPrimaryCount(firstPrimaryCount);
        beanBitFrame1.setSecondaryCount(firstSecondaryCount);

        beanBitFrame2.setImageLink(imageLink2);
        beanBitFrame2.setImageComment(bindingShadeFour.getSecondComment());
        beanBitFrame2.setLocalImage(isSecondLocalImage);
        beanBitFrame2.setHasExtention(isSecondHasExtension);
        beanBitFrame2.setPrimaryCount(secondPrimaryCount);
        beanBitFrame2.setSecondaryCount(secondSecondaryCount);

        beanBitFrame3.setImageLink(imageLink3);
        beanBitFrame3.setImageComment(bindingShadeFour.getThirdComment());
        beanBitFrame3.setLocalImage(isThirdLocalImage);
        beanBitFrame3.setHasExtention(isThirdHasExtension);
        beanBitFrame3.setPrimaryCount(thirdPrimaryCount);
        beanBitFrame3.setSecondaryCount(thirdSecondaryCount);

        beanBitFrame4.setImageLink(imageLink4);
        beanBitFrame4.setImageComment(bindingShadeFour.getFourthComment());
        beanBitFrame4.setLocalImage(isFourthLocalImage);
        beanBitFrame4.setHasExtention(isFourthHasExtension);
        beanBitFrame4.setPrimaryCount(fourthPrimaryCount);
        beanBitFrame4.setSecondaryCount(fourthSecondaryCount);

        if(hasImageProperties){
            int mixedColor = Utils.getMixedArgbColor(bindingShadeFour.getFirstImageBgColor(), bindingShadeFour.getSecondImageBgColor(), bindingShadeFour.getThirdImageBgColor(), bindingShadeFour.getFourthImageBgColor());
            int inverseColor = Utils.getInverseColor(mixedColor);

            layoutCallback.setColorsToAddMoreView(bindingShadeFour.getFourthImageBgColor(), mixedColor, inverseColor);
            layoutCallback.frameResult(beanBitFrame1, beanBitFrame2, beanBitFrame3, beanBitFrame4);

            //bindingShadeThree.setDividerVisible(Utils.showShowDivider());
            bindingShadeFour.setDividerColor(inverseColor);

            final Picasso picasso = Picasso.with(context.getApplicationContext());
            //need to notify ImageShading too, to load image via picasso
            Utils.logError("IMAGE_LOADING : "+" going to load four image");
            if(frameModel.isShouldStoreImages()){
                picasso.load(imageLink1).fit().centerInside().noPlaceholder().into(imageView1, new Callback() {
                    @Override
                    public void onSuccess() {
                        //do nothing
                        Utils.logError("IMAGE_LOADING success");
                    }

                    @Override
                    public void onError() {
                        Utils.logError("IMAGE_LOADING error");
                        picasso.load(imageLink1+"?"+System.currentTimeMillis()).fit().centerInside().noPlaceholder().into(imageView1);
                    }
                });
                picasso.load(imageLink2).fit().centerInside().noPlaceholder().into(imageView2, new Callback() {
                    @Override
                    public void onSuccess() {
                        //do nothing
                        Utils.logError("IMAGE_LOADING success");
                    }

                    @Override
                    public void onError() {
                        Utils.logError("IMAGE_LOADING error");
                        picasso.load(imageLink2+"?"+System.currentTimeMillis()).fit().centerInside().noPlaceholder().into(imageView2);
                    }
                });
                picasso.load(imageLink3).fit().centerInside().noPlaceholder().into(imageView3, new Callback() {
                    @Override
                    public void onSuccess() {
                        //do nothing
                        Utils.logError("IMAGE_LOADING success");
                    }

                    @Override
                    public void onError() {
                        Utils.logError("IMAGE_LOADING error");
                        picasso.load(imageLink3+"?"+System.currentTimeMillis()).fit().centerInside().noPlaceholder().into(imageView3);
                    }
                });
                picasso.load(imageLink4).fit().centerInside().noPlaceholder().into(imageView4, new Callback() {
                    @Override
                    public void onSuccess() {
                        //do nothing
                        Utils.logError("IMAGE_LOADING success");
                    }

                    @Override
                    public void onError() {
                        Utils.logError("IMAGE_LOADING error");
                        picasso.load(imageLink4+"?"+System.currentTimeMillis()).fit().centerInside().noPlaceholder().into(imageView4);
                    }
                });
            }else {
                picasso.load(imageLink1).memoryPolicy(MemoryPolicy.NO_STORE)
                        .networkPolicy(NetworkPolicy.NO_STORE).fit().centerInside().noPlaceholder().into(imageView1, new Callback() {
                    @Override
                    public void onSuccess() {
                        //do nothing
                        Utils.logError("IMAGE_LOADING success");
                    }

                    @Override
                    public void onError() {
                        Utils.logError("IMAGE_LOADING error");
                        picasso.load(imageLink1+"?"+System.currentTimeMillis()).memoryPolicy(MemoryPolicy.NO_STORE)
                                .networkPolicy(NetworkPolicy.NO_STORE).fit().centerInside().noPlaceholder().into(imageView1);
                    }
                });
                picasso.load(imageLink2).memoryPolicy(MemoryPolicy.NO_STORE)
                        .networkPolicy(NetworkPolicy.NO_STORE).fit().centerInside().noPlaceholder().into(imageView2, new Callback() {
                    @Override
                    public void onSuccess() {
                        //do nothing
                        Utils.logError("IMAGE_LOADING success");
                    }

                    @Override
                    public void onError() {
                        Utils.logError("IMAGE_LOADING error");
                        picasso.load(imageLink2+"?"+System.currentTimeMillis()).memoryPolicy(MemoryPolicy.NO_STORE)
                                .networkPolicy(NetworkPolicy.NO_STORE).fit().centerInside().noPlaceholder().into(imageView2);
                    }
                });
                picasso.load(imageLink3).memoryPolicy(MemoryPolicy.NO_STORE)
                        .networkPolicy(NetworkPolicy.NO_STORE).fit().centerInside().noPlaceholder().into(imageView3, new Callback() {
                    @Override
                    public void onSuccess() {
                        //do nothing
                        Utils.logError("IMAGE_LOADING success");
                    }

                    @Override
                    public void onError() {
                        Utils.logError("IMAGE_LOADING error");
                        picasso.load(imageLink3+"?"+System.currentTimeMillis()).memoryPolicy(MemoryPolicy.NO_STORE)
                                .networkPolicy(NetworkPolicy.NO_STORE).fit().centerInside().noPlaceholder().into(imageView3);
                    }
                });
                picasso.load(imageLink4).memoryPolicy(MemoryPolicy.NO_STORE)
                        .networkPolicy(NetworkPolicy.NO_STORE).fit().centerInside().noPlaceholder().into(imageView4, new Callback() {
                    @Override
                    public void onSuccess() {
                        //do nothing
                        Utils.logError("IMAGE_LOADING success");
                    }

                    @Override
                    public void onError() {
                        Utils.logError("IMAGE_LOADING error");
                        picasso.load(imageLink4+"?"+System.currentTimeMillis()).memoryPolicy(MemoryPolicy.NO_STORE)
                                .networkPolicy(NetworkPolicy.NO_STORE).fit().centerInside().noPlaceholder().into(imageView4);
                    }
                });
            }
        }
    }

    @Override
    public void onImageShadeClick(View view) {
        switch((String)view.getTag()){
            case "img1":
                layoutCallback.imageClicked(ImageType.VIEW_MULTIPLE_1, 1, imageLink1);
                break;
            case "img2":
                layoutCallback.imageClicked(ImageType.VIEW_MULTIPLE_1, 2, imageLink2);
                break;
            case "img3":
                layoutCallback.imageClicked(ImageType.VIEW_MULTIPLE_1, 3, imageLink3);
                break;
            case "img4":
                layoutCallback.imageClicked(ImageType.VIEW_MULTIPLE_1, 4, imageLink4);
                break;
        }
    }


    private int[] resultColor = new int[4];

    private void generatePalette(Bitmap bitmap, final int viewId){
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int defaultColor = Color.parseColor("#ffffffff");
                int resultColor = 0;
                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                Palette.Swatch mutedSwatch = palette.getMutedSwatch();
                int vibrantPopulation = vibrantSwatch == null ? 0 : vibrantSwatch.getPopulation();
                int mutedPopulation = mutedSwatch == null ? 0 : mutedSwatch.getPopulation();


                int vibrantColor = palette.getVibrantColor(defaultColor);
                int mutedColor = palette.getMutedColor(defaultColor);

                boolean hasGreaterVibrantPopulation = vibrantPopulation > mutedPopulation;

                switch(frameModel.getColorCombination()){
                    case VIBRANT_TO_MUTED:
                        if(hasGreaterVibrantPopulation)
                            resultColor = vibrantColor;
                        else resultColor = mutedColor;
                        break;
                    case MUTED_TO_VIBRANT:
                        if(hasGreaterVibrantPopulation)
                            resultColor = mutedColor;
                        else resultColor = vibrantColor;
                        break;
                }

                Utils.logMessage("vibrant pop = "+vibrantPopulation+"  muted pop"+mutedPopulation);

                switch(viewId){
                    case 0:
                        bindingShadeFour.setFirstImageBgColor(resultColor);
                        bindingShadeFour.setFirstCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));
                        beanBitFrame1.setMutedColor(mutedColor);
                        beanBitFrame1.setVibrantColor(vibrantColor);
                        beanBitFrame1.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);
                        break;
                    case 1:
                        bindingShadeFour.setSecondImageBgColor(resultColor);
                        bindingShadeFour.setSecondCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));
                        beanBitFrame2.setMutedColor(mutedColor);
                        beanBitFrame2.setVibrantColor(vibrantColor);
                        beanBitFrame2.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);
                        break;
                    case 2:
                        bindingShadeFour.setThirdImageBgColor(resultColor);
                        bindingShadeFour.setThirdCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));
                        beanBitFrame3.setMutedColor(mutedColor);
                        beanBitFrame3.setVibrantColor(vibrantColor);
                        beanBitFrame3.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);
                        break;
                    case 3:
                        bindingShadeFour.setFourthImageBgColor(resultColor);
                        bindingShadeFour.setFourthCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));
                        beanBitFrame4.setMutedColor(mutedColor);
                        beanBitFrame4.setVibrantColor(vibrantColor);
                        beanBitFrame4.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);
                        break;
                }
                ImageShadingFour.this.resultColor[viewId] = resultColor;

                if(ImageShadingFour.this.resultColor[0] == 0 || ImageShadingFour.this.resultColor[1] == 0 || ImageShadingFour.this.resultColor[2] == 0 || ImageShadingFour.this.resultColor[3] == 0) return;

                int mixedColor = Utils.getMixedArgbColor(ImageShadingFour.this.resultColor);
                int inverseColor = Utils.getInverseColor(mixedColor);
                layoutCallback.setColorsToAddMoreView(resultColor, mixedColor, inverseColor);
                layoutCallback.frameResult(beanBitFrame1, beanBitFrame2, beanBitFrame3, beanBitFrame4);

                //bindingShadeFour.setDividerVisible(Utils.showShowDivider());
                bindingShadeFour.setDividerColor(inverseColor);
            }
        });
    }
}
