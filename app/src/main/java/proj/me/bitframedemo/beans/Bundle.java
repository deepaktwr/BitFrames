package proj.me.bitframedemo.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "img_name",
        "comment",
        "primary_count",
        "secondary_count",
        "title",
        "description",
        "width",
        "height",
        "vibrant_color",
        "muted_color",
        "has_greater_vibrant"
})
public class Bundle implements Parcelable{

    @JsonProperty("img_name")
    private String imgName;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("primary_count")
    private int primaryCount;
    @JsonProperty("secondary_count")
    private int secondaryCount;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("width")
    private int width;
    @JsonProperty("height")
    private int height;
    @JsonProperty("vibrant_color")
    private int vibrantColor;
    @JsonProperty("muted_color")
    private int mutedColor;
    @JsonProperty("has_greater_vibrant")
    private boolean hasGreaterVibrant;

    public Bundle(){}

    protected Bundle(Parcel in) {
        imgName = in.readString();
        comment = in.readString();
        primaryCount = in.readInt();
        secondaryCount = in.readInt();
        title = in.readString();
        description = in.readString();
        width = in.readInt();
        height = in.readInt();
        vibrantColor = in.readInt();
        mutedColor = in.readInt();
        hasGreaterVibrant = in.readByte() != 0;
    }

    public static final Creator<Bundle> CREATOR = new Creator<Bundle>() {
        @Override
        public Bundle createFromParcel(Parcel in) {
            return new Bundle(in);
        }

        @Override
        public Bundle[] newArray(int size) {
            return new Bundle[size];
        }
    };

    /**
     * @return The imgName
     */
    @JsonProperty("img_name")
    public String getImgName() {
        return imgName;
    }

    /**
     * @param imgName The img_name
     */
    @JsonProperty("img_name")
    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    /**
     * @return The comment
     */
    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    /**
     * @param comment The comment
     */
    @JsonProperty("comment")
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return The primaryCount
     */
    @JsonProperty("primary_count")
    public int getPrimaryCount() {
        return primaryCount;
    }

    /**
     * @param primaryCount The primary_count
     */
    @JsonProperty("primary_count")
    public void setPrimaryCount(int primaryCount) {
        this.primaryCount = primaryCount;
    }

    /**
     * @return The secondaryCount
     */
    @JsonProperty("secondary_count")
    public int getSecondaryCount() {
        return secondaryCount;
    }

    /**
     * @param secondaryCount The secondary_count
     */
    @JsonProperty("secondary_count")
    public void setSecondaryCount(int secondaryCount) {
        this.secondaryCount = secondaryCount;
    }

    /**
     * @return The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The width
     */
    @JsonProperty("width")
    public int getWidth() {
        return width;
    }

    /**
     * @param width The width
     */
    @JsonProperty("width")
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return The height
     */
    @JsonProperty("height")
    public int getHeight() {
        return height;
    }

    /**
     * @param height The height
     */
    @JsonProperty("height")
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return The vibrantColor
     */
    @JsonProperty("vibrant_color")
    public int getVibrantColor() {
        return vibrantColor;
    }

    /**
     * @param vibrantColor The vibrant_color
     */
    @JsonProperty("vibrant_color")
    public void setVibrantColor(int vibrantColor) {
        this.vibrantColor = vibrantColor;
    }

    /**
     * @return The mutedColor
     */
    @JsonProperty("muted_color")
    public int getMutedColor() {
        return mutedColor;
    }

    /**
     * @param mutedColor The muted_color
     */
    @JsonProperty("muted_color")
    public void setMutedColor(int mutedColor) {
        this.mutedColor = mutedColor;
    }

    /**
     * @return The hasGreaterVibrant
     */
    @JsonProperty("has_greater_vibrant")
    public boolean isHasGreaterVibrant() {
        return hasGreaterVibrant;
    }

    /**
     * @param hasGreaterVibrant The has_greater_vibrant
     */
    @JsonProperty("has_greater_vibrant")
    public void setHasGreaterVibrant(boolean hasGreaterVibrant) {
        this.hasGreaterVibrant = hasGreaterVibrant;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imgName);
        dest.writeString(comment);
        dest.writeInt(primaryCount);
        dest.writeInt(secondaryCount);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeInt(vibrantColor);
        dest.writeInt(mutedColor);
        dest.writeByte((byte) (hasGreaterVibrant ? 1 : 0));
    }
}