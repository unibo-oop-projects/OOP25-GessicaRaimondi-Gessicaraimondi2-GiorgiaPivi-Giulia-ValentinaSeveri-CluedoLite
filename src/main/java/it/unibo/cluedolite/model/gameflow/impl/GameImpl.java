package it.unibo.cluedolite.model.gameflow.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.cluedolite.model.gameboard.api.GameBoardModel;
import it.unibo.cluedolite.model.gameboard.impl.GameBoardModelImpl;
import it.unibo.cluedolite.model.gameflow.api.Game;
import it.unibo.cluedolite.model.gameflow.api.GameState;
import it.unibo.cluedolite.model.player.api.Player;
import it.unibo.cluedolite.model.player.impl.CreationCharacterImpl;
import it.unibo.cluedolite.model.turnmanager.api.TurnManager;
import it.unibo.cluedolite.model.turnmanager.impl.TurnManagerImpl;

/**
 * Implementation of {@link Game} that manages the full lifecycle of a CluedoLite game.
 * Handles player setup, character assignment, game state transitions,
 * board initialization, and turn management.
 */
public final class GameImpl implements Game {

    private static final int MIN_PLAYERS = 3;
    private static final int MAX_PLAYERS = 6;

    private static final List<CreationCharacterImpl> DEFAULT_CHARACTERS = List.of(
        new CreationCharacterImpl("Miss Scarlet", "RED"),
        new CreationCharacterImpl("Colonel Mustard", "YELLOW"),
        new CreationCharacterImpl("Mrs. White", "WHITE"),
        new CreationCharacterImpl("Mr. Green", "GREEN"),
        new CreationCharacterImpl("Mrs. Peacock", "BLUE"),
        new CreationCharacterImpl("Professor Plum", "PURPLE")
    );

    private final List<Player> players;
    private final List<CreationCharacterImpl> availableCharacters;
    private GameState state;
    private GameBoardModel gameBoard;
    private TurnManager turnManager;

    /**
     * Constructs a new {@code GameImpl} with the given number of players.
     *
     * @param numPlayers the number of players, must be between 3 and 6 (inclusive)
     * @throws IllegalArgumentException if {@code numPlayers} is not between 3 and 6
     */
    public GameImpl(final int numPlayers) {
        if (numPlayers < MIN_PLAYERS || numPlayers > MAX_PLAYERS) {
            throw new IllegalArgumentException("Number of players must be between 3 and 6");
        }
        this.players = new ArrayList<>(numPlayers);
        for (int i = 0; i < numPlayers; i++) {
            players.add(null);
        }
        this.availableCharacters = new ArrayList<>(DEFAULT_CHARACTERS);
        this.state = GameState.MENU;
    }

    @Override
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    @Override
    public List<CreationCharacterImpl> getAvailableCharacters() {
        return List.copyOf(availableCharacters);
    }

    @Override
    public void setPlayer(final int index, final Player player) {
        players.set(index, player);
    }

    @Override
    public void assignCharacterToPlayer(final int index, final CreationCharacterImpl character) {
        if (players.get(index) == null) {
            throw new IllegalStateException("Player not initialized");
        }
        for (final Player p : players) {
            if (p != null && p.getCharacter() != null && p.getCharacter().equals(character)) {
                throw new IllegalArgumentException("This character is already chosen by another player");
            }
        }
        players.get(index).chooseCharacter(character);
        availableCharacters.remove(character);
    }

    @Override
    public GameState getState() {
        return state;
    }

    @Override
    public void enterLobby() {
        if (state != GameState.MENU) {
            throw new IllegalStateException("Game is not in the main menu");
        }
        this.state = GameState.WAITING;
    }

    @Override
    public void startGame() {
        if (state != GameState.WAITING) {
            throw new IllegalStateException("Game is not in the lobby");
        }
        if (!allCharactersAssigned()) {
            throw new IllegalStateException("Not all players have a character");
        }
        this.gameBoard = new GameBoardModelImpl();
        this.turnManager = new TurnManagerImpl(players);
        this.state = GameState.IN_PROGRESS;
    }

    @Override
    public boolean allCharactersAssigned() {
        for (final Player p : players) {
            if (p == null || p.getCharacter() == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void quitToMenu() {
        players.replaceAll(p -> null);
        availableCharacters.clear();
        availableCharacters.addAll(DEFAULT_CHARACTERS);
        this.gameBoard = null;
        this.turnManager = null;
        this.state = GameState.MENU;
    }

    @Override
    public void resetGame() {
        if (state != GameState.IN_PROGRESS) {
            throw new IllegalStateException("Game is not in progress");
        }
        for (final Player p : players) {
            if (p != null) {
                p.restore();
            }
        }
        availableCharacters.clear();
        availableCharacters.addAll(DEFAULT_CHARACTERS);
        this.gameBoard = new GameBoardModelImpl();
        this.turnManager = new TurnManagerImpl(players);
        this.state = GameState.WAITING;
    }

    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP",
        justification = "Intentional: caller needs direct reference to interact with the board"
    )
    @Override
    public GameBoardModel getGameBoard() {
        return gameBoard;
    }

    @Override
    public TurnManager getTurnManager() {
        return turnManager;
    }
}
