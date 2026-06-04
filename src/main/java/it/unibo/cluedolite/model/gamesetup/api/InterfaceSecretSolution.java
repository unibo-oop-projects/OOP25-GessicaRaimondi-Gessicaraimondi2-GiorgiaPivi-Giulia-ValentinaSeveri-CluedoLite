package it.unibo.cluedolite.model.gamesetup.api;

import java.util.List;

import it.unibo.cluedolite.model.creationcards.impl.AbstractCard;

/**
 * Interface for the secret solution of the game.
 * The secret solution is composed of one character, one weapon and one room card.
 */
@FunctionalInterface
public interface InterfaceSecretSolution {

    /**
     * Returns the list of cards that compose the secret solution.
     * 
     * @return a list containing the secret character, weapon and room card.
     */
    List<AbstractCard> getSolution();
}
