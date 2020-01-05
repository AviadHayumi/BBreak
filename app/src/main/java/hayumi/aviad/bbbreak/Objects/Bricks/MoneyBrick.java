package hayumi.aviad.bbbreak.Objects.Bricks;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import hayumi.aviad.bbbreak.BehindTheScenes.Data;
import hayumi.aviad.bbbreak.Objects.Ball;

/**
 * Created by GA97 on 27/01/2017.
 */

public class MoneyBrick extends Brick
{
    private Bitmap bitmap;
    private Data data;
    public MoneyBrick(int row, int col, float brickSize, Bitmap bitmap,Data data)
    {
        super(row, col, brickSize, 1);
        this.bitmap=bitmap;
        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int) brickSize, (int) brickSize, true);
        this.data=data;
    }

    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        canvas.drawBitmap(bitmap, (col-1)*getBrickSize(),(row)*getBrickSize(), paint);
    }

    public Data getData()
    {
        return data;
    }

    public boolean IsHit(Ball ball)
    {

        if(super.IsHit(ball))
        {
            data.addMoney();
            return true;
        }
        return false;
    }

}
