import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GUI {

    private JFrame frame;
    private JPanel mainPanel;  // Main panel for CardLayout
    private CardLayout cardLayout;  // CardLayout to switch between screens

    public GUI() {
        // Create the main frame
        frame = new JFrame();
        frame.setSize(800,800);
        frame.setResizable(false);

        // Set up CardLayout for switching between panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add the main menu panel and about us panel
        mainPanel.add(createMainMenuPanel(), "Main Menu");
        mainPanel.add(createAboutUsPanel(), "About Us");

        // Add the main panel to the frame
        frame.add(mainPanel, BorderLayout.CENTER);

        // Frame settings
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Frogger Menu");
        frame.pack();  // Adjusts the frame to fit its components
        frame.setVisible(true);  // Makes the frame visible
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

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameScreen().gameScreen();  // Open the new game frame
                frame.dispose();  // Close the current main menu frame (optional)
            }
        });

        // Add buttons to the panel
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
