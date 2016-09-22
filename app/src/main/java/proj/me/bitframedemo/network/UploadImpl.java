package proj.me.bitframedemo.network;

import okhttp3.RequestBody;
import proj.me.bitframedemo.beans.BaseResponse;
import proj.me.bitframedemo.beans.UploadRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by root on 18/9/16.
 */

public interface UploadImpl {

    @Multipart
    @POST("uploadBundle")
    Call<BaseResponse> uploadImage(@Part("file\"; fileName=\"myFile.png\" ")RequestBody requestBodyFile, @Part("image") RequestBody requestBodyJson);

    @POST("postBundle")
    Call<BaseResponse> postBundle(@Body UploadRequest uploadRequest);

}
