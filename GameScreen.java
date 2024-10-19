import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameScreen extends JPanel implements Runnable, KeyListener {
    private Frog froggy;
    private Log loggy;
    private Car[] cars = new Car[4];
    private Motorcycle[] motorcycles = new Motorcycle[4];
    private Truck[] trucks = new Truck[4];
    private Image backgroundImage;
    private Image heartImage;
    private JButton backToMenuButton;
    private JFrame gameFrame;

    static final int DISPLAY_HEIGHT = 800;
    static final int DISPLAY_WIDTH = 800;
    static final int GRID = 60;

    private final int initialX = DISPLAY_WIDTH / 2 - 19;
    private final int initialY = DISPLAY_HEIGHT - 59;

    private boolean gameOver = false;
    private int lifeCounter = 3;
    private int score = 0;  // Initialize score

    public GameScreen(JFrame frame) {
        this.gameFrame = frame;

        // Load the background and heart images
        try {
            backgroundImage = ImageIO.read(new File("resources/frogger_map.png"));
            heartImage = ImageIO.read(new File("resources/heart.png"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        froggy = new Frog(initialX, initialY, 40, 40);
        loggy = new Log();
        setUpTheVehicles();
        setUpBackToMenuButton(); // Call to set up the "Back to Menu" button

        addKeyListener(this);
        setFocusable(true);
        setVisible(true);
        setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);

        new Thread(this).start();
    }

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

    public void setUpBackToMenuButton() {
        backToMenuButton = new JButton("Back to Menu");
        backToMenuButton.setVisible(false); // Initially hidden
        backToMenuButton.setBounds(300, 500, 200, 50);
        backToMenuButton.addActionListener(e -> returnToMenu());

        this.setLayout(null); // Use absolute layout for positioning the button
        add(backToMenuButton);
    }

    public void returnToMenu() {
        gameFrame.dispose(); // Close the game window
        GUI mainMenu = new GUI();  // Reopen the main menu by creating a new instance
    }

    public void isInsideLog() {
        if (froggy.getCenterY() >= loggy.getLogY()
                && froggy.getCenterY() <= loggy.getMaxY()
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

        for (int i = 0; i < 4; i++) {
            trucks[i].draw(g);
            cars[i].draw(g);
            motorcycles[i].draw(g);
        }

        loggy.draw(g);
        froggy.draw(g);

        // Draw hearts based on life counter
        for (int i = 0; i < lifeCounter; i++) {
            g.drawImage(heartImage, 10 + i * 40, 10, 32, 32, null);
        }

        // Draw the score on the right side
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Score: " + score, DISPLAY_WIDTH - 200, 50);

        // If game over, show the score and game over text
        if (gameOver) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", 250, 400);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Final Score: " + score, 250, 470);
            backToMenuButton.setVisible(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int theKey = e.getKeyCode();

        if (!gameOver) {
            new Thread(() -> {
                try {
                    // Move up
                    if (theKey == KeyEvent.VK_UP) {
                        froggy.switchSprite("resources/frog.png");
                        if (froggy.frogY - GRID >= 0) {
                            froggy.switchSprite("resources/frog_front.png");
                            froggy.frogY -= GRID / 2;
                            Thread.sleep(32);
                            froggy.frogY -= GRID / 2;
                            froggy.switchSprite("resources/frog.png");

                            // Update score when moving forward
                            score += 10;
                        }
                    }

                    // Move down
                    if (theKey == KeyEvent.VK_DOWN) {
                        froggy.switchSprite("resources/frog_front.png");
                        if (froggy.frogY + GRID <= DISPLAY_HEIGHT - froggy.frogHeight) {
                            froggy.switchSprite("resources/frog.png");
                            froggy.frogY += GRID / 2;
                            Thread.sleep(32);
                            froggy.frogY += GRID / 2;
                            froggy.switchSprite("resources/frog_front.png");
                        }
                    }

                    // Move right
                    if (theKey == KeyEvent.VK_RIGHT) {
                        froggy.switchSprite("resources/frog_right.png");
                        if (froggy.frogX + GRID <= DISPLAY_WIDTH - froggy.frogWidth) {
                            froggy.switchSprite("resources/frog_left.png");
                            froggy.frogX += GRID / 2;
                            Thread.sleep(32);
                            froggy.frogX += GRID / 2;
                            froggy.switchSprite("resources/frog_right.png");
                        }
                    }

                    // Move left
                    if (theKey == KeyEvent.VK_LEFT) {
                        froggy.switchSprite("resources/frog_left.png");
                        if (froggy.frogX - GRID >= 0) {
                            froggy.switchSprite("resources/frog_right.png");
                            froggy.frogX -= GRID / 2;
                            Thread.sleep(32);
                            froggy.switchSprite("resources/frog_left.png");
                            froggy.frogX -= GRID / 2;
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

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public void checkCollision() {
        Rectangle frogBounds = froggy.getBounds();

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

    public void resetGame() {
        froggy.frogX = initialX;
        froggy.frogY = initialY;
        repaint();
    }

    @Override
    public void run() {
        while (true) {
            if (!gameOver) {
                for (int i = 0; i < 4; i++) {
                    motorcycles[i].move();
                    cars[i].move();
                    trucks[i].move();
                }
                loggy.move();

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

    public void startUp() {
        JFrame frame = new JFrame("Frog Game");
        GameScreen gameScreen = new GameScreen(frame);

        frame.add(gameScreen);
        frame.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        gameScreen.requestFocusInWindow();
    }
}
