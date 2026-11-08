import java.awt.Color;
import java.awt.Graphics;

/**
 * Star.java  (WORKED EXAMPLE)
 * A Star is a kind of FallingObject. Catch it to score points!
 * Study this class closely - you will write Asteroid.java the same way.
 */
public class Star extends FallingObject
{
    /** Constructor: calls the superclass constructor with a size of 30. */
    public Star(int x, int speed)
    {
        super(x, 30, speed);
    }

    /** OVERRIDES draw() from FallingObject to draw a 5-point yellow star. */
    @Override
    public void draw(Graphics g)
    {
        int centerX = getX() + getSize() / 2;
        int centerY = getY() + getSize() / 2;
        int outerRadius = getSize() / 2;
        int innerRadius = getSize() / 4;

        // A star has 10 points: 5 outer tips alternating with 5 inner corners.
        int[] px = new int[10];
        int[] py = new int[10];
        for (int i = 0; i < 10; i++)
        {
            double angle = Math.PI / 5 * i - Math.PI / 2;
            int radius;
            if (i % 2 == 0)
            {
                radius = outerRadius;
            }
            else
            {
                radius = innerRadius;
            }
            px[i] = centerX + (int) (radius * Math.cos(angle));
            py[i] = centerY + (int) (radius * Math.sin(angle));
        }

        g.setColor(Color.YELLOW);
        g.fillPolygon(px, py, 10);
    }
}
