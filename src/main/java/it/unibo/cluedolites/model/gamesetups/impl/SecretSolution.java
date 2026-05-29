package it.unibo.cluedolite.model.gamesetup.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.model.creationcards.impl.CardType;
import it.unibo.cluedolite.model.gamesetup.api.InterfaceSecretSolution;

public class SecretSolution implements InterfaceSecretSolution{
    private final List<Card> solution = new ArrayList<>();
    private Card secretCharacter;
    private Card secretWeapon;
    private Card secretRoom;
    
    public SecretSolution(List<Card> cards) {
        Collections.shuffle(cards);
        generateSecretSolution(cards);
    }

    private void generateSecretSolution(List<Card> cards){
        secretCharacter = null;
        secretWeapon = null;
        secretRoom = null;

        for (Card card : cards) {
            if (card.getType() == CardType.CHARACTER && secretCharacter == null) {
                secretCharacter = card;
                solution.add(secretCharacter);
            } else if (card.getType() == CardType.WEAPON && secretWeapon == null) {
                secretWeapon = card;
                solution.add(secretWeapon);
            } else if (card.getType() == CardType.ROOM && secretRoom == null) {
                secretRoom = card;
                solution.add(secretRoom);
            }
        }
        cards.remove(secretCharacter);
        cards.remove(secretWeapon);
        cards.remove(secretRoom);
    }

    public List<Card> getSolution() {
        return solution;
    }
}