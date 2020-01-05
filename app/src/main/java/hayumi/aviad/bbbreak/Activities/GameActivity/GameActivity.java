package hayumi.aviad.bbbreak.Activities.GameActivity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import hayumi.aviad.bbbreak.Activities.MainActivity;
import hayumi.aviad.bbbreak.Views.CustomView;

public class GameActivity extends Activity
{
    private CustomView gameView;
    Intent intent2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        BroadcastReceiver batteryLevelReceiver = new BatteryLevelReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        this.registerReceiver(batteryLevelReceiver, filter);
        intent2 = new Intent(this, MainActivity.class);

        // get screen size
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // create the game view
        gameView = new CustomView(this, size.x, size.y , (intent2));

        // cancel the bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // show the our custom view on the screen
        setContentView(gameView);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        gameView.resume();
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("");
        alert.setMessage("Do you want go to end this game ? ");

        alert.setNegativeButton("NO", null);

        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                gameView.stopBackgroundMusic();
                finish();

            }
        });
        alert.show();
    }


}

