import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


public class GameScreen extends JPanel implements Runnable, KeyListener {
    // Declare all the objects pertaining to the program.
    private Frog froggy;
    private Log loggy;

    private Vehicle truck;
    private Vehicle car;
    private Vehicle motorcycle;

    private Vehicle[] vehicles = new Vehicle[3];

    // Declare a variable meant to store the background image.
    private Image backgroundImage;
    //private JButton backToMenuButton;
    //private JFrame gameFrame;

    static final int DISPLAY_HEIGHT = 800;
    static final int DISPLAY_WIDTH = 800;
    static final int GRID = 40;

    // Initialize the frog's starting coordinates.
    private final int initialX = DISPLAY_WIDTH / 2 + 1;
    private final int initialY = DISPLAY_HEIGHT - 120;


    private boolean gameOver = false;
    private int lifeCounter = 3;



    /** 
     * Implement a constructor for the GameScreen class.
    */
    public GameScreen() {
        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("resources/map.png"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // Initialize all the objects.
        froggy = new Frog();
        loggy = new Log();
       
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
        // Setup the game screen.
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

    public void isInsideLog() {
        if (froggy.getCenterY() >= loggy.getLogY()
                && froggy.getCenterY()  <= loggy.getMaxY()
                && froggy.getCenterX() <= loggy.getMaxX()
                && froggy.getCenterX() >= loggy.getLogX()) {

            froggy.frogX += loggy.logSpeed;
        }
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
        truck.draw(g);
        car.draw(g);
        motorcycle.draw(g);
        loggy.draw(g);
        froggy.draw(g);

        if (gameOver) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", 250, 400);
            //backToMenuButton.setVisible(true);
        }
    }

    /** 
     * Update the frog's position based on the key input.
     * @param e registers the key which has been pressed recently.
    */
    @Override
    public void keyPressed(KeyEvent e) {
        int theKey = e.getKeyCode();

        //if (!gameOver && lifeCounter != 0){
        // Update the frog's position based on the key input

        // Move up.
        if(!gameOver){
            if (theKey == KeyEvent.VK_UP) {
                froggy.switchSprite("resources/frog.png");
                if (froggy.frogY - GRID >= 0) {
                    froggy.frogY -= GRID; // Move up
                }
            }

            // Move down.
            if (theKey == KeyEvent.VK_DOWN) {
                froggy.switchSprite("resources/frog_front.png");
                if (froggy.frogY + GRID <= DISPLAY_HEIGHT - froggy.frogHeight) {
                    froggy.frogY += GRID;
                }
            }

            // Move right.
            if (theKey == KeyEvent.VK_RIGHT) {
                froggy.switchSprite("resources/frog_right.png");
                if (froggy.frogX + GRID <= DISPLAY_WIDTH - froggy.frogWidth) {
                    froggy.frogX += GRID;
                }
            }
        

            // Move left.
            if (theKey == KeyEvent.VK_LEFT) {
                froggy.switchSprite("resources/frog_left.png");
                if (froggy.frogX - GRID >= 0) {
                    froggy.frogX -= GRID; 
                }
            }
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    /**
     * Checks if the frog was hit by a car.
     */
    public void checkCollision() {
        Rectangle frogBounds = froggy.getBounds();
        if (frogBounds.intersects(truck.getBounds()) 
            || frogBounds.intersects(car.getBounds())
            || frogBounds.intersects(motorcycle.getBounds())) {
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
                loggy.move();

                // Check if the player comes into contact with any other object.
                isInsideLog();
                checkCollision();
                repaint();
                
            }

            try {
                Thread.sleep(48); // Approx. 60 FPS
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
