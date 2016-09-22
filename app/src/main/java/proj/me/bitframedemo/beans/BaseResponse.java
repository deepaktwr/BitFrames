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
        "message"
})
public class BaseResponse {

    @JsonProperty("status")
    private int status;
    @JsonProperty("message")
    private String message;

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
}