package proj.me.bitframe.shading_two;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import proj.me.bitframe.BR;


/**
 * Created by root on 14/3/16.
 */
public class BindingShadeTwo extends BaseObservable{
    //horz
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

    private int addBgColor;
    private int addTextColor;
    private String addText;
    private boolean addVisibility;
    private boolean addAsCounter;

    private String counterText;
    private boolean counterVisibility;

    @Bindable public boolean isDividerVisible() {
        return dividerVisible;
    }

    public void setDividerVisible(boolean dividerVisible) {
        this.dividerVisible = dividerVisible;
        notifyPropertyChanged(BR.dividerVisible);
    }

    @Bindable public boolean isAddAsCounter() {
        return addAsCounter;
    }

    public void setAddAsCounter(boolean addAsCounter) {
        this.addAsCounter = addAsCounter;
        notifyPropertyChanged(BR.addAsCounter);
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

    @Bindable public int getAddBgColor() {
        return addBgColor;
    }

    public void setAddBgColor(int addBgColor) {
        this.addBgColor = addBgColor;
		notifyPropertyChanged(BR.addBgColor);
    }

    @Bindable public int getAddTextColor() {
        return addTextColor;
    }

    public void setAddTextColor(int addTextColor) {
        this.addTextColor = addTextColor;
		notifyPropertyChanged(BR.addTextColor);
    }

    @Bindable public String getAddText() {
        return addText;
    }

    public void setAddText(String addText) {
        this.addText = addText;
		notifyPropertyChanged(BR.addText);
    }

    @Bindable public boolean isAddVisibility() {
        return addVisibility;
    }

    public void setAddVisibility(boolean addVisibility) {
        this.addVisibility = addVisibility;
		notifyPropertyChanged(BR.addVisibility);
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
