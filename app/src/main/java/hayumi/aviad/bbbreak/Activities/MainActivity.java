package hayumi.aviad.bbbreak.Activities;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import hayumi.aviad.bbbreak.Activities.GameActivity.GameActivity;
import hayumi.aviad.bbbreak.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnPlay;
    private ImageButton btnPlay1,btnShop , btnQuestionMark;
    private TextView score;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private CheckBox cbxSound;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        btnShop =(ImageButton)findViewById(R.id.btnShop);
        btnPlay1= (ImageButton)findViewById(R.id.btnPlay1);
        btnQuestionMark = (ImageButton) findViewById(R.id.btnQuestionMark);
        score=(TextView)findViewById(R.id.score);
        cbxSound= (CheckBox)findViewById(R.id.checkBox2);


        btnPlay1.setOnClickListener(this);
        prefs = getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        editor = prefs.edit();
        int top = prefs.getInt("money",0);

        score.setText("Your High Score : " + top);

        boolean PlaySound = prefs.getBoolean("soundEffect",false);
        cbxSound.setChecked(PlaySound);

        cbxSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                editor.putBoolean("soundEffect",b);
                editor.commit();
            }
        });
    }


    @Override
    public void onClick(View v)
    {
            Intent intent;
            intent = new Intent(this, GameActivity.class);
            startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("");
        alert.setMessage("Do you to give up ? ");

        alert.setNegativeButton("NO", null);

        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();

            }
        });
        alert.show();
    }

}
