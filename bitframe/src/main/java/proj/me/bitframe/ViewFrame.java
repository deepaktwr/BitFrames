package proj.me.bitframe;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import proj.me.bitframe.databinding.ItemImageviewBinding;
import proj.me.bitframe.databinding.NoscrollItemImageviewBinding;
import proj.me.bitframe.exceptions.FrameException;
import proj.me.bitframe.helper.ColorCombination;
import proj.me.bitframe.helper.FrameType;
import proj.me.bitframe.helper.Utils;

import static java.lang.Math.round;

/**
 * Created by Deepak.Tiwari on 10-08-2015.
 */
public class ViewFrame extends LinearLayout{
    //can't be a problem in recycle view as it'll be different for different view types by RecycleView
    FrameModel frameModel;

    LayoutInflater layoutInflater;
    RelativeLayout imageContainer;

    BinadingBitFrame binadingBitFrame;

    List<UnframedPicassoTargetNew> targets;
    Picasso currentFramePicasso;

    ImageShading imageShading;

    static class MyImageCallback implements ImageCallback{
        WeakReference<ViewFrame> viewFrameSoftReference;
        WeakReference<FrameCallback> frameCallbackSoftReference;
        List<BeanBitFrame> beanBitFramesResult;
        int linkCount;
        MyImageCallback(ViewFrame viewFrame, FrameCallback frameCallback, int linkCount){
            viewFrameSoftReference = new WeakReference<>(viewFrame);
            frameCallbackSoftReference = new WeakReference<>(frameCallback);
            beanBitFramesResult = new ArrayList<>();
            this.linkCount = linkCount;
        }
        @Override
        public void addImageView(View view, int viewWidth, int viewHeight, boolean isAddInLayout) {
            ViewFrame viewFrame = viewFrameSoftReference.get();
            if(viewFrame == null) return;

            FrameCallback frameCallback = frameCallbackSoftReference.get();
            if(frameCallback == null) return;

            int hashCode = (int) viewFrame.getTag(R.id.frame_tag);
            if(this.hashCode() != hashCode) return;

            viewFrame.binadingBitFrame.setProgressBarVisibility(false);

            viewFrame.imageContainer.addView(view);
            viewFrame.imageContainer.requestLayout();

            frameCallback.containerAdded(viewWidth, viewHeight, isAddInLayout);
        }

        @Override
        public void imageClicked(ImageType imageType, int imagePosition, String imageLink) {
            ViewFrame viewFrame = viewFrameSoftReference.get();
            if(viewFrame == null) return;

            FrameCallback frameCallback = frameCallbackSoftReference.get();
            if(frameCallback == null) return;

            int hashCode = (int) viewFrame.getTag(R.id.frame_tag);
            if(this.hashCode() != hashCode) return;

            Bundle imageBundle = new Bundle();

            ImageScrollParcelable imageScrollParcelable = new ImageScrollParcelable();
            imageScrollParcelable.setImageType(imageType);
            imageScrollParcelable.setImagePosition(imagePosition);
            imageScrollParcelable.setImageLink(imageLink);
            imageBundle.putParcelable("image_data", imageScrollParcelable);

            frameCallback.imageClick(imageType, imagePosition, imageLink);
        }

        @Override
        public void setColorsToAddMoreView(int resultColor, int mixedColor, int invertedColor) {
            ViewFrame viewFrame = viewFrameSoftReference.get();
            if(viewFrame == null) return;

            FrameCallback frameCallback = frameCallbackSoftReference.get();
            if(frameCallback == null) return;

            int hashCode = (int) viewFrame.getTag(R.id.frame_tag);
            if(this.hashCode() != hashCode) return;

            Utils.logVerbose("colors came");
            viewFrame.binadingBitFrame.setProgressBarColor(mixedColor);

            frameCallback.loadedFrameColors(resultColor, mixedColor, invertedColor);
        }

