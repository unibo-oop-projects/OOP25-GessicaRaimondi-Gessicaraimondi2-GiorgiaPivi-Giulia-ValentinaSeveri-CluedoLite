package it.unibo.cluedolite.view.cardview;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.GridBagLayout;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Swing view that displays a single card image given its name.
 * Opens a large window with a black background and the card centered.
 * The window closes automatically after 5 seconds or when the close button is clicked.
 */
public class CardView extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(CardView.class.getName());
    private static final int CARD_WIDTH = 500;
    private static final int CARD_HEIGHT = 600;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 900;
    private static final int AUTO_CLOSE_MS = 5_000;

    /**
     * Creates and shows a large window displaying the card with the given name.
     *
     * @param cardName the name of the card (e.g. "Miss Scarlett", "Revolver")
     */
    public CardView(String cardName) {
        setTitle(cardName);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);

        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(Color.BLACK);

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel nameLabel = new JLabel(cardName.toUpperCase());
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel imageLabel = new JLabel();
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));

        ImageIcon icon = loadCardImage(cardName);
        if (icon != null) {
            Image scaled = icon.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        } else {
            imageLabel.setText("Image not found: " + cardName);
            imageLabel.setForeground(Color.RED);
        }

        cardPanel.add(nameLabel);
        cardPanel.add(imageLabel);

        outerPanel.add(cardPanel);

        add(outerPanel);
        setVisible(true);

        Timer timer = new Timer(AUTO_CLOSE_MS, e -> dispose());
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Converts the card name to a filename and loads the image from resources.
     * Example: "Miss Scarlett" -> /images/missscarlett.png
     *
     * @param cardName the name of the card
     * @return the loaded {@link ImageIcon}, or {@code null} if not found
     */
    private ImageIcon loadCardImage(String cardName) {

        String baseName = cardName.toLowerCase()
                .replace(" ", "")
                .replace(".", "");

        for (String ext : new String[]{".png", ".jpg", ".jpeg"}) {
            URL url = getClass().getResource("/images/" + baseName + ext);
            if (url != null) {
                return new ImageIcon(url);
            }
        }
        LOG.warning("Image not found for card: " + cardName);
        return null;
    }
}