package com.inthinc.pro.model.form;

public class SubmissionDataItem {
    private String tag;
    private String question;
    private String answer;
    
    public SubmissionDataItem() {
        
    }
    public SubmissionDataItem(String tag, String question, String answer) {
        super();
        this.tag = tag;
        this.question = question;
        this.answer = answer;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
