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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

    private static final long serialVersionUID = 1L;
    private static final double SCREEN_WIDTH_RATIO = 0.25;
    private static final double CHARACTERS_HEIGHT_RATIO = 0.12;
    private static final double WEAPONS_HEIGHT_RATIO = 0.12;
    private static final double ROOMS_HEIGHT_RATIO = 0.25;
    private static final int TITLE_HEIGHT = 30;
    private static final int TITLE_BORDER = 4;
    private static final int TITLE_BORDER_LEFT = 8;
    private static final int BORDER_THICKNESS = 1;
    private static final String ARROW_COLLAPSED = "▶ ";
    private static final String ARROW_EXPANDED = "▼ ";

    private final Map<String, CardPanel> cardMap = new HashMap<>();
    private final NotesPanel notesPanel;
    private final int panelWidth;
    
    /**
     * Creates a new {@link TablePanel}, populating it with cards grouped
     * by type and adding a notes panel at the bottom.
     *
     * @param table the {@link Table} containing the current state of all cards
     */
    public TablePanel(final Table table) {
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        panelWidth = (int) (screen.width * SCREEN_WIDTH_RATIO);
        final int panelHeight = screen.height;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(AppColorFont.BACKGROUND_DARK);
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setMaximumSize(new Dimension(panelWidth, Integer.MAX_VALUE));
        setMinimumSize(new Dimension(panelWidth, panelHeight));

        final JPanel characters = createCardsPanel(CHARACTERS_HEIGHT_RATIO);
        final JPanel rooms = createCardsPanel(ROOMS_HEIGHT_RATIO);
        final JPanel weapons = createCardsPanel(WEAPONS_HEIGHT_RATIO);
        fillTable(table.searchType(Deck.getCardsByType(CardType.CHARACTER).get(0)), characters);
        fillTable(table.searchType(Deck.getCardsByType(CardType.WEAPON).get(0)), weapons);
        fillTable(table.searchType(Deck.getCardsByType(CardType.ROOM).get(0)), rooms);

        add(createContainer("Characters", characters));
        add(createContainer("Rooms", rooms));
        add(createContainer("Weapons", weapons));
        notesPanel = new NotesPanel();
        add(notesPanel);
    }

    /**
     * Creates an empty panel to hold card panels, hidden by default.
     *
     * @param heightPercent the fraction of the screen height to use
     * @return a configured {@link JPanel} for cards
     */
    private JPanel createCardsPanel(final double heightPercent) {
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int height = (int) (screen.height * heightPercent);
        final JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(AppColorFont.BACKGROUND_MEDIUM);
        panel.setBorder(BorderFactory.createLineBorder(AppColorFont.BORDER, BORDER_THICKNESS));
        panel.setVisible(false);
        panel.setPreferredSize(new Dimension(panelWidth, height));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
        panel.setMinimumSize(new Dimension(panelWidth, height));
        return panel;
    }

    /**
     * Populates the given panel with a {@link CardPanel} for each box in the list.
     *
     * @param list  the list of {@link Box} entries to display
     * @param panel the {@link JPanel} to populate
     */
    private void fillTable(final List<Box> list, final JPanel panel) {
        list.forEach(c -> {
            final CardPanel card = new CardPanel(c.getCard().getName(), c.getState());
            cardMap.put(c.getCard().getName(), card);
            panel.add(card);
        });
    }

    /**
     * Creates a collapsible container with a clickable title label
     * that shows or hides the given cards panel.
     *
     * @param title      the title of the section
     * @param cardsPanel the {@link JPanel} to show or hide
     * @return a configured container {@link JPanel}
     */
    private JPanel createContainer(final String title, final JPanel cardsPanel) {
        final JLabel titleLabel = new JLabel(ARROW_COLLAPSED + title);
        final JPanel container = new JPanel();

        titleLabel.setFont(AppColorFont.FONT_BODY);
        titleLabel.setForeground(AppColorFont.ACCENT_SECONDARY);
        titleLabel.setBackground(AppColorFont.BACKGROUND_LIGHT);
        titleLabel.setOpaque(true);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(TITLE_BORDER, TITLE_BORDER_LEFT, TITLE_BORDER, 0));
        titleLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, TITLE_HEIGHT));
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                cardsPanel.setVisible(!cardsPanel.isVisible());
                titleLabel.setText((cardsPanel.isVisible() ? ARROW_EXPANDED : ARROW_COLLAPSED) + title);
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

    /**
     * Marks the {@link CardPanel} corresponding to the given card as excluded.
     *
     * @param card the {@link Card} to mark as excluded
     */
    public void refresh(final Card card) {
        final CardPanel panel = cardMap.get(card.getName());
        if (panel != null) {
            panel.excludeCard();
        }
    }

    /**
     * Collapses all sections and resets the notes panel.
     */
    public void resetSections() {
        for (final Component c : getComponents()) {
            if (c instanceof JPanel) {
                for (final Component inner : ((JPanel) c).getComponents()) {
                    if (inner instanceof JPanel) {
                        inner.setVisible(false);
                    }
                }
            }
        }
        revalidate();
        repaint();
        notesPanel.reset();
    }

}