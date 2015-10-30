package hk.ust.cse.hunkim.questionroom.services;

import java.util.List;

import hk.ust.cse.hunkim.questionroom.question.Question;
import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Joel on 29/10/2015.
 */
public interface QuestionService {
    @FormUrlEncoded
    @POST("v1/question")
    Call<ErrorIdResponse> createQuestion(
            @Field("text") String text,
            @Field("imageURL") String imageURL,
            @Field("room") String room
    );

    @GET("v1/question")
    Call<List<Question>> getQuestions();

    @GET("v1/question/room")
    Call<List<Question>> getQuestions(@Query("room") String room);
}
