package hk.ust.cse.hunkim.questionroom.question;

import java.util.Date;

/**
 * Created by hunkim on 7/16/15.
 */
public class Question extends BaseQuestion {
    private String room;
    private Answer[] answers;

    public Answer[] getAnswers() {
        return answers;
    }

    public Question(String text) {
        super(text);

        this.room = "";
    }

    public String getRoom() {
        return this.room;
    }
}
