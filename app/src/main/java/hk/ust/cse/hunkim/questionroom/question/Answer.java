package hk.ust.cse.hunkim.questionroom.question;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Joel on 29/10/2015.
 */
public class Answer extends BaseQuestion {
    @JsonProperty("follow_ups") private FollowUp[] follow_ups;

    public FollowUp[] getFollow_ups() {
        return follow_ups;
    }

    // Dummy Constructor for JSONObject-ifying
    public Answer() {}

    public Answer(String text) {
        super(text);
    }
}
