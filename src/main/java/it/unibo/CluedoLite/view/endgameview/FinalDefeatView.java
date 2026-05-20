package it.unibo.CluedoLite.view.endgameview;

import javax.swing.*;
import java.awt.*;
import it.unibo.CluedoLite.view.AppColorFont;
import it.unibo.CluedoLite.view.buttonflowview.QuitButtonView;
import it.unibo.CluedoLite.view.buttonflowview.ResetButtonView;
import it.unibo.CluedoLite.controller.buttonflowcontroller.api.QuitButtonController;
import it.unibo.CluedoLite.controller.buttonflowcontroller.api.ResetButtonController;

public class FinalDefeatView extends JFrame {

    public FinalDefeatView(final ResetButtonController resetController,
                           final QuitButtonController quitController) {
        setTitle("Defeat");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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

        final JLabel subtitleLabel = new JLabel("Nobody won...");
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setFont(AppColorFont.FONT_LABEL);
        subtitleLabel.setForeground(AppColorFont.TEXT_SECONDARY);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        innerPanel.add(titleLabel);
        innerPanel.add(subtitleLabel);
        outerPanel.add(innerPanel);

        final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonsPanel.setBackground(AppColorFont.BACKGROUND_DARK);

        final ResetButtonView resetBtn = new ResetButtonView(resetController);
        resetBtn.addActionListener(e -> {
            if (resetController.onResetClicked()) {
                dispose();
            }
        });
        buttonsPanel.add(resetBtn);
        buttonsPanel.add(new QuitButtonView(quitController));

        rootPanel.add(outerPanel, BorderLayout.CENTER);
        rootPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(rootPanel);
        setVisible(true);
    }
}