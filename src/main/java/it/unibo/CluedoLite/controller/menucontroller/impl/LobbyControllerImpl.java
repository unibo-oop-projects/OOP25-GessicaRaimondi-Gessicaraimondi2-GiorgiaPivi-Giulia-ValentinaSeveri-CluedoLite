package it.unibo.CluedoLite.controller.menucontroller.impl;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import it.unibo.CluedoLite.controller.accuseandsuspectcontroller.impl.AccusationController;
import it.unibo.CluedoLite.controller.accuseandsuspectcontroller.impl.SuspicionController;
import it.unibo.CluedoLite.controller.buttonflowcontroller.impl.QuitButtonControllerImpl;
import it.unibo.CluedoLite.controller.buttonflowcontroller.impl.ResetButtonControllerImpl;
import it.unibo.CluedoLite.controller.gameboard.impl.GameBoardControllerImpl;
import it.unibo.CluedoLite.controller.menucontroller.api.LobbyController;
import it.unibo.CluedoLite.model.accuseandsuspect.impl.AccuseManager;
import it.unibo.CluedoLite.model.accuseandsuspect.impl.SuspicionManager;
import it.unibo.CluedoLite.model.creationcards.impl.Card;
import it.unibo.CluedoLite.model.creationcards.impl.CardType;
import it.unibo.CluedoLite.model.gameflow.api.Game;
import it.unibo.CluedoLite.model.gameflow.impl.GameImpl;
import it.unibo.CluedoLite.model.gamesetup.impl.Deck;
import it.unibo.CluedoLite.model.gamesetup.impl.SecretSolution;
import it.unibo.CluedoLite.model.player.impl.CreationCharacterImpl;
import it.unibo.CluedoLite.model.player.impl.PlayerImpl;
import it.unibo.CluedoLite.model.suspectnotes.impl.TableImpl;
import it.unibo.CluedoLite.view.GameView;
import it.unibo.CluedoLite.view.menuview.LobbyView;

/**
 * Controller for the LobbyView.
 * Handles player setup and character assignment before the game starts,
 * then transitions to the GameView.
 */
public class LobbyControllerImpl implements LobbyController {

    public LobbyControllerImpl() {}

    @Override
    public void onPlayClicked(final LobbyView view) {
        final int numPlayers = view.getNumPlayers();

        // check se due giocatori hanno lo stesso personaggio
        for (int i = 0; i < numPlayers; i++) {
            for (int j = i + 1; j < numPlayers; j++) {
                if (view.getSelectedCharacter(i).equals(view.getSelectedCharacter(j))) {
                    JOptionPane.showMessageDialog(view, "Two players have the same character");
                    return;
                }
            }
        }

        // crea il Game ora che si conosce il numero di giocatori
        final Game game = new GameImpl(numPlayers);
        game.enterLobby(); // MENU → WAITING

        // assign players and characters
        for (int i = 0; i < numPlayers; i++) {
            final String selectedName = view.getSelectedCharacter(i);
            final CreationCharacterImpl character = game.getAvailableCharacters().stream()
                    .filter(c -> c.getName().equals(selectedName))
                    .findFirst()
                    .get();
            game.setPlayer(i, new PlayerImpl("Player " + (i + 1)));
            game.assignCharacterToPlayer(i, character);
        }

        game.startGame(); // WAITING → IN_PROGRESS
        openGameView(game, view);
    }

    /*
     * Builds all controllers and views needed for the game screen,
     * then swaps the current window content.
     */
    private void openGameView(final Game game, final LobbyView lobbyView) {

        // --- carte ---
        final Card[] characters = Deck.getCardsByType(CardType.CHARACTER).toArray(new Card[0]);
        final Card[] weapons    = Deck.getCardsByType(CardType.WEAPON).toArray(new Card[0]);
        final Card[] rooms      = Deck.getCardsByType(CardType.ROOM).toArray(new Card[0]);

        // --- SecretSolution e AccuseManager ---
        final List<Card> allCards = new ArrayList<>();
        allCards.addAll(Deck.getCardsByType(CardType.CHARACTER));
        allCards.addAll(Deck.getCardsByType(CardType.WEAPON));
        allCards.addAll(Deck.getCardsByType(CardType.ROOM));
        final SecretSolution secretSolution = new SecretSolution(allCards);
        final AccuseManager  accuseManager  = new AccuseManager(secretSolution);

        // --- GameBoardController ---
        final GameBoardControllerImpl boardController = new GameBoardControllerImpl(
                game.getGameBoard(),
                game.getTurnManager()
        );

        // --- SuspicionController ---
        final SuspicionController suspicionController = new SuspicionController(
                new SuspicionManager(),
                game.getTurnManager().getCurrentPlayer(),
                characters,
                weapons,
                rooms[0],
                suspicion -> System.out.println("Suspicion: " + suspicion)
        );

        // --- AccusationController ---
        final AccusationController accuseController = new AccusationController(
                accuseManager,
                characters,
                weapons,
                rooms,
                result -> {
                    if (result) {
                        JOptionPane.showMessageDialog(null, "You won! Correct accusation!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong accusation! You are eliminated.");
                    }
                }
        );

        // --- ResetButtonController ---
        final ResetButtonControllerImpl resetController = new ResetButtonControllerImpl(game);

        // --- Table reale con le carte in mano al giocatore corrente ---
        final TableImpl table = new TableImpl(
                game.getTurnManager().getCurrentPlayer().getHand()
        );

        try {
            // il frame va creato prima del QuitController per passargli il riferimento
            final JFrame gameFrame = new JFrame("Cluedo Lite");

            // --- QuitButtonController (riceve il frame per poterlo chiudere) ---
            final QuitButtonControllerImpl quitController = new QuitButtonControllerImpl(game, gameFrame);

            final GameView gameView = new GameView(
                    game,
                    boardController,
                    suspicionController,
                    accuseController,
                    resetController,
                    quitController,
                    table,
                    secretSolution.getSolution()
            );

            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            gameFrame.add(gameView);
            gameFrame.setVisible(true);
            lobbyView.dispose();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore: " + e.getMessage());
        }
    }
}