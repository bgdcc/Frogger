import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Define the GameScreen class.
 */
public class GameScreen extends JPanel implements Runnable, KeyListener {
    private Frog player; //Initialize the player object.

    // Initialize the object arrays.
    private Car[] cars = new Car[4];
    private Motorcycle[] motorcycles = new Motorcycle[4];
    private Truck[] trucks = new Truck[4];
    private Log[] logs = new Log[8];
    private Turtle[] turtles = new Turtle[15];
    private Lilypad[] lilypads = new Lilypad[5];

    // Initializr the images used.
    private Image backgroundImage;
    private Image heartImage;
    private Image winFrogImage;

    private JButton backToMenuButton;
    private JButton playAgainButton;
    private JFrame gameFrame;

    static final int DISPLAY_HEIGHT = 800;
    static final int DISPLAY_WIDTH = 800;
    static final int GRID = 60;

    private final int initialX = DISPLAY_WIDTH / 2 - 19;
    private final int initialY = DISPLAY_HEIGHT - 59;

    static boolean previousMusicChoice = GUI.soundEnabled;

    // Implement a variable which tracks the frog's progress on the map.
    private int currentProgress = initialY;

    private int gamesWon = 0;
    private boolean winCondition;
    private boolean gameOver;
    private int lifeCounter = 3;
    private int score = 0;  // Initialize score.

