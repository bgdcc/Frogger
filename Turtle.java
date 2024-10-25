import java.awt.event.*;
import java.awt.image.*;
import java.util.Random;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Turtle extends JPanel {
    private int x;
    private int y;

    private RenderedImage image;

    int turtleSpeed = 10;

    int turtleWidth = 60;
    int turtleHeight = 60;

    // Implement a boolean which tracks if the turtle is underwater.
    boolean touchable = true;

    // Random rand  = new Random();
    int direction;

    private Rectangle turtle;

    Turtle(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;

        this.turtleSpeed *= direction;
        // this.turtleType = turtleType;

        turtle = new Rectangle(x, y, turtleWidth, turtleHeight);

        //
        try {
            image = ImageIO.read(new File("resources/vector_turtle.png"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        repaint();

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    

    public void draw(Graphics g) {
        g.drawImage((Image) image, x, y, turtleWidth, turtleHeight, null);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage((Image) image, this.x, this.y, null);
    }

    public void move() {
        if (x > GameScreen.DISPLAY_WIDTH && turtleSpeed > 0) {
            switchSprite("resources/vector_turtle.png");
            // x = - Log.logWidth;
            x = - turtleWidth;
        } else if (x - 1 + turtleWidth <= 0 && turtleSpeed < 0) {
            x = GameScreen.DISPLAY_WIDTH + 1;
        }

        x += turtleSpeed;
    }

    public int getTurtleX() {
        return x;
    }

    public int getMaxX() {
        return x + turtleWidth - 1;
    }

    public int getMaxY() {
        return y + turtleHeight - 1;
    }

    public int getTurtleY() {
        return y;
    }

    public Rectangle getBounds() {
        return turtle;
    }

    public void switchSprite(String sprite) {
        try {
            //File intialFile = new File(sprite);
            image = ImageIO.read(new File(sprite));
            //ImageIO.write(image, "png", new File(sprite));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}