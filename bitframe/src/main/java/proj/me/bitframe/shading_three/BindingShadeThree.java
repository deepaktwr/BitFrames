package proj.me.bitframe.shading_three;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import proj.me.bitframe.BR;

/**
 * Created by root on 16/3/16.
 */
public class BindingShadeThree extends BaseObservable{
    private int dividerColor;
    private boolean dividerVisible;

    private int firstImageBgColor;
    private int firstCommentBgColor;
    private String firstComment;
    private boolean firstCommentVisibility;
    private ImageView.ScaleType firstImageScaleType;

    private int secondImageBgColor;
    private int secondCommentBgColor;
    private String secondComment;
    private boolean secondCommentVisibility;
    private ImageView.ScaleType secondImageScaleType;

    private int thirdImageBgColor;
    private int thirdCommentBgColor;
    private String thirdComment;
    private boolean thirdCommentVisibility;
    private ImageView.ScaleType thirdImageScaleType;

    private String counterText;
    private boolean counterVisibility;

    @Bindable public boolean isDividerVisible() {
        return dividerVisible;
    }

    public void setDividerVisible(boolean dividerVisible) {
        this.dividerVisible = dividerVisible;
        notifyPropertyChanged(BR.dividerVisible);
    }

    @Bindable public int getDividerColor() {
        return dividerColor;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
		notifyPropertyChanged(BR.dividerColor);
    }

    @Bindable public int getFirstImageBgColor() {
        return firstImageBgColor;
    }

    public void setFirstImageBgColor(int firstImageBgColor) {
        this.firstImageBgColor = firstImageBgColor;
		notifyPropertyChanged(BR.firstImageBgColor);
    }

    @Bindable public int getFirstCommentBgColor() {
        return firstCommentBgColor;
    }

    public void setFirstCommentBgColor(int firstCommentBgColor) {
        this.firstCommentBgColor = firstCommentBgColor;
		notifyPropertyChanged(BR.firstCommentBgColor);
    }

    @Bindable public String getFirstComment() {
        return firstComment;
    }

    public void setFirstComment(String firstComment) {
        this.firstComment = firstComment;
		notifyPropertyChanged(BR.firstComment);
    }

    @Bindable public boolean isFirstCommentVisibility() {
        return firstCommentVisibility;
    }

    public void setFirstCommentVisibility(boolean firstCommentVisibility) {
        this.firstCommentVisibility = firstCommentVisibility;
		notifyPropertyChanged(BR.firstCommentVisibility);
    }

    @Bindable public ImageView.ScaleType getFirstImageScaleType() {
        return firstImageScaleType;
    }

    public void setFirstImageScaleType(ImageView.ScaleType firstImageScaleType) {
        this.firstImageScaleType = firstImageScaleType;
		notifyPropertyChanged(BR.firstImageScaleType);
    }

    @Bindable public int getSecondImageBgColor() {
        return secondImageBgColor;
    }

    public void setSecondImageBgColor(int secondImageBgColor) {
        this.secondImageBgColor = secondImageBgColor;
		notifyPropertyChanged(BR.secondImageBgColor);
    }

    @Bindable public int getSecondCommentBgColor() {
        return secondCommentBgColor;
    }

    public void setSecondCommentBgColor(int secondCommentBgColor) {
        this.secondCommentBgColor = secondCommentBgColor;
		notifyPropertyChanged(BR.secondCommentBgColor);
    }

    @Bindable public String getSecondComment() {
        return secondComment;
    }

    public void setSecondComment(String secondComment) {
        this.secondComment = secondComment;
		notifyPropertyChanged(BR.secondComment);
    }

    @Bindable public boolean isSecondCommentVisibility() {
        return secondCommentVisibility;
    }

    public void setSecondCommentVisibility(boolean secondCommentVisibility) {
        this.secondCommentVisibility = secondCommentVisibility;
		notifyPropertyChanged(BR.secondCommentVisibility);
    }

    @Bindable public ImageView.ScaleType getSecondImageScaleType() {
        return secondImageScaleType;
    }

    public void setSecondImageScaleType(ImageView.ScaleType secondImageScaleType) {
        this.secondImageScaleType = secondImageScaleType;
		notifyPropertyChanged(BR.secondImageScaleType);
    }

    @Bindable public int getThirdImageBgColor() {
        return thirdImageBgColor;
    }

    public void setThirdImageBgColor(int thirdImageBgColor) {
        this.thirdImageBgColor = thirdImageBgColor;
		notifyPropertyChanged(BR.thirdImageBgColor);
    }

    @Bindable public int getThirdCommentBgColor() {
        return thirdCommentBgColor;
    }

    public void setThirdCommentBgColor(int thirdCommentBgColor) {
        this.thirdCommentBgColor = thirdCommentBgColor;
		notifyPropertyChanged(BR.thirdCommentBgColor);
    }

    @Bindable public String getThirdComment() {
        return thirdComment;
    }

    public void setThirdComment(String thirdComment) {
        this.thirdComment = thirdComment;
		notifyPropertyChanged(BR.thirdComment);
    }

    @Bindable public boolean isThirdCommentVisibility() {
        return thirdCommentVisibility;
    }

    public void setThirdCommentVisibility(boolean thirdCommentVisibility) {
        this.thirdCommentVisibility = thirdCommentVisibility;
		notifyPropertyChanged(BR.thirdCommentVisibility);
    }

    @Bindable public ImageView.ScaleType getThirdImageScaleType() {
        return thirdImageScaleType;
    }

    public void setThirdImageScaleType(ImageView.ScaleType thirdImageScaleType) {
        this.thirdImageScaleType = thirdImageScaleType;
		notifyPropertyChanged(BR.thirdImageScaleType);
    }

    @Bindable public String getCounterText() {
        return counterText;
    }

    public void setCounterText(String counterText) {
        this.counterText = counterText;
		notifyPropertyChanged(BR.counterText);
    }

    @Bindable public boolean isCounterVisibility() {
        return counterVisibility;
    }

    public void setCounterVisibility(boolean counterVisibility) {
        this.counterVisibility = counterVisibility;
		notifyPropertyChanged(BR.counterVisibility);
    }


    @BindingAdapter("android:layout_width")
    public static void setLayoutWidth(View view, int width){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        view.setLayoutParams(params);
    }

    @BindingAdapter("android:layout_height")
    public static void setLayoutHeight(View view, int height){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        view.setLayoutParams(params);
    }

    @BindingAdapter("android:src")
    public static void setBitmap(ImageView view, Bitmap bitmap){
        view.setImageBitmap(bitmap);
    }

}
