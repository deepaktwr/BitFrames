package proj.me.bitframedemo.network;

import proj.me.bitframedemo.beans.NextBundle;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by root on 17/9/16.
 */

public interface RetrofitImpl {
    @GET("getNextBundle")
    Call<NextBundle> getNextBundle(@Query("device_id") String deviceId, @Query("request_id") int requestId);
}
