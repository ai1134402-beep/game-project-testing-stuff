import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Player.java
 * The ship at the bottom of the screen that the player controls.
 *
 * YOU WILL COMPLETE THIS CLASS IN STEPS 2 AND 3 of the tutorial.
 */
public class Player
{
    //  2.1: Declare six PRIVATE instance variables (all int):
    //           x, y, width, height, speed, direction
    //           x and y are the ship's top-left corner.
    //           direction will be -1 (left), 0 (still), or 1 (right).
    private int x;
    private int y;
    private int width = 28;
    private int height = 44;
    private int speed = 5;
    private int direction = 0;
    
    private final Color shipHitboxColor = new Color(0,255,255);
    private BufferedImage shipSprite;
    private int flicker = 0;
    /** Constructor: sets up a new Player at the given starting position. */
    public Player(int startX, int startY)
    {
        //  2.2: Give every instance variable a starting value.
        //           x and y come from the parameters.
        //           Suggested: width = 80, height = 20, speed = 7, direction = 0
        x = startX;
        y = startY;

        try {
            // Option A: Use this if the image is inside your project's source/assets folder (Recommended)
            shipSprite = ImageIO.read(getClass().getResourceAsStream("/ship.png"));

            // Option B: Use this if the image is just loose in your root project folder
            // shipSprite = ImageIO.read(new java.io.File("ship.png"));
        } catch (IOException e) {
            System.out.println("Error: Could not load player image!");
            e.printStackTrace();
        }
    }

    /** Called by GamePanel to tell the player which way to move. */
    public void setDirection(int dir)
    {
        //  3.1: Store dir in the direction instance variable.

        direction = dir;
    }

    /** Moves the player one frame and keeps it inside the window. */
    public void update(int panelWidth)
    {
        //  3.2: Move the ship horizontally: x += direction * speed;
        //

        //  3.3: Keep the ship on screen using two if statements.
        //           - If x is less than 0, set x to 0.
        //           - If the RIGHT edge (x + width) is bigger than panelWidth,
        //             set x so the right edge is exactly at panelWidth.
        if ((x+direction * speed) >= 0 && (x+width+direction * speed) <= panelWidth) x += direction * speed;
    }

    /** Draws the ship. */
    public void draw(Graphics g)
    {
        //  2.3: Draw the ship using g.setColor(...) and g.fillRect(...).
        //           Try a cyan rectangle for the body, and a smaller white
        //           rectangle on top for the cockpit. Be creative!
        //           Reminder: g.fillRect(left, top, width, height);
        

        if (shipSprite != null) {
            flicker++;
            if (flicker <= 10) { //flips the image to add a 'flicker' to the flame on the thruster
                // Draw normally
                g.drawImage(shipSprite,
                        x, y, x + width, y + height,
                        0, 0, shipSprite.getWidth(), shipSprite.getHeight(),
                        null);
            } else {
                // Draw flipped
                g.drawImage(shipSprite,
                        x + width, y, x, y + height,
                        0, 0, shipSprite.getWidth(), shipSprite.getHeight(),
                        null);
                if (flicker >= 20) flicker = 0;
            }
        } else {
            g.setColor(shipHitboxColor);
            g.fillRect(x, y, width, height);
        }
    }

    /** The rectangle that surrounds the ship (used for collisions). */
    public Rectangle getBounds()
    {
        //  2.4: Replace this line so it returns a Rectangle built from
        //           the ship's real x, y, width, and height.
        return new Rectangle(x, y, width, height);
    }
}