package it.unibo.cluedolite.model.accuseandsuspect.impl;

import java.util.List;

import it.unibo.cluedolite.model.accuseandsuspect.api.InterfaceAccuseManager;
import it.unibo.cluedolite.model.accuseandsuspect.api.InterfaceSuspicion;
import it.unibo.cluedolite.model.creationcards.impl.*;
import it.unibo.cluedolite.model.gamesetup.impl.*;

/*
* This class manages the logic for formal accusations in the Cluedo game.
* It checks if a player's accusation matches the secret solution of the game.
* An accusation is essentially a suspicion that is checked against the solution.
* The class implements the InterfaceAccuseManager, which defines the contract for checking accusations.
*/

public class AccuseManager implements InterfaceAccuseManager {

    private final SecretSolution secretSolution;

    public AccuseManager(SecretSolution secretSolution) {
        this.secretSolution = secretSolution;
    }

    @Override
    public boolean checkAccuse(InterfaceSuspicion suspicion) {
        List<Card> solution = secretSolution.getSolution();
        return solution.contains(suspicion.getCharacters())
            && solution.contains(suspicion.getWeapon())
            && solution.contains(suspicion.getRoom());
    }
}