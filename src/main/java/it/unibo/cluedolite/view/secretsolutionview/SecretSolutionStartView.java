package it.unibo.cluedolite.view.secretsolutionview;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.util.List;

import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.view.AppColorFont;

/**
 * Swing view displayed when the secret solution is created.
 * Shows three face-down cards (CHARACTER, WEAPON, ROOM) side by side,
 * with a decorative envelope card below them, all on the application's dark background.
 * The window closes automatically after {@value AUTO_CLOSE_MS} milliseconds.
 */
public class SecretSolutionStartView extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final int CARD_WIDTH = 200;
    private static final int CARD_HEIGHT = 300;
    private static final int ENVELOPE_WIDTH = 300;
    private static final int ENVELOPE_HEIGHT = 200;
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
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setLocationRelativeTo(null);

        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(AppColorFont.BACKGROUND_DARK);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(AppColorFont.BACKGROUND_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel cardsRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        cardsRow.setBackground(AppColorFont.BACKGROUND_DARK);

        String[] labels = {"CHARACTER", "WEAPON", "ROOM"};
        for (String label : labels) {
            cardsRow.add(createCoveredCard(label));
        }

        JPanel envelopeCard = createEnvelopeCard();

        JPanel cardsRowWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cardsRowWrapper.setBackground(AppColorFont.BACKGROUND_DARK);
        cardsRowWrapper.add(cardsRow);

        JPanel envelopeWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        envelopeWrapper.setBackground(AppColorFont.BACKGROUND_DARK);
        envelopeWrapper.add(envelopeCard);

        mainPanel.add(cardsRowWrapper);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(envelopeWrapper);

        outerPanel.add(mainPanel);
        add(outerPanel);
        setVisible(true);

        Timer timer = new Timer(AUTO_CLOSE_MS, e -> dispose());
        timer.setRepeats(false);
        timer.start();
    }

    /**
    * Builds a single face-down card panel with a question mark and a category label below it.
    *
    * @param label the category label to display beneath the card (e.g. "CHARACTER", "WEAPON", "ROOM")
    * @return a {@link JPanel} representing the face-down card
    */
    private JPanel createCoveredCard(String label) {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(AppColorFont.BACKGROUND_DARK);

        JPanel card = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(AppColorFont.BACKGROUND_LIGHT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                g2.setColor(AppColorFont.TEXT_PRIMARY);
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(8, 8, getWidth() - 16, getHeight() - 16, 15, 15);

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
    *
    * @return a {@link JPanel} representing the decorative envelope
    */
    private JPanel createEnvelopeCard() {

        JPanel envelope = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(AppColorFont.BACKGROUND_DARK);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                g2.setColor(AppColorFont.TEXT_PRIMARY);
                g2.setStroke(new BasicStroke(4));
                g2.drawRoundRect(6, 6, getWidth() - 12, getHeight() - 12, 15, 15);

                int[] xPoints = {0, getWidth() / 2, getWidth()};
                int[] yPoints = {0, getHeight() / 2, 0};
                g2.setColor(AppColorFont.BACKGROUND_MEDIUM);
                g2.fillPolygon(xPoints, yPoints, 3);

                g2.setColor(AppColorFont.TEXT_PRIMARY);
                g2.setStroke(new BasicStroke(2));
                g2.drawPolygon(xPoints, yPoints, 3);

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