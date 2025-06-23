package model;

public class Question {
    public String question, a, b, c, d, correct;

    public Question(String question, String a, String b, String c, String d, String correct) {
        this.question = question;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.correct = correct;
    }
}
