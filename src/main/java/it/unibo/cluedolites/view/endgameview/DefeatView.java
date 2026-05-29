package it.unibo.cluedolite.view.endgameview;

import javax.swing.*;

import it.unibo.cluedolite.view.AppColorFont;

import java.awt.*;

public class DefeatView extends JFrame {

    public DefeatView() {
        setTitle("Defeat");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        final JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(AppColorFont.BACKGROUND_DARK);
        rootPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 6));

        final JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(AppColorFont.BACKGROUND_DARK);

        final JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.setBackground(AppColorFont.BACKGROUND_DARK);
        innerPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        final JLabel titleLabel = new JLabel("LOSER :(");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(AppColorFont.FONT_TITLE.deriveFont(72f));
        titleLabel.setForeground(Color.BLACK);

        final JLabel subtitleLabel = new JLabel("Try Again");
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setFont(AppColorFont.FONT_LABEL);
        subtitleLabel.setForeground(AppColorFont.TEXT_SECONDARY);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        innerPanel.add(titleLabel);
        innerPanel.add(subtitleLabel);
        outerPanel.add(innerPanel);
        rootPanel.add(outerPanel, BorderLayout.CENTER); // ← collegare outerPanel a rootPanel

        add(rootPanel);         // ← aggiungere rootPanel al frame
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);       // ← rendere visibile

        final Timer timer = new Timer(3000, e -> dispose());
        timer.setRepeats(false);
        timer.start();
    }
}