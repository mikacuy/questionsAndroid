package hk.ust.cse.hunkim.questionroom.db;

import android.util.Log;

import hk.ust.cse.hunkim.questionroom.question.Question;
import hk.ust.cse.hunkim.questionroom.services.ErrorIdResponse;
import hk.ust.cse.hunkim.questionroom.services.POSTQuestion;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Joel on 29/10/2015.
 */
public class Database {
    private static final String BASE_URL = "http://questions-backend.herokuapp.com/api/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static void push(final Question question) {
        POSTQuestion service = retrofit.create(POSTQuestion.class);

        Call<ErrorIdResponse> response = service.createQuestion(
                question.getText(),
                "",
                //question.getImageURL(),
                "3005"
                //question.getRoom()
        );

        response.enqueue(new Callback<ErrorIdResponse>() {
            @Override
            public void onResponse(Response<ErrorIdResponse> response, Retrofit retrofit) {
                question.setId(response.body().id);
                Log.d("ASDF", response.body().id);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("FAILURE", "", t);
            }
        });
    }
}
