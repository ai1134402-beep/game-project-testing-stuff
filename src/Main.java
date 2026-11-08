import javax.swing.JFrame;

/**
 * Main.java  (PROVIDED - you do not need to change this file)
 *
 * This is where the program starts. It creates the window (a JFrame),
 * puts our GamePanel inside it, and makes it visible.
 */
public class Main
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Star Catcher");
        GamePanel panel = new GamePanel();

        frame.add(panel);
        frame.pack();                                  // size the window to fit the panel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);             // center on screen
        frame.setVisible(true);

        panel.requestFocusInWindow();                  // so the keyboard works right away
    }
}
