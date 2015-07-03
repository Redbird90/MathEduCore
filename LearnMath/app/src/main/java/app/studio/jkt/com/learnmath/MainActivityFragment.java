package app.studio.jkt.com.learnmath;

import android.content.Intent;
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

    public MainActivityFragment() {
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
                //Intent progressViewIntent = new Intent(getActivity(), progressView.class);
                //startActivity(progressViewIntent);
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
