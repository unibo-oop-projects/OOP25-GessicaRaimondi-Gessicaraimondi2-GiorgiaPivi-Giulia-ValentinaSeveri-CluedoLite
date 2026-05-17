package it.unibo.CluedoLite.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.List;

import it.unibo.CluedoLite.model.creationcards.impl.Card;
import it.unibo.CluedoLite.model.gameflow.api.Game;
import it.unibo.CluedoLite.view.gamebutton.ButtonGamePanel;
import it.unibo.CluedoLite.view.gameboard.impl.BoardImpl;
import it.unibo.CluedoLite.view.tableview.TablePanel;
import it.unibo.CluedoLite.view.secretsolutionview.SecretSolutionStartView;
import it.unibo.CluedoLite.view.endgameview.VictoryView;
import it.unibo.CluedoLite.view.endgameview.DefeatView;
import it.unibo.CluedoLite.controller.gameboard.api.GameBoardController;
import it.unibo.CluedoLite.controller.accuseandsuspectcontroller.api.InterfaceSuspicionController;
import it.unibo.CluedoLite.controller.accuseandsuspectcontroller.api.InterfaceAccusation;
import it.unibo.CluedoLite.controller.buttonflowcontroller.api.ResetButtonController;
import it.unibo.CluedoLite.controller.endturnbutton.api.EndTurnController;
import it.unibo.CluedoLite.controller.buttonflowcontroller.api.QuitButtonController;

/**
 * Main game view.
 * Layout: buttons panel (west) | board (center) | suspect table (east).
 * On startup, shows the {@link SecretSolutionStartView} for 5 seconds.
 */
public class GameView extends JPanel {

    private final ResetButtonController resetController;
    private final QuitButtonController quitController;
    private final ButtonGamePanel buttonPanel;
    private final List<Card> solution;

    /**
     * @param game                the game model
     * @param boardController     controller for the board
     * @param suspicionController controller for the suspicion action
     * @param accuseController    controller for the accusation action
     * @param resetController     controller for the reset action
     * @param quitController      controller for the quit action
     * @param tablePanel          the suspect notes panel
     * @param solution            the three secret solution cards to display on startup
     */
    public GameView(final Game game,
                    final GameBoardController boardController,
                    final InterfaceSuspicionController suspicionController,
                    final InterfaceAccusation accuseController,
                    final ResetButtonController resetController,
                    final QuitButtonController quitController,
                    final EndTurnController endTurnController,
                    final TablePanel tablePanel,
                    final List<Card> solution) {

        this.resetController = resetController;
        this.quitController = quitController;
        this.solution = solution;
        System.out.println("[DEBUG] Soluzione segreta: " + solution);

        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLayout(new BorderLayout());
        setPreferredSize(screen);

        // --- pannello bottoni (sinistra) ---
        buttonPanel = new ButtonGamePanel(
                suspicionController,
                accuseController,
                resetController,
                quitController,
                endTurnController);
        add(buttonPanel, BorderLayout.WEST);

        // --- tabellone (centro) ---
        final BoardImpl board = new BoardImpl(game.getPlayers(), boardController);
        boardController.setView(board);
        final int boardSize = (int) (screen.height * 0.95);
        board.setPreferredSize(new Dimension(boardSize, boardSize));
        board.setMinimumSize(new Dimension(boardSize, boardSize));
        add(board, BorderLayout.CENTER);

        // --- tabella sospetti (destra) ---
        final JScrollPane scrollTable = new JScrollPane(tablePanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollTable.setPreferredSize(new Dimension(520, screen.height));
        add(scrollTable, BorderLayout.EAST);

        // --- schermata soluzione segreta (si apre appena il pannello è visibile) ---
        SwingUtilities.invokeLater(() -> new SecretSolutionStartView(solution));
    }

    /** Disables suspicion + accusation, enables end turn. */
    public void disableActionButtons() {
        buttonPanel.disableActionButtons();
    }
 
    /** Re-enables suspicion + accusation, disables end turn. */
    public void resetForNewTurn() {
        buttonPanel.resetForNewTurn();
    }

    /**
     * Opens the victory window. Call this when the accusation is correct.
     */
    public void showVictory() {
        SwingUtilities.invokeLater(() -> new VictoryView(resetController, quitController));
        final Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
    }

    /**
     * Opens the defeat window. Call this when the accusation is wrong.
     */
    public void showDefeat() {
        SwingUtilities.invokeLater(() -> new DefeatView());
    }

}