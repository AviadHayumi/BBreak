package hayumi.aviad.bbbreak.BehindTheScenes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.Settings;

import hayumi.aviad.bbbreak.Objects.ItemsMap;
import hayumi.aviad.bbbreak.Objects.Player;

/**
 * Created by GA97 on 17/12/2016.
 */

public class Data
{
    private int screenWidth;
    private int screenHeight;

    private Paint paint;
    private int level;
    private int money;
    private int top;
    private int numBalls;
    private ItemsMap map;
    private Bitmap spaceShipImg,pauseImg,moneyImg,topImg,prevImg,nextImg,backgroundImg,resumeImg;
    private Player player;


    public Data(int money,int top,int screenWidth,int screenHeight,Bitmap[] bitmap)
    {
        this.screenWidth=screenWidth;
        this.screenHeight=screenHeight;
        this.level = 1;
        this.money=money;

        this.top=top;
        numBalls=1;
        spaceShipImg=bitmap[3];
        pauseImg=bitmap[8];
        moneyImg=bitmap[0];
        topImg=bitmap[6];
        prevImg=bitmap[5];
        nextImg=bitmap[4];
        backgroundImg=bitmap[7];
        resumeImg=bitmap[9];
    }

    public void setPlayer(Player player)
    {
        this.player=player;
        spaceShipImg = Bitmap.createScaledBitmap(spaceShipImg, (int)(map.getBrickSize()*1.5f ), (int) map.getBrickSize(), true);
        pauseImg = Bitmap.createScaledBitmap(pauseImg, (int) map.getBrickSize(), (int) map.getBrickSize(), true);
        moneyImg = Bitmap.createScaledBitmap(moneyImg, (int) map.getBrickSize(), (int) map.getBrickSize(), true);
        topImg = Bitmap.createScaledBitmap(topImg, (int) map.getBrickSize(), (int) map.getBrickSize(), true);
        prevImg = Bitmap.createScaledBitmap(prevImg, (int) map.getBrickSize(), (int) map.getBrickSize(), true);
        nextImg = Bitmap.createScaledBitmap(nextImg, (int) map.getBrickSize(), (int) map.getBrickSize(), true);
        resumeImg = Bitmap.createScaledBitmap(nextImg, (int) map.getBrickSize(), (int) map.getBrickSize(), true);
    }


    public void draw(Canvas canvas,boolean pasue)
    {
        paint=new Paint();
        int num=this.numBalls%10;
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

        int TEXT_SIZE = (screenHeight/map.getNUM_OF_ROWS()/2) ;
        canvas.drawBitmap(backgroundImg,0,0,paint);
        canvas.drawBitmap(backgroundImg,0,screenHeight/2,paint);
        paint.setTextSize(TEXT_SIZE);
        canvas.drawLine(0,screenHeight/map.getNUM_OF_ROWS(),screenWidth,screenHeight/map.getNUM_OF_ROWS(),paint);
        canvas.drawLine(0,screenHeight-screenHeight/map.getNUM_OF_ROWS()-map.getBrickSize()*0.5f,screenWidth,screenHeight-screenHeight/map.getNUM_OF_ROWS()-map.getBrickSize()*0.5f,paint);
        canvas.drawBitmap(spaceShipImg, player.getX()-map.getBrickSize()/2,(player.getY()-map.getBrickSize()/2), paint);
        TEXT_SIZE=TEXT_SIZE/2;
        paint.setTextSize(TEXT_SIZE);
        canvas.drawText("X" +numBalls, player.getX()-map.getBrickSize()/2 ,player.getY()-map.getBrickSize()/2,paint);
        TEXT_SIZE=TEXT_SIZE*2;
        paint.setTextSize(TEXT_SIZE);
        float textInPixels = paint.measureText(String.valueOf(this.level));
        float textX ;
        float textY = 1.5f*(textInPixels);
        canvas.drawText(String.valueOf(this.level), (screenWidth+map.getBrickSize())*0.5f, textY ,paint);
        if(!pasue)
        {
            canvas.drawBitmap(pauseImg,0,0,paint);
        }
        else
        {
            canvas.drawBitmap(resumeImg,0,0,paint);
        }
        canvas.drawLine(map.getBrickSize(),0,map.getBrickSize(),map.getBrickSize(),paint);
        canvas.drawBitmap(moneyImg,1.1f*map.getBrickSize(),0,paint);


        textX = (textInPixels/ 2);
        textY = 1.5f*(textInPixels);
        canvas.drawText( String.valueOf(this.money), 2.1f*map.getBrickSize(), textY ,paint);
        textX =screenWidth*0.75f - (textInPixels*0.75f);
        textY = 1.5f*(textInPixels);
        canvas.drawBitmap(topImg,screenWidth-map.getBrickSize()*2,0,paint);
        canvas.drawText( String.valueOf(this.top), screenWidth-map.getBrickSize(), textY ,paint);
        textX =  (textInPixels/2);
        textY = screenHeight - 1.5f*(textInPixels);

    }

    public void drawGameOver(Canvas canvas)
    {
        canvas.drawBitmap(backgroundImg,0,0,paint);
        canvas.drawBitmap(backgroundImg,0,screenHeight/2,paint);
        paint.setTextSize(screenHeight / 15);
        paint.setColor(Color.WHITE);
        canvas.drawText("Game Over !", 0.2f*screenWidth, screenHeight / 2, paint);
        canvas.drawText("Final Score:" + level,0.2f*screenWidth, screenHeight / 2 + screenHeight / 15, paint);

    }


    public boolean isTouchPause(float ex,float ey)
    {
        return ( ex<=map.getBrickSize() && ey<=map.getBrickSize() );
    }



    public void addMoney()
    {
        this.money++;
    }
    public void addDimoand()
    {
        this.money+=20;
    }
    public void addBall() {this.numBalls++;}
    public int getMoney() {
        return money;
    }
    public int getNumBalls() {return numBalls;}
    public void setMap(ItemsMap map)
    {
        this.map=map;
    }
    public void  nextLevel()
    {
        level++;
    }
    public int getLevel()
    {
        return level;
    }
    public void minusBall()
    {
        if(numBalls!=1)
        {
            numBalls--;
        }
    }
}
