package hk.ust.cse.hunkim.questionroom.question;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by hunkim on 7/16/15.
 */
public class Question extends BaseQuestion {
    @JsonProperty("room") private String room;
    @JsonProperty("answers") private Answer[] answers;

    // Dummy Constructor for JSONObject-ifying
    public Question() {}
    public Question(String room, String text) {
        super(text);

        this.room = room;
    }

    public String getRoom() {
        return this.room;
    }

    public Answer[] getAnswers() {
        return answers;
    }

}
