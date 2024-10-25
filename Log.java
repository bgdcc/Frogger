import java.awt.event.*;
import java.awt.image.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Log extends JPanel {
    Random rand = new Random();
    
    private int x;
    private int y;

    private RenderedImage image;

    int logSpeed = 10;

    int logWidth;
    int logHeight = 40;

    private Rectangle log;

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

    

    public void draw(Graphics g) {
        // Image newImage = ((Image) image).getScaledInstance(logWidth, logHeight, Image.SCALE_DEFAULT);
        g.drawImage((Image) image, x, y, logWidth, logHeight, null);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage((Image) image, this.x, this.y, null);
    }

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