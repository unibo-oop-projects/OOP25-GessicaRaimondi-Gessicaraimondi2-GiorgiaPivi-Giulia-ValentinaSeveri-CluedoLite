package it.unibo.cluedolite.view.tableview;

import java.awt.Dimension;
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

import java.awt.Toolkit;

/*
* Represents a scrollable notepad panel where the player can write personal notes.
*/
public class NotesPanel extends JPanel {
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JLabel titleLabel;

    // Builds the collapsible notepad with a title label and a scrollable text area.
    public NotesPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(AppColorFont.BACKGROUND_LIGHT);

        titleLabel = new JLabel("▶ Notes");
        titleLabel.setFont(AppColorFont.FONT_BODY);
        titleLabel.setForeground(AppColorFont.ACCENT_SECONDARY);
        titleLabel.setBackground(AppColorFont.BACKGROUND_LIGHT);
        titleLabel.setOpaque(true);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 0));
        titleLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                scrollPane.setVisible(!scrollPane.isVisible());
                titleLabel.setText((scrollPane.isVisible() ? "▼ " : "▶ ") + "Notes");
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
        textArea.setText("Click to write...");
        textArea.setFocusable(false);
        textArea.addMouseListener(new MouseAdapter() { // Enables focus and clears the placeholder text on first click
            @Override
            public void mouseClicked(MouseEvent e) {
                textArea.setFocusable(true);
                textArea.requestFocusInWindow();
                if (textArea.getText().equals("Click to write...")) { // removes placeholder if not yet edited
                    textArea.setText("");
                }
            }
        });
        textArea.addFocusListener(new FocusAdapter() { // Restores the placeholder text and disables focus when the text area is left empty
            @Override
            public void focusLost(FocusEvent e) {
                if (textArea.getText().isEmpty()) { // only if the user didn't write anything
                    textArea.setText("Click to write...");
                    textArea.setForeground(AppColorFont.ACCENT_SECONDARY);
                    textArea.setFocusable(false);
                }
            }
        });

        // Wraps the text area in a scroll pane
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(480, 150));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        scrollPane.setBorder(BorderFactory.createLineBorder(AppColorFont.ACCENT_SECONDARY, 1));
        scrollPane.setViewportBorder(null);
        scrollPane.setVisible(false);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        scrollPane.setPreferredSize(new Dimension((int)(screen.width * 0.25), (int)(screen.height * 0.15)));

        add(titleLabel);
        add(scrollPane);
    }

    public void reset() {
        scrollPane.setVisible(false);
        titleLabel.setText("▶ Notes");
    }
}