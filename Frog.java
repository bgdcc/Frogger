
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Implement the Frog class.
 */
public class Frog extends JPanel implements ActionListener, KeyListener {
    Timer t = new Timer(500, this); // Implement a timer.

    // Implement the original coordinates of the frog/player.
    private int x = GameScreen.getWidth() / 2 + 1;
    private int y = GameScreen.getHeight() - 120;
    
    // Implement the Frog's wio
    private int frogWidth = 40;
    private int frogHeight = 40;

    private RenderedImage image = null;
    private String sprite;

    private Rectangle frog;

    /** 
     * Implement a constructor for the Frog class.
     * */
    Frog() {
        frog = new Rectangle(x, y, frogWidth, frogHeight);
        switchSprite("resources/frog.png");

        t.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 =  (Graphics2D) g;
        //g2.fill(frog);

        g2.drawImage((Image) image, this.x, this.y, null);
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

    /**
     * Define a set of parameters to restrict the frog's movement.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (x < 0) {
            x = 0;
        }

        if (x > GameScreen.displayWidth - frogWidth + 1) {
            x = GameScreen.displayWidth - frogWidth + 1;
        }

        if (y < 0) {
            y = 0;
        }

        if (y > GameScreen.displayHeight - frogHeight + 1) {
            y = GameScreen.displayHeight - frogHeight + 1;
        }

        repaint();
    }

    public void keyPressed(KeyEvent e) {
        int theKey = e.getKeyCode();

        if (theKey == KeyEvent.VK_UP) {
            switchSprite("resources/frog.png");
            y -= GameScreen.GRID;
            frog.setLocation(x, y);
        }

        if (theKey == KeyEvent.VK_DOWN) {
            switchSprite("resources/frog_front.png");
            y += GameScreen.GRID;
            frog.setLocation(x, y);
        }

        if (theKey == KeyEvent.VK_RIGHT) {
            switchSprite("resources/frog_right.png");
            x += GameScreen.GRID;
            frog.setLocation(x, y);
        }

        if (theKey == KeyEvent.VK_LEFT) {
            switchSprite("resources/frog_left.png");
            x -= GameScreen.GRID;
            frog.setLocation(x, y);
        }
    }

    public void keyTyped(KeyEvent e) {}
    
    public void keyReleased(KeyEvent e) {}
}
