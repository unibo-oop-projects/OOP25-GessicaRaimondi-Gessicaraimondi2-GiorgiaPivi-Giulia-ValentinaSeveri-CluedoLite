package it.unibo.cluedolite.view.endgameview;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;

import it.unibo.cluedolite.view.AppColorFont;

/**
 * View displayed when the player loses the game in CluedoLite.
 * Shows a fullscreen defeat message that closes automatically after 3 seconds.
 */
public final class DefeatView extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int BORDER_LINE = 6;
    private static final int BORDER_VERTICAL = 30;
    private static final int BORDER_HORIZONTAL = 40;
    private static final int BORDER_SUBTITLE_TOP = 20;
    private static final float TITLE_FONT_SIZE = 72f;
    private static final int AUTO_CLOSE_MS = 3000;

    /**
     * Constructs and displays the defeat screen.
     * The window closes automatically after 3 seconds.
     */
    public DefeatView() {
        setTitle("Defeat");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setUndecorated(true);

        final JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(AppColorFont.BACKGROUND_DARK);
        rootPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_LINE));

        final JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(AppColorFont.BACKGROUND_DARK);

        final JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.setBackground(AppColorFont.BACKGROUND_DARK);
        innerPanel.setBorder(BorderFactory.createEmptyBorder(
            BORDER_VERTICAL, 
            BORDER_HORIZONTAL, 
            BORDER_VERTICAL, 
            BORDER_HORIZONTAL));

        final JLabel titleLabel = new JLabel("LOSER :(");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setFont(AppColorFont.FONT_TITLE.deriveFont(TITLE_FONT_SIZE));
        titleLabel.setForeground(Color.BLACK);

        final JLabel subtitleLabel = new JLabel("Try Again");
        subtitleLabel.setAlignmentX(CENTER_ALIGNMENT);
        subtitleLabel.setFont(AppColorFont.FONT_LABEL);
        subtitleLabel.setForeground(AppColorFont.TEXT_SECONDARY);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(BORDER_SUBTITLE_TOP, 0, 0, 0));

        innerPanel.add(titleLabel);
        innerPanel.add(subtitleLabel);
        outerPanel.add(innerPanel);
        rootPanel.add(outerPanel, BorderLayout.CENTER);

        add(rootPanel);
        setExtendedState(MAXIMIZED_BOTH);
        javax.swing.SwingUtilities.invokeLater(() -> {
            setVisible(true);

            final Timer timer = new Timer(AUTO_CLOSE_MS, e -> dispose());
            timer.setRepeats(false);
            timer.start();
        });
    }
}
