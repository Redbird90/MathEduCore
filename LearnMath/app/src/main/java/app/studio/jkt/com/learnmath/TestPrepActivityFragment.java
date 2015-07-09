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
    public ArrayList incorrectProblemKeys;
    public ArrayList<String> incorrectAnswers;
    private ArrayList<String> correctAnswers;
    private ArrayList<EditText> editObjects;
    private ArrayList<Integer> arrayTestNewBadges;
    private int timeValueSec;
    private int timeRemainingSec;
    private int sectionNumber;
    private boolean atReview;
    private int totAvailReviewTimeSec;
    private int totalNumberOfTestingProblems;
    public ArrayList<Problem> testProblemList;

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
        final String sectionTitle = presentIntent.getStringExtra(Intent.EXTRA_TEXT);

        timeValueSec = timeValueMin * 60;
        timeRemainingSec = timeValueSec;
        atReview = false;

        ScrollView scrollView = (ScrollView) rootView.findViewById(R.id.scrollViewTestPrep);

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams linLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        if (sectionNumber == 1) {
            totalNumberOfTestingProblems = getResources().getInteger(R.integer
                    .section1_test_total_problems);
            // Add problem information from problems.xml to testingProblemList
            Problem problem1 = new WordProblem(getString(R.string.section1_test_problem1_q), getString(R.string.section1_test_problem1_a));
            testingProblemList.add(problem1);
            Problem problem2 = new WordProblem(getString(R.string.section1_test_problem2_q), getString(R.string.section1_practice_problem2_a));
            testingProblemList.add(problem2);
            Problem problem3 = new WordProblem(getString(R.string.section1_test_problem3_q), getString(R.string.section1_practice_problem3_a));
            testingProblemList.add(problem3);
            Problem problem4 = new WordProblem(getString(R.string.section1_test_problem4_q), getString(R.string.section1_test_problem4_a));
            testingProblemList.add(problem4);
            Problem problem5 = new WordProblem(getString(R.string.section1_test_problem5_q), getString(R.string.section1_test_problem5_a));
            testingProblemList.add(problem5);
        } else {
            Log.e("TestPrepActFrag", "SECTION NUMBER UPDATED INCORRECTLY");
        }


        testingProblemNumber = 1;
        correctAnswers = new ArrayList<String>(totalNumberOfTestingProblems);
        editObjects = new ArrayList<EditText>(totalNumberOfTestingProblems);
        testingProbAnswered = new boolean[totalNumberOfTestingProblems];
        testingProbCorrect = new boolean[totalNumberOfTestingProblems];

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

                editObjects.add(editTextA);
                correctAnswers.add(currProblem.getAnswer());
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

                int timeSpentSec = timeValueSec;
                int timeInReviewSeconds;
                int problemsAnswered = updateGetProbAnswered(editObjects);
                int problemsCorrect = updateGetProbCorrect(editObjects);
                int grade = getGrade(problemsCorrect);

                testResultsIntent2.putExtra("sectionNumber", sectionNumber);
                testResultsIntent2.putExtra(Intent.EXTRA_TEXT, sectionTitle);
                testResultsIntent2.putExtra("timeElapsed", true);
                testResultsIntent2.putExtra("timeRemainingSec", 0);

                testResultsIntent2.putExtra("timeSpentSec", timeSpentSec);

                testResultsIntent2.putExtra("grade", grade);
                testResultsIntent2.putExtra("gradeNewBest", updateGetGradeNewBest(grade));

                if (!atReview) {
                    timeInReviewSeconds = 0;
                    testResultsIntent2.putExtra("timeInReviewSec", timeInReviewSeconds);
                } else {
                    timeInReviewSeconds = totAvailReviewTimeSec - timeRemainingSec;
                    testResultsIntent2.putExtra("timeInReviewSec", timeInReviewSeconds);
                }

                testResultsIntent2.putExtra("problemsAnswered", problemsAnswered);

                testResultsIntent2.putExtra("problemsCorrect", problemsCorrect);
                testResultsIntent2.putExtra("problemsTotal", totalNumberOfTestingProblems);

                testResultsIntent2.putExtra("incorrectProbKeys", incorrectProblemKeys);
                testResultsIntent2.putExtra("incorrectAnswers", incorrectAnswers);

                updateRelevantPrefs(timeSpentSec, problemsAnswered, problemsCorrect, grade, timeInReviewSeconds);

                testResultsIntent2.putExtra("testNewBadgesArray", arrayTestNewBadges);

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

                Intent testResultsIntent = new Intent(getActivity(), TestResults.class);

                countDownTimer.cancel();
                int timeSpentSec = timeValueSec - timeRemainingSec;
                int timeInReviewSec;
                int problemsAnswered = updateGetProbAnswered(editObjects);
                int problemsCorrect = updateGetProbCorrect(editObjects);
                int grade = getGrade(problemsCorrect);

                testResultsIntent.putExtra("sectionNumber", sectionNumber);
                testResultsIntent.putExtra(Intent.EXTRA_TEXT, sectionTitle);
                testResultsIntent.putExtra("timeElapsed", false);
                testResultsIntent.putExtra("timeRemainingSec", timeRemainingSec);

                testResultsIntent.putExtra("timeSpentSec", timeSpentSec);

                testResultsIntent.putExtra("grade", grade);
                testResultsIntent.putExtra("gradeNewBest", updateGetGradeNewBest(grade));

                if (!atReview) {
                    timeInReviewSec = 0;
                    testResultsIntent.putExtra("timeInReviewSec", timeInReviewSec);
                } else {
                    timeInReviewSec = totAvailReviewTimeSec;
                    testResultsIntent.putExtra("timeInReviewSec", timeInReviewSec);
                }

                testResultsIntent.putExtra("problemsAnswered", problemsAnswered);

                testResultsIntent.putExtra("problemsCorrect", problemsCorrect);
                testResultsIntent.putExtra("problemsTotal", totalNumberOfTestingProblems);

                testResultsIntent.putExtra("incorrectProbKeys", incorrectProblemKeys);
                testResultsIntent.putExtra("incorrectAnswers", incorrectAnswers);

                updateRelevantPrefs(timeSpentSec, problemsAnswered, problemsCorrect, grade, timeInReviewSec);

                testResultsIntent.putExtra("testNewBadgesArray", arrayTestNewBadges);

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

    // END onCreateView()


    private boolean updateGetGradeNewBest(int currentGrade) {

        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("progress", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPrefs.edit();

        String prefix = "section" + String.valueOf(sectionNumber) + ".";

        boolean best = false;

        int oldBest = sharedPrefs.getInt(prefix + "test_best_grade", -1);
        if (oldBest == -1) {
            oldBest = currentGrade;
            best = true;
        } else {
            if (oldBest < currentGrade) {
                oldBest = currentGrade;
                best = true;
            }
        }
        prefsEditor.putInt(prefix + "test_best_grade", oldBest);

        prefsEditor.commit();

        return best;
    }

    private void updateRelevantPrefs(int timeSpentSec, int problemsAnswered, int problemsCorrect, int currGrade, int timeInReviewSec) {
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

        arrayTestNewBadges = new ArrayList<Integer>();

        boolean badge1unlocked = sharedPreferences.getBoolean(prefix + "test_b1unlocked", false);
        boolean badge2unlocked = sharedPreferences.getBoolean(prefix + "test_b2unlocked", false);
        boolean badge3unlocked = sharedPreferences.getBoolean(prefix + "test_b3unlocked", false);
        boolean badge4unlocked = sharedPreferences.getBoolean(prefix + "test_b4unlocked", false);
        boolean badge5unlocked = sharedPreferences.getBoolean(prefix + "test_b5unlocked", false);
        boolean badge6unlocked = sharedPreferences.getBoolean(prefix + "test_b6unlocked", false);

        float percentCorrect = problemsCorrect/totalNumberOfTestingProblems;
        if (!badge1unlocked &&  percentCorrect > 0.70) {
            editor.putBoolean(prefix + "test_b1unlocked", false);
            arrayTestNewBadges.add(1);
        }
        if (!badge2unlocked && percentCorrect > 0.80) {
            editor.putBoolean(prefix + "test_b2unlocked", false);
            arrayTestNewBadges.add(2);
        }
        if (!badge3unlocked && percentCorrect > 0.90) {
            editor.putBoolean(prefix + "test_b3unlocked", false);
            arrayTestNewBadges.add(3);
        }
        if (!badge4unlocked && problemsAnswered == totalNumberOfTestingProblems) {
            editor.putBoolean(prefix + "test_b4unlocked", false);
            arrayTestNewBadges.add(4);
        }
        if (!badge5unlocked && TimeUnit.SECONDS.toMinutes((long) timeInReviewSec) >= 4) {
            editor.putBoolean(prefix + "test_b5unlocked", false);
            arrayTestNewBadges.add(5);
        }
        if (!badge6unlocked && TimeUnit.SECONDS.toMinutes((long) timeSpentSec) < 10) {
            editor.putBoolean(prefix + "test_b6unlocked", false);
            arrayTestNewBadges.add(6);
        }

        int fastestTime = sharedPreferences.getInt("test_fastest_time", 3);
        if (fastestTime == -1) {
            fastestTime = timeSpentSec;
        } else {
            if (fastestTime > timeSpentSec) {
                fastestTime = timeSpentSec;
            }
        }
        editor.putInt("test_fastest_time", fastestTime);

        int avgReviewTime = sharedPreferences.getInt("test_avg_review_time", -1);
        int numberRevTimes = sharedPreferences.getInt("test_avg_review_numbers", 0);
        if  (numberRevTimes == 0) {
            avgReviewTime = timeInReviewSec;
            numberRevTimes += 1;
        } else {
            numberRevTimes += 1;
            avgReviewTime = (avgReviewTime + timeInReviewSec) / (numberRevTimes);
        }
        editor.putInt("test_avg_review_time", avgReviewTime);
        editor.putInt("test_avg_review_numbers", numberRevTimes);

        int avgCompletionTime = sharedPreferences.getInt("test_avg_completion_time", -1);
        int numberCompletionTimes = sharedPreferences.getInt("test_avg_completion_numbers", 0);
        if (numberCompletionTimes == 0) {
            avgCompletionTime = timeSpentSec;
            numberCompletionTimes += 1;
        } else {
            numberCompletionTimes += 1;
            avgCompletionTime = (avgCompletionTime + timeSpentSec) / (numberCompletionTimes);
        }
        editor.putInt("test_avg_completion_time", avgCompletionTime);
        editor.putInt("test_avg_completion_numbers", numberCompletionTimes);

        editor.commit();

    }

    private int getGrade(int numCorrect) {
        // Grade values will be on a 0 - 9 int scale
        // A+, A, A-, B+, B, B-, C+, C, C-, D or lower
        // 0,  1, 2,  3,  4, 5,  6,  7, 8,  9

        int gradeNum;
        float percCorrect = numCorrect/totalNumberOfTestingProblems;
        if (percCorrect >= 0.97) {
            gradeNum = 0;
        } else if (percCorrect >= 0.93) {
            gradeNum = 1;
        } else if (percCorrect >= 0.90) {
            gradeNum = 2;
        } else if (percCorrect >= 0.87) {
            gradeNum = 3;
        } else if (percCorrect >= 0.83) {
            gradeNum = 4;
        } else if (percCorrect >= 0.80) {
            gradeNum = 5;
        } else if (percCorrect >= 0.77) {
            gradeNum = 6;
        } else if (percCorrect >= 0.75) {
            gradeNum = 7;
        } else if (percCorrect >= 0.73) {
            gradeNum = 8;
        } else {
            gradeNum = 9;
        }

        return gradeNum;

    }

    private int updateGetProbAnswered(ArrayList<EditText> answerLines) {

        SharedPreferences shPrefs = getActivity().getSharedPreferences("progress", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shPrefs.edit();

        int numAnswered = 0;
        String prefix = "section" + String.valueOf(sectionNumber) + ".";
        for (int i=0; i < totalNumberOfTestingProblems; i++) {
            String prob_prefix = "problem" + String.valueOf(i + 1) + ".";
            boolean wasAnswered = shPrefs.getBoolean(prefix + prob_prefix + "test_attempted", false);
            if (!wasAnswered) {
                String answer = answerLines.get(i).getText().toString();
                if (answer.length() > 0) {
                    testingProbAnswered[i] = true;
                    numAnswered += 1;
                    editor.putBoolean(prefix + prob_prefix + "test_attempted", true);
                }
            }
        }

        editor.commit();
        return numAnswered;
    }

    private int updateGetProbCorrect(ArrayList<EditText> answerLines) {

        SharedPreferences shPrefs = getActivity().getSharedPreferences("progress", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shPrefs.edit();


        int numCorrect = 0;
        incorrectProblemKeys = new ArrayList();
        incorrectAnswers = new ArrayList<String>();
        String prefix = "section" + String.valueOf(sectionNumber) + ".";
        for (int i=0; i < totalNumberOfTestingProblems; i++) {
            String prob_prefix = "problem" + String.valueOf(i + 1) + ".";
            boolean wasCorrect = shPrefs.getBoolean(prefix + prob_prefix + "test_correct", false);
            String answer = answerLines.get(i).getText().toString();
            if (!wasCorrect) {
                if (answer == correctAnswers.get(i)) {
                    testingProbCorrect[i] = true;
                    numCorrect += 1;
                    editor.putBoolean(prefix + prob_prefix + "test_correct", true);
                }
            }
            if (!(answer == correctAnswers.get(i))) {
                incorrectProblemKeys.add(i+1);
                incorrectAnswers.add(answer);
            }
        }

        editor.commit();
        return numCorrect;
    }

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
