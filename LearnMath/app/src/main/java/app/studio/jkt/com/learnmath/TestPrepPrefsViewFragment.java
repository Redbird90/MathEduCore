package app.studio.jkt.com.learnmath;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A placeholder fragment containing a simple view.
 */
public class TestPrepPrefsViewFragment extends Fragment {

    public TestPrepPrefsViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int chosenTimeKey = getResources().getInteger(R.integer.section1_test_time_recommended);
        final int[] timeValue = {getResources().getInteger(R.integer.section1_test_time3)};
        final String sectionTitle = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);

        View rootView = inflater.inflate(R.layout.fragment_test_prep_prefs_view, container, false);

        Button buttonT1 = (Button) rootView.findViewById(R.id.buttonTime1);
        Button buttonT2 = (Button) rootView.findViewById(R.id.buttonTime2);
        Button buttonT3 = (Button) rootView.findViewById(R.id.buttonTime3);
        Button buttonT4 = (Button) rootView.findViewById(R.id.buttonTime4);

        final int sectionNumber = getActivity().getIntent().getIntExtra("sectionNumber", 4);
        String prefix = "section" + String.valueOf(sectionNumber) + ".";

        final TextView textViewTimeAllowed = (TextView) rootView.findViewById(R.id.textViewTimeAllowed);
        // Time Allowed(10 Q's): 10 Minutes
        textViewTimeAllowed.setText(getString(R.string.testprep_time_allowed) + "(" + String.valueOf(getResources().getInteger(R.integer.section1_test_total_problems)) + " Q's): " + getChosenTime() + " Minutes");

        if (sectionNumber == 1) {
            buttonT1.setText(String.valueOf(getResources().getInteger(R.integer.section1_test_time1)) + getString(R.string.testprep_min));
            buttonT2.setText(String.valueOf(getResources().getInteger(R.integer.section1_test_time2)) + getString(R.string.testprep_min));
            buttonT3.setText(String.valueOf(getResources().getInteger(R.integer.section1_test_time3)) + getString(R.string.testprep_min));
            buttonT4.setText(String.valueOf(getResources().getInteger(R.integer.section1_test_time4)) + getString(R.string.testprep_min));
        }

        TextView textViewRecText = (TextView) rootView.findViewById(R.id.textViewRecText);
        ViewGroup.MarginLayoutParams recTextLayParams = (ViewGroup.MarginLayoutParams) textViewRecText.getLayoutParams();

        // TODO: Fix layout of textViewRecText
        
        if (chosenTimeKey == 1) {
            
            ViewGroup.MarginLayoutParams buttonT1LayParams = (ViewGroup.MarginLayoutParams) buttonT1.getLayoutParams();
            recTextLayParams.leftMargin = buttonT1LayParams.leftMargin;
            textViewRecText.setLayoutParams(recTextLayParams);
            timeValue[0] = getResources().getInteger(R.integer.section1_test_time1);
            
        } else if (chosenTimeKey == 2) {
            
            ViewGroup.MarginLayoutParams buttonT2LayParams = (ViewGroup.MarginLayoutParams) buttonT2.getLayoutParams();
            recTextLayParams.leftMargin = buttonT2LayParams.leftMargin;
            textViewRecText.setLayoutParams(recTextLayParams);
            timeValue[0] = getResources().getInteger(R.integer.section1_test_time2);
            
        } else if (chosenTimeKey == 3) {

            Log.i("TestPrepPrefsV", "chosen3");
            LinearLayout.LayoutParams buttonT3LayParams = (LinearLayout.LayoutParams) buttonT3.getLayoutParams();
            Log.i("TestPrepPrefsV", String.valueOf(buttonT3LayParams.leftMargin));
            Log.i("TestPrepPrefsV", String.valueOf(recTextLayParams.leftMargin));
            recTextLayParams.leftMargin = buttonT3LayParams.leftMargin;
            Log.i("TestPrepPrefsV", String.valueOf(recTextLayParams.leftMargin));
            textViewRecText.setLayoutParams(recTextLayParams);
            timeValue[0] = getResources().getInteger(R.integer.section1_test_time3);
            
        } else if (chosenTimeKey == 4) {
            
            ViewGroup.MarginLayoutParams buttonT4LayParams = (ViewGroup.MarginLayoutParams) buttonT4.getLayoutParams();
            recTextLayParams.leftMargin = buttonT4LayParams.leftMargin;
            textViewRecText.setLayoutParams(recTextLayParams);
            timeValue[0] = getResources().getInteger(R.integer.section1_test_time4);
            
        } else {
            Log.e("TestPrepPrefsViewFrag", "recTextLayParams not entered");
            timeValue[0] = getResources().getInteger(R.integer.section1_test_time3);
        }

        Button buttonStart = (Button) rootView.findViewById(R.id.buttonStartTest);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testIntent = new Intent(getActivity(), TestPrepActivity.class);
                testIntent.putExtra("timeValue", timeValue[0]);
                testIntent.putExtra("sectionNumber", sectionNumber);
                testIntent.putExtra(Intent.EXTRA_TEXT, sectionTitle);
                startActivity(testIntent);
            }
        });
        
        buttonT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeValue[0] = getResources().getInteger(R.integer.section1_test_time1);
                textViewTimeAllowed.setText(getString(R.string.testprep_time_allowed) + "(" + String.valueOf(getResources().getInteger(R.integer.section1_test_total_problems)) + " Q's): " + String.valueOf(timeValue[0]) + " Minutes");
            }
        });

        buttonT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeValue[0] = getResources().getInteger(R.integer.section1_test_time2);
                textViewTimeAllowed.setText(getString(R.string.testprep_time_allowed) + "(" + String.valueOf(getResources().getInteger(R.integer.section1_test_total_problems)) + " Q's): " + String.valueOf(timeValue[0]) + " Minutes");
            }
        });

        buttonT3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeValue[0] = getResources().getInteger(R.integer.section1_test_time3);
                textViewTimeAllowed.setText(getString(R.string.testprep_time_allowed) + "(" + String.valueOf(getResources().getInteger(R.integer.section1_test_total_problems)) + " Q's): " + String.valueOf(timeValue[0]) + " Minutes");
            }
        });

        buttonT4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeValue[0] = getResources().getInteger(R.integer.section1_test_time4);
                textViewTimeAllowed.setText(getString(R.string.testprep_time_allowed) + "(" + String.valueOf(getResources().getInteger(R.integer.section1_test_total_problems)) + " Q's): " + String.valueOf(timeValue[0]) + " Minutes");
            }
        });

        return rootView;
    }

    private String getChosenTime() {

        int chosenTimeKey = getResources().getInteger(R.integer.section1_test_time_recommended);

        if (chosenTimeKey == 1) {
            return String.valueOf(getResources().getInteger(R.integer.section1_test_time1));
        } else if (chosenTimeKey == 2) {
            return String.valueOf(getResources().getInteger(R.integer.section1_test_time2));
        } else if (chosenTimeKey == 3) {
            return String.valueOf(getResources().getInteger(R.integer.section1_test_time3));
        } else if (chosenTimeKey == 4) {
            return String.valueOf(getResources().getInteger(R.integer.section1_test_time4));
        } else {
            return null;
        }

    }
}
