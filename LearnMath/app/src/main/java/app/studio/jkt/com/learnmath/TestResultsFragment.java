package app.studio.jkt.com.learnmath;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class TestResultsFragment extends Fragment {

    public TestResultsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_test_results, container, false);

        Intent currResultsIntent = getActivity().getIntent();
        boolean timeElapsed = currResultsIntent.getBooleanExtra("timeElapsed", false);
        int timeRemainingSec = currResultsIntent.getIntExtra("timeRemainingSec", 60);

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





        

        return rootView;
    }
}
