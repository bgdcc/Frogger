import java.awt.event.*;
import java.awt.image.*;
import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Log extends JPanel {
    private int x = 0;
    private int y = 640;

    int logSpeed = 35;

    int logWidth = 80;
    int logHeight = 40;

    private Rectangle log;

    Log() {
        log = new Rectangle(x, y, logWidth, logHeight);

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, logWidth, logHeight);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.fill(log);
    }

    public void move() {
        if (x > GameScreen.DISPLAY_WIDTH) {
            x = 0;
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