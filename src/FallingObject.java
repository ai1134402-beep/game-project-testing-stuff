import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * FallingObject.java
 * SUPERCLASS for everything that falls from the sky.
 * Star and Asteroid both "extend" this class, so they inherit
 * all of this code without rewriting it.
 *
 * YOU WILL COMPLETE THIS CLASS IN STEP 4 of the tutorial.
 */
public class FallingObject
{
    private int x;
    private int y;
    private int size;    // width AND height (objects are squares/circles)
    private int speed;   // pixels moved down per frame

    /** Constructor (PROVIDED): object starts just above the top of the screen. */
    public FallingObject(int x, int size, int speed)
    {
        this.x = x;
        this.y = -size;      // negative y = above the visible window
        this.size = size;
        this.speed = speed;
    }

    /** Moves the object down by its speed. */
    public void update()
    {
        //  4.1: Add speed to y.
        y += speed;

    }

    /** True once the object has fallen past the bottom of the window. */
    public boolean isOffScreen(int panelHeight)
    {
        //  4.2: Return true if y is greater than panelHeight,
        //           otherwise return false.
        //           (Hint: a comparison already gives you a boolean!)
        return y > panelHeight;
    }

    /** The rectangle that surrounds this object (used for collisions). */
    public Rectangle getBounds()
    {
        //  4.3: Return a new Rectangle using x, y, size (width), size (height).
        return new Rectangle(x, y, size, size);
    }

    /** Default look (PROVIDED): a white square. Subclasses OVERRIDE this. */
    public void draw(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, size, size);
    }

    /** Default behavior (PROVIDED): objects are harmless. Subclasses may OVERRIDE this. */
    public boolean isHazard()
    {
        return false;
    }

    // ----- accessor methods (PROVIDED) -----
    // Subclasses can't touch private variables directly, so they call these.
    public int getX()    { return x; }
    public int getY()    { return y; }
    public int getSize() { return size; }
}
