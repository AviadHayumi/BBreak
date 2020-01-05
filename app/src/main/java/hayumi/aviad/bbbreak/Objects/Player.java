package hayumi.aviad.bbbreak.Objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import hayumi.aviad.bbbreak.BehindTheScenes.PointFloat;

/**
 * Created by GA97 on 17/12/2016.
 */

public class Player
{
    private float x,y;
    private PointFloat curPoint;
    private Paint paint;

    public Player(int x, float y)
    {
        this.x = x;
        this.y = y;
        paint=new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setPathEffect(new DashPathEffect(new float[] {10,10}, 5));
        paint.setColor(Color.WHITE);
    }

    public void clear()
    {

        this.curPoint=null;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public void updateTouchPoint(PointFloat p)
    {
        this.curPoint=p;
    }

    public void drawPlayerLine(Canvas canvas)
    {
        if(this.curPoint!=null)
        {
            canvas.drawLine(x, y, curPoint.getX(), curPoint.getY(), this.paint); //o תותח
        }
    }

    public PointFloat gerBallMovementDirection()
    {
        PointFloat pf = curPoint;
        if(pf != null)
        {
            float a =  curPoint.getX() - x; //o ניצב 1
            float b =  curPoint.getY() - y; //o ניצב 2
            float c = (float) Math.sqrt(Math.pow(a,2) + Math.pow(b,2));
            return new PointFloat(a/c,b/c);
        }
        else
        {
            return null;
        }
    }

}
