package app.studio.jkt.com.learnmath;

import android.os.Parcel;
import android.os.Parcelable;

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

    public  WordProblem(Parcel in) {

        String[] data = new String[3];

        in.readStringArray(data);
        this.text = data[0];
        this.units = data[1];
        this.answer = data[2];

    }

    public String getProblemType() {
        return "word";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeStringArray(new String[] {this.text, this.units, this.answer});

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public WordProblem createFromParcel(Parcel in) {
            return new WordProblem(in);
        }
        public WordProblem[] newArray(int size) {
            return new WordProblem[size];
        }
    };
}
