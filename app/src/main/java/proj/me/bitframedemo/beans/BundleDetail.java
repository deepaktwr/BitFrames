package proj.me.bitframedemo.beans;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
/**
 * Created by root on 20/9/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "_id",
        "bundle_id",
        "device_id",
        "images"
})
public class BundleDetail implements Parcelable{
    @JsonProperty("bundle_id")
    private int bundleId;
    @JsonProperty("device_id")
    private String deviceId;
    @JsonProperty("images")
    private List<Bundle> images = new ArrayList<Bundle>();

    public BundleDetail(){}


    protected BundleDetail(Parcel in) {
        bundleId = in.readInt();
        deviceId = in.readString();
        images = in.createTypedArrayList(Bundle.CREATOR);
    }

    public static final Creator<BundleDetail> CREATOR = new Creator<BundleDetail>() {
        @Override
        public BundleDetail createFromParcel(Parcel in) {
            return new BundleDetail(in);
        }

        @Override
        public BundleDetail[] newArray(int size) {
            return new BundleDetail[size];
        }
    };

    /**
     *
     * @return
     * The bundleId
     */
    @JsonProperty("bundle_id")
    public int getBundleId() {
        return bundleId;
    }

    /**
     *
     * @param bundleId
     * The bundle_id
     */
    @JsonProperty("bundle_id")
    public void setBundleId(int bundleId) {
        this.bundleId = bundleId;
    }

    /**
     *
     * @return
     * The deviceId
     */
    @JsonProperty("device_id")
    public String getDeviceId() {
        return deviceId;
    }

    /**
     *
     * @param deviceId
     * The device_id
     */
    @JsonProperty("device_id")
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     *
     * @return
     * The images
     */
    @JsonProperty("images")
    public List<Bundle> getImages() {
        return images;
    }

    /**
     *
     * @param images
     * The images
     */
    @JsonProperty("images")
    public void setImages(List<Bundle> images) {
        this.images = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bundleId);
        dest.writeString(deviceId);
        dest.writeTypedList(images);
    }
}
