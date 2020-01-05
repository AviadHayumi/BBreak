package hayumi.aviad.bbbreak.Activities.GameActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

/**
 * Created by Admin on 08/03/2017.
 */

public class BatteryLevelReceiver extends BroadcastReceiver
{

    public BatteryLevelReceiver() {

    }

    @Override

    public void onReceive(Context context, Intent intent) {


        if(intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED))

        {

            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
            int percent = (level * 100) / scale;
            if (percent < 5)
            {
                Toast.makeText(context, "The battery level is low ,  might turned off", Toast.LENGTH_LONG).show();

            }
        }

    }

}