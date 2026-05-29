package it.unibo.cluedolite.view.secretsolutionview;

import javax.swing.*;

import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.view.AppColorFont;

import java.awt.*;
import java.util.List;

/**
 * Swing view displayed when the secret solution is created.
 * Shows three face-down cards (CHARACTER, WEAPON, ROOM) side by side,
 * with a decorative envelope card below them, all on the application's dark background.
 * The window closes automatically after {@value AUTO_CLOSE_MS} milliseconds.
 */
public class SecretSolutionStartView extends JFrame {

    /** Width in pixels of each individual face-down card panel. */
    private static final int CARD_WIDTH = 200;

    /** Height in pixels of each individual face-down card panel. */
    private static final int CARD_HEIGHT = 300;

    /** Width in pixels of the decorative envelope panel. */
    private static final int ENVELOPE_WIDTH = 300;

    /** Height in pixels of the decorative envelope panel. */
    private static final int ENVELOPE_HEIGHT = 200;

    /** Delay in milliseconds before the window is automatically closed. */
    private static final int AUTO_CLOSE_MS = 3_000;

    /**
     * Creates and displays the secret solution start window.
     * Lays out three face-down cards horizontally and a decorative envelope
     * below them, then starts a timer to dispose the window after
     * {@value AUTO_CLOSE_MS} ms.
     *
     * @param solution the list of three secret cards (character, weapon, room)
     *                 whose categories are shown but whose identities remain hidden
     */
    public SecretSolutionStartView(List<Card> solution) {
        setTitle("Secret Solution");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // ← schermo intero
        setUndecorated(true);                      // ← rimuove la barra del titolo
        setLocationRelativeTo(null);

        // Outer panel centres all content both vertically and horizontally
        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(AppColorFont.BACKGROUND_DARK);

        // Main vertical panel stacks the card row and the envelope
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(AppColorFont.BACKGROUND_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Horizontal row holding the three face-down card panels
        JPanel cardsRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        cardsRow.setBackground(AppColorFont.BACKGROUND_DARK);

        // One covered card per solution slot: character, weapon, room
        String[] labels = {"CHARACTER", "WEAPON", "ROOM"};
        for (String label : labels) {
            cardsRow.add(createCoveredCard(label));
        }

        // Decorative envelope displayed below the three cards
        JPanel envelopeCard = createEnvelopeCard();

        // Wrapper centres the card row horizontally inside the main panel
        JPanel cardsRowWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cardsRowWrapper.setBackground(AppColorFont.BACKGROUND_DARK);
        cardsRowWrapper.add(cardsRow);

        // Wrapper centres the envelope horizontally inside the main panel
        JPanel envelopeWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        envelopeWrapper.setBackground(AppColorFont.BACKGROUND_DARK);
        envelopeWrapper.add(envelopeCard);

        mainPanel.add(cardsRowWrapper);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(envelopeWrapper);

        outerPanel.add(mainPanel);
        add(outerPanel);
        setVisible(true);

        // Automatically dispose the window after the configured delay
        Timer timer = new Timer(AUTO_CLOSE_MS, e -> dispose());
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Builds a single face-down card panel composed of:
     * <ul>
     *   <li>a custom-painted card back showing a question mark;</li>
     *   <li>a label below the card indicating the card category.</li>
     * </ul>
     * The card back is painted with a dark-red fill, a white inner border,
     * and a large white question mark centred on the face.
     *
     * @param label the category label to display beneath the card
     *              (e.g. "CHARACTER", "WEAPON", "ROOM")
     * @return a {@link JPanel} representing the fully assembled face-down card
     */
    private JPanel createCoveredCard(String label) {
        // Outer wrapper stacks the card graphic and the category label vertically
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(AppColorFont.BACKGROUND_DARK);

        // Custom-painted panel that draws the card back
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Dark-red card back fill
                g2.setColor(AppColorFont.BACKGROUND_LIGHT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // White inner border to mimic a card frame
                g2.setColor(AppColorFont.TEXT_PRIMARY);
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(8, 8, getWidth() - 16, getHeight() - 16, 15, 15);

                // Large question mark centred on the card face
                g2.setFont(new Font("SansSerif", Font.BOLD, 80));
                FontMetrics fm = g2.getFontMetrics();
                String q = "?";
                int x = (getWidth() - fm.stringWidth(q)) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(q, x, y);
            }
        };
        card.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        card.setOpaque(false);

        // Category label placed below the card graphic
        JLabel typeLabel = new JLabel(label);
        typeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        typeLabel.setFont(AppColorFont.FONT_BODY);
        typeLabel.setForeground(AppColorFont.TEXT_PRIMARY);
        typeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        wrapper.add(card);
        wrapper.add(typeLabel);

        return wrapper;
    }

    /**
     * Builds the decorative envelope panel displayed below the three face-down cards.
     * The envelope is custom-painted with:
     * <ul>
     *   <li>a dark-red rounded rectangle as the body;</li>
     *   <li>a white outer border;</li>
     *   <li>a medium-red triangular flap at the top with a white border;</li>
     *   <li>a large white question mark centred on the body.</li>
     * </ul>
     *
     * @return a {@link JPanel} representing the decorative envelope
     */
    private JPanel createEnvelopeCard() {
        // Custom-painted panel that draws the envelope shape
        JPanel envelope = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Dark-red envelope body
                g2.setColor(AppColorFont.BACKGROUND_DARK);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // White outer border around the envelope body
                g2.setColor(AppColorFont.TEXT_PRIMARY);
                g2.setStroke(new BasicStroke(4));
                g2.drawRoundRect(6, 6, getWidth() - 12, getHeight() - 12, 15, 15);

                // Triangular flap at the top of the envelope — medium red fill
                int[] xPoints = {0, getWidth() / 2, getWidth()};
                int[] yPoints = {0, getHeight() / 2, 0};
                g2.setColor(AppColorFont.BACKGROUND_MEDIUM);
                g2.fillPolygon(xPoints, yPoints, 3);

                // White border on the triangular flap
                g2.setColor(AppColorFont.TEXT_PRIMARY);
                g2.setStroke(new BasicStroke(2));
                g2.drawPolygon(xPoints, yPoints, 3);

                // Large white question mark centred on the envelope body
                g2.setColor(AppColorFont.TEXT_PRIMARY);
                g2.setFont(new Font("SansSerif", Font.BOLD, 60));
                FontMetrics fm = g2.getFontMetrics();
                String q = "?";
                int x = (getWidth() - fm.stringWidth(q)) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2 + 20;
                g2.drawString(q, x, y);
            }
        };
        envelope.setPreferredSize(new Dimension(ENVELOPE_WIDTH, ENVELOPE_HEIGHT));
        envelope.setOpaque(false);

        return envelope;
    }
}