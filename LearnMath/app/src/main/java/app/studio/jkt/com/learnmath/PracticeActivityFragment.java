package app.studio.jkt.com.learnmath;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class PracticeActivityFragment extends Fragment {

    private int problemNumber;
    public ArrayList<Problem> problemList;

    public PracticeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_practice, container, false);

        LinearLayout outerLinearLayout = new LinearLayout(getActivity());
        outerLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout
                .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        outerLinearLayout.setOrientation(LinearLayout.VERTICAL);

        ScrollView scrollableView = (ScrollView) rootView.findViewById(R.id.scrollViewPractice);
        Log.i("PAF", checkForNullViews(scrollableView));

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams linLayoutParams = new LinearLayout.LayoutParams(LinearLayout
                .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Intent currIntent = getActivity().getIntent();
        final String sectionKey = currIntent.getStringExtra(Intent.EXTRA_TEXT);

        problemList = new ArrayList<>();
        final WordProblem wordProblem1 = new WordProblem("If Jeff walks 1/3 of a mile each 1/2 of an " +
                "hour, how fast is Jeff walking in miles per hour?", "miles per hour", "2/3");
        problemList.add(wordProblem1);
        problemList.add(wordProblem1);
        problemList.add(wordProblem1);
        problemList.add(wordProblem1);
        problemList.add(wordProblem1);
        problemList.add(wordProblem1);
        problemList.add(wordProblem1);
        problemList.add(wordProblem1);
        problemList.add(wordProblem1);

        int problemNumber = 1;
        for (int i=0; i<problemList.size(); i++) {
            Problem currProblem = problemList.get(i);
            String probType = currProblem.getProblemType();
            if (probType == "word") {
                WordProblem wordProblem = (WordProblem) currProblem;

                TextView textViewQ = new TextView(getActivity());
                EditText editTextA = new EditText(getActivity());

                textViewQ.setText(String.valueOf(problemNumber) + ". " + wordProblem.getText());
                textViewQ.setPadding(25, 30, 25, 0);

                InputFilter[] fArray = new InputFilter[1];
                fArray[0] = new InputFilter.LengthFilter(8);
                editTextA.setFilters(fArray);
                editTextA.setPadding(0, 0, 0, 30);

                LinearLayout.LayoutParams textQLayoutParams = new LinearLayout.LayoutParams(LinearLayout
                        .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams editLayoutParams = new LinearLayout.LayoutParams(LinearLayout
                        .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                editLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;

                textViewQ.setLayoutParams(textQLayoutParams);
                editTextA.setLayoutParams(editLayoutParams);

                linearLayout.addView(textViewQ);
                linearLayout.addView(editTextA);

                problemNumber += 1;

            }
        }

        Button submitButtonPractice = new Button(getActivity());
        submitButtonPractice.setText(getString(R.string.practice_submit_button_text));
        submitButtonPractice.setGravity(Gravity.CENTER_HORIZONTAL);

        linearLayout.addView(submitButtonPractice);

        submitButtonPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent practiceCompleteView = new Intent(getActivity(),
                        PracticeResults.class);

                // TODO: Create method to check answers in all edit text fields
                // Sample answer check pass
                practiceCompleteView.putExtra(Intent.EXTRA_TEXT, sectionKey);
                practiceCompleteView.putExtra("numTotalAnswers", 5);
                practiceCompleteView.putExtra("numCorrectAnswers", 3);
                practiceCompleteView.putExtra("numTotalQuestions", 9);
                practiceCompleteView.putExtra("arrayIncorrectQuestions", new Problem[]
                        {wordProblem1, wordProblem1, wordProblem1});
                practiceCompleteView.putExtra("arrayNewBadges", new int[] {1, 2});
                practiceCompleteView.putExtra("priorNumCorrectAnswers", 2);


                // TODO: Pass incorrect problems to new Activity

                startActivity(practiceCompleteView);
            }
        });

        scrollableView.removeAllViews();
        scrollableView.addView(linearLayout);

        return rootView;
    }

    public String checkForNullViews(View a) {
        if (a == null) {
            return "View is NULL";
        } else {
            return "View is non-null";
        }
    }
}
