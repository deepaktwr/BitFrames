package proj.me.bitframe;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by root on 17/4/16.
 */
public class BeanImage implements Comparable<BeanImage>, Parcelable {
    String imageLink;
    String imageComment;
    int primaryCount, secondaryCount;

    public BeanImage(){}

    protected BeanImage(Parcel in) {
        imageLink = in.readString();
        imageComment = in.readString();
        primaryCount = in.readInt();
        secondaryCount = in.readInt();
    }

    public static final Creator<BeanImage> CREATOR = new Creator<BeanImage>() {
        @Override
        public BeanImage createFromParcel(Parcel in) {
            return new BeanImage(in);
        }

        @Override
        public BeanImage[] newArray(int size) {
            return new BeanImage[size];
        }
    };


    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getImageComment() {
        return imageComment;
    }

    public void setImageComment(String imageComment) {
        this.imageComment = imageComment;
    }

    public int getPrimaryCount() {
        return primaryCount;
    }

    public void setPrimaryCount(int primaryCount) {
        this.primaryCount = primaryCount;
    }

    public int getSecondaryCount() {
        return secondaryCount;
    }

    public void setSecondaryCount(int secondaryCount) {
        this.secondaryCount = secondaryCount;
    }

    @Override
    public int compareTo(BeanImage another) {
        if(primaryCount > another.getPrimaryCount() || secondaryCount > another.getSecondaryCount()) return 1;
        else if(primaryCount == another.getPrimaryCount() || secondaryCount == another.getSecondaryCount()) return 0;
        return -1;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageLink);
        dest.writeString(imageComment);
        dest.writeInt(primaryCount);
        dest.writeInt(secondaryCount);
    }
}
