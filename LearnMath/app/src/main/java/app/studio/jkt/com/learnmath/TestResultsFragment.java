package app.studio.jkt.com.learnmath;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


/**
 * A placeholder fragment containing a simple view.
 */
public class TestResultsFragment extends Fragment {

    private int sectionNumber;
    private String sectionTitle;
    private ArrayList<Problem> incorrectProblemList;
    private ArrayList<Integer> newestBadgesArray;

    public TestResultsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_test_results, container, false);

        Intent currResultsIntent = getActivity().getIntent();
        sectionTitle = currResultsIntent.getStringExtra(Intent.EXTRA_TEXT);
        sectionNumber = currResultsIntent.getIntExtra("sectionNumber", 1);
        boolean timeElapsed = currResultsIntent.getBooleanExtra("timeElapsed", false);
        int timeRemainingSec = currResultsIntent.getIntExtra("timeRemainingSec", 60);
        int timeSpentSec = currResultsIntent.getIntExtra("timeSpentSec", (60 * 15));
        int testingGrade = currResultsIntent.getIntExtra("grade", 7);
        boolean newBestGrade = currResultsIntent.getBooleanExtra("gradeNewBest", false);
        int timeInReviewSec = currResultsIntent.getIntExtra("timeInReviewSec", 0);
        int probsAnswered = currResultsIntent.getIntExtra("problemsAnswered", 0);
        int probsCorrect = currResultsIntent.getIntExtra("problemsCorrect", 0);
        int totalProblems = currResultsIntent.getIntExtra("problemsTotal", getResources().getInteger(R.integer.section1_test_total_problems));
        ArrayList<Integer> incorrectProblemKeys = currResultsIntent.getIntegerArrayListExtra("incorrectProbKeys");
        final ArrayList<String> incorrectAnswers = currResultsIntent.getStringArrayListExtra("incorrectAnswers");
        newestBadgesArray = currResultsIntent.getIntegerArrayListExtra("testNewBadgesArray");

        incorrectProblemList = new ArrayList<Problem>(incorrectProblemKeys.size());
        for (int i=0; i < incorrectProblemKeys.size(); i++) {

            // Initialize currIncProblem in case cases not satisfied
            Problem currIncProblem = null;
            if (sectionNumber == 1) {
                switch (incorrectProblemKeys.get(i)) {
                    case 1:
                        currIncProblem = new WordProblem(getString(R.string.section1_test_problem1_q), getString(R.string.section1_test_problem1_a));
                    case 2:
                        currIncProblem = new WordProblem(getString(R.string.section1_practice_problem2_q), getString(R.string.section1_practice_problem3_a));
                    case 3:
                        currIncProblem = new WordProblem(getString(R.string.section1_test_problem3_q), getString(R.string.section1_test_problem3_a));
                    case 4:
                        currIncProblem = new WordProblem(getString(R.string.section1_test_problem4_q), getString(R.string.section1_test_problem4_a));
                    case 5:
                        currIncProblem = new WordProblem(getString(R.string.section1_test_problem5_q), getString(R.string.section1_test_problem5_a));
                }
                incorrectProblemList.add(currIncProblem);
            }
        }

        ImageView imageViewScore = (ImageView) rootView.findViewById(R.id.imageViewScore);
        TextView textViewProbCorrect = (TextView) rootView.findViewById(R.id
                .textViewTestProbCorrect);
        TextView textViewProbAnswered = (TextView) rootView.findViewById(R.id
                .textViewTestProbAnswered);
        TextView textViewNewBest = (TextView) rootView.findViewById(R.id.textViewTestNewBest);
        TextView textViewTimeSpent = (TextView) rootView.findViewById(R.id
                .textViewTestTimeSpent);
        TextView textViewTimeRemaining = (TextView) rootView.findViewById(R.id
                .textViewTestTimeRem);
        TextView textViewTimeReviewed = (TextView) rootView.findViewById(R.id
                .textViewTestTimeRev);
        ImageView imageViewBadge1 = (ImageView) rootView.findViewById(R.id
                .imageViewTestBadge1);
        ImageView imageViewBadge2 = (ImageView) rootView.findViewById(R.id
                .imageViewTestBadge2);
        ImageView imageViewBadge3 = (ImageView) rootView.findViewById(R.id
                .imageViewTestBadge3);
        ImageView imageViewBadge4 = (ImageView) rootView.findViewById(R.id
                .imageViewTestBadge4);
        ImageView imageViewBadge5 = (ImageView) rootView.findViewById(R.id
                .imageViewTestBadge5);
        ImageView imageViewBadge6 = (ImageView) rootView.findViewById(R.id
                .imageViewTestBadge6);
        TextView textViewBadgesInfo = (TextView) rootView.findViewById(R.id
                .textViewTestBadgesInfo);

        Button buttonShare = (Button) rootView.findViewById(R.id.buttonTestShare);
        Button buttonReview = (Button) rootView.findViewById(R.id.buttonTestReview);
        Button buttonBackToMM = (Button) rootView.findViewById(R.id.buttonTestBackMM);

        setScoreImageView(testingGrade, imageViewScore);
        setTextViewProblemsInfo(probsCorrect, probsAnswered, totalProblems, textViewProbCorrect, textViewProbAnswered);;
        setTextViewNewB(newBestGrade, textViewNewBest);
        setTextViewTimes(timeSpentSec, timeRemainingSec, timeInReviewSec, textViewTimeSpent, textViewTimeRemaining, textViewTimeReviewed);
        setBadgesImageTextViews(sectionTitle, imageViewBadge1, imageViewBadge2, imageViewBadge3, imageViewBadge4, imageViewBadge5, imageViewBadge6, textViewBadgesInfo);

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reviewIntent = new Intent(getActivity(), TestReview.class);

                reviewIntent.putExtra("incorrectProblemsList", incorrectProblemList);
                reviewIntent.putExtra("incorrectAnswers", incorrectAnswers);
                reviewIntent.putExtra(Intent.EXTRA_TEXT, sectionTitle);
                reviewIntent.putExtra("sectionNumber", sectionNumber);

                startActivity(reviewIntent);

            }
        });

        buttonBackToMM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenuIntent = new Intent(getActivity(), MainActivity.class);

                mainMenuIntent.putExtra("fromValue", "testprep");

                startActivity(mainMenuIntent);

            }
        });

        return rootView;
    }

    private void setScoreImageView(int grade, ImageView imageViewScore) {

        switch (grade) {
            case 0:
                imageViewScore.setImageResource(R.drawable.test_gradeap);
            case 1:
                imageViewScore.setImageResource(R.drawable.test_gradea);
            case 2:
                imageViewScore.setImageResource(R.drawable.test_gradeam);
            case 3:
                imageViewScore.setImageResource(R.drawable.test_gradebp);
            case 4:
                imageViewScore.setImageResource(R.drawable.test_gradeb);
            case 5:
                imageViewScore.setImageResource(R.drawable.test_gradebm);
            case 6:
                imageViewScore.setImageResource(R.drawable.test_gradecp);
            case 7:
                imageViewScore.setImageResource(R.drawable.test_gradec);
            case 8:
                imageViewScore.setImageResource(R.drawable.test_gradecm);
            case 9:
                imageViewScore.setImageResource(R.drawable.test_graded);
        }

    }

    private void setTextViewProblemsInfo(int numProbsCorrect, int numProbsAnswered, int numTotProblems, TextView tvProbCorrect, TextView tvProbAnswered) {

        tvProbCorrect.setText(getString(R.string.progress_section_problems_correct) + String.valueOf(numProbsCorrect) + "/" + numTotProblems);

        tvProbAnswered.setText(getString(R.string.progress_section_problems_answered) + String.valueOf(numProbsAnswered) + "/" + numTotProblems);

    }

    private void setTextViewNewB(boolean newBestGrade, TextView tvNewBest) {

        if (newBestGrade) {
            tvNewBest.setText("New Best!");
            tvNewBest.setVisibility(View.VISIBLE);
        } else {
            tvNewBest.setVisibility(View.INVISIBLE);
        }
    }

    private void setTextViewTimes(int timeSpentSec, int timeRemainingSec, int timeInReviewSec, TextView tvTimeSpent, TextView tvTimeRemaining, TextView tvTimeReviewed) {

        tvTimeSpent.setText(getString(R.string.testprep_time_spent) + String.format("%d minutes, %d seconds", TimeUnit.SECONDS.toMinutes(timeSpentSec), timeSpentSec - (TimeUnit.SECONDS.toMinutes(timeSpentSec)*60)));
        
        tvTimeRemaining.setText(getString(R.string.testprep_time_remaining) + " " + String.format("%d minutes, %d seconds", TimeUnit.SECONDS.toMinutes(timeRemainingSec), timeRemainingSec - (TimeUnit.SECONDS.toMinutes(timeRemainingSec)*60)));
        
        tvTimeReviewed.setText(getString(R.string.testprep_timeinreview) + String.format("%d minutes, %d seconds", TimeUnit.SECONDS.toMinutes(timeInReviewSec), timeInReviewSec - (TimeUnit.SECONDS.toMinutes(timeInReviewSec)*60)));
        
    }
    
    private void setBadgesImageTextViews(String secTitle, ImageView ivBadge1, ImageView ivBadge2, ImageView ivBadge3, ImageView ivBadge4, ImageView ivBadge5, ImageView ivBadge6, TextView tvBadgesInfo) {

        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("progress", Context.MODE_PRIVATE);

        String prefix = "section" + String.valueOf(sectionNumber) + ".";

        if (sectionNumber == 1) {

            if (sharedPrefs.getBoolean(prefix + "test_b1unlocked", false)) {
                ivBadge1.setImageResource(R.drawable.section1_testbadge1);
            } else {
                ivBadge1.setImageResource(R.drawable.test_badgelocked);
            }

            if (sharedPrefs.getBoolean(prefix + "test_b2unlocked", false)) {
                ivBadge2.setImageResource(R.drawable.section1_testbadge2);
            } else {
                ivBadge2.setImageResource(R.drawable.test_badgelocked);
            }

            if (sharedPrefs.getBoolean(prefix + "test_b3unlocked", false)) {
                ivBadge3.setImageResource(R.drawable.section1_testbadge3);
            } else {
                ivBadge3.setImageResource(R.drawable.test_badgelocked);
            }

            if (sharedPrefs.getBoolean(prefix + "test_b4unlocked", false)) {
                ivBadge4.setImageResource(R.drawable.section1_testbadge4);
            } else {
                ivBadge4.setImageResource(R.drawable.test_badgelocked);
            }

            if (sharedPrefs.getBoolean(prefix + "test_b5unlocked", false)) {
                ivBadge5.setImageResource(R.drawable.section1_testbadge5);
            } else {
                ivBadge5.setImageResource(R.drawable.test_badgelocked);
            }

            if (sharedPrefs.getBoolean(prefix + "test_b6unlocked", false)) {
                ivBadge6.setImageResource(R.drawable.section1_testbadge6);
            } else {
                ivBadge6.setImageResource(R.drawable.test_badgelocked);
            }

            int numOfNewBadges = newestBadgesArray.size();
            if (numOfNewBadges == 1) {
                String sectionBadgeName = null;
                switch (newestBadgesArray.get(0)) {
                    case 1:
                        sectionBadgeName = getString(R.string.test_badge1_name);
                    case 2:
                        sectionBadgeName = getString(R.string.test_badge2_name);
                    case 3:
                        sectionBadgeName = getString(R.string.test_badge3_name);
                    case 4:
                        sectionBadgeName = getString(R.string.test_badge4_name);
                    case 5:
                        sectionBadgeName = getString(R.string.test_badge5_name);
                    case 6:
                        sectionBadgeName = getString(R.string.test_badge6_name);
                }
                tvBadgesInfo.setText(getString(R.string.practice_results_one_badge_unlocked1) + secTitle + " " + sectionBadgeName);

            }

        }
    }
}
