package proj.me.bitframe;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

/**
 * Created by Deepak.Tiwari on 02-12-2015.
 */
public class ImageScrollParcelable implements Parcelable{

    ImageType imageType;
    int imagePosition;
    String imageLink;

    public ImageType getImageType() {
        return imageType;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

    public int getImagePosition() {
        return imagePosition;
    }

    public void setImagePosition(int imagePosition) {
        this.imagePosition = imagePosition;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public ImageScrollParcelable(){}
    protected ImageScrollParcelable(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<ImageScrollParcelable> CREATOR = new Creator<ImageScrollParcelable>() {
        @Override
        public ImageScrollParcelable createFromParcel(Parcel in) {
            return new ImageScrollParcelable(in);
        }

        @Override
        public ImageScrollParcelable[] newArray(int size) {
            return new ImageScrollParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(imageType);
        parcel.writeInt(imagePosition);
        parcel.writeString(imageLink);
    }
    private void readFromParcel(Parcel in){
        imageType = (ImageType)in.readValue(getClass().getClassLoader());
        imagePosition = in.readInt();
        imageLink = in.readString();
    }
}
