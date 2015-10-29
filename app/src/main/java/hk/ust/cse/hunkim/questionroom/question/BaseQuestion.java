package hk.ust.cse.hunkim.questionroom.question;

import java.util.Date;

/**
 * Created by Joel on 29/10/2015.
 */
public abstract class BaseQuestion implements Comparable<BaseQuestion> {
    private String id;
    private int userId;
    private String username;
    private String text;
    private long time;
    private String imageURL;
    private int likes;
    private boolean newQuestion;

    /**
     * Set question from a String message
     * @param message string message
     */
    public BaseQuestion(String message) {
        this.text = message;
        this.likes = 0;
        this.time = new Date().getTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    public long getTime() {
        return time;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getLikes() {
        return likes;
    }

    public void updateNewQuestion() {
        newQuestion = this.time > new Date().getTime() - 180000;
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
            if (other.time == this.time) {
                return 0;
            }
            return other.time > this.time ? -1 : 1;
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
}
