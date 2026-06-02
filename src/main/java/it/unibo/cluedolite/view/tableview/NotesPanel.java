package it.unibo.cluedolite.view.tableview;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import it.unibo.cluedolite.view.AppColorFont;

/**
 * Scrollable notepad panel where the player can write personal notes.
 * The panel can be collapsed and expanded by clicking the title label.
 */
public class NotesPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int TITLE_HEIGHT = 30;
    private static final int NOTES_HEIGHT = 150;
    private static final double SCREEN_WIDTH_RATIO = 0.25;
    private static final double SCREEN_HEIGHT_RATIO = 0.15;
    private static final int TITLE_BORDER = 4;
    private static final int TITLE_BORDER_LEFT = 8;
    private static final int BORDER_THICKNESS = 1;
    private static final String PLACEHOLDER = "Click to write...";
    private static final String TITLE_COLLAPSED = "▶ Notes";
    private static final String TITLE_EXPANDED = "▼ Notes";

    private final JTextArea textArea;
    private final JScrollPane scrollPane;
    private final JLabel titleLabel;

    /**
     * Creates a new {@link NotesPanel} with a collapsible title
     * and a scrollable text area for personal notes.
     */
    public NotesPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(AppColorFont.BACKGROUND_LIGHT);

        titleLabel = new JLabel(TITLE_COLLAPSED);
        titleLabel.setFont(AppColorFont.FONT_BODY);
        titleLabel.setForeground(AppColorFont.ACCENT_SECONDARY);
        titleLabel.setBackground(AppColorFont.BACKGROUND_LIGHT);
        titleLabel.setOpaque(true);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(TITLE_BORDER, TITLE_BORDER_LEFT, TITLE_BORDER, 0));
        titleLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, TITLE_HEIGHT));
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                scrollPane.setVisible(!scrollPane.isVisible());
                titleLabel.setText(scrollPane.isVisible() ? TITLE_EXPANDED : TITLE_COLLAPSED);
                revalidate();
                repaint();
            }
        });

        textArea = new JTextArea();
        textArea.setFont(AppColorFont.FONT_SMALL);
        textArea.setBackground(AppColorFont.BACKGROUND_MEDIUM);
        textArea.setForeground(AppColorFont.ACCENT_SECONDARY);
        textArea.setCaretColor(AppColorFont.TEXT_PRIMARY);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText(PLACEHOLDER);
        textArea.setFocusable(false);
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                textArea.setFocusable(true);
                textArea.requestFocusInWindow();
                if (PLACEHOLDER.equals(textArea.getText())) {
                    textArea.setText("");
                }
            }
        });
        textArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent e) {
                if (textArea.getText().isEmpty()) {
                    textArea.setText(PLACEHOLDER);
                    textArea.setForeground(AppColorFont.ACCENT_SECONDARY);
                    textArea.setFocusable(false);
                }
            }
        });

        scrollPane = new JScrollPane(textArea);
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, NOTES_HEIGHT));
        scrollPane.setBorder(BorderFactory.createLineBorder(AppColorFont.ACCENT_SECONDARY, BORDER_THICKNESS));
        scrollPane.setViewportBorder(null);
        scrollPane.setVisible(false);
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        scrollPane.setPreferredSize(new Dimension(
            (int) (screen.width * SCREEN_WIDTH_RATIO),
            (int) (screen.height * SCREEN_HEIGHT_RATIO)
        ));

        add(titleLabel);
        add(scrollPane);
    }

    /**
     * Resets the notes panel to its initial collapsed state.
     */
    public void reset() {
        scrollPane.setVisible(false);
        titleLabel.setText(TITLE_COLLAPSED);
    }
}