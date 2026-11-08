/*
 * Asteroid.java
 *
 * YOU WRITE THIS WHOLE CLASS IN STEP 6 of the tutorial.
 * (Delete this comment block when you start, then write the class below.)
 *
 * Use Star.java as your model. Your Asteroid class must:
 *
 *   1. Be declared as:   public class Asteroid extends FallingObject
 *
 *   2. Have a constructor:   public Asteroid(int x, int speed)
 *        - it must call super(...) with a size of 40
 *
 *   3. OVERRIDE draw(Graphics g)
 *        - draw a gray circle:  g.fillOval(getX(), getY(), getSize(), getSize());
 *        - add a few smaller dark-gray ovals as craters
 *        - you will need:  import java.awt.Color;  and  import java.awt.Graphics;
 *
 *   4. OVERRIDE isHazard()
 *        - return true (asteroids are dangerous!)
 *
 *   Don't forget @Override above each overridden method.
 */
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Asteroid extends FallingObject
{
    private BufferedImage shipSprite;

    /** Constructor: calls the superclass constructor with a size of 30. */
    public Asteroid(int x, int speed)
    {
        super(x, 40, speed);
        try {
            // Option A: Use this if the image is inside your project's source/assets folder (Recommended)
            shipSprite = ImageIO.read(getClass().getResourceAsStream("/astroid.png"));

            // Option B: Use this if the image is just loose in your root project folder
            // shipSprite = ImageIO.read(new java.io.File("ship.png"));
        } catch (
                IOException e) {
            System.out.println("Error: Could not load player image!");
            e.printStackTrace();
        }
    }

    /** OVERRIDES draw() from FallingObject to draw an asteroid */
    @Override
    public void draw(Graphics g)
    {
        // add asteroid img
        if (shipSprite != null) {
            g.drawImage(shipSprite, getX(), getY(), getSize(), getSize(),null);
        } else {
            g.setColor(new Color(100, 100, 130));
            g.fillOval(getX(), getY(), getSize(), getSize());
        }
    }
    @Override
    public boolean isHazard() {
        return true;
    }
}
