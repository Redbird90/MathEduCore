package app.studio.jkt.com.learnmath;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A placeholder fragment containing a simple view.
 */
public class SectionCompletionFragment extends Fragment {

    public SectionCompletionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_section_completion, container, false);

        ScrollView scrollableView = (ScrollView) rootView.findViewById(R.id.scrollViewSectionCompletion);

        LinearLayout outerLinLayout = new LinearLayout(getActivity());
        outerLinLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        outerLinLayout.setOrientation(LinearLayout.VERTICAL);

        int sectionsCount = 1;
        for (int i=0; i < sectionsCount; i++) {
            TextView tvHeader = new TextView(getActivity());
            tvHeader.setTextSize(20);

            TextView tvSubHeaderPractice = new TextView(getActivity());
            tvSubHeaderPractice.setTextSize(15);
            TextView tvPracProbAnswered = new TextView(getActivity());
            tvPracProbAnswered.setTextSize(10);
            TextView tvPracProbCorrect = new TextView(getActivity());
            tvPracProbCorrect.setTextSize(10);
            TextView tvPracBadgesUnlocked = new TextView(getActivity());
            tvPracBadgesUnlocked.setTextSize(10);

            TextView tvSubHeaderTest = new TextView(getActivity());
            tvSubHeaderTest.setTextSize(15);
            TextView tvTestProbAnswered = new TextView(getActivity());
            tvTestProbAnswered.setTextSize(10);
            TextView tvTestProbCorrect = new TextView(getActivity());
            tvTestProbCorrect.setTextSize(10);
            TextView tvTestBadgesUnlocked = new TextView(getActivity());
            tvTestBadgesUnlocked.setTextSize(10);
            TextView tvTestFastestTime = new TextView(getActivity());
            tvTestFastestTime.setTextSize(10);
            TextView tvTestReviewTime = new TextView(getActivity());
            tvTestReviewTime.setTextSize(10);
            TextView tvTestAvgCompletionTime = new TextView(getActivity());
            tvTestAvgCompletionTime.setTextSize(10);

            tvHeader.setText(getString(R.string.section_1_name));
            tvSubHeaderPractice.setText(getString(R.string.progress_practice_header));

            SharedPreferences shPrefs = this.getActivity().getSharedPreferences("progress", Context
                    .MODE_PRIVATE);
            // Use 3 as default value for debugging purposes
            // TODO: Reset default values from SharedPreferences

            String prefix = "section" + String.valueOf(i+1) + ".";

            String totalPracticeProblems = String.valueOf(getResources().getInteger(R.integer.section1_prac_total_problems));

            Integer badgesUnlockedPractice = 0;
            if (shPrefs.getBoolean(prefix + "prac_b1unlocked", false)) {
                badgesUnlockedPractice += 1;
            }
            if (shPrefs.getBoolean(prefix + "prac_b2unlocked", false)) {
                badgesUnlockedPractice += 1;
            }
            if (shPrefs.getBoolean(prefix + "prac_b3unlocked", false)) {
                badgesUnlockedPractice += 1;
            }
            tvPracProbAnswered.setText(getString(R.string.progress_section_problems_answered) + String.valueOf(shPrefs.getInt(prefix + "prac_problems_attempted", 3)) + "/" + totalPracticeProblems);

            tvPracProbCorrect.setText(getString(R.string.progress_section_problems_correct) + String.valueOf(shPrefs.getInt(prefix + "prac_problems_correct", 3)) + "/" + totalPracticeProblems);

            tvPracBadgesUnlocked.setText(getString(R.string.progress_section_badges_unlocked) + String.valueOf(badgesUnlockedPractice) + "/" + String.valueOf(getResources().getInteger(R.integer.practice_total_badges)));

            String totalTestProblems = String.valueOf(getResources().getInteger(R.integer.section1_test_total_problems));
            tvSubHeaderTest.setText(getString(R.string.progress_test_header));

            Integer badgesUnlockedTest = 0;
            if (shPrefs.getBoolean(prefix + "test_b1unlocked", false)) {
                badgesUnlockedTest += 1;
            }
            if (shPrefs.getBoolean(prefix + "test_b2unlocked", false)) {
                badgesUnlockedTest += 1;
            }
            if (shPrefs.getBoolean(prefix + "test_b3unlocked", false)) {
                badgesUnlockedTest += 1;
            }

            tvTestProbAnswered.setText(getString(R.string.progress_section_problems_answered) + String.valueOf(shPrefs.getInt(prefix + "test_problems_attempted", 3)) + "/" + totalTestProblems);

            tvTestProbCorrect.setText(getString(R.string.progress_section_problems_correct) + String.valueOf(shPrefs.getInt(prefix + "test_problems_correct", 3)) + "/" + totalTestProblems);

            tvTestBadgesUnlocked.setText(getString(R.string.progress_section_badges_unlocked) + String.valueOf(badgesUnlockedTest) + "/" + String.valueOf(getResources().getInteger(R.integer.test_total_badges)));

            tvTestFastestTime.setText(getString(R.string.progress_fastest_time) + String.valueOf(shPrefs.getInt(prefix + "test_fastest_time", 3)));

            tvTestReviewTime.setText(getString(R.string.progress_review_time) + String.valueOf(shPrefs.getInt(prefix + "test_review_time", 3)));

            tvTestAvgCompletionTime.setText(getString(R.string.progress_completion_time) + String.valueOf(shPrefs.getInt(prefix + "test_avg_completion_time", 3)));

            outerLinLayout.addView(tvHeader);
            outerLinLayout.addView(tvSubHeaderPractice);
            outerLinLayout.addView(tvPracProbAnswered);
            outerLinLayout.addView(tvPracProbCorrect);
            outerLinLayout.addView(tvPracBadgesUnlocked);
            outerLinLayout.addView(tvSubHeaderTest);
            outerLinLayout.addView(tvTestProbAnswered);
            outerLinLayout.addView(tvTestProbCorrect);
            outerLinLayout.addView(tvTestBadgesUnlocked);
            outerLinLayout.addView(tvTestFastestTime);
            outerLinLayout.addView(tvTestReviewTime);
            outerLinLayout.addView(tvTestAvgCompletionTime);

        }

        Button buttonBack = new Button(getActivity());
        buttonBack.setText(getString(R.string.back_button));
        buttonBack.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        buttonBack.setGravity(Gravity.CENTER_HORIZONTAL);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProgressView.class);
                startActivity(intent);
            }
        });

        outerLinLayout.addView(buttonBack);
        scrollableView.addView(outerLinLayout);

        return rootView;
    }
}
