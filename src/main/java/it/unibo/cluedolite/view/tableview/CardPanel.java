package it.unibo.cluedolite.view.tableview;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import it.unibo.cluedolite.model.suspectnotes.api.State;
import it.unibo.cluedolite.view.AppColorFont;

/**
 * Custom {@link JPanel} representing a single card in the suspect notes table.
 * Its appearance changes based on the card state.
 */
public class CardPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int PREFERRED_WIDTH = 77;
    private static final int PREFERRED_HEIGHT = 100;
    private static final int IMAGE_Y_OFFSET = 20;
    private static final int TEXT_LINE_HEIGHT = 12;
    private static final int TEXT_Y_START = 13;
    private static final int TEXT_X_START = 4;
    private static final int TEXT_PADDING = 4;
    private static final int OVERLAY_ALPHA = 120;
    private static final int STROKE_WIDTH = 3;

    private final String name;
    private State state;
    private transient BufferedImage image;

    /**
     * Creates a new {@link CardPanel} with the given name and state.
     *
     * @param name  the name of the card
     * @param state the initial {@link State} of the card
     */
    public CardPanel(final String name, final State state) {
        this.name = name;
        this.state = state;
        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        loadImage(name);
        setBackground(Color.WHITE);
    }

    /**
     * Loads the card image by trying common extensions (png, jpg, jpeg).
     * The file name is normalized to match resource file names.
     * If no image is found, the field is set to null.
     *
     * @param cardName the name of the card to load the image for
     */
    private void loadImage(final String cardName) {
        final String baseName = cardName.toLowerCase(Locale.ROOT)
            .replace(" ", "")
            .replace(".", "");
        for (final String ext : List.of("png", "jpg", "jpeg")) {
            final String path = "/images/" + baseName + "." + ext;
            try {
                final var stream = getClass().getResourceAsStream(path);
                if (stream != null) {
                    image = ImageIO.read(stream);
                    return;
                }
            } catch (final IOException e) {
                // stream could not be read, try next extension
            }
        }
        image = null;
    }

    /**
     * Paints the card component, rendering the card image and name.
     * If the card is excluded, overlays a dark tint, a red X and a red border.
     *
     * @param g the {@link Graphics} context used for painting
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, IMAGE_Y_OFFSET, getWidth(), getHeight() - IMAGE_Y_OFFSET, this);
        final String[] words = name.split(" ");
        final int textHeight = words.length * TEXT_LINE_HEIGHT + TEXT_PADDING;
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), textHeight);
        if (state == State.EXCLUDED) {
            final Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(0, 0, 0, OVERLAY_ALPHA));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(AppColorFont.ERROR);
            g2.setStroke(new BasicStroke(STROKE_WIDTH));
            g2.drawLine(0, 0, getWidth(), getHeight());
            g2.drawLine(getWidth(), 0, 0, getHeight());
            g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
        int y = TEXT_Y_START;
        for (final String word : words) {
            g.setFont(AppColorFont.FONT_DROPDOWN);
            g.setColor(state == State.EXCLUDED ? Color.WHITE : Color.BLACK);
            g.drawString(word, TEXT_X_START, y);
            y += TEXT_LINE_HEIGHT;
        }
    }

    /**
     * Marks this card as excluded and repaints the component.
     */
    public void excludeCard() {
        this.state = State.EXCLUDED;
        repaint();
    }

}