package proj.me.bitframe;

import android.widget.ImageView;

import proj.me.bitframe.helper.ColorCombination;

/**
 * Created by root on 18/4/16.
 */
public class FrameModel {
    int commentTransparencyPercent;
    int sortDifferenceThreshold;
    float minFrameWidth;
    float minFrameHeight;
    float maxContainerWidth;
    float maxContainerHeight;
    int maxFrameCount;
    float minAddRatio;
    boolean isAddInLayout;
    boolean hasScroll;
    boolean shouldShowComment;
    boolean hasFixedDimensions;
    boolean shouldSortImages;
    ColorCombination colorCombination;
    ImageView.ScaleType scaleType;
    int errorDrawable;
    boolean shouldRecycleBitmaps;
    boolean shouldStoreImages;

    public boolean isShouldStoreImages() {
        return shouldStoreImages;
    }

    public void setShouldStoreImages(boolean shouldStoreImages) {
        this.shouldStoreImages = shouldStoreImages;
    }

    public boolean isShouldRecycleBitmaps() {
        return shouldRecycleBitmaps;
    }

    public void setShouldRecycleBitmaps(boolean shouldRecycleBitmaps) {
        this.shouldRecycleBitmaps = shouldRecycleBitmaps;
    }

    public boolean isHasScroll() {
        return hasScroll;
    }

    public void setHasScroll(boolean hasScroll) {
        this.hasScroll = hasScroll;
    }

    public boolean isShouldSortImages() {
        return shouldSortImages;
    }

    public void setShouldSortImages(boolean shouldSortImages) {
        this.shouldSortImages = shouldSortImages;
    }

    public int getCommentTransparencyPercent() {
        return commentTransparencyPercent;
    }

    public void setCommentTransparencyPercent(int commentTransparencyPercent) {
        this.commentTransparencyPercent = commentTransparencyPercent;
    }

    public int getSortDifferenceThreshold() {
        return sortDifferenceThreshold;
    }

    public void setSortDifferenceThreshold(int sortDifferenceThreshold) {
        this.sortDifferenceThreshold = sortDifferenceThreshold;
    }

    public boolean isHasFixedDimensions() {
        return hasFixedDimensions;
    }

    public void setHasFixedDimensions(boolean hasFixedDimensions) {
        this.hasFixedDimensions = hasFixedDimensions;
    }

    public float getMinFrameWidth() {
        return minFrameWidth;
    }

    public void setMinFrameWidth(float minFrameWidth) {
        this.minFrameWidth = minFrameWidth;
    }

    public float getMinFrameHeight() {
        return minFrameHeight;
    }

    public void setMinFrameHeight(float minFrameHeight) {
        this.minFrameHeight = minFrameHeight;
    }

    public float getMaxContainerWidth() {
        return maxContainerWidth;
    }

    public void setMaxContainerWidth(float maxContainerWidth) {
        this.maxContainerWidth = maxContainerWidth;
    }

    public float getMaxContainerHeight() {
        return maxContainerHeight;
    }

    public void setMaxContainerHeight(float maxContainerHeight) {
        this.maxContainerHeight = maxContainerHeight;
    }

    public int getMaxFrameCount() {
        return maxFrameCount;
    }

    public void setMaxFrameCount(int maxFrameCount) {
        this.maxFrameCount = maxFrameCount;
    }

    public float getMinAddRatio() {
        return minAddRatio;
    }

    public void setMinAddRatio(float minAddRatio) {
        this.minAddRatio = minAddRatio;
    }

    public boolean isAddInLayout() {
        return isAddInLayout;
    }

    public void setAddInLayout(boolean addInLayout) {
        isAddInLayout = addInLayout;
    }

    public boolean isShouldShowComment() {
        return shouldShowComment;
    }

    public void setShouldShowComment(boolean shouldShowComment) {
        this.shouldShowComment = shouldShowComment;
    }

    public ColorCombination getColorCombination() {
        return colorCombination;
    }

    public void setColorCombination(ColorCombination colorCombination) {
        this.colorCombination = colorCombination;
    }

    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
    }

    public int getErrorDrawable() {
        return errorDrawable;
    }

    public void setErrorDrawable(int errorDrawable) {
        this.errorDrawable = errorDrawable;
    }
}
