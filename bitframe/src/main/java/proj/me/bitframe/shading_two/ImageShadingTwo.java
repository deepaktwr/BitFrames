package proj.me.bitframe.shading_two;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
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
import proj.me.bitframe.ImageShades;
import proj.me.bitframe.ImageType;
import proj.me.bitframe.R;
import proj.me.bitframe.databinding.ViewDoubleHorzBinding;
import proj.me.bitframe.databinding.ViewDoubleVertBinding;
import proj.me.bitframe.dimentions.BeanShade2;
import proj.me.bitframe.dimentions.ImageOrder;
import proj.me.bitframe.dimentions.LayoutType;
import proj.me.bitframe.helper.Utils;

/**
 * Created by Deepak.Tiwari on 29-09-2015.
 */
 public final class ImageShadingTwo extends ImageShades {

    LayoutInflater inflater;
    Context context;
    int totalImages;

    BindingShadeTwo bindingShadeTwo;

    String imageLink1, imageLink2;

    BeanBitFrame beanBitFrame1, beanBitFrame2;

    FrameModel frameModel;

    public ImageShadingTwo(Context context, int totalImages, FrameModel frameModel){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.totalImages = totalImages;
        bindingShadeTwo = new BindingShadeTwo();

        beanBitFrame1 = new BeanBitFrame();
        beanBitFrame2 = new BeanBitFrame();
        beanBitFrame1.setLoaded(true);
        beanBitFrame2.setLoaded(true);

        this.frameModel = frameModel;
    }

    @Override
    protected void updateFrameUi(List<Bitmap> images, List<BeanImage> beanImages, boolean hasImageProperties){
        BeanBitFrame beanBitFrameFirst = null, beanBitFrameSecond = null;
        if(hasImageProperties){
            beanBitFrameFirst = (BeanBitFrame) beanImages.get(0);
            beanBitFrameSecond = (BeanBitFrame) beanImages.get(1);
        }

        int width1 = hasImageProperties ? (int) beanBitFrameFirst.getWidth() : images.get(0).getWidth();
        int height1 = hasImageProperties ? (int) beanBitFrameFirst.getHeight() :  images.get(0).getHeight();

        int width2 = hasImageProperties ? (int) beanBitFrameSecond.getWidth() : images.get(1).getWidth();
        int height2 = hasImageProperties ? (int) beanBitFrameSecond.getHeight() :  images.get(1).getHeight();


        BeanShade2 beanShade2 = ShadeTwo.calculateDimentions(frameModel, width1, height1, width2, height2);

        boolean isFirstImageLeftOrTop = beanShade2.getImageOrderList().get(0) == ImageOrder.FIRST;
        boolean isVertLayout = beanShade2.getLayoutType() == LayoutType.VERT;


        Utils.logMessage("getWidth1 : "+width1);
        Utils.logMessage("getHeight1 : "+height1);
        Utils.logMessage("getWidth2 : "+width2);
        Utils.logMessage("getHeight2 : "+height2);

        Utils.logMessage("Start++++++++++++++++++++++++++++++++++++Start");
        Utils.logMessage("getWidth1 : "+beanShade2.getWidth1());
        Utils.logMessage("getHeight1 : "+beanShade2.getHeight1());
        Utils.logMessage("getWidth2 : "+beanShade2.getWidth2());
        Utils.logMessage("getHeight2 : "+beanShade2.getHeight2());
        Utils.logMessage("isFirstImageLeftOrTop : "+isFirstImageLeftOrTop);
        Utils.logMessage("isVertLayout : "+isVertLayout);
        Utils.logMessage("End++++++++++++++++++++++++++++++++++++End");

        View root = null;

        if(isVertLayout){
            ViewDoubleVertBinding viewDoubleVertBinding = DataBindingUtil.bind(inflater.inflate(R.layout.view_double_vert, null));
            viewDoubleVertBinding.setShadeTwo(bindingShadeTwo);
            viewDoubleVertBinding.setClickHandler(this);
            root = viewDoubleVertBinding.getRoot();
        }else{
            ViewDoubleHorzBinding viewDoubleHorzBinding = DataBindingUtil.bind(inflater.inflate(R.layout.view_double_horz, null));
            viewDoubleHorzBinding.setShadeTwo(bindingShadeTwo);
            viewDoubleHorzBinding.setClickHandler(this);
            root = viewDoubleHorzBinding.getRoot();
        }

        Bitmap bitmap1 = hasImageProperties ? null : images.get(0);
        Bitmap bitmap2 = hasImageProperties ? null : images.get(1);

        boolean isAddViewVisible = false;

        final ImageView imageView1 = (ImageView) root.findViewById(R.id.view_double_image1);
        final ImageView imageView2 = (ImageView) root.findViewById(R.id.view_double_image2);

        BindingShadeTwo.setLayoutHeight(imageView1, beanShade2.getHeight1());
        BindingShadeTwo.setLayoutWidth(imageView1, beanShade2.getWidth1());
        if(!hasImageProperties) {
            generatePalette(isFirstImageLeftOrTop ? bitmap1 : bitmap2, 1);
            BindingShadeTwo.setBitmap(imageView1, isFirstImageLeftOrTop ? bitmap1 : bitmap2);
        }else{
            //set bean properties
            int resultColor = 0;
            beanBitFrame1.setHasGreaterVibrantPopulation(isFirstImageLeftOrTop ? beanBitFrameFirst.isHasGreaterVibrantPopulation() : beanBitFrameSecond.isHasGreaterVibrantPopulation());
            beanBitFrame1.setMutedColor(isFirstImageLeftOrTop ? beanBitFrameFirst.getMutedColor() : beanBitFrameSecond.getMutedColor());
            beanBitFrame1.setVibrantColor(isFirstImageLeftOrTop ? beanBitFrameFirst.getVibrantColor() : beanBitFrameSecond.getVibrantColor());

            switch(frameModel.getColorCombination()){
                case VIBRANT_TO_MUTED:
                    if(beanBitFrame1.isHasGreaterVibrantPopulation())
                        resultColor = beanBitFrame1.getVibrantColor();
                    else resultColor = beanBitFrame1.getMutedColor();
                    break;
                case MUTED_TO_VIBRANT:
                    if(beanBitFrame1.isHasGreaterVibrantPopulation())
                        resultColor = beanBitFrame1.getMutedColor();
                    else resultColor = beanBitFrame1.getVibrantColor();
                    break;
            }

            bindingShadeTwo.setFirstImageBgColor(resultColor);
            bindingShadeTwo.setFirstCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));
            beanBitFrame1.setPrimaryCount(isFirstImageLeftOrTop ? beanBitFrameFirst.getPrimaryCount() : beanBitFrameSecond.getPrimaryCount());
            beanBitFrame1.setSecondaryCount(isFirstImageLeftOrTop ? beanBitFrameFirst.getSecondaryCount() : beanBitFrameSecond.getSecondaryCount());
        }

        BindingShadeTwo.setLayoutWidth(imageView2, beanShade2.getWidth2());
        BindingShadeTwo.setLayoutHeight(imageView2, beanShade2.getHeight2());

        if(!hasImageProperties) {
            generatePalette(isFirstImageLeftOrTop ? bitmap2 : bitmap1, 2);
            BindingShadeTwo.setBitmap(imageView2, isFirstImageLeftOrTop ? bitmap2 : bitmap1);
        }else{
            //set bean properties
            int resultColor = 0;
            beanBitFrame2.setHasGreaterVibrantPopulation(isFirstImageLeftOrTop ? beanBitFrameSecond.isHasGreaterVibrantPopulation() : beanBitFrameFirst.isHasGreaterVibrantPopulation());
            beanBitFrame2.setMutedColor(isFirstImageLeftOrTop ? beanBitFrameSecond.getMutedColor() : beanBitFrameFirst.getMutedColor());
            beanBitFrame2.setVibrantColor(isFirstImageLeftOrTop ? beanBitFrameSecond.getVibrantColor() : beanBitFrameFirst.getVibrantColor());

            switch(frameModel.getColorCombination()){
                case VIBRANT_TO_MUTED:
                    if(beanBitFrame2.isHasGreaterVibrantPopulation())
                        resultColor = beanBitFrame2.getVibrantColor();
                    else resultColor = beanBitFrame2.getMutedColor();
                    break;
                case MUTED_TO_VIBRANT:
                    if(beanBitFrame2.isHasGreaterVibrantPopulation())
                        resultColor = beanBitFrame2.getMutedColor();
                    else resultColor = beanBitFrame2.getVibrantColor();
                    break;
            }

            bindingShadeTwo.setSecondImageBgColor(resultColor);
            bindingShadeTwo.setSecondCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));
            beanBitFrame2.setPrimaryCount(isFirstImageLeftOrTop ? beanBitFrameSecond.getPrimaryCount() : beanBitFrameFirst.getPrimaryCount());
            beanBitFrame2.setSecondaryCount(isFirstImageLeftOrTop ? beanBitFrameSecond.getSecondaryCount() : beanBitFrameFirst.getSecondaryCount());
        }

        bindingShadeTwo.setAddVisibility(true);

        if(beanShade2.isAddInLayout()) {
            if (totalImages > 2) {
                bindingShadeTwo.setAddAsCounter(true);
                bindingShadeTwo.setAddText("+" + (totalImages - 2));
                //text color
                //bg
            } else {
                bindingShadeTwo.setAddAsCounter(false);
                bindingShadeTwo.setAddText("+");
                //text color
                //bg
                isAddViewVisible = true;
            }
        }else {
            if (totalImages > 2) {
                bindingShadeTwo.setCounterVisibility(true);
                bindingShadeTwo.setCounterText("+" + (totalImages - 2));
            }
        }

        bindingShadeTwo.setFirstImageScaleType(frameModel.getScaleType());
        //bg
        bindingShadeTwo.setSecondImageScaleType(frameModel.getScaleType());
        //bg

        bindingShadeTwo.setFirstComment(isFirstImageLeftOrTop ? beanImages.get(0).getImageComment() : beanImages.get(1).getImageComment());
        bindingShadeTwo.setFirstCommentVisibility(frameModel.isShouldShowComment());
        //bg
        bindingShadeTwo.setSecondComment(isFirstImageLeftOrTop ? beanImages.get(1).getImageComment() : beanImages.get(0).getImageComment());
        bindingShadeTwo.setSecondCommentVisibility(frameModel.isShouldShowComment());
        //bg

        //no need to add or remove from list

        /*imageLink1 = isFirstImageLeftOrTop ? beanImages.get(0).getImageLink() : beanImages.get(1).getImageLink();
        imageLink2 = isFirstImageLeftOrTop ? beanImages.get(1).getImageLink() : beanImages.get(0).getImageLink();*/

        BeanImage beanImage1 = null;
        BeanImage beanImage2 = null;
        if(isFirstImageLeftOrTop){
            beanImage1 = beanImages.get(0);
            beanImage2 = beanImages.get(1);
        }else{
            beanImage1 = beanImages.get(1);
            beanImage2 = beanImages.get(0);
        }

        imageLink1 = beanImage1.getImageLink();
        int firstPrimaryCount = beanImage1.getPrimaryCount();
        int firstSecondaryCount = beanImage1.getSecondaryCount();

        imageLink2 = beanImage2.getImageLink();
        int secondPrimaryCount = beanImage2.getPrimaryCount();
        int secondSecondaryCount = beanImage2.getSecondaryCount();

        if(isVertLayout) addImageView(root, isFirstImageLeftOrTop ? beanShade2.getWidth1() : beanShade2.getWidth2(),
                    beanShade2.getHeight1() + beanShade2.getHeight2(), isAddViewVisible);
        else addImageView(root, isFirstImageLeftOrTop ? beanShade2.getHeight1() : beanShade2.getHeight2(),
                    beanShade2.getWidth1() + beanShade2.getWidth2(), isAddViewVisible);

        beanBitFrame1.setWidth(/*beanShade2.getWidth1()*/hasImageProperties ? (isFirstImageLeftOrTop ? beanBitFrameFirst.getWidth() : beanBitFrameSecond.getWidth()) : isFirstImageLeftOrTop ? bitmap1.getWidth() : bitmap2.getWidth());
        beanBitFrame1.setHeight(/*beanShade2.getHeight1()*/hasImageProperties ? (isFirstImageLeftOrTop ? beanBitFrameFirst.getHeight() : beanBitFrameSecond.getHeight()) : isFirstImageLeftOrTop ? bitmap1.getHeight() : bitmap2.getHeight());
        beanBitFrame1.setImageLink(imageLink1);
        beanBitFrame1.setImageComment(bindingShadeTwo.getFirstComment());
        beanBitFrame1.setPrimaryCount(firstPrimaryCount);
        beanBitFrame1.setSecondaryCount(firstSecondaryCount);

        beanBitFrame2.setWidth(/*beanShade2.getWidth2()*/hasImageProperties ? (isFirstImageLeftOrTop ? beanBitFrameSecond.getWidth() : beanBitFrameFirst.getWidth()) : isFirstImageLeftOrTop ? bitmap2.getWidth() : bitmap1.getWidth());
        beanBitFrame2.setHeight(/*beanShade2.getHeight2()*/hasImageProperties ? (isFirstImageLeftOrTop ? beanBitFrameSecond.getHeight() : beanBitFrameFirst.getHeight()) : isFirstImageLeftOrTop ? bitmap2.getHeight() : bitmap1.getHeight());
        beanBitFrame2.setImageLink(imageLink2);
        beanBitFrame2.setImageComment(bindingShadeTwo.getSecondComment());
        beanBitFrame2.setPrimaryCount(secondPrimaryCount);
        beanBitFrame2.setSecondaryCount(secondSecondaryCount);

        if(hasImageProperties){
            //extra properties
            int defaultColor = Color.parseColor("#ffffffff");
            int commentColor = Color.parseColor("#33000000");

            int mixedColor = Utils.getMixedArgbColor(bindingShadeTwo.getFirstImageBgColor(), bindingShadeTwo.getSecondImageBgColor());
            int inverseColor = Utils.getInverseColor(mixedColor);

            setColorsToAddMoreView(bindingShadeTwo.getSecondImageBgColor(), mixedColor, inverseColor);
            frameResult(beanBitFrame1, beanBitFrame2);

            //bindingShadeTwo.setDividerVisible(Utils.showShowDivider());
            bindingShadeTwo.setDividerColor(inverseColor);



            if(bindingShadeTwo.isAddVisibility()){
                if(bindingShadeTwo.isAddAsCounter()){
                    bindingShadeTwo.setAddTextColor(defaultColor);
                    bindingShadeTwo.setAddBgColor(commentColor);
                }else{
                    bindingShadeTwo.setAddBgColor(mixedColor);
                    bindingShadeTwo.setAddTextColor(inverseColor);
                }
            }

            final Picasso picasso = Picasso.with(context.getApplicationContext());
            //need to notify ImageShading too, to load image via picasso
            Utils.logError("IMAGE_LOADING : "+" going to load two image");
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
            }
        }
    }
    @Override
    public void onImageShadeClick(View view) {
        switch((String)view.getTag()){
            case "img1":
                imageClicked(ImageType.VIEW_DOUBLE, 1, imageLink1);
                break;
            case "img2":
                imageClicked(ImageType.VIEW_DOUBLE, 2, imageLink2);
                break;
            case "add":
                addMore();
                break;
        }
    }

    private int resultColor;
    private void generatePalette(Bitmap bitmap, final int viewId){
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int defaultColor = Color.parseColor("#ffffffff");
                int commentColor = Color.parseColor("#33000000");
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
                    case 1:
                        bindingShadeTwo.setFirstImageBgColor(resultColor);
                        bindingShadeTwo.setFirstCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));
                        beanBitFrame1.setMutedColor(mutedColor);
                        beanBitFrame1.setVibrantColor(vibrantColor);
                        beanBitFrame1.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);
                        break;
                    case 2:
                        bindingShadeTwo.setSecondImageBgColor(resultColor);
                        bindingShadeTwo.setSecondCommentBgColor(Utils.getColorWithTransparency(resultColor, frameModel.getCommentTransparencyPercent()));
                        beanBitFrame2.setMutedColor(mutedColor);
                        beanBitFrame2.setVibrantColor(vibrantColor);
                        beanBitFrame2.setHasGreaterVibrantPopulation(hasGreaterVibrantPopulation);
                        break;
                }

                if(ImageShadingTwo.this.resultColor == 0) ImageShadingTwo.this.resultColor = resultColor;
                else {
                    int mixedColor = Utils.getMixedArgbColor(ImageShadingTwo.this.resultColor, resultColor);
                    int inverseColor = Utils.getInverseColor(mixedColor);
                    setColorsToAddMoreView(resultColor, mixedColor, inverseColor);
                    frameResult(beanBitFrame1, beanBitFrame2);

                    //bindingShadeTwo.setDividerVisible(Utils.showShowDivider());
                    bindingShadeTwo.setDividerColor(inverseColor);



                    if(bindingShadeTwo.isAddVisibility()){
                        if(bindingShadeTwo.isAddAsCounter()){
                            bindingShadeTwo.setAddTextColor(defaultColor);
                            bindingShadeTwo.setAddBgColor(commentColor);
                        }else{
                            bindingShadeTwo.setAddBgColor(mixedColor);
                            bindingShadeTwo.setAddTextColor(inverseColor);
                        }
                    }
                }
            }
        });
    }
}
