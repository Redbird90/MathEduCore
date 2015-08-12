package app.studio.jkt.com.learnmath;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.lang.ref.WeakReference;


/**
 * A placeholder fragment containing a simple view.
 */
public class SectionDetailFragment extends Fragment {

    public SectionDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_section_detail, container, false);

        ImageButton imageButtonL = (ImageButton) rootView.findViewById(R.id.imageButtonLearn);
        imageButtonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent learnIntent = new Intent(getActivity(), LearnActivity.class);
                Intent currIntent3 = getActivity().getIntent();
                learnIntent.putExtra(Intent.EXTRA_TEXT,currIntent3.getStringArrayExtra(Intent.EXTRA_TEXT));
                learnIntent.putExtra("sectionNumber", currIntent3.getIntExtra("sectionNumber", 1));
                startActivity(learnIntent);
            }
        });
        imageButtonL.setImageResource(R.drawable.learnbtn);

        ImageButton imageButtonPrac = (ImageButton) rootView.findViewById(R.id.imageButtonPractice);
        imageButtonPrac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent practiceIntent = new Intent(getActivity(), PracticeActivity.class);
                Intent currIntent = getActivity().getIntent();
                practiceIntent.putExtra(Intent.EXTRA_TEXT, currIntent.getStringExtra(Intent.EXTRA_TEXT));
                practiceIntent.putExtra("sectionNumber", currIntent.getIntExtra("sectionNumber", 1));
                startActivity(practiceIntent);
            }
        });
        imageButtonPrac.setImageResource(R.drawable.practicebtn);

        ImageButton imageButtonTP = (ImageButton) rootView.findViewById(R.id.imageButtonTestPrep);
        imageButtonTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testPrepIntent = new Intent(getActivity(), TestPrepPrefsView.class);
                Intent currIntent2 = getActivity().getIntent();
                testPrepIntent.putExtra(Intent.EXTRA_TEXT, currIntent2.getStringExtra(Intent.EXTRA_TEXT));
                testPrepIntent.putExtra("sectionNumber", currIntent2.getIntExtra("sectionNumber", 1));
                startActivity(testPrepIntent);
            }
        });
        imageButtonTP.setImageResource(R.drawable.testprepbtn);

/*        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageViewSectionOverview);
        DisplayMetrics displayMerics = new DisplayMetrics();
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(imageView);
        bitmapWorkerTask.execute(R.drawable.samplescrollableimagelowres);*/
        //imageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable
        //        .samplescrollableimage, 480, 2400));

        // TODO: Set click listener for buttonSDBack
        Button buttonSectionBack = (Button) rootView.findViewById(R.id.buttonSDBack);
        buttonSectionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sectionsViewIntent = new Intent(getActivity(), SectionsView.class);
                startActivity(sectionsViewIntent);
            }
        });

        return rootView;
    }

    // Method courtesy of Google : http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    // Method courtesy of Google : http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    // Method courtesy of Google : http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private int data = 0;

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            // TO-DO: ENSURE PIXEL CONFIG IS UNIVERSAL TO OTHER DEVICES
            return decodeSampledBitmapFromResource(getResources(), data, 480, 2400);
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

}