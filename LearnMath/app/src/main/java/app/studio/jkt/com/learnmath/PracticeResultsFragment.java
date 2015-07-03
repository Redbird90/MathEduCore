package app.studio.jkt.com.learnmath;

import android.content.Intent;
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

        String sectionKey = intent.getStringExtra(Intent.EXTRA_TEXT);

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
        setTextViewBadgesInfo(textViewBadgesInfo);

        return rootView;
    }

    private void setTextViewBadgesInfo(TextView textViewBadgesInfo) {

        Intent intent = getActivity().getIntent();
        int[] arrayNewBadges = intent.getIntArrayExtra("arrayNewBadges");
        String sectionKey = intent.getStringExtra(Intent.EXTRA_TEXT);


        if (arrayNewBadges.length > 0) {
            if (arrayNewBadges.length == 3) {
                textViewBadgesInfo.setText(getString(R.string
                        .practice_results_all_badges_unlocked));
            } else if (arrayNewBadges.length == 2) {
                textViewBadgesInfo.setText(getString(R.string.practice_results_two_badges_unlocked));
            } else {
                textViewBadgesInfo.setText(getString(R.string
                        .practice_results_one_badge_unlocked1) + sectionKey + section badge_name
                        + getString(R.string.practice_results_one_badge_unlocked2));
            }
        } else {
            // TODO: insert conditions to handle 0 new badges or badges already unlocked
        }
    }

    private void setImageViewBadges(ImageView imageViewStarterBadge, ImageView imageViewQuickBadge,
                                    ImageView imageViewTrainingBadge) {

        // TODO: Create data storage system for keeping track of user progress

    }

    private void setTextViewNewBestAttributes(TextView textViewNewBest) {

        Intent intent = getActivity().getIntent();
        int totCorrect = intent.getIntExtra("numCorrectAnswers", 1);
        int priorCorrect = intent.getIntExtra("priorNumCorrectAnswers", 1);

        if (totCorrect > priorCorrect) {
            textViewNewBest.setText("New Best!");
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
        int totAnswers = intent.getIntExtra("numTotalAnswers", 1);

        if (totAnswers > 0) {
            Random random = new Random();
            int randInt = random.nextInt(7);
            text = congratsTexts[randInt];
        } else {
            text = "Care to try again?";
        }

        return text;
    }
}