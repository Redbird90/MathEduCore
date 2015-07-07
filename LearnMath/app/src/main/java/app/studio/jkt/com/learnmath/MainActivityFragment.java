package app.studio.jkt.com.learnmath;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    boolean firstTimeCreate = true;
    int sectionsCount = 1;

    public MainActivityFragment() {

        if (firstTimeCreate) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("progress", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("study_time",0);
            editor.putInt("problems_tackled", 0);
            editor.putInt("problems_defeated", 0);
            editor.putString("best_score", "None");
            editor.putString("last_score", "None");
            
            for (int i=0; i < sectionsCount; i++) {
                String prefix = "section" + String.valueOf(i+1) + ".";

                editor.putBoolean(prefix + "created", true);
                
                editor.putInt(prefix + "prac_problems_attempted", 0);
                editor.putInt(prefix + "prac_problems_correct", 0);
                editor.putBoolean(prefix + "prac_b1unlocked", false);
                editor.putBoolean(prefix + "prac_b2unlocked", false);
                editor.putBoolean(prefix + "prac_b3unlocked", false);

                editor.putInt(prefix + "test_problems_attempted", 0);
                editor.putInt(prefix + "test_problems_correct", 0);
                editor.putBoolean(prefix + "test_b1unlocked", false);
                editor.putBoolean(prefix + "test_b2unlocked", false);
                editor.putBoolean(prefix + "test_b3unlocked", false);
                editor.putInt(prefix + "test_fastest_time", 0);
                editor.putInt(prefix + "test_review_time", 0);
                editor.putInt(prefix + "test_avg_completion_time", 0);

                int prac_tot_problems = getResources().getInteger(R.integer.section1_prac_total_problems);
                for (int x=0; i < prac_tot_problems; i++) {
                    String prob_prefix = "problem" + String.valueOf(x+1) + ".";
                    editor.putBoolean(prefix + prob_prefix + "practice_attempted", false);
                    editor.putBoolean(prefix + prob_prefix + "practice_correct", false);
                }

                int test_tot_problems = getResources().getInteger(R.integer.section1_test_total_problems);
                for (int y=0; i < test_tot_problems; i++) {
                    String prob_prefix = "problem" + String.valueOf(y+1) + ".";
                    editor.putBoolean(prefix + prob_prefix + "test_attempted", false);
                    editor.putBoolean(prefix + prob_prefix + "test_correct", false);
                }
            }
            editor.commit();
            firstTimeCreate = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Set up Study Button
        ImageButton imageButton = (ImageButton) rootView.findViewById(R.id.imageButtonStart);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sectionsViewIntent = new Intent(getActivity(), SectionsView.class);
                startActivity(sectionsViewIntent);
            }
        });
        imageButton.setImageResource(R.drawable.studybtn);

        // Do same for remaining buttons

        ImageButton imageButtonP = (ImageButton) rootView.findViewById(R.id.imageButtonProgress);
        imageButtonP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent progressViewIntent = new Intent(getActivity(), ProgressView.class);
                startActivity(progressViewIntent);
            }
        });
        imageButtonP.setImageResource(R.drawable.progressbtn);

        ImageButton imageButtonS = (ImageButton) rootView.findViewById(R.id.imageButtonShare);
        imageButtonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent sharingIntent = new Intent(getActivity(), sharingView.class);
                //startActivity(sharingIntent);
            }
        });
        imageButtonS.setImageResource(R.drawable.sharebtn);

        return rootView;
    }
}
