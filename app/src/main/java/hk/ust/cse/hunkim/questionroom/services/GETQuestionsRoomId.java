package hk.ust.cse.hunkim.questionroom.services;


import com.squareup.okhttp.RequestBody;

import java.util.List;

import hk.ust.cse.hunkim.questionroom.question.Question;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.Part;
/**
 * Created by Joel on 29/10/2015.
 */
public interface GETQuestionsRoomId {
    String BASE_URL = "http://questions-backend.herokuapp.com/";

    @Multipart
    @GET("v1/questions/room/{room_id}")
    Call<List<Question>> questions(
            @Part("image\"; filename=\"image.jpg") RequestBody userPhoto
    );
}
