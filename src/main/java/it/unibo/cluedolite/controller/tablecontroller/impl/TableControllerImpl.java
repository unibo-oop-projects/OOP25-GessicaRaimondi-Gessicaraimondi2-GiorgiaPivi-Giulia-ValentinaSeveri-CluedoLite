package it.unibo.cluedolite.controller.tablecontroller.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.cluedolite.controller.tablecontroller.api.TableController;
import it.unibo.cluedolite.model.accuseandsuspect.api.InterfaceSuspicion;
import it.unibo.cluedolite.model.creationcards.impl.AbstractCard;
import it.unibo.cluedolite.model.suspectnotes.api.Table;
import it.unibo.cluedolite.model.suspectnotes.impl.TableImpl;
import it.unibo.cluedolite.model.turnmanager.api.TurnManager;
import it.unibo.cluedolite.view.tableview.TablePanel;

/**
 * Implementation of {@link TableController}.
 * Manages the suspect notes table for each player,
 * coordinating the model and the view during the suspicion phase.
 */
public final class TableControllerImpl implements TableController {

    private final TurnManager turnManager;
    private final Map<String, TableImpl> playerTables = new HashMap<>();
    private final Map<String, TablePanel> playerPanels = new HashMap<>();
    private Table table;

    /**
     * Creates a new {@link TableControllerImpl} for the given turn manager,
     * table and panel, registering them for the current player.
     *
     * @param turnManager the {@link TurnManager} managing the current turn
     * @param table       the {@link Table} representing the suspect notes
     * @param tablePanel  the {@link TablePanel} displaying the suspect notes
     */
    public TableControllerImpl(final TurnManager turnManager, final Table table, final TablePanel tablePanel) {
        this.turnManager = turnManager;
        this.table = table;
        playerTables.put(turnManager.getCurrentPlayer().getName(), (TableImpl) table);
        playerPanels.put(turnManager.getCurrentPlayer().getName(), tablePanel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleSuspicion(final InterfaceSuspicion suspicion, final Optional<AbstractCard> shownCard) {
        final TablePanel currentPanel = playerPanels.get(turnManager.getCurrentPlayer().getName());
        shownCard.ifPresent(c -> {
            table.updateTable(c);
            currentPanel.refresh(c);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TablePanel refreshForPlayer() {
        final String name = turnManager.getCurrentPlayer().getName();
        table = playerTables.computeIfAbsent(name,
            k -> new TableImpl(turnManager.getCurrentPlayer().getHand()));
        final TablePanel panel = playerPanels.computeIfAbsent(name,
            k -> new TablePanel(table));
        panel.resetSections();
        return panel;
    }
}
