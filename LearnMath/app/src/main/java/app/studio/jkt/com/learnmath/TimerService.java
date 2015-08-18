package app.studio.jkt.com.learnmath;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by JDK on 8/14/2015.
 */
public class TimerService extends Service {

    @Override
    public IBinder onBind(Intent i) {
        return null;
    }
    @Override
    public int onStartCommand(Intent i, int flags, int startId) {
        // Put your timer code here
    }
}