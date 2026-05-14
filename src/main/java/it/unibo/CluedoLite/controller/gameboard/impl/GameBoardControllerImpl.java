package it.unibo.CluedoLite.controller.gameboard.impl;

import it.unibo.CluedoLite.controller.gameboard.api.GameBoardController;
import it.unibo.CluedoLite.model.gameboard.api.GameBoardModel;
import it.unibo.CluedoLite.model.gameboard.api.Room;
import it.unibo.CluedoLite.model.player.api.Player;
import it.unibo.CluedoLite.model.turnmanager.api.TurnManager;
import it.unibo.CluedoLite.view.gameboard.api.Board;

public class GameBoardControllerImpl implements GameBoardController{
    GameBoardModel gb;
    TurnManager tm;
    Board view;

    public GameBoardControllerImpl(GameBoardModel gb,TurnManager tm){
        this.gb=gb;
        this.tm=tm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setView(Board v) {
        this.view = v;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move(Room r){
        Player currentplayer=tm.getCurrentPlayer();

        if (r == null) return;

        if (currentplayer.isEliminated()) {
            view.wrongRoomSelected(); 
            return;
        }

         if (currentplayer.hasmoved()) {
            return;
        }

        if(gb.canMoveTo(currentplayer, r)){
            gb.setPlayerPosition(currentplayer,r);
            currentplayer.setMoved(true);
            view.repaint();
        }else{
            view.wrongRoomSelected();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player currentPlayer(){
        return tm.getCurrentPlayer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Room getRoomByName(String name) {
        return gb.getRoomByName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Room getCurrentRoomOf(Player p) {
        return gb.getPlayerPosition(p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endTurn(){
        tm.nextTurn();
        view.repaint();
    }
}
