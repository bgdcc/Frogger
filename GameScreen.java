import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class GameScreen extends JPanel implements Runnable, KeyListener {
    private Frog froggy;
    //private Vehicle vehi;
    private Image backgroundImage;
    private Vehicle truck;
    private Vehicle car;
    private Vehicle motorcycle;
    //private JButton backToMenuButton;
    //private JFrame gameFrame;

    static final int DISPLAY_HEIGHT = 800;
    static final int DISPLAY_WIDTH = 800;
    public static final int GRID = 40;
    private boolean gameOver = false;

    public GameScreen() {
        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("resources/map.png"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // Initialize the frog
        froggy = new Frog();
       
        truck = new Vehicle(0, 300, 5); 
        car = new Vehicle(0, 400, 10); 
        motorcycle = new Vehicle(0, 500, 15); 
        //backToMenuButton = new JButton("Back to Menu");
        //backToMenuButton.setVisible(false);  
        /*backToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Go back to the main menu when clicked
                returnToMenu();
            }
        });
        */
        // Setup the game screen
        addKeyListener(this);
        setFocusable(true);
        setVisible(true);
        setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);

        // Start the game loop
          
          this.setLayout(null);  
          //backToMenuButton.setBounds(300, 500, 200, 50);  
          //add(backToMenuButton);
        new Thread(this).start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image newImage = backgroundImage.getScaledInstance(800, 800, Image.SCALE_DEFAULT);

        // Draw the background
        if (backgroundImage != null) {
            g.drawImage(newImage, 0, 0, null);
        }

        // Draw the frog
        froggy.draw(g);
        truck.draw(g);
        car.draw(g);
        motorcycle.draw(g);
        if (gameOver) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", 250, 400);
            //backToMenuButton.setVisible(true);

        }
    }

    
    @Override
    public void keyPressed(KeyEvent e) {
        int theKey = e.getKeyCode();

        // Update frog position based on arrow key input
        if (theKey == KeyEvent.VK_UP) {
            froggy.switchSprite("resources/frog.png");
            if (froggy.frogY - GRID >= 0) {
                froggy.frogY -= GRID; // Move up
            }
        }

        if (theKey == KeyEvent.VK_DOWN) {
            froggy.switchSprite("resources/frog_front.png");
            if (froggy.frogY + GRID <= DISPLAY_HEIGHT - froggy.frogHeight) {
                froggy.frogY += GRID; // Move down
            }
        }

        if (theKey == KeyEvent.VK_RIGHT) {
            froggy.switchSprite("resources/frog_right.png");
            if (froggy.frogX + GRID <= DISPLAY_WIDTH - froggy.frogWidth) {
                froggy.frogX += GRID; // Move right
            }
        }

        if (theKey == KeyEvent.VK_LEFT) {
            froggy.switchSprite("resources/frog_left.png");
            if (froggy.frogX - GRID >= 0) {
                froggy.frogX -= GRID; // Move left
            }
        }

    
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public void checkCollision() {
        Rectangle frogBounds = froggy.getBounds();
        if (frogBounds.intersects(truck.getBounds()) || frogBounds.intersects(car.getBounds()) || frogBounds.intersects(motorcycle.getBounds())) {
            gameOver = true;
        }
    }

    @Override
    public void run() {
        while (true) {
            if (!gameOver) {
                truck.move();
                car.move();
                motorcycle.move();

                checkCollision();
                repaint();
                
            }

            try {
                Thread.sleep(16); // Approx. 60 FPS
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
    }
    

    public void startUp() {
        JFrame frame = new JFrame("Frog Game");
        GameScreen gameScreen = new GameScreen();

        frame.add(gameScreen);
        frame.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        gameScreen.requestFocusInWindow(); // Ensure the game screen gets focus for key events
    }
}
