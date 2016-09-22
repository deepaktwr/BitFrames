package proj.me.bitframe;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 17/4/16.
 */
public class BeanBitFrame extends BeanImage{
    float width, height;
    int vibrantColor, mutedColor;

    boolean isLoaded;

    boolean hasGreaterVibrantPopulation;
    public BeanBitFrame(){
        super();
    }

    protected BeanBitFrame(Parcel in) {
        super(in);
        width = in.readFloat();
        height = in.readFloat();
        vibrantColor = in.readInt();
        mutedColor = in.readInt();
        isLoaded = in.readByte() != 0;
        hasGreaterVibrantPopulation = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeFloat(width);
        dest.writeFloat(height);
        dest.writeInt(vibrantColor);
        dest.writeInt(mutedColor);
        dest.writeByte((byte) (isLoaded ? 1 : 0));
        dest.writeByte((byte) (hasGreaterVibrantPopulation ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BeanBitFrame> CREATOR = new Creator<BeanBitFrame>() {
        @Override
        public BeanBitFrame createFromParcel(Parcel in) {
            return new BeanBitFrame(in);
        }

        @Override
        public BeanBitFrame[] newArray(int size) {
            return new BeanBitFrame[size];
        }
    };
    public boolean isHasGreaterVibrantPopulation() {
        return hasGreaterVibrantPopulation;
    }

    public void setHasGreaterVibrantPopulation(boolean hasGreaterVibrantPopulation) {
        this.hasGreaterVibrantPopulation = hasGreaterVibrantPopulation;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getVibrantColor() {
        return vibrantColor;
    }

    public void setVibrantColor(int vibrantColor) {
        this.vibrantColor = vibrantColor;
    }

    public int getMutedColor() {
        return mutedColor;
    }

    public void setMutedColor(int mutedColor) {
        this.mutedColor = mutedColor;
    }
}
