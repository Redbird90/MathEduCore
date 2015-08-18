package app.studio.jkt.com.learnmath;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;


/**
 * A placeholder fragment containing a simple view.
 */
public class PracticeResultsFragment extends Fragment {

    private String[] congratsTexts = new String[] {"Good Job!", "Great Work!", "Nicely Done!",
            "Your Skills are Improving!", "Way to go!", "Great Practice!", "Amazing Job!"};

    public PracticeResultsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_practice_results, container, false);

        Intent intent = getActivity().getIntent();
        final String sectionKey = intent.getStringExtra(Intent.EXTRA_TEXT);
        final int sectionNumber = intent.getIntExtra("sectionNumber", 1);

        TextView textViewCongrats = (TextView) rootView.findViewById(R.id.textViewCongrats);
        TextView textViewAnswered = (TextView) rootView.findViewById(R.id.textViewAnswered);
        TextView textViewCorrect = (TextView) rootView.findViewById(R.id.textViewCorrect);
        TextView textViewNewBest = (TextView) rootView.findViewById(R.id.textViewNewBest);

        ImageView imageViewStarterBadge = (ImageView) rootView.findViewById(R
                .id.imageViewStarterBadge);
        ImageView imageViewQuickBadge = (ImageView) rootView.findViewById(R.id
                .imageViewQuickBadge);
        ImageView imageViewTrainingBadge = (ImageView) rootView.findViewById(R.id
                .imageViewTrainingBadge);

        TextView textViewBadgesInfo = (TextView) rootView.findViewById(R.id.textViewBadgesInfo);
        TextView textViewRetry = (TextView) rootView.findViewById(R.id.textViewRetry);

        Button buttonRetry = (Button) rootView.findViewById(R.id.buttonRetry);
        Button buttonBackToSection = (Button) rootView.findViewById(R.id.buttonBackToSection);

        textViewCongrats.setText(congratsSetText());
        textViewAnswered.setText(answeredSetText());
        textViewCorrect.setText(correctSetText());
        setTextViewNewBestAttributes(textViewNewBest);
        setImageViewBadges(imageViewStarterBadge, imageViewQuickBadge, imageViewTrainingBadge);
        setTextViewBadgesInfo(sectionNumber, textViewBadgesInfo);
        setTextViewRetry(textViewRetry);
        // Retry Buttons text already set; constants

        buttonRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent currentIntent = getActivity().getIntent();
                ArrayList<Integer> retryProbKeys = currentIntent.getIntegerArrayListExtra("arrayIncorrectProbKeys");
                ArrayList<String> priorIncAnswers = currentIntent.getStringArrayListExtra("pracIncorrectAnswers");

                Intent retryIntent = new Intent(getActivity(), PracticeRetry.class);

                retryIntent.putExtra(Intent.EXTRA_TEXT, sectionKey);
                retryIntent.putExtra("sectionNumber", sectionNumber);
                retryIntent.putExtra("retryProblemKeys", retryProbKeys);
                retryIntent.putExtra("userAnswers", priorIncAnswers);

                startActivity(retryIntent);
            }
        });

        buttonBackToSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sectionDetailIntent = new Intent(getActivity(), SectionDetail.class);

                sectionDetailIntent.putExtra(Intent.EXTRA_TEXT, sectionKey);
                sectionDetailIntent.putExtra("sectionNumber", sectionNumber);

                startActivity(sectionDetailIntent);
            }
        });

        return rootView;
    }

    private void setTextViewRetry(TextView textViewRetry) {

        Intent intent = getActivity().getIntent();
        //Bundle data = intent.getExtras();
        //Parcelable[] incorrectProblems = data.getParcelableArray("arrayIncorrectQuestions");
        ArrayList<Integer> incorrectProbKeys = intent.getIntegerArrayListExtra("arrayIncorrectProbKeys");

        if (incorrectProbKeys.size() > 0) {
            textViewRetry.setText(getString(R.string.practice_results_retry_incorrect));
        } else {
            textViewRetry.setText(getString(R.string.practice_results_retry_correct));
        }

    }

    private void setTextViewBadgesInfo(int sectionNumber, TextView textViewBadgesInfo) {

        Intent intent = getActivity().getIntent();
        ArrayList<Integer> arrayNewBadges = intent.getIntegerArrayListExtra("arrayNewBadges");
        String sectionKey = intent.getStringExtra(Intent.EXTRA_TEXT);


        if (arrayNewBadges.size() > 0) {
            if (arrayNewBadges.size() == 3) {
                textViewBadgesInfo.setText(getString(R.string
                        .practice_results_all_badges_unlocked));
            } else if (arrayNewBadges.size() == 2) {
                textViewBadgesInfo.setText(getString(R.string.practice_results_two_badges_unlocked));
            } else {
                String sectionBadgeName = "Badge Name Debug: " + arrayNewBadges.get(0);
                switch (arrayNewBadges.get(0)) {
                    case 0:
                        sectionBadgeName = getString(R.string.practice_badge1_name);
                    case 1:
                        sectionBadgeName = getString(R.string.practice_badge2_name);
                    case 2:
                        sectionBadgeName = getString(R.string.practice_badge3_name);
                }
                textViewBadgesInfo.setText(getString(R.string
                        .practice_results_one_badge_unlocked1) + sectionKey + " " + sectionBadgeName
                        + "!");
            }
        } else {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("progress", Context.MODE_PRIVATE);
            String prefix = "section" + String.valueOf(sectionNumber) + ".";
            if (sharedPreferences.getBoolean(prefix + "prac_b1unlocked", false) && sharedPreferences.getBoolean(prefix + "prac_b2unlocked", false) && sharedPreferences.getBoolean(prefix + "prac_b3unlocked", false)) {
                textViewBadgesInfo.setText(getString(R.string.practice_results_all_badges_unlocked));
            } else {
                textViewBadgesInfo.setText(getString(R.string.practice_results_no_badges));
            }
        }
    }

    private void setImageViewBadges(ImageView imageViewStarterBadge, ImageView imageViewQuickBadge, ImageView imageViewTrainingBadge) {

        // TODO: Fix below 5 value after debugging
        Intent currentIntent = getActivity().getIntent();
        int sectionNumber = currentIntent.getIntExtra("sectionNumber", 5);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("progress", Context.MODE_PRIVATE);
        String prefix = "section" + String.valueOf(sectionNumber) + ".";
        if (sharedPreferences.getBoolean(prefix + "prac_b1unlocked", false)) {
            imageViewStarterBadge.setImageResource(R.drawable.practicebadge1);
        }
        if (sharedPreferences.getBoolean(prefix + "prac_b2unlocked", false)) {
            imageViewQuickBadge.setImageResource(R.drawable.practicebadge2);
        }
        if (sharedPreferences.getBoolean(prefix + "prac_b3unlocked", false)) {
            imageViewTrainingBadge.setImageResource(R.drawable.practicebadge3);
        }

    }

    private void setTextViewNewBestAttributes(TextView textViewNewBest) {

        Intent intent = getActivity().getIntent();
        int totCorrect = intent.getIntExtra("numCorrectAnswers", 1);
        int priorCorrect = intent.getIntExtra("priorNumCorrectAnswers", 1);

        if (totCorrect > priorCorrect) {
            textViewNewBest.setText(getString(R.string.results_new_best));
            textViewNewBest.setVisibility(View.VISIBLE);
        } else {
            textViewNewBest.setVisibility(View.INVISIBLE);
        }
    }

    private String correctSetText() {

        Intent intent = getActivity().getIntent();
        int totCorrect = intent.getIntExtra("numCorrectAnswers", 1);
        int totQuestions = intent.getIntExtra("numTotalQuestions", 1);

        return "You got " + String.valueOf(totCorrect) + "/" + String.valueOf(totQuestions)
                + " questions correct.";
    }

    private String answeredSetText() {

        Intent intent = getActivity().getIntent();
        int totAnswers = intent.getIntExtra("numTotalAnswers", 1);
        int totQuestions = intent.getIntExtra("numTotalQuestions", 1);

        return "You answered " + String.valueOf(totAnswers) + "/" + String.valueOf(totQuestions)
                + " questions.";
    }

    private String congratsSetText() {
        String text;

        Intent intent = getActivity().getIntent();
        int totCorrect = intent.getIntExtra("numCorrectAnswers", 1);

        if (totCorrect > 0) {
            Random random = new Random();
            int randInt = random.nextInt(7);
            text = congratsTexts[randInt];
        } else {
            text = "Care to try again?";
        }

        return text;
    }
}
