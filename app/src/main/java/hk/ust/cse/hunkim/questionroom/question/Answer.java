package hk.ust.cse.hunkim.questionroom.question;

/**
 * Created by Joel on 29/10/2015.
 */
public class Answer extends BaseQuestion {
    private FollowUp[] follow_ups;

    public FollowUp[] getFollow_ups() {
        return follow_ups;
    }

    public Answer(String text) {
        super(text);
    }
}
