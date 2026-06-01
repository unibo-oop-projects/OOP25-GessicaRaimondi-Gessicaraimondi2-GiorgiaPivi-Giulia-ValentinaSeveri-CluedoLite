package it.unibo.cluedolite.model.suspectnotes.api;

/**
 * Represents the possible states of a card in the suspect notes.
 */
public enum State {
    /** The card is still a possible suspect. */
    POSSIBLE,
    /** The card has been excluded from suspicion. */
    EXCLUDED
}