        @Override
        public void frameResult(BeanBitFrame... beanBitFrames) {
            ViewFrame viewFrame = viewFrameSoftReference.get();
            if(viewFrame == null) return;

            FrameCallback frameCallback = frameCallbackSoftReference.get();
            if(frameCallback == null) return;

            int hashCode = (int) viewFrame.getTag(R.id.frame_tag);
            if(this.hashCode() != hashCode) return;

            Utils.logVerbose("frame result came");
            //might be called multiple times
            beanBitFramesResult.addAll(Arrays.asList(beanBitFrames));
            if(linkCount == beanBitFramesResult.size()) frameCallback.frameResult(beanBitFramesResult);
        }

        @Override
        public void addMore() {
            ViewFrame viewFrame = viewFrameSoftReference.get();
            if(viewFrame == null) return;

            FrameCallback frameCallback = frameCallbackSoftReference.get();
            if(frameCallback == null) return;

            int hashCode = (int) viewFrame.getTag(R.id.frame_tag);
            if(this.hashCode() != hashCode) return;

            //when add in layout enabled, then it's click will come in this method
            frameCallback.addMoreClick();
        }
    }


    public ViewFrame(Context context) {
        super(context);
        setOrientation(VERTICAL);
        init((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE), null, 0);
    }

    public ViewFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        init((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE), attrs, 0);
    }

    public ViewFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        init((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE), attrs, defStyleAttr);
    }

    @Override
    public void setOrientation(int orientation) {
        orientation = VERTICAL;
        super.setOrientation(orientation);
    }

    private void init(LayoutInflater layoutInflater, AttributeSet attrs, int defStyleAttr){
        binadingBitFrame = new BinadingBitFrame();
        this.layoutInflater = layoutInflater;
        frameModel = new FrameModel();
        setImageContainer(attrs, defStyleAttr);
        currentFramePicasso = new Picasso.Builder(this.getContext()).build();
    }

    private void setImageContainer(AttributeSet attrs, int defStyleAttr){
        TypedArray typedArray = null;
        Resources resources = null;

        boolean hasScroll = frameModel.isHasScroll();

        if(attrs != null){
            resources = getResources();
            if(defStyleAttr == 0)
                typedArray = this.getContext().obtainStyledAttributes(attrs, R.styleable.ViewFrame);
            else
                typedArray = this.getContext().obtainStyledAttributes(attrs, R.styleable.ViewFrame, defStyleAttr, 0);

            int hasScrollId = typedArray.getResourceId(R.styleable.ViewFrame_hasScroll, -1);
            if(hasScrollId == -1) {
                hasScroll = typedArray.getBoolean(R.styleable.ViewFrame_hasScroll, resources.getBoolean(R.bool.has_scroll));
                hasScrollId = 1;
            }else hasScroll = resources.getBoolean(hasScrollId);
        }

        View view = null;
        if(hasScroll) {
            ItemImageviewBinding itemImageviewBinding = DataBindingUtil.bind(layoutInflater.inflate(
                    R.layout.item_imageview, null));
            itemImageviewBinding.setBitFrame(binadingBitFrame);
            addView(view = itemImageviewBinding.getRoot());
        }else{
            NoscrollItemImageviewBinding noscrollItemImageviewBinding = DataBindingUtil.bind(layoutInflater.inflate(
                    R.layout.noscroll_item_imageview, null));
            noscrollItemImageviewBinding.setBitFrame(binadingBitFrame);
            addView(view = noscrollItemImageviewBinding.getRoot());
        }

        imageContainer = (RelativeLayout)view.findViewById(R.id.parent_relative);

        if(attrs == null) return;

        //getting comment transparency percent
        int commentTransparencyPercent = typedArray.getResourceId(R.styleable.ViewFrame_commentTransparencyPercent, -1);
        if(commentTransparencyPercent == -1)
            commentTransparencyPercent = typedArray.getInteger(R.styleable.ViewFrame_commentTransparencyPercent, -1);
        else
            commentTransparencyPercent = resources.getInteger(commentTransparencyPercent);
        if(commentTransparencyPercent <= 0)
            commentTransparencyPercent = resources.getInteger(R.integer.comment_transparency_percent);

        //getting sort difference threshold
        int sortDifferenceThreshold = typedArray.getResourceId(R.styleable.ViewFrame_sortDifferenceThreshold, -1);
        if(sortDifferenceThreshold == -1)
            sortDifferenceThreshold = typedArray.getInteger(R.styleable.ViewFrame_sortDifferenceThreshold, -1);
        else
            sortDifferenceThreshold = resources.getInteger(sortDifferenceThreshold);
        if(sortDifferenceThreshold <= 0)
            sortDifferenceThreshold = resources.getInteger(R.integer.sort_difference_threshold);

        //getting max frame count
        int maxFrameCount = typedArray.getResourceId(R.styleable.ViewFrame_maxFrameCount, -1);
        if(maxFrameCount == -1)
            maxFrameCount = typedArray.getInteger(R.styleable.ViewFrame_maxFrameCount, -1);
        else
            maxFrameCount = resources.getInteger(maxFrameCount);
        if(maxFrameCount <= 0)
            maxFrameCount = resources.getInteger(R.integer.max_frame_count);

        //min add ratio
        float minAddRatio = typedArray.getResourceId(R.styleable.ViewFrame_minAddRatio, -1);
        if(minAddRatio == -1)
            minAddRatio = typedArray.getFraction(R.styleable.ViewFrame_minAddRatio, 100, 4, -1);
        else
            minAddRatio = resources.getFraction((int)minAddRatio, 100, 4);
        if(minAddRatio <= 0)
            minAddRatio = resources.getFraction(R.fraction.min_add_ratio, 100, 4);

        //error drawable
        int errorDrawable = typedArray.getResourceId(R.styleable.ViewFrame_errorDrawable, R.drawable.ic_launcher);

        //min frame width
        float minFrameWidth = typedArray.getResourceId(R.styleable.ViewFrame_minFrameWidth, -1);
        if(minFrameWidth == -1)
            minFrameWidth = typedArray.getDimension(R.styleable.ViewFrame_minFrameWidth, -1);
        else
            minFrameWidth = resources.getDimension((int)minFrameWidth);
        if(minFrameWidth <= 0)
            minFrameWidth = resources.getDimension(R.dimen.min_frame_width);

        //min frame height
        float minFrameHeight = typedArray.getResourceId(R.styleable.ViewFrame_minFrameHeight, -1);
        if(minFrameHeight == -1)
            minFrameHeight = typedArray.getDimension(R.styleable.ViewFrame_minFrameHeight, -1);
        else
            minFrameHeight = resources.getDimension((int)minFrameHeight);
        if(minFrameHeight <= 0)
            minFrameHeight = resources.getDimension(R.dimen.min_frame_height);

        // max container width
        float maxContainerWidth = typedArray.getResourceId(R.styleable.ViewFrame_maxContainerWidth, -1);
        if(maxContainerWidth == -1)
            maxContainerWidth = typedArray.getDimension(R.styleable.ViewFrame_maxContainerWidth, -1);
        else
            maxContainerWidth = resources.getDimension((int)maxContainerWidth);
        if(maxContainerWidth <= 0)
            maxContainerWidth = resources.getDimension(R.dimen.max_container_width);

        //maxx container height
        float maxContainerHeight = typedArray.getResourceId(R.styleable.ViewFrame_maxContainerHeight, -1);
        if(maxContainerHeight == -1)
            maxContainerHeight = typedArray.getDimension(R.styleable.ViewFrame_maxContainerHeight, -1);
        else
            maxContainerHeight = resources.getDimension((int)maxContainerHeight);
        if(maxContainerHeight <= 0)
            maxContainerHeight = resources.getDimension(R.dimen.max_container_height);

        //is add in layout
        boolean hasAddinLayout = false;
        int isAddInLayout = typedArray.getResourceId(R.styleable.ViewFrame_isAddInLayout, -1);
        if(isAddInLayout == -1) {
            hasAddinLayout = typedArray.getBoolean(R.styleable.ViewFrame_isAddInLayout, resources.getBoolean(R.bool.is_add_in_layout));
            isAddInLayout = 1;
        }else hasAddinLayout = resources.getBoolean(isAddInLayout);

        //should show comment
        boolean shouldShowComment = false;
        int shouldShowCommentId = typedArray.getResourceId(R.styleable.ViewFrame_shouldShowComment, -1);
        if(shouldShowCommentId == -1) {
            shouldShowComment = typedArray.getBoolean(R.styleable.ViewFrame_shouldShowComment, resources.getBoolean(R.bool.should_show_comment));
            shouldShowCommentId = 1;
        }else shouldShowComment = resources.getBoolean(shouldShowCommentId);

        boolean hasFixedDimensions = false;
        int hasFixedDimensionsId = typedArray.getResourceId(R.styleable.ViewFrame_hasFixedDimensions, -1);
        if(hasFixedDimensionsId == -1) {
            hasFixedDimensions = typedArray.getBoolean(R.styleable.ViewFrame_hasFixedDimensions, resources.getBoolean(R.bool.has_fixed_dimensions));
            hasFixedDimensionsId = 1;
        }else hasFixedDimensions = resources.getBoolean(hasFixedDimensionsId);

        boolean shouldStoreImages = false;
        int shouldStoreImagesId = typedArray.getResourceId(R.styleable.ViewFrame_shouldStoreImages, -1);
        if(shouldStoreImagesId == -1) {
            shouldStoreImages = typedArray.getBoolean(R.styleable.ViewFrame_shouldStoreImages, resources.getBoolean(R.bool.should_store_images));
            shouldStoreImagesId = 1;
        }else shouldStoreImages = resources.getBoolean(shouldStoreImagesId);

        boolean shouldRecycleBitmaps = false;
        int shouldRecycleBitmapsId = typedArray.getResourceId(R.styleable.ViewFrame_shouldRecycleBitmaps, -1);
        if(shouldRecycleBitmapsId == -1) {
            shouldRecycleBitmaps = typedArray.getBoolean(R.styleable.ViewFrame_shouldRecycleBitmaps, resources.getBoolean(R.bool.has_fixed_dimensions));
            shouldRecycleBitmapsId = 1;
        }else shouldRecycleBitmaps = resources.getBoolean(shouldRecycleBitmapsId);

        boolean shouldSortImages = false;
        int shouldSortImagesId = typedArray.getResourceId(R.styleable.ViewFrame_shouldSortImages, -1);
        if(shouldSortImagesId == -1) {
            shouldSortImages = typedArray.getBoolean(R.styleable.ViewFrame_shouldSortImages, resources.getBoolean(R.bool.should_sort_images));
            shouldSortImagesId = 1;
        }else shouldSortImages = resources.getBoolean(shouldSortImagesId);


        //color combination
        String colorCombinationString = "VIBRANT_TO_MUTED";
        if(typedArray.hasValue(R.styleable.ViewFrame_colorCombination))
            colorCombinationString = typedArray.getString(R.styleable.ViewFrame_colorCombination);
        ColorCombination colorCombination = ColorCombination.valueOf(colorCombinationString);

        //image scale type
        int  imageScaleTypeString = 6;
        if(typedArray.hasValue(R.styleable.ViewFrame_imageScaleType))
            imageScaleTypeString = typedArray.getInt(R.styleable.ViewFrame_imageScaleType, 6);
        ImageView.ScaleType imageScaleType = ImageView.ScaleType.CENTER_CROP;
        switch(imageScaleTypeString){
            case 0:
                imageScaleType = ImageView.ScaleType.MATRIX;
                break;
            case 1:
                imageScaleType = ImageView.ScaleType.FIT_XY;
                break;
            case 2:
                imageScaleType = ImageView.ScaleType.FIT_START;
                break;
            case 3:
                imageScaleType = ImageView.ScaleType.FIT_CENTER;
                break;
            case 4:
                imageScaleType = ImageView.ScaleType.FIT_END;
                break;
            case 5:
                imageScaleType = ImageView.ScaleType.CENTER;
                break;
            case 6:
                imageScaleType = ImageView.ScaleType.CENTER_CROP;
                break;
            case 7:
                imageScaleType = ImageView.ScaleType.CENTER_INSIDE;
                break;
        }

        typedArray.recycle();

        //setting values to frame model
        frameModel.setCommentTransparencyPercent(commentTransparencyPercent);
        frameModel.setSortDifferenceThreshold(sortDifferenceThreshold);


        //first convert width and height to pixel
        frameModel.setMinFrameWidth(Utils.dpToPx(minFrameWidth, resources));
        frameModel.setMinFrameHeight(Utils.dpToPx(minFrameHeight, resources));
        frameModel.setMaxContainerWidth(Utils.dpToPx(maxContainerWidth, resources));
        frameModel.setMaxContainerHeight(Utils.dpToPx(maxContainerHeight, resources));


        frameModel.setHasScroll(hasScroll);
        frameModel.setMaxFrameCount(maxFrameCount);
        frameModel.setMinAddRatio(minAddRatio);
        frameModel.setAddInLayout(hasAddinLayout);
        frameModel.setShouldShowComment(shouldShowComment);
        frameModel.setHasFixedDimensions(hasFixedDimensions);
        frameModel.setShouldSortImages(shouldSortImages);
        frameModel.setColorCombination(colorCombination);
        frameModel.setErrorDrawable(errorDrawable);
        frameModel.setScaleType(imageScaleType);
        frameModel.setShouldRecycleBitmaps(shouldRecycleBitmaps);
        frameModel.setShouldStoreImages(shouldStoreImages);
    }

    /**
     * set frame min and max dimentions
     * @param minFrameWidth will go as min width of a single frame
     * @param minFrameHeight will go as min height of a single frame
     * @param maxContainerWidth will go as max width of the frame container
     * @param maxContainerHeight will go as max height of the frame container
     * */
    public void setFrameDimensions(int minFrameWidth, int minFrameHeight, int maxContainerWidth, int maxContainerHeight){
        frameModel.setMinFrameWidth(minFrameWidth % 2 == 0 ? minFrameWidth : minFrameWidth + 1);
        frameModel.setMinFrameHeight(minFrameHeight % 2 == 0 ? minFrameHeight : minFrameHeight + 1);
        frameModel.setMaxContainerWidth(maxContainerWidth % 2 == 0 ? maxContainerWidth : maxContainerWidth);
        frameModel.setMaxContainerHeight(maxContainerHeight % 2 == 0 ? maxContainerHeight : maxContainerHeight + 1);
    }
    /**
     * it'll set the container's width and height fixed and frame will be managed according to that
     * @param hasFixedDimensions need to set fixed dimension for container as given in max width and height
     *                           default is false
     * */
    public void setHasFixedDimensions(boolean hasFixedDimensions){
        frameModel.setHasFixedDimensions(hasFixedDimensions);
    }

    /**
     * to generate the bit frame based on a list of images.
     * @param beanImageList the list of image uri's and the notes on that image by user, it can be a list of @BeanImages
     *                      if you don't have image dimension and colors otherwise you can pass @BeanBitFrame
     * @param frameCallback callback which will notify the completion and will give required attributes
     * @param frameType if the images doesn't have their pallet colors and dimensions then set this to true,
     *                  or if all the images are local.
     * */
    public void showBitFrame(List<BeanImage> beanImageList, FrameCallback frameCallback, FrameType frameType){
        //precondition minWidth or minHeight should be less than half of max width or max height respectively
        if(frameCallback == null || beanImageList == null || beanImageList.size() == 0){
            Utils.logVerbose("list and callback both are required and must not have size 0");
            return;
        }
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int heightPixels = getResources().getDisplayMetrics().heightPixels;

        if(frameModel.getMaxContainerHeight() <= 0 || frameModel.getMaxContainerWidth() <= 0 || frameModel.getMaxContainerHeight() > heightPixels || frameModel.getMaxContainerWidth() > widthPixels){
            Utils.logVerbose("container max height or width should not greater than the device dimensions "+
            Utils.formatMessage("device width pixels %d and device height pixels %d", widthPixels, heightPixels));
            int maxW = round(widthPixels - widthPixels * 0.04f);
            int maxH = round(maxW + maxW * 0.15f > heightPixels ? maxW : maxW + maxW * 0.15f);

            maxW = maxW % 2 == 0 ? maxW : maxW + 1;
            maxH = maxH % 2 == 0 ? maxH : maxH + 1;

            frameModel.setMaxContainerWidth(maxW);
            frameModel.setMaxContainerHeight(maxH);

            Utils.logMessage(Utils.formatMessage("setting max container width to %d and max container height to %d",
                    maxW, maxH));
        }
        if(frameModel.getMinFrameWidth() <= 0 || frameModel.getMinFrameHeight() <= 0f || frameModel.getMinFrameWidth() * 2 >= frameModel.getMaxContainerWidth() ||
                frameModel.getMinFrameHeight() * 2 >= frameModel.getMaxContainerHeight()){
            Utils.logVerbose("frame min width or height must be less than half of the container width or height respectivally, " +
                    "you may call @setFrameDimentions to set those explicitly");

            int maxW = round(frameModel.getMaxContainerWidth());
            int maxH = round(frameModel.getMaxContainerHeight());
            int minW = round(maxW * 0.30f);
            int minH = round(maxH * 0.30f);

            minW = minW % 2 == 0 ? minW : minW + 1;
            minH = minH % 2 == 0 ? minH : minH + 1;

            frameModel.setMinFrameWidth(minW);
            frameModel.setMinFrameHeight(minH);


            Utils.logMessage(Utils.formatMessage("device width pixels are %d and height pixels are %d," +
                    " setting min frame width to %d and min frame height to %d",
                    widthPixels, heightPixels, minW, minH));
        }
        if(frameModel.getMaxFrameCount() > 4){
            frameModel.setMaxFrameCount(4);
            Utils.logVerbose("max supported frames are 4 for now");
            Utils.logMessage("setting max frames to 4");
        }
        if(frameModel.getMaxFrameCount() <= 0) {
            frameModel.setMaxFrameCount(1);
            Utils.logVerbose("container must have at-least one frame");
            Utils.logMessage("setting max frames to 1");
        }
        if(frameModel.isAddInLayout() && frameModel.getMinAddRatio() > 0.50 || frameModel.getMinAddRatio() < 0.15){
            Utils.logVerbose("min add ratio must be less than 50 percent i.e 0.50 so that min width or height could not get smaller" +
                    " than that and it must be greater than 15 percent (which will go 15 percent of max width or max height) " +
                    "so that it itself doesn't look too small");
            frameModel.setMinAddRatio(0.25f);

            Utils.logMessage("setting min ad ratio to 25 percent (0.25)");
        }

        MyImageCallback imageCallback = new MyImageCallback(this, frameCallback, beanImageList.size());
        setTag(R.id.frame_tag, imageCallback.hashCode());

        imageShading = new ImageShading(this.getContext(), imageCallback, frameModel, currentFramePicasso);

        binadingBitFrame.setProgressBarVisibility(true);
        imageContainer.removeAllViews();

        switch(frameType){
            case UNFRAMED:
                if(targets == null) targets = new ArrayList<>();
                else{
                    for(UnframedPicassoTargetNew target : targets){
                        currentFramePicasso.cancelRequest(target);
                    }
                    targets.clear();
                }
                imageShading.mapUnframedImages(beanImageList, targets);
                break;
            case FRAMED:
                try {
                    imageShading.mapFramedImages(beanImageList);
                } catch (FrameException e) {
                    e.printStackTrace();
                }
                break;
            default: Utils.logError("invalid frame type");
        }

    }

    public void clearContainerChilds(){
        Utils.logVerbose("clearing views");
        Utils.unbindDrawables(imageContainer, false, frameModel.isShouldRecycleBitmaps());

    }

    /**came to
     * A new #Picasso instance has been created for every frame.
     * It should not be shutdown during frame creation otherwise it will throw #IllegalStateException on loading images.
     * You should use this instance to load other uri's instead of creating your own instance, though the cancelling of
     * requests should be done using picasso request tags, otherwise frame requests could also get cancelled.
     * */
    public Picasso getPicassoInstance(){
        return currentFramePicasso;
    }

    public void destroyFrame(){
        Utils.logVerbose("view detached");
        Utils.unbindDrawables(this, true, frameModel.isShouldRecycleBitmaps());

        layoutInflater = null;
        imageContainer = null;
        binadingBitFrame = null;
        frameModel = null;
        if(targets != null) {
            for (UnframedPicassoTargetNew target : targets) {
                currentFramePicasso.cancelRequest(target);
                target = null;
            }
            targets.clear();
        }
        currentFramePicasso.shutdown();
        currentFramePicasso = null;
        targets = null;
    }

}
