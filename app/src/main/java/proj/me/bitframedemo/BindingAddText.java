package proj.me.bitframedemo;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * Created by root on 23/4/16.
 */
public class BindingAddText extends BaseObservable{
    private String addText;
    private boolean textVisibility;
    private boolean errorVisibility;
    private int textColor;

    @Bindable public String getAddText() {
        return addText;
    }

    public void setAddText(String addText) {
        this.addText = addText;
        notifyPropertyChanged(BR.addText);
    }

    @Bindable public boolean isTextVisibility() {
        return textVisibility;
    }

    public void setTextVisibility(boolean textVisibility) {
        this.textVisibility = textVisibility;
        notifyPropertyChanged(BR.textVisibility);
    }

    @Bindable public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        notifyPropertyChanged(BR.textColor);
    }

    @Bindable public boolean isErrorVisibility() {
        return errorVisibility;
    }

    public void setErrorVisibility(boolean errorVisibility) {
        this.errorVisibility = errorVisibility;
        notifyPropertyChanged(BR.errorVisibility);
    }
}
