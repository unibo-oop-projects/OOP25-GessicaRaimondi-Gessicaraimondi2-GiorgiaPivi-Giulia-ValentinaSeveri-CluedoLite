package it.unibo.cluedolite.view.tableview;

import javax.imageio.ImageIO;
import javax.swing.*;

import it.unibo.cluedolite.model.suspectnotes.api.State;
import it.unibo.cluedolite.view.AppColorFont;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.*;
import java.util.List;

/*
 * Custom JPanel representing a single card in the suspect notes table.
 * Its appearance changes based on the card state.
 */

public class CardPanel extends JPanel {
    private String name;
    private State state;
    private BufferedImage image;

    // The constructor creates a card panel with the given name and state and a preferred size.
    public CardPanel(String name, State state){
        this.name = name;
        this.state = state;
        setPreferredSize(new Dimension(77, 100));
        loadImage(name);
        setBackground(Color.WHITE);
    }

    /*
    *  Loads the card image by trying common extensions (png, jpg, jpeg).
    *  The file name is normalized (lowercase, no spaces or dots) to match resource file names.
    *  If no image is found, the field is set to null.
    */
    private void loadImage(String name) {
        String baseName = name.toLowerCase()
            .replace( " ", "")
            .replace(".", "");
        for (String ext : List.of("png", "jpg", "jpeg")) {
            String path = "/images/" + baseName + "." + ext;
            try {
                var stream = getClass().getResourceAsStream(path);
                if (stream != null) {
                    image = ImageIO.read(stream);
                    return;
                }
            } catch (IOException e) {
                // Failed to read this format, try the next one
              }
        }
        image = null;
    }

    /*
    *  Draws the card appearance based on its state: normal if POSSIBLE, 
    *  darkened with a red X if EXCLUDED.
    */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 20, getWidth(), getHeight() - 20, this); // Draws the card image scaled to fill the area below the title bar
        String[] words = name.split(" ");
        int textHeight = words.length * 12 + 4;
        g.setColor(Color.WHITE); // Draws a white background strip at the top sized to fit the card name
        g.fillRect(0, 0, getWidth(), textHeight);
        
        // If excluded, overlays a dark tint, a red X and a red border on the entire card
        if (state == State.EXCLUDED) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(0, 0, 0, 120));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(AppColorFont.ERROR);
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(0, 0, getWidth(), getHeight());
            g2.drawLine(getWidth(), 0, 0, getHeight());
            g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            }

        // Draws each word of the card name on a separate line, white if excluded, black otherwise
        int y = 13;
        for (String word : words) {
            g.setFont(AppColorFont.FONT_DROPDOWN);
            g.setColor(state == State.EXCLUDED ? Color.WHITE : Color.BLACK);
            g.drawString(word, 4, y);
            y += 12;
        }
    }

    // Marks the card as excluded and repaints the component to reflect the new state
    public void excludeCard() {
        this.state = State.EXCLUDED;
        repaint();
    }
    
}
