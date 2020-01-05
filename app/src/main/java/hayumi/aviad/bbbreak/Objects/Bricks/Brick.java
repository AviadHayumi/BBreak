package hayumi.aviad.bbbreak.Objects.Bricks;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import hayumi.aviad.bbbreak.BehindTheScenes.PointFloat;
import hayumi.aviad.bbbreak.Objects.Ball;

public class Brick
{

    public boolean isCrashed = false;
    private int numHit;
    public int row,col;
    private float brickSize;

    public Brick(int row,int col,float brickSize,int numHit)
    {
        this.row=row;
        this.col=col;
        this.brickSize=brickSize;
        this.numHit=numHit;

    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public PointFloat getStart()
    {
        float x=(col-1)*brickSize;
        float y=(row-1)*brickSize+brickSize;
        return new PointFloat(x,y);
    }

    public PointFloat getFinish()
    {
        float x=(col)*brickSize;
        float y=(row)*brickSize+brickSize;
        return new PointFloat(x,y);

    }

    public float getBrickSize()
    {
        return brickSize;
    }

    public void setColor(Paint paint)
    {
        int num=this.numHit%10;
        switch (num)
        {
            case 0: paint.setColor(Color.RED);
                break;
            case 1: paint.setColor(Color.rgb(255,127,0));
                break;
            case 2: paint.setColor(Color.rgb(255,255,0));
                break;
            case 3: paint.setColor(Color.rgb(0,255,0));
                break;
            case 4: paint.setColor(Color.rgb(0,0,255));
                break;
            case 5: paint.setColor(Color.rgb(75,0,130));
                break;
            case 6: paint.setColor(Color.rgb(148,0,211));
                break;
            case 7: paint.setColor(Color.WHITE);
                break;
            case 8: paint.setColor(Color.RED);
                break;
            case 9: paint.setColor(Color.GRAY);
                break;
            default: paint.setColor(Color.WHITE);
        }
    }

    public void draw(Canvas canvas)
    {
        Paint paint;
        paint=new Paint();
        setColor(paint);
        PointFloat start=getStart();
        PointFloat finish=getFinish();

        int TEXT_SIZE = 60;

        paint.setStyle(Paint.Style.FILL);

        float boxWidth = finish.getX()-start.getX();
        float boxHeight = finish.getY() - start.getY();

        canvas.drawRect(start.getX(), start.getY(), finish.getX(), finish.getY(), paint);

        paint.setColor(Color.BLACK);
        float diffWidth= boxWidth*0.05f;
        float diffHeight = boxHeight*0.05f;

        canvas.drawRect(start.getX()+diffWidth,
                        start.getY()+diffHeight,
                        finish.getX()-diffWidth,
                        finish.getY()-diffHeight,
                        paint);

        setColor(paint);
        paint.setTextSize(TEXT_SIZE);
        float textInPixels = paint.measureText(String.valueOf(this.numHit));
        float textX = start.getX()+(0.5f*boxWidth)-(textInPixels/ 2);
        float textY = start.getY()+(0.5f*boxHeight)+(textInPixels/ 2);
        canvas.drawText(String.valueOf(this.numHit), textX, textY ,paint);
    }


    //o check if the ball hit the brick
    public boolean IsHit(Ball ball)
    {
        if(ball!=null)
        {
            int numEdge = ball.whatEdge(new PointF(getStart().getX(),getStart().getY()), new PointF(getFinish().getX(),getFinish().getY()));

            if (numEdge == 1 || numEdge == 3) {
                ball.setDy(-ball.getDy());
                if(numHit>0)
                {
                    numHit--;
                }
                return true;
            }
            else if(numEdge == 0 || numEdge == 2) {
                ball.setDx(-ball.getDx());
                if(numHit>0)
                {
                    numHit--;
                }
                return true;
            }
            else
                return false;
        }
        return false;
    }



    public int getNumHit()
    {
        return numHit;
    }
}