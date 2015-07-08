package app.studio.jkt.com.learnmath;

import android.os.Parcelable;

/**
 * Created by JDK on 6/29/2015.
 */
public interface Problem extends Parcelable {

    public String getProblemType();

    public String getAnswer();

}
