package it.unibo.CluedoLite.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import it.unibo.CluedoLite.controller.accuseandsuspectcontroller.impl.AccusationController;
import it.unibo.CluedoLite.controller.accuseandsuspectcontroller.impl.SuspicionController;
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
import it.unibo.CluedoLite.model.gamesetup.impl.Deck;
import it.unibo.CluedoLite.model.gamesetup.impl.SecretSolution;
import it.unibo.CluedoLite.model.suspectnotes.impl.TableImpl;
import it.unibo.CluedoLite.view.GameView;
import it.unibo.CluedoLite.view.menuview.StartView;
import it.unibo.CluedoLite.view.tableview.TablePanel;

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
    private final SecretSolution secretSolution;
    private final AccuseManager accuseManager;

    // Rebuilt on each reset
    private JFrame gameFrame;
    private GameBoardControllerImpl boardController;
    private GameView gameView;

    public GameController(final Game game) {
        this.game = game;

        this.characters = Deck.getCardsByType(CardType.CHARACTER).toArray(new Card[0]);
        this.weapons    = Deck.getCardsByType(CardType.WEAPON).toArray(new Card[0]);
        this.rooms      = Deck.getCardsByType(CardType.ROOM).toArray(new Card[0]);

        final List<Card> allCards = new ArrayList<>();
        allCards.addAll(List.of(characters));
        allCards.addAll(List.of(weapons));
        allCards.addAll(List.of(rooms));
        this.secretSolution = new SecretSolution(allCards);
        this.accuseManager  = new AccuseManager(secretSolution);

        Collections.shuffle(allCards);
        int numPlayers = game.getPlayers().size();
        for (int i = 0; i < allCards.size(); i++) {
            game.getPlayers().get(i % numPlayers).addCard(allCards.get(i));
        }
    }

    // -----------------------------------------------------------------------
    // Public API
    // -----------------------------------------------------------------------

    public void openGameWindow(final JFrame previousWindow) {
        final JFrame oldFrame = gameFrame;

        try {
            boardController = new GameBoardControllerImpl(
                    game.getGameBoard(), game.getTurnManager());

            System.out.println("[DEBUG] Mano giocatore: " + game.getTurnManager().getCurrentPlayer().getHand());
            final TableImpl table = new TableImpl(
                    game.getTurnManager().getCurrentPlayer().getHand());
            final TablePanel tablePanel = new TablePanel(table);

            final TableControllerImpl tableController = new TableControllerImpl(
                    game.getTurnManager(), table, tablePanel);

            final SuspicionController suspicionController = new SuspicionController(
                    new SuspicionManager(),
                    characters,
                    weapons,
                    this::getCurrentPlayerRoom,
                    suspicion -> {
                        // mostra la carta confutatrice se esiste
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
                        gameView.disableActionButtons();
                    },
                    game.getTurnManager()::getCurrentPlayer
            );

            final AccusationController accusationController = new AccusationController(
                    accuseManager, characters, weapons, rooms,
                    this::handleAccusationResult);

            // fine turno: bloccato se partita finita o azione non ancora eseguita
            final EndTurnControllerImpl endTurnController = new EndTurnControllerImpl(() -> {
                if (game.getTurnManager().isGameOver()) return;
                advanceTurn();
            });

            final ResetButtonControllerImpl resetController =
                    new ResetButtonControllerImpl(game) {
                        @Override
                        public void onResetClicked() {
                            final int confirm = JOptionPane.showConfirmDialog(
                                null, "Are you sure you want to restart?",
                                "Reset", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                handleReset();
                            }
                        }
                    };

            gameFrame = new JFrame("Cluedo Lite");
            final QuitButtonControllerImpl quitController =
                    new QuitButtonControllerImpl(game, gameFrame) {
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
                    secretSolution.getSolution()
            );

            gameView.resetForNewTurn(); //disabilita/abilita i pulsanti giusti

            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            gameFrame.add(gameView);
            gameFrame.setVisible(true);

            if (previousWindow != null) previousWindow.dispose();
            if (oldFrame != null && oldFrame != previousWindow) oldFrame.dispose();

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

    public void handleAccusationResult(final boolean result) {
        if (result) {
            game.getTurnManager().endGame();  // segna la partita come finita
            gameView.showVictory();
        } else {
            gameView.showDefeat();
            game.getTurnManager().getCurrentPlayer().eliminate();
            advanceTurn();
        }
    }

    public void handleReset() {
        game.resetGame();
        game.startGame();
        openGameWindow(null);
    }

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

    private void advanceTurn() {
        gameView.resetForNewTurn();
        boardController.endTurn();
    }

    private Card getCurrentPlayerRoom() {
        if (boardController == null) return rooms[0];

        final var currentRoom = boardController.getCurrentRoomOf(
                game.getTurnManager().getCurrentPlayer());

        if (currentRoom == null) return rooms[0];

        for (final Card card : rooms) {
            if (card.getName().equals(currentRoom.getName())) return card;
        }
        return rooms[0];
    }
}