package it.unibo.cluedolite.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import it.unibo.cluedolite.controller.accuseandsuspectcontroller.impl.AccusationController;
import it.unibo.cluedolite.controller.accuseandsuspectcontroller.impl.SuspicionController;
import it.unibo.cluedolite.controller.buttonflowcontroller.api.QuitButtonController;
import it.unibo.cluedolite.controller.buttonflowcontroller.impl.QuitButtonControllerImpl;
import it.unibo.cluedolite.controller.buttonflowcontroller.impl.ResetButtonControllerImpl;
import it.unibo.cluedolite.controller.endturnbuttoncontroller.impl.EndTurnControllerImpl;
import it.unibo.cluedolite.controller.gameboardcontroller.impl.GameBoardControllerImpl;
import it.unibo.cluedolite.controller.menucontroller.impl.StartControllerImpl;
import it.unibo.cluedolite.controller.tablecontroller.impl.TableControllerImpl;
import it.unibo.cluedolite.model.accuseandsuspect.impl.AccuseManager;
import it.unibo.cluedolite.model.accuseandsuspect.impl.SuspicionManager;
import it.unibo.cluedolite.model.creationcards.impl.AbstractCard;
import it.unibo.cluedolite.model.creationcards.impl.CardType;
import it.unibo.cluedolite.model.gameflow.api.Game;
import it.unibo.cluedolite.model.gamesetup.impl.CardDistribution;
import it.unibo.cluedolite.model.gamesetup.impl.Deck;
import it.unibo.cluedolite.model.gamesetup.impl.SecretSolution;
import it.unibo.cluedolite.model.player.api.Player;
import it.unibo.cluedolite.model.suspectnotes.impl.TableImpl;
import it.unibo.cluedolite.view.GameView;
import it.unibo.cluedolite.view.menuview.StartView;
import it.unibo.cluedolite.view.tableview.TablePanel;

/**
 * Central controller for a CluedoLite game session.
 *
 * <p>Turn flow:
 * <ol>
 *   <li>Player moves on the board (once per turn).</li>
 *   <li>Player makes a suspicion or accusation (mandatory before ending turn).</li>
 *   <li>Player clicks "End turn" to advance.</li>
 * </ol>
 */
public class GameController {

    private final Game game;
    private final AbstractCard[] characters;
    private final AbstractCard[] weapons;
    private final AbstractCard[] rooms;
    private SecretSolution secretSolution;
    private AccuseManager accuseManager;

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

        this.characters = Deck.getCardsByType(CardType.CHARACTER).toArray(new AbstractCard[0]);
        this.weapons = Deck.getCardsByType(CardType.WEAPON).toArray(new AbstractCard[0]);
        this.rooms = Deck.getCardsByType(CardType.ROOM).toArray(new AbstractCard[0]);

        initSession();
    }

    /**
     * Builds and displays the main game window.
     *
     * @param previousWindow the window to dispose after opening the game window,
     *                       or {@code null} if there is none
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public void openGameWindow(final JFrame previousWindow) {
        final JFrame oldFrame = gameFrame;

        try {
            boardController = new GameBoardControllerImpl(
                    game.getGameBoard(), game.getTurnManager());

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
                final Optional<AbstractCard> refutation = game.getTurnManager().checkSuspicion(suspicion);
                if (refutation.isPresent()) {
                    JOptionPane.showMessageDialog(null,
                            "A player show the card: " + refutation.get().getName(),
                            "Suspicion refuted", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No player can refute the suspicion!",
                            "Unrefuted suspicion", JOptionPane.WARNING_MESSAGE);
                }
                tableController.handleSuspicion(suspicion, refutation);
                boardController.lockMovement();
                gameView.disableActionButtons();

                final String suspect = game.getTurnManager().getCurrentPlayer().getName();
                if (refutation.isPresent()) {
                    gameView.addHistoryEntry(suspect + " made a suspicion: ["
                        + suspicion.getCharacter() + ", " + suspicion.getWeapon()
                        + ", " + suspicion.getRoom() + "] — player "
                        + game.getTurnManager().getShownBy() + " showed a card");
                } else {
                    gameView.addHistoryEntry(suspect + " made a suspicion: ["
                        + suspicion.getCharacter() + ", " + suspicion.getWeapon()
                        + ", " + suspicion.getRoom() + "] — no player could refute it");
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
                public void windowClosing(final java.awt.event.WindowEvent e) {
                    for (final java.awt.Window w : java.awt.Window.getWindows()) {
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

        } catch (final HeadlessException e) {
            JOptionPane.showMessageDialog(null,
                    "Error opening the game window: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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

    /**
     * Advances the turn to the next player and updates the view accordingly.
     */
    private void advanceTurn() {
        boardController.endTurn();
        final TablePanel newPanel = tableController.refreshForPlayer();
        gameView.updateTablePanel(newPanel);
        gameView.resetForNewTurn();
    }

    /**
     * Returns the {@link AbstractCard} corresponding to the current player's room,
     * or {@code null} if the player is not in any room.
     *
     * @return the room card, or {@code null}
     */
    private AbstractCard getCurrentPlayerRoom() {
        if (boardController == null) {
            return null;
        }

        final var currentRoom = boardController.getCurrentRoomOf(
                game.getTurnManager().getCurrentPlayer());

        if (currentRoom == null) {
            return null;
        }

        for (final AbstractCard card : rooms) {
            if (card.getName().equals(currentRoom.getName())) {
                return card;
            }
        }
        return null;
    }

    /**
     * Returns the number of players that have not been eliminated.
     *
     * @return the count of active players
     */
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
        final List<AbstractCard> allCards = new ArrayList<>();
        allCards.addAll(List.of(characters));
        allCards.addAll(List.of(weapons));
        allCards.addAll(List.of(rooms));

        game.getPlayers().forEach(Player::clearHand);

        this.secretSolution = new SecretSolution(allCards);
        this.accuseManager = new AccuseManager(secretSolution);

        Collections.shuffle(allCards);
        new CardDistribution(allCards, game.getPlayers());
    }
}
