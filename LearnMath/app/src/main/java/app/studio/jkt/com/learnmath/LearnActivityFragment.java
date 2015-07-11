package app.studio.jkt.com.learnmath;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class LearnActivityFragment extends Fragment {

    private String sectionTitle;
    private int sectionNumber;
    private Drawable[] learnIVResourceArray;

    public LearnActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_learn, container, false);

        Intent currLearnIntent = getActivity().getIntent();
        sectionTitle = currLearnIntent.getStringExtra(Intent.EXTRA_TEXT);
        sectionNumber = currLearnIntent.getIntExtra("sectionNumber", 1);

        LinearLayout outerLinearLayout = (LinearLayout) rootView.findViewById(R.id.learnOuterLinLayout);

        TextView textViewJumpToSection = new TextView(getActivity());
        textViewJumpToSection.setText(getString(R.string.learn_jumpto));
        textViewJumpToSection.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));

        outerLinearLayout.addView(textViewJumpToSection, 0);

        if (sectionNumber == 1) {
            int numOfTopics = getResources().getInteger(R.integer.section1_learn_topicnum);
            String[] romanNum = new String[] {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
            for (int i=0; i < numOfTopics; i++) {
                Button topicButton = new Button(getActivity());
                topicButton.setText(romanNum[i]);
                topicButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            }
        }

        Button escBtn = new Button(getActivity());
        escBtn.setText(R.string.learn_escbtn);
        escBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));


        return rootView;
    }
}
