package proj.me.bitframedemo.network;

import proj.me.bitframedemo.beans.FrameResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by root on 20/9/16.
 */

public interface DownloadImpl {
    @GET("getDeviceBundle")
    Call<FrameResponse> getDeviceBundle(@Query("device_id") String deviceId);
}
