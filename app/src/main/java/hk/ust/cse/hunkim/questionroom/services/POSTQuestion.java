package hk.ust.cse.hunkim.questionroom.services;

import com.squareup.okhttp.RequestBody;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Joel on 29/10/2015.
 */
public interface POSTQuestion {
    @FormUrlEncoded
    @POST("v1/question")
    Call<ErrorIdResponse> createQuestion(
            @Field("text") String text,
            @Field("imageURL") String imageURL,
            @Field("room") String room
    );
}
