package it.unibo.cluedolite.view.secretsolutionview;

import javax.swing.*;

import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.view.AppColorFont;

import java.awt.*;
import java.net.URL;
import java.util.List;

/**
 * Swing view displayed at the end of the game to reveal the secret solution.
 * Shows three cards side by side, each with its image and name,
 * rendered on the application's dark background.
 * The window closes automatically after {@value AUTO_CLOSE_MS} milliseconds.
 */
public class SecretSolutionEndView extends JFrame {

    /** Width in pixels of each individual card panel. */
    private static final int CARD_WIDTH = 200;

    /** Height in pixels of each individual card panel. */
    private static final int CARD_HEIGHT = 300;

    /** Total width of the window. */
    private static final int WINDOW_WIDTH = 900;

    /** Total height of the window. */
    private static final int WINDOW_HEIGHT = 600;

    /** Delay in milliseconds before the window is automatically closed. */
    private static final int AUTO_CLOSE_MS = 3_000;

    /**
     * Creates and displays the secret solution reveal window.
     * Lays out the three solution cards horizontally and starts
     * a timer to dispose the window after {@value AUTO_CLOSE_MS} ms.
     *
     * @param solution the list of three secret cards (character, weapon, room)
     *                 whose names and images will be revealed
     */
    public SecretSolutionEndView(List<Card> solution) {
        setTitle("Secret Solution Revealed");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);

        // Outer panel centres the card row both vertically and horizontally
        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(AppColorFont.BACKGROUND_DARK);

        // Horizontal row that holds the three revealed card panels
        JPanel cardsRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        cardsRow.setBackground(AppColorFont.BACKGROUND_DARK);

        // Build and add one revealed card panel per solution card
        for (Card card : solution) {
            cardsRow.add(createRevealedCard(card.getName(), card.getType().toString()));
        }

        outerPanel.add(cardsRow);
        add(outerPanel);
        setVisible(true);

        // Automatically dispose the window after the configured delay
        Timer timer = new Timer(AUTO_CLOSE_MS, e -> dispose());
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Builds a single revealed card panel composed of:
     * <ul>
     *   <li>a white card area containing the card name and its image;</li>
     *   <li>a label below the card showing the card type.</li>
     * </ul>
     * If the image resource cannot be found, a fallback text is displayed instead.
     *
     * @param cardName  the display name of the card (e.g. "Miss Scarlett")
     * @param typeLabel the category string of the card (e.g. "CHARACTER", "WEAPON", "ROOM")
     * @return a {@link JPanel} representing the fully assembled revealed card
     */
    private JPanel createRevealedCard(String cardName, String typeLabel) {
        // Outer wrapper stacks the white card and the type label vertically
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(AppColorFont.BACKGROUND_DARK);

        // White card panel mimics a physical playing card
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Card name displayed in bold uppercase at the top of the card
        JLabel nameLabel = new JLabel(cardName.toUpperCase());
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Image label that will host the scaled card artwork
        JLabel imageLabel = new JLabel();
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));

        // Attempt to load the card image; fall back to an error message if missing
        ImageIcon icon = loadCardImage(cardName);
        if (icon != null) {
            Image scaled = icon.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        } else {
            imageLabel.setText("Image not found");
            imageLabel.setForeground(Color.RED);
        }

        cardPanel.add(nameLabel);
        cardPanel.add(imageLabel);

        // Type label placed below the white card area
        JLabel typeLabelComponent = new JLabel(typeLabel);
        typeLabelComponent.setAlignmentX(Component.CENTER_ALIGNMENT);
        typeLabelComponent.setFont(AppColorFont.FONT_BODY);
        typeLabelComponent.setForeground(AppColorFont.TEXT_PRIMARY);
        typeLabelComponent.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        wrapper.add(cardPanel);
        wrapper.add(typeLabelComponent);

        return wrapper;
    }

    /**
     * Resolves the image resource for a given card name by normalising the name
     * to a lowercase, whitespace-free filename and trying common image extensions.
     * <p>
     * Naming convention example: {@code "Miss Scarlett"} → {@code /images/missscarlett.png}
     * </p>
     *
     * @param cardName the display name of the card whose image should be loaded
     * @return the loaded {@link ImageIcon}, or {@code null} if no matching
     *         resource is found for any supported extension
     */
    private ImageIcon loadCardImage(String cardName) {
        // Normalise to lowercase with no spaces or dots to match the file naming convention
        String baseName = cardName.toLowerCase()
                .replace(" ", "")
                .replace(".", "");

        // Try each supported image extension in order
        for (String ext : new String[]{".png", ".jpg", ".jpeg"}) {
            URL url = getClass().getResource("/images/" + baseName + ext);
            if (url != null) {
                return new ImageIcon(url);
            }
        }

        System.err.println("Image not found for card: " + cardName);
        return null;
    }
}