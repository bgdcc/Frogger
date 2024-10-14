import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
    private int y = 300;

    private int vehicleSpeed = 50;

    private int vehicleWidth = 80;
    private int vehicleHeight = 40;

    private Rectangle vehicle;

    Vehicle() {
        vehicle = new Rectangle(x, y, vehicleWidth, vehicleHeight);

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, vehicleWidth, vehicleHeight);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.fill(vehicle);
        // t.start();
    }

    public void move() {
        if (x > GameScreen.DISPLAY_WIDTH) {
            x = 0;
        }

        x += vehicleSpeed;
    }

    /*
    public void actionPerformed(ActionEvent e) {
        if (x > GameScreen.getWidth()) {
            x = 0;
        }

        x += vehicleSpeed;
        repaint();
    }*/
}
