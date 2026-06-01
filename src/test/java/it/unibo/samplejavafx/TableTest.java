package it.unibo.samplejavafx;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.model.creationcards.impl.CardType;
import it.unibo.cluedolite.model.gamesetup.impl.Deck;
import it.unibo.cluedolite.model.suspectnotes.api.Box;
import it.unibo.cluedolite.model.suspectnotes.api.State;
import it.unibo.cluedolite.model.suspectnotes.api.Table;
import it.unibo.cluedolite.model.suspectnotes.impl.BoxImpl;
import it.unibo.cluedolite.model.suspectnotes.impl.TableImpl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link Table} and {@link Box}.
 */
class TableTest {

    private Card missScarlett;
    private Card candlestick;
    private Card kitchen;

    @BeforeEach
    void setUp() {
        final List<Card> allCards = Deck.getAllCards();

        missScarlett = allCards.stream()
                .filter(c -> c.getType() == CardType.CHARACTER)
                .findFirst()
                .orElseThrow();
        candlestick = allCards.stream()
                .filter(c -> c.getType() == CardType.WEAPON)
                .findFirst()
                .orElseThrow();
        kitchen = allCards.stream()
                .filter(c -> c.getType() == CardType.ROOM)
                .findFirst()
                .orElseThrow();
    }

    @Test
    void defaultState() {
        final Box box = new BoxImpl(missScarlett);
        assertEquals(State.POSSIBLE, box.getState());
    }

    @Test
    void excludeCard() {
        final Box box = new BoxImpl(missScarlett);
        box.excludeCard();
        assertEquals(State.EXCLUDED, box.getState());
    }

    @Test
    void tableCreation() {
        final Table table = new TableImpl(new ArrayList<>());
        final int deckSize = Deck.getAllCards().size();
        final int boxesNum = table.searchType(missScarlett).size()
                     + table.searchType(candlestick).size()
                     + table.searchType(kitchen).size();
        assertEquals(deckSize, boxesNum);
    }

    @Test
    void searchTypeCharacters() {
        final Table table = new TableImpl(new ArrayList<>());
        final List<Box> characterList = table.searchType(missScarlett);
        assertTrue(characterList.stream().allMatch(b -> b.getCard().getType() == CardType.CHARACTER));
    }

    @Test
    void searchTypeWeapons() {
        final Table table = new TableImpl(new ArrayList<>());
        final List<Box> weaponList = table.searchType(candlestick);
        assertTrue(weaponList.stream().allMatch(b -> b.getCard().getType() == CardType.WEAPON));
    }

    @Test
    void searchTypeRooms() {
        final Table table = new TableImpl(new ArrayList<>());
        final List<Box> roomList = table.searchType(kitchen);
        assertTrue(roomList.stream().allMatch(b -> b.getCard().getType() == CardType.ROOM));
    }

    @Test
    void initializeTableTwice() {
        final List<Card> hand = List.of(missScarlett);
        final Table table = new TableImpl(hand);
        assertDoesNotThrow(() -> table.initializeTable(hand));

        final long characterCount = Deck.getAllCards().stream()
                .filter(c -> c.getType() == CardType.CHARACTER).count();
        assertEquals(characterCount, table.searchType(missScarlett).size());
    }

    @Test
    void updateTable() {
        final Table table = new TableImpl(new ArrayList<>());
        assertFalse(table.alreadyExcluded(missScarlett));
        table.updateTable(missScarlett);
        assertTrue(table.alreadyExcluded(missScarlett));
    }

    @Test
    void alreadyExcludedReturnsFalseByDefault() {
        final Table table = new TableImpl(new ArrayList<>());
        assertFalse(table.alreadyExcluded(missScarlett));
    }

    @Test
    void initializeTableExcludesHandCards() {
        final List<Card> hand = List.of(missScarlett, candlestick, kitchen);
        final Table table = new TableImpl(hand);
        assertTrue(table.alreadyExcluded(missScarlett));
        assertTrue(table.alreadyExcluded(candlestick));
        assertTrue(table.alreadyExcluded(kitchen));
    }
}