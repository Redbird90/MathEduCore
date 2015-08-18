package app.studio.jkt.com.learnmath;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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


/**
 * A placeholder fragment containing a simple view.
 */
public class PracticeActivityFragment extends Fragment {

    private int problemNumber;
    private int sectionNumber;
    private boolean pracCorrNewBest;
    private boolean[] pracCurrAnswered;
    private boolean[] pracCurrCorrect;
    private boolean[] pracPriorAnswered;
    private boolean[] pracPriorCorrect;
    public ArrayList<Problem> pracProblemList;
    private ArrayList pracIncorrectProbKeys;
    private ArrayList<String> pracIncorrectAnswers;
    private ArrayList<String> pracCorrectAnswers;
    private ArrayList<EditText> pracEditObjects;
    private ArrayList<Integer> arrayPracNewBadges;
    private int totalNumPracProblems;


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
        final String sectionTitle = currIntent.getStringExtra(Intent.EXTRA_TEXT);
        sectionNumber = currIntent.getIntExtra("sectionNumber", 1);


        pracProblemList = new ArrayList<Problem>();
        if (sectionNumber == 1) {
            totalNumPracProblems = getResources().getInteger(R.integer.section1_prac_total_problems);
            Problem problem1 = new WordProblem(getString(R.string.section1_practice_problem1_q), getString(R.string.section1_practice_problem1_a));
            pracProblemList.add(problem1);
            Problem problem2 = new WordProblem(getString(R.string.section1_practice_problem2_q), getString(R.string.section1_practice_problem2_a));
            pracProblemList.add(problem2);
            Problem problem3 = new WordProblem(getString(R.string.section1_practice_problem3_q), getString(R.string.section1_test_problem3_a));
            pracProblemList.add(problem3);
            Problem problem4 = new WordProblem(getString(R.string.section1_practice_problem4_q), getString(R.string.section1_practice_problem4_a));
            pracProblemList.add(problem4);
            Problem problem5 = new WordProblem(getString(R.string.section1_practice_problem5_q), getString(R.string.section1_practice_problem5_a));
            pracProblemList.add(problem5);
        } else {
            Log.e("PracActivityFrag", "SECTION NUMBER UPDATED INCORRECTLY");
        }

        problemNumber = 1;
        pracCorrectAnswers = new ArrayList<String>(totalNumPracProblems);
        pracEditObjects = new ArrayList<EditText>(totalNumPracProblems);
        pracCurrAnswered = new boolean[totalNumPracProblems];
        pracCurrCorrect = new boolean[totalNumPracProblems];
        pracPriorAnswered = new boolean[totalNumPracProblems];
        pracPriorCorrect = new boolean[totalNumPracProblems];

