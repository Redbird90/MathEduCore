package app.studio.jkt.com.learnmath;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class PracticeRetryFragment extends Fragment {

    private String sectionKey;
    private int sectionNumber;

    public PracticeRetryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_practice_retry, container, false);

        Intent currRetryIntent = getActivity().getIntent();
        sectionKey = currRetryIntent.getStringExtra(Intent.EXTRA_TEXT);
        sectionNumber = currRetryIntent.getIntExtra("sectionNumber", 1);
        ArrayList<Integer> retryProblemKeys = currRetryIntent.getIntegerArrayListExtra("retryProblemKeys");
        ArrayList<String> userAnswers = currRetryIntent.getStringArrayListExtra("userAnswers");

        TextView textViewHeader = (TextView) rootView.findViewById(R.id.textViewRetryHeader);
        TextView textViewMissed = (TextView) rootView.findViewById(R.id.textViewRetryMissed);

        textViewHeader.setText(sectionKey + getString(R.string.practice_retry_header));
        textViewMissed.setText(getString(R.string.test_review_missed) + retryProblemKeys.size());

        ScrollView scrollViewRetry = (ScrollView) rootView.findViewById(R.id.scrollViewPracRetry);

        LinearLayout linearLayout = new LinearLayout(getActivity());

        ArrayList<Problem> retryProbsList = new ArrayList<Problem>(retryProblemKeys.size());

        for (int i = 0; i < retryProblemKeys.size(); i++) {
            Problem currRetryProb = null;
            if (sectionNumber == 1) {
                switch (retryProblemKeys.get(i)) {
                    case 1:
                        currRetryProb = new WordProblem(getString(R.string.section1_practice_problem1_q), getString(R.string.section1_practice_problem1_a));
                    case 2:
                        currRetryProb = new WordProblem(getString(R.string.section1_practice_problem2_q), getString(R.string.section1_practice_problem2_a));
                    case 3:
                        currRetryProb = new WordProblem(getString(R.string.section1_practice_problem3_q), getString(R.string.section1_practice_problem3_a));
                    case 4:
                        currRetryProb = new WordProblem(getString(R.string.section1_practice_problem4_q), getString(R.string.section1_practice_problem4_a));
                    case 5:
                        currRetryProb = new WordProblem(getString(R.string.section1_practice_problem5_q), getString(R.string.section1_practice_problem5_a));
                }
                retryProbsList.add(currRetryProb);
            }
        }

        int probNumber = 0;
        for (int x=0; x < retryProbsList.size(); x++) {
            Problem currProbToInflate = retryProbsList.get(x);
            String probType = currProbToInflate.getProblemType();

            if (probType == "word") {
                final WordProblem wordProblem = (WordProblem) currProbToInflate;

                TextView textViewQ = new TextView(getActivity());
                final Button buttonShowAns = new Button(getActivity());
                TextView textViewUserAnswer = new TextView(getActivity());

                textViewQ.setText(String.valueOf(probNumber) + ". " + wordProblem.getText());
                textViewQ.setPadding(25, 30, 25, 0);
                buttonShowAns.setText(getString(R.string.practice_retry_show));
                buttonShowAns.setPadding(0, 0, 0, 30);
                textViewUserAnswer.setText(getString(R.string.practice_retry_user_answer) + userAnswers.get(x));

                buttonShowAns.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (buttonShowAns.getText() == getString(R.string.practice_retry_show)) {
                            buttonShowAns.setText(getString(R.string.practice_retry_show2));
                        } else {
                            // TODO: Check answer fetching below points correctly
                            buttonShowAns.setText(wordProblem.getAnswer());
                            buttonShowAns.setEnabled(false);
                        }
                    }
                });

                LinearLayout.LayoutParams questionLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams ansLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                textViewQ.setLayoutParams(questionLayoutParams);
                buttonShowAns.setLayoutParams(ansLayoutParams);
                textViewUserAnswer.setLayoutParams(ansLayoutParams);

                linearLayout.addView(textViewQ);
                linearLayout.addView(buttonShowAns);
                linearLayout.addView(textViewUserAnswer);

            }
            probNumber += 1;
        }

        LinearLayout.LayoutParams btnLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button btnBackToSection = new Button(getActivity());
        btnBackToSection.setLayoutParams(btnLayoutParams);
        btnBackToSection.setText(getString(R.string.test_review_backtosection));
        Button btnBackToMainMenu = new Button(getActivity());
        btnBackToMainMenu.setLayoutParams(btnLayoutParams);
        btnBackToMainMenu.setText(getString(R.string.testprep_backtoMM));

        btnBackToSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToSectionIntent = new Intent(getActivity(), SectionDetail.class);

                backToSectionIntent.putExtra(Intent.EXTRA_TEXT, sectionKey);
                backToSectionIntent.putExtra("sectionNumber", sectionNumber);

                startActivity(backToSectionIntent);
            }
        });

        btnBackToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMMIntent = new Intent(getActivity(), MainActivity.class);

                backToMMIntent.putExtra("fromValue", "practice");

                startActivity(backToMMIntent);
            }
        });

        linearLayout.addView(btnBackToSection);
        linearLayout.addView(btnBackToMainMenu);

        scrollViewRetry.addView(linearLayout);

        return rootView;
    }
}
