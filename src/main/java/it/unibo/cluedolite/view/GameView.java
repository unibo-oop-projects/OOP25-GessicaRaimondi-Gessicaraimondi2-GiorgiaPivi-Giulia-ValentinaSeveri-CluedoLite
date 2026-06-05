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
import it.unibo.cluedolite.model.creationcards.impl.AbstractCard;
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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Main game view.
 * Layout: buttons panel (west) | board (center) | suspect table (east).
 * On startup, shows the {@link SecretSolutionStartView} for 5 seconds.
 */
public final class GameView extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int WINDOW_WIDTH = 520;
    private final transient ResetButtonController resetController;
    private final transient Function<Supplier<JFrame>, QuitButtonController> quitFactory;
    private final ButtonGamePanel buttonPanel;
    private final transient List<AbstractCard> solution;

    /**
     * Creates a new {@link GameView} with all required controllers and panels.
     *
     * @param game                the game model
     * @param boardController     controller for the board
     * @param suspicionController controller for the suspicion action
     * @param accuseController    controller for the accusation action
     * @param resetController     controller for the reset action
     * @param quitController      controller for the quit button in the main game screen
     * @param endTurnController   controller for the end-turn action
     * @param tablePanel          the suspect notes panel
     * @param solution            the three secret solution cards to display on startup
     * @param quitFactory         factory that produces a {@link QuitButtonController} given the supplier
     *                            of the calling view's {@link JFrame}; used by victory and defeat views
     *                            to correctly center the quit dialog
     */
    public GameView(final Game game,
                    final GameBoardController boardController,
                    final InterfaceSuspicionController suspicionController,
                    final InterfaceAccusation accuseController,
                    final ResetButtonController resetController,
                    final QuitButtonController quitController,
                    final EndTurnController endTurnController,
                    final TablePanel tablePanel,
                    final List<AbstractCard> solution,
                    final Function<Supplier<JFrame>, QuitButtonController> quitFactory) {

        this.resetController = resetController;
        this.quitFactory = quitFactory;
        this.solution = new ArrayList<>(solution);

        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLayout(new BorderLayout());
        setPreferredSize(screen);

        /*
         * Wraps the suspicion and accusation controllers to disable buttons
         * immediately on click, before opening the view, preventing duplicate instances.
         */
        final ButtonGamePanel[] panelRef = {null};
        final InterfaceSuspicionController wrappedSuspicion = () -> {
            suspicionController.openSuspicionView();
        };

        final InterfaceAccusation wrappedAccuse = () -> {
            panelRef[0].disableActionButtons();
            accuseController.openAccusationView();
        };

        buttonPanel = new ButtonGamePanel(
                wrappedSuspicion,
                wrappedAccuse,
                resetController,
                quitController,
                endTurnController);
        panelRef[0] = buttonPanel;
        add(buttonPanel, BorderLayout.WEST);

        final BoardViewImpl board = new BoardViewImpl(game.getPlayers(), boardController);
        boardController.setView(board);
        final int boardSize = (int) (screen.height * 0.95);
        board.setPreferredSize(new Dimension(boardSize, boardSize));
        board.setMinimumSize(new Dimension(boardSize, boardSize));
        add(board, BorderLayout.CENTER);

        final JScrollPane scrollTable = new JScrollPane(tablePanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollTable.setPreferredSize(new Dimension(WINDOW_WIDTH, screen.height));
        add(scrollTable, BorderLayout.EAST);

        SwingUtilities.invokeLater(() -> new SecretSolutionStartView(solution));
    }

    /**
     * Disables the suspicion and accusation buttons and enables the end-turn button.
     */
    public void disableActionButtons() {
        buttonPanel.disableActionButtons();
    }

    /**
     * Re-enables the suspicion and accusation buttons and disables the end-turn button.
     */
    public void resetForNewTurn() {
        buttonPanel.resetForNewTurn();
    }

    /**
     * Opens the victory screen and closes the game window.
     *
     * <p>Uses {@code quitFactory} to create a {@link QuitButtonController} bound
     * to the {@link VictoryView} frame via a lazy reference array pattern.
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
     * Opens the temporary defeat screen.
     * Does not close the main game window.
     */
    public void showDefeat() {
        SwingUtilities.invokeLater(DefeatView::new);
    }

    /**
     * Opens the final defeat screen and closes the game window.
     *
     * <p>Uses the same lazy reference pattern as {@link #showVictory()}.
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

    /**
     * Adds an entry to the game history panel.
     *
     * @param message the message to add
     */
    public void addHistoryEntry(final String message) {
        buttonPanel.addHistoryEntry(message);
    }

    /**
     * Prevents deserialization of this class.
     *
     * @param stream the object input stream
     * @throws java.io.NotSerializableException always
     */
    private void readObject(final java.io.ObjectInputStream stream) throws java.io.IOException, ClassNotFoundException {
        throw new java.io.NotSerializableException(stream.getClass().getName());
    }
}
