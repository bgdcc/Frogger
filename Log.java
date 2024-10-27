import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Implement the Log class.
 */
public class Log extends JPanel {
    Random rand = new Random();
    
    private int x;
    private int y;

    private RenderedImage image;

    int logSpeed = 10;

    int logWidth;
    int logHeight = 40;

    private Rectangle log;

    /**
     * Implement a constructor for the Log class.
     * @param x is the x-coordinate.
     * @param y is the y-coordinate.
     * @param newWidth is the width of th Log.
     * @param direction is the direction of the Log (1 moves to the right, -1 moves to the left).
     */
    Log(int x, int y, int newWidth, int direction) {
        this.x = x;
        this.y = y;
        this.logWidth = newWidth;

        logSpeed *= direction;
        log = new Rectangle(x, y, logWidth, logHeight);

        try {
            image = ImageIO.read(new File("resources/log_with_border.png"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    /**
     * Draws the log image.
     * @param g helps draw the log image.
     */
    public void draw(Graphics g) {
        g.drawImage((Image) image, x, y, logWidth, logHeight, null);

    }

    /**
     * Inherits the g parameter and draws the image.
     * @param g helps draw the log image.
    */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage((Image) image, this.x, this.y, null);
    }

    /**
     * Moves the log across the map.
     */
    public void move() {
        if (x > GameScreen.DISPLAY_WIDTH && logSpeed > 0) {
            x = -logWidth;
        } else if (x - 1 + logWidth < 0 && logSpeed < 0) {
            x = GameScreen.DISPLAY_WIDTH + 1;
        }

        x += logSpeed;
    }

    public int getLogX() {
        return x;
    }

    public int getMaxX() {
        return x + logWidth - 1;
    }

    public int getMaxY() {
        return y + logHeight - 1;
    }

    public int getLogY() {
        return y;
    }

    public Rectangle getBounds() {
        return log;
    }
}