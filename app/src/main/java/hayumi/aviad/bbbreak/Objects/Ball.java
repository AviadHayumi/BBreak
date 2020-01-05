package hayumi.aviad.bbbreak.Objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by GA97 on 17/12/2016.
 */

public class Ball
{
    private float cx, cy;
    private float radius;
    private float dx, dy;
    private Bitmap bitmap;
    private int screenWidth, screenHeight;
    private long StartMoovingTime;
    private ItemsMap map;
    private float TIKRA;
    private float RIZPA;


    public Ball(int screenWidth,int screenHight,Bitmap bitmap,int numBalls,float cx, float cy , float dx, float dy, long startMoovingTime,ItemsMap map)
    {
        this.screenWidth = screenWidth;
        screenHeight = screenHight;
        radius = 50;
        this.cx=cx;
        this.cy=cy;
        this.dx = dx;
        this.dy = dy;
        float size = 2 * radius;
        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int) size, (int) size, true);
        this.StartMoovingTime=startMoovingTime;
        this.map=map;
        TIKRA=screenHeight/map.getNUM_OF_ROWS();
        RIZPA=screenHeight-screenHeight/map.getNUM_OF_ROWS()-map.getBrickSize()*0.5f;

    }



    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        canvas.drawBitmap(bitmap, cx - radius, cy - radius, paint);
    }


    public void  move()
    {
        if(System.currentTimeMillis() >= StartMoovingTime)
        {
            cx += dx;
            cy += dy;
            if (cx <= radius) {
                cx = radius;
                dx = -dx;
            }
            if (cx >= screenWidth - radius) {
                cx = screenWidth - radius;
                dx = -dx;
            }
            if (cy <= TIKRA+radius ) {
                cy = TIKRA+radius;
                dy = -dy;
            }

            if (cy >= RIZPA - radius && dy>0) {
                cy = RIZPA - radius;
                dy = -dy;
            }


            if (isCrashFloor())
            {
                stop();

            }
        }
    }

    public void stop()
    {
        dx = 0;
        dy = 0;
    }


    public boolean isCrashFloor()
    {
        if (dy < 0 && cy >= RIZPA - radius)
        {
            cy = RIZPA - radius;
            return true;
        }
        return false;

    }

    public boolean contain(PointF p1, PointF p2, float x, float y)
    {
        return x >= p1.x && x <= p2.x  && y >= p1.y && y <= p2.y;
    }


    public int whatEdge (PointF p1, PointF p2) {

        // obtaine vertex of this ball is inside the racket
        float x0 = cx - radius, y0 = cy - radius;// upper left corner
        float x1 = x0, y1 = cy + radius;// lower left corner
        float x2 = cx + radius, y2 = y1;// lower right corner
        float x3 = x2, y3 = y0;// upper right corner
        float xe, ye, xs, ys; // the segment between two last position of the inside point

        if (contain(p1, p2, x0, y0)) {
            xe = x0;
            ye = y0;
        } else if (contain(p1, p2, x1, y1)) {
            xe = x1;
            ye = y1;
        } else if (contain(p1, p2,x2, y3)) {
            xe = x2;
            ye = y2;
        } else if (contain(p1, p2,x3, y3)) {
            xe = x3;
            ye = y3;
        } else
            return -1;
        // the segmentis (xs, ys) - (xe, ye)
        xs = xe - dx;
        ys = ye - dy;

        // y = ax + b
        float a, b, x, y;
        if (dx != 0)
        {
            a = dy / dx;
            b = ye - a * xe;
            //0
            y = a * p1.x + b;
            if (y >= ys && y <= ye || y >= ye && y <= ys)
                return 0;
            //2
            y = a * p2.x + b;
            if (y >= ys && y <= ye || y >= ye && y <= ys)
                return 2;
            //1
            x = (p2.y - b) / a;
            if (x >= xs && x <= xe || x >= xe && x <= xs)
                return 1;
            //3
            x = (p1.y - b) / a;
            if (x >= xs && x <= xe || x >= xe && x <= xs)
                return 3;
        }
        else // dx = 0, dy != 0 - vertical direction
        {
            if (ys <= p1.y) return 3;
            else return 1;
        }
        return 0; // just for syntax

    }

    public boolean isHitNextMoveFloor()
    {
        return !(this.cy + dy >= RIZPA - radius);
    }


    //o Getters and Setters
    public float getCx() {
        return cx;
    }
    public float getCy() {
        return cy;
    }
    public float getDx() {
        return dx;
    }
    public float getDy() {
        return dy;
    }
    public void setDx(float dx) {
        this.dx = dx;
    }
    public void setDy(float dy) {
        this.dy = dy;
    }


}