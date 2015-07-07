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

        TextView textViewStudyTime = (TextView) getActivity().findViewById(R.id.textViewStudyTime);
        TextView textViewProblemsTackled = (TextView) getActivity().findViewById(R.id.textViewProblemsTackled);
        TextView textViewProblemsDefeated = (TextView) getActivity().findViewById(R.id.textViewProblemsDefeated);
        TextView textViewBestScore = (TextView) getActivity().findViewById(R.id.textViewBestScore);
        TextView textViewLastScore = (TextView) getActivity().findViewById(R.id.textViewLastScore);

        Button buttonSectionCompletion = (Button) getActivity().findViewById(R.id.buttonSectionCompletion);
        Button buttonBadgesUnlocked = (Button) getActivity().findViewById(R.id.buttonBadgesUnlocked);
        Button buttonReturnToMenu = (Button) getActivity().findViewById(R.id.buttonProgressToMenu);

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


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("progress", Context.MODE_PRIVATE);

        textViewStudyTime.setText(getString(R.string.progress_study_time) + String.valueOf(sharedPreferences.getInt("study_time", 1)));

        textViewProblemsTackled.setText(getString(R.string.progress_prob_tackled) + String.valueOf(sharedPreferences.getInt("problems_tackled", 1)));

        textViewProblemsDefeated.setText(getString(R.string.progress_prob_defeated) + String.valueOf(sharedPreferences.getInt("problems_defeated", 1)));

        textViewBestScore.setText(getString(R.string.progress_best_score) + String.valueOf(sharedPreferences.getString("best_score", "N/A")));

        textViewLastScore.setText(getString(R.string.progress_last_score) + String.valueOf(sharedPreferences.getString("last_score", "N/A")));


        return rootView;
    }
}
