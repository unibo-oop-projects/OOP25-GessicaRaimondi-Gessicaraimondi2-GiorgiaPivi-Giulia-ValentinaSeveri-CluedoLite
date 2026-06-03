package it.unibo.cluedolite.model.turnmanager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unibo.cluedolite.model.accuseandsuspect.api.InterfaceSuspicion;
import it.unibo.cluedolite.model.creationcards.impl.AbstractCard;
import it.unibo.cluedolite.model.player.api.Player;
import it.unibo.cluedolite.model.turnmanager.api.TurnManager;

/**
 * Implementation of the {@link TurnManager} interface.
 * Manages turn progression, game-over state, and suspicion resolution.
 */
public final class TurnManagerImpl implements TurnManager {

    private final List<Player> players;
    private int currentIndex;
    private boolean gameOver;
    private int shownBy;

    /**
     * Creates a new {@code TurnManagerImpl}.
     *
     * @param players the list of players in turn order
     */
    public TurnManagerImpl(final List<Player> players) {
        this.players = new ArrayList<>(players);
        this.currentIndex = 0;
        this.gameOver = false;
        this.shownBy = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getCurrentPlayer() {
        return this.players.get(this.currentIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endGame() {
        this.gameOver = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameOver() {
        return this.gameOver;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player nextTurn() {
        if (this.gameOver) {
            throw new IllegalStateException("The game is over");
        }

        do {
            this.currentIndex = (this.currentIndex + 1) % this.players.size();
        } while (this.players.get(this.currentIndex).isEliminated());

        return this.players.get(this.currentIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AbstractCard> checkSuspicion(final InterfaceSuspicion suspicion) {
        final int suspectIndex = this.currentIndex;

        for (int i = 1; i < this.players.size(); i++) {
            final Player p = this.players.get((suspectIndex + i) % this.players.size());
            final Optional<AbstractCard> cardToShow = p.findMatchingCard(
                    suspicion.getCharacter(),
                    suspicion.getWeapon(),
                    suspicion.getRoom()
            );

            if (cardToShow.isPresent()) {
                this.shownBy = this.players.indexOf(p) + 1;
                return cardToShow;
            }
        }

        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getShownBy() {
        return this.shownBy;
    }
}