    /** 
     * Implement a constructor for the GameScreen class.
     * */
    public GameScreen(JFrame frame) {
        this.gameFrame = frame;

        // Load the background and heart images.
        try {
            backgroundImage = ImageIO.read(new File("resources/frogger_map.png"));
            heartImage = ImageIO.read(new File("resources/heart.png"));
            winFrogImage = ImageIO.read(new File("resources/win_frog.png"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        setUpTheObjects();
        setUpPlayAgainButton();
        setUpBackToMenuButton(); // Set up the "Back to Menu" button.

        addKeyListener(this);
        setFocusable(true);
        setVisible(true);
        setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);

        new Thread(this).start();
    }

    /**
     * Initialize all the obstacles the frog/player will encounter.
     */
    public void setUpTheObjects() {
        player = new Frog(initialX, initialY, 40, 40);

        cars[0] = new Car(1, 576, 1);
        cars[1] = new Car(201, 576, 1);
        cars[2] = new Car(401, 576, 1);
        cars[3] = new Car(601, 576, 1);

        motorcycles[0] = new Motorcycle(1, 451, 1);
        motorcycles[1] = new Motorcycle(401, 451, 1);
        motorcycles[2] = new Motorcycle(121, 691, 1);
        motorcycles[3] = new Motorcycle(521, 691, 1);

        trucks[0] = new Truck(1, 621, -1);
        trucks[1] = new Truck(401, 621, -1);
        trucks[2] = new Truck(161, 501, -1);
        trucks[3] = new Truck(561, 501, -1);

        logs[0] = new Log(1, 271, 120, -1);
        logs[1] = new Log(271, 271, 120, -1);
        logs[2] = new Log(541, 271, 120, -1);
        logs[3] = new Log(1, 211, 300, 1);
        logs[4] = new Log(601, 211, 300, 1);
        logs[5] = new Log(271, 91, 120, 1);
        logs[6] = new Log(541, 91, 120, 1);
        logs[7] = new Log(11, 91, 120, 1);

        turtles[0] = new Turtle(1, 321, 1);
        turtles[1] = new Turtle(61, 321, 1);
        turtles[2] = new Turtle(121, 321, 1);
        turtles[3] = new DrowningTurtle(241, 321, 1);
        turtles[4] = new DrowningTurtle(301, 321, 1);
        turtles[5] = new DrowningTurtle(361, 321, 1);
        turtles[6] = new Turtle(481, 321, 1);
        turtles[7] = new Turtle(541, 321, 1);
        turtles[8] = new Turtle(601, 321, 1);
        turtles[9] = new DrowningTurtle(241, 141, -1);
        turtles[10] = new DrowningTurtle(301, 141, -1);
        turtles[11] = new Turtle(511, 141, -1);
        turtles[12] = new Turtle(571, 141, -1);
        turtles[13] = new Turtle(781, 141, -1);
        turtles[14] = new Turtle(841, 141, -1);

        lilypads[0] = new Lilypad(131, 21);
        lilypads[1] = new Lilypad(251, 21);
        lilypads[2] = new Lilypad(371, 21);
        lilypads[3] = new Lilypad(491, 21);
        lilypads[4] = new Lilypad(611, 21);

        for (Car car: cars) {
            car.switchSprite();
        }

        for (Motorcycle motorcycle: motorcycles) {
            motorcycle.switchSprite();
        }

        for (Truck truck: trucks) {
            truck.switchSprite();
        }

    }

    /**
     * Set up a back to menu button for the moment when the game ends.
     * 
     */
    public void setUpBackToMenuButton() {
        backToMenuButton = new JButton("Back to Menu");
        backToMenuButton.setVisible(false); 
        backToMenuButton.setBounds(300, 550, 200, 50);
        backToMenuButton.addActionListener(e -> returnToMenu());

        this.setLayout(null);
        add(backToMenuButton);
    }

    /**
     * Set up a play again button for the moment when the game ends.
     */
    public void setUpPlayAgainButton() {
        playAgainButton = new JButton("Play Again");
        playAgainButton.setVisible(false); // Initially hidden
        playAgainButton.setBounds(300, 500, 200, 50);
        playAgainButton.addActionListener(e -> playTheGameAgain());

        this.setLayout(null);
        add(playAgainButton);
    }

    /**
     * Go back to the menu once the game ends.
     */
    public void returnToMenu() {
        gameFrame.dispose(); // Close the game window.

        GUI.soundEnabled = false;
        new GUI();  // Reopen the main menu by creating a new instance.
    }

    /**
     * Replay the game after it ends.
     */
    public void playTheGameAgain() {
        startUp();
    }

    /**
     * Modify the speed of the vehicles according to the difficulty.
     * @param multiplier is dependent on the difficulty.
     */
    public void setDifficulty(double multiplier) {
        // Adjust the speed of all vehicles based on the multiplier.
        for (Car car : cars) {
            car.vehicleSpeed *= multiplier;
        }
        for (Motorcycle motorcycle : motorcycles) {
            motorcycle.vehicleSpeed *= multiplier;
        }
        for (Truck truck : trucks) {
            truck.vehicleSpeed *= multiplier;
        }
    }

    /**
     * Implement a method which makes the frog move alongside the log when in contact.
     */
    public void checkForLogSpeed() {
        for (Log loggy: logs) {
            if (player.getCenterY() >= loggy.getLogY()
                && player.getCenterY() <= loggy.getMaxY()
                && player.getCenterX() <= loggy.getMaxX()
                && player.getCenterX() >= loggy.getLogX()) {
                player.frogX += loggy.logSpeed;
            }
        }
    }

    /**
     * Checks if the frog is on a log.
     */
    public boolean isOnLog() {
        for (Log loggy: logs) {
            if (player.getCenterY() >= loggy.getLogY()
                && player.getCenterY() <= loggy.getMaxY()
                && player.getCenterX() <= loggy.getMaxX()
                && player.getCenterX() >= loggy.getLogX()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Implement a method which ends the player's current life if in contact with the water.
     */
    public void checkWaterContact() {
        int frogMaxY = player.frogY + player.frogWidth - 1;
        // Modify if wrong with 51
        if (frogMaxY < 381
             && !isOnLog() && !isOnTurtle()
             && !isOnLilyPad()
             && player.frogY % 60 == 21) {

            lifeCounter--;

            if (lifeCounter == 0) {        
                gameOver = true;
            } else {
                resetGame();
            }
        }
    }

    /**
     * Increase the player's speed if they are on a turtle.
     */
    public void checkTurtleContact() {
        for (Turtle turt: turtles) {
            if (player.getCenterY() >= turt.getTurtleY() 
                && player.getCenterY() <= turt.getTurtleY() + turt.turtleHeight - 1
                && player.getCenterX() >= turt.getTurtleX()
                && player.getCenterX() <= turt.getTurtleX() + turt.turtleWidth - 1) {
            
                if (turt.touchable) {
                    player.frogX += turt.turtleSpeed;
                }
            }
        }
    }

    /**
     * Check if the player comes in contact with a turtle.
     * @return true if the player is on a turtle.
     */
    public boolean isOnTurtle() {

        for (Turtle turt: turtles) {

            if (player.getCenterY() >= turt.getTurtleY() 
                && player.getCenterY() <= turt.getTurtleY() + turt.turtleHeight - 1
                && player.getCenterX() >= turt.getTurtleX()
                && player.getCenterX() <= turt.getTurtleX() + turt.turtleWidth - 1
                && turt.touchable) {
                return true;
            }
        }

        return false;
    }

    /**
     * Go to the next game if the player is on a lilypad.
     * Win the game if the player wins 5 consecutive times.
     */
    public void checkLilypadContact() {
        Rectangle frogBounds = player.getBounds();

        for (Lilypad lily: lilypads) {
            Rectangle lilyBounds = lily.getLilypad();

            if (frogBounds.intersects(lilyBounds) 
                && lily.isAvailable
                && player.frogY % 60 == 21) {

                gamesWon++;
                lily.isAvailable = false;
                lily.switchSprite("resources/lilypad_with_frog.png");

                if (gamesWon == 5) {
                    winCondition = true;
                } else {
                    resetGame();
                }
            }
        }
    }

    /**
     * Check if the player is on a lilypad.
     * @return true if the player is on a lilypad.
     */
    public boolean isOnLilyPad() {
        Rectangle frogBounds = player.getBounds();

        for (Lilypad lily: lilypads) {
            Rectangle lilyBounds = lily.getLilypad();

            if (frogBounds.intersects(lilyBounds) 
                && lily.isAvailable
                && player.frogY % 60 == 21) {
                
                return true;
            }
        }

        return false;
    } 

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image newImage = backgroundImage.getScaledInstance(800, 800, Image.SCALE_DEFAULT);

        // Draw the background.
        if (backgroundImage != null) {
            g.drawImage(newImage, 0, 0, null);
        }

        // Draw all the Objects present in the game.
        for (int i = 0; i < logs.length; i++) {
            logs[i].draw(g);
        }

        for (int i = 0; i < turtles.length; i++) {
            turtles[i].draw(g);
        }

        for (int i = 0; i < lilypads.length; i++) {
            lilypads[i].draw(g);
        }

        player.draw(g);

        for (int i = 0; i < 4; i++) {
            trucks[i].draw(g);
            cars[i].draw(g);
            motorcycles[i].draw(g);
        }

        // Draw hearts based on life counter.
        for (int i = 0; i < lifeCounter; i++) {
            g.drawImage(heartImage, i * 40, 10, 60, 60, null);
        }

        // Draw the score on the right side.
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRect(DISPLAY_WIDTH - 120, 6, 135, 70);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Score: " + score, DISPLAY_WIDTH - 115, 50);

        // If game over, show the score and let the player return back to menu or start again.
        if (gameOver) {
            g.setColor(new Color(0, 0, 0, 160));
            g.fillRect(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);

            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", 250, 400);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Final Score: " + score, 250, 470);
            playAgainButton.setVisible(true);
            backToMenuButton.setVisible(true);
        }

        // If the player won, show the score and let the player return back to menu or start again.
        if (winCondition) {
            g.setColor(new Color(0, 0, 0, 160));
            g.fillRect(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);

            g.drawImage(winFrogImage, 160, -55, 500, 500, null);

            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString(" YOU WIN!", 250, 400);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Final Score: " + score, 250, 470);
            playAgainButton.setVisible(true);
            backToMenuButton.setVisible(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int theKey = e.getKeyCode();

        if (!gameOver && !winCondition) {
            new Thread(() -> {
                try {
                    // Move up.
                    if (theKey == KeyEvent.VK_UP) {
                        player.switchSprite("resources/frog_sprites/frog.png");
                        if (player.frogY - GRID >= 0) {
                            // Switch between sprites to denote movement.
                            player.switchSprite("resources/frog_sprites/frog_jump.png");
                            player.frogY -= GRID / 2;
                            Thread.sleep(48);
                            player.frogY -= GRID / 2;
                            player.switchSprite("resources/frog_sprites/frog.png");

                            // Update score when moving forward.
                            if (player.frogY < currentProgress) {
                                currentProgress = player.frogY;
                                score += 10; 
                            }
                        }
                    }

                    // Move down.
                    if (theKey == KeyEvent.VK_DOWN) {
                        player.switchSprite("resources/frog_sprites/frog_front.png");
                        if (player.frogY + GRID <= DISPLAY_HEIGHT - player.frogHeight) {
                            player.switchSprite("resources/frog_sprites/frog_front_jump.png");
                            player.frogY += GRID / 2;
                            Thread.sleep(48);
                            player.frogY += GRID / 2;
                            player.switchSprite("resources/frog_sprites/frog_front.png");
                        }
                    }

                    // Move right.
                    if (theKey == KeyEvent.VK_RIGHT) {
                        player.switchSprite("resources/frog_sprites/frog_right.png");
                        if (player.frogX + GRID <= DISPLAY_WIDTH - player.frogWidth) {
                            player.switchSprite("resources/frog_sprites/frog_right_jump.png");
                            player.frogX += GRID / 2;
                            Thread.sleep(48);
                            player.frogX += GRID / 2;
                            player.switchSprite("resources/frog_sprites/frog_right.png");
                        }
                    }

                    // Move left.
                    if (theKey == KeyEvent.VK_LEFT) {
                        player.switchSprite("resources/frog_sprites/frog_left.png");
                        if (player.frogX - GRID >= 0) {
                            player.switchSprite("resources/frog_sprites/frog_left_jump.png");
                            player.frogX -= GRID / 2;
                            Thread.sleep(48);
                            player.switchSprite("resources/frog_sprites/frog_left.png");
                            player.frogX -= GRID / 2;
                        }
                    }

                    repaint();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }).start();
        }

        repaint();
    }

    public void keyTyped(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}

    /**
     * Check collision between frog and vehicles.
     * 
     */
    public void checkCollision() {
        Rectangle frogBounds = player.getBounds();

        for (int i = 0; i < 4; i++) {
            if (frogBounds.intersects(trucks[i].getBounds())
                || frogBounds.intersects(cars[i].getBounds())
                || frogBounds.intersects(motorcycles[i].getBounds())) {
                lifeCounter--;
                if (lifeCounter == 0) {
                    gameOver = true;
                } else {
                    resetGame();
                }
            }
        }
    }

    /**
     * Reset the frog to its initial coordinates.
     */
    public void resetGame() {
        player.frogX = initialX;
        player.frogY = initialY;

        currentProgress = initialY;

        repaint();
    }

    // Call the previously defined methods.
    @Override
    public void run() {
        while (true) {
            // if (!gameOver && !winCondition) {
            for (int i = 0; i < 4; i++) {
                motorcycles[i].move();
                cars[i].move();
                trucks[i].move();
            }

            for (int i = 0; i < logs.length; i++) {
                logs[i].move();
            }

            for (int i = 0; i < turtles.length; i++) {
                turtles[i].move();
            }

            checkForLogSpeed();
            checkTurtleContact();
            checkLilypadContact();
            checkCollision();
            checkWaterContact();
            repaint();
            // }

            try {
                Thread.sleep(32);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * Run the program.
     */
    public void startUp() {
        JFrame frame = new JFrame();
        GameScreen gameScreen = new GameScreen(frame);

        frame.add(gameScreen);
        frame.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        gameScreen.requestFocusInWindow();
    }
}