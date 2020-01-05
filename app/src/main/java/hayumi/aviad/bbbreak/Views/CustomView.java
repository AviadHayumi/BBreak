package hayumi.aviad.bbbreak.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import hayumi.aviad.bbbreak.BehindTheScenes.Data;
import hayumi.aviad.bbbreak.BehindTheScenes.PointFloat;
import hayumi.aviad.bbbreak.Objects.Ball;
import hayumi.aviad.bbbreak.Objects.BallCollection;
import hayumi.aviad.bbbreak.Objects.ItemsMap;
import hayumi.aviad.bbbreak.Objects.Player;
import hayumi.aviad.bbbreak.R;

public class CustomView extends SurfaceView implements Runnable
{

    private Context context;
    volatile boolean playing; //o The boolean checking if this is okay to play
    private Thread gameThread = null;
    private Paint paint;
    private Canvas canvas; //o on this canvas the game is painted
    private SurfaceHolder holder;
    private int screenWidth, screenHeight; //o the size of the screen

    private Intent Main;
    private Player player;
    private ItemsMap map;
    public Bitmap ballImg;
    public Bitmap[]images;
    private BallCollection balls;
    private Ball ball;
    private Data data;
    private long[] pattern = {0, 100, 1000, 300};
    public boolean isTouching;
    public boolean isPause;
    private int TIME_TO_SLEEP=5;
    private float BALLSPEED=40;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Vibrator v;
    private MediaPlayer downlinesound,gameoversound,backgroundsound;
    private boolean soundEffect;




    public CustomView(Context context, int w, int h,Intent Main)
    {
        super(context);
        this.context = context;
        screenWidth = w;
        screenHeight = h;
        this.Main=Main;
        holder = getHolder();
        paint = new Paint();

        ballImg = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        images=new Bitmap[18];
        images[0]=BitmapFactory.decodeResource(getResources(), R.drawable.money);
        images[1]=BitmapFactory.decodeResource(getResources(), R.drawable.diamond);
        images[2]=BitmapFactory.decodeResource(getResources(), R.drawable.add);
        images[3]=BitmapFactory.decodeResource(getResources(), R.drawable.spaceship);
        images[4]=BitmapFactory.decodeResource(getResources(), R.drawable.next);
        images[5]=BitmapFactory.decodeResource(getResources(), R.drawable.previous);
        images[6]=BitmapFactory.decodeResource(getResources(), R.drawable.top);
        images[7]=BitmapFactory.decodeResource(getResources(), R.drawable.space);
        images[8]=BitmapFactory.decodeResource(getResources(), R.drawable.pause);
        images[9]=BitmapFactory.decodeResource(getResources(), R.drawable.resume);
        images[10]=BitmapFactory.decodeResource(getResources(), R.drawable.minus );
        downlinesound =MediaPlayer.create(context,R.raw.downline);
        gameoversound =MediaPlayer.create(context,R.raw.gameover);
        backgroundsound=MediaPlayer.create(context,R.raw.backgroundmusic);
        startGame();

    }

    //o start all the Objects
    public void startGame()
    {
        prefs = context.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        editor = prefs.edit();
        int top = prefs.getInt("top",0);
        int money = prefs.getInt("money",0);
        soundEffect=prefs.getBoolean("soundEffect",false);
        isTouching=false;
        data=new Data(top,money,screenWidth,screenHeight,images);
        map=new ItemsMap(screenWidth,screenHeight,data,images);
        float f=map.getBrickSize();
        player=new Player(screenWidth/2,screenHeight-(screenHeight/(map.getNUM_OF_ROWS()-1)+f*0.5f ));
        data.setMap(map);
        data.setPlayer(player);
        isPause=false;
        v = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        map.startGame();
        backgroundsound.setVolume(20,20);
        backgroundsound.setLooping(true);
        if(soundEffect) {backgroundsound.start();backgroundsound.setLooping(true);}

    }


    //o update when there is a chance
    private void update()
    {
        if(balls!=null)
        {
            balls.move();
            map.handleCollosion(balls);
        }

        if (balls != null &&  !balls.isMooving())
        {
            v.vibrate(50);
            map.downLine();
            if(soundEffect) { downlinesound.start();}
            data.nextLevel();
            player.setX(balls.getXYFirstBall().getX());
            balls = null;
        }



    }


    public boolean onTouchEvent(MotionEvent event)
    {
        float ex = event.getX();
        float ey = event.getY();

        switch ( event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if(data.isTouchPause(ex,ey))
                {
                    if(!isPause)
                    {
                        pause();
                        isPause=true;
                    }
                    else
                    {
                        resume();
                        isPause=false;
                    }
                }
                if(!IsBallMooving())
                {
                    player.updateTouchPoint(new PointFloat(ex, ey));
                    isTouching=true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(!IsBallMooving())
                {
                    PointFloat pf = player.gerBallMovementDirection();
                    if (pf != null)
                    {
                        balls = new BallCollection(screenWidth, screenHeight, ballImg, data.getNumBalls(), player.getX(), player.getY()-map.getBrickSize()*0.5f, pf.getX() * BALLSPEED, pf.getY() * BALLSPEED, map);
                        isTouching=false;
                    }
                    player.clear();
                    break;
                }

        }
        return true;
    }



    //o methos that draw all the objects
    private void draw()
    {

        canvas = holder.lockCanvas(); //o lock the canvas as MuteX works
        if (holder.getSurface().isValid())
        {
            if(map.isGameOver()) //o in case that the player lose
            {
                if(soundEffect) {backgroundsound.stop();backgroundsound.setLooping(false);gameoversound.start();}
                data.drawGameOver(canvas);
                Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
                long[] pattern = {0, 100, 1000, 300, 200, 100, 500, 200, 100};
                v.vibrate(pattern,-1);
                playing=false;
            }
            else
            {
                data.draw(canvas, isPause);
                map.draw(canvas);
                player.drawPlayerLine(canvas);
                if (balls != null) {
                    balls.draw(canvas);
                }
            }
            holder.unlockCanvasAndPost(canvas);
        }

    }

    //o animation thread -  the game sleeps for few millisecond's that the player would be able to see the objects mooving
    private void control()
    {
        try
        {
            gameThread.sleep(TIME_TO_SLEEP);

        }
        catch (InterruptedException e)
        {
        }
    }

    //o in case of pausing game
    public void pause()
    {
        if(!map.isGameOver())
        {
            playing = false;
        }
        try
        {
            gameThread.join();
            if(prefs.getInt("top",0)<data.getLevel())
            {
                editor.putInt("top",data.getLevel());
            }
            editor.putBoolean("soundEffect",soundEffect);
            editor.putInt("money",data.getMoney()+data.getMoney());
            editor.commit();
        }
        catch (InterruptedException e)
        {
        }
    }

    public void resume()
    {
        if(!map.isGameOver()) {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    //o whith this method you stop or start the game
    public void setPlaying(boolean playing) {this.playing = playing;}

    //o to avoid duplications when the player go back to the homescreen,this method is used by GameActivity
    public void stopBackgroundMusic()
    {
        backgroundsound.stop();
    }

    //o check if there is ball on the surface that his directions isnt 0
    public boolean IsBallMooving()
    {
        return (  (this.balls!=null) && (balls.isMooving()) );
    }


    //o the method that run this game while this is valid to play
    @Override
    public void run()
    {
        while (playing)
        {
            update();
            draw();
            control();
        }
    }


}

