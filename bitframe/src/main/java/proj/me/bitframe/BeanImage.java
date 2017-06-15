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
        /*//if in right order then <= 0
        //else need to interchange so > 0
        if(primaryCount == another.getPrimaryCount() && secondaryCount == another.getSecondaryCount()) return 0;
        else if(primaryCount < another.getPrimaryCount()) return 1;//need to interchange a/c to our requirement
        else if(primaryCount > another.getPrimaryCount()) return -1;//are in right order a/c to our requirement
        else if(secondaryCount < another.getSecondaryCount()) return 1;
        else if(secondaryCount > another.getSecondaryCount()) return -1;
        return 1;*/

        int first = Math.max(primaryCount + ImageShading.SORT_DIFFERENCE_THRESHOLD, secondaryCount);
        int second = Math.max(another.getPrimaryCount() + ImageShading.SORT_DIFFERENCE_THRESHOLD, another.getSecondaryCount());

        return second - first;
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
