package it.unibo.cluedolite.model.accuseandsuspect.api;

import it.unibo.cluedolite.model.accuseandsuspect.impl.Suspicion;

/**
 * Interface for the accusation manager in CluedoLite.
 * 
 * <p>An accusation reuses {@link Suspicion} as its data structure,
 * since an accusation is conceptually a suspicion checked against the solution.
 */
@FunctionalInterface
public interface InterfaceAccuseManager {

    /**
     * Checks whether the given suspicion matches the solution exactly.
     * All three cards (character, weapon, room) must match.
     *
     * @param suspicion the accusation to verify
     * @return {@code true} if the accusation is correct, {@code false} otherwise
     */
    boolean checkAccuse(InterfaceSuspicion suspicion);
}
