import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Vehicle extends JPanel {
    Random rand = new Random();

    private int x = 0;
    private int y = 400;

    // private String folderName;
    // private relativePosition;

    int vehicleSpeed;

    int vehicleWidth;
    int vehicleHeight;

    private RenderedImage image = null;
    private String sprite;
    private Rectangle vehicle;

    /** 
     * Implement a constructor for the Vehicle class.
    */
    Vehicle(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        vehicle = new Rectangle(x, y, vehicleWidth, vehicleHeight);

        switchSprite();

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
        g2.drawImage((Image) image, this.x, this.y, null);
    }

    public void draw(Graphics g) {
        g.drawImage((Image) image, x, y, vehicleWidth, vehicleHeight, null);
    }

    /** 
     * Move the vehicle on the screen.
     */
    public void move() {
        if (x - 1 + vehicleWidth <= 0 && vehicleSpeed < 0) {
            switchSprite();
            x = GameScreen.DISPLAY_WIDTH + 1;
        } else if (x > GameScreen.DISPLAY_WIDTH && vehicleSpeed > 0) {
            switchSprite();
            x = -vehicleWidth;
        }
        x += vehicleSpeed;
        vehicle.setBounds(x, y, vehicleWidth, vehicleHeight);
    }


    public Rectangle getBounds() {
        return vehicle;
    }

    /**
     * Initialize a method to switch between sprites.
     */
    public void switchSprite() {
        // this.sprite = sprite;

        String[] colors = {"red", "blue", "yellow", "orange"};
        int colorIndex = rand.nextInt(4);
        try {
            image = ImageIO.read(new File("resources/" + colors[colorIndex] + "_car.png"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}