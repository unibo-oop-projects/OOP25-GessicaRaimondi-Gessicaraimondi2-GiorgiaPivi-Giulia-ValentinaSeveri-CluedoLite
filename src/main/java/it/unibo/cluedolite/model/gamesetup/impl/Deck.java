package it.unibo.cluedolite.model.gamesetup.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.model.creationcards.impl.CardType;
import it.unibo.cluedolite.model.creationcards.impl.Characters;
import it.unibo.cluedolite.model.creationcards.impl.Weapons;
import it.unibo.cluedolite.model.creationcards.impl.Rooms;

/**
 * Enum representing the deck of cards in the game.
 * It contains all the characters, weapons, and rooms.
 * Each card is represented as an enum constant, and the enum provides methods
 * to retrieve cards by type and to get all cards as a list.
 */
public enum Deck {

    MISS_SCARLETT(new Characters("Miss Scarlett")),
    COLONEL_MUSTARD(new Characters("Colonel Mustard")),
    MRS_WHITE(new Characters("Mrs. White")),
    MR_GREEN(new Characters("Mr. Green")),
    MRS_PEACOCK(new Characters("Mrs. Peacock")),
    PROFESSOR_PLUM(new Characters("Professor Plum")),

    CANDLESTICK(new Weapons("Candlestick")),
    DAGGER(new Weapons("Dagger")),
    LEAD_PIPE(new Weapons("Lead Pipe")),
    REVOLVER(new Weapons("Revolver")),
    ROPE(new Weapons("Rope")),
    WRENCH(new Weapons("Wrench")),

    KITCHEN(new Rooms("Kitchen")),
    BALLROOM(new Rooms("Ballroom")),
    CONSERVATORY(new Rooms("Conservatory")),
    DINING_ROOM(new Rooms("Dining Room")),
    BILLIARD_ROOM(new Rooms("Billiard Room")),
    LIBRARY(new Rooms("Library")),
    LOUNGE(new Rooms("Lounge")),
    HALL(new Rooms("Hall")),
    STUDY(new Rooms("Study"));

    private final Card card;

    /**
     * Constructs a Deck constant with the given card.
     *
     * @param card the card associated with this enum constant
     */
    Deck(Card card) {
        this.card = card;
    }

    /**
     * Returns the card associated with this enum constant.
     *
     * @return the {@link Card} of this enum constant
     */
    public Card getCard() {
        return card;
    }

    /**
     * Returns the type of the card associated with this enum constant.
     *
     * @return the {@link CardType} of this card (Character, Weapon, or Room)
     */
    public CardType getType() {
        return card.getType();
    }

    /**
     * Returns the name of the card associated with this enum constant.
     *
     * @return the name of this card as a {@link String}
     */
    public String getName() {
        return card.getName();
    }

    /**
     * Returns all cards in the deck as a list.
     *
     * @return a {@link List} containing all {@link Card} objects in the deck
     */
    public static List<Card> getAllCards() {
        return Arrays.stream(values())
                .map(Deck::getCard)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Returns all cards of a specific type as a list.
     *
     * @param type the {@link CardType} to filter by
     * @return a {@link List} containing all {@link Card} objects of the given type
     */
    public static List<Card> getCardsByType(CardType type) {
        return Arrays.stream(values())
                .filter(d -> d.getType() == type)
                .map(Deck::getCard)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}