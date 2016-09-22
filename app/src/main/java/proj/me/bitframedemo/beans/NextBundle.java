package proj.me.bitframedemo.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "message",
        "bundle_id"
})
public class NextBundle implements Parcelable{

    @JsonProperty("status")
    private int status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("bundle_id")
    private int bundleId;

    public NextBundle(){}
    protected NextBundle(Parcel in) {
        status = in.readInt();
        message = in.readString();
        bundleId = in.readInt();
    }

    public static final Creator<NextBundle> CREATOR = new Creator<NextBundle>() {
        @Override
        public NextBundle createFromParcel(Parcel in) {
            return new NextBundle(in);
        }

        @Override
        public NextBundle[] newArray(int size) {
            return new NextBundle[size];
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
     * @return The bundleId
     */
    @JsonProperty("bundle_id")
    public int getBundleId() {
        return bundleId;
    }

    /**
     * @param bundleId The bundle_id
     */
    @JsonProperty("bundle_id")
    public void setBundleId(int bundleId) {
        this.bundleId = bundleId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
        dest.writeString(message);
        dest.writeInt(bundleId);
    }
}