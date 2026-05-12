package it.unibo.CluedoLite.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import it.unibo.CluedoLite.controller.accuseandsuspectcontroller.impl.AccusationController;
import it.unibo.CluedoLite.controller.accuseandsuspectcontroller.impl.SuspicionController;
import it.unibo.CluedoLite.controller.buttonflowcontroller.impl.QuitButtonControllerImpl;
import it.unibo.CluedoLite.controller.buttonflowcontroller.impl.ResetButtonControllerImpl;
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
 *   <li>Player moves on the board.</li>
 *   <li>Player makes a suspicion or accusation (mandatory).</li>
 *   <li>Suspicion → table updates, turn advances.</li>
 *   <li>Accusation correct → player wins, returns to main menu.</li>
 *   <li>Accusation wrong → player eliminated, turn advances.</li>
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
    }

    // -----------------------------------------------------------------------
    // Public API
    // -----------------------------------------------------------------------

    public void openGameWindow(final JFrame previousWindow) {
        final JFrame oldFrame = gameFrame;

        try {
            boardController = new GameBoardControllerImpl(
                    game.getGameBoard(), game.getTurnManager());

            final TableImpl table = new TableImpl(
                    game.getTurnManager().getCurrentPlayer().getHand());
            final TablePanel tablePanel = new TablePanel(table);

            final TableControllerImpl tableController = new TableControllerImpl(
                    game.getTurnManager(), table, tablePanel);

            // Ordine parametri allineato al costruttore di SuspicionController:
            // (SuspicionManager, Card[], Card[], Supplier<Card>, Consumer<Suspicion>, Supplier<Player>)
            final SuspicionController suspicionController = new SuspicionController(
                    new SuspicionManager(),
                    characters,
                    weapons,
                    this::getCurrentPlayerRoom,
                    suspicion -> {
                        tableController.handleSuspicion(suspicion);
                        advanceTurn();
                    },
                    game.getTurnManager()::getCurrentPlayer
            );

            final AccusationController accusationController = new AccusationController(
                    accuseManager, characters, weapons, rooms,
                    this::handleAccusationResult);

            final ResetButtonControllerImpl resetController =
                    new ResetButtonControllerImpl(game) {
                        @Override
                        public void onResetClicked() {
                            final int confirm = JOptionPane.showConfirmDialog(
                                null, "Sei sicuro di voler ricominciare?",
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
                            handleQuit();
                        }
                    };

            final GameView gameView = new GameView(
                    game,
                    boardController,
                    suspicionController,
                    accusationController,
                    resetController,
                    quitController,
                    tablePanel,
                    secretSolution.getSolution()
            );

            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            gameFrame.add(gameView);
            gameFrame.setVisible(true);

            if (previousWindow != null) previousWindow.dispose();
            if (oldFrame != null && oldFrame != previousWindow) oldFrame.dispose();

        } catch (final Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Errore nell'apertura della finestra di gioco: " + e.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    // -----------------------------------------------------------------------
    // Game-event handlers
    // -----------------------------------------------------------------------

    public void handleAccusationResult(final boolean result) {
        if (result) {
            JOptionPane.showMessageDialog(null,
                    "Congratulazioni! Accusa corretta. Hai vinto!",
                    "Vittoria!", JOptionPane.INFORMATION_MESSAGE);
            handleQuit();
        } else {
            JOptionPane.showMessageDialog(null,
                    "Accusa sbagliata! Sei eliminato.",
                    "Eliminato", JOptionPane.WARNING_MESSAGE);
            game.getTurnManager().getCurrentPlayer().eliminate(); // ← elimina prima
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
        boardController.endTurn();
    }

    /**
     * Restituisce la Card corrispondente alla stanza corrente del giocatore attivo.
     * Usato come Supplier, quindi eseguito nel momento in cui serve.
     */
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