package app.studio.jkt.com.learnmath;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class TestReviewFragment extends Fragment {

    private String sectionTitle;
    private int sectionNumber;

    public TestReviewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_test_review, container, false);

        ScrollView scrollView = (ScrollView) rootView.findViewById(R.id.scrollViewTestReview);

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams linLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(linLayoutParams);

        Intent currReviewIntent = getActivity().getIntent();
        ArrayList<Problem> problemsToReview = currReviewIntent.getParcelableArrayListExtra("incorrectProblemsList");
        ArrayList<String> priorAnswers = currReviewIntent.getStringArrayListExtra("incorrectAnswers");
        sectionTitle = currReviewIntent.getStringExtra(Intent.EXTRA_TEXT);
        sectionNumber = currReviewIntent.getIntExtra("sectionNumber", 1);

        TextView textViewHeader = (TextView) rootView.findViewById(R.id.textViewReviewHeader);
        TextView textViewReviewMissed = (TextView) rootView.findViewById(R.id.textViewReviewMissed);
        textViewHeader.setText(sectionTitle + getString(R.string.test_review_header));
        textViewReviewMissed.setText(getString(R.string.test_review_missed) + problemsToReview.size());



        int problemNumber = 1;

        if (sectionNumber == 1) {
            int totalNumberOfReviewProblems = problemsToReview.size();
            for (int i=0; i < totalNumberOfReviewProblems; i++) {
                Problem currReviewProb = problemsToReview.get(i);
                String probType = currReviewProb.getProblemType();

                if (probType == "word") {
                    WordProblem wordProblem = (WordProblem) currReviewProb;

                    TextView textViewQ = new TextView(getActivity());
                    TextView textViewCorrAnswer = new TextView(getActivity());
                    TextView textViewUserAnswer = new TextView(getActivity());

                    textViewQ.setText(String.valueOf(problemNumber) + ". " + wordProblem.getText());
                    textViewQ.setPadding(25, 30, 25, 0);
                    textViewCorrAnswer.setText(getString(R.string.test_review_correct_answer) + wordProblem.getAnswer());
                    textViewCorrAnswer.setPadding(0, 0, 0, 30);
                    textViewUserAnswer.setText(getString(R.string.test_review_user_answer) + priorAnswers.get(problemNumber - 1));
                    textViewUserAnswer.setPadding(0, 0, 0, 30);

                    LinearLayout.LayoutParams questionLayoutParams = new LinearLayout.LayoutParams(LinearLayout
                            .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    LinearLayout.LayoutParams AnswerLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    textViewQ.setLayoutParams(questionLayoutParams);
                    textViewCorrAnswer.setLayoutParams(AnswerLayoutParams);
                    textViewUserAnswer.setLayoutParams(AnswerLayoutParams);

                    linearLayout.addView(textViewQ);
                    linearLayout.addView(textViewCorrAnswer);
                    linearLayout.addView(textViewUserAnswer);

                }
                problemNumber += 1;
            }
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

                backToSectionIntent.putExtra(Intent.EXTRA_TEXT, sectionTitle);
                backToSectionIntent.putExtra("sectionNumber", sectionNumber);

                startActivity(backToSectionIntent);
            }
        });

        btnBackToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMMIntent = new Intent(getActivity(), MainActivity.class);

                backToMMIntent.putExtra("fromValue", "testprep");

                startActivity(backToMMIntent);
            }
        });

        linearLayout.addView(btnBackToSection);
        linearLayout.addView(btnBackToMainMenu);

        scrollView.addView(linearLayout);

        return rootView;
    }
}
