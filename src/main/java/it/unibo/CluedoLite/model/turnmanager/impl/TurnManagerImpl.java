package it.unibo.CluedoLite.model.turnmanager.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.CluedoLite.model.accuseandsuspect.impl.Suspicion;
import it.unibo.CluedoLite.model.creationcards.impl.Card;
import it.unibo.CluedoLite.model.turnmanager.api.TurnManager;
import it.unibo.CluedoLite.model.player.api.Player;

/**
 * Implementation of the {@link TurnManager} interface.
 */

public class TurnManagerImpl implements TurnManager{

    private final List<Player> players;
    private int currentIndex;
    private boolean gameOver = false;

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
            getCurrentPlayer().setMoved(false);
            this.currentIndex = (this.currentIndex + 1) % this.players.size();
        } while (this.players.get(this.currentIndex).isEliminated());

        return this.players.get(this.currentIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Card checkSuspicion(Suspicion suspicion) {
        int suspectIndex = currentIndex;

        for (int i = 1; i < players.size(); i++) {
            Player respondent = players.get((suspectIndex + i) % players.size());
            Card cardToShow = respondent.findMatchingCard(suspicion.getCharacters(), suspicion.getWeapon(), suspicion.getRoom());

            if (cardToShow != null) {
                return cardToShow;
            }
        }
        return null;
    }
} 