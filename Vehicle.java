import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Vehicle extends JPanel {
    // Timer t = new Timer(500, this);

    private int x = 0;
    private int y = 400;

    private String folderName;

    int vehicleSpeed;

    int vehicleWidth;
    int vehicleHeight;

    private RenderedImage image = null;
    private String sprite;
    private Rectangle vehicle;

    /** 
     * Implement a constructor for the Vehicle class.
    */
    Vehicle(int x, int y) {
        this.x = x;
        this.y = y;
        vehicle = new Rectangle(x, y, vehicleWidth, vehicleHeight);

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, vehicleWidth, vehicleHeight);
    }

    /** 
     * Move the vehicle on the screen.
     */
    public void move() {
        if (x - 1 + vehicleWidth <= 0 && vehicleSpeed < 0 ) {
            x = GameScreen.DISPLAY_WIDTH + 1;
        } else if (x > GameScreen.DISPLAY_WIDTH && vehicleSpeed > 0) {
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
     * @param sprite
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
}