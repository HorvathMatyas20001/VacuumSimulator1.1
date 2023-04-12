package org.simulator.controls;

import lombok.Getter;
import lombok.Setter;
import org.simulator.board.Board;
import org.simulator.board.Direction;
import org.simulator.board.StateType;
import org.simulator.board.Tile;
import org.simulator.gui.CheckConnections;
import org.simulator.gui.ComponentButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;


public class SimulatorLogic extends UniversalAdapter{
    @Getter
    private JFrame mainFrame;
    public static final int INITIAL_BOARD_SIZE = 10;
    @Getter
    private Mode mode;
    @Getter @Setter
    private Board board;
    @Getter @Setter
    private StateType drawType;
    private Tile startTile;
    private Tile endTile;
    @Getter
    private int xDimension;
    @Getter
    private int yDimension;
    @Getter @Setter
    private List<ComponentButtons> componentButtonsList;
    @Getter @Setter
    private CheckConnections checkConnectionsButton;

    public SimulatorLogic(JFrame mainFrame){
        this.drawType = StateType.NONE;
        this.mainFrame = mainFrame;
        xDimension = 10;
        yDimension = 20;
        //this.initializeNewBoard(xDimension,yDimension);
        //this.mainFrame.add(this.board);
        mainFrame.setVisible(true);
        this.mode = Mode.DRAW_MODE;
        startTile = null;
        endTile = null;
    }

    private void initializeNewBoard(int xDimension, int yDimension){
        this.board = new Board(xDimension,yDimension);
        this.board.addMouseListener(this);
        this.board.addMouseMotionListener(this);
    }
    public void changeMode(Mode mode){
        this.board.changeBoardMode(mode);
        this.mode = mode;
        this.board.repaint();
    }
    private void generateErrorMessage(){

    }
    private void paintTile(MouseEvent e){
        try {
            Component current = this.board.getComponentAt(e.getX(), e.getY());
            if (drawType != StateType.NONE && drawType != this.board.findTile((Tile) current).getStateType()) {
                this.board.changeTile((Tile) current, drawType);
            }
            this.board.repaint();
        }catch(Exception i){
            //System.out.println("not tile");
        }
    }
    private void getComponentInfo(MouseEvent e){
        this.board.repaint();
        try{
            Component current = this.board.getComponentAt(e.getX(), e.getY());
            Tile temp = (Tile)current;
            String error = "none";
            if(temp.isTooFewConnections()){
                error = "too few connections";
            }else if(temp.isTooManyConnections()){
                error = "too Many connections";
            }
            JOptionPane.showMessageDialog(null,"Component type:"+ temp.getStateType()+"\n"
                    + "Max connections:" + temp.getStateType().getMaxConnections() + "\n"
                    + "Min connections:" + temp.getStateType().getMinConnections() + "\n"
                    + "Errors:" + error,"Information",JOptionPane.INFORMATION_MESSAGE);
        }catch(ClassCastException i){}
    }

    @Override
    public void mouseMoved(MouseEvent e){
        try{
            Component current = this.board.getComponentAt(e.getX(),e.getY());
            //this.board.TestStatus((Tile) current);
            //test((Tile) current);
        }catch(Exception i){
            //System.out.println("not a tile");
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
            //System.out.println("not tile");
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        try {
            Component current = this.board.getComponentAt(e.getX(), e.getY());
            endTile = this.board.findTile((Tile) current);
            for (Direction direction : Direction.values()) {
                if (endTile.getNeighbours().containsKey(direction)) {
                    if (Objects.equals(endTile.getNeighbours().get(direction).getTile(), startTile)) {
                        if (!endTile.getNeighbours().get(direction).isConnected()) {
                            endTile.connectBothWays(direction);
                        } else {
                            endTile.disconnectBothWays(direction);
                        }
                    }
                }
            }
            //testStartAndEnd(startTile, endTile);
            endTile = null;
            startTile = null;
            this.board.repaint();
        }catch(Exception i){
            //System.out.println("not tile");
        }
    }
    void test(Tile current){
        this.board.TestStatus(current);
        System.out.println("mode: " + mode);
    }
    void testStartAndEnd(Tile startTile,Tile endTile){
        System.out.println("start:");
        this.board.TestStatus(startTile);
        System.out.println("end:");
        this.board.TestStatus(endTile);
    }
}
