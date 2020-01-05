package hayumi.aviad.bbbreak.Objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import hayumi.aviad.bbbreak.BehindTheScenes.PointFloat;

/**
 * Created by GA97 on 17/12/2016.
 */

public class BallCollection
{
    private ArrayList<Ball> balls=new ArrayList<>();
    private int numBalls;
    private final long BALL_TIME_DIFF=250; //o the space between the balls

    public BallCollection(int screenWidth,int screenHight,Bitmap bitmap,int numBalls,float cx, float cy , float dx, float dy,ItemsMap map)
    {

        this.numBalls=numBalls;
        long currTime = System.currentTimeMillis();
        for (int i=0; i<this.numBalls; i++)
        {
            long ballStartMoveTime = currTime + (BALL_TIME_DIFF * i);
            balls.add(new Ball(screenWidth,screenHight,bitmap,numBalls,cx,cy,dx,dy,ballStartMoveTime,map));
        }
    }


    public void draw(Canvas canvas)
    {
        for (Ball ball : balls)
        {
            ball.draw(canvas);
        }
    }

    public void move()
    {
            for (Ball ball : balls)
            {
                ball.move();
            }
    }


    public ArrayList<Ball> getBalls()
    {
        return balls;
    }


    public boolean isMooving()
    {
        boolean res=false;
        for (Ball ball : balls)
        {
            if(ball.getDx()!=0 || ball.getDy()!=0)
                res=true;
        }
        return res;
    }

    public PointFloat getXYFirstBall() //o return to place of the first ball to change to place of the spaceship
    {
        return new PointFloat(balls.get(0).getCx(),balls.get(0).getCy());
    }

}
