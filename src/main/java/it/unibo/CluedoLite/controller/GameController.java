package it.unibo.CluedoLite.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import it.unibo.CluedoLite.controller.accuseandsuspectcontroller.impl.AccusationController;
import it.unibo.CluedoLite.controller.accuseandsuspectcontroller.impl.SuspicionController;
import it.unibo.CluedoLite.controller.buttonflowcontroller.api.QuitButtonController;
import it.unibo.CluedoLite.controller.buttonflowcontroller.impl.QuitButtonControllerImpl;
import it.unibo.CluedoLite.controller.buttonflowcontroller.impl.ResetButtonControllerImpl;
import it.unibo.CluedoLite.controller.endturnbutton.impl.EndTurnControllerImpl;
import it.unibo.CluedoLite.controller.gameboard.impl.GameBoardControllerImpl;
import it.unibo.CluedoLite.controller.menucontroller.impl.StartControllerImpl;
import it.unibo.CluedoLite.controller.tablecontroller.impl.TableControllerImpl;
import it.unibo.CluedoLite.model.accuseandsuspect.impl.AccuseManager;
import it.unibo.CluedoLite.model.accuseandsuspect.impl.SuspicionManager;
import it.unibo.CluedoLite.model.creationcards.impl.Card;
import it.unibo.CluedoLite.model.creationcards.impl.CardType;
import it.unibo.CluedoLite.model.gameflow.api.Game;
import it.unibo.CluedoLite.model.gamesetup.impl.CardDistribution;
import it.unibo.CluedoLite.model.gamesetup.impl.Deck;
import it.unibo.CluedoLite.model.gamesetup.impl.SecretSolution;
import it.unibo.CluedoLite.model.player.api.Player;
import it.unibo.CluedoLite.model.suspectnotes.impl.TableImpl;
import it.unibo.CluedoLite.view.GameView;
import it.unibo.CluedoLite.view.menuview.StartView;
import it.unibo.CluedoLite.view.tableview.TablePanel;
import javafx.stage.Window;

/**
 * Central controller for a CluedoLite game session.
 *
 * <p>Turn flow:
 * <ol>
 *   <li>Player moves on the board (once per turn).</li>
 *   <li>Player makes a suspicion or accusation (mandatory before ending turn).</li>
 *   <li>Player clicks "Fine turno" to advance.</li>
 * </ol>
 */
public class GameController {

    // Fixed for the whole session
    private final Game game;
    private final Card[] characters;
    private final Card[] weapons;
    private final Card[] rooms;
    private SecretSolution secretSolution;
    private AccuseManager accuseManager;

    // Rebuilt on each reset
    private JFrame gameFrame;
    private GameBoardControllerImpl boardController;
    private GameView gameView;
    private AccusationController accusationController;
    private TableControllerImpl tableController;

    /**
     * Constructs a {@link GameController} for the given game session.
     *
     * @param game the game model to control
     */
    public GameController(final Game game) {
        this.game = game;

        this.characters = Deck.getCardsByType(CardType.CHARACTER).toArray(new Card[0]);
        this.weapons    = Deck.getCardsByType(CardType.WEAPON).toArray(new Card[0]);
        this.rooms      = Deck.getCardsByType(CardType.ROOM).toArray(new Card[0]);

        initSession();
    }

    // -----------------------------------------------------------------------
    // Public API
    // -----------------------------------------------------------------------

