package hk.ust.cse.hunkim.questionroom.question;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Joel on 29/10/2015.
 */
public class FollowUp extends BaseQuestion {
    // Dummy Constructor for JSONObject-ifying
    public FollowUp() {}

    public FollowUp(String text) {
        super(text);
    }
}
