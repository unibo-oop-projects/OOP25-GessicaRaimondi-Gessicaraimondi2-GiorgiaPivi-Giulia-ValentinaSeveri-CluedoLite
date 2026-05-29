package it.unibo.cluedolite.controller.tablecontroller.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.cluedolite.controller.tablecontroller.api.TableController;
import it.unibo.cluedolite.model.accuseandsuspect.impl.Suspicion;
import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.model.suspectnotes.api.Table;
import it.unibo.cluedolite.model.suspectnotes.impl.TableImpl;
import it.unibo.cluedolite.model.turnmanager.api.TurnManager;
import it.unibo.cluedolite.view.tableview.TablePanel;

/*
 * Controller responsible for managing the suspicion phase.
 * It coordinates the model and the view when a suspicion is made.
 */
    public class TableControllerImpl implements TableController {
    private final TurnManager turnManager;
    private final Map<String, TableImpl> playerTables = new HashMap<>();
    private final Map<String, TablePanel> playerPanels = new HashMap<>();
    private Table table;
    private TablePanel tablePanel;

    public TableControllerImpl(TurnManager turnManager, Table table, TablePanel tablePanel) {
        this.turnManager = turnManager;
        this.table = table;
        this.tablePanel = tablePanel;
        playerTables.put(turnManager.getCurrentPlayer().getName(), (TableImpl) table);
        playerPanels.put(turnManager.getCurrentPlayer().getName(), tablePanel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleSuspicion(Suspicion suspicion, Optional<Card> shownCard) {
        shownCard.ifPresent(c -> {
            table.updateTable(c);
            tablePanel.refresh(c);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TablePanel refreshForPlayer() {
        String name = turnManager.getCurrentPlayer().getName();
        table = playerTables.computeIfAbsent(name,
            k -> new TableImpl(turnManager.getCurrentPlayer().getHand()));
        tablePanel = playerPanels.computeIfAbsent(name,
            k -> new TablePanel(table));
        tablePanel.resetSections();
        return tablePanel;
    }
}