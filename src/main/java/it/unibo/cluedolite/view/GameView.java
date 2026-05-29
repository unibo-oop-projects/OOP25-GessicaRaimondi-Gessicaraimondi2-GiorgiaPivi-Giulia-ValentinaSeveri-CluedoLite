package it.unibo.cluedolite.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import it.unibo.cluedolite.controller.accuseandsuspectcontroller.api.InterfaceAccusation;
import it.unibo.cluedolite.controller.accuseandsuspectcontroller.api.InterfaceSuspicionController;
import it.unibo.cluedolite.controller.buttonflowcontroller.api.QuitButtonController;
import it.unibo.cluedolite.controller.buttonflowcontroller.api.ResetButtonController;
import it.unibo.cluedolite.controller.endturnbuttoncontroller.api.EndTurnController;
import it.unibo.cluedolite.controller.gameboardcontroller.api.GameBoardController;
import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.model.gameflow.api.Game;
import it.unibo.cluedolite.view.endgameview.DefeatView;
import it.unibo.cluedolite.view.endgameview.FinalDefeatView;
import it.unibo.cluedolite.view.endgameview.VictoryView;
import it.unibo.cluedolite.view.gameboardview.impl.BoardViewImpl;
import it.unibo.cluedolite.view.gamebutton.ButtonGamePanel;
import it.unibo.cluedolite.view.secretsolutionview.SecretSolutionStartView;
import it.unibo.cluedolite.view.tableview.TablePanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

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

        /*
         * Wrapping dei controller di sospetto e accusa:
         * disabilita i bottoni immediatamente al click, PRIMA di aprire la finestra,
         * cosi' e' impossibile aprire due istanze della stessa view.
         */
        final ButtonGamePanel[] panelRef = {null};
        final InterfaceSuspicionController wrappedSuspicion = () -> {
            suspicionController.openSuspicionView();
        };

        final InterfaceAccusation wrappedAccuse = () -> {
            panelRef[0].disableActionButtons();
            accuseController.openAccusationView();
        };

        // --- pannello bottoni (sinistra) ---
        buttonPanel = new ButtonGamePanel(
                wrappedSuspicion,
                wrappedAccuse,
                resetController,
                quitController,
                endTurnController);
        panelRef[0] = buttonPanel; 
        add(buttonPanel, BorderLayout.WEST);

        // --- tabellone (centro) ---
        final BoardViewImpl board = new BoardViewImpl(game.getPlayers(), boardController);
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

        // --- schermata soluzione segreta (si apre appena il pannello e' visibile) ---
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
     * il supplier {@code () -> ref[0]} e' valutato in modo lazy, quindi quando
     * l'utente clicca il bottone quit, {@code ref[0]} e' gia' stato assegnato
     * alla finestra corretta.
     */
    public void showVictory() {
        SwingUtilities.invokeLater(() -> {
            final JFrame[] ref = {null};
            final QuitButtonController vc = quitFactory.apply(() -> ref[0]);
            ref[0] = new VictoryView(resetController, vc);

            final javax.swing.Timer timer = new javax.swing.Timer(1000, e ->
                new it.unibo.cluedolite.view.secretsolutionview.SecretSolutionEndView(solution));
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

            final javax.swing.Timer timer = new javax.swing.Timer(1000, e ->
                new it.unibo.cluedolite.view.secretsolutionview.SecretSolutionEndView(solution));
            timer.setRepeats(false);
            timer.start();
        });

        final Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
    }

    /**
     * Replaces the suspect notes panel in the east scroll pane.
     *
     * @param newTablePanel the new panel to display
     */
    public void updateTablePanel(final TablePanel newTablePanel) {
        ((JScrollPane) ((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.EAST))
            .setViewportView(newTablePanel);
        revalidate();
        repaint();
    }

    public void addHistoryEntry(String message) {
        buttonPanel.addHistoryEntry(message);
    }
}