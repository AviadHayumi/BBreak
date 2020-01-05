package hayumi.aviad.bbbreak.Objects.Bricks;

import android.graphics.Bitmap;

import hayumi.aviad.bbbreak.BehindTheScenes.Data;
import hayumi.aviad.bbbreak.Objects.Ball;

/**
 * Created by Admin on 29/03/2017.
 */

public class MinusBrick extends MoneyBrick
{
    public MinusBrick(int row, int col, float brickSize, Bitmap bitmap, Data data)
    {
        super(row, col, brickSize, bitmap,data);
    }

    public boolean IsHit(Ball ball)
    {
        if(ball!=null)
        {
            if(super.IsHit(ball))
            {
                getData().minusBall();
                return true;
            }
        }
        return false;
    }
}
