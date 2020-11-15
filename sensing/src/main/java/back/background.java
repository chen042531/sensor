package back;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class background  extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("haha_service_start","start");
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          Log.d("haha_service_start","ok");
                                      }
                                  }, 3000);
        return  START_NOT_STICKY;
    }
}
