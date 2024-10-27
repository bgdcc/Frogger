import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Define the Vehicle super-class.
 */
public class Vehicle extends JPanel {
    Random rand = new Random();

    private int x = 0;
    private int y = 400;

    // ArrayList<String> options = new ArrayList<>();
    String folderName;
    String vehicleType;

    double vehicleSpeed;

    int vehicleWidth;
    int vehicleHeight;

    private RenderedImage image = null;
    private Rectangle vehicle;

    /** 
     * Implement a constructor for the Vehicle class.
     * @param x is the x-coordinate.
     * @param y is the y-coordinate.
     * @param direction is the direction of the Vehicle.
    */
    Vehicle(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        vehicle = new Rectangle(x, y, vehicleWidth, vehicleHeight);
        
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
        // Move the vehicle across the screen.
        if (x - 1 + vehicleWidth <= 0 && vehicleSpeed < 0) {
            switchSprite();
            x = GameScreen.DISPLAY_WIDTH + 1;
        } else if (x > GameScreen.DISPLAY_WIDTH && vehicleSpeed > 0) {
            switchSprite();
            x = -vehicleWidth;
        }
        x += vehicleSpeed;  // Move according to the current speed
        vehicle.setBounds(x, y, vehicleWidth, vehicleHeight);
    }


    public Rectangle getBounds() {
        return vehicle;
    }

    /**
     * Initialize a method to switch between sprites.
     */
    public void switchSprite() {
        int colorIndex = rand.nextInt(4);
        String[] options = {"option1", "option2", "option3", "option4"};
        
        try {
            String desiredSprite = "resources/" + this.folderName + "/" 
                                 + options[colorIndex] + ".png";

            image = ImageIO.read(new File(desiredSprite));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}