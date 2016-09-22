package proj.me.bitframedemo.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by root on 18/9/16.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "device_id",
        "request_id",
        "bundle_id",
        "bundle"
})
public class UploadRequest {
    @JsonProperty("device_id")
    private String deviceId;
    @JsonProperty("request_id")
    private int requestId;
    @JsonProperty("bundle_id")
    private int bundleId;
    @JsonProperty("bundle")
    private Bundle bundle;

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
     * The requestId
     */
    @JsonProperty("request_id")
    public int getRequestId() {
        return requestId;
    }

    /**
     *
     * @param requestId
     * The request_id
     */
    @JsonProperty("request_id")
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

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
     * The bundle
     */
    @JsonProperty("bundle")
    public Bundle getBundle() {
        return bundle;
    }

    /**
     *
     * @param bundle
     * The bundle
     */
    @JsonProperty("bundle")
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
