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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "message",
        "bundle"
})
public class FrameResponse implements Parcelable{

    @JsonProperty("status")
    private int status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("bundle")
    private List<BundleDetail> bundle = new ArrayList<BundleDetail>();

    public FrameResponse(){}

    protected FrameResponse(Parcel in) {
        status = in.readInt();
        message = in.readString();
        bundle = in.createTypedArrayList(BundleDetail.CREATOR);
    }

    public static final Creator<FrameResponse> CREATOR = new Creator<FrameResponse>() {
        @Override
        public FrameResponse createFromParcel(Parcel in) {
            return new FrameResponse(in);
        }

        @Override
        public FrameResponse[] newArray(int size) {
            return new FrameResponse[size];
        }
    };

    /**
     * @return The status
     */
    @JsonProperty("status")
    public int getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    @JsonProperty("status")
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return The message
     */
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return The bundle
     */
    @JsonProperty("bundle")
    public List<BundleDetail> getBundle() {
        return bundle;
    }

    /**
     * @param bundle The bundle
     */
    @JsonProperty("bundle")
    public void setBundle(List<BundleDetail> bundle) {
        this.bundle = bundle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
        dest.writeString(message);
        dest.writeTypedList(bundle);
    }
}