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

    // private Vehicle truck;
    // private Vehicle car;
    // private Vehicle motorcycle;

    private Car[] cars = new Car[4];
    private Motorcycle[] motorcycles = new Motorcycle[4];
    private Truck[] trucks = new Truck[4];

    // Declare a variable meant to store the background image.
    private Image backgroundImage;
    //private JButton backToMenuButton;
    //private JFrame gameFrame;

    static final int DISPLAY_HEIGHT = 800;
    static final int DISPLAY_WIDTH = 800;
    static final int GRID = 60;

    // Initialize the frog's starting coordinates.
    private final int initialX = DISPLAY_WIDTH / 2 - 19;
    private final int initialY = DISPLAY_HEIGHT - 59;


    private boolean gameOver = false;
    private int lifeCounter = 3;

    /** 
     * Implement a constructor for the GameScreen class.
    */
    public GameScreen() {
        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("resources/frogger_map.png"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // Initialize all the objects.
        froggy = new Frog(initialX, initialY, 40, 40);
        loggy = new Log();

        setUpTheVehicles();
       
        // truck = new Vehicle(0, 300, 5); 
        // car = new Vehicle(0, 400, 10); 
        // motorcycle = new Vehicle(0, 500, 15); 
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

    /**
     * Initialize all the vehicles/logs present in the game.
     */
    public void setUpTheVehicles() {
        cars[0] = new Car(1, 576);
        cars[1] = new Car(201, 576);
        cars[2] = new Car(401, 576);
        cars[3] = new Car(601, 576);

        motorcycles[0] = new Motorcycle(1, 451);
        motorcycles[1] = new Motorcycle(401, 451);
        motorcycles[2] = new Motorcycle(121, 691);
        motorcycles[3] = new Motorcycle(521, 691);

        trucks[0] = new Truck(1, 631);
        trucks[1] = new Truck(401, 631);
        trucks[2] = new Truck(161, 511);
        trucks[3] = new Truck(561, 511);
    }

    /**
     * Check if the player comes into contact with a log.
     */
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

        // Draw the background.
        if (backgroundImage != null) {
            g.drawImage(newImage, 0, 0, null);
        }

        // Draw the frog

        // Draw all the vehicles.
        for (int i = 0; i < 4; i++) {
            trucks[i].draw(g);
            cars[i].draw(g);
            motorcycles[i].draw(g);
        }

        // truck.draw(g);
        // car.draw(g);
        // motorcycle.draw(g);
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
        if (!gameOver) {
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
     * Checks if the frog was hit by a vehicle.
     */
    public void checkCollision() {
        Rectangle frogBounds = froggy.getBounds();

        for (int i = 0; i < 4; i++) {
            if (frogBounds.intersects(trucks[i].getBounds()) 
                || frogBounds.intersects(cars[i].getBounds())
                || frogBounds.intersects(motorcycles[i].getBounds())) {
                gameOver = true;
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            if (!gameOver) {
                //truck.move();
                //car.move();
                //motorcycle.move();

                for (int i = 0; i < 4; i++) {
                    motorcycles[i].move();
                    cars[i].move();
                    trucks[i].move();
                }
                loggy.move();

                // Check if the player comes into contact with any other object.
                isInsideLog();
                checkCollision();
                repaint();
                
            }

            try {
                Thread.sleep(32); // Approx. 60 FPS
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
    }
    

    /**  
     * Generates the gamescreen, will be called from the main class.
    */
    public void startUp() {
        JFrame frame = new JFrame("Frog Game");
        GameScreen gameScreen = new GameScreen();

        frame.add(gameScreen);
        frame.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        gameScreen.requestFocusInWindow();
    }
}
