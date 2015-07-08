package app.studio.jkt.com.learnmath;

import android.app.Activity;
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


/**
 * A placeholder fragment containing a simple view.
 */
public class BadgesViewFragment extends Fragment {

    public BadgesViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_badges_view, container, false);

        // MANUAL IMPLEMENTATION; does not draw upon Constants defined in XML


        ImageView imageViewSec1P1 = (ImageView) rootView.findViewById(R.id.imageViewSec1P1);
        ImageView imageViewSec1P2 = (ImageView) rootView.findViewById(R.id.imageViewSec1P2);
        ImageView imageViewSec1P3 = (ImageView) rootView.findViewById(R.id.imageViewSec1P3);
        ImageView imageViewSec2P1 = (ImageView) rootView.findViewById(R.id.imageViewSec2P1);
        ImageView imageViewSec2P2 = (ImageView) rootView.findViewById(R.id.imageViewSec2P2);
        ImageView imageViewSec2P3 = (ImageView) rootView.findViewById(R.id.imageViewSec2P3);
        
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("progress",
                Context.MODE_PRIVATE);

        String prefix = "section" + "1" + ".";
        if (sharedPreferences.getBoolean(prefix + "prac_b1unlocked", false)) {
            imageViewSec1P1.setImageResource(R.drawable.practicebadge1);
        }
        if (sharedPreferences.getBoolean(prefix + "prac_b2unlocked", false)) {
            imageViewSec1P1.setImageResource(R.drawable.practicebadge2);
        }
        if (sharedPreferences.getBoolean(prefix + "prac_b3unlocked", false)) {
            imageViewSec1P1.setImageResource(R.drawable.practicebadge3);
        }
        if (sharedPreferences.getBoolean(prefix + "test_b1unlocked", false)) {
            imageViewSec1P1.setImageResource(R.drawable.testbadge1);
        }
        if (sharedPreferences.getBoolean(prefix + "test_b2unlocked", false)) {
            imageViewSec1P1.setImageResource(R.drawable.testbadge2);
        }
        if (sharedPreferences.getBoolean(prefix + "test_b3unlocked", false)) {
            imageViewSec1P1.setImageResource(R.drawable.testbadge3);
        }

        // TODO: Add click listeners for user tapping on badges which display badge details

        Button backButton = (Button) rootView.findViewById(R.id.buttonReturn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProgressView.class);
                startActivity(intent);
            }
        });


        return rootView;
    }


}
