package hk.ust.cse.hunkim.questionroom;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import hk.ust.cse.hunkim.questionroom.question.Question;
import hk.ust.cse.hunkim.questionroom.services.ErrorIdResponse;
import hk.ust.cse.hunkim.questionroom.services.QuestionService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Joel on 29/10/2015.
 */
public abstract class DatabaseListAdapter extends BaseAdapter {
    private static final String BASE_URL = "http://questions-backend.herokuapp.com/api/";

    private int mLayout;
    private LayoutInflater mInflater;
    private List<Question> mQuestionList;
    private final static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    public DatabaseListAdapter(int mLayout, Activity activity) {
        this.mLayout = mLayout;
        this.mInflater = activity.getLayoutInflater();
        this.mQuestionList = new ArrayList<Question>();
    }

    public void push(final Question question) {
        QuestionService service = retrofit.create(QuestionService.class);

        Call<ErrorIdResponse> response = service.createQuestion(
                question.getText(),
                question.getImageURL(),
                question.getRoom()
        );

        response.enqueue(new Callback<ErrorIdResponse>() {
            @Override
            public void onResponse(Response<ErrorIdResponse> response, Retrofit retrofit) {
                question.setId(response.body().id);
                notifyDataSetChanged();
                mQuestionList.add(question);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("QUESTIONROOM", "Failed at DatabaseListAdapter.push():", t);
            }
        });
    }

    public void pull(final String roomName) {
        QuestionService service = retrofit.create(QuestionService.class);
        Call<List<Question>> response;

        // TODO: Wait until backend fixes /question/room/[roomID]
        //if (roomName == "")
            response = service.getQuestions();
        //else
        //    response = service.getQuestions(roomName);

        response.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Response<List<Question>> response, Retrofit retrofit) {
                for (Question q: response.body()) {
                    if (q != null && q.getId() != null && q.getRoom().equals(roomName))
                        mQuestionList.add(q);
                }

                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("QUESTIONROOM", "Failed at DatabaseListAdapter.pull():", t);
            }
        });
    }

    public void cleanup() {
        mQuestionList.clear();
    }

    @Override
    public int getCount() {
        return mQuestionList.size();
    }

    @Override
    public Object getItem(int i) {
        return mQuestionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mInflater.inflate(mLayout, viewGroup, false);
        }

        // FIXME: Perhaps this is the first time to show data
        // Let's order the list
        if (i == 0) {
            sortModels(mQuestionList);
        }
        // Let's get keys and models
        Question question = mQuestionList.get(i);

        // Call out to subclass to marshall this model into the provided view
        populateView(view, question);
        return view;
    }

    protected abstract void populateView(View view, Question question);

    protected abstract void sortModels(List<Question> mModels);
}
