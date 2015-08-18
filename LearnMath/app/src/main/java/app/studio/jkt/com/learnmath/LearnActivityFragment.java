package app.studio.jkt.com.learnmath;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class LearnActivityFragment extends Fragment {

    private String sectionTitle;
    private int sectionNumber;
    private ArrayList<Button> topicButtonArray;
    private Drawable[] learnIVResArray;
    private int currentView;
    private int prevView;

    public LearnActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_learn, container, false);

        Intent currLearnIntent = getActivity().getIntent();
        sectionTitle = currLearnIntent.getStringExtra(Intent.EXTRA_TEXT);
        sectionNumber = currLearnIntent.getIntExtra("sectionNumber", 1);

        LinearLayout outerLinearLayout = (LinearLayout) rootView.findViewById(R.id.learnOuterLinLayout);

        final ImageView imageViewContent = (ImageView) rootView.findViewById(R.id.imageViewLearn);

        TextView textViewJumpToSection = new TextView(getActivity());
        textViewJumpToSection.setText(getString(R.string.learn_jumpto));
        // Last parameter represents layout weight
        textViewJumpToSection.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout
                .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, (float) 0.8));
        textViewJumpToSection.setGravity(Gravity.CENTER_VERTICAL);

        outerLinearLayout.addView(textViewJumpToSection, 0);

        prevView = 0;

        if (sectionNumber == 1) {
            int numOfTopics = getResources().getInteger(R.integer.section1_learn_topicnum);
            topicButtonArray = new ArrayList<Button>(numOfTopics);
            learnIVResArray = loadDrawResources(numOfTopics);
            String[] romanNum = new String[] {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
            for (int i=0; i < numOfTopics; i++) {
                final int currentKey = i;
                final Button topicButton = new Button(getActivity());
                topicButton.setText(romanNum[i]);
                // Last parameter represents layout weight
                topicButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout
                        .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                topicButtonArray.add(topicButton);
                outerLinearLayout.addView(topicButton);

            }

        }

        for (int x=0; x<topicButtonArray.size(); x++) {
            final int currKey = x;
            final Button currButton = topicButtonArray.get(x);
            currButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int topicBtnKey = currKey;
                    currButton.setEnabled(false);
                    topicButtonArray.get(prevView).setEnabled(true);
                    prevView = topicBtnKey;
                }
            });
        }

        // Initialize with currentView's topic button disabled
        topicButtonArray.get(0).setEnabled(false);

        Button escBtn = new Button(getActivity());
        escBtn.setText(R.string.learn_escbtn);
        escBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        escBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escBtnPressed();
            }
        });

        outerLinearLayout.addView(escBtn);

        Button nextSecButton = (Button) rootView.findViewById(R.id.buttonLearnNext);
        nextSecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    topicButtonArray.get(prevView + 1).setEnabled(false);
                    topicButtonArray.get(prevView).setEnabled(true);
                    prevView += 1;
                } catch (IndexOutOfBoundsException e) {
                    Log.e("LearnActivityFrag", String.valueOf(e));
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                            .setTitle("Exit")
                            .setMessage("You have gone over all the study material.  Would you " +
                                    "like to go over practice material for this section?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent practiceIntent = new Intent(getActivity(), PracticeActivity.class);
                                    Intent currIntent = getActivity().getIntent();
                                    practiceIntent.putExtra(Intent.EXTRA_TEXT, currIntent.getStringExtra(Intent.EXTRA_TEXT));
                                    practiceIntent.putExtra("sectionNumber", currIntent.getIntExtra("sectionNumber", 1));
                                    startActivity(practiceIntent);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.show();
                }
            }
        });

        return rootView;
    }

    private Drawable[] loadDrawResources(int numOfTopics) {
        if (sectionNumber == 1) {
            // TODO: Update method body
        }
        Drawable[] dr = new Drawable[] {};
        return dr;
    }

    private void changeViews(int viewKey, ImageView imageViewHolder) {
        if (sectionNumber == 1) {
            if (viewKey == 1) {
                //imageViewHolder.setImageResource();
                // TODO: Update method body once resources are created
            }
        }
    }

    private void escBtnPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.learn_esc_dialog_header))
                .setMessage(getString(R.string.learn_esc_dialog_msg))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent sectionReturnIntent = new Intent(getActivity(), SectionDetail.class);
                        Intent currIntent = getActivity().getIntent();

                        sectionReturnIntent.putExtra(Intent.EXTRA_TEXT, currIntent.getStringExtra(Intent.EXTRA_TEXT));
                        sectionReturnIntent.putExtra("sectionNumber", currIntent.getIntExtra("sectionNumber", 1));

                        startActivity(sectionReturnIntent);
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        builder.show();
    }


}
