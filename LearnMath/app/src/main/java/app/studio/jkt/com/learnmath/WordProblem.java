package app.studio.jkt.com.learnmath;

/**
 * Created by JDK on 6/29/2015.
 */
public class WordProblem implements Problem {

    private String text;
    private String units;
    private String answer;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public WordProblem(String text, String units, String answer) {
        this.text = text;
        this.units = units;
        this.answer = answer;
    }

    public String getProblemType() {
        return "word";
    }
}
