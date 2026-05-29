package it.unibo.cluedolite.model.turnmanager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unibo.cluedolite.model.accuseandsuspect.api.InterfaceSuspicion;
import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.model.player.api.Player;
import it.unibo.cluedolite.model.turnmanager.api.TurnManager;

/**
 * Implementation of the {@link TurnManager} interface.
 */

public class TurnManagerImpl implements TurnManager{

    private final List<Player> players;
    private int currentIndex;
    private boolean gameOver = false;
    private int shownBy;

    public TurnManagerImpl(List<Player> players){
            this.players=new ArrayList<>(players);
            this.currentIndex=0;
    } 

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getCurrentPlayer(){
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
    public Player nextTurn(){
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
    public Optional<Card> checkSuspicion(InterfaceSuspicion suspicion) {
        final int suspectIndex = currentIndex;

        for (int i = 1; i < players.size(); i++) {
            final Player p = players.get((suspectIndex + i) % players.size());
            final Optional<Card> cardToShow = p.findMatchingCard(
                suspicion.getCharacter(), suspicion.getWeapon(), suspicion.getRoom());

            if (cardToShow.isPresent()) {
                this.shownBy = players.indexOf(p) + 1;
                return cardToShow;
            }
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getShownBy(){
        return this.shownBy;
    }
} 