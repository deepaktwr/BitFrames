package proj.me.bitframedemo.binders;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import proj.me.bitframedemo.BR;

/**
 * Created by root on 20/9/16.
 */

public class FrameBinder extends BaseObservable{

    private String errorText;
    private boolean hasData;

    @Bindable public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
        notifyPropertyChanged(BR.errorText);
    }

    @Bindable public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
        notifyPropertyChanged(BR.hasData);
    }
}
