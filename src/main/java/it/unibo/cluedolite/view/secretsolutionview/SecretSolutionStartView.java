package it.unibo.cluedolite.view.secretsolutionview;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.util.List;

import it.unibo.cluedolite.model.creationcards.impl.AbstractCard;
import it.unibo.cluedolite.view.AppColorFont;

/**
 * Swing view displayed when the secret solution is created.
 * Shows three face-down cards (CHARACTER, WEAPON, ROOM) side by side,
 * with a decorative envelope card below them, all on the application's dark background.
 * The window closes automatically after {@value AUTO_CLOSE_MS} milliseconds.
 */
public final class SecretSolutionStartView extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final int CARD_WIDTH = 200;
    private static final int CARD_HEIGHT = 300;
    private static final int ENVELOPE_WIDTH = 300;
    private static final int ENVELOPE_HEIGHT = 200;
    private static final int AUTO_CLOSE_MS = 3_000;

    private static final int PANEL_PADDING = 20;
    private static final int CARDS_GAP_H = 30;
    private static final int CARD_ARC = 20;
    private static final int CARD_INNER_OFFSET = 8;
    private static final int CARD_INNER_SIZE_REDUCTION = 16;
    private static final int CARD_INNER_ARC = 15;
    private static final float CARD_STROKE = 3f;
    private static final int QUESTION_FONT_SIZE = 80;
    private static final int TYPE_LABEL_BORDER_TOP = 10;
    private static final int VERTICAL_STRUT = 20;

    private static final float ENVELOPE_STROKE_OUTER = 4f;
    private static final float ENVELOPE_STROKE_INNER = 2f;
    private static final int ENVELOPE_OFFSET = 6;
    private static final int ENVELOPE_SIZE_REDUCTION = 12;
    private static final int ENVELOPE_ARC = 15;
    private static final int ENVELOPE_QUESTION_FONT_SIZE = 60;
    private static final int ENVELOPE_QUESTION_Y_OFFSET = 20;

    private static final Font CARD_QUESTION_FONT = new Font("SansSerif", Font.BOLD, QUESTION_FONT_SIZE);
    private static final Font ENVELOPE_QUESTION_FONT = new Font("SansSerif", Font.BOLD, ENVELOPE_QUESTION_FONT_SIZE);
    private static final String[] CARD_LABELS = {"CHARACTER", "WEAPON", "ROOM"};

    /**
     * Creates and displays the secret solution start window.
     * Lays out three face-down cards horizontally and a decorative envelope
     * below them, then starts a timer to dispose the window after
     * {@value AUTO_CLOSE_MS} ms.
     *
     * @param solution the list of three secret cards (character, weapon, room)
     *                 whose categories are shown but whose identities remain hidden
     */
    public SecretSolutionStartView(final List<AbstractCard> solution) {
        setTitle("Secret Solution");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setExtendedState(MAXIMIZED_BOTH);
        setUndecorated(true);
        setLocationRelativeTo(null);

        final JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(AppColorFont.BACKGROUND_DARK);

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(AppColorFont.BACKGROUND_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(
            PANEL_PADDING, PANEL_PADDING, PANEL_PADDING, PANEL_PADDING));

        final JPanel cardsRow = new JPanel(new FlowLayout(FlowLayout.CENTER, CARDS_GAP_H, 0));
        cardsRow.setBackground(AppColorFont.BACKGROUND_DARK);

        for (final String label : CARD_LABELS) {
            cardsRow.add(createCoveredCard(label));
        }

        final JPanel envelopeCard = createEnvelopeCard();

        final JPanel cardsRowWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cardsRowWrapper.setBackground(AppColorFont.BACKGROUND_DARK);
        cardsRowWrapper.add(cardsRow);

        final JPanel envelopeWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        envelopeWrapper.setBackground(AppColorFont.BACKGROUND_DARK);
        envelopeWrapper.add(envelopeCard);

        mainPanel.add(cardsRowWrapper);
        mainPanel.add(Box.createVerticalStrut(VERTICAL_STRUT));
        mainPanel.add(envelopeWrapper);

        outerPanel.add(mainPanel);
        add(outerPanel);
        javax.swing.SwingUtilities.invokeLater(() -> {
            setVisible(true);

            final Timer timer = new Timer(AUTO_CLOSE_MS, e -> dispose());
            timer.setRepeats(false);
            timer.start();
        });
    }

    /**
     * Builds a single face-down card panel with a question mark and a category label below it.
     *
     * @param label the category label to display beneath the card (e.g. "CHARACTER", "WEAPON", "ROOM")
     * @return a {@link JPanel} representing the face-down card
     */
    private JPanel createCoveredCard(final String label) {
        final JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(AppColorFont.BACKGROUND_DARK);

        final JPanel card = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                final Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(AppColorFont.BACKGROUND_LIGHT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), CARD_ARC, CARD_ARC);

                g2.setColor(AppColorFont.TEXT_PRIMARY);
                g2.setStroke(new BasicStroke(CARD_STROKE));
                g2.drawRoundRect(
                    CARD_INNER_OFFSET, CARD_INNER_OFFSET,
                    getWidth() - CARD_INNER_SIZE_REDUCTION,
                    getHeight() - CARD_INNER_SIZE_REDUCTION,
                    CARD_INNER_ARC, CARD_INNER_ARC);

                g2.setFont(CARD_QUESTION_FONT);
                final FontMetrics fm = g2.getFontMetrics();
                final String q = "?";
                final int x = (getWidth() - fm.stringWidth(q)) / 2;
                final int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(q, x, y);
            }
        };
        card.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        card.setOpaque(false);

        final JLabel typeLabel = new JLabel(label);
        typeLabel.setAlignmentX(CENTER_ALIGNMENT);
        typeLabel.setFont(AppColorFont.FONT_BODY);
        typeLabel.setForeground(AppColorFont.TEXT_PRIMARY);
        typeLabel.setBorder(BorderFactory.createEmptyBorder(TYPE_LABEL_BORDER_TOP, 0, 0, 0));

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
        final JPanel envelope = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                final Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(AppColorFont.BACKGROUND_DARK);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), CARD_ARC, CARD_ARC);

                g2.setColor(AppColorFont.TEXT_PRIMARY);
                g2.setStroke(new BasicStroke(ENVELOPE_STROKE_OUTER));
                g2.drawRoundRect(
                    ENVELOPE_OFFSET, ENVELOPE_OFFSET,
                    getWidth() - ENVELOPE_SIZE_REDUCTION,
                    getHeight() - ENVELOPE_SIZE_REDUCTION,
                    ENVELOPE_ARC, ENVELOPE_ARC);

                final int[] xPoints = {0, getWidth() / 2, getWidth()};
                final int[] yPoints = {0, getHeight() / 2, 0};
                g2.setColor(AppColorFont.BACKGROUND_MEDIUM);
                g2.fillPolygon(xPoints, yPoints, 3);

                g2.setColor(AppColorFont.TEXT_PRIMARY);
                g2.setStroke(new BasicStroke(ENVELOPE_STROKE_INNER));
                g2.drawPolygon(xPoints, yPoints, 3);

                g2.setFont(ENVELOPE_QUESTION_FONT);
                final FontMetrics fm = g2.getFontMetrics();
                final String q = "?";
                final int x = (getWidth() - fm.stringWidth(q)) / 2;
                final int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2 + ENVELOPE_QUESTION_Y_OFFSET;
                g2.drawString(q, x, y);
            }
        };
        envelope.setPreferredSize(new Dimension(ENVELOPE_WIDTH, ENVELOPE_HEIGHT));
        envelope.setOpaque(false);

        return envelope;
    }
}
