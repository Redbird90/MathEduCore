package app.studio.jkt.com.learnmath;

import android.content.Intent;
import android.os.CountDownTimer;
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

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


/**
 * A placeholder fragment containing a simple view.
 */
public class TestPrepActivityFragment extends Fragment {

    private int testingProblemNumber;
    public ArrayList<Problem> testingProblemList;

    public TestPrepActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_test_prep, container, false);

        Intent presentIntent = getActivity().getIntent();
        Integer timeValue = presentIntent.getIntExtra("timeValue", 15);
        Integer sectionNumber = presentIntent.getIntExtra("sectionNumber", 1);

        ScrollView scrollView = (ScrollView) getActivity().findViewById(R.id.scrollViewTestPrep);

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams linLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        testingProblemList = new ArrayList<>();
        final WordProblem wordProblemTest = new WordProblem("If John walks 5 miles in 6 hours, how far did he walk?", "miles", "30");
        testingProblemList.add(wordProblemTest);
        testingProblemList.add(wordProblemTest);
        testingProblemList.add(wordProblemTest);

        int totalNumberOfTestingProblems = getResources().getInteger(R.integer.section1_test_total_problems);

        // TODO: Remove line below once ArrayList properly fetches information
        totalNumberOfTestingProblems = 3;

        testingProblemNumber = 1;
        for (int i=0; i < totalNumberOfTestingProblems; i++) {
            Problem currProblem = testingProblemList.get(i);
            String probType = currProblem.getProblemType();
            if (probType == "word") {
                WordProblem wordProblem = (WordProblem) currProblem;

                TextView textViewQ = new TextView(getActivity());
                EditText editTextA = new EditText(getActivity());

                textViewQ.setText(String.valueOf(testingProblemNumber) + ". " + wordProblem.getText());
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

                testingProblemNumber += 1;

            }
        }

        Button submitButtonTest = new Button(getActivity());
        submitButtonTest.setText(getString(R.string.testprep_submit_test));
        submitButtonTest.setGravity(Gravity.CENTER_HORIZONTAL);

        linearLayout.addView(submitButtonTest);

        submitButtonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Set onClick actions after test completion
                Intent testResultsIntent = new Intent(getActivity(), TestResults.class);
                startActivity(testResultsIntent);
            }
        });

        scrollView.removeAllViews();
        scrollView.addView(linearLayout);

        final TextView textViewTimeRemaining = (TextView) getActivity().findViewById(R.id.textViewTimeRemaining);

        CountDownTimer countDownTimer = new CountDownTimer(TimeUnit.MINUTES.toMillis(timeValue), 1000) {
            @Override
            public void onTick(long l) {
                textViewTimeRemaining.setText(getString(R.string.testprep_time_remaining) + String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(l),
                        TimeUnit.MILLISECONDS.toSeconds(l) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l))
                ));
            }

            @Override
            public void onFinish() {
                textViewTimeRemaining.setText(getString(R.string.testprep_time_elapsed));
            }
        };
        countDownTimer.start();




        return rootView;
    }
}
