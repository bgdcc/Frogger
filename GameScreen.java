import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameScreen {
    private JFrame f = new JFrame();

    private Image backgroundImage;
    
    static int displayHeight = 800;
    static int displayWidth = 800;

    public static int GRID = 40;

    public static int getHeight() {
        return displayHeight;
    }

    public static int getWidth() {
        return displayWidth;
    }

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

        f.add(froggy);

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 800);
        f.setResizable(false);
    }
}
