package it.unibo.CluedoLite.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import it.unibo.CluedoLite.model.creationcards.impl.Card;
import it.unibo.CluedoLite.model.gameflow.api.Game;
import it.unibo.CluedoLite.view.gamebutton.ButtonGamePanel;
import it.unibo.CluedoLite.view.gameboard.impl.BoardImpl;
import it.unibo.CluedoLite.view.tableview.TablePanel;
import it.unibo.CluedoLite.view.secretsolutionview.SecretSolutionStartView;
import it.unibo.CluedoLite.view.endgameview.VictoryView;
import it.unibo.CluedoLite.view.endgameview.DefeatView;
import it.unibo.CluedoLite.view.endgameview.FinalDefeatView;
import it.unibo.CluedoLite.controller.gameboard.api.GameBoardController;
import it.unibo.CluedoLite.controller.accuseandsuspectcontroller.api.InterfaceSuspicionController;
import it.unibo.CluedoLite.controller.accuseandsuspectcontroller.api.InterfaceAccusation;
import it.unibo.CluedoLite.controller.buttonflowcontroller.api.QuitButtonController;
import it.unibo.CluedoLite.controller.buttonflowcontroller.api.ResetButtonController;
import it.unibo.CluedoLite.controller.endturnbutton.api.EndTurnController;

/**
 * Main game view.
 * Layout: buttons panel (west) | board (center) | suspect table (east).
 * On startup, shows the {@link SecretSolutionStartView} for 5 seconds.
 */
public class GameView extends JPanel {

    private final ResetButtonController resetController;

    /*
     * Factory che crea un QuitButtonController legato a un JFrame specifico.
     * Viene usata da showVictory() e showFinalDefeat() per ottenere un controller
     * il cui dialogo di conferma è centrato sul frame della end-game view,
     * e non su gameFrame (già disposto a quel punto).
     *
     * Firma: Supplier<JFrame> -> QuitButtonController
     */
    private final Function<Supplier<JFrame>, QuitButtonController> quitFactory;

    private final ButtonGamePanel buttonPanel;
    private final List<Card> solution;

    /**
     * @param game                the game model
     * @param boardController     controller for the board
     * @param suspicionController controller for the suspicion action
     * @param accuseController    controller for the accusation action
     * @param resetController     controller for the reset action
     * @param quitController      controller for the quit button in the main game screen
     * @param endTurnController   controller for the end-turn action
     * @param tablePanel          the suspect notes panel
     * @param solution            the three secret solution cards to display on startup
     * @param quitFactory         factory che produce un QuitButtonController dato il supplier
     *                            del JFrame della view chiamante; usata da VictoryView e
     *                            FinalDefeatView per avere il frame corretto nel dialogo
     */
    public GameView(final Game game,
                    final GameBoardController boardController,
                    final InterfaceSuspicionController suspicionController,
                    final InterfaceAccusation accuseController,
                    final ResetButtonController resetController,
                    final QuitButtonController quitController,
                    final EndTurnController endTurnController,
                    final TablePanel tablePanel,
                    final List<Card> solution,
                    final Function<Supplier<JFrame>, QuitButtonController> quitFactory) {

        this.resetController = resetController;
        this.quitFactory = quitFactory;
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

    /** Disabilita suspicion + accusation, abilita fine turno. */
    public void disableActionButtons() {
        buttonPanel.disableActionButtons();
    }

    /** Riabilita suspicion + accusation, disabilita fine turno. */
    public void resetForNewTurn() {
        buttonPanel.resetForNewTurn();
    }

    /**
     * Apre la schermata di vittoria e chiude la finestra di gioco.
     *
     * <p>Usa {@code quitFactory} per creare un QuitButtonController legato
     * al JFrame di VictoryView tramite il pattern dell'array di riferimento:
     * il supplier {@code () -> ref[0]} è valutato in modo lazy, quindi quando
     * l'utente clicca il bottone quit, {@code ref[0]} è già stato assegnato
     * alla finestra corretta.
     */
    public void showVictory() {
    SwingUtilities.invokeLater(() -> {
        final JFrame[] ref = {null};
        final QuitButtonController vc = quitFactory.apply(() -> ref[0]);
        ref[0] = new VictoryView(resetController, vc);

        // Dopo 1 secondo apre la schermata della soluzione segreta
        final javax.swing.Timer timer = new javax.swing.Timer(1000, e ->
            new it.unibo.CluedoLite.view.secretsolutionview.SecretSolutionEndView(solution));
        timer.setRepeats(false);
        timer.start();
    });

    final Window window = SwingUtilities.getWindowAncestor(this);
    if (window != null) {
        window.dispose();
    }
}

    /**
     * Apre la schermata di sconfitta temporanea (con timer).
     * Non chiude la finestra di gioco principale.
     */
    public void showDefeat() {
        SwingUtilities.invokeLater(DefeatView::new);
    }

    /**
     * Apre la schermata di sconfitta definitiva (tutti eliminati) e chiude la finestra di gioco.
     *
     * <p>Stesso pattern lazy di {@link #showVictory()} per il frame corretto.
     */
    public void showFinalDefeat() {
        SwingUtilities.invokeLater(() -> {
            final JFrame[] ref = {null};
            final QuitButtonController vc = quitFactory.apply(() -> ref[0]);
            ref[0] = new FinalDefeatView(resetController, vc);

            // Dopo 1 secondo apre la schermata della soluzione segreta
            final javax.swing.Timer timer = new javax.swing.Timer(1000, e ->
                new it.unibo.CluedoLite.view.secretsolutionview.SecretSolutionEndView(solution));
            timer.setRepeats(false);
            timer.start();
        });

        final Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
    }

    public void updateTablePanel(TablePanel newTablePanel) {
        ((JScrollPane) ((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.EAST))
            .setViewportView(newTablePanel);
        revalidate();
        repaint();
    }
}