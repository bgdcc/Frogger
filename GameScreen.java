import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameScreen {
    private JFrame f = new JFrame();

    private Image backgroundImage;
    
    static final int DISPLAY_HEIGHT = 800;
    static final int DISPLAY_WIDTH = 800;
    public static final int GRID = 40;

    public void paintComponent(Graphics g) throws IOException {
        try {
            backgroundImage = ImageIO.read(new File("resources/map.png"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        g.drawImage(backgroundImage, 0, 0, null);
    }

    public void gameScreen() {    
        Frog froggy = new Frog();
        // Vehicle vehi = new Vehicle();

        f.add(froggy);
        // f.add(vehi);

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 800);
        f.setResizable(false);
    }

    public static int getHeight() {
        return DISPLAY_HEIGHT;
    }

    public static int getWidth() {
        return DISPLAY_WIDTH;
    }

}
