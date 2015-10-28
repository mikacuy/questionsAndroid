package hk.ust.cse.hunkim.questionroom.services;

import com.squareup.okhttp.RequestBody;

import retrofit.Call;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by Joel on 28/10/2015.
 */
public interface UploadPhotoService {
    public static final String BASE_URL = "http://questions-backend.herokuapp.com/";

    @Multipart
    @POST("api/uploadphoto")
    Call<ImageResponse> uploadPhoto(
            @Part("image\"; filename=\"image.jpg") RequestBody userPhoto
    );


}
