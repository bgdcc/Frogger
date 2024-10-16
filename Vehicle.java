import java.awt.event.*;
import java.awt.image.*;
import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Vehicle extends JPanel {
    // Timer t = new Timer(500, this);

    private int x = 0;
    private int y = 400;

    private int vehicleSpeed;

    private int vehicleWidth = 80;
    private int vehicleHeight = 40;

    private Rectangle vehicle;

    Vehicle(int x, int y, int vehicleSpeed) {
        this.x = x;
        this.y = y;
        this.vehicleSpeed = vehicleSpeed;
        vehicle = new Rectangle(x, y, vehicleWidth, vehicleHeight);

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, vehicleWidth, vehicleHeight);
    }

    public void move() {
        if (x > GameScreen.DISPLAY_WIDTH) {
            x = 0;
        }
        x += vehicleSpeed;
        vehicle.setBounds(x, y, vehicleWidth, vehicleHeight);
    }

    public Rectangle getBounds() {
        return vehicle;
    }
}
