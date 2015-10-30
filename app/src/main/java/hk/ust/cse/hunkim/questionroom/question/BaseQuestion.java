package hk.ust.cse.hunkim.questionroom.question;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by Joel on 29/10/2015.
 */
public abstract class BaseQuestion implements Comparable<BaseQuestion> {
    @JsonProperty("__v") private String __v;
    @JsonProperty("_id") protected String id;
    @JsonProperty("text") protected String text;
    @JsonProperty("imageURL") protected String imageURL;
    // TODO: Fix time handling; server side's fault probably.
    @JsonProperty("time") private String time;
    protected long unixTime;
    protected int likes;
    protected boolean newQuestion;

    // Dummy Constructor for JSONObject-ifying
    public BaseQuestion() {}

    /**
     * Set question from a String message
     * @param message string message
     */
    public BaseQuestion(String message) {
        this.text = message;
        this.likes = 0;
        //this.unixTime = new Date().getTime();
        this.imageURL = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public long getUnixTime() {
        return unixTime;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getLikes() {
        return likes;
    }

    public void updateNewQuestion() {
        newQuestion = this.unixTime > new Date().getTime() - 180000;
    }

    public boolean isNewQuestion() {
        return newQuestion;
    }

    /**
     * New one/high echo goes bottom
     * @param other other chat
     * @return order
     */
    @Override
    public int compareTo(BaseQuestion other) {
        // Push new on top
        other.updateNewQuestion(); // update NEW button
        this.updateNewQuestion();

        if (this.newQuestion != other.newQuestion) {
            return this.newQuestion ? 1 : -1; // this is the winner
        }


        if (this.likes == other.likes) {
            if (other.unixTime == this.unixTime) {
                return 0;
            }
            return other.unixTime > this.unixTime ? -1 : 1;
        }
        return this.likes - other.likes;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BaseQuestion)) {
            return false;
        }
        return getId() == ((BaseQuestion) o).getId();
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
