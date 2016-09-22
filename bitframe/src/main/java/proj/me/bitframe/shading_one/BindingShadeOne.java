package proj.me.bitframe.shading_one;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import proj.me.bitframe.BR;


/**
 * Created by root on 11/3/16.
 */
public class BindingShadeOne extends BaseObservable{
    //for comment
    private boolean shouldCommentVisible;
    private int commentTextBackgroundColor;
    private String comment;
    //for counter
    private String imageCounterText;
    private boolean imageCounterVisibility;
    //for image
    private int imageBackgroundColor;
    private ImageView.ScaleType imageScaleType;

    @Bindable public ImageView.ScaleType getImageScaleType() {
        return imageScaleType;
    }

    public void setImageScaleType(ImageView.ScaleType imageScaleType) {
        this.imageScaleType = imageScaleType;
        notifyPropertyChanged(BR.imageScaleType);
    }

    @Bindable public int getImageBackgroundColor() {
        return imageBackgroundColor;
    }

    public void setImageBackgroundColor(int imageBackgroundColor) {
        this.imageBackgroundColor = imageBackgroundColor;
		notifyPropertyChanged(BR.imageBackgroundColor);
    }

    @Bindable public String getImageCounterText() {
        return imageCounterText;
    }

    public void setImageCounterText(String imageCounterText) {
        this.imageCounterText = imageCounterText;
		notifyPropertyChanged(BR.imageCounterText);
    }

    @Bindable
    public boolean isImageCounterVisibility() {
        return imageCounterVisibility;
    }

    public void setImageCounterVisibility(boolean imageCounterVisibility) {
        this.imageCounterVisibility = imageCounterVisibility;
		notifyPropertyChanged(BR.imageCounterVisibility);
    }
    @Bindable public boolean isShouldCommentVisible() {
        return shouldCommentVisible;
    }

    public void setShouldCommentVisible(boolean shouldCommentVisible) {
        this.shouldCommentVisible = shouldCommentVisible;
		notifyPropertyChanged(BR.shouldCommentVisible);
    }
   @Bindable public int getCommentTextBackgroundColor() {
        return commentTextBackgroundColor;
    }

    public void setCommentTextBackgroundColor(int commentTextBackgroundColor) {
        this.commentTextBackgroundColor = commentTextBackgroundColor;
		notifyPropertyChanged(BR.commentTextBackgroundColor);
    }
   @Bindable public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
		notifyPropertyChanged(BR.comment);
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
