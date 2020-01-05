package hayumi.aviad.bbbreak.Objects.Bricks;

import android.graphics.Bitmap;

import hayumi.aviad.bbbreak.BehindTheScenes.Data;
import hayumi.aviad.bbbreak.Objects.Ball;

/**
 * Created by GA97 on 27/01/2017.
 */

public class DiamondBrick extends MoneyBrick
{

    public DiamondBrick(int row, int col, float brickSize, Bitmap bitmap, Data data)
    {
        super(row, col, brickSize, bitmap, data);

    }


    public boolean IsHit(Ball ball)
    {
        if(ball!=null)
        {
            if(super.IsHit(ball))
            {
                getData().addDimoand();
                return true;
            }
        }
        return false;
    }


}
