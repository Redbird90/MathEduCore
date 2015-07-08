package app.studio.jkt.com.learnmath;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
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
import java.util.concurrent.TimeUnit;


/**
 * A placeholder fragment containing a simple view.
 */
public class TestPrepActivityFragment extends Fragment {

    private int testingProblemNumber;
    public ArrayList<Problem> testingProblemList;
    public boolean[] testingProbCorrect;
    public boolean[] testingProbAnswered;
    private ArrayList<String> correctAnswers;
    private int timeValueSec;
    private int timeRemainingSec;
    private int sectionNumber;
    private boolean atReview;
    private int totAvailReviewTimeSec;
    private int totalNumberOfTestingProblems;

    public TestPrepActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_test_prep, container, false);

        Log.i("TPTEST", String.valueOf(totAvailReviewTimeSec));

        Intent presentIntent = getActivity().getIntent();
        final Integer timeValueMin = presentIntent.getIntExtra("timeValue", 15);
        sectionNumber = presentIntent.getIntExtra("sectionNumber", 1);

        timeValueSec = timeValueMin * 60;
        timeRemainingSec = timeValueSec;
        atReview = false;

        ScrollView scrollView = (ScrollView) rootView.findViewById(R.id.scrollViewTestPrep);

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams linLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        testingProblemList = new ArrayList<>();
        final WordProblem wordProblemTest = new WordProblem("If John walks 5 miles in 6 hours, how far did he walk?", "miles", "30");
        testingProblemList.add(wordProblemTest);
        testingProblemList.add(wordProblemTest);
        testingProblemList.add(wordProblemTest);

        if (sectionNumber == 1) {
            totalNumberOfTestingProblems = getResources().getInteger(R.integer
                    .section1_test_total_problems);
        }
        // TODO: Remove line below once ArrayList properly fetches information
        totalNumberOfTestingProblems = 3;

        testingProbAnswered = new boolean[totalNumberOfTestingProblems];
        testingProbCorrect = new boolean[totalNumberOfTestingProblems];

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

        final TextView textViewTimeRemaining = (TextView) rootView.findViewById(R.id
                .textViewTestTimeSpent);

        // TODO: Resolve bug with back button pressed at test time (if needed)

        final CountDownTimer countDownTimer = new CountDownTimer(TimeUnit.MINUTES.toMillis
                (timeValueMin), 1000) {
            @Override
            public void onTick(long l) {
                textViewTimeRemaining.setText(getString(R.string.testprep_time_remaining) + String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(l),
                        TimeUnit.MILLISECONDS.toSeconds(l) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l))
                ));
                timeRemainingSec = (int) TimeUnit.MILLISECONDS.toSeconds(l);
            }

            @Override
            public void onFinish() {
                textViewTimeRemaining.setText(getString(R.string.testprep_time_elapsed));
                Intent testResultsIntent2 = new Intent(getActivity(), TestResults.class);

                testing

                testResultsIntent2.putExtra("timeElapsed", true);
                testResultsIntent2.putExtra("timeRemainingSec", 0);
                int timeSpentSec = timeValueSec;
                testResultsIntent2.putExtra("timeSpentSec", timeValueSec);
                int grade = getGrade();
                testResultsIntent2.putExtra("grade", grade);
                testResultsIntent2.putExtra("gradeNewBest", updateGetGradeNewBest());
                if (!atReview) {
                    testResultsIntent2.putExtra("anyReview", false);
                    testResultsIntent2.putExtra("timeInReviewSec", 0);
                } else {
                    int timeInReview = totAvailReviewTimeSec - timeRemainingSec;
                    testResultsIntent2.putExtra("timeInReviewSec", timeInReview);
                }
                testResultsIntent2.putExtra("problemsAnswered", getProbAnswered());
                testResultsIntent2.putExtra("problemsCorrect", getProbCorrect());

                updateRelevantPrefs(timeSpentSec);
                startActivity(testResultsIntent2);
            }
        };

        Button submitButtonTest = new Button(getActivity());
        submitButtonTest.setText(getString(R.string.testprep_submit_test));
        submitButtonTest.setGravity(Gravity.CENTER_HORIZONTAL);
        submitButtonTest.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT));

        final Button reviewButtonBot = new Button(getActivity());
        reviewButtonBot.setText(getString(R.string.testprep_button_review));
        reviewButtonBot.setGravity(Gravity.CENTER_HORIZONTAL);
        reviewButtonBot.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        linearLayout.addView(reviewButtonBot);
        linearLayout.addView(submitButtonTest);

        final Button reviewButtonTop = (Button) rootView.findViewById(R.id.buttonTestingRevTop);

        submitButtonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Set onClick actions after test completion
                Intent testResultsIntent = new Intent(getActivity(), TestResults.class);

                countDownTimer.cancel();

                testResultsIntent.putExtra("timeElapsed", false);
                testResultsIntent.putExtra("timeRemainingSec", timeRemainingSec);
                int timeSpentSec = timeValueSec - timeRemainingSec;
                testResultsIntent.putExtra("timeSpentSec", timeSpentSec);
                testResultsIntent.putExtra("grade", getGrade());
                testResultsIntent.putExtra("gradeNewBest", updateGetGradeNewBest());
                if (!atReview) {
                    testResultsIntent.putExtra("anyReview", false);
                    testResultsIntent.putExtra("timeInReviewSec", 0);
                } else {
                    int timeInReview = totAvailReviewTimeSec;
                    testResultsIntent.putExtra("timeInReviewSec", timeInReview);
                }
                int problemsAnswered = getProbAnswered();
                testResultsIntent.putExtra("problemsAnswered", problemsAnswered);
                testResultsIntent.putExtra("problemsCorrect", getProbCorrect());

                updateRelevantPrefs(timeSpentSec);
                startActivity(testResultsIntent);
            }
        });

        reviewButtonTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TestPrepActFrag", "reviewButtonTopPressed");
                reviewButtonPressed(reviewButtonTop, reviewButtonBot);
            }
        });

        reviewButtonBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TestPrepActFrag", "reviewButtonBotPressed");
                reviewButtonPressed(reviewButtonTop, reviewButtonBot);
            }
        });




        scrollView.removeAllViews();
        scrollView.addView(linearLayout);

        countDownTimer.start();


        return rootView;
    }

    private boolean updateGetGradeNewBest() {
    }

    private void updateRelevantPrefs(int timeSpentSec) {
        // Relevant Prefs:
        // STUDYSTATS
        // time spent? (skip)
        // problems tackled, defeated
        // best test score, last test score
        // SECTION TP
        // Prob Answered
        // Prob Correct
        // Fastest Completion Time
        // Avg Test Review Time
        // Avg Test Completion Time
        // BADGES
        // Individual Badge bools

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("progress",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int studyTime = sharedPreferences.getInt("study_time", 3);
        studyTime += timeSpentSec;
        editor.putInt("study_time", studyTime);

        int problemsTackled = sharedPreferences.getInt("problems_tackled", 3);
        problemsTackled += problemsAnswered;
        editor.putInt("problems_tackled", problemsTackled);

        int problemsDefeated = sharedPreferences.getInt("problems_defeated", 3);
        problemsDefeated += problemsCorrect;
        editor.putInt("problems_defeated", problemsDefeated);

        int bestTestGrade = sharedPreferences.getInt("best_score", -1);
        if (bestTestGrade == -1) {
            bestTestGrade = currGrade;
        } else {
            if (currGrade < bestTestGrade) {
                bestTestGrade = currGrade;
            }
        }
        editor.putInt("best_score", bestTestGrade);

        editor.putInt("last_score", currGrade);

        String prefix = "section" + String.valueOf(sectionNumber) + ".";

        int testProbAttempted = sharedPreferences.getInt(prefix + "test_problems_attempted", 3);
        if (problemsAnswered > testProbAttempted) {
            testProbAttempted = problemsAnswered;
        }
        editor.putInt(prefix + "test_problems_attempted", testProbAttempted);

        int testProbCorrect = sharedPreferences.getInt(prefix + "test_problems_correct", 3);
        if (problemsCorrect > testProbCorrect) {
            testProbCorrect = problemsCorrect;
        }
        editor.putInt(prefix + "test_problems_correct", testProbCorrect);

        boolean badge1unlocked = sharedPreferences.getBoolean(prefix + "test_b1unlocked", false);
        boolean badge2unlocked = sharedPreferences.getBoolean(prefix + "test_b2unlocked", false);
        boolean badge3unlocked = sharedPreferences.getBoolean(prefix + "test_b3unlocked", false);
        boolean badge4unlocked = sharedPreferences.getBoolean(prefix + "test_b4unlocked", false);
        boolean badge5unlocked = sharedPreferences.getBoolean(prefix + "test_b5unlocked", false);
        boolean badge6unlocked = sharedPreferences.getBoolean(prefix + "test_b6unlocked", false);








    }

    private boolean getGradeNewBest() {
        return false;
    }

    private int getGrade() {
        // Grade values will be on a 0 - 9 int scale
        // A+, A, A-, B+, B, B-, C+, C, C-, D or lower
        // 0,  1, 2,  3,  4, 5,  6,  7, 8,  9

        // TODO: Calculate grade and return proper value
        return 5;

    }

    private int getProbAnswered() {
        // TODO: Fill in subroutine logic for getProbAnswered()
        return 8;
    }

    private int getProbCorrect() {
        // TODO: Fill in subroutine logic for getProbCorrect()
        return 6;
    }

    // TODO: Fix layout of bottom two buttons

    private void reviewButtonPressed(final Button reviewBtnTop, final Button reviewBtnBot) {
        Log.i("TestPrepActFrag", "reviewButtonPressedStarted");

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.testing_rev_dialog_title))
                .setMessage(getString(R.string.testing_rev_dialog_msg))
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        atReview = true;
                        totAvailReviewTimeSec = timeRemainingSec;
                        reviewBtnTop.setEnabled(false);
                        reviewBtnBot.setEnabled(false);
                        reviewBtnTop.setText(R.string.testing_rev_btn_pressed);
                        reviewBtnBot.setText(R.string.testing_rev_btn_pressed);
                    }
                })
                .setNegativeButton(R.string.not_yet, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        builder.show();
        Log.i("TestPrepActFrag", "reviewButtonPressedFinished");

    }
}
