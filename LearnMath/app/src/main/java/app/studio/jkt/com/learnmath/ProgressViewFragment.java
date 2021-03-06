package app.studio.jkt.com.learnmath;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A placeholder fragment containing a simple view.
 */
public class ProgressViewFragment extends Fragment {

    public ProgressViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_progress_view, container, false);

        TextView textViewStudyTime = (TextView) rootView.findViewById(R.id.textViewStudyTime);
        TextView textViewProblemsTackled = (TextView) rootView.findViewById(R.id.textViewProblemsTackled);
        TextView textViewProblemsDefeated = (TextView) rootView.findViewById(R.id.textViewProblemsDefeated);
        TextView textViewBestScore = (TextView) rootView.findViewById(R.id.textViewBestScore);
        TextView textViewLastScore = (TextView) rootView.findViewById(R.id.textViewLastScore);
        
        Button buttonSectionCompletion = (Button) rootView.findViewById(R.id.buttonSectionCompletion);
        Button buttonBadgesUnlocked = (Button) rootView.findViewById(R.id.buttonBadgesUnlocked);
        Button buttonReturnToMenu = (Button) rootView.findViewById(R.id.buttonProgressToMenu);

        buttonSectionCompletion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sectionsViewIntentFromProgress = new Intent(getActivity(), SectionsView.class);
                sectionsViewIntentFromProgress.putExtra("fromProgressView", true);
                startActivity(sectionsViewIntentFromProgress);
            }
        });

        buttonBadgesUnlocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent badgesViewIntent = new Intent(getActivity(), BadgesView.class);
                startActivity(badgesViewIntent);
            }
        });

        buttonReturnToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuIntent = new Intent(getActivity(), MainActivity.class);
                startActivity(menuIntent);
            }
        });


        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("progress",
                Context.MODE_PRIVATE);

        textViewStudyTime.setText(getString(R.string.progress_study_time) + String.valueOf(sharedPreferences.getInt("study_time", 1)));

        textViewProblemsTackled.setText(getString(R.string.progress_prob_tackled) + String.valueOf(sharedPreferences.getInt("problems_tackled", 1)));

        textViewProblemsDefeated.setText(getString(R.string.progress_prob_defeated) + String.valueOf(sharedPreferences.getInt("problems_defeated", 1)));

        if (sharedPreferences.getInt("best_score", 0) == -1) {
            textViewBestScore.setText(getString(R.string.progress_best_score) + getString(R.string
                    .progress_score_missing));
        } else {
            textViewBestScore.setText(getString(R.string.progress_best_score) + String.valueOf
                    (sharedPreferences.getInt("best_score", -1)));
        }

        if (sharedPreferences.getInt("last_score", 0) == -1) {
            textViewLastScore.setText(getString(R.string.progress_last_score) + getString(R
                    .string.progress_score_missing));
        } else {
            textViewLastScore.setText(getString(R.string.progress_last_score) + String.valueOf
                    (sharedPreferences.getInt("last_score", -1)));
        }


        return rootView;
    }
}
