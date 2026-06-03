package it.unibo.cluedolite.controller.endturnbuttoncontroller.impl;

import java.util.Objects;

import it.unibo.cluedolite.controller.endturnbuttoncontroller.api.EndTurnController;

/**
 * Implementation of {@link EndTurnController}.
 * Delegates the end-turn logic to a {@link Runnable} provided at construction time,
 * keeping this controller decoupled from {@code GameController}.
 */
public final class EndTurnControllerImpl implements EndTurnController {

    private final Runnable onEndTurn;

    /**
     * Creates a new {@code EndTurnControllerImpl}.
     *
     * @param onEndTurn callback executed when the player ends their turn;
     *                  typically {@code GameController::advanceTurn}
     */
    public EndTurnControllerImpl(final Runnable onEndTurn) {
        this.onEndTurn = Objects.requireNonNull(onEndTurn, "onEndTurn must not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEndTurnClicked() {
        onEndTurn.run();
    }
}
