import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Define the Lilypad class.
 * Once the 
 */
public class Lilypad extends JPanel {
    private int x;
    private int y;

    int lilyHeight = 60;
    int lilyWidth = 60;

    private RenderedImage image = null;

    boolean isAvailable;

    /**
     * Implement a constructor for the Lilypad class.
     * @param x is the x-coordinate.
     * @param y is the y-coordinate.
     */
    public Lilypad(int x, int y) {
        this.x = x;
        this.y = y;

        this.isAvailable = true;

        switchSprite("resources/lilypad.png");
    }

    /**
     * Method to switch sprites after the player lands on the lilypad.
     * @param sprite is the name of the sprite.
     */
    public void switchSprite(String sprite) {
        try {
            image = ImageIO.read(new File(sprite));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Draw the Lilypad.
     * @param g is a Swing specific class which helps in drawing the Object.
     */
    public void draw(Graphics g) {
        g.drawImage((Image) image, x, y, lilyWidth, lilyHeight, null);

    }

    /**
     * Draws the Lilypad using a Swing specific method.
     * @param g is a Swing specific class which helps in drawing the Object.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage((Image) image, this.x, this.y, null);
    }

    public int getLilyX() {
        return x;
    }

    public int getLilyY() {
        return y;
    }

    public Rectangle getLilypad() {
        return new Rectangle(x, y, lilyWidth, lilyHeight);
    }
}
