package hayumi.aviad.bbbreak.Objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import hayumi.aviad.bbbreak.BehindTheScenes.Data;
import hayumi.aviad.bbbreak.Objects.Bricks.AddBall;
import hayumi.aviad.bbbreak.Objects.Bricks.Brick;
import hayumi.aviad.bbbreak.Objects.Bricks.DiamondBrick;
import hayumi.aviad.bbbreak.Objects.Bricks.MinusBrick;
import hayumi.aviad.bbbreak.Objects.Bricks.MoneyBrick;


public class ItemsMap
{
    private int screenwidth;
    private float BrickSize;
    private final int NUM_OF_COULUMNS=7;
    private final int NUM_OF_ROWS=12;
    private ArrayList<Brick> Items=new ArrayList<>();
    private Random rnd = new Random();
    private Data data;
    private Bitmap moneyImg,dimoandImg,addImg,minusImg;

    public ItemsMap(int screenWidth,int ScreenHeight,Data data,Bitmap[] bitmap)
    {
        this.screenwidth=screenWidth;
        BrickSize=screenWidth/NUM_OF_COULUMNS;
        this.data=data;
        moneyImg=bitmap[0];
        dimoandImg=bitmap[1];
        addImg=bitmap[2];
        minusImg=bitmap[10];

    }

    public void startGame()
    {
        addBrick();
    }

//    o לא לשכוח - המטרה פה היא להוסיף מספר של לבנים ולא לבנה אחת הכל אמור להיות מרונדר
    public void addBrick()
    {
        int[] numOfBricks;


        if(Items.size()<25)
        {
            numOfBricks = new int[rnd.nextInt(NUM_OF_COULUMNS-4)+4];
        }
        else
        {
            numOfBricks = new int[rnd.nextInt(NUM_OF_COULUMNS)];
        }
        int numOfRandomBricks=numOfBricks.length;
        for (int i=0; i<numOfRandomBricks;i++)
        {
            numOfBricks[i]=rnd.nextInt(NUM_OF_COULUMNS);
        }

        int[]avoidDuplication=removeDuplicates(numOfBricks);


        for (int i=0; i<avoidDuplication.length; i++)
        {
           int num =wichTypeOfBrick();
           switch (num)
           {
               case 0:
                       Items.add(new MoneyBrick(2, avoidDuplication[i]+1, BrickSize, moneyImg,data));
                   break;
               case 1: Items.add(new AddBall(2, avoidDuplication[i]+1, BrickSize, addImg,data));
                   break;
               case 2:      Items.add(new MinusBrick(2, avoidDuplication[i]+1, BrickSize, minusImg,data));
                   break;
               case 3:  Items.add(new DiamondBrick(2, avoidDuplication[i]+1, BrickSize, dimoandImg,data));
                   break;
               case 4:
                     Items.add(new Brick(2, avoidDuplication[i]+1,BrickSize, data.getLevel()));
           }

        }

        if(Items.size()==0)
        {
            addBrick();
        }

    }


    private int putLevel()
    {
        int num=data.getLevel();
        if(num%2==0)
        {
           return rnd.nextInt(num / 2) + num / 2;
        }
        num++;
        return rnd.nextInt(num/2)+num/2;

    }

    public void downLine()
    {
        for (Brick item : Items)
        {
            item.row++;
        }
        addBrick();
        removeCrashedBricks();
    }

    public void removeCrashedBricks()
    {
        ArrayList<Brick> newList=new ArrayList<>();
        for (Brick item : Items)
        {
            if(!item.isCrashed)
            {
                newList.add(item);
            }
        }

        Items=newList;
    }

    public void draw(Canvas canvas)
    {
        for (Brick item : Items)
        {
            item.draw(canvas);
        }
    }


    public boolean handleCollosion(BallCollection balls)
    {
        boolean res=false;
        float wichCoulum;
        for(int i=0 ; i<balls.getBalls().size();i++)
        {
            for (Brick item : Items)
            {
                wichCoulum= (balls.getBalls().get(i).getCx()/NUM_OF_COULUMNS)* screenwidth ;
                int num=(int)wichCoulum;
       //         if(item.getRow()==num || item.getRow()==num+1)
       //         {
                 if (item.IsHit(balls.getBalls().get(i)) && item.getNumHit()==0)
                   {
                        item.isCrashed = true;
                        res=true;
                   }
                }
        //    }
        }

        ArrayList<Brick> tmp = new ArrayList<>();
        for (int i=0 ; i<Items.size(); i++)
        {
            if(Items.get(i).getNumHit()!=0)
            {
                tmp.add(Items.get(i));
            }
        }
        this.Items=tmp;
        return res;
    }



    public boolean isGameOver()
    {

        boolean retVal = false;

        for (Brick item : Items)
        {
            if ( item != null)
            {
                if (  item.getRow() == (NUM_OF_ROWS - 2))
                {

                    retVal = true;
                    break;
                }
            }
        }
        return retVal;
    }

    private char wichTypeOfBrick()
    {
        int randomNumber=rnd.nextInt(100);
        if(randomNumber >= 0 && randomNumber<5)
            return 0; //for money brick
        else if(randomNumber > 5 && randomNumber <= 20)
               return 1; //for add brick
            else if(randomNumber>20 && randomNumber<=25)
                  return 2; //for minus Brick
                  else if(randomNumber==26)
                        return 3; //for diamond Brick
        return 4; //o for regular brick
    }



    public static int[] removeDuplicates(int[] arr) {

        int end = arr.length;

        for (int i = 0; i < end; i++) {
            for (int j = i + 1; j < end; j++) {
                if (arr[i] == arr[j]) {
                    int shiftLeft = j;
                    for (int k = j+1; k < end; k++, shiftLeft++) {
                        arr[shiftLeft] = arr[k];
                    }
                    end--;
                    j--;
                }
            }
        }

        int[] whitelist = new int[end];
        for(int i = 0; i < end; i++){
            whitelist[i] = arr[i];
        }
        return whitelist;
    }

    //o Getters
    public float getBrickSize() {
        return BrickSize;
    }

    public int getNUM_OF_ROWS() {
        return NUM_OF_ROWS;
    }

    public Data getData() {
        return data;
    }


}

