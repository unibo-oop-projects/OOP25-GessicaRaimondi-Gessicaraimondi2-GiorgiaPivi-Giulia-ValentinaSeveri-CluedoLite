package it.unibo.cluedolite.view.gamebutton;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import it.unibo.cluedolite.view.AppColorFont;

import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * Scrollable panel displaying the history of game actions.
 */
public class HistoryPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int PANEL_HEIGHT = 300;
    private final JTextArea textArea;

    /** 
     * Builds the history panel with a scrollable text area. 
     * 
     * @param width the width of the panel
     */
    @SuppressWarnings("PMD.ConstructorCallsOverridableMethod")
    public HistoryPanel(final int width) {
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
        scroll.setPreferredSize(new Dimension(width, PANEL_HEIGHT));
        scroll.setBorder(BorderFactory.createLineBorder(AppColorFont.BORDER, 1));
        add(scroll, BorderLayout.CENTER);
    }

    /**
     * Adds a new entry to the action history.
     *
     * @param message the message to append
     */
    public void addEntry(final String message) {
        textArea.append("• " + message + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}
