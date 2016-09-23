package proj.me.bitframedemo.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import proj.me.bitframedemo.beans.BaseResponse;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by root on 17/9/16.
 */

public class RetrofitClient<T> {
    static RetrofitClient retrofitClient;

    Retrofit retrofit;
    ObjectMapper objectMapper;
    Map<Integer, T> implObject;

    private RetrofitClient(){
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES)
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl("YOUR BASE URL")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(okHttpClient)
                .build();
    }

    public static RetrofitClient createClient(){
        if(retrofitClient != null) return retrofitClient;
        return retrofitClient = new RetrofitClient<>();
    }

    public T getRetrofitImpl(Class<T> classObj, int classId){
        if(implObject == null) implObject = new HashMap<>();
        else if(implObject.containsKey(classId)) return implObject.get(classId);
        T obj = retrofit.create(classObj);
        implObject.put(classId, obj);
        return obj;
    }

    public <V> String getJsonObject(V object){
        String jsonObj = null;
        try {
            jsonObj = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public <V> V getErrorRespose(Class<V> classObj, Response<BaseResponse> response) throws IOException {
        Converter<ResponseBody, V> errorConverter = retrofit.responseBodyConverter(classObj, new Annotation[0]);
        return errorConverter.convert(response.errorBody());
    }
}