        for (int i=0; i< pracProblemList.size(); i++) {
            Problem currProblem = pracProblemList.get(i);
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
                LinearLayout.LayoutParams editLayoutParams = new LinearLayout.LayoutParams(dip
                        (getActivity(), 100), dip(getActivity(), 50));
                editLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;

                textViewQ.setLayoutParams(textQLayoutParams);
                editTextA.setLayoutParams(editLayoutParams);

                linearLayout.addView(textViewQ);
                linearLayout.addView(editTextA);

                pracEditObjects.add(editTextA);
                pracCorrectAnswers.add(currProblem.getAnswer());
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
                Intent currIntent = getActivity().getIntent();

                // TODO: Create method to check answers in all edit text fields
                int problemsAnswered = updateGetProbAnswered(pracEditObjects);
                int problemsCorrect = updateGetProbCorrect(pracEditObjects);
                // Sample answer check pass
                practiceCompleteView.putExtra(Intent.EXTRA_TEXT, sectionTitle);
                practiceCompleteView.putExtra("sectionNumber", currIntent.getIntExtra("sectionNumber", 1));
                practiceCompleteView.putExtra("numTotalAnswers", problemsAnswered);
                practiceCompleteView.putExtra("numCorrectAnswers", problemsCorrect);
                practiceCompleteView.putExtra("numTotalQuestions", totalNumPracProblems);
                practiceCompleteView.putExtra("arrayIncorrectProbKeys", pracIncorrectProbKeys);
                practiceCompleteView.putExtra("pracIncorrectAnswers", pracIncorrectAnswers);
                practiceCompleteView.putExtra("pracNewBest", pracCorrNewBest);

                updateRelevantPracPrefs(problemsCorrect, problemsAnswered);

                practiceCompleteView.putExtra("arrayNewBadges", arrayPracNewBadges);

                startActivity(practiceCompleteView);
            }
        });

        scrollableView.removeAllViews();
        scrollableView.addView(linearLayout);

        return rootView;
    }

    private void updateRelevantPracPrefs(int numAnswers, int numCorrectAnswers) {
        // Relevant Prefs:
        // STUDYSTATS
        // time spent? (skip)
        // problems tackled, defeated
        // best test score, last test score
        // SECTION P
        // Attempted
        // Correct
        // Individual badge bools


        SharedPreferences shPreferences = getActivity().getSharedPreferences("progress", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shPreferences.edit();

        // TODO: Keep track of and update study time


        int problemAttempts = shPreferences.getInt("problems_tackled", 3);
        problemAttempts += numAnswers;
        editor.putInt("problems_tackled", problemAttempts);

        int probDefeats = shPreferences.getInt("problems_defeated", 3);
        probDefeats += numCorrectAnswers;
        editor.putInt("problems_defeated", probDefeats);

        String prefix = "section" + String.valueOf(sectionNumber) + ".";

        int totPracProbAttempted = 0;
        int totPracProbCorrect = 0;
        for (int x=0; x < totalNumPracProblems; x++) {
            if (pracPriorAnswered[x]) {
                totPracProbAttempted += 1;
            } else if (pracCurrAnswered[x]) {
                totPracProbAttempted += 1;
            }
            if (pracPriorCorrect[x]) {
                totPracProbCorrect += 1;
            } else if (pracCurrCorrect[x]) {
                totPracProbCorrect += 1;
            }
        }
        editor.putInt(prefix + "prac_problems_attempted", totPracProbAttempted);
        editor.putInt(prefix + "prac_problems_correct", totPracProbCorrect);

        arrayPracNewBadges = new ArrayList<Integer>();

        boolean badge1unlock = shPreferences.getBoolean(prefix + "prac_b1unlocked", false);
        boolean badge2unlock = shPreferences.getBoolean(prefix + "prac_b2unlocked", false);
        boolean badge3unlock = shPreferences.getBoolean(prefix + "prac_b3unlocked", false);

        if (!badge1unlock && numAnswers >= totalNumPracProblems/2) {
            editor.putBoolean(prefix + "prac_b1unlocked", true);
            arrayPracNewBadges.add(1);
        }
        if (!badge2unlock && numCorrectAnswers >= totalNumPracProblems/2) {
            editor.putBoolean(prefix + "prac_b2unlocked", true);
            arrayPracNewBadges.add(2);
        }
        if (!badge3unlock && numCorrectAnswers == totalNumPracProblems) {
            editor.putBoolean(prefix + "prac_b3unlocked", true);
            arrayPracNewBadges.add(3);
        }

        editor.commit();

    }

    private int updateGetProbAnswered(ArrayList<EditText> editLinesArray) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("progress", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int numAnswered = 0;
        String prefix = "section" + String.valueOf(sectionNumber) + ".";
        for (int i=0; i < editLinesArray.size(); i++) {
            String prob_prefix = "problem" + String.valueOf(i + 1) + ".";
            boolean wasAnswered = sharedPreferences.getBoolean(prefix + prob_prefix + "practice_attempted", false);
            String answer = editLinesArray.get(i).getText().toString();
            if (!wasAnswered) {
                if (answer.length() > 0) {
                    pracCurrAnswered[i] = true;
                    numAnswered += 1;
                    editor.putBoolean(prefix + prob_prefix + "practice_attempted", true);
                }
            } else {
                if (answer.length() > 0) {
                    pracCurrAnswered[i] = true;
                    numAnswered += 1;
                }
                pracPriorAnswered[i] = true;
            }
        }
        editor.commit();

        return  numAnswered;
    }

    private int updateGetProbCorrect(ArrayList<EditText> editAnsArray) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("progress", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        int numCorrect = 0;
        pracIncorrectProbKeys = new ArrayList<Integer>();
        pracIncorrectAnswers = new ArrayList<String>();
        String prefix = "section" + String.valueOf(sectionNumber) + ".";
        int priorNumCorrect = sharedPreferences.getInt(prefix + "practice_problems_correct", totalNumPracProblems);
        for (int i=0; i < totalNumPracProblems; i++) {
            String prob_prefix = "problem" + String.valueOf(i + 1) + ".";
            boolean wasCorrect = sharedPreferences.getBoolean(prefix + prob_prefix + "test_correct", false);
            String answer = editAnsArray.get(i).getText().toString().trim();
            if (!wasCorrect) {
                if (answer == pracCorrectAnswers.get(i)) {
                    pracCurrCorrect[i] = true;
                    numCorrect += 1;
                    editor.putBoolean(prefix + prob_prefix + "practice_correct", true);
                }
            } else {
                if (answer == pracCorrectAnswers.get(i)) {
                    pracCurrCorrect[i] = true;
                    numCorrect += 1;
                }
                pracPriorCorrect[i] = true;
            }
            if (answer != pracCorrectAnswers.get(i)) {
                pracIncorrectProbKeys.add(i+1);
                pracIncorrectAnswers.add(answer);
            }
        }

        if (numCorrect > priorNumCorrect) {
            pracCorrNewBest = true;
        } else {
            pracCorrNewBest = false;
        }

        editor.commit();
        return numCorrect;

    }

    public String checkForNullViews(View a) {
        if (a == null) {
            return "View is NULL";
        } else {
            return "View is non-null";
        }
    }

    public static int dip(Context context, int pixels) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pixels * scale + 0.5f);
    }
}