    /**
     * Builds and displays the main game window.
     *
     * @param previousWindow the window to dispose after opening the game window,
     *                       or {@code null} if there is none
     */
    public void openGameWindow(final JFrame previousWindow) {
        final JFrame oldFrame = gameFrame;

        try {
            boardController = new GameBoardControllerImpl(
                    game.getGameBoard(), game.getTurnManager());

            System.out.println("[DEBUG] Mano giocatore: "
                    + game.getTurnManager().getCurrentPlayer().getHand());

            final TableImpl table = new TableImpl(
                    game.getTurnManager().getCurrentPlayer().getHand());
            final TablePanel tablePanel = new TablePanel(table);

            this.tableController = new TableControllerImpl(
                    game.getTurnManager(), table, tablePanel);

            final SuspicionController suspicionController = new SuspicionController(
                    new SuspicionManager(),
                    characters,
                    weapons,
                    this::getCurrentPlayerRoom,
                    suspicion -> {
                        final Card refutation = game.getTurnManager().checkSuspicion(suspicion);
                        if (refutation != null) {
                            JOptionPane.showMessageDialog(null,
                                    "A player show the card: " + refutation.getName(),
                                    "Sospect refuted", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "No player can refute the suspicion!",
                                    "Unrefuted suspect", JOptionPane.WARNING_MESSAGE);
                        }
                        tableController.handleSuspicion(suspicion);
                        boardController.lockMovement();
                        gameView.disableActionButtons();

                        final String suspect = game.getTurnManager().getCurrentPlayer().getName();
                        if (refutation != null) {
                            gameView.addHistoryEntry(suspect + " made a suspicion — a player showed a card");
                        } else {
                            gameView.addHistoryEntry(suspect + " made a suspicion — no player could refute it");
                        }
                    },
                    game.getTurnManager()::getCurrentPlayer,
                    () -> gameView.disableActionButtons()
            );

            this.accusationController = new AccusationController(
                    accuseManager,
                    characters,
                    weapons,
                    rooms,
                    this::handleAccusationResult,
                    () -> gameView.disableActionButtons()
            );

            final EndTurnControllerImpl endTurnController = new EndTurnControllerImpl(() -> {
                if (game.getTurnManager().isGameOver()) {
                    return;
                }
                advanceTurn();
            });

            final ResetButtonControllerImpl resetController =
                    new ResetButtonControllerImpl(game) {
                        @Override
                        public boolean onResetClicked() {
                            final int confirm = JOptionPane.showConfirmDialog(
                                    null,
                                    "Are you sure you want to restart?",
                                    "Reset",
                                    JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                handleReset();
                                return true;
                            }
                            return false;
                        }
                    };

            gameFrame = new JFrame("Cluedo Lite");

            final QuitButtonControllerImpl quitController =
                    new QuitButtonControllerImpl(game, () -> gameFrame) {
                        @Override
                        public void onQuitClicked() {
                            final int confirm = JOptionPane.showConfirmDialog(
                                    gameFrame,
                                    "Are you sure you want to quit to the main menu?",
                                    "Quit",
                                    JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                handleQuit();
                            }
                        }
                    };

            gameView = new GameView(
                    game,
                    boardController,
                    suspicionController,
                    accusationController,
                    resetController,
                    quitController,
                    endTurnController,
                    tablePanel,
                    secretSolution.getSolution(),
                    this::quitControllerFor
            );

            gameView.resetForNewTurn();

            gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            gameFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                    for (java.awt.Window w : java.awt.Window.getWindows()) {
                        if (w != gameFrame && w.isVisible()) {
                            w.dispose();
                        }
                    }
                    gameFrame.dispose();
                    System.exit(0);
                }
            });
            gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            gameFrame.add(gameView);
            gameFrame.setVisible(true);

            if (previousWindow != null) {
                previousWindow.dispose();
            }
            if (oldFrame != null && oldFrame != previousWindow) {
                oldFrame.dispose();
            }

        } catch (final Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error opening the game window: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // -----------------------------------------------------------------------
    // Game-event handlers
    // -----------------------------------------------------------------------

    /**
     * Handles the result of an accusation.
     *
     * @param result {@code true} if the accusation was correct, {@code false} otherwise
     */
    public void handleAccusationResult(final boolean result) {
        if (result) {
            game.getTurnManager().endGame();
            gameView.showVictory();
        } else {
            game.getTurnManager().getCurrentPlayer().eliminate();

            if (countActivePlayers() == 1) {
                game.getTurnManager().nextTurn();
                gameFrame.dispose();
                accusationController.openAccusationView();
            } else if (countActivePlayers() == 0) {
                game.getTurnManager().endGame();
                gameView.showFinalDefeat();
            } else {
                gameView.showDefeat();
                advanceTurn();
            }
        }
    }

    /**
     * Resets the game to its initial state and reopens the game window.
     */
    public void handleReset() {
        game.resetGame();
        game.startGame();
        initSession();
        openGameWindow(null);
    }

    /**
     * Quits the current game session and returns to the main menu.
     */
    public void handleQuit() {
        game.quitToMenu();
        if (gameFrame != null) {
            gameFrame.dispose();
            gameFrame = null;
        }
        SwingUtilities.invokeLater(() -> {
            final StartControllerImpl startController = new StartControllerImpl();
            new StartView(startController);
        });
    }

    // -----------------------------------------------------------------------
    // Private helpers
    // -----------------------------------------------------------------------

    /**
     * Factory that creates a {@link QuitButtonController} bound to a specific frame.
     *
     * <p>Passed to {@link GameView} as a method reference ({@code this::quitControllerFor})
     * so that {@code VictoryView} and {@code FinalDefeatView} can build their own controller
     * with a supplier for their own frame, instead of using {@code gameFrame} which will
     * already have been disposed at that point.
     *
     * @param frameSupplier supplier returning the JFrame of the calling view
     * @return a {@link QuitButtonController} with a confirmation dialog centred on the
     *         correct frame
     */
    private QuitButtonController quitControllerFor(final Supplier<JFrame> frameSupplier) {
        return new QuitButtonControllerImpl(game, frameSupplier) {
            @Override
            public void onQuitClicked() {
                final int confirm = JOptionPane.showConfirmDialog(
                        frameSupplier.get(),
                        "Are you sure you want to quit to the main menu?",
                        "Quit",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    final JFrame endGameFrame = frameSupplier.get();
                    handleQuit();
                    if (endGameFrame != null) {
                        endGameFrame.dispose();
                    }
                }
            }
        };
    }

    private void advanceTurn() {
        boardController.endTurn();
        final TablePanel newPanel = tableController.refreshForPlayer();
        gameView.updateTablePanel(newPanel);
        gameView.resetForNewTurn();
    }

    private Card getCurrentPlayerRoom() {
        if (boardController == null) {
            return null;
        }

        final var currentRoom = boardController.getCurrentRoomOf(
                game.getTurnManager().getCurrentPlayer());

        if (currentRoom == null) {
            return null;
        }

        for (final Card card : rooms) {
            if (card.getName().equals(currentRoom.getName())) {
                return card;
            }
        }
        return null;
    }

    private long countActivePlayers() {
        return game.getPlayers().stream()
                .filter(p -> !p.isEliminated())
                .count();
    }

    /**
     * Re-initialises the secret solution, accusation manager, and card distribution.
     * Called both at construction time and on every reset, to ensure each game
     * has a different solution.
     */
    private void initSession() {
        final List<Card> allCards = new ArrayList<>();
        allCards.addAll(List.of(characters));
        allCards.addAll(List.of(weapons));
        allCards.addAll(List.of(rooms));

        game.getPlayers().forEach(Player::clearHand);

        this.secretSolution = new SecretSolution(allCards);
        this.accuseManager  = new AccuseManager(secretSolution);

        Collections.shuffle(allCards);
        new CardDistribution(allCards, game.getPlayers());
    }
}