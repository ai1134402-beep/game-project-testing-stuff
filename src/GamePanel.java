import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * GamePanel.java
 * The "brain" of the game. It runs the game loop, handles the keyboard,
 * keeps track of every object, and draws everything on the screen.
 * GAME LOOP (runs about 60 times per second):
 *      1. actionPerformed() is called by the Timer
 *      2. update()  -> change the game's data (move things, check collisions)
 *      3. repaint() -> Java calls paintComponent() to redraw the screen
 * Search for "" to find every place you need to write code.
 */
public class GamePanel extends JPanel implements ActionListener, KeyListener
{
    // ----- constants -----
    public static final int WIDTH = 600;
    public static final int HEIGHT = 700;
    private static final int DELAY = 16;              // milliseconds between frames (~60 fps)
    private static final int STARTING_LIVES = 3;
    private static final int NUM_BG_STARS = 60;

    //  1.1: Change this color to your own design. new Color(red, green, blue)
    //           where each value is 0-255.
    private static final Color BACKGROUND_COLOR = new Color(10, 10, 40); // I like this color for space

    // ----- game data -----
    private Timer timer;
    private Player player;
    private ArrayList<FallingObject> objects;         // every Star and Asteroid on screen
    private int[] bgX;                                // background star positions
    private int[] bgY;                                //   (parallel arrays)
    private int score;
    private int lives;
    private int level;
    private int frameCount;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean gameOver;
    private int direction;

    // =====================================================================
    //  SET UP
    // =====================================================================

    public GamePanel()
    {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(BACKGROUND_COLOR);
        setFocusable(true);
        addKeyListener(this);

        startNewGame();

        //  4.4 (TEMPORARY TEST): Uncomment the next line to test your
        //          FallingObject code. You should see one star fall down.
        //          Delete or comment it out again when you finish Step 5.
        objects.add(new Star(280, 2));

        timer = new Timer(DELAY, this);
        timer.start();
    }

    /** Resets every piece of game data. Used at the start AND when restarting. */
    private void startNewGame()
    {
        player = new Player(WIDTH / 2 - 40, HEIGHT - 60);
        objects = new ArrayList<FallingObject>();

        bgX = new int[NUM_BG_STARS];
        bgY = new int[NUM_BG_STARS];
        //  5.1: Use a for loop to give every background star a random
        //           position:   bgX[i] = (int) (Math.random() * WIDTH);
        //                       bgY[i] = (int) (Math.random() * HEIGHT);
        //           (Hint: loop i from 0 up to bgX.length.)
        for (int i = 0; i < bgX.length; i++) {
            bgX[i] = (int) (Math.random() * WIDTH);
            bgY[i] = (int) (Math.random() * HEIGHT);
        }
        score = 0;
        lives = STARTING_LIVES;
        level = 1;
        frameCount = 0;
        leftPressed = false;
        rightPressed = false;
        gameOver = false;
    }

    // =====================================================================
    //  GAME LOOP
    // =====================================================================

    @Override
    public void actionPerformed(ActionEvent e)
    {
        update();
        repaint();
    }

    /** Updates ALL game data for one frame. */
    private void update()
    {
        if (gameOver)
        {
            return;
        }
        frameCount++;

        // --- player movement ---
        //  3.4: Figure out which way the player should move:
        //           - start a local int named direction at 0
        //           - if leftPressed is true, subtract 1 from direction
        //           - if rightPressed is true, add 1 to direction
        //           Then call:  player.setDirection(direction);
        //           and:        player.update(WIDTH);
        //           (Why use TWO separate ifs instead of if / else if?
        //            Answer in your notebook after you test it.)

        
        direction = 0;

        if (leftPressed) direction--;
        if (rightPressed) direction++;

        player.setDirection(direction);
        player.update(WIDTH);

        // --- background stars drift downward ---
        //  5.2: Write a for loop over the bgY array. For each star:
        //           - add 1 + i % 3 to bgY[i]  (so stars drift at different speeds)
        //           - if bgY[i] is greater than HEIGHT, wrap it back to the top:
        //                 bgY[i] = 0;
        //                 bgX[i] = (int) (Math.random() * WIDTH);
        for (int i = 0; i < bgY.length; i++) {
            bgY[i] += 1 + i % 3;
            if (bgY[i] > HEIGHT) {
                bgY[i] = 0;
                bgX[i] = (int) (Math.random() * WIDTH);
            }
        }


        // --- difficulty ---
        //  7.2: Calculate the level from the score:  level = score / 100 + 1;
        //           (Integer division! What level are you on at score 99? 100?)
        level = score / 100 + 1;
        int spawnRate = Math.max(15, 60 - 5 * level); // frames between new objects

        // --- spawning ---
        if (frameCount % spawnRate == 0)
        {
            spawnObject();
        }

        // --- falling objects: move, collide, remove ---
        //  4.5: Write an enhanced for loop that goes through objects and
        //           calls update() on each one:
        //               for (FallingObject obj : objects) { ... }
        //           (This is enough to test Step 4. You will REPLACE it in Step 5.)
        for (int i = objects.size() - 1; i >= 0; i--) {
            FallingObject obj = objects.get(i);
            obj.update();
            if (obj.getBounds().intersects(player.getBounds())) {
                if (obj.isHazard()) lives--;
                else score += 10;
                objects.remove(i);
            } else if (obj.isOffScreen(HEIGHT)) objects.remove(i);
        }

        //  5.4: REPLACE your enhanced for loop above with a for loop that
        //           counts BACKWARDS through the list:
        //               for (int i = objects.size() - 1; i >= 0; i--)
        //           Inside the loop:
        //             a) FallingObject obj = objects.get(i);
        //             b) obj.update();
        //             c) if obj.isOffScreen(HEIGHT) then objects.remove(i);
        //           WHY backwards? Because removing an item shifts everything
        //           after it. Going backwards means no item gets skipped.
        //           (Try going forwards and watch what happens!)
        //
        //  6.2: Collisions. Inside your loop, BEFORE the off-screen check:
        //             if (obj.getBounds().intersects(player.getBounds()))
        //           then:
        //             - if obj.isHazard() is true, subtract 1 from lives
        //             - otherwise add 10 to score
        //             - remove the object from the list
        //           and make the off-screen check an "else if" after it.


        // --- game over? ---
        //  7.1: If lives is 0 or less, set gameOver to true.
        if (lives <= 0) gameOver = true;

    }

