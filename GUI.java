import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.io.File;
import java.io.IOException;

public class GUI {

    private JFrame frame;
    private JPanel mainPanel;  // Main panel for CardLayout
    private CardLayout cardLayout;  // CardLayout to switch between screens
    private GameScreen gameScreen;  // Reference to GameScreen
    private boolean soundEnabled = true;  // Sound is enabled by default
    private Clip clip;  // Clip to hold the sound file

    /**
     * Define a constructor for the GUI class.
     */
    public GUI() {
        // Load the sound file
        loadSound();
        
        // Start playing the sound in a loop
        if (soundEnabled) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);  // Loop sound infinitely
        }

        // Create the main frame
        frame = new JFrame();

        // Set up CardLayout for switching between panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize the game screen
        gameScreen = new GameScreen(frame);  // Create GameScreen instance

        // Add the main menu panel, about us panel, and settings panel
        mainPanel.add(createMainMenuPanel(), "Main Menu");
        mainPanel.add(createAboutUsPanel(), "About Us");
        mainPanel.add(createSettingsPanel(), "Settings");  // Add the settings panel

        // Add the main panel to the frame
        frame.add(mainPanel, BorderLayout.CENTER);

        // Frame settings
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Frogger Menu");
        frame.pack();  // Adjusts the frame to fit its components
        frame.setVisible(true);  // Makes the frame visible
        frame.setSize(800, 800);
        frame.setResizable(false);
    }

    // Method to load the sound file
    public void loadSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("resources/sound.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to toggle sound on and off
    public void toggleSound() {
        if (soundEnabled) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);  // Loop sound infinitely
        } else {
            clip.stop();  // Stop the sound if disabled
        }
    }

    // Method to create the main menu panel
    private JPanel createMainMenuPanel() {
        // Custom panel with background image
        BackgroundPanel panel = new BackgroundPanel(new ImageIcon(getClass().getResource("/resources/background.jpg")).getImage());

        // Set panel's border and layout with reduced vertical gap between buttons
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        panel.setLayout(new GridLayout(0, 1, 0, 5));  // 5px vertical gap between buttons

        // Load and scale images for buttons
        JButton startButton = createButtonWithScaledIcon("/resources/Start.png", 200);
        JButton aboutButton = createButtonWithScaledIcon("/resources/AboutUs.png", 200);
        JButton settingsButton = createButtonWithScaledIcon("/resources/Settings.png", 200);

        // Add ActionListener to "About Us" button to switch to the "About Us" screen
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "About Us");  // Switch to the About Us panel
            }
        });

        // Add ActionListener to "Settings" button to switch to the "Settings" screen
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Settings");  // Switch to the Settings panel
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame gameFrame = new JFrame();  // Create a new frame for the game
                gameFrame.add(gameScreen);  // Add the existing gameScreen instance
                gameFrame.setSize(800, 800);
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameFrame.setVisible(true);
                gameScreen.requestFocusInWindow();  // Request focus for key input

                frame.dispose();  // Close the main menu frame (optional)
            }
        });

        // Add buttons to the panel.
        panel.add(startButton);
        panel.add(aboutButton);
        panel.add(settingsButton);

        return panel;
    }

    // Method to create the "About Us" panel
    private JPanel createAboutUsPanel() {
        // Create a panel for the About Us screen
        JPanel aboutPanel = new JPanel(new BorderLayout());

        // Add content to the About Us panel
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Main Menu");  // Switch back to the main menu panel
            }
        });

        aboutPanel.add(backButton, BorderLayout.SOUTH);  // Add the back button at the bottom
        aboutPanel.add(new JButton("This is the About Us screen!"), BorderLayout.CENTER);  // Replace with actual info

        return aboutPanel;
    }

    // Method to create the "Settings" panel
    private JPanel createSettingsPanel() {
        JPanel settingsPanel = new JPanel(new GridLayout(5, 1));  // Adjust grid layout to fit the sound button

        // Difficulty buttons (already implemented)
        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton = new JButton("Hard");

        // Add ActionListener for easy difficulty
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameScreen.setDifficulty(1.0);  // Easy: Multiply speed by 1
            }
        });

        // Add ActionListener for medium difficulty
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameScreen.setDifficulty(1.5);  // Medium: Multiply speed by 1.5
            }
        });

        // Add ActionListener for hard difficulty
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameScreen.setDifficulty(2.0);  // Hard: Multiply speed by 2
            }
        });

        // Sound toggle button
        JButton soundButton = new JButton("Sound On");
        soundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundEnabled = !soundEnabled;  // Toggle sound
                soundButton.setText(soundEnabled ? "Sound On" : "Sound Off");  // Update button label
                toggleSound();  // Enable or disable sound
            }
        });

        // Add content to the Settings panel
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Main Menu");  // Switch back to the main menu panel
            }
        });

        // Add buttons to the settings panel
        settingsPanel.add(easyButton);
        settingsPanel.add(mediumButton);
        settingsPanel.add(hardButton);
        settingsPanel.add(soundButton);  // Add the sound button to the settings panel
        settingsPanel.add(backButton);  // Add the back button at the bottom

        return settingsPanel;
    }

    // Utility method to create a button with a scaled icon
    private JButton createButtonWithScaledIcon(String imagePath, int targetWidth) {
        ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));

        // Scale the image while maintaining aspect ratio
        Image scaledImage = originalIcon.getImage().getScaledInstance(targetWidth, -1, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(scaledImage));

        // Remove the button text
        button.setText("");
        button.setBorderPainted(false);  // Remove button border for a cleaner look
        button.setFocusPainted(false);   // Remove focus border
        button.setContentAreaFilled(false);  // Make sure only the icon is displayed without background color

        // Reduce button margin to minimize the space between buttons
        button.setBorder(new EmptyBorder(5, 5, 5, 5));  // Adjust the padding inside the button as needed

        return button;
    }

    public static void main(String[] args) {
        // Run the GUI
        new GUI();
    }
}

// Custom JPanel class to paint background
class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    // Constructor to set the background image
    public BackgroundPanel(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    // Override paintComponent to draw the background
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
