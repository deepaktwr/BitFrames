package proj.me.bitframedemo.binders;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import proj.me.bitframedemo.BR;

/**
 * Created by root on 20/9/16.
 */

public class CardBinder extends BaseObservable{
    private String title;
    private String description;
    private int titleColor;
    private int descriptionColor;


    @Bindable public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        notifyPropertyChanged(BR.titleColor);
    }

    @Bindable public int getDescriptionColor() {
        return descriptionColor;
    }

    public void setDescriptionColor(int descriptionColor) {
        this.descriptionColor = descriptionColor;
        notifyPropertyChanged(BR.descriptionColor);
    }
}
