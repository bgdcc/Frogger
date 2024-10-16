import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Implement the Frog class.
 */
public class Frog extends JPanel {
    // Implement the original coordinates of the frog/player.
    int frogX = GameScreen.DISPLAY_WIDTH / 2 + 1;
    int frogY = GameScreen.DISPLAY_HEIGHT - 120;
    
    // Implement the Frog's wio
    int frogWidth = 40;
    int frogHeight = 40;

    public RenderedImage image = null;
    private String sprite;

    private Rectangle frog;

    /** 
     * Implement a constructor for the Frog class.
     * */
    Frog() {
        frog = new Rectangle(frogX, frogY, frogWidth, frogHeight);
        switchSprite("resources/frog.png");

        //t.start();
        //addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
    
    /**
     * Implement a method which initializez the Object's image.
     * @param g paints the image on screen.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 =  (Graphics2D) g;
        g2.drawImage((Image) image, this.frogX, this.frogY, null);
    }
    public Rectangle getBounds() {
        return new Rectangle(frogX, frogY, frogWidth, frogHeight);
    }
    public void draw(Graphics g) {
        g.drawImage((Image) image, frogX, frogY, frogWidth, frogHeight, null);
    }

    /**
     * Implement a function which can change the current sprite.
     * @param sprite represents the name of the image the sprite is  obtained from.
     */
    public void switchSprite(String sprite) {
        this.sprite = sprite;
        try {
            //File intialFile = new File(sprite);
            image = ImageIO.read(new File(sprite));
            //ImageIO.write(image, "png", new File(sprite));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public int getCenterX() {
        return frogX - 1 + frogWidth / 2;
    }

    public int getCenterY() {
        return frogY - 1 + frogHeight / 2;
    }
}
