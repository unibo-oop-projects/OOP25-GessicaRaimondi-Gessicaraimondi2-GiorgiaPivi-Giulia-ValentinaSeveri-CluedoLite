package it.unibo.cluedolite.view.tableview;

import it.unibo.cluedolite.model.creationcards.impl.CardType;
import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.model.gamesetup.impl.Deck;
import it.unibo.cluedolite.model.suspectnotes.api.Table;
import it.unibo.cluedolite.model.suspectnotes.impl.BoxImpl;
import it.unibo.cluedolite.view.AppColorFont;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import java.awt.Component;

/*
 * Represents the suspect notes table, organized into three sections:
 * characters, weapons, and rooms. Each section contains a CardPanel for every card,
 * whose appearance reflects its current state.
 */

public class TablePanel extends JPanel {
    private final Map<String, CardPanel> cardMap = new HashMap<>();
    private final NotesPanel notesPanel;
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    private final int panelWidth = (int)(screen.width * 0.25);
    

    /*
    *  Builds the table panel by creating three sections (characters, weapons, rooms)
    *  and populating each with the corresponding cards from the table.
    */
    public TablePanel(Table table){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(AppColorFont.BACKGROUND_DARK);
        setPreferredSize(new Dimension(500, 600)); // Fixed size to prevent the panel from resizing when sections are expanded
        setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
        setMinimumSize(new Dimension(500, 600));

        int panelHeight = screen.height;

        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setMaximumSize(new Dimension(panelWidth, Integer.MAX_VALUE));
        setMinimumSize(new Dimension(panelWidth, panelHeight));

        JPanel characters = createCardsPanel(0.12);
        JPanel rooms = createCardsPanel(0.25);
        JPanel weapons = createCardsPanel(0.12);
        fillTable(table.searchType(Deck.getCardsByType(CardType.CHARACTER).get(0)), characters);
        fillTable(table.searchType(Deck.getCardsByType(CardType.WEAPON).get(0)), weapons);
        fillTable(table.searchType(Deck.getCardsByType(CardType.ROOM).get(0)), rooms);

        // Adds the three collapsible sections to the panel
        add(createContainer("Characters", characters));
        add(createContainer("Rooms", rooms));
        add(createContainer("Weapons", weapons));
        notesPanel = new NotesPanel();
        add(notesPanel);    
    }

    // Creates an empty panel to hold card panels, hidden by default until the section is expanded.
    private JPanel createCardsPanel(double heightPercent){
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int)(screen.height * heightPercent);
        
        
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(AppColorFont.BACKGROUND_MEDIUM);
        panel.setBorder(BorderFactory.createLineBorder(AppColorFont.BORDER, 1));
        panel.setVisible(false);
        panel.setPreferredSize(new Dimension(panelWidth, height));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
        panel.setMinimumSize(new Dimension(panelWidth, height));

        return panel;
    }

    // For each item in the list creates a CardPanel, fills it with information and adds it to the panel.
    private void fillTable(List<BoxImpl> list, JPanel panel){
        list.stream().forEach(c -> {
            CardPanel card = new CardPanel(c.getCard().getName(), c.getState());
            cardMap.put(c.getCard().getName(), card);
            panel.add(card);
        });
    }

    // Creates a collapsible container with a clickable title label that shows or hides the cards panel.
    private JPanel createContainer(String title, JPanel cardsPanel){
        JLabel titleLabel = new JLabel("▶ " + title);
        JPanel container = new JPanel();

        titleLabel.setFont(AppColorFont.FONT_BODY);
        titleLabel.setForeground(AppColorFont.ACCENT_SECONDARY);
        titleLabel.setBackground(AppColorFont.BACKGROUND_LIGHT); 
        titleLabel.setOpaque(true);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 0));
        titleLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        titleLabel.addMouseListener(new MouseAdapter() {    // Toggles the cards panel visibility and updates the arrow on title click
            @Override
            public void mouseClicked(MouseEvent e) {
                cardsPanel.setVisible(!cardsPanel.isVisible());
                titleLabel.setText((cardsPanel.isVisible() ? "▼ " : "▶ ") + title);
                container.revalidate();
                container.repaint();
            }
        });

        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(AppColorFont.BACKGROUND_LIGHT); 
        container.add(titleLabel);
        container.add(cardsPanel);

        return container;
    }

    // Finds the CardPanel corresponding to the given card and marks it as excluded
    public void refresh(Card card) {
        CardPanel panel = cardMap.get(card.getName());
        if (panel != null) {
            panel.excludeCard();
        }
    }

    public void resetSections() {
        for (Component c : getComponents()) {
            if (c instanceof JPanel section) {
                for (Component inner : ((JPanel) c).getComponents()) {
                    if (inner instanceof JPanel cards) {
                        cards.setVisible(false);
                    }
                }
            }
        }
        revalidate();
        repaint();
        notesPanel.reset();
    }

}