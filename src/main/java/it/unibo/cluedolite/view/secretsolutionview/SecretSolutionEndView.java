package it.unibo.cluedolite.view.secretsolutionview;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;
import java.util.Locale;

import it.unibo.cluedolite.model.creationcards.impl.AbstractCard;
import it.unibo.cluedolite.view.AppColorFont;

/**
 * Swing view displayed at the end of the game to reveal the secret solution.
 * Shows three cards side by side, each with its image and name,
 * rendered on the application's dark background.
 * The window closes automatically after {@value AUTO_CLOSE_MS} milliseconds.
 */
public final class SecretSolutionEndView extends JFrame {

    private static final Logger LOG = Logger.getLogger(SecretSolutionEndView.class.getName());
    private static final long serialVersionUID = 1L;

    private static final int CARD_WIDTH = 200;
    private static final int CARD_HEIGHT = 300;
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 600;
    private static final int AUTO_CLOSE_MS = 3_000;
    private static final int CARDS_GAP_H = 30;
    private static final int CARD_PADDING = 15;
    private static final int CARD_NAME_FONT_SIZE = 14;
    private static final int NAME_BORDER_BOTTOM = 10;
    private static final int TYPE_BORDER_TOP = 10;
    private static final Font CARD_NAME_FONT = new Font("SansSerif", Font.BOLD, CARD_NAME_FONT_SIZE);
    private static final String[] IMAGE_EXTENSIONS = {".png", ".jpg", ".jpeg"};

    /**
     * Creates and displays the secret solution reveal window.
     * Lays out the three solution cards horizontally and starts
     * a timer to dispose the window after {@value AUTO_CLOSE_MS} ms.
     *
     * @param solution the list of three secret cards (character, weapon, room)
     *                 whose names and images will be revealed
     */
    public SecretSolutionEndView(final List<AbstractCard> solution) {
        setTitle("Secret Solution Revealed");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);

        final JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(AppColorFont.BACKGROUND_DARK);

        final JPanel cardsRow = new JPanel(new FlowLayout(FlowLayout.CENTER, CARDS_GAP_H, 0));
        cardsRow.setBackground(AppColorFont.BACKGROUND_DARK);

        for (final AbstractCard card : solution) {
            cardsRow.add(createRevealedCard(card.getName(), card.getType().toString()));
        }

        outerPanel.add(cardsRow);
        add(outerPanel);
        javax.swing.SwingUtilities.invokeLater(() -> {
            setVisible(true);

            final Timer timer = new Timer(AUTO_CLOSE_MS, e -> dispose());
            timer.setRepeats(false);
            timer.start();
        });
    }

    /**
     * Builds a single revealed card panel with the card name, image, and type label.
     * If the image resource cannot be found, a fallback text is displayed instead.
     *
     * @param cardName  the display name of the card (e.g. "Miss Scarlett")
     * @param typeLabel the category string of the card (e.g. "CHARACTER", "WEAPON", "ROOM")
     * @return a {@link JPanel} representing the fully assembled revealed card
     */
    private JPanel createRevealedCard(final String cardName, final String typeLabel) {
        final JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(AppColorFont.BACKGROUND_DARK);

        final JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(
            CARD_PADDING, CARD_PADDING, CARD_PADDING, CARD_PADDING));

        final JLabel nameLabel = new JLabel(cardName.toUpperCase(Locale.ROOT));
        nameLabel.setAlignmentX(CENTER_ALIGNMENT);
        nameLabel.setFont(CARD_NAME_FONT);
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, NAME_BORDER_BOTTOM, 0));

        final JLabel imageLabel = new JLabel();
        imageLabel.setAlignmentX(CENTER_ALIGNMENT);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));

        final ImageIcon icon = loadCardImage(cardName);
        if (icon != null) {
            final Image scaled = icon.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        } else {
            imageLabel.setText("Image not found");
            imageLabel.setForeground(Color.RED);
        }

        cardPanel.add(nameLabel);
        cardPanel.add(imageLabel);

        final JLabel typeLabelComponent = new JLabel(typeLabel);
        typeLabelComponent.setAlignmentX(CENTER_ALIGNMENT);
        typeLabelComponent.setFont(AppColorFont.FONT_BODY);
        typeLabelComponent.setForeground(AppColorFont.TEXT_PRIMARY);
        typeLabelComponent.setBorder(BorderFactory.createEmptyBorder(TYPE_BORDER_TOP, 0, 0, 0));

        wrapper.add(cardPanel);
        wrapper.add(typeLabelComponent);

        return wrapper;
    }

    /**
     * Loads the image resource for the given card name.
     * The name is normalized to a lowercase filename without spaces or dots.
     * Example: "Miss Scarlett" becomes /images/missscarlett.png.
     *
     * @param cardName the display name of the card whose image should be loaded
     * @return the loaded {@link ImageIcon}, or {@code null} if not found
     */
    private ImageIcon loadCardImage(final String cardName) {
        final String baseName = cardName.toLowerCase(Locale.ROOT)
                .replace(" ", "")
                .replace(".", "");

        for (final String ext : IMAGE_EXTENSIONS) {
            final URL url = getClass().getResource("/images/" + baseName + ext);
            if (url != null) {
                return new ImageIcon(url);
            }
        }

        LOG.warning("Image not found for card: " + cardName);
        return null;
    }
}
