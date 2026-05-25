package it.unibo.CluedoLite.view.gamebutton;

import javax.swing.*;
import java.awt.*;
import it.unibo.CluedoLite.view.AppColorFont;

/**
 * Scrollable panel displaying the history of game actions.
 */
public class HistoryPanel extends JPanel {

    private final JTextArea textArea;

    /** Builds the history panel with a scrollable text area. */
    public HistoryPanel(int width) {
        setLayout(new BorderLayout());
        setBackground(AppColorFont.BACKGROUND_MEDIUM);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(AppColorFont.BACKGROUND_DARK);
        textArea.setForeground(AppColorFont.ACCENT_SECONDARY);
        textArea.setFont(AppColorFont.FONT_SMALL);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        final JScrollPane scroll = new JScrollPane(textArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(width, 300));
        scroll.setBorder(BorderFactory.createLineBorder(AppColorFont.BORDER, 1));
        add(scroll, BorderLayout.CENTER);
    }

    /**
     * Adds a new entry to the action history.
     *
     * @param message the message to append
     */
    public void addEntry(String message) {
        textArea.append("• " + message + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}
