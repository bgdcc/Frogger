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
    //Timer t = new Timer(500, this); // Implement a timer.

    // Implement the original coordinates of the frog/player.
    int frogX = GameScreen.DISPLAY_WIDTH / 2 + 1;
    int frogY = GameScreen.DISPLAY_HEIGHT - 120;
    
    // Implement the Frog's wio
    int frogWidth = 40;
    int frogHeight = 40;

    int frogCenterX = frogX - 1 + frogWidth / 2;
    int frogCenterY = frogY + frogHeight / 2;

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

    public Rectangle getBounds() {
        return frog;
    }

    /**
     * Define a set of parameters to restrict the frog's movement.
     */
    //@Override
    /*
    public void actionPerformed(ActionEvent e) {
        if (x < 0) {
            x = 0;
        }

        if (x > GameScreen.DISPLAY_WIDTH - frogWidth + 1) {
            x = GameScreen.DISPLAY_WIDTH - frogWidth + 1;
        }

        if (y < 0) {
            y = 0;
        }

        if (y > GameScreen.DISPLAY_HEIGHT - frogHeight + 1) {
            y = GameScreen.DISPLAY_HEIGHT - frogHeight + 1;
        }

        repaint();
    }*/

    /**
     * Define the frog's movements based on the keys previously pressed.
     * @param e registers the code of the key previously pressed.
     */
    /*
    public void keyPressed(KeyEvent e) {
        int theKey = e.getKeyCode();

        if (theKey == KeyEvent.VK_UP) {
            switchSprite("resources/frog.png");

            if (y - GameScreen.GRID < 0) {
                y = 0;
            } else {
                y -= GameScreen.GRID;
            }

            frog.setLocationfro(x, y);
        }

        if (theKey == KeyEvent.VK_DOWN) {
            switchSprite("resources/frog_front.png");

            if (y + GameScreen.GRID > GameScreen.DISPLAY_WIDTH - frogWidth + 1) {
                y = GameScreen.DISPLAY_WIDTH - frogWidth + 1;
            } else {
                y += GameScreen.GRID;
            }

            frog.setLocation(x, y);
        }

        if (theKey == KeyEvent.VK_RIGHT) {
            switchSprite("resources/frog_right.png");

            if (x + GameScreen.GRID > GameScreen.DISPLAY_WIDTH - frogWidth + 1) {
                x = GameScreen.DISPLAY_WIDTH - frogWidth + 1;
            } else {
                x += GameScreen.GRID;
            }

            frog.setLocation(x, y);
        }

        if (theKey == KeyEvent.VK_LEFT) {
            switchSprite("resources/frog_left.png");

            if (x - GameScreen.GRID < 0) {
                x = 0;
            } else {
                x += GameScreen.GRID;
            }

            x -= GameScreen.GRID;
            frog.setLocation(x, y);
        }
    }

    public void keyTyped(KeyEvent e) {}
    
    public void keyReleased(KeyEvent e) {}
    */
}
