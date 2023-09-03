package org.simulator.controls;

import lombok.Getter;
import lombok.Setter;
import org.simulator.board.Board;
import org.simulator.board.Components.Tile;
import org.simulator.board.StateType;
import org.simulator.gui.InfoPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;


public class SimulatorLogic extends UniversalAdapter{
    @Getter
    private final JFrame mainFrame;
    @Getter
    private Mode mode;
    @Getter @Setter
    private Board board;
    @Getter @Setter
    private StateType drawType;
    private Tile startTile;
    private Tile endTile;
    @Getter @Setter
    private String path;
    @Getter
    private final InfoPanel infoPanel;

    public SimulatorLogic(JFrame mainFrame){
        this.drawType = StateType.NONE;
        this.mainFrame = mainFrame;
        mainFrame.setVisible(true);
        this.mode = Mode.DRAW_MODE;
        this.startTile = null;
        this.endTile = null;
        infoPanel = new InfoPanel();
    }
    public void changeMode(Mode mode){
        this.board.changeBoardMode(mode);
        this.mode = mode;
        this.board.repaint();
    }
    private void paintTile(MouseEvent e){
        try {
            Component current = this.board.getComponentAt(e.getX(), e.getY());
            if (drawType != StateType.NONE && drawType != this.board.findTile((Tile) current).getStateType()) {
                this.board.changeTile((Tile) current, drawType);
            }
            //CheckConnectionLogic.setFlagForIncorrectConnections(board);
            this.board.repaint();
        }catch(Exception i){
            System.out.println("not tile");
        }
    }
    private void getComponentInfo(MouseEvent e){
        //this.board.repaint();
        try{
            Component current = this.board.getComponentAt(e.getX(), e.getY());
            Tile temp = (Tile)current;
            //infoPanel.updateInfoPanel(temp);
        }catch(ClassCastException i){
            System.out.println("not tile");
        }
    }
    //mouseMoved is only for testing
    @Override
    public void mouseMoved(MouseEvent e){
        try{
            Component current = this.board.getComponentAt(e.getX(),e.getY());
            //this.board.TestStatus((Tile) current);
            //test((Tile) current);
        }catch(Exception i){
            System.out.println("not a tile");
        }
    }
    @Override
    public void mouseClicked(MouseEvent e){
        if (SwingUtilities.isRightMouseButton(e)) {
            getComponentInfo(e);
        }else if(SwingUtilities.isLeftMouseButton(e)){
            paintTile(e);
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        try{
            Component current = this.board.getComponentAt(e.getX(), e.getY());
            startTile = this.board.findTile((Tile) current);
        }catch(Exception i){
            System.out.println("not tile");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        try {
            Component current = this.board.getComponentAt(e.getX(), e.getY());
            endTile = this.board.findTile((Tile) current);
            //connectTiles();
        }catch(Exception i){
            System.out.println("not tile");
        }
    }
//    void connectTiles(){
//        for (Direction direction : Direction.values()) {
//            if (endTile.getNeighbours().containsKey(direction)) {
//                if (Objects.equals(endTile.getNeighbours().get(direction).getTile(), startTile)) {
//                    if (!endTile.getNeighbours().get(direction).isConnected()) {
//                        endTile.connectBothWays(direction);
//                    } else {
//                        endTile.disconnectBothWays(direction);
//                    }
//                }
//            }
//        }
//        //testStartAndEnd(startTile, endTile);
//        endTile = null;
//        startTile = null;
//        CheckConnectionLogic.setFlagForIncorrectConnections(board);
//        this.board.repaint();
//    }
//    void test(Tile current){
//        this.board.TestStatus(current);
//        System.out.println("mode: " + mode);
//    }
//    void testStartAndEnd(Tile startTile,Tile endTile){
//        System.out.println("start:");
//        this.board.TestStatus(startTile);
//        System.out.println("end:");
//        this.board.TestStatus(endTile);
//    }
}