    /** Creates one new Star (70% of the time) or Asteroid (30%) at a random x. */
    private void spawnObject()
    {
        //  5.3: Create a Star and add it to the objects list.
        //           - pick a random x:     int x = (int) (Math.random() * (WIDTH - 40));
        //           - pick a random speed: int speed = 2 + (int) (Math.random() * 3);
        //           - objects.add(new Star(x, speed));
        //
        //  6.1: AFTER you write Asteroid.java, change this method so it
        //           makes an Asteroid 30% of the time:
        //               if (Math.random() < 0.3) { objects.add(new Asteroid(x, speed)); }
        //               else                     { objects.add(new Star(x, speed));     }
        //           Then make the objects faster as the level goes up:
        //               int speed = 2 + (int) (Math.random() * 3) + level / 2;
        int x = (int) (Math.random() * (WIDTH - 40));
        int speed = 2 + (int) (Math.random() * 3);

        if (Math.random() <= .5) objects.add(new Asteroid(x,speed));
        else objects.add(new Star(x,speed));
    }

    // =====================================================================
    //  DRAWING   (PROVIDED - you do not need to change this section)
    // =====================================================================

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);   // clears the screen with the background color

        // background stars
        g.setColor(new Color(190,190,190));
        for (int i = 0; i < bgX.length; i++)
        {
            g.fillRect(bgX[i], bgY[i], 2, 2);
        }

        // falling objects (each one draws itself - polymorphism!)
        for (FallingObject obj : objects)
        {
            obj.draw(g);
        }

        player.draw(g);

        // heads-up display
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 15, 30);
        g.drawString("Level: " + level, 15, 55);
        g.drawString("Lives: " + lives, WIDTH - 110, 30);

        if (gameOver)
        {
            g.setColor(new Color(0, 0, 0, 170));
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            drawCentered(g, "GAME OVER", HEIGHT / 2 - 20);
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            drawCentered(g, "Final score: " + score, HEIGHT / 2 + 25);
            drawCentered(g, "Press R to play again", HEIGHT / 2 + 65);
        }
    }

    /** Helper (PROVIDED): draws text centered horizontally. */
    private void drawCentered(Graphics g, String text, int y)
    {
        FontMetrics fm = g.getFontMetrics();
        int x = (WIDTH - fm.stringWidth(text)) / 2;
        g.drawString(text, x, y);
    }

    // =====================================================================
    //  KEYBOARD
    // =====================================================================

    @Override
    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();

        //  3.5: If code equals KeyEvent.VK_LEFT or KeyEvent.VK_A,
        //           set leftPressed to true.
        //           If code equals KeyEvent.VK_RIGHT or KeyEvent.VK_D,
        //           set rightPressed to true.
        //           (Use the OR operator ||  to accept either key.)
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) leftPressed = true;
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) rightPressed = true;

        //  7.3: If the game is over AND the player pressed KeyEvent.VK_R,
        //           call startNewGame().  (Use the AND operator &&.)
        if (gameOver && code == KeyEvent.VK_R) startNewGame();
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        int code = e.getKeyCode();

        //  3.6: Do the opposite of keyPressed: when a key is RELEASED,
        //           set leftPressed / rightPressed back to false.

        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) leftPressed = false;
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) rightPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        // not needed, but required by the KeyListener interface
    }
}
