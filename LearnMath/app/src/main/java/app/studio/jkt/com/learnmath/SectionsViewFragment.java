package app.studio.jkt.com.learnmath;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class SectionsViewFragment extends Fragment {

    private ArrayAdapter<String> sampleSectionsAdapter;

    public SectionsViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sections_view, container, false);

        ArrayList<String> sectionsList = new ArrayList<>();
        sectionsList.add(getString(R.string.section_1_name));
        sampleSectionsAdapter = new ArrayAdapter<String>(getActivity(), R.layout
                .list_item_section, R.id.list_item_section_textview, sectionsList);

        ListView listView = (ListView) rootView.findViewById(R.id.listViewSections);
        listView.setAdapter(sampleSectionsAdapter);

        boolean fromProgressView = getActivity().getIntent().getBooleanExtra("fromProgressView", false);

        if (!fromProgressView) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String sectionTitle = sampleSectionsAdapter.getItem(i);
                    Intent sectionDetailIntent = new Intent(getActivity(), SectionDetail.class);
                    sectionDetailIntent.putExtra("sectionNumber", i+1);
                    Log.i("SectionsViewFragment", "Value of i is " + String.valueOf(i));
                    sectionDetailIntent.putExtra(Intent.EXTRA_TEXT, sectionTitle);
                    startActivity(sectionDetailIntent);
                }
            });
        } else {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String sectionTitle = sampleSectionsAdapter.getItem(i);
                    Intent sectionCompletionIntent = new Intent(getActivity(), SectionCompletion.class);
                    startActivity(sectionCompletionIntent);
                }
            });
        }

        return rootView;
    }

}
